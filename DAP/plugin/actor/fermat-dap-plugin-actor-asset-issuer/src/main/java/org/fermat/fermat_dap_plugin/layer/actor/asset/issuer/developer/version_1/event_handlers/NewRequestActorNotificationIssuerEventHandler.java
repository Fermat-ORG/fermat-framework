package org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;

import org.fermat.fermat_dap_api.layer.all_definition.events.NewRequestActorNotificationEvent;
import org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.AssetIssuerActorPluginRoot;

/**
 * Created by Nerio on 23/03/16.
 */
public class NewRequestActorNotificationIssuerEventHandler implements FermatEventHandler {

    private AssetIssuerActorPluginRoot assetIssuerActorPluginRoot;

    public NewRequestActorNotificationIssuerEventHandler(AssetIssuerActorPluginRoot assetIssuerActorPluginRoot) {
        this.assetIssuerActorPluginRoot = assetIssuerActorPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.assetIssuerActorPluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent.getSource().equals(EventSource.ACTOR_ASSET_ISSUER)) {
                System.out.println("ACTOR ASSET ISSUER RECEIVE CONNECTION - handleEvent = " + fermatEvent);
                NewRequestActorNotificationEvent newRequestActorNotificationEvent = (NewRequestActorNotificationEvent) fermatEvent;
             /*
              *  Actor Asset Issuer make the job
              */
                this.assetIssuerActorPluginRoot.handleNewReceiveRequestActorIssuerNotificationEvent(
                        newRequestActorNotificationEvent.getActorNotification());
            }
        }
    }
}