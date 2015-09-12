/*
 * @#ComponentConnectionRequestPacketProcesor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationVpnServerManagerAgent;
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
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(WebSocket, FermatPacket, ECCKeyPair)
     */
    @Override
    public void processingPackage(WebSocket clientConnection, FermatPacket receiveFermatPacket, ECCKeyPair serverIdentity) {


        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("ComponentConnectionRequestPacketProcessor - Starting processingPackage");

        /*
         * Get the packet content from the message content and decrypt
         */
        String packetContentJsonStringRepresentation = AsymmectricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), serverIdentity.getPrivateKey());
        System.out.println("ComponentConnectionRequestPacketProcessor - packetContentJsonStringRepresentation = "+packetContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        Gson gson = new Gson();

        /*
         * Get the list
         */
        List<String> participantsList = gson.fromJson(packetContentJsonStringRepresentation, new TypeToken<List<String>>(){}.getType());

        //Create a new vpn
        getWsCommunicationCloudServer().getWsCommunicationVpnServerManagerAgent().createNewWsCommunicationVPNServer(participantsList);

        //if no running
        if (!getWsCommunicationCloudServer().getWsCommunicationVpnServerManagerAgent().isRunning()){

            //Start the agent
            getWsCommunicationCloudServer().getWsCommunicationVpnServerManagerAgent().start();
        }


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
