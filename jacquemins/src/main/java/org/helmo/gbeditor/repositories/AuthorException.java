package org.helmo.gbeditor.repositories;
/**
 * Exception pour des erreurs lié au auteur
 */
public class AuthorException extends Exception{

    private final String message;
    private final Exception cause;

    /**
     * constructeur de AuthorException
     * @param message
     *      message du probleme
     * @param cause
     *      message de la cause du probleme
     */
    public AuthorException(final String message, final Exception cause){
        this.message = message == null || message.isBlank() ? "pas de message" : message;
        this.cause = cause;
    }

    @Override
    public String getMessage(){
        return message;
    }

    @Override
    public Exception getCause(){
        return cause;
    }

}
