package org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;

import org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetRedeemPointCompleteRegistrationNotificationEvent;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.RedeemPointActorPluginRoot;

/**
 * Created by Nerio on 02/11/15.
 */
public class ActorAssetRedeemPointCompleteRegistrationNotificationEventHandler implements FermatEventHandler {

    //    private ActorNetworkServiceAssetUser actorNetworkServiceAssetUser;
    private RedeemPointActorPluginRoot redeemPointPluginRoot;

    public ActorAssetRedeemPointCompleteRegistrationNotificationEventHandler(RedeemPointActorPluginRoot redeemPointPluginRoot) {
        this.redeemPointPluginRoot = redeemPointPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.println("Actor Asset Redeem Point Succesfull register - handleEvent =" + platformEvent);

        if (this.redeemPointPluginRoot.getStatus() == ServiceStatus.STARTED) {

            ActorAssetRedeemPointCompleteRegistrationNotificationEvent actorAssetRedeemPointCompleteRegistrationNotificationEvent = (ActorAssetRedeemPointCompleteRegistrationNotificationEvent) platformEvent;
             /*
             *  ActorNetworkServiceAssetUser make the job
             */
            this.redeemPointPluginRoot.handleCompleteActorAssetRedeemPointRegistrationNotificationEvent(actorAssetRedeemPointCompleteRegistrationNotificationEvent.getActorAssetRedeemPoint());
        }
    }
}