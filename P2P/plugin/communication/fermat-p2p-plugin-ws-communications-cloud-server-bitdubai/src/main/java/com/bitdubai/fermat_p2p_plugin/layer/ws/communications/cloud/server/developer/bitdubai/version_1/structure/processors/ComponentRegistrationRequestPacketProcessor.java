/*
 * @#ComponentRegistrationRequestPacketProcessor.java - 2015
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
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ComponentRegistrationRequestPacketProcessor</code> this
 * class process the FermatPacket of type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.COMPONENT_REGISTRATION_REQUEST</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ComponentRegistrationRequestPacketProcessor extends FermatPacketProcessor {


    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(WebSocket, FermatPacket, ECCKeyPair)
     */
    @Override
    public void processingPackage(final WebSocket clientConnection,final FermatPacket receiveFermatPacket, final ECCKeyPair serverIdentity) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("ComponentRegistrationRequestPacketProcessor - processingPackage");

        /*
         * Get the platformComponentProfile from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmectricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), serverIdentity.getPrivateKey());

        System.out.println("ComponentRegistrationRequestPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation );

        /*
         * Convert in platformComponentProfile
         */
        PlatformComponentProfile platformComponentProfile = new PlatformComponentProfileCommunication().fromJson(messageContentJsonStringRepresentation);

        /*
         * Switch between platform component type
         */
        switch (platformComponentProfile.getPlatformComponentType().getCode()){

            //COMMUNICATION_CLOUD_SERVER_COMPONENT;
            case "COM_CLD_SER_COMP" :
                registerCommunicationsCloudServerComponent(platformComponentProfile, receiveFermatPacket, clientConnection, serverIdentity);
                break;

            //COMMUNICATION_CLOUD_CLIENT_COMPONENT
            case "COM_CLD_CLI_COMP" :
                    registerCommunicationsCloudClientComponent(platformComponentProfile, receiveFermatPacket, clientConnection, serverIdentity);
                break;

            //Others
            default :
                    registerOtherComponent(platformComponentProfile, receiveFermatPacket, clientConnection, serverIdentity);
                break;

        }

    }

    /**
     * Method that process the registration of the Communications
     * Cloud Server Component
     */
    private void registerCommunicationsCloudServerComponent(final PlatformComponentProfile platformComponentProfile, final FermatPacket receiveFermatPacket, final WebSocket clientConnection,  final ECCKeyPair serverIdentity){

        System.out.println("ComponentRegistrationRequestPacketProcessor - registerCommunicationsCloudServerComponent");

        /* TODO: Do it in data base is better
         * Add to the cache
         */
        getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().put(clientConnection.hashCode(), platformComponentProfile);

    }

    /**
     * Method that process the registration of the Communications
     * Cloud Client Component
     */
    private void registerCommunicationsCloudClientComponent(final PlatformComponentProfile platformComponentProfile, final FermatPacket receiveFermatPacket, final WebSocket clientConnection,  final ECCKeyPair serverIdentity){

        System.out.println("ComponentRegistrationRequestPacketProcessor - registerCommunicationsCloudClientComponent");

        /* ------------------------------------
         * IMPORTANT: At this moment the server only
         * know the temporal identity of the client
         * the packet was constructed with this identity
         * --------------------------------------
         */

        /**
         * Validate if the connection if in the PendingRegisterClientConnectionsCache
         */
        if (getWsCommunicationCloudServer().getPendingRegisterClientConnectionsCache().containsKey(receiveFermatPacket.getSender())){

            /* TODO: Do it in data base is better
             * Add to the cache
             */
            getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().put(clientConnection.hashCode(), platformComponentProfile);

            /*
             * Remove from the PendingRegisterClientConnectionsCache
             */
            getWsCommunicationCloudServer().getPendingRegisterClientConnectionsCache().remove(receiveFermatPacket.getSender()); //Remove using temporal client identity

            /*
             * Add to the RegisteredClientConnectionsCache
             */
            getWsCommunicationCloudServer().getRegisteredClientConnectionsCache().put(platformComponentProfile.getIdentityPublicKey(), clientConnection); //Add using the real client identity from profile


            /**
             * Update the ClientIdentityByClientConnectionCache to the real identity
             */
            getWsCommunicationCloudServer().getClientIdentityByClientConnectionCache().put(clientConnection.hashCode(), platformComponentProfile.getIdentityPublicKey());


            /*
             * Construct a fermat packet whit the same platform component profile
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                  //Destination
                                                                                                                        serverIdentity.getPublicKey(),                    //Sender
                                                                                                                        platformComponentProfile.toJson(),                //Message Content
                                                                                                                        FermatPacketType.COMPLETE_COMPONENT_REGISTRATION, //Packet type
                                                                                                                        serverIdentity.getPrivateKey());                  //Sender private key

            /*
             * Send the encode packet to the server
             */
            clientConnection.send(FermatPacketEncoder.encode(fermatPacketRespond));

        }else {
            throw new RuntimeException("Forbidden connection this if NOT in the PendingRegisterClientConnectionsCache");
        }

    }

    /**
     * Method that process the registration of the others components
     */
    private void registerOtherComponent(final PlatformComponentProfile platformComponentProfile, final FermatPacket receiveFermatPacket, final WebSocket clientConnection,  final ECCKeyPair serverIdentity){

        System.out.println("ComponentRegistrationRequestPacketProcessor - registerOtherComponent");

        //TODO: Do it all this in data base is better

        /*
         * Validate if contain the PlatformComponentType
         */
        if (getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().containsValue(platformComponentProfile.getPlatformComponentType())){

            /*
             * Validate if contain the NetworkServiceType
             */
            if (getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentProfile.getPlatformComponentType()).containsKey(platformComponentProfile.getNetworkServiceType())){

                /*
                 * Add to the cache
                 */
                getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().
                                                get(platformComponentProfile.getPlatformComponentType()).
                                                get(platformComponentProfile.getNetworkServiceType()).
                                                add(platformComponentProfile);
            }else {

                /*
                 * Create new list by the NetworkServiceType and add the profile
                 */
                List<PlatformComponentProfile> newListPCP = new ArrayList<>();
                newListPCP.add(platformComponentProfile);

                /*
                 * Add to the cache
                 */
                getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().
                                                get(platformComponentProfile.getPlatformComponentType()).
                                                put(platformComponentProfile.getNetworkServiceType(), newListPCP);

            }

        }else {

            /*
             * Create new list by the NetworkServiceType and add the profile
             */
            List<PlatformComponentProfile> newListPCP = new ArrayList<>();
            newListPCP.add(platformComponentProfile);

            /*
             * Create a new map for holds the NetworkServiceTypes and add the list
             */
            Map<NetworkServiceType, List<PlatformComponentProfile>> newMapNS = new ConcurrentHashMap<>();
            newMapNS.put(platformComponentProfile.getNetworkServiceType(), newListPCP);

            /*
             * Create a new map for holds the NetworkServiceTypes and add the list
             */
            Map<PlatformComponentType, Map<NetworkServiceType, List<PlatformComponentProfile>>> newMapPCPT = new ConcurrentHashMap<>();
            newMapPCPT.put(platformComponentProfile.getPlatformComponentType(), newMapNS);

        }

    }


    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPONENT_REGISTRATION_REQUEST;
    }
}
