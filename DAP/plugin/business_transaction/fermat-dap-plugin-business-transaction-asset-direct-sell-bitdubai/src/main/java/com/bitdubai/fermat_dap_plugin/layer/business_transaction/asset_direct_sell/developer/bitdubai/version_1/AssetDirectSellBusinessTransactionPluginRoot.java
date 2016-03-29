package com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.dap_business_transaction.asset_direct_sell.interfaces.AssetDirectSellManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.developer_utils.AssetDirectSellDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.structure.database.AssetDirectSellDAO;
import com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.structure.database.AssetDirectSellDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.structure.events.AssetDirectSellMonitorAgent;
import com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.structure.events.AssetDirectSellRecorderService;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;

/**
 * Luis Torres (lutor1106@gmail.com") 16/03/16
 */
public class AssetDirectSellBusinessTransactionPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        AssetDirectSellManager{


    //VARIABLE DECLARATION

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    private AssetDirectSellRecorderService recorderService;
    private AssetDirectSellMonitorAgent agent;
    private AssetDirectSellDAO dao;

    //CONSTRUCTORS
    public AssetDirectSellBusinessTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartPluginException {
        try {
            System.out.println("LFTL: EL PLUGIN INICIA");
            createDatabase();
            dao = new AssetDirectSellDAO(pluginDatabaseSystem,pluginId);
            initializeMonitorAgent();
            initializeRecorderService();
            super.start();
        } catch (Exception e) {
            System.out.println("LFTL: EL PLUGIN NO INICIA");
            throw new CantStartPluginException(FermatException.wrapException(e));
        }
    }

    @Override
    public void stop() {
        try {
            agent.stop();
            recorderService.stop();
            super.stop();
        } catch (CantStopAgentException e) {
            e.printStackTrace();
        } finally {
            super.stop();
        }
    }

    //PRIVATE METHODS
    private void createDatabase() throws CantCreateDatabaseException {
        AssetDirectSellDatabaseFactory databaseFactory = new AssetDirectSellDatabaseFactory(pluginDatabaseSystem, pluginId);
        if (!databaseFactory.databaseExists()) {
            databaseFactory.createDatabase();
        }
    }

    private void initializeMonitorAgent() throws CantStartAgentException {
        agent = new AssetDirectSellMonitorAgent(errorManager, dao);
        agent.start();
    }

    private void initializeRecorderService() throws CantStartServiceException {
        recorderService = new AssetDirectSellRecorderService(eventManager, pluginDatabaseSystem, pluginId, dao);
        recorderService.start();
    }

    //GETTER AND SETTERS
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return AssetDirectSellDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory, pluginId);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return AssetDirectSellDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }


    //INNER CLASSES
}