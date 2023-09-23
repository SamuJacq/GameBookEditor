package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.domains.Choice;

/**
 * converti un choix en DTO pour
 * l'envoyé à la BD
 */
public class DTOChoice {

    private final String contain;
    private final int referencePage;

    /**
     * constructeur de DTOChoice
     * @param choice
     *      choix à convertir
     */
    public DTOChoice(Choice choice){
        this.contain = choice.getIntitule();
        this.referencePage = choice.getNumPage();
    }

    public String getContain(){return contain;}
    public int getReferencePage(){return referencePage;}

    /**
     * constructeur de DTOChoice
     * @param contain
     *      contenu donné
     * @param numPage
     *      numéro de la page reçu
     */
    public DTOChoice(String contain, int numPage){
        this.contain = contain;
        this.referencePage = numPage;
    }

    /**
     * créer un choix avec les donnée du DTO
     * @return choix converti
     */
    public Choice newChoice(){
        return new Choice(contain, referencePage);
    }

}
