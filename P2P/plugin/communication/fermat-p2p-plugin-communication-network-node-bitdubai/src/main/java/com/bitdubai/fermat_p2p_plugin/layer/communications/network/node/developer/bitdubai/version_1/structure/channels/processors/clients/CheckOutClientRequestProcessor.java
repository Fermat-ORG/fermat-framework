/*
 * @#CheckInClientRequestProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;


import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckOutProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckOutProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedClientsHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInClient;

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
    private final Logger LOG = Logger.getLogger(CheckOutClientRequestProcessor.class.getName());

    /**
     * Constructor whit parameter
     *
     * @param webSocketChannelServerEndpoint register
     */
    public CheckOutClientRequestProcessor(WebSocketChannelServerEndpoint webSocketChannelServerEndpoint) {
        super(webSocketChannelServerEndpoint, PackageType.CHECK_IN_CLIENT_REQUEST);
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

            CheckOutProfileMsgRequest messageContent = (CheckOutProfileMsgRequest) packageReceived.getContent();

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getIdentityPublicKey()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.TEXT){

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
                if (checkedInClient != null){

                    /*
                     * CheckedInClient into data base
                     */
                    deleteCheckedInClient(profileIdentity);

                    /*
                     * CheckedClientsHistory into data base
                     */
                    insertCheckedClientsHistory(checkedInClient);

                    /*
                     * If all ok, respond whit success message
                     */
                    CheckOutProfileMsjRespond checkOutProfileMsjRespond = new CheckOutProfileMsjRespond(CheckOutProfileMsjRespond.STATUS.SUCCESS,  CheckOutProfileMsjRespond.STATUS.SUCCESS.toString(), profileIdentity);
                    Package packageRespond = Package.createInstance(checkOutProfileMsjRespond, packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_IN_CLIENT_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
                Package packageRespond = Package.createInstance(checkOutProfileMsjRespond, packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_IN_CLIENT_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
    private void deleteCheckedInClient(String profileIdentity) throws CantDeleteRecordDataBaseException, RecordNotFoundException {

        /*
         * Delete from the data base
         */
        getDaoFactory().getCheckedInClientDao().delete(profileIdentity);
    }

    /**
     * Create a new row into the data base
     *
     * @param checkedInClient
     * @throws CantInsertRecordDataBaseException
     */
    private void insertCheckedClientsHistory(CheckedInClient checkedInClient) throws CantInsertRecordDataBaseException {

        /*
         * Create the CheckedClientsHistory
         */
        CheckedClientsHistory checkedClientsHistory = new CheckedClientsHistory();
        checkedClientsHistory.setIdentityPublicKey(checkedInClient.getIdentityPublicKey());
        checkedClientsHistory.setDeviceType(checkedInClient.getDeviceType());
        checkedClientsHistory.setLastLatitude(checkedInClient.getLatitude());
        checkedClientsHistory.setLastLongitude(checkedInClient.getLongitude());
        checkedClientsHistory.setCheckType(CheckedClientsHistory.CHECK_TYPE_OUT);

        /*
         * Save into the data base
         */
        getDaoFactory().getCheckedClientsHistoryDao().create(checkedClientsHistory);

    }

}
