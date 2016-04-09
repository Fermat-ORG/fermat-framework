/*
 * @#ReceivedActorCatalogTransactionsRespondProcessor.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.ReceiveActorCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetNodeCatalogMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.ReceiveActorCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.ReceivedNodeCatalogTransactionsMsjRespond;

import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.ReceivedActorCatalogTransactionsRespondProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 10/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ReceivedActorCatalogTransactionsRespondProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ReceivedActorCatalogTransactionsRespondProcessor.class.getName());

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public ReceivedActorCatalogTransactionsRespondProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.RECEIVE_ACTOR_CATALOG_TRANSACTIONS_REQUEST);
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
        ReceiveActorCatalogTransactionsMsjRespond receiveActorCatalogTransactionsMsjRespond = null;
        Integer lateNotificationsCounter = new Integer(0);

        try {

            ReceiveActorCatalogTransactionsMsjRequest messageContent = (ReceiveActorCatalogTransactionsMsjRequest)  packageReceived.getContent();

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getActorsCatalogTransactions()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){


                /*
                 * If all ok, respond whit success message
                 */
                receiveActorCatalogTransactionsMsjRespond = new ReceiveActorCatalogTransactionsMsjRespond(ReceivedNodeCatalogTransactionsMsjRespond.STATUS.SUCCESS, GetNodeCatalogMsjRespond.STATUS.SUCCESS.toString(), lateNotificationsCounter);
                Package packageRespond = Package.createInstance(receiveActorCatalogTransactionsMsjRespond, packageReceived.getNetworkServiceTypeSource(), PackageType.RECEIVE_ACTOR_CATALOG_TRANSACTIONS_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
                receiveActorCatalogTransactionsMsjRespond = new ReceiveActorCatalogTransactionsMsjRespond(ReceivedNodeCatalogTransactionsMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), lateNotificationsCounter);
                Package packageRespond = Package.createInstance(receiveActorCatalogTransactionsMsjRespond, packageReceived.getNetworkServiceTypeSource(), PackageType.RECEIVE_ACTOR_CATALOG_TRANSACTIONS_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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

}
