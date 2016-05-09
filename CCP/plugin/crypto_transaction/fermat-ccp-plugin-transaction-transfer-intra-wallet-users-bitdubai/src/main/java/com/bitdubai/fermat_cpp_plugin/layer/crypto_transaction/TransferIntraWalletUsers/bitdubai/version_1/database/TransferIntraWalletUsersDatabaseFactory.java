package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.database;

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
 * Created by Joaquin Carrasquero on 17/03/16.
 */
public class TransferIntraWalletUsersDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public TransferIntraWalletUsersDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
    protected Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Outgoing Intra User table.
             */
            table = databaseFactory.newTableFactory(ownerId, TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TABLE_NAME);

            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.TRUE);

            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_HASH_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_ACTOR_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_REFERENCE_TYPE_SENDING_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_REFERENCE_TYPE_RECEIVING_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_PUBLIC_KEY_SENDING_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_PUBLIC_KEY_RECEIVING_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_RUNNING_NETWORK_TYPE, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addIndex(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
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
