/*
 * @#MessageProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors;


import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Message;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketChannelServerEndpoint;

import javax.websocket.Session;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.MessageProcessor</code>
 * is the base class for all message processor class <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class MessageProcessor {

    /**
     * Represent the webSocketChannelServerEndpoint instance with the processor are register
     */
    private WebSocketChannelServerEndpoint channel;

    /**
     * Represent the messageType
     */
    private MessageType messageType;

    /**
     * Constructor with parameter
     *
     * @param messageType
     */
    public MessageProcessor(WebSocketChannelServerEndpoint channel, MessageType messageType) {
        this.channel = channel;
        this.messageType = messageType;
    }

    /**
     * Gets the value of webSocketChannelServerEndpoint and returns
     *
     * @return webSocketChannelServerEndpoint
     */
    public WebSocketChannelServerEndpoint getChannel() {
        return channel;
    }

    /**
     * Gets the value of messageType and returns
     *
     * @return messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Method that call to process the message
     *
     * @param session that send the message
     * @param message received to process
     */
    public abstract void processingMessage(Session session, final Message message);
}
