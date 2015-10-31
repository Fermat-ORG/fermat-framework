package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.CryptoAddressesMiddlewarePluginRoot;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CryptoAddressesMiddlewarePluginNotStartedException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesMiddlewareRegistry;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressDeniedEvent;

/**
 * The class <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.event_handlers.CryptoAddressDeniedEventHandler</code>
 * contains all the functionality to handle events of type CRYPTO_ADDRESS_DENIED.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressDeniedEventHandler implements FermatEventHandler {

    private final CryptoAddressesMiddlewareRegistry   cryptoAddressesMiddlewareRegistry  ;
    private final CryptoAddressesMiddlewarePluginRoot cryptoAddressesMiddlewarePluginRoot;

    public CryptoAddressDeniedEventHandler(final CryptoAddressesMiddlewareRegistry   cryptoAddressesMiddlewareRegistry  ,
                                           final CryptoAddressesMiddlewarePluginRoot cryptoAddressesMiddlewarePluginRoot) {

        this.cryptoAddressesMiddlewareRegistry   = cryptoAddressesMiddlewareRegistry  ;
        this.cryptoAddressesMiddlewarePluginRoot = cryptoAddressesMiddlewarePluginRoot;
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

        if (this.cryptoAddressesMiddlewarePluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent instanceof CryptoAddressDeniedEvent) {
                CryptoAddressDeniedEvent cryptoAddressDeniedEvent = (CryptoAddressDeniedEvent) fermatEvent;

                cryptoAddressesMiddlewareRegistry.handleCryptoAddressDeniedEvent(cryptoAddressDeniedEvent.getRequestId());

            } else {
                EventType eventExpected = EventType.CRYPTO_ADDRESS_DENIED;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                 "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new CryptoAddressesMiddlewarePluginNotStartedException("Plugin is not started.", "");
        }
    }
}