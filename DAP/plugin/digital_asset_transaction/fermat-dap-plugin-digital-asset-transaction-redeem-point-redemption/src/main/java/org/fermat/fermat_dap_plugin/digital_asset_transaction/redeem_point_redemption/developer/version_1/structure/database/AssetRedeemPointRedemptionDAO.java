package org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyStartedException;
import org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionEventListException;
import org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantPersistTransactionMetadataException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class AssetRedeemPointRedemptionDAO {

    //VARIABLE DECLARATION
    private Database database;

    //CONSTRUCTORS
    public AssetRedeemPointRedemptionDAO(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantOpenDatabaseException, DatabaseNotFoundException {
        database = pluginDatabaseSystem.openDatabase(pluginId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_DATABASE);
    }

    //PUBLIC METHODS

    /*
    * Event Recorded Table's Actions.
    *
    */
    public void saveNewEvent(FermatEvent event) throws CantSaveEventException {
        String eventType = event.getEventType().getCode();
        String eventSource = event.getSource().getCode();

        String context = "Event Type : " + eventType + " - Event Source: " + eventSource;
        try {
            DatabaseTable databaseTable = this.database.getTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());

            databaseTable.insertRecord(eventRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in Asset Reception database");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public List<String> getPendingActorAssetUserEvents() throws CantLoadAssetRedemptionEventListException {
        return getPendingEventsBySource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER);
    }

    public List<String> getPendingAssetTransmissionEvents() throws CantLoadAssetRedemptionEventListException {
        return getPendingEventsBySource(EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION);
    }

    public List<String> getPendingCryptoRouterEvents() throws CantLoadAssetRedemptionEventListException {
        return getPendingEventsBySource(EventSource.CRYPTO_ROUTER);
    }

    public EventType getEventDapTypeById(String id) throws CantLoadAssetRedemptionEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventType.getByCode(getStringFieldByEventId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_EVENT_COLUMN_NAME, id));
    }

    public com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType getEventBchTypeById(String id) throws CantLoadAssetRedemptionEventListException, InvalidParameterException, RecordsNotFoundException {
        return com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType.getByCode(getStringFieldByEventId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_EVENT_COLUMN_NAME, id));
    }

    public EventSource getEventSourceById(String id) throws CantLoadAssetRedemptionEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventSource.getByCode(getStringFieldByEventId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_SOURCE_COLUMN_NAME, id));
    }

    public EventStatus getEventStatusById(String id) throws CantLoadAssetRedemptionEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventStatus.getByCode(getStringFieldByEventId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_STATUS_COLUMN_NAME, id));
    }

    public void updateEventStatus(EventStatus status, String eventId) throws CantLoadAssetRedemptionEventListException, RecordsNotFoundException {
        updateStringFieldByEventId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_STATUS_COLUMN_NAME, status.getCode(), eventId);
    }

    public boolean isPendingActorAssetUserEvents() throws CantLoadAssetRedemptionEventListException {
        return isPendingEventsBySource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER);
    }

    /*
    * Metadata Table's Actions.
    *
    */
    public void newTransaction(String transactionId, String senderId, String receiverId, DistributionStatus transactionStatus, CryptoStatus cryptoStatus) throws CantPersistTransactionMetadataException {
        String context = "Transaction Genesis Address: " + transactionId
                + "Distribution Status: " + transactionStatus.getCode()
                + "Sender Public Key: " + senderId
                + "Receiver Public Key: " + receiverId
                + "CryptoStatus: " + cryptoStatus.getCode();
        try {
            if (isTransactionRegistered(transactionId)) {
                updateTransaction(transactionId, senderId, receiverId, transactionStatus, cryptoStatus);
            } else {
                persistTransaction(transactionId, senderId, receiverId, transactionStatus, cryptoStatus);
            }
        } catch (Exception e) {
            throw new CantPersistTransactionMetadataException(FermatException.wrapException(e), context, "Database Error.");
        }
    }

    /*
    * Metadata Table's Actions.
    *
    */

    public void updateTransaction(String transactionId, String senderId, String receiverId, DistributionStatus transactionStatus, CryptoStatus cryptoStatus) throws CantLoadTableToMemoryException, CantUpdateRecordException {
        DatabaseTable databaseTable = this.database.getTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TABLE_NAME);
        databaseTable.addStringFilter(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();
        DatabaseTableRecord metadataRecord = databaseTable.getRecords().get(0);

        metadataRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_STATUS_COLUMN_NAME, transactionStatus.getCode());
        metadataRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
        metadataRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_SENDER_KEY_COLUMN_NAME, senderId);
        metadataRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_RECEIVER_KEY_COLUMN_NAME, receiverId);
        metadataRecord.setLongValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
        databaseTable.updateRecord(metadataRecord);
    }

    public void persistTransaction(String transactionId, String senderId, String receiverId, DistributionStatus transactionStatus, CryptoStatus cryptoStatus) throws CantPersistTransactionMetadataException, TransactionAlreadyStartedException, CantInsertRecordException {
        DatabaseTable databaseTable = this.database.getTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TABLE_NAME);
        DatabaseTableRecord metadataRecord = databaseTable.getEmptyRecord();

        metadataRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_ID_COLUMN_NAME, transactionId);
        metadataRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_STATUS_COLUMN_NAME, transactionStatus.getCode());
        metadataRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
        metadataRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_SENDER_KEY_COLUMN_NAME, senderId);
        metadataRecord.setStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_RECEIVER_KEY_COLUMN_NAME, receiverId);
        metadataRecord.setLongValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());

        databaseTable.insertRecord(metadataRecord);
    }

    public void updateTransactionStatusById(DistributionStatus status, String transactionId) throws RecordsNotFoundException, org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        updateStringFieldByTransactionId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_STATUS_COLUMN_NAME, status.getCode(), transactionId);
    }

    public void updateTransactionCryptoStatusById(CryptoStatus status, String transactionId) throws RecordsNotFoundException, org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        updateStringFieldByTransactionId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_CRYPTO_STATUS_COLUMN_NAME, status.getCode(), transactionId);
    }

    //PRIVATE METHODS
    private boolean isPendingEventsBySource(EventSource eventSource) throws CantLoadAssetRedemptionEventListException {
        return !getPendingEventsBySource(eventSource).isEmpty();
    }

    private void updateStringFieldByTransactionId(String columnName, String value, String transactionId) throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException, RecordsNotFoundException {
        String context = "Column Name: " + columnName + " - Value: " + value + " - Id: " + transactionId;
        try {
            DatabaseTable metadataTable;
            metadataTable = database.getTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TABLE_NAME);
            metadataTable.addStringFilter(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            metadataTable.loadToMemory();
            if (metadataTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }
            for (DatabaseTableRecord record : metadataTable.getRecords()) {
                record.setStringValue(columnName, value);
                metadataTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException(exception, context, "Cannot update record.");
        }
    }

    private void updateStringFieldByEventId(String columnName, String value, String id) throws CantLoadAssetRedemptionEventListException, RecordsNotFoundException {
        String context = "Column Name: " + columnName + " - Id: " + id;
        try {
            DatabaseTable eventRecordedTable;
            eventRecordedTable = database.getTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_NAME);
            eventRecordedTable.addStringFilter(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            eventRecordedTable.loadToMemory();
            if (eventRecordedTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }
            for (DatabaseTableRecord record : eventRecordedTable.getRecords()) {
                record.setStringValue(columnName, value);
                eventRecordedTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetRedemptionEventListException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new CantLoadAssetRedemptionEventListException(exception, context, "Cannot update record.");
        }
    }

    private String getStringFieldByTransactionId(String columnName, String id) throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException, RecordsNotFoundException {
        try {
            String context = "Column Name: " + columnName + " - Id: " + id;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                return record.getStringValue(columnName);
            }
            throw new RecordsNotFoundException(null, context, "");
        } catch (CantLoadTableToMemoryException exception) {
            throw new org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }


    private String getStringFieldByEventId(String columnName, String id) throws CantLoadAssetRedemptionEventListException, RecordsNotFoundException {
        try {
            String context = "Column Name: " + columnName + " - Id: " + id;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.addStringFilter(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                return record.getStringValue(columnName);
            }
            throw new RecordsNotFoundException(null, context, "");
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetRedemptionEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new CantLoadAssetRedemptionEventListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }

    private List<String> getPendingEventsBySource(EventSource eventSource) throws CantLoadAssetRedemptionEventListException {
        try {
            DatabaseTable eventsRecordedTable;
            eventsRecordedTable = database.getTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_NAME);

            DatabaseTableFilter statusFilter = eventsRecordedTable.getEmptyTableFilter();
            statusFilter.setColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_STATUS_COLUMN_NAME);
            statusFilter.setValue(EventStatus.PENDING.getCode());
            statusFilter.setType(DatabaseFilterType.EQUAL);

            DatabaseTableFilter sourceFilter = eventsRecordedTable.getEmptyTableFilter();
            sourceFilter.setColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
            sourceFilter.setValue(eventSource.getCode());
            sourceFilter.setType(DatabaseFilterType.EQUAL);

            List<DatabaseTableFilter> filters = new ArrayList<>();
            filters.add(statusFilter);
            filters.add(sourceFilter);

            eventsRecordedTable.setFilterGroup(
                    eventsRecordedTable.getNewFilterGroup(filters,
                            new ArrayList<DatabaseTableFilterGroup>(),
                            DatabaseFilterOperator.AND));

            eventsRecordedTable.addFilterOrder(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            eventsRecordedTable.loadToMemory();
            List<String> eventIdList = new ArrayList<>();
            for (DatabaseTableRecord record : eventsRecordedTable.getRecords()) {
                eventIdList.add(record.getStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_ID_COLUMN_NAME));
            }
            return eventIdList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetRedemptionEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new CantLoadAssetRedemptionEventListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }

    private boolean isPendingTransactionByCryptoStatus(CryptoStatus status) throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        return getGenesisTransactionByCryptoStatus(status).isEmpty();
    }

    private boolean isTransactionRegistered(String transactionId) {
        try {
            getStringFieldByTransactionId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_STATUS_COLUMN_NAME, transactionId);
        } catch (org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException | RecordsNotFoundException e) {
            return false;
        }
        return true;
    }

    private List<String> getGenesisTransactionByCryptoStatus(CryptoStatus status) throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        try {
            DatabaseTable metadataTable;
            metadataTable = database.getTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TABLE_NAME);
            metadataTable.addStringFilter(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_CRYPTO_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
            metadataTable.addFilterOrder(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            metadataTable.loadToMemory();
            List<String> genesisTransactionList = new ArrayList<>();
            for (DatabaseTableRecord record : metadataTable.getRecords()) {
                genesisTransactionList.add(record.getStringValue(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_ID_COLUMN_NAME));
            }
            return genesisTransactionList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }
    //GETTER AND SETTERS

    public Database getDatabase() {
        return database;
    }

    public boolean isPendingSubmitTransactions() throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        return isPendingTransactionByCryptoStatus(CryptoStatus.PENDING_SUBMIT);
    }

    public boolean isOnCryptoNetworkTransactions() throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        return isPendingTransactionByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
    }

    public boolean isOnBlockChainTransactions() throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        return isPendingTransactionByCryptoStatus(CryptoStatus.ON_BLOCKCHAIN);
    }

    public String getSenderPublicKeyById(String transactionId) throws RecordsNotFoundException, org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        return getStringFieldByTransactionId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_SENDER_KEY_COLUMN_NAME, transactionId);
    }

    public String getReceiverPublicKeyById(String transactionId) throws RecordsNotFoundException, org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        return getStringFieldByTransactionId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_RECEIVER_KEY_COLUMN_NAME, transactionId);
    }

    public CryptoStatus getTransactionCryptoStatusById(String transactionId) throws RecordsNotFoundException, org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException, InvalidParameterException {
        return CryptoStatus.getByCode(getStringFieldByTransactionId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_CRYPTO_STATUS_COLUMN_NAME, transactionId));
    }

    public DistributionStatus getTransactionStatusById(String transactionId) throws RecordsNotFoundException, org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException, InvalidParameterException {
        return DistributionStatus.getByCode(getStringFieldByTransactionId(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_STATUS_COLUMN_NAME, transactionId));
    }

    public List<String> getPendingSubmitGenesisTransactions() throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        return getGenesisTransactionByCryptoStatus(CryptoStatus.PENDING_SUBMIT);
    }

    public List<String> getOnCryptoNetworkGenesisTransactions() throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        return getGenesisTransactionByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
    }

    public List<String> getOnBlockChainGenesisTransactions() throws org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException {
        return getGenesisTransactionByCryptoStatus(CryptoStatus.REVERSED_ON_BLOCKCHAIN);
    }

    //INNER CLASSES
}
