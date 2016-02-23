/*
 * @#JWTManager  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.security;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.security.JWTManager</code> implements
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class JWTManager {

    private static final ECCKeyPair key = new ECCKeyPair();

    private static final Long expiration = new Long(604800);

    public static ECCKeyPair getKey() {
        return key;
    }
}
