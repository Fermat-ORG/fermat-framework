package org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;

import org.fermat.fermat_dap_api.layer.all_definition.events.NewRequestActorNotificationEvent;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.RedeemPointActorPluginRoot;

/**
 * Created by Nerio on 26/11/15.
 */
public class NewReceiveExtendedNotificationEventHandler implements FermatEventHandler {

    private RedeemPointActorPluginRoot redeemPointActorPluginRoot;

    public NewReceiveExtendedNotificationEventHandler(RedeemPointActorPluginRoot redeemPointActorPluginRoot) {
        this.redeemPointActorPluginRoot = redeemPointActorPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.redeemPointActorPluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent.getSource().equals(EventSource.ACTOR_ASSET_ISSUER)) {
                System.out.println("ACTOR ASSET REDEEM POINT RECEIVE EXTENDED KEY - handleEvent = " + fermatEvent);
                NewRequestActorNotificationEvent newRequestActorNotificationEvent = (NewRequestActorNotificationEvent) fermatEvent;
             /*
              *  Actor Asset Issuer make the job
              */
                this.redeemPointActorPluginRoot.handleNewReceiveMessageActorNotificationEvent(
                        newRequestActorNotificationEvent.getActorNotification());
            }

//            if (fermatEvent.getSource().equals(EventSource.NETWORK_SERVICE_ACTOR_ASSET_ISSUER)) {
//                System.out.println("ACTOR ASSET REDEEM POINT RECEIVE MESSAGE REGISTER - handleEvent = " + fermatEvent);
//                NewReceiveMessageActorNotificationEvent newReceiveMessageActorNotificationEvent = (NewReceiveMessageActorNotificationEvent) fermatEvent;
//                DAPMessage message = newReceiveMessageActorNotificationEvent.getMessage();
//
//                if(message.getActorSender() instanceof ActorAssetRedeemPoint) {
//                    System.out.println("SAVING REDEEM POINT ON REGISTERED TABLE");
//                    this.redeemPointActorPluginRoot.saveRegisteredActorRedeemPoint((ActorAssetRedeemPoint) message.getActorSender());
//                }
//            }
        }
    }
}