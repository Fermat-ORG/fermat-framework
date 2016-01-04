package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
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

    private Database database;

    public BrokerSubmitOfflineMerchandiseBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database             = database            ;
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

                throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                        CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

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
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Submit Online Merchandise database");
        } catch(Exception exception){
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns the actual contract transaction status
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{

            String stringContractTransactionStatus=getValue(
                    contractHash,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
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
            DatabaseTable databaseTable=getDatabaseSubmitTable();
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
     * This method persists a basic record in database
     * @param customerBrokerContractSale
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale,
            String walletPublicKey,
            long amount,
            String cbpWalletPublicKey,
            BigDecimal referencePrice,
            CurrencyType currencyType)
            throws CantInsertRecordException {

        DatabaseTable databaseTable=getDatabaseSubmitTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord= buildDatabaseTableRecord(
                databaseTableRecord,
                customerBrokerContractSale,
                walletPublicKey,
                amount,
                cbpWalletPublicKey,
                referencePrice,
                currencyType
        );
        databaseTable.insertRecord(databaseTableRecord);
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractPurchase object.
     * This record is not complete, is missing the transaction hash,  and the crypto status,
     * this values will after sending the crypto amount, also the timestamp is set at this moment.
     * @param record
     * @param customerBrokerContractSale
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractSale customerBrokerContractSale,
            String walletPublicKey,
            long amount,
            String cbpWalletPublicKey,
            BigDecimal referencePrice,
            CurrencyType currencyType) {

        UUID transactionId=UUID.randomUUID();
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
        record.setLongValue(
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
                currencyType.getCode()
        );
        return record;
    }

    /**
     * This method returns a BusinessTransactionRecord list from the pending Crypto De Stock transactions
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingDeStockTransactionList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getCustomerOfflinePaymentRecordList(
                ContractTransactionStatus.PENDING_OFFLINE_DE_STOCK.getCode(),
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME
        );
    }

    /**
     * This method returns a BusinessTransactionRecord List according the arguments.
     * @param key String with the search key.
     * @param keyColumn String with the key column name.
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
        List<String> pendingContractHash= getStringList(
                key,
                keyColumn,
                valueColumn);
        List<BusinessTransactionRecord> businessTransactionRecordList =new ArrayList<>();
        BusinessTransactionRecord businessTransactionRecord;
        for(String contractHash : pendingContractHash){
            businessTransactionRecord = getBusinessTransactionRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
    }

    /**
     * This method returns a <code>List<String></code> with the parameter in the arguments.
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
            DatabaseTable databaseTable=getDatabaseSubmitTable();
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
     * This method returns the BusinessTransactionRecord from a given contractHashId.
     * The BusinessTransactionRecord contains all the Submit Online Merchandise table record.
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBusinessTransactionRecord(String contractHash)
            throws
            UnexpectedResultReturnedFromDatabaseException {

        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            ContractTransactionStatus contractTransactionStatus;
            CryptoAddress brokerCryptoAddress;
            String cryptoAddressString;
            BigDecimal referencePrice;
            CurrencyType paymentType;
            FiatCurrency currencyType;
            double referencePriceFromDatabase;
            BusinessTransactionRecord businessTransactionRecord =new BusinessTransactionRecord();
            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            businessTransactionRecord.setBrokerPublicKey(
                    record.getStringValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));
            contractTransactionStatus=ContractTransactionStatus.getByCode(record.getStringValue(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);
            businessTransactionRecord.setCustomerPublicKey(
                    record.getStringValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(contractHash);
            businessTransactionRecord.setTransactionId(
                    record.getStringValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));
            //Represents the external wallet
            businessTransactionRecord.setExternalWalletPublicKey(
                    record.getStringValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME));
            //Represents the amount
            businessTransactionRecord.setCryptoAmount(
                    record.getLongValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_AMOUNT_COLUMN_NAME));
            businessTransactionRecord.setCBPWalletPublicKey(
                    record.getStringValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME
                    )
            );
            referencePriceFromDatabase=record.getDoubleValue(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME
            );
            referencePrice=BigDecimal.valueOf(referencePriceFromDatabase);
            businessTransactionRecord.setPriceReference(
                    referencePrice
            );
            paymentType = CurrencyType.getByCode(
                    record.getStringValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_PAYMENT_TYPE_COLUMN_NAME));
            businessTransactionRecord.setPaymentType(
                    paymentType
            );
            currencyType = FiatCurrency.getByCode(
                    record.getStringValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_CURRENCY_TYPE_COLUMN_NAME));
            businessTransactionRecord.setCurrencyType(currencyType);
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

    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            String contractHash= businessTransactionRecord.getContractHash();
            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
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
     * This method returns a complete database table record from a BusinessTransactionRecord
     * @param record
     * @param businessTransactionRecord
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            BusinessTransactionRecord businessTransactionRecord){
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
        record.setDoubleValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME,
                businessTransactionRecord.getCryptoAmount()
        );
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getCBPWalletPublicKey()
        );
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CURRENCY_TYPE_COLUMN_NAME,
                businessTransactionRecord.getCurrencyType().getCode()
        );
        record.setStringValue(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_PAYMENT_TYPE_COLUMN_NAME,
                businessTransactionRecord.getPaymentType().getCode()
        );

        return record;
    }

    /**
     * This method returns the pending to submit notifications transactions
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getCustomerOfflinePaymentRecordList(
                ContractTransactionStatus.PENDING_SUBMIT_OFFLINE_MERCHANDISE_NOTIFICATION.getCode(),
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                        SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                        SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
    }

    /**
     * This method returns a BusinessTransactionRecord list from the pending Crypto transactions
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingCryptoTransactionList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        return getCustomerOnlinePaymentRecordList(
                ContractTransactionStatus.OFFLINE_MERCHANDISE_SUBMITTED.getCode(),
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME
        );
    }

    /**
     * This method returns a BusinessTransactionRecord List according the arguments.
     * @param key String with the search key.
     * @param keyColumn String with the key column name.
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
        List<String> pendingContractHash= getStringList(
                key,
                keyColumn,
                valueColumn);
        List<BusinessTransactionRecord> businessTransactionRecordList =new ArrayList<>();
        BusinessTransactionRecord businessTransactionRecord;
        for(String contractHash : pendingContractHash){
            businessTransactionRecord = getBusinessTransactionRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
    }

    /**
     * This method returns the pending event list.
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            List<String> eventTypeList=new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
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
                        BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        }
    }

    /**
     * This method returns the event type recorded in database by event id
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
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    /**
     * This method checks if the given contract hash exists in database.
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public boolean isContractHashInDatabase(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        String contractHashFromDatabase=getValue(
                contractHash,
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                        SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                        SUBMIT_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        return contractHashFromDatabase!=null;
    }

    /**
     * This method updates the event status.
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
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractPurchase in crypto broker side, only for backup
     * @param customerBrokerContractPurchase
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractPurchase customerBrokerContractPurchase)
            throws CantInsertRecordException {

        DatabaseTable databaseTable=getDatabaseSubmitTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord= buildDatabaseTableRecord(
                databaseTableRecord,
                customerBrokerContractPurchase
        );
        databaseTable.insertRecord(databaseTableRecord);
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
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
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
                ContractTransactionStatus.PENDING_OFFLINE_DE_STOCK.getCode());

        return record;
    }

}
