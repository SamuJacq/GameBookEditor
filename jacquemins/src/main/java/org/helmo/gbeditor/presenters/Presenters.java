package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.domains.*;
import org.helmo.gbeditor.infrastructures.DataStorageFactory;
import org.helmo.gbeditor.repositories.*;

import java.util.List;

/**
 * presenter principale qui fait tous les transactions entre la view
 * et les class domains
 */
public class Presenters {

    private final IMainView view;
    private StorageRepository storage;
    private final Editor editor;
    private final LoginPresenter loginP;
    private final HomePresenter homeP;
    private final EditBookPresenter editBookP;

    /**
     * constructeur du presenter
     * @param mainView
     *      vue principale
     * @param storage
     *      interface pour l'infrastructure
     */
    public Presenters(final IMainView mainView, final DataStorageFactory storage) {
        this.view = mainView;
        this.editor = new Editor();

        try{
            this.storage = storage.newDataStorageSession();
        } catch (ConnectionFailedException e) {
            e.printStackTrace();
        }

        loginP = new LoginPresenter(this, editor);
        homeP = new HomePresenter(this, editor);
        editBookP = new EditBookPresenter(this, editor);

        view.setPresenter(this);
        mainView.goTo("Login");
    }
    public LoginPresenter getLoginP(){
        return loginP;
    }
    public HomePresenter getHomeP(){
        return homeP;
    }
    public EditBookPresenter getEditBookP(){
        return editBookP;
    }


    /**
     * indique si le storage a été créer ou non
     * @return
     */
    public boolean startScene(){
        return storage != null;
    }

    /**
     * connecter l'auteur en l'enregistrant
     * si c'est la premier fois qu'il se connecte
     */
    public void login() throws AuthorException {
        storage.saveAuthor(editor.getAuthor());
        view.goTo("Home");
    }

    /**
     * indique à la MainView quelle view à afficher
     * @param view
     *      nom de la vue à afficher
     */
    public void switchView(String view){
        this.view.goTo(view);
    }

    /**
     * charge les choix d'une page
     * @param indexCurrentPage
     *      index de la page courant
     * @param currentPage
     *      page courant
     */
    public void loadChoices(final int indexCurrentPage, final Page currentPage) throws ChoiceException {
        for(var choice : storage.loadChoice(indexCurrentPage)){
            currentPage.addChoice(choice);
        }
    }

    /**
     * enregistre un nouveau livre ou les modif d'un livre
     * @param book
     *      livre à enregistrer
     * @param isUpdate
     *      indique si c'est une Upadate
     * @param lastIsbn
     *      isbn du livre à avance l'update
     */
    public void saveBook(final Book book, final boolean isUpdate, final String lastIsbn) throws ISBNException, AuthorException {
        if(isUpdate){
            storage.updateBook(book, lastIsbn);
        }else{
            storage.saveBook(book);
        }
    }

    /**
     * recuperer une liste de livre qui sera afficher
     * par la view
     */
    public List<Book> loadBook() throws BookException {
        return storage.load(editor.getAuthor());
    }

    /**
     * recuperer une liste de livre qui sera afficher
     * par la view
     */
    public void loadPage() throws PageException {
        if(editor.pagesNotLoad()) {
            List<Page> listPage = storage.loadPage(editor.getCurrentBook());
            givePages(listPage);
        }
    }

    /**
     * sauvegarde les pages et choix fait durant
     * l'edition du livre current
     */
    public void saveEdit() throws BookException {
        storage.saveEdit(editor.getCurrentBook());
    }

    /**
     * donne toutes les pages au livre courant
     * qui ont été récupére dans la BD
     */
    private void givePages(final List<Page> listPage){
        for(var page : listPage){
            editor.addPage(page.getContain());
        }
    }

    /**
     * publie le livre courant
     */
    public void publierBook() throws BookException {
        editor.publierBook();
        storage.publierBook(editor.getCurrentBook());
        switchView("Home");
    }

}
