package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * The abstract Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractNewReceiveMessagesNotificationEventHandler</code>
 * contains all the basic functionality of a NewReceiveMessagesNotificationEventHandler.
 *
 * The method <code>handleNewMessages</code> can be override to modify its behavior.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractNewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    protected final AbstractNetworkService networkService;

    /**
     * Constructor with parameter.
     */
    public AbstractNewReceiveMessagesNotificationEventHandler(final AbstractNetworkService networkService) {

        this.networkService = networkService;
    }

    @Override
    public final void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (networkService.isStarted()) {

            if (fermatEvent instanceof NewNetworkServiceMessageReceivedNotificationEvent) {
                NewNetworkServiceMessageReceivedNotificationEvent newNetworkServiceMessageSentNotificationEvent = (NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent;

                if(newNetworkServiceMessageSentNotificationEvent.getNetworkServiceTypeApplicant() == networkService.getPlatformComponentProfilePluginRoot().getNetworkServiceType())
                    this.handleNewMessages((FermatMessage) newNetworkServiceMessageSentNotificationEvent.getData());

            } else {
                P2pEventType eventExpected = P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                        "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        }
    }

    protected void handleNewMessages(final FermatMessage message) throws FermatException {
        networkService.handleNewMessages(message);
    }
}
