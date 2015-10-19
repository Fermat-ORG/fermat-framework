/*
 * @#ComponentConnectionRespondPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.WsCommunicationsCloudClientPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.vpn.WsCommunicationVPNClientManagerAgent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.util.StringTokenizer;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.ComponentConnectionRespondPacketProcessor</code> implement
 * the logic to process the packet when a packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.COMPONENT_CONNECTION_RESPOND</code> is receive by the server.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ComponentConnectionRespondPacketProcessor extends FermatPacketProcessor {

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {


        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("ComponentConnectionRespondPacketProcessor - Starting processingPackage");

        /*
         * Get the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getClientIdentity().getPrivateKey());


        System.out.println("ComponentConnectionRespondPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject respond = parser.parse(messageContentJsonStringRepresentation).getAsJsonObject();

        try {

            //Get all values
            URI vpnServerUri = new URI(respond.get(JsonAttNamesConstants.VPN_URI).getAsString());
            String vpnServerIdentity = respond.get(JsonAttNamesConstants.VPN_SERVER_IDENTITY).getAsString();
            String participantIdentity = respond.get(JsonAttNamesConstants.REGISTER_PARTICIPANT_IDENTITY_VPN).getAsString();
            PlatformComponentProfile remotePlatformComponentProfile = gson.fromJson(respond.get(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN).getAsString(), PlatformComponentProfileCommunication.class);
            PlatformComponentProfile remoteNsPlatformComponentProfile = gson.fromJson(respond.get(JsonAttNamesConstants.REMOTE_PARTICIPANT_NS_VPN).getAsString(), PlatformComponentProfileCommunication.class);

            /*
             * TEMPORAL:
             * Reconstruct the uri, for the configuration of the AWS. The internal ip is different to the public ip,
             * when return the vpnServerUri the cloud server only know the internal ip and send this in the respond.
             * Need to fix this situation in the future
             */
            StringTokenizer stringTokenizer = new StringTokenizer(vpnServerUri.toString(), ":");
            stringTokenizer.nextElement();
            stringTokenizer.nextElement();
            String port = (String) stringTokenizer.nextElement();
            vpnServerUri = new URI("ws://" + WsCommunicationsCloudClientPluginRoot.SERVER_IP  + ":" + port);

            System.out.println("ComponentConnectionRespondPacketProcessor - reconstruct vpnServerUri = "+vpnServerUri);

            /*
             * Get the  wsCommunicationVPNClientManagerAgent
             */
            WsCommunicationVPNClientManagerAgent wsCommunicationVPNClientManagerAgent = getWsCommunicationsCloudClientChannel().getWsCommunicationsCloudClientConnection().getWsCommunicationVPNClientManagerAgent();

            /*
             * Create a new VPN client
             */
            wsCommunicationVPNClientManagerAgent.createNewWsCommunicationVPNClient(vpnServerUri, vpnServerIdentity, participantIdentity, remotePlatformComponentProfile, remoteNsPlatformComponentProfile);

            /*
             * Is not running
             */
            if (!wsCommunicationVPNClientManagerAgent.isRunning()){
                wsCommunicationVPNClientManagerAgent.start();
            }

        } catch (Exception e) {
           throw new RuntimeException(e);
        }

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPONENT_CONNECTION_RESPOND;
    }
}
