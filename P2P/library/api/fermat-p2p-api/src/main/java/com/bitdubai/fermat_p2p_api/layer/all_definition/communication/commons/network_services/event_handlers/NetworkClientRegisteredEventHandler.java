package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientRegisteredEventHandler</code>
 * implements the handling of the event <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientRegisteredEvent</code>
 * reference: <code>P2pEventType.NETWORK_CLIENT_REGISTERED</code>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientRegisteredEventHandler implements FermatEventHandler<NetworkClientRegisteredEvent> {

    /*
    * Represent the networkService
    */
    private AbstractNetworkService networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public NetworkClientRegisteredEventHandler(AbstractNetworkService networkService) {
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
    public void handleEvent(NetworkClientRegisteredEvent fermatEvent) throws FermatException {
        //todo: esto lo saqué, ahora usamos la capa para registrar.
        //if (this.networkService.isStarted())
            //networkService.handleNetworkClientRegisteredEvent(fermatEvent.getCommunicationChannel());

    }
}
