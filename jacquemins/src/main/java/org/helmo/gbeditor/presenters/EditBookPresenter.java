package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.domains.*;
import org.helmo.gbeditor.repositories.*;

import java.util.ArrayList;
import java.util.List;

/**
 * presenter pour la view de création d'un cahpitre
 */
public class EditBookPresenter {

    private final Presenters presenters;
    private EditBookInterface view;
    private final Editor editor;
    private Page currentPage;
    private int numCurrentPage;

    /**
     * constructeur de EditBookPresenter
     * @param presenters
     *      presenters principale
     * @param editor
     *      editeur des classes domains
     */
    public EditBookPresenter(final Presenters presenters, final Editor editor){
        this.presenters = presenters;
        this.editor = editor;
    }

    public void setView(EditBookInterface view){
        this.view = view;
    }

    /**
     * recupere l'auteur connecter
     * @return auteur connecter
     */
    public String getAuthor(){return editor.getAuthor().toString();}

    /**
     * initialise la page courant pour modifier
     * ses choix
     * @param index
     *      index de la page
     */
    public void setCurrentPage(int index){
        this.currentPage = editor.getOnePage(index);
        this.numCurrentPage = index+1;
        if(currentPage.getListChoice().isEmpty()){
            loadChoices();
        }
    }

    /**
     * modifie les données du livre
     * @param title
     *      nouveau titre
     * @param isbn
     *      nouveau isbn
     * @param resume
     *      nouveau resume
     * @param lastIsbn
     *      ancien isbn du livre pour pourvoir
     *      modifier le livre
     */
    public void updateBook(final String title, final String isbn, final String resume, final String lastIsbn){
        Book book = editor.createBook(title, isbn, resume);
        if(book == null){
            view.alert("remplir tous les champs");
        }else{
            try{
                presenters.saveBook(book, true, lastIsbn);
                view.success("modification enregistrer");
            }catch(ISBNException e){
                view.alert("isbn déjà utilisé");
            } catch (AuthorException e) {
                view.alert(e.getMessage());
            }

        }

    }

    /**
     * vérifie si les données pour ajouter une page
     * sont correct
     * @param contain
     *      contenu de la page
     */
    public void checkPage(final String contain){
        if(contain == null || contain.isBlank()){
            view.alert("veuillez remplir le formulaire de la page");
        }else{
            editor.addPage(contain);
            view.success("nouvelle page ajouté");
        }
    }

    /**
     * retourne le livre courant sous forme de model view
     * @return modele du livre courant
     */
    public BookViewModel getBook(){
        return new BookViewModel(editor.getCurrentBook().getCover(), editor.getCurrentBook().isPublier());
    }

    /**
     * supprimme une page de la liste du livre courant
     * @param index
     *      index de la page
     */
    public void removePage(int index){
        if(index == -1){
            view.alert("veuillez sélectionner une page");
        }else{
            editor.removePage(index);
            view.success("page supprimée");
        }
    }
    /**
     * recupere les pages du livre courant et
     * les transforme en Model View
     * @return list de PageViewModel
     */
    public List<PageViewModel> getPages() {
        try {
            presenters.loadPage();
            return editor.getPages();
        } catch (PageException e) {
            view.alert(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * vérifier s'il y a pas de choice qui référence
     * une page donc on souhaite la supprimer
     * @param numPage
     *      index de la page qu'on souhaite supprimer
     * @return true si un choix référence la page
     */
    public boolean haveChoice(final int numPage){
        return editor.pageIsReferenced(numPage);
    }

    private void loadChoices(){
        try {
            presenters.loadChoices(numCurrentPage, currentPage);
        } catch (ChoiceException e) {
            view.alert(e.getMessage());
        }
    }

    /**
     * recupere les choix de la page qui seront afficher
     * via des model view
     * @return list de model view des choix
     */
    public List<ChoiceViewModel> getChoices(){
        List<ChoiceViewModel> list = new ArrayList<>();
        for(var choice : currentPage.getListChoice()){
            list.add(new ChoiceViewModel(choice));
        }
        return list;
    }

    /**
     * vérifie on peut changer la page de place
     * si oui elle change de place la page
     * @param choice
     * @param indexPage
     */
    public void movePage(boolean choice, int indexPage){
        if(choice && indexPage == 0){
            view.alert("vous ne pouvez pas monter cette page");
        }else if(!choice && indexPage == editor.getCurrentBook().getPapers().size()-1){
            view.alert("vous ne pouvez pas descencdre cette page");
        }else{
            editor.movePage(choice, indexPage);

        }
    }

    /**
     * ajoute un choix dans la liste
     * de la page courant
     * @param numPage
     *      numéro de la page associer
     * @param contenu
     *      contenu du choix
     */
    public void addChoice(int numPage, String contenu){
        if(currentPage.equals(editor.getOnePage(numPage-1))){
            view.alert("vous ne pouvez pas mettre ce choix");
        }else if(contenu == null || contenu.isEmpty()){
            view.alert("champ titre du choix vide");
        }else{
            if(editor.numPageCorrect(numPage)){
                currentPage.addChoice(new Choice(contenu, numPage));
                view.success("choix ajouté");
            }else{
                view.alert("numéro de page incorrect");
            }
        }
    }

    /**
     * supprimme un choix de la liste de la page courant
     * @param indexChoice
     *      index du choix à supprimmer
     */
    public void removeChoice(final int indexChoice){
        if(indexChoice == -1){
            view.alert("veuillez sélectionner un choix");
        }else{
            currentPage.removeChoice(indexChoice);
            view.success("choix supprimé");
        }
    }

    /**
     * sauvegarde les pages et choix fait durant
     * l'edition du livre current
     */
    public void saveEdit(){
        try{
            presenters.saveEdit();
            view.success("pages et choix enregistré");
        }catch(BookException e){
            view.alert(e.getMessage());
        }
    }

    /**
     * changer de view vers Home
     */
    public void switchHome(){
        presenters.switchView("Home");
    }

    /**
     * vérifie si le livre courant à au moins une page
     * si oui il le publie
     */
    public void publierBook() {
        if(getPages().isEmpty()){
            view.alert("veuillez insérer une page avant de publier");
        }else{
            try {
                presenters.publierBook();
                view.success("livre publié");
            } catch (BookException e) {
                view.alert(e.getMessage());
            }
        }
    }

}
