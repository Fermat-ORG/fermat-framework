package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.BrokerSubmitOfflineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class BrokerSubmitOfflineMerchandiseBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final BrokerSubmitOfflineMerchandisePluginRoot pluginRoot;
    private Database database;

    public BrokerSubmitOfflineMerchandiseBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final BrokerSubmitOfflineMerchandisePluginRoot pluginRoot) {
        this.pluginRoot = pluginRoot;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
    }

    public void initialize() throws CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseFactory databaseFactory =
                        new BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, f);
                throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                pluginRoot.reportError(
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        z);
                throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                        CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                    CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
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
     * Returns the Ack Online Payment DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseSubmitTable() {
        return getDataBase().getTable(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_TABLE_NAME);
    }

    /**
     * Returns the Submit Online Merchandise Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME);
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
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Submit Online Merchandise database");
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
     * This method returns the actual contract transaction status
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
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
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
            DatabaseTable databaseTable = getDatabaseSubmitTable();
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
     * @param customerBrokerContractSale
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale,
            String walletPublicKey,
            double amount,
            String cbpWalletPublicKey,
            BigDecimal referencePrice,
            FiatCurrency fiatCurrency,
            MoneyType moneyType)
            throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(customerBrokerContractSale.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(customerBrokerContractSale).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractSale,
                    walletPublicKey,
                    amount,
                    cbpWalletPublicKey,
                    referencePrice,
                    fiatCurrency,
                    moneyType
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
     * This method creates a database table record from a CustomerBrokerContractPurchase object.
     * This record is not complete, is missing the transaction hash,  and the crypto status,
     * this values will after sending the crypto amount, also the timestamp is set at this moment.
     *
     * @param record
     * @param customerBrokerContractSale
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractSale customerBrokerContractSale,
            String walletPublicKey,
            double amount,
            String cbpWalletPublicKey,
            BigDecimal referencePrice,
            FiatCurrency merchandiseType,
            MoneyType moneyType) {

        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_OFFLINE_DE_STOCK.getCode());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME,
                walletPublicKey);
        record.setDoubleValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_AMOUNT_COLUMN_NAME,
                amount);
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME,
                cbpWalletPublicKey
        );
        record.setDoubleValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME,
                referencePrice.doubleValue()
        );
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_PAYMENT_TYPE_COLUMN_NAME,
                moneyType.getCode()
        );
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CURRENCY_TYPE_COLUMN_NAME,
                merchandiseType.getCode()
        );
        return record;
    }

    /**
     * This method returns a BusinessTransactionRecord list from the pending Crypto De Stock transactions
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingDeStockTransactionList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getCustomerOfflinePaymentRecordList(
                    ContractTransactionStatus.PENDING_OFFLINE_DE_STOCK.getCode(),
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME
            );
        } catch (CantGetContractListException e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    e, "Getting value from getPendingDeStockTransactionList", "");
        } catch (Exception e) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns a BusinessTransactionRecord List according the arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return List<BusinessTransactionRecord>
     * @throws CantGetContractListException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private List<BusinessTransactionRecord> getCustomerOfflinePaymentRecordList(
            String key,
            String keyColumn,
            String valueColumn) throws
            CantGetContractListException,
            UnexpectedResultReturnedFromDatabaseException {
        List<String> pendingContractHash = getStringList(
                key,
                keyColumn,
                valueColumn);
        List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();
        BusinessTransactionRecord businessTransactionRecord;
        for (String contractHash : pendingContractHash) {
            if (key.equals(ContractTransactionStatus.PENDING_OFFLINE_MERCHANDISE_CONFIRMATION.getCode()))
                businessTransactionRecord = getBusinessTransactionRecordSummary(contractHash);
            else businessTransactionRecord = getBusinessTransactionRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
    }

    /**
     * This method returns a <code>List<String></code> with the parameter in the arguments.
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
            DatabaseTable databaseTable = getDatabaseSubmitTable();
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
     * This method returns the BusinessTransactionRecord from a given contractHashId.
     * The BusinessTransactionRecord contains all the Submit Online Merchandise table record.
     *
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBusinessTransactionRecordSummary(String contractHash)
            throws
            UnexpectedResultReturnedFromDatabaseException {

        try {
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            ContractTransactionStatus contractTransactionStatus;
            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);


            businessTransactionRecord.setTransactionId(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));

            businessTransactionRecord.setContractHash(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));

            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));

            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));

            contractTransactionStatus = ContractTransactionStatus.getByCode(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);

            businessTransactionRecord.setTransactionHash(contractHash);

            return businessTransactionRecord;
        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (InvalidParameterException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Invalid parameter in ContractTransactionStatus");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting value from database",
                    "Unexpected error");
        }

    }

    /**
     * This method returns the BusinessTransactionRecord from a given contractHashId.
     * The BusinessTransactionRecord contains all the Submit Online Merchandise table record.
     *
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBusinessTransactionRecord(String contractHash)
            throws
            UnexpectedResultReturnedFromDatabaseException {

        try {
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            ContractTransactionStatus contractTransactionStatus;
            BigDecimal referencePrice;
            FiatCurrency fiatCurrency;
            double referencePriceFromDatabase;
            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);

            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));

            businessTransactionRecord.setContractHash(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));

            contractTransactionStatus = ContractTransactionStatus.getByCode(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);

            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(contractHash);

            businessTransactionRecord.setTransactionId(record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));

            //Represents the external wallet
            String walletPublicKey = record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME);
            businessTransactionRecord.setExternalWalletPublicKey(walletPublicKey);

            //Represents the amount
            businessTransactionRecord.setCryptoAmount(record.getLongValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_AMOUNT_COLUMN_NAME));

            //Represent cbpWalletPublicKey
            String cbpWalletPublicKey = record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME);
            businessTransactionRecord.setCBPWalletPublicKey(cbpWalletPublicKey);

            //Represent referencePrice
            referencePriceFromDatabase = record.getDoubleValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME);
            referencePrice = BigDecimal.valueOf(referencePriceFromDatabase);
            businessTransactionRecord.setPriceReference(referencePrice);

            //Represent fiatCurrency
            String paymentTypeString = record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_CURRENCY_TYPE_COLUMN_NAME);
            fiatCurrency = FiatCurrency.getByCode(paymentTypeString);
            businessTransactionRecord.setFiatCurrency(fiatCurrency);

            //Represent paymentType
            String paymentTypeCode = record.getStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_PAYMENT_TYPE_COLUMN_NAME);
            final MoneyType paymentType = MoneyType.getByCode(paymentTypeCode);
            businessTransactionRecord.setPaymentType(paymentType);

            return businessTransactionRecord;
        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (InvalidParameterException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Invalid parameter in ContractTransactionStatus");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting value from database",
                    "Unexpected error");
        }

    }

    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {

            DatabaseTable databaseTable = getDatabaseSubmitTable();
            String contractHash = businessTransactionRecord.getContractHash();
            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record = buildDatabaseTableRecord(record, businessTransactionRecord);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected results in database");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Getting value from database",
                    "Unexpected results in database");
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
    public void updateContractTransactionStatus(String contractHash, ContractTransactionStatus contractTransactionStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {

            ObjectChecker.checkArgument(contractHash);

            DatabaseTable databaseTable = getDatabaseSubmitTable();

            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);

            record.setStringValue(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    contractTransactionStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected results in database");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Getting value from database",
                    "Unexpected results in database");
        }

    }

    /**
     * This method returns a complete database table record from a BusinessTransactionRecord
     *
     * @param record
     * @param businessTransactionRecord
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            BusinessTransactionRecord businessTransactionRecord) {
        BigDecimal referencePrice;
        double doubleReferencePrice;
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getBrokerPublicKey());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                businessTransactionRecord.getContractHash());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                businessTransactionRecord.getContractTransactionStatus().getCode());
        record.setLongValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_AMOUNT_COLUMN_NAME,
                businessTransactionRecord.getCryptoAmount());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getCustomerPublicKey());
        record.setLongValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME,
                businessTransactionRecord.getTimestamp());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                businessTransactionRecord.getTransactionId());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getExternalWalletPublicKey());
        /**
         * Transform BigDecimal Object to double, this version of database plugin not support BigDecimal.
         */
        referencePrice = businessTransactionRecord.getPriceReference();
        doubleReferencePrice = referencePrice.doubleValue();
        record.setDoubleValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME,
                doubleReferencePrice
        );
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getCBPWalletPublicKey()
        );
        FiatCurrency fiatCurrency = businessTransactionRecord.getFiatCurrency();
        if (fiatCurrency != null) {
            record.setStringValue(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CURRENCY_TYPE_COLUMN_NAME,
                    fiatCurrency.getCode()
            );
        }
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_PAYMENT_TYPE_COLUMN_NAME,
                businessTransactionRecord.getPaymentType().getCode()
        );

        return record;
    }

    /**
     * This method returns the pending to submit notifications transactions
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getCustomerOfflinePaymentRecordList(
                    ContractTransactionStatus.PENDING_SUBMIT_OFFLINE_MERCHANDISE_NOTIFICATION.getCode(),
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from getPendingToSubmitNotificationList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns the pending to submit notifications transactions
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitConfirmList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getCustomerOfflinePaymentRecordList(
                    ContractTransactionStatus.PENDING_OFFLINE_MERCHANDISE_CONFIRMATION.getCode(),
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from getPendingToSubmitNotificationList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns a BusinessTransactionRecord list from the pending Crypto transactions
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingCryptoTransactionList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            return getCustomerOnlinePaymentRecordList(
                    ContractTransactionStatus.OFFLINE_MERCHANDISE_SUBMITTED.getCode(),
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME
            );
        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception, "Getting value from getPendingCryptoTransactionList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns a BusinessTransactionRecord List according the arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return List<BusinessTransactionRecord>
     * @throws CantGetContractListException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private List<BusinessTransactionRecord> getCustomerOnlinePaymentRecordList(
            String key,
            String keyColumn,
            String valueColumn) throws
            CantGetContractListException,
            UnexpectedResultReturnedFromDatabaseException {
        List<String> pendingContractHash = getStringList(
                key,
                keyColumn,
                valueColumn);
        List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();
        BusinessTransactionRecord businessTransactionRecord;
        for (String contractHash : pendingContractHash) {
            businessTransactionRecord = getBusinessTransactionRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
    }

    /**
     * This method returns the pending event list.
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            List<String> eventTypeList = new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
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
                        BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Unexpected Error", "CHeck the cause");
        }
    }

    /**
     * This method returns the event type recorded in database by event id
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
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value = records
                    .get(0)
                    .getStringValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Unexpected result from Database", "Check the cause");
        }

    }

    /**
     * This method checks if the given contract hash exists in database.
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
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
            return contractHashFromDatabase != null;
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Unexpected result from Database", "Check the cause");
        }
    }

    /**
     * This method updates the event status.
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
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    new StringBuilder().append("Updating parameter ").append(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME).toString(), "");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Unexpected Result from database", "Check the cause");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractPurchase in crypto broker side, only for backup
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
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractPurchase
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
     * @param customerBrokerContractPurchase
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractPurchase customerBrokerContractPurchase) {

        UUID transactionId = UUID.randomUUID();

        record.setUUIDValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_OFFLINE_MERCHANDISE_CONFIRMATION.getCode());

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
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty()) {
                return 0;
            }
            checkDatabaseRecords(records);
            return records.get(0).getLongValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_OFFLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME);
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
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
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
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME,
                    completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Setting completion date from database",
                    "Cannot load the database table");
        }
    }

}
