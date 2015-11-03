package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.CryptoAddressesMiddlewarePluginRoot;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CryptoAddressesMiddlewarePluginNotStartedException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressMiddlewareExecutorService;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressesNewsEvent;

/**
 * The class <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.event_handlers.CryptoAddressesNewsEventHandler</code>
 * contains all the functionality to handle events of type CRYPTO_ADDRESSES_NEWS.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesNewsEventHandler implements FermatEventHandler {

    private final CryptoAddressMiddlewareExecutorService executorService;
    private final CryptoAddressesMiddlewarePluginRoot    pluginRoot  ;

    public CryptoAddressesNewsEventHandler(final CryptoAddressMiddlewareExecutorService executorService,
                                           final CryptoAddressesMiddlewarePluginRoot    pluginRoot     ) {

        this.executorService = executorService;
        this.pluginRoot      = pluginRoot     ;
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

        if (this.pluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent instanceof CryptoAddressesNewsEvent) {

                executorService.executePendingActions();

            } else {
                EventType eventExpected = EventType.CRYPTO_ADDRESSES_NEWS;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                 "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new CryptoAddressesMiddlewarePluginNotStartedException("Plugin is not started.", "");
        }
    }
}