package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.UpdateProfileGeolocationMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.UpdateProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.sql.Timestamp;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.UpdateActorLastConnectionIntoCatalogProcessor</code>
 * process all packages received the type <code>MessageType.CHECK_IN_ACTOR_REQUEST</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/07/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class UpdateActorLastConnectionIntoCatalogProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(UpdateActorLastConnectionIntoCatalogProcessor.class));

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public UpdateActorLastConnectionIntoCatalogProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.CHECK_IN_ACTOR_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received "+packageReceived.getPackageType());

        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);

        CheckInProfileMsgRequest messageContent = CheckInProfileMsgRequest.parseContent(packageReceived.getContent());

        try {

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getProfileToRegister()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON){

               updateActor((ActorProfile) messageContent.getProfileToRegister());

            }

            LOG.info("Process finish");

        } catch (Exception exception){

            exception.printStackTrace();
            try {

                LOG.error(exception.getCause());

            } catch (Exception e) {
               LOG.error(e.getMessage());
            }
        }

    }

    private UpdateProfileMsjRespond updateActor(ActorProfile messageContent) throws Exception {

        // create database transaction for the update process
        DatabaseTransaction databaseTransaction = getDaoFactory().getActorsCatalogDao().getNewTransaction();
        DatabaseTransactionStatementPair pair;

        /*
         * Validate if exists
         */
        if (getDaoFactory().getActorsCatalogDao().exists(messageContent.getIdentityPublicKey())){

            LOG.info("Updating actor profile last connection");

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            /*
             * Update the profile in the catalog
             */
            pair = updateActorsCatalog(messageContent.getIdentityPublicKey(), timestamp);
            databaseTransaction.addRecordToUpdate(pair.getTable(), pair.getRecord());

            ActorsCatalogTransaction actorsCatalogTransaction = createActorsCatalogTransaction(messageContent.getIdentityPublicKey(), timestamp);

            /*
             * Create the transaction
             */
            pair = insertActorsCatalogTransaction(actorsCatalogTransaction);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            /*
             * Create the transaction for propagation
             */
            pair = insertActorsCatalogTransactionsPendingForPropagation(actorsCatalogTransaction);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            databaseTransaction.execute();

            /*
             * If all ok, respond whit success message
             */
            return new UpdateProfileMsjRespond(MsgRespond.STATUS.SUCCESS, MsgRespond.STATUS.SUCCESS.toString(), messageContent.getIdentityPublicKey());
        } else {
            return new UpdateProfileMsjRespond(MsgRespond.STATUS.FAIL, "The actor profile does not exist in the catalog.", messageContent.getIdentityPublicKey());
        }
    }

    /**
     * Update a row into the data base
     *
     * @param actorPublicKey
     * @param lastConnection
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair updateActorsCatalog(final String    actorPublicKey,
                                                                 final Timestamp lastConnection) throws CantCreateTransactionStatementPairException {

        /*
         * Save into the data base
         */
        return getDaoFactory().getActorsCatalogDao().createLastConnectionUpdateTransaction(actorPublicKey, lastConnection);
    }

    /**
     * Create a new row into the data base
     *
     * @param transaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair insertActorsCatalogTransaction(ActorsCatalogTransaction transaction) throws CantCreateTransactionStatementPairException {

        /*
         * Save into the data base
         */
        return getDaoFactory().getActorsCatalogTransactionDao().createInsertTransactionStatementPair(transaction);
    }

    /**
     * Create a new row into the data base
     *
     * @param identityPublicKey
     * @param lastConnection
     */
    private ActorsCatalogTransaction createActorsCatalogTransaction(final String    identityPublicKey,
                                                                    final Timestamp lastConnection   )  {

        /*
         * Create the transaction
         */
        ActorsCatalogTransaction transaction = new ActorsCatalogTransaction();
        transaction.setIdentityPublicKey(identityPublicKey);
        transaction.setTransactionType(ActorsCatalogTransaction.UPDATE_LAST_CONNECTION_TRANSACTION_TYPE);
        transaction.setGenerationTime(lastConnection);
        transaction.setLastConnection(lastConnection);

        /*
         * Create Object transaction
         */
        return transaction;
    }


    /**
     * Create a new row into the data base
     *
     * @param transaction
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair insertActorsCatalogTransactionsPendingForPropagation(ActorsCatalogTransaction transaction) throws CantCreateTransactionStatementPairException {


        /*
         * Save into the data base
         */
        return getDaoFactory().getActorsCatalogTransactionsPendingForPropagationDao().createInsertTransactionStatementPair(transaction);
    }

}
