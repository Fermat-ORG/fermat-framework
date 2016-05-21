/*
 * @#NodeChannelConfigurator.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf;


import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

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
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(NodeChannelConfigurator.class));

    /**
     * (non-javadoc)
     *
     * @see ServerEndpointConfig.Configurator#modifyHandshake(ServerEndpointConfig, HandshakeRequest, HandshakeResponse)
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig serverEndpointConfig, HandshakeRequest handshakeRequest, HandshakeResponse handshakeResponse) {

       /* for (String key : handshakeRequest.getHeaders().keySet()) {
            LOG.info(key + " : "+handshakeRequest.getHeaders().get(key));
        } */

        /*
         * Validate if the client public key identity come in the header
         */
        if (handshakeRequest.getHeaders().containsKey(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME)){

            /*
             * Get the client public key identity
             */
            String tcpki = handshakeRequest.getHeaders().get(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME).get(0);

            /*
             * Pass the identity create to the FermatWebSocketClientChannelServerEndpoint
             */
            serverEndpointConfig.getUserProperties().put(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME, tcpki);

        }else {
            LOG.warn(HeadersAttName.NPKI_ATT_HEADER_NAME + " No Found");
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
