package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
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
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.ReceptionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 12/10/15.
 */
public class AssetReceptionDao {
    UUID pluginId;
    Database database;
    PluginDatabaseSystem pluginDatabaseSystem;

    public AssetReceptionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        database = openDatabase();
        database.closeDatabase();

    }

    private DatabaseTable getDatabaseTable(String tableName){
        DatabaseTable assetReceptionDatabaseTable = database.getTable(tableName);
        return assetReceptionDatabaseTable;
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, AssetReceptionDatabaseConstants.ASSET_RECEPTION_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Reception Transaction Database", "Error in database plugin.");
        }
    }

    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            this.database=openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            Logger LOG = Logger.getGlobal();
            LOG.info("ASSET DAO:\nUUID:"+eventRecordID+"\n"+unixTime);
            eventRecord.setUUIDValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);
            LOG.info("record:"+eventRecord.getStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN));
            this.database.closeDatabase();
        }  catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot open or find the Asset Reception database");
        } catch (CantInsertRecordException exception) {
            this.database.closeDatabase();
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Asset Reception database");
        } catch(Exception exception){
            this.database.closeDatabase();
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
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
            databaseTable = database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.toString(), DatabaseFilterType.EQUAL);
            databaseTable.setStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource.getCode(), DatabaseFilterType.EQUAL);
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

    public void persistDigitalAsset(String genesisTransaction,
                                    String localStoragePath,
                                    String digitalAssetHash,
                                    String senderId)throws CantPersistDigitalAssetException {
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction);
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_DIGITAL_ASSET_HASH_COLUMN_NAME, digitalAssetHash);
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME, localStoragePath);
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_ACTOR_ASSET_ISSUER_ID_COLUMN_NAME, senderId);
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_STATUS_COLUMN_NAME, DistributionStatus.CHECKING_HASH.getCode());
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode());
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());
            databaseTable.insertRecord(record);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception, "Persisting a receiving genesis digital asset","Cannot open the Asset Reception database");
        } catch (CantInsertRecordException exception) {
            throw new CantPersistDigitalAssetException(exception, "Persisting a receiving genesis digital asset","Cannot insert a record in the Asset Reception database");
        } catch (Exception exception){
            throw new CantPersistDigitalAssetException(exception, "Persisting a receiving genesis digital asset","Unexpected exception");
        }
    }

    public void updateReceptionStatusByGenesisTransaction(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        updateStringValueByStringField(distributionStatus.getCode(),
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_STATUS_COLUMN_NAME,
                genesisTransaction,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    private void updateStringValueByStringField(String value, String columnName, String filterValue, String filterColumn) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable=this.database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME);
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

}
