package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Natalia on 31/03/2015.
 */

/**
 * This class define methods to create and open Databases
 *
 * *
 */

public class AndroidPlatformDatabaseSystem implements PlatformDatabaseSystem {

    /**
     * PlatformDatabaseSystem Interface member variables.
     */
    private Context context;


    /**
     * PlatformDatabaseSystem Interface implementation.
     */

    /**
     * <p>This method open a specified database file.
     *
     * @param databaseName database name to use
     * @return  Database object
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */

    @Override
    public Database openDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException{
        try{
            AndroidDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new AndroidDatabase(this.context, hasDBName);
            database.openDatabase(hasDBName);

            return database;
        }
        catch (NoSuchAlgorithmException e){
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
    public Database createDatabase (String databaseName) throws CantCreateDatabaseException{
        try{
            AndroidDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new AndroidDatabase(this.context, hasDBName);
            database.createDatabase(hasDBName);

            return database;
        }
        catch (NoSuchAlgorithmException e){
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
        try{
            AndroidDatabase database;

            database = new AndroidDatabase(this.context, databaseName);
            database.deleteDatabase(databaseName);


        }
        catch (DatabaseNotFoundException e)
        {
            throw new CantOpenDatabaseException();
        }

    }

    /**
     *<p> This method set the context object
     *
     * @param context Android Context object
     */
    @Override
    public void setContext (Object context){
        this.context = (Context)context;
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
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(databaseName.getBytes(Charset.forName("UTF-8")));
            byte[] digest = md.digest();
            byte[] encoded = Base64.encode(digest, 1);

            try {
            	encryptedString = new String(encoded, "UTF-8");	
            } catch (Exception e) {
            	throw new NoSuchAlgorithmException (e);
            }
            
            
        }catch(NoSuchAlgorithmException e){
            throw e;
        }
        return encryptedString.replace("/","");
    }
}
