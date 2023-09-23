package org.helmo.gbeditor.domains;

import java.util.ArrayList;
import java.util.List;

/**
 * class qui décrit une page du Book
 */
public class Page{

    private final String contain;
    private final List<Choice> listChoice = new ArrayList<>();

    /**
     * constrcteur de Page
     * @param contain
     *      contenu du Page
     */
    public Page(String contain){
        this.contain = contain == null || contain.isBlank() ? "page empty" : contain;
    }

    public String getContain(){
        return contain;
    }

    public List<Choice> getListChoice(){
        return listChoice;
    }

    /**
     * vérifier s'il y a pas de choice qui référence
     * unepage donc on souhaite la supprimer
     * @param numPage
     *      nunméro de la page qu'on veut supprimer
     * @return true si un choix référence la page
     */
    public boolean haveChoice(int numPage){
        for(var choice : listChoice){
            if(choice.getNumPage() == numPage){
                return true;
            }
        }
        return false;
    }

    /**
     * ajoute un choice dans la list
     * @param choice
     *      choice à ajouter
     */
    public void addChoice(Choice choice){
        if(choice == null){
            return;
        }
        listChoice.add(choice);
    }

    /**
     * supprimme le choix dans la list qui pointe
     * vers le numéro de page donné
     * @param numPage
     *      numéro de la Page supprimmer
     */
    public void removeChoiceByPage(int numPage){
        for(int i = 0; i<listChoice.size();i++){
            if(listChoice.get(i).getNumPage() == numPage){
                removeChoice(i);
            }
        }
    }

    /**
     * supprimme un choice dans la liste
     * @param index
     *      index du choice
     */
    public void removeChoice(int index){
        listChoice.remove(index);
    }
}
