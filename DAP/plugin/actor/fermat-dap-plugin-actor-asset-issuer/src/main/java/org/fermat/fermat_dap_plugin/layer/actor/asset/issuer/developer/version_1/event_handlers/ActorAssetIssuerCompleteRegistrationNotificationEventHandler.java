package org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;

import org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetIssuerCompleteRegistrationNotificationEvent;

/**
 * Created by Nerio on 02/11/15.
 */
public class ActorAssetIssuerCompleteRegistrationNotificationEventHandler implements FermatEventHandler {

//    private ActorNetworkServiceAssetUser actorNetworkServiceAssetUser;
    private org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.AssetIssuerActorPluginRoot assetActorIssuerPluginRoot;

    public ActorAssetIssuerCompleteRegistrationNotificationEventHandler(org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.AssetIssuerActorPluginRoot assetActorIssuerPluginRoot) {
        this.assetActorIssuerPluginRoot = assetActorIssuerPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.println("Actor Asset Issuer Succesfull register - handleEvent =" + platformEvent);

        if (this.assetActorIssuerPluginRoot.getStatus() == ServiceStatus.STARTED) {

            ActorAssetIssuerCompleteRegistrationNotificationEvent completeActorAssetIssuerRegistrationNotificationEvent = (ActorAssetIssuerCompleteRegistrationNotificationEvent) platformEvent;
             /*
             *  ActorNetworkServiceAssetUser make the job
             */
            this.assetActorIssuerPluginRoot.handleCompleteAssetIssuerActorRegistrationNotificationEvent(completeActorAssetIssuerRegistrationNotificationEvent.getActorAssetIssuer());
        }
    }
}
