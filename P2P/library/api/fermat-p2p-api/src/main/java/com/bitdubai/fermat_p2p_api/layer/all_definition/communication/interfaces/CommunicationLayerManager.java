package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.interfaces;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;

import java.util.Collection;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.interfaces.CommunicationLayerManager</code>
 * expose the public methods of the communication layer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public interface CommunicationLayerManager {

    /**
     * Through this method we can get a default connection object from the communication layer.
     *
     * @return an instance of a network client connection.
     */
    NetworkClientConnection getNetworkClientConnection();

    /**
     * Through this method we can get a connection object from the communication layer.
     *
     * @param communicationChannel indicates which type of connection do we need
     *
     * @return an instance of a network client connection.
     */
    NetworkClientConnection getNetworkClientConnection(CommunicationChannels communicationChannel);

    /**
     * Through this method we can know which communication channels are active in our device.
     *
     * @return a list of available communication channels.
     */
    Collection<CommunicationChannels> listAvailableCommunicationChannels();

    /**
     * Through this method we can know if our device is connected.
     *
     * @return a boolean indicating if is connected or not.
     */
    boolean isConnected();

    /**
     * Through this method we can know if our device is connected.
     *
     * @param communicationChannel indicates which type of connection do we need
     *
     * @return a boolean indicating if is connected or not.
     */
    boolean isConnected(CommunicationChannels communicationChannel);

}
