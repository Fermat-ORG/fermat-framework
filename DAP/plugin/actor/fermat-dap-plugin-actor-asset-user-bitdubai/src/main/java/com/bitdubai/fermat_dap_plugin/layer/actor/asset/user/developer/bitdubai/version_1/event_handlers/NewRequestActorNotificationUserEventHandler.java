package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressesNewsEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewRequestActorNotificationEvent;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.AssetUserActorPluginRoot;

import java.util.List;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewRequestActorNotificationUserEventHandler implements FermatEventHandler {

    private AssetUserActorPluginRoot assetUserActorPluginRoot;

    public NewRequestActorNotificationUserEventHandler(AssetUserActorPluginRoot assetUserActorPluginRoot) {
        this.assetUserActorPluginRoot = assetUserActorPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.assetUserActorPluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent.getSource().equals(EventSource.ACTOR_ASSET_USER)) {
                System.out.println("ACTOR ASSET USER RECEIVE CONNECTION - handleEvent = " + fermatEvent);
                NewRequestActorNotificationEvent newRequestActorNotificationEvent = (NewRequestActorNotificationEvent) fermatEvent;
             /*
              *  Actor Asset Issuer make the job
              */
                this.assetUserActorPluginRoot.handleNewReceiveRequestActorUserNotificationEvent(
                        newRequestActorNotificationEvent.getActorNotification());
            }
        }
    }
}