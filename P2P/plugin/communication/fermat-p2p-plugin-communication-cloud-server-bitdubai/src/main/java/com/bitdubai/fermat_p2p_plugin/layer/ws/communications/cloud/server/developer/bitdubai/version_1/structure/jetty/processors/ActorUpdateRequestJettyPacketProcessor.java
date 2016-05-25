/*
* @#ActorUpdateRequestPacketProcessor.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors;

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
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.ClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ActorUpdateRequestJettyPacketProcessor</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 06/01/16.
 * Update by Roberto Requena   - (rart3001@gmail.com) on 09/01/16
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorUpdateRequestJettyPacketProcessor extends FermatJettyPacketProcessor {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ActorUpdateRequestJettyPacketProcessor.class));

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
    public ActorUpdateRequestJettyPacketProcessor() {
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
        LOG.info("processingPackage");
        String packetContentJsonStringRepresentation = null;
        NetworkServiceType networkServiceTypeApplicant = null;
        PlatformComponentProfile platformComponentProfileToUpdate = null;


        try{

            /*
             * Get the platformComponentProfile from the message content and decrypt
             */
            packetContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), clientConnection.getServerIdentity().getPrivateKey());

            /*
             * Construct the json object
             */
            JsonObject contentJsonObject = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
            networkServiceTypeApplicant = gson.fromJson(contentJsonObject.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE).getAsString(), NetworkServiceType.class);
            platformComponentProfileToUpdate = new PlatformComponentProfileCommunication().fromJson(contentJsonObject.get(JsonAttNamesConstants.PROFILE_TO_UPDATE).getAsString());

            LOG.info("Identity = " + platformComponentProfileToUpdate.getIdentityPublicKey());
            LOG.info("Alias    = " + platformComponentProfileToUpdate.getAlias());
            LOG.info("Name     = " + platformComponentProfileToUpdate.getName());
            //LOG.info("Extra     = " + platformComponentProfileToUpdate.getExtraData());
            LOG.info("Type     = " + platformComponentProfileToUpdate.getPlatformComponentType());
            LOG.info("NSType   = " + platformComponentProfileToUpdate.getNetworkServiceType());


            if(platformComponentProfileToUpdate.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_SERVER ||
                    platformComponentProfileToUpdate.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT ||
                        platformComponentProfileToUpdate.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE){

                failureToUpdateActor(platformComponentProfileToUpdate, receiveFermatPacket, clientConnection, clientConnection.getServerIdentity(), networkServiceTypeApplicant, packetContentJsonStringRepresentation);

            }else{
                updateComponentActorProfile(platformComponentProfileToUpdate, receiveFermatPacket, clientConnection, clientConnection.getServerIdentity(), networkServiceTypeApplicant, packetContentJsonStringRepresentation);
            }

        }catch (Exception e){

            LOG.info("requested update is no possible ");
            LOG.info("cause: " + e.getMessage());

            /*
             * Construct the json object
             */
            JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
            packetContent.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());
            packetContent.addProperty(JsonAttNamesConstants.PROFILE_TO_UPDATE, platformComponentProfileToUpdate.toJson());
            packetContent.addProperty(JsonAttNamesConstants.FAILURE_VPN_MSJ, "failure in Update Component Actor Profile: "+e.getMessage());

            /*
             * Create the respond packet
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(), //Destination
                                                                                                                        clientConnection.getServerIdentity().getPublicKey(), //Sender
                                                                                                                        gson.toJson(packetContent), //packet Content
                                                                                                                        FermatPacketType.FAILURE_UPDATE_ACTOR_REQUEST, //Packet type
                                                                                                                        clientConnection.getServerIdentity().getPrivateKey()); //Sender private key
            /*
             * Send the packet
             */
            clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

        }

    }

    private void updateComponentActorProfile(final PlatformComponentProfile platformComponentProfileToUpdate, final FermatPacket receiveFermatPacket, final ClientConnection clientConnection,  final ECCKeyPair serverIdentity, final NetworkServiceType networkServiceTypeApplicant, final String packetContentJsonStringRepresentation){

        LOG.info("============================================================================ ");
        LOG.info("updateComponentActorProfile");


        Map<PlatformComponentType, List<PlatformComponentProfile>> registeredPlatformComponentProfile = MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache();

        /*
         * used to update actor profile
         */
        PlatformComponentProfile platformComponentProfileTemporal = null;

        /*
         * used to handle the object index
         */
        int element = 0;

        /*
         * used to notify if exist failure
         */
        boolean foundRegister = false;

        /*
         * Validate if contain a list for the NetworkServiceType
         */
        if (registeredPlatformComponentProfile.containsKey(platformComponentProfileToUpdate.getPlatformComponentType())){

            for (PlatformComponentProfile registered : registeredPlatformComponentProfile.get(platformComponentProfileToUpdate.getPlatformComponentType())) {

                if (registered.getIdentityPublicKey().equals(platformComponentProfileToUpdate.getIdentityPublicKey())){

                     /*
                      * used to notify if exist failure
                      */
                    foundRegister = true;

                    if(platformComponentProfileToUpdate.getName() != null ||
                            platformComponentProfileToUpdate.getAlias() != null ||
                            platformComponentProfileToUpdate.getExtraData() != null){

                        String nombre = (platformComponentProfileToUpdate.getName() != null) ? platformComponentProfileToUpdate.getName() : registered.getName();
                        String alias = (platformComponentProfileToUpdate.getAlias() != null) ? platformComponentProfileToUpdate.getAlias() : registered.getAlias();
                        String extraData = (platformComponentProfileToUpdate.getExtraData() != null) ? platformComponentProfileToUpdate.getExtraData() : registered.getExtraData();

                        platformComponentProfileTemporal = new PlatformComponentProfileCommunication(alias,
                                                                                                registered.getCommunicationCloudClientIdentity(),
                                                                                                registered.getIdentityPublicKey(),
                                                                                                registered.getLocation(),
                                                                                                nombre,
                                                                                                registered.getNetworkServiceType(),
                                                                                                registered.getPlatformComponentType(),
                                                                                                extraData);


                    }

                    break;

                }

                element++;
            }

        }

        if(platformComponentProfileTemporal != null){

            /*
             *  set the values to specific Actor Profile
             */
            registeredPlatformComponentProfile.get(platformComponentProfileToUpdate.getPlatformComponentType()).set(element,platformComponentProfileTemporal);

            /*
             * Construct the respond
             */
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());
            jsonObject.addProperty(JsonAttNamesConstants.PROFILE_TO_UPDATE, platformComponentProfileTemporal.toJson());

            /*
             * Construct a fermat packet whit the same platform component profile and different FermatPacketType
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(), //Destination
                                                                                                                        serverIdentity.getPublicKey(),                    //Sender
                                                                                                                        gson.toJson(jsonObject),                          //Message Content
                                                                                                                        FermatPacketType.COMPLETE_UPDATE_ACTOR, //Packet type
                                                                                                                        serverIdentity.getPrivateKey());                  //Sender private key

             /*
             * Send the encode packet to the server
             */
            clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

        }else{

            String message = null;

            if(foundRegister){
                message = "The fields Name, Alias, ExtraData must be setters are obligatorily";
            }else{
                message = "Register not found";
            }

            /*
             * Construct the json object
             */
            JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
            packetContent.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());
            packetContent.addProperty(JsonAttNamesConstants.PROFILE_TO_UPDATE, platformComponentProfileToUpdate.toJson());
            packetContent.addProperty(JsonAttNamesConstants.FAILURE_VPN_MSJ, message);

            /*
             * Create the respond packet
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(), //Destination
                                                                                                                        serverIdentity.getPublicKey(), //Sender
                                                                                                                        gson.toJson(packetContent), //packet Content
                                                                                                                        FermatPacketType.FAILURE_UPDATE_ACTOR_REQUEST, //Packet type
                                                                                                                        serverIdentity.getPrivateKey()); //Sender private key
            /*
             * Send the packet
             */
            clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

        }

    }

    private void failureToUpdateActor(final PlatformComponentProfile platformComponentProfileToUpdate, final FermatPacket receiveFermatPacket, final ClientConnection clientConnection,  final ECCKeyPair serverIdentity, final NetworkServiceType networkServiceTypeApplicant, final String packetContentJsonStringRepresentation){


        /*
         * Construct the json object
         */
        JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
        packetContent.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());
        packetContent.addProperty(JsonAttNamesConstants.PROFILE_TO_UPDATE, platformComponentProfileToUpdate.toJson());
        packetContent.addProperty(JsonAttNamesConstants.FAILURE_VPN_MSJ, "Failure Update ComponentProfile Cause: is only supported Actor to update ");

        /*
         * Create the respond packet
         */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(), //Destination
                                                                                                                    serverIdentity.getPublicKey(), //Sender
                                                                                                                    gson.toJson(packetContent), //packet Content
                                                                                                                    FermatPacketType.FAILURE_UPDATE_ACTOR_REQUEST, //Packet type
                                                                                                                    serverIdentity.getPrivateKey()); //Sender private key
        /*
         * Send the packet
         */
        clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

    }

    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.UPDATE_ACTOR_REQUEST;
    }

}
