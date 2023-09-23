package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.domains.Book;
import org.helmo.gbeditor.domains.Editor;
import org.helmo.gbeditor.repositories.*;

import java.util.ArrayList;
import java.util.List;

/**
 * presenter pour la view du menu principal
 */
public class HomePresenter {

    private final Presenters presenters;
    private HomeInterface view;
    private List<Book> listBook;
    private final Editor editor;

    /**
     * constructeur HomePresenter
     * @param presenters
     *      presenter principale
     * @param editor
     *      editeur des classes domains
     */
    public HomePresenter(final Presenters presenters, final Editor editor){
        this.presenters = presenters;
        this.editor = editor;
    }

    public void setView(HomeInterface view){
        this.view = view;
    }

    /**
     * recupere l'auteur enregistrer pour l'afficher
     * @return l'auteur enregistrer
     */
    public String getAuthor(){return editor.getAuthor().toString();}

    /**
     * vérifie si les données création du livre sont correctes
     * et créer un livre
     * @param title
     *      titre entré
     * @param isbn
     *      isbn entré
     * @param resume
     *      resume entré
     */
    public void checkCreateBook(final String title, final String isbn, final String resume){
        String message = editor.messageError(title, isbn, resume);
        if(!message.isEmpty()){
            view.alert(message);
        }else{
            verifBook(editor.createBook(title, isbn, resume));
        }
    }

    private void verifBook(final Book book){
        try{
            saveBook(book);
        }catch(ISBNException e){
            view.alert("isbn déjà utilisé");
        } catch (AuthorException e) {
            view.alert(e.getMessage());
        } catch (BookException e) {
            view.alert(e.getMessage());
        }
    }

    private void saveBook(final Book book) throws ISBNException, AuthorException, BookException{
        presenters.saveBook(book, false, "");
        listBook.add(book);
        listBook = presenters.loadBook();
        view.success("nouveau livre créé");
    }

    /**
     * charge les livre de l'auteur
     * @return les livre de l'auteur
     */
    public List<BookViewModel> loadBook(){
        List<BookViewModel> list = new ArrayList<>();
        if(listBook == null || listBook.isEmpty()){
            try {
                this.listBook = presenters.loadBook();
            } catch (BookException e) {
               view.alert(e.getMessage());
            }
        }
        for(var book : listBook){
            BookViewModel model = new BookViewModel(book.getCover(), book.isPublier());
            list.add(model);
        }
        return list;
    }

    /**
     * converti le livre selectionner en BookViewModel
     * pour l'afficher
     * @param index
     *      index du livre dans la list afficher
     * @return BookViewModel du livre séléctionné
     */
    public BookViewModel getBookSelected(final int index){
        editor.setCurrentBook(listBook.get(index));
        return new BookViewModel(editor.getCurrentBook().getCover(), editor.getCurrentBook().isPublier());
    }

    /**
     * indique qu'il faut switch sur
     * la view d'EditBook
     */
    public void switchEditBook(){
        presenters.switchView("EditBook");
    }

}
