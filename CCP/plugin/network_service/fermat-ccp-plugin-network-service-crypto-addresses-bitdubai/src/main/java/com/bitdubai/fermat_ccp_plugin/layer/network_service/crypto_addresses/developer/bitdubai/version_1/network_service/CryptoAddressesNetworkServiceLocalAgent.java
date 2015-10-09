package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.exceptions.CantInsertRecordDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.database.OutgoingMessageDAO;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesNetworkServiceLocalAgent implements Observer {
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
     * @param remoteNetworkServicePublicKey public key
     * @param errorManager                  instance
     * @param outgoingMessageDao            instance
     */
    public CryptoAddressesNetworkServiceLocalAgent(String remoteNetworkServicePublicKey, ErrorManager errorManager, EventManager eventManager, OutgoingMessageDAO outgoingMessageDao) {
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
             * Cast the message to CryptoAddressesNetworkServiceMessage
             */
            CryptoAddressesNetworkServiceMessage outgoingCryptoAddressesNetworkServiceMessage = (CryptoAddressesNetworkServiceMessage) message;

            /*
             * Configure the correct status
             */
            outgoingCryptoAddressesNetworkServiceMessage.setStatus(MessagesStatus.PENDING_TO_SEND);

            /*
             * Save to the data base table
             */
            outgoingMessageDao.create(outgoingCryptoAddressesNetworkServiceMessage);

        } catch (CantInsertRecordDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message. Error reason: " + e.getMessage()));
        }

    }


    /**
     * Notify the client when a incoming message is receive by the incomingTemplateNetworkServiceMessage
     * ant fire a new event
     *
     * @param incomingTemplateNetworkServiceMessage received
     */
    private void onMessageReceived(CryptoAddressesNetworkServiceMessage incomingTemplateNetworkServiceMessage) {

        /**
         * Put the message on a event and fire new event
         */
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
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
        if (data instanceof CryptoAddressesNetworkServiceMessage)
            onMessageReceived((CryptoAddressesNetworkServiceMessage) data);
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
