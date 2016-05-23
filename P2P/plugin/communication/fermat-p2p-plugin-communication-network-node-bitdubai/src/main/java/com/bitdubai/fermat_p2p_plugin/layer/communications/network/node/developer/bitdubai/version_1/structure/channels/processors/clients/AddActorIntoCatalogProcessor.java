package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodePluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;

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
                        updateActorsCatalog(actorProfile);

                        /*
                         * Create the transaction
                         */
                        ActorsCatalogTransaction actorsCatalogTransaction = insertActorsCatalogTransaction(actorProfile, ActorsCatalogTransaction.UPDATE_TRANSACTION_TYPE);

                        /*
                         * Create the transaction for propagation
                         */
                        insertActorsCatalogTransactionsPendingForPropagation(actorsCatalogTransaction);

                    }

                }else {

                    LOG.info("New Profile proceed to insert into catalog");

                    /*
                     * Insert into the catalog
                     */
                    insertActorsCatalog(actorProfile);

                    /*
                     * Create the transaction
                     */
                    ActorsCatalogTransaction actorsCatalogTransaction = insertActorsCatalogTransaction(actorProfile, ActorsCatalogTransaction.ADD_TRANSACTION_TYPE);

                    /*
                     * Create the transaction for propagation
                     */
                    insertActorsCatalogTransactionsPendingForPropagation(actorsCatalogTransaction);
                }
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
    private void insertActorsCatalog(ActorProfile actorProfile) throws CantInsertRecordDataBaseException {

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
            actorsCatalog.setLastLatitude(actorProfile.getLocation().getLatitude());
            actorsCatalog.setLastLongitude(actorProfile.getLocation().getLongitude());
        }else{
            actorsCatalog.setLastLatitude(0.0);
            actorsCatalog.setLastLongitude(0.0);
        }

        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogDao().create(actorsCatalog);

    }


    /**
     * Update a row into the data base
     *
     * @param actorProfile
     * @throws CantInsertRecordDataBaseException
     */
    private void updateActorsCatalog(ActorProfile actorProfile) throws CantUpdateRecordDataBaseException, InvalidParameterException, RecordNotFoundException {

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
            actorsCatalog.setLastLatitude(actorProfile.getLocation().getLatitude());
            actorsCatalog.setLastLongitude(actorProfile.getLocation().getLongitude());
        }else{
            actorsCatalog.setLastLatitude(0.0);
            actorsCatalog.setLastLongitude(0.0);
        }

        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogDao().update(actorsCatalog);

    }

    /**
     * Create a new row into the data base
     *
     * @param actorProfile
     * @throws CantInsertRecordDataBaseException
     */
    private ActorsCatalogTransaction insertActorsCatalogTransaction(ActorProfile actorProfile, String transactionType) throws CantInsertRecordDataBaseException {

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
            transaction.setLastLatitude(actorProfile.getLocation().getLatitude());
            transaction.setLastLongitude(actorProfile.getLocation().getLongitude());
        }else{
            transaction.setLastLatitude(0.0);
            transaction.setLastLongitude(0.0);
        }

        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogTransactionDao().create(transaction);

        return transaction;

    }


    /**
     * Create a new row into the data base
     *
     * @param transaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertActorsCatalogTransactionsPendingForPropagation(ActorsCatalogTransaction transaction) throws CantInsertRecordDataBaseException {


        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogTransactionsPendingForPropagationDao().create(transaction);

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
            actorsCatalog.setLastLatitude(actorProfile.getLocation().getLatitude());
            actorsCatalog.setLastLongitude(actorProfile.getLocation().getLongitude());
        }else{
            actorsCatalog.setLastLatitude(0.0);
            actorsCatalog.setLastLongitude(0.0);
        }


        ActorsCatalog actorsCatalogRegister = getDaoFactory().getActorsCatalogDao().findById(actorProfile.getIdentityPublicKey());

        if (!actorsCatalogRegister.equals(actorsCatalog)){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

}
