package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.domains.Cover;

/**
 * DTO de la class cover
 */
public class DTOCover {

    private final String title;
    private final String isbn;
    private final String resume;
    private final DTOAuthor author;

    /**
     * constructeur de DTOCover
     * @param cover
     *      couverture du livre
     */
    public DTOCover(final Cover cover){
        this.title = cover.getTitle();
        this.isbn = cover.getIsbn();
        this.resume = cover.getResume();
        this.author = new DTOAuthor(cover.getAuthor());
    }

    public String getTitle(){return title;}
    public String getIsbn(){return isbn;}
    public String getResume(){return resume;}
    public DTOAuthor getAuthor(){return author;}

    /**
     * constructeur de DTOCover
     * @param title
     *      titre de la couverture
     * @param isbn
     *      isbn de la couverture
     * @param resume
     *      resume de la couverture
     * @param author
     *      auteur de la couverture
     */
    public DTOCover(final String title, final String isbn, final String resume, final DTOAuthor author){
        this.title = title;
        this.isbn = isbn;
        this.resume = resume;
        this.author = author;
    }

    /**
     * convertit un DTOCover en Cover
     * @return le Cover du livre
     */
    public Cover createCover(){
        return new Cover(title, isbn, resume, author.convertDTOAuthor());
    }

}
