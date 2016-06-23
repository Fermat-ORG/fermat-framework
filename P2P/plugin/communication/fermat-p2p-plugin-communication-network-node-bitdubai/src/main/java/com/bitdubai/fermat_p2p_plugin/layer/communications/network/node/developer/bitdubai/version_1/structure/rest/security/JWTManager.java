/*
 * @#JWTManager  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.security;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

import java.util.Date;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.security.JWTManager</code> implements
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/06/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class JWTManager {

    /**
     * Represent the key
     */
    private static final ECCKeyPair key = new ECCKeyPair();

    /**
     * Represent the expirationTime time
     */
    private static final Long expirationTime = new Long(1800000);

    /**
     * Get the key value
     * @return String
     */
    public static String getKey() {
        return key.getPrivateKey();
    }

    /**
     * Get the expiration time value
     * @return Long
     */
    public static Long getExpirationTime() {
        return new Date().getTime() + expirationTime;
    }
}
