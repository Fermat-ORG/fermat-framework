package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
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
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CustomerOnlinePaymentRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.exceptions.CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class BrokerAckOfflinePaymentBusinessTransactionDao {
    
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public BrokerAckOfflinePaymentBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database             = database            ;
    }

    public void initialize() throws CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory databaseFactory =
                        new BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException(
                        CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

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
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{

            String stringContractTransactionStatus=getValue(
                    contractHash,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        }
    }

    public boolean isContractHashInDatabase(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        String contractHashFromDatabase=getValue(
                contractHash,
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
        return contractHashFromDatabase!=null;
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
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.addStringFilter(
                    keyColumn,
                    key,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
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
            eventRecord.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Ack Online Payment database");
        } catch(Exception exception){
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
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
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

        DatabaseTable databaseTable=getDatabaseContractTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord= buildDatabaseTableRecord(
                databaseTableRecord,
                customerBrokerContractSale
        );
        databaseTable.insertRecord(databaseTableRecord);
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
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_OFFLINE_PAYMENT_CONFIRMATION.getCode());

        return record;
    }

    public CustomerOnlinePaymentRecord getCustomerOnlinePaymentRecordByContractHash(
            String contractHash)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        return getCustomerOnlinePaymentRecord(
                contractHash,
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                        ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME);

    }

    /**
     * This methos returns a CustomerOnlinePaymentRecord by the parameters given.
     * @param keyValue
     * @param keyColumn
     * @return
     */
    private CustomerOnlinePaymentRecord getCustomerOnlinePaymentRecord(
            String keyValue,
            String keyColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            ContractTransactionStatus contractTransactionStatus;
            long paymentAmount;
            CurrencyType paymentType;
            FiatCurrency currencyType;
            CustomerOnlinePaymentRecord customerOnlinePaymentRecord=new CustomerOnlinePaymentRecord();
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
            customerOnlinePaymentRecord.setBrokerPublicKey(
                    record.getStringValue(
                            BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME));
            customerOnlinePaymentRecord.setContractHash(record.getStringValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));
            contractTransactionStatus=ContractTransactionStatus.getByCode(record.getStringValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            customerOnlinePaymentRecord.setContractTransactionStatus(contractTransactionStatus);
            customerOnlinePaymentRecord.setCustomerPublicKey(
                    record.getStringValue(
                            BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            customerOnlinePaymentRecord.setTransactionHash(
                    record.getStringValue(
                            BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME
                    ));
            customerOnlinePaymentRecord.setTransactionId(
                    record.getStringValue(
                            BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME));
            paymentAmount=record.getLongValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_PAYMENT_AMOUNT_COLUMN_NAME);
            customerOnlinePaymentRecord.setPaymentAmount(paymentAmount);
            paymentType=CurrencyType.getByCode(record.getStringValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_PAYMENT_TYPE_COLUMN_NAME));
            customerOnlinePaymentRecord.setPaymentType(paymentType);
            currencyType= FiatCurrency.getByCode(record.getStringValue(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.
                            ACK_OFFLINE_PAYMENT_CURRENCY_TYPE_COLUMN_NAME));
            customerOnlinePaymentRecord.setCurrencyType(currencyType);
            return customerOnlinePaymentRecord;
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
     * This method updates the ContractTransactionStatus by a contractHash
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
        updateRecordStatus(contractHash,
                BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                contractTransactionStatus.getCode());
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
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.addStringFilter(
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
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

}
