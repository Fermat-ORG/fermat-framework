package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetCryptoAmountException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.IncomingMoneyEventWrapper;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.CustomerAckOnlineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ACK_ONLINE_MERCHANDISE;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ACK_ONLINE_MERCHANDISE_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ONLINE_MERCHANDISE_CONFIRMATION;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.getByCode;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TRANSACTION_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerAckOnlineMerchandiseBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private CustomerAckOnlineMerchandisePluginRoot pluginRoot;
    private Database database;

    public CustomerAckOnlineMerchandiseBusinessTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                                              final UUID pluginId,
                                                              final Database database,
                                                              final CustomerAckOnlineMerchandisePluginRoot pluginRoot) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
        this.pluginRoot = pluginRoot;
    }

    public void initialize() throws CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {

            try {
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory databaseFactory =
                        new CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, DATABASE_NAME);

            } catch (CantCreateDatabaseException f) {
                pluginRoot.reportError(DISABLES_THIS_PLUGIN, f);
                throw new CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                pluginRoot.reportError(DISABLES_THIS_PLUGIN, z);
                throw new CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e,
                    "", "Exception not handled by the plugin, there is a problem and I cannot open the database.");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException(
                    CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE, e,
                    "", "Generic Exception.");
        }
    }

    /**
     * This method save an incoming new event in database. You can set the event Id with this method
     *
     * @param eventType   the event type
     * @param eventSource the event source
     * @param eventId     the event id
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource, String eventId) throws CantSaveEventException {
        try {
            if (!eventExists(eventId)) {
                DatabaseTable databaseTable = getDatabaseEventsTable();
                DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
                long unixTime = System.currentTimeMillis();

                eventRecord.setStringValue(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
                eventRecord.setStringValue(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
                eventRecord.setStringValue(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
                eventRecord.setStringValue(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
                eventRecord.setLongValue(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);

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
     * @param eventType   the event type
     * @param eventSource the event source
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
     * @param event the event to save
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
     * This method returns the contract transaction status
     *
     * @param contractHash the contract Hash/ID
     * @return the Contract Transaction Status
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {

            String stringContractTransactionStatus = getValue(
                    contractHash,
                    ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);

            return getByCode(stringContractTransactionStatus);

        } catch (InvalidParameterException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Getting the contract transaction status",
                    "Unexpected error");
        }
    }

    /**
     * This method returns the recorded pending events
     *
     * @return a list of event IDs
     * @throws CantGetContractListException
     */
    public List<String> getPendingIncomingMoneyEvents() throws CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseIncomingMoneyTable();

            return getPendingGenericsEvents(
                    databaseTable,
                    ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_STATUS_COLUMN_NAME,
                    ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingToSubmitCryptoList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * @return a list of BusinessTransactionRecord pending to submit notification
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {
            return getBusinessTransactionRecordList(
                    PENDING_ACK_ONLINE_MERCHANDISE_NOTIFICATION.getCode(),
                    ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception, "Getting value from PendingTosSubmitNotificationList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * @return list of Business Transaction records pending to submit confirmation
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitConfirmationList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {

        try {
            List<String> pendingContractHash = getStringList(
                    PENDING_ACK_ONLINE_MERCHANDISE.getCode(),
                    ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

            List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();
            for (String contractHash : pendingContractHash) {
                BusinessTransactionRecord businessTransactionRecord = getBrokerBusinessTransactionRecordByContractHash(contractHash);
                businessTransactionRecordList.add(businessTransactionRecord);
            }

            return businessTransactionRecordList;

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingTosSubmitNotificationList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns the business transaction record by a contract hash
     *
     * @param contractHash the contract Hash/ID
     * @return the business transaction record
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBusinessTransactionRecordByContractHash(String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        try {

            return getBusinessTransactionRecord(contractHash, ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Unexpected result", "Check the cause");
        }
    }

    /**
     * This method updates the Contract Transaction Status by a given contract hash/Id
     *
     * @param contractHash the contract Hash/ID
     * @param newStatus    the new contract transaction status
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateContractTransactionStatus(String contractHash, ContractTransactionStatus newStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {

            DatabaseTable databaseTable = getAckMerchandiseTable();
            databaseTable.addStringFilter(ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, newStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantUpdateRecordException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantUpdateRecordException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Cant Update Record ", "Check the cause");

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    new StringBuilder().append("updating parameter ").append(ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME).toString(), "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns the recorded pending events
     *
     * @return list of pending events
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();

            return getPendingGenericsEvents(
                    databaseTable,
                    ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME);

        } catch (CantGetContractListException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantGetContractListException(e, "Getting events in EventStatus.PENDING", "Cannot load the table into memory");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting events in EventStatus.PENDING", "Unexpected error");
        }
    }

    /**
     * This method returns the Incoming Money Wrapper by the eventId.
     *
     * @param eventId the event ID
     * @return a IncomingMoneyEventWrapper object with the information of the Incoming Money event
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public IncomingMoneyEventWrapper getIncomingMoneyEventWrapper(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseIncomingMoneyTable();
            databaseTable.addStringFilter(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);

            return new IncomingMoneyEventWrapper(
                    record.getStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME),
                    record.getStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME),
                    record.getLongValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME),
                    CryptoCurrency.getByCode(record.getStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME)),
                    record.getStringValue(ACK_ONLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME),
                    record.getLongValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME),
                    record.getStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TRANSACTION_HASH_COLUMN_NAME)
            );

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");

        } catch (InvalidParameterException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Invalid parameter in ContractTransactionStatus");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Unexpected Result", "Check the cause");
        }
    }

    /**
     * Update a Business Transaction record information in database
     *
     * @param businessTransactionRecord the Business Transaction record with the updated information
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            String contractHash = businessTransactionRecord.getContractHash();
            databaseTable.addStringFilter(ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record = buildDatabaseTableRecord(record, businessTransactionRecord);

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Updating a BusinessTransactionRecord in ack_online_merchandise table",
                    "Unexpected result in database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Updating a BusinessTransactionRecord in ack_online_merchandise table",
                    "Unexpected error");
        }
    }

    /**
     * This method returns the event type by event Id
     *
     * @param eventId the event ID
     * @return the event type
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public String getEventType(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            return records.get(0).getStringValue(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Unexpected error");
        }

    }

    /**
     * This method returns if the contract is persisted in database.
     *
     * @param contractHash the contract Hash/ID
     * @return <code>true</code> if the contract is persisted in database. <code>false</code> otherwise
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public boolean isContractHashInDatabase(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            String contractHashFromDatabase = getValue(
                    contractHash,
                    ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

            return contractHashFromDatabase != null;

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param saleContract the object with the sale contract information to persist
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(CustomerBrokerContractSale saleContract) throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(saleContract.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(saleContract).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getAckMerchandiseTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
            databaseTableRecord = buildDatabaseTableRecord(databaseTableRecord, saleContract);

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
     * This method updates the event status by an eventId
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
            databaseTable.addStringFilter(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setStringValue(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, eventStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    new StringBuilder().append("Updating parameter ").append(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME).toString(), "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method updates the Incoming Money event status by its eventId
     *
     * @param eventId     the Incoming Money event ID
     * @param eventStatus the new event status
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateIncomingEventStatus(String eventId, EventStatus eventStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseIncomingMoneyTable();
            databaseTable.addStringFilter(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_STATUS_COLUMN_NAME, eventStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    new StringBuilder().append("Updating parameter ").append(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME).toString(), "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     *
     * @param contractPurchase        the contract purchase object
     * @param cryptoAmount            the merchandise crypto amount
     * @param merchandiseCurrencyCode the merchandise currency code
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(CustomerBrokerContractPurchase contractPurchase,
                                          long cryptoAmount,
                                          String merchandiseCurrencyCode) throws CantInsertRecordException {

        try {
            if (isContractHashInDatabase(contractPurchase.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(contractPurchase).append(" exists in database").toString());
                return;
            }
            //Get information from negotiation clauses.
            DatabaseTable databaseTable = getAckMerchandiseTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();

            databaseTableRecord = buildDatabaseTableRecord(databaseTableRecord, contractPurchase, merchandiseCurrencyCode, cryptoAmount);

            databaseTable.insertRecord(databaseTableRecord);

        } catch (CantGetCryptoAmountException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantInsertRecordException(CantGetCryptoAmountException.DEFAULT_MESSAGE, e,
                    "Persisting a Record in Database", "Cannot get the crypto amount from Negotiation");

        } catch (CantGetListClauseException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantInsertRecordException(CantGetListClauseException.DEFAULT_MESSAGE, e,
                    "Persisting a Record in Database", "Cannot get the Clauses List from Negotiation");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns the completion date from database.
     *
     * @param contractHash contract Hash
     * @return the completion date in millis
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public long getCompletionDateByContractHash(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            databaseTable.addStringFilter(ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty())
                return 0;

            checkDatabaseRecords(records);
            return records.get(0).getLongValue(ACK_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting completion date from database", "Cannot load the database table");
        }
    }

    /**
     * This method sets the completion date in the database.
     *
     * @param contractHash   contract Hash
     * @param completionDate the completion date in millis
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public void setCompletionDateByContractHash(String contractHash, long completionDate)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            databaseTable.addStringFilter(ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty())
                return;

            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setLongValue(ACK_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME, completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Setting completion date from database", "Cannot load the database table");
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
     * Returns the Ack Online Merchandise DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getAckMerchandiseTable() {
        return getDataBase().getTable(ACK_ONLINE_MERCHANDISE_TABLE_NAME);
    }

    /**
     * Returns the Ack Online Payment Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * Returns the Ack Online Incoming money event DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseIncomingMoneyTable() {
        return getDataBase().getTable(
                ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TABLE_NAME);
    }

    /**
     * This method fill a database table record in crypto customer side
     *
     * @param record           the database record
     * @param purchaseContract the purchase contract
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record,
                                                         CustomerBrokerContractPurchase purchaseContract,
                                                         String merchandiseCurrencyCode,
                                                         long cryptoAmount) throws CantGetListClauseException, CantGetCryptoAmountException {
        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, purchaseContract.getContractId());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, purchaseContract.getPublicKeyCustomer());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME, purchaseContract.getPublicKeyBroker());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, PENDING_ONLINE_MERCHANDISE_CONFIRMATION.getCode());
        record.setLongValue(ACK_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME, merchandiseCurrencyCode);

        return record;
    }

    /**
     * This method creates a database table record in crypto broker side, only for backup
     *
     * @param record       the database record to fill
     * @param saleContract the object with the sale contract information to persist
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record, CustomerBrokerContractSale saleContract) {
        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, saleContract.getContractId());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, saleContract.getPublicKeyCustomer());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME, saleContract.getPublicKeyBroker());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, PENDING_ACK_ONLINE_MERCHANDISE.getCode());

        return record;
    }

    /**
     * This method returns a complete database table record from a BusinessTransactionRecord
     *
     * @param record                    the database record to fill
     * @param businessTransactionRecord the BusinessTransactionRecord object with the information
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record, BusinessTransactionRecord businessTransactionRecord) {
        record.setStringValue(ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getBrokerPublicKey());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, businessTransactionRecord.getContractHash());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME, businessTransactionRecord.getCryptoAddress().getAddress());
        record.setLongValue(ACK_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME, businessTransactionRecord.getCryptoAmount());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getCustomerPublicKey());
        record.setLongValue(ACK_ONLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME, businessTransactionRecord.getTimestamp());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME, businessTransactionRecord.getTransactionHash());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME, businessTransactionRecord.getTransactionId());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getExternalWalletPublicKey());

        final ContractTransactionStatus contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();
        if (contractTransactionStatus != null)
            record.setStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, contractTransactionStatus.getCode());

        final CryptoCurrency cryptoCurrency = businessTransactionRecord.getCryptoCurrency();
        if (cryptoCurrency != null)
            record.setStringValue(ACK_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME, cryptoCurrency.getCode());

        final CryptoStatus cryptoStatus = businessTransactionRecord.getCryptoStatus();
        if (cryptoStatus != null)
            record.setStringValue(ACK_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());

        return record;
    }

    /**
     * Fill a database record with the IncomingMoneyEventWrapper data
     *
     * @param record                    the database record to fill
     * @param incomingMoneyEventWrapper the IncomingMoneyEventWrapper with the data
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record, IncomingMoneyEventWrapper incomingMoneyEventWrapper) {

        record.setStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME, incomingMoneyEventWrapper.getEventId());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME, incomingMoneyEventWrapper.getReceiverPublicKey());
        record.setLongValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME, incomingMoneyEventWrapper.getCryptoAmount());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME, incomingMoneyEventWrapper.getSenderPublicKey());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
        record.setLongValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME, incomingMoneyEventWrapper.getTimestamp());
        record.setStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TRANSACTION_HASH_COLUMN_NAME, incomingMoneyEventWrapper.getTransactionHash());

        CryptoCurrency cryptoCurrency = incomingMoneyEventWrapper.getCryptoCurrency();
        if (cryptoCurrency != null)
            record.setStringValue(ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME, cryptoCurrency.getCode());

        return record;

    }

    /**
     * This method check the database record result.
     *
     * @param records the list of records to check
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws UnexpectedResultReturnedFromDatabaseException {
        // Represents the maximum number of records in <code>records</code>
        // I'm gonna set this number in 1 for now, because I want to check the records object has one only result.
        int VALID_RESULTS_NUMBER = 1;
        int recordsSize;

        if (records.isEmpty())
            return;

        recordsSize = records.size();

        if (recordsSize > VALID_RESULTS_NUMBER)
            throw new UnexpectedResultReturnedFromDatabaseException(new StringBuilder().append("I excepted ").append(VALID_RESULTS_NUMBER).append(", but I got ").append(recordsSize).toString());
    }

    /**
     * This method returns a BusinessTransactionRecord  in the Crypto Customer by the parameters given.
     *
     * @param keyValue  the key to search
     * @param keyColumn the column to Search
     * @return the BusinessTransactionRecord
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private BusinessTransactionRecord getBusinessTransactionRecord(String keyValue, String keyColumn)
            throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();

            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(keyColumn, keyValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            if (records.isEmpty())
                return null;

            DatabaseTableRecord record = records.get(0);
            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));
            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(record.getStringValue(ACK_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME));
            businessTransactionRecord.setTransactionId(record.getStringValue(ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));
            businessTransactionRecord.setExternalWalletPublicKey(record.getStringValue(ACK_ONLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setCryptoAmount(record.getLongValue(ACK_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME));

            ContractTransactionStatus status = getByCode(record.getStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(status);

            String cryptoCurrencyCode = record.getStringValue(ACK_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME);
            if (cryptoCurrencyCode != null) {
                businessTransactionRecord.setCryptoCurrency(CryptoCurrency.getByCode(cryptoCurrencyCode));

                String cryptoAddressString = record.getStringValue(ACK_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME);
                CryptoAddress brokerCryptoAddress = new CryptoAddress(cryptoAddressString, businessTransactionRecord.getCryptoCurrency());
                businessTransactionRecord.setCryptoAddress(brokerCryptoAddress);
            }

            return businessTransactionRecord;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Invalid parameter in ContractTransactionStatus");
        }
    }

    /**
     * This method returns a BusinessTransactionRecord in the Crypto Broker side by the parameters given.
     *
     * @param keyValue  the key to search
     * @param keyColumn the column to Search
     * @return the BusinessTransactionRecord
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private BusinessTransactionRecord getBrokerBusinessTransactionRecord(String keyValue, String keyColumn)
            throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            ContractTransactionStatus contractTransactionStatus;
            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(keyColumn, keyValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            if (records.isEmpty())
                return null;

            DatabaseTableRecord record = records.get(0);
            businessTransactionRecord.setTransactionId(record.getStringValue(ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));
            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));
            contractTransactionStatus = getByCode(record.getStringValue(ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);

            return businessTransactionRecord;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Invalid parameter in ContractTransactionStatus");
        }
    }

    /**
     * Return a Business Transaction record given the contract Hash/ID
     *
     * @param contractHash the contract Hash/ID
     * @return the Business Transaction record
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private BusinessTransactionRecord getBrokerBusinessTransactionRecordByContractHash(String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        try {

            return getBrokerBusinessTransactionRecord(contractHash, ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Unexpected result", "Check the cause");
        }
    }

    /**
     * This method returns a CustomerOnlinePaymentRecordList according the arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return a list of BusinessTransactionRecord
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
     * This method returns a List of String with the parameter in the arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return a list of String
     */
    private List<String> getStringList(String key, String keyColumn, String valueColumn) throws CantGetContractListException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
            databaseTable.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            List<String> contractHashList = new ArrayList<>();

            if (records.isEmpty())
                return contractHashList; //There is no records in database, I'll return an empty list.

            for (DatabaseTableRecord databaseTableRecord : records) {
                String value = databaseTableRecord.getStringValue(valueColumn);
                contractHashList.add(value);
            }

            return contractHashList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e, new StringBuilder().append("Getting ").append(valueColumn).append(" based on ").append(key).toString(), "Cannot load the table into memory");
        }
    }

    /**
     * This method returns pending generic events by given parameters
     *
     * @param databaseTable the database table
     * @param statusColumn  the status column
     * @param idColumn      the event id column
     * @return a list of event IDs
     * @throws CantGetContractListException
     */
    private List<String> getPendingGenericsEvents(DatabaseTable databaseTable, String statusColumn, String idColumn)
            throws CantGetContractListException {
        try {
            List<String> eventTypeList = new ArrayList<>();

            databaseTable.addStringFilter(statusColumn, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return eventTypeList; //There is no records in database, I'll return an empty list.

            for (DatabaseTableRecord databaseTableRecord : records) {
                String eventId = databaseTableRecord.getStringValue(idColumn);
                eventTypeList.add(eventId);
            }

            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e, new StringBuilder().append("Getting events in EventStatus.PENDING in table ").append(databaseTable.getTableName()).toString(),
                    "Cannot load the table into memory");
        }
    }

    /**
     * This method returns a String value from parameters in database.
     *
     * @param key         the key to search
     * @param keyColumn   the column to search
     * @param valueColumn the column with the value
     * @return the String value
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private String getValue(String key, String keyColumn, String valueColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getAckMerchandiseTable();
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

    private boolean eventExists(String eventId) throws CantSaveEventException {
        try {
            DatabaseTable table = getDatabaseEventsTable();

            table.addStringFilter(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantSaveEventException(em.getMessage(), em, "Customer Ack Online Merchandise Transaction Event Id Not Exists",
                    new StringBuilder().append("Cant load ").append(ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME).append(" table in memory.").toString());

        } catch (Exception e) {
            throw new CantSaveEventException(e.getMessage(), FermatException.wrapException(e),
                    "Customer Ack Online Merchandise Transaction Event Id Not Exists", "unknown failure.");
        }

    }
}
