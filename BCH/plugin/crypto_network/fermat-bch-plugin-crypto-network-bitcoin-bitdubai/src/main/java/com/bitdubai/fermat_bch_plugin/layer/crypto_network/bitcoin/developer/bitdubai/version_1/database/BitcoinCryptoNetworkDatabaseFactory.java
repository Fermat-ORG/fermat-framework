package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 09/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
class BitcoinCryptoNetworkDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public BitcoinCryptoNetworkDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Incoming_Transactions table.
             */
            table = databaseFactory.newTableFactory(ownerId, BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);

            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_HASH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCKCHAIN_NETWORK_TYPE, DatabaseDataType.STRING, 20, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_DEPTH_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_BTC_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_FEE_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_OP_RETURN_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 5, Boolean.FALSE);

            table.addIndex(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create CryptoVaults_Stats table.
             */
            table = databaseFactory.newTableFactory(ownerId, BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_TABLE_NAME);

            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_CRYPTO_VAULT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.FALSE);

            table.addIndex(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create CryptoVaults_detailed_Stats table.
             */
            table = databaseFactory.newTableFactory(ownerId, BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_TABLE_NAME);

            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_CRYPTO_VAULT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_NETWORK_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_ORDER_COLUMN_NAME, DatabaseDataType.INTEGER, 0, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_MONITORED_ADDRESSES_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create EventAgent_Stats table.
             */
            table = databaseFactory.newTableFactory(ownerId, BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_TABLE_NAME);

            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_LAST_EXECUTION_DATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_INCOMING_TRANSACTIONS_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_OUTGOING_TRANSACTIONS_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.FALSE);

            table.addIndex(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }


            /**
             * Create Broadcast table.
             */
            table = databaseFactory.newTableFactory(ownerId, BitcoinCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);

            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_EXECUTION_NUMBER_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_NETWORK, DatabaseDataType.STRING, 20, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_TRANSACTION_ID, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_PEER_COUNT, DatabaseDataType.INTEGER, 10, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_PEER_BROADCAST_IP, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_RETRIES_COUNT, DatabaseDataType.INTEGER, 5, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_STATUS, DatabaseDataType.STRING, 5, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_EXCEPTION, DatabaseDataType.STRING, 300, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_LAST_EXECUTION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 10, Boolean.FALSE);


            table.addIndex(BitcoinCryptoNetworkDatabaseConstants.BRTOADCAST_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }


            /**
             * Create ACTIVENETWORKS table.
             */
            table = databaseFactory.newTableFactory(ownerId, BitcoinCryptoNetworkDatabaseConstants.ACTIVENETWORKS_TABLE_NAME);

            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.ACTIVENETWORKS_NETWORKTYPE, DatabaseDataType.STRING, 20, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.ACTIVENETWORKS_KEYS, DatabaseDataType.INTEGER, 0, Boolean.FALSE);
            table.addColumn(BitcoinCryptoNetworkDatabaseConstants.ACTIVENETWORKS_LAST_UPDATE, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);


            table.addIndex(BitcoinCryptoNetworkDatabaseConstants.ACTIVENETWORKS_NETWORKTYPE);

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
