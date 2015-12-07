/*
 * @#RequestCheckInNetworkService.java - 2015
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
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.RequestCheckInNetworkService</code>
 * process all messages received the type <code>MessageType.REQUEST_REGISTER_NETWORK_SERVICE</code><p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RequestCheckInNetworkService extends MessageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(RequestCheckInNetworkService.class.getName());

    /**
     * Constructor  whit parameter
     *
     * @param webSocketChannelServerEndpoint register
     *
     */
    public RequestCheckInNetworkService(WebSocketChannelServerEndpoint webSocketChannelServerEndpoint) {
        super(webSocketChannelServerEndpoint, MessageType.REQUEST_REGISTER_NETWORK_SERVICE);
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
