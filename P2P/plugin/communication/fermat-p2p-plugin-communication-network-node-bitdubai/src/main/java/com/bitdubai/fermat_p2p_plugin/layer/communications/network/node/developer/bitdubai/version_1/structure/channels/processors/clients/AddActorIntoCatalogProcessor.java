package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodePluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.AddActorIntoCatalogProcessor</code>
 * process all packages received the type <code>MessageType.CHECK_IN_ACTOR_REQUEST</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AddActorIntoCatalogProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(AddActorIntoCatalogProcessor.class));

    /**
     * Represent the nodeIdentity
     */
    private String nodeIdentity = ((NetworkNodePluginRoot) NodeContext.get(NodeContextItem.PLUGIN_ROOT)).getIdentity().getPublicKey();

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public AddActorIntoCatalogProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.CHECK_IN_ACTOR_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);
        ActorProfile actorProfile = null;

        try {

            CheckInProfileMsgRequest messageContent = CheckInProfileMsgRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getProfileToRegister()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON){

                /*
                 * Obtain the profile of the actor
                 */
                actorProfile = (ActorProfile) messageContent.getProfileToRegister();

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

                        LOG.info("Updating profile");

                        /*
                         * Update the profile in the catalog
                         */
                        pair = updateActorsCatalog(actorProfile);
                        databaseTransaction.addRecordToUpdate(pair.getTable(), pair.getRecord());

                        /*
                         * Create the transaction
                         */
                        pair = insertActorsCatalogTransaction(actorProfile, ActorsCatalogTransaction.UPDATE_TRANSACTION_TYPE);
                        databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                        ActorsCatalogTransaction actorsCatalogTransaction = createActorsCatalogTransaction(actorProfile, ActorsCatalogTransaction.UPDATE_TRANSACTION_TYPE);

                        /*
                         * Create the transaction for propagation
                         */
                        pair = insertActorsCatalogTransactionsPendingForPropagation(actorsCatalogTransaction);
                        databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                    }

                }else {

                    LOG.info("New Profile proceed to insert into catalog");

                    /*
                     * Insert into the catalog
                     */
                    pair = insertActorsCatalog(actorProfile);
                    databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                    /*
                     * Create the transaction
                     */
                    pair = insertActorsCatalogTransaction(actorProfile, ActorsCatalogTransaction.ADD_TRANSACTION_TYPE);
                    databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                    ActorsCatalogTransaction actorsCatalogTransaction = createActorsCatalogTransaction(actorProfile, ActorsCatalogTransaction.ADD_TRANSACTION_TYPE);

                    /*
                     * Create the transaction for propagation
                     */
                    pair = insertActorsCatalogTransactionsPendingForPropagation(actorsCatalogTransaction);
                    databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());
                }

                databaseTransaction.execute();

            }

            LOG.info("Process finish");

        }catch (Exception exception){

            exception.printStackTrace();
            LOG.error(exception.getCause());

        }

    }

    /**
     * Create a new row into the data base
     *
     * @param actorProfile
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertActorsCatalog(ActorProfile actorProfile) throws CantCreateTransactionStatementPairException {

        /*
         * Create the actorsCatalog
         */
        ActorsCatalog actorsCatalog = new ActorsCatalog();
        actorsCatalog.setIdentityPublicKey(actorProfile.getIdentityPublicKey());
        actorsCatalog.setActorType(actorProfile.getActorType());
        actorsCatalog.setAlias(actorProfile.getAlias());
        actorsCatalog.setExtraData(actorProfile.getExtraData());
        actorsCatalog.setName(actorProfile.getName());
        actorsCatalog.setPhoto(actorProfile.getPhoto());
        actorsCatalog.setNodeIdentityPublicKey(nodeIdentity);
        actorsCatalog.setClientIdentityPublicKey(actorProfile.getClientIdentityPublicKey());

        //Validate if location are available
        if (actorProfile.getLocation() != null){
            actorsCatalog.setLastLocation(actorProfile.getLocation().getLatitude(), actorProfile.getLocation().getLongitude());
        }else{
            actorsCatalog.setLastLocation(0.0, 0.0);
        }

        /*
         * Save into the data base
         */
        return getDaoFactory().getActorsCatalogDao().createInsertTransactionStatementPair(actorsCatalog);

    }


    /**
     * Update a row into the data base
     *
     * @param actorProfile
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair updateActorsCatalog(ActorProfile actorProfile) throws CantCreateTransactionStatementPairException {

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

        //Validate if location are available
        if (actorProfile.getLocation() != null){
            actorsCatalog.setLastLocation(actorProfile.getLocation().getLatitude(), actorProfile.getLocation().getLongitude());
        }else{
            actorsCatalog.setLastLocation(0.0, 0.0);
        }

        /*
         * Save into the data base
         */
        return getDaoFactory().getActorsCatalogDao().createUpdateTransactionStatementPair(actorsCatalog);

    }

    /**
     * Create a new row into the data base
     *
     * @param actorProfile
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertActorsCatalogTransaction(ActorProfile actorProfile, String transactionType) throws CantCreateTransactionStatementPairException {

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

        //Validate if location are available
        if (actorProfile.getLocation() != null){
            transaction.setLastLocation(actorProfile.getLocation().getLatitude(), actorProfile.getLocation().getLongitude());
        }else{
            transaction.setLastLocation(0.0, 0.0);
        }

        /*
         * Save into the data base
         */
        return getDaoFactory().getActorsCatalogTransactionDao().createInsertTransactionStatementPair(transaction);


    }

    /**
     * Create a new row into the data base
     *
     * @param actorProfile
     * @throws CantInsertRecordDataBaseException
     */
    private ActorsCatalogTransaction createActorsCatalogTransaction(ActorProfile actorProfile, String transactionType)  {

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

        //Validate if location are available
        if (actorProfile.getLocation() != null){
            transaction.setLastLocation(actorProfile.getLocation().getLatitude(), actorProfile.getLocation().getLongitude());
        }else{
            transaction.setLastLocation(0.0, 0.0);
        }

        /*
         * Create Object transaction
         */
        return transaction;

    }


    /**
     * Create a new row into the data base
     *
     * @param transaction
     * @throws CantInsertRecordDataBaseException
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
    private boolean validateProfileChange(ActorProfile actorProfile) throws CantReadRecordDataBaseException, RecordNotFoundException {

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

        ActorsCatalog actorsCatalogRegister = getDaoFactory().getActorsCatalogDao().findById(actorProfile.getIdentityPublicKey());

        if (!actorsCatalogRegister.equals(actorsCatalog)){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

}
