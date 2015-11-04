package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressRequestedEvent;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.IntraWalletUserIdentityPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.IntraWalletUserIdentityPluginNotStartedException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentityCryptoAddressGenerationService;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressRequestedEventHandler implements FermatEventHandler {

    private final IntraWalletUserIdentityCryptoAddressGenerationService cryptoAddressGenerationService;
    private final IntraWalletUserIdentityPluginRoot intraUserIdentityPluginRoot;

    public CryptoAddressRequestedEventHandler(final IntraWalletUserIdentityPluginRoot intraUserIdentityPluginRoot,
                                              final IntraWalletUserIdentityCryptoAddressGenerationService cryptoAddressGenerationService) {
        this.cryptoAddressGenerationService = cryptoAddressGenerationService;
        this.intraUserIdentityPluginRoot    = intraUserIdentityPluginRoot;
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

      //  if (this.intraUserIdentityPluginRoot.isStarted()) {

            if (fermatEvent instanceof CryptoAddressRequestedEvent) {
                CryptoAddressRequestedEvent cryptoAddressRequestedEvent = (CryptoAddressRequestedEvent) fermatEvent;

                //if (cryptoAddressRequestedEvent.getActorType().equals(Actors.INTRA_USER))
                    //cryptoAddressGenerationService.handleCryptoAddressRequestedEvent(cryptoAddressRequestedEvent.getRequestId());

            } else {
                EventType eventExpected = EventType.CRYPTO_ADDRESS_REQUESTED;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                 "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
     /*   } else {
            throw new IntraWalletUserIdentityPluginNotStartedException(null, "Plugin is not started.", "");
        }*/
    }
}