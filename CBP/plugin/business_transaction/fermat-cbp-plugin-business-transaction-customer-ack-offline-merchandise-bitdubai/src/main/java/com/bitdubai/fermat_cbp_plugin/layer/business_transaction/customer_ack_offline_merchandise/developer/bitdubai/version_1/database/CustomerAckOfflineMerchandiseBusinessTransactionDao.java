package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.CustomerAckOfflineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeCustomerAckOfflineMerchandiseBusinessTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/12/15.
 */
public class CustomerAckOfflineMerchandiseBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private CustomerAckOfflineMerchandisePluginRoot pluginRoot;
    private Database database;

    public CustomerAckOfflineMerchandiseBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final CustomerAckOfflineMerchandisePluginRoot pluginRoot) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
        this.pluginRoot = pluginRoot;
    }

    public void initialize() throws CantInitializeCustomerAckOfflineMerchandiseBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseFactory databaseFactory =
                        new CustomerAckOfflineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, f);
                throw new CantInitializeCustomerAckOfflineMerchandiseBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, z);
                throw new CantInitializeCustomerAckOfflineMerchandiseBusinessTransactionDatabaseException(
                        CantInitializeCustomerAckOfflineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerAckOfflineMerchandiseBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and I cannot open the database.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerAckOfflineMerchandiseBusinessTransactionDatabaseException(
                    CantInitializeCustomerAckOfflineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Generic Exception.");
        }
    }

    /**
     * Returns the Database
     *
     * @return Database
     */
    private Database getDataBase() {
        return database;
    }

    /**
     * Returns the Ack Offline Merchandise DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getAckMerchandiseTable() {
        return getDataBase().getTable(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_TABLE_NAME);
    }

    /**
     * Returns the Ack Offline Merchandise Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME);
    }


    /**
     * This method save an incoming new event in database. You can set the event Id with this method
     *
     * @param eventType
     * @param eventSource
     * @param eventId
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource, String eventId) throws CantSaveEventException {
        try {
            if (isContractHashInDatabase(eventId)) {
                return;
            }
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            long unixTime = System.currentTimeMillis();
            eventRecord.setStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Ack Online Payment database");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method save an incoming new event in database.
     *
     * @param eventType
     * @param eventSource
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            UUID eventRecordID = UUID.randomUUID();
            saveNewEvent(eventType, eventSource, eventRecordID.toString());

        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns the contract transaction status
     *
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {

            String stringContractTransactionStatus = getValue(
                    contractHash,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        } catch (Exception exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting the contract transaction status",
                    "Unexpected error");
        }
    }

    /**
     * This method returns a String value from parameters in database.
     *
     * @param key
     * @param keyColumn
     * @param valueColumn
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private String getValue(String key,
                            String keyColumn,
                            String valueColumn)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            databaseTable.addStringFilter(
                    keyColumn,
                    key,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty()) {
                return null;
            }
            checkDatabaseRecords(records);
            String value = records
                    .get(0)
                    .getStringValue(valueColumn);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    /**
     * This method check the database record result.
     *
     * @param records
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws
            UnexpectedResultReturnedFromDatabaseException {
        /**
         * Represents the maximum number of records in <code>records</code>
         * I'm gonna set this number in 1 for now, because I want to check the records object has
         * one only result.
         */
        int VALID_RESULTS_NUMBER = 1;
        int recordsSize;
        if (records.isEmpty()) {
            return;
        }
        recordsSize = records.size();
        if (recordsSize > VALID_RESULTS_NUMBER) {
            throw new UnexpectedResultReturnedFromDatabaseException(new StringBuilder().append("I excepted ").append(VALID_RESULTS_NUMBER).append(", but I got ").append(recordsSize).toString());
        }
    }

    /**
     * This method returns TRUE if the contract is persisted in database.
     *
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public boolean isContractHashInDatabase(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            String contractHashFromDatabase = getValue(
                    contractHash,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
            return contractHashFromDatabase != null;
        } catch (Exception exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param customerBrokerContractPurchase
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractPurchase customerBrokerContractPurchase)
            throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(customerBrokerContractPurchase.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(customerBrokerContractPurchase).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getAckMerchandiseTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractPurchase
            );
            databaseTable.insertRecord(databaseTableRecord);
        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,
                    exception, "Error in persistContractInDatabase", "");
        } catch (Exception exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method creates a database table record in crypto broker side, only for backup
     *
     * @param record
     * @param customerBrokerContractPurchase
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractPurchase customerBrokerContractPurchase) {
        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_OFFLINE_MERCHANDISE_NOTIFICATION.getCode());

        return record;
    }

    /**
     * This method updates the ContractTransactionStatus by a contractHash
     *
     * @param contractHash
     * @param contractTransactionStatus
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateContractTransactionStatus(String contractHash, ContractTransactionStatus contractTransactionStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {

            ObjectChecker.checkArgument(contractHash);

            DatabaseTable databaseTable = getAckMerchandiseTable();

            databaseTable.addStringFilter(
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);

            record.setStringValue(
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    contractTransactionStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected results in database");
        } catch (Exception exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Getting value from database",
                    "Unexpected results in database");
        }

    }

    /**
     * This method update a database record by contract hash.
     *
     * @param contractHash
     * @param statusColumnName
     * @param newStatus
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    private void updateRecordStatus(String contractHash,
                                    String statusColumnName,
                                    String newStatus) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {

        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            databaseTable.addStringFilter(
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(statusColumnName, newStatus);
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, new StringBuilder().append("Updating parameter ").append(statusColumnName).toString(), "");
        }
    }

    /**
     * This method returns the pending to submit notifications list
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getBusinessTransactionRecordList(
                    ContractTransactionStatus.PENDING_OFFLINE_MERCHANDISE_NOTIFICATION.getCode(),
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingTosSubmitNotificationList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns the pending to submit notifications list
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitConfirmList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getBusinessTransactionRecordList(
                    ContractTransactionStatus.PENDING_ACK_OFFLINE_MERCHANDISE.getCode(),
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingTosSubmitNotificationList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns a CustomerOnlinePaymentRecordList according the arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return List<BusinessTransactionRecord>
     * @throws CantGetContractListException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private List<BusinessTransactionRecord> getBusinessTransactionRecordList(
            String key,
            String keyColumn,
            String valueColumn) throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException {
        List<String> pendingContractHash = getStringList(
                key,
                keyColumn,
                valueColumn);
        List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();
        BusinessTransactionRecord businessTransactionRecord;
        for (String contractHash : pendingContractHash) {
            businessTransactionRecord = getBusinessTransactionRecordByContractHash(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
    }

    /**
     * This method returns a List with the parameter in the arguments.
     *
     * @param key
     * @param keyColumn
     * @param valueColumn
     * @return
     */
    private List<String> getStringList(
            String key,
            String keyColumn,
            String valueColumn) throws CantGetContractListException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            List<String> contractHashList = new ArrayList<>();
            String contractHash;
            databaseTable.addStringFilter(
                    keyColumn,
                    key,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty()) {
                //There is no records in database, I'll return an empty list.
                return contractHashList;
            }
            for (DatabaseTableRecord databaseTableRecord : records) {
                contractHash = databaseTableRecord.getStringValue(valueColumn);
                contractHashList.add(contractHash);
            }
            return contractHashList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e,
                    new StringBuilder().append("Getting ").append(valueColumn).append(" based on ").append(key).toString(),
                    "Cannot load the table into memory");
        }
    }

    /**
     * This method returns the business transaction record by a contract hash
     *
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBusinessTransactionRecordByContractHash(
            String contractHash)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            return getBusinessTransactionRecord(
                    contractHash,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        } catch (Exception e) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Unexpected Result", "Check the cause");
        }

    }

    /**
     * This method returns a BusinessTransactionRecord by the parameters given.
     *
     * @param keyValue
     * @param keyColumn
     * @return
     */
    private BusinessTransactionRecord getBusinessTransactionRecord(
            String keyValue,
            String keyColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            ContractTransactionStatus contractTransactionStatus;
            long paymentAmount;
            MoneyType paymentType;
            FiatCurrency currencyType;
            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(
                    keyColumn,
                    keyValue,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            if (records.isEmpty()) {
                return null;
            }
            DatabaseTableRecord record = records.get(0);

            businessTransactionRecord.setTransactionId(record.getStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));

            businessTransactionRecord.setContractHash(record.getStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));

            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));

            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));

            contractTransactionStatus = ContractTransactionStatus.getByCode(record.getStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);

            businessTransactionRecord.setTransactionHash(keyValue);

            return businessTransactionRecord;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Invalid parameter in ContractTransactionStatus");
        }
    }

    /**
     * This method returns the recorded pending events
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            return getPendingGenericsEvents(
                    databaseTable,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME
            );
        } catch (CantGetContractListException e) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot Get the table");
        } catch (Exception exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting events in EventStatus.PENDING\"",
                    "Unexpected error");
        }
    }

    /**
     * This method returns pending generic events by given parameters
     *
     * @return
     */
    private List<String> getPendingGenericsEvents(
            DatabaseTable databaseTable,
            String statusColumn,
            String idColumn) throws
            CantGetContractListException {
        try {
            List<String> eventTypeList = new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(
                    statusColumn,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty()) {
                //There is no records in database, I'll return an empty list.
                return eventTypeList;
            }
            for (DatabaseTableRecord databaseTableRecord : records) {
                eventId = databaseTableRecord.getStringValue(
                        idColumn);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e,
                    new StringBuilder().append("Getting events in EventStatus.PENDING in table ").append(databaseTable.getTableName()).toString(),
                    "Cannot load the table into memory");
        }
    }

    /**
     * This method returns the event type by event Id
     *
     * @param eventId
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public String getEventType(String eventId)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            return records.get(0).getStringValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting value from database",
                    "Unexpected error");
        }

    }

    /**
     * This method updates the event status by an eventId
     *
     * @param eventId
     * @param eventStatus
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateEventStatus(
            String eventId,
            EventStatus eventStatus) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            //checkDatabaseRecords(records);
            if (records == null || records.isEmpty()) {
                return;
            }
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    new StringBuilder().append("Updating parameter ").append(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME).toString(), "");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param customerBrokerContractSale
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale)
            throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(customerBrokerContractSale.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(customerBrokerContractSale).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getAckMerchandiseTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractSale
            );
            databaseTable.insertRecord(databaseTableRecord);
        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Error in persistContractInDatabase", "");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method creates a database table record in crypto broker side, only for backup
     *
     * @param record
     * @param customerBrokerContractSale
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractSale customerBrokerContractSale) {
        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.ACK_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ACK_OFFLINE_MERCHANDISE.getCode());

        return record;
    }

    /**
     * This method returns the completion date from database.
     *
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public long getCompletionDateByContractHash(
            String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            databaseTable.addStringFilter(
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty()) {
                return 0;
            }
            checkDatabaseRecords(records);
            long completionDate = records
                    .get(0)
                    .getLongValue(CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME);
            return completionDate;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting completion date from database",
                    "Cannot load the database table");
        }
    }

    /**
     * This method sets the completion date in the database.
     *
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public void setCompletionDateByContractHash(
            String contractHash,
            long completionDate)
            throws UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            databaseTable.addStringFilter(
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty()) {
                return;
            }
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setLongValue(
                    CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME,
                    completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Setting completion date from database",
                    "Cannot load the database table");
        }
    }

}