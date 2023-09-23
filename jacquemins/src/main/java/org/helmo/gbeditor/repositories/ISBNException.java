package org.helmo.gbeditor.repositories;

/**
 * exception qui indique si un ISBN est déjà dans
 * la BD
 */
public class ISBNException extends Exception{

    private final String message;
    private final Exception cause;

    /**
     * constructeur de ISBNException
     * @param message
     *      message du probleme
     * @param cause
     *      message de la cause du probleme
     */
    public ISBNException(final String message, final Exception cause){
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
