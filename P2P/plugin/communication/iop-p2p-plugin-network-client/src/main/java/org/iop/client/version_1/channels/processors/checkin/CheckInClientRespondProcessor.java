package org.iop.client.version_1.channels.processors.checkin;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;

import org.iop.client.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import org.iop.client.version_1.channels.processors.PackageProcessor;

import javax.websocket.Session;

/**
 * Created by mati on 12/08/16.
 */
public class CheckInClientRespondProcessor extends PackageProcessor {

    /**
     * Constructor with parameter
     *
     * @param channel
     */
    public CheckInClientRespondProcessor(NetworkClientCommunicationChannel channel) {
        super(channel, PackageType.CHECK_IN_CLIENT_RESPOND);
    }


    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: " + packageReceived.getPackageType()+", content: "+packageReceived.getContent());

        //ClientCheckInRespond clientCheckInRespond = ClientCheckInRespond.parseContent(packageReceived.getContent());

        /*
         * Create a raise a new event whit the platformComponentProfile registered
         */
        NetworkClientRegisteredEvent event = getEventManager().getNewEventMati(P2pEventType.NETWORK_CLIENT_REGISTERED, NetworkClientRegisteredEvent.class);
        event.setSource(EventSource.NETWORK_CLIENT);
        event.setCommunicationChannel(CommunicationChannels.P2P_SERVERS);

        /*
         * Raise the event
         */
        System.out.println("CheckInClientRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_REGISTERED");
        getEventManager().raiseEvent(event);

    }

}
