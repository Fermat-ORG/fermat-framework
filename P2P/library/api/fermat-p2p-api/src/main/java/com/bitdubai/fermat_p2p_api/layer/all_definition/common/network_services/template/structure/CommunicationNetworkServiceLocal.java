/*
 * @#TemplateNetworkServiceLocal.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

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
public class CommunicationNetworkServiceLocal implements Observer, NetworkServiceLocal {

    private final NetworkService networkServicePluginRoot;
    /**
     * Represent the profile of the remote network service
     */
    private PlatformComponentProfile remoteNetworkServiceProfile;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDao outgoingMessageDao;

    /**
     * Represent the lastMessageReceived
     */
    private FermatMessage lastMessageReceived;

    /**
     * Constructor with parameters
     *
     * @param remoteNetworkServiceProfile
     * @param errorManager                  instance
     * @param outgoingMessageDao            instance
     */
    public CommunicationNetworkServiceLocal(PlatformComponentProfile remoteNetworkServiceProfile,
                                            ErrorManager errorManager, EventManager eventManager,
                                            OutgoingMessageDao outgoingMessageDao,
                                            NetworkService networkService) {
        this.remoteNetworkServiceProfile = remoteNetworkServiceProfile;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.outgoingMessageDao = outgoingMessageDao;
        this.networkServicePluginRoot = networkService;
    }


    /**
     * (non-javadoc)
     */
    public void sendMessage(final String senderIdentityPublicKey,final String receiverPublicKey, final String messageContent) {

        try {

            FermatMessage fermatMessage  = FermatMessageCommunicationFactory.constructFermatMessage(senderIdentityPublicKey,  //Sender NetworkService
                                                                                                    receiverPublicKey,   //Receiver
                                                                                                    messageContent,                //Message Content
                                                                                                    FermatMessageContentType.TEXT);//Type
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

        System.out.println("CommunicationNetworkServiceLocal - onMessageReceived ");
        System.out.println(incomingMessage.getContent());

        /*
         * set the last message received
         */
        this.lastMessageReceived = incomingMessage;
        /**
         * Put the message on a event and fire new event
         */
        //FermatEvent fermatEvent = eventManager.getNewEvent(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        //TODO: no source
//        fermatEvent.setSource();
//        ((NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent).setData(incomingMessage);
//        ((NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent).setNetworkServiceTypeApplicant(networkServiceTypePluginRoot);
//        eventManager.raiseEvent(fermatEvent);
        networkServicePluginRoot.handleNewMessages(incomingMessage);
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

    /**
     * (non-javadoc)
     * @see NetworkServiceLocal#
     */
    public FermatMessage getLastMessageReceived() {
        return lastMessageReceived;
    }
}
