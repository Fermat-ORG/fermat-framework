/*
 * @#WebSocketVpnIdentity.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.WebSocketVpnIdentity</code>
 * generate a share identity to all vpn web sockets
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WebSocketVpnIdentity {

    /**
     * Represent the instance
     */
    private static WebSocketVpnIdentity instance = new WebSocketVpnIdentity();

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Constructor
     */
    private WebSocketVpnIdentity() {
        this.identity = new ECCKeyPair();
    }

    /**
     * Get the instance value
     *
     * @return instance current value
     */
    public static WebSocketVpnIdentity getInstance() {
        return instance;
    }

    /**
     * Get the identity value
     *
     * @return identity current value
     */
    public ECCKeyPair getIdentity() {
        return identity;
    }
}
