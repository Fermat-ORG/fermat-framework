package org.fermat.fermat_dap_plugin.layer.metadata.digital_asset.developer.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public final class DigitalAssetMetadataDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    public DigitalAssetMetadataDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    public Database createDatabase() throws CantCreateDatabaseException {
        Database database;
        try {
            database = this.pluginDatabaseSystem.createDatabase(pluginId, DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_DATABASE);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        try {
            DatabaseTableFactory digitalAssetMetadataTable;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            digitalAssetMetadataTable = databaseFactory.newTableFactory(pluginId, DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_TABLE);
            digitalAssetMetadataTable.addColumn(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            digitalAssetMetadataTable.addColumn(DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_OBJECT_COLUMN_NAME, DatabaseDataType.STRING, Validate.MAX_SIZE_STRING_COLUMN, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(pluginId, digitalAssetMetadataTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating " + DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_TABLE + " table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }


    public boolean databaseExists() {
        try {
            pluginDatabaseSystem.openDatabase(pluginId, DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_DATABASE);
            return true;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            return false;
        }
    }
}
