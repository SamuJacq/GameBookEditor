package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.domains.Author;

/**
 * class qui converti les auteurs en
 * DTO pour les envoyés dans la BD
 */
public class DTOAuthor {

    private final String name;

    /**
     * constructeur de DTOAuthor
     * @param author
     *      author à convertir
     */
    public DTOAuthor(final Author author){
        name = author.toString();
    }

    public String getName(){return name;}

    /**
     * converti un DTO en author pour le pour
     * le programme
     * @return author convertit
     */
    public Author convertDTOAuthor(){
        String[] tab = this.name.split(" ");
        return new Author(tab[0], tab[1]);
    }

}
