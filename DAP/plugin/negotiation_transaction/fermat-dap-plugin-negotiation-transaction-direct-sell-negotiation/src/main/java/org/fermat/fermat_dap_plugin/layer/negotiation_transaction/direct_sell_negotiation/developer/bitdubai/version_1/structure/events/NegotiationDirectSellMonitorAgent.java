package org.fermat.fermat_dap_plugin.layer.negotiation_transaction.direct_sell_negotiation.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import org.fermat.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15. - Jose Briceño (josebricenor@gmail.com) 16/03/16.
 */
public class NegotiationDirectSellMonitorAgent implements Agent {

    //VARIABLE DECLARATION
    private ServiceStatus status;

    {
        this.status = ServiceStatus.CREATED;
    }

    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ExtraUserManager extraUserManager;

    private DirectSellNegotiationAgent directSellNegotiationAgent;

    //CONSTRUCTORS


    public NegotiationDirectSellMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                             ErrorManager errorManager,
                                             UUID pluginId,
                                             ExtraUserManager extraUserManager) throws CantSetObjectException {
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.errorManager = Validate.verifySetter(errorManager, "errorManager is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.extraUserManager = Validate.verifySetter(extraUserManager, "extraUserManager is null");
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            directSellNegotiationAgent = new DirectSellNegotiationAgent();
            Thread eventThread = new Thread(directSellNegotiationAgent, "Direct Sell Negotiation MonitorAgent");
            eventThread.start();
        } catch (Exception e) {
            throw new CantStartAgentException();
        }
        this.status = ServiceStatus.STARTED;
   }

    @Override
    public void stop() {
        directSellNegotiationAgent.stopAgent();
        directSellNegotiationAgent = null; //RELEASE RESOURCES.
        this.status = ServiceStatus.STOPPED;
    }

    public boolean isMonitorAgentActive() {
        return status == ServiceStatus.STARTED;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
    private class DirectSellNegotiationAgent implements Runnable {

        private volatile boolean agentRunning;
        private static final int WAIT_TIME = 5 * 1000; //SECONDS

        public DirectSellNegotiationAgent() {
            startAgent();
        }

        @Override
        public void run() {
            while (agentRunning) {
                try {
                    doTheMainTask();
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    /*If this happen there's a chance that the information remains
                    in a corrupt state. That probably would be fixed in a next run.
                    */
                    errorManager.reportUnexpectedPluginException(Plugins.ASSET_DIRECT_SELL, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.ASSET_DIRECT_SELL, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() {

        }

        private Actor getExtraUser(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateExtraUserException {
            try {
                return extraUserManager.getActorByPublicKey(digitalAssetMetadata.getDigitalAsset().getPublicKey());
            } catch (CantGetExtraUserException | ExtraUserNotFoundException e) {
                byte[] image = digitalAssetMetadata.getDigitalAsset().getResources().isEmpty() ? new byte[0] : digitalAssetMetadata.getDigitalAsset().getResources().get(0).getResourceBinayData();
                return extraUserManager.createActor(digitalAssetMetadata.getDigitalAsset().getName(), image);
            }
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
