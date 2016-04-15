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

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

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
    private WebSocketContainer container      ;
    private Session            session        ;

    private CommunicationsNetworkClientChannel communicationsNetworkClientChannel;

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
    }

    public String getServerIdentity() {
        return serverIdentity;
    }

    @Override
    public void run(){

        try{

            container = ContainerProvider.getWebSocketContainer();
            /*this.communicationsNetworkClientChannel = new CommunicationsNetworkClientChannel(
                    clientIdentity,
                    errorManager  ,
                    eventManager
            );*/

            session   = container.connectToServer(CommunicationsNetworkClientChannel.class, uri);

            //validate if is connected
            if(session.isOpen()){
                this.isConnected = Boolean.TRUE;
                serverIdentity = (String) session.getUserProperties().get("");
                setCheckInClientRequestProcessor();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
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
