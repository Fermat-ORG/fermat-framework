package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;

import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Natalia on 31/03/2015.
 */
public class AndroidPlatformDatabaseSystem implements PlatformDatabaseSystem {

    /**
     * PlatformDatabaseSystem Interface member variables.
     */
    private Context context;


    /**
     * PlatformDatabaseSystem Interface implementation.
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
            e.printStackTrace();
            throw new CantOpenDatabaseException();
        }

    }

    @Override
    public Database createDatabase (String databaseName) throws CantCreateDatabaseException{
        try{
            AndroidDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new AndroidDatabase(this.context, hashDataBaseName(hasDBName));
            database.createDatabase(hashDataBaseName(hasDBName));

            return database;
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new CantCreateDatabaseException();
        }

    }

    @Override
    public void setContext (Object context){
        this.context = (Context)context;
    }

    /**
     *
     * Hash the file name using the algorithm SHA 256
     */
    private String hashDataBaseName(String databaseName) throws NoSuchAlgorithmException {
        String encryptedString = databaseName;
      /*  try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(databaseName.getBytes());
            encryptedString = new String(messageDigest.digest());
        }catch(NoSuchAlgorithmException e){
            throw e;
        }*/
        return encryptedString;
    }
}
