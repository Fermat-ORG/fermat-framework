package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;

import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.Status;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.TransactionTypes;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.VaultKeyMaintenanceParameters;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions.CantInitializeFermatCryptoNetworkDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util.FermatBlockchainNetworkSelector;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util.FermatTransactionConverter;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util.TransactionProtocolData;

import org.apache.commons.lang.StringUtils;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 6/22/16.
 */
public class FermatCryptoNetworkDatabaseDao {

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
    public FermatCryptoNetworkDatabaseDao(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;


        try {
            initializeDatabase();
        } catch (CantInitializeFermatCryptoNetworkDatabaseException e) {
            System.out.println("CantInitializeFermatCryptoNetworkDatabaseException FermatCryṕtoNetworkDatabaseDAO, please check this");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Exception, method FermatCryptoNetworkDatabaseDao, FermatCryṕtoNetworkDatabaseDAO, please check this");
            e.printStackTrace();
        }
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeFermatCryptoNetworkDatabaseException
     */
    public void initializeDatabase() throws CantInitializeFermatCryptoNetworkDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

            if(database==null){
                System.out.println("database null in FermatCryptoNetworkDatabaseDAO, please check this");
            }

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeFermatCryptoNetworkDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {
            System.out.println("DatabaseNotFoundException FermatCryṕtoNetworkDatabaseDAO, please check this");
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            FermatCryptoNetworkDatabaseFactory FermatCryptoNetworkDatabaseFactory = new FermatCryptoNetworkDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = FermatCryptoNetworkDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                System.out.println("CantCreateDatabaseException FermatCryṕtoNetworkDatabaseDAO, please check this");
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeFermatCryptoNetworkDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e){

            System.out.println("Exception FermatCryṕtoNetworkDatabaseDAO, please check this");
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            FermatCryptoNetworkDatabaseFactory FermatCryptoNetworkDatabaseFactory = new FermatCryptoNetworkDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = FermatCryptoNetworkDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                System.out.println("CantCreateDatabaseException FermatCryṕtoNetworkDatabaseDAO, please check this");
                throw new CantInitializeFermatCryptoNetworkDatabaseException(cantCreateDatabaseException.getMessage());
            } catch (Exception e1){
                System.out.println("another exception");
                e1.printStackTrace();
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
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_CRYPTO_VAULT_COLUMN_NAME, cryptoVault.getCode(), DatabaseFilterType.EQUAL);
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
                record.setStringValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_CRYPTO_VAULT_COLUMN_NAME, cryptoVault.getCode());
                record.setLongValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME, getCurrentDateTime());
                record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME, monitoredPublicKeys);

                databaseTable.insertRecord(record);
            } else {
                /**
                 * I will update an existing record.
                 */
                record = databaseTable.getRecords().get(0);
                record.setLongValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME, getCurrentDateTime());
                record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME, monitoredPublicKeys);

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
     * @param cryptoTransaction
     * @param transactionId
     * @param protocolStatus
     * @throws CantExecuteDatabaseOperationException
     */
    private void saveNewTransaction(CryptoTransaction cryptoTransaction, UUID transactionId, ProtocolStatus protocolStatus)
            throws CantExecuteDatabaseOperationException{
        /**
         * if the transaction hash already exists with the same crypto Status, then I won't insert it.
         * Issue #4501
         * This is because the wallet is not detecting as own our outgoing transactions and the commit launches the incoming bitcoins
         * event. A transaction that we are sending may be actually recorded as incoming. The important thing at this point is not to
         * duplicate transactions
         */
        if (!this.isNewTransaction(cryptoTransaction.getTransactionHash(), cryptoTransaction.getCryptoStatus(), cryptoTransaction.getAddressTo(), cryptoTransaction.getCryptoTransactionType()))
            return;

        /**
         * I will verify that this txId doesn't already exists. If it does, then is an error
         */
        if (isDuplicateTransaction(transactionId)){
            StringBuilder output = new StringBuilder("The specified TransactionId already exists.");
            output.append(System.lineSeparator());
            output.append(transactionId.toString());
            output.append(System.lineSeparator());
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, null, output.toString(), "Multiple calls from transaction plugin to send bitcoins using the same transaction");
        }

        /**
         * will add any missed transaction because we may detect the crypto transaction already OBC, so we need
         * to manually add any previous state.
         */
        saveMissingCryptoTransaction(cryptoTransaction);

        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        DatabaseTableRecord record = getRecordFromCryptoTransaction(transactionId, protocolStatus, cryptoTransaction);

        try {
            databaseTable.insertRecord(record);
        } catch (CantInsertRecordException e) {
            StringBuilder outputMessage = new StringBuilder("There was an error inserting a new transaction. Transaction record is:");
            outputMessage.append(System.lineSeparator());
            outputMessage.append(XMLParser.parseObject(record));

            throw new CantExecuteDatabaseOperationException (CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage.toString(), "database issue.");
        }
    }

    private void saveMissingCryptoTransaction(CryptoTransaction cryptoTransaction) throws CantExecuteDatabaseOperationException {
        switch (cryptoTransaction.getCryptoStatus()){
            case ON_BLOCKCHAIN:
                if (isNewTransaction(cryptoTransaction.getTransactionHash(), CryptoStatus.ON_CRYPTO_NETWORK, cryptoTransaction.getAddressTo(), cryptoTransaction.getCryptoTransactionType())){
                    CryptoTransaction missingCryptoTransaction = FermatTransactionConverter.copyCryptoTransaction(cryptoTransaction);
                    missingCryptoTransaction.setCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                    saveNewTransaction(missingCryptoTransaction, UUID.randomUUID(), calculateProtocolStatus(missingCryptoTransaction));
                }
                break;
            case IRREVERSIBLE:
                if (isNewTransaction(cryptoTransaction.getTransactionHash(), CryptoStatus.ON_CRYPTO_NETWORK, cryptoTransaction.getAddressTo(), cryptoTransaction.getCryptoTransactionType())){
                    CryptoTransaction missingCryptoTransaction = FermatTransactionConverter.copyCryptoTransaction(cryptoTransaction);
                    missingCryptoTransaction.setCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                    saveNewTransaction(missingCryptoTransaction, UUID.randomUUID(), calculateProtocolStatus(missingCryptoTransaction));
                }
                if (isNewTransaction(cryptoTransaction.getTransactionHash(), CryptoStatus.ON_BLOCKCHAIN, cryptoTransaction.getAddressTo(), cryptoTransaction.getCryptoTransactionType())){
                    CryptoTransaction missingOBCCryptoTransaction = FermatTransactionConverter.copyCryptoTransaction(cryptoTransaction);
                    missingOBCCryptoTransaction.setCryptoStatus(CryptoStatus.ON_BLOCKCHAIN);
                    saveNewTransaction(missingOBCCryptoTransaction, UUID.randomUUID(), calculateProtocolStatus(missingOBCCryptoTransaction));
                }
                break;
        }
    }

    /**
     * will validate if the transaction ID already exists in the database
     * @param trxId
     * @return
     */
    private boolean isDuplicateTransaction(UUID trxId) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.addUUIDFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME, trxId, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        return !databaseTable.getRecords().isEmpty();
    }

    /**
     * gets the current Crypto Status for the specified Transaction ID
     * @param txHash the Bitcoin transaction hash
     * @return the last crypto status
     * @throws CantGetTransactionCryptoStatusException
     */
    public CryptoStatus getTransactionCryptoStatus(String txHash) throws CantExecuteDatabaseOperationException{
        DatabaseTable cryptoTransactionsTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);

        cryptoTransactionsTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        // we are only getting results from outgoing transactions
        cryptoTransactionsTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, CryptoTransactionType.OUTGOING.getCode(), DatabaseFilterType.EQUAL);
        try {
            cryptoTransactionsTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, cryptoTransactionsTable.getTableName());
        }

        List<DatabaseTableRecord> databaseTableRecordList = cryptoTransactionsTable.getRecords();
        if (databaseTableRecordList.isEmpty()) {
            return CryptoStatus.PENDING_SUBMIT;
        } else {
            CryptoStatus lastCryptoStatus = null;
            for (DatabaseTableRecord record : databaseTableRecordList) {
                CryptoStatus cryptoStatus = null;
                try {
                    cryptoStatus = CryptoStatus.getByCode(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME));
                } catch (InvalidParameterException e) {
                    throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "the stored CryptoStatus is not valid.", null);
                }
                if (lastCryptoStatus == null)
                    lastCryptoStatus = cryptoStatus;
                else if (lastCryptoStatus.getOrder() < cryptoStatus.getOrder())
                    lastCryptoStatus = cryptoStatus;
            }
            return lastCryptoStatus;
        }

    }

    /**
     * gets the current date and time to save in a database
     * @return
     */
    private long getCurrentDateTime(){
        return  System.currentTimeMillis();
    }

    /**
     * If the transaction exists in the Transactions table and is of type Incoming it will return true, otherwise it will return false
     * @param txHash
     * @return
     */
    public boolean isIncomingTransaction(String txHash) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, TransactionTypes.INCOMING.getCode(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        return databaseTable.getRecords().size() > 0;
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
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        if (transactionType != null )
            databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.addFilterOrder(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

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
            cryptoStatus = CryptoStatus.getByCode(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME));
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
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

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
    public synchronized void updateEventAgentStats(int pendingIncoming, int pendingOutgoing) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_TABLE_NAME);

        /**
         * I will get the current execution number
         */
        int currentExecutionNumber;
        databaseTable.addFilterOrder(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        if (databaseTable.getRecords().size() != 0)
            currentExecutionNumber = databaseTable.getRecords().get(0).getIntegerValue(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME);
        else
            currentExecutionNumber = 0;

        /**
         * if I have pending transactions, I will insert a new record, also if this is the first time I'm executing.
         */
        DatabaseTableRecord record = null;
        try{
            if (pendingIncoming != 0 || pendingOutgoing != 0 || currentExecutionNumber == 0){
                record = databaseTable.getEmptyRecord();
                record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME, currentExecutionNumber + 1);
                record.setLongValue(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_LAST_EXECUTION_DATE_COLUMN_NAME, getCurrentDateTime());
                record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_INCOMING_TRANSACTIONS_COLUMN_NAME, pendingIncoming);
                record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_OUTGOING_TRANSACTIONS_COLUMN_NAME, pendingOutgoing);

                databaseTable.insertRecord(record);

            } else {
                /**
                 * I will update existing value with zero pending transactions
                 */
                DatabaseTableFilter filterIncoming = databaseTable.getEmptyTableFilter();
                filterIncoming.setColumn(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_INCOMING_TRANSACTIONS_COLUMN_NAME);
                filterIncoming.setValue("0");
                filterIncoming.setType(DatabaseFilterType.EQUAL);

                DatabaseTableFilter filterOutgoing = databaseTable.getEmptyTableFilter();
                filterOutgoing.setColumn(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_PENDING_OUTGOING_TRANSACTIONS_COLUMN_NAME);
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
                record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME, currentExecutionNumber + 1);
                record.setLongValue(FermatCryptoNetworkDatabaseConstants.EVENTAGENT_STATS_LAST_EXECUTION_DATE_COLUMN_NAME, getCurrentDateTime());

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

        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        cryptoStatusColumnName = FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME;

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
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

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
            record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.RECEPTION_NOTIFIED.getCode());
            record.setLongValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, getCurrentDateTime());

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
        List<TransactionProtocolData> transactionProtocolDataList = getTransactionProtocolData(TransactionTypes.INCOMING, ProtocolStatus.TO_BE_NOTIFIED);
        transactionProtocolDataList.addAll(getTransactionProtocolData(TransactionTypes.OUTGOING, ProtocolStatus.TO_BE_NOTIFIED));
        return transactionProtocolDataList;
    }

    /**
     * Gets the pending transaction data from the specified table
     * @param transactionType
     * @return
     */
    private List<TransactionProtocolData> getTransactionProtocolData(TransactionTypes transactionType, @Nullable ProtocolStatus protocolStatus) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable;
        String transactionIdColumnName;

        List<TransactionProtocolData> transactionProtocolDataList = new ArrayList<>();

        databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);

        /**
         * I will set the ProtocolStatus filter, If I received something.
         */
        if (protocolStatus != null)
            databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);

        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        transactionIdColumnName = FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME;


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
            transactionProtocolData.setCryptoTransaction(getCryptoTransactionFromRecord(record));
            transactionProtocolData.setAction(Action.APPLY);
            transactionProtocolData.setTimestamp(System.currentTimeMillis() / 1000L); //todo I need to convert the stored saved date to long

            transactionProtocolDataList.add(transactionProtocolData);
        }

        return transactionProtocolDataList;

    }

    /**
     * Creates a incoming or outgoing CryptoTransaction object from a database record
     * @param record
     * @return
     */
    private CryptoTransaction getCryptoTransactionFromRecord(DatabaseTableRecord record) {
        CryptoTransaction cryptoTransaction = new CryptoTransaction();

        //TransactionHash
        cryptoTransaction.setTransactionHash(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME));
        // Block hash
        if (!StringUtils.isBlank(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_HASH_COLUMN_NAME)))
            cryptoTransaction.setBlockHash(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_HASH_COLUMN_NAME));
        //BlockchainNetworkType
        cryptoTransaction.setBlockchainNetworkType(BlockchainNetworkType.getByCode(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCKCHAIN_NETWORK_TYPE)));
        //Currency
        cryptoTransaction.setCryptoCurrency(CryptoCurrency.FERMAT);
        //CryptoStatus
        try {
            cryptoTransaction.setCryptoStatus(CryptoStatus.getByCode(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME)));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
        //BTC Amount
        cryptoTransaction.setBtcAmount(record.getLongValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BTC_AMOUNT_COLUMN_NAME));
        //Fee Amount
        cryptoTransaction.setFee(record.getLongValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_FEE_AMOUNT_COLUMN_NAME));
        //CryptoAmount
        cryptoTransaction.setCryptoAmount(record.getLongValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_AMOUNT_COLUMN_NAME));
        //AddressFrom
        cryptoTransaction.setAddressFrom(new CryptoAddress(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME), CryptoCurrency.FERMAT));
        //AddressTo
        cryptoTransaction.setAddressTo(new CryptoAddress(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME), CryptoCurrency.FERMAT));
        //OP_Return
        cryptoTransaction.setOp_Return(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_OP_RETURN_COLUMN_NAME));
        //CryptoTransactionType
        try {
            cryptoTransaction.setCryptoTransactionType(CryptoTransactionType.getByCode(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME)));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
        cryptoTransaction.setBlockDepth(record.getIntegerValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_DEPTH_COLUMN_NAME));

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
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        for (DatabaseTableRecord record : databaseTable.getRecords()){
            transactionsSet.add(record.getStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME));
        }
        return transactionsSet;
    }

    /**
     * Gets the incoming transaction data and forms the CryptoTransaction object
     * @param txHash
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public List<CryptoTransaction> getCryptoTransactions(String txHash, @Nullable CryptoTransactionType cryptoTransactionType, @Nullable CryptoAddress toAddress)  throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);

        if (cryptoTransactionType != null)
            databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, cryptoTransactionType.getCode(), DatabaseFilterType.EQUAL);

        if (toAddress != null)
            databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME, toAddress.getAddress(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        List<CryptoTransaction> cryptoTransactions = new ArrayList<>();
        for (DatabaseTableRecord record : databaseTable.getRecords()){
            CryptoTransaction cryptoTransaction = getCryptoTransactionFromRecord(record);
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
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * If I didn't get the expected result, I will raise an error.
         */
        if (databaseTable.getRecords().size() != 1) {
            StringBuilder output = new StringBuilder("There was an unexpected result executing the query.");
            output.append(System.lineSeparator());
            output.append("Records returned: " + databaseTable.getRecords().size());
            output.append(System.lineSeparator());
            output.append("Transaction id: " + transactionId.toString());
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, null, output.toString(), null);
        }

        /**
         * I will get the record and Update the protocol status to the passed value
         */
        DatabaseTableRecord record = databaseTable.getRecords().get(0);
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, protocolStatus.getCode() );
        try {
            databaseTable.updateRecord(record);
        } catch (CantUpdateRecordException e) {
            StringBuilder output = new StringBuilder("There was an error updating the protocol status value.");
            output.append(System.lineSeparator());
            output.append("Record returned: " + XMLParser.parseObject(record));
            output.append(System.lineSeparator());
            output.append("Protocol Status: " + protocolStatus.getCode());
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, null, output.toString(), null);
        }
    }

    /**
     * Verifies if the passed transaction already exists in the database
     * @param txHash
     * @param cryptoStatus
     * @return
     */
    private boolean isNewTransaction(String txHash, CryptoStatus cryptoStatus, CryptoAddress addressTo, CryptoTransactionType cryptoTransactionType) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        /**
         * sets the table filters
         */
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME, addressTo.getAddress(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, cryptoTransactionType.getCode(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * If there are no records, then return true.
         */
        return databaseTable.getRecords().isEmpty();
    }

    /**
     * Updates the detailed crypto stats with the passed information
     * @param cryptoVault
     * @param blockchainNetworkType
     * @param keyList
     * @throws CantExecuteDatabaseOperationException
     */
    public void updateDetailedCryptoStats(CryptoVaults cryptoVault, BlockchainNetworkType blockchainNetworkType, List<ECKey> keyList)  throws CantExecuteDatabaseOperationException {
        /**
         * If we are not allowed to save detailed information then we will exit
         */
        if (!VaultKeyMaintenanceParameters.STORE_DETAILED_KEY_INFORMATION)
            return;

        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_TABLE_NAME);
        DatabaseTransaction transaction = database.newTransaction();

        /**
         * for each passed Key, I will insert a record
         */
        int order = 1;
        for (ECKey key : keyList){
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_CRYPTO_VAULT_COLUMN_NAME, cryptoVault.getCode());
            record.setStringValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_NETWORK_COLUMN_NAME, blockchainNetworkType.getCode());
            record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_ORDER_COLUMN_NAME, order);
            order++;
            record.setStringValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME, key.getPublicKeyAsHex());

            /**
             * I will form the address from the public key and the network so I can insert it.
             */
            NetworkParameters networkParameters = FermatBlockchainNetworkSelector.getNetworkParameter(blockchainNetworkType);
            Address address = null;
            try {
                address = new Address(networkParameters, key.toAddress(networkParameters).toString());
            } catch (AddressFormatException e) {
                throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "coundl't form an address.", null);
            }
            record.setStringValue(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_MONITORED_ADDRESSES_COLUMN_NAME, address.toString());

            /**
             * Add all the prepared records into a transaction
             */
            transaction.addRecordToInsert(databaseTable, record);
        }

        /**
         * execute the transaction
         */
        try {
            database.executeTransaction(transaction);
        } catch (DatabaseTransactionFailedException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Cant insert records.", null);
        }

    }

    /**
     * deletes all existing stats for this crypto vault and network type.
     * @param cryptoVault
     * @param blockchainNetworkType
     */
    public void deleteDetailedCryptoStats(CryptoVaults cryptoVault, BlockchainNetworkType blockchainNetworkType) throws CantExecuteDatabaseOperationException {
        /**
         * If we are not allowed to save detailed information then we will exit
         */
        if (!VaultKeyMaintenanceParameters.STORE_DETAILED_KEY_INFORMATION)
            return;

        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.CRYPTOVAULTS_DETAILED_STATS_CRYPTO_VAULT_COLUMN_NAME, cryptoVault.getCode(), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * delete all records. DB should offer a better way to do this.
         */
        for (DatabaseTableRecord record : databaseTable.getRecords()){
            try {
                databaseTable.deleteRecord(record);
            } catch (CantDeleteRecordException e) {
                throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to delete statst record.", "database issue");
            }
        }
    }

    /**
     * gets all stored TransactionProtocol data, both incoming and outgoing and with no ProtocolStatus filter.
     * @return
     */
    public List<TransactionProtocolData> getAllTransactionProtocolData() throws CantExecuteDatabaseOperationException{
        List<TransactionProtocolData> transactionProtocolDataList = getTransactionProtocolData(TransactionTypes.INCOMING, null);
        transactionProtocolDataList.addAll(getTransactionProtocolData(TransactionTypes.OUTGOING, null));
        return transactionProtocolDataList;
    }

    /**
     * stores a new Transaction in the database to be broadcasted later.
     * @param blockchainNetworkType
     * @param txHash
     * @param transactionId
     * @param peerCount
     * @param peerIp
     * @throws CantExecuteDatabaseOperationException
     */
    public void storeBroadcastBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, String txHash, UUID transactionId, int peerCount, String peerIp) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);

        /**
         * I will verify that I don't have this transaction already stored, if so I will delete it
         */
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * delete if already exists.
         */
        if (!databaseTable.getRecords().isEmpty()){
            this.deleteStoredBroadcastBitcoinTransaction(txHash);
        }

        /**
         * If everything is ok, then I will save this transaction in the table
         */
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_EXECUTION_NUMBER_COLUMN_NAME, getBroadcastTableExecutionNumber());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_NETWORK, blockchainNetworkType.getCode());
        record.setUUIDValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_TRANSACTION_ID, transactionId);
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH, txHash);
        record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_PEER_COUNT, peerCount);
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_PEER_BROADCAST_IP, peerIp);

        BroadcastStatus broadcastStatus = new BroadcastStatus(0, Status.IDLE);//this is a new transaction, so no retries
        record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_RETRIES_COUNT, broadcastStatus.getRetriesCount());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_STATUS, broadcastStatus.getStatus().getCode());
        record.setLongValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_LAST_EXECUTION_DATE_COLUMN_NAME, getCurrentDateTime());

        try {
            databaseTable.insertRecord(record);
        } catch (CantInsertRecordException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "There was an error inserting a new Transaction in Broadcast Table" , "database issue");
        }
    }

    /**
     * Gets the next execution number available for the Broadcast Table
     * @return
     */
    private Integer getBroadcastTableExecutionNumber() {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            return 1;
        }

        int currentExecutionNumber = databaseTable.getRecords().size();

        return currentExecutionNumber + 1;
    }

    /**
     * Will detele a previously stored transaction in the database, probably to rollback it.
     * T TransTio
     * @param txHash
     */
    public void deleteStoredBroadcastBitcoinTransaction(String txHash) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH, txHash, DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_STATUS, Status.IDLE.getCode(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        if (!databaseTable.getRecords().isEmpty()){
            DatabaseTableRecord record = databaseTable.getRecords().get(0);
            try {
                databaseTable.deleteRecord(record);
            } catch (CantDeleteRecordException e) {
                throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Error deleting a record from Broadcast table", "database issue");
            }
        }
    }

    /**
     * Verifies the passed Transaction exists in the Broadcast table
     * @param txHash
     * @return
     */
    public boolean transactionExistsInBroadcast(String txHash) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH, txHash, DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        return !databaseTable.getRecords().isEmpty();
    }


    /**
     * Gets the current Broadcast Status for the given Transaction
     * @param txHash
     * @return
     */
    public BroadcastStatus getBroadcastStatus(String txHash) throws CantExecuteDatabaseOperationException {
        /**
         * make sure it exsists.
         */
        if (!transactionExistsInBroadcast(txHash)){
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, null, "the given transaction " + txHash + " does not exists.", null);
        }

        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH, txHash, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * If I have multiple status for this transaction, I will return the last.
         */
        DatabaseTableRecord record = null;
        if (databaseTable.getRecords().size() != 1){
            for (DatabaseTableRecord broadcastRecord : databaseTable.getRecords()){
                record = broadcastRecord;
            }
        } else
            record = databaseTable.getRecords().get(0);

        /**
         * Forms the Broadcast Status and return it.
         */
        BroadcastStatus broadcastStatus = new BroadcastStatus();
        broadcastStatus.setRetriesCount(record.getIntegerValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_RETRIES_COUNT));
        try {
            broadcastStatus.setStatus(Status.getByCode(record.getStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_STATUS)));
        } catch (InvalidParameterException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "The stored Broadcast Status is not valid.", null);
        }

        /**
         * I will get the stored exception, if any.
         */
        try{
            String xmlException = record.getStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_EXCEPTION);
            if (!StringUtils.isBlank(xmlException)){
                Exception broadcastException = null;
                broadcastException = (Exception) XMLParser.parseXML(xmlException, new Exception());
                broadcastStatus.setLastException(broadcastException);
            }
        } catch (Exception e){
            /**
             * If there was an error parsing the exception, I will create my own exception.
             */
            CantBroadcastTransactionException cantBroadcastTransactionException = new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, null, "Couln't parse the correct exception", null);
            broadcastStatus.setLastException(cantBroadcastTransactionException);
        }

        broadcastStatus.setConnectedPeers(record.getIntegerValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_PEER_COUNT));
        return broadcastStatus;
    }

    /**
     * Sets the passed broadcast status to the specified transaction hash
     * @param status
     * @param connectedPeers
     * @param lastException
     * @param txHash
     * @throws CantExecuteDatabaseOperationException
     */
    public void setBroadcastStatus(Status status, @Nullable int connectedPeers, @Nullable Exception lastException, String txHash) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH, txHash, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * If I have multiple records, then I will update the last
         */
        DatabaseTableRecord record = null;
        if (databaseTable.getRecords().size() != 1){
            for (DatabaseTableRecord broadcastRecord : databaseTable.getRecords()){
                record = broadcastRecord;
            }
        } else
            record = databaseTable.getRecords().get(0);

        /**
         * I will get the current amount of retries.
         */
        int retriesAmount;
        if (record.getIntegerValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_RETRIES_COUNT) == null)
            retriesAmount = 0;
        else
            retriesAmount = record.getIntegerValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_RETRIES_COUNT);

        /**
         * if the passed Status is Broadcasting or With_Error, then I will increase the retry count and update the status
         */
        BroadcastStatus broadcastStatus = new BroadcastStatus();
        broadcastStatus.setStatus(status);
        if (status == Status.BROADCASTING || status == Status.WITH_ERROR)
            broadcastStatus.setRetriesCount(retriesAmount+1);
        else
            broadcastStatus.setRetriesCount(retriesAmount);

        broadcastStatus.setConnectedPeers(connectedPeers);
        broadcastStatus.setLastException(lastException);

        /**
         * I will set the new values and execute
         */

        record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_RETRIES_COUNT, broadcastStatus.getRetriesCount());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_STATUS, broadcastStatus.getStatus().getCode());
        record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_PEER_COUNT, broadcastStatus.getConnectedPeers());

        /**
         * I will set the exception column if any.
         */
        if (broadcastStatus.getLastException() != null){
            try{
                String xmlException = XMLParser.parseObject(broadcastStatus.getLastException());
                record.setStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_EXCEPTION, xmlException);
            } catch (Exception e){
                /**
                 * If there was an error parsing the exception, I will create a new exception
                 */
                CantBroadcastTransactionException cantBroadcastTransactionException = new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, null, "Couln't parse the correct exception", null);
                record.setStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_EXCEPTION, XMLParser.parseObject(cantBroadcastTransactionException));
            }
        }

        /**
         * sets the last update column data
         */
        record.setLongValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_LAST_EXECUTION_DATE_COLUMN_NAME, getCurrentDateTime());

        /**
         * finally, update the record.
         */
        try {
            databaseTable.updateRecord(record);
        } catch (CantUpdateRecordException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Unable to update record in Broadcast table.", "database issue");
        }
    }

    /**
     * Will get all the Transaction hash of broadcasting table for the specified status
     * @param status
     * @return
     */
    public List<String> getBroadcastTransactionsByStatus(BlockchainNetworkType blockchainNetworkType, Status status) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);

        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_NETWORK, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_STATUS, status.getCode(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        List<String> txHashes = new ArrayList<>();
        for (DatabaseTableRecord record : databaseTable.getRecords()){
            txHashes.add(record.getStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH));
        }

        return txHashes;
    }

    /**
     * Gets the Transaction Id for the specified transaction passed
     * @param blockchainNetworkType
     * @param txHash
     * @return
     */
    public UUID getBroadcastedTransactionId(BlockchainNetworkType blockchainNetworkType, String txHash) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);

        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_NETWORK, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH, txHash, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * I might have multiple txHash, so I will get the last one.
         * This may need to be corrected at some point
         */
        DatabaseTableRecord record = null;
        for (DatabaseTableRecord uuidRecord : databaseTable.getRecords()){
            record = uuidRecord;
        }

        if (record != null)
            return record.getUUIDValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME);
        else
            return null;
    }

    /**
     * Gets the last CryptoTransaction that we have locally.
     * @param txHash
     * @return
     */
    public CryptoTransaction getCryptoTransaction(String txHash, @Nullable CryptoTransactionType cryptoTransactionType, @Nullable CryptoAddress toAddress) throws CantExecuteDatabaseOperationException{
        /**
         * I get the list of stored cryptoTransactions with all their crypto Status
         */
        List<CryptoTransaction> cryptoTransactions = this.getCryptoTransactions(txHash, cryptoTransactionType, toAddress);

        /**
         * I get the last available crypto Status
         */
        CryptoStatus lastCryptoStatus = null;
        for (CryptoTransaction cryptoTransaction: cryptoTransactions){
            CryptoStatus cryptoStatus = cryptoTransaction.getCryptoStatus();

            if (lastCryptoStatus == null)
                lastCryptoStatus = cryptoStatus;
            else if (lastCryptoStatus.getOrder() < cryptoStatus.getOrder())
                lastCryptoStatus = cryptoStatus;
        }

        /**
         * I return the CryptoTranasction with the last cryptoStatus that are outgoing first.
         */
        for (CryptoTransaction cryptoTransaction: cryptoTransactions){
            if (cryptoTransaction.getCryptoStatus() == lastCryptoStatus)
                return cryptoTransaction;
        }

        //this might happen if the transaction has not been broadcasted yet.
        return null;
    }

    /**
     * Will get the BlockchainNetworkType the passed trasaction was registered from
     * @param txHash
     * @return
     */
    public BlockchainNetworkType getBlockchainNetworkTypeFromBroadcast(String txHash) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.BROADCAST_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.BROADCAST_TX_HASH, txHash, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        if (databaseTable.getRecords().size() != 1)
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, null, "unexpected result obtained. The passed tx is not broadcasting.", null);

        BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(databaseTable.getRecords().get(0).getStringValue(FermatCryptoNetworkDatabaseConstants.BROADCAST_NETWORK));
        return blockchainNetworkType;
    }

    /**
     * Updates the ActiveNetwork table with the active network information and the monitored keys.
     * @param blockchainNetworkType
     * @param amountOfKeys amount of keys we are monitoring on the passed network
     * @throws CantExecuteDatabaseOperationException
     */
    public synchronized  void updateActiveNetworks (BlockchainNetworkType blockchainNetworkType, int amountOfKeys) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_TABLE_NAME);

        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_NETWORKTYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        DatabaseTableRecord record = null;
        /**
         * If no previous record exists, I will insert a new one
         */
        if (databaseTable.getRecords().isEmpty()){
            record = getNewActiveNetworkRecord(blockchainNetworkType, amountOfKeys);
            try {
                databaseTable.insertRecord(record);
            } catch (CantInsertRecordException e) {
                throw new CantExecuteDatabaseOperationException("There was an error inserting a new ActiveNetwork record.", e, "updateActiveNetworks method", "Database Error");
            }
        } else {
            /**
             * or update the existing one with the data.
             */
            record = databaseTable.getRecords().get(0);
            record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_KEYS, amountOfKeys);
            record.setLongValue(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_LAST_UPDATE, getCurrentDateTime());
            try {
                databaseTable.updateRecord(record);
            } catch (CantUpdateRecordException e) {
                throw new CantExecuteDatabaseOperationException("There was an error updating an existing ActiveNetwork record.", e, "updateActiveNetworks method", "Database Error");
            }
        }
    }

    private DatabaseTableRecord getNewActiveNetworkRecord(BlockchainNetworkType blockchainNetworkType, int amountOfKeys) {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_TABLE_NAME);

        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_NETWORKTYPE, blockchainNetworkType.getCode());
        record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_KEYS, amountOfKeys);
        record.setLongValue(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_LAST_UPDATE, getCurrentDateTime());
        return record;
    }

    /**
     * Gets the active network types that we are monitoring
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public List<BlockchainNetworkType> getActiveBlockchainNetworkTypes() throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_TABLE_NAME);
        List<BlockchainNetworkType> blockchainNetworkTypes = new ArrayList<>();

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        for (DatabaseTableRecord record : databaseTable.getRecords()){
            blockchainNetworkTypes.add(BlockchainNetworkType.getByCode(record.getStringValue(FermatCryptoNetworkDatabaseConstants.ACTIVENETWORKS_NETWORKTYPE)));
        }

        return blockchainNetworkTypes;

    }

    /**
     * Saves the passed CryptoTransaction into the database
     * @param cryptoTransaction
     */
    public void saveCryptoTransaction(CryptoTransaction cryptoTransaction, @Nullable UUID transactionId) throws CantExecuteDatabaseOperationException {
        /**
         * if not passed, I will define my own transactionId
         */
        if (transactionId == null)
            transactionId = UUID.randomUUID();


        saveNewTransaction(cryptoTransaction, transactionId,calculateProtocolStatus(cryptoTransaction));
    }

    /**
     * Gets the list of CryptoTransactions stored for the specified network
     * @param blockchainNetworkType
     * @return
     */
    public List<CryptoTransaction> getCryptoTransactions(BlockchainNetworkType blockchainNetworkType, CryptoAddress addressTo, @Nullable CryptoTransactionType cryptoTransactionType) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCKCHAIN_NETWORK_TYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME, addressTo.getAddress(), DatabaseFilterType.EQUAL);

        if (cryptoTransactionType != null)
            databaseTable.addStringFilter(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, cryptoTransactionType.getCode(), DatabaseFilterType.EQUAL);

        List<CryptoTransaction> cryptoTransactions = new ArrayList<>();

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        for (DatabaseTableRecord record : databaseTable.getRecords()){
            CryptoTransaction cryptoTransaction = getCryptoTransactionFromRecord(record);
            cryptoTransactions.add(cryptoTransaction);
        }
        return cryptoTransactions;
    }

    /**
     * transform a CryptoTransaction into a Database Record
     * @param cryptoTransaction
     * @return
     */
    private DatabaseTableRecord getRecordFromCryptoTransaction(UUID transactionID, ProtocolStatus protocolStatus, CryptoTransaction cryptoTransaction){
        DatabaseTable databaseTable = database.getTable(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TRX_ID_COLUMN_NAME, transactionID);
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_HASH_COLUMN_NAME, cryptoTransaction.getTransactionHash());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_HASH_COLUMN_NAME, cryptoTransaction.getBlockHash());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCKCHAIN_NETWORK_TYPE, cryptoTransaction.getBlockchainNetworkType().getCode());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME, cryptoTransaction.getCryptoStatus().getCode());
        record.setIntegerValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BLOCK_DEPTH_COLUMN_NAME, cryptoTransaction.getBlockDepth());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_TO_COLUMN_NAME, cryptoTransaction.getAddressTo().getAddress());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME, cryptoTransaction.getAddressFrom().getAddress());
        record.setLongValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_BTC_AMOUNT_COLUMN_NAME, cryptoTransaction.getBtcAmount());
        record.setLongValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_FEE_AMOUNT_COLUMN_NAME, cryptoTransaction.getFee());
        record.setLongValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_CRYPTO_AMOUNT_COLUMN_NAME, cryptoTransaction.getCryptoAmount());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_OP_RETURN_COLUMN_NAME, cryptoTransaction.getOp_Return());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME, protocolStatus.getCode());
        record.setLongValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_LAST_UPDATE_COLUMN_NAME, getCurrentDateTime());
        record.setStringValue(FermatCryptoNetworkDatabaseConstants.TRANSACTIONS_TYPE_COLUMN_NAME, cryptoTransaction.getCryptoTransactionType().getCode());

        return record;
    }

    private ProtocolStatus calculateProtocolStatus(CryptoTransaction cryptoTransaction){
        if (cryptoTransaction.getCryptoTransactionType() == CryptoTransactionType.OUTGOING)
            return ProtocolStatus.NO_ACTION_REQUIRED;

        if (cryptoTransaction.getCryptoStatus() == CryptoStatus.PENDING_SUBMIT)
            return ProtocolStatus.NO_ACTION_REQUIRED;
        // for every other case, we are returning TO_BE_NOTIFIED
        return ProtocolStatus.TO_BE_NOTIFIED;
    }
}
