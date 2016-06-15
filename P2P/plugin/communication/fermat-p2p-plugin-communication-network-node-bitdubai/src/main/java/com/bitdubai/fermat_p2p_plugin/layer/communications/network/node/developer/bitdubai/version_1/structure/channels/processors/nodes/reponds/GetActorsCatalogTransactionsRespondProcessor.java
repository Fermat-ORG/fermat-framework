package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.reponds;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetActorCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetActorCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetNodeCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
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
public class GetActorsCatalogTransactionsRespondProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(GetActorsCatalogTransactionsRespondProcessor.class));

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public GetActorsCatalogTransactionsRespondProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.GET_ACTOR_CATALOG_TRANSACTIONS_RESPONSE);
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

            GetActorCatalogTransactionsMsjRespond messageContent =  GetActorCatalogTransactionsMsjRespond.parseContent(packageReceived.getContent());

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
                    List<ActorsCatalogTransaction>  transactionList = messageContent.getActorsCatalogTransaction();

                    LOG.info("transactionList size = "+transactionList.size());

                    for (ActorsCatalogTransaction actorsCatalogTransaction : transactionList) {

                        /*
                         * Process the transaction
                         */
                        processTransaction(actorsCatalogTransaction);
                    }

                    long totalRowInDb = getDaoFactory().getActorsCatalogTransactionDao().getAllCount();

                    LOG.info("Row in node catalog  = "+totalRowInDb);
                    LOG.info("Row in catalog seed node = "+messageContent.getCount());

                    if (totalRowInDb < messageContent.getCount()){

                        LOG.info("Requesting more transactions.");

                        GetActorCatalogTransactionsMsjRequest getActorCatalogTransactionsMsjRequest = new GetActorCatalogTransactionsMsjRequest((int) totalRowInDb+1, 250);
                        Package packageRespond = Package.createInstance(getActorCatalogTransactionsMsjRequest.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.GET_ACTOR_CATALOG_TRANSACTIONS_REQUEST, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
     * @param actorsCatalogTransaction
     */
    private void processTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException, DatabaseTransactionFailedException {

        LOG.info("Executing method processTransaction");

        // create transaction for
        DatabaseTransaction databaseTransaction = getDaoFactory().getActorsCatalogDao().getNewTransaction();
        DatabaseTransactionStatementPair pair;

        switch (actorsCatalogTransaction.getTransactionType()){

            case ActorsCatalogTransaction.ADD_TRANSACTION_TYPE :
                pair = insertActorsCatalog(actorsCatalogTransaction);
                databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());
                break;

            case ActorsCatalogTransaction.UPDATE_TRANSACTION_TYPE :
                pair = updateActorsCatalog(actorsCatalogTransaction);
                databaseTransaction.addRecordToUpdate(pair.getTable(), pair.getRecord());
                break;

            case ActorsCatalogTransaction.DELETE_TRANSACTION_TYPE :
                pair = deleteActorsCatalog(actorsCatalogTransaction.getIdentityPublicKey());
                databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());
                break;
        }

        pair = insertActorsCatalogTransaction(actorsCatalogTransaction);
        databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

        databaseTransaction.execute();

    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException {

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
        actorsCatalog.setLastLocation(actorsCatalogTransaction.getLastLocation());
        actorsCatalog.setName(actorsCatalogTransaction.getName());
        actorsCatalog.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
        actorsCatalog.setClientIdentityPublicKey(actorsCatalogTransaction.getClientIdentityPublicKey());
        actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

        /*
         * Save into the data base
         */
        return  getDaoFactory().getActorsCatalogDao().createInsertTransactionStatementPair(actorsCatalog);

    }

    /**
     * Update a row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair updateActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException {

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
        actorsCatalog.setLastLocation(actorsCatalogTransaction.getLastLocation());
        actorsCatalog.setName(actorsCatalogTransaction.getName());
        actorsCatalog.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
        actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

        /*
         * Save into the data base
         */
        return  getDaoFactory().getActorsCatalogDao().createUpdateTransactionStatementPair(actorsCatalog);

    }

    /**
     * Delete a row from the data base
     *
     * @param identityPublicKey
     * @throws CantDeleteRecordDataBaseException
     * @throws RecordNotFoundException
     */
    private DatabaseTransactionStatementPair deleteActorsCatalog(String identityPublicKey) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method deleteActorsCatalog");

        /*
         * Delete from the data base
         */
        return getDaoFactory().getActorsCatalogDao().createDeleteTransactionStatementPair(identityPublicKey);
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertActorsCatalogTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantCreateTransactionStatementPairException {

        LOG.info("Executing method insertActorsCatalogTransaction");

        /*
         * Save into the data base
         */
        return getDaoFactory().getActorsCatalogTransactionDao().createInsertTransactionStatementPair(actorsCatalogTransaction);
    }
}
