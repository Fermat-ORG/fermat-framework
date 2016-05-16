/*
 * @#TemplateNetworkServiceLocal.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.daos.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Observable;
import java.util.Observer;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal</code> represent
 * the remote network services locally
 * <p/>
 * This class extend of the <code>java.util.Observer</code> class,  its used on the software design pattern called: The observer pattern,
 * for more info see @link https://en.wikipedia.org/wiki/Observer_pattern
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CommunicationNetworkServiceLocal implements Observer, NetworkServiceLocal {

    /**
     * Represent the profile of the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the profile of the remote network service
     */
    private PlatformComponentProfile remoteComponentProfile;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDao outgoingMessageDao;

    /**
     *
     * @param communicationNetworkServiceConnectionManager
     */
    public CommunicationNetworkServiceLocal(CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager, PlatformComponentProfile remoteComponentProfile, ErrorManager errorManager) {
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.remoteComponentProfile                       = remoteComponentProfile;
        this.errorManager                                 = errorManager;
        this.outgoingMessageDao                           = communicationNetworkServiceConnectionManager.getOutgoingMessageDao();
    }

    public void sendMessage(final String                senderIdentityPublicKey,
                            final PlatformComponentType senderType             ,
                            final NetworkServiceType    senderNsType             ,
                            final String                messageContent         ) {

        try {

            FermatMessage fermatMessage  = FermatMessageCommunicationFactory.constructFermatMessage(
                    senderIdentityPublicKey,                           // Sender NetworkService
                    senderType,                                        // Sender type
                    senderNsType,                                      // Sender NS type
                    remoteComponentProfile.getIdentityPublicKey(),     // Receiver
                    remoteComponentProfile.getPlatformComponentType(), // Receiver Type
                    remoteComponentProfile.getNetworkServiceType(),    // Receiver NS type
                    messageContent,                                    // Message Content
                    FermatMessageContentType.TEXT                      // Type
            );
            /*
             * Configure the correct status
             */
            ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);

            /*
             * Save to the data base table
             */
            outgoingMessageDao.create(fermatMessage);

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message. Error reason: " + e.getMessage()));
        }

    }


    /**
     * Notify the client when a incoming message is receive by the incomingTemplateNetworkServiceMessage
     * ant fire a new event
     *
     * @param incomingMessage received
     */
    private void onMessageReceived(FermatMessage incomingMessage) {

        try {

            /*
             * process the new message receive
             */
            communicationNetworkServiceConnectionManager.getNetworkServiceRoot().onNewMessagesReceive(incomingMessage);

            ((FermatMessageCommunication) incomingMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
            communicationNetworkServiceConnectionManager.getIncomingMessageDao().update(incomingMessage);

        } catch (CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called automatically when CommunicationNetworkServiceRemoteAgent (Observable object) update the database
     * when new message is received
     *
     * @param observable the observable object
     * @param data       the data update
     */
    @Override
    public void update(Observable observable, Object data) {

        //Validate and process
        if (data instanceof FermatMessage) {
            onMessageReceived((FermatMessage) data);
        }
    }
}
