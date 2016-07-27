package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageTransmitEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import com.google.gson.Gson;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.MessageTransmitProcessor</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 15/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MessageTransmitProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public MessageTransmitProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.MESSAGE_TRANSMIT
        );
    }


    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        System.out.println(new Gson().toJson(packageReceived));

        try {

              /*
             * Create a raise a new event whit the platformComponentProfile registered
             */
            FermatEvent event = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_NEW_MESSAGE_TRANSMIT);
            event.setSource(EventSource.NETWORK_CLIENT);

            ((NetworkClientNewMessageTransmitEvent) event).setContent(packageReceived.getContent());
            ((NetworkClientNewMessageTransmitEvent) event).setNetworkServiceTypeSource(packageReceived.getNetworkServiceTypeSource());

            /*
             * Raise the event
             */
            System.out.println("MessageTransmitProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_NEW_MESSAGE_TRANSMIT");
            getEventManager().raiseEvent(event);

        } catch (Exception exception){

            exception.printStackTrace();

        }
    }


}
