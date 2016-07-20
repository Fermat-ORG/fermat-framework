package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectedToNodeEvent;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.NetworkClientCommunicationPluginRoot;

/**
 * Created by Gabriel Araujo on 19/07/16.
 */
public class NetworkClientConnectedToNodeEventHandler implements FermatEventHandler<NetworkClientConnectedToNodeEvent> {

    /*
   * Represents the networkService
   */
    private NetworkClientCommunicationPluginRoot networkService;

    public NetworkClientConnectedToNodeEventHandler(NetworkClientCommunicationPluginRoot networkService) {
        this.networkService = networkService;
    }

    @Override
    public void handleEvent(NetworkClientConnectedToNodeEvent fermatEvent) throws FermatException {
        if (this.networkService.isStarted())
            networkService.register();
    }
}
