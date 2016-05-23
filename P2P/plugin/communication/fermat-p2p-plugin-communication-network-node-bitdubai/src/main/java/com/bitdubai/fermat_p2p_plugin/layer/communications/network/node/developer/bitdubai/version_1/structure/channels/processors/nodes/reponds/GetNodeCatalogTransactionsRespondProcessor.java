package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.reponds;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetNodeCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetNodeCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.GetNodeCatalogTransactionsRespondProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GetNodeCatalogTransactionsRespondProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(GetNodeCatalogTransactionsRespondProcessor.class));

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public GetNodeCatalogTransactionsRespondProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.GET_NODE_CATALOG_TRANSACTIONS_RESPOND);
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

        try {

            GetNodeCatalogTransactionsMsjRespond messageContent =  GetNodeCatalogTransactionsMsjRespond.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){


                if (messageContent.getStatus() == GetNodeCatalogTransactionsMsjRespond.STATUS.SUCCESS){

                     /*
                     * Get the block of transactions
                     */
                    List<NodesCatalogTransaction>  transactionList = messageContent.getNodesCatalogTransactions();

                    long totalRowInDb = getDaoFactory().getNodesCatalogTransactionDao().getAllCount();

                    LOG.info("Row in node catalog  = "+totalRowInDb);
                    LOG.info("transactionList size = "+transactionList.size());

                    for (NodesCatalogTransaction nodesCatalogTransaction : transactionList) {

                        /*
                         * Process the transaction
                         */
                        processTransaction(nodesCatalogTransaction);
                    }

                    totalRowInDb = getDaoFactory().getNodesCatalogTransactionDao().getAllCount();

                    LOG.info("Row in node catalog  = "+totalRowInDb);
                    LOG.info("Row in catalog seed node = "+messageContent.getCount());

                    if (totalRowInDb < messageContent.getCount()){

                        LOG.info("Requesting more transactions.");

                        GetNodeCatalogTransactionsMsjRequest nodeCatalogTransactionsMsjRequest = new GetNodeCatalogTransactionsMsjRequest((int) totalRowInDb+1, 250);
                        Package packageRespond = Package.createInstance(nodeCatalogTransactionsMsjRequest.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.GET_NODE_CATALOG_TRANSACTIONS_REQUEST, channelIdentityPrivateKey, destinationIdentityPublicKey);

                        /*
                         * Send the respond
                         */
                        session.getAsyncRemote().sendObject(packageRespond);

                    }else {

                        session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Process finish..."));
                    }

                }else {

                    LOG.warn(messageContent.getStatus() + " - " + messageContent.getDetails());
                }

            }

        } catch (Exception exception){

            exception.printStackTrace();
            LOG.error(exception.getMessage());
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, exception.getMessage()));
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }

    }


    /**
     * Process the transaction
     * @param nodesCatalogTransaction
     */
    private void processTransaction(NodesCatalogTransaction nodesCatalogTransaction) throws CantReadRecordDataBaseException, RecordNotFoundException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException, CantDeleteRecordDataBaseException, InvalidParameterException {

        LOG.info("Executing method processTransaction");

        switch (nodesCatalogTransaction.getTransactionType()){

            case NodesCatalogTransaction.ADD_TRANSACTION_TYPE :
                insertNodesCatalog(nodesCatalogTransaction);
                break;

            case NodesCatalogTransaction.UPDATE_TRANSACTION_TYPE :
                updateNodesCatalog(nodesCatalogTransaction);
                break;

            case NodesCatalogTransaction.DELETE_TRANSACTION_TYPE :
                deleteNodesCatalog(nodesCatalogTransaction.getIdentityPublicKey());
                break;
        }

        insertNodesCatalogTransaction(nodesCatalogTransaction);

    }

    /**
     * Create a new row into the data base
     *
     * @param nodesCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertNodesCatalog(NodesCatalogTransaction nodesCatalogTransaction) throws CantInsertRecordDataBaseException, CantReadRecordDataBaseException {

        LOG.info("Executing method insertNodesCatalog");

        if (!getDaoFactory().getNodesCatalogDao().exists(nodesCatalogTransaction.getIdentityPublicKey())){

            /*
             * Create the NodesCatalog
             */
            NodesCatalog nodeCatalog = new NodesCatalog();
            nodeCatalog.setIp(nodesCatalogTransaction.getIp());
            nodeCatalog.setDefaultPort(nodesCatalogTransaction.getDefaultPort());
            nodeCatalog.setIdentityPublicKey(nodesCatalogTransaction.getIdentityPublicKey());
            nodeCatalog.setName(nodesCatalogTransaction.getName());
            nodeCatalog.setOfflineCounter(0);
            nodeCatalog.setLastLatitude(nodesCatalogTransaction.getLastLatitude());
            nodeCatalog.setLastLongitude(nodesCatalogTransaction.getLastLongitude());
            nodeCatalog.setLastConnectionTimestamp(nodesCatalogTransaction.getLastConnectionTimestamp());
            nodeCatalog.setRegisteredTimestamp(nodesCatalogTransaction.getRegisteredTimestamp());

            /*
             * Save into the data base
             */
            getDaoFactory().getNodesCatalogDao().create(nodeCatalog);

        }

    }

    /**
     * Update a row into the data base
     *
     * @param nodesCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void updateNodesCatalog(NodesCatalogTransaction nodesCatalogTransaction) throws CantUpdateRecordDataBaseException, RecordNotFoundException, InvalidParameterException, CantReadRecordDataBaseException, CantInsertRecordDataBaseException {

        LOG.info("Executing method updateNodesCatalog");

        if (getDaoFactory().getNodesCatalogDao().exists(nodesCatalogTransaction.getIdentityPublicKey())){

            /*
             * Create the NodesCatalog
             */
            NodesCatalog nodeCatalog = new NodesCatalog();
            nodeCatalog.setIp(nodesCatalogTransaction.getIp());
            nodeCatalog.setDefaultPort(nodesCatalogTransaction.getDefaultPort());
            nodeCatalog.setIdentityPublicKey(nodesCatalogTransaction.getIdentityPublicKey());
            nodeCatalog.setName(nodesCatalogTransaction.getName());
            nodeCatalog.setOfflineCounter(0);
            nodeCatalog.setLastLatitude(nodesCatalogTransaction.getLastLatitude());
            nodeCatalog.setLastLongitude(nodesCatalogTransaction.getLastLongitude());
            nodeCatalog.setLastConnectionTimestamp(nodesCatalogTransaction.getLastConnectionTimestamp());
            nodeCatalog.setRegisteredTimestamp(nodesCatalogTransaction.getRegisteredTimestamp());

            /*
             * Save into the data base
             */
            getDaoFactory().getNodesCatalogDao().update(nodeCatalog);

        }else {

            insertNodesCatalog(nodesCatalogTransaction);
        }

    }

    /**
     * Delete a row from the data base
     *
     * @param identityPublicKey
     * @throws CantDeleteRecordDataBaseException
     * @throws RecordNotFoundException
     */
    private void deleteNodesCatalog(String identityPublicKey) throws CantDeleteRecordDataBaseException, RecordNotFoundException {

        LOG.info("Executing method deleteNodesCatalog");

        /*
         * Delete from the data base
         */
        getDaoFactory().getNodesCatalogDao().delete(identityPublicKey);
    }

    /**
     * Create a new row into the data base
     *
     * @param nodesCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertNodesCatalogTransaction(NodesCatalogTransaction nodesCatalogTransaction) throws CantInsertRecordDataBaseException, CantReadRecordDataBaseException {

        LOG.info("Executing method insertNodesCatalogTransaction");

        /*
         * Save into the data base
         */
        if (!getDaoFactory().getNodesCatalogTransactionDao().exists(nodesCatalogTransaction.getId()))
            getDaoFactory().getNodesCatalogTransactionDao().create(nodesCatalogTransaction);
    }
}
