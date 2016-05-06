package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;

import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.functional.AssetFactoryMiddlewareManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        this.agentThread = new Thread(monitorAgent, "Asset Factory Middleware MonitorAgent");
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
                    monitorIssuingFactory(assetFactory);
                    monitorState(assetFactory);
                }
            } catch (CantSaveAssetFactoryException e) {

                throw new CantSaveAssetFactoryException(e, "Cant Save Asset Factory", "Method: doTheMainTask");
            } catch (CantExecuteDatabaseOperationException | CantDeleteAsserFactoryException e) {

                e.printStackTrace();
            } catch (CantPersistFileException e) {

                throw new CantPersistFileException("Cant Persist File", e, "Method: doTheMainTask", null);
            } catch (CantCreateFileException e) {

                throw new CantCreateFileException("Cant Create File", e, "Method: doTheMainTask", null);
            } catch (CantGetAssetFactoryException e) {

                throw new CantGetAssetFactoryException("Cant Get Asset Factory", e, "Method: doTheMainTask", null);
            }

        }

        private void monitorState(AssetFactory assetFactory) throws CantExecuteDatabaseOperationException, CantGetAssetFactoryException, CantCreateFileException, CantDeleteAsserFactoryException, CantSaveAssetFactoryException, CantPersistFileException {
            if (assetFactory.getState() != State.DRAFT) {
                IssuingStatus issuingStatus = assetIssuingManager.getIssuingStatus(assetFactory.getAssetPublicKey());
                switch (issuingStatus) {
                    case ISSUING:
                        break;
                    case ISSUED:
                        if (!assetFactoryMiddlewareManager.getFactoryByStateAndAssetPublicKey(assetFactory, State.PENDING_FINAL).isEmpty()) {
                            assetFactoryMiddlewareManager.removePendingFinalFactory(assetFactory.getAssetPublicKey());
                        }
                        break;
                    case INSUFFICIENT_FONDS:
                        break;
                    case UNEXPECTED_INTERRUPTION:
                        assetFactoryMiddlewareManager.markAssetFactoryState(State.DRAFT, assetFactory.getAssetPublicKey());
                        break;
                    default:
                        break;
                }
            }
        }

        private void monitorIssuingFactory(AssetFactory assetFactory) throws CantExecuteDatabaseOperationException, CantGetAssetFactoryException, CantCreateFileException, CantSaveAssetFactoryException, CantPersistFileException {
            int assetsIssued = assetIssuingManager.getNumberOfIssuedAssets(assetFactory.getAssetPublicKey());
            if (assetsIssued >= 1) {
                if (assetFactoryMiddlewareManager.getFactoryByStateAndAssetPublicKey(assetFactory, State.FINAL).isEmpty()) {
                    assetFactory.setFactoryId(UUID.randomUUID().toString());
                    assetFactory.setState(State.FINAL);
                    assetFactory.setQuantity(assetsIssued);
                    assetFactoryMiddlewareManager.saveAssetFactory(assetFactory);
                } else {
                    List<AssetFactory> finalFactories = assetFactoryMiddlewareManager.getFactoryByStateAndAssetPublicKey(assetFactory, State.FINAL);
                    List<AssetFactory> pendingFinal = assetFactoryMiddlewareManager.getFactoryByStateAndAssetPublicKey(assetFactory, State.PENDING_FINAL);
                    List<AssetFactory> both = assetFactoryMiddlewareManager.getFactoryByStateAndAssetPublicKey(assetFactory, State.DRAFT);
                    both.addAll(pendingFinal);
                    if (!finalFactories.isEmpty()) {
                        assetFactoryMiddlewareManager.updateAssetFactoryQuantity(assetsIssued, finalFactories.get(0).getFactoryId());
                    }
                    if (!both.isEmpty()) {
                        AssetFactory notFinalFactory = both.get(0);
                        int missingAmount = notFinalFactory.getTotalQuantity() - assetsIssued;
                        assetFactoryMiddlewareManager.updateAssetFactoryQuantity(missingAmount, notFinalFactory.getFactoryId());
                    }
                }
            }
        }

        private List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException {
            List<AssetFactory> factories = new ArrayList<>();
            factories.addAll(assetFactoryMiddlewareManager.getAssetFactoryAll(BlockchainNetworkType.REG_TEST));
            factories.addAll(assetFactoryMiddlewareManager.getAssetFactoryAll(BlockchainNetworkType.TEST_NET));
            factories.addAll(assetFactoryMiddlewareManager.getAssetFactoryAll(BlockchainNetworkType.PRODUCTION));
            return factories;
        }

    }
}
