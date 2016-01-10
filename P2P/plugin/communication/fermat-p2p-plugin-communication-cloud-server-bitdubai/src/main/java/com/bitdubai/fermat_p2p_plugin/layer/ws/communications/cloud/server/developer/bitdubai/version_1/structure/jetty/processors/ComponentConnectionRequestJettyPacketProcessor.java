/*
 * @#ComponentConnectionRequestPacketProcesor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.ClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.vpn.WsCommunicationVPNServer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ComponentConnectionRequestJettyPacketProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ComponentConnectionRequestJettyPacketProcessor extends FermatJettyPacketProcessor {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ComponentConnectionRequestJettyPacketProcessor.class));

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the jsonParser
     */
    private JsonParser jsonParser;

    /**
     * Constructor
     */
    public ComponentConnectionRequestJettyPacketProcessor() {
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    /**
     * (no-javadoc)
     * @see FermatJettyPacketProcessor#processingPackage(ClientConnection, FermatPacket)
     */
    @Override
    public void processingPackage(ClientConnection clientConnection, FermatPacket receiveFermatPacket) {


        LOG.info("--------------------------------------------------------------------- ");
        LOG.info("Starting processingPackage");
        String packetContentJsonStringRepresentation = null;
        PlatformComponentProfile peer1 = null;
        PlatformComponentProfile peer2 = null;

        try {

             /*
             * Get the packet content from the message content and decrypt
             */
            packetContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), clientConnection.getServerIdentity().getPrivateKey());
            LOG.info("packetContentJsonStringRepresentation = " + packetContentJsonStringRepresentation);

            /*
             * Get the list
             */
            List<PlatformComponentProfile> participantsList = gson.fromJson(packetContentJsonStringRepresentation, new TypeToken<List<PlatformComponentProfileCommunication>>() {
            }.getType());

            for (PlatformComponentProfile participant: participantsList) {
                LOG.info("participant = " + participant.getAlias() + "("+participant.getIdentityPublicKey()+")");
            }

            peer1 = participantsList.get(0);
            peer2 = participantsList.get((participantsList.size() - 1));

            //Create a new vpn
            //WsCommunicationVPNServer vpnServer = getWebSocketCloudServerChannel().getWsCommunicationVpnServerManagerAgent().createNewWsCommunicationVPNServer(participantsList, getWebSocketCloudServerChannel(), peer1.getNetworkServiceType());

            //constructRespondPacketAndSend(vpnServer, peer1, peer2, peer2);
            //constructRespondPacketAndSend(vpnServer, peer2, peer1, peer1);

            //if no running
           // if (!getWebSocketCloudServerChannel().getWsCommunicationVpnServerManagerAgent().isRunning()){

                //Start the agent
             //   getWebSocketCloudServerChannel().getWsCommunicationVpnServerManagerAgent().start();
           // }

        }catch (Exception e){

            LOG.error("requested connection is no possible ");
            LOG.error(" cause: "+e.getMessage());

            /*
             * Construct the json object
             */
            JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
            packetContent.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_NS_VPN, peer1.toJson());
            packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN, peer2.toJson());
            packetContent.addProperty(JsonAttNamesConstants.FAILURE_VPN_MSJ, "failure in component connection: "+e.getMessage());

            /*
             * Create the respond packet
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(), //Destination
                                                                                                                        clientConnection.getServerIdentity().getPublicKey(), //Sender
                                                                                                                        gson.toJson(packetContent), //packet Content
                                                                                                                        FermatPacketType.FAILURE_COMPONENT_CONNECTION_REQUEST, //Packet type
                                                                                                                        clientConnection.getServerIdentity().getPrivateKey()); //Sender private key

            /*
             * Send the packet
             */
            clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

        }




    }

    /**
     * Construct Respond Packet
     *
     * @param vpnServer
     * @param platformComponentProfileDestination
     * @param remoteParticipant
     * @param remoteParticipantNetworkService
     */
    private void constructRespondPacketAndSend(WsCommunicationVPNServer vpnServer, PlatformComponentProfile platformComponentProfileDestination, PlatformComponentProfile remoteParticipant, PlatformComponentProfile remoteParticipantNetworkService){

        /*
         * Get json representation for the filters
         */
        JsonObject packetContent = new JsonObject();
        packetContent.addProperty(JsonAttNamesConstants.VPN_URI, vpnServer.getUriConnection().toString());
        packetContent.addProperty(JsonAttNamesConstants.VPN_SERVER_IDENTITY, vpnServer.getVpnServerIdentityPublicKey());
        packetContent.addProperty(JsonAttNamesConstants.REGISTER_PARTICIPANT_IDENTITY_VPN, platformComponentProfileDestination.getIdentityPublicKey());
        packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN, remoteParticipant.toJson());
        packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_NS_VPN, remoteParticipantNetworkService.toJson());

        /*
         * Get the client connection destination
         */
        ClientConnection clientConnectionDestination = MemoryCache.getInstance().getRegisteredClientConnectionsCache().get(platformComponentProfileDestination.getCommunicationCloudClientIdentity());

        /*
         * Get the server identity for this client
         */
        ECCKeyPair serverIdentity = clientConnectionDestination.getServerIdentity();

        /*
         * Create the respond packet
         */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(platformComponentProfileDestination.getCommunicationCloudClientIdentity(), //Destination
                serverIdentity.getPublicKey(), //Sender
                gson.toJson(packetContent), //packet Content
                FermatPacketType.COMPONENT_CONNECTION_RESPOND, //Packet type
                serverIdentity.getPrivateKey()); //Sender private key
        /*
         * Send the packet
         */
        clientConnectionDestination.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

    }

    /**
     * (no-javadoc)
     * @see FermatJettyPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPONENT_CONNECTION_REQUEST;
    }
}
