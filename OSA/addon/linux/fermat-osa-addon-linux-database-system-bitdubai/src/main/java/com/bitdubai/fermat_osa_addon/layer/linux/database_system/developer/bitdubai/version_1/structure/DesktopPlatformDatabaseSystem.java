/*
* @#DesktopPlatformDatabaseSystem.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopPlatformDatabaseSystem</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
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
            // String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(databaseName);
            database.openDatabase(databaseName);

            return database;
        } catch (Exception e) {
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
            //String hasDBName = hashDataBaseName(databaseName);
            database = new DesktopDatabase(databaseName);
            database.createDatabase(databaseName);

            return database;
        } catch (Exception e) {
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

/*
    private String hashDataBaseName(String databaseName) throws NoSuchAlgorithmException {
        String encryptedString;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(databaseName.getBytes(Charset.forName("UTF-8")));
            byte[] digest = md.digest();
            byte[] encoded = android.util.Base64.encode(digest, 1);

            try {
                encryptedString = new String(encoded, "UTF-8");
            } catch (Exception e) {
                throw new NoSuchAlgorithmException (e);
            }


        }catch(NoSuchAlgorithmException e){
            throw e;
        }
        encryptedString = encryptedString.replace("+","");
        return encryptedString.replace("/","");
    }
*/


    /**
     *
     * Hash the file name using the algorithm SHA 256
     */

/*    private String hashFileName(String fileName) throws NoSuchAlgorithmException {
        String encryptedString = fileName;
        try{

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(fileName.getBytes(Charset.forName("UTF-8")));
            byte[] digest = md.digest();
            Base64 base64 = new Base64();
            byte[] encoded = base64.encode(digest);

            try {
                encryptedString = new String(encoded, "UTF-8");
            } catch (Exception e){
                throw new NoSuchAlgorithmException (e);
            }

        }catch(NoSuchAlgorithmException e){
            throw e;
        }
        return encryptedString.replace("/","");
    }
*/


}
