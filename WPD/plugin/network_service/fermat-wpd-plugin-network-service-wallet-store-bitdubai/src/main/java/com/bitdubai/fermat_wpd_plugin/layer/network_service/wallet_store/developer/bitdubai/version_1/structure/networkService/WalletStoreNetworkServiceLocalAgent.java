package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.OutgoingMessageDAO;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by rodrigo on 8/11/15.
 */
public class WalletStoreNetworkServiceLocalAgent implements Observer {
    /**
     * Represent the public key of the remote network service
     */
    private String remoteNetworkServicePublicKey;

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
    private OutgoingMessageDAO outgoingMessageDao;

    /**
     * Constructor with parameters
     *
     * @param remoteNetworkServicePublicKey
     * @param errorManager                  instance
     * @param outgoingMessageDao            instance
     */
    public WalletStoreNetworkServiceLocalAgent(String remoteNetworkServicePublicKey, ErrorManager errorManager, EventManager eventManager, OutgoingMessageDAO outgoingMessageDao) {
        this.remoteNetworkServicePublicKey = remoteNetworkServicePublicKey;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.outgoingMessageDao = outgoingMessageDao;
    }


    /**
     * This method prepare the message to send and save on the
     * data base in the table <code>outgoing_messages</code>
     *
     * @param message the message to send
     */
    public void sendMessage(Message message) {

        try {

            /*
             * Cast the message to WalletStoreNetworkServiceMessage
             */
            WalletStoreNetworkServiceMessage outgoingIntraUserNetworkServiceMessage = (WalletStoreNetworkServiceMessage) message;

            /*
             * Configure the correct status
             */
            outgoingIntraUserNetworkServiceMessage.setStatus(MessagesStatus.PENDING_TO_SEND);

            /*
             * Save to the data base table
             */
            outgoingMessageDao.create(outgoingIntraUserNetworkServiceMessage);

        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message. Error reason: " + e.getMessage()));
        }

    }


    /**
     * Notify the client when a incoming message is receive by the incomingTemplateNetworkServiceMessage
     * ant fire a new event
     *
     * @param incomingTemplateNetworkServiceMessage received
     */
    private void onMessageReceived(WalletStoreNetworkServiceMessage incomingTemplateNetworkServiceMessage) {

        /**
         * Put the message on a event and fire new event
         */
        FermatEvent fermatEvent = eventManager.getNewEvent(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEvent.setSource(EventSource.NETWORK_SERVICE_TEMPLATE_PLUGIN);
        ((NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent).setData(incomingTemplateNetworkServiceMessage); //VALIDAR CON LUIS ESTE ATTRIBUTO
        eventManager.raiseEvent(fermatEvent);

    }

    /**
     * This method is called automatically when TemplateNetworkServiceRemoteAgent (Observable object) update the database
     * when new message is received
     *
     * @param observable the observable object
     * @param data       the data update
     */
    @Override
    public void update(Observable observable, Object data) {

        //Validate and process
        if (data instanceof WalletStoreNetworkServiceMessage)
            onMessageReceived((WalletStoreNetworkServiceMessage) data);
    }

    /**
     * Return the public key of the remote network service
     *
     * @return
     */
    public String getRemoteNetworkServicePublicKey() {
        return remoteNetworkServicePublicKey;
    }
}
