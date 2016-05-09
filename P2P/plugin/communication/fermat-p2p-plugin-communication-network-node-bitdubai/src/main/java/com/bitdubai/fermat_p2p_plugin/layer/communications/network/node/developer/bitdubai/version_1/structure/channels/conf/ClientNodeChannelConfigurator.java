/*
 * @#ClientNodeChannelConfigurator.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodePluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.HandshakeResponse;

/**
 * The class <code>ClientNodeChannelConfigurator</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientNodeChannelConfigurator extends ClientEndpointConfig.Configurator {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ClientNodeChannelConfigurator.class));

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {

        /* for (String key : headers.keySet()) {
            LOG.info(key + " : "+headers.get(key));
        } */

        /*
         * Add the att to the header
         */
        //headers.put(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME, Arrays.asList(new ECCKeyPair().getPublicKey()));

        headers.put(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME, Arrays.asList(((NetworkNodePluginRoot) NodeContext.get(NodeContextItem.PLUGIN_ROOT)).getIdentity().getPublicKey()));
    }

    @Override
    public void afterResponse(HandshakeResponse hr) {
    }
}
