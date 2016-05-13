package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionLostEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantCreateNetworkCallException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUnregisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkCallChannel;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorCallRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileDiscoveryQueryMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckOutProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.NearNodeListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.NetworkServiceCallRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.NetworkClientCommunicationPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.exceptions.CantSendPackageException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 14/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientCommunicationConnection implements NetworkClientConnection {

    private URI                    uri                   ;
    private ErrorManager           errorManager          ;
    private EventManager           eventManager          ;
    private LocationManager        locationManager       ;
    private ECCKeyPair             clientIdentity        ;
    private PluginVersionReference pluginVersionReference;
    private Session                session               ;

    /**
     * Represent if it must reconnect to the server
     */
    private boolean tryToReconnect;

    /**
     * Represent the webSocketContainer
     */
    private ClientManager container;

    /**
     * Represent the if is Connected
     */
    private boolean isConnected;

    /**
     * Represent the serverIdentity
     */
    private String serverIdentity;

    /*
     * Represent the networkClientCommunicationPluginRoot
     */
    private NetworkClientCommunicationPluginRoot networkClientCommunicationPluginRoot;

    /*
     * Represent the nodesListPosition
     */
    private Integer nodesListPosition;

    /*
     * Represent the communicationsNetworkClientChannel
     */
    private CommunicationsNetworkClientChannel communicationsNetworkClientChannel;

    /*
     * Represent the clientProfile
     */
    private ClientProfile clientProfile;

    /*
     * Constructor
     */
    public NetworkClientCommunicationConnection(final URI                    uri                   ,
                                                final ErrorManager           errorManager          ,
                                                final EventManager           eventManager          ,
                                                final LocationManager        locationManager       ,
                                                final ECCKeyPair             clientIdentity        ,
                                                final PluginVersionReference pluginVersionReference,
                                                NetworkClientCommunicationPluginRoot networkClientCommunicationPluginRoot,
                                                Integer nodesListPosition){

        this.uri                    = uri                   ;
        this.errorManager           = errorManager          ;
        this.eventManager           = eventManager          ;
        this.locationManager        = locationManager       ;
        this.clientIdentity         = clientIdentity        ;
        this.pluginVersionReference = pluginVersionReference;
        this.networkClientCommunicationPluginRoot = networkClientCommunicationPluginRoot;
        this.nodesListPosition = nodesListPosition;
        this.communicationsNetworkClientChannel = new CommunicationsNetworkClientChannel(this);

        this.isConnected            = Boolean.FALSE         ;
        this.tryToReconnect         = Boolean.TRUE          ;

        this.container              = ClientManager.createClient();
    }

    /*
     * initialize And Connect to Network Node
     */
    public void initializeAndConnect() {

        System.out.println("*****************************************************************");
        System.out.println("Connecting To Server: " + uri);
        System.out.println("*****************************************************************");

        /*
         * Create a ReconnectHandler
         */
        ClientManager.ReconnectHandler reconnectHandler = new ClientManager.ReconnectHandler() {

            int i = 0;

            @Override
            public boolean onDisconnect(CloseReason closeReason) {
                if(nodesListPosition >= 0){
                    i++;

                    if(i > 4){

                        networkClientCommunicationPluginRoot.intentToConnectToOtherNode(nodesListPosition);
                        return Boolean.FALSE;

                    }else{
                        return tryToReconnect;
                    }

                }else {
                    System.out.println("##########################################################################");
                    System.out.println("#  NetworkClientCommunicationConnection  - Disconnect -> Reconnecting... #");
                    System.out.println("##########################################################################");
                    return tryToReconnect;
                }
            }

            @Override
            public boolean onConnectFailure(Exception exception) {
                if(nodesListPosition >= 0){
                    i++;

                    if(i > 4){

                        networkClientCommunicationPluginRoot.intentToConnectToOtherNode(nodesListPosition);
                        return Boolean.FALSE;

                    }else{

                        try {

                            //System.out.println("# NetworkClientCommunicationConnection - Reconnect Failure Message: "+exception.getMessage()+" Cause: "+exception.getCause());
                            // To avoid potential DDoS when you don't limit number of reconnects, wait to the next try.
                            Thread.sleep(5000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        return tryToReconnect;
                    }

                }else {
                    try {

                        //System.out.println("# NetworkClientCommunicationConnection - Reconnect Failure Message: "+exception.getMessage()+" Cause: "+exception.getCause());
                        // To avoid potential DDoS when you don't limit number of reconnects, wait to the next try.
                        Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("###############################################################################");
                    System.out.println("#  NetworkClientCommunicationConnection  - Connect Failure -> Reconnecting... #");
                    System.out.println("###############################################################################");
                    return tryToReconnect;
                }
            }

        };

        /*
         * Register the ReconnectHandler
         */
        container.getProperties().put(ClientProperties.RECONNECT_HANDLER, reconnectHandler);

        try {

            // es malo usar la session de una vez ya que es asincrono la conexion
            // y da exception
            session = container.connectToServer(communicationsNetworkClientChannel, uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {

        try {
            if (communicationsNetworkClientChannel.getClientConnection() != null)
                return communicationsNetworkClientChannel.getClientConnection().isOpen();
        }catch (Exception e){
            return Boolean.FALSE;
        }

        return Boolean.FALSE;
    }

    @Override
    public boolean isRegistered() {

        return communicationsNetworkClientChannel.isRegistered();
    }

    /*
     * CheckIn Client Request to Network Node
     */
    public void setCheckInClientRequestProcessor(){

        clientProfile = new ClientProfile();
        clientProfile.setIdentityPublicKey(clientIdentity.getPublicKey());
        clientProfile.setDeviceType("");

        try {
            if(locationManager.getLocation() != null){
              clientProfile.setLocation(locationManager.getLocation());
            }
        } catch (CantGetDeviceLocationException e) {
            e.printStackTrace();
        }

        try {
            registerProfile(clientProfile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getNearbyNodes(final Location location) throws CantRegisterProfileException {

        NearNodeListMsgRequest nearNodeListMsgRequest = new NearNodeListMsgRequest(location);
        nearNodeListMsgRequest.setMessageContentType(MessageContentType.JSON);

        try {

            sendPackage(nearNodeListMsgRequest, PackageType.NEAR_NODE_LIST_REQUEST);

        } catch (CantSendPackageException cantSendPackageException) {

            CantRegisterProfileException fermatException = new CantRegisterProfileException(
                    cantSendPackageException,
                    "location:" + location,
                    "Cant send package."
            );

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }
    }

    @Override
    public void registerProfile(final Profile profile) throws CantRegisterProfileException {

        CheckInProfileMsgRequest profileCheckInMsgRequest = new CheckInProfileMsgRequest(profile);
        profileCheckInMsgRequest.setMessageContentType(MessageContentType.JSON);

        PackageType packageType;

        if (profile instanceof ActorProfile)
            packageType = PackageType.CHECK_IN_ACTOR_REQUEST;
        else if (profile instanceof ClientProfile)
            packageType = PackageType.CHECK_IN_CLIENT_REQUEST;
        else if (profile instanceof NetworkServiceProfile) {
            packageType = PackageType.CHECK_IN_NETWORK_SERVICE_REQUEST;
            ((NetworkServiceProfile) profile).setClientIdentityPublicKey(clientIdentity.getPublicKey());
        } else {
            CantRegisterProfileException fermatException = new CantRegisterProfileException(
                    "profile:" + profile,
                    "Unsupported profile type."
            );

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }

        try {

            sendPackage(profileCheckInMsgRequest, packageType);

        } catch (CantSendPackageException cantSendPackageException) {

            CantRegisterProfileException fermatException = new CantRegisterProfileException(
                    cantSendPackageException,
                    "profile:" + profile,
                    "Cant send package."
            );

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }
    }

    @Override
    public void unregisterProfile(final Profile profile) throws CantUnregisterProfileException {

        CheckOutProfileMsgRequest checkOutProfileMsgRequest = new CheckOutProfileMsgRequest(profile);
        checkOutProfileMsgRequest.setMessageContentType(MessageContentType.JSON);

        PackageType packageType;

        if (profile instanceof ActorProfile)
            packageType = PackageType.CHECK_OUT_ACTOR_REQUEST;
        else if (profile instanceof ClientProfile)
            packageType = PackageType.CHECK_OUT_CLIENT_REQUEST;
        else if (profile instanceof NetworkServiceProfile)
            packageType = PackageType.CHECK_OUT_NETWORK_SERVICE_REQUEST;
        else {
            CantUnregisterProfileException fermatException = new CantUnregisterProfileException(
                    "profile:" + profile,
                    "Unsupported profile type."
            );

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }


        try {

            sendPackage(checkOutProfileMsgRequest, packageType);

        } catch (CantSendPackageException cantSendPackageException) {

            CantUnregisterProfileException fermatException = new CantUnregisterProfileException(
                    cantSendPackageException,
                    "profile:" + profile,
                    "Cant send package."
            );

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }
    }

    @Override
    public void callNetworkService(final NetworkServiceProfile fromNetworkService,
                                   final NetworkServiceProfile toNetworkService  ) {

        System.out.println("NetworkClientCommunicationConnection - requestNetworkCall");

        /*
         * Validate parameter
         */
        if (fromNetworkService == null || toNetworkService == null)
            throw new IllegalArgumentException("All parameters are required, can not be null");

        if (fromNetworkService.getIdentityPublicKey().equals(toNetworkService.getIdentityPublicKey()))
            throw new IllegalArgumentException("The fromNetworkService and toNetworkService can not be the same component");

        try{

            List<NetworkServiceProfile> participants = new ArrayList<>();
            participants.add(fromNetworkService);
            participants.add(toNetworkService);

            /**
             * Validate all are the same type and NETWORK_SERVICE
             */
            for (NetworkServiceProfile participant: participants) {

                if (participant.getNetworkServiceType() != fromNetworkService.getNetworkServiceType()){
                    throw new IllegalArgumentException("All the Profile has to be the same type of network service type ");
                }
            }

            sendPackage(
                    new NetworkServiceCallRequest(
                            fromNetworkService,
                            toNetworkService
                    ),
                    PackageType.NETWORK_SERVICE_CALL_REQUEST
            );

        } catch (Exception e){
            System.out.println("NetworkClientCommunicationConnection: " + e);
            CantCreateNetworkCallException cantCreateNetworkCallException = new CantCreateNetworkCallException(e, e.getLocalizedMessage(), e.getLocalizedMessage());
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateNetworkCallException);
        }
    }

    @Override
    public void callActor(final ActorProfile          fromActor         ,
                          final ActorProfile          toActor           ,
                          final NetworkServiceProfile fromNetworkService) {

        System.out.println("NetworkClientCommunicationConnection - requestNetworkCall");

        /*
         * Validate parameter
         */
        if (fromActor == null || fromNetworkService == null || toActor == null)
            throw new IllegalArgumentException("All parameters are required, can not be null");

        /*
         * Validate are the  type NETWORK_SERVICE
         */
        if (fromActor.getIdentityPublicKey().equals(toActor.getIdentityPublicKey())){
            throw new IllegalArgumentException("The fromActor and toActor can not be the same component");
        }

        try {

            sendPackage(
                    new ActorCallRequest(
                            fromActor,
                            fromNetworkService,
                            toActor
                    ),
                    PackageType.ACTOR_CALL_REQUEST
            );

        } catch (Exception e){
            CantCreateNetworkCallException cantCreateNetworkCallException = new CantCreateNetworkCallException(e, e.getLocalizedMessage(), e.getLocalizedMessage());
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateNetworkCallException);
        }

    }

    @Override
    public NetworkCallChannel getCallChannel(Profile from, Profile to) {
        return null;
    }

    @Override
    public void registeredProfileDiscoveryQuery(final DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestProfileListException {

        CheckInProfileDiscoveryQueryMsgRequest checkInProfileDiscoveryQueryMsgRequest = new CheckInProfileDiscoveryQueryMsgRequest(discoveryQueryParameters);
        checkInProfileDiscoveryQueryMsgRequest.setMessageContentType(MessageContentType.JSON);

        try {

            sendPackage(checkInProfileDiscoveryQueryMsgRequest, PackageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_REQUEST);

        } catch (CantSendPackageException cantSendPackageException) {

            CantRequestProfileListException fermatException = new CantRequestProfileListException(
                    cantSendPackageException,
                    "discoveryQueryParameters:" + discoveryQueryParameters,
                    "Cant send package."
            );

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }
    }

    @Override
    public void actorTraceDiscoveryQuery(final DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestProfileListException {

        CheckInProfileDiscoveryQueryMsgRequest checkInProfileDiscoveryQueryMsgRequest = new CheckInProfileDiscoveryQueryMsgRequest(discoveryQueryParameters);
        checkInProfileDiscoveryQueryMsgRequest.setMessageContentType(MessageContentType.JSON);

        try {

            sendPackage(checkInProfileDiscoveryQueryMsgRequest, PackageType.ACTOR_TRACE_DISCOVERY_QUERY_REQUEST);

        } catch (CantSendPackageException cantSendPackageException) {

            CantRequestProfileListException fermatException = new CantRequestProfileListException(
                    cantSendPackageException,
                    "discoveryQueryParameters:" + discoveryQueryParameters,
                    "Cant send package."
            );

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    fermatException
            );

            throw fermatException;
        }
    }

    private void sendPackage(final PackageContent packageContent,
                             final PackageType    packageType   ) throws CantSendPackageException {

        if (isConnected()){

            try {
                communicationsNetworkClientChannel.getClientConnection().getBasicRemote().sendObject(
                        Package.createInstance(
                                packageContent.toJson(),
                                NetworkServiceType.UNDEFINED,
                                packageType,
                                clientIdentity.getPrivateKey(),
                                serverIdentity
                        )
                );
            } catch (IOException | EncodeException exception){


                throw new CantSendPackageException(
                        exception,
                        "packageContent:"+packageContent,
                        "Error trying to send the message through the session."
                );

            } catch (Exception exception) {

                throw new CantSendPackageException(
                        exception,
                        "packageContent:"+packageContent,
                        "Unhandled error trying to send the message through the session."
                );
            }

        } else {

            raiseClientConnectionLostNotificationEvent();

            throw new CantSendPackageException(
                    "packageContent: "+packageContent+" - packageType: "+packageType,
                    "Client Connection is Closed."
            );
        }

    }

    /**
     * Notify when the network client connection is lost.
     */
    public void raiseClientConnectionLostNotificationEvent() {

        System.out.println("CommunicationsNetworkClientConnection - raiseClientConnectionLostNotificationEvent");
        FermatEvent platformEvent = eventManager.getNewEvent(P2pEventType.NETWORK_CLIENT_CONNECTION_LOST);
        platformEvent.setSource(EventSource.NETWORK_CLIENT);
        ((NetworkClientConnectionLostEvent) platformEvent).setCommunicationChannel(CommunicationChannels.P2P_SERVERS);
        eventManager.raiseEvent(platformEvent);
        System.out.println("CommunicationsNetworkClientConnection - Raised Event = P2pEventType.NETWORK_CLIENT_CONNECTION_LOST");
    }

    @Override
    public CommunicationChannels getCommunicationChannelType() {

        return CommunicationChannels.P2P_SERVERS;
    }

    /*
         * set nodesListPosition to -1 when the client is checkIn to avoid connecting to other node if this fails
         */
    public void setNodesListPosition() {
        this.nodesListPosition = -1;
    }

    public void setServerIdentity(String serverIdentity) {
        this.serverIdentity = serverIdentity;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public String getServerIdentity() {
        return serverIdentity;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    public URI getUri() {
        return uri;
    }

    public CommunicationsNetworkClientChannel getCommunicationsNetworkClientChannel() {
        return communicationsNetworkClientChannel;
    }
}
