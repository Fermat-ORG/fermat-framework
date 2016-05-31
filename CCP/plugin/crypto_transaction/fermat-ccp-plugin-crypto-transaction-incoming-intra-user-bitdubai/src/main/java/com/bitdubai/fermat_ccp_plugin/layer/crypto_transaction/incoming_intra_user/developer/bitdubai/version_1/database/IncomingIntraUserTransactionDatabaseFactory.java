package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.database.Incoming Intra UserTransactionDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IncomingIntraUserTransactionDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public IncomingIntraUserTransactionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Incoming Intra User Registry table.
             */
            table = databaseFactory.newTableFactory(ownerId, IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME);

            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_HASH_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ADDRESS_TO_COLUMN_NAME, DatabaseDataType.STRING, 34, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.MONEY, 100, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ADDRESS_FROM_COLUMN_NAME, DatabaseDataType.STRING, 34, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ACTION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_PROTOCOL_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_NETWORK_TYPE, DatabaseDataType.STRING, 10, Boolean.FALSE);


            table.addIndex(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Incoming Intra User Events Recorded table.
             */
            table = databaseFactory.newTableFactory(ownerId, IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TABLE_NAME);

            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            table.addIndex(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Incoming Intra User Crypto Metadata table.
             */
            table = databaseFactory.newTableFactory(ownerId, IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TABLE_NAME);

            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_REQUEST_FLAG_COLUMN_NAME, DatabaseDataType.STRING, 1, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_REQUEST_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_SENDER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.TRUE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ACTION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PROTOCOL_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TRANSACTION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);


            table.addIndex(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_FIRST_KEY_COLUMN);

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

