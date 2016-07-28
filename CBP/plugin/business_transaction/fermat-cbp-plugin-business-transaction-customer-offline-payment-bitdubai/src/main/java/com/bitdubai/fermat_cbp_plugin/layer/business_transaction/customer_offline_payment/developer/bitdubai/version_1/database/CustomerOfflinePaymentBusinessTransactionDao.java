package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.exceptions.CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure.CustomerOfflinePaymentRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerOfflinePaymentBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private CustomerOfflinePaymentPluginRoot pluginRoot;
    private Database database;

    public CustomerOfflinePaymentBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final CustomerOfflinePaymentPluginRoot pluginRoot) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
        this.pluginRoot = pluginRoot;
    }

    public void initialize() throws CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CustomerOfflinePaymentBusinessTransactionDatabaseFactory databaseFactory =
                        new CustomerOfflinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        CustomerOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, f);
                throw new CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, z);
                throw new CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException(
                        CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException(
                    CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
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
     * Returns the Open Contract DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseContractTable() {
        return getDataBase().getTable(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TABLE_NAME);
    }

    /**
     * Returns the Open Contract Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME);
    }

    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {

            String stringContractTransactionStatus = getValue(
                    contractHash,
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting the contract transaction status",
                    "Unexpected error");
        }
    }

    public List<String> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            List<String> eventTypeList = new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
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
                        CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting events in EventStatus.PENDING\"",
                    "Unexpected error");
        }
    }

    public String getEventType(String eventId)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value = records
                    .get(0)
                    .getStringValue(
                            CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting events in EventStatus.PENDING\"",
                    "Unexpected error");
        }

    }

    /*public List<String> getPendingToSubmitCryptoList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getStringList(
                ContractTransactionStatus.PENDING_PAYMENT.getCode(),
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
    }

    /*public List<CustomerOfflinePaymentRecord> getPendingToSubmitCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getCustomerOnlinePaymentRecordList(
                CryptoStatus.PENDING_SUBMIT.getCode(),
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
    }*/

    public List<CustomerOfflinePaymentRecord> getPendingToSubmitNotificationList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getCustomerOfflinePaymentRecordList(
                    ContractTransactionStatus.PENDING_OFFLINE_PAYMENT_NOTIFICATION.getCode(),
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public List<CustomerOfflinePaymentRecord> getPendingToSubmitConfirmList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getCustomerOfflinePaymentRecordList(
                    ContractTransactionStatus.PENDING_OFFLINE_PAYMENT_CONFIRMATION.getCode(),
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /*public List<CustomerOfflinePaymentRecord> getOnCryptoNetworkCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getCustomerOnlinePaymentRecordList(
                CryptoStatus.ON_CRYPTO_NETWORK.getCode(),
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
    }*/

    /*public List<CustomerOfflinePaymentRecord> getOnBlockchainkCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getCustomerOnlinePaymentRecordList(
                CryptoStatus.ON_BLOCKCHAIN.getCode(),
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
    }*/

    /**
     * This method returns a CustomerOnlinePaymentRecordList according the arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return List<CustomerOfflinePaymentRecord>
     * @throws CantGetContractListException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private List<CustomerOfflinePaymentRecord> getCustomerOfflinePaymentRecordList(
            String key,
            String keyColumn,
            String valueColumn) throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException {
        List<String> pendingContractHash = getStringList(
                key,
                keyColumn,
                valueColumn);
        List<CustomerOfflinePaymentRecord> customerOfflinePaymentRecordList = new ArrayList<>();
        CustomerOfflinePaymentRecord customerOnlinePaymentRecord;
        for (String contractHash : pendingContractHash) {
            customerOnlinePaymentRecord = getCustomerOfflinePaymentRecord(contractHash);
            customerOfflinePaymentRecordList.add(customerOnlinePaymentRecord);
        }
        return customerOfflinePaymentRecordList;
    }

    /**
     * This method returns a CustomerOfflinePaymentRecord
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<CustomerOfflinePaymentRecord> getPendingCryptoTransactionList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getCustomerOfflinePaymentRecordList(
                    ContractTransactionStatus.OFFLINE_PAYMENT_SUBMITTED.getCode(),
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME
            );
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
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
            DatabaseTable databaseTable = getDatabaseContractTable();
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

    public boolean isContractHashInDatabase(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            String contractHashFromDatabase = getValue(
                    contractHash,
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
            return contractHashFromDatabase != null;
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
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
            DatabaseTable databaseTable = getDatabaseContractTable();
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
     * This method persists a basic record in database
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
            DatabaseTable databaseTable = getDatabaseContractTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractPurchase
            );
            databaseTable.insertRecord(databaseTableRecord);
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public CustomerOfflinePaymentRecord getCustomerOfflinePaymentRecord(String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {

        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            ContractTransactionStatus contractTransactionStatus;
            CustomerOfflinePaymentRecord customerOfflinePaymentRecord = new CustomerOfflinePaymentRecord();
            databaseTable.addStringFilter(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            customerOfflinePaymentRecord.setBrokerPublicKey(
                    record.getStringValue(
                            CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                                    OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME));
            customerOfflinePaymentRecord.setContractHash(record.getStringValue(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                            OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));
            contractTransactionStatus = ContractTransactionStatus.getByCode(record.getStringValue(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                            OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            customerOfflinePaymentRecord.setContractTransactionStatus(contractTransactionStatus);
            customerOfflinePaymentRecord.setCustomerPublicKey(
                    record.getStringValue(
                            CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                                    OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            customerOfflinePaymentRecord.setTransactionHash(contractHash);
            customerOfflinePaymentRecord.setTransactionId(
                    record.getStringValue(
                            CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                                    OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME));
            return customerOfflinePaymentRecord;
        } catch (CantLoadTableToMemoryException e) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (InvalidParameterException e) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Invalid parameter in ContractTransactionStatus");
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting value from database",
                    "Unexpected error");
        }

    }

    public void updateOnlinePaymentRecord(CustomerOfflinePaymentRecord customerOfflinePaymentRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            String contractHash = customerOfflinePaymentRecord.getContractHash();
            databaseTable.addStringFilter(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record = buildDatabaseTableRecord(record, customerOfflinePaymentRecord);
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating databaseTableRecord from a CustomerOfflinePaymentRecord",
                    "Unexpected results in database");
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected Error",
                    "Unexpected error");
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
            DatabaseTable databaseTable = getDatabaseContractTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractSale
            );
            databaseTable.insertRecord(databaseTableRecord);
        } catch (Exception exception) {
            this.pluginRoot.reportError(

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
     * @param customerBrokerContractSale
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractSale customerBrokerContractSale) {
        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_OFFLINE_PAYMENT_CONFIRMATION.getCode());

        return record;
    }

    /**
     * This method returns a complete database table record from a CustomerOfflinePaymentRecord
     *
     * @param record
     * @param customerOfflinePaymentRecord
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerOfflinePaymentRecord customerOfflinePaymentRecord) {
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerOfflinePaymentRecord.getBrokerPublicKey());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerOfflinePaymentRecord.getContractHash());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                customerOfflinePaymentRecord.getContractTransactionStatus().getCode());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerOfflinePaymentRecord.getCustomerPublicKey());
        record.setLongValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TIMESTAMP_COLUMN_NAME,
                customerOfflinePaymentRecord.getTimestamp());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME,
                customerOfflinePaymentRecord.getTransactionHash());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                customerOfflinePaymentRecord.getTransactionId());

        return record;
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractPurchase object.
     * This record is not complete, is missing the transaction hash,  and the crypto status,
     * this values will after sending the crypto amount, also the timestamp is set at this moment.
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
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_OFFLINE_PAYMENT_NOTIFICATION.getCode());
        return record;
    }

    /**
     * This method persists in an existing record in database the transaction UUID from
     * IntraActorCryptoTransactionManager by the contract hash.
     *
     * @param contractHash
     * @param cryptoTransactionUUID
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void persistsCryptoTransactionUUID(String contractHash,
                                              UUID cryptoTransactionUUID) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {

        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setUUIDValue(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                    cryptoTransactionUUID);
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Persisting crypto transaction in database",
                    "There was an unexpected result in database");
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Persisting crypto transaction in database",
                    "Unexpected error");
        }
    }

    public void updateContractTransactionStatus(String contractHash,
                                                ContractTransactionStatus contractTransactionStatus)
            throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try {
            updateRecordStatus(contractHash,
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    contractTransactionStatus.getCode());
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
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
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
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

    public void updateEventStatus(
            String eventId,
            EventStatus eventStatus) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    new StringBuilder().append("Updating parameter ").append(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME).toString(), "");
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
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
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            eventRecord.setUUIDValue(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);


        } catch (CantInsertRecordException exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Offline Payment database");
        } catch (Exception exception) {
            this.pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
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
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                            OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
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
                    .getLongValue(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                            OFFLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME);
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
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                            OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
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
                    CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                            OFFLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME,
                    completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Setting completion date from database",
                    "Cannot load the database table");
        }
    }

}
