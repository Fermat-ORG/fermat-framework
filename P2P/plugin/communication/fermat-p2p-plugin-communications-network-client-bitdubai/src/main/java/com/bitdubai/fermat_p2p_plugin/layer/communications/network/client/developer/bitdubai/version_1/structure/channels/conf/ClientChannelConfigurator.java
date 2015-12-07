/*
* @#ClientChannelConfigurator.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.conf;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import org.jboss.logging.Logger;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.conf.ClientChannelConfigurator</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientChannelConfigurator extends ClientEndpointConfig.Configurator {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClientChannelConfigurator.class.getName());

    /**
     * Represent the clientIdentity
     */
    ECCKeyPair clientIdentity;

    public ClientChannelConfigurator(ECCKeyPair clientIdentity){
        this.clientIdentity=clientIdentity;
    }

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {

        List<String> values = new ArrayList<String>();
        values.add(clientIdentity.getPublicKey());
        LOG.info("Client Public Key = " + clientIdentity.getPublicKey());
        headers.put(HeadersAttName.CPKI_ATT_HEADER_NAME,values);
    }

}
