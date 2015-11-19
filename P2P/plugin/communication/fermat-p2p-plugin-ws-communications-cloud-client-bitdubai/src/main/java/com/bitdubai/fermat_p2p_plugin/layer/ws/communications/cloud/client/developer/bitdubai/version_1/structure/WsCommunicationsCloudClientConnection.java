/*
 * @#WsCommunicationsCloudClientConnection.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.DiscoveryQueryParametersCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.WsCommunicationsCloudClientPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents.WsCommunicationsCloudClientAgent;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents.WsCommunicationsCloudClientPingAgent;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.CompleteComponentConnectionRequestPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.CompleteRegistrationComponentPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.ComponentConnectionRespondPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.FailureComponentConnectionRequestPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.FailureComponentRegistrationRequestPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.FailureRequestedListNoAvailblePacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.RequestListComponentRegisterPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.ServerHandshakeRespondPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.vpn.WsCommunicationVPNClientManagerAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.java_websocket.drafts.Draft_17;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsCloudClientConnection</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsCloudClientConnection implements CommunicationsClientConnection {

    /**
     * Represent the wsCommunicationsCloudClientChannel
     */
    private WsCommunicationsCloudClientChannel wsCommunicationsCloudClientChannel;

    /**
     * Represent the wsCommunicationVPNClientManagerAgent
     */
    private WsCommunicationVPNClientManagerAgent wsCommunicationVPNClientManagerAgent;

    /**
     * Represent the wsCommunicationsCloudClientAgent
     */
    private WsCommunicationsCloudClientAgent wsCommunicationsCloudClientAgent;

    /**
     * Represent the wsCommunicationsCloudClientPingAgent
     */
    private WsCommunicationsCloudClientPingAgent wsCommunicationsCloudClientPingAgent;

    /**
     * Represent the locationManager
     */
    private LocationManager locationManager;

    /**
     * Represent the client
     */
    private Client client;

    /**
     * Constructor whit parameters
     *
     * @param uri
     * @param eventManager
     */
    public WsCommunicationsCloudClientConnection(URI uri, EventManager eventManager, LocationManager locationManager) {
        super();
        this.wsCommunicationsCloudClientChannel   = WsCommunicationsCloudClientChannel.constructWsCommunicationsCloudClientFactory(uri, new Draft_17(), this, eventManager);
        this.wsCommunicationsCloudClientAgent     = new WsCommunicationsCloudClientAgent(wsCommunicationsCloudClientChannel);
        this.wsCommunicationsCloudClientPingAgent = new WsCommunicationsCloudClientPingAgent(wsCommunicationsCloudClientChannel);
        this.wsCommunicationVPNClientManagerAgent = new WsCommunicationVPNClientManagerAgent();
        this.locationManager                      = locationManager;
        this.client = new Client(new Context(), Protocol.HTTP);
    }

    /**
     * Register fermat packet processors whit this communication channel
     */
    private void registerFermatPacketProcessors(){

        /*
         * Clean all
         */
        wsCommunicationsCloudClientChannel.cleanPacketProcessorsRegistered();

        /*
         * Register the packet processors
         */
        wsCommunicationsCloudClientChannel.registerFermatPacketProcessor(new ServerHandshakeRespondPacketProcessor());
        wsCommunicationsCloudClientChannel.registerFermatPacketProcessor(new CompleteRegistrationComponentPacketProcessor());
        wsCommunicationsCloudClientChannel.registerFermatPacketProcessor(new RequestListComponentRegisterPacketProcessor());
        wsCommunicationsCloudClientChannel.registerFermatPacketProcessor(new ComponentConnectionRespondPacketProcessor());
        wsCommunicationsCloudClientChannel.registerFermatPacketProcessor(new CompleteComponentConnectionRequestPacketProcessor());
        wsCommunicationsCloudClientChannel.registerFermatPacketProcessor(new FailureComponentConnectionRequestPacketProcessor());
        wsCommunicationsCloudClientChannel.registerFermatPacketProcessor(new FailureComponentRegistrationRequestPacketProcessor());
        wsCommunicationsCloudClientChannel.registerFermatPacketProcessor(new FailureRequestedListNoAvailblePacketProcessor());

    }

    /**
     * Method that initialize the component and open a new connection
     */
    public void initializeAndConnect(){

        /*
         * Register the processors
         */
        registerFermatPacketProcessors();

        /*
         * Start the agent to try the connect
         */
        wsCommunicationsCloudClientAgent.start();


        //wsCommunicationsCloudClientPingAgent.start();

    }


    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#constructPlatformComponentProfileFactory(String, String, String, NetworkServiceType, PlatformComponentType, String)
     */
    @Override
    public PlatformComponentProfile constructPlatformComponentProfileFactory(String identityPublicKey, String alias, String name, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType, String extraData){

        try {

            //Validate parameters
            if ((identityPublicKey == null || identityPublicKey == "") ||
                    (alias == null || alias == "")                     ||
                        (name == null || name == "")                   ||
                                    networkServiceType == null         ||
                                        platformComponentType == null  ){

                throw new IllegalArgumentException("All argument are required, can not be null ");

            }

            Location location = null;

            try {

                location = locationManager.getLocation();

            }catch (CantGetDeviceLocationException e){
                System.out.println("WsCommunicationsCloudClientConnection - Error getting the geolocation for this device ");
            }

            /*
             * Construct a PlatformComponentProfile instance
             */
            return new PlatformComponentProfileCommunication(alias,
                                                             wsCommunicationsCloudClientChannel.getClientIdentity().getPublicKey(),
                                                             identityPublicKey,
                                                             location,
                                                             name,
                                                             networkServiceType,
                                                             platformComponentType,
                                                             extraData);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }


    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#constructBasicPlatformComponentProfileFactory(String, NetworkServiceType, PlatformComponentType)
     */
    @Override
    public PlatformComponentProfile constructBasicPlatformComponentProfileFactory(String identityPublicKey, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType){

        try {

            //Validate parameters
            if ((identityPublicKey == null || identityPublicKey == "") ||
                    networkServiceType == null                         ||
                    platformComponentType == null  ){

                throw new IllegalArgumentException("All argument are required, can not be null ");

            }

            return new PlatformComponentProfileCommunication(null,
                                                            wsCommunicationsCloudClientChannel.getClientIdentity().getPublicKey(),
                                                            identityPublicKey,
                                                            null,
                                                            null,
                                                            networkServiceType,
                                                            platformComponentType,
                                                            null);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }


    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType, String, String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer offset, Integer max, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType){

        //Validate parameters
        if (platformComponentType == null && networkServiceType == null){
            throw new IllegalArgumentException("The platformComponentType and networkServiceType argument are required, can not be null ");
        }

        /*
         * Construct a PlatformComponentProfile instance
         */
        return new DiscoveryQueryParametersCommunication(alias, identityPublicKey, location, distance, name, networkServiceType, platformComponentType, extraData, offset, max, fromOtherPlatformComponentType, fromOtherNetworkServiceType);

    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#registerComponentForCommunication(NetworkServiceType, PlatformComponentProfile)
     */
    @Override
    public void registerComponentForCommunication(NetworkServiceType networkServiceNetworkServiceTypeApplicant, PlatformComponentProfile platformComponentProfile) throws CantRegisterComponentException {

        try {

            System.out.println("WsCommunicationsCloudClientConnection - registerComponentForCommunication");

            /*
             * Validate parameter
             */
            if (platformComponentProfile == null){

                throw new IllegalArgumentException("The platformComponentProfile is required, can not be null");
            }

            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceNetworkServiceTypeApplicant.toString());
            jsonObject.addProperty(JsonAttNamesConstants.PROFILE_TO_REGISTER, platformComponentProfile.toJson());

             /*
             * Construct a fermat packet whit the PlatformComponentProfile
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsCloudClientChannel.getServerIdentity(),                  //Destination
                    wsCommunicationsCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                    gson.toJson(jsonObject),                                                 //Message Content
                    FermatPacketType.COMPONENT_REGISTRATION_REQUEST,                         //Packet type
                    wsCommunicationsCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key

            /*
             * Send the encode packet to the server
             */
            wsCommunicationsCloudClientChannel.send(FermatPacketEncoder.encode(fermatPacketRespond));


        }catch (Exception e){

            CantRegisterComponentException pluginStartException = new CantRegisterComponentException(CantRegisterComponentException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), "Connection with server loose");
            throw pluginStartException;

        }

    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#requestListComponentRegistered(PlatformComponentProfile, DiscoveryQueryParameters)
     */
    @Override
    public void requestListComponentRegistered(PlatformComponentProfile networkServiceApplicant, DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestListException {

        try {

                System.out.println("WsCommunicationsCloudClientConnection - requestListComponentRegistered");

                /*
                 * Validate parameter
                 */
                if (discoveryQueryParameters == null){

                    throw new IllegalArgumentException("The discoveryQueryParameters is required, can not be null");
                }

                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceApplicant.getNetworkServiceType().toString());
                jsonObject.addProperty(JsonAttNamesConstants.DISCOVERY_PARAM     , discoveryQueryParameters.toJson());

                 /*
                 * Construct a fermat packet whit the filters
                 */
                FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsCloudClientChannel.getServerIdentity(),                  //Destination
                                                                                                                            wsCommunicationsCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                                                                                                                            gson.toJson(jsonObject),                                           //Message Content
                                                                                                                            FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED,                      //Packet type
                                                                                                                            wsCommunicationsCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key

                /*
                 * Send the encode packet to the server
                 */
                wsCommunicationsCloudClientChannel.send(FermatPacketEncoder.encode(fermatPacketRespond));

        }catch (Exception e){

            CantRequestListException pluginStartException = new CantRequestListException(CantRequestListException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), "Connection with server loose");
            throw pluginStartException;

        }

    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#requestListComponentRegistered(DiscoveryQueryParameters)
     */
    @Override
    public List<PlatformComponentProfile> requestListComponentRegistered(DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestListException {

        System.out.println("WsCommunicationsCloudClientConnection - new requestListComponentRegistered");
        List<PlatformComponentProfile> resultList = new ArrayList<>();

        try {

            /*
             * Validate parameter
             */
            if (discoveryQueryParameters == null){
                throw new IllegalArgumentException("The discoveryQueryParameters is required, can not be null");
            }

            /*
             * Construct a jsonObject whit the parameters
             */
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.NAME_IDENTITY, wsCommunicationsCloudClientChannel.getIdentityPublicKey());
            jsonObject.addProperty(JsonAttNamesConstants.DISCOVERY_PARAM, discoveryQueryParameters.toJson());

            /*
             * Construct the web service client
             */
            ClientResource clientResource = new ClientResource(WsCommunicationsCloudClientPluginRoot.HTTP_PROTOCOL + WsCommunicationsCloudClientPluginRoot.SERVER_IP + ":" + WsCommunicationsCloudClientPluginRoot.WEB_SERVICE_PORT + "/fermat/cloud-server/v1/components/registered/");
            clientResource.setNext(client);
            clientResource.setRequestEntityBuffering(true);
            clientResource.accept(MediaType.APPLICATION_JSON);

            /*
             * Construct the parameters JsonRepresentation
             */
            JsonRepresentation parameters = new JsonRepresentation(gson.toJson(jsonObject));

            /*
             * Do the request via post and obtain the result
             */
            Representation respond = clientResource.post(parameters);

            System.out.println("WsCommunicationsCloudClientConnection - respond:" + respond);

           InputStream in = respond.getStream();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            int index = 0;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);

               for (Character character:line.toCharArray()) {
                    System.out.print(index + " -- character = " + character);
                    System.out.println("");
                    index++;
                }

            }

            System.out.println("WsCommunicationsCloudClientConnection - jsonString length:" + stringBuilder.length());
            System.out.println("WsCommunicationsCloudClientConnection - Respond getSize:" + respond.getSize());
            System.out.println("WsCommunicationsCloudClientConnection - respond.getAvailableSize() :" + respond.getAvailableSize());

            /*
             * if respond have the result list
             */
            if (respond.getSize() > 39){

                /*
                 * Decode into a json object
                 */
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(stringBuilder.toString());

                //JsonObject respondJsonObject = (JsonObject) parser.parse(stringRepresentation.getText());

                 /*
                 * Get the receivedList
                 */
                resultList = gson.fromJson(respondJsonObject.get(JsonAttNamesConstants.RESULT_LIST).getAsString(), new TypeToken<List<PlatformComponentProfileCommunication>>() {
                }.getType());

                System.out.println("WsCommunicationsCloudClientConnection - resultList.size() = " + resultList.size());

            }else {
                System.out.println("WsCommunicationsCloudClientConnection - Requested list is not available, resultList.size() = " + resultList.size());
            }

        }catch (Exception e){
            e.printStackTrace();
            CantRequestListException cantRequestListException = new CantRequestListException(CantRequestListException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), "Connection with server loose");
            throw cantRequestListException;

        }

        return resultList;
    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#requestVpnConnection(PlatformComponentProfile, PlatformComponentProfile)
     */
    @Override
    public void requestVpnConnection(PlatformComponentProfile applicant, PlatformComponentProfile remoteDestination) throws CantEstablishConnectionException {


        try{

                System.out.println("WsCommunicationsCloudClientConnection - requestVpnConnection");

                /*
                 * Validate parameter
                 */
                if (applicant == null || remoteDestination == null){

                    throw new IllegalArgumentException("All parameters are required, can not be null");
                }

                List<PlatformComponentProfile> participants = new ArrayList();
                participants.add(applicant);
                participants.add(remoteDestination);

                /**
                 * Validate all are the same type and NETWORK_SERVICE
                 */
                for (PlatformComponentProfile participant: participants) {

                    if (participant.getPlatformComponentType() != PlatformComponentType.NETWORK_SERVICE){
                        throw new IllegalArgumentException("All the PlatformComponentProfile has to be NETWORK_SERVICE ");
                    }

                    if (participant.getNetworkServiceType() != applicant.getNetworkServiceType()){
                        throw new IllegalArgumentException("All the PlatformComponentProfile has to be the same type of network service type ");
                    }
                }

                /*
                 * Construct the json object
                 */
                Gson gson = new Gson();

                /*
                 * Convert to json representation
                 */
                String jsonListRepresentation = gson.toJson(participants, new TypeToken<List<PlatformComponentProfileCommunication>>() { }.getType());

                 /*
                 * Construct a fermat packet whit the request
                 */
                FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsCloudClientChannel.getServerIdentity(),                  //Destination
                                                                                                                            wsCommunicationsCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                                                                                                                            jsonListRepresentation,                                                  //Message Content
                                                                                                                            FermatPacketType.COMPONENT_CONNECTION_REQUEST,                           //Packet type
                                                                                                                            wsCommunicationsCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key
                /*
                 * Send the encode packet to the server
                 */
                wsCommunicationsCloudClientChannel.send(FermatPacketEncoder.encode(fermatPacketRespond));

        }catch (Exception e){

            CantEstablishConnectionException pluginStartException = new CantEstablishConnectionException(CantEstablishConnectionException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), "Connection with server loose");
            throw pluginStartException;

        }

    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#requestDiscoveryVpnConnection(PlatformComponentProfile, PlatformComponentProfile, PlatformComponentProfile)
     */
    @Override
    public void requestDiscoveryVpnConnection(PlatformComponentProfile applicantParticipant, PlatformComponentProfile applicantNetworkService, PlatformComponentProfile remoteParticipant) throws CantEstablishConnectionException{

        try {

            System.out.println("WsCommunicationsCloudClientConnection - requestDiscoveryVpnConnection");

            /*
             * Validate parameter
             */
            if (applicantParticipant == null || applicantNetworkService == null || remoteParticipant == null){

                throw new IllegalArgumentException("All parameters are required, can not be null");
            }

            /*
             * Validate are the  type NETWORK_SERVICE
             */
            if (applicantNetworkService.getPlatformComponentType() != PlatformComponentType.NETWORK_SERVICE){
                throw new IllegalArgumentException("All the PlatformComponentProfile has to be NETWORK_SERVICE ");
            }

            /*
             * Construct the json object
             */
            Gson gson = new Gson();
            JsonObject packetContent = new JsonObject();
            packetContent.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN, applicantParticipant.toJson());
            packetContent.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_NS_VPN, applicantNetworkService.toJson());
            packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN, remoteParticipant.toJson());

            /*
             * Convert to json representation
             */
            String packetContentJson = gson.toJson(packetContent);

             /*
             * Construct a fermat packet whit the request
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsCloudClientChannel.getServerIdentity(),                  //Destination
                                                                                                                        wsCommunicationsCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                                                                                                                        packetContentJson,                                                  //Message Content
                                                                                                                        FermatPacketType.DISCOVERY_COMPONENT_CONNECTION_REQUEST,                 //Packet type
                                                                                                                        wsCommunicationsCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key
            /*
             * Send the encode packet to the server
             */
            wsCommunicationsCloudClientChannel.send(FermatPacketEncoder.encode(fermatPacketRespond));

        }catch (Exception e){

            CantEstablishConnectionException pluginStartException = new CantEstablishConnectionException(CantEstablishConnectionException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), "Connection with server loose");
            throw pluginStartException;

        }

    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#isConnected()
     */
    @Override
    public boolean isConnected(){
        return wsCommunicationsCloudClientChannel.getConnection().isOpen();
    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#getCommunicationsVPNConnectionStablished(NetworkServiceType, PlatformComponentProfile)
     */
    @Override
    public CommunicationsVPNConnection getCommunicationsVPNConnectionStablished(NetworkServiceType networkServiceType, PlatformComponentProfile remotePlatformComponentProfile) {
        return wsCommunicationVPNClientManagerAgent.getActiveVpnConnection(networkServiceType, remotePlatformComponentProfile);
    }

    /**
     * Get the WsCommunicationsCloudClientChannel
     * @return WsCommunicationsCloudClientChannel
     */
    public WsCommunicationsCloudClientChannel getWsCommunicationsCloudClientChannel() {
        return wsCommunicationsCloudClientChannel;
    }

    /**
     * Get the WsCommunicationVPNClientManagerAgent
     * @return WsCommunicationVPNClientManagerAgent
     */
    public WsCommunicationVPNClientManagerAgent getWsCommunicationVPNClientManagerAgent() {
        return wsCommunicationVPNClientManagerAgent;
    }


    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#isRegister()
     */
    public boolean isRegister() {
        return wsCommunicationsCloudClientChannel.isRegister();
    }
}
