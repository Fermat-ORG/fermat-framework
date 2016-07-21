package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorFoundEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorListReceivedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientActorListReceivedEventHandler</code>
 * implements the handling of the event <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorListReceivedEvent</code>
 * in a basic network service.
 * reference: <code>P2pEventType.NETWORK_CLIENT_ACTOR_LIST_RECEIVED</code>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientActorListReceivedEventHandler implements FermatEventHandler<NetworkClientActorListReceivedEvent> {

    /**
     * Represent the networkService
     */
    private AbstractNetworkService networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public NetworkClientActorListReceivedEventHandler(AbstractNetworkService networkService) {
        this.networkService = networkService;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param fermatEvent instance of NetworkClientActorFoundEvent
     *
     * @throws FermatException if something goes wrong.
     */
    @Override
    public void handleEvent(NetworkClientActorListReceivedEvent fermatEvent) throws FermatException {

        if (this.networkService.isStarted() &&
                this.networkService.getProfile().getIdentityPublicKey().equals(fermatEvent.getNetworkServicePublicKey())) {

            if (fermatEvent.getStatus() == NetworkClientActorListReceivedEvent.STATUS.SUCCESS)
                this.networkService.handleNetworkClientActorListReceivedEvent(fermatEvent.getQueryID(), fermatEvent.getActorList());
            else
                System.out.println("ERROR IN THE QUERY WITH ID: "+ fermatEvent.getQueryID());
        }

    }

}
