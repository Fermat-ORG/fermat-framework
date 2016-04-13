package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.clients;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.exception.PackageTypeNotSupportedException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf.ClientNodeChannelConfigurator;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.ReceivedActorCatalogTransactionsRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.ReceivedNodeCatalogTransactionsRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConstantAttNames;

import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.FermatWebSocketClientNodeChannel.FermatWebSocketClientNodeChannel</code>
 * is the client to communicate nodes by the node client channel<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ClientEndpoint(configurator = ClientNodeChannelConfigurator.class )
public class FermatWebSocketClientNodeChannel extends FermatWebSocketChannelEndpoint {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(FermatWebSocketClientNodeChannel.class.getName());

    /**
     * Represent the clientConnection
     */
    private Session clientConnection;

    /**
     * Constructor with parameter
     *
     * @param remoteNodeCatalogProfile
     */
    public FermatWebSocketClientNodeChannel(NodesCatalog remoteNodeCatalogProfile){

        try {

            URI endpointURI = new URI("ws://"+remoteNodeCatalogProfile.getIp()+":"+remoteNodeCatalogProfile.getDefaultPort());
            WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
            clientConnection = webSocketContainer.connectToServer(this, endpointURI);
            clientConnection.getUserProperties().put(ConstantAttNames.REMOTE_NODE_CATALOG_PROFILE, remoteNodeCatalogProfile);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * (non-javadoc)
     *
     * @see FermatWebSocketChannelEndpoint#initPackageProcessorsRegistration()
     */
    @Override
    protected void initPackageProcessorsRegistration() {

        /*
         * Register all messages processor for this
         * channel
         */
        registerMessageProcessor(new ReceivedNodeCatalogTransactionsRespondProcessor(this));
        registerMessageProcessor(new ReceivedActorCatalogTransactionsRespondProcessor(this));
    }

    /**
     *  Method called to handle a new connection
     *
     * @param session connected
     * @param endpointConfig created
     * @throws IOException
     */
    @OnOpen
    public void onConnect(final Session session, EndpointConfig endpointConfig) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" Starting method onOpen");
        System.out.println(" id = "+session.getId());
        System.out.println(" url = "+session.getRequestURI());
    }

    /**
     * Method called to handle a new packet received
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

    /**
     * Return if the client connection are connected
     *
     * @return boolean
     */
    public boolean isConnected(){

        try {

            if (clientConnection != null){
                return clientConnection.isOpen();
            }

        }catch (Exception e){
            return Boolean.FALSE;
        }

        return Boolean.FALSE;
    }

    /**
     * Send message
     * @param message
     */
    public void sendMessage(String message) {

        String channelIdentityPrivateKey = getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) clientConnection.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);
        Package packageRespond = Package.createInstance(message, NetworkServiceType.UNDEFINED, PackageType.ADD_NODE_TO_CATALOG_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);


        if (isConnected()){
            this.clientConnection.getAsyncRemote().sendObject(packageRespond);
        }

    }

}
