package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidPluginDatabaseSystem  implements PluginDatabaseSystem{

    /**
     * PluginDatabaseSystem Interface member variables.
     */
    private Context Context;


    /**
     * PluginDatabaseSystem Interface implementation.
     */
    
    @Override
    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        try{
            AndroidDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new AndroidDatabase(this.Context, ownerId, hasDBName);
            database.openDatabase(hasDBName);

            return database;
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CantOpenDatabaseException();
        }

    }

    @Override
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException{
        try{
            AndroidDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new AndroidDatabase(this.Context, ownerId, hasDBName);
            database.createDatabase(hasDBName);

            return database;
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CantCreateDatabaseException();
        }

    }


    @Override
    public void setContext(Object context) {
        this.Context = (Context) context;
    }


    /**
     *
     * Hash the file name using the algorithm SHA 256
     */
    private String hashDataBaseName(String databaseName) throws NoSuchAlgorithmException {
        String encryptedString = databaseName;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(databaseName.getBytes());
            byte[] digest = md.digest();
            byte[] encoded = Base64.encode(digest, 1);

            encryptedString = new String(encoded);
        }catch(NoSuchAlgorithmException e){
            throw e;
        }
        return encryptedString.replace("/","");
    }

}
