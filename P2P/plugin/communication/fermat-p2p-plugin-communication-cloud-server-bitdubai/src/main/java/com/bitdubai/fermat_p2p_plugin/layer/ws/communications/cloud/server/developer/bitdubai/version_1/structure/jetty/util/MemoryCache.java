/*
 * @#ClientsSessionMemoryCache.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util;

import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.ClientConnection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.ClientsSessionMemoryCache</code>
 * is responsible the holds a cache<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MemoryCache {

    /**
     * Represent the singleton instance
     */
    private static MemoryCache instance;

    /**
     * Holds all properties
     */
    private final Map<String, Object> properties;

    /**
     * Holds the registered clients connections cache
     */
    private Map<String, ClientConnection> registeredClientConnectionsCache;

    /**
     * Constructor
     */
    private MemoryCache(){
        super();
        this.properties = Collections.synchronizedMap(new HashMap<String, Object>());
        this.registeredClientConnectionsCache = new ConcurrentHashMap<>();
    }

    /**
     * Return the singleton instance
     *
     * @return ClientsSessionMemoryCache
     */
    public static MemoryCache getInstance(){

        /*
         * If no exist create a new one
         */
        if (instance == null){
            instance = new MemoryCache();
        }

        return instance;
    }

    /**
     * Get the session client
     *
     * @param key to identify
     * @return the Object value
     */
    public Object get(String key){

        /*
         * Return the session of this client
         */
        return instance.properties.get(key);
    }

    /**
     * Add a new session to the memory cache
     *
     * @param key the identify
     * @param value to storage
     */
    public void add(String key, Object value){

        /*
         * Add to the cache
         */
        instance.properties.put(key, value);
    }

    /**
     * Remove the session client
     *
     * @param key that identify
     * @return the object value
     */
    public Object remove(String key){

        /*
         * remove the session of this client
         */
        return instance.properties.remove(key);
    }

    /**
     * Verify is exist a session for a client
     *
     * @param key that identify
     * @return (TRUE or FALSE)
     */
    public boolean exist(String key){

        return instance.properties.containsKey(key);
    }

    /**
     * Get the registeredClientConnectionsCache value
     *
     * @return registeredClientConnectionsCache current value
     */
    public Map<String, ClientConnection> getRegisteredClientConnectionsCache() {
        return registeredClientConnectionsCache;
    }
}
