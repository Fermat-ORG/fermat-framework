/*
 * @#RequestListComponentRegisterPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.AttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.RequestListComponentRegisterPacketProcessor</code> implement
 * the logic to process the packet when a packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED</code> is receive by the server.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RequestListComponentRegisterPacketProcessor extends FermatPacketProcessor {

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(WebSocket, FermatPacket, ECCKeyPair)
     */
    @Override
    public void processingPackage(WebSocket clientConnection, FermatPacket receiveFermatPacket, ECCKeyPair serverIdentity) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("RequestListComponentRegisterPacketProcessor - Starting processingPackage");

        /*
         * Get the filters from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmectricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), serverIdentity.getPrivateKey());


        System.out.println("RequestListComponentRegisterPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject filters = parser.parse(messageContentJsonStringRepresentation).getAsJsonObject();

        /*
         * Get the filters
         */
        PlatformComponentType platformComponentType = gson.fromJson(filters.get(AttNamesConstants.JSON_ATT_NAME_COMPONENT_TYPE), PlatformComponentType.class);
        NetworkServiceType networkServiceType       = gson.fromJson(filters.get(AttNamesConstants.JSON_ATT_NAME_NETWORK_SERVICE_TYPE), NetworkServiceType.class);

        System.out.println("RequestListComponentRegisterPacketProcessor - platformComponentType = "+platformComponentType);
        System.out.println("RequestListComponentRegisterPacketProcessor - networkServiceType    = "+networkServiceType);

        /*
         * Get the list
         */
        List<PlatformComponentProfile> list = null;

        /*
         * Switch between platform component type
         */
        switch (platformComponentType.getCode()){

            //COMMUNICATION_CLOUD_SERVER_COMPONENT;
            case "COM_CLD_SER_COMP" :
                list = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().values());
                break;

            //COMMUNICATION_CLOUD_CLIENT_COMPONENT
            case "COM_CLD_CLI_COMP" :
                list = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().values());
                break;

            //Others
            default :
                list = getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentType).get(networkServiceType);
                break;

        }

        System.out.println("RequestListComponentRegisterPacketProcessor - list.size()    = "+list.size());

        /*
         * Convert to json representation
         */
        String jsonListRepresentation = gson.toJson(list, new TypeToken<List<PlatformComponentProfileCommunication>>() { }.getType());

        System.out.println("RequestListComponentRegisterPacketProcessor - gson.toJson(list)    = "+jsonListRepresentation);


         /*
         * Construct a fermat packet whit the list
         */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                    //Destination
                                                                                                                    serverIdentity.getPublicKey(),                      //Sender
                                                                                                                    jsonListRepresentation,                             //Message Content
                                                                                                                    FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED, //Packet type
                                                                                                                    serverIdentity.getPrivateKey());                    //Sender private key

        /*
        * Send the encode packet to the server
        */
        clientConnection.send(FermatPacketEncoder.encode(fermatPacketRespond));
    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED;
    }
}
