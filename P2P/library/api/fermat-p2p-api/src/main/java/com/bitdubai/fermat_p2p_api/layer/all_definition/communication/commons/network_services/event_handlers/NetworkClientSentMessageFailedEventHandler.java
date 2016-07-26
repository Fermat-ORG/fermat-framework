package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageFailedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 14/07/16.
 */
public class NetworkClientSentMessageFailedEventHandler implements FermatEventHandler<NetworkClientNewMessageFailedEvent> {

    /**
     * Represent the networkService
     */
    private AbstractNetworkService networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public NetworkClientSentMessageFailedEventHandler(AbstractNetworkService networkService) {
        this.networkService = networkService;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param fermatEvent instance of NetworkClientConnectionLostEvent
     *
     * @throws com.bitdubai.fermat_api.FermatException if something goes wrong.
     */
    @Override
    public void handleEvent(NetworkClientNewMessageFailedEvent fermatEvent) throws FermatException {
//        System.out.println("12345P2P INSIDE FAILED MESSAGE EVENT");

        if (this.networkService.isStarted() &&
                this.networkService.getProfile().getNetworkServiceType().equals(fermatEvent.getNetworkServiceTypeSource())) {

            if(networkService.getNetworkServiceConnectionManager().getOutgoingMessagesDao().exists(fermatEvent.getId()))
                networkService.onNetworkServiceFailedMessage(networkService.getNetworkServiceConnectionManager().getOutgoingMessagesDao().findById(fermatEvent.getId()));

        }

    }

}
