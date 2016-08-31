package org.iop.client.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClienteEventPublishEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.EventPublishRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

import org.iop.client.version_1.channels.endpoints.NetworkClientCommunicationChannel;

import javax.websocket.Session;

/**
 * Created by mati on 24/08/16.
 */
public class EventPublishProcessor extends PackageProcessor {

    /**
     * Constructor with parameter
     *
     * @param channel
     */
    public EventPublishProcessor(NetworkClientCommunicationChannel channel) {
        super(channel, PackageType.EVENT_PUBLISH);
    }


    /**
     * (non-javadoc)
     *
     * @see PackageProcessor#processingPackage(Session, com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: " + packageReceived.getPackageType() + ", content: " + packageReceived.getContent());

        EventPublishRespond eventPublishRespond = EventPublishRespond.parseContent(packageReceived.getContent());

        /*
        * Create a raise a new event whit the platformComponentProfile registered
        */
        NetworkClienteEventPublishEvent event = getEventManager().getNewEventMati(P2pEventType.NETWORK_CLIENT_EVENT_PUBLISH, NetworkClienteEventPublishEvent.class);
        event.setSource(EventSource.NETWORK_CLIENT);
        event.setEventPublishRespond(eventPublishRespond);
        event.setPackageId(packageReceived.getPackageId());

        /*
         * Raise the event
         */
        System.out.println("EventPublishProcessor - Raised a event = P2pEventType.EVENT_PUBLISH");
        getEventManager().raiseEvent(event);

    }
}
