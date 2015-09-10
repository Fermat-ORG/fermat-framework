package com.bitdubai.fermat_dap_plugin.layer.middleware.asset_factory.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 9/7/15.
 */
public class AssetFactoryPluginRoot implements DealsWithErrors, DealsWithLogger, DealsWithEvents, Plugin, DatabaseManagerForDevelopers, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, Service {
    /**
     * DealsWithErrors interface member variables
     */
    com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager;

    /**
     * DealsWithDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

//    private com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure.AssetFactoryManager getAssetIssuerManager(){
//        com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure.AssetFactoryManager assetIssuerManager = new com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure.AssetFactoryManager(errorManager, logManager, pluginDatabaseSystem, pluginFileSystem, pluginId);
//        return assetIssuerManager;
//    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setErrorManager(com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        //TODO: Implement
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        //TODO: Implement
    }

    @Override
    public void resume() {
        //TODO: Implement
    }

    @Override
    public void stop() {
        //TODO: Implement
    }

    @Override
    public ServiceStatus getStatus() {
        return null;
    }


}
