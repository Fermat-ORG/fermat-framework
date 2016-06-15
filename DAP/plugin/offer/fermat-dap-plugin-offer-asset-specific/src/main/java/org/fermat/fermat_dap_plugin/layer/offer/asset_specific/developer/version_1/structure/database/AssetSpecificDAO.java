package org.fermat.fermat_dap_plugin.layer.offer.asset_specific.developer.version_1.structure.database;

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

import org.fermat.fermat_dap_api.layer.all_definition.offer.AssetSpecificOffer;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetSpecificDAO {

    //VARIABLE DECLARATION

    //VARIABLE DECLARATION
    private final Database database;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    //CONSTRUCTORS
    public AssetSpecificDAO(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) throws CantExecuteDatabaseOperationException {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        database = openDatabase(pluginDatabaseSystem, pluginId);
    }

    //PUBLIC METHODS
    public void storeAssetSpecificOffer(AssetSpecificOffer offer) throws CantUpdateRecordException, CantLoadTableToMemoryException, CantInsertRecordException {
        if (metadataExists(offer)) {
            updateMetadata(offer);
        } else {
            saveNewMetadata(offer);
        }
    }

    public AssetSpecificOffer getAssetSpecificOffer(UUID offerId) throws CantLoadTableToMemoryException, RecordsNotFoundException {
        DatabaseTable databaseTable = getDigitalAssetMetadataTable();
        databaseTable.addStringFilter(AssetSpecificDatabaseConstants.SPECIFIC_OFFER_ID_COLUMN_NAME, offerId.toString(), DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();
        if (databaseTable.getRecords().isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = databaseTable.getRecords().get(0);
        String metadataXML = record.getStringValue(AssetSpecificDatabaseConstants.SPECIFIC_OFFER_OBJECT_COLUMN_NAME);
        return (AssetSpecificOffer) XMLParser.parseXML(metadataXML, new AssetSpecificOffer());
    }

    //PRIVATE METHODS
    private void saveNewMetadata(AssetSpecificOffer offer) throws CantInsertRecordException {
        DatabaseTable databaseTable = getDigitalAssetMetadataTable();
        DatabaseTableRecord newRecord = databaseTable.getEmptyRecord();
        newRecord.setStringValue(AssetSpecificDatabaseConstants.SPECIFIC_OFFER_ID_COLUMN_NAME, offer.getOfferId().toString());
        newRecord.setStringValue(AssetSpecificDatabaseConstants.SPECIFIC_OFFER_OBJECT_COLUMN_NAME, XMLParser.parseObject(offer));
        databaseTable.insertRecord(newRecord);
    }

    private boolean metadataExists(AssetSpecificOffer offer) throws CantLoadTableToMemoryException {
        DatabaseTable databaseTable = getDigitalAssetMetadataTable();
        databaseTable.addStringFilter(AssetSpecificDatabaseConstants.SPECIFIC_OFFER_ID_COLUMN_NAME, offer.getOfferId().toString(), DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();
        return !databaseTable.getRecords().isEmpty();
    }

    private void updateMetadata(AssetSpecificOffer offer) throws CantLoadTableToMemoryException, CantUpdateRecordException {
        DatabaseTable databaseTable = getDigitalAssetMetadataTable();
        databaseTable.addStringFilter(AssetSpecificDatabaseConstants.SPECIFIC_OFFER_ID_COLUMN_NAME, offer.getOfferId().toString(), DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();
        DatabaseTableRecord record = databaseTable.getRecords().get(0);
        record.setStringValue(AssetSpecificDatabaseConstants.SPECIFIC_OFFER_OBJECT_COLUMN_NAME, XMLParser.parseObject(offer));
        databaseTable.updateRecord(record);
    }

    private Database openDatabase(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, AssetSpecificDatabaseConstants.SPECIFIC_OFFER_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Reception Transaction Database", "Error in database plugin.");
        }
    }

    private DatabaseTable getDigitalAssetMetadataTable() {
        return database.getTable(AssetSpecificDatabaseConstants.SPECIFIC_OFFER_TABLE);
    }

    //INNER CLASSES
}
