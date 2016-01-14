/*
 * @#ComponentRegistrationRequestPacketProcessor.java - 2015
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
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ComponentRegistrationRequestJettyPacketProcessor</code> this
 * class process the FermatPacket of type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.COMPONENT_REGISTRATION_REQUEST</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ComponentRegistrationRequestJettyPacketProcessor extends FermatJettyPacketProcessor {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ComponentRegistrationRequestJettyPacketProcessor.class));

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
    public ComponentRegistrationRequestJettyPacketProcessor() {
        gson = new Gson();
        jsonParser = new JsonParser();
    }


    /**
     * (no-javadoc)
     * @see FermatJettyPacketProcessor#processingPackage(ClientConnection, FermatPacket)
     */
    @Override
    public void processingPackage(final ClientConnection clientConnection, final FermatPacket receiveFermatPacket) {

        LOG.info("--------------------------------------------------------------------- ");
        LOG.info("processingPackage");
        String packetContentJsonStringRepresentation = null;
        NetworkServiceType networkServiceTypeApplicant = null;
        PlatformComponentProfile platformComponentProfileToRegister = null;

        try {

            /*
             * Get the platformComponentProfile from the message content and decrypt
             */
            packetContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), clientConnection.getServerIdentity().getPrivateKey());

            /*
             * Construct the json object
             */
            JsonObject contentJsonObject = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
            networkServiceTypeApplicant = gson.fromJson(contentJsonObject.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE).getAsString(), NetworkServiceType.class);
            platformComponentProfileToRegister = new PlatformComponentProfileCommunication().fromJson(contentJsonObject.get(JsonAttNamesConstants.PROFILE_TO_REGISTER).getAsString());

            LOG.info("Identity = " + platformComponentProfileToRegister.getIdentityPublicKey());
            LOG.info("Alias    = " + platformComponentProfileToRegister.getAlias());
            LOG.info("Name     = " + platformComponentProfileToRegister.getName());
            LOG.info("Type     = " + platformComponentProfileToRegister.getPlatformComponentType());
            LOG.info("NSType   = " + platformComponentProfileToRegister.getNetworkServiceType());

            /*
             * Switch between platform component type
             */
            switch (platformComponentProfileToRegister.getPlatformComponentType()){

                case COMMUNICATION_CLOUD_SERVER :
                    registerCommunicationsCloudServerComponent(platformComponentProfileToRegister, receiveFermatPacket, clientConnection, networkServiceTypeApplicant);
                    break;

                case COMMUNICATION_CLOUD_CLIENT :
                    registerCommunicationsCloudClientComponent(platformComponentProfileToRegister, receiveFermatPacket, clientConnection, networkServiceTypeApplicant);
                    break;

                case NETWORK_SERVICE :
                    registerNetworkServiceComponent(platformComponentProfileToRegister, receiveFermatPacket, clientConnection, networkServiceTypeApplicant);
                    break;

                //Others
                default :
                    registerOtherComponent(platformComponentProfileToRegister, receiveFermatPacket, clientConnection, networkServiceTypeApplicant);
                    break;

            }

        }catch (Exception e){

            LOG.info("requested registration is no possible ");
            LOG.info("cause: " + e.getMessage());

            /*
             * Construct the json object
             */
            JsonObject packetContent = jsonParser.parse(packetContentJsonStringRepresentation).getAsJsonObject();
            packetContent.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());
            packetContent.addProperty(JsonAttNamesConstants.PROFILE_TO_REGISTER, platformComponentProfileToRegister.toJson());
            packetContent.addProperty(JsonAttNamesConstants.FAILURE_VPN_MSJ, "failure in registration component: "+e.getMessage());

            /*
             * Create the respond packet
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(), //Destination
                                                                                                                        clientConnection.getServerIdentity().getPublicKey(), //Sender
                                                                                                                        gson.toJson(packetContent), //packet Content
                                                                                                                        FermatPacketType.FAILURE_COMPONENT_REGISTRATION_REQUEST, //Packet type
                                                                                                                        clientConnection.getServerIdentity().getPrivateKey()); //Sender private key
            /*
             * Send the packet
             */
            clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

        }




    }

    /**
     * Method that process the registration of the Communications
     * Cloud Server Component
     */
    private void registerCommunicationsCloudServerComponent(final PlatformComponentProfile platformComponentProfileToRegister, final FermatPacket receiveFermatPacket, final ClientConnection clientConnection, final NetworkServiceType networkServiceTypeApplicant){

        LOG.info("registerCommunicationsCloudServerComponent");

        /*
         * Add to the cache
         */
        MemoryCache.getInstance().getRegisteredCommunicationsCloudServerCache().put(clientConnection.hashCode(), platformComponentProfileToRegister);

         /*
         * Construct the respond
         */
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());
        jsonObject.addProperty(JsonAttNamesConstants.PROFILE_TO_REGISTER, platformComponentProfileToRegister.toJson());

        /*
        * Construct a fermat packet whit the same platform component profile and different FermatPacketType
        */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                  //Destination
                                                                                                                    clientConnection.getServerIdentity().getPublicKey(),                    //Sender
                                                                                                                    gson.toJson(jsonObject),                          //Message Content
                                                                                                                    FermatPacketType.COMPLETE_COMPONENT_REGISTRATION, //Packet type
                                                                                                                    clientConnection.getServerIdentity().getPrivateKey());                  //Sender private key

        /*
         * Send the encode packet to the server
         */
         clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

        LOG.info("Total Communications Cloud Server Component Registered = " + MemoryCache.getInstance().getRegisteredCommunicationsCloudServerCache().size());

    }

    /**
     * Method that process the registration of the Communications
     * Cloud Client Component
     */
    private void registerCommunicationsCloudClientComponent(final PlatformComponentProfile platformComponentProfileToRegister, final FermatPacket receiveFermatPacket, final ClientConnection clientConnection, final NetworkServiceType networkServiceTypeApplicant){

        LOG.info("registerCommunicationsCloudClientComponent");

        /* ------------------------------------
         * IMPORTANT: At this moment the server only
         * know the temporal identity of the client
         * the packet was constructed with this identity
         * --------------------------------------
         */

        /*
         * Validate if the connection if in the PendingRegisterClientConnectionsCache
         */
        if (MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().containsKey(receiveFermatPacket.getSender())){

            PlatformComponentProfile oldReference = null;

            /*
             * Validate are not yet registered
             */
            for (PlatformComponentProfile registered : MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().values()) {

                if (registered.getIdentityPublicKey().equals(platformComponentProfileToRegister.getIdentityPublicKey())){
                    LOG.info("Find a old reference");
                    oldReference = registered;
                }
            }

            /*
             * If exist remove all reference
             */
            if (oldReference != null) {
                LOG.info("Removing old reference");
                MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().remove(oldReference);
            }

            /*
             * Add to the cache
             */
            MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().put(clientConnection.getSession().hashCode(), platformComponentProfileToRegister);

            /*
             * Remove from the PendingRegisterClientConnectionsCache
             */
            MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().remove(receiveFermatPacket.getSender()); //Remove using temporal client identity

            /*
             * Add to the RegisteredClientConnectionsCache
             */
            MemoryCache.getInstance().getRegisteredClientConnectionsCache().put(platformComponentProfileToRegister.getIdentityPublicKey(), clientConnection); //Add using the real client identity from profile

            /*
             * Update the ClientIdentityByClientConnectionCache to the real identity
             */
            clientConnection.setClientIdentity(platformComponentProfileToRegister.getIdentityPublicKey());

            FermatPacket fermatPacketRespond = null;

            /*
             * Validate if no has a profile references in stand by, is have is a reconnection
             */
            if(!MemoryCache.getInstance().getStandByProfileByClientIdentity().containsKey(platformComponentProfileToRegister.getIdentityPublicKey())){

                LOG.info("New registration");

                /*
                 * Construct the respond
                 */
                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());
                jsonObject.addProperty(JsonAttNamesConstants.PROFILE_TO_REGISTER, platformComponentProfileToRegister.toJson());

                /*
                 * Construct a fermat packet whit the same platform component profile and different FermatPacketType
                 */
                fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                  //Destination
                                                                                                                clientConnection.getServerIdentity().getPublicKey(),                    //Sender
                                                                                                                gson.toJson(jsonObject),                          //Message Content
                                                                                                                FermatPacketType.COMPLETE_COMPONENT_REGISTRATION, //Packet type
                                                                                                                clientConnection.getServerIdentity().getPrivateKey());  //Sender private key

            }else{

                LOG.info("Registration for reconnection");

                if (MemoryCache.getInstance().getTimersByClientIdentity().containsKey(platformComponentProfileToRegister.getIdentityPublicKey())){
                    LOG.info("Cancel timer task to clean references");
                    Timer timer = MemoryCache.getInstance().getTimersByClientIdentity().get(platformComponentProfileToRegister.getIdentityPublicKey());
                    timer.cancel();
                }

                /*
                 * Get the profiles in stand by
                 */
                List<PlatformComponentProfile> profilesInStandBy = MemoryCache.getInstance().getStandByProfileByClientIdentity().remove(platformComponentProfileToRegister.getIdentityPublicKey());

                /*
                 * Move again to the registers profile cache
                 */
                moveFromStandByToRegistersCache(profilesInStandBy);

                /*
                 * Construct a fermat packet whit reconnect notification
                 */
                fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                  //Destination
                                                                                                                clientConnection.getServerIdentity().getPublicKey(),                    //Sender
                                                                                                                "Reconnect successfully",                          //Message Content
                                                                                                                FermatPacketType.CLIENT_CONNECTION_SUCCESSFULLY_RECONNECT, //Packet type
                                                                                                                clientConnection.getServerIdentity().getPrivateKey());

            }


            LOG.info("FermatPacketRespond to send = "+fermatPacketRespond.getFermatPacketType());

            /*
             * Send the encode packet to the server
             */
            clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

            LOG.info("Total Communications Cloud Client Component Registered = " + MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().size());

        }else {

            throw new RuntimeException("Forbidden connection this if NOT in the PendingRegisterClientConnectionsCache");
        }

    }



    /**
     * Method that process the registration of the Network Service Component
     */
    private void registerNetworkServiceComponent(final PlatformComponentProfile platformComponentProfileToRegister, final FermatPacket receiveFermatPacket, final ClientConnection clientConnection, final NetworkServiceType networkServiceTypeApplicant){

        LOG.info("registerNetworkServiceComponent");

        Map<NetworkServiceType, List<PlatformComponentProfile>> networkServiceRegistered = MemoryCache.getInstance().getRegisteredNetworkServicesCache();

        /*
         * Validate if contain a list for the NetworkServiceType
         */
        if (networkServiceRegistered.containsKey(platformComponentProfileToRegister.getNetworkServiceType())){

            /*
             * Validate are not yet registered
             */
            for (PlatformComponentProfile registered : networkServiceRegistered.get(platformComponentProfileToRegister.getNetworkServiceType())) {

                if (registered.getIdentityPublicKey().equals(platformComponentProfileToRegister.getIdentityPublicKey())){
                    throw new RuntimeException("The PlatformComponentProfile is already registered con el server");
                }
            }

            /*
             * Add to the list
             */
            networkServiceRegistered.get(platformComponentProfileToRegister.getNetworkServiceType()).add(platformComponentProfileToRegister);

        }else {

            /*
             * Create new list by the NetworkServiceType and add the profile
             */
            List<PlatformComponentProfile> newListPCP = new ArrayList<>();
            newListPCP.add(platformComponentProfileToRegister);

            networkServiceRegistered.put(platformComponentProfileToRegister.getNetworkServiceType(), newListPCP);

        }

         /*
         * Construct the respond
         */
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());
        jsonObject.addProperty(JsonAttNamesConstants.PROFILE_TO_REGISTER, platformComponentProfileToRegister.toJson());


        /*
         * Construct a fermat packet whit the same platform component profile and different FermatPacketType
         */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                  //Destination
                                                                                                                    clientConnection.getServerIdentity().getPublicKey(),                    //Sender
                                                                                                                    gson.toJson(jsonObject),                    //Message Content
                                                                                                                    FermatPacketType.COMPLETE_COMPONENT_REGISTRATION, //Packet type
                                                                                                                    clientConnection.getServerIdentity().getPrivateKey());                  //Sender private key

         /*
         * Send the encode packet to the server
         */
        clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

        LOG.info("Total Network Service Component Registered (" + platformComponentProfileToRegister.getNetworkServiceType() + ") = " + networkServiceRegistered.get(platformComponentProfileToRegister.getNetworkServiceType()).size());

    }

    /**
     * Method that process the registration of the others components
     */
    private void registerOtherComponent(final PlatformComponentProfile platformComponentProfileToRegister, final FermatPacket receiveFermatPacket, final ClientConnection clientConnection, final NetworkServiceType networkServiceTypeApplicant){

        LOG.info("============================================================================ ");
        LOG.info("registerOtherComponent");


        Map<PlatformComponentType, List<PlatformComponentProfile>> registeredPlatformComponentProfile = MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache();

        /*
         * Validate if contain a list for the NetworkServiceType
         */
        if (registeredPlatformComponentProfile.containsKey(platformComponentProfileToRegister.getPlatformComponentType())){

            /*
             * Validate are not yet registered
             */
            for (PlatformComponentProfile registered : registeredPlatformComponentProfile.get(platformComponentProfileToRegister.getPlatformComponentType())) {

                if (registered.getIdentityPublicKey().equals(platformComponentProfileToRegister.getIdentityPublicKey())){
                    throw new RuntimeException("The PlatformComponentProfile is already registered con el server");
                }
            }

            /*
             * Add to the list
             */
            registeredPlatformComponentProfile.get(platformComponentProfileToRegister.getPlatformComponentType()).add(platformComponentProfileToRegister);

        }else {

            /*
             * Create new list by the PlatformComponentType and add the profile
             */
            List<PlatformComponentProfile> newListPCP = new ArrayList<>();
            newListPCP.add(platformComponentProfileToRegister);

            registeredPlatformComponentProfile.put(platformComponentProfileToRegister.getPlatformComponentType(), newListPCP);

        }

        /*
         * Construct the respond
         */
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());
        jsonObject.addProperty(JsonAttNamesConstants.PROFILE_TO_REGISTER, platformComponentProfileToRegister.toJson());

        /*
         * Construct a fermat packet whit the same platform component profile and different FermatPacketType
         */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                  //Destination
                                                                                                                    clientConnection.getServerIdentity().getPublicKey(),                    //Sender
                                                                                                                    gson.toJson(jsonObject),                          //Message Content
                                                                                                                    FermatPacketType.COMPLETE_COMPONENT_REGISTRATION, //Packet type
                                                                                                                    clientConnection.getServerIdentity().getPrivateKey());                  //Sender private key

         /*
         * Send the encode packet to the server
         */
        clientConnection.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

        LOG.info(" Total (" + platformComponentProfileToRegister.getPlatformComponentType() + ") Component Registered  = " + registeredPlatformComponentProfile.get(platformComponentProfileToRegister.getPlatformComponentType()).size());

    }

    /**
     * Move all profile in stand by of the list to the respective register cache
     * @param profilesInStandBy
     */
    private void moveFromStandByToRegistersCache(List<PlatformComponentProfile> profilesInStandBy){

        LOG.info(" Moving "+profilesInStandBy.size()+" references from StandBy cache To Registers Caches");

        Iterator<PlatformComponentProfile> iterable = (Iterator<PlatformComponentProfile>) profilesInStandBy.iterator();

        while (iterable.hasNext()){

            PlatformComponentProfile platformComponentProfileToRegister = iterable.next();
            LOG.info(" Moving component "+platformComponentProfileToRegister.getPlatformComponentType().toString()+" ["+platformComponentProfileToRegister.getNetworkServiceType().toString()+"]  references from StandBy cache To Registers Caches");

            /*
             * Switch between platform component type
             */
            switch (platformComponentProfileToRegister.getPlatformComponentType()){

                case NETWORK_SERVICE :

                        Map<NetworkServiceType, List<PlatformComponentProfile>> networkServiceRegistered = MemoryCache.getInstance().getRegisteredNetworkServicesCache();

                        /*
                         * Validate if contain a list for the NetworkServiceType
                         */
                        if (networkServiceRegistered.containsKey(platformComponentProfileToRegister.getNetworkServiceType())){

                            /*
                             * Validate are not yet registered
                             */
                            for (PlatformComponentProfile registered : networkServiceRegistered.get(platformComponentProfileToRegister.getNetworkServiceType())) {

                                if (registered.getIdentityPublicKey().equals(platformComponentProfileToRegister.getIdentityPublicKey())){
                                    throw new RuntimeException("The PlatformComponentProfile is already registered con el server");
                                }
                            }

                            /*
                             * Add to the list
                             */
                            networkServiceRegistered.get(platformComponentProfileToRegister.getNetworkServiceType()).add(platformComponentProfileToRegister);

                        }else {

                            /*
                             * Create new list by the NetworkServiceType and add the profile
                             */
                            List<PlatformComponentProfile> newListPCP = new ArrayList<>();
                            newListPCP.add(platformComponentProfileToRegister);

                            networkServiceRegistered.put(platformComponentProfileToRegister.getNetworkServiceType(), newListPCP);

                        }

                    iterable.remove();

                    break;

                //Others
                default :

                        Map<PlatformComponentType, List<PlatformComponentProfile>> registeredPlatformComponentProfile = MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache();

                        /*
                         * Validate if contain a list for the NetworkServiceType
                         */
                        if (registeredPlatformComponentProfile.containsKey(platformComponentProfileToRegister.getPlatformComponentType())){

                            /*
                             * Validate are not yet registered
                             */
                            for (PlatformComponentProfile registered : registeredPlatformComponentProfile.get(platformComponentProfileToRegister.getPlatformComponentType())) {

                                if (registered.getIdentityPublicKey().equals(platformComponentProfileToRegister.getIdentityPublicKey())){
                                    throw new RuntimeException("The PlatformComponentProfile is already registered con el server");
                                }
                            }

                            /*
                             * Add to the list
                             */
                            registeredPlatformComponentProfile.get(platformComponentProfileToRegister.getPlatformComponentType()).add(platformComponentProfileToRegister);

                        }else {

                            /*
                             * Create new list by the PlatformComponentType and add the profile
                             */
                            List<PlatformComponentProfile> newListPCP = new ArrayList<>();
                            newListPCP.add(platformComponentProfileToRegister);

                            registeredPlatformComponentProfile.put(platformComponentProfileToRegister.getPlatformComponentType(), newListPCP);

                        }

                    iterable.remove();

                    break;

            }

        }

    }


    /**
     * (no-javadoc)
     * @see FermatJettyPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPONENT_REGISTRATION_REQUEST;
    }
}
