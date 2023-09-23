package org.helmo.gbeditor.presenters;

/**
 * recuperer les donnée de Page pour qu'elle soit afficher
 */
public class PageViewModel {

    private final String contains;

    /**
     * constructeur de Page ViewModel
     * @param contains
     *      contenu qui sera afficher
     */
    public PageViewModel(final String contains){
        this.contains = contains;
    }

    public String getContains(){
        return contains;
    }

}
