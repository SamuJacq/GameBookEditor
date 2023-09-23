package org.helmo.gbeditor.domains;

import java.util.LinkedList;
import java.util.List;

/**
 * class qui va construire un livre avec les données
 * acquisent par l'utilisateur
 */
public class Book{

    private final Cover cover;
    private boolean publier;
    private final List<Page> papers = new LinkedList<>();

    /**
     * constructeur de Book
     * @param cover
     *      couverture du livre
     * @param publier
     *      indique s'il est publier
     */
    public Book(final Cover cover, final boolean publier){
        this.cover = cover;
        this.publier = publier;
    }

    public Cover getCover(){return cover;}

    public boolean isPublier(){
        return publier;
    }

    public void setPublier(boolean publier){
        this.publier = publier;
    }

    public List<Page> getPapers(){return papers;}

    /**
     * recupere une Page de la liste
     * @param index
     *      index de la page
     * @return le page demandé
     */
    public Page getOnePage(final int index){
        return papers.get(index);
    }

    /**
     * ajoute un chapitre (page) au livre
     * @param paper
     *      chapitre (page) à ajouter
     */
    public void addPage(final Page paper){
        if(paper == null){
            return;
        }
        papers.add(paper);
    }

    /**
     * supprimmer une page de la liste
     * @param index
     *      index de la page
     */
    public void removePage(final int index){
        for(var page : papers){
            page.removeChoiceByPage(index+1);
        }
        papers.remove(index);
    }

    /**
     * changer de position un chapitre (page)
     * true = monter la page; false = descendre la page
     * @param index
     *      nouvelle emplacement
     */
    public void movePage(final boolean choice, int index){
        Page pageToMoveSelect = papers.get(index);
        Page pageInit;
        if(choice){
            pageInit = papers.get(index-1);
            papers.set(index-1, pageToMoveSelect);
        }else{
            pageInit = papers.get(index+1);
            papers.set(index+1, pageToMoveSelect);
        }
        papers.set(index, pageInit);
    }

    /**
     * vérifier s'il y a pas de choice qui référence
     * une page donc on souhaite la supprimer
     * @param numPage
     *      numéro de la page qu'on veut supprimer
     * @return true si un choix référence la page
     */
    public boolean haveChoice(int numPage){
        for(var page : papers){
            if(page.haveChoice(numPage)){
                return true;
            }
        }
        return false;
    }

}
