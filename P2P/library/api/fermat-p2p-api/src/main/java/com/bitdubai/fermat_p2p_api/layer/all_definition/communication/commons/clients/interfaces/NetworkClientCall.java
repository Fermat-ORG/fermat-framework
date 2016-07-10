package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;

/**
 * The abstract class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientCall</code>
 * is the representation of a network client call.
 * Contains all the necessary methods to send messages to other client.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public interface NetworkClientCall {

    /**
     * Through the method <code>sendPackageMessage</code> we can send messages to other Client.
     *
     * @param packageContent  content of the package.
     *
     * @throws CantSendMessageException if something goes wrong.
     */
    void sendPackageMessage(NetworkServiceMessage packageContent) throws CantSendMessageException;

    /**
     * Through the method <code>hangUp</code> we can hang up the call.
     */
    void hangUp();

    /**
     * Through the method <code>getNetworkServiceType</code> we can know to which network service is directed to.
     *
     * @return an element of NetworkServiceType enum.
     */
    NetworkServiceType getNetworkServiceType();

    /**
     * Through the method <code>getProfile</code> we can know to which profile is directed to.
     *
     * @return an instance of a profile.
     */
    Profile getProfile();

    boolean isConnected();

}
