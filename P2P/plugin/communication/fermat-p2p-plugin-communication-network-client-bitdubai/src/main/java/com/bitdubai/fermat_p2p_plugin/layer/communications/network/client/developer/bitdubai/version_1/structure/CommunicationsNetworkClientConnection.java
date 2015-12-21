/*
* @#CommunicationsNetworkClientConnection.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.CommunicationsNetworkClientChannel;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.CommunicationsNetworkClientConnection</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationsNetworkClientConnection extends Thread{

    private URI uri;
    private EventManager eventManager;
    private LocationManager locationManager;
    private ECCKeyPair clientIdentity;
    private WebSocketContainer container;
    private Session session;

    /**
     * Represent the if is Connected
     */
    private boolean isConnected;

    public CommunicationsNetworkClientConnection(URI uri, EventManager eventManager, LocationManager locationManager,ECCKeyPair clientIdentity){
        this.uri = uri;
        this.eventManager = eventManager;
        this.locationManager = locationManager;
        this.clientIdentity = clientIdentity;
        this.isConnected = Boolean.FALSE;


    }

    @Override
    public void run(){

        try{

            container = ContainerProvider.getWebSocketContainer();
            session =  container.connectToServer(CommunicationsNetworkClientChannel.class, uri);

            //validate if is connected
            if(session.isOpen()){
                this.isConnected = Boolean.TRUE;
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

}
