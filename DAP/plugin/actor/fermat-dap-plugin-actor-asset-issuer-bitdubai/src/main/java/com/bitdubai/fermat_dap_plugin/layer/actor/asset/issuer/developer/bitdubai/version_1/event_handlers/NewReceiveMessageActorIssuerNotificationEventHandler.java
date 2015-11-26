package com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewReceiveMessageActorNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewRequestMessageActorNotificationEvent;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.AssetIssuerActorPluginRoot;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewReceiveMessageActorIssuerNotificationEventHandler implements FermatEventHandler {

    private AssetIssuerActorPluginRoot assetActorIssuerPluginRoot;

    public NewReceiveMessageActorIssuerNotificationEventHandler(AssetIssuerActorPluginRoot assetActorIssuerPluginRoot) {
        this.assetActorIssuerPluginRoot = assetActorIssuerPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        System.out.println("ACTOR ASSET ISSUER RECEIVE MESSAGE REGISTER - handleEvent = " + fermatEvent);

        if (this.assetActorIssuerPluginRoot.getStatus() == ServiceStatus.STARTED) {

            NewReceiveMessageActorNotificationEvent newReceiveMessageActorNotificationEvent = (NewReceiveMessageActorNotificationEvent) fermatEvent;
             /*
             *  Actor Asset Issuer make the job
             */
            this.assetActorIssuerPluginRoot.handleNewReceiveMessageActorNotificationEvent(newReceiveMessageActorNotificationEvent.getActorAssetSender(), newReceiveMessageActorNotificationEvent.getMessage());
        }
    }
}