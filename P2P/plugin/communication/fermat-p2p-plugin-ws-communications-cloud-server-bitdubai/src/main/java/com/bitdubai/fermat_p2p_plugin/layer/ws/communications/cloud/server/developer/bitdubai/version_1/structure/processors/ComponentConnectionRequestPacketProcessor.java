/*
 * @#ComponentConnectionRequestPacketProcesor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.vpn.WsCommunicationVPNServer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.java_websocket.WebSocket;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ComponentConnectionRequestPacketProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ComponentConnectionRequestPacketProcessor extends FermatPacketProcessor {

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
    public ComponentConnectionRequestPacketProcessor() {
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(WebSocket, FermatPacket, ECCKeyPair)
     */
    @Override
    public void processingPackage(WebSocket clientConnection, FermatPacket receiveFermatPacket, ECCKeyPair serverIdentity) {


        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("ComponentConnectionRequestPacketProcessor - Starting processingPackage");
        String packetContentJsonStringRepresentation = null;

        try {

             /*
             * Get the packet content from the message content and decrypt
             */
            packetContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), serverIdentity.getPrivateKey());
            System.out.println("ComponentConnectionRequestPacketProcessor - packetContentJsonStringRepresentation = "+packetContentJsonStringRepresentation);

            /*
             * Get the list
             */
            List<PlatformComponentProfile> participantsList = gson.fromJson(packetContentJsonStringRepresentation, new TypeToken<List<PlatformComponentProfileCommunication>>(){}.getType());

            for (PlatformComponentProfile participant: participantsList) {
                System.out.println("ComponentConnectionRequestPacketProcessor - participant = "+participant.getIdentityPublicKey());
            }

            PlatformComponentProfile peer1 = participantsList.get(0);
            PlatformComponentProfile peer2 = participantsList.get((participantsList.size() - 1));

            //Create a new vpn
            WsCommunicationVPNServer vpnServer = getWsCommunicationCloudServer().getWsCommunicationVpnServerManagerAgent().createNewWsCommunicationVPNServer(participantsList, getWsCommunicationCloudServer(), peer1.getNetworkServiceType());

            constructRespondPacketAndSend(vpnServer, peer1, peer2, peer2);
            constructRespondPacketAndSend(vpnServer, peer2, peer1, peer1);

            //if no running
            if (!getWsCommunicationCloudServer().getWsCommunicationVpnServerManagerAgent().isRunning()){

                //Start the agent
                getWsCommunicationCloudServer().getWsCommunicationVpnServerManagerAgent().start();
            }

        }catch (Exception e){

            System.out.println("ComponentConnectionRequestPacketProcessor - requested connection is no possible ");
           // e.printStackTrace();

            /*
             * Get the client connection destination
             */
            WebSocket clientConnectionDestination = getWsCommunicationCloudServer().getRegisteredClientConnectionsCache().get(receiveFermatPacket.getSender());

            /*
             * Construct the json object
             */
            JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
            packetContent.addProperty(JsonAttNamesConstants.FAILURE_VPN_MSJ, "failure in component connection: "+e.getMessage());

            /*
             * Create the respond packet
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(), //Destination
                                                                                                                        serverIdentity.getPublicKey(), //Sender
                                                                                                                        gson.toJson(packetContent), //packet Content
                                                                                                                        FermatPacketType.FAILURE_COMPONENT_CONNECTION_REQUEST, //Packet type
                                                                                                                        serverIdentity.getPrivateKey()); //Sender private key
            /*
             * Send the packet
             */
            clientConnectionDestination.send(FermatPacketEncoder.encode(fermatPacketRespond));

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
        WebSocket clientConnectionDestination = getWsCommunicationCloudServer().getRegisteredClientConnectionsCache().get(platformComponentProfileDestination.getCommunicationCloudClientIdentity());

        /*
         * Get the server identity for this client
         */
        ECCKeyPair serverIdentity = getWsCommunicationCloudServer().getServerIdentityByClientCache().get(clientConnectionDestination.hashCode());

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
        clientConnectionDestination.send(FermatPacketEncoder.encode(fermatPacketRespond));

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPONENT_CONNECTION_REQUEST;
    }
}
