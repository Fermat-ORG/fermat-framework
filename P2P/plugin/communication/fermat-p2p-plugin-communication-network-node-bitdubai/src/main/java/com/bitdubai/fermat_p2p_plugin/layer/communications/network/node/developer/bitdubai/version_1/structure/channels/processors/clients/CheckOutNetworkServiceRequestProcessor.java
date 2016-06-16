package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckOutProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckOutProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInNetworkService;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedNetworkServicesHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInNetworkServiceRequestProcessor</code>
 * process all packages received the type <code>MessageType.CHECK_IN_NETWORK_SERVICE_REQUEST</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckOutNetworkServiceRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(CheckOutNetworkServiceRequestProcessor.class));

    /**
     * Constructor  whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     *
     */
    public CheckOutNetworkServiceRequestProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.CHECK_OUT_NETWORK_SERVICE_REQUEST);
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
                CheckedInNetworkService checkedInNetworkService = getDaoFactory().getCheckedInNetworkServiceDao().findEntityByFilter(
                        CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                        profileIdentity);

                /*
                 * Validate if exist
                 */
                if (checkedInNetworkService != null){

                    // create transaction for
                    DatabaseTransaction databaseTransaction = getDaoFactory().getCheckedInNetworkServiceDao().getNewTransaction();
                    DatabaseTransactionStatementPair pair;

                    /*
                     * Delete from data base
                     */
                    pair = deleteCheckedInNetworkService(profileIdentity);
                    databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());

                    /*
                     * CheckedInNetworkServiceHistory into data base
                     */
                    pair = insertCheckedInNetworkServiceHistory(checkedInNetworkService);
                    databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                    databaseTransaction.execute();

                    /*
                     * If all ok, respond whit success message
                     */
                    CheckOutProfileMsjRespond checkOutProfileMsjRespond = new CheckOutProfileMsjRespond(CheckOutProfileMsjRespond.STATUS.SUCCESS, CheckOutProfileMsjRespond.STATUS.SUCCESS.toString(), profileIdentity);
                    Package packageRespond = Package.createInstance(checkOutProfileMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_OUT_NETWORK_SERVICE_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
                Package packageRespond = Package.createInstance(checkOutProfileMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_OUT_NETWORK_SERVICE_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
     * @throws CantDeleteRecordDataBaseException
     * @throws RecordNotFoundException
     */
    private DatabaseTransactionStatementPair deleteCheckedInNetworkService(String profileIdentity) throws CantDeleteRecordDataBaseException, RecordNotFoundException, CantReadRecordDataBaseException, CantCreateTransactionStatementPairException {

        /*
         * validate if exists
         */
        return getDaoFactory().getCheckedInNetworkServiceDao().createDeleteTransactionStatementPair(profileIdentity);

    }

    /**
     * Create a new row into the data base
     *
     * @param checkedInNetworkService
     * @throws CantInsertRecordDataBaseException
     */
    private DatabaseTransactionStatementPair insertCheckedInNetworkServiceHistory(CheckedInNetworkService checkedInNetworkService) throws CantInsertRecordDataBaseException, CantCreateTransactionStatementPairException {

        /*
         * Create the ClientsRegistrationHistory
         */
        CheckedNetworkServicesHistory checkedNetworkServicesHistory = new CheckedNetworkServicesHistory();
        checkedNetworkServicesHistory.setIdentityPublicKey(checkedInNetworkService.getIdentityPublicKey());
        checkedNetworkServicesHistory.setClientIdentityPublicKey(checkedInNetworkService.getClientIdentityPublicKey());
        checkedNetworkServicesHistory.setNetworkServiceType(checkedInNetworkService.getNetworkServiceType());
        checkedNetworkServicesHistory.setLastLatitude(checkedInNetworkService.getLatitude());
        checkedNetworkServicesHistory.setLastLongitude(checkedInNetworkService.getLongitude());
        checkedNetworkServicesHistory.setCheckType(CheckedNetworkServicesHistory.CHECK_TYPE_OUT);

        /*
         * Save into the data base
         */
        return getDaoFactory().getCheckedNetworkServicesHistoryDao().createInsertTransactionStatementPair(checkedNetworkServicesHistory);

    }

}
