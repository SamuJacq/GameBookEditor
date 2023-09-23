package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.domains.Author;
import org.helmo.gbeditor.domains.Cover;

/**
 * recuperer les donnée de Book pour qu'elle soit afficher
 */
public class BookViewModel {

    private final String title;
    private final String isbn;
    private final String resume;
    private final Author author;
    private final boolean publier;

    /**
     * constructeur de BookViewModel
     * @param cover
     *      couverture du livre
     * @param publier
     *      indique s'il est publié
     */
    public BookViewModel(Cover cover, boolean publier){
        this.title = cover.getTitle();
        this.isbn = cover.getIsbn();
        this.resume = cover.getResume();
        this.author = cover.getAuthor();
        this.publier = publier;
    }

    public String getTitle(){
        return title;
    }

    public String getIsbn(){
        return isbn;
    }

    public String getResume(){
        return resume;
    }

    public String getAuthor(){
        return author.toString();
    }

    public boolean isPublier(){
        return publier;
    }


}
