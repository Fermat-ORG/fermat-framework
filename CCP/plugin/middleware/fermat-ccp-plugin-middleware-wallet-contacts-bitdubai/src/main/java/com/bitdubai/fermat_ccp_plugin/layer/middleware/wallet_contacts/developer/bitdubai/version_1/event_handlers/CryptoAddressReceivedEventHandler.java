package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressReceivedEvent;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.WalletContactsMiddlewarePluginNotStartedException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressReceivedEventHandler implements FermatEventHandler {

    private final WalletContactsMiddlewareRegistry   walletContactsMiddlewareRegistry;
    private final WalletContactsMiddlewarePluginRoot walletContactsMiddlewarePluginRoot;

    public CryptoAddressReceivedEventHandler(final WalletContactsMiddlewareRegistry   walletContactsMiddlewareRegistry,
                                             final WalletContactsMiddlewarePluginRoot walletContactsMiddlewarePluginRoot  ) {

        this.walletContactsMiddlewareRegistry   = walletContactsMiddlewareRegistry;
        this.walletContactsMiddlewarePluginRoot = walletContactsMiddlewarePluginRoot;
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

        if (this.walletContactsMiddlewarePluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent instanceof CryptoAddressReceivedEvent) {
                CryptoAddressReceivedEvent cryptoAddressReceivedEvent = (CryptoAddressReceivedEvent) fermatEvent;

                if (cryptoAddressReceivedEvent.getActorType().equals(WalletContactsMiddlewarePluginRoot.actorType))
                    walletContactsMiddlewareRegistry.handleCryptoAddressReceivedEvent(cryptoAddressReceivedEvent.getRequestId());

            } else {
                EventType eventExpected = EventType.CRYPTO_ADDRESS_RECEIVED;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                 "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new WalletContactsMiddlewarePluginNotStartedException(null, "Plugin is not started.", "");
        }
    }
}