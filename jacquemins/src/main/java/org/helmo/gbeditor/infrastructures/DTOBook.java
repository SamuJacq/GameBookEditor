package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.domains.Book;
import org.helmo.gbeditor.domains.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * class qui va convertir un book en DTObook
 * pour l'enregistre dans la BD
 */
public class DTOBook {
    private final DTOCover cover;
    private List<DTOPage> listPage = new ArrayList<>();
    private final boolean publier;

    /**
     * constructeur qui converti un book
     * @param book
     *      book à convertir
     */
    public DTOBook(Book book){
        this.cover = new DTOCover(book.getCover());
        this.listPage = Mapping.writePage(book.getPapers());
        this.publier = book.isPublier();
    }

    public DTOCover getCover(){return cover;}
    public List<DTOPage> getListPage(){return listPage;}
    public boolean isPublier(){return publier;}

    /**
     * constructeur de DTOBook
     * @param cover
     *      DTO de la couverture du livre
     * @param publier
     *      indique si le livre est publier
     */
    public DTOBook(final DTOCover cover, final boolean publier){
        this.cover = cover;
        this.publier = publier;
    }

    /**
     * ajoute une DTOPage à la list du dtoBook
     * @param index
     * @param contain
     */
    public void addPage(int index, String contain){
        listPage.add(index-1,new DTOPage(contain));
    }

    /**
     * creer un book avec les infos du dto
     * @return un book
     */
    public Book createBook(){
        Book book = new Book(Mapping.readCover(cover), this.publier);
        for(var page : this.listPage){
            book.addPage(new Page(Mapping.readpage(page).getContain()));
        }
        return book;
    }

}
