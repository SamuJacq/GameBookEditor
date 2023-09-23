package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.domains.*;
import org.helmo.gbeditor.repositories.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class qui gerer tous ce qui à avoir avec la BD
 */
public class DataStorage implements StorageRepository , AutoCloseable{

    private final Connection connection;
    private final Map<String, Integer> mapBook = new HashMap<>();
    private final Map<Integer,Integer> mapPage = new HashMap<>();

    /**
     * constructeur de DataStorage
     * @param con
     *      connection à la BD
     */
    public DataStorage(final Connection con){
        this.connection = con;
    }

    public Connection getConnection(){return connection;}

    @Override
    public void saveBook(Book book) throws ISBNException, AuthorException {
        DTOCover cover = Mapping.writeCover(book.getCover());
        try (PreparedStatement insert = connection.prepareStatement(
                "INSERT INTO Book(title, isbn, resume, isPublier, idAuthor)VALUES(?,?,?,false,?)",
             Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            insertBook(insert, cover);
            insert.executeBatch();
            try(ResultSet generatedKeys = insert.getGeneratedKeys()){
                if(generatedKeys.next()){
                    mapBook.put(cover.getIsbn(), generatedKeys.getInt(1));
                }
            }
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new ISBNException("isbn déjà existante, veuillez choisir un autre\n", e);
        }finally {
            activeAutoCommit();
        }
    }

    @Override
    public void updateBook(Book book, String lastISBN) throws ISBNException {
        DTOCover cover = Mapping.writeCover(book.getCover());
        try (PreparedStatement update = connection.prepareStatement("UPDATE Book set title = ?, isbn = ?, resume = ? where isbn like ?")){
            connection.setAutoCommit(false);
            updateBook(update, cover, lastISBN);
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new ISBNException("isbn déjà existante, veuillez choisir un autre\n" , e);
        }finally {
            activeAutoCommit();
        }
    }

    private void updateBook(PreparedStatement update, DTOCover cover, String lastISBN) throws SQLException {
        update.setString(1, cover.getTitle());
        update.setString(2, cover.getIsbn());
        update.setString(3, cover.getResume());
        update.setString(4, lastISBN);

        update.executeUpdate();

    }

    private void insertBook(PreparedStatement insert, DTOCover cover) throws SQLException, AuthorException {
        insert.setString(1, cover.getTitle());
        insert.setString(2, cover.getIsbn());
        insert.setString(3, cover.getResume());
        insert.setInt(4, existsAuthor(cover.getAuthor()));

        insert.addBatch();

    }

    @Override
    public void saveAuthor(Author author) throws AuthorException {
        DTOAuthor dtoauthor = Mapping.writeAuthor(author);
        if(existsAuthor(dtoauthor) == 0){
            try (PreparedStatement insert = connection.prepareStatement("INSERT INTO Author(name)VALUES(?)")) {
                connection.setAutoCommit(false);
                insert.setString(1, dtoauthor.getName());
                insert.addBatch();
                insert.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                rollback();
                throw new AuthorException("nous avons pas su vous enregistrer\n", e);
            }finally {
                activeAutoCommit();
            }
        }

    }

    private int existsAuthor(DTOAuthor author) throws AuthorException {
        try(PreparedStatement select = connection.prepareStatement("SELECT* FROM Author WHERE name LIKE ?")){
            select.setString(1, author.getName());
            try(ResultSet rs = select.executeQuery()){
                return rs.next() ? rs.getInt("idAuthor") : 0;
            }
        }catch(SQLException e){
            throw new AuthorException("nous n'avons pas su charger votre profil \n", e);
        }

    }

    @Override
    public void saveEdit(Book book) throws BookException {
        DTOBook dtoBook = Mapping.writeBook(book);
        try{
            connection.setAutoCommit(false);
            deletePage(dtoBook.getCover());
            savePageAndChoice(dtoBook.getCover(), dtoBook.getListPage());
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new BookException("nous avons pas su enregistrer vos page et choix",e);
        }finally {
            activeAutoCommit();
        }
    }

    private void deletePage(DTOCover cover) throws SQLException {
        try (PreparedStatement deletePage = connection.prepareStatement(
                "DELETE FROM Page where idBook = ?")) {
            deletePage.setInt(1, mapBook.get(cover.getIsbn()));
            deletePage.executeUpdate();
        }
    }

    private void savePageAndChoice(DTOCover cover, List<DTOPage> listPage) throws SQLException {
        int i = 1;
        for(var page : listPage){
            savePage(page, i, cover.getIsbn());
            for(var choice : page.getListDto()){
                saveChoice(choice, i);
            }
            i++;
        }
    }

    private void savePage(DTOPage page, int numPage, String isbn) throws SQLException {
        try (PreparedStatement pageInsert = connection.prepareStatement(
                "INSERT INTO Page(numPage, contain, idBook)VALUES(?,?,?)"
                ,Statement.RETURN_GENERATED_KEYS)) {
            //connection.setAutoCommit(false);
            insertPage(pageInsert, page, numPage, isbn);
            try(ResultSet generatedKeys = pageInsert.getGeneratedKeys()){
                if(generatedKeys.next()) {
                    mapPage.put(numPage, generatedKeys.getInt(1));
                }
            }
        }
    }

    private void insertPage(PreparedStatement insert, DTOPage dtoPage, int numPage, String isbn) throws SQLException {
        insert.setInt(1, numPage);
        insert.setString(2, dtoPage.getContain());
        insert.setInt(3, mapBook.get(isbn));
        insert.addBatch();
        insert.executeBatch();
    }

    private void saveChoice(DTOChoice choice, int numPage) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(
                "insert into Choice(numPage, summary, idPage)values(?, ?, ?)")) {
            //connection.setAutoCommit(false);
            insert.setInt(1,choice.getReferencePage());
            insert.setString(2,choice.getContain());
            insert.setInt(3,mapPage.get(numPage));
            insert.addBatch();
            insert.executeBatch();
        }
    }

    @Override
    public List<Book> load(Author author) throws BookException {
        DTOAuthor dtoauthor = Mapping.writeAuthor(author);
        List<DTOBook> list = new ArrayList<>();
        try {
            loadBook(dtoauthor, list);
        } catch (SQLException e) {
            throw new BookException("nous avons pas su charger vos livres \n",e);
        }
        return Mapping.readBook(list);
    }

    @Override
    public List<Choice> loadChoice(int numPageCurrent) throws ChoiceException {
        List<DTOChoice> choices = new ArrayList<>();
        if(!mapPage.isEmpty()) {
            try (PreparedStatement select = connection.prepareStatement(
                    "SELECT * FROM Choice c WHERE idPage = ?")) {
                select.setInt(1, mapPage.get(numPageCurrent));
                try (ResultSet rs = select.executeQuery()) {
                    while (rs.next()) {
                        choices.add(new DTOChoice(rs.getString("summary"), rs.getInt("numPage")));
                    }
                }
            } catch (SQLException e) {
                throw new ChoiceException("nous avons pas su charger vos choix \n", e);
            }
        }
        return Mapping.readChoice(choices);
    }

    @Override
    public List<Page> loadPage(Book book) throws PageException {
        List<Page> listPage = new ArrayList<>();
        DTOCover cover = Mapping.writeCover(book.getCover());
        mapPage.clear();
        try(PreparedStatement select = connection.prepareStatement("SELECT * FROM Page WHERE idBook = ? Order by numPage")){
            select.setInt(1, mapBook.get(cover.getIsbn()));
            try(ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    listPage.add(Mapping.readpage(new DTOPage(rs.getString("contain"))));
                    mapPage.put(rs.getInt("numPage"), rs.getInt("idPage"));
                }
            }
        }catch (SQLException e) {
            throw new PageException("nous avons pas su charger vos pages \n", e);
        }
        return listPage;
    }

    private void loadBook(DTOAuthor author,List<DTOBook> list) throws SQLException {
        try(PreparedStatement select = connection.prepareStatement(
                "SELECT * FROM Book b JOIN Author a on a.idAuthor = b.idAuthor WHERE a.name LIKE ?")){
            select.setString(1, author.getName());
            try(ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    list.add(new DTOBook(new DTOCover(rs.getString("title"), rs.getString("isbn"), rs.getString("resume"), author), rs.getBoolean("isPublier")));
                    mapBook.put(rs.getString("isbn"), rs.getInt("idBook"));
                }
            }
        }

    }

    @Override
    public void publierBook(Book book) throws BookException {
        DTOCover cover = Mapping.writeCover(book.getCover());
        try (PreparedStatement update = connection.prepareStatement(
                "UPDATE Book set isPublier = true where isbn like ?")){
            connection.setAutoCommit(false);
            update.setString(1, cover.getIsbn());
            update.executeUpdate();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            rollback();
            throw new BookException("nous avons pas su publier votre livre\n", e);
        }finally {
            activeAutoCommit();
        }
    }

    private void activeAutoCommit(){
        try{
            connection.setAutoCommit(true);
        }catch(SQLException e){
            throw new RuntimeException("autoCommit true raté", e);
        }
    }

    private void rollback(){
        try{
            connection.rollback();
        }catch(SQLException e){
            throw new RuntimeException("rollback raté", e);
        }
    }

    @Override
    public void close(){
        try{
            connection.close();
        }catch (SQLException ex){
            throw new RuntimeException("la fermeture a échoué\n", ex);
        }
    }

}
