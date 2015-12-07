/*
 * @#ClientChannelConfigurator.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;

import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf.ClientChannelConfigurator</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientChannelConfigurator extends ServerEndpointConfig.Configurator {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClientChannelConfigurator.class.getName());

    /**
     * (non-javadoc)
     *
     * @see ServerEndpointConfig.Configurator#modifyHandshake(ServerEndpointConfig, HandshakeRequest, HandshakeResponse)
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig serverEndpointConfig, HandshakeRequest handshakeRequest, HandshakeResponse handshakeResponse) {

        /*
         * Create a node identity for this session
         */
        ECCKeyPair nodeIdentityForSession = new ECCKeyPair();

        /*
         * Create the NPKI_ATT_HEADER_NAME header attribute value
         * to share with the client
         */
        List<String> value = new ArrayList<>();
        value.add(nodeIdentityForSession.getPublicKey());

        /*
         * Set the new header attribute
         */
        handshakeResponse.getHeaders().put(HeadersAttName.NPKI_ATT_HEADER_NAME, value);

        /*
         * Pass the identity create to the WebSocketClientChannelServerEndpoint
         */
        serverEndpointConfig.getUserProperties().put(HeadersAttName.NPKI_ATT_HEADER_NAME, nodeIdentityForSession);
    }

    /**
     * (non-javadoc)
     *
     * @see ServerEndpointConfig.Configurator#checkOrigin(String)
     */
    @Override
    public boolean checkOrigin(String originHeaderValue) {

        LOG.info("originHeaderValue = "+originHeaderValue);

        return super.checkOrigin(originHeaderValue);
    }

}
