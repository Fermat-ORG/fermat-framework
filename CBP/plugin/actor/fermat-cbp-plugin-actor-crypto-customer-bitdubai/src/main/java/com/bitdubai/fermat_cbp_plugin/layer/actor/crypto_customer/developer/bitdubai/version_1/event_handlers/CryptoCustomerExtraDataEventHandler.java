package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.events.GenericCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CryptoCustomerActorExtraDataNotStartedException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.ActorCustomerExtraDataEventActions;

public class CryptoCustomerExtraDataEventHandler implements FermatEventHandler {

    private ActorCustomerExtraDataEventActions actorExtraDataEventAction;
    private CryptoCustomerActorPluginRoot cryptoCustomerActorPluginRoot;

    public CryptoCustomerExtraDataEventHandler(final ActorCustomerExtraDataEventActions actorExtraDataEventActions,
                                               final CryptoCustomerActorPluginRoot cryptoCustomerActorPluginRoot) {

        this.actorExtraDataEventAction = actorExtraDataEventActions;
        this.cryptoCustomerActorPluginRoot = cryptoCustomerActorPluginRoot;
    }

    /**
     * FermatEventHandler interface implementation
     * <p/>
     * Plugin is started?
     * The event is the expected event?
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.cryptoCustomerActorPluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent instanceof GenericCBPFermatEvent) {
                this.actorExtraDataEventAction.handleNewsEvent();
            } else {
                EventType eventExpected = EventType.CRYPTO_BROKER_QUOTES_REQUEST_UPDATES;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode() + "\n" + "Event expected: " + eventExpected.toString() + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new CryptoCustomerActorExtraDataNotStartedException("Plugin is not started.", "");
        }
    }
}