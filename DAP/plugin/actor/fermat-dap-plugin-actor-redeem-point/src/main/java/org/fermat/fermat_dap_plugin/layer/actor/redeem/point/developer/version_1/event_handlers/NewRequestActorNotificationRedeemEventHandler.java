package org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;

import org.fermat.fermat_dap_api.layer.all_definition.events.NewRequestActorNotificationEvent;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.RedeemPointActorPluginRoot;

/**
 * Created by Nerio on 23/03/16.
 */
public class NewRequestActorNotificationRedeemEventHandler implements FermatEventHandler {

    private RedeemPointActorPluginRoot redeemPointActorPluginRoot;

    public NewRequestActorNotificationRedeemEventHandler(RedeemPointActorPluginRoot redeemPointActorPluginRoot) {
        this.redeemPointActorPluginRoot = redeemPointActorPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.redeemPointActorPluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent.getSource().equals(EventSource.ACTOR_ASSET_REDEEM_POINT)) {
                System.out.println("ACTOR ASSET REDEEM RECEIVE CONNECTION - handleEvent = " + fermatEvent);
                NewRequestActorNotificationEvent newRequestActorNotificationEvent = (NewRequestActorNotificationEvent) fermatEvent;
             /*
              *  Actor Asset Issuer make the job
              */
                this.redeemPointActorPluginRoot.handleNewReceiveRequestActorRedeemNotificationEvent(
                        newRequestActorNotificationEvent.getActorNotification());
            }
        }
    }
}