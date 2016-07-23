package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.content.Context;
import android.util.Base64;

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
 */

/**
 * <p>This class define methods to create and open Databases
 * <p>It requires Plugin Identifier
 * *
 */

public class AndroidPluginDatabaseSystem implements PluginDatabaseSystem {

    /**
     * PluginDatabaseSystem Interface member variables.
     */
    private final Context context;

    public AndroidPluginDatabaseSystem(final Context context) {
        this.context = context;
    }

    /**
     * PluginDatabaseSystem Interface implementation.
     */

    @Override
    public final Database openDatabase(final UUID ownerId,
                                       final String databaseName) throws CantOpenDatabaseException,
            DatabaseNotFoundException {

        try {

            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database = new AndroidDatabase(context.getFilesDir().getPath(), ownerId, hasDBName);
            database.openDatabase();
            return database;

        } catch (final NoSuchAlgorithmException e) {

            throw new CantOpenDatabaseException(e, new StringBuilder().append("Database Name : ").append(databaseName).toString(), "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name");
        } catch (final DatabaseNotFoundException exception) {

            throw exception;
        } catch (final Exception e) {

            throw new CantOpenDatabaseException(e, null, "Unhandled Exception");
        }

    }

    @Override
    public final void deleteDatabase(final UUID ownerId,
                                     final String databaseName) throws CantOpenDatabaseException,
            DatabaseNotFoundException {

        try {

            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database = new AndroidDatabase(context.getFilesDir().getPath(), ownerId, hasDBName);
            database.deleteDatabase();

        } catch (final NoSuchAlgorithmException e) {

            throw new CantOpenDatabaseException(e, new StringBuilder().append("Database Name : ").append(databaseName).toString(), "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name");
        } catch (final DatabaseNotFoundException exception) {

            throw exception;
        } catch (final Exception e) {

            throw new CantOpenDatabaseException(e, null, "Unhandled Exception");
        }

    }

    @Override
    public final Database createDatabase(final UUID ownerId,
                                         final String databaseName) throws CantCreateDatabaseException {

        try {
            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database = new AndroidDatabase(context.getFilesDir().getPath(), ownerId, hasDBName);
            database.createDatabase(hasDBName);
            return database;

        } catch (final NoSuchAlgorithmException e) {

            throw new CantCreateDatabaseException(e, new StringBuilder().append("Database Name : ").append(databaseName).toString(), "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name");
        } catch (final Exception e) {

            throw new CantCreateDatabaseException(e, new StringBuilder().append("Context= ownerId: ").append(ownerId).append(", databaseName: ").append(databaseName).toString(), "Unhandled Exception");
        }

    }

    private String hashDataBaseName(final String databaseName) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(databaseName.getBytes(Charset.forName("UTF-8")));
        byte[] digest = md.digest();
        byte[] encoded = Base64.encode(digest, 1);

        try {

            String encryptedString = new String(encoded, "UTF-8");

            encryptedString = encryptedString.replace("/", "");

            return encryptedString.replace("\n", "");

        } catch (final Exception e) {

            throw new NoSuchAlgorithmException(e);
        }
    }
}
