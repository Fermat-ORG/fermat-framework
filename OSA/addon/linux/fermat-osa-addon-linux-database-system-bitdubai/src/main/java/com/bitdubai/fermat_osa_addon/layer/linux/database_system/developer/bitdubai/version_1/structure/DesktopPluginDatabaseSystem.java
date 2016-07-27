/*
* @#DesktopPluginDatabaseSystem.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopPluginDatabaseSystem</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
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
            //String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(ownerId, databaseName);
            database.openDatabase(databaseName);

            return database;
        } catch (final DatabaseNotFoundException exception) {

            throw exception;
        } catch (Exception e) {
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
            //String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(ownerId, databaseName);
            database.deleteDatabase(databaseName);


        } catch (Exception e) {
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
            // String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(ownerId, databaseName);
            database.createDatabase(databaseName);

            return database;
        } catch (Exception e) {
            throw new CantCreateDatabaseException();
        }

    }


    /**
     *<p> This method set the context object
     *
     * @param context Android Context object
     */


    /**
     * <p> This method hash the database file name using the algorithm SHA 256
     *
     * @param databaseName database name to use
     * @return String hashed database file name
     * @throws NoSuchAlgorithmException
     */
 /*   private String hashDataBaseName(String databaseName) throws NoSuchAlgorithmException {
        String encryptedString = databaseName;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(databaseName.getBytes(Charset.forName("UTF-8")));
            byte[] digest = md.digest();
            Base64 base64 = new Base64();
            byte[] encoded = base64.encode(digest);

            try {
                encryptedString = new String(encoded, "UTF-8");
            } catch (Exception e) {
                throw new NoSuchAlgorithmException (e);
            }


        }catch(NoSuchAlgorithmException e){
            throw e;
        }
        return encryptedString.replace("/","");
    }*/
}
