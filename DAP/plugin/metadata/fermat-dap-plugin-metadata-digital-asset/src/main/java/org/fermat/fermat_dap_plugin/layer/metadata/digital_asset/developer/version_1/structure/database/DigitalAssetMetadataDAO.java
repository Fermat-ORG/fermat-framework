package org.fermat.fermat_dap_plugin.layer.metadata.digital_asset.developer.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class DigitalAssetMetadataDAO {

    //VARIABLE DECLARATION

    //VARIABLE DECLARATION
    private final Database database;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    //CONSTRUCTORS
    public DigitalAssetMetadataDAO(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) throws CantExecuteDatabaseOperationException {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        database = openDatabase(pluginDatabaseSystem, pluginId);
    }

    //PUBLIC METHODS
    public void storeDigitalAssetMetadata(DigitalAssetMetadata assetMetadata) throws CantUpdateRecordException, CantLoadTableToMemoryException, CantInsertRecordException {
        if (metadataExists(assetMetadata)) {
            updateMetadata(assetMetadata);
        } else {
            saveNewMetadata(assetMetadata);
        }
    }

    public DigitalAssetMetadata getDigitalAssetMetadata(UUID metadataId) throws CantLoadTableToMemoryException, RecordsNotFoundException {
        DatabaseTable databaseTable = getDigitalAssetMetadataTable();
        databaseTable.addStringFilter(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_ID_COLUMN_NAME, metadataId.toString(), DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();
        if (databaseTable.getRecords().isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = databaseTable.getRecords().get(0);
        String metadataXML = record.getStringValue(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_OBJECT_COLUMN_NAME);
        return (DigitalAssetMetadata) XMLParser.parseXML(metadataXML, new DigitalAssetMetadata());
    }

    //PRIVATE METHODS
    private void saveNewMetadata(DigitalAssetMetadata assetMetadata) throws CantInsertRecordException {
        DatabaseTable databaseTable = getDigitalAssetMetadataTable();
        DatabaseTableRecord newRecord = databaseTable.getEmptyRecord();
        newRecord.setStringValue(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_ID_COLUMN_NAME, assetMetadata.getMetadataId().toString());
        newRecord.setStringValue(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_OBJECT_COLUMN_NAME, XMLParser.parseObject(assetMetadata));
        databaseTable.insertRecord(newRecord);
    }

    private boolean metadataExists(DigitalAssetMetadata assetMetadata) throws CantLoadTableToMemoryException {
        DatabaseTable databaseTable = getDigitalAssetMetadataTable();
        databaseTable.addStringFilter(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_ID_COLUMN_NAME, assetMetadata.getMetadataId().toString(), DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();
        return !databaseTable.getRecords().isEmpty();
    }

    private void updateMetadata(DigitalAssetMetadata assetMetadata) throws CantLoadTableToMemoryException, CantUpdateRecordException {
        DatabaseTable databaseTable = getDigitalAssetMetadataTable();
        databaseTable.addStringFilter(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_ID_COLUMN_NAME, assetMetadata.getMetadataId().toString(), DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();
        DatabaseTableRecord record = databaseTable.getRecords().get(0);
        record.setStringValue(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_OBJECT_COLUMN_NAME, XMLParser.parseObject(assetMetadata));
        databaseTable.updateRecord(record);
    }

    private Database openDatabase(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Reception Transaction Database", "Error in database plugin.");
        }
    }

    private DatabaseTable getDigitalAssetMetadataTable() {
        return database.getTable(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_TABLE);
    }

    //INNER CLASSES
}
