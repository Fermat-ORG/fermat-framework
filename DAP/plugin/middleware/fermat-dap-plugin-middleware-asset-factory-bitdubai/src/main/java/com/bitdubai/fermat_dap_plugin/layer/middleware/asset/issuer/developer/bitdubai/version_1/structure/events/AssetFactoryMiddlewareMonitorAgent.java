package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantInitializeAssetMonitorAgentException;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.AssetFactoryMiddlewarePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.AssetFactoryMiddlewareManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by franklin on 12/10/15.
 */
public class AssetFactoryMiddlewareMonitorAgent implements Agent, DealsWithLogger, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, DealsWithPluginFileSystem {
    Database database;
    Thread agentThread;
    MonitorAgent monitorAgent;
    LogManager logManager;
    ErrorManager errorManager;
    EventManager eventManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    AssetFactoryMiddlewareManager assetFactoryMiddlewareManager;
    AssetIssuingManager assetIssuingManager;
    UUID pluginId;
    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

    public AssetFactoryMiddlewareMonitorAgent(EventManager eventManager,
                                              PluginDatabaseSystem pluginDatabaseSystem,
                                              ErrorManager errorManager,
                                              AssetFactoryMiddlewareManager assetFactoryMiddlewareManager,
                                              AssetIssuingManager assetIssuingManager,
                                              UUID pluginId,
                                              PluginFileSystem pluginFileSystem) throws CantSetObjectException {
        this.eventManager= eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        setAssetFactoryMiddlewareManager(assetFactoryMiddlewareManager);
        setAssetIssuingManager(assetIssuingManager);
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;

    }

    private void setAssetFactoryMiddlewareManager(AssetFactoryMiddlewareManager assetFactoryMiddlewareManager) throws CantSetObjectException {
        if(assetFactoryMiddlewareManager == null){
            throw new CantSetObjectException("AssetFactoryMiddlewareManager is null");
        }
        this.assetFactoryMiddlewareManager = assetFactoryMiddlewareManager;
    }

    private void setAssetIssuingManager(AssetIssuingManager assetIssuingManager) throws CantSetObjectException {
        if (assetIssuingManager == null){
            throw new CantSetObjectException("AssetIssuingManager is null");
        }
        this.assetIssuingManager = assetIssuingManager;
    }

    @Override
    public void start() throws CantStartAgentException {
        Logger LOG = Logger.getGlobal();
        LOG.info("Asset Factory monitor agent starting");

        monitorAgent = new MonitorAgent();


        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

//        try {
//            ((MonitorAgent) this.monitorAgent).Initialize();
//        } catch (CantInitializeAssetMonitorAgentException exception) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//        }


        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }



    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements Runnable, DealsWithPluginDatabaseSystem, DealsWithErrors{

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        boolean threadWorking;
        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }
        @Override
        public void run() {
            threadWorking = true;
            while(threadWorking){
                /**
                 * Increase the iteration counter
                 */
                iterationNumber++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {
                    doTheMainTask();
                }
                    catch (Exception e){
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }
        }

        private void doTheMainTask() throws CantSaveAssetFactoryException , CantExecuteDatabaseOperationException, CantPersistFileException, CantCreateFileException, CantGetAssetFactoryException{
            try
            {
                List<AssetFactory> assetFactories = getAssetFactoryAll();

                for(AssetFactory assetFactory : assetFactories){
                    if (assetFactory.getState().getCode() != State.DRAFT.getCode())
                    {
                        int numberOfIssuedAssets = assetIssuingManager.getNumberOfIssuedAssets(assetFactory.getPublicKey());
                        int totalFaltante = assetFactory.getQuantity() - numberOfIssuedAssets;
                        if (totalFaltante == 0){
                            assetFactoryMiddlewareManager.markAssetFactoryState(State.FINAL, assetFactory.getPublicKey());
                        }
                    }
                }
            } catch (CantSaveAssetFactoryException e) {
                throw new CantSaveAssetFactoryException(e, "Cant Save Asset Factory", "Method: doTheMainTask");
            } catch (CantExecuteDatabaseOperationException e) {
                e.printStackTrace();
            } catch (CantPersistFileException e) {
                throw new CantPersistFileException("Cant Persist File", e, "Method: doTheMainTask", null);
            } catch (CantCreateFileException e) {
                throw new CantCreateFileException("Cant Create File", e, "Method: doTheMainTask", null);
            } catch (CantGetAssetFactoryException e) {
                throw new CantGetAssetFactoryException("Cant Get Asset Factory", e, "Method: doTheMainTask", null);
            }

        }

        private List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException
        {
            return assetFactoryMiddlewareManager.getAssetFactoryAll();
        }

    }
}
