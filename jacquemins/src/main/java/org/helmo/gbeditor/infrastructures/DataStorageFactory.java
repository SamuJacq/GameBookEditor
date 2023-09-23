package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.repositories.ConnectionFailedException;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * class factory qui va créer une session pour pouvoir
 * se connecter à la BD
 */
public class DataStorageFactory{

    private final String nameDB;
    private final String username;
    private final String password;

    /**
     * constructeur de DataStoragefactory
     * @param driverName
     *      nom du driver
     * @param nameDB
     *      nom de BD
     * @param username
     *      nom de l'utilisateur
     * @param password
     *      mot passe de l'utilisateur
     */
    public DataStorageFactory(final String driverName, final String nameDB, final String username, final String password) {
        try{
            getClass().forName(driverName);
            this.nameDB = nameDB;
            this.username = username;
            this.password = password;
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException("initialisation de la Factory raté\n" + driverName);
        }

    }

    /**
     * creer un factory avec les données de la BD
     * @return factory
     */
    public static DataStorageFactory createFactory(){
        return new DataStorageFactory(
                "driver",
                "name BD",
                "Username",
                "PSW"
        );
    }

    /**
     * créer une nouvelle session pour ce connecter à la BD
     * @return une seesion de connection
     */
    public DataStorage newDataStorageSession() throws ConnectionFailedException {
        try{
            return new DataStorage(DriverManager.getConnection(nameDB, username, password));
        } catch (SQLException e) {
            throw new ConnectionFailedException("Connection a échoué", e);
        }
    }

}
