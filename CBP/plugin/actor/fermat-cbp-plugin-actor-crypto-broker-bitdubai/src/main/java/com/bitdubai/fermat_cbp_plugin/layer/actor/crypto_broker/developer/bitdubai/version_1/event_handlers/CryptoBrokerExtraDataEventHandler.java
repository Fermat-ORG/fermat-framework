package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.events.GenericCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions.CryptoBrokerActorExtraDataNotStartedException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure.ActorBrokerExtraDataEventActions;

public class CryptoBrokerExtraDataEventHandler implements FermatEventHandler {

    private ActorBrokerExtraDataEventActions actorExtraDataEventAction;
    private CryptoBrokerActorPluginRoot cryptoCustomerActorPluginRoot;

    public CryptoBrokerExtraDataEventHandler(final ActorBrokerExtraDataEventActions actorExtraDataEventActions,
                                             final CryptoBrokerActorPluginRoot cryptoCustomerActorPluginRoot) {

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
                EventType eventExpected = EventType.CRYPTO_BROKER_QUOTES_REQUEST_NEWS;
                String context = new StringBuilder().append("Event received: ").append(fermatEvent.getEventType().toString()).append(" - ").append(fermatEvent.getEventType().getCode()).append("\n").append("Event expected: ").append(eventExpected.toString()).append(" - ").append(eventExpected.getCode()).toString();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new CryptoBrokerActorExtraDataNotStartedException("Plugin is not started.", "");
        }
    }
}