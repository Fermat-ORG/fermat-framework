package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
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

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.ReceptionStatus;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.exceptions.CantCheckAssetReceptionProgressException;

import java.util.ArrayList;
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


    }

    private DatabaseTable getDatabaseTable(String tableName) {
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
            this.database = openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            Logger LOG = Logger.getGlobal();
            LOG.info("ASSET DAO:\nUUID:" + eventRecordID + "\n" + unixTime);
            eventRecord.setUUIDValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);
            LOG.info("record:" + eventRecord.getStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN));

        } catch (CantExecuteDatabaseOperationException exception) {

            throw new CantSaveEventException(exception, "Saving new event.", "Cannot open or find the Asset Reception database");
        } catch (CantInsertRecordException exception) {

            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Asset Reception database");
        } catch (Exception exception) {

            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }

    public boolean isPendingNetworkLayerEvents() throws CantExecuteQueryException {
        return isPendingEventsBySource(EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION);
    }

    public boolean isPendingIncomingCryptoEvents() throws CantExecuteQueryException {
        return isPendingEventsBySource(EventSource.CRYPTO_ROUTER);
    }

    public boolean isAcceptedAssets() throws CantExecuteQueryException {
        return isAssetsByReceptionStatus(ReceptionStatus.ASSET_ACCEPTED);
    }

    public boolean isRejectedByContract() throws CantExecuteQueryException {
        return isAssetsByReceptionStatus(ReceptionStatus.REJECTED_BY_CONTRACT);
    }

    public boolean isRejectedByHash() throws CantExecuteQueryException {
        return isAssetsByReceptionStatus(ReceptionStatus.REJECTED_BY_HASH);
    }

    private boolean isAssetsByReceptionStatus(ReceptionStatus receptionStatus) throws CantExecuteQueryException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_STATUS_COLUMN_NAME, receptionStatus.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Getting assets by reception status.", "Cannot load table to memory.");
        } catch (Exception exception) {

            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Getting assets by reception status.", "Unexpected exception");
        }
    }

    public String getActorUserPublicKeyByGenesisTransaction(String genesisTransaction) throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromSelectedTableTableByFieldCode(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME,
                genesisTransaction,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_SENDER_ID_COLUMN_NAME,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public String getTransactionIdByGenesisTransaction(String genesisTransaction) throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromSelectedTableTableByFieldCode(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME,
                genesisTransaction,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_ID_COLUMN_NAME,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public List<String> getGenesisTransactionListByCryptoStatus(CryptoStatus cryptoStatus) throws CantCheckAssetReceptionProgressException {
        return getValueListFromTableByColumn(cryptoStatus.getCode(),
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_CRYPTO_STATUS_COLUMN_NAME,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public List<String> getGenesisTransactionByReceptionStatus(ReceptionStatus receptionStatus) throws CantCheckAssetReceptionProgressException {
        return getValueListFromTableByColumn(receptionStatus.getCode(),
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_STATUS_COLUMN_NAME,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    private List<String> getValueListFromTableByColumn(String referenceValue, String table, String referenceColumn, String returningColumn) throws CantCheckAssetReceptionProgressException {

        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            List<String> returningList = new ArrayList<>();
            databaseTable = database.getTable(table);
            databaseTable.addStringFilter(referenceColumn, referenceValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                returningList.add(record.getStringValue(returningColumn));
            }

            return returningList;
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Getting " + referenceColumn + " list", "Cannot load table to memory");
        } catch (CantExecuteDatabaseOperationException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Getting " + referenceColumn + " list", "Cannot open or find the Asset Reception database");
        } catch (Exception exception) {

            throw new CantCheckAssetReceptionProgressException(FermatException.wrapException(exception), "Getting " + referenceColumn + " list", "Unexpected exception");
        }
    }

    public String getSenderIdByGenesisTransaction(String genesisTransaction) throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromSelectedTableTableByFieldCode(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME,
                genesisTransaction,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_SENDER_ID_COLUMN_NAME,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public Actors getSenderTypeByGenesisTransaction(String genesisTransaction) throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return Actors.getByCode(getStringValueFromSelectedTableTableByFieldCode(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME,
                genesisTransaction,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_SENDER_TYPE_COLUMN_NAME,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME));
    }
    /**
     * This method returns a String value from the fieldCode, filtered by value in indexColumn
     *
     * @param tableName   table name
     * @param value       value used as filter
     * @param fieldCode   column that contains the required value
     * @param indexColumn the column filter
     * @return a String with the required value
     * @throws CantCheckAssetReceptionProgressException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private String getStringValueFromSelectedTableTableByFieldCode(String tableName, String value, String fieldCode, String indexColumn) throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(tableName);
            databaseTable.addStringFilter(indexColumn, value, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {

                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", indexColumn + ":" + value);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }

            String stringToReturn = databaseTableRecord.getStringValue(fieldCode);
            return stringToReturn;
        } catch (CantExecuteDatabaseOperationException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Trying to get " + fieldCode, "Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Trying to get " + fieldCode, "Cannot load the database into memory");
        }
    }

    public String getEventTypeById(String eventId) throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {

        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1) {

                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Event Id" + eventId);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }

            return databaseTableRecord.getStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        } catch (CantExecuteDatabaseOperationException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Trying to get pending events", "Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Trying to get pending events", "Cannot load the database into memory");
        } catch (Exception exception) {

            throw new CantCheckAssetReceptionProgressException(FermatException.wrapException(exception), "Trying to get pending events.", "Unexpected exception");
        }
    }

    public List<String> getPendingNetworkLayerEvents() throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getPendingEventsBySource(EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION);
    }

    public List<String> getIncomingCryptoEvents() throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getPendingEventsBySource(EventSource.CRYPTO_ROUTER);
    }

    private List<String> getPendingEventsBySource(EventSource eventSource) throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {

        try {
            this.database = openDatabase();
            List<String> eventIdList = new ArrayList<>();
            DatabaseTable databaseTable = getDatabaseTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            for (DatabaseTableRecord databaseTableRecord : databaseTableRecords) {
                String eventId = databaseTableRecord.getStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventIdList.add(eventId);
            }

            return eventIdList;
        } catch (CantExecuteDatabaseOperationException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Trying to get pending events", "Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Trying to get pending events", "Cannot load the database into memory");
        } catch (Exception exception) {

            throw new CantCheckAssetReceptionProgressException(FermatException.wrapException(exception), "Trying to get pending events.", "Unexpected exception");
        }
    }

    private boolean isPendingEventsBySource(EventSource eventSource) throws CantExecuteQueryException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();


            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {

            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }

    public boolean isPendingTransactions(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Getting pending transactions.", "Cannot load table to memory.");
        } catch (Exception exception) {

            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Getting pending transactions.", "Unexpected exception");
        }
    }

    public boolean isGenesisTransactionRegistered(String genesisTransaction) throws CantExecuteQueryException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Checking if genesis transaction exists in database.", "Cannot load table to memory.");
        } catch (Exception exception) {

            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Checking if genesis transaction exits in database.", "Unexpected exception");
        }
    }

    public void persistDigitalAsset(String genesisTransaction,
                                    String localStoragePath,
                                    String digitalAssetHash,
                                    String senderId,
                                    Actors actorType) throws CantPersistDigitalAssetException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction);
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_DIGITAL_ASSET_HASH_COLUMN_NAME, digitalAssetHash);
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME, localStoragePath);
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_SENDER_ID_COLUMN_NAME, senderId);
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_SENDER_TYPE_COLUMN_NAME, actorType.getCode());
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_STATUS_COLUMN_NAME, ReceptionStatus.RECEIVING.getCode());
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode());
            record.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());

            databaseTable.insertRecord(record);
        } catch (CantExecuteDatabaseOperationException exception) {
            throw new CantPersistDigitalAssetException(exception, "Persisting a receiving genesis digital asset", "Cannot open the Asset Reception database");
        } catch (CantInsertRecordException exception) {
            throw new CantPersistDigitalAssetException(exception, "Persisting a receiving genesis digital asset", "Cannot insert a record in the Asset Reception database");
        } catch (Exception exception) {
            throw new CantPersistDigitalAssetException(exception, "Persisting a receiving genesis digital asset", "Unexpected exception");
        }
    }

    public void updateReceptionStatusByGenesisTransaction(ReceptionStatus receptionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        updateStringValueByStringField(receptionStatus.getCode(),
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_STATUS_COLUMN_NAME,
                genesisTransaction,
                AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }


    private void updateStringValueByStringField(String value, String columnName, String filterValue, String filterColumn) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME);
            databaseTable.addStringFilter(filterColumn, filterValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {

                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", filterColumn + ": " + filterColumn);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(columnName, value);
            databaseTable.updateRecord(databaseTableRecord);

        } catch (CantExecuteDatabaseOperationException exception) {

            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Trying to update " + columnName, "Check the cause");
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to update " + columnName, "Check the cause");
        } catch (Exception exception) {

            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Trying to update " + columnName, "Check the cause");
        }
    }

    public void persistReceptionId(String genesisTransaction, UUID distributionId) throws CantPersistsTransactionUUIDException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {

                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "GenesisTransaction:" + genesisTransaction + " OutgoingId:" + distributionId);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_ID_COLUMN_NAME, distributionId.toString());
            databaseTableRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_STATUS_COLUMN_NAME, ReceptionStatus.RECEIVING.getCode());
            databaseTable.updateRecord(databaseTableRecord);

        } catch (CantExecuteDatabaseOperationException exception) {

            throw new CantPersistsTransactionUUIDException(exception, "Persisting distributionId in database", "Cannot open or find the database");
        } catch (Exception exception) {

            throw new CantPersistsTransactionUUIDException(FermatException.wrapException(exception), "Persisting distributionId in database", "Unexpected exception");
        }
    }

    public void updateEventStatus(String eventId) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {

                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Event ID:" + eventId);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.NOTIFIED.getCode());
            databaseTable.updateRecord(databaseTableRecord);

        } catch (CantExecuteDatabaseOperationException exception) {

            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME, "Check the cause");
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME, "Check the cause");
        } catch (Exception exception) {

            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Trying to update " + AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME, "Unexpected exception");
        }
    }

    public void updateDigitalAssetCryptoStatusByGenesisTransaction(String genesisTransaction, CryptoStatus cryptoStatus) throws CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME);
            databaseTable.addStringFilter(AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1) {

                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Genesis Transaction:" + genesisTransaction);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetReceptionDatabaseConstants.ASSET_RECEPTION_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);

        } catch (CantExecuteDatabaseOperationException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Updating Crypto Status.", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantCheckAssetReceptionProgressException(exception, "Updating Crypto Status ", "Cannot load the table into memory");
        } catch (Exception exception) {

            throw new CantCheckAssetReceptionProgressException(FermatException.wrapException(exception), "Updating Crypto Status.", "Unexpected exception - Transaction hash:" + genesisTransaction);
        }
    }

}
