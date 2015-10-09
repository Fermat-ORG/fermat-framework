/*
 * @#RequestListComponentRegisterPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.DiscoveryQueryParametersCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

        /*
         * Construct the json object
         */
        Gson gson = new Gson();
        DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParametersCommunication().fromJson(messageContentJsonStringRepresentation);

        /*
         * hold the result list
         */
        List<PlatformComponentProfile> resultList = null;

        if (discoveryQueryParameters.getFromOtherPlatformComponentType() == null &&
                discoveryQueryParameters.getFromOtherNetworkServiceType() == null){

            resultList = applyDiscoveryQueryParameters(discoveryQueryParameters, receiveFermatPacket);

        }else{

            resultList = applyDiscoveryQueryParametersFromOtherComponent(discoveryQueryParameters, receiveFermatPacket);

        }

        System.out.println("RequestListComponentRegisterPacketProcessor - filteredLis.size() ="+resultList.size());

        /*
         * Convert the list to json representation
         */
        String jsonListRepresentation = gson.toJson(resultList, new TypeToken<List<PlatformComponentProfileCommunication>>(){ }.getType());

        /*
         * Create the respond
         */
        JsonObject jsonObjectRespond = new JsonObject();
        jsonObjectRespond.addProperty(JsonAttNamesConstants.COMPONENT_TYPE,       discoveryQueryParameters.getPlatformComponentType().toString());
        jsonObjectRespond.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, discoveryQueryParameters.getNetworkServiceType().toString());
        jsonObjectRespond.addProperty(JsonAttNamesConstants.RESULT_LIST,          jsonListRepresentation);

         /*
         * Construct a fermat packet whit the list
         */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(receiveFermatPacket.getSender(),                    //Destination
                                                                                                                    serverIdentity.getPublicKey(),                      //Sender
                                                                                                                    gson.toJson(jsonObjectRespond),                     //Message Content
                                                                                                                    FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED, //Packet type
                                                                                                                    serverIdentity.getPrivateKey());                    //Sender private key
        /*
        * Send the encode packet to the server
        */
        clientConnection.send(FermatPacketEncoder.encode(fermatPacketRespond));
    }

    /**
     * Return the primary list from the cache filtered by the platformComponentType or
     * networkServiceType
     *
     * @param platformComponentType
     * @param networkServiceType
     * @return List<PlatformComponentProfile>
     */
    public List<PlatformComponentProfile> getPrimaryFilteredListFromCache(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, FermatPacket receiveFermatPacket){

        /*
         * Get the list
         */
        List<PlatformComponentProfile> list = null;

         /*
         * Switch between platform component type
         */
        switch (platformComponentType){

            case COMMUNICATION_CLOUD_SERVER :
                list = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().values());
                break;

            case COMMUNICATION_CLOUD_CLIENT :
                list = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().values());
                break;

            case NETWORK_SERVICE :
                list = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredNetworkServicesCache().get(networkServiceType));
                break;

            //Others
            default :
                list = getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentType);
                break;

        }

        /*
         * Remove the requester from the list
         */
        for (PlatformComponentProfile platformComponentProfileRegistered: list) {
            if(platformComponentProfileRegistered.getCommunicationCloudClientIdentity().equals(receiveFermatPacket.getSender())){
                System.out.println("RequestListComponentRegisterPacketProcessor - removing ="+platformComponentProfileRegistered.getName());
                list.remove(platformComponentProfileRegistered);
                break;
            }
        }

        return list;
    }


    /**
     * Filter the PlatformComponentProfile that match with the discoveryQueryParameters
     *
     * @param discoveryQueryParameters
     * @param receiveFermatPacket
     * @return List<PlatformComponentProfile>
     */
    private  List<PlatformComponentProfile> applyDiscoveryQueryParameters(DiscoveryQueryParameters discoveryQueryParameters, FermatPacket receiveFermatPacket){

        int totalFilterToApply = countFilers(discoveryQueryParameters);
        int filterMatched = 0;

        List<PlatformComponentProfile>  list = getPrimaryFilteredListFromCache(discoveryQueryParameters.getPlatformComponentType(), discoveryQueryParameters.getNetworkServiceType(), receiveFermatPacket);
        List<PlatformComponentProfile>  filteredLis = new ArrayList<>();

        System.out.println("RequestListComponentRegisterPacketProcessor - totalFilterToApply    = "+totalFilterToApply);


        if (totalFilterToApply > 0){

            /*
             * Apply the basic filter
             */
            for (PlatformComponentProfile platformComponentProfile: list) {

                if (discoveryQueryParameters.getIdentityPublicKey() != null && discoveryQueryParameters.getIdentityPublicKey() != ""){
                    if (platformComponentProfile.getIdentityPublicKey().equals(discoveryQueryParameters.getIdentityPublicKey())){
                        filterMatched += 1;
                    }
                }

                if (discoveryQueryParameters.getAlias() != null && discoveryQueryParameters.getAlias() != ""){
                    if (discoveryQueryParameters.getAlias().toLowerCase().contains(platformComponentProfile.getAlias().toLowerCase())){
                        filterMatched += 1;
                    }
                }

                if (discoveryQueryParameters.getName() != null && discoveryQueryParameters.getName() != ""){
                    if (discoveryQueryParameters.getName().toLowerCase().contains(platformComponentProfile.getName().toLowerCase())){
                        filterMatched += 1;
                    }
                }

                if (discoveryQueryParameters.getExtraData() != null && discoveryQueryParameters.getExtraData() != ""){
                    if (discoveryQueryParameters.getExtraData().toLowerCase().contains(platformComponentProfile.getExtraData().toLowerCase())){
                        filterMatched += 1;
                    }
                }

                //if all filter matched
                if (totalFilterToApply == filterMatched){
                    //Add to the list
                    filteredLis.add(platformComponentProfile);
                }

            }

        }else {

            filteredLis = list;
        }

        /*
         * Apply geo location filter
         */
        if (discoveryQueryParameters.getLocation() != null &&
                discoveryQueryParameters.getLocation().getLatitude() != 0 &&
                    discoveryQueryParameters.getLocation().getLongitude() != 0){

        }

        if ((discoveryQueryParameters.getMax() != 0) && (discoveryQueryParameters.getOffset() != 0)){

            /*
             * Apply pagination
             */
            if (filteredLis.size() > discoveryQueryParameters.getMax() &&
                    filteredLis.size() > discoveryQueryParameters.getOffset()){
                filteredLis =  filteredLis.subList(discoveryQueryParameters.getOffset(), discoveryQueryParameters.getMax());
            }else if (filteredLis.size() > 100) {
                filteredLis = filteredLis.subList(discoveryQueryParameters.getOffset(), 100);
            }

        }else if (filteredLis.size() > 100) {
            filteredLis = filteredLis.subList(0, 100);
        }

        return filteredLis;

    }

    /**
     * Filter the PlatformComponentProfiles that match with the discoveryQueryParameters that get from other component
     *
     * @param discoveryQueryParameters
     * @param receiveFermatPacket
     * @return List<PlatformComponentProfile>
     */
    private  List<PlatformComponentProfile> applyDiscoveryQueryParametersFromOtherComponent(DiscoveryQueryParameters discoveryQueryParameters, FermatPacket receiveFermatPacket){

        System.out.println("RequestListComponentRegisterPacketProcessor - applyDiscoveryQueryParametersFromOtherComponent    = ");

        List<PlatformComponentProfile>  filteredListFromOtherComponentType = new ArrayList<>();

        /*
         * Get the list from the cache that match with the other componet
         */
        List<PlatformComponentProfile> otherComponentList = searchProfile(discoveryQueryParameters.getFromOtherPlatformComponentType(), discoveryQueryParameters.getFromOtherNetworkServiceType(), discoveryQueryParameters.getIdentityPublicKey());

        System.out.println("RequestListComponentRegisterPacketProcessor - otherComponentList  = "+otherComponentList.size());

        /*
         * Find the other component that match with the identity
         */
        for (PlatformComponentProfile platformComponentProfile: otherComponentList) {

            if (discoveryQueryParameters.getIdentityPublicKey() != null && discoveryQueryParameters.getIdentityPublicKey() != ""){
                List<PlatformComponentProfile>  newList = searchProfileByCommunicationCloudClientIdentity(discoveryQueryParameters.getPlatformComponentType(), discoveryQueryParameters.getNetworkServiceType(), platformComponentProfile.getCommunicationCloudClientIdentity());
                filteredListFromOtherComponentType.addAll(newList);
            }

        }

        /*
         * Remove the requester from the list
         */
        for (PlatformComponentProfile platformComponentProfileRegistered: filteredListFromOtherComponentType) {
            if(platformComponentProfileRegistered.getCommunicationCloudClientIdentity().equals(receiveFermatPacket.getSender())){
                System.out.println("RequestListComponentRegisterPacketProcessor - removing ="+platformComponentProfileRegistered.getName());
                filteredListFromOtherComponentType.remove(platformComponentProfileRegistered);
                break;
            }
        }

        System.out.println("RequestListComponentRegisterPacketProcessor - filteredListFromOtherComponentType  = "+filteredListFromOtherComponentType.size());


        return filteredListFromOtherComponentType;

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
        List<PlatformComponentProfile> temporalList = null;
        List<PlatformComponentProfile>  finalFilteredList = new ArrayList<>();

         /*
         * Switch between platform component type
         */
        switch (platformComponentType){

            case COMMUNICATION_CLOUD_SERVER :
                temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().values());
                break;

            case COMMUNICATION_CLOUD_CLIENT :
                temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().values());
                break;

            case NETWORK_SERVICE :
                temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredNetworkServicesCache().get(networkServiceType));
                break;

            //Others
            default :
                temporalList = getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentType);
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
        List<PlatformComponentProfile> temporalList = null;
        List<PlatformComponentProfile>  finalFilteredList = new ArrayList<>();

         /*
         * Switch between platform component type
         */
        switch (platformComponentType){

            case COMMUNICATION_CLOUD_SERVER :
                temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudServerCache().values());
                break;

            case COMMUNICATION_CLOUD_CLIENT :
                temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredCommunicationsCloudClientCache().values());
                break;

            case NETWORK_SERVICE :
                temporalList = new ArrayList<>(getWsCommunicationCloudServer().getRegisteredNetworkServicesCache().get(networkServiceType));
                break;

            //Others
            default :
                temporalList = getWsCommunicationCloudServer().getRegisteredPlatformComponentProfileCache().get(platformComponentType);
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
     * Count the number of filter to apply
     *
     * @param discoveryQueryParameters
     * @return int
     */
    private int countFilers(DiscoveryQueryParameters discoveryQueryParameters){

        int total = 0;

        if (discoveryQueryParameters.getIdentityPublicKey() != null && discoveryQueryParameters.getIdentityPublicKey() != ""){
            total += 1;
        }

        if (discoveryQueryParameters.getAlias() != null && discoveryQueryParameters.getAlias() != ""){
            total += 1;
        }

        if (discoveryQueryParameters.getName() != null && discoveryQueryParameters.getName() != ""){
            total += 1;
        }

        if (discoveryQueryParameters.getExtraData() != null && discoveryQueryParameters.getExtraData() != ""){
            total += 1;
        }

        return  total;
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
