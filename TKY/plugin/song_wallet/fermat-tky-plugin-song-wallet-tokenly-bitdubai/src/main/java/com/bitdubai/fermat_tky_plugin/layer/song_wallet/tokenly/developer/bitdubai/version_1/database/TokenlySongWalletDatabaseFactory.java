package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/03/16.
 */
public class TokenlySongWalletDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public TokenlySongWalletDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(
                    CantCreateDatabaseException.DEFAULT_MESSAGE,
                    cantCreateDatabaseException,
                    "",
                    "Exception not handled by the plugin, There is a problem and I cannot create " +
                            "the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Song table.
             */
            table = databaseFactory.newTableFactory(ownerId, TokenlySongWalletDatabaseConstants.SONG_TABLE_NAME);

            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_SONG_ID_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.TRUE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_TOKENLY_ID_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_TOKENS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_PERFORMERS_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_COMPOSERS_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.TRUE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_RELEASE_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_LYRICS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_CREDITS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_COPYRIGHT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_OWNERSHIP_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_USAGE_RIGHTS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_USAGE_PROHIBITIONS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_DEVICE_PATH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_SONG_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SONG_TOKENLY_USERNAME_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addIndex(TokenlySongWalletDatabaseConstants.SONG_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        cantCreateTableException,
                        "", "Exception not handled by the plugin, There is a problem and I cannot " +
                        "create the table.");
            }

            /**
             * Create Synchronize table.
             */
            table = databaseFactory.newTableFactory(ownerId, TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TABLE_NAME);

            table.addColumn(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_SYNC_ID_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.TRUE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_DEVICE_SONGS_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TOKENLY_SONGS_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TOKENLY_USERNAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TIMESTAMP, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            table.addIndex(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        cantCreateTableException,
                        "", "Exception not handled by the plugin, There is a problem and I cannot " +
                        "create the table.");
            }


        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(
                    CantCreateDatabaseException.DEFAULT_MESSAGE,
                    invalidOwnerId,
                    "",
                    "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}