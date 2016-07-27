package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.IncomingMoneyEventWrapper;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.BrokerAckOnlinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.exceptions.CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ACK_ONLINE_PAYMENT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ACK_ONLINE_PAYMENT_CONFIRMATION;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ACK_ONLINE_PAYMENT_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.getByCode;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_CURRENCY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_TRANSACTION_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_WALLET_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 * Updated by Nelson Ramirez (nelsonalfo@gmail.com) on 26/05/15.
 */
public class BrokerAckOnlinePaymentBusinessTransactionDao {

    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private BrokerAckOnlinePaymentPluginRoot pluginRoot;
    private Database database;


    public BrokerAckOnlinePaymentBusinessTransactionDao(PluginDatabaseSystem pluginDatabaseSystem,
                                                        UUID pluginId,
                                                        Database database,
                                                        BrokerAckOnlinePaymentPluginRoot pluginRoot) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
        this.pluginRoot = pluginRoot;
    }

    public void initialize() throws CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {

            try {
                BrokerAckOnlinePaymentBusinessTransactionDatabaseFactory databaseFactory =
                        new BrokerAckOnlinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, DATABASE_NAME);

            } catch (CantCreateDatabaseException f) {
                pluginRoot.reportError(DISABLES_THIS_PLUGIN, f);
                throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                pluginRoot.reportError(DISABLES_THIS_PLUGIN, z);
                throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(
                        CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e,
                    "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(
                    CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE, e,
                    "", "Generic Exception.");
        }
    }

    /**
     * This method returns the contract transaction status
     *
     * @param contractHash the contract Hash/ID
     * @return the Contract Transaction Status
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {

            String stringContractTransactionStatus = getValue(
                    contractHash,
                    ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);

            return getByCode(stringContractTransactionStatus);

        } catch (InvalidParameterException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting the contract transaction status", "Invalid code in ContractTransactionStatus enum");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting the contract transaction status", "Unexpected error");
        }
    }

    /**
     * This method returns the recorded Incoming Money pending events
     *
     * @return the recorded pending event IDs
     * @throws CantGetContractListException
     */
    public List<String> getPendingIncomingMoneyEvents() throws CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseIncomingMoneyTable();

            return getPendingGenericsEvents(databaseTable,
                    ACK_ONLINE_PAYMENT_INCOMING_MONEY_STATUS_COLUMN_NAME,
                    ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME);

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns the recorded pending events
     *
     * @return the recorded pending event IDs
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();

            return getPendingGenericsEvents(
                    databaseTable,
                    ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME);

        } catch (CantGetContractListException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantGetContractListException(e, "Getting events in getPendingEvents", "Cannot load the table into memory");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(exception, "Getting events in GetPendingEvents", "Unexpected error");
        }
    }

    /**
     * This method returns the event type by event Id
     *
     * @param eventId the event ID
     * @return the event type code
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public String getEventType(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            return records.get(0).getStringValue(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Getting value from database", "Unexpected error");
        }

    }

    /**
     * This method return a list of Business Transaction records with Contract Transaction Status PENDING_ACK_ONLINE_PAYMENT_NOTIFICATION
     *
     * @return list of Business Transaction records
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getBusinessTransactionRecordList(
                    PENDING_ACK_ONLINE_PAYMENT_NOTIFICATION.getCode(),
                    ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE,
                    exception, "Getting value from PendingTosSubmitNotificationList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method return a list of Business Transaction records with Contract Transaction Status PENDING_ACK_ONLINE_PAYMENT_CONFIRMATION
     *
     * @return list of Business Transaction records
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitConfirmList() throws
            UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getBusinessTransactionRecordList(
                    PENDING_ACK_ONLINE_PAYMENT_CONFIRMATION.getCode(),
                    ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE,
                    exception, "Getting value from PendingToSubmitConfirmList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * check if the a business transaction with the given contract Hash is in database
     *
     * @param contractHash the contract Hash/ID to check
     * @return <code>true</code> if the business transaction is database. <code>false</code> otherwise
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public boolean isContractHashInDatabase(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            String contractHashFromDatabase = getValue(
                    contractHash,
                    ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);

            return (contractHashFromDatabase != null);

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param contractSale    the sale contract object with the information to persist
     * @param paymentCurrency the payment crypto currency
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(CustomerBrokerContractSale contractSale,
                                          long cryptoAmount,
                                          CryptoCurrency paymentCurrency) throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(contractSale.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(contractSale).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getDatabaseContractTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();

            databaseTableRecord = buildDatabaseTableRecord(databaseTableRecord, contractSale, cryptoAmount, paymentCurrency);

            databaseTable.insertRecord(databaseTableRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Error in persistContractInDatabase", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Unexpected error", "Check the cause");
        }

    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param contractPurchase the purchase contract object with the information to persist
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(CustomerBrokerContractPurchase contractPurchase) throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(contractPurchase.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(contractPurchase).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getDatabaseContractTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(databaseTableRecord, contractPurchase);

            databaseTable.insertRecord(databaseTableRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Error in persistContractInDatabase", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * Return a IncomingMoneyEventWrapper object with the information about the registered Incoming Money event
     *
     * @param eventId the Incoming Money event ID
     * @return the IncomingMoneyEventWrapper object
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public IncomingMoneyEventWrapper getIncomingMoneyEventWrapper(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseIncomingMoneyTable();
            databaseTable.addStringFilter(ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);

            return new IncomingMoneyEventWrapper(
                    record.getStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME),
                    record.getStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME),
                    record.getLongValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME),
                    CryptoCurrency.getByCode(record.getStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME)),
                    record.getStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_WALLET_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME),
                    record.getLongValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME),
                    record.getStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_TRANSACTION_HASH_COLUMN_NAME));

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        } catch (InvalidParameterException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Invalid parameter in ContractTransactionStatus");
        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Getting value from database", "Unexpected Result");
        }
    }

    /**
     * Return the Business Transaction record associated to the contract Hash
     *
     * @param contractHash the contract Hash/ID
     * @return the associated Business Transaction record
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBusinessTransactionRecordByContractHash(String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            if (records.isEmpty())
                return null;

            DatabaseTableRecord record = records.get(0);
            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));
            ContractTransactionStatus status = getByCode(record.getStringValue(ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(status);
            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(record.getStringValue(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));
            businessTransactionRecord.setTransactionId(record.getStringValue(ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME));
            businessTransactionRecord.setExternalWalletPublicKey(record.getStringValue(ACK_ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setCryptoAmount(record.getLongValue(ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME));

            String cryptoCurrencyCode = record.getStringValue(ACK_ONLINE_PAYMENT_CRYPTO_CURRENCY_COLUMN_NAME);
            if (cryptoCurrencyCode != null) {
                businessTransactionRecord.setCryptoCurrency(CryptoCurrency.getByCode(cryptoCurrencyCode));

                String cryptoAddressString = record.getStringValue(ACK_ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME);
                CryptoAddress brokerCryptoAddress = new CryptoAddress(cryptoAddressString, businessTransactionRecord.getCryptoCurrency());
                businessTransactionRecord.setCryptoAddress(brokerCryptoAddress);
            }

            return businessTransactionRecord;

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");

        } catch (InvalidParameterException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Invalid parameter in ContractTransactionStatus");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Check the cause");
        }
    }

    /**
     * Update in database the information of a Business Transaction record
     *
     * @param businessTransactionRecord the Business Transaction record with the updated information
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            String contractHash = businessTransactionRecord.getContractHash();
            databaseTable.addStringFilter(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record = buildDatabaseTableRecord(record, businessTransactionRecord);

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord", "Unexpected results in database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Getting value from database", "Check the cause");
        }
    }

    /**
     * Update in database the Contract Transaction Status of a Business Transaction record
     *
     * @param contractHash the contract Hash/ID
     * @param newStatus    the new status
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateContractTransactionStatus(String contractHash, ContractTransactionStatus newStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setStringValue(ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, newStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantUpdateRecordException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Update Contract Transaction Status in database", "There was an unexpected result in database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Updating Contract Transaction Status in database", "Check the cause");
        }
    }

    /**
     * Update in database status of a event
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
            databaseTable.addStringFilter(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            DatabaseTableRecord record = records.get(0);
            record.setStringValue(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, eventStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    new StringBuilder().append("Updating parameter ").append(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME).toString(), "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * Update in database the status of the Incoming Money event
     *
     * @param eventId     the Incoming Money event ID
     * @param eventStatus the new event status
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateIncomingMoneyEventStatus(String eventId, EventStatus eventStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseIncomingMoneyTable();
            databaseTable.addStringFilter(ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_STATUS_COLUMN_NAME, eventStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    new StringBuilder().append("Updating parameter ").append(ACK_ONLINE_PAYMENT_INCOMING_MONEY_STATUS_COLUMN_NAME).toString(), "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method save an incoming new event in database. You can set the event Id with this method
     *
     * @param eventType   the event type code
     * @param eventSource the event source code
     * @param eventId     the event ID
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource, String eventId) throws CantSaveEventException {
        try {
            if (!eventExists(eventId)) {
                DatabaseTable databaseTable = getDatabaseEventsTable();
                DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
                long unixTime = System.currentTimeMillis();
                eventRecord.setStringValue(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
                eventRecord.setStringValue(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
                eventRecord.setStringValue(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
                eventRecord.setStringValue(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
                eventRecord.setLongValue(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
                databaseTable.insertRecord(eventRecord);
            }

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Ack Online Payment database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
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
            UUID eventRecordID = UUID.randomUUID();
            saveNewEvent(eventType, eventSource, eventRecordID.toString());

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }

    /**
     * This method save an incoming money event in database. You can set the event Id with this method
     *
     * @param event the IncomingMoneyNotificationEvent object with the information to persist
     * @throws CantSaveEventException
     */
    public void saveIncomingMoneyEvent(IncomingMoneyNotificationEvent event) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseIncomingMoneyTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();

            IncomingMoneyEventWrapper incomingMoneyEventWrapper = new IncomingMoneyEventWrapper(event);

            eventRecord = buildDatabaseTableRecord(eventRecord, incomingMoneyEventWrapper);

            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Ack Online Payment database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
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
            databaseTable.addStringFilter(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty())
                return 0;

            return records.get(0).getLongValue(ACK_ONLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting completion date from database", "Cannot load the database table");
        }
    }

    /**
     * This method sets the completion date in the database.
     *
     * @param contractHash   contract Hash/ID
     * @param completionDate the completion date in millis
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public void setCompletionDateByContractHash(String contractHash, long completionDate)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            databaseTable.addStringFilter(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty())
                return;

            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setLongValue(ACK_ONLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME, completionDate);

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
     * Returns the Ack Online Payment DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseContractTable() {
        return getDataBase().getTable(ACK_ONLINE_PAYMENT_TABLE_NAME);
    }

    /**
     * Returns the Ack Online Payment Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * Returns the Ack Online Incoming money event DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseIncomingMoneyTable() {
        return getDataBase().getTable(ACK_ONLINE_PAYMENT_INCOMING_MONEY_TABLE_NAME);
    }

    /**
     * This method fill a database table record in crypto broker side, only for backup
     *
     * @param record          the database record to fill
     * @param contractSale    the contract sale object with the information to persist
     * @param paymentCurrency the payment crypto currency
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record,
                                                         CustomerBrokerContractSale contractSale,
                                                         long cryptoAmount,
                                                         CryptoCurrency paymentCurrency) {
        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractSale.getContractId());
        record.setStringValue(ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, contractSale.getPublicKeyCustomer());
        record.setStringValue(ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME, contractSale.getPublicKeyBroker());
        record.setStringValue(ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, PENDING_ACK_ONLINE_PAYMENT.getCode());
        record.setLongValue(ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);

        if (paymentCurrency != null)
            record.setStringValue(ACK_ONLINE_PAYMENT_CRYPTO_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());

        return record;
    }

    /**
     * This method fill a database table record in crypto broker side, only for backup
     *
     * @param record           the database table record to fill
     * @param contractPurchase the purchase contract with the information to persist
     * @return the filled database table record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record, CustomerBrokerContractPurchase contractPurchase) {
        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, contractPurchase.getContractId());
        record.setStringValue(ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, contractPurchase.getPublicKeyCustomer());
        record.setStringValue(ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME, contractPurchase.getPublicKeyBroker());
        record.setStringValue(ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, PENDING_ACK_ONLINE_PAYMENT_CONFIRMATION.getCode());

        return record;
    }

    /**
     * This method fill a database record with the information of a Business Transaction Record
     *
     * @param record                    the database record to fill
     * @param businessTransactionRecord the Business Transaction record with the information to fill
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record, BusinessTransactionRecord businessTransactionRecord) {
        record.setStringValue(ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getBrokerPublicKey());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME, businessTransactionRecord.getContractHash());
        record.setStringValue(ACK_ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME, businessTransactionRecord.getCryptoAddress().getAddress());
        record.setLongValue(ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME, businessTransactionRecord.getCryptoAmount());
        record.setStringValue(ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getCustomerPublicKey());
        record.setLongValue(ACK_ONLINE_PAYMENT_TIMESTAMP_COLUMN_NAME, businessTransactionRecord.getTimestamp());
        record.setStringValue(ACK_ONLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME, businessTransactionRecord.getTransactionHash());
        record.setStringValue(ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, businessTransactionRecord.getTransactionId());
        record.setStringValue(ACK_ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getExternalWalletPublicKey());

        final CryptoCurrency cryptoCurrency = businessTransactionRecord.getCryptoCurrency();
        if (cryptoCurrency != null)
            record.setStringValue(ACK_ONLINE_PAYMENT_CRYPTO_CURRENCY_COLUMN_NAME, cryptoCurrency.getCode());

        final CryptoStatus cryptoStatus = businessTransactionRecord.getCryptoStatus();
        if (cryptoStatus != null)
            record.setStringValue(ACK_ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());

        final ContractTransactionStatus contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();
        if (contractTransactionStatus != null)
            record.setStringValue(ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, contractTransactionStatus.getCode());

        return record;
    }

    /**
     * This method fill a database record with the data of a IncomingMoneyEventWrapper object
     *
     * @param record             the database record to fill
     * @param incomingMoneyEvent Incoming Money event wrapper object with the information to fill the database record
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record, IncomingMoneyEventWrapper incomingMoneyEvent) {

        record.setStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME, incomingMoneyEvent.getEventId());
        record.setStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME, incomingMoneyEvent.getReceiverPublicKey());
        record.setLongValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME, incomingMoneyEvent.getCryptoAmount());
        record.setStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME, incomingMoneyEvent.getSenderPublicKey());
        record.setStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
        record.setStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_WALLET_PUBLIC_KEY_COLUMN_NAME, incomingMoneyEvent.getWalletPublicKey());
        record.setLongValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME, incomingMoneyEvent.getTimestamp());
        record.setStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_TRANSACTION_HASH_COLUMN_NAME, incomingMoneyEvent.getTransactionHash());

        if (incomingMoneyEvent.getCryptoCurrency() != null)
            record.setStringValue(ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME, incomingMoneyEvent.getCryptoCurrency().getCode());

        return record;
    }

    /**
     * This method check the database record result.
     *
     * @param records a list of database records
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws UnexpectedResultReturnedFromDatabaseException {
        // Represents the maximum number of records in <code>records</code>
        // I'm gonna set this number in 1 for now, because I want to check the records object has one only result.
        int VALID_RESULTS_NUMBER = 1;

        if (records.isEmpty())
            return;

        int recordsSize = records.size();
        if (recordsSize > VALID_RESULTS_NUMBER)
            throw new UnexpectedResultReturnedFromDatabaseException(new StringBuilder().append("I excepted ").append(VALID_RESULTS_NUMBER).append(", but I got ").append(recordsSize).toString());
    }

    /**
     * This method returns a String value from parameters in database.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return the String value
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

    /**
     * This method returns pending generic events by given parameters
     *
     * @param databaseTable the database table
     * @param statusColumn  status column
     * @param idColumn      the id column
     * @return the recorded pending event IDs
     * @throws CantGetContractListException
     */
    private List<String> getPendingGenericsEvents(DatabaseTable databaseTable, String statusColumn, String idColumn) throws CantGetContractListException {
        try {
            List<String> eventTypeList = new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(statusColumn, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return eventTypeList; //There is no records in database, I'll return an empty list.

            for (DatabaseTableRecord databaseTableRecord : records) {
                eventId = databaseTableRecord.getStringValue(idColumn);
                eventTypeList.add(eventId);
            }

            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e, new StringBuilder().append("Getting events in EventStatus.PENDING in table ").append(databaseTable.getTableName()).toString(),
                    "Cannot load the table into memory");
        }
    }

    /**
     * This method returns a CustomerOnlinePaymentRecordList according the arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return a list of Business Transaction records
     * @throws CantGetContractListException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private List<BusinessTransactionRecord> getBusinessTransactionRecordList(String key, String keyColumn, String valueColumn)
            throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException {

        List<String> pendingContractHash = getStringList(key, keyColumn, valueColumn);

        List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();
        for (String contractHash : pendingContractHash) {
            BusinessTransactionRecord businessTransactionRecord = getBusinessTransactionRecordByContractHash(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
    }

    /**
     * This method returns a list of String values for given parameters.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return list of String values
     */
    private List<String> getStringList(String key, String keyColumn, String valueColumn) throws CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseContractTable();
            List<String> contractHashList = new ArrayList<>();
            databaseTable.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return contractHashList; //There is no records in database, I'll return an empty list.

            for (DatabaseTableRecord databaseTableRecord : records) {
                String contractHash = databaseTableRecord.getStringValue(valueColumn);
                contractHashList.add(contractHash);
            }

            return contractHashList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e, new StringBuilder().append("Getting ").append(valueColumn).append(" based on ").append(key).toString(), "Cannot load the table into memory");
        }
    }

    private boolean eventExists(String eventId) throws CantSaveEventException {
        try {
            DatabaseTable table = getDatabaseEventsTable();

            table.addStringFilter(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return (table.getRecords().size() > 0);

        } catch (CantLoadTableToMemoryException ex) {
            throw new CantSaveEventException(ex.getMessage(), ex, "Broker Ack Online Payment Transaction Event Id Not Exists",
                    new StringBuilder().append("Cant load ").append(ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME).append(" table in memory.").toString());

        } catch (Exception ex) {
            throw new CantSaveEventException(ex.getMessage(), FermatException.wrapException(ex),
                    "Broker Ack Online Payment Transaction Event Id Not Exists", "unknown failure.");
        }
    }
}
