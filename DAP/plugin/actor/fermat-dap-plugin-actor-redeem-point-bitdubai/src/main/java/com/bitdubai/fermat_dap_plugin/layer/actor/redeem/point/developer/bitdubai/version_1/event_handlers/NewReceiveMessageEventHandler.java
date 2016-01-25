package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_bch_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewReceiveMessageActorNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.RedeemPointActorPluginRoot;

/**
 * Created by Nerio on 26/11/15.
 */
public class NewReceiveMessageEventHandler implements FermatEventHandler {

    private RedeemPointActorPluginRoot redeemPointActorPluginRoot;

    public NewReceiveMessageEventHandler(RedeemPointActorPluginRoot redeemPointActorPluginRoot) {
        this.redeemPointActorPluginRoot = redeemPointActorPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.redeemPointActorPluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent.getSource().equals(EventSource.NETWORK_SERVICE_ACTOR_ASSET_REDEEM_POINT)) {
                System.out.println("ACTOR ASSET REDEEM POINT RECEIVE MESSAGE REGISTER - handleEvent = " + fermatEvent);
                NewReceiveMessageActorNotificationEvent newReceiveMessageActorNotificationEvent = (NewReceiveMessageActorNotificationEvent) fermatEvent;
             /*
              *  Actor Asset Issuer make the job
              */
                this.redeemPointActorPluginRoot.handleNewReceiveMessageActorNotificationEvent(
                        newReceiveMessageActorNotificationEvent.getMessage());
            }

            if (fermatEvent.getSource().equals(EventSource.NETWORK_SERVICE_ACTOR_ASSET_ISSUER)) {
                System.out.println("ACTOR ASSET REDEEM POINT RECEIVE MESSAGE REGISTER - handleEvent = " + fermatEvent);
                NewReceiveMessageActorNotificationEvent newReceiveMessageActorNotificationEvent = (NewReceiveMessageActorNotificationEvent) fermatEvent;
                DAPMessage message = newReceiveMessageActorNotificationEvent.getMessage();

                if(message.getActorSender() instanceof ActorAssetRedeemPoint) {
                    System.out.println("SAVING REDEEM POINT ON REGISTERED TABLE");
                    this.redeemPointActorPluginRoot.saveRegisteredActorRedeemPoint((ActorAssetRedeemPoint) message.getActorSender());
                }
            }
        }
    }
}