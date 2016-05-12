/*
 * @#CLoudClientConfigurator.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.conf;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.HandshakeResponse;

/**
 * The class <code>CLoudClientConfigurator</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CLoudClientConfigurator extends ClientEndpointConfig.Configurator {

    /*
     * Create a new temporal identity
     */
    public final static ECCKeyPair tempIdentity = new ECCKeyPair();

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {

         /*
         * Get json representation
         */
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(JsonAttNamesConstants.NAME_IDENTITY, tempIdentity.getPublicKey());

        /*
         * Add the att to the header
         */
        headers.put(JsonAttNamesConstants.HEADER_ATT_NAME_TI, Arrays.asList(jsonObject.toString()));
        //headers.put("Origin", Arrays.asList("myOrigin"));

       // System.out.println("CLoudClientConfigurator - tempIdentity.getPublicKey() = "+tempIdentity.getPublicKey());

    }

    @Override
    public void afterResponse(HandshakeResponse hr) {
    }
}
