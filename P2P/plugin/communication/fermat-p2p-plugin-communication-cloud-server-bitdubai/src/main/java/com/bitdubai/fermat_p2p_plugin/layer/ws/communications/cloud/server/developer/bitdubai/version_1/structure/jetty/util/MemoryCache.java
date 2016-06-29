/*
 * @#MemoryCache.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.ClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.ActorUpdateRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.ComponentConnectionRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.ComponentRegistrationRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.DiscoveryComponentConnectionRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.FermatJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.RequestListComponentRegisterJettyPacketProcessor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache</code>
 * is responsible the holds a cache<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MemoryCache {

    /**
     * Represent the logger instance
     */
    private static Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(MemoryCache.class));

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
     * Holds all the registered Communications Cloud Client by his client connection hash
     */
    private Map<String, PlatformComponentProfile> registeredCommunicationsCloudClientCache;

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

    /*
     * Holds the server of platforms active
     */
    private Map<NetworkServiceType,String> listServerConfByPlatform;

    /**
     * Constructor
     */
    private MemoryCache(){
        super();
        this.properties = Collections.synchronizedMap(new HashMap<String, Object>());
        this.registeredClientConnectionsCache = new ConcurrentHashMap<>();this.packetProcessorsRegister                     = new ConcurrentHashMap<>();
        this.pendingRegisterClientConnectionsCache        = new ConcurrentHashMap<>();
        this.pendingRegisterClientConnectionsCache        = new ConcurrentHashMap<>();
        this.registeredCommunicationsCloudClientCache     = new ConcurrentHashMap<>();
        this.registeredNetworkServicesCache               = new ConcurrentHashMap<>();
        this.registeredOtherPlatformComponentProfileCache = new ConcurrentHashMap<>();
        this.timersByClientIdentity                       = new ConcurrentHashMap<>();
        this.standByProfileByClientIdentity               = new ConcurrentHashMap<>();
        this.listServerConfByPlatform                     = new ConcurrentHashMap<>();

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
    public   static MemoryCache getInstance(){

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
    public   Object get(String key){

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
    public   void add(String key, Object value){

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
    public   Object remove(String key){

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
    public   boolean exist(String key){

        return instance.properties.containsKey(key);
    }

    public Map<NetworkServiceType, String> getListServerConfByPlatform() {
        return listServerConfByPlatform;
    }

    /**
     * Get the registeredClientConnectionsCache value
     *
     * @return registeredClientConnectionsCache current value
     */
    public   Map<String, ClientConnection> getRegisteredClientConnectionsCache() {
        return registeredClientConnectionsCache;
    }

    /**
     * This method register a FermatJettyPacketProcessor object with this
     * server
     */
    public   void registerFermatPacketProcessor(FermatJettyPacketProcessor fermatJettyPacketProcessor) {

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
    public   Map<String, ClientConnection> getPendingRegisterClientConnectionsCache() {
        return pendingRegisterClientConnectionsCache;
    }

    /**
     * Get the packetProcessorsRegister value
     *
     * @return packetProcessorsRegister current value
     */
    public   Map<FermatPacketType, List<FermatJettyPacketProcessor>> getPacketProcessorsRegister() {
        return packetProcessorsRegister;
    }

    /**
     * Get the registeredCommunicationsCloudClientCache value
     *
     * @return registeredCommunicationsCloudClientCache current value
     */
    public   Map<String, PlatformComponentProfile> getRegisteredCommunicationsCloudClientCache() {
        return registeredCommunicationsCloudClientCache;
    }

    /**
     * Get the registeredNetworkServicesCache value
     *
     * @return registeredNetworkServicesCache current value
     */
    public   Map<NetworkServiceType, List<PlatformComponentProfile>> getRegisteredNetworkServicesCache() {
        return registeredNetworkServicesCache;
    }

    /**
     * Get the registeredOtherPlatformComponentProfileCache value
     *
     * @return registeredOtherPlatformComponentProfileCache current value
     */
    public   Map<PlatformComponentType, List<PlatformComponentProfile>> getRegisteredOtherPlatformComponentProfileCache() {
        return registeredOtherPlatformComponentProfileCache;
    }

    /**
     * Get the standByProfileByClientIdentity value
     *
     * @return standByProfileByClientIdentity current value
     */
    public   Map<String, List<PlatformComponentProfile>> getStandByProfileByClientIdentity() {
        return standByProfileByClientIdentity;
    }

    /**
     * Get the timersByClientIdentity value
     *
     * @return timersByClientIdentity current value
     */
    public   Map<String, Timer> getTimersByClientIdentity() {
        return timersByClientIdentity;
    }


    /**
     * Clean all reference from the connection
     */
    public synchronized void cleanReferences(ClientConnection activeClientConnection){

        try {

            if (activeClientConnection.getClientIdentity() != null){

                LOG.info("--------------------------------------------------------------------- ");
                LOG.info("Starting method cleanReferences" );
                LOG.info("ID = " + activeClientConnection.getSession().getId());
                LOG.info("hashCode = " + activeClientConnection.getSession().hashCode());
                removeNetworkServiceRegisteredByClientIdentity(activeClientConnection.getClientIdentity());
                removeOtherPlatformComponentRegisteredByClientIdentity(activeClientConnection.getClientIdentity());
                MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().remove(activeClientConnection.getClientIdentity());

                MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().remove(activeClientConnection.getClientIdentity());
                MemoryCache.getInstance().getRegisteredClientConnectionsCache().remove(activeClientConnection.getClientIdentity());

                LOG.info("pendingRegisterClientConnectionsCache.size()    = " + MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().size());
                LOG.info("registeredCommunicationsCloudClientCache.size() = " + MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().size());
                LOG.info("registeredNetworkServicesCache.size()           = " + MemoryCache.getInstance().getRegisteredNetworkServicesCache().size());
                for (NetworkServiceType networkServiceType: MemoryCache.getInstance().getRegisteredNetworkServicesCache().keySet()) {
                    LOG.info(networkServiceType + " = " + MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).size());
                }
                LOG.info("registeredOtherPlatformComponentProfileCache.size()  = " + MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().size());
                for (PlatformComponentType platformComponentType: MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().keySet()) {
                    LOG.info(platformComponentType + " = " + MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).size());
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Clean all reference from the registers cache into
     * a standByCache to wait to reconnect
     */
    public synchronized void putReferencesToStandBy(ClientConnection activeClientConnection){

        if (activeClientConnection.getClientIdentity() != null) {

            try {

                LOG.info("--------------------------------------------------------------------- ");
                LOG.info("Starting method putReferencesToStandBy");
                LOG.info("ID = " + activeClientConnection.getSession().getId());

               /*
                 * Clean all the caches, remove data bind whit this connection and put
                 * on stand by, to wait to reconnect
                 */
                MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().remove(activeClientConnection.getClientIdentity());
                MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().remove(activeClientConnection.getClientIdentity());
                MemoryCache.getInstance().getRegisteredClientConnectionsCache().remove(activeClientConnection.getClientIdentity());

                List<PlatformComponentProfile> removeProfile = removeNetworkServiceRegisteredByClientIdentity(activeClientConnection.getClientIdentity());
                removeProfile.addAll(removeOtherPlatformComponentRegisteredByClientIdentity(activeClientConnection.getClientIdentity()));

                LOG.info("Number of profiles put into standby " + removeProfile.size());
                MemoryCache.getInstance().getStandByProfileByClientIdentity().put(activeClientConnection.getClientIdentity(), removeProfile);
                LOG.info("Number of list of profiles into standby cache = " + MemoryCache.getInstance().getStandByProfileByClientIdentity().size());

            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }

    /**
     * This method unregister network service component profile
     * register
     */
    private List<PlatformComponentProfile> removeNetworkServiceRegisteredByClientIdentity(final String clientIdentity){

        LOG.info("removeNetworkServiceRegisteredByClientIdentity ");

        List<PlatformComponentProfile> removeProfile = new ArrayList<>();

        Iterator<NetworkServiceType> iteratorNetworkServiceType = MemoryCache.getInstance().getRegisteredNetworkServicesCache().keySet().iterator();

        while (iteratorNetworkServiceType.hasNext()){

            NetworkServiceType networkServiceType = iteratorNetworkServiceType.next();
            Iterator<PlatformComponentProfile> iterator = MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).iterator();
            while (iterator.hasNext()){

                /*
                 * Remove the platformComponentProfileRegistered
                 */
                PlatformComponentProfile platformComponentProfileRegistered = iterator.next();

                if(platformComponentProfileRegistered.getCommunicationCloudClientIdentity().equals(clientIdentity)){
                    LOG.info("removing =" + platformComponentProfileRegistered.getName());
                    removeProfile.add(platformComponentProfileRegistered);
                    iterator.remove();
                }
            }

            if (MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).isEmpty()){
                MemoryCache.getInstance().getRegisteredNetworkServicesCache().remove(networkServiceType);
            }
        }

        /*
         * Remove the networkServiceType empty
         */
        for (NetworkServiceType networkServiceType : MemoryCache.getInstance().getRegisteredNetworkServicesCache().keySet()) {

            if (MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).isEmpty()){
                MemoryCache.getInstance().getRegisteredNetworkServicesCache().remove(networkServiceType);
            }
        }

        return removeProfile;

    }


    /**
     * This method unregister all platform component profile
     * register
     */
    private List<PlatformComponentProfile> removeOtherPlatformComponentRegisteredByClientIdentity(final String clientIdentity){

        LOG.info("removeOtherPlatformComponentRegisteredByClientIdentity ");

        List<PlatformComponentProfile> removeProfile = new ArrayList<>();
        Iterator<PlatformComponentType> iteratorPlatformComponentType = MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().keySet().iterator();
        while (iteratorPlatformComponentType.hasNext()){

            PlatformComponentType platformComponentType = iteratorPlatformComponentType.next();
            Iterator<PlatformComponentProfile> iterator = MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).iterator();
            while (iterator.hasNext()){

                /*
                 * Remove the platformComponentProfileRegistered
                 */
                PlatformComponentProfile platformComponentProfileRegistered = iterator.next();
                if(platformComponentProfileRegistered.getCommunicationCloudClientIdentity().equals(clientIdentity)){
                    LOG.info("removing Other ="+platformComponentProfileRegistered.getName());
                    removeProfile.add(platformComponentProfileRegistered);
                    iterator.remove();
                }
            }

            /*
             * Remove the platformComponentType empty
             */
            if (MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).isEmpty()){
                MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().remove(platformComponentType);
            }
        }

        return removeProfile;
    }

    public void sendMessageToAll(NetworkServiceType networkServiceType, String ipServer) throws Exception{

        if(instance.getRegisteredClientConnectionsCache() != null){

            Gson gson = new Gson();
            JsonObject packetContent = new JsonObject();
            packetContent.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, gson.toJson(networkServiceType));
            packetContent.addProperty(JsonAttNamesConstants.IPSERVER,ipServer);

            for(ClientConnection client : instance.getRegisteredClientConnectionsCache().values()){

                if(client.getSession().isOpen()) {

                    //LOG.info("SEND MESSAGEEEEEEEEEEEEEEEEEEEE");

                    FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(client.getClientIdentity(), //Destination
                                                                                                                                client.getServerIdentity().getPublicKey(), //Sender
                                                                                                                                gson.toJson(packetContent), //packet Content
                                                                                                                                FermatPacketType.REGISTER_SERVER_REQUEST, //Packet type
                                                                                                                                client.getServerIdentity().getPrivateKey()); //Sender private key
                    /*
                     * Send the packet
                     */
                    client.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));
                }

            }

            /*
             * Clear list of the networkServiceType in getRegisteredNetworkServicesCache
             */
            if(instance.getRegisteredNetworkServicesCache().containsKey(networkServiceType)){
                instance.getRegisteredNetworkServicesCache().get(networkServiceType).clear();
                instance.getRegisteredNetworkServicesCache().remove(networkServiceType);
            }


        }else{
            LOG.info("instance.SESSION == null");
        }

    }

    private void putInAlllist(){

        /* ARTIST */
        listServerConfByPlatform.put(NetworkServiceType.ARTIST_ACTOR, "192.168.1.4");
        /* ARTIST */

        /* CBP */
        listServerConfByPlatform.put(NetworkServiceType.CRYPTO_BROKER, "192.168.1.15");
        /* CBP */

        /* CCP */
        listServerConfByPlatform.put(NetworkServiceType.INTRA_USER, "192.168.1.6");
        /* CCP */

        /* CHT */
        listServerConfByPlatform.put(NetworkServiceType.CHAT, "192.168.1.7");
        /* CHT */

        /* DAP */
        listServerConfByPlatform.put(NetworkServiceType.ASSET_USER_ACTOR, "192.168.1.8");
        /* DAP */

        /* MONITOR */
        listServerConfByPlatform.put(NetworkServiceType.FERMAT_MONITOR, "192.168.1.9");
        /* MONITOR */
    }


}
