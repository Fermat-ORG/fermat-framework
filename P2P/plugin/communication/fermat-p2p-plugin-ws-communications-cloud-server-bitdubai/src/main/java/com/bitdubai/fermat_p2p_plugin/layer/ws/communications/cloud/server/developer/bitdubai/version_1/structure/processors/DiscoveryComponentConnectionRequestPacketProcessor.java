/*
 * @#ComponentConnectionRequestPacketProcesor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
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

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.DiscoveryComponentConnectionRequestPacketProcessor</code> process
 * the received packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.DISCOVERY_COMPONENT_CONNECTION_REQUEST</code>, this packet type indicate that a
 * a component type is try to connect whit the component is the his same type, but he has the identity public key from the other component type different as hin.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DiscoveryComponentConnectionRequestPacketProcessor extends FermatPacketProcessor {

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
    public DiscoveryComponentConnectionRequestPacketProcessor() {
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
        System.out.println("DiscoveryComponentConnectionRequestPacketProcessor - Starting processingPackage");

        String packetContentJsonStringRepresentation = null;
        PlatformComponentProfile networkServiceApplicant = null;
        PlatformComponentProfile remoteParticipant = null;

        try {

            /*
             * Get the packet content from the message content and decrypt
             */
            packetContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), serverIdentity.getPrivateKey());
            System.out.println("DiscoveryComponentConnectionRequestPacketProcessor - packetContentJsonStringRepresentation = "+packetContentJsonStringRepresentation);


            /*
             * Construct the json object
             */
            JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();

            /*
             * Get the applicant network service
             */
            networkServiceApplicant = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.APPLICANT_PARTICIPANT_NS_VPN).getAsString());
            System.out.println("DiscoveryComponentConnectionRequestPacketProcessor - networkServiceApplicant "+networkServiceApplicant.toJson());

            /*
             * Get the component profile as participant that live in the same device of the network service applicant
             */
            PlatformComponentProfile applicantParticipant = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN).getAsString());
            List<PlatformComponentProfile> applicantParticipantList = searchProfileByCommunicationCloudClientIdentity(applicantParticipant.getPlatformComponentType(), applicantParticipant.getNetworkServiceType(), networkServiceApplicant.getCommunicationCloudClientIdentity());
            for (PlatformComponentProfile platformComponentProfile: applicantParticipantList) {
                if (platformComponentProfile.getIdentityPublicKey().equals(applicantParticipant.getIdentityPublicKey()) &&
                        platformComponentProfile.getCommunicationCloudClientIdentity().equals(networkServiceApplicant.getCommunicationCloudClientIdentity())){
                    applicantParticipant = platformComponentProfile;
                    break;
                }
            }
            System.out.println("DiscoveryComponentConnectionRequestPacketProcessor - applicantParticipant "+applicantParticipant.toJson());

            /*
             * Get the component profile to connect as remote participant
             */
            remoteParticipant = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN).getAsString());
            List<PlatformComponentProfile> remoteParticipantList = searchProfile(remoteParticipant.getPlatformComponentType(), remoteParticipant.getNetworkServiceType(), remoteParticipant.getIdentityPublicKey());
            remoteParticipant = remoteParticipantList.get(0);
            System.out.println("DiscoveryComponentConnectionRequestPacketProcessor - remoteParticipant "+remoteParticipant.toJson());

            /*
             * Get the network service profile that live in the same device of the remote participant
             */
            List<PlatformComponentProfile> remoteNsParticipantList = searchProfileByCommunicationCloudClientIdentity(networkServiceApplicant.getPlatformComponentType(), networkServiceApplicant.getNetworkServiceType(), remoteParticipant.getCommunicationCloudClientIdentity());
            PlatformComponentProfile remoteNsParticipant = remoteNsParticipantList.get(0);
            System.out.println("DiscoveryComponentConnectionRequestPacketProcessor - remoteNsParticipant "+remoteNsParticipant.toJson());

            /*
             * Create the list of participant
             */
            List<PlatformComponentProfile> participantsList = new ArrayList<>();
            participantsList.add(applicantParticipant);
            participantsList.add(remoteParticipant);

            System.out.println("DiscoveryComponentConnectionRequestPacketProcessor - participantsList.size() = "+participantsList.size());

            //Create a new vpn
            WsCommunicationVPNServer vpnServer = getWsCommunicationCloudServer().getWsCommunicationVpnServerManagerAgent().createNewWsCommunicationVPNServer(participantsList, getWsCommunicationCloudServer(), networkServiceApplicant.getNetworkServiceType());

            /*
             * Notify to the participants of the vpn
             */
            constructRespondPacketAndSend(vpnServer, applicantParticipant, remoteParticipant, remoteNsParticipant);
            constructRespondPacketAndSend(vpnServer, remoteParticipant, applicantParticipant, networkServiceApplicant);

            //if no running
            if (!getWsCommunicationCloudServer().getWsCommunicationVpnServerManagerAgent().isRunning()){

                //Start the agent
                getWsCommunicationCloudServer().getWsCommunicationVpnServerManagerAgent().start();
            }

        }catch (Exception e){

            System.out.println("DiscoveryComponentConnectionRequestPacketProcessor - requested connection is no possible ");
            System.out.println("DiscoveryComponentConnectionRequestPacketProcessor - cause: "+e.getMessage());

            /*
             * Get the client connection destination
             */
            WebSocket clientConnectionDestination = getWsCommunicationCloudServer().getRegisteredClientConnectionsCache().get(receiveFermatPacket.getSender());

            /*
             * Construct the json object
             */
            JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
            packetContent.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_NS_VPN, networkServiceApplicant.toJson());
            packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN, remoteParticipant.toJson());
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
     * Method that search the PlatformComponentProfiles tha mach with the
     * parameters
     *
     * @param platformComponentType
     * @param networkServiceType
     * @param identityPublicKey
     * @return List<PlatformComponentProfile>
     */
    private List<PlatformComponentProfile> searchProfile(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String identityPublicKey) {

        /*
         * Prepare the list
         */
        List<PlatformComponentProfile> temporalList = new ArrayList<>();
        List<PlatformComponentProfile>  finalFilteredList = new ArrayList<>();

         /*
         * Switch between platform component type
         */
        switch (platformComponentType){

            case COMMUNICATION_CLOUD_SERVER :
                if (!getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().isEmpty()){
                    temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().values());
                }
                break;

            case COMMUNICATION_CLOUD_CLIENT :
                if (!getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().isEmpty()){
                    temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().values());
                }
                break;

            case NETWORK_SERVICE :
                if(getWsCommunicationCloudServer().getRegisteredNetworkServicesCache().containsKey(networkServiceType) && !getWsCommunicationCloudServer().getRegisteredNetworkServicesCache().get(networkServiceType).isEmpty()){
                    temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredNetworkServicesCache().get(networkServiceType));
                }
                break;

            //Others
            default :
                if (getWsCommunicationCloudServer().getRegisteredOtherPlatformComponentProfileCache().containsKey(platformComponentType) && !getWsCommunicationCloudServer().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).isEmpty()){
                    temporalList = getWsCommunicationCloudServer().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType);
                }
                break;

        }

        /*
         * Find the component that match with the identity
         */
        for (PlatformComponentProfile platformComponentProfile: temporalList) {

            if (platformComponentProfile.getIdentityPublicKey().equals(identityPublicKey)){
                finalFilteredList.add(platformComponentProfile);
            }
        }


        return finalFilteredList;

    }


    /**
     * Method that search the PlatformComponentProfiles tha mach with the
     * parameters
     *
     * @param platformComponentType
     * @param networkServiceType
     * @param communicationCloudClientIdentity
     * @return List<PlatformComponentProfile>
     */
    private List<PlatformComponentProfile> searchProfileByCommunicationCloudClientIdentity(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String communicationCloudClientIdentity) {

        /*
         * Prepare the list
         */
        List<PlatformComponentProfile> temporalList =  new ArrayList<>();
        List<PlatformComponentProfile>  finalFilteredList = new ArrayList<>();

         /*
         * Switch between platform component type
         */
        switch (platformComponentType){

            case COMMUNICATION_CLOUD_SERVER :
                if (!getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().isEmpty()) {
                    temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().values());
                }
                break;

            case COMMUNICATION_CLOUD_CLIENT :
                if (!getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().isEmpty()){
                    temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().values());
                }
                break;

            case NETWORK_SERVICE :
                if(getWsCommunicationCloudServer().getRegisteredNetworkServicesCache().containsKey(networkServiceType) && !getWsCommunicationCloudServer().getRegisteredNetworkServicesCache().get(networkServiceType).isEmpty()) {
                    temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredNetworkServicesCache().get(networkServiceType));
                }
                break;

            //Others
            default :
                if (getWsCommunicationCloudServer().getRegisteredOtherPlatformComponentProfileCache().containsKey(platformComponentType) && !getWsCommunicationCloudServer().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).isEmpty()) {
                    temporalList = getWsCommunicationCloudServer().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType);
                }
                break;
        }

        /*
         * Find the component that match with the CommunicationCloudClientIdentity
         */
        for (PlatformComponentProfile platformComponentProfile: temporalList) {

            if (platformComponentProfile.getCommunicationCloudClientIdentity().equals(communicationCloudClientIdentity)){
                finalFilteredList.add(platformComponentProfile);
            }
        }

        return finalFilteredList;

    }


    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.DISCOVERY_COMPONENT_CONNECTION_REQUEST;
    }
}
