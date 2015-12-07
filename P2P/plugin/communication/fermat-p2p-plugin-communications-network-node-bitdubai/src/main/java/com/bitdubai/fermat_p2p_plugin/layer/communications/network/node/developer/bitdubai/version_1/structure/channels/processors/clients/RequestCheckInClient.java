/*
 * @#RequestCheckInClient.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Message;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.MessageProcessor;

import org.jboss.logging.Logger;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.RequestCheckInClient</code>
 * process all messages received the type <code>MessageType.REQUEST_CHECK_IN_CLIENT</code><p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RequestCheckInClient extends MessageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(RequestCheckInClient.class.getName());

    /**
     * Constructor whit parameter
     *
     * @param webSocketChannelServerEndpoint register
     */
    public RequestCheckInClient(WebSocketChannelServerEndpoint webSocketChannelServerEndpoint) {
        super(webSocketChannelServerEndpoint, MessageType.REQUEST_CHECK_IN_CLIENT);
    }

    /**
     * (non-javadoc)
     * @see MessageProcessor#processingMessage(Session, Message)
     */
    @Override
    public void processingMessage(Session session, Message message) {

        LOG.info("Processing new message received");
    }
}
