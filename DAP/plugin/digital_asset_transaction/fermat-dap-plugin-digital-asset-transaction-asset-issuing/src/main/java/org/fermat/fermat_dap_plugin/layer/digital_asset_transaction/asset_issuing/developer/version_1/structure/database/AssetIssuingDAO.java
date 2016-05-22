package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.exceptions.CantCreateDigitalAssetTransactionException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.DigitalAssetIssuingVault;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.IssuingRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/03/16.
 */
public class AssetIssuingDAO {

    //VARIABLE DECLARATION
    private final UUID pluginId;
    private final Database database;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final DigitalAssetIssuingVault vault;

    //CONSTRUCTORS

    public AssetIssuingDAO(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, DigitalAssetIssuingVault vault) throws CantExecuteDatabaseOperationException {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.vault = vault;
        database = openDatabase();
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
            DatabaseTable databaseTable = this.database.getTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());

            databaseTable.insertRecord(eventRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in Asset Appropriation Event Table");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public void notifyEvent(String eventId) throws RecordsNotFoundException, CantUpdateRecordException, CantLoadTableToMemoryException {
        updateRecordsForTableByFilter(getDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME), AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.NOTIFIED.getCode(), AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
    }

    /*
    Asset Issuing Methods
     */
    public void startIssuing(DigitalAsset assetToIssue, int assetQuantity, BlockchainNetworkType networkType, String btcWalletPK, String issuerWalletPk) throws CantCreateDigitalAssetTransactionException {
        String context = "Asset : " + assetToIssue + " - Quantity: " + assetQuantity + " - Network Type: " + networkType.getCode() + " - WalletPk: " + btcWalletPK;
        try {
            vault.persistDigitalAssetInLocalStorage(assetToIssue);
            DatabaseTable databaseTable = getIssuingTable();
            DatabaseTableRecord newRecord = databaseTable.getEmptyRecord();

            newRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, assetToIssue.getPublicKey());
            newRecord.setIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_COMPLETED_COLUMN_NAME, 0);
            newRecord.setIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_PROCESSED_COLUMN_NAME, 0);
            newRecord.setIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_TO_GENERATE_COLUMN_NAME, assetQuantity);
            newRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_NETWORK_TYPE_COLUMN_NAME, networkType.getCode());
            newRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_BTC_WALLET_PUBLIC_KEY_COLUMN_NAME, btcWalletPK);
            newRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUER_WALLET_PUBLIC_KEY_COLUMN_NAME, issuerWalletPk);
            newRecord.setLongValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_CREATION_DATE_COLUMN_NAME, System.currentTimeMillis());
            newRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME, IssuingStatus.ISSUING.getCode());
            newRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_PROCESSING_COLUMN_NAME, Boolean.FALSE.toString());

            databaseTable.insertRecord(newRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception, context, "Cannot insert a record in Asset Appropriation Event Table");
        } catch (Exception exception) {
            throw new CantCreateDigitalAssetTransactionException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public synchronized void newMetadata(DigitalAssetMetadata metadata, UUID outgoingId) throws CantCreateDigitalAssetFileException, CantCreateDigitalAssetTransactionException {
        String context = "Metadata : " + metadata;
        try {
            UUID transactionId = UUID.randomUUID();
            vault.persistDigitalAssetMetadataInLocalStorage(metadata, transactionId.toString());
            DatabaseTable databaseTable = getMetadataTable();
            DatabaseTableRecord newRecord = databaseTable.getEmptyRecord();
            newRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ID_COLUMN_NAME, transactionId.toString());
            newRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_OUTGOING_ID_COLUMN_NAME, outgoingId.toString());
            newRecord.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ISSUING_STATUS_COLUMN_NAME, IssuingStatus.SENDING_CRYPTO.getCode());
            newRecord.setLongValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_CREATION_TIME_COLUMN_NAME, System.currentTimeMillis());
            assetProcessed(metadata.getDigitalAsset().getPublicKey());
            databaseTable.insertRecord(newRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantCreateDigitalAssetTransactionException(exception, context, "Cannot insert a record in Asset Appropriation Event Table");
        } catch (Exception exception) {
            throw new CantCreateDigitalAssetTransactionException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public synchronized void assetProcessed(String assetPk) throws CantCreateDigitalAssetTransactionException {
        String context = "Asset : " + assetPk;
        try {
            DatabaseTable databaseTable = getIssuingTable();
            databaseTable.addStringFilter(AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPk, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = records.get(0);
            int assetsProcessed = record.getIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_PROCESSED_COLUMN_NAME);
            int assetsToGenerate = record.getIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_TO_GENERATE_COLUMN_NAME);

            record.setIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_PROCESSED_COLUMN_NAME, ++assetsProcessed);
            if (assetsProcessed >= assetsToGenerate) {
                record.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME, IssuingStatus.PROCESSED.getCode());
            }
            databaseTable.updateRecord(record);
        } catch (Exception exception) {
            throw new CantCreateDigitalAssetTransactionException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public synchronized void assetCompleted(String assetPk) throws CantCreateDigitalAssetTransactionException {
        String context = "Asset : " + assetPk;
        try {
            DatabaseTable databaseTable = getIssuingTable();
            databaseTable.addStringFilter(AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPk, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = records.get(0);
            int assetsCompleted = record.getIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_COMPLETED_COLUMN_NAME);
            int assetsToGenerate = record.getIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_TO_GENERATE_COLUMN_NAME);

            record.setIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_COMPLETED_COLUMN_NAME, ++assetsCompleted);
            if (assetsCompleted >= assetsToGenerate) {
                record.setStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME, IssuingStatus.ISSUED.getCode());
            }
            databaseTable.updateRecord(record);
        } catch (Exception exception) {
            throw new CantCreateDigitalAssetTransactionException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public void updateMetadataIssuingStatus(UUID recordId, IssuingStatus status) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordsForTableByFilter(getMetadataTable(), AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ISSUING_STATUS_COLUMN_NAME, status, AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ID_COLUMN_NAME, recordId.toString());
    }

    public void updateWalletBalance(CryptoTransaction cryptoTransaction, UUID internalId, BalanceType balanceType) throws CantDeliverDigitalAssetToAssetWalletException {
        vault.deliverDigitalAssetMetadataToAssetWallet(cryptoTransaction, internalId.toString(), balanceType);
    }

    public void reprocessIssuingAssets() {
        try {
            updateRecordsForTableByFilter(getIssuingTable(), AssetIssuingDatabaseConstants.ASSET_ISSUING_PROCESSING_COLUMN_NAME, Boolean.FALSE, AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME, IssuingStatus.ISSUING.getCode());
        } catch (Exception e) {
            //Nothing.
        }
    }

    public void updateIssuingStatus(String assetPK, IssuingStatus status) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordsForTableByFilter(getIssuingTable(), AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME, status, AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPK);
    }

    public void processingAsset(String assetPk) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordsForTableByFilter(getIssuingTable(), AssetIssuingDatabaseConstants.ASSET_ISSUING_PROCESSING_COLUMN_NAME, Boolean.TRUE, AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPk);
    }

    public void unProcessingAsset(String assetPk) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordsForTableByFilter(getIssuingTable(), AssetIssuingDatabaseConstants.ASSET_ISSUING_PROCESSING_COLUMN_NAME, Boolean.FALSE, AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPk);
    }

    public void updateGenesisTx(UUID transactionId, DigitalAssetMetadata metadata, CryptoTransaction cryptoTx) throws CantCreateDigitalAssetFileException {
        metadata.addNewTransaction(cryptoTx.getTransactionHash(), cryptoTx.getBlockHash());
        vault.persistDigitalAssetMetadataInLocalStorage(metadata, transactionId.toString());
    }

    //PRIVATE METHODS

    /*
    Database Methods
     */
    private DatabaseTable getDatabaseTable(String tableName) {
        return database.getTable(tableName);
    }

    private DatabaseTable getIssuingTable() {
        return getDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME);
    }

    private DatabaseTable getMetadataTable() {
        return getDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_TABLE);
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Issuing Transaction Database", "Error in database plugin.");
        }
    }

    private DatabaseTableFilter constructEqualFilter(String column, String value) {
        return constructFilter(column, value, DatabaseFilterType.EQUAL);
    }

    private DatabaseTableFilter constructFilter(final String column, final String value, final DatabaseFilterType type) {
        return new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return column;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public DatabaseFilterType getType() {
                return type;
            }
        };
    }

    private IssuingRecord constructIssuingRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetDigitalAssetFromLocalStorageException {
        int assetsToGenerate = record.getIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_TO_GENERATE_COLUMN_NAME);
        int assetsCompleted = record.getIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_COMPLETED_COLUMN_NAME);
        int assetsProcessed = record.getIntegerValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_PROCESSED_COLUMN_NAME);
        BlockchainNetworkType networkType = BlockchainNetworkType.getByCode(record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_NETWORK_TYPE_COLUMN_NAME));
        String assetPk = record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME);
        DigitalAsset digitalAsset = vault.getDigitalAssetFromLocalStorage(assetPk);
        IssuingStatus status = IssuingStatus.getByCode(record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME));
        String btcWalletPk = record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_BTC_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String issuerWalletPk = record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUER_WALLET_PUBLIC_KEY_COLUMN_NAME);
        long creationTime = record.getLongValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_CREATION_DATE_COLUMN_NAME);
        return new IssuingRecord(assetsCompleted, assetsToGenerate, assetsProcessed, networkType, digitalAsset, status, btcWalletPk, issuerWalletPk, creationTime);
    }


    private org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.MetadataRecord constructMetadataRecord(DatabaseTableRecord record) throws CantGetDigitalAssetFromLocalStorageException, InvalidParameterException {
        UUID transactionId = UUID.fromString(record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ID_COLUMN_NAME));
        IssuingStatus status = IssuingStatus.getByCode(record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ISSUING_STATUS_COLUMN_NAME));
        UUID outgoingId = UUID.fromString(record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_OUTGOING_ID_COLUMN_NAME));
        DigitalAssetMetadata metadata = vault.getDigitalAssetMetadataFromLocalStorage(transactionId.toString());
        return new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.MetadataRecord(outgoingId, status, metadata, transactionId);
    }

    private void updateRecordsForTableByFilter(DatabaseTable table, String recordName, Object recordValue, String filterColumn, String filterValue) throws CantUpdateRecordException, CantLoadTableToMemoryException, RecordsNotFoundException {
        table.addStringFilter(filterColumn, filterValue, DatabaseFilterType.EQUAL);
        table.loadToMemory();
        if (table.getRecords().isEmpty()) throw new RecordsNotFoundException();
        for (DatabaseTableRecord record : table.getRecords()) {
            if (recordValue instanceof Long) {
                record.setLongValue(recordName, (Long) recordValue);
            } else if (recordValue instanceof Integer) {
                record.setIntegerValue(recordName, (Integer) recordValue);
            } else if (recordValue instanceof String) {
                record.setStringValue(recordName, (String) recordValue);
            } else if (recordValue instanceof FermatEnum) {
                record.setStringValue(recordName, ((FermatEnum) recordValue).getCode());
            } else {
                record.setStringValue(recordName, recordValue == null ? "null" : recordValue.toString());
            }
            table.updateRecord(record);
        }
    }


    private List<DatabaseTableRecord> getRecordsByFilter(DatabaseTable table, String orderColumn, DatabaseFilterOrder order, DatabaseTableFilter... filters) throws CantLoadTableToMemoryException {
        for (DatabaseTableFilter filter : filters) {
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        }
        table.addFilterOrder(orderColumn, order);
        table.loadToMemory();
        return table.getRecords();
    }

    private List<DatabaseTableRecord> getRecordsIssuing(DatabaseTableFilter... filters) throws CantLoadTableToMemoryException {
        return getRecordsByFilter(getIssuingTable(), AssetIssuingDatabaseConstants.ASSET_ISSUING_CREATION_DATE_COLUMN_NAME, DatabaseFilterOrder.ASCENDING, filters);
    }

    private List<DatabaseTableRecord> getRecordsMetadata(DatabaseTableFilter... filters) throws CantLoadTableToMemoryException {
        return getRecordsByFilter(getMetadataTable(), AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_CREATION_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING, filters);
    }

    private List<DatabaseTableRecord> getMetadataByStatus(IssuingStatus status) throws CantLoadTableToMemoryException {
        DatabaseTableFilter statusFilter = constructEqualFilter(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ISSUING_STATUS_COLUMN_NAME, status.getCode());
        return getRecordsMetadata(statusFilter);
    }

    private List<DatabaseTableRecord> getIssuingByStatus(IssuingStatus status) throws CantLoadTableToMemoryException {
        DatabaseTableFilter statusFilter = constructEqualFilter(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME, status.getCode());
        return getRecordsIssuing(statusFilter, getProcessingFilter());
    }

    private List<IssuingRecord> constructIssuingRecords(List<DatabaseTableRecord> records) throws CantGetDigitalAssetFromLocalStorageException, InvalidParameterException {
        List<IssuingRecord> issuingRecords = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            issuingRecords.add(constructIssuingRecord(record));
        }
        return issuingRecords;
    }

    private List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.MetadataRecord> constructMetadataRecords(List<DatabaseTableRecord> records) throws CantGetDigitalAssetFromLocalStorageException, InvalidParameterException {
        List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.MetadataRecord> issuingRecords = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            issuingRecords.add(constructMetadataRecord(record));
        }
        return issuingRecords;
    }

    private DatabaseTableFilter getProcessingFilter() {
        return constructEqualFilter(AssetIssuingDatabaseConstants.ASSET_ISSUING_PROCESSING_COLUMN_NAME, Boolean.FALSE.toString());
    }

    //GETTER AND SETTERS
    public IssuingRecord getRecordForAsset(String assetPk) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantGetDigitalAssetFromLocalStorageException, InvalidParameterException {
        DatabaseTableFilter assetFilter = constructEqualFilter(AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPk);
        List<DatabaseTableRecord> records = getRecordsIssuing(assetFilter);
        if (records.isEmpty()) throw new RecordsNotFoundException();
        return constructIssuingRecord(records.get(0));
    }

    public List<IssuingRecord> getRecordsForStatus(IssuingStatus status) throws CantLoadTableToMemoryException, CantGetDigitalAssetFromLocalStorageException, InvalidParameterException {
        return constructIssuingRecords(getIssuingByStatus(status));
    }

    public List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.MetadataRecord> getMetadataForStatus(IssuingStatus status) throws CantLoadTableToMemoryException, CantGetDigitalAssetFromLocalStorageException, InvalidParameterException {
        return constructMetadataRecords(getMetadataByStatus(status));
    }

    public List<String> getPendingEvents() throws CantLoadTableToMemoryException {
        DatabaseTableFilter pendingFilter = constructEqualFilter(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
        List<DatabaseTableRecord> records = getRecordsByFilter(getDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME), AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING, pendingFilter);
        List<String> toReturn = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            toReturn.add(record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_ID_COLUMN_NAME));
        }
        return toReturn;
    }

    public EventType getEventType(String eventId) throws InvalidParameterException, CantLoadTableToMemoryException, RecordsNotFoundException {
        DatabaseTableFilter idFilter = constructEqualFilter(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
        List<DatabaseTableRecord> records = getRecordsByFilter(getDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME), AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING, idFilter);
        if (records.isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = records.get(0);
        return EventType.getByCode(record.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_EVENT_COLUMN_NAME));
    }

    //INNER CLASSES
}
