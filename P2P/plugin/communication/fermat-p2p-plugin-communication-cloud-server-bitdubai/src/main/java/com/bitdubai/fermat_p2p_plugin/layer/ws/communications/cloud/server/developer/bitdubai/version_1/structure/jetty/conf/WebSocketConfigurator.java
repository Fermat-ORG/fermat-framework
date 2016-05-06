/*
 * @#WebSocketConfigurator.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.conf;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.conf.WebSocketConfigurator</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WebSocketConfigurator extends ServerEndpointConfig.Configurator{

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(WebSocketConfigurator.class));

    /**
    * (non-javadoc)
    *
    * @see ServerEndpointConfig.Configurator#modifyHandshake(ServerEndpointConfig, HandshakeRequest, HandshakeResponse)
    */
    @Override
    public void modifyHandshake(ServerEndpointConfig serverEndpointConfig, HandshakeRequest handshakeRequest, HandshakeResponse handshakeResponse) {

      /*  LOG.info("Headers Attributes:");

        for (String key : handshakeRequest.getHeaders().keySet()) {
            LOG.info(key + " : "+handshakeRequest.getHeaders().get(key));
        } */

        if (handshakeRequest.getHeaders().containsKey(JsonAttNamesConstants.HEADER_ATT_NAME_TI)){

            /*
             * Get the json temporal identity
             */
            String ti = handshakeRequest.getHeaders().get(JsonAttNamesConstants.HEADER_ATT_NAME_TI).get(0);

            /*
             * Pass the identity create to the WebSocketClientChannelServerEndpoint
             */
            serverEndpointConfig.getUserProperties().put(JsonAttNamesConstants.HEADER_ATT_NAME_TI, ti);

        }else {
            LOG.warn(JsonAttNamesConstants.HEADER_ATT_NAME_TI + " No Found");
        }


    }
}
