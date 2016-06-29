package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientProfileRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractActorNetworkService;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientActorProfileRegisteredEventHandler</code>
 * implements the handling of the event <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientProfileRegisteredEvent</code>
 * reference: <code>P2pEventType.NETWORK_CLIENT_ACTOR_PROFILE_REGISTERED</code>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientActorProfileRegisteredEventHandler implements FermatEventHandler<NetworkClientProfileRegisteredEvent> {

    /*
    * Represent the networkService
    */
    private AbstractActorNetworkService networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public NetworkClientActorProfileRegisteredEventHandler(AbstractActorNetworkService networkService) {
        this.networkService = networkService;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param fermatEvent instance of NetworkClientRegisteredEvent
     *
     * @throws FermatException if something goes wrong.
     */
    @Override
    public void handleEvent(NetworkClientProfileRegisteredEvent fermatEvent) throws FermatException {

        if (this.networkService.isStarted())
            if (fermatEvent.getStatus() == NetworkClientProfileRegisteredEvent.STATUS.SUCCESS)
                networkService.onActorRegistered(fermatEvent.getPublicKey());

    }
}
