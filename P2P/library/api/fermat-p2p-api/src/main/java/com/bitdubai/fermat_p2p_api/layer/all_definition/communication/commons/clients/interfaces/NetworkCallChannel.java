package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkCallChannel</code>
 * contains all the methods related with a network call.
 *
 * A network call channel its a valid instance of a vpn connection with other actor or network service.
 *
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public interface NetworkCallChannel {

    /**
     * Through the method <code>sendMessage</code> we can send a message to the counterpart.
     *
     * @param fermatMessage an instance of the fermat message that we're trying to send.
     *
     * @throws CantSendMessageException if something goes wrong.
     */
    void sendMessage(FermatMessage fermatMessage) throws CantSendMessageException;

    /**
     * Through the method <code>isActive</code> we can get a boolean value indicating
     * if the network call is still active.
     *
     * @return a boolean value.
     */
    boolean isActive();

    /**
     * Through the method <code>getUnreadMessagesCount</code> you can get the number of unread
     * messages in the network call channel.
     *
     * @return int indicating the quantity of unread messages.
     */
    int getUnreadMessagesCount();

    /**
     * Through the method <code>getNextUnreadMessage</code> you can get the next unread message
     * in the network call channel.
     *
     * @return an instance of an unread FermatMessage.
     */
    FermatMessage getNextUnreadMessage();

    /**
     * Through the method <code>markMessageAsRead</code> you can delete from the list of
     * unread messages of the network call channel an specific fermat message.
     *
     * @param fermatMessage an instance of the fermat message that we already read.
     */
    void markMessageAsRead(FermatMessage fermatMessage);

    /**
     * Through the method <code>closeChannel</code> we can close the network channel.
     */
    void closeChannel();

    /**
     * Through the method <code>getRemoteParticipant</code> we can get the profile
     * of the remote participant.
     *
     * @return an instance of the profile.
     */
    Profile getRemoteParticipant();

    /**
     * Through the method <code>getRemoteParticipantNetworkService</code> we can get the profile
     * of the parent of the remote participant.
     *
     * @return an instance of the profile.
     */
    NetworkServiceProfile getRemoteParticipantNetworkService();

}
