/*
 * @#ServerConf.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.util;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.util.ServerConf</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ServerConf {

    /**
     * Represent the SERVER_IP in the production environment
     */
    public static final String SERVER_IP_PRODUCTION = "193.234.224.198"; // "192.168.1.6"; //

    /**
     * Represent the SERVER_IP in the developer environment
     */
    public static final String SERVER_IP_DEVELOPER_AWS = "192.168.1.6";

    /**
     * Represent the SERVER_IP in the local environment
     */
    public static final String SERVER_IP_DEVELOPER_LOCAL = "192.168.8.100";

    /**
     * Represents the value of DISABLE_CLIENT
     */
    public static final Boolean DISABLE_CLIENT = Boolean.TRUE;

    /**
     * Represents the value of ENABLE_CLIENT
     */
    public static final Boolean ENABLE_CLIENT = Boolean.FALSE;

    /**
     * Represent the WS_PROTOCOL
     */
    public static final String WS_PROTOCOL = "ws://";

    /**
     * Represent the WEB_SOCKET_CONTEXT_PATH
     */
    public static final String WEB_SOCKET_CONTEXT_PATH = "/fermat/ws/";

    /**
     * Represent the HTTP_PROTOCOL
     */
    public static final String HTTP_PROTOCOL = "http://";
    
    /**
     * Represent the DEFAULT_PORT
     */
    public static final int DEFAULT_PORT = 9090;

    /**
     * Represent the WEB_SERVICE_PORT
     */
    public static final int WEB_SERVICE_PORT = 8080;
}
