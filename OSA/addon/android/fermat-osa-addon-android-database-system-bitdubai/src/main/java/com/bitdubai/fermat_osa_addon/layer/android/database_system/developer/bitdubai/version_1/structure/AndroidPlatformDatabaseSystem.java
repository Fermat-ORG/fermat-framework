package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.util.Base64;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

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
    private final String path;

    public AndroidPlatformDatabaseSystem(final String path) {

        this.path = path;
    }


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
            database = new AndroidDatabase(path, hasDBName);
            database.openDatabase();
            return database;
        }
        catch (NoSuchAlgorithmException e){
            String message = CantOpenDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Database Name : " + databaseName;
            String possibleReason = "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name";
            throw new CantOpenDatabaseException(message, cause, context, possibleReason);
        }
        catch(Exception e){
            throw new CantOpenDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE,FermatException.wrapException(e),null,"Check the cause");
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
            database = new AndroidDatabase(path, hasDBName);
            database.createDatabase(hasDBName);
            return database;
        }
        catch (NoSuchAlgorithmException e){
            String message = CantCreateDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Database Name : " + databaseName;
            String possibleReason = "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name";
            throw new CantCreateDatabaseException(message, cause, context, possibleReason);
        }
        catch (Exception e){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE,FermatException.wrapException(e),null,"Check the cause");
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
            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database;
            database = new AndroidDatabase(path, hasDBName);
            database.deleteDatabase();

        } catch (NoSuchAlgorithmException e){
            String message = CantOpenDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Database Name : " + databaseName;
            String possibleReason = "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name";
            throw new CantOpenDatabaseException(message, cause, context, possibleReason);
        }
        catch (Exception e){
            throw new CantOpenDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE,FermatException.wrapException(e),null,"Check the cause");
        }
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
