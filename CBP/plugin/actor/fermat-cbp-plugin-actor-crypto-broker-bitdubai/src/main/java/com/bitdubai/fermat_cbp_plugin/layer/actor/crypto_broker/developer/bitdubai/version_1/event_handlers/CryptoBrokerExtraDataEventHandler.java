package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionRequestNewsEvent;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions.CryptoBrokerActorExtraDataNotStartedException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure.ActorExtraDataEventActions;

public class CryptoBrokerExtraDataEventHandler implements FermatEventHandler {

    private ActorExtraDataEventActions actorExtraDataEventAction;
    private CryptoBrokerActorPluginRoot cryptoBrokerActorPluginRoot;

    public CryptoBrokerExtraDataEventHandler(final ActorExtraDataEventActions actorExtraDataEventActions,
                                             final CryptoBrokerActorPluginRoot cryptoBrokerActorPluginRoot) {

        this.actorExtraDataEventAction   = actorExtraDataEventActions;
        this.cryptoBrokerActorPluginRoot = cryptoBrokerActorPluginRoot;
    }

    /**
     * FermatEventHandler interface implementation
     *
     * Plugin is started?
     * The event is the expected event?
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.cryptoBrokerActorPluginRoot.getStatus() == ServiceStatus.STARTED) {
            /*
            // TODO: cambiar el CryptoBrokerConnectionRequestNewsEvent por el evento real
            if (fermatEvent instanceof CryptoBrokerConnectionRequestNewsEvent) {

                this.actorExtraDataEventAction.handleNewsEvent();

            } else {
                // TODO: cambiar el EventType
                EventType eventExpected = EventType.CRYPTO_BROKER_CONNECTION_REQUEST_NEWS;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                        "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
            */
        } else {
            throw new CryptoBrokerActorExtraDataNotStartedException("Plugin is not started.", "");
        }
    }
}