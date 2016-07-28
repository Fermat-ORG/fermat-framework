package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionRequestUpdatesEvent;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorConnectionPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.exceptions.CryptoCustomerActorConnectionNotStartedException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure.ActorConnectionEventActions;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.event_handlers.CryptoBrokerConnectionRequestUpdatesEventHandler</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/02/2016.
 */
public class CryptoBrokerConnectionRequestUpdatesEventHandler implements FermatEventHandler {

    private final ActorConnectionEventActions actorConnectionEventActions;
    private final CryptoCustomerActorConnectionPluginRoot cryptoCustomerActorConnectionPluginRoot;

    public CryptoBrokerConnectionRequestUpdatesEventHandler(final ActorConnectionEventActions actorConnectionEventActions,
                                                            final CryptoCustomerActorConnectionPluginRoot cryptoCustomerActorConnectionPluginRoot) {

        this.actorConnectionEventActions = actorConnectionEventActions;
        this.cryptoCustomerActorConnectionPluginRoot = cryptoCustomerActorConnectionPluginRoot;
    }

    /**
     * FermatEventHandler interface implementation
     * <p/>
     * Plugin is started?
     * The event is the expected event?
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.cryptoCustomerActorConnectionPluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent instanceof CryptoBrokerConnectionRequestUpdatesEvent) {

                actorConnectionEventActions.handleCryptoBrokerUpdateEvent();

            } else {
                EventType eventExpected = EventType.CRYPTO_BROKER_CONNECTION_REQUEST_UPDATES;
                String context = new StringBuilder().append("Event received: ").append(fermatEvent.getEventType().toString()).append(" - ").append(fermatEvent.getEventType().getCode()).append("\n").append("Event expected: ").append(eventExpected.toString()).append(" - ").append(eventExpected.getCode()).toString();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new CryptoCustomerActorConnectionNotStartedException("Plugin is not started.", "");
        }
    }
}