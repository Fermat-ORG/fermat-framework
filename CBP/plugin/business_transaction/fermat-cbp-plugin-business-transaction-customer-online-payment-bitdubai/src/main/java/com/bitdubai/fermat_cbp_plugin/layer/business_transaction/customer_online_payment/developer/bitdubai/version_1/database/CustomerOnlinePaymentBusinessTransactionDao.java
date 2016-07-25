package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
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
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.CustomerOnlinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.exceptions.CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ONLINE_PAYMENT_CONFIRMATION;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ONLINE_PAYMENT_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_PAYMENT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.getByCode;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_BROKER_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_CURRENCY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/2015.
 * Updated by Nelson Ramirez (nelsonalfo@gmail.com) on 26/05/2016
 */
public class CustomerOnlinePaymentBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private CustomerOnlinePaymentPluginRoot pluginRoot;
    private Database database;


    public CustomerOnlinePaymentBusinessTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                                       final UUID pluginId,
                                                       final Database database,
                                                       final CustomerOnlinePaymentPluginRoot pluginRoot) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
        this.pluginRoot = pluginRoot;
    }

    public void initialize() throws CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            try {
                CustomerOnlinePaymentBusinessTransactionDatabaseFactory databaseFactory;
                databaseFactory = new CustomerOnlinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, DATABASE_NAME);

            } catch (CantCreateDatabaseException f) {
                pluginRoot.reportError(DISABLES_THIS_PLUGIN, f);
                throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f, "", "There is a problem and i cannot create the database.");

            } catch (Exception z) {
                pluginRoot.reportError(DISABLES_THIS_PLUGIN, z);
                throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                        CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e,
                    "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException(
                    CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /**
     * Return the transaction status of a contract
     *
     * @param contractHash the contract Hash/ID
     * @return the contract transaction status
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            final String contractTransactionStatusCode = getValue(
                    contractHash,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);

            return getByCode(contractTransactionStatusCode);

        } catch (InvalidParameterException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the contract transaction status", "Unexpected error");
        }
    }

    /**
     * Return list of event IDs with PENDING event status
     *
     * @return list of event status IDs
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            List<String> eventTypeList = new ArrayList<>();

            databaseTable.addStringFilter(ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return eventTypeList; //There is no records in database, I'll return an empty list.

            for (DatabaseTableRecord databaseTableRecord : records) {
                String eventId = databaseTableRecord.getStringValue(ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }

            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetContractListException(e, "Getting events in EventStatus.PENDING", "Cannot load the table into memory");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting events in EventStatus.PENDING\"", "Unexpected error");
        }
    }

    /**
     * return the event type of the given event ID
     *
     * @param eventId the event ID
     * @return a String with event type code
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public String getEventType(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            final DatabaseTableRecord tableRecord = records.get(0);
            return tableRecord.getStringValue(ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Getting value from database", "Unexpected error");
        }
    }

    /**
     * Return a list of Business transaction IDs with Contract Transaction Status PENDING_PAYMENT
     *
     * @return a list of Business transaction IDs
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingToSubmitCryptoList() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getStringList(
                    PENDING_PAYMENT.getCode(),
                    ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingToSubmitCryptoList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * Return a list of Business Transaction with Contract Transaction Status PENDING_ONLINE_PAYMENT_NOTIFICATION
     *
     * @return a list of Business Transaction
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList() throws
            UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getCustomerOnlinePaymentRecordList(
                    PENDING_ONLINE_PAYMENT_NOTIFICATION.getCode(),
                    ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingToSubmitNotificationList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * Return a list of Business Transaction with Contract Transaction Status PENDING_ONLINE_PAYMENT_CONFIRMATION
     *
     * @return a list of Business Transaction
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitConfirmList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getCustomerOnlinePaymentRecordList(
                    PENDING_ONLINE_PAYMENT_CONFIRMATION.getCode(),
                    ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingToSubmitConfirmList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error", "Check the cause");
        }
    }

    /**
     * Return a list of Business Transaction with Contract Transaction Status ONLINE_PAYMENT_SUBMITTED
     *
     * @return a list of Business Transaction
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingCryptoTransactionList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getCustomerOnlinePaymentRecordList(
                    ONLINE_PAYMENT_SUBMITTED.getCode(),
                    ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from getPendingCryptoTransactionList", "");
        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * Return a list of Business Transaction with Crypto Status PENDING_SUBMIT
     *
     * @return a list of Business Transaction records
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitCryptoStatusList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getCustomerOnlinePaymentRecordList(
                    CryptoStatus.PENDING_SUBMIT.getCode(),
                    ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from getPendingToSubmitCryptoStatusList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * Return a list of Business Transaction with Crypto Status ON_CRYPTO_NETWORK
     *
     * @return a list of Business Transaction records
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getOnCryptoNetworkCryptoStatusList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getCustomerOnlinePaymentRecordList(
                    CryptoStatus.ON_CRYPTO_NETWORK.getCode(),
                    ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE,
                    exception, "Getting value from getOnCryptoNetworkCryptoStatusList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error", "Check the cause");
        }
    }

    /**
     * Return a list of Business Transaction with Crypto Status ON_BLOCKCHAIN
     *
     * @return a list of Business Transaction records
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getOnBlockchainCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getCustomerOnlinePaymentRecordList(
                    CryptoStatus.ON_BLOCKCHAIN.getCode(),
                    ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE,
                    exception, "Getting value from getOnBlockchainCryptoStatusList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error", "Check the cause");
        }

    }

    /**
     * check if the contract hash is in database
     *
     * @param contractHash the contract hash to check
     * @return <code>true</code> if contract hash is in database. <code>false</code> otherwise
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public boolean isContractHashInDatabase(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {

            String contractHashFromDatabase = getValue(
                    contractHash,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

            return (contractHashFromDatabase != null);

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method persists a basic record in database
     *
     * @param contractPurchase            the purchase contract
     * @param brokerCryptoAddress         the broker crypto address
     * @param walletPublicKey             customer wallet public key
     * @param cryptoAmount                the crypto amount to send to the broker
     * @param paymentCurrency             the currency to pay
     * @param blockchainNetworkType       the Blockchain Network Type
     * @param intraActorReceiverPublicKey the intra actor public key
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(CustomerBrokerContractPurchase contractPurchase,
                                          String brokerCryptoAddress,
                                          String walletPublicKey,
                                          long cryptoAmount,
                                          CryptoCurrency paymentCurrency,
                                          BlockchainNetworkType blockchainNetworkType,
                                          String intraActorReceiverPublicKey,
                                          FeeOrigin feeOrigin,
                                          long fee) throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(contractPurchase.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(contractPurchase).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getDatabaseContractTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();

            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    contractPurchase,
                    brokerCryptoAddress,
                    walletPublicKey,
                    cryptoAmount,
                    paymentCurrency,
                    blockchainNetworkType,
                    intraActorReceiverPublicKey,
                    feeOrigin,
                    fee);

            databaseTable.insertRecord(databaseTableRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Error in persistContractInDatabase", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns a BusinessTransactionRecord by a contract hash.
     *
     * @param contractHash the contract Hash/ID
     * @return the Business Transaction record
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getCustomerOnlinePaymentRecord(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {

        try {
            final DatabaseTable databaseTable = getDatabaseContractTable();

            final BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            final List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            final DatabaseTableRecord record = records.get(0);
            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));
            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(contractHash);
            businessTransactionRecord.setTransactionId(record.getStringValue(ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME));
            businessTransactionRecord.setActorPublicKey(record.getStringValue(ONLINE_PAYMENT_BROKER_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setExternalWalletPublicKey(record.getStringValue(ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setCryptoAmount(record.getLongValue(ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME));

            final ContractTransactionStatus status = getByCode(record.getStringValue(ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(status);

            final String cryptoCurrencyCode = record.getStringValue(ONLINE_PAYMENT_CRYPTO_CURRENCY_COLUMN_NAME);
            if (cryptoCurrencyCode != null) {
                businessTransactionRecord.setCryptoCurrency(CryptoCurrency.getByCode(cryptoCurrencyCode));

                final String cryptoAddress = record.getStringValue(ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME);
                final CryptoAddress brokerCryptoAddress = new CryptoAddress(cryptoAddress, businessTransactionRecord.getCryptoCurrency());
                businessTransactionRecord.setCryptoAddress(brokerCryptoAddress);
            }

            final String blockchainNetworkTypeString = record.getStringValue(ONLINE_PAYMENT_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME);
            final BlockchainNetworkType blockchainNetworkType = (blockchainNetworkTypeString == null || blockchainNetworkTypeString.isEmpty()) ?
                    BlockchainNetworkType.getDefaultBlockchainNetworkType() :
                    BlockchainNetworkType.getByCode(blockchainNetworkTypeString);

            businessTransactionRecord.setBlockchainNetworkType(blockchainNetworkType);

            businessTransactionRecord.setFee(record.getLongValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_MERCHANDISE_FEE_COLUMN_NAME));
            String feeOriginString = record.getStringValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_MERCHANDISE_ORIGIN_FEE_COLUMN_NAME);
            FeeOrigin feeOrigin;
            if (feeOriginString == null || feeOriginString.isEmpty()) {
                feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT;
            } else {
                try {
                    feeOrigin = FeeOrigin.getByCode(feeOriginString);
                } catch (InvalidParameterException ex) {
                    feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT;
                }
            }
            businessTransactionRecord.setFeeOrigin(feeOrigin);
            return businessTransactionRecord;

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database", "Cannot load the database table");

        } catch (InvalidParameterException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database", "Invalid parameter in ContractTransactionStatus");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Unexpected error");
        }

    }

    /**
     * Update in database the information of a business transaction
     *
     * @param businessTransactionRecord the Business Transaction record with the updated information to persist in database
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            String contractHash = businessTransactionRecord.getContractHash();

            databaseTable.addStringFilter(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record = buildDatabaseTableRecord(record, businessTransactionRecord);

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord", "Unexpected results in database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord", "Unexpected error");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param saleContract the sale contract with the information to persist
     * @param currencyCode the crypto currency to send to the broker
     * @param cryptoAmount the crypto amount to send to the broker
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(CustomerBrokerContractSale saleContract, String currencyCode, long cryptoAmount)
            throws CantInsertRecordException, UnexpectedResultReturnedFromDatabaseException {
        try {
            if (isContractHashInDatabase(saleContract.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(saleContract).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getDatabaseContractTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();

            databaseTableRecord = buildDatabaseTableRecord(databaseTableRecord, saleContract, currencyCode, cryptoAmount);

            databaseTable.insertRecord(databaseTableRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception,
                    "Error in persistContractInDatabase", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception,
                    "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method persists in an existing record in database the transaction UUID
     * from IntraActorCryptoTransactionManager by the contract hash.
     *
     * @param contractHash          the contract Hash/ID
     * @param cryptoTransactionUUID the crypto transaction ID
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void persistsCryptoTransactionUUID(String contractHash, UUID cryptoTransactionUUID)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setUUIDValue(ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, cryptoTransactionUUID);
            record.setStringValue(ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Persisting crypto transaction in database", "There was an unexpected result in database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Persisting crypto transaction in database", "Unexpected error");
        }
    }

    /**
     * Update the contract transaction status un database
     *
     * @param contractHash              the contract hash
     * @param contractTransactionStatus the new contract status
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateContractTransactionStatus(String contractHash, ContractTransactionStatus contractTransactionStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setStringValue(ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, contractTransactionStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantUpdateRecordException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE, exception, "Cant Update Record", "Check the cause");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * Update the status of an event
     *
     * @param eventId     the event ID
     * @param eventStatus the new event status
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateEventStatus(String eventId, EventStatus eventStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setStringValue(ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, eventStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    new StringBuilder().append("Updating parameter ").append(ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME).toString(), "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method save an incoming new event in database.
     *
     * @param eventType   the event type code
     * @param eventSource the event source code
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();

            eventRecord.setUUIDValue(ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(ONLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(ONLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);

            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Online Payment database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantSaveEventException(exception, "Saving new event.", "Unexpected exception");
        }
    }

    /**
     * This method returns the completion date from database.
     *
     * @param contractHash the contract Hash/ID
     * @return the completion date in millis
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public long getCompletionDateByContractHash(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return 0;

            checkDatabaseRecords(records);

            return records.get(0).getLongValue(ONLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting completion date from database", "Cannot load the database table");
        }
    }

    /**
     * This method sets the completion date in the database.
     *
     * @param contractHash   the contract Hash/ID
     * @param completionDate the completion date in millis
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public void setCompletionDateByContractHash(String contractHash, long completionDate) throws UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty())
                return;

            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setLongValue(ONLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME, completionDate);

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Setting completion date from database", "Cannot load the database table");
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
        return getDataBase().getTable(ONLINE_PAYMENT_TABLE_NAME);
    }

    /**
     * Returns the Open Contract Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * This method fill a database table record in crypto broker side, only for backup
     *
     * @param record       the record to fill
     * @param contractSale the sale contract where to extract the information
     * @param cryptoAmount the crypto amount to send to the broker
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record,
                                                         CustomerBrokerContractSale contractSale,
                                                         String currencyCode,
                                                         long cryptoAmount) {
        UUID transactionId = UUID.randomUUID();

        record.setUUIDValue(ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractSale.getContractId());
        record.setStringValue(ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, contractSale.getPublicKeyCustomer());
        record.setStringValue(ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME, contractSale.getPublicKeyBroker());
        record.setStringValue(ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, PENDING_ONLINE_PAYMENT_CONFIRMATION.getCode());
        record.setStringValue(ONLINE_PAYMENT_CRYPTO_CURRENCY_COLUMN_NAME, currencyCode);
        record.setLongValue(ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);

        return record;
    }

    /**
     * This method returns a complete database table record from a BusinessTransactionRecord
     *
     * @param record                    the database record to fill
     * @param businessTransactionRecord the business transaction record where to extract the information
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record, BusinessTransactionRecord businessTransactionRecord) {
        record.setStringValue(ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getBrokerPublicKey());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, businessTransactionRecord.getContractHash());
        record.setStringValue(ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME, businessTransactionRecord.getCryptoAddress().getAddress());
        record.setLongValue(ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME, businessTransactionRecord.getCryptoAmount());
        record.setStringValue(ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME, businessTransactionRecord.getCryptoStatus().getCode());
        record.setStringValue(ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getCustomerPublicKey());
        record.setLongValue(ONLINE_PAYMENT_TIMESTAMP_COLUMN_NAME, businessTransactionRecord.getTimestamp());
        record.setStringValue(ONLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME, businessTransactionRecord.getTransactionHash());
        record.setStringValue(ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, businessTransactionRecord.getTransactionId());
        record.setStringValue(ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getExternalWalletPublicKey());
        final ContractTransactionStatus contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();
        record.setStringValue(ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, contractTransactionStatus.getCode());
        //new fields
        //Origin Fee
        record.setStringValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_MERCHANDISE_ORIGIN_FEE_COLUMN_NAME, businessTransactionRecord.getFeeOrigin().getCode());
        //Fee
        record.setLongValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_MERCHANDISE_FEE_COLUMN_NAME, businessTransactionRecord.getFee());


        return record;
    }

    /**
     * This fill a database record based on the given parameters
     *
     * @param record                      the record to fill
     * @param purchaseContract            the purchase contract with this information: Contract ID, Customer Public Key, Broker Public Key
     * @param cryptoAddress               the crypto address
     * @param walletPublicKey             the customer wallet public key
     * @param cryptoAmount                the crypto amount to send
     * @param paymentCurrency             the currency to pay
     * @param blockchainNetworkType       the Blockchain Network Type
     * @param intraActorReceiverPublicKey the intra actor public key
     * @return the filled record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record,
                                                         CustomerBrokerContractPurchase purchaseContract,
                                                         String cryptoAddress,
                                                         String walletPublicKey,
                                                         long cryptoAmount,
                                                         CryptoCurrency paymentCurrency,
                                                         BlockchainNetworkType blockchainNetworkType,
                                                         String intraActorReceiverPublicKey,
                                                         FeeOrigin feeOrigin,
                                                         long fee) {


        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, purchaseContract.getContractId());
        record.setStringValue(ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, purchaseContract.getPublicKeyCustomer());
        record.setStringValue(ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME, purchaseContract.getPublicKeyBroker());
        //This state is the initial in this type of transaction
        record.setStringValue(ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, PENDING_PAYMENT.getCode());
        record.setStringValue(ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress);
        record.setStringValue(ONLINE_PAYMENT_BROKER_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME, intraActorReceiverPublicKey);
        record.setStringValue(ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey);
        record.setLongValue(ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);

        if (paymentCurrency != null)
            record.setStringValue(ONLINE_PAYMENT_CRYPTO_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());

        if (blockchainNetworkType == null)
            blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
        record.setStringValue(ONLINE_PAYMENT_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, blockchainNetworkType.getCode());
        //Origin Fee
        record.setStringValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_MERCHANDISE_ORIGIN_FEE_COLUMN_NAME, feeOrigin.getCode());
        //Fee
        record.setLongValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_MERCHANDISE_FEE_COLUMN_NAME, fee);

        return record;
    }

    /**
     * This method check the database record result.
     *
     * @param records list of database records
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws UnexpectedResultReturnedFromDatabaseException {
        // Represents the maximum number of records in <code>records</code>
        // I'm gonna set this number in 1 for now, because I want to check the records object has one only result.
        int VALID_RESULTS_NUMBER = 0;

        if (records.isEmpty())
            return;

        int recordsSize = records.size();

        if (recordsSize < VALID_RESULTS_NUMBER)
            throw new UnexpectedResultReturnedFromDatabaseException(new StringBuilder().append("I excepted ").append(VALID_RESULTS_NUMBER).append(", but I got ").append(recordsSize).toString());
    }

    /**
     * This method returns a list of Business Transaction records according the given arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return List<BusinessTransactionRecord>
     * @throws CantGetContractListException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private List<BusinessTransactionRecord> getCustomerOnlinePaymentRecordList(String key, String keyColumn, String valueColumn)
            throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException {

        List<String> pendingContractHash = getStringList(key, keyColumn, valueColumn);

        List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();
        for (String contractHash : pendingContractHash) {
            BusinessTransactionRecord businessTransactionRecord = getCustomerOnlinePaymentRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }

        return businessTransactionRecordList;
    }

    /**
     * This method search in the database and returns a list of String values for the given parameters
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return list of string values
     */
    private List<String> getStringList(String key, String keyColumn, String valueColumn) throws CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            List<String> contractHashList = new ArrayList<>();
            String contractHash;
            databaseTable.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return contractHashList; //There is no records in database, I'll return an empty list.

            for (DatabaseTableRecord databaseTableRecord : records) {
                contractHash = databaseTableRecord.getStringValue(valueColumn);
                contractHashList.add(contractHash);
            }

            return contractHashList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e, new StringBuilder().append("Getting ").append(valueColumn).append(" based on ").append(key).toString(), "Cannot load the table into memory");
        }
    }

    /**
     * This method returns a String value from parameters in database.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return the string value
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private String getValue(String key, String keyColumn, String valueColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return null;

            checkDatabaseRecords(records);

            return records.get(0).getStringValue(valueColumn);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        }

    }


}
