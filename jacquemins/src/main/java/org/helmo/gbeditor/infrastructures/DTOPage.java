package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.domains.Page;

import java.util.List;

/**
 * class qui converti un chapter en DTOchapter
 * pour l'enregistrer dans la BD
 */
public class DTOPage {

    private final String contain;
    private List<DTOChoice> listDto;

    /**
     * constructeur de DTOChapter
     * @param page
     *      chapitre où le contenu sera récupérer
     */
    public DTOPage(final Page page){
        contain = page.getContain();
        listDto = Mapping.writeChoice(page.getListChoice());
    }

    public String getContain(){return contain;}
    public List<DTOChoice> getListDto(){return listDto;}

    /**
     * constructeur de DTOChapter
     * @param contain
     *      contenu de la page
     */
    public DTOPage(String contain){
        this.contain = contain;
    }

    /**
     * constructeur de DTOChapter
     * @param contain
     *      contenu de la page
     */

    /**
     * créer un page avec le contenu du DTO
     * @return Page converti
     */
    public Page createPage(){
        return new Page(this.contain);
    }

}
