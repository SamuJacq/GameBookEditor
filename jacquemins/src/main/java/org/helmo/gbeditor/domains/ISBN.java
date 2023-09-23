package org.helmo.gbeditor.domains;

/**
 * class qui permet d'identifier un Book
 */
public class ISBN {

    private final String numero;

    /**
     * constructeur de ISBN
     * @param isbn
     *      isbn du livre
     */
    public ISBN(final String isbn){
        this.numero = isbn == null || isbn.isBlank() ? "unknown isbn" : isbn.length() == 10 ? isbn : generateVerifCode(isbn);
    }

    public String getNumero(){
        return numero;
    }

    /**
     * algorithme qui va génére le code de vérification de l'isbn
     * et le rajouter à la fin
     * @param isbn
     *      isbn qui a été sélectionner
     * @return l'isbn avec le code de verif
     */
    private String generateVerifCode(final String isbn){
        int result = 0;
        int reste;
        for(int i = 0; i <isbn.length();i++){
            result += (int) isbn.charAt(i) * (10-i);
        }
        reste = result % 11;
        result = (11 - reste);
        return isbn + (result == 11 ? "0" : result == 10 ? "X" : result);
    }

}
