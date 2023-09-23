package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.domains.*;

import java.util.ArrayList;
import java.util.List;

/**
 * class qui fait le transition entre les class Domains
 * et les ressources
 */
public class Mapping {

    /**
     * converti un book en DTOBook
     * @param book
     *      book à convertir
     * @return DTOBook
     */
    public static DTOBook writeBook(Book book){
        return new DTOBook(book);
    }

    /**
     * converti un author en DTOAuthor
     * @param author
     *      author à convertir
     * @return DTOAuthor
     */
    public static DTOAuthor writeAuthor(Author author){
        return new DTOAuthor(author);
    }

    /**
     * converti un DTOAuthor en author
     * @param author
     *      author à convertir
     * @return Author
     */
    public static Author readAuthor(DTOAuthor author){
        return author.convertDTOAuthor();
    }

    /**
     * converti un Cover en DTOCover
     * @param cover
     *      cover à convertit
     * @return DTO du Cover
     */
    public static DTOCover writeCover(Cover cover){
        return new DTOCover(cover);
    }

    /**
     * converti un DTOCover en Cover
     * @param cover
     *      DTOCover à convertit
     * @return Cover du DTOCover
     */
    public static Cover readCover(DTOCover cover){
        return cover.createCover();
    }

    /**
     * converti un DTOPage en page
     * @param page
     *      page à convertir
     * @return DTOPage
     */
    public static DTOPage writePage(Page page){
        return new DTOPage(page);
    }

    /**
     * converti un DTOPage en page
     * @param choice
     *      page à convertir
     * @return Author
     */
    public static DTOChoice writeChoice(Choice choice){
        return new DTOChoice(choice);
    }

    /**
     * converti un DTOPage en page
     * @param page
     *      page à convertir
     * @return Author
     */
    public static Page readpage(DTOPage page){
        return page.createPage();
    }

    /**
     * converti une liste de Page en liste
     * de DTOPage
     * @param listPages
     *      liste de page à convertir
     * @return liste de DTOPage
     */
    public static List<DTOPage> writePage(List<Page> listPages){
        List<DTOPage> list = new ArrayList<>();
        for(var page : listPages){
            list.add(new DTOPage(page));
        }
        return list;
    }

    /**
     * converti une liste de Page en liste
     * de DTOPage
     * @param listChoices
     *      liste de page à convertir
     * @return liste de DTOPage
     */
    public static List<DTOChoice> writeChoice(List<Choice> listChoices){
        List<DTOChoice> list = new ArrayList<>();
        for(var choice : listChoices){
            list.add(new DTOChoice(choice));
        }
        return list;
    }

    /**
     * converti une liste de DTO en List de Choice
     * @param listChoices
     *      list à convertir
     * @return list de choix
     */
    public static List<Choice> readChoice(List<DTOChoice> listChoices){
        List<Choice> list = new ArrayList<>();
        for(var choice : listChoices){
            list.add(choice.newChoice());
        }
        return list;
    }

    /**
     * converti une liste de DTOBook en liste de Book
     * @param books
     *      liste de DTOBook à convertir
     * @return liste de book
     */
    public static List<Book> readBook(List<DTOBook> books) {
        List<Book> list = new ArrayList<>();
        for (var book : books) {
            list.add(book.createBook());
        }
        return list;
    }

}
