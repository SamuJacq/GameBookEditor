package org.helmo.gbeditor.repositories;

/**
 * exception qui indique si la connection a su
 * être créer ou pas
 */
public class ConnectionFailedException extends Exception{

    private final String message;
    private final Exception cause;

    /**
     * constructeur de ConnectionFailedException
     * @param message
     *      message du probleme
     * @param cause
     *      message de la cause du probleme
     */
    public ConnectionFailedException(final String message, final Exception cause){
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
