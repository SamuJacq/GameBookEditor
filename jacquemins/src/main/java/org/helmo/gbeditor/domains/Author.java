package org.helmo.gbeditor.domains;

/**
 * class qui créer un auteur pour l'identifier dans l'appli
 */
public class Author {

    private final String name;
    private final String firstname;

    /**
     * constructeur qui initialise les attribute
     * @param name
     *         nom de l'auteur
     * @param firstname
     *          prenom de l'auteur
     */
    public Author(final String name, final String firstname){
        this.name = name == null || name.isBlank() ? "unknown" : name;
        this.firstname = firstname == null || firstname.isBlank() ? "author" : firstname;
    }

    public String getName(){
        return name;
    }

    public String getFirstname(){
        return firstname;
    }

    @Override
    public String toString(){
        return String.format("%s %s", name, firstname);
    }

}
