package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.domains.Editor;
import org.helmo.gbeditor.repositories.AuthorException;

/**
 * presenter pour la view de connection
 */
public class LoginPresenter{

    private final Presenters presenters;
    private LoginInterface view;
    private final Editor editor;

    /**
     * constrcteur de LoginPresenter
     * @param presenters
     *      presenters principale
     * @param editor
     *      editeur des classe domaines
     */
    public LoginPresenter(final Presenters presenters, final Editor editor){
        this.presenters = presenters;
        this.editor = editor;
    }

    public void setView(LoginInterface view){
        this.view = view;
    }

    /**
     * verifie si le login entrer est correcte et
     * connecte l'auteur
     * @param name
     *      nom entré
     * @param firstName
     *      prénom entré
     */
    public void checkLogin(final String name, final String firstName){
        if((name == null || name.isBlank()) || (firstName == null || firstName.isBlank())){
            view.alert("veuillez remplir les champs");
        }else{
            try{
                editor.setAuthor(name,firstName);
                presenters.login();
            } catch (AuthorException e) {
                view.alert(e.getMessage());
            }
        }
    }

}
