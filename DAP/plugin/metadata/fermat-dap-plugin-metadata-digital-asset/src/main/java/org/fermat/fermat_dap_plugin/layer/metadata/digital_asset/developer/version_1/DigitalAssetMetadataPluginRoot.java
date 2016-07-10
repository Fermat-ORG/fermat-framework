package org.fermat.fermat_dap_plugin.layer.metadata.digital_asset.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.metadata.exceptions.CantGetDigitalAssetMetadataException;
import org.fermat.fermat_dap_api.layer.metadata.exceptions.CantStoreDigitalAssetMetadataException;
import org.fermat.fermat_dap_api.layer.metadata.interfaces.DigitalAssetMetadataManager;
import org.fermat.fermat_dap_plugin.layer.metadata.digital_asset.developer.version_1.developer_utils.DigitalAssetMetadataDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.metadata.digital_asset.developer.version_1.structure.database.DigitalAssetMetadataDAO;
import org.fermat.fermat_dap_plugin.layer.metadata.digital_asset.developer.version_1.structure.database.DigitalAssetMetadataDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.metadata.digital_asset.developer.version_1.structure.database.DigitalAssetMetadataDatabaseFactory;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class DigitalAssetMetadataPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        DigitalAssetMetadataManager {


    //VARIABLE DECLARATION
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    private DigitalAssetMetadataDAO dao;

    //CONSTRUCTORS
    public DigitalAssetMetadataPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartPluginException {
        try {
            createDatabase();
            dao = new DigitalAssetMetadataDAO(pluginId, pluginDatabaseSystem);
            super.start();
        } catch (Exception e) {
            throw new CantStartPluginException(FermatException.wrapException(e));
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    /**
     * Stores the passed digital asset Metadata
     *
     * @param digitalAssetMetadata
     * @throws CantStoreDigitalAssetMetadataException
     */
    @Override
    public void storeDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantStoreDigitalAssetMetadataException {
        String context = "Digital Asset Metadata: " + digitalAssetMetadata;
        try {
            dao.storeDigitalAssetMetadata(digitalAssetMetadata);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException | CantInsertRecordException e) {
            throw new CantStoreDigitalAssetMetadataException(e, context, null);
        }
    }

    /**
     * Gets a previously stored asset metadata
     *
     * @param id the id of the metadata to get (must exists)
     * @return the stored metadata
     * @throws CantGetDigitalAssetMetadataException if not found.
     */
    @Override
    public DigitalAssetMetadata getDigitalAssetMetadata(UUID id) throws CantGetDigitalAssetMetadataException {
        String context = "Metadata ID: " + id;
        try {
            return dao.getDigitalAssetMetadata(id);
        } catch (CantLoadTableToMemoryException | RecordsNotFoundException e) {
            throw new CantGetDigitalAssetMetadataException(e, context, null);
        }
    }

    //PRIVATE METHODS
    private void createDatabase() throws CantCreateDatabaseException {
        DigitalAssetMetadataDatabaseFactory databaseFactory = new DigitalAssetMetadataDatabaseFactory(pluginDatabaseSystem, pluginId);
        if (!databaseFactory.databaseExists()) {
            databaseFactory.createDatabase();
        }
    }

    //GETTER AND SETTERS
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return DigitalAssetMetadataDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory, pluginId);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return DigitalAssetMetadataDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, DigitalAssetMetadataDatabaseConstants.DIGITAL_ASSET_METADATA_DATABASE);
            return DigitalAssetMetadataDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", exception, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }

    //INNER CLASSES
}
