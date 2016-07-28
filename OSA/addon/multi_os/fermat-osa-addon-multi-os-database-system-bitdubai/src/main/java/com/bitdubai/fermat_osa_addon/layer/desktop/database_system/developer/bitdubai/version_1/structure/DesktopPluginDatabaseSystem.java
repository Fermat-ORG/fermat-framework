package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

//import java.util.Base64;


/**
 * Created by ciencias on 20.01.15 Migrate to desktop by Matias.
 */

/**
 * <p>This class define methods to create and open Databases
 * <p>It requires Plugin Identifier
 * *
 */

public class DesktopPluginDatabaseSystem implements PluginDatabaseSystem {


    /**
     * PluginDatabaseSystem Interface implementation.
     */

    /**
     * <p>This method open a specified database file.
     *
     * @param ownerId      ID plugin
     * @param databaseName database name to use
     * @return Database Object
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    @Override
    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        try {
            DesktopDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(ownerId, hasDBName);
            database.openDatabase(hasDBName);

            return database;
        } catch (NoSuchAlgorithmException e) {
            throw new CantOpenDatabaseException();
        }

    }


    /**
     * <p>This method delete a specified database file.
     *
     * @param ownerId      ID plugin
     * @param databaseName database name to use
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    @Override
    public void deleteDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        try {
            DesktopDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(ownerId, hasDBName);
            database.deleteDatabase(hasDBName);


        } catch (NoSuchAlgorithmException e) {
            throw new CantOpenDatabaseException();
        }

    }


    /**
     * <p> This method create a new database file
     *
     * @param ownerId      ID plugin
     * @param databaseName database name to use
     * @return
     * @throws CantCreateDatabaseException
     */
    @Override
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        try {
            DesktopDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(ownerId, hasDBName);
            database.createDatabase(hasDBName);

            return database;
        } catch (NoSuchAlgorithmException e) {
            throw new CantCreateDatabaseException();
        }

    }


    /**
     * <p> This method set the context object
     *
     * @param context Android Context object
     */
    // This method is only for Android OS
    @Override
    public void setContext(Object context) {
        //this.Context = (Context) context;
    }


    /**
     * <p> This method hash the database file name using the algorithm SHA 256
     *
     * @param databaseName database name to use
     * @return String hashed database file name
     * @throws NoSuchAlgorithmException
     */
    private String hashDataBaseName(String databaseName) throws NoSuchAlgorithmException {
        String encryptedString = databaseName;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(databaseName.getBytes(Charset.forName("UTF-8")));
            byte[] digest = md.digest();
            //byte[] encoded = Base64.getEncoder().encode(digest);

            try {
                //	encryptedString = new String(encoded, "UTF-8");
            } catch (Exception e) {
                throw new NoSuchAlgorithmException(e);
            }


        } catch (NoSuchAlgorithmException e) {
            throw e;
        }
        return encryptedString.replace("/", "");
    }

}
