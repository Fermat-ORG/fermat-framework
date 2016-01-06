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
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.exceptions.CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.IncomingMoneyEventWrapper;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class BrokerAckOnlinePaymentBusinessTransactionDao {
    
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public BrokerAckOnlinePaymentBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database             = database            ;
    }

    public void initialize() throws CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                BrokerAckOnlinePaymentBusinessTransactionDatabaseFactory databaseFactory =
                        new BrokerAckOnlinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(
                        CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(
                    CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
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
    private DatabaseTable getDatabaseContractTable() {
        return getDataBase().getTable(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TABLE_NAME);
    }

    /**
     * Returns the Ack Online Payment Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * Returns the Ack Online Incoming money event DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseIncomingMoneyTable() {
        return getDataBase().getTable(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_TABLE_NAME);
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
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
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
        DatabaseTable databaseTable=getDatabaseIncomingMoneyTable();
        return getPendingGenericsEvents(
                databaseTable,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_STATUS_COLUMN_NAME,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME
                );
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

    /**
     * This method returns the recorded pending events
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws CantGetContractListException {
        DatabaseTable databaseTable=getDatabaseEventsTable();
        return getPendingGenericsEvents(
                databaseTable,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME
                );
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
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    public List<String> getPendingToAckCryptoList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getStringList(
                ContractTransactionStatus.PENDING_PAYMENT.getCode(),
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
    }

    /*public List<BusinessTransactionRecord> getPendingToSubmitCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getBusinessTransactionRecordList(
                CryptoStatus.PENDING_SUBMIT.getCode(),
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
    }*/

    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getBusinessTransactionRecordList(
                ContractTransactionStatus.PENDING_ACK_ONLINE_PAYMENT_NOTIFICATION.getCode(),
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
    }

    public List<BusinessTransactionRecord> getPendingToSubmitConfirmList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getBusinessTransactionRecordList(
                ContractTransactionStatus.PENDING_ACK_ONLINE_PAYMENT_CONFIRMATION.getCode(),
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
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
     * This method returns a BusinessTransactionRecord
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    /*public List<BusinessTransactionRecord> getPendingCryptoTransactionList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getBusinessTransactionRecordList(
                ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED.getCode(),
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME
        );
    }*/

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
            DatabaseTable databaseTable=getDatabaseContractTable();
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

    public boolean isContractHashInDatabase(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        String contractHashFromDatabase=getValue(
                contractHash,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
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
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     * @param customerBrokerContractPurchase
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractPurchase customerBrokerContractPurchase)
            throws CantInsertRecordException {

        DatabaseTable databaseTable=getDatabaseContractTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord= buildDatabaseTableRecord(
                databaseTableRecord,
                customerBrokerContractPurchase
        );
        databaseTable.insertRecord(databaseTableRecord);
    }

    /**
     * This method persists a basic record in database
     * @param customerBrokerContractPurchase
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            String brokerCryptoAddress,
            String walletPublicKey,
            long cryptoAmount)
            throws CantInsertRecordException {

        DatabaseTable databaseTable=getDatabaseContractTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord= buildDatabaseTableRecord(
                databaseTableRecord,
                customerBrokerContractPurchase,
                brokerCryptoAddress,
                walletPublicKey,
                cryptoAmount
        );
        databaseTable.insertRecord(databaseTableRecord);
    }

    public IncomingMoneyEventWrapper getIncomingMoneyEventWrapper(
            String eventId)
            throws
            UnexpectedResultReturnedFromDatabaseException{
        try{
            DatabaseTable databaseTable=getDatabaseIncomingMoneyTable();
            databaseTable.addStringFilter(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            IncomingMoneyEventWrapper incomingMoneyEventWrapper=new IncomingMoneyEventWrapper(
                    record.getStringValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME),
                    record.getStringValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME),
                    record.getLongValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME),
                    CryptoCurrency.getByCode(
                            record.getStringValue(
                                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME)),
                    record.getStringValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME),
                    record.getLongValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME)
                    );
            return incomingMoneyEventWrapper;
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
     * This method returns a BusinessTransactionRecord by the parameters given.
     * @param keyValue
     * @param keyColumn
     * @return
     */
    private BusinessTransactionRecord getBusinessTransactionRecord(
            String keyValue,
            String keyColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
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
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME));
            contractTransactionStatus=ContractTransactionStatus.getByCode(record.getStringValue(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);
            businessTransactionRecord.setCustomerPublicKey(
                    record.getStringValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(
                    record.getStringValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME
                    ));
            businessTransactionRecord.setTransactionId(
                    record.getStringValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME));
            cryptoAddressString=record.getStringValue(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                            ACK_ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME);
            //I going to set the money as bitcoin in this version
            brokerCryptoAddress=new CryptoAddress(cryptoAddressString, CryptoCurrency.BITCOIN);
            businessTransactionRecord.setCryptoAddress(brokerCryptoAddress);
            businessTransactionRecord.setExternalWalletPublicKey(
                    record.getStringValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setCryptoAmount(
                    record.getLongValue(
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                                    ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME));
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

    public BusinessTransactionRecord getBusinessTransactionRecordByContractHash(
            String contractHash)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        return getBusinessTransactionRecord(
                contractHash,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                        ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME);

    }

    public BusinessTransactionRecord getBusinessTransactionRecordByCustomerPublicKey(
            String customerPublicKey) throws UnexpectedResultReturnedFromDatabaseException {
        return getBusinessTransactionRecord(
                customerPublicKey,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.
                        ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
    }

    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            String contractHash= businessTransactionRecord.getContractHash();
            databaseTable.addStringFilter(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record=buildDatabaseTableRecord(record, businessTransactionRecord);
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected results in database");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     * @param customerBrokerContractSale
     * @throws CantInsertRecordException
     */
    /*public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale)
            throws CantInsertRecordException {

        DatabaseTable databaseTable=getDatabaseContractTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord= buildDatabaseTableRecord(
                databaseTableRecord,
                customerBrokerContractSale
        );
        databaseTable.insertRecord(databaseTableRecord);
    }*/

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
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ACK_ONLINE_PAYMENT.getCode());

        return record;
    }

    /**
     * This method creates a database table record in crypto broker side, only for backup
     * @param record
     * @param customerBrokerContractPurchase
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractPurchase customerBrokerContractPurchase){
        UUID transactionId=UUID.randomUUID();
        record.setUUIDValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ONLINE_PAYMENT_CONFIRMATION.getCode());

        return record;
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
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getBrokerPublicKey());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                businessTransactionRecord.getContractHash());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                businessTransactionRecord.getContractTransactionStatus().getCode());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME,
                businessTransactionRecord.getCryptoAddress().getAddress());
        record.setLongValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME,
                businessTransactionRecord.getCryptoAmount());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                businessTransactionRecord.getCryptoStatus().getCode());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getCustomerPublicKey());
        record.setLongValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TIMESTAMP_COLUMN_NAME,
                businessTransactionRecord.getTimestamp());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME,
                businessTransactionRecord.getTransactionHash());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                businessTransactionRecord.getTransactionId());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getExternalWalletPublicKey());

        return record;
    }
    /**
     * This method creates a database table record from a CustomerBrokerContractPurchase object.
     * This record is not complete, is missing the transaction hash,  and the crypto status,
     * this values will after sending the crypto amount, also the timestamp is set at this moment.
     * @param record
     * @param customerBrokerContractPurchase
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            String brokerCryptoAddress,
            String walletPublicKey,
            long cryptoAmount) {

        UUID transactionId=UUID.randomUUID();
        record.setUUIDValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_PAYMENT.getCode());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME,
                brokerCryptoAddress);
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME,
                walletPublicKey);
        record.setLongValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME,
                cryptoAmount);
        return record;
    }

    /**
     * This method persists in an existing record in database the transaction UUID from
     * IntraActorCryptoTransactionManager by the contract hash.
     * @param contractHash
     * @param cryptoTransactionUUID
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void persistsCryptoTransactionUUID(String contractHash,
                                    UUID cryptoTransactionUUID) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {

        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.addStringFilter(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setUUIDValue(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME,
                    cryptoTransactionUUID);
            record.setStringValue(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME,
                    CryptoStatus.PENDING_SUBMIT.getCode());
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Persisting crypto transaction in database",
                    "There was an unexpected result in database");
        }
    }

    public void updateContractTransactionStatus(String contractHash,
                                                ContractTransactionStatus contractTransactionStatus)
            throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        updateRecordStatus(contractHash,
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
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
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
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

    public void updateEventStatus(
            String eventId,
            EventStatus eventStatus) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+ BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
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
            eventRecord.setStringValue(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
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

    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            IncomingMoneyEventWrapper incomingMoneyEventWrapper){

        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME,
                incomingMoneyEventWrapper.getEventId());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME,
                incomingMoneyEventWrapper.getReceiverPublicKey());
        record.setLongValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME,
                incomingMoneyEventWrapper.getCryptoAmount());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME,
                incomingMoneyEventWrapper.getCryptoCurrency().getCode()
        );
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME,
                incomingMoneyEventWrapper.getSenderPublicKey());
        record.setStringValue(
                BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_STATUS_COLUMN_NAME,
                EventStatus.PENDING.getCode());
        record.setLongValue(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TIMESTAMP_COLUMN_NAME, incomingMoneyEventWrapper.getTimestamp());

        return record;

    }
    
}
