package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.reponds;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.agents.PropagateNodeCatalogAgent;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.ReceivedNodeCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConstantAttNames;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.reponds.ReceivedNodeCatalogTransactionsRespondProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ReceivedNodeCatalogTransactionsRespondProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ReceivedNodeCatalogTransactionsRespondProcessor.class));

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public ReceivedNodeCatalogTransactionsRespondProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.RECEIVE_NODE_CATALOG_TRANSACTIONS_RESPONSE);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        try {

            ReceivedNodeCatalogTransactionsMsjRespond messageContent = ReceivedNodeCatalogTransactionsMsjRespond.parseContent(packageReceived.getContent());

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){

                if (messageContent.getStatus() == MsgRespond.STATUS.SUCCESS){

                    LOG.info("MsgRespond status "+MsgRespond.STATUS.SUCCESS);

                    if (messageContent.getLateNotificationsCounter() > 0) {

                        LOG.info("(messageContent.getLateNotificationsCounter() = "+messageContent.getLateNotificationsCounter());

                        NodesCatalog remoteNodesCatalog = (NodesCatalog) session.getUserProperties().get(ConstantAttNames.REMOTE_NODE_CATALOG_PROFILE);
                        remoteNodesCatalog.setLateNotificationsCounter(remoteNodesCatalog.getLateNotificationsCounter() + messageContent.getLateNotificationsCounter());
                        getNetworkNodePluginRoot().getPropagateNodeCatalogAgent().setSuccessfulPropagateCount(getNetworkNodePluginRoot().getPropagateActorCatalogAgent().getSuccessfulPropagateCount()+1);
                        getDaoFactory().getNodesCatalogDao().update(remoteNodesCatalog);
                    }

                }else {

                    LOG.info("MsgRespond status "+MsgRespond.STATUS.FAIL);
                    LOG.info("MsgRespond status "+messageContent.getDetails());
                }


                /*
                 * If successful propagation is higher or equals to
                 * the minimum required delete all pending transactions
                 */
                if (getNetworkNodePluginRoot().getPropagateNodeCatalogAgent().getSuccessfulPropagateCount() >= PropagateNodeCatalogAgent.MIN_SUCCESSFUL_PROPAGATION_COUNT) {

                    LOG.info("Deleting all Transactions Pending For Propagation ");
                    getDaoFactory().getNodesCatalogTransactionsPendingForPropagationDao().deleteAll();
                    LOG.info("Total Transactions Pending For Propagation = " + getDaoFactory().getNodesCatalogTransactionsPendingForPropagationDao().getAllCount());
                    getNetworkNodePluginRoot().getPropagateNodeCatalogAgent().setSuccessfulPropagateCount(0);
                }

                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Finish propagation node catalog to this node"));

            }

        } catch (Exception exception){

            try {
                exception.printStackTrace();
                LOG.error(exception.getMessage());
                session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "Can't process respond: "+ exception.getMessage()));

            } catch (IOException iOException) {
                LOG.error(iOException.getMessage());
            }

        }
    }
}
