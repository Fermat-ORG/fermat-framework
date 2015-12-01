/*
 * @#ClientsSessionMemoryCache.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.ClientsSessionMemoryCache</code>
 * is responsible the manage the cache of the client session connected with the <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketClientChannelServerEndpoint</code><p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 27/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientsSessionMemoryCache {

    /**
     * Represent the singleton instance
     */
    private static ClientsSessionMemoryCache instance;

    /**
     * Holds all client sessions
     */
    private final Map<String, Session> clientSessions;

    /**
     * Constructor
     */
    private ClientsSessionMemoryCache(){
        super();
        clientSessions = Collections.synchronizedMap(new HashMap<String, Session>());
    }

    /**
     * Return the singleton instance
     *
     * @return ClientsSessionMemoryCache
     */
    public static ClientsSessionMemoryCache getInstance(){

        /*
         * If no exist create a new one
         */
        if (instance == null){
            instance = new ClientsSessionMemoryCache();
        }

        return instance;
    }

    /**
     * Get the session client
     *
     * @param clientPublicKeyIdentity the client identity
     * @return the session of the client
     */
    private Session get(String clientPublicKeyIdentity){

        /*
         * Return the session of this client
         */
        return instance.clientSessions.get(clientPublicKeyIdentity);
    }

    /**
     * Add a new session to the memory cache
     *
     * @param clientPublicKeyIdentity the client identity
     * @param session the client session
     */
    private void add(String clientPublicKeyIdentity, Session session){

        /*
         * Add to the cache
         */
        instance.clientSessions.put(clientPublicKeyIdentity, session);
    }

    /**
     * Remove the session client
     *
     * @param clientPublicKeyIdentity the client identity
     * @return the session of the client
     */
    private Session remove(String clientPublicKeyIdentity){

        /*
         * remove the session of this client
         */
        return instance.clientSessions.remove(clientPublicKeyIdentity);
    }

    /**
     * Verify is exist a session for a client
     *
     * @param clientPublicKeyIdentity the client identity
     * @return (TRUE or FALSE)
     */
    public boolean exist(String clientPublicKeyIdentity){

        return instance.clientSessions.containsKey(clientPublicKeyIdentity);
    }
}
