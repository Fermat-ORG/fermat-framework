/*
 * @#GetNodeCatalogTransacctionsProcessor.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers.WebSocketChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetNodeCatalogMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetNodeCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetNodeCatalogMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetNodeCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransaction;

import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.GetNodeCatalogTransactionsProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GetNodeCatalogTransactionsProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(GetNodeCatalogTransactionsProcessor.class.getName());

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public GetNodeCatalogTransactionsProcessor(WebSocketChannelServerEndpoint channel) {
        super(channel, PackageType.GET_NODE_CATALOG_TRANSACTIONS_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);
        GetNodeCatalogTransactionsMsjRespond getNodeCatalogTransactionsMsjRespond = null;
        List<NodesCatalogTransaction> nodesCatalogTransactionList = null;

        try {

            GetNodeCatalogTransactionsMsjRequest messageContent = (GetNodeCatalogTransactionsMsjRequest)  packageReceived.getContent();

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){


                nodesCatalogTransactionList = loadData(messageContent.getOffset(), messageContent.getMax());

                //TODO: CREATE A METHOD COUNT ROW IN DATA BASE
                Integer count = 0;

                /*
                 * If all ok, respond whit success message
                 */
                getNodeCatalogTransactionsMsjRespond = new GetNodeCatalogTransactionsMsjRespond(GetNodeCatalogTransactionsMsjRespond.STATUS.SUCCESS, GetNodeCatalogTransactionsMsjRespond.STATUS.SUCCESS.toString(), nodesCatalogTransactionList, count);
                Package packageRespond = Package.createInstance(getNodeCatalogTransactionsMsjRespond, packageReceived.getNetworkServiceTypeSource(), PackageType.GET_NODE_CATALOG_TRANSACTIONS_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getBasicRemote().sendObject(packageRespond);

            }


        } catch (Exception exception){

            try {

                LOG.error(exception.getMessage());

                /*
                 * Respond whit fail message
                 */
                getNodeCatalogTransactionsMsjRespond = new GetNodeCatalogTransactionsMsjRespond(GetNodeCatalogTransactionsMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), nodesCatalogTransactionList, 0);
                Package packageRespond = Package.createInstance(getNodeCatalogTransactionsMsjRespond, packageReceived.getNetworkServiceTypeSource(), PackageType.GET_NODE_CATALOG_TRANSACTIONS_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getBasicRemote().sendObject(packageRespond);

            } catch (IOException iOException) {
                LOG.error(iOException.getMessage());
            } catch (EncodeException encodeException) {
                LOG.error(encodeException.getMessage());
            }

        }

    }

    /**
     * Load the data from database
     *
     * @param offset
     * @param max
     * @return List<NodeProfile>
     */
    public List<NodesCatalogTransaction> loadData(Integer offset, Integer max) throws CantReadRecordDataBaseException {

        List<NodesCatalogTransaction> nodesCatalogTransactionList = null;

        if (offset > 0 && max > 0){

            //TODO: CREATE A METHOD WITH THE PAGINATION
            nodesCatalogTransactionList = getDaoFactory().getNodesCatalogTransactionDao().findAll();

        }else {

            nodesCatalogTransactionList = getDaoFactory().getNodesCatalogTransactionDao().findAll();

        }

        return nodesCatalogTransactionList;

    }
}
