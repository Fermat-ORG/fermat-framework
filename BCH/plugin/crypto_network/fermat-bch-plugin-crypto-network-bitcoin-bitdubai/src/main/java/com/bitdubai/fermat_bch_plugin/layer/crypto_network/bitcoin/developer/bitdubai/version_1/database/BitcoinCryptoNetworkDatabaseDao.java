package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.CryptoVaults;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinCryptoNetworkDatabaseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by rodrigo on 10/9/15.
 */
public class BitcoinCryptoNetworkDatabaseDao {
    /**
     * Class variables
     */
    Database database;

    /**
     * platform variables
     */
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor
     */
    public BitcoinCryptoNetworkDatabaseDao(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;


        try {
            initializeDatabase();
        } catch (CantInitializeBitcoinCryptoNetworkDatabaseException e) {
            e.printStackTrace();
        }
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

    /**
     * Update the crypto vault statistics when called to start monitoring the network.
     * @param cryptoVault
     * @param monitoredPublicKeys
     * @throws CantExecuteDatabaseOperationException
     */
    public void updateCryptoVaultsStatistics(CryptoVaults cryptoVault, int monitoredPublicKeys) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_TABLE_NAME);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_CRYPTO_VAULT_COLUMN_NAME, cryptoVault.getCode(), DatabaseFilterType.EQUAL);
        /**
         * I will check if I have the record to update it
         */
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantExecuteDatabaseOperationException(
                    CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    "trying to load table " + databaseTable.getTableName() + " into memory",
                    null);
        }

        DatabaseTableRecord record = null;
        try{
            if (databaseTable.getRecords().size() == 0){
                /**
                 * I will insert a new record
                 */
                record = databaseTable.getEmptyRecord();
                record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_CRYPTO_VAULT_COLUMN_NAME, cryptoVault.getCode());
                record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME, new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
                record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME, monitoredPublicKeys);

                databaseTable.insertRecord(record);
            } else {
                /**
                 * I will update an existing record.
                 */
                record = databaseTable.getRecords().get(0);
                record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME, new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
                record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME, monitoredPublicKeys);

                databaseTable.updateRecord(record);
            }
        } catch (CantInsertRecordException | CantUpdateRecordException e) {
            StringBuilder outputMessage = new StringBuilder("There was an error inserting or updating a record in table ");
            outputMessage.append(databaseTable.getTableName());
            outputMessage.append("Record is:");
            outputMessage.append(System.lineSeparator());
            outputMessage.append(XMLParser.parseObject(record));

            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage.toString(), "database issue.");
        }

    }

}
