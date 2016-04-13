package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.RecordNotFoundException;
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
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransactionsPendingForPropagation;

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
    private final Logger LOG = Logger.getLogger(ReceivedActorCatalogTransactionsProcessor.class.getName());

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
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);
        ReceiveActorCatalogTransactionsMsjRespond receiveActorCatalogTransactionsMsjRespond = null;
        Integer lateNotificationsCounter = new Integer(0);

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
    private int processTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantReadRecordDataBaseException, RecordNotFoundException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException, CantDeleteRecordDataBaseException {

        LOG.info("Executing method processTransaction");

        if (exist(actorsCatalogTransaction.getIdentityPublicKey())){
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
            insertActorsCatalogTransactionsPendingForPropagation(actorsCatalogTransaction);

        }

        return 0;
    }

    /**
     * Validate if the node exist into the catalog
     *
     * @param identityPublicKey
     * @return boolean
     */
    private boolean exist(String identityPublicKey) throws CantReadRecordDataBaseException, RecordNotFoundException {

        LOG.info("Executing method exist");

        /*
         * Search in the data base
         */
        ActorsCatalog actorsCatalog = getDaoFactory().getActorsCatalogDao().findById(identityPublicKey);

        if (actorsCatalog != null){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantInsertRecordDataBaseException {

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
        actorsCatalog.setLastLatitude(actorsCatalogTransaction.getLastLatitude());
        actorsCatalog.setLastLongitude(actorsCatalogTransaction.getLastLongitude());
        actorsCatalog.setName(actorsCatalogTransaction.getName());
        actorsCatalog.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
        actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogDao().create(actorsCatalog);
    }

    /**
     * Update a row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void updateActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantUpdateRecordDataBaseException, RecordNotFoundException {

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
        actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogDao().update(actorsCatalog);
    }

    /**
     * Delete a row from the data base
     *
     * @param identityPublicKey
     * @throws CantDeleteRecordDataBaseException
     * @throws RecordNotFoundException
     */
    private void deleteActorsCatalog(String identityPublicKey) throws CantDeleteRecordDataBaseException, RecordNotFoundException {

        LOG.info("Executing method deleteActorsCatalog");

        /*
         * Delete from the data base
         */
        getDaoFactory().getActorsCatalogDao().delete(identityPublicKey);
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertActorsCatalogTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantInsertRecordDataBaseException {

        LOG.info("Executing method insertActorsCatalogTransaction");

        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogTransactionDao().create(actorsCatalogTransaction);
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertActorsCatalogTransactionsPendingForPropagation(ActorsCatalogTransaction actorsCatalogTransaction) throws CantInsertRecordDataBaseException {

        LOG.info("Executing method insertActorsCatalogTransactionsPendingForPropagation");

        /*
         * Create the ActorsCatalogTransactionsPendingForPropagation
         */
        ActorsCatalogTransactionsPendingForPropagation transaction = new ActorsCatalogTransactionsPendingForPropagation();
        transaction.setIdentityPublicKey(actorsCatalogTransaction.getIdentityPublicKey());
        transaction.setActorType(actorsCatalogTransaction.getActorType());
        transaction.setAlias(actorsCatalogTransaction.getAlias());
        transaction.setExtraData(actorsCatalogTransaction.getExtraData());
        transaction.setHostedTimestamp(actorsCatalogTransaction.getHostedTimestamp());
        transaction.setLastLatitude(actorsCatalogTransaction.getLastLatitude());
        transaction.setLastLongitude(actorsCatalogTransaction.getLastLongitude());
        transaction.setName(actorsCatalogTransaction.getName());
        transaction.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
        transaction.setPhoto(actorsCatalogTransaction.getPhoto());

        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogTransactionsPendingForPropagationDao().create(transaction);
    }

}
