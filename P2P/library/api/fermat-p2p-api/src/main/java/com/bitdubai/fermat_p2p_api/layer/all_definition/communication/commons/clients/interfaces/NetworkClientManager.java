package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientManager</code>
 * contains all the methods related with a NetworkClientManager.
 *
 * A network call channel its a valid instance of a vpn connection with other actor or network service.
 *
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public interface NetworkClientManager {

    /**
     * Get a NetworkClientConnection
     *
     * @return NetworkClientConnection instance
     */
    NetworkClientConnection getConnection();

    /**
     * Get a NetworkClientConnection from uriToNode
     * @param uriToNode
     * @return NetworkClientConnection instance
     */
    NetworkClientConnection getConnection(String uriToNode);

}
