package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


/**
 * Created by ciencias on 20.01.15.
 *
 */

/**
 * <p>This class define methods to create and open Databases
 * <p>It requires Plugin Identifier
 * *
 */

public class AndroidPluginDatabaseSystem  implements PluginDatabaseSystem{

    /**
     * PluginDatabaseSystem Interface member variables.
     */
    private String path;

    public AndroidPluginDatabaseSystem(String path) {
        this.path = path;
    }

    /**
     * PluginDatabaseSystem Interface implementation.
     */

    /**
     * <p>This method open a specified database file.
     *
     * @param ownerId ID plugin
     * @param databaseName database name to use
     * @return Database Object
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    @Override
    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        try{
            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database = new AndroidDatabase(path, ownerId, hasDBName);
            database.openDatabase();
            return database;
        } catch (NoSuchAlgorithmException e) {
            String message = CantOpenDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Database Name : " + databaseName;
            String possibleReason = "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name";
            throw new CantOpenDatabaseException(message, cause, context, possibleReason);
        } catch (DatabaseNotFoundException exception){
          throw exception;
        } catch (Exception e){
            throw new CantOpenDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE,FermatException.wrapException(e),null,"Check the cause");
        }

    }


    /**
     * <p>This method delete a specified database file.
     *
     * @param ownerId ID plugin
     * @param databaseName database name to use
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    @Override
    public void deleteDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        try{
            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database = new AndroidDatabase(path, ownerId, hasDBName);
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
     *<p> This method create a new database file
     *
     * @param ownerId ID plugin
     * @param databaseName database name to use
     * @return
     * @throws CantCreateDatabaseException
     */
    @Override
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        try{
            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database = new AndroidDatabase(path, ownerId, hasDBName);
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
     * <p> This method hash the database file name using the algorithm SHA 256
     *
     * @param databaseName database name to use
     * @return String hashed database file name
     * @throws NoSuchAlgorithmException
     */
    private String hashDataBaseName(String databaseName) throws NoSuchAlgorithmException {
        String encryptedString;
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
        encryptedString = encryptedString.replace("/","");
        return encryptedString.replace("\n","");
    }

}
