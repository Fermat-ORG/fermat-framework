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
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInClient;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ClientsRegistrationHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums.RegistrationResult;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums.RegistrationType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInClientRequestProcessor</code>
 * process all packages received the type <code>PackageType.CHECK_IN_CLIENT_REQUEST</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 29/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckOutClientRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(CheckOutClientRequestProcessor.class));

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public CheckOutClientRequestProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.CHECK_OUT_CLIENT_REQUEST);
    }

    /**
     * (non-javadoc)
     *
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
            if (messageContent.getMessageContentType() == MessageContentType.JSON) {

                /*
                * Obtain the profile identity
                */
                profileIdentity = messageContent.getIdentityPublicKey();

                /*
                 * Load from Database
                 */
                CheckedInClient checkedInClient = getDaoFactory().getCheckedInClientDao().findById(profileIdentity);

                /*
                 * Validate if exist
                 */
                if (checkedInClient != null) {

                    // create transaction for
                    DatabaseTransaction databaseTransaction = getDaoFactory().getCheckedInClientDao().getNewTransaction();
                    DatabaseTransactionStatementPair pair;

                    /*
                     * CheckedInClient into data base
                     */
                    pair = deleteCheckedInClient(profileIdentity);
                    databaseTransaction.addRecordToDelete(pair.getTable(), pair.getRecord());

                    /*
                     * ClientsRegistrationHistory into data base
                     */
                    pair = insertClientsRegistrationHistory(checkedInClient, RegistrationResult.SUCCESS, null);
                    databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

                    databaseTransaction.execute();

                    /*
                     * If all ok, respond whit success message
                     */
                    CheckOutProfileMsjRespond checkOutProfileMsjRespond = new CheckOutProfileMsjRespond(CheckOutProfileMsjRespond.STATUS.SUCCESS, CheckOutProfileMsjRespond.STATUS.SUCCESS.toString(), profileIdentity);
                    Package packageRespond = Package.createInstance(checkOutProfileMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_OUT_CLIENT_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                    /*
                     * Send the respond
                     */
                    session.getAsyncRemote().sendObject(packageRespond);

                } else {
                    throw new Exception("The Profile is no actually check in");
                }

            }

        } catch (Exception exception) {

            try {

                LOG.error(exception.getMessage());

                /*
                 * Respond whit fail message
                 */
                CheckOutProfileMsjRespond checkOutProfileMsjRespond = new CheckOutProfileMsjRespond(CheckOutProfileMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), profileIdentity);
                Package packageRespond = Package.createInstance(checkOutProfileMsjRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_OUT_CLIENT_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
     * Delete a row from the data base
     *
     * @param profileIdentity
     *
     * @throws CantDeleteRecordDataBaseException
     * @throws RecordNotFoundException
     */
    private DatabaseTransactionStatementPair deleteCheckedInClient(String profileIdentity) throws CantCreateTransactionStatementPairException {

        /*
         * Create statement.
         */
        return getDaoFactory().getCheckedInClientDao().createDeleteTransactionStatementPair(profileIdentity);

    }

    /**
     * Create a new row into the data base
     *
     * @param checkedInClient data of the client.
     * @param result          of the registration.
     * @param detail          of the registration.
     *
     * @throws CantInsertRecordDataBaseException if something goes wrong.
     */
    private DatabaseTransactionStatementPair insertClientsRegistrationHistory(final CheckedInClient    checkedInClient,
                                                  final RegistrationResult result         ,
                                                  final String             detail         ) throws CantCreateTransactionStatementPairException {

        /*
         * Create the ClientsRegistrationHistory
         */
        ClientsRegistrationHistory clientsRegistrationHistory = new ClientsRegistrationHistory();
        clientsRegistrationHistory.setIdentityPublicKey(checkedInClient.getIdentityPublicKey());
        clientsRegistrationHistory.setDeviceType(checkedInClient.getDeviceType());
        clientsRegistrationHistory.setType(RegistrationType.CHECK_OUT);
        clientsRegistrationHistory.setResult(result);
        clientsRegistrationHistory.setDetail(detail);

        /*
         * Save into the data base
         */
        return getDaoFactory().getClientsRegistrationHistoryDao().createInsertTransactionStatementPair(clientsRegistrationHistory);
    }

}
