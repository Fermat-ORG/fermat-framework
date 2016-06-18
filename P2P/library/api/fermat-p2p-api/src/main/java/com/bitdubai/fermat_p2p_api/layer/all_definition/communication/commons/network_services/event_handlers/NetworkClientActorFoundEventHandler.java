package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorFoundEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientActorFoundEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/05/16.
 *
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientActorFoundEventHandler implements FermatEventHandler<NetworkClientActorFoundEvent> {

    /**
     * Represent the networkService
     */
    private AbstractNetworkService networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public NetworkClientActorFoundEventHandler(AbstractNetworkService networkService) {
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
    public void handleEvent(NetworkClientActorFoundEvent fermatEvent) throws FermatException {

        if (this.networkService.isStarted() &&
                this.networkService.getProfile().getNetworkServiceType() == fermatEvent.getNetworkServiceTypeIntermediate()) {

            this.networkService.handleActorFoundEvent(fermatEvent.getActorProfile());
        }

    }

}
