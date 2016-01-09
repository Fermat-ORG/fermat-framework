/*
 * @#WebSocketCloudServerChannel.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.conf.WebSocketConfigurator;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.ActorUpdateRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.ComponentConnectionRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.ComponentRegistrationRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.DiscoveryComponentConnectionRequestJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.FermatJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.RequestListComponentRegisterJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ActorUpdateRequestPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ComponentConnectionRequestPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ComponentRegistrationRequestPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.DiscoveryComponentConnectionRequestPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.FermatPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.RequestListComponentRegisterPacketProcessor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.jsr356.annotations.OnMessagePongCallable;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.WebSocketCloudServerChannel</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ServerEndpoint(value="/ws/", configurator = WebSocketConfigurator.class)
public class WebSocketCloudServerChannel {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(WebSocketCloudServerChannel.class));

    /**
     * Holds the pending register clients connections cache
     */
    private Map<String, ClientConnection> pendingRegisterClientConnectionsCache;

    /**
     * Holds the packet processors objects
     */
    private Map<FermatPacketType, List<FermatJettyPacketProcessor>> packetProcessorsRegister;

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
     * Represent the active client Connection
     */
    private ClientConnection activeClientConnection;

    /**
     * Constructor
     */
    public WebSocketCloudServerChannel(){
        super();
        this.packetProcessorsRegister                     = new ConcurrentHashMap<>();
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
     * Method called on open a new web socket connection
     */
    @OnOpen
    public void onWebSocketConnect(Session session) throws IOException {

        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("Starting method onOpen");
        LOG.info("New Client: " + session.getId() + " is connected!");

        String temp_i = (String) session.getUserProperties().get(JsonAttNamesConstants.HEADER_ATT_NAME_TI);
        LOG.info("temp-i = " + temp_i);

        /*
         * Validate is a handshake valid
         */
        if (temp_i != null && temp_i != ""){

            activeClientConnection = new ClientConnection(session);

            /*
             * Get the temporal identity of the CommunicationsClientConnection component
             */
            JsonParser parser = new JsonParser();
            JsonObject temporalIdentity = parser.parse(temp_i).getAsJsonObject();
            String temporalClientIdentity = temporalIdentity.get(JsonAttNamesConstants.NAME_IDENTITY).getAsString();

            /*
             * Create a new server identity to talk with this client
             */
            ECCKeyPair serverIdentity = new ECCKeyPair();

            /*
             * Get json representation for the serverIdentity
             */
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.SERVER_IDENTITY, serverIdentity.getPublicKey());

            /*
             * Construct a fermat packet whit the server identity
             */
            FermatPacket fermatPacket = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(temporalClientIdentity,                   //Destination
                    serverIdentity.getPublicKey(),            //Sender
                    jsonObject.toString(),                    //Message Content
                    FermatPacketType.SERVER_HANDSHAKE_RESPOND,//Packet type
                    serverIdentity.getPrivateKey());          //Sender private key

            /*
             * Send the encode packet to the client
             */
            session.getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacket));

            /*
             * Add to the pending register client connection
             */
            pendingRegisterClientConnectionsCache.put(temporalClientIdentity, activeClientConnection);

            /*
             * Add the server identity for this client connection
             */
            activeClientConnection.setServerIdentity(serverIdentity);

            /*
             * Add client identity for his client connection
             */
            activeClientConnection.setClientIdentity(temporalClientIdentity);

        }else {

            if (session.isOpen()) {
                session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "DENIED, NOT VALID HANDSHAKE"));
            }
        }

    }

    @OnMessage
    public void onWebSocketText(String fermatPacketEncode)
    {
        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("Starting method onWebSocketText");

        /*
         * Get the server identity for this client connection
         */
        ECCKeyPair serverIdentity = activeClientConnection.getServerIdentity();

        /*
         * Decode the fermatPacketEncode into a fermatPacket
         */
        FermatPacket fermatPacketReceive = FermatPacketDecoder.decode(fermatPacketEncode, serverIdentity.getPrivateKey());


        LOG.info("fermatPacket.getFermatPacketType() = " + fermatPacketReceive.getFermatPacketType());


        //verify is packet supported
        if (packetProcessorsRegister.containsKey(fermatPacketReceive.getFermatPacketType())){


            /*
             * Call the processors for this packet
             */
            for (FermatJettyPacketProcessor fermatPacketProcessor :packetProcessorsRegister.get(fermatPacketReceive.getFermatPacketType())) {

                /*
                 * Processor make his job
                 */
                fermatPacketProcessor.processingPackage(activeClientConnection, fermatPacketReceive);
            }


        } else {
            LOG.info("Packet type " + fermatPacketReceive.getFermatPacketType() + "is not supported");
        }
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("Starting method onWebSocketClose");
        LOG.info(activeClientConnection.getSession().getId() + " is disconnect! code = " + reason.getCloseCode() + " reason = " + reason.getReasonPhrase());

        if (reason.getCloseCode().equals(CloseReason.CloseCodes.CLOSED_ABNORMALLY)) {
            LOG.info("Waiting for client reconnect");

            final String clientIdentity = activeClientConnection.getClientIdentity();
            final String id = activeClientConnection.getSession().getId();

            if (clientIdentity != null && clientIdentity != "") {

                putReferencesToStandBy();

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       LOG.info("client (" + id + ") not reconnect, proceed to clean references");
                                       standByProfileByClientIdentity.remove(clientIdentity);
                                       LOG.info("standByProfileByClientIdentity = " + standByProfileByClientIdentity.size());
                                   }
                               },
                        60000
                );

                timersByClientIdentity.put(clientIdentity, timer);
            }

        } else {
            cleanReferences();
        }
    }

    @OnError
    public void onWebSocketError(Throwable cause) throws IOException {

        LOG.info("--------------------------------------------------------------------- ");
        LOG.info("Starting method onWebSocketError");
        cause.printStackTrace();
        cleanReferences();
        if (activeClientConnection.getSession().isOpen()) {
            activeClientConnection.getSession().close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, cause.getMessage()));
        }

    }

    /**
     * Send ping message to the remote node, to verify is connection
     * alive
     */
    public void sendPingMessage(ClientConnection clientConnection){

        LOG.debug("Sending ping message to remote node (" + clientConnection.getSession().getId() + ")");
        try {
            ByteBuffer payload = ByteBuffer.wrap("PING".getBytes());
            clientConnection.getSession().getAsyncRemote().sendPing(payload);
            clientConnection.setPendingPongMsg(Boolean.TRUE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Clean all reference from the connection
     */
    private void cleanReferences(){

        try {

            LOG.info("--------------------------------------------------------------------- ");
            LOG.info("Starting method cleanReferences" );
            LOG.info("ID = " + activeClientConnection.getSession().getId());
            removeNetworkServiceRegisteredByClientIdentity(activeClientConnection.getClientIdentity());
            removeOtherPlatformComponentRegisteredByClientIdentity(activeClientConnection.getClientIdentity());
            pendingRegisterClientConnectionsCache.remove(activeClientConnection.getClientIdentity());
            registeredCommunicationsCloudServerCache.remove(activeClientConnection.getClientIdentity());
            registeredCommunicationsCloudClientCache.remove(activeClientConnection.getClientIdentity());

            LOG.info("pendingRegisterClientConnectionsCache.size()    = " + pendingRegisterClientConnectionsCache.size());
            LOG.info("registeredCommunicationsCloudServerCache.size() = " + registeredCommunicationsCloudServerCache.size());
            LOG.info("registeredCommunicationsCloudClientCache.size() = " + registeredCommunicationsCloudClientCache.size());
            LOG.info("registeredNetworkServicesCache.size()           = " + registeredNetworkServicesCache.size());
            for (NetworkServiceType networkServiceType: registeredNetworkServicesCache.keySet()) {
                LOG.info(networkServiceType + " = " + registeredNetworkServicesCache.get(networkServiceType).size());
            }
            LOG.info("registeredOtherPlatformComponentProfileCache.size()  = " + registeredOtherPlatformComponentProfileCache.size());
            for (PlatformComponentType platformComponentType: registeredOtherPlatformComponentProfileCache.keySet()) {
                LOG.info(platformComponentType + " = " + registeredOtherPlatformComponentProfileCache.get(platformComponentType).size());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Clean all reference from the registers cache into
     * a standByCache to wait to reconnect
     */
    private void putReferencesToStandBy(){

        try {

            LOG.info("--------------------------------------------------------------------- ");
            LOG.info("Starting method putReferencesToStandBy" );
            LOG.info("ID = " + activeClientConnection.getSession().getId());

           /*
             * Clean all the caches, remove data bind whit this connection and put
             * on stand by, to wait to reconnect
             */
            List<PlatformComponentProfile> removeProfile = removeNetworkServiceRegisteredByClientIdentity(activeClientConnection.getClientIdentity());
            removeProfile.addAll(removeOtherPlatformComponentRegisteredByClientIdentity(activeClientConnection.getClientIdentity()));
            pendingRegisterClientConnectionsCache.remove(activeClientConnection.getClientIdentity());
            standByProfileByClientIdentity.put(activeClientConnection.getClientIdentity(), removeProfile);
            registeredCommunicationsCloudServerCache.remove(activeClientConnection.getClientIdentity());
            registeredCommunicationsCloudClientCache.remove(activeClientConnection.getClientIdentity());
            LOG.info("Number of list of profiles put into standby = " + removeProfile.size());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method unregister network service component profile
     * register
     */
    private List<PlatformComponentProfile> removeNetworkServiceRegisteredByClientIdentity(final String clientIdentity){

        LOG.info("removeNetworkServiceRegisteredByClientIdentity ");

        List<PlatformComponentProfile> removeProfile = new ArrayList<>();

        Iterator<NetworkServiceType> iteratorNetworkServiceType = registeredNetworkServicesCache.keySet().iterator();

        while (iteratorNetworkServiceType.hasNext()){

            NetworkServiceType networkServiceType = iteratorNetworkServiceType.next();
            Iterator<PlatformComponentProfile> iterator = registeredNetworkServicesCache.get(networkServiceType).iterator();
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

            if (registeredNetworkServicesCache.get(networkServiceType).isEmpty()){
                registeredNetworkServicesCache.remove(networkServiceType);
            }
        }

        /*
         * Remove the networkServiceType empty
         */
        for (NetworkServiceType networkServiceType : registeredNetworkServicesCache.keySet()) {

            if (registeredNetworkServicesCache.get(networkServiceType).isEmpty()){
                registeredNetworkServicesCache.remove(networkServiceType);
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
        Iterator<PlatformComponentType> iteratorPlatformComponentType = registeredOtherPlatformComponentProfileCache.keySet().iterator();
        while (iteratorPlatformComponentType.hasNext()){

            PlatformComponentType platformComponentType = iteratorPlatformComponentType.next();
            Iterator<PlatformComponentProfile> iterator = registeredOtherPlatformComponentProfileCache.get(platformComponentType).iterator();
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
            if (registeredOtherPlatformComponentProfileCache.get(platformComponentType).isEmpty()){
                registeredOtherPlatformComponentProfileCache.remove(platformComponentType);
            }
        }

        return removeProfile;
    }


    /**
     * This method register a FermatJettyPacketProcessor object with this
     * server
     */
    public void registerFermatPacketProcessor(FermatJettyPacketProcessor fermatJettyPacketProcessor) {

        /*
         * Set server reference
         */
        fermatJettyPacketProcessor.setWebSocketCloudServerChannel(this);

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

        LOG.info("packetProcessorsRegister = " + packetProcessorsRegister.size());

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

    /**
     * Get the activeClientConnection value
     *
     * @return activeClientConnection current value
     */
    public ClientConnection getActiveClientConnection() {
        return activeClientConnection;
    }


}
