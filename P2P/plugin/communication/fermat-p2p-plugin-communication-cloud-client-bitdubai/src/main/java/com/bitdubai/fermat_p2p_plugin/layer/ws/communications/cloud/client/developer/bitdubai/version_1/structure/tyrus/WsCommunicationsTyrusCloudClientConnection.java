/*
 * @#WsCommunicationsCloudClientConnection.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
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
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.ClientSuccessfullyReconnectTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.CompleteComponentConnectionRequestTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.CompleteRegistrationComponentTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.CompleteUpdateActorTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.ComponentConnectionRespondTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.FailureComponentConnectionRequestTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.FailureComponentRegistrationRequestTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.FailureRequestedListNoAvailbleTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.FailureUpdateActorTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.RegisterServerRequestTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.RequestListComponentRegisterTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.ServerHandshakeRespondTyrusPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.vpn.WsCommunicationTyrusVPNClientManagerAgent;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.util.ServerConf;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsCloudClientConnection</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsTyrusCloudClientConnection implements CommunicationsClientConnection {

    /*
     * Represent the WsCommunicationsCloudClientPluginRoot
     */
    private WsCommunicationsCloudClientPluginRoot WsCommunicationsCloudClientPluginRoot;

    /**
     * Represent the WEB_SERVICE_URL
     */
    //private static String WEB_SERVICE_URL = ServerConf.HTTP_PROTOCOL + WsCommunicationsCloudClientPluginRoot.getServerIp() + ":" + ServerConf.WEB_SERVICE_PORT + "/fermat/cloud-server/v1/components/registered/";

    /**
     * Represent the wsCommunicationsTyrusCloudClientChannel
     */
    private WsCommunicationsTyrusCloudClientChannel wsCommunicationsTyrusCloudClientChannel;

    /**
     * Represent the wsCommunicationTyrusVPNClientManagerAgent
     */
    private WsCommunicationTyrusVPNClientManagerAgent wsCommunicationTyrusVPNClientManagerAgent;

    /*
     * Represent The networkServiceType use to reconnecting to main backup Connection AWS
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the webSocketContainer
     */
    private ClientManager webSocketContainer;

    /**
     * Represent the locationManager
     */
    private LocationManager locationManager;

    /**
     * Represent the uri
     */
    private URI uri;

    /**
     * Represent the SERVER_IP
     */
    private String SERVER_IP;

    /**
     * Represent the PORT
     */
    private Integer PORT;

    /*
     * Represent if it must reconnect to cloud server
     */
    private boolean tryToReconnect;

    /**
     * Constructor whit parameters
     *
     * @param uri
     * @param eventManager
     */
    public  WsCommunicationsTyrusCloudClientConnection(URI uri, EventManager eventManager, LocationManager locationManager, ECCKeyPair clientIdentity, WsCommunicationsCloudClientPluginRoot WsCommunicationsCloudClientPluginRoot, String SERVER_IP, Integer PORT,NetworkServiceType networkServiceType) throws IOException, DeploymentException {
        super();
        this.uri = uri;
        this.wsCommunicationsTyrusCloudClientChannel = new WsCommunicationsTyrusCloudClientChannel(this, eventManager, clientIdentity);
        this.wsCommunicationTyrusVPNClientManagerAgent    = WsCommunicationTyrusVPNClientManagerAgent.getInstance();
        this.locationManager                         = locationManager;
        this.webSocketContainer = ClientManager.createClient();
        this.WsCommunicationsCloudClientPluginRoot = WsCommunicationsCloudClientPluginRoot;
        this.tryToReconnect = Boolean.TRUE;
        this.SERVER_IP = SERVER_IP;
        this.PORT = PORT;
        this.networkServiceType = networkServiceType;
    }

    /**
     * Register fermat packet processors whit this communication channel
     */
    private void registerFermatPacketProcessors(){

        /*
         * Clean all
         */
        wsCommunicationsTyrusCloudClientChannel.cleanPacketProcessorsRegistered();

        /*
         * Register the packet processors
         */
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new ServerHandshakeRespondTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new CompleteRegistrationComponentTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new RequestListComponentRegisterTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new ComponentConnectionRespondTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new CompleteComponentConnectionRequestTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new FailureComponentConnectionRequestTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new FailureComponentRegistrationRequestTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new FailureRequestedListNoAvailbleTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new CompleteUpdateActorTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new FailureUpdateActorTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new ClientSuccessfullyReconnectTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));
        wsCommunicationsTyrusCloudClientChannel.registerFermatPacketProcessor(new RegisterServerRequestTyrusPacketProcessor(wsCommunicationsTyrusCloudClientChannel));

    }

    public void setNotTryToReconnectToCloud(){
        tryToReconnect = Boolean.FALSE;
    }

    /**
     * Method that initialize the component and open a new connection
     */
    public void initializeAndConnect() throws IOException, DeploymentException {

        System.out.println("***********************************************************************");
        System.out.println("* WsCommunicationsCloudClientConnection - Initializing And Connecting *");
        System.out.println("***********************************************************************");

        /*
         * Register the processors
         */
        registerFermatPacketProcessors();

        /*
         * Create a ReconnectHandler
         */
        ClientManager.ReconnectHandler reconnectHandler = new ClientManager.ReconnectHandler() {

            int i = 0;

            @Override
            public boolean onDisconnect(CloseReason closeReason) {
                if(networkServiceType == NetworkServiceType.UNDEFINED) {

                    System.out.println("############################################################");
                    System.out.println("#  WsCommunicationsCloudClientConnection - Reconnecting... #");
                    System.out.println("############################################################");

                    return tryToReconnect;
                }else{
                    i++;

                    if(i > 4){
                        try {
                            getWsCommunicationsCloudClientPluginRoot().connectToBackupConnection(networkServiceType);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Boolean.FALSE;
                    }else{
                        return tryToReconnect;
                    }


                }
            }

            @Override
            public boolean onConnectFailure(Exception exception) {
                if(networkServiceType == NetworkServiceType.UNDEFINED) {
                    try {

                        System.out.println("# WsCommunicationsCloudClientConnection - Reconnect Failure Message: " + exception.getMessage() + " Cause: " + exception.getCause());
                        // To avoid potential DDoS when you don't limit number of reconnects, wait to the next try.
                        Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return tryToReconnect;
                }else{

                    i++;

                    if(i > 4){
                        try {
                            getWsCommunicationsCloudClientPluginRoot().connectToBackupConnection(networkServiceType);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Boolean.FALSE;
                    }else{

                        try {

                            System.out.println("# WsCommunicationsCloudClientConnection - Reconnect Failure Message: " + exception.getMessage() + " Cause: " + exception.getCause());
                            // To avoid potential DDoS when you don't limit number of reconnects, wait to the next try.
                            Thread.sleep(5000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return tryToReconnect;

                    }

                }
            }

        };

        /*
         * Register the ReconnectHandler
         */
        webSocketContainer.getProperties().put(ClientProperties.RECONNECT_HANDLER, reconnectHandler);

        /*
         * Connect
         */
        webSocketContainer.connectToServer(wsCommunicationsTyrusCloudClientChannel, uri);
        System.out.println("WsCommunicationsCloudClientConnection - final initializeAndConnect ");

    }


    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#constructPlatformComponentProfileFactory(String, String,String, NetworkServiceType, PlatformComponentType, String)
     */
    @Override
    public PlatformComponentProfile constructPlatformComponentProfileFactory(String identityPublicKey, String alias, String name, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType, String extraData){

        try {

            //Validate parameters
            if ((identityPublicKey == null || identityPublicKey.equals("")) ||
                    (alias == null || alias.equals(""))                     ||
                    (name == null || name.equals(""))                   ||
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
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPublicKey(),
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
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPublicKey(),
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

        System.out.println("WsCommunicationsCloudClientConnection - registerComponentForCommunication");

            /*
             * Validate parameter
             */
        if (platformComponentProfile == null){

            throw new IllegalArgumentException("The platformComponentProfile is required, can not be null");
        }

        try {

            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceNetworkServiceTypeApplicant.toString());
            jsonObject.addProperty(JsonAttNamesConstants.PROFILE_TO_REGISTER, platformComponentProfile.toJson());

             /*
             * Construct a fermat packet whit the PlatformComponentProfile
             */
            FermatPacket fermatPacket = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsTyrusCloudClientChannel.getServerIdentity(),                  //Destination
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                    gson.toJson(jsonObject),                                                 //Message Content
                    FermatPacketType.COMPONENT_REGISTRATION_REQUEST,                         //Packet type
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key


            String fermatPacketEncode = FermatPacketEncoder.encode(fermatPacket);
//ahi, listo
            System.out.println("WsCommunicationsCloudClientConnection - wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen() " + wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen());
            if (isConnected()){
                /*
                 * Send the encode packet to the server
                 */
                wsCommunicationsTyrusCloudClientChannel.sendMessage(fermatPacketEncode);

            }else{
                wsCommunicationsTyrusCloudClientChannel.raiseClientConnectionLooseNotificationEvent();
                throw new Exception("Client Connection is Close");
            }


        }catch (Exception e){
            System.out.println("WsCommunicationsCloudClientConnection: "+e.getStackTrace());
            CantRegisterComponentException pluginStartException = new CantRegisterComponentException(CantRegisterComponentException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), e.getLocalizedMessage());
            throw pluginStartException;

        }

    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#updateRegisterActorProfile(NetworkServiceType, PlatformComponentProfile)
     */
    @Override
    public void updateRegisterActorProfile(NetworkServiceType networkServiceNetworkServiceTypeApplicant, PlatformComponentProfile platformComponentProfile) throws CantRegisterComponentException {


        System.out.println("WsCommunicationsCloudClientConnection - registerComponentForCommunication");

            /*
             * Validate parameter
             */
        if (platformComponentProfile == null){

            throw new IllegalArgumentException("The platformComponentProfile is required, can not be null");
        }

        try {

            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceNetworkServiceTypeApplicant.toString());
            jsonObject.addProperty(JsonAttNamesConstants.PROFILE_TO_UPDATE, platformComponentProfile.toJson());

             /*
             * Construct a fermat packet whit the PlatformComponentProfile
             */
            FermatPacket fermatPacket = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsTyrusCloudClientChannel.getServerIdentity(),                  //Destination
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                    gson.toJson(jsonObject),                                                 //Message Content
                    FermatPacketType.UPDATE_ACTOR_REQUEST,                         //Packet type
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key


            String fermatPacketEncode = FermatPacketEncoder.encode(fermatPacket);

            System.out.println("WsCommunicationsCloudClientConnection - wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen() "+ wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen());
            if (isConnected()){

                /*
                 * Send the encode packet to the server
                 */
                wsCommunicationsTyrusCloudClientChannel.sendMessage(fermatPacketEncode);

            }else{
                wsCommunicationsTyrusCloudClientChannel.raiseClientConnectionLooseNotificationEvent();
                throw new Exception("Client Connection is Close");
            }


        }catch (Exception e){
            System.out.println("WsCommunicationsCloudClientConnection+:" + e.getStackTrace());
            CantRegisterComponentException pluginStartException = new CantRegisterComponentException(CantRegisterComponentException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), e.getLocalizedMessage());
            throw pluginStartException;

        }

    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#requestListComponentRegistered(PlatformComponentProfile, DiscoveryQueryParameters)
     */
    @Override
    public void requestListComponentRegistered(PlatformComponentProfile networkServiceApplicant, DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestListException {

        System.out.println("WsCommunicationsCloudClientConnection - requestListComponentRegistered");

        /*
         * Validate parameter
         */
        if (discoveryQueryParameters == null || networkServiceApplicant == null){

            throw new IllegalArgumentException("The argument are required, can not be null");
        }

        try {

            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceApplicant.getNetworkServiceType().toString());
            jsonObject.addProperty(JsonAttNamesConstants.DISCOVERY_PARAM, discoveryQueryParameters.toJson());

                 /*
                 * Construct a fermat packet whit the filters
                 */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsTyrusCloudClientChannel.getServerIdentity(),                  //Destination
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                    gson.toJson(jsonObject),                                           //Message Content
                    FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED,                      //Packet type
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key

            System.out.println("WsCommunicationsCloudClientConnection - wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen() "+ wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen());
            if (isConnected()){

                /*
                 * Send the encode packet to the server
                 */
                wsCommunicationsTyrusCloudClientChannel.sendMessage(FermatPacketEncoder.encode(fermatPacketRespond));

            }else{
                wsCommunicationsTyrusCloudClientChannel.raiseClientConnectionLooseNotificationEvent();
                throw new Exception("Client Connection is Close");
            }

        }catch (Exception e){
            System.out.println("WsCommunicationsCloudClientConnection: " + e.getStackTrace());
            CantRequestListException pluginStartException = new CantRequestListException(CantRequestListException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), e.getLocalizedMessage());
            throw pluginStartException;

        }

    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#requestListComponentRegistered(DiscoveryQueryParameters)
     */
    public List<PlatformComponentProfile> requestListComponentRegisteredOld(DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestListException {

        System.out.println("WsCommunicationsCloudClientConnection - new requestListComponentRegistered");
        List<PlatformComponentProfile> resultList = new ArrayList<>();

        /*
         * Validate parameter
         */
        if (discoveryQueryParameters == null){
            throw new IllegalArgumentException("The discoveryQueryParameters is required, can not be null");
        }

        try {

            /*
             * Construct a jsonObject whit the parameters
             */
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.NAME_IDENTITY, wsCommunicationsTyrusCloudClientChannel.getIdentityPublicKey());
            jsonObject.addProperty(JsonAttNamesConstants.DISCOVERY_PARAM, discoveryQueryParameters.toJson());

            // Create a new RestTemplate instance
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set("Connection", "Close");
            requestHeaders.setAccept(Collections.singletonList(new org.springframework.http.MediaType("application", "json")));

            HttpEntity<?> requestEntity = new HttpEntity<Object>(jsonObject.toString(), requestHeaders);
            RestTemplate restTemplate = new RestTemplate(true);
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

            ResponseEntity<String> responseEntity = restTemplate.exchange(getWebServiceURL(), HttpMethod.POST, requestEntity, String.class);

            String respond = responseEntity.getBody();
            //System.out.println("responseEntity = " + respond);

            /*
             * if respond have the result list
             */
            if (respond.contains(JsonAttNamesConstants.RESULT_LIST)){

                /*
                 * Decode into a json object
                 */
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(respond.toString());

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
            CantRequestListException cantRequestListException = new CantRequestListException(CantRequestListException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), e.getLocalizedMessage());
            throw cantRequestListException;

        }

        return resultList;
    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#requestListComponentRegistered(DiscoveryQueryParameters)
     */
    @Override
    public List<PlatformComponentProfile> requestListComponentRegistered(DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestListException {

        System.out.println("WsCommunicationsCloudClientConnection - new requestListComponentRegistered");
        List<PlatformComponentProfile> resultList = new ArrayList<>();

        /*
         * Validate parameter
         */
        if (discoveryQueryParameters == null){
            throw new IllegalArgumentException("The discoveryQueryParameters is required, can not be null");
        }

        try {

            /*
             * Construct the parameters
             */
            MultiValueMap<String,Object> parameters = new LinkedMultiValueMap<>();
            parameters.add(JsonAttNamesConstants.NAME_IDENTITY, wsCommunicationsTyrusCloudClientChannel.getIdentityPublicKey());
            parameters.add(JsonAttNamesConstants.DISCOVERY_PARAM, discoveryQueryParameters.toJson());

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set("Connection", "Close");
            requestHeaders.setAccept(Collections.singletonList(new org.springframework.http.MediaType("application", "json")));
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(parameters, requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate(true);
            String respond = restTemplate.postForObject("http://" + getServerIp() + ":" + getServerPort() + "/fermat/api/components/registered", request, String.class);

            /*
             * if respond have the result list
             */
            if (respond.contains(JsonAttNamesConstants.RESULT_LIST)){

                /*
                 * Decode into a json object
                 */
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(respond.toString());

                 /*
                 * Get the receivedList
                 */
                Gson gson = new Gson();
                resultList = gson.fromJson(respondJsonObject.get(JsonAttNamesConstants.RESULT_LIST).getAsString(), new TypeToken<List<PlatformComponentProfileCommunication>>() {
                }.getType());

                System.out.println("WsCommunicationsCloudClientConnection - resultList.size() = " + resultList.size());

            }else {
                System.out.println("WsCommunicationsCloudClientConnection - Requested list is not available, resultList.size() = " + resultList.size());
            }

        }catch (Exception e){
            e.printStackTrace();
            CantRequestListException cantRequestListException = new CantRequestListException(CantRequestListException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), e.getLocalizedMessage());
            throw cantRequestListException;

        }

        return resultList;
    }


    /**
     * Method that request to the communication cloud server the list of component registered that match
     * whit the discovery query params
     *
     * @param discoveryQueryParameters
     * @throws CantRequestListException this exception means the list receive is empty or a internal error
     */
    public List<PlatformComponentProfile> requestListComponentRegisteredSocket(DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestListException{

        System.out.println("WsCommunicationsCloudClientConnection - new requestListComponentRegistered");

        /*
         * Validate parameter
         */
        if (discoveryQueryParameters == null){
            throw new IllegalArgumentException("The discoveryQueryParameters is required, can not be null");
        }

        List<PlatformComponentProfile> resultList = new ArrayList<>();
        Socket clientConnect = null;
        BufferedReader bufferedReader=null;
        PrintWriter printWriter=null;

        try {

            /*
             * Construct a jsonObject whit the parameters
             */
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.NAME_IDENTITY, wsCommunicationsTyrusCloudClientChannel.getIdentityPublicKey());
            jsonObject.addProperty(JsonAttNamesConstants.DISCOVERY_PARAM, discoveryQueryParameters.toJson());

            clientConnect = new Socket(WsCommunicationsCloudClientPluginRoot.getServerIp(),9001);
            bufferedReader = new BufferedReader(new InputStreamReader(clientConnect.getInputStream()));

            printWriter= new PrintWriter(clientConnect.getOutputStream());
            printWriter.println(gson.toJson(jsonObject));
            printWriter.flush();

            String respondServer =  bufferedReader.readLine();

            if(respondServer != null && respondServer != "" && respondServer.contains(JsonAttNamesConstants.RESULT_LIST)){

                 /*
                 * Decode into a json object
                 */
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(respondServer);

                  /*
                 * Get the receivedList
                 */
                resultList = gson.fromJson(respondJsonObject.get(JsonAttNamesConstants.RESULT_LIST).getAsString(), new TypeToken<List<PlatformComponentProfileCommunication>>() {
                }.getType());

                System.out.println("WsCommunicationsCloudClientConnection - resultList.size() = " + resultList.size());

            }

            bufferedReader.close();
            printWriter.close();
            clientConnect.close();

        }catch (Exception e){

            try {

                if (clientConnect != null) {
                    bufferedReader.close();
                    printWriter.close();
                    clientConnect.close();
                }

            }catch (Exception ex){}

            e.printStackTrace();
            CantRequestListException cantRequestListException = new CantRequestListException(CantRequestListException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), e.getLocalizedMessage());
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

        System.out.println("WsCommunicationsCloudClientConnection - requestVpnConnection");

        /*
         * Validate parameter
         */
        if (applicant == null || remoteDestination == null){

            throw new IllegalArgumentException("All parameters are required, can not be null");
        }

        /*
         * Validate are the  type NETWORK_SERVICE
         */
        if (applicant.getIdentityPublicKey().equals(remoteDestination.getIdentityPublicKey())){
            throw new IllegalArgumentException("The applicant and remote can not be the same component");
        }

        try{

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
            String jsonListRepresentation = gson.toJson(participants, new TypeToken<List<PlatformComponentProfileCommunication>>() {
            }.getType());

             /*
             * Construct a fermat packet whit the request
             */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsTyrusCloudClientChannel.getServerIdentity(),                  //Destination
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                    jsonListRepresentation,                                                  //Message Content
                    FermatPacketType.COMPONENT_CONNECTION_REQUEST,                           //Packet type
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key


            System.out.println("WsCommunicationsCloudClientConnection - wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen() " + wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen());
            if (isConnected()){
                /*
                 * Send the encode packet to the server
                 */
                wsCommunicationsTyrusCloudClientChannel.sendMessage(FermatPacketEncoder.encode(fermatPacketRespond));

            }else{
                wsCommunicationsTyrusCloudClientChannel.raiseClientConnectionLooseNotificationEvent();
                throw new Exception("Client Connection is Close");
            }

        }catch (Exception e){
            System.out.println("WsCommunicationsCloudClientConnection: " + e);
            CantEstablishConnectionException pluginStartException = new CantEstablishConnectionException(CantEstablishConnectionException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), e.getLocalizedMessage());
            throw pluginStartException;

        }

    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#requestDiscoveryVpnConnection(PlatformComponentProfile, PlatformComponentProfile, PlatformComponentProfile)
     */
    public void requestDiscoveryVpnConnectionOld(PlatformComponentProfile applicantParticipant, PlatformComponentProfile applicantNetworkService, PlatformComponentProfile remoteParticipant) throws CantEstablishConnectionException{

        System.out.println("WsCommunicationsCloudClientConnection - requestDiscoveryVpnConnection");

            /*
             * Validate parameter
             */
        if (applicantParticipant == null || applicantNetworkService == null || remoteParticipant == null){

            throw new IllegalArgumentException("All parameters are required, can not be null");
        }

        try {

            /*
             * Validate are the  type NETWORK_SERVICE
             */
            if (applicantNetworkService.getPlatformComponentType() != PlatformComponentType.NETWORK_SERVICE){
                throw new IllegalArgumentException("The PlatformComponentProfile of the applicantNetworkService has to be NETWORK_SERVICE ");
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
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsTyrusCloudClientChannel.getServerIdentity(),                  //Destination
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                    packetContentJson,                                                  //Message Content
                    FermatPacketType.DISCOVERY_COMPONENT_CONNECTION_REQUEST,                 //Packet type
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key


            System.out.println("WsCommunicationsCloudClientConnection - wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen() " + wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen());
            if (isConnected()){

                /*
                 * Send the encode packet to the server
                 */
                wsCommunicationsTyrusCloudClientChannel.sendMessage(FermatPacketEncoder.encode(fermatPacketRespond));

            }else{
                wsCommunicationsTyrusCloudClientChannel.raiseClientConnectionLooseNotificationEvent();
                throw new Exception("Client Connection is Close");
            }

        }catch (Exception e){
            System.out.println("WsCommunicationsCloudClientConnection - " + e.getStackTrace());
            CantEstablishConnectionException pluginStartException = new CantEstablishConnectionException(CantEstablishConnectionException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), e.getLocalizedMessage());
            throw pluginStartException;
        }

    }


    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#requestDiscoveryVpnConnection(PlatformComponentProfile, PlatformComponentProfile, PlatformComponentProfile)
     */
    @Override
    public void requestDiscoveryVpnConnection(PlatformComponentProfile applicantParticipant, PlatformComponentProfile applicantNetworkService, PlatformComponentProfile remoteParticipant) throws CantEstablishConnectionException{

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
            throw new IllegalArgumentException("The PlatformComponentProfile of the applicantNetworkService has to be NETWORK_SERVICE ");
        }

        /*
         * Validate are the  type NETWORK_SERVICE
         */
        if (applicantParticipant.getIdentityPublicKey().equals(remoteParticipant.getIdentityPublicKey())){
            throw new IllegalArgumentException("The applicant and remote can not be the same component");
        }

        try {

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
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(wsCommunicationsTyrusCloudClientChannel.getServerIdentity(),                  //Destination
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPublicKey(),   //Sender
                    packetContentJson,                                                  //Message Content
                    FermatPacketType.DISCOVERY_COMPONENT_CONNECTION_REQUEST,                 //Packet type
                    wsCommunicationsTyrusCloudClientChannel.getClientIdentity().getPrivateKey()); //Sender private key


            System.out.println("WsCommunicationsCloudClientConnection - wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen() " + wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen());
            if (isConnected()){

                /*
                 * Send the encode packet to the server
                 */
                wsCommunicationsTyrusCloudClientChannel.sendMessage(FermatPacketEncoder.encode(fermatPacketRespond));

            }else{
                wsCommunicationsTyrusCloudClientChannel.raiseClientConnectionLooseNotificationEvent();
                throw new Exception("Client Connection is Close");
            }

        }catch (Exception e){
            System.out.println("WsCommunicationsCloudClientConnection: "+e.getStackTrace());
            CantEstablishConnectionException pluginStartException = new CantEstablishConnectionException(CantEstablishConnectionException.DEFAULT_MESSAGE, e, e.getLocalizedMessage(), e.getLocalizedMessage());
            throw pluginStartException;
        }

    }


    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#isConnected()
     */
    @Override
    public boolean isConnected(){

        try {

            if (wsCommunicationsTyrusCloudClientChannel.getClientConnection() != null){
                return wsCommunicationsTyrusCloudClientChannel.getClientConnection().isOpen();
            }

        }catch (Exception e){
            return Boolean.FALSE;
        }

        return Boolean.FALSE;
    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#getCommunicationsVPNConnectionStablished(NetworkServiceType, PlatformComponentProfile)
     */
    @Override
    public CommunicationsVPNConnection getCommunicationsVPNConnectionStablished(NetworkServiceType networkServiceType, PlatformComponentProfile remotePlatformComponentProfile) {
        return wsCommunicationTyrusVPNClientManagerAgent.getActiveVpnConnection(networkServiceType, remotePlatformComponentProfile);
    }

    /*
     * Close the main connection when is closing the App
     */
    @Override
    public void closeMainConnection() {


        System.out.println("WsCommunicationsTyrusCloudClientConnection - closeMainConnection()");

        if(isConnected()){

            System.out.println("WsCommunicationsTyrusCloudClientConnection - Calling  - wsCommunicationTyrusVPNClientManagerAgent.closeAllVpnConnections()");
            System.out.println("WsCommunicationsTyrusCloudClientConnection - Calling  - wsCommunicationsTyrusCloudClientChannel.closeConnection(");

            wsCommunicationTyrusVPNClientManagerAgent.closeAllVpnConnections();
            wsCommunicationsTyrusCloudClientChannel.closeConnection();
        }

    }

    /**
     * Get the wsCommunicationsTyrusCloudClientChannel value
     *
     * @return wsCommunicationsTyrusCloudClientChannel current value
     */
    public WsCommunicationsTyrusCloudClientChannel getWsCommunicationsTyrusCloudClientChannel() {
        return wsCommunicationsTyrusCloudClientChannel;
    }

    /**
     * (non-javadoc)
     * @see CommunicationsClientConnection#isRegister()
     */
    public boolean isRegister() {
        return wsCommunicationsTyrusCloudClientChannel.isRegister();
    }

    /*
     * get the WebService URL of the Server Cloud
     */
    private String getWebServiceURL(){
        return ServerConf.HTTP_PROTOCOL + WsCommunicationsCloudClientPluginRoot.getServerIp() + ":" + ServerConf.WEB_SERVICE_PORT + "/fermat/cloud-server/v1/components/registered/";
    }

    /*
     * get the WsCommunicationsCloudClientPluginRoot
     */
    public WsCommunicationsCloudClientPluginRoot getWsCommunicationsCloudClientPluginRoot(){
        return WsCommunicationsCloudClientPluginRoot;
    }


    /*
     * get the Server Ip
     */
    public String getServerIp() {
        return SERVER_IP;
    }

    /*
     * get the Server Port
     */
    public Integer getServerPort(){
        return PORT;
    }

}
