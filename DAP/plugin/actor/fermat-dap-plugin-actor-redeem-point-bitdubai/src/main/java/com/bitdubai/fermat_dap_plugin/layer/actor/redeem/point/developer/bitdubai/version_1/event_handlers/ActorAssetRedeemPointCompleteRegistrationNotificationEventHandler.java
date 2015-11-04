package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetRedeemPointCompleteRegistrationNotificationEvent;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.RedeemPointPluginRoot;

/**
 * Created by Nerio on 02/11/15.
 */
public class ActorAssetRedeemPointCompleteRegistrationNotificationEventHandler implements FermatEventHandler {

    //    private ActorNetworkServiceAssetUser actorNetworkServiceAssetUser;
    private RedeemPointPluginRoot redeemPointPluginRoot;

    public ActorAssetRedeemPointCompleteRegistrationNotificationEventHandler(RedeemPointPluginRoot redeemPointPluginRoot) {
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