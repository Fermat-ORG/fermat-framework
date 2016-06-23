package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedActorsHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInActorRequestProcessor</code>
 * process all packages received the type <code>MessageType.CHECK_IN_ACTOR_REQUEST</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckInActorRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(CheckInActorRequestProcessor.class));

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public CheckInActorRequestProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.CHECK_IN_ACTOR_REQUEST);
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
                DatabaseTransaction databaseTransaction = getDaoFactory().getCheckedInActorDao().getNewTransaction();
                DatabaseTransactionStatementPair pair;

                /*
                 * CheckedInActor into data base
                 */
                pair = insertCheckedInActor(actorProfile);

                if(!getDaoFactory().getCheckedInActorDao().exists(actorProfile.getIdentityPublicKey())) {
                    databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());
                }else {

                    boolean hasChanges = validateProfileChange(actorProfile);

                    if(hasChanges)
                        databaseTransaction.addRecordToUpdate(pair.getTable(), pair.getRecord());

                }

                /*
                 * CheckedActorsHistory into data base
                 */
                pair = insertCheckedActorsHistory(actorProfile);
                databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                databaseTransaction.execute();

                /*
                 * If all ok, respond whit success message
                 */
                CheckInProfileMsjRespond respondProfileCheckInMsj = new CheckInProfileMsjRespond(CheckInProfileMsjRespond.STATUS.SUCCESS, CheckInProfileMsjRespond.STATUS.SUCCESS.toString(), actorProfile.getIdentityPublicKey());
                Package packageRespond = Package.createInstance(respondProfileCheckInMsj.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_IN_ACTOR_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

            }

        }catch (Exception exception){

            try {

                exception.printStackTrace();
                LOG.error(exception.getCause());

                /*
                 * Respond whit fail message
                 */
                CheckInProfileMsjRespond respondProfileCheckInMsj = new CheckInProfileMsjRespond(CheckInProfileMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), actorProfile.getIdentityPublicKey());
                Package packageRespond = Package.createInstance(respondProfileCheckInMsj.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_IN_ACTOR_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

                exception.printStackTrace();

            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }

    }

    /**
     * Create a new row into the data base
     *
     * @param actorProfile
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertCheckedInActor(final ActorProfile actorProfile) throws CantCreateTransactionStatementPairException, CantReadRecordDataBaseException {



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


          if(!getDaoFactory().getCheckedInActorDao().exists(actorProfile.getIdentityPublicKey()))
             return getDaoFactory().getCheckedInActorDao().createInsertTransactionStatementPair(checkedInActor);
          else
              return getDaoFactory().getCheckedInActorDao().createUpdateTransactionStatementPair(checkedInActor);

    }

    /**
     * Create a new row into the data base
     *
     * @param actorProfile
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertCheckedActorsHistory(ActorProfile actorProfile) throws CantCreateTransactionStatementPairException {

        /*
         * Create the CheckedActorsHistory
         */
        CheckedActorsHistory checkedActorsHistory = new CheckedActorsHistory();
        checkedActorsHistory.setIdentityPublicKey(actorProfile.getIdentityPublicKey());
        checkedActorsHistory.setActorType(actorProfile.getActorType());
        checkedActorsHistory.setAlias(actorProfile.getAlias());
        checkedActorsHistory.setName(actorProfile.getName());
        checkedActorsHistory.setPhoto(actorProfile.getPhoto());
        checkedActorsHistory.setExtraData(actorProfile.getExtraData());
        checkedActorsHistory.setCheckType(CheckedActorsHistory.CHECK_TYPE_IN);
        checkedActorsHistory.setClientIdentityPublicKey(actorProfile.getClientIdentityPublicKey());

        //Validate if location are available
        if (actorProfile.getLocation() != null){
            checkedActorsHistory.setLastLatitude(actorProfile.getLocation().getLatitude());
            checkedActorsHistory.setLastLongitude(actorProfile.getLocation().getLongitude());
        }else{
            checkedActorsHistory.setLastLatitude(0.0);
            checkedActorsHistory.setLastLongitude(0.0);
        }

        /*
         * Save into the data base
         */
        return getDaoFactory().getCheckedActorsHistoryDao().createInsertTransactionStatementPair(checkedActorsHistory);

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

        CheckedInActor actorsRegistered = getDaoFactory().getCheckedInActorDao().findById(actorProfile.getIdentityPublicKey());

        if (!actorsRegistered.equals(checkedInActor)){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }

    }

}
