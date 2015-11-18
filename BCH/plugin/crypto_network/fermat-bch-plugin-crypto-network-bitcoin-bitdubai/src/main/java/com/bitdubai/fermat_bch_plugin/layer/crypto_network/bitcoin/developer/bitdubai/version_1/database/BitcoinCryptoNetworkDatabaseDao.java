package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.TransactionTypes;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinCryptoNetworkDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util.TransactionProtocolData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

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
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        DatabaseTableRecord record = null;
        try{
            if (databaseTable.getRecords().size() == 0){
                /**
                 * I will insert a new record
                 */
                record = databaseTable.getEmptyRecord();
                record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_CRYPTO_VAULT_COLUMN_NAME, cryptoVault.getCode());
                record.setLongValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME, getCurrentDateTime());
                record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME, monitoredPublicKeys);

                databaseTable.insertRecord(record);
            } else {
                /**
                 * I will update an existing record.
                 */
                record = databaseTable.getRecords().get(0);
                record.setLongValue(BitcoinCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME, getCurrentDateTime());
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
     * Saves a new incoming transaction into the database
     * @param hash
     * @param cryptoStatus
     * @param blockDepth
     * @param addressTo
     * @param addressFrom
     * @param value
     * @param op_Return
     * @param protocolStatus
     * @throws CantExecuteDatabaseOperationException
     */
    public void saveNewIncomingTransaction  (String hash,
                                             CryptoStatus cryptoStatus,
                                            int blockDepth,
                                            CryptoAddress addressTo,
                                            CryptoAddress addressFrom,
                                            long value,
                                            String op_Return,
                                            ProtocolStatus protocolStatus)
            throws CantExecuteDatabaseOperationException{
        this.saveNewTransaction(hash, cryptoStatus, blockDepth, addressTo, addressFrom, value, op_Return, protocolStatus, TransactionTypes.INCOMING);
    }

    /**
     * saves a new Crypto transaction into database
     * @param hash
     * @param cryptoStatus
     * @param blockDepth
     * @param addressTo
     * @param addressFrom
     * @param value
     * @param op_Return
     * @param protocolStatus
     * @throws CantExecuteDatabaseOperationException
     */
    private void saveNewTransaction( String hash,
                                    CryptoStatus cryptoStatus,
                                    int blockDepth,
                                    CryptoAddress addressTo,
                                    CryptoAddress addressFrom,
                                    long value,
                                    String op_Return,
                                    ProtocolStatus protocolStatus,
                                    TransactionTypes transactionTypes)
            throws CantExecuteDatabaseOperationException{

        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        /**
         * generates the trx_id
         */
        UUID trxId = UUID.randomUUID();

        record.setUUIDValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME, trxId);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, hash);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
        record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_DEPTH_COLUMN_NAME, blockDepth);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME, addressTo.getAddress());
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME, addressFrom.getAddress());
        record.setDoubleValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_VALUE_COLUMN_NAME, value);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_OP_RETURN_COLUMN_NAME, op_Return);
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, protocolStatus.getCode());
        record.setLongValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, getCurrentDateTime());
        record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionTypes.getCode());
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
    private long getCurrentDateTime(){
        return  System.currentTimeMillis();
    }

    /**
     * Saves and outgoing transaction into the database
     * @param hash
     * @param cryptoStatus
     * @param blockDepth
     * @param addressTo
     * @param addressFrom
     * @param value
     * @param op_Return
     * @param protocolStatus
     * @throws CantExecuteDatabaseOperationException
     */
    public void saveNewOutgoingTransaction(String hash,
                                           CryptoStatus cryptoStatus,
                                           int blockDepth,
                                           CryptoAddress addressTo,
                                           CryptoAddress addressFrom,
                                           long value,
                                           String op_Return,
                                           ProtocolStatus protocolStatus)
            throws CantExecuteDatabaseOperationException{
        this.saveNewTransaction(hash, cryptoStatus, blockDepth, addressTo, addressFrom, value, op_Return, protocolStatus, TransactionTypes.OUTGOING);
    }

    /**
     * If the transaction exists in the Transactions table and is of type Incoming it will return true, otherwise it will return false
     * @param txHash
     * @return
     */
    public boolean isIncomingTransaction(String txHash) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, TransactionTypes.INCOMING.getCode(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
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
    public CryptoStatus getStoredTransactionCryptoStatus(@Nullable TransactionTypes transactionType, String txHash) throws CantExecuteDatabaseOperationException {
        /**
         * I will define the outgoing or incoming table, the filter and the sort order
         */
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        if (transactionType != null )
            databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.setFilterOrder(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

        /**
         * Wil load the table into memory
          */
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * If I didn't get anything, I will return null
         */
        if (databaseTable.getRecords().isEmpty())
            return null;

        /**
         * Since Im ordering by update date, I will only get the fist record retrieved to form the crypto status
         */
        DatabaseTableRecord record = databaseTable.getRecords().get(0);
        CryptoStatus cryptoStatus= null;

        /**
         * will get and form the crypto status
         */
        try {
            cryptoStatus = CryptoStatus.getByCode(record.getStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME));
        } catch (InvalidParameterException e1) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e1, "The stored Crypto Status value is not valid.", null);
        }

        return cryptoStatus;
    }

    /**
     * Gets the amount of transaction in ProtocolStatus as Pending Notified from the specified table
     * @param transactionType
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public int getPendingNotifiedTransactions(TransactionTypes transactionType) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * I return the amount of records.
         */
        return databaseTable.getRecords().size();
    }

    /**
     * Updates the execution statistics of the event Agent
     * it will insert a new record if we have pending transactions, and update the existing value if we have zero transactions.
     * @param pendingIncoming
     * @param pendingOutgoing
     */
    public void updateEventAgentStats(int pendingIncoming, int pendingOutgoing) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_TABLE_NAME);

        /**
         * I will get the current execution number
         */
        int currentExecutionNumber;
        databaseTable.setFilterOrder(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        if (databaseTable.getRecords().size() != 0)
            currentExecutionNumber = databaseTable.getRecords().get(0).getIntegerValue(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME);
        else
            currentExecutionNumber = 0;

        /**
         * if I have pending transactions, I will insert a new record, also if this is the first time I'm executing.
         */
        DatabaseTableRecord record = null;
        try{
            if (pendingIncoming != 0 || pendingOutgoing != 0 || currentExecutionNumber == 0){
                record = databaseTable.getEmptyRecord();
                record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME, currentExecutionNumber + 1);
                record.setLongValue(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_LAST_EXECUTION_DATE_COLUMN_NAME, getCurrentDateTime());
                record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_INCOMING_TRANSACTIONS_COLUMN_NAME, pendingIncoming);
                record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_OUTGOING_TRANSACTIONS_COLUMN_NAME, pendingOutgoing);

                databaseTable.insertRecord(record);

            } else {
                /**
                 * I will update existing value with zero pending transactions
                 */
                DatabaseTableFilter filterIncoming = databaseTable.getEmptyTableFilter();
                filterIncoming.setColumn(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_INCOMING_TRANSACTIONS_COLUMN_NAME);
                filterIncoming.setValue("0");
                filterIncoming.setType(DatabaseFilterType.EQUAL);

                DatabaseTableFilter filterOutgoing = databaseTable.getEmptyTableFilter();
                filterOutgoing.setColumn(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_OUTGOING_TRANSACTIONS_COLUMN_NAME);
                filterOutgoing.setValue("0");
                filterOutgoing.setType(DatabaseFilterType.EQUAL);

                /**
                 * I create the two filters and add them to form a filter group.
                 */
                List<DatabaseTableFilter> filters = new ArrayList<>();
                filters.add(filterIncoming);
                filters.add(filterOutgoing);

                List<DatabaseTableFilterGroup> filterGroups = new ArrayList<>();
                databaseTable.setFilterGroup(databaseTable.getNewFilterGroup(filters, filterGroups, DatabaseFilterOperator.AND));
                databaseTable.loadToMemory();

                record = databaseTable.getRecords().get(0);
                record.setIntegerValue(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME, currentExecutionNumber + 1);
                record.setLongValue(BitcoinCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_LAST_EXECUTION_DATE_COLUMN_NAME, getCurrentDateTime());

                databaseTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        } catch (CantInsertRecordException | CantUpdateRecordException e) {
            StringBuilder outputMessage = new StringBuilder("There was an error inserting or modifying a record. The record is:");
            outputMessage.append(System.lineSeparator());
            outputMessage.append(XMLParser.parseObject(record));

            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage.toString(), "database issue");
        }
    }

    /**
     * Gets the crypto Status list that are in pending status from the specified table.
     * @param transactionType
     * @return
     */
    public Set<CryptoStatus> getPendingCryptoStatus(TransactionTypes transactionType) throws CantExecuteDatabaseOperationException {
        String cryptoStatusColumnName;
        Set<CryptoStatus> cryptoStatuses = new HashSet<>();

        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        cryptoStatusColumnName = BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME;

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * get all the CryptoStatus and remove duplicates as Im storing them in a set.
         */
        for (DatabaseTableRecord record : databaseTable.getRecords()){
            try {
                CryptoStatus cryptoStatus = CryptoStatus.getByCode(record.getStringValue(cryptoStatusColumnName));
                cryptoStatuses.add(cryptoStatus);
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
        }

        return cryptoStatuses;
    }

    /**
     * Confirms a transaction by marking it to No Action Required in is Protocol Status.
     * I will have to search this transaction in both incoming and outgoing tables
     * @param transactionID
     * @throws CantExecuteDatabaseOperationException
     */
    public void confirmReception(UUID transactionID) throws CantExecuteDatabaseOperationException{
        confirmIncomingTransactionReception(transactionID);
        confirmOutgoingTransactionReception(transactionID);
    }


    /**
     * Marks to RECEPTION_NOTIFIED the protocol status of this Incoming transaction if found.
     * @param transactionID
     */
    private void confirmIncomingTransactionReception(UUID transactionID) throws CantExecuteDatabaseOperationException {
        confirmTransactionReception(transactionID, TransactionTypes.INCOMING);
    }

    /**
     * Marks to RECEPTION_NOTIFIED the protocol status of this Outgoing transaction if found.
     * @param transactionID
     */
    private void confirmOutgoingTransactionReception(UUID transactionID) throws CantExecuteDatabaseOperationException {
        confirmTransactionReception(transactionID, TransactionTypes.OUTGOING);
    }

    /**
     * Confirms the Transaction by updating the PROTOCOL STATUS to RECEPTION_NOTIFIED
     * @param transactionID the internal Transaction Id
     * @param transactionType incoming or outgoing transaction
     * @throws CantExecuteDatabaseOperationException
     */
    private void confirmTransactionReception(UUID transactionID, TransactionTypes transactionType) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * set the value to RECEPTION_NOTIFIED
         */
        if (databaseTable.getRecords().size() > 0){
            DatabaseTableRecord record = databaseTable.getRecords().get(0);
            record.setStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.RECEPTION_NOTIFIED.getCode());
            record.setLongValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, getCurrentDateTime());

            try {
                databaseTable.updateRecord(record);
            } catch (CantUpdateRecordException e) {
                StringBuilder errorOutput = new StringBuilder("There was a problem setting to RECEPTION_NOTIFIED the following transaction:");
                errorOutput.append(System.lineSeparator());
                errorOutput.append(XMLParser.parseObject(record));
                throw  new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, errorOutput.toString(), "database issue");
            }
        }

    }


    /**
     * Gets the list of Pending trasactions and forms the TransactionProtocolData object to pass to the Incoming Crypto Router
     * Gets the data from both incoming and outgoing transactions.
     * @return
     */
    public List<TransactionProtocolData> getPendingTransactionProtocolData() throws CantExecuteDatabaseOperationException {
        List<TransactionProtocolData> transactionProtocolDataList = getPendingTransactionProtocolData(TransactionTypes.INCOMING);
        transactionProtocolDataList.addAll(getPendingTransactionProtocolData(TransactionTypes.OUTGOING));
        return transactionProtocolDataList;
    }

    /**
     * Gets the pending transaction data from the specified table
     * @param transactionType
     * @return
     */
    private List<TransactionProtocolData> getPendingTransactionProtocolData(TransactionTypes transactionType) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable;
        String transactionIdColumnName;

        List<TransactionProtocolData> transactionProtocolDataList = new ArrayList<>();

        databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        transactionIdColumnName = BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME;


        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * Will form the TransactionProtocolData object from the records
         */
        for (DatabaseTableRecord record : databaseTable.getRecords()){
            TransactionProtocolData transactionProtocolData = new TransactionProtocolData();
            transactionProtocolData.setTransactionId(UUID.fromString(record.getStringValue(transactionIdColumnName)));
            transactionProtocolData.setCryptoTransaction(getCryptoTransactionFromRecord(transactionType, record));
            transactionProtocolData.setAction(Action.APPLY);
            transactionProtocolData.setTimestamp(System.currentTimeMillis() / 1000L); //todo I need to convert the stored saved date to long

            transactionProtocolDataList.add(transactionProtocolData);
        }

        return transactionProtocolDataList;

    }

    /**
     * Creates a incoming or outgoing CryptoTransaction object from a database record
     * @param transactionType
     * @param record
     * @return
     */
    private CryptoTransaction getCryptoTransactionFromRecord(TransactionTypes transactionType, DatabaseTableRecord record) {
        String addressFromColumnName, addressToColumnName, transactionHashColumnName, valueColumnName, cryptoStatusColumnName, op_Return;
        transactionHashColumnName = BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME;
        addressFromColumnName = BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME;
        addressToColumnName = BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME;
        valueColumnName = BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_VALUE_COLUMN_NAME;
        cryptoStatusColumnName = BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME;


       CryptoTransaction cryptoTransaction = new CryptoTransaction();
        cryptoTransaction.setTransactionHash(record.getStringValue(transactionHashColumnName));
        cryptoTransaction.setCryptoCurrency(CryptoCurrency.BITCOIN);
        try {
            cryptoTransaction.setCryptoStatus(CryptoStatus.getByCode(record.getStringValue(cryptoStatusColumnName)));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
        cryptoTransaction.setCryptoAmount(record.getLongValue(valueColumnName));
        cryptoTransaction.setAddressFrom(new CryptoAddress(record.getStringValue(addressFromColumnName), CryptoCurrency.BITCOIN));
        cryptoTransaction.setAddressTo(new CryptoAddress(record.getStringValue(addressToColumnName), CryptoCurrency.BITCOIN));
        cryptoTransaction.setOp_Return(record.getStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_OP_RETURN_COLUMN_NAME));
        return cryptoTransaction;
    }

    /**
     * Gets both incoming and outgoing transactions hash stored in the database
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public Set<String> getStoredTransactionHash () throws CantExecuteDatabaseOperationException{
        Set<String> transactionsSet = new HashSet<>();

        /**
         * Loads and puts in the transactionsSet  the list of stored Hashes
         */
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        for (DatabaseTableRecord record : databaseTable.getRecords()){
            transactionsSet.add(record.getStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME));
        }
        return transactionsSet;
    }

    /**
     * Gets the incoming transaction data and forms the CryptoTransaction object
     * @param txHash
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public List<CryptoTransaction> getIncomingCryptoTransaction(String txHash)  throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        List<CryptoTransaction> cryptoTransactions = new ArrayList<>();
        for (DatabaseTableRecord record : databaseTable.getRecords()){

            /**
             * Gets all the values
             */
            CryptoAddress addressFrom = new CryptoAddress();
            addressFrom.setAddress(record.getStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME));
            addressFrom.setCryptoCurrency(CryptoCurrency.BITCOIN);

            CryptoAddress addressTo = new CryptoAddress();
            addressFrom.setAddress(record.getStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME));
            addressFrom.setCryptoCurrency(CryptoCurrency.BITCOIN);

            long amount = record.getLongValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_VALUE_COLUMN_NAME);

            CryptoStatus cryptoStatus = null;
            try {
                cryptoStatus = CryptoStatus.getByCode(record.getStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME));
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }

            String op_Return = record.getStringValue(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_OP_RETURN_COLUMN_NAME);

            /**
             * Forms the CryptoTransaction object
             */
            CryptoTransaction cryptoTransaction = new CryptoTransaction();
            cryptoTransaction.setTransactionHash(txHash);
            cryptoTransaction.setAddressTo(addressTo);
            cryptoTransaction.setAddressFrom(addressFrom);
            cryptoTransaction.setCryptoAmount(amount);
            cryptoTransaction.setCryptoCurrency(CryptoCurrency.BITCOIN);
            cryptoTransaction.setCryptoStatus(cryptoStatus);
            cryptoTransaction.setOp_Return(op_Return);

            /**
             * adds it to the list
             */
            cryptoTransactions.add(cryptoTransaction);
        }

        return cryptoTransactions;
    }

    /**
     * sets and thorws the error when we coundl't load a table into memory
     * @param e
     * @param tableName
     * @throws CantExecuteDatabaseOperationException
     */
    private void throwLoadToMemoryException(Exception e, String tableName) throws CantExecuteDatabaseOperationException{
        String outputMessage = "There was an error loading into memory table " + tableName + ".";
        throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage, "Database error.");
    }

    /**
     * Will set the passed protocol status to the given transaction. If the transaction doesn't exists
     * I will throw an error
     * @param transactionId     the internal transactionId
     * @param protocolStatus    the new protocol status to update
     * @throws CantExecuteDatabaseOperationException
     */
    public void setTransactionProtocolStatus(UUID transactionId, ProtocolStatus protocolStatus) throws CantExecuteDatabaseOperationException{

    }

    /**
     * Verifies if the passed transaction already exists in the database
     * @param txHash
     * @param cryptoStatus
     * @return
     */
    public boolean isNewTransaction(String txHash, CryptoStatus cryptoStatus) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        /**
         * sets the table filters
         */
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        databaseTable.setStringFilter(BitcoinCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * If there are no records, then return true.
         */
        if (databaseTable.getRecords().isEmpty())
            return true;
        else
            return false;
    }
}
