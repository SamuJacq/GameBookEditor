package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.domains.Choice;
/**
 * recuperer les donnée de Choice pour qu'elle soit afficher
 */
public class ChoiceViewModel {

    private final String summary;

    /**
     * constructeur de Page ViewModel
     * @param choice
     *      choix qui sera afficher
     */
    public ChoiceViewModel(final Choice choice){
        summary = String.format("page %d %s", choice.getNumPage(), choice.getIntitule());
    }

    public String getSummary(){
        return summary;
    }

}
