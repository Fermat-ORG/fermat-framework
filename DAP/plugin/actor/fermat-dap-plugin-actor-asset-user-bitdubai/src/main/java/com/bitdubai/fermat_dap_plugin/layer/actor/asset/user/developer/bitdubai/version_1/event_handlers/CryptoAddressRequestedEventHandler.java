package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.AssetActorUserPluginRoot;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressRequestedEventHandler implements FermatEventHandler {
    AssetActorUserPluginRoot assetActorUserPluginRoot;

    public CryptoAddressRequestedEventHandler(AssetActorUserPluginRoot assetActorUserPluginRoot){
             this.assetActorUserPluginRoot = assetActorUserPluginRoot;
    }


    /**
     * FermatEventHandler interface implementation
     *
     * Plugin is started?
     * The event is the expected event?
     * Belongs to this Actor Type?
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.assetActorUserPluginRoot.getStatus() == ServiceStatus.STARTED) {

           /* if (fermatEvent instanceof CryptoAddressRequestedEvent) {
                CryptoAddressRequestedEvent cryptoAddressRequestedEvent = (CryptoAddressRequestedEvent) fermatEvent;

                if (cryptoAddressRequestedEvent.getActorType().equals(Actors.DAP_ASSET_USER))
                    assetActorUserPluginRoot.getGenesisAddress(cryptoAddressRequestedEvent.getRequestId());

            } else {
                EventType eventExpected = EventType.CRYPTO_ADDRESS_REQUESTED;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                 "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {*/
        }
    }
}