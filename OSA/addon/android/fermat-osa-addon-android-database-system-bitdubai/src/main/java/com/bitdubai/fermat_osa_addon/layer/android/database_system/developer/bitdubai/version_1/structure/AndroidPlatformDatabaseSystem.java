package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.content.Context;
import android.util.Base64;

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
 * <p/>
 * *
 */

public class AndroidPlatformDatabaseSystem implements PlatformDatabaseSystem {

    /**
     * PlatformDatabaseSystem Interface member variables.
     */
    private final Context context;

    public AndroidPlatformDatabaseSystem(final Context context) {

        this.context = context;
    }

    @Override
    public final Database openDatabase(final String databaseName) throws CantOpenDatabaseException,
            DatabaseNotFoundException {

        try {
            AndroidDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new AndroidDatabase(context.getFilesDir().getPath(), hasDBName);
            database.openDatabase();
            return database;
        } catch (final NoSuchAlgorithmException e) {

            throw new CantOpenDatabaseException(e, new StringBuilder().append("Database Name : ").append(databaseName).toString(), "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name");
        } catch (final Exception e) {

            throw new CantOpenDatabaseException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public final Database createDatabase(final String databaseName) throws CantCreateDatabaseException {

        try {
            AndroidDatabase database;
            String hasDBName = hashDataBaseName(databaseName);
            database = new AndroidDatabase(context.getFilesDir().getPath(), hasDBName);
            database.createDatabase(hasDBName);
            return database;
        } catch (final NoSuchAlgorithmException e) {

            throw new CantCreateDatabaseException(e, new StringBuilder().append("Database Name : ").append(databaseName).toString(), "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name");
        } catch (Exception e) {

            throw new CantCreateDatabaseException(e, null, "Unhandled Exception.");
        }

    }

    @Override
    public final void deleteDatabase(final String databaseName) throws CantOpenDatabaseException,
            DatabaseNotFoundException {
        try {
            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database;
            database = new AndroidDatabase(context.getFilesDir().getPath(), hasDBName);
            database.deleteDatabase();

        } catch (final NoSuchAlgorithmException e) {

            throw new CantOpenDatabaseException(e, new StringBuilder().append("Database Name : ").append(databaseName).toString(), "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name");
        } catch (final Exception e) {

            throw new CantOpenDatabaseException(e, null, "Unhandled Exception.");
        }
    }

    private String hashDataBaseName(String databaseName) throws NoSuchAlgorithmException {
        String encryptedString;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(databaseName.getBytes(Charset.forName("UTF-8")));
            byte[] digest = md.digest();
            byte[] encoded = Base64.encode(digest, 1);

            try {
                encryptedString = new String(encoded, "UTF-8");
            } catch (Exception e) {
                throw new NoSuchAlgorithmException(e);
            }


        } catch (NoSuchAlgorithmException e) {
            throw e;
        }
        encryptedString = encryptedString.replace("+", "");
        return encryptedString.replace("/", "");
    }
}
