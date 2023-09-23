package org.helmo.gbeditor.repositories;

import org.helmo.gbeditor.domains.*;

import java.util.List;

/**
 * cette interface n'est plus utiliser
 */

public interface StorageRepository {

    /**
     * enregistre un livre dans la BD
     * @param book
     *      Book à enregistrer
     */
    public void saveBook(Book book) throws ISBNException, AuthorException;


    /**
     * modifie un livre dans la bd
     * @param book
     *      nouvelle donnée du livre
     * @param lastISBN
     *      ancien isbn du livre à modifier
     */
    public void updateBook(Book book, String lastISBN) throws ISBNException;

    /**
     * enregistre un auteur dans la BB
     * @param author
     *      auteur à enregistrer
     */
    public void saveAuthor(Author author) throws AuthorException;

    /**
     * sauvegarde les pages et choix du livre
     * @param book
     *      book où les modif ont été fait
     */
    public void saveEdit(Book book) throws BookException;

    /**
     * recuperer les livres d'un auteur pour
     * les changer dans la vue
     * @param author
     *      auteur connecter
     * @return liste des livres recuperer
     */
    public List<Book> load(Author author) throws BookException;

    /**
     * charge les choix de la page selectionner
     * @param numPageCurrent
     *      numéro de la page
     * @return
     */
    public List<Choice> loadChoice(int numPageCurrent) throws ChoiceException;

    /**
     * charge les pages du livre courant
     * @param book
     *      livre courant
     * @return pages du livre
     */
    public List<Page> loadPage(Book book) throws PageException;

    /**
     * publie un livre
     * @param book
     *      livre à publier
     */
    public void publierBook(Book book) throws BookException;

}
