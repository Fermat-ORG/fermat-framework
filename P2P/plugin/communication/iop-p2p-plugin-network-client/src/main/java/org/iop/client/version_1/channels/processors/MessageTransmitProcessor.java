package org.iop.client.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageTransmitEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.google.gson.Gson;

import org.iop.client.version_1.channels.endpoints.NetworkClientCommunicationChannel;

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
            NetworkClientNewMessageTransmitEvent event = getEventManager().getNewEventMati(P2pEventType.NETWORK_CLIENT_NEW_MESSAGE_TRANSMIT,NetworkClientNewMessageTransmitEvent.class);
            event.setSource(EventSource.NETWORK_CLIENT);

            event.setContent(packageReceived.getContent());
//            event.setNetworkServiceTypeSource(packageReceived.getNetworkServiceTypeSource());
            event.setPackageId(packageReceived.getPackageId());

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
