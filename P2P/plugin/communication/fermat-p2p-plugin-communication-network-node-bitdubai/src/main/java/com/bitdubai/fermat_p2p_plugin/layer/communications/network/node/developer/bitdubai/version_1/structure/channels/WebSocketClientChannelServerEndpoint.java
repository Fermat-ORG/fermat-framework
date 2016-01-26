/*
 * @#WSClientChannelEndpoint.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.PackageDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.PackageEncoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.exception.PackageTypeNotSupportedException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf.ClientChannelConfigurator;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInActorRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInClientRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInNetworkServiceRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ClientsConnectionHistory;

import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketClientChannelServerEndpoint</code> this
 * is a especial channel to manage all the communication between the clients and the node
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ServerEndpoint(
        value = "/client-channel",
        configurator = ClientChannelConfigurator.class,
        encoders = {PackageEncoder.class},
        decoders = {PackageDecoder.class}
)
public class WebSocketClientChannelServerEndpoint extends WebSocketChannelServerEndpoint{

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(WebSocketClientChannelServerEndpoint.class.getName());

    /**
     * Represent the clientsSessionMemoryCache instance
     */
    private ClientsSessionMemoryCache clientsSessionMemoryCache;

    /**
     * Constructor
     */
    public WebSocketClientChannelServerEndpoint(){
        super();
        this.clientsSessionMemoryCache = ClientsSessionMemoryCache.getInstance();
    }

    /**
     * (non-javadoc)
     *
     * @see WebSocketChannelServerEndpoint#initPackageProcessorsRegistration()
     */
    @Override
    void initPackageProcessorsRegistration(){

        /*
         * Register all messages processor for this
         * channel
         */
        registerMessageProcessor(new CheckInClientRequestProcessor(this));
        registerMessageProcessor(new CheckInNetworkServiceRequestProcessor(this));
        registerMessageProcessor(new CheckInActorRequestProcessor(this));

    }

    /**
     *  Method called to handle a new connection
     *
     * @param session connected
     * @param endpointConfig created
     * @throws IOException
     */
    @OnOpen
    public void onConnect(Session session, EndpointConfig endpointConfig) throws IOException {

        LOG.info(" New connection stablished: " + session.getId());

        /*
         * Get the node identity
         */
        setChannelIdentity((ECCKeyPair) endpointConfig.getUserProperties().get(HeadersAttName.NPKI_ATT_HEADER_NAME));
        endpointConfig.getUserProperties().remove(HeadersAttName.NPKI_ATT_HEADER_NAME);

        /*
         * Get the client public key identity
         */
        String cpki = (String) endpointConfig.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);

        /*
         * Mach the session whit the client public key identity
         */
        clientsSessionMemoryCache.add(cpki, session);

        /*
         * Create a new ClientsConnectionHistory
         */
        ClientsConnectionHistory clientsConnectionHistory = new ClientsConnectionHistory();
        clientsConnectionHistory.setIdentityPublicKey(cpki);
        clientsConnectionHistory.setStatus(ClientsConnectionHistory.STATUS_SUCCESS);

    }

    /**
     * Method called to handle a new message received
     *
     * @param packageReceived new
     * @param session sender
     */
    @OnMessage
    public void newPackageReceived(Package packageReceived, Session session) {

        LOG.info("New message Received");
        LOG.info("Session: " + session.getId() + " packageReceived = " + packageReceived + "");

        try {

            /*
             * Process the new package received
             */
            processMessage(packageReceived, session);

        }catch (PackageTypeNotSupportedException p){
            LOG.warn(p.getMessage());
        }

    }

    /**
     * Method called to handle a connection close
     *
     * @param closeReason message
     * @param session closed
     */
    @OnClose
    public void onClose(CloseReason closeReason, Session session) {

        LOG.info("Closed session : " + session.getId() + " Code: (" + closeReason.getCloseCode() + ") - reason: "+ closeReason.getReasonPhrase());

    }




}
