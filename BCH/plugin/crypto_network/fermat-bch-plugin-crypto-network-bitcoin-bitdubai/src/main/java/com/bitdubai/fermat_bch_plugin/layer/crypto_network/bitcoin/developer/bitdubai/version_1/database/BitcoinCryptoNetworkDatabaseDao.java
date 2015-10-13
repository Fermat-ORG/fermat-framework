package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
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
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.TransactionTypes;
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
                record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME, getCurrentDateTime());
                record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME, monitoredPublicKeys);

                databaseTable.insertRecord(record);
            } else {
                /**
                 * I will update an existing record.
                 */
                record = databaseTable.getRecords().get(0);
                record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME, getCurrentDateTime());
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

    /**
     * saves a new Crypto transaction into database
     * @param hash
     * @param cryptoStatus
     * @param blockDepth
     * @param addressTo
     * @param addressFrom
     * @param value
     * @param fee
     * @param protocolStatus
     * @throws CantExecuteDatabaseOperationException
     */
    public void saveNewIncomingTransaction(String hash, CryptoStatus cryptoStatus, int blockDepth, CryptoAddress addressTo, CryptoAddress addressFrom, long value, long fee, ProtocolStatus protocolStatus) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        /**
         * generates the trx_id
         */
        UUID trxId = UUID.randomUUID();

        record.setUUIDValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_TRX_ID_COLUMN_NAME, trxId);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_HASH_COLUMN_NAME, hash);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
        record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_BLOCK_DEPTH_COLUMN_NAME, blockDepth);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_ADDRESS_TO_COLUMN_NAME, addressTo.getAddress());
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME, addressFrom.getAddress());
        record.setDoubleValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_VALUE_COLUMN_NAME, value);
        record.setDoubleValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_FEE_COLUMN_NAME, fee);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, protocolStatus.getCode());
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, getCurrentDateTime());
        try {
            databaseTable.insertRecord(record);
        } catch (CantInsertRecordException e) {
            StringBuilder outputMessage = new StringBuilder("There was an error inserting a new transaction. Transaction record is:");
            outputMessage.append(System.lineSeparator());
            outputMessage.append(XMLParser.parseObject(record));

            throw new CantExecuteDatabaseOperationException (CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage.toString(), "database issue.");
        }
    }

    public CryptoStatus getTransactionCryptoStatus(String txHash) throws CantExecuteDatabaseOperationException{

        return null;
    }

    /**
     * gets the current date and time to save in a database
     * @return
     */
    private String getCurrentDateTime(){
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();
        return date;
    }

    public void saveNewOutgoingTransaction(String hash, CryptoStatus cryptoStatus, int blockDepth, CryptoAddress addressTo, CryptoAddress addressFrom, long value, long fee, ProtocolStatus protocolStatus) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.OUTGOING_TRANSACTIONS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        /**
         * generates the trx_id
         */
        UUID trxId = UUID.randomUUID();

        record.setUUIDValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_TRX_ID_COLUMN_NAME, trxId);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_HASH_COLUMN_NAME, hash);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
        record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_BLOCK_DEPTH_COLUMN_NAME, blockDepth);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_ADDRESS_TO_COLUMN_NAME, addressTo.getAddress());
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME, addressFrom.getAddress());
        record.setDoubleValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_VALUE_COLUMN_NAME, value);
        record.setDoubleValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_FEE_COLUMN_NAME, fee);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, protocolStatus.getCode());
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, getCurrentDateTime());
        try {
            databaseTable.insertRecord(record);
        } catch (CantInsertRecordException e) {
            StringBuilder outputMessage = new StringBuilder("There was an error inserting a new transaction in the Outgoing Transactions Table. Transaction record is:");
            outputMessage.append(System.lineSeparator());
            outputMessage.append(XMLParser.parseObject(record));

            throw new CantExecuteDatabaseOperationException (CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage.toString(), "database issue.");
        }
    }

    /**
     * If the transaction exists in the IncomingTransactions table it will return true, otherwise it will return false
     * @param txHash
     * @return
     */
    public boolean isIncomingTransaction(String txHash) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_TABLE_NAME);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Cant load IncomingTransaction table into memory", "database issue");
        }

        if (databaseTable.getRecords().size() > 0)
            return true;
        else
            return false;
    }

    /**
     * Will get the last current Crypto Status recorded from the given outgoing or incoming transaction
     * @param transactionType
     * @param txHash
     * @return
     */
    public CryptoStatus getStoredTransactionCryptoStatus(TransactionTypes transactionType, String txHash) throws CantExecuteDatabaseOperationException {
        /**
         * I will define the outgoing or incoming table, the filter and the sort order
         */
        DatabaseTable databaseTable = null;
        if (transactionType == TransactionTypes.OUTGOING){
            databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.OUTGOING_TRANSACTIONS_TABLE_NAME);
            databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.OUTGOING_TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
            databaseTable.setFilterOrder(BitcoinCryptoNetworkDatabaseConstants.OUTGOING_TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
        }
        else {
            databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_TABLE_NAME);
            databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
            databaseTable.setFilterOrder(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
        }

        /**
         * Wil load the table into memory
          */
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantExecuteDatabaseOperationException (CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Cant load transaction table into memory.", "database error");
        }

        /**
         * Since Im ordering by update date, I will only get the fist record retrieved to form the crypto status
         */
        DatabaseTableRecord record = databaseTable.getRecords().get(0);
       CryptoStatus cryptoStatus= null;

        /**
         * will get and form the crypto status
         */
        try{
            if (transactionType == TransactionTypes.OUTGOING)
                cryptoStatus = CryptoStatus.getByCode(record.getStringValue(BitcoinCryptoNetworkDatabaseConstants.OUTGOING_TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME));
            else
                cryptoStatus = CryptoStatus.getByCode(record.getStringValue(BitcoinCryptoNetworkDatabaseConstants.INCOMING_TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantExecuteDatabaseOperationException (CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Cant load the crypto status from the database.", "database error");
        }

        return cryptoStatus;
    }
}
