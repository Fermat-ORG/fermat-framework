package com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.structure.events;


import com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.structure.database.IssuerRedemptionDao;
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetRedeemedContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class IssuerRedemptionMonitorAgent implements Agent {

    private final AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager;
    private final ErrorManager errorManager;
    private MonitorAgent agent;
    private IssuerRedemptionDao issuerRedemptionDao;
    private AssetIssuerWalletManager assetIssuerWalletManager;

    public IssuerRedemptionMonitorAgent(AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager,
                                        AssetIssuerWalletManager assetIssuerWalletManager,
                                        ErrorManager errorManager,
                                        UUID pluginId,
                                        PluginDatabaseSystem pluginDatabaseSystem) throws CantSetObjectException, CantExecuteDatabaseOperationException {
        this.assetIssuerWalletManager = Validate.verifySetter(assetIssuerWalletManager, "assetIssuerWalletManager is null");
        this.errorManager = Validate.verifySetter(errorManager, "errorManager is null");
        this.assetIssuerActorNetworkServiceManager = Validate.verifySetter(assetIssuerActorNetworkServiceManager, "assetIssuerActorNetworkServiceManager  is null");
        issuerRedemptionDao = new IssuerRedemptionDao(pluginId, pluginDatabaseSystem);
    }

    @Override
    public void start() throws CantStartAgentException {
        agent = new MonitorAgent();
        Thread agentThread = new Thread(agent);
        agentThread.start();
    }

    @Override
    public void stop() {
        agent.stopAgent();
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements Runnable {

        private int WAIT_TIME = 5 * 1000;
        private boolean agentRunning;

        {
            startAgent();
        }

        @Override
        public void run() {
            while (agentRunning) {
                try {
                    doTheMainTask();
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    agentRunning = false;
                    /*If this happen there's a chance that the information remains
                    in a corrupt state. That probably would be fixed in a next run.
                    */
                    errorManager.reportUnexpectedPluginException(Plugins.ISSUER_REDEMPTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.ISSUER_REDEMPTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

        }


        private void doTheMainTask() throws Exception {
            for (String eventId : issuerRedemptionDao.getPendingNewReceiveMessageActorEvents()) {
                for (DAPMessage message : assetIssuerActorNetworkServiceManager.getUnreadDAPMessagesByType(DAPMessageType.ASSET_REDEEMED)) {
                    if (message.getMessageContent() instanceof AssetRedeemedContentMessage) { //Just a security measure, this SHOULD always be true.
                        AssetRedeemedContentMessage contentMessage = (AssetRedeemedContentMessage) message.getMessageContent();
                        //TODO REMOVE HARDCODE.
                        AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet("walletPublicKeyTest");
                        wallet.assetRedeemed(contentMessage.getAssetRedeemedPublicKey(),
                                contentMessage.getUserThatRedeemed(),
                                contentMessage.getRedeemPointPublicKey());
                    }
                }
                issuerRedemptionDao.notifyEvent(eventId);
            }
        }


        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }
    }
}
