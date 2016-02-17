/*
 * @#CheckInNetworkServiceRequestProcessor.java - 2015
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInNetworkService;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedNetworkServicesHistory;

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
    private final Logger LOG = Logger.getLogger(CheckOutNetworkServiceRequestProcessor.class.getName());

    /**
     * Constructor  whit parameter
     *
     * @param webSocketChannelServerEndpoint register
     *
     */
    public CheckOutNetworkServiceRequestProcessor(WebSocketChannelServerEndpoint webSocketChannelServerEndpoint) {
        super(webSocketChannelServerEndpoint, PackageType.CHECK_IN_NETWORK_SERVICE_REQUEST);
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
                CheckedInNetworkService checkedInNetworkService = getDaoFactory().getCheckedInNetworkServiceDao().findById(profileIdentity);

                /*
                 * Validate if exist
                 */
                if (checkedInNetworkService != null){

                    /*
                     * Delete from data base
                     */
                    deleteCheckedInNetworkService(profileIdentity);

                    /*
                     * CheckedInNetworkServiceHistory into data base
                     */
                    insertCheckedInNetworkServiceHistory(checkedInNetworkService);

                    /*
                     * If all ok, respond whit success message
                     */
                    CheckOutProfileMsjRespond checkOutProfileMsjRespond = new CheckOutProfileMsjRespond(CheckOutProfileMsjRespond.STATUS.SUCCESS, CheckOutProfileMsjRespond.STATUS.SUCCESS.toString(), profileIdentity);
                    Package packageRespond = Package.createInstance(checkOutProfileMsjRespond, packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_IN_NETWORK_SERVICE_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
    private void deleteCheckedInNetworkService(String profileIdentity) throws CantDeleteRecordDataBaseException, RecordNotFoundException {

        /*
         * Delete from database
         */
        getDaoFactory().getCheckedInNetworkServiceDao().delete(profileIdentity);
    }

    /**
     * Create a new row into the data base
     *
     * @param checkedInNetworkService
     * @throws CantInsertRecordDataBaseException
     */
    private void insertCheckedInNetworkServiceHistory(CheckedInNetworkService checkedInNetworkService) throws CantInsertRecordDataBaseException {

        /*
         * Create the CheckedClientsHistory
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
        getDaoFactory().getCheckedNetworkServicesHistoryDao().create(checkedNetworkServicesHistory);

    }

}
