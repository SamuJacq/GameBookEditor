package org.helmo.gbeditor.presenters;

/**
 * interface de la MainView
 */
public interface IMainView {

    /**
     * initialise le presenter de la vue
     * @param p
     *      presenter de la vue
     */
    void setPresenter(Presenters p);

    /**
     * envoie un message en cas d'erreur
     * @param s
     *      message d'erreur
     */
    void alert(String s);

    /**
     * envoie un message pour confirmer une action
     * @param s
     *      message de l'action
     */
    void success(String s);

    /**
     * indique dans quelle vue il faut changer
     * @param newPane
     *      nom de la vue
     */
    void goTo(String newPane);

}
