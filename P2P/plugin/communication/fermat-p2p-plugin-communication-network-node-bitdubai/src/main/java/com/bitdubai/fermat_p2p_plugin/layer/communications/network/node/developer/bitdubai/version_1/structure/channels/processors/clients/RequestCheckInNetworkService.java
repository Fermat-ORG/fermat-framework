/*
 * @#RequestCheckInNetworkService.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.RequestProfileCheckInMsg;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.RespondProfileCheckInMsj;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInNetworkService;

import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.RequestCheckInNetworkService</code>
 * process all messages received the type <code>MessageType.REQUEST_CHECK_IN_NETWORK_SERVICE</code><p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RequestCheckInNetworkService extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(RequestCheckInNetworkService.class.getName());

    /**
     * Constructor  whit parameter
     *
     * @param webSocketChannelServerEndpoint register
     *
     */
    public RequestCheckInNetworkService(WebSocketChannelServerEndpoint webSocketChannelServerEndpoint) {
        super(webSocketChannelServerEndpoint, PackageType.REQUEST_CHECK_IN_NETWORK_SERVICE);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get("");
        NetworkServiceProfile networkServiceProfile = null;

        try {

            RequestProfileCheckInMsg messageContent = (RequestProfileCheckInMsg) packageReceived.getContent();

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getProfileToRegister()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON){

                /*
                 * Obtain the profile of the network service
                 */
                networkServiceProfile = (NetworkServiceProfile) messageContent.getProfileToRegister();

                /*
                 * Create the checkedInNetworkService
                 */
                CheckedInNetworkService checkedInNetworkService = new CheckedInNetworkService();
                checkedInNetworkService.setIdentityPublicKey(networkServiceProfile.getIdentityPublicKey());
                checkedInNetworkService.setClientIdentityPublicKey(networkServiceProfile.getClientIdentityPublicKey());
                checkedInNetworkService.setNetworkServiceType(networkServiceProfile.getNetworkServiceType().getCode());

                //Validate if location are available
                if (networkServiceProfile.getLocation() != null){
                    checkedInNetworkService.setLatitude(networkServiceProfile.getLocation().getLatitude());
                    checkedInNetworkService.setLongitude(networkServiceProfile.getLocation().getLongitude());
                }

                /*
                 * If all ok, respond whit success message
                 */
                RespondProfileCheckInMsj respondProfileCheckInMsj = new RespondProfileCheckInMsj(RespondProfileCheckInMsj.STATUS.SUCCESS, RespondProfileCheckInMsj.STATUS.SUCCESS.toString(), networkServiceProfile.getIdentityPublicKey());
                Package packageRespond = Package.createInstance(respondProfileCheckInMsj, packageReceived.getNetworkServiceTypeSource(), PackageType.RESPOND_CHECK_IN_NETWORK_SERVICE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
                RespondProfileCheckInMsj respondProfileCheckInMsj = new RespondProfileCheckInMsj(RespondProfileCheckInMsj.STATUS.FAIL, exception.getLocalizedMessage(), networkServiceProfile.getIdentityPublicKey());
                Package packageRespond = Package.createInstance(respondProfileCheckInMsj, packageReceived.getNetworkServiceTypeSource(), PackageType.RESPOND_CHECK_IN_CLIENT, channelIdentityPrivateKey, destinationIdentityPublicKey);

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

}
