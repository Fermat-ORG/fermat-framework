package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
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
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ThumbnailUtil;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.List;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.ReceivedActorCatalogTransactionsProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ReceivedActorCatalogTransactionsProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ReceivedActorCatalogTransactionsProcessor.class));

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public ReceivedActorCatalogTransactionsProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.RECEIVE_ACTOR_CATALOG_TRANSACTIONS_REQUEST);
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
        ReceiveActorCatalogTransactionsMsjRespond receiveActorCatalogTransactionsMsjRespond = null;
        Integer lateNotificationsCounter = 0;

        try {

            ReceiveActorCatalogTransactionsMsjRequest messageContent = ReceiveActorCatalogTransactionsMsjRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getActorsCatalogTransactions()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){

                /*
                 * Get the block of transactions
                 */
                List<ActorsCatalogTransaction> transactionList = messageContent.getActorsCatalogTransactions();

                /*
                 * Process the transaction list
                 */
                for (ActorsCatalogTransaction actorsCatalogTransaction : transactionList)
                    lateNotificationsCounter = lateNotificationsCounter + processTransaction(actorsCatalogTransaction);

                /*
                 * If all ok, respond whit success message
                 */
                receiveActorCatalogTransactionsMsjRespond = new ReceiveActorCatalogTransactionsMsjRespond(ReceivedNodeCatalogTransactionsMsjRespond.STATUS.SUCCESS, GetNodeCatalogMsjRespond.STATUS.SUCCESS.toString(), lateNotificationsCounter);
                Package packageRespond = Package.createInstance(receiveActorCatalogTransactionsMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.RECEIVE_ACTOR_CATALOG_TRANSACTIONS_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

            }

        } catch (Exception exception){

            try {

                exception.printStackTrace();
                LOG.error(exception.getMessage());

                /*
                 * Respond whit fail message
                 */
                receiveActorCatalogTransactionsMsjRespond = new ReceiveActorCatalogTransactionsMsjRespond(ReceivedNodeCatalogTransactionsMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), lateNotificationsCounter);
                Package packageRespond = Package.createInstance(receiveActorCatalogTransactionsMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.RECEIVE_ACTOR_CATALOG_TRANSACTIONS_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
     * @param actorsCatalogTransaction
     */
    private int processTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantReadRecordDataBaseException, CantCreateTransactionStatementPairException, DatabaseTransactionFailedException {

        LOG.info("Executing method processTransaction");


        if ((getDaoFactory().getActorsCatalogDao().exists(actorsCatalogTransaction.getIdentityPublicKey())) &&
                (actorsCatalogTransaction.getTransactionType().equals(ActorsCatalogTransaction.ADD_TRANSACTION_TYPE)) ||
                getDaoFactory().getActorsCatalogTransactionDao().exists(actorsCatalogTransaction.getId())){

            return 1;

        } else {

            try {

                // create transaction for
                DatabaseTransaction databaseTransaction = getDaoFactory().getActorsCatalogDao().getNewTransaction();
                DatabaseTransactionStatementPair pair;

                switch (actorsCatalogTransaction.getTransactionType()){

                    case ActorsCatalogTransaction.ADD_TRANSACTION_TYPE :
                        /*
                         * Insert ActorsCatalog into data base
                         */
                        pair = insertActorsCatalog(actorsCatalogTransaction);
                        databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());
                        break;

                    case ActorsCatalogTransaction.UPDATE_TRANSACTION_TYPE :
                        /*
                         * Update ActorsCatalog into data base
                         */
                        pair = updateActorsCatalog(actorsCatalogTransaction);
                        databaseTransaction.addRecordToUpdate(pair.getTable(), pair.getRecord());
                        break;

                    case ActorsCatalogTransaction.DELETE_TRANSACTION_TYPE :
                        /*
                         * Delete ActorsCatalog into data base
                         */
                        pair = deleteActorsCatalog(actorsCatalogTransaction.getIdentityPublicKey());
                        databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());
                        break;

                    case ActorsCatalogTransaction.UPDATE_GEOLOCATION_TRANSACTION_TYPE :
                        /*
                         * Delete ActorsCatalog into data base
                         */
                        pair = updateLocationActorsCatalog(actorsCatalogTransaction);
                        databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());
                        break;

                    case ActorsCatalogTransaction.UPDATE_LAST_CONNECTION_TRANSACTION_TYPE :
                        /*
                         * Delete ActorsCatalog into data base
                         */
                        pair = updateLastConnectionActorsCatalog(actorsCatalogTransaction);
                        databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());
                        break;
                }

                /*
                 * Insert ActorsCatalogTransaction
                 */
                pair = insertActorsCatalogTransaction(actorsCatalogTransaction);
                databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                /*
                 * Insert ActorsCatalogTransactionsPendingForPropagation
                 */
                pair = insertActorsCatalogTransactionsPendingForPropagation(actorsCatalogTransaction);
                databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                databaseTransaction.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return 0;
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair insertActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException, IOException {

        LOG.info("Executing method insertActorsCatalog");

        /*
         * Create the ActorsCatalog
         */
        ActorsCatalog actorsCatalog = new ActorsCatalog();
        actorsCatalog.setIdentityPublicKey(actorsCatalogTransaction.getIdentityPublicKey());
        actorsCatalog.setActorType(actorsCatalogTransaction.getActorType());
        actorsCatalog.setAlias(actorsCatalogTransaction.getAlias());
        actorsCatalog.setExtraData(actorsCatalogTransaction.getExtraData());
        actorsCatalog.setHostedTimestamp(actorsCatalogTransaction.getHostedTimestamp());
        actorsCatalog.setLastUpdateTime(actorsCatalogTransaction.getGenerationTime());
        actorsCatalog.setLastLocation(actorsCatalogTransaction.getLastLocation());
        actorsCatalog.setLastConnection(actorsCatalogTransaction.getLastConnection());
        actorsCatalog.setName(actorsCatalogTransaction.getName());
        actorsCatalog.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
        actorsCatalog.setClientIdentityPublicKey(actorsCatalogTransaction.getClientIdentityPublicKey());
        actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

        if(actorsCatalogTransaction.getPhoto() != null)
            actorsCatalog.setThumbnail(ThumbnailUtil.generateThumbnail(actorsCatalogTransaction.getPhoto(),null));
        else
            actorsCatalog.setThumbnail(null);

        /*
         * Create statement.
        */
        return getDaoFactory().getActorsCatalogDao().createInsertTransactionStatementPair(actorsCatalog);
    }

    /**
     * Update a row into the data base
     *
     * @param actorsCatalogTransaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair updateActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException, IOException {

        LOG.info("Executing method updateActorsCatalog");

        /*
         * Create the ActorsCatalog
         */
        ActorsCatalog actorsCatalog = new ActorsCatalog();
        actorsCatalog.setIdentityPublicKey(actorsCatalogTransaction.getIdentityPublicKey());
        actorsCatalog.setActorType(actorsCatalogTransaction.getActorType());
        actorsCatalog.setAlias(actorsCatalogTransaction.getAlias());
        actorsCatalog.setExtraData(actorsCatalogTransaction.getExtraData());
        actorsCatalog.setHostedTimestamp(actorsCatalogTransaction.getHostedTimestamp());
        actorsCatalog.setLastUpdateTime(actorsCatalogTransaction.getGenerationTime());
        actorsCatalog.setLastConnection(actorsCatalogTransaction.getLastConnection());
        actorsCatalog.setLastLocation(actorsCatalogTransaction.getLastLocation());
        actorsCatalog.setName(actorsCatalogTransaction.getName());
        actorsCatalog.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
        actorsCatalog.setClientIdentityPublicKey(actorsCatalogTransaction.getClientIdentityPublicKey());
        actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

        if(actorsCatalogTransaction.getPhoto() != null)
            actorsCatalog.setThumbnail(ThumbnailUtil.generateThumbnail(actorsCatalogTransaction.getPhoto(), null));
        else
            actorsCatalog.setThumbnail(null);

        /*
         * Create statement.
         */
        return getDaoFactory().getActorsCatalogDao().createUpdateTransactionStatementPair(actorsCatalog);
    }

    /**
     * Update a row into the data base
     *
     * @param actorsCatalogTransaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair updateLocationActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method updateLocationActorsCatalog");

        /*
         * Create statement.
         */
        return getDaoFactory().getActorsCatalogDao().createLocationUpdateTransactionStatementPair(actorsCatalogTransaction.getIdentityPublicKey(), actorsCatalogTransaction.getLastLocation(), actorsCatalogTransaction.getGenerationTime());
    }

    /**
     * Update a row into the data base
     *
     * @param actorsCatalogTransaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair updateLastConnectionActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method updateLastConnectionActorsCatalog");

        /*
         * Create statement.
         */
        return getDaoFactory().getActorsCatalogDao().createLastConnectionUpdateTransaction(actorsCatalogTransaction.getIdentityPublicKey(), actorsCatalogTransaction.getLastConnection());
    }

    /**
     * Delete a row from the data base
     *
     * @param identityPublicKey
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair deleteActorsCatalog(String identityPublicKey) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method deleteActorsCatalog");

        /*
         * Create statement.
         */

         return getDaoFactory().getActorsCatalogDao().createDeleteTransactionStatementPair(identityPublicKey);
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair insertActorsCatalogTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method insertActorsCatalogTransaction");

         /*
         * Create statement.
         */
        return   getDaoFactory().getActorsCatalogTransactionDao().createInsertTransactionStatementPair(actorsCatalogTransaction);
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair insertActorsCatalogTransactionsPendingForPropagation(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method insertActorsCatalogTransactionsPendingForPropagation");

        /*
         * Create statement.
         */
        return  getDaoFactory().getActorsCatalogTransactionsPendingForPropagationDao().createInsertTransactionStatementPair(actorsCatalogTransaction);
    }

}
