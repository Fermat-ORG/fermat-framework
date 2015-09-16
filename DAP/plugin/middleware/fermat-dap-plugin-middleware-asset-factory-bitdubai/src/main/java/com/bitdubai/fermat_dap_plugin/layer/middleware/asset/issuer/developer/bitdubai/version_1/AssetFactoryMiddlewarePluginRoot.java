package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssertFactoryMiddlewareDatabaseConstant;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssetFactoryMiddlewareDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 9/7/15.
 */
public class AssetFactoryMiddlewarePluginRoot implements DealsWithErrors, DealsWithLogger, DealsWithEvents, Plugin, DatabaseManagerForDevelopers, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, Service {
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
    List<FermatEventListener> listenersAdded = new ArrayList<>();

//    private com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure.AssetFactoryMiddlewareManager getAssetIssuerManager(){
//        com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure.AssetFactoryMiddlewareManager assetIssuerManager = new com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure.AssetFactoryMiddlewareManager(errorManager, logManager, pluginDatabaseSystem, pluginFileSystem, pluginId);
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
        //TODO: Implement falta instanciar el manager del assetfactory
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, AssertFactoryMiddlewareDatabaseConstant.DATABASE_NAME);
            database.closeDatabase();
        }
        catch (CantOpenDatabaseException | DatabaseNotFoundException e)
        {
            try
            {
                AssetFactoryMiddlewareDatabaseFactory assetFactoryMiddlewareDatabaseFactory = new AssetFactoryMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
                assetFactoryMiddlewareDatabaseFactory.createDatabase(this.pluginId, AssertFactoryMiddlewareDatabaseConstant.DATABASE_NAME);
            }
            catch(CantCreateDatabaseException cantCreateDatabaseException)
            {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            }catch (Exception exception) {
                throw new CantStartPluginException("Cannot start AssetFactoryMiddleware plugin.", FermatException.wrapException(exception), null, null);
            }
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }
}
