/*
 * @#Message.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.FermatMessage</code> represent
 * the message
 * <p/>
 *
 * Created by ciencias on 2/23/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 11/06/15.
 *
 * @version 1.0
 */
public interface Message {

    /**
     * Set the text content
     *
     * @param content
     */
    public void setTextContent (String content);

    /**
     * Get the text content
     *
     * @return String
     */
    public String getTextContent ();

    /**
     * Get the status
     *
     * @return MessagesStatus
     */
    public MessagesStatus getStatus();

    /**
     * Convert this object to json string
     *
     * @return String json
     */
    public String toJson();

    /**
     * Convert to FermatPacketCommunication from json
     *
     * @param json string object
     * @return FermatMessage
     */
    public Message fromJson(String json);
    
}
