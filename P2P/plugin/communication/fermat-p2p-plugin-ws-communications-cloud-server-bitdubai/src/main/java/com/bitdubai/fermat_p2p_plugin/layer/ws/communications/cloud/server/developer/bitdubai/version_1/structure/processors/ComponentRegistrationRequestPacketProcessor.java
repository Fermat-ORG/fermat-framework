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
        PlatformComponentProfile platformComponentProfileToRegister = new PlatformComponentProfileCommunication().fromJson(messageContentJsonStringRepresentation);

        System.out.println("ComponentRegistrationRequestPacketProcessor - platformComponentProfileToRegister.getPlatformComponentType() = "+platformComponentProfileToRegister.getPlatformComponentType() );


        /*
         * Switch between platform component type
         */
        switch (platformComponentProfileToRegister.getPlatformComponentType().getCode()){

            //COMMUNICATION_CLOUD_SERVER_COMPONENT;
            case "COM_CLD_SER_COMP" :
                registerCommunicationsCloudServerComponent(platformComponentProfileToRegister, receiveFermatPacket, clientConnection, serverIdentity);
                break;

            //COMMUNICATION_CLOUD_CLIENT_COMPONENT
            case "COM_CLD_CLI_COMP" :
                    registerCommunicationsCloudClientComponent(platformComponentProfileToRegister, receiveFermatPacket, clientConnection, serverIdentity);
                break;

            //Others
            default :
                    registerOtherComponent(platformComponentProfileToRegister, receiveFermatPacket, clientConnection, serverIdentity);
                break;

        }

    }

    /**
     * Method that process the registration of the Communications
     * Cloud Server Component
     */
    private void registerCommunicationsCloudServerComponent(final PlatformComponentProfile platformComponentProfileToRegister, final FermatPacket receiveFermatPacket, final WebSocket clientConnection,  final ECCKeyPair serverIdentity){

        System.out.println("ComponentRegistrationRequestPacketProcessor - registerCommunicationsCloudServerComponent");

        /* TODO: Do it in data base is better
         * Add to the cache
         */
        getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().put(clientConnection.hashCode(), platformComponentProfileToRegister);

        /*
        * Construct a fermat packet whit the same platform component profile and different FermatPacketType
        */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                  //Destination
                                                                                                                    serverIdentity.getPublicKey(),                    //Sender
                                                                                                                    platformComponentProfileToRegister.toJson(),     //Message Content
                                                                                                                    FermatPacketType.COMPLETE_COMPONENT_REGISTRATION, //Packet type
                                                                                                                    serverIdentity.getPrivateKey());                  //Sender private key

        /*
         * Send the encode packet to the server
         */
         clientConnection.send(FermatPacketEncoder.encode(fermatPacketRespond));

    }

    /**
     * Method that process the registration of the Communications
     * Cloud Client Component
     */
    private void registerCommunicationsCloudClientComponent(final PlatformComponentProfile platformComponentProfileToRegister, final FermatPacket receiveFermatPacket, final WebSocket clientConnection,  final ECCKeyPair serverIdentity){

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
            getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().put(clientConnection.hashCode(), platformComponentProfileToRegister);

            /*
             * Remove from the PendingRegisterClientConnectionsCache
             */
            getWsCommunicationCloudServer().getPendingRegisterClientConnectionsCache().remove(receiveFermatPacket.getSender()); //Remove using temporal client identity

            /*
             * Add to the RegisteredClientConnectionsCache
             */
            getWsCommunicationCloudServer().getRegisteredClientConnectionsCache().put(platformComponentProfileToRegister.getIdentityPublicKey(), clientConnection); //Add using the real client identity from profile


            /**
             * Update the ClientIdentityByClientConnectionCache to the real identity
             */
            getWsCommunicationCloudServer().getClientIdentityByClientConnectionCache().put(clientConnection.hashCode(), platformComponentProfileToRegister.getIdentityPublicKey());


            /*
             * Construct a fermat packet whit the same platform component profile and different FermatPacketType
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                  //Destination
                                                                                                                        serverIdentity.getPublicKey(),                    //Sender
                                                                                                                        platformComponentProfileToRegister.toJson(),      //Message Content
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
    private void registerOtherComponent(final PlatformComponentProfile platformComponentProfileToRegister, final FermatPacket receiveFermatPacket, final WebSocket clientConnection,  final ECCKeyPair serverIdentity){

        System.out.println("ComponentRegistrationRequestPacketProcessor - registerOtherComponent");

        //TODO: Do it all this in data base is better

        System.out.println("ComponentRegistrationRequestPacketProcessor - getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().containsKey(platformComponentProfileToRegister.getPlatformComponentType()) = "+getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().containsKey(platformComponentProfileToRegister.getPlatformComponentType()));

        /*
         * Validate if contain the PlatformComponentType
         */
        if (getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().containsKey(platformComponentProfileToRegister.getPlatformComponentType())) {


            System.out.println("ComponentRegistrationRequestPacketProcessor - getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentProfileToRegister.getPlatformComponentType()).containsKey(platformComponentProfileToRegister.getNetworkServiceType()) = "+getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentProfileToRegister.getPlatformComponentType()).containsKey(platformComponentProfileToRegister.getNetworkServiceType()));

            /*
             * Validate if contain the NetworkServiceType
             */
            if (getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentProfileToRegister.getPlatformComponentType()).containsKey(platformComponentProfileToRegister.getNetworkServiceType())){


                System.out.println("ComponentRegistrationRequestPacketProcessor - entro en paso 1");

                /*
                 * Add to the cache
                 */
                getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().
                                                get(platformComponentProfileToRegister.getPlatformComponentType()).
                                                get(platformComponentProfileToRegister.getNetworkServiceType()).
                                                add(platformComponentProfileToRegister);
            }else {

                System.out.println("ComponentRegistrationRequestPacketProcessor - entro en paso 2");

                /*
                 * Create new list by the NetworkServiceType and add the profile
                 */
                List<PlatformComponentProfile> newListPCP = new ArrayList<>();
                newListPCP.add(platformComponentProfileToRegister);

                /*
                 * Add to the cache
                 */
                getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().
                                                get(platformComponentProfileToRegister.getPlatformComponentType()).
                                                put(platformComponentProfileToRegister.getNetworkServiceType(), newListPCP);

            }

        }else {

            System.out.println("ComponentRegistrationRequestPacketProcessor - entro en paso 3");

            /*
             * Create new list and add the profile
             */
            List<PlatformComponentProfile> newListPCP = new ArrayList<>();
            newListPCP.add(platformComponentProfileToRegister);

            /*
             * Create a new map for holds the NetworkServiceTypes and add the list
             */
            Map<NetworkServiceType, List<PlatformComponentProfile>> newMapNS = new ConcurrentHashMap<>();
            newMapNS.put(platformComponentProfileToRegister.getNetworkServiceType(), newListPCP);

            /*
             * Add to the cache
             */
            getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().put(platformComponentProfileToRegister.getPlatformComponentType(), newMapNS);

        }

        System.out.println("ComponentRegistrationRequestPacketProcessor - getRegisteredPlatformComponentProfileCache = " + getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().size());
        System.out.println("ComponentRegistrationRequestPacketProcessor - getPlatformComponentType = "+getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentProfileToRegister.getPlatformComponentType()).size());
        System.out.println("ComponentRegistrationRequestPacketProcessor - getNetworkServiceType = "+getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentProfileToRegister.getPlatformComponentType()).get(platformComponentProfileToRegister.getNetworkServiceType()).size());


        /*
         * Construct a fermat packet whit the same platform component profile and different FermatPacketType
         */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                  //Destination
                                                                                                                    serverIdentity.getPublicKey(),                    //Sender
                                                                                                                    platformComponentProfileToRegister.toJson(),      //Message Content
                                                                                                                    FermatPacketType.COMPLETE_COMPONENT_REGISTRATION, //Packet type
                                                                                                                    serverIdentity.getPrivateKey());                  //Sender private key

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
        return FermatPacketType.COMPONENT_REGISTRATION_REQUEST;
    }
}
