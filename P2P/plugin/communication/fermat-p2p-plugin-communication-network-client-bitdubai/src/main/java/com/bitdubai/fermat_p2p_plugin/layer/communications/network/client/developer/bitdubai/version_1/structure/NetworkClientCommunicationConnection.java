package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantCreateNetworkCallException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUnregisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.ProfileAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.ProfileNotRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileDiscoveryQueryMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckOutProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.NearNodeListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.exceptions.CantSendPackageException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

import java.io.IOException;
import java.net.URI;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/12/15.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientCommunicationConnection extends Thread implements NetworkClientConnection {

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

    public NetworkClientCommunicationConnection(final URI                    uri                   ,
                                                final ErrorManager           errorManager          ,
                                                final EventManager           eventManager          ,
                                                final LocationManager        locationManager       ,
                                                final ECCKeyPair             clientIdentity        ,
                                                final PluginVersionReference pluginVersionReference){

        this.uri                    = uri                   ;
        this.errorManager           = errorManager          ;
        this.eventManager           = eventManager          ;
        this.locationManager        = locationManager       ;
        this.clientIdentity         = clientIdentity        ;
        this.pluginVersionReference = pluginVersionReference;

        this.isConnected            = Boolean.FALSE         ;
        this.tryToReconnect         = Boolean.TRUE          ;

        this.container              = ClientManager.createClient();
    }

    public String getServerIdentity() {
        return serverIdentity;
    }

    @Override
    public void run() {

        /*
         * Create a ReconnectHandler
         */
        ClientManager.ReconnectHandler reconnectHandler = new ClientManager.ReconnectHandler() {

            @Override
            public boolean onDisconnect(CloseReason closeReason) {
                System.out.println("##########################################################################");
                System.out.println("#  NetworkClientCommunicationConnection  - Disconnect -> Reconnecting... #");
                System.out.println("##########################################################################");
                return tryToReconnect;
            }

            @Override
            public boolean onConnectFailure(Exception exception) {
                try {

                    //System.out.println("# WsCommunicationsCloudClientConnection - Reconnect Failure Message: "+exception.getMessage()+" Cause: "+exception.getCause());
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

        };

        /*
         * Register the ReconnectHandler
         */
        container.getProperties().put(ClientProperties.RECONNECT_HANDLER, reconnectHandler);

        try {
            session = container.connectToServer(CommunicationsNetworkClientChannel.class, uri);

            //validate if is connected
            if (session.isOpen()) {
                this.isConnected = Boolean.TRUE;
                serverIdentity = (String) session.getUserProperties().get("");
                setCheckInClientRequestProcessor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNotTryToReconnect(){
        tryToReconnect = Boolean.FALSE;
    }

    public Session getSession() {
        return session;
    }

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public boolean isRegistered() {

        // TODO IMPLEMENT
        return false;
    }

    private void setCheckInClientRequestProcessor(){

        ClientProfile clientProfile = new ClientProfile();
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

    public void getNearbyNodes(final Location location) throws CantRegisterProfileException, ProfileAlreadyRegisteredException {

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
    public void registerProfile(final Profile profile) throws CantRegisterProfileException, ProfileAlreadyRegisteredException {

        CheckInProfileMsgRequest profileCheckInMsgRequest = new CheckInProfileMsgRequest(profile);
        profileCheckInMsgRequest.setMessageContentType(MessageContentType.JSON);

        PackageType packageType;

        if (profile instanceof ActorProfile)
            packageType = PackageType.CHECK_IN_ACTOR_REQUEST;
        else if (profile instanceof ClientProfile)
            packageType = PackageType.CHECK_IN_CLIENT_REQUEST;
        else if (profile instanceof NetworkServiceProfile)
            packageType = PackageType.CHECK_IN_NETWORK_SERVICE_REQUEST;
        else {
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
    public void unregisterProfile(final Profile profile) throws CantUnregisterProfileException, ProfileNotRegisteredException {

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
                                   final NetworkServiceProfile toNetworkService  ) throws CantCreateNetworkCallException {

    }

    @Override
    public void callActor(final ActorProfile          fromActor         ,
                          final ActorProfile          toActor           ,
                          final NetworkServiceProfile fromNetworkService) throws CantCreateNetworkCallException {

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

        try {
            session.getBasicRemote().sendObject(
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
    }

}
