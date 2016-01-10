/*
 * @#ClientsSessionMemoryCache.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.ClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.ActorUpdateRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.ComponentConnectionRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.ComponentRegistrationRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.DiscoveryComponentConnectionRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.FermatJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.RequestListComponentRegisterJettyPacketProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

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
     * Holds all the registered Communications Cloud Server by his client connection hash
     */
    private Map<Integer, PlatformComponentProfile> registeredCommunicationsCloudServerCache;

    /**
     * Holds all the registered Communications Cloud Client by his client connection hash
     */
    private Map<Integer, PlatformComponentProfile> registeredCommunicationsCloudClientCache;

    /**
     * Holds all the registered network services by his network service type
     */
    private Map<NetworkServiceType, List<PlatformComponentProfile>> registeredNetworkServicesCache;

    /**
     * Holds all other Platform Component Profile register by type
     */
    private Map<PlatformComponentType, List<PlatformComponentProfile>> registeredOtherPlatformComponentProfileCache;

    /**
     * Holds all profile by client identity, to wait to reconnect
     */
    private Map<String, List<PlatformComponentProfile>> standByProfileByClientIdentity;

    /**
     * Holds all Timer that clear references by client identity
     */
    private Map<String, Timer> timersByClientIdentity;

    /**
     * Holds the pending register clients connections cache
     */
    private Map<String, ClientConnection> pendingRegisterClientConnectionsCache;

    /**
     * Holds the packet processors objects
     */
    private Map<FermatPacketType, List<FermatJettyPacketProcessor>> packetProcessorsRegister;

    /**
     * Constructor
     */
    private MemoryCache(){
        super();
        this.properties = Collections.synchronizedMap(new HashMap<String, Object>());
        this.registeredClientConnectionsCache = new ConcurrentHashMap<>();this.packetProcessorsRegister                     = new ConcurrentHashMap<>();
        this.pendingRegisterClientConnectionsCache        = new ConcurrentHashMap<>();
        this.pendingRegisterClientConnectionsCache        = new ConcurrentHashMap<>();
        this.registeredCommunicationsCloudServerCache     = new ConcurrentHashMap<>();
        this.registeredCommunicationsCloudClientCache     = new ConcurrentHashMap<>();
        this.registeredNetworkServicesCache               = new ConcurrentHashMap<>();
        this.registeredOtherPlatformComponentProfileCache = new ConcurrentHashMap<>();
        this.timersByClientIdentity                       = new ConcurrentHashMap<>();
        this.standByProfileByClientIdentity               = new ConcurrentHashMap<>();

        registerFermatPacketProcessor(new ComponentRegistrationRequestJettyPacketProcessor());
        registerFermatPacketProcessor(new ComponentConnectionRequestJettyPacketProcessor());
        registerFermatPacketProcessor(new DiscoveryComponentConnectionRequestJettyPacketProcessor());
        registerFermatPacketProcessor(new RequestListComponentRegisterJettyPacketProcessor());
        registerFermatPacketProcessor(new ActorUpdateRequestJettyPacketProcessor());
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

    /**
     * This method register a FermatJettyPacketProcessor object with this
     * server
     */
    public void registerFermatPacketProcessor(FermatJettyPacketProcessor fermatJettyPacketProcessor) {

        //Validate if a previous list created
        if (packetProcessorsRegister.containsKey(fermatJettyPacketProcessor.getFermatPacketType())){

            /*
             * Add to the existing list
             */
            packetProcessorsRegister.get(fermatJettyPacketProcessor.getFermatPacketType()).add(fermatJettyPacketProcessor);

        }else{

            /*
             * Create a new list and add the fermatPacketProcessor
             */
            List<FermatJettyPacketProcessor> fermatPacketProcessorList = new ArrayList<>();
            fermatPacketProcessorList.add(fermatJettyPacketProcessor);

            /*
             * Add to the packetProcessorsRegister
             */
            packetProcessorsRegister.put(fermatJettyPacketProcessor.getFermatPacketType(), fermatPacketProcessorList);
        }

    }



    /**
     * Get the pendingRegisterClientConnectionsCache value
     *
     * @return pendingRegisterClientConnectionsCache current value
     */
    public Map<String, ClientConnection> getPendingRegisterClientConnectionsCache() {
        return pendingRegisterClientConnectionsCache;
    }

    /**
     * Get the packetProcessorsRegister value
     *
     * @return packetProcessorsRegister current value
     */
    public Map<FermatPacketType, List<FermatJettyPacketProcessor>> getPacketProcessorsRegister() {
        return packetProcessorsRegister;
    }

    /**
     * Get the registeredCommunicationsCloudServerCache value
     *
     * @return registeredCommunicationsCloudServerCache current value
     */
    public Map<Integer, PlatformComponentProfile> getRegisteredCommunicationsCloudServerCache() {
        return registeredCommunicationsCloudServerCache;
    }

    /**
     * Get the registeredCommunicationsCloudClientCache value
     *
     * @return registeredCommunicationsCloudClientCache current value
     */
    public Map<Integer, PlatformComponentProfile> getRegisteredCommunicationsCloudClientCache() {
        return registeredCommunicationsCloudClientCache;
    }

    /**
     * Get the registeredNetworkServicesCache value
     *
     * @return registeredNetworkServicesCache current value
     */
    public Map<NetworkServiceType, List<PlatformComponentProfile>> getRegisteredNetworkServicesCache() {
        return registeredNetworkServicesCache;
    }

    /**
     * Get the registeredOtherPlatformComponentProfileCache value
     *
     * @return registeredOtherPlatformComponentProfileCache current value
     */
    public Map<PlatformComponentType, List<PlatformComponentProfile>> getRegisteredOtherPlatformComponentProfileCache() {
        return registeredOtherPlatformComponentProfileCache;
    }

    /**
     * Get the standByProfileByClientIdentity value
     *
     * @return standByProfileByClientIdentity current value
     */
    public Map<String, List<PlatformComponentProfile>> getStandByProfileByClientIdentity() {
        return standByProfileByClientIdentity;
    }

    /**
     * Get the timersByClientIdentity value
     *
     * @return timersByClientIdentity current value
     */
    public Map<String, Timer> getTimersByClientIdentity() {
        return timersByClientIdentity;
    }
}
