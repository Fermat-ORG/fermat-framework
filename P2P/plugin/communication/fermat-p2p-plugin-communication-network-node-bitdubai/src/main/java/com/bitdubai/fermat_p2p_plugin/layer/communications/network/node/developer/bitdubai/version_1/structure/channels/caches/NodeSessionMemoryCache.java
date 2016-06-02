/*
 * @#NodeSessionMemoryCache.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.NodeSessionMemoryCache</code>
 * is responsible the manage the cache of the node session connected with the <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers.FermatWebSocketNodeChannelServerEndpoint</code><p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeSessionMemoryCache {

    /**
     * Represent the singleton instance
     */
    private static NodeSessionMemoryCache instance;

    /**
     * Holds all client sessions
     */
    private final Map<String, Session> nodeSessions;

    /**
     * Constructor
     */
    private NodeSessionMemoryCache(){
        super();
        nodeSessions = Collections.synchronizedMap(new HashMap<String, Session>());
    }

    /**
     * Return the singleton instance
     *
     * @return ClientsSessionMemoryCache
     */
    public static NodeSessionMemoryCache getInstance(){

        /*
         * If no exist create a new one
         */
        if (instance == null){
            instance = new NodeSessionMemoryCache();
        }

        return instance;
    }

    /**
     * Get the node session
     *
     * @param nodePublicKeyIdentity the node identity
     * @return the session of the client
     */
    public Session get(String nodePublicKeyIdentity){

        /*
         * Return the session of this node
         */
        return instance.nodeSessions.get(nodePublicKeyIdentity);
    }

    /**
     * Add a new session to the memory cache
     *
     * @param nodePublicKeyIdentity the node identity
     * @param session the client session
     */
    public void add(String nodePublicKeyIdentity, Session session){

        /*
         * Add to the cache
         */
        instance.nodeSessions.put(nodePublicKeyIdentity, session);
    }

    /**
     * Remove the node session
     *
     * @param nodePublicKeyIdentity the node identity
     * @return the session of the client
     */
    public Session remove(String nodePublicKeyIdentity){

        /*
         * remove the session of this node
         */
        return instance.nodeSessions.remove(nodePublicKeyIdentity);
    }

    /**
     * Verify is exist a session for a node
     *
     * @param nodePublicKeyIdentity the node identity
     * @return (TRUE or FALSE)
     */
    public boolean exist(String nodePublicKeyIdentity){

        return instance.nodeSessions.containsKey(nodePublicKeyIdentity);
    }

    /**
     * Get node sessions
     * @return Map<String, Session>
     */
    public static Map<String, Session> getNodeSessions() {
        return instance.nodeSessions;
    }
}
