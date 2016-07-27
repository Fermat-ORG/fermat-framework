package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.BrokerAckOfflinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.exceptions.CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class BrokerAckOfflinePaymentBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private BrokerAckOfflinePaymentPluginRoot pluginRoot;
    private Database database;

    public BrokerAckOfflinePaymentBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final BrokerAckOfflinePaymentPluginRoot pluginRoot) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
        this.pluginRoot = pluginRoot;
    }

    public void initialize() throws CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(this.pluginId,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {

            try {

                BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory databaseFactory =
                        new BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {
                pluginRoot.reportError(
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        f);
                throw new CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                pluginRoot.reportError(
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        z);
                throw new CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException(
                        CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException(
                    CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
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
     * Returns the Ack Offline Payment DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseContractTable() {
        return getDataBase().getTable(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_TABLE_NAME);
    }

    /**
     * Returns the Ack Offline Payment Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME);
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
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting the contract transaction status",
                    "Unexpected error");
        }
    }

    public boolean isContractHashInDatabase(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            String contractHashFromDatabase = getValue(
                    contractHash,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
            return contractHashFromDatabase != null;
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
            //TODO: fix all the isContractHash methods.
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
            eventRecord.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Ack Offline Payment database");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param customerBrokerContractSale
     * @param paymentType
     * @param fiatCurrency
     * @param actorPublicKey
     * @param customerAlias              @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale,
            MoneyType paymentType,
            FiatCurrency fiatCurrency,
            String actorPublicKey,
            String customerAlias)
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
                    customerBrokerContractSale,
                    paymentType,
                    fiatCurrency,
                    actorPublicKey,
                    customerAlias
            );
            databaseTable.insertRecord(databaseTableRecord);
        } catch (ObjectNotSetException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(
                    ObjectNotSetException.DEFAULT_MESSAGE,
                    exception,
                    "Persisting a contract in database",
                    "An argument in null");
        } catch (InvalidParameterException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(
                    ObjectNotSetException.DEFAULT_MESSAGE,
                    exception,
                    "Persisting a contract in database",
                    "The paymentType is invalid in this plugin");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }

    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param customerBrokerContractSale
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale,
            MoneyType paymentType,
            String actorPublicKey,
            ContractTransactionStatus contractTransactionStatus,
            FiatCurrency currency)
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
                    customerBrokerContractSale,
                    paymentType,
                    actorPublicKey,
                    contractTransactionStatus,
                    currency
            );
            databaseTable.insertRecord(databaseTableRecord);
        } catch (ObjectNotSetException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(
                    ObjectNotSetException.DEFAULT_MESSAGE,
                    exception,
                    "Persisting a contract in database",
                    "An argument in null");
        } catch (InvalidParameterException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(
                    ObjectNotSetException.DEFAULT_MESSAGE,
                    exception,
                    "Persisting a contract in database",
                    "The paymentType is invalid in this plugin");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }

    }

    /**
     * This method creates a database table record in crypto broker side.
     *
     * @param record
     * @param customerBrokerContractSale
     * @param paymentType
     * @param fiatCurrency
     * @param actorPublicKey
     * @param customerAlias              @return
     * @throws ObjectNotSetException
     * @throws InvalidParameterException
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractSale customerBrokerContractSale,
            MoneyType paymentType,
            FiatCurrency fiatCurrency,
            String actorPublicKey,
            String customerAlias) throws ObjectNotSetException, InvalidParameterException {

        ObjectChecker.checkArgument(customerBrokerContractSale, "The customerBrokerContractSale in buildDatabaseTableRecord method is null");

        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);

        //For the business transaction this value represents the contract hash.
        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());

        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());

        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());

        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_ACTOR_PUBLIC_KEY_COLUMN_NAME,
                actorPublicKey);

        switch (paymentType) {
            case BANK:
                record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                        ContractTransactionStatus.PENDING_CREDIT_BANK_WALLET.getCode());
                break;
            case CASH_DELIVERY:
                record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                        ContractTransactionStatus.PENDING_CREDIT_CASH_WALLET.getCode());
                break;
            case CASH_ON_HAND:
                record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                        ContractTransactionStatus.PENDING_CREDIT_CASH_WALLET.getCode());
                break;
            default:
                throw new InvalidParameterException(new StringBuilder().append(paymentType).append(" value from MoneyType is not valid in this plugin").toString());
        }

        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_PAYMENT_TYPE_COLUMN_NAME, paymentType.getCode());

        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CURRENCY_TYPE_COLUMN_NAME, fiatCurrency.getCode());

        //Sets the customerAlias
        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CUSTOMER_ALIAS_COLUMN_NAME, customerAlias);
        return record;
    }

    /**
     * * This method creates a database table record in crypto broker side, only for backup
     *
     * @param record
     * @param customerBrokerContractSale
     * @param paymentType
     * @param actorPublicKey
     * @param contractTransactionStatus
     * @param currency
     * @return
     * @throws ObjectNotSetException
     * @throws InvalidParameterException
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractSale customerBrokerContractSale,
            MoneyType paymentType,
            String actorPublicKey,
            ContractTransactionStatus contractTransactionStatus,
            FiatCurrency currency) throws
            ObjectNotSetException,
            InvalidParameterException {

        ObjectChecker.checkArgument(
                customerBrokerContractSale,
                "The customerBrokerContractSale in buildDatabaseTableRecord method is null");
        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_ACTOR_PUBLIC_KEY_COLUMN_NAME,
                actorPublicKey);
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                contractTransactionStatus.getCode());
        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_PAYMENT_TYPE_COLUMN_NAME,
                paymentType.getCode());
        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_CURRENCY_TYPE_COLUMN_NAME,
                currency.getCode());

        return record;
    }

    /**
     * return a {@link BusinessTransactionRecord} with the data persisted in the broker device
     *
     * @param contractHash the contract hash that identify the Record
     * @return a {@link BusinessTransactionRecord} with the data
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBrokerBusinessTransactionRecordByContractHash(String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        try {
            return getBrokerBusinessTransactionRecord(
                    contractHash,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * return a {@link BusinessTransactionRecord} with the data persisted in the customer device
     *
     * @param contractHash the contract hash that identify the Record
     * @return a {@link BusinessTransactionRecord} with the data
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getCustomerBusinessTransactionRecordByContractHash(String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        try {
            return getCustomerBusinessTransactionRecord(contractHash, BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns a BusinessTransactionRecord by the parameters given.
     *
     * @param keyValue
     * @param keyColumn
     * @return
     */
    private BusinessTransactionRecord getBrokerBusinessTransactionRecord(String keyValue, String keyColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            ContractTransactionStatus contractTransactionStatus;
            long paymentAmount;
            MoneyType paymentType;
            FiatCurrency fiatCurrency;
            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(keyColumn, keyValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            if (records.isEmpty())
                return null;

            DatabaseTableRecord record = records.get(0);

            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME));

            businessTransactionRecord.setContractHash(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));

            businessTransactionRecord.setTransactionHash(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));

            String contractStatusStringValue = record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            contractTransactionStatus = ContractTransactionStatus.getByCode(contractStatusStringValue);
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);

            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));

            businessTransactionRecord.setTransactionId(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME));

            paymentAmount = record.getLongValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_PAYMENT_AMOUNT_COLUMN_NAME);
            businessTransactionRecord.setPaymentAmount(paymentAmount);

            String paymentTypeString = record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_PAYMENT_TYPE_COLUMN_NAME);
            if (paymentTypeString == null || paymentTypeString.isEmpty())
                throw new InvalidParameterException("The paymentType is null");
            paymentType = MoneyType.getByCode(paymentTypeString);
            businessTransactionRecord.setPaymentType(paymentType);

            String fiatCurrencyCode = record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CURRENCY_TYPE_COLUMN_NAME);
            if (fiatCurrencyCode == null || fiatCurrencyCode.isEmpty())
                throw new InvalidParameterException("The fiatCurrency is null");
            fiatCurrency = FiatCurrency.getByCode(fiatCurrencyCode);
            businessTransactionRecord.setFiatCurrency(fiatCurrency);

            String cbpWalletPublicKey = record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME);
            businessTransactionRecord.setCBPWalletPublicKey(cbpWalletPublicKey);

            String customerAlias = record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CUSTOMER_ALIAS_COLUMN_NAME);
            if (customerAlias == null || customerAlias.isEmpty())
                customerAlias = "Unregistered customer";
            businessTransactionRecord.setCustomerAlias(customerAlias);

            return businessTransactionRecord;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Invalid parameter in ContractTransactionStatus");
        }
    }

    private BusinessTransactionRecord getCustomerBusinessTransactionRecord(String keyValue, String keyColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(keyColumn, keyValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            if (records.isEmpty())
                return null;

            DatabaseTableRecord record = records.get(0);

            businessTransactionRecord.setTransactionId(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME));

            businessTransactionRecord.setContractHash(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));

            businessTransactionRecord.setTransactionHash(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));

            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));

            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME));

            String contractStatusStringValue = record.getStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                    ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            businessTransactionRecord.setContractTransactionStatus(ContractTransactionStatus.getByCode(contractStatusStringValue));

            return businessTransactionRecord;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Invalid parameter in ContractTransactionStatus");
        }
    }

    /**
     * This method updates the ContractTransactionStatus by a contractHash
     *
     * @param contractHash
     * @param contractTransactionStatus
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateContractTransactionStatus(String contractHash,
                                                ContractTransactionStatus contractTransactionStatus)
            throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try {
            ObjectChecker.checkArgument(contractHash);
            updateRecordStatus(contractHash,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    contractTransactionStatus.getCode());
        } catch (ObjectNotSetException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantUpdateRecordException(
                    ObjectNotSetException.DEFAULT_MESSAGE,
                    exception,
                    "Updating the contract transaction status",
                    "The contract hash/Id is null");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }

    }

    public void updateBusinessTransactionRecord(
            BusinessTransactionRecord businessTransactionRecord)
            throws CantUpdateRecordException {
        String contractHash = "emptyContractHash";
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            contractHash = businessTransactionRecord.getContractHash();
            databaseTable.addStringFilter(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record = buildDatabaseTableRecord(
                    businessTransactionRecord,
                    record);
            databaseTable.updateRecord(record);
        } catch (UnexpectedResultReturnedFromDatabaseException | CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantUpdateRecordException(
                    new StringBuilder().append("Updating BusinessTransactionRecord with contractHash ").append(contractHash).toString(),
                    exception
            );
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE, exception,
                    new StringBuilder().append("Updating BusinessTransactionRecord with contractHash ").append(contractHash).toString(), "Unexpected error");
        }
    }

    private DatabaseTableRecord buildDatabaseTableRecord(
            BusinessTransactionRecord businessTransactionRecord, DatabaseTableRecord record) {
        //Set contractHash
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                businessTransactionRecord.getContractHash());
        //Set customerPublicKey
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getCustomerPublicKey());
        //Set BrokerPublicKey
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getBrokerPublicKey());
        //Set the transactionId
        String transactionIdString = businessTransactionRecord.getTransactionId();
        UUID transactionId;
        /**
         * This, in theory, can't happen, but, I'll check, just in case
         */
        if (transactionIdString == null || transactionIdString.isEmpty()) {
            transactionId = UUID.randomUUID();
        } else {
            transactionId = UUID.fromString(transactionIdString);
        }
        record.setUUIDValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        //Set the transactionHash
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME,
                businessTransactionRecord.getTransactionHash());
        //Set the contractTransactionStatus
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                businessTransactionRecord.getContractTransactionStatus().getCode());
        //Set the timestamp
        record.setLongValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_TIMESTAMP_COLUMN_NAME,
                businessTransactionRecord.getTimestamp());
        //Set the paymentAmount
        record.setLongValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_PAYMENT_AMOUNT_COLUMN_NAME,
                businessTransactionRecord.getPaymentAmount());
        //Set the payment Type
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_PAYMENT_TYPE_COLUMN_NAME,
                businessTransactionRecord.getPaymentType().getCode());
        //Set the currency type
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_CURRENCY_TYPE_COLUMN_NAME,
                businessTransactionRecord.getFiatCurrency().getCode());
        //Set the external Id
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_EXTERNAL_TRANSACTION_ID_COLUMN_NAME,
                businessTransactionRecord.getTransactionId());
        //Set the actor Public key
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_ACTOR_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getActorPublicKey());
        return record;
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
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(statusColumnName, newStatus);
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    new StringBuilder().append("Updating parameter ").append(statusColumnName).toString(),
                    "");
        }
    }

    /**
     * This method update a database record, the payment type field, by contract hash.
     *
     * @param contractHash
     * @param paymentType
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateRecordPaymentTypeByContractHash(
            String contractHash,
            MoneyType paymentType) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {

        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_PAYMENT_TYPE_COLUMN_NAME, paymentType.getCode());
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter ACK_OFFLINE_PAYMENT_PAYMENT_TYPE_COLUMN_NAME",
                    "");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter ACK_OFFLINE_PAYMENT_PAYMENT_TYPE_COLUMN_NAME",
                    "Unexpected Result");
        }
    }

    /**
     * This method update a database record, the payment type field, by contract hash.
     *
     * @param contractHash
     * @param currency
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateRecordCurrencyByContractHash(String contractHash, FiatCurrency currency)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CURRENCY_TYPE_COLUMN_NAME, currency.getCode());
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter ACK_OFFLINE_PAYMENT_CURRENCY_TYPE_COLUMN_NAME",
                    "");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter ACK_OFFLINE_PAYMENT_CURRENCY_TYPE_COLUMN_NAME",
                    "Unexpected Result");
        }
    }

    /**
     * This method update a database record, the CBP wallet public key field, by contract hash.
     *
     * @param contractHash
     * @param cbpWalletPublicKey
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateRecordCBPWalletPublicKeyByContractHash(
            String contractHash,
            String cbpWalletPublicKey) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {

        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, cbpWalletPublicKey);
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter ACK_OFFLINE_PAYMENT_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME",
                    "");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter ACK_OFFLINE_PAYMENT_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME",
                    "Unexpected Result");
        }
    }

    /**
     * This method update a database record, the CBP wallet public key field, by contract hash.
     *
     * @param contractHash
     * @param customerAlias
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateCustomerAliasByContractHash(
            String contractHash,
            String customerAlias) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {

        try {
            if (customerAlias == null || customerAlias.isEmpty()) {
                customerAlias = "Unregistered customer";
            }
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CUSTOMER_ALIAS_COLUMN_NAME, customerAlias);
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter ACK_OFFLINE_PAYMENT_CUSTOMER_ALIAS_COLUMN_NAME",
                    "");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter ACK_OFFLINE_PAYMENT_CUSTOMER_ALIAS_COLUMN_NAME",
                    "Unexpected Result");
        }
    }

    /**
     * This method returns the pending to submit notification list.
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
                    ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION.getCode(),
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingToSubmitNotificationList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns the pending to submit notification list.
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitConfirmationList() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {
            List<String> pendingContractHash = getStringList(
                    ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT_CONFIRMATION.getCode(),
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

            List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();
            BusinessTransactionRecord businessTransactionRecord;
            for (String contractHash : pendingContractHash) {
                businessTransactionRecord = getCustomerBusinessTransactionRecordByContractHash(contractHash);
                businessTransactionRecordList.add(businessTransactionRecord);
            }
            return businessTransactionRecordList;

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, "Getting value from PendingToSubmitNotificationList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns the pending to bank credit list.
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToBankCreditList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getBusinessTransactionRecordList(
                    ContractTransactionStatus.PENDING_CREDIT_BANK_WALLET.getCode(),
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingToBankCreditList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns the pending to bank credit list.
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToCashCreditList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getBusinessTransactionRecordList(
                    ContractTransactionStatus.PENDING_CREDIT_CASH_WALLET.getCode(),
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingToCashCreditList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
            businessTransactionRecord = getBrokerBusinessTransactionRecordByContractHash(contractHash);
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

    /**
     * This method returns the recorded pending events
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            return getPendingGenericsEvents(
                    databaseTable,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME
            );
        } catch (CantGetContractListException e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        } catch (Exception e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetContractListException(e,
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
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value = records
                    .get(0)
                    .getStringValue(
                            BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting value from database",
                    "Unexpected error");
        }

    }

    /**
     * This method updates the event status
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
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    new StringBuilder().append("Updating parameter ").append(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME).toString(), "");
        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param customerBrokerContractPurchase
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(CustomerBrokerContractPurchase customerBrokerContractPurchase) throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(customerBrokerContractPurchase.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(customerBrokerContractPurchase).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getDatabaseContractTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(databaseTableRecord, customerBrokerContractPurchase);
            databaseTable.insertRecord(databaseTableRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Error in persistContractInDatabase", "");

        } catch (Exception exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method creates a database table record in crypto broker side, only for backup
     *
     * @param record
     * @param customerBrokerContractPurchase
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record, CustomerBrokerContractPurchase customerBrokerContractPurchase) {
        UUID transactionId = UUID.randomUUID();

        record.setUUIDValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT_CONFIRMATION.getCode());

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
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
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
                    .getLongValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME);
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
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
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
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME,
                    completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Setting completion date from database",
                    "Cannot load the database table");
        }
    }

    private boolean eventExists(String eventId) throws CantSaveEventException {
        try {
            DatabaseTable table = getDatabaseEventsTable();
            ;
            if (table == null) {
                throw new CantSaveEventException("Cant check if Broker Ack Offline Payment Transaction event tablet exists");
            }
            table.addStringFilter(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantSaveEventException(em.getMessage(), em, "Broker Ack Offline Payment Transaction Id Not Exists", new StringBuilder().append("Cant load ").append(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantSaveEventException(e.getMessage(), FermatException.wrapException(e), "Broker Ack Offline Payment Transaction Id Not Exists", "unknown failure.");
        }
    }

}
