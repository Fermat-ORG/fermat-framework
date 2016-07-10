package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.UpdateActorProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.UpdateProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodePluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ThumbnailUtil;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.sql.Timestamp;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.AddActorIntoCatalogProcessor</code>
 * process all packages received the type <code>MessageType.UPDATE_ACTOR_PROFILE_REQUEST</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/06/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UpdateActorProfileIntoCatalogProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(UpdateActorProfileIntoCatalogProcessor.class));

    /**
     * Represent the nodeIdentity
     */
    private String nodeIdentity = ((NetworkNodePluginRoot) NodeContext.get(NodeContextItem.PLUGIN_ROOT)).getIdentity().getPublicKey();

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public UpdateActorProfileIntoCatalogProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.UPDATE_ACTOR_PROFILE_REQUEST);
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
        ActorProfile actorProfile = null;
        UpdateProfileMsjRespond updateProfileMsjRespond = null;

        try {

            UpdateActorProfileMsgRequest messageContent = UpdateActorProfileMsgRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getProfileToUpdate()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON){

                /*
                 * Obtain the profile of the actor
                 */
                actorProfile = (ActorProfile) messageContent.getProfileToUpdate();

                // create transaction for
                DatabaseTransaction databaseTransaction = getDaoFactory().getActorsCatalogDao().getNewTransaction();
                DatabaseTransactionStatementPair pair;

                /*
                 * Validate if exist
                 */
                if (getDaoFactory().getActorsCatalogDao().exists(actorProfile.getIdentityPublicKey())){

                    LOG.info("Existing profile");

                    boolean hasChanges = validateProfileChange(actorProfile);

                    LOG.info("hasChanges = "+hasChanges);

                    if (hasChanges){

                        Timestamp currentMillis = new Timestamp(System.currentTimeMillis());
                        LOG.info("Updating profile");

                        /*
                         * Update the profile in the catalog
                         */
                        pair = updateActorsCatalog(actorProfile, currentMillis);
                        databaseTransaction.addRecordToUpdate(pair.getTable(), pair.getRecord());

                        /*
                         * update actor in checkinactor table if exist in
                         */
                        if(getDaoFactory().getCheckedInActorDao().exists(actorProfile.getIdentityPublicKey())){
                           /*
                            * Update the profile in the checkinactor table
                            */
                            pair = updateCheckedInActor(actorProfile);
                            databaseTransaction.addRecordToUpdate(pair.getTable(), pair.getRecord());
                        }

                        ActorsCatalogTransaction actorsCatalogTransaction = createActorsCatalogTransaction(actorProfile, ActorsCatalogTransaction.UPDATE_TRANSACTION_TYPE, currentMillis);

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
                        updateProfileMsjRespond = new UpdateProfileMsjRespond(MsgRespond.STATUS.SUCCESS, MsgRespond.STATUS.SUCCESS.toString(), actorProfile.getIdentityPublicKey());
                    }

                } else {

                    updateProfileMsjRespond = new UpdateProfileMsjRespond(MsgRespond.STATUS.FAIL, "The actor profile no exist", actorProfile.getIdentityPublicKey());

                }

                /*
                 * Send the respond
                 */
                Package packageRespond = Package.createInstance(updateProfileMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.UPDATE_ACTOR_PROFILE_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);
                session.getAsyncRemote().sendObject(packageRespond);

            }

            LOG.info("Process finish");

        } catch (Exception exception){

            try {

                LOG.error(exception.getCause());
                updateProfileMsjRespond = new UpdateProfileMsjRespond(MsgRespond.STATUS.FAIL, exception.getCause().getMessage(), actorProfile.getIdentityPublicKey());
                Package packageRespond = Package.createInstance(updateProfileMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.UPDATE_ACTOR_PROFILE_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);
                session.getAsyncRemote().sendObject(packageRespond);

            }catch (Exception e) {
               LOG.error(e.getMessage());
            }
        }

    }

    /**
     * Update a row into the data base
     *
     * @param actorProfile
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair updateActorsCatalog(ActorProfile actorProfile, Timestamp currentMillis) throws CantCreateTransactionStatementPairException, IOException {

        /*
         * Create the actorsCatalog
         */
        ActorsCatalog actorsCatalog = new ActorsCatalog();
        actorsCatalog.setIdentityPublicKey(actorProfile.getIdentityPublicKey());
        actorsCatalog.setActorType(actorProfile.getActorType());
        actorsCatalog.setAlias(actorProfile.getAlias());
        actorsCatalog.setName(actorProfile.getName());
        actorsCatalog.setPhoto(actorProfile.getPhoto());
        actorsCatalog.setExtraData(actorProfile.getExtraData());
        actorsCatalog.setNodeIdentityPublicKey(nodeIdentity);
        actorsCatalog.setClientIdentityPublicKey(actorProfile.getClientIdentityPublicKey());
        actorsCatalog.setLastConnection(currentMillis);
        actorsCatalog.setLastUpdateTime(currentMillis);
        actorsCatalog.setLastLocation(actorProfile.getLocation());

        if(actorProfile.getPhoto() != null)
            actorsCatalog.setThumbnail(ThumbnailUtil.generateThumbnail(actorProfile.getPhoto(),null));
        else
            actorsCatalog.setThumbnail(null);

        /*
         * Save into the data base
         */
        return getDaoFactory().getActorsCatalogDao().createUpdateTransactionStatementPair(actorsCatalog);
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
     * @param actorProfile
     */
    private ActorsCatalogTransaction createActorsCatalogTransaction(ActorProfile actorProfile, String transactionType, Timestamp currentMillis) throws IOException {

        /*
         * Create the transaction
         */
        ActorsCatalogTransaction transaction = new ActorsCatalogTransaction();

        transaction.setIdentityPublicKey(actorProfile.getIdentityPublicKey());
        transaction.setActorType(actorProfile.getActorType());
        transaction.setAlias(actorProfile.getAlias());
        transaction.setName(actorProfile.getName());
        transaction.setPhoto(actorProfile.getPhoto());
        transaction.setExtraData(actorProfile.getExtraData());
        transaction.setNodeIdentityPublicKey(nodeIdentity);
        transaction.setClientIdentityPublicKey(actorProfile.getClientIdentityPublicKey());
        transaction.setTransactionType(transactionType);
        transaction.setGenerationTime(currentMillis);
        transaction.setLastConnection(currentMillis);
        transaction.setLastLocation(actorProfile.getLocation());

        if(actorProfile.getPhoto() != null)
            transaction.setThumbnail(ThumbnailUtil.generateThumbnail(actorProfile.getPhoto(),null));
        else
            transaction.setThumbnail(null);

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

    /**
     * Validate if the profile register have changes
     *
     * @param actorProfile
     * @return boolean
     * @throws CantReadRecordDataBaseException
     * @throws RecordNotFoundException
     */
    private boolean validateProfileChange(ActorProfile actorProfile) throws CantReadRecordDataBaseException, RecordNotFoundException, IOException {

        /*
         * Create the actorsCatalog
         */
        ActorsCatalog actorsCatalog = new ActorsCatalog();
        actorsCatalog.setIdentityPublicKey(actorProfile.getIdentityPublicKey());
        actorsCatalog.setActorType(actorProfile.getActorType());
        actorsCatalog.setAlias(actorProfile.getAlias());
        actorsCatalog.setName(actorProfile.getName());
        actorsCatalog.setPhoto(actorProfile.getPhoto());
        actorsCatalog.setExtraData(actorProfile.getExtraData());
        actorsCatalog.setClientIdentityPublicKey(actorProfile.getClientIdentityPublicKey());
        actorsCatalog.setNodeIdentityPublicKey(nodeIdentity);

        //Validate if location are available
        if (actorProfile.getLocation() != null){
            actorsCatalog.setLastLocation(actorProfile.getLocation().getLatitude(), actorProfile.getLocation().getLongitude());
        }else{
            actorsCatalog.setLastLocation(0.0, 0.0);
        }

        if(actorProfile.getPhoto() != null)
            actorsCatalog.setThumbnail(ThumbnailUtil.generateThumbnail(actorProfile.getPhoto(), null));
        else
            actorsCatalog.setThumbnail(null);

        ActorsCatalog actorsCatalogRegister = getDaoFactory().getActorsCatalogDao().findById(actorProfile.getIdentityPublicKey());

        return !actorsCatalogRegister.equals(actorsCatalog);
    }

    /**
     * Create a new row into the data base
     *
     * @param actorProfile
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    private DatabaseTransactionStatementPair updateCheckedInActor(final ActorProfile actorProfile) throws CantCreateTransactionStatementPairException {

        /*
         * Create the CheckedInActor
         */
        CheckedInActor checkedInActor = new CheckedInActor();
        checkedInActor.setIdentityPublicKey(actorProfile.getIdentityPublicKey());
        checkedInActor.setActorType(actorProfile.getActorType());
        checkedInActor.setAlias(actorProfile.getAlias());
        checkedInActor.setName(actorProfile.getName());
        checkedInActor.setPhoto(actorProfile.getPhoto());
        checkedInActor.setExtraData(actorProfile.getExtraData());
        checkedInActor.setNsIdentityPublicKey(actorProfile.getNsIdentityPublicKey());
        checkedInActor.setClientIdentityPublicKey(actorProfile.getClientIdentityPublicKey());

        //Validate if location are available
        if (actorProfile.getLocation() != null){
            checkedInActor.setLatitude(actorProfile.getLocation().getLatitude());
            checkedInActor.setLongitude(actorProfile.getLocation().getLongitude());
        }else{
            checkedInActor.setLatitude(0.0);
            checkedInActor.setLongitude(0.0);
        }

        return getDaoFactory().getCheckedInActorDao().createUpdateTransactionStatementPair(checkedInActor);
    }
}
