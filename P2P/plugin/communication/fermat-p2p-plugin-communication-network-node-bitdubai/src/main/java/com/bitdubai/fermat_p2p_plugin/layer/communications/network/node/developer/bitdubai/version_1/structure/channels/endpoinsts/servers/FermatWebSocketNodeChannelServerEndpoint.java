/*
 * @#WebSocketNodeChannelServerEndpoint.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.exception.PackageTypeNotSupportedException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.NodeSessionMemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.conf.NodeChannelConfigurator;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.AddNodeToCatalogProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.GetActorCatalogTransactionsProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.GetNodeCatalogProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.GetNodeCatalogTransactionsProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.ReceivedActorCatalogTransactionsProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.ReceivedNodeCatalogTransactionsProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.UpdateNodeInCatalogProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ClientsConnectionHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodeConnectionHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.PackageDecoder;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.PackageEncoder;

import org.apache.commons.lang.ClassUtils;
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
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers.FermatWebSocketNodeChannelServerEndpoint</code>
 * represent the the communication chanel between nodes<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ServerEndpoint(
        value ="/node-channel",
        configurator = NodeChannelConfigurator.class,
        encoders = {PackageEncoder.class},
        decoders = {PackageDecoder.class}
)
public class FermatWebSocketNodeChannelServerEndpoint extends FermatWebSocketChannelEndpoint {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(FermatWebSocketNodeChannelServerEndpoint.class));

    /**
     * Represent the nodeSessionMemoryCache
     */
    private NodeSessionMemoryCache nodeSessionMemoryCache;

    /**
     * Constructor
     */
    public FermatWebSocketNodeChannelServerEndpoint(){
        super();
        this.nodeSessionMemoryCache = NodeSessionMemoryCache.getInstance();
    }

    /**
     * (non-javadoc)
     *
     * @see FermatWebSocketChannelEndpoint#initPackageProcessorsRegistration()
     */
    @Override
    protected void initPackageProcessorsRegistration(){

        /*
         * Register all messages processor for this
         * channel
         */
        registerMessageProcessor(new AddNodeToCatalogProcessor(this));
        registerMessageProcessor(new GetNodeCatalogProcessor(this));
        registerMessageProcessor(new GetNodeCatalogTransactionsProcessor(this));
        registerMessageProcessor(new GetActorCatalogTransactionsProcessor(this));
        registerMessageProcessor(new ReceivedActorCatalogTransactionsProcessor(this));
        registerMessageProcessor(new ReceivedNodeCatalogTransactionsProcessor(this));
        registerMessageProcessor(new UpdateNodeInCatalogProcessor(this));

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
        LOG.info(" RNPKI: " + endpointConfig.getUserProperties().get(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME));

        if (endpointConfig.getUserProperties().containsKey(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME)){

            /*
             * Get the node public key identity
             */
            String npki = (String) endpointConfig.getUserProperties().remove(HeadersAttName.REMOTE_NPKI_ATT_HEADER_NAME);
            session.getUserProperties().put(HeadersAttName.CPKI_ATT_HEADER_NAME,npki);
            session.setMaxIdleTimeout(FermatWebSocketChannelEndpoint.MAX_IDLE_TIMEOUT);
            session.setMaxTextMessageBufferSize(FermatWebSocketChannelEndpoint.MAX_MESSAGE_SIZE);

            /*
             * Mach the session whit the node public key identity
             */
            nodeSessionMemoryCache.add(npki, session);

            /*
             * Create a new NodeConnectionHistory
             */
            NodeConnectionHistory nodeConnectionHistory = new NodeConnectionHistory();
            nodeConnectionHistory.setIdentityPublicKey(npki);
            nodeConnectionHistory.setStatus(ClientsConnectionHistory.STATUS_SUCCESS);

        } else {

            LOG.warn("Temporal Node identity public key identity no provide ...");
            session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "Temporal Remote Node identity public key identity no provide "));
        }

    }

    /**
     *  Method called to handle all packet received
     * @param packageReceived
     * @param session
     */
    @OnMessage
    public void newPackageReceived(Package packageReceived, Session session) throws IOException {

        LOG.info("New package received ("+packageReceived.getPackageType().name()+")");
        LOG.info("Session: " + session.getId());

        try {

            /*
             * Process the new package received
             */
            processMessage(packageReceived, session);

        }catch (PackageTypeNotSupportedException p){
            LOG.warn(p.getMessage());
            session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, p.getMessage()));
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

        LOG.info("Closed connection: " + session.getId() + " -- (" + closeReason.getReasonPhrase() + ")");

    }

}
