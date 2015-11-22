package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.exceptions.CantCheckAssetUserRedemptionProgressException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/11/15.
 */
public class UserRedemptionDao {
    UUID pluginId;
    Database database;
    PluginDatabaseSystem pluginDatabaseSystem;

    public UserRedemptionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        database = openDatabase();
        database.closeDatabase();

    }

    private DatabaseTable getDatabaseTable(String tableName){
        DatabaseTable assetDistributionDatabaseTable = database.getTable(tableName);
        return assetDistributionDatabaseTable;
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, UserRedemptionDatabaseConstants.USER_REDEMPTION_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the User Redemption Transaction Database", "Error in database plugin.");
        }
    }

    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            this.database=openDatabase();
            DatabaseTable databaseTable = this.database.getTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            Logger LOG = Logger.getGlobal();
            LOG.info("Distribution DAO:\nUUID:"+eventRecordID+"\n"+unixTime);
            eventRecord.setUUIDValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);
            LOG.info("record:" + eventRecord.getStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME));
            this.database.closeDatabase();
        }  catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot open or find the User Redemption database");
        } catch (CantInsertRecordException exception) {
            this.database.closeDatabase();
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in User Redemption database");
        } catch(Exception exception){
            this.database.closeDatabase();
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }

    public void updateDistributionStatusByGenesisTransaction(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        updateStringValueByStringField(distributionStatus.getCode(),
                UserRedemptionDatabaseConstants.USER_REDEMPTION_REDEMPTION_STATUS_COLUMN_NAME,
                genesisTransaction,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    private void updateStringValueByStringField(String value, String columnName, String filterValue, String filterColumn) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable=this.database.getTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME);
            databaseTable.setStringFilter(filterColumn, filterValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            if (databaseTableRecords.size() > 1){
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  filterColumn+": " + filterColumn);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(columnName, value);
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,exception, "Trying to update "+columnName,"Check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception,"Trying to update "+columnName,"Check the cause");
        } catch (Exception exception){
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception),"Trying to update "+columnName,"Check the cause");
        }
    }

    public void persistDigitalAsset(String genesisTransaction,
                                    String localStoragePath,
                                    String digitalAssetHash,
                                    String actorReceiverPublicKey,
                                    String actorReceiverBitcoinAddress)throws CantPersistDigitalAssetException {
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction);
            record.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_DIGITAL_ASSET_HASH_COLUMN_NAME, digitalAssetHash);
            record.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME, localStoragePath);
            record.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_ACTOR_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, actorReceiverPublicKey);
            record.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_REDEMPTION_STATUS_COLUMN_NAME, DistributionStatus.CHECKING_HASH.getCode());
            record.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode());
            record.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());
            record.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_ACTOR_REDEEM_POINT_BITCOIN_ADDRESS_COLUMN_NAME, actorReceiverBitcoinAddress);
            databaseTable.insertRecord(record);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception, "Persisting a redeeming genesis digital asset","Cannot open the User Redemption database");
        } catch (CantInsertRecordException exception) {
            throw new CantPersistDigitalAssetException(exception, "Persisting a redeeming genesis digital asset","Cannot insert a record in the User Redemption database");
        } catch (Exception exception){
            throw new CantPersistDigitalAssetException(exception, "Persisting a redeeming genesis digital asset","Unexpected exception");
        }
    }

    public void persistRedemptionId(String genesisTransaction, UUID distributionId) throws CantPersistsTransactionUUIDException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            if (databaseTableRecords.size() > 1){
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  "GenesisTransaction:" + genesisTransaction+ " OutgoingId:" + distributionId);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_REDEMPTION_ID_COLUMN_NAME, distributionId.toString());
            databaseTableRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_REDEMPTION_STATUS_COLUMN_NAME, TransactionStatus.TO_DELIVER.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistsTransactionUUIDException(exception, "Persisting redemptionId in database", "Cannot open or find the database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantPersistsTransactionUUIDException(FermatException.wrapException(exception), "Persisting redemptionId in database", "Unexpected exception");
        }
    }

    public boolean isGenesisTransactionRegistered(String genesisTransaction) throws CantExecuteQueryException {
        try {
            this.database=openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            this.database.closeDatabase();
            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Checking if genesis transaction exists in database.", "Cannot load table to memory.");
        }catch(Exception exception){
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Checking if genesis transaction exits in database.", "Unexpected exception");
        }
    }

    public String getActorUserPublicKeyByGenesisTransaction(String genesisTransaction) throws CantCheckAssetUserRedemptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromSelectedTableTableByFieldCode(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME,
                genesisTransaction,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_ACTOR_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public String getActorRedeemPointCryptoAddressByGenesisTransaction(String genesisTransaction) throws CantCheckAssetUserRedemptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromSelectedTableTableByFieldCode(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME,
                genesisTransaction,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_ACTOR_REDEEM_POINT_BITCOIN_ADDRESS_COLUMN_NAME,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public String getTransactionIdByGenesisTransaction(String genesisTransaction) throws CantCheckAssetUserRedemptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromSelectedTableTableByFieldCode(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME,
                genesisTransaction,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_REDEMPTION_ID_COLUMN_NAME,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public List<String> getGenesisTransactionListByCryptoStatus(CryptoStatus cryptoStatus) throws CantCheckAssetUserRedemptionProgressException {
        return getValueListFromTableByColumn(cryptoStatus.getCode(),
                UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_CRYPTO_STATUS_COLUMN_NAME,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }
    
    /**
    * This method returns a String value from the fieldCode, filtered by value in indexColumn
    * @param tableName table name
    * @param value value used as filter
    * @param fieldCode column that contains the required value
    * @param indexColumn the column filter
    * @return a String with the required value
    * @throws CantCheckAssetUserRedemptionProgressException
    * @throws UnexpectedResultReturnedFromDatabaseException
    */
    private String getStringValueFromSelectedTableTableByFieldCode(String tableName, String value, String fieldCode, String indexColumn) throws CantCheckAssetUserRedemptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database=openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(tableName);
            databaseTable.setStringFilter(indexColumn, value, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            if (databaseTableRecords.size() > 1){
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  indexColumn+":" + value);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            this.database.closeDatabase();
            String stringToReturn=databaseTableRecord.getStringValue(fieldCode);
            return stringToReturn;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Trying to get "+fieldCode,"Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Trying to get "+fieldCode,"Cannot load the database into memory");
        }
    }

    public void updateEventStatus(String eventId) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable=this.database.getTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            if (databaseTableRecords.size() > 1){
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  "Event ID:" + eventId);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.NOTIFIED.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,exception, "Trying to update "+UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME,"Check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception,"Trying to update "+UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME,"Check the cause");
        } catch (Exception exception){
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception),"Trying to update "+UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME,"Check the cause");
        }
    }

    public List<String> getPendingNetworkLayerEvents() throws CantCheckAssetUserRedemptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getPendingEventsBySource(EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION);
    }

    public List<String> getPendingCryptoRouterEvents() throws CantCheckAssetUserRedemptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getPendingEventsBySource(EventSource.ASSETS_OVER_BITCOIN_VAULT);
    }
    
    private List<String> getPendingEventsBySource(EventSource eventSource) throws CantCheckAssetUserRedemptionProgressException, UnexpectedResultReturnedFromDatabaseException {

        try {
            this.database=openDatabase();
            List<String> eventIdList=new ArrayList<>();
            DatabaseTable databaseTable = getDatabaseTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.setFilterOrder(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            for(DatabaseTableRecord databaseTableRecord : databaseTableRecords){
                String eventId=databaseTableRecord.getStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventIdList.add(eventId);
            }
            this.database.closeDatabase();
            return eventIdList;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Trying to get pending events","Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Trying to get pending events","Cannot load the database into memory");
        } catch(Exception exception){
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(FermatException.wrapException(exception), "Trying to get pending events.", "Unexpected exception");
        }
    }

    public List<String> getGenesisTransactionByAssetAcceptedStatus() throws CantCheckAssetUserRedemptionProgressException {
        return getGenesisTransactionByDistributionStatus(DistributionStatus.ASSET_ACCEPTED);
    }

    public List<String> getGenesisTransactionByAssetRejectedByHashStatus() throws CantCheckAssetUserRedemptionProgressException {
        return getGenesisTransactionByDistributionStatus(DistributionStatus.ASSET_REJECTED_BY_HASH);
    }

    public List<String> getGenesisTransactionByAssetRejectedByContractStatus() throws CantCheckAssetUserRedemptionProgressException {
        return getGenesisTransactionByDistributionStatus(DistributionStatus.ASSET_REJECTED_BY_CONTRACT);
    }

    private List<String> getGenesisTransactionByDistributionStatus(DistributionStatus distributionStatus)throws CantCheckAssetUserRedemptionProgressException{
        return getValueListFromTableByColumn(distributionStatus.getCode(),
                UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_REDEMPTION_STATUS_COLUMN_NAME,
                UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    private List<String> getValueListFromTableByColumn(String referenceValue, String table, String referenceColumn, String returningColumn)throws CantCheckAssetUserRedemptionProgressException{

        try{
            this.database=openDatabase();
            DatabaseTable databaseTable;
            List<String> returningList=new ArrayList<>();
            databaseTable = database.getTable(table);
            databaseTable.setStringFilter(referenceColumn, referenceValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            for (DatabaseTableRecord record : databaseTable.getRecords()){
                returningList.add(record.getStringValue(returningColumn));
            }
            this.database.closeDatabase();
            return returningList;
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Getting "+referenceColumn+" list", "Cannot load table to memory");
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Getting "+referenceColumn+" list", "Cannot open or find the Asset Distribution database");
        } catch(Exception exception){
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(FermatException.wrapException(exception), "Getting "+referenceColumn+" list", "Unexpected exception");
        }
    }

    public void updateDigitalAssetCryptoStatusByGenesisTransaction(String genesisTransaction, CryptoStatus cryptoStatus) throws CantCheckAssetUserRedemptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1){
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  "Genesis Transaction:" + genesisTransaction);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Updating Crypto Status.", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Updating Crypto Status ", "Cannot load the table into memory");
        } catch(Exception exception){
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(FermatException.wrapException(exception), "Updating Crypto Status.", "Unexpected exception - Transaction hash:" + genesisTransaction);
        }
    }

    public String getEventTypeById(String eventId) throws CantCheckAssetUserRedemptionProgressException, UnexpectedResultReturnedFromDatabaseException {

        try {
            this.database=openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1){
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  "Event Id" + eventId);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            this.database.closeDatabase();
            return databaseTableRecord.getStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Trying to get pending events","Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(exception, "Trying to get pending events","Cannot load the database into memory");
        } catch(Exception exception){
            this.database.closeDatabase();
            throw new CantCheckAssetUserRedemptionProgressException(FermatException.wrapException(exception), "Trying to get pending events.", "Unexpected exception");
        }
    }

    public boolean isPendingTransactions(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
        try {
            this.database=openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            this.database.closeDatabase();
            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Getting pending transactions.", "Cannot load table to memory.");
        }catch(Exception exception){
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Getting pending transactions.", "Unexpected exception");
        }
    }

    public boolean isPendingNetworkLayerEvents() throws CantExecuteQueryException {
        return isPendingEventsBySource(EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION);
    }

    public boolean isPendingIncomingCryptoEvents() throws CantExecuteQueryException {
        return isPendingEventsBySource(EventSource.CRYPTO_ROUTER);
    }

    private boolean isPendingEventsBySource(EventSource eventSource) throws CantExecuteQueryException {
        try {
            this.database=openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.setStringFilter(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            this.database.closeDatabase();
            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Getting pending events.", "Cannot load table to memory.");
        }catch(Exception exception){
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }

}
