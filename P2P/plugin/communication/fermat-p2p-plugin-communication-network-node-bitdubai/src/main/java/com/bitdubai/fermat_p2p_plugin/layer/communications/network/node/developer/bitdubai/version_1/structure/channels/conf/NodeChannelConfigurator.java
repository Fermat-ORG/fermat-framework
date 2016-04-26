/*
 * @#NodeChannelConfigurator.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf.NodeChannelConfigurator</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeChannelConfigurator extends ServerEndpointConfig.Configurator {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(NodeChannelConfigurator.class.getName());

    /**
     * (non-javadoc)
     *
     * @see ServerEndpointConfig.Configurator#modifyHandshake(ServerEndpointConfig, HandshakeRequest, HandshakeResponse)
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig serverEndpointConfig, HandshakeRequest handshakeRequest, HandshakeResponse handshakeResponse) {

        for (String key : handshakeRequest.getHeaders().keySet()) {
            LOG.info(key + " : "+handshakeRequest.getHeaders().get(key));
        }

        /*
         * Validate if the client public key identity come in the header
         */
        if (handshakeRequest.getHeaders().containsKey(JsonAttNamesConstants.HEADER_ATT_NAME_TI)){

            /*
             * Get the client public key identity
             */
            String tcpki = handshakeRequest.getHeaders().get(JsonAttNamesConstants.HEADER_ATT_NAME_TI).get(0);

             /*
             * Get the temporal identity of the CommunicationsClientConnection component
             */
            JsonParser parser = new JsonParser();
            JsonObject temporalIdentity = parser.parse(tcpki).getAsJsonObject();
            String temporalClientIdentity = temporalIdentity.get(JsonAttNamesConstants.NAME_IDENTITY).getAsString();

            /*
             * Pass the identity create to the FermatWebSocketClientChannelServerEndpoint
             */
            serverEndpointConfig.getUserProperties().put(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME, temporalClientIdentity);

            /*
             * Create a node identity for this session
             */
            ECCKeyPair nodeIdentityForSession = new ECCKeyPair();

            /*
             * Create the node public key identity header attribute value
             * to share with the client
             */
             List<String> value = new ArrayList<>();
             value.add(nodeIdentityForSession.getPublicKey());

            /*
             * Set the new header attribute
             */
             handshakeResponse.getHeaders().put(HeadersAttName.NPKI_ATT_HEADER_NAME, value);

            /*
             * Pass the identity create to the FermatWebSocketClientChannelServerEndpoint
             */
             serverEndpointConfig.getUserProperties().put(HeadersAttName.NPKI_ATT_HEADER_NAME, nodeIdentityForSession);

        }


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
