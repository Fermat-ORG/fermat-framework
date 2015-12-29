/*
* @#CommunicationsNetworkClientChannel.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.PackageDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.PackageEncoder;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.conf.ClientChannelConfigurator;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.CommunicationsNetworkClientChannel</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 27/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

@ClientEndpoint(
        configurator = ClientChannelConfigurator.class,
        encoders = {PackageEncoder.class},
        decoders = {PackageDecoder.class}
)
public class CommunicationsNetworkClientChannel {

    /**
     * Represent if the client is register with the server
     */
    private boolean isRegister;

    /**
     * Represent the Header Before Request
     */
    private ClientChannelConfigurator clientChannelConfigurator;

    /**
     * Represent the clientIdentity
     */
    private ECCKeyPair clientIdentity;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    public CommunicationsNetworkClientChannel(ECCKeyPair clientIdentity,EventManager eventManager){
        this.clientIdentity = clientIdentity;
        this.isRegister = Boolean.FALSE;
        this.eventManager = eventManager;
        this.clientChannelConfigurator=new ClientChannelConfigurator(this.clientIdentity);
    }

    @OnOpen
    public void onOpen(Session session){

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" CommunicationsNetworkClientChannel - Starting method onOpen");


    }

    @OnMessage
    public void onMessage(Package message, Session session){
        System.out.println("New package Received");
        System.out.println("session: " + session.getId() + " package = " + message + "");


    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){

        System.out.println("Closed session : " + session.getId() + " Code: (" + closeReason.getCloseCode() + ") - reason: "+ closeReason.getReasonPhrase());

    }

    /**
     * Get the isActive value
     * @return boolean
     */
    public boolean isRegister() {
        return isRegister;
    }

    /**
     * Set the isActive
     * @param isRegister
     */
    public void setIsRegister(boolean isRegister) {
        this.isRegister = isRegister;
    }

    /**
     * Get the EventManager
     * @return EventManager
     */
    public EventManager getEventManager() {
        return eventManager;
    }


}
