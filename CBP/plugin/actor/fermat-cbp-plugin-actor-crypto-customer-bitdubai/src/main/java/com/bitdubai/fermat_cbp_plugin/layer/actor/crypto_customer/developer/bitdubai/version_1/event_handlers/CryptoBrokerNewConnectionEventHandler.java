package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.events.CryptoBrokerActorConnectionNewConnectionEvent;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CryptoCustomerActorExtraDataNotStartedException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.ActorCustomerExtraDataEventActions;

public class CryptoBrokerNewConnectionEventHandler implements FermatEventHandler {

    private ActorCustomerExtraDataEventActions actorExtraDataEventAction;
    private CryptoCustomerActorPluginRoot cryptoCustomerActorPluginRoot;

    public CryptoBrokerNewConnectionEventHandler(final ActorCustomerExtraDataEventActions actorExtraDataEventActions,
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
            if (fermatEvent instanceof CryptoBrokerActorConnectionNewConnectionEvent) {
                this.actorExtraDataEventAction.handleNewConnectionEvent();
            } else {
                EventType eventExpected = EventType.CRYPTO_BROKER_ACTOR_CONNECTION_NEW_CONNECTION;
                String context = new StringBuilder().append("Event received: ").append(fermatEvent.getEventType().toString()).append(" - ").append(fermatEvent.getEventType().getCode()).append("\n").append("Event expected: ").append(eventExpected.toString()).append(" - ").append(eventExpected.getCode()).toString();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new CryptoCustomerActorExtraDataNotStartedException("Plugin is not started.", "");
        }
    }
}