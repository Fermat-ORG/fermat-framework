package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewReceiveMessageActorNotificationEvent;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.RedeemPointActorPluginRoot;

/**
 * Created by Nerio on 26/11/15.
 */
public class NewReceiveMessageActorRedeemPointNotificationEventHandler implements FermatEventHandler {

    private RedeemPointActorPluginRoot redeemPointActorPluginRoot;

    public NewReceiveMessageActorRedeemPointNotificationEventHandler(RedeemPointActorPluginRoot redeemPointActorPluginRoot) {
        this.redeemPointActorPluginRoot = redeemPointActorPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        System.out.println("ACTOR ASSET REDEEM POINT RECEIVE MESSAGE REGISTER - handleEvent = " + fermatEvent);

        if (this.redeemPointActorPluginRoot.getStatus() == ServiceStatus.STARTED) {

            NewReceiveMessageActorNotificationEvent newReceiveMessageActorNotificationEvent = (NewReceiveMessageActorNotificationEvent) fermatEvent;
             /*
             *  Actor Asset Issuer make the job
             */
            this.redeemPointActorPluginRoot.handleNewReceiveMessageActorNotificationEvent(newReceiveMessageActorNotificationEvent.getActorAssetSender(), newReceiveMessageActorNotificationEvent.getMessage());
        }
    }
}