package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckOutProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckOutProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedActorsHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckOutActorRequestProcessor</code>
 * process all packages received the type <code>MessageType.CHECK_OUT_ACTOR_REQUEST</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 29/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckOutActorRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(CheckOutActorRequestProcessor.class));

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public CheckOutActorRequestProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.CHECK_OUT_ACTOR_REQUEST);
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
        String profileIdentity = null;

        try {

            CheckOutProfileMsgRequest messageContent = CheckOutProfileMsgRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getIdentityPublicKey()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON){

                /*
                 * Obtain the profile identity
                 */
                profileIdentity = messageContent.getIdentityPublicKey();

                /*
                 * Load from Database
                 */
                CheckedInActor checkedInActor = getDaoFactory().getCheckedInActorDao().findEntityByFilter(
                        CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                        profileIdentity);

                /*
                 * Validate if exist
                 */
                if (checkedInActor != null){

                    /*
                     * Delete from data base
                     */
                    deleteCheckedInActor(profileIdentity);

                    /*
                     * CheckedActorsHistory into data base
                     */
                    insertCheckedActorsHistory(checkedInActor);

                    /*
                     * If all ok, respond whit success message
                     */
                    CheckOutProfileMsjRespond checkOutProfileMsjRespond = new CheckOutProfileMsjRespond(CheckOutProfileMsjRespond.STATUS.SUCCESS, CheckOutProfileMsjRespond.STATUS.SUCCESS.toString(), profileIdentity);
                    Package packageRespond = Package.createInstance(checkOutProfileMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_OUT_ACTOR_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

                    /*
                     * Send the respond
                     */
                    session.getBasicRemote().sendObject(packageRespond);

                }else{

                    throw new Exception("The Profile is no actually check in");
                }

            }

        }catch (Exception exception){

            try {

                LOG.error(exception.getMessage());

                /*
                 * Respond whit fail message
                 */
                CheckOutProfileMsjRespond checkOutProfileMsjRespond = new CheckOutProfileMsjRespond(CheckOutProfileMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), profileIdentity);
                Package packageRespond = Package.createInstance(checkOutProfileMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_OUT_ACTOR_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getBasicRemote().sendObject(packageRespond);

            } catch (IOException iOException) {
                LOG.error(iOException.getMessage());
            } catch (EncodeException encodeException) {
                LOG.error(encodeException.getMessage());
            }

        }

    }

    /**
     * Delete a row from the data base
     *
     * @param profileIdentity
     * @throws CantInsertRecordDataBaseException
     */
    private void deleteCheckedInActor(String profileIdentity) throws CantDeleteRecordDataBaseException, RecordNotFoundException, CantReadRecordDataBaseException {

        /*
         * validate if exists
         */
        if(getDaoFactory().getCheckedInActorDao().exists(profileIdentity)) {
        /*
         * delete from the data base
         */
            getDaoFactory().getCheckedInActorDao().delete(profileIdentity);
        }

    }

    /**
     * Create a new row into the data base
     *
     * @param checkedInActor
     * @throws CantInsertRecordDataBaseException
     */
    private void insertCheckedActorsHistory(CheckedInActor checkedInActor) throws CantInsertRecordDataBaseException {

        /*
         * Create the CheckedActorsHistory
         */
        CheckedActorsHistory checkedActorsHistory = new CheckedActorsHistory();
        checkedActorsHistory.setIdentityPublicKey(checkedInActor.getIdentityPublicKey());
        checkedActorsHistory.setActorType(checkedInActor.getActorType());
        checkedActorsHistory.setAlias(checkedInActor.getAlias());
        checkedActorsHistory.setName(checkedInActor.getName());
        checkedActorsHistory.setPhoto(checkedInActor.getPhoto());
        checkedActorsHistory.setExtraData(checkedInActor.getExtraData());
        checkedActorsHistory.setLastLatitude((checkedInActor.getLatitude() != null ? checkedInActor.getLatitude() : 0.0));
        checkedActorsHistory.setLastLongitude((checkedInActor.getLongitude() != null ? checkedInActor.getLongitude() : 0.0));
        checkedActorsHistory.setCheckType(CheckedActorsHistory.CHECK_TYPE_OUT);

        /*
         * Save into the data base
         */
        getDaoFactory().getCheckedActorsHistoryDao().create(checkedActorsHistory);

    }

}
