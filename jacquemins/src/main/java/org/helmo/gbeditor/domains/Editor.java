package org.helmo.gbeditor.domains;

import org.helmo.gbeditor.presenters.PageViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * class qui communique et reçoit les informations
 * par le presenter
 */
public class Editor{

    private Author author;
    private Book currentBook;
    /**
     * constructeur d'editor
     */

    public Author getAuthor(){
        return author;
    }

    public Book getCurrentBook(){
        return currentBook;
    }

    /**
     * initialise l'auteur qui se connecte
     * @param name
     *      nom de l'auteur
     * @param firstname
     *      prenom de l'auteur
     */
    public void setAuthor(String name, String firstname){
        this.author = new Author(name, firstname);
    }

    public void setCurrentBook(final Book book){
        currentBook = book;
    }

    /**
     * retourne la page selectionne
     * @param index
     *      index de la page
     * @return la page selectionne
     */
    public Page getOnePage(final int index){
        return currentBook.getOnePage(index);
    }

    /**
     * vérifie si les données acquise pour créer un livre
     * sont correcte
     * @param title
     *      titre sélectionné
     * @param isbn
     *      isbn sélectionné
     * @param resume
     *      resume sélectionne
     * @return un message s'il y a un parametre mal donné
     */
    public String messageError(String title, String isbn, String resume){
        if((title != null && title.isBlank()) || title.length() > 150){
            return "le titre vide ou trop long";
        }
        if(!Pattern.matches("^2200017\\d{2}$", isbn)){
            return "isbn incorrect ou vide";
        }
        if((resume != null && resume.isBlank()) || resume.length() > 500) {
            return "resume vide ou trop long";
        }
        return "";
    }

    /**
     * creer un Book avec les donnée acquise
     * @param title
     *      titre sélectionné
     * @param isbn
     *      isbn sélectionné
     * @param resume
     *      resume sélectionne
     * @return le Book créer
     */
    public Book createBook(String title, String isbn, String resume){
        return new Book(new Cover(title, isbn, resume, author), false);
    }

    /**
     * créer une liste de PageViewModel avec les
     * pages ud livre curant
     * @return list de PageViewModel
     */
    public List<PageViewModel> getPages(){
        List<PageViewModel> pages = new ArrayList<>();
        for(var page : currentBook.getPapers()){
            pages.add(new PageViewModel(page.getContain()));
        }
        return pages;
    }

    /**
     * verifie si le livre courant n'a pas de page
     * @return true si le livre n'a pas de page
     */
    public boolean pagesNotLoad(){
        return currentBook.getPapers().isEmpty();
    }

    /**
     * ajoute un chapitre au Book donné
     * @param contain
     *      contenu du chapitre
     */
    public void addPage(String contain){
        currentBook.addPage(new Page(contain));
    }

    /**
     * bouge la page en fonction du choix
     * ne bouge rien si l'index est négatif
     * @param choice
     *      choix si font monter ou descendre la page
     * @param index
     *      index de la page
     */
    public void movePage(final boolean choice, final int index){
        if(index < 0){
            return;
        }
        currentBook.movePage(choice, index);
    }

    /**
     * supprime la page selectionné
     * ne fait rien si l'index est négative
     * @param index
     *      index de la page
     */
    public void removePage(int index){
        if(index < 0){
            return;
        }
        currentBook.removePage(index);
    }

    /**
     * vérifie si le numéro de la page est présente dans la list
     * @param numPage
     *      numéro de la sélectionner
     * @return true si c'est ni trop petit ni négative
     */
    public boolean numPageCorrect(int numPage){
        return currentBook.getPapers().size() < numPage || currentBook.getPapers().size() >= numPage;
    }

    /**
     * verifie si le numero de la page n'est pas référence
     * par un choix ou plus
     * @param numPage
     *      numéro de la page
     * @return true si le page est reference
     */
    public boolean pageIsReferenced(final int numPage){
        return currentBook.haveChoice(numPage+1);
    }


    /**
     * indique au livre courant qu'il est publié
     */
    public void publierBook(){
        currentBook.setPublier(true);
    }

}
