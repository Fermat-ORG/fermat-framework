/*
 * @#CommunicationsVPNConnection.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 14/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface CommunicationsVPNConnection {

    /**
     * Method that send a message to other component
     *
     * @param fermatMessage
     */
    public void sendMessage(FermatMessage fermatMessage);

    /**
     * Get the unread messages count
     *
     * @return int
     */
    public int getUnreadMessagesCount();

    /**
     * Read the next message receive
     *
     * @return FermatMessage
     */
    public FermatMessage readNextMessage();

    /**
     * Remove the message read
     *
     * @return FermatMessage
     */
    public void removeMessageRead(FermatMessage fermatMessage);

    /**
     * Get the isActive
     * @return boolean
     */
    public boolean isActive();

    /**
     * Close the connection
     */
    public void close();

    /**
     * Get the RemoteParticipant
     *
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getRemoteParticipant();

    /**
     * Get the RemoteParticipantNetworkService
     *
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getRemoteParticipantNetworkService();
}
