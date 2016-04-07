/*
 * @#WebSocketClientNodeChannel.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.clients;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf.ClientNodeChannelConfigurator;

import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketClientNodeChannel.WebSocketClientNodeChannel</code>
 * is the client to communicate nodes by the node client channel<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ClientEndpoint(configurator = ClientNodeChannelConfigurator.class )
public class WebSocketClientNodeChannel {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(WebSocketClientNodeChannel.class.getName());

    /**
     * Represent the clientConnection
     */
    private Session clientConnection;

    /**
     *  Method called to handle a new connection
     *
     * @param session connected
     * @param endpointConfig created
     * @throws IOException
     */
    @OnOpen
    public void onConnect(final Session session, EndpointConfig endpointConfig) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" Starting method onOpen");
        System.out.println(" id = "+session.getId());
        System.out.println(" url = "+session.getRequestURI());

        this.clientConnection = session;
    }

    /**
     *  Method called to handle a new package received
     * @param packet
     * @param session
     */
    @OnMessage
    public void newPackageReceived(String packet, Session session) {

        LOG.info("newPackageReceived: " + session.getId() + " message = " + packet + ")");

    }

    /**
     *  Method called to handle a connection is closed
     * @param session
     * @param reason
     */
    @OnClose
    public void onClose(final Session session, final CloseReason reason) {



    }
}
