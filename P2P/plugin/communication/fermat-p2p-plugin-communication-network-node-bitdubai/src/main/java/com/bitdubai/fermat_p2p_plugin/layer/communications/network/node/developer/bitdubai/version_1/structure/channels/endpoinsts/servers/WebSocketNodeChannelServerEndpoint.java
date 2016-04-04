/*
 * @#WebSocketNodeChannelServerEndpoint.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.PackageDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.PackageEncoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.NodeSessionMemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf.NodeChannelConfigurator;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ClientsConnectionHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodeConnectionHistory;

import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers.WebSocketNodeChannelServerEndpoint</code>
 * represent the the communication chanel between nodes<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ServerEndpoint(
        value ="/node-channel",
        configurator = NodeChannelConfigurator.class,
        encoders = {PackageEncoder.class},
        decoders = {PackageDecoder.class}
)
public class WebSocketNodeChannelServerEndpoint extends com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers.WebSocketChannelServerEndpoint {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(WebSocketNodeChannelServerEndpoint.class.getName());

    /**
     * Represent the nodeSessionMemoryCache
     */
    private NodeSessionMemoryCache nodeSessionMemoryCache;

    /**
     * Constructor
     */
    public WebSocketNodeChannelServerEndpoint(){
        super();
        this.nodeSessionMemoryCache = NodeSessionMemoryCache.getInstance();
    }

    /**
     * (non-javadoc)
     *
     * @see com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers.WebSocketChannelServerEndpoint#initPackageProcessorsRegistration()
     */
    @Override
    void initPackageProcessorsRegistration(){

    }


    /**
     *  Method called to handle a new connection
     *
     * @param session connected
     * @param endpointConfig created
     * @throws IOException
     */
    @OnOpen
    public void onConnect(Session session, EndpointConfig endpointConfig) throws IOException {

        LOG.info(" New connection stablished: " + session.getId());

        /*
         * Get the node identity
         */
        setChannelIdentity((ECCKeyPair) endpointConfig.getUserProperties().get(HeadersAttName.NPKI_ATT_HEADER_NAME));
        endpointConfig.getUserProperties().remove(HeadersAttName.NPKI_ATT_HEADER_NAME);

        /*
         * Get the node public key identity
         */
        String npki = (String) endpointConfig.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);

        /*
         * Mach the session whit the node public key identity
         */
        nodeSessionMemoryCache.add(npki, session);

        /*
         * Create a new NodeConnectionHistory
         */
        NodeConnectionHistory nodeConnectionHistory = new NodeConnectionHistory();
        nodeConnectionHistory.setIdentityPublicKey(npki);
        nodeConnectionHistory.setStatus(ClientsConnectionHistory.STATUS_SUCCESS);
    }

    @OnMessage
    public void newPackageReceived(String message, Session session) {

        LOG.info("On Message: " + session.getId() + " message = " + message + ")");

        for (Session s : session.getOpenSessions()) {
            s.getAsyncRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(CloseReason closeReason, Session session) {

        LOG.info("Closed connection: " + session.getId() + "(" + closeReason.getReasonPhrase() + ")");

    }

}
