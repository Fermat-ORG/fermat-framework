package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.reponds;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetNodeCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetNodeCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;

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
 * @since   Java JDK 1.7
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
        super(channel, PackageType.GET_NODE_CATALOG_TRANSACTIONS_RESPONSE);
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
    private synchronized void processTransaction(NodesCatalogTransaction nodesCatalogTransaction) throws CantCreateTransactionStatementPairException, DatabaseTransactionFailedException, CantReadRecordDataBaseException {

        LOG.info("Executing method processTransaction");

        if (!getDaoFactory().getNodesCatalogTransactionDao().exists(nodesCatalogTransaction.getId())) {

            // create transaction for
            DatabaseTransaction databaseTransaction = getDaoFactory().getNodesCatalogDao().getNewTransaction();
            DatabaseTransactionStatementPair pair;

            switch (nodesCatalogTransaction.getTransactionType()) {

                case NodesCatalogTransaction.ADD_TRANSACTION_TYPE:
                    pair = insertNodesCatalog(nodesCatalogTransaction);
                    databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());
                    break;

                case NodesCatalogTransaction.UPDATE_TRANSACTION_TYPE:
                    pair = updateNodesCatalog(nodesCatalogTransaction);
                    databaseTransaction.addRecordToUpdate(pair.getTable(), pair.getRecord());
                    break;

                case NodesCatalogTransaction.DELETE_TRANSACTION_TYPE:
                    pair = deleteNodesCatalog(nodesCatalogTransaction.getIdentityPublicKey());
                    databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());
                    break;
            }

            pair = insertNodesCatalogTransaction(nodesCatalogTransaction);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            databaseTransaction.execute();
        }
    }

    /**
     * Create a new row into the data base
     *
     * @param nodesCatalogTransaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair insertNodesCatalog(NodesCatalogTransaction nodesCatalogTransaction) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method insertNodesCatalog");

        /*
         * Create the NodesCatalog
         */
        NodesCatalog nodeCatalog = new NodesCatalog();
        nodeCatalog.setIp(nodesCatalogTransaction.getIp());
        nodeCatalog.setDefaultPort(nodesCatalogTransaction.getDefaultPort());
        nodeCatalog.setIdentityPublicKey(nodesCatalogTransaction.getIdentityPublicKey());
        nodeCatalog.setOfflineCounter(0);
        nodeCatalog.setName(nodesCatalogTransaction.getName());
        nodeCatalog.setLastLocation(nodesCatalogTransaction.getLastLocation());
        nodeCatalog.setLastConnectionTimestamp(nodesCatalogTransaction.getLastConnectionTimestamp());
        nodeCatalog.setRegisteredTimestamp(nodesCatalogTransaction.getRegisteredTimestamp());

        /*
         * Save into the data base
         */
        return getDaoFactory().getNodesCatalogDao().createInsertTransactionStatementPair(nodeCatalog);

    }

    /**
     * Update a row into the data base
     *
     * @param nodesCatalogTransaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair updateNodesCatalog(NodesCatalogTransaction nodesCatalogTransaction) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method updateNodesCatalog");

        /*
         * Create the NodesCatalog
         */
        NodesCatalog nodeCatalog = new NodesCatalog();
        nodeCatalog.setIp(nodesCatalogTransaction.getIp());
        nodeCatalog.setDefaultPort(nodesCatalogTransaction.getDefaultPort());
        nodeCatalog.setIdentityPublicKey(nodesCatalogTransaction.getIdentityPublicKey());
        nodeCatalog.setName(nodesCatalogTransaction.getName());
        nodeCatalog.setOfflineCounter(0);
        nodeCatalog.setLastLocation(nodesCatalogTransaction.getLastLocation());
        nodeCatalog.setLastConnectionTimestamp(nodesCatalogTransaction.getLastConnectionTimestamp());
        nodeCatalog.setRegisteredTimestamp(nodesCatalogTransaction.getRegisteredTimestamp());

        /*
         * Save into the data base
         */
        return getDaoFactory().getNodesCatalogDao().createUpdateTransactionStatementPair(nodeCatalog);

    }

    /**
     * Delete a row from the data base
     *
     * @param identityPublicKey
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair deleteNodesCatalog(String identityPublicKey) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method deleteNodesCatalog");

        /*
         * Delete from the data base
         */
        return getDaoFactory().getNodesCatalogDao().createDeleteTransactionStatementPair(identityPublicKey);
    }

    /**
     * Create a new row into the data base
     *
     * @param nodesCatalogTransaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair insertNodesCatalogTransaction(NodesCatalogTransaction nodesCatalogTransaction) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method insertNodesCatalogTransaction");

        /*
         * Save into the data base
         */
        return getDaoFactory().getNodesCatalogTransactionDao().createInsertTransactionStatementPair(nodesCatalogTransaction);
    }
}
