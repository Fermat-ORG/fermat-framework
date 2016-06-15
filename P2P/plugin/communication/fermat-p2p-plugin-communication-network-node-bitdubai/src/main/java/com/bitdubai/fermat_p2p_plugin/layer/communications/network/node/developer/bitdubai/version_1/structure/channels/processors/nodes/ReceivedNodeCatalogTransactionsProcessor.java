package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.ReceiveNodeCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetNodeCatalogMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.ReceivedNodeCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.util.List;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.ReceivedNodeCatalogTransactionsProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ReceivedNodeCatalogTransactionsProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ReceivedNodeCatalogTransactionsProcessor.class));

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public ReceivedNodeCatalogTransactionsProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.RECEIVE_NODE_CATALOG_TRANSACTIONS_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public synchronized void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);
        ReceivedNodeCatalogTransactionsMsjRespond receivedNodeCatalogTransactionsMsjRespond;
        Integer lateNotificationsCounter = 0;

        try {

            ReceiveNodeCatalogTransactionsMsjRequest messageContent = ReceiveNodeCatalogTransactionsMsjRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getNodesCatalogTransactions()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){

                /*
                 * Get the block of transactions
                 */
                List<NodesCatalogTransaction>  transactionList = messageContent.getNodesCatalogTransactions();

                for (NodesCatalogTransaction nodesCatalogTransaction : transactionList) {

                    /*
                     * Process the transaction
                     */
                    lateNotificationsCounter = lateNotificationsCounter + processTransaction(nodesCatalogTransaction);
                }

                /*
                 * If all ok, respond whit success message
                 */
                receivedNodeCatalogTransactionsMsjRespond = new ReceivedNodeCatalogTransactionsMsjRespond(ReceivedNodeCatalogTransactionsMsjRespond.STATUS.SUCCESS, GetNodeCatalogMsjRespond.STATUS.SUCCESS.toString(), lateNotificationsCounter);
                Package packageRespond = Package.createInstance(receivedNodeCatalogTransactionsMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.RECEIVE_NODE_CATALOG_TRANSACTIONS_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

            }


        } catch (Exception exception){

            try {

                LOG.error(exception.getMessage());

                /*
                 * Respond whit fail message
                 */
                receivedNodeCatalogTransactionsMsjRespond = new ReceivedNodeCatalogTransactionsMsjRespond(ReceivedNodeCatalogTransactionsMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), lateNotificationsCounter);
                Package packageRespond = Package.createInstance(receivedNodeCatalogTransactionsMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.RECEIVE_NODE_CATALOG_TRANSACTIONS_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

            } catch (Exception e) {
                LOG.error(e.getMessage());
            }

        }
    }

    /**
     * Process the transaction
     * @param nodesCatalogTransaction
     */
    private int processTransaction(NodesCatalogTransaction nodesCatalogTransaction) throws CantReadRecordDataBaseException, RecordNotFoundException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException, CantDeleteRecordDataBaseException, InvalidParameterException, CantCreateTransactionStatementPairException, DatabaseTransactionFailedException {

        LOG.info("Executing method processTransaction");

        if ((getDaoFactory().getNodesCatalogDao().exists(nodesCatalogTransaction.getIdentityPublicKey())) &&
                nodesCatalogTransaction.getTransactionType() == NodesCatalogTransaction.ADD_TRANSACTION_TYPE){

            return 1;

        }else {

            // create transaction for
            DatabaseTransaction databaseTransaction = getDaoFactory().getNodesCatalogDao().getNewTransaction();
            DatabaseTransactionStatementPair pair;

            switch (nodesCatalogTransaction.getTransactionType()){

                case NodesCatalogTransaction.ADD_TRANSACTION_TYPE :
                    /*
                     *  create the node catalog transaction
                     */
                    pair = insertNodesCatalog(nodesCatalogTransaction);
                    databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());
                    break;

                case NodesCatalogTransaction.UPDATE_TRANSACTION_TYPE :
                    /*
                     * Update the node catalog transaction
                     */
                    pair = updateNodesCatalog(nodesCatalogTransaction);
                    databaseTransaction.addRecordToUpdate(pair.getTable(), pair.getRecord());
                    break;

                case NodesCatalogTransaction.DELETE_TRANSACTION_TYPE :
                    /*
                     * Delete the node catalog transaction
                     */
                    pair = deleteNodesCatalog(nodesCatalogTransaction.getIdentityPublicKey());
                    databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());
                    break;
            }

            /*
             * Insert NodesCatalogTransaction into data base
             */
            pair = insertNodesCatalogTransaction(nodesCatalogTransaction);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            /*
             * Insert NodesCatalogTransactionsPendingForPropagation into data base
             */
            pair = insertNodesCatalogTransactionsPendingForPropagation(nodesCatalogTransaction);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            databaseTransaction.execute();

        }

        return 0;
    }

    /**
     * Create a new row into the data base
     *
     * @param nodesCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertNodesCatalog(NodesCatalogTransaction nodesCatalogTransaction) throws CantInsertRecordDataBaseException, CantReadRecordDataBaseException, CantCreateTransactionStatementPairException {

            LOG.info("Executing method insertNodesCatalog");

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
             * Create statement.
             */
            return getDaoFactory().getNodesCatalogDao().createInsertTransactionStatementPair(nodeCatalog);

    }

    /**
     * Update a row into the data base
     *
     * @param nodesCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair updateNodesCatalog(NodesCatalogTransaction nodesCatalogTransaction) throws CantInsertRecordDataBaseException, CantReadRecordDataBaseException, CantUpdateRecordDataBaseException, RecordNotFoundException, InvalidParameterException, CantCreateTransactionStatementPairException {

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
         * Create statement.
         */
        return getDaoFactory().getNodesCatalogDao().createUpdateTransactionStatementPair(nodeCatalog);

    }

    /**
     * Delete a row from the data base
     *
     * @param identityPublicKey
     * @throws CantDeleteRecordDataBaseException
     * @throws RecordNotFoundException
     */
    private DatabaseTransactionStatementPair deleteNodesCatalog(String identityPublicKey) throws CantDeleteRecordDataBaseException, RecordNotFoundException, CantReadRecordDataBaseException, CantCreateTransactionStatementPairException {

        LOG.info("Executing method deleteNodesCatalog");

        /*
         * Create statement.
         */
        return getDaoFactory().getNodesCatalogDao().createDeleteTransactionStatementPair(identityPublicKey);
    }

    /**
     * Create a new row into the data base
     *
     * @param nodesCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertNodesCatalogTransaction(NodesCatalogTransaction nodesCatalogTransaction) throws CantInsertRecordDataBaseException, CantReadRecordDataBaseException, CantCreateTransactionStatementPairException {

        LOG.info("Executing method insertNodesCatalogTransaction");

        /*
         * Create statement.
         */
        return getDaoFactory().getNodesCatalogTransactionDao().createInsertTransactionStatementPair(nodesCatalogTransaction);
    }

    /**
     * Create a new row into the data base
     *
     * @param nodesCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertNodesCatalogTransactionsPendingForPropagation(NodesCatalogTransaction nodesCatalogTransaction) throws CantInsertRecordDataBaseException, CantReadRecordDataBaseException, CantCreateTransactionStatementPairException {

        LOG.info("Executing method insertNodesCatalogTransactionsPendingForPropagation");

        /*
         * Create statement.
         */
        return getDaoFactory().getNodesCatalogTransactionsPendingForPropagationDao().createInsertTransactionStatementPair(nodesCatalogTransaction);
    }

}
