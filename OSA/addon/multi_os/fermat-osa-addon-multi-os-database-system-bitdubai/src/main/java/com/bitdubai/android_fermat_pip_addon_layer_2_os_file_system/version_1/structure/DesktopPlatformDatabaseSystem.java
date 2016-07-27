package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


/**
 * Created by Matias.
 */

/**
 * This class define methods to create and open Databases
 * <p/>
 * *
 */

public class DesktopPlatformDatabaseSystem implements PlatformDatabaseSystem {


    /**
     * PlatformDatabaseSystem Interface implementation.
     */

    /**
     * <p>This method open a specified database file.
     *
     * @param databaseName database name to use
     * @return Database object
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */

    @Override
    public Database openDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        try {
            DesktopDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(hasDBName);
            database.openDatabase(hasDBName);

            return database;
        } catch (NoSuchAlgorithmException e) {
            throw new CantOpenDatabaseException();
        }

    }

    /**
     * <p>This method create a new database file.
     *
     * @param databaseName database name to use
     * @return Database Object
     * @throws CantCreateDatabaseException
     */
    @Override
    public Database createDatabase(String databaseName) throws CantCreateDatabaseException {
        try {
            DesktopDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(hasDBName);
            database.createDatabase(hasDBName);

            return database;
        } catch (NoSuchAlgorithmException e) {
            throw new CantCreateDatabaseException();
        }

    }

    /**
     * <p>This method delete a specified database file.
     *
     * @param databaseName database name to use
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    @Override
    public void deleteDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        try {
            DesktopDatabase database;

            database = new DesktopDatabase(databaseName);
            database.deleteDatabase(databaseName);


        } catch (DatabaseNotFoundException e) {
            throw new CantOpenDatabaseException();
        }

    }


    // This method is only for android OS
    @Override
    public void setContext(Object context) {
        //this.context = (Context)context;
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
            //Base64 base64 = new Base64();
            byte[] encoded = Base64.getEncoder().encode(digest);

            try {
                encryptedString = new String(encoded, "UTF-8");
            } catch (Exception e) {
                throw new NoSuchAlgorithmException(e);
            }
        } catch (NoSuchAlgorithmException e) {
            throw e;
        }
        return encryptedString.replace("/", "");
    }
}
