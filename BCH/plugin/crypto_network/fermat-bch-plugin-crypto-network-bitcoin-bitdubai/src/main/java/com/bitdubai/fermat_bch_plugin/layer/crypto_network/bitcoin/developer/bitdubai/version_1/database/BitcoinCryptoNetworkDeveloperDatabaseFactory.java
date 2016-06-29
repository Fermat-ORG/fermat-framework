package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinCryptoNetworkDatabaseException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 09/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class BitcoinCryptoNetworkDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public BitcoinCryptoNetworkDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeBitcoinCryptoNetworkDatabaseException
     */
    public void initializeDatabase() throws CantInitializeBitcoinCryptoNetworkDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeBitcoinCryptoNetworkDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            BitcoinCryptoNetworkDatabaseFactory bitcoinCryptoNetworkDatabaseFactory = new BitcoinCryptoNetworkDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = bitcoinCryptoNetworkDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeBitcoinCryptoNetworkDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Bitcoin", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Incoming_Transactions columns.
         */
        List<String> transactionsColumns = new ArrayList<String>();

        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_HASH_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCKCHAIN_NETWORK_TYPE);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_DEPTH_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_BTC_AMOUNT_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_FEE_AMOUNT_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_AMOUNT_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_OP_RETURN_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_LAST_UPDATE_COLUMN_NAME);
        transactionsColumns.add(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME);
        /**
         * Table Transactions addition.
         */
        DeveloperDatabaseTable TransactionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME, transactionsColumns);
        tables.add(TransactionsTable);


        /**
         * Table CryptoVaults_Stats columns.
         */
        List<String> cryptoVaults_StatsColumns = new ArrayList<String>();

        cryptoVaults_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_CRYPTO_VAULT_COLUMN_NAME);
        cryptoVaults_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME);
        cryptoVaults_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME);


        /**
         * Table CryptoVaults_Stats addition.
         */
        DeveloperDatabaseTable cryptoVaults_StatsTable = developerObjectFactory.getNewDeveloperDatabaseTable(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_TABLE_NAME, cryptoVaults_StatsColumns);
        tables.add(cryptoVaults_StatsTable);

        /**
         * Table CryptoVaults_Detailed_Stats columns.
         */
        List<String> cryptoVaults_Detailed_StatsColumns = new ArrayList<String>();

        cryptoVaults_Detailed_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_CRYPTO_VAULT_COLUMN_NAME);
        cryptoVaults_Detailed_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_NETWORK_COLUMN_NAME);
        cryptoVaults_Detailed_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_ORDER_COLUMN_NAME);
        cryptoVaults_Detailed_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME);
        cryptoVaults_Detailed_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_MONITORED_ADDRESSES_COLUMN_NAME);

        /**
         * Table CryptoVaults_Detailed_Stats addition.
         */
        DeveloperDatabaseTable cryptoVaults_Detailed_StatsTable = developerObjectFactory.getNewDeveloperDatabaseTable(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_TABLE_NAME, cryptoVaults_Detailed_StatsColumns);
        tables.add(cryptoVaults_Detailed_StatsTable);

        /**
         * Table EventAgent_Stats columns.
         */
        List<String> eventAgent_StatsColumns = new ArrayList<String>();

        eventAgent_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME);
        eventAgent_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_LAST_EXECUTION_DATE_COLUMN_NAME);
        eventAgent_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_INCOMING_TRANSACTIONS_COLUMN_NAME);
        eventAgent_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_OUTGOING_TRANSACTIONS_COLUMN_NAME);
        /**
         * Table EventAgent_Stats addition.
         */
        DeveloperDatabaseTable eventAgent_StatsTable = developerObjectFactory.getNewDeveloperDatabaseTable(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_TABLE_NAME, eventAgent_StatsColumns);
        tables.add(eventAgent_StatsTable);


        /**
         * Table Broadcast columns.
         */
        List<String> broadcast_StatsColumns = new ArrayList<String>();

        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_EXECUTION_NUMBER_COLUMN_NAME);
        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_NETWORK);
        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_TRANSACTION_ID);
        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH);
        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_PEER_COUNT);
        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_PEER_BROADCAST_IP);
        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_RETRIES_COUNT);
        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_STATUS);
        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_EXCEPTION);
        broadcast_StatsColumns.add(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_LAST_EXECUTION_DATE_COLUMN_NAME);
        /**
         * Table EventAgent_Stats addition.
         */
        DeveloperDatabaseTable broadcast_StatsTable = developerObjectFactory.getNewDeveloperDatabaseTable(BitcoinCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME, broadcast_StatsColumns);
        tables.add(broadcast_StatsTable);


        /**
         * Table ACTIVENETWORKS columns.
         */
        List<String> activeNetworksColumns = new ArrayList<String>();

        activeNetworksColumns.add(BitcoinCryptoNetworkDatabaseConstants.ACTIVENETWORKS_NETWORKTYPE);
        activeNetworksColumns.add(BitcoinCryptoNetworkDatabaseConstants.ACTIVENETWORKS_KEYS);
        activeNetworksColumns.add(BitcoinCryptoNetworkDatabaseConstants.ACTIVENETWORKS_LAST_UPDATE);

        /**
         * Table ACTIVENETWORKS addition.
         */
        DeveloperDatabaseTable activeNetworksTable = developerObjectFactory.getNewDeveloperDatabaseTable(BitcoinCryptoNetworkDatabaseConstants.ACTIVENETWORKS_TABLE_NAME, activeNetworksColumns);
        tables.add(activeNetworksTable);


        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row: records){
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()){
                    /**
                     * I get each row and save them into a List<String>
                     */
                    developerRow.add(field.getValue());
                }
                /**
                 * I create the Developer Database record
                 */
                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
            /**
             * return the list of DeveloperRecords for the passed table.
             */
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        } catch (Exception e){
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }

//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//    }
//
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }
}