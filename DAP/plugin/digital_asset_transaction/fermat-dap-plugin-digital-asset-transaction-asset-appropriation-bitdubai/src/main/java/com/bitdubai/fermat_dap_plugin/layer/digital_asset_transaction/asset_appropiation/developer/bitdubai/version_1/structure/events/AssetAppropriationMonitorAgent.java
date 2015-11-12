package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.AssetAppropriationDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetAppropriationMonitorAgent implements Agent {

    //VARIABLE DECLARATION
    private ServiceStatus status;

    {
        this.status = ServiceStatus.CREATED;
    }

    private ErrorManager errorManager;
    private LogManager logManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private AppropriationAgent agent;

    //VARIABLES ACCESSED BY AGENT INNER CLASS.
    //NEEDS TO BE VOLATILE SINCE THEY'RE BEING USED ON ANOTHER THREAD.
    //I NEED THREAD TO NOTICE ASAP.
    private volatile UUID pluginId;
    private volatile PluginFileSystem pluginFileSystem;
    private volatile BitcoinNetworkManager bitcoinNetworkManager;
    private volatile CountDownLatch latch;

    //CONSTRUCTORS


    public AssetAppropriationMonitorAgent(PluginFileSystem pluginFileSystem, UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, LogManager logManager, ErrorManager errorManager) throws CantSetObjectException {
        this.pluginFileSystem = Validate.verifySetter(pluginFileSystem, "pluginFileSystem is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.logManager = Validate.verifySetter(logManager, "logManager is null");
        this.errorManager = Validate.verifySetter(errorManager, "errorManager is null");
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: starting...", null, null);
            latch = new CountDownLatch(1);
            agent = new AppropriationAgent(pluginId, pluginFileSystem);
            Thread agentThread = new Thread(agent);
            agentThread.start();
        } catch (Exception e) {
            throw new CantStartAgentException();
        }
        this.status = ServiceStatus.STARTED;
        logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: successfully started...", null, null);
    }

    @Override
    public void stop() {
        logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: stopping...", null, null);
        agent.stopAgent();
        try {
            latch.await(); //WAIT UNTIL THE LAST RUN FINISH
        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
        }
        agent = null; //RELEASE RESOURCES.
        logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: successfully stopped...", null, null);
        this.status = ServiceStatus.STOPPED;
    }

    public boolean isMonitorAgentActive() {
        return status == ServiceStatus.STARTED;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
    private class AppropriationAgent extends AbstractDigitalAssetVault implements Runnable {

        private volatile boolean agentRunning;
        private static final int WAIT_TIME = 20; //SECONDS

        public AppropriationAgent(UUID pluginId, PluginFileSystem pluginFileSystem) throws CantSetObjectException {
            super.setPluginId(pluginId);
            super.setPluginFileSystem(pluginFileSystem);
            startAgent();
        }

        @Override
        public void run() {
            while (agentRunning) {
                doTheMainTask();
                try {
                    Thread.sleep(WAIT_TIME * 1000);
                } catch (InterruptedException e) {
                    /*If this happen there's a chance that the information remains
                    in a corrupt state. That probably would be fixed in a next run.
                    */
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                }
            }
            latch.countDown();
        }

        private void doTheMainTask() {
            //TODO.
        }

        public boolean isAgentRunning() {
            return agentRunning;
        }

        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }
    }
}
