package org.iop.client.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientACKEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ACKRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

import org.iop.client.version_1.channels.endpoints.NetworkClientCommunicationChannel;

import javax.websocket.Session;

/**
 * Created by mati on 12/08/16.
 */
public class ACKProcessor extends PackageProcessor{

    /**
     * Constructor with parameter
     *
     * @param channel
     */
    public ACKProcessor(NetworkClientCommunicationChannel channel) {
        super(channel, PackageType.ACK);
    }


    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: " + packageReceived.getPackageType()+", content: "+packageReceived.getContent());

        ACKRespond ackRespond = ACKRespond.parseContent(packageReceived.getContent());

        /*
        * Create a raise a new event whit the platformComponentProfile registered
        */
        NetworkClientACKEvent event = getEventManager().getNewEventMati(P2pEventType.NETWORK_CLIENT_ACK, NetworkClientACKEvent.class);
        event.setSource(EventSource.NETWORK_CLIENT);
        event.setContent(ackRespond);

        /*
         * Raise the event
         */
        System.out.println("ACKProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_ACK");
        getEventManager().raiseEvent(event);

    }

}
