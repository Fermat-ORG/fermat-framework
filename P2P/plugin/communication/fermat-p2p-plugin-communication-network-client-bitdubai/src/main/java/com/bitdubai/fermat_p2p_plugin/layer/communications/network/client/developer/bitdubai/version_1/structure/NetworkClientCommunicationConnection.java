package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.endpoints.CommunicationsNetworkClientChannel;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

import java.net.URI;

import javax.websocket.CloseReason;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientCommunicationConnection extends Thread {

    private URI                uri            ;
    private ErrorManager       errorManager   ;
    private EventManager       eventManager   ;
    private LocationManager    locationManager;
    private ECCKeyPair         clientIdentity ;
    private Session            session        ;

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

    public NetworkClientCommunicationConnection(final URI uri,
                                                final ErrorManager errorManager,
                                                final EventManager eventManager,
                                                final LocationManager locationManager,
                                                final ECCKeyPair clientIdentity){
        this.uri             = uri            ;
        this.errorManager    = errorManager   ;
        this.eventManager    = eventManager   ;
        this.locationManager = locationManager;
        this.clientIdentity  = clientIdentity ;

        this.isConnected     = Boolean.FALSE  ;
        this.tryToReconnect  = Boolean.TRUE   ;

        this.container       = ClientManager.createClient();
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

    public void setNotTryToReconnectToCloud(){
        tryToReconnect = Boolean.FALSE;
    }

    public Session getSession() {
        return session;
    }

    public boolean isConnected() {
        return isConnected;
    }

    private void setCheckInClientRequestProcessor(){

        System.out.println("I'm the client and I'm checking me in.");

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

        CheckInProfileMsgRequest profileCheckInMsgRequest = new CheckInProfileMsgRequest(clientProfile);
        profileCheckInMsgRequest.setMessageContentType(MessageContentType.JSON);

        try {
            session.getBasicRemote().sendObject(Package.createInstance(profileCheckInMsgRequest.toJson(), NetworkServiceType.UNDEFINED, PackageType.CHECK_IN_CLIENT_REQUEST, clientIdentity.getPrivateKey(), serverIdentity));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("I'm the client and I had a big error trying to check me in.");
        }

        System.out.println("I'm the client and I finally the process of checking me in.");
    }

}
