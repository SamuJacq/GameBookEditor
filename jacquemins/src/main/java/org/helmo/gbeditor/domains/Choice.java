package org.helmo.gbeditor.domains;

/**
 * class qui indique sur qu'elle page
 * elle pointera si on l'a choisit
 */
public class Choice {

    private final String intitule;
    private final int numPage;

    /**
     * constructeur de Choice
     * @param intitule
     *      titre du choix
     * @param numPage
     *      numero de la page pointé
     */
    public Choice(final String intitule, int numPage){
        this.intitule = intitule == null || intitule.isBlank() ? "unknown intitule" : intitule;
        this.numPage = numPage;
    }

    public String getIntitule(){
        return this.intitule;
    }

    public int getNumPage(){
        return this.numPage;
    }



}
