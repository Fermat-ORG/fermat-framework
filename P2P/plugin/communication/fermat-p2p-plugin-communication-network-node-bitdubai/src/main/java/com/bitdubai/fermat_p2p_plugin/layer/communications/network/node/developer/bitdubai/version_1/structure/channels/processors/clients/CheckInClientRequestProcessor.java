/*
 * @#CheckInClientRequestProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;


import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileMsjRespond;
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
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckInClientRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(CheckInClientRequestProcessor.class.getName());

    /**
     * Constructor whit parameter
     *
     * @param webSocketChannelServerEndpoint register
     */
    public CheckInClientRequestProcessor(WebSocketChannelServerEndpoint webSocketChannelServerEndpoint) {
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
        ClientProfile clientProfile = null;

        try {

            CheckInProfileMsgRequest messageContent = (CheckInProfileMsgRequest) packageReceived.getContent();

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getProfileToRegister()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON){

                /*
                 * Obtain the profile of the client
                 */
                clientProfile = (ClientProfile) messageContent.getProfileToRegister();

                /*
                 * CheckedInClient into data base
                 */
                insertCheckedInClient(clientProfile);

                /*
                 * CheckedClientsHistory into data base
                 */
                insertCheckedClientsHistory(clientProfile);

                /*
                 * If all ok, respond whit success message
                 */
                CheckInProfileMsjRespond respondProfileCheckInMsj = new CheckInProfileMsjRespond(CheckInProfileMsjRespond.STATUS.SUCCESS,  CheckInProfileMsjRespond.STATUS.SUCCESS.toString(), clientProfile.getIdentityPublicKey());
                Package packageRespond = Package.createInstance(respondProfileCheckInMsj, packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_IN_CLIENT_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getBasicRemote().sendObject(packageRespond);

            }

        }catch (Exception exception){

            try {

                LOG.error(exception.getMessage());

                /*
                 * Respond whit fail message
                 */
                CheckInProfileMsjRespond respondProfileCheckInMsj = new CheckInProfileMsjRespond(CheckInProfileMsjRespond.STATUS.FAIL, exception.getLocalizedMessage(), clientProfile.getIdentityPublicKey());
                Package packageRespond = Package.createInstance(respondProfileCheckInMsj, packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_IN_CLIENT_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
     * Create a new row into the data base
     *
     * @param clientProfile
     * @throws CantInsertRecordDataBaseException
     */
    private void insertCheckedInClient(ClientProfile clientProfile) throws CantInsertRecordDataBaseException {

        /*
         * Create the CheckedInClient
         */
        CheckedInClient checkedInClient = new CheckedInClient();
        checkedInClient.setIdentityPublicKey(clientProfile.getIdentityPublicKey());
        checkedInClient.setDeviceType(clientProfile.getDeviceType());

        //Validate if location are available
        if (clientProfile.getLocation() != null){
            checkedInClient.setLatitude(clientProfile.getLocation().getLatitude());
            checkedInClient.setLongitude(clientProfile.getLocation().getLongitude());
        }

        /*
         * Save into the data base
         */
        getDaoFactory().getCheckedInClientDao().create(checkedInClient);
    }

    /**
     * Create a new row into the data base
     *
     * @param clientProfile
     * @throws CantInsertRecordDataBaseException
     */
    private void insertCheckedClientsHistory(ClientProfile clientProfile) throws CantInsertRecordDataBaseException {

        /*
         * Create the CheckedClientsHistory
         */
        CheckedClientsHistory checkedClientsHistory = new CheckedClientsHistory();
        checkedClientsHistory.setIdentityPublicKey(clientProfile.getIdentityPublicKey());
        checkedClientsHistory.setDeviceType(clientProfile.getDeviceType());
        checkedClientsHistory.setCheckType(CheckedClientsHistory.CHECK_TYPE_IN);

        //Validate if location are available
        if (clientProfile.getLocation() != null){
            checkedClientsHistory.setLastLatitude(clientProfile.getLocation().getLatitude());
            checkedClientsHistory.setLastLongitude(clientProfile.getLocation().getLongitude());
        }

        /*
         * Save into the data base
         */
        getDaoFactory().getCheckedClientsHistoryDao().create(checkedClientsHistory);

    }

}
