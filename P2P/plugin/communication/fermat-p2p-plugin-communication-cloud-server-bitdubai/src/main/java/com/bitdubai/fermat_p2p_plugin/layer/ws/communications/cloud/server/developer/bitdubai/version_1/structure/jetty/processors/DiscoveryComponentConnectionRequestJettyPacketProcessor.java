/*
 * @#ComponentConnectionRequestPacketProcesor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.ClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.JettyEmbeddedAppServer;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.WebSocketVpnIdentity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.DiscoveryComponentConnectionRequestJettyPacketProcessor</code> process
 * the received packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.DISCOVERY_COMPONENT_CONNECTION_REQUEST</code>, this packet type indicate that a
 * a component type is try to connect whit the component is the his same type, but he has the identity public key from the other component type different as hin.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DiscoveryComponentConnectionRequestJettyPacketProcessor extends FermatJettyPacketProcessor {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(DiscoveryComponentConnectionRequestJettyPacketProcessor.class));

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
    public DiscoveryComponentConnectionRequestJettyPacketProcessor() {
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

        String packetContentJsonStringRepresentation     = null;
        PlatformComponentProfile applicantParticipant    = null;
        PlatformComponentProfile applicantNetworkService = null;
        PlatformComponentProfile remoteParticipantRequested = null;
        PlatformComponentProfile remoteParticipant       = null;
        PlatformComponentProfile remoteNsParticipant     = null;

        try {

            /*
             * Get the packet content from the message content and decrypt
             */
            packetContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), clientConnection.getServerIdentity().getPrivateKey());
            LOG.info("packetContentJsonStringRepresentation = " + packetContentJsonStringRepresentation);

            /*
             * Construct the json object
             */
            JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();

            /*
             * Get the applicant network service
             */
            applicantNetworkService = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.APPLICANT_PARTICIPANT_NS_VPN).getAsString());
            LOG.info("networkServiceApplicant = " + applicantNetworkService.getAlias() + "("+applicantNetworkService.getIdentityPublicKey()+")");

            /*
             * Get the component profile as participant that live in the same device of the network service applicant
             */
            applicantParticipant = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN).getAsString());
            List<PlatformComponentProfile> applicantParticipantList = searchProfileByCommunicationCloudClientIdentity(applicantParticipant.getPlatformComponentType(), applicantParticipant.getNetworkServiceType(), applicantNetworkService.getCommunicationCloudClientIdentity());
            for (PlatformComponentProfile platformComponentProfile: applicantParticipantList) {
                if (platformComponentProfile.getIdentityPublicKey().equals(applicantParticipant.getIdentityPublicKey()) &&
                        platformComponentProfile.getCommunicationCloudClientIdentity().equals(applicantNetworkService.getCommunicationCloudClientIdentity())){
                    applicantParticipant = platformComponentProfile;
                    break;
                }
            }
            LOG.info("applicantParticipant = " + applicantParticipant.getAlias() + "("+applicantParticipant.getIdentityPublicKey()+")");

            /*
             * Get the component profile to connect as remote participant
             */
            remoteParticipantRequested = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN).getAsString());
            List<PlatformComponentProfile> remoteParticipantList = searchProfile(remoteParticipantRequested.getPlatformComponentType(), remoteParticipantRequested.getNetworkServiceType(), remoteParticipantRequested.getIdentityPublicKey());
            remoteParticipant = remoteParticipantList.get(0);
            LOG.info("remoteParticipant = " + remoteParticipant.getAlias() + "("+remoteParticipant.getIdentityPublicKey()+")");

            /*
             * Get the network service profile that live in the same device of the remote participant
             */
            List<PlatformComponentProfile> remoteNsParticipantList = searchProfileByCommunicationCloudClientIdentity(applicantNetworkService.getPlatformComponentType(), applicantNetworkService.getNetworkServiceType(), remoteParticipant.getCommunicationCloudClientIdentity());
            remoteNsParticipant = remoteNsParticipantList.get(0);
            LOG.info("remoteNsParticipant = " + remoteNsParticipant.getAlias() + "("+remoteNsParticipant.getIdentityPublicKey()+")");

            //Create a new vpn
            String vpnPath = JettyEmbeddedAppServer.DEFAULT_CONTEXT_PATH + "/vpn/";

            LOG.info("Vpn path = " + vpnPath);

            constructRequestConnectToVpnRespondPacketAndSend(vpnPath, applicantParticipant, remoteParticipant, remoteNsParticipant);
            constructRequestConnectToVpnRespondPacketAndSend(vpnPath, remoteParticipant, applicantParticipant, applicantNetworkService);


        }catch (Exception e){

            LOG.error("Requested connection is no possible, some of the participant are no available.");
            LOG.error("Details: ");
            LOG.error("ApplicantParticipant is available    = " + (applicantParticipant    != null ? "SI (" + applicantParticipant.getAlias()    + ")" : "NO" ));
            LOG.error("NetworkServiceApplicant is available = " + (applicantNetworkService != null ? "SI (" + applicantNetworkService.getAlias() + ")" : "NO"));
            LOG.error("RemoteParticipant is available       = " + (remoteParticipant       != null ? "SI (" + remoteParticipant.getAlias()       + ")" : "NO"));
            LOG.error("RemoteNsParticipant is available     = " + (remoteNsParticipant     != null ? "SI (" + remoteNsParticipant.getAlias()     + ")" : "NO"));
            LOG.error("Cause: " + e.getMessage());

            String details = "";

            if (applicantParticipant == null){
                details = "Applicant Participant is not available. ";
            }

            if (applicantNetworkService == null){
                details = "Applicant Network Service is not available. ";
            }

            if (remoteParticipant == null){
                details = "RemoteParticipant is not available. ";
            }

            if (remoteNsParticipant == null){
                details = "Remote Network Service Participant is not available. ";
            }

            /*
             * Construct the json object
             */
            JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
            packetContent.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_NS_VPN, applicantNetworkService.toJson());
            packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN, remoteParticipantRequested.toJson());
            packetContent.addProperty(JsonAttNamesConstants.FAILURE_VPN_MSJ, "Failure in component connection, some of the component needed to establish the vpn are no available: "+details+" "+e.getMessage());

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
     * @param path
     * @param platformComponentProfileDestination
     * @param remoteParticipant
     * @param remoteParticipantNetworkService
     */
    private void constructRequestConnectToVpnRespondPacketAndSend(String path, PlatformComponentProfile platformComponentProfileDestination, PlatformComponentProfile remoteParticipant, PlatformComponentProfile remoteParticipantNetworkService){


        LOG.info("------------------------------------------------------ -----------------------------------------------------");
        LOG.info("Sending vpn connection to = " + platformComponentProfileDestination.getAlias());
        LOG.info("Sending whit remote = " + remoteParticipant.getAlias());

        /*
         * Get json representation for the filters
         */
        JsonObject packetContent = new JsonObject();
        packetContent.addProperty(JsonAttNamesConstants.VPN_URI, path);
        packetContent.addProperty(JsonAttNamesConstants.VPN_SERVER_IDENTITY, WebSocketVpnIdentity.getInstance().getIdentity().getPublicKey());
        packetContent.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN, platformComponentProfileDestination.toJson());
        packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN, remoteParticipant.toJson());
        packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_NS_VPN, remoteParticipantNetworkService.toJson());

        LOG.debug("packetContent = " +gson.toJson(packetContent));

        /*
         * Get the client connection destination
         */
        ClientConnection clientConnectionDestination = MemoryCache.getInstance().getRegisteredClientConnectionsCache().get(platformComponentProfileDestination.getCommunicationCloudClientIdentity());

        LOG.info("platformComponentProfileDestination.getCommunicationCloudClientIdentity() = " +platformComponentProfileDestination.getCommunicationCloudClientIdentity());
        LOG.info("clientConnectionDestination = " +clientConnectionDestination);
        LOG.info("clientConnectionDestination.getServerIdentity() = " +clientConnectionDestination.getServerIdentity());
        LOG.info("clientConnectionDestination.getServerIdentity().getPublicKey() = " +clientConnectionDestination.getServerIdentity().getPublicKey());
        LOG.info("clientConnectionDestination.getServerIdentity().getPrivateKey() = " +clientConnectionDestination.getServerIdentity().getPrivateKey());

        /*
         * Create the respond packet
         */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(platformComponentProfileDestination.getCommunicationCloudClientIdentity(), //Destination
                                                                                                                    clientConnectionDestination.getServerIdentity().getPublicKey(), //Sender
                                                                                                                    gson.toJson(packetContent), //packet Content
                                                                                                                    FermatPacketType.COMPONENT_CONNECTION_RESPOND, //Packet type
                                                                                                                    clientConnectionDestination.getServerIdentity().getPrivateKey()); //Sender private key
        /*
         * Send the packet
         */
        clientConnectionDestination.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

    }

    /**
     * Construct a packet whit the information that a vpn is ready
     *
     * @param destinationPlatformComponentProfile
     * @param remotePlatformComponentProfile
     */
    private void sendNotificationVpnConnectionComplete(PlatformComponentProfile destinationPlatformComponentProfile, PlatformComponentProfile remotePlatformComponentProfile, NetworkServiceType networkServiceType){

        LOG.info("sendNotificationVpnConnectionComplete = " + destinationPlatformComponentProfile.getName() + " (" + destinationPlatformComponentProfile.getIdentityPublicKey() + ")");

         /*
         * Construct the content of the msj
         */
        Gson gson = new Gson();
        JsonObject packetContent = new JsonObject();
        packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN,  remotePlatformComponentProfile.toJson());
        packetContent.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN,  destinationPlatformComponentProfile.toJson());
        packetContent.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceType.toString());

        /*
         * Get the connection client of the destination
         * IMPORTANT: No send by vpn connection, no support this type of packet
         */
        ClientConnection clientConnectionDestination = MemoryCache.getInstance().getRegisteredClientConnectionsCache().get(destinationPlatformComponentProfile.getCommunicationCloudClientIdentity());

        /*
        * Construct a new fermat packet whit the same message and different destination
        */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(destinationPlatformComponentProfile.getCommunicationCloudClientIdentity(), //Destination
                clientConnectionDestination.getServerIdentity().getPublicKey(), //Sender
                gson.toJson(packetContent),                                        //Message Content
                FermatPacketType.COMPLETE_COMPONENT_CONNECTION_REQUEST,            //Packet type
                clientConnectionDestination.getServerIdentity().getPrivateKey()); //Sender private key
        /*
        * Send the encode packet to the destination
        */
        clientConnectionDestination.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

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

            case COMMUNICATION_CLOUD_CLIENT :
                if (!MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().isEmpty()){
                    temporalList = new ArrayList<>(MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().values());
                }
                break;

            case NETWORK_SERVICE :
                if(MemoryCache.getInstance().getRegisteredNetworkServicesCache().containsKey(networkServiceType) && !MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).isEmpty()){
                    temporalList = new ArrayList<>(MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType));
                }
                break;

            //Others
            default :
                if (MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().containsKey(platformComponentType) && !MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).isEmpty()){
                    temporalList = MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType);
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

            case COMMUNICATION_CLOUD_CLIENT :
                if (!MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().isEmpty()){
                    temporalList = new ArrayList<>(MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().values());
                }
                break;

            case NETWORK_SERVICE :
                if(MemoryCache.getInstance().getRegisteredNetworkServicesCache().containsKey(networkServiceType) && !MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).isEmpty()) {
                    temporalList = new ArrayList<>(MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType));
                }
                break;

            //Others
            default :
                if (MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().containsKey(platformComponentType) && !MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).isEmpty()) {
                    temporalList = MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType);
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
     * @see FermatJettyPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.DISCOVERY_COMPONENT_CONNECTION_REQUEST;
    }
}
