package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetCryptoAmountException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.IncomingMoneyEventWrapper;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerAckOnlineMerchandiseBusinessTransactionDao {
    
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private ErrorManager errorManager;
    private Database database;

    public CustomerAckOnlineMerchandiseBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final ErrorManager errorManager) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database             = database            ;
        this.errorManager         = errorManager        ;
    }

    public void initialize() throws CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory databaseFactory =
                        new CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        f);
                throw new CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        z);
                throw new CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and I cannot open the database.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException(
                    CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
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
     * Returns the Ack Online Merchandise DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getAckMerchandiseTable() {
        return getDataBase().getTable(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TABLE_NAME);
    }

    /**
     * Returns the Ack Online Payment Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * Returns the Ack Online Incoming money event DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseIncomingMoneyTable() {
        return getDataBase().getTable(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TABLE_NAME);
    }

    /**
     * This method save an incoming new event in database. You can set the event Id with this method
     * @param eventType
     * @param eventSource
     * @param eventId
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource, String eventId) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            long unixTime = System.currentTimeMillis();
            eventRecord.setStringValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Ack Online Payment database");
        } catch(Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
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
     * @param eventType
     * @param eventSource
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            UUID eventRecordID = UUID.randomUUID();
            saveNewEvent(eventType, eventSource, eventRecordID.toString());

        } catch(Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method save an incoming money event in database. You can set the event Id with this method
     * @param event
     * @throws CantSaveEventException
     */
    public void saveIncomingMoneyEvent(IncomingMoneyNotificationEvent event) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseIncomingMoneyTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            IncomingMoneyEventWrapper incomingMoneyEventWrapper=new IncomingMoneyEventWrapper(
                    event);
            eventRecord=buildDatabaseTableRecord(eventRecord, incomingMoneyEventWrapper);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Ack Online Payment database");
        } catch(Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            IncomingMoneyEventWrapper incomingMoneyEventWrapper){

        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME,
                incomingMoneyEventWrapper.getEventId());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME,
                incomingMoneyEventWrapper.getReceiverPublicKey());
        record.setLongValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME,
                incomingMoneyEventWrapper.getCryptoAmount());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME,
                incomingMoneyEventWrapper.getCryptoCurrency().getCode()
        );
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME,
                incomingMoneyEventWrapper.getSenderPublicKey());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_STATUS_COLUMN_NAME,
                EventStatus.PENDING.getCode());
        record.setLongValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME, incomingMoneyEventWrapper.getTimestamp());

        return record;

    }

    /**
     * This method returns the contract transaction status
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{

            String stringContractTransactionStatus=getValue(
                    contractHash,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting the contract transaction status",
                    "Unexpected error");
        }
    }

    /**
     * This method returns a String value from parameters in database.
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
        try{
            DatabaseTable databaseTable=getAckMerchandiseTable();
            databaseTable.addStringFilter(
                    keyColumn,
                    key,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                return null;
            }
            checkDatabaseRecords(records);
            String value=records
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
        int VALID_RESULTS_NUMBER=1;
        int recordsSize;
        if(records.isEmpty()){
            return;
        }
        recordsSize=records.size();
        if(recordsSize>VALID_RESULTS_NUMBER){
            throw new UnexpectedResultReturnedFromDatabaseException("I excepted "+VALID_RESULTS_NUMBER+", but I got "+recordsSize);
        }
    }

    /**
     * This method returns the recorded pending events
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingIncomingMoneyEvents() throws
            CantGetContractListException {
        try{
            DatabaseTable databaseTable=getDatabaseIncomingMoneyTable();
            return getPendingGenericsEvents(
                    databaseTable,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_STATUS_COLUMN_NAME,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME
            );
        }catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingToSubmitCryptoList", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns pending generic events by given parameters
     * @return
     */
    private List<String> getPendingGenericsEvents(
            DatabaseTable databaseTable,
            String statusColumn,
            String idColumn) throws
            CantGetContractListException {
        try{
            List<String> eventTypeList=new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(
                    statusColumn,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return eventTypeList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                eventId=databaseTableRecord.getStringValue(
                        idColumn);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING in table "+databaseTable.getTableName(),
                    "Cannot load the table into memory");
        }
    }

    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try{
            return getBusinessTransactionRecordList(
                    ContractTransactionStatus.PENDING_ACK_ONLINE_MERCHANDISE_NOTIFICATION.getCode(),
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        }catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception, "Getting value from PendingTosSubmitNotificationList", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    public List<BusinessTransactionRecord> getPendingToSubmitConfirmationList() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException{
        try{
            List<String> pendingContractHash = getStringList(
                    ContractTransactionStatus.PENDING_ACK_ONLINE_MERCHANDISE.getCode(),
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

            List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();
            for(String contractHash : pendingContractHash){
                BusinessTransactionRecord businessTransactionRecord = getBrokerBusinessTransactionRecordByContractHash(contractHash);
                businessTransactionRecordList.add(businessTransactionRecord);
            }

            return businessTransactionRecordList;

        } catch (CantGetContractListException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,exception);

            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingTosSubmitNotificationList", "");

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    private BusinessTransactionRecord getBrokerBusinessTransactionRecordByContractHash(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            return getBrokerBusinessTransactionRecord(contractHash, CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        }catch(Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected result","Check the cause");
        }
    }

    /**
     * This method returns a CustomerOnlinePaymentRecordList according the arguments.
     * @param key String with the search key.
     * @param keyColumn String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return List<BusinessTransactionRecord>
     * @throws CantGetContractListException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private List<BusinessTransactionRecord> getBusinessTransactionRecordList(
            String key,
            String keyColumn,
            String valueColumn) throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException {
        List<String> pendingContractHash= getStringList(
                key,
                keyColumn,
                valueColumn);
        List<BusinessTransactionRecord> businessTransactionRecordList =new ArrayList<>();
        BusinessTransactionRecord businessTransactionRecord;
        for(String contractHash : pendingContractHash){
            businessTransactionRecord = getBusinessTransactionRecordByContractHash(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
    }

    /**
     * This method returns a List with the parameter in the arguments.
     * @param key
     * @param keyColumn
     * @param valueColumn
     * @return
     */
    private List<String> getStringList(
            String key,
            String keyColumn,
            String valueColumn) throws CantGetContractListException {
        try{
            DatabaseTable databaseTable=getAckMerchandiseTable();
            List<String> contractHashList=new ArrayList<>();
            String contractHash;
            databaseTable.addStringFilter(
                    keyColumn,
                    key,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return contractHashList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                contractHash=databaseTableRecord.getStringValue(valueColumn);
                contractHashList.add(contractHash);
            }
            return contractHashList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e,
                    "Getting "+valueColumn+" based on "+key,
                    "Cannot load the table into memory");
        }
    }

    /**
     * This method returns the business transaction record by a contract hash
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBusinessTransactionRecordByContractHash(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            return getBusinessTransactionRecord(contractHash, CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        }catch(Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected result","Check the cause");
        }
    }

    /**
     * This methos returns a BusinessTransactionRecord by the parameters given.
     * @param keyValue
     * @param keyColumn
     * @return
     */
    private BusinessTransactionRecord getBusinessTransactionRecord(String keyValue, String keyColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getAckMerchandiseTable();
            ContractTransactionStatus contractTransactionStatus;
            CryptoAddress brokerCryptoAddress;
            String cryptoAddressString;
            BusinessTransactionRecord businessTransactionRecord =new BusinessTransactionRecord();
            databaseTable.addStringFilter(
                    keyColumn,
                    keyValue,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            if(records.isEmpty()){
                return null;
            }
            DatabaseTableRecord record = records.get(0);
            businessTransactionRecord.setBrokerPublicKey(
                    record.getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));
            contractTransactionStatus=ContractTransactionStatus.getByCode(record.getStringValue(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);
            businessTransactionRecord.setCustomerPublicKey(
                    record.getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(
                    record.getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME
                    ));
            businessTransactionRecord.setTransactionId(
                    record.getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));
            cryptoAddressString=record.getStringValue(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME);
            //I going to set the money as bitcoin in this version
            brokerCryptoAddress=new CryptoAddress(cryptoAddressString, CryptoCurrency.BITCOIN);
            businessTransactionRecord.setCryptoAddress(brokerCryptoAddress);
            businessTransactionRecord.setExternalWalletPublicKey(
                    record.getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setCryptoAmount(
                    record.getLongValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME));
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

    private BusinessTransactionRecord getBrokerBusinessTransactionRecord(String keyValue, String keyColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getAckMerchandiseTable();
            ContractTransactionStatus contractTransactionStatus;
            BusinessTransactionRecord businessTransactionRecord =new BusinessTransactionRecord();
            databaseTable.addStringFilter(keyColumn, keyValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            if(records.isEmpty()){
                return null;
            }
            DatabaseTableRecord record = records.get(0);
            businessTransactionRecord.setTransactionId(record.getStringValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));

            businessTransactionRecord.setContractHash(record.getStringValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));

            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));

            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                    ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));

            contractTransactionStatus = ContractTransactionStatus.getByCode(record.getStringValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);

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
     * This method updates the Contract Transaction Status by a given contract hash/Id
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
        try{
            updateRecordStatus(contractHash,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractTransactionStatus.getCode());
        } catch (CantUpdateRecordException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantUpdateRecordException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,"Cant Update Record ","Check the cause");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method update a database record by contract hash.
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

        try{
            DatabaseTable databaseTable=getAckMerchandiseTable();
            databaseTable.addStringFilter(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(statusColumnName, newStatus);
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Updating parameter "+statusColumnName,"");
        }
    }

    /**
     * This method returns the recorded pending events
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws CantGetContractListException,UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            return getPendingGenericsEvents(
                    databaseTable,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME
            );
        }catch (CantGetContractListException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting events in EventStatus.PENDING\"",
                    "Unexpected error");
        }
    }

    /**
     * This method returns the Incoming Money Wrapper by the eventId.
     * @param eventId
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public IncomingMoneyEventWrapper getIncomingMoneyEventWrapper(
            String eventId)
            throws
            UnexpectedResultReturnedFromDatabaseException{
        try{
            DatabaseTable databaseTable=getDatabaseIncomingMoneyTable();
            databaseTable.addStringFilter(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            IncomingMoneyEventWrapper incomingMoneyEventWrapper=new IncomingMoneyEventWrapper(
                    record.getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_EVENT_ID_COLUMN_NAME),
                    record.getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME),
                    record.getLongValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME),
                    CryptoCurrency.getByCode(
                            record.getStringValue(
                                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME)),
                    record.getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME),
                    record.getLongValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME)
            );
            return incomingMoneyEventWrapper;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Invalid parameter in ContractTransactionStatus");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    /**
     * This method returns the business transaction record by the broker public key.
     * @param brokerPublicKey
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBusinessTransactionRecordByBrokerPublicKey(
            String brokerPublicKey) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            return getBusinessTransactionRecord(
                    brokerPublicKey,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        }catch(Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getAckMerchandiseTable();
            String contractHash= businessTransactionRecord.getContractHash();
            databaseTable.addStringFilter(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record=buildDatabaseTableRecord(record, businessTransactionRecord);
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected results in database");
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected error");
        }
    }

    /**
     * This method returns a complete database table record from a BusinessTransactionRecord
     * @param record
     * @param businessTransactionRecord
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            BusinessTransactionRecord businessTransactionRecord){
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getBrokerPublicKey());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                businessTransactionRecord.getContractHash());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                businessTransactionRecord.getContractTransactionStatus().getCode());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME,
                businessTransactionRecord.getCryptoAddress().getAddress());
        record.setLongValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME,
                businessTransactionRecord.getCryptoAmount());
        CryptoStatus cryptoStatus=businessTransactionRecord.getCryptoStatus();
        if(cryptoStatus!=null){
            record.setStringValue(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME,
                    cryptoStatus.getCode());
        }
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getCustomerPublicKey());
        record.setLongValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME,
                businessTransactionRecord.getTimestamp());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME,
                businessTransactionRecord.getTransactionHash());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                businessTransactionRecord.getTransactionId());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getExternalWalletPublicKey());

        return record;
    }

    /**
     * This method returns the event type by event Id
     * @param eventId
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public String getEventType(String eventId)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting value from database",
                    "Unexpected error");
        }

    }

    /**
     * This method returns TRUE if the contract is persisted in database.
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public boolean isContractHashInDatabase(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            String contractHashFromDatabase=getValue(
                    contractHash,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
            return contractHashFromDatabase!=null;
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     * @param customerBrokerContractSale
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale)
            throws CantInsertRecordException {
        try{
            DatabaseTable databaseTable=getAckMerchandiseTable();
            DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
            databaseTableRecord= buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractSale
            );
            databaseTable.insertRecord(databaseTableRecord);
        }catch (CantInsertRecordException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,
                    exception, "Error in persistContractInDatabase", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method creates a database table record in crypto broker side, only for backup
     * @param record
     * @param customerBrokerContractSale
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractSale customerBrokerContractSale){
        UUID transactionId=UUID.randomUUID();
        record.setUUIDValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ACK_ONLINE_MERCHANDISE.getCode());

        return record;
    }

    /**
     * This method updates the event status by an eventId
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
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+ CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     * @param customerBrokerContractPurchase
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation)
            throws CantInsertRecordException {

        try{
            DatabaseTable databaseTable=getAckMerchandiseTable();
            DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
            databaseTableRecord= buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractPurchase,
                    customerBrokerPurchaseNegotiation
            );

            databaseTable.insertRecord(databaseTableRecord);
        } catch (CantGetCryptoAmountException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInsertRecordException(
                    CantGetCryptoAmountException.DEFAULT_MESSAGE,
                    e,
                    "Persisting a Record in Database",
                    "Cannot get the crypto amount from Negotiation");
        } catch (CantGetListClauseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInsertRecordException(
                    CantGetListClauseException.DEFAULT_MESSAGE,
                    e,
                    "Persisting a Record in Database",
                    "Cannot get the Clauses List from Negotiation");
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,
                    exception,
                    "Unexpected error",
                    "Check the cause");
        }

    }

    /**
     * This method creates a database table record in crypto broker side, only for backup
     * @param record
     * @param customerBrokerContractPurchase
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws
            CantGetListClauseException, CantGetCryptoAmountException {
        UUID transactionId=UUID.randomUUID();
        record.setUUIDValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ONLINE_MERCHANDISE_CONFIRMATION.getCode());
        //Get information from negotiation clauses.
        Collection<Clause> negotiationClauses=customerBrokerPurchaseNegotiation.getClauses();
        long cryptoAmount=getCryptoAmountFromNegotiationClauses(negotiationClauses);
        record.setLongValue(
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.ACK_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME,
                cryptoAmount);

        return record;
    }

    private long getCryptoAmountFromNegotiationClauses(
            Collection<Clause> negotiationClauses) throws
            CantGetCryptoAmountException {
        try{
            long cryptoAmount;
            for(Clause clause : negotiationClauses){
                if(clause.getType().equals(ClauseType.CUSTOMER_CURRENCY_QUANTITY)){
                    cryptoAmount=parseToLong(clause.getValue());
                    return cryptoAmount;
                }
            }
            throw new CantGetCryptoAmountException(
                    "The Negotiation clauses doesn't include the broker crypto amount");
        }  catch (InvalidParameterException e) {
            throw new CantGetCryptoAmountException(
                    e,
                    "Getting the broker crypto amount",
                    "There is an error parsing a String to long.");
        }
    }

    /**
     * This method parse a String object to a long object
     * @param stringValue
     * @return
     * @throws InvalidParameterException
     */
    public long parseToLong(String stringValue) throws InvalidParameterException {
        if(stringValue==null){
            throw new InvalidParameterException("Cannot parse a null string value to long");
        }else{
            try{
                return Long.valueOf(stringValue);
            }catch (Exception exception){
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE,
                        FermatException.wrapException(exception),
                        "Parsing String object to long",
                        "Cannot parse "+stringValue+" string value to long");
            }

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
            DatabaseTable databaseTable=getAckMerchandiseTable();
            databaseTable.addStringFilter(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
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
                    .getLongValue(CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME);
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
            DatabaseTable databaseTable=getAckMerchandiseTable();
            databaseTable.addStringFilter(
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
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
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME,
                    completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Setting completion date from database",
                    "Cannot load the database table");
        }
    }

}
