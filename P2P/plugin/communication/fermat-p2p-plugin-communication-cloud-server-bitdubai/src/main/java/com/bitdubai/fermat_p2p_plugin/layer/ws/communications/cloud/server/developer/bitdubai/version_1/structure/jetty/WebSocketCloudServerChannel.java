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
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.FermatJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
     * Represent the active client Connection
     */
    private ClientConnection activeClientConnection;

    /**
     * Constructor
     */
    public WebSocketCloudServerChannel(){
        super();
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
            MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().put(temporalClientIdentity, activeClientConnection);

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

        LOG.info("session.getOpenSessions().size() = " + session.getOpenSessions().size());

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
        if (MemoryCache.getInstance().getPacketProcessorsRegister().containsKey(fermatPacketReceive.getFermatPacketType())){


            /*
             * Call the processors for this packet
             */
            for (FermatJettyPacketProcessor fermatPacketProcessor : MemoryCache.getInstance().getPacketProcessorsRegister().get(fermatPacketReceive.getFermatPacketType())) {

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
                                       MemoryCache.getInstance().getStandByProfileByClientIdentity().remove(clientIdentity);
                                       LOG.info("standByProfileByClientIdentity = " + MemoryCache.getInstance().getStandByProfileByClientIdentity().size());
                                   }
                               },
                        60000
                );

                MemoryCache.getInstance().getTimersByClientIdentity().put(clientIdentity, timer);
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
            MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().remove(activeClientConnection.getClientIdentity());
            MemoryCache.getInstance().getRegisteredCommunicationsCloudServerCache().remove(activeClientConnection.getClientIdentity());
            MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().remove(activeClientConnection.getClientIdentity());
            MemoryCache.getInstance().getRegisteredClientConnectionsCache().remove(activeClientConnection.getClientIdentity());

            LOG.info("pendingRegisterClientConnectionsCache.size()    = " + MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().size());
            LOG.info("registeredCommunicationsCloudServerCache.size() = " + MemoryCache.getInstance().getRegisteredCommunicationsCloudServerCache().size());
            LOG.info("registeredCommunicationsCloudClientCache.size() = " + MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().size());
            LOG.info("registeredNetworkServicesCache.size()           = " + MemoryCache.getInstance().getRegisteredNetworkServicesCache().size());
            for (NetworkServiceType networkServiceType: MemoryCache.getInstance().getRegisteredNetworkServicesCache().keySet()) {
                LOG.info(networkServiceType + " = " + MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).size());
            }
            LOG.info("registeredOtherPlatformComponentProfileCache.size()  = " + MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().size());
            for (PlatformComponentType platformComponentType: MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().keySet()) {
                LOG.info(platformComponentType + " = " + MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).size());
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
            MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().remove(activeClientConnection.getClientIdentity());
            MemoryCache.getInstance().getRegisteredCommunicationsCloudServerCache().remove(activeClientConnection.getClientIdentity());
            MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().remove(activeClientConnection.getClientIdentity());
            MemoryCache.getInstance().getRegisteredClientConnectionsCache().remove(activeClientConnection.getClientIdentity());

            List<PlatformComponentProfile> removeProfile = removeNetworkServiceRegisteredByClientIdentity(activeClientConnection.getClientIdentity());
            removeProfile.addAll(removeOtherPlatformComponentRegisteredByClientIdentity(activeClientConnection.getClientIdentity()));

            MemoryCache.getInstance().getStandByProfileByClientIdentity().put(activeClientConnection.getClientIdentity(), removeProfile);
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

    /**
     * Get the activeClientConnection value
     *
     * @return activeClientConnection current value
     */
    public ClientConnection getActiveClientConnection() {
        return activeClientConnection;
    }


}
