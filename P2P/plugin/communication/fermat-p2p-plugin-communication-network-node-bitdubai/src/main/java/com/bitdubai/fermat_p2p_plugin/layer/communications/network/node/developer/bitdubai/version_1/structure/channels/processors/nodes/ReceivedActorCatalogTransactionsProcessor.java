package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;
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
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.util.List;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.ReceivedActorCatalogTransactionsProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
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

                for (ActorsCatalogTransaction actorsCatalogTransaction : transactionList) {

                    /*
                     * Process the transaction
                     */
                    lateNotificationsCounter = lateNotificationsCounter + processTransaction(actorsCatalogTransaction);
                }

                /*
                 * If all ok, respond whit success message
                 */
                receiveActorCatalogTransactionsMsjRespond = new ReceiveActorCatalogTransactionsMsjRespond(ReceivedNodeCatalogTransactionsMsjRespond.STATUS.SUCCESS, GetNodeCatalogMsjRespond.STATUS.SUCCESS.toString(), lateNotificationsCounter);
                Package packageRespond = Package.createInstance(receiveActorCatalogTransactionsMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.RECEIVE_ACTOR_CATALOG_TRANSACTIONS_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
                Package packageRespond = Package.createInstance(receiveActorCatalogTransactionsMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.RECEIVE_ACTOR_CATALOG_TRANSACTIONS_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
    private int processTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantReadRecordDataBaseException, RecordNotFoundException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException, CantDeleteRecordDataBaseException, InvalidParameterException {

        LOG.info("Executing method processTransaction");


        if ((getDaoFactory().getActorsCatalogDao().exists(actorsCatalogTransaction.getIdentityPublicKey())) &&
                (actorsCatalogTransaction.getTransactionType() == ActorsCatalogTransaction.ADD_TRANSACTION_TYPE)){

            return 1;

        }else {

            switch (actorsCatalogTransaction.getTransactionType()){

                case ActorsCatalogTransaction.ADD_TRANSACTION_TYPE :
                    insertActorsCatalog(actorsCatalogTransaction);
                    break;

                case ActorsCatalogTransaction.UPDATE_TRANSACTION_TYPE :
                    updateActorsCatalog(actorsCatalogTransaction);
                    break;

                case ActorsCatalogTransaction.DELETE_TRANSACTION_TYPE :
                    deleteActorsCatalog(actorsCatalogTransaction.getIdentityPublicKey());
                    break;
            }

            insertActorsCatalogTransaction(actorsCatalogTransaction);
            //insertActorsCatalogTransaction(actorsCatalogTransaction);
            insertActorsCatalogTransactionsPendingForPropagation(actorsCatalogTransaction);

        }

        return 0;
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantInsertRecordDataBaseException, CantReadRecordDataBaseException {

        LOG.info("Executing method insertActorsCatalog");

        if (!getDaoFactory().getActorsCatalogDao().exists(actorsCatalogTransaction.getIdentityPublicKey())) {

        /*
         * Create the ActorsCatalog
         */
            ActorsCatalog actorsCatalog = new ActorsCatalog();
            actorsCatalog.setIdentityPublicKey(actorsCatalogTransaction.getIdentityPublicKey());
            actorsCatalog.setActorType(actorsCatalogTransaction.getActorType());
            actorsCatalog.setAlias(actorsCatalogTransaction.getAlias());
            actorsCatalog.setExtraData(actorsCatalogTransaction.getExtraData());
            actorsCatalog.setHostedTimestamp(actorsCatalogTransaction.getHostedTimestamp());
            actorsCatalog.setLastLatitude(actorsCatalogTransaction.getLastLatitude());
            actorsCatalog.setLastLongitude(actorsCatalogTransaction.getLastLongitude());
            actorsCatalog.setName(actorsCatalogTransaction.getName());
            actorsCatalog.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
            actorsCatalog.setClientIdentityPublicKey(actorsCatalogTransaction.getClientIdentityPublicKey());
            actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

        /*
         * Save into the data base
         */
            getDaoFactory().getActorsCatalogDao().create(actorsCatalog);

        }
    }

    /**
     * Update a row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void updateActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException, RecordNotFoundException, InvalidParameterException, CantReadRecordDataBaseException {

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
        actorsCatalog.setLastLatitude(actorsCatalogTransaction.getLastLatitude());
        actorsCatalog.setLastLongitude(actorsCatalogTransaction.getLastLongitude());
        actorsCatalog.setName(actorsCatalogTransaction.getName());
        actorsCatalog.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
        actorsCatalog.setClientIdentityPublicKey(actorsCatalogTransaction.getClientIdentityPublicKey());
        actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

        if (getDaoFactory().getActorsCatalogDao().exists(actorsCatalogTransaction.getIdentityPublicKey())) {
            /*
             * Save into the data base
             */
            getDaoFactory().getActorsCatalogDao().update(actorsCatalog);
        } else {
            // if not exist i create it
            getDaoFactory().getActorsCatalogDao().create(actorsCatalog);
        }
    }

    /**
     * Delete a row from the data base
     *
     * @param identityPublicKey
     * @throws CantDeleteRecordDataBaseException
     * @throws RecordNotFoundException
     */
    private void deleteActorsCatalog(String identityPublicKey) throws CantDeleteRecordDataBaseException, RecordNotFoundException, CantReadRecordDataBaseException {

        LOG.info("Executing method deleteActorsCatalog");

        /*
         * Delete from the data base
         */
        if(getDaoFactory().getActorsCatalogDao().exists(identityPublicKey))
            getDaoFactory().getActorsCatalogDao().delete(identityPublicKey);
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertActorsCatalogTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantInsertRecordDataBaseException, CantReadRecordDataBaseException {

        LOG.info("Executing method insertActorsCatalogTransaction");

        /*
         * Save into the data base
         */
        if(!getDaoFactory().getActorsCatalogTransactionDao().exists(actorsCatalogTransaction.getId()))
            getDaoFactory().getActorsCatalogTransactionDao().create(actorsCatalogTransaction);
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertActorsCatalogTransactionsPendingForPropagation(ActorsCatalogTransaction actorsCatalogTransaction) throws CantInsertRecordDataBaseException, CantReadRecordDataBaseException {

        if (!getDaoFactory().getActorsCatalogTransactionsPendingForPropagationDao().exists(actorsCatalogTransaction.getId())) {
            LOG.info("Executing method insertActorsCatalogTransactionsPendingForPropagation");

            /*
             * Save into the data base
             */
            getDaoFactory().getActorsCatalogTransactionsPendingForPropagationDao().create(actorsCatalogTransaction);
        }
    }

}
