package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantListNodesException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;

import java.util.Collection;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection</code>
 * contains all the method related with a network client connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public interface NetworkClientConnection {

    Collection<NodeProfile> listNodesByLocationAndNearness(Location location) throws CantListNodesException;

    /**
     * Method that verify if the connection object is
     * connected with the server.
     *
     * @return boolean
     */
    boolean isConnected();

    /**
     * Method that verify if the client is
     * registered on a server.
     *
     * @return boolean
     */
    boolean isRegistered();

}
