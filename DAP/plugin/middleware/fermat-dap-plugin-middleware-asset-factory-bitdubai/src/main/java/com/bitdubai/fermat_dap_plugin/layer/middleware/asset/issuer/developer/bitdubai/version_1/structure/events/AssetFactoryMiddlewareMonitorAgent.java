package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.AssetFactoryMiddlewareManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.List;

/**
 * Created by franklin on 12/10/15.
 */
public final class AssetFactoryMiddlewareMonitorAgent implements Agent {

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final AssetFactoryMiddlewareManager assetFactoryMiddlewareManager;
    private final AssetIssuingManager assetIssuingManager;


    public AssetFactoryMiddlewareMonitorAgent(AssetFactoryMiddlewareManager assetFactoryMiddlewareManager,
                                              AssetIssuingManager assetIssuingManager,
                                              ErrorManager errorManager) throws CantSetObjectException {

        if (assetFactoryMiddlewareManager == null)
            throw new CantSetObjectException("AssetFactoryMiddlewareManager is null");

        if (assetIssuingManager == null)
            throw new CantSetObjectException("AssetIssuingManager is null");

        this.errorManager = errorManager;
        this.assetFactoryMiddlewareManager = assetFactoryMiddlewareManager;
        this.assetIssuingManager = assetIssuingManager;

    }

    @Override
    public void start() throws CantStartAgentException {
        final MonitorAgent monitorAgent = new MonitorAgent(errorManager);
        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private final class MonitorAgent implements Runnable {

        private final ErrorManager errorManager;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        boolean threadWorking;

        public MonitorAgent(final ErrorManager errorManager) {

            this.errorManager = errorManager;
        }

        @Override
        public void run() {
            threadWorking = true;
            while (threadWorking) {
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
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }
        }

        private void doTheMainTask() throws CantSaveAssetFactoryException, CantExecuteDatabaseOperationException, CantPersistFileException, CantCreateFileException, CantGetAssetFactoryException {
            try {
                List<AssetFactory> assetFactories = getAssetFactoryAll();

                for (AssetFactory assetFactory : assetFactories) {
                    if (assetFactory.getState() != State.DRAFT) {
                        IssuingStatus issuingStatus = assetIssuingManager.getIssuingStatus(assetFactory.getPublicKey());
                        if (issuingStatus != IssuingStatus.ISSUING) {
                            if (issuingStatus == IssuingStatus.ISSUED) {
                                assetFactoryMiddlewareManager.markAssetFactoryState(State.FINAL, assetFactory.getPublicKey());
                            } else {
                                //TODO, UPDATE THE STATUS WHEN ITS AN ERROR TO LET THE FINAL USER KNOWS WHAT HAPPENED.
                            }
                        }
//
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

        private List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException {
            return assetFactoryMiddlewareManager.getAssetFactoryAll();
        }

    }
}
