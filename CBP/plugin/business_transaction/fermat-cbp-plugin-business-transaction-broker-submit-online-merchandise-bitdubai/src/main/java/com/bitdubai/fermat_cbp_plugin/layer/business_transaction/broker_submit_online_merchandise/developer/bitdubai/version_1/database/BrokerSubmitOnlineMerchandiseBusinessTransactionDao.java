package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class BrokerSubmitOnlineMerchandiseBusinessTransactionDao {
    
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private ErrorManager errorManager;
    private Database database;

    public BrokerSubmitOnlineMerchandiseBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final ErrorManager errorManager) {

        this.errorManager         = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database             = database            ;
    }

    public void initialize() throws CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory databaseFactory =
                        new BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {
                errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        f);
                throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        z);
                throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                    CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
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
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TABLE_NAME);
    }

    /**
     * Returns the Submit Online Merchandise Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME);
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
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
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
            eventRecord.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Submit Online Merchandise database");
        } catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
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
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            String stringContractTransactionStatus=getValue(
                    contractHash,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException( exception,
                    "Getting the contract transaction status",
                    "Unexpected error" );
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
            if(records.isEmpty()){
                return null;
            }
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(valueColumn);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
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
        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
            databaseTableRecord= buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractSale
            );
            databaseTable.insertRecord(databaseTableRecord);
        }catch(CantInsertRecordException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,
                    exception,"Error in persistContractInDatabase","");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,exception,
                    "Unexpected error",
                    "Check the cause");
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
        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
            databaseTableRecord= buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractPurchase
            );
            databaseTable.insertRecord(databaseTableRecord);
        }catch(CantInsertRecordException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,
                    exception,"Error in persistContractInDatabase","");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method persists a basic record in database
     * @param customerBrokerContractSale
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale,
            String brokerCryptoAddress,
            String cryptoWalletPublicKey,
            long cryptoAmount,
            String cbpWalletPublicKey,
            BigDecimal referencePrice,
            BlockchainNetworkType blockchainNetworkType,String intraActorPK)
            throws CantInsertRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
            databaseTableRecord= buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractSale,
                    brokerCryptoAddress,
                    cryptoWalletPublicKey,
                    cryptoAmount,
                    cbpWalletPublicKey,
                    referencePrice,
                    blockchainNetworkType,intraActorPK
            );
            databaseTable.insertRecord(databaseTableRecord);
        }catch(CantInsertRecordException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,exception,"Error in persistContractInDatabase","");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method persists a basic record in database
     * @param customerBrokerContractPurchase
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractPurchase customerBrokerContractPurchase,
            String brokerCryptoAddress,
            String cryptoWalletPublicKey,
            long cryptoAmount,
            String cbpWalletPublicKey,
            BigDecimal referencePrice)
            throws CantInsertRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
            databaseTableRecord= buildDatabaseTableRecord(
                    databaseTableRecord,
                    customerBrokerContractPurchase,
                    brokerCryptoAddress,
                    cryptoWalletPublicKey,
                    cryptoAmount,
                    cbpWalletPublicKey,
                    referencePrice
            );
            databaseTable.insertRecord(databaseTableRecord);
        }catch(CantInsertRecordException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,
                    exception,"Error in persistContractInDatabase","");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,exception,
                    "Unexpected error",
                    "Check the cause");
        }
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
            String brokerCryptoAddress,
            String walletPublicKey,
            long cryptoAmount,
            String cbpWalletPublicKey,
            BigDecimal referencePrice,
            BlockchainNetworkType blockchainNetworkType,String intraActorPk) {

        UUID transactionId=UUID.randomUUID();
        record.setUUIDValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ONLINE_DE_STOCK.getCode());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME,
                brokerCryptoAddress);
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME,
                intraActorPk);
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_WALLET_PUBLIC_KEY_COLUMN_NAME,
                walletPublicKey);
        record.setLongValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME,
                cryptoAmount);
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME,
                cbpWalletPublicKey
        );
        record.setDoubleValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME,
                referencePrice.doubleValue()
        );
        if(blockchainNetworkType==null){
            blockchainNetworkType=BlockchainNetworkType.getDefaultBlockchainNetworkType();
        }
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME,
                blockchainNetworkType.getCode()
        );
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
            long cryptoAmount,
            String cbpWalletPublicKey,
            BigDecimal referencePrice) {

        UUID transactionId=UUID.randomUUID();
        record.setUUIDValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ONLINE_DE_STOCK.getCode());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME,
                brokerCryptoAddress);
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_WALLET_PUBLIC_KEY_COLUMN_NAME,
                walletPublicKey);
        record.setLongValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME,
                cryptoAmount);
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME,
                cbpWalletPublicKey
        );
        record.setDoubleValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME,
                referencePrice.doubleValue()
        );
        return record;
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
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ONLINE_DE_STOCK.getCode());

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
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractPurchase.getContractId());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyCustomer());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractPurchase.getPublicKeyBroker());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE_CONFIRMATION.getCode());

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
        try{
            return getBrokerOnlinePaymentRecordList(
                    ContractTransactionStatus.PENDING_ONLINE_DE_STOCK.getCode(),
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME
            );
        }catch(CantGetContractListException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE,
                    exception,"Error in persistContractInDatabase","");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
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
        try{
            return getBrokerOnlinePaymentRecordList(
                    ContractTransactionStatus.CRYPTO_MERCHANDISE_SUBMITTED.getCode(),
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME
            );
        }catch (CantGetContractListException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,"Getting value from getPendingCryptoTransactionList","");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
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
    private List<BusinessTransactionRecord> getBrokerOnlinePaymentRecordList(
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
            businessTransactionRecord = getBrokerBusinessTransactionRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
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
            businessTransactionRecord = getBrokerBusinessTransactionRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }
        return businessTransactionRecordList;
    }

    private BusinessTransactionRecord getCustomerBusinessTransactionRecord(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            ContractTransactionStatus contractTransactionStatus;
            BusinessTransactionRecord businessTransactionRecord =new BusinessTransactionRecord();

            databaseTable.addStringFilter(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);

            businessTransactionRecord.setTransactionId(record.getStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));

            businessTransactionRecord.setContractHash(record.getStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));

            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(record.getStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME));

            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                    SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));

            contractTransactionStatus=ContractTransactionStatus.getByCode(record.getStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);

            return businessTransactionRecord;

        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Invalid parameter in ContractTransactionStatus");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Unexpected Result");
        }
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
    public BusinessTransactionRecord getBrokerBusinessTransactionRecord(String contractHash)
            throws
            UnexpectedResultReturnedFromDatabaseException {

        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            ContractTransactionStatus contractTransactionStatus;
            CryptoAddress brokerCryptoAddress;
            String cryptoAddressString;
            BigDecimal referencePrice;
            double referencePriceFromDatabase;
            BusinessTransactionRecord businessTransactionRecord =new BusinessTransactionRecord();
            databaseTable.addStringFilter(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            businessTransactionRecord.setBrokerPublicKey(
                    record.getStringValue(
                            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));
            contractTransactionStatus=ContractTransactionStatus.getByCode(record.getStringValue(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(contractTransactionStatus);
            businessTransactionRecord.setCustomerPublicKey(
                    record.getStringValue(
                            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(record.getStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME));
            businessTransactionRecord.setTransactionId(
                    record.getStringValue(
                            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));
            cryptoAddressString=record.getStringValue(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME);
            //I going to set the money as bitcoin in this version
            brokerCryptoAddress=new CryptoAddress(cryptoAddressString, CryptoCurrency.BITCOIN);
            businessTransactionRecord.setCryptoAddress(brokerCryptoAddress);

            businessTransactionRecord.setActorPublicKey(record.getStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME));

            businessTransactionRecord.setExternalWalletPublicKey(
                    record.getStringValue(
                            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_ONLINE_MERCHANDISE_CRYPTO_WALLET_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setCryptoAmount(
                    record.getLongValue(
                            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME));
            businessTransactionRecord.setCBPWalletPublicKey(
                    record.getStringValue(
                            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_ONLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME
                    )
            );
            referencePriceFromDatabase=record.getDoubleValue(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME
            );
            referencePrice=BigDecimal.valueOf(referencePriceFromDatabase);
            businessTransactionRecord.setPriceReference(
                    referencePrice
            );
            //Setting the blockchain network type.
            BlockchainNetworkType blockchainNetworkType;
            String blockchainNetworkTypeString = record.getStringValue(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME
            );
            if(blockchainNetworkTypeString==null||blockchainNetworkTypeString.isEmpty()){
                blockchainNetworkType=BlockchainNetworkType.getDefaultBlockchainNetworkType();
            } else {
                blockchainNetworkType=BlockchainNetworkType.getByCode(blockchainNetworkTypeString);
            }
            businessTransactionRecord.setBlockchainNetworkType(blockchainNetworkType);
            return businessTransactionRecord;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Invalid parameter in ContractTransactionStatus");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Unexpected Result");
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
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            databaseTable.addStringFilter(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
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

    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            String contractHash= businessTransactionRecord.getContractHash();
            databaseTable.addStringFilter(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record=buildDatabaseTableRecord(record, businessTransactionRecord);
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected results in database");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
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
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getBrokerPublicKey());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                businessTransactionRecord.getContractHash());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                businessTransactionRecord.getContractTransactionStatus().getCode());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME,
                businessTransactionRecord.getCryptoAddress().getAddress());
        record.setLongValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME,
                businessTransactionRecord.getCryptoAmount());
        //I need to check if crypto status is null
        CryptoStatus cryptoStatus=businessTransactionRecord.getCryptoStatus();
        if(cryptoStatus!=null){
            record.setStringValue(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME,
                    cryptoStatus.getCode());
        }
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getCustomerPublicKey());
        record.setLongValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME,
                businessTransactionRecord.getTimestamp());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME,
                businessTransactionRecord.getTransactionHash());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                businessTransactionRecord.getTransactionId());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_WALLET_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getExternalWalletPublicKey());
        record.setDoubleValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME,
                businessTransactionRecord.getPriceReference().doubleValue()
        );
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME,
                businessTransactionRecord.getCBPWalletPublicKey()
        );

        return record;
    }

    /**
     * This method returns the pending to submit crypto transactions
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingToSubmitCryptoList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try{
            return getStringList(
                    ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE.getCode(),
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        }catch(CantGetContractListException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,"Getting value from PendingToSubmitCryptoList","");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
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
        try{
            return getBrokerOnlinePaymentRecordList(
                    ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE_NOTIFICATION.getCode(),
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        }catch(CantGetContractListException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingTosSubmitNotificationList","");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public List<BusinessTransactionRecord> getPendingToSubmitConfirmationList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try{
            return getCustomerOnlinePaymentRecordList(
                    ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE_CONFIRMATION.getCode(),
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        }catch(CantGetContractListException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,
                    "Getting value from PendingTosSubmitNotificationList","");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns the PENDING_SUBMIT crypto status transactions
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try{
            return getBrokerOnlinePaymentRecordList(
                    CryptoStatus.PENDING_SUBMIT.getCode(),
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        }catch(CantGetContractListException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    exception,"Getting value from getPendingToSubmitCryptoStatusList","");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns the ON_CRYPTO_NETWORK crypto status transactions
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getOnCryptoNetworkCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try{
            return getBrokerOnlinePaymentRecordList(
                    CryptoStatus.ON_CRYPTO_NETWORK.getCode(),
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        }catch(CantGetContractListException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException("Error",exception,"Getting value from getOnCryptoNetworkCryptoStatusList","");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    /**
     * This method returns the ON_BLOCKCHAIN crypto status transactions
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getOnBlockchainkCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetContractListException {
        try{
            return getBrokerOnlinePaymentRecordList(
                    CryptoStatus.ON_BLOCKCHAIN.getCode(),
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
        }catch(CantGetContractListException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantGetContractListException("Error",exception,"Getting value from getOnBlockchainkCryptoStatusList","");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
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
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(
                            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                    SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting value from database",
                    "Unexpected error");
        }

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
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
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
                        BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                                SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Getting events in EventStatus.PENDING\"",
                    "Unexpected error");
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
        try{
            String contractHashFromDatabase=getValue(
                    contractHash,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);
            return contractHashFromDatabase!=null;
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected error",
                    "Check the cause");
        }
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
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
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
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            databaseTable.addStringFilter(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
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
                    .getLongValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME);
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
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            databaseTable.addStringFilter(
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
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
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.
                            SUBMIT_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME,
                    completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Setting completion date from database",
                    "Cannot load the database table");
        }
    }

}
