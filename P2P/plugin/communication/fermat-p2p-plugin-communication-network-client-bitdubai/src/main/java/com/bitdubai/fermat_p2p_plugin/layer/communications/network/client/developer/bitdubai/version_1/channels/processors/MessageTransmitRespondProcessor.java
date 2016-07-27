/*
* @#MessageTransmitRespondProcessor.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageDeliveredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageFailedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MessageTransmitRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.MessageTransmitRespondProcessor</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 15/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MessageTransmitRespondProcessor extends PackageProcessor{

    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public MessageTransmitRespondProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.MESSAGE_TRANSMIT_RESPONSE
        );
    }


    @Override
    public void processingPackage(Session session,Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        MessageTransmitRespond messageTransmitRespond = MessageTransmitRespond.parseContent(packageReceived.getContent());

        System.out.println(messageTransmitRespond.toJson());

        if(messageTransmitRespond.getStatus() == MessageTransmitRespond.STATUS.SUCCESS){

            /*
             * Create a raise a new event whit NETWORK_CLIENT_SENT_MESSAGE_DELIVERED
             */
//            System.out.println("12345P2P MENSAJE CONFIRMADO");
            FermatEvent event = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_SENT_MESSAGE_DELIVERED);
            event.setSource(EventSource.NETWORK_CLIENT);

            ((NetworkClientNewMessageDeliveredEvent) event).setId(messageTransmitRespond.getMessageId().toString());
            ((NetworkClientNewMessageDeliveredEvent) event).setNetworkServiceTypeSource(packageReceived.getNetworkServiceTypeSource());

            /*
             * Raise the event
             */
            System.out.println("MessageTransmitRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_SENT_MESSAGE_DELIVERED");
            getEventManager().raiseEvent(event);


        }else{
            //TODO: cambiar a pending to send y al límite de intentos, cambiar a fallido
             /*
             * Create a raise a new event whit NETWORK_CLIENT_SENT_MESSAGE_FAILED
             */
//            System.out.println("12345P2P MENSAJE FALLIDO");
            FermatEvent event = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_SENT_MESSAGE_FAILED);
            event.setSource(EventSource.NETWORK_CLIENT);

            ((NetworkClientNewMessageFailedEvent) event).setId(messageTransmitRespond.getMessageId().toString());
            ((NetworkClientNewMessageFailedEvent) event).setNetworkServiceTypeSource(packageReceived.getNetworkServiceTypeSource());

            /*
             * Raise the event
             */
            System.out.println("MessageTransmitRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_SENT_MESSAGE_DELIVERED");
            getEventManager().raiseEvent(event);
        }

    }

}
