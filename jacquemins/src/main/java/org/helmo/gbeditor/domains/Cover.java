package org.helmo.gbeditor.domains;

/**
 * class qui represente la couverture de son livre
 */
public class Cover {

    private final String title;
    private final ISBN isbn;
    private final String resume;
    private final Author author;

    /**
     * constructeur de Cover
     * @param title
     *      titre du livre
     * @param isbn
     *       isbn du livre
     * @param resume
     *      resume du livre
     * @param author
     *      auteur du livre
     */
    public Cover(final String title, final String isbn, final String resume, final Author author){
        this.title = title == null || title.isBlank() ? "unknown title" : title;
        this.isbn = new ISBN(isbn);
        this.resume = resume == null || resume.isBlank() ? "unknown resume" : resume;
        this.author = author == null ? new Author(null, null) : author;
    }

    public String getTitle(){
        return title;
    }
    public String getIsbn(){
        return isbn.getNumero();
    }
    public String getResume(){
        return resume;
    }
    public Author getAuthor(){
        return author;
    }

}
