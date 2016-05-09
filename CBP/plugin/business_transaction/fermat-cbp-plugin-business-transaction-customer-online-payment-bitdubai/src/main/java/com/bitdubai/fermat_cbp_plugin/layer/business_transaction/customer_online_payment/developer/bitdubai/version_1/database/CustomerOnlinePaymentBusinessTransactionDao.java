package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
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
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.exceptions.CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerOnlinePaymentBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private ErrorManager errorManager;
    private Database database;

    public CustomerOnlinePaymentBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final ErrorManager errorManager) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
        this.errorManager=errorManager;
    }

    public void initialize() throws CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CustomerOnlinePaymentBusinessTransactionDatabaseFactory databaseFactory =
                        new CustomerOnlinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ONLINE_PAYMENT,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        f);
                throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ONLINE_PAYMENT,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        z);
                throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                        CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                    CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
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
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TABLE_NAME);
    }

    /**
     * Returns the Open Contract Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME);
    }

    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {

            String stringContractTransactionStatus = getValue(
                    contractHash,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
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
                        CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
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
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value = records
                    .get(0)
                    .getStringValue(
                            CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting value from database",
                    "Unexpected error");
        }

    }

    public List<String> getPendingToSubmitCryptoList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            List<String> stringList = getStringList(
                    ContractTransactionStatus.PENDING_PAYMENT.getCode(),
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
            return stringList;
        } catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingToSubmitCryptoList", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public List<BusinessTransactionRecord> getPendingToSubmitCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            List<BusinessTransactionRecord> customerOnlinePaymentRecordList = getCustomerOnlinePaymentRecordList(
                    CryptoStatus.PENDING_SUBMIT.getCode(),
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
            return customerOnlinePaymentRecordList;
        } catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from getPendingToSubmitCryptoStatusList", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            List<BusinessTransactionRecord> customerOnlinePaymentRecordList = getCustomerOnlinePaymentRecordList(
                    ContractTransactionStatus.PENDING_ONLINE_PAYMENT_NOTIFICATION.getCode(),
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
            return customerOnlinePaymentRecordList;
        } catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingToSubmitNotificationList", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public List<BusinessTransactionRecord> getPendingToSubmitConfirmList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            List<BusinessTransactionRecord> customerOnlinePaymentRecordList = getCustomerOnlinePaymentRecordList(
                    ContractTransactionStatus.PENDING_ONLINE_PAYMENT_CONFIRMATION.getCode(),
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
            return customerOnlinePaymentRecordList;
        } catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingToSubmitConfirmList", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public List<BusinessTransactionRecord> getOnCryptoNetworkCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            List<BusinessTransactionRecord> customerOnlinePaymentRecordList = getCustomerOnlinePaymentRecordList(
                    CryptoStatus.ON_CRYPTO_NETWORK.getCode(),
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
            return customerOnlinePaymentRecordList;
        } catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE,
                    exception, "Getting value from getOnCryptoNetworkCryptoStatusList", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public List<BusinessTransactionRecord> getOnBlockchainkCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {

            List<BusinessTransactionRecord> customerOnlinePaymentRecordList = getCustomerOnlinePaymentRecordList(
                    CryptoStatus.ON_BLOCKCHAIN.getCode(),
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

            return customerOnlinePaymentRecordList;
        } catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE,
                    exception, "Getting value from getOnBlockchainCryptoStatusList", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
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
    private List<BusinessTransactionRecord> getCustomerOnlinePaymentRecordList(
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
            businessTransactionRecord = getCustomerOnlinePaymentRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
    }

    /**
     * This method returns a BusinessTransactionRecord
     *
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingCryptoTransactionList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try {
            List<BusinessTransactionRecord> customerOnlinePaymentRecordList = getCustomerOnlinePaymentRecordList(
                    ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED.getCode(),
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME
            );
            return customerOnlinePaymentRecordList;
        } catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from getPendingCryptoTransactionList", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
                    "Getting " + valueColumn + " based on " + key,
                    "Cannot load the table into memory");
        }
    }

    public boolean isContractHashInDatabase(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            String contractHashFromDatabase = getValue(
                    contractHash,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
            return contractHashFromDatabase != null;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
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
            throw new UnexpectedResultReturnedFromDatabaseException("I excepted " + VALID_RESULTS_NUMBER + ", but I got " + recordsSize);
        }
    }

    /**
     * This method persists a basic record in database
     *
     * @param customerBrokerContractPurchase
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            String brokerCryptoAddress,
            String walletPublicKey,
            long cryptoAmount,
            BlockchainNetworkType blockchainNetworkType,String intraActorReceiverPublickey)
            throws CantInsertRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractPurchase,
                    brokerCryptoAddress,
                    walletPublicKey,
                    cryptoAmount,
                    blockchainNetworkType,intraActorReceiverPublickey
            );
            databaseTable.insertRecord(databaseTableRecord);
        } catch (CantInsertRecordException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,
                    exception, "Error in persistContractInDatabase", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns a BusinessTransactionRecord by a contract hash.
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getCustomerOnlinePaymentRecord(
            String contractHash) throws UnexpectedResultReturnedFromDatabaseException {

        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            ContractTransactionStatus contractTransactionStatus;
            CryptoAddress brokerCryptoAddress;
            String cryptoAddressString;
            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            businessTransactionRecord.setBrokerPublicKey(
                    record.getStringValue(
                            CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                            ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));
            contractTransactionStatus = ContractTransactionStatus.getByCode(record.getStringValue(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                            ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);
            businessTransactionRecord.setCustomerPublicKey(
                    record.getStringValue(
                            CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(contractHash);
            businessTransactionRecord.setTransactionId(
                    record.getStringValue(
                            CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME));
            cryptoAddressString = record.getStringValue(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                            ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME);
            //I going to set the money as bitcoin in this version
            brokerCryptoAddress = new CryptoAddress(cryptoAddressString, CryptoCurrency.BITCOIN);
            businessTransactionRecord.setCryptoAddress(brokerCryptoAddress);

            businessTransactionRecord.setActorPublicKey(record.getStringValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_BROKER_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME));

            businessTransactionRecord.setExternalWalletPublicKey(
                    record.getStringValue(
                            CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setCryptoAmount(
                    record.getLongValue(
                            CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME));
            String blockchainNetworkTypeString = record.getStringValue(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                            ONLINE_PAYMENT_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME);
            BlockchainNetworkType blockchainNetworkType;
            if(blockchainNetworkTypeString==null||blockchainNetworkTypeString.isEmpty()){
                blockchainNetworkType=BlockchainNetworkType.getDefaultBlockchainNetworkType();
            } else{
                blockchainNetworkType=BlockchainNetworkType.getByCode(blockchainNetworkTypeString);
            }
            businessTransactionRecord.setBlockchainNetworkType(blockchainNetworkType);
            return businessTransactionRecord;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Invalid parameter in ContractTransactionStatus");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting value from database",
                    "Unexpected error");
        }

    }

    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            String contractHash = businessTransactionRecord.getContractHash();
            databaseTable.addStringFilter(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record = buildDatabaseTableRecord(record, businessTransactionRecord);
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected results in database");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
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
            throws CantInsertRecordException, UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractSale
            );
            databaseTable.insertRecord(databaseTableRecord);
        } catch (CantInsertRecordException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception,
                    "Error in persistContractInDatabase", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ONLINE_PAYMENT_CONFIRMATION.getCode());

        return record;
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
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getBrokerPublicKey());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                businessTransactionRecord.getContractHash());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                businessTransactionRecord.getContractTransactionStatus().getCode());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME,
                businessTransactionRecord.getCryptoAddress().getAddress());
        record.setLongValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME,
                businessTransactionRecord.getCryptoAmount());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                businessTransactionRecord.getCryptoStatus().getCode());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getCustomerPublicKey());
        record.setLongValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TIMESTAMP_COLUMN_NAME,
                businessTransactionRecord.getTimestamp());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME,
                businessTransactionRecord.getTransactionHash());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                businessTransactionRecord.getTransactionId());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getExternalWalletPublicKey());

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
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            String cryptoaddress,
            String walletPublicKey,
            long cryptoAmount,
            BlockchainNetworkType blockchainNetworkType,String intraActorReceiverPublickey) {



        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_PAYMENT.getCode());

        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME,
                cryptoaddress);
        record.setStringValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_BROKER_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME,intraActorReceiverPublickey);

        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME,
                walletPublicKey);
        record.setLongValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME,
                cryptoAmount);
        if(blockchainNetworkType==null){
            blockchainNetworkType=BlockchainNetworkType.getDefaultBlockchainNetworkType();
        }
        record.setStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME,
                blockchainNetworkType.getCode());
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
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setUUIDValue(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                    cryptoTransactionUUID);
            record.setStringValue(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                    CryptoStatus.PENDING_SUBMIT.getCode());
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Persisting crypto transaction in database",
                    "There was an unexpected result in database");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    contractTransactionStatus.getCode());
        } catch (CantUpdateRecordException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE,
                    exception,"Cant Update Record",
                    "Check the cause");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(statusColumnName, newStatus);
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Updating parameter " + statusColumnName, "");
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
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter " + CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
            eventRecord.setUUIDValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);


        } catch (CantInsertRecordException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Online Payment database");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns the completion date from database.
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public long getCompletionDateByContractHash(
            String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.addStringFilter(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                            ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                return 0;
            }
            checkDatabaseRecords(records);
            long completionDate=records
                    .get(0)
                    .getLongValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                            ONLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME);
            return completionDate;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting completion date from database",
                    "Cannot load the database table");
        }
    }

    /**
     * This method sets the completion date in the database.
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public void setCompletionDateByContractHash(
            String contractHash,
            long completionDate)
            throws UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.addStringFilter(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                            ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                return ;
            }
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setLongValue(
                    CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                            ONLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME,
                    completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Setting completion date from database",
                    "Cannot load the database table");
        }
    }

}
