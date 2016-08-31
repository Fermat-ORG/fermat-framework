package org.iop.client.version_1.channels.processors.checkin;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientProfileRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ACKRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

import org.iop.client.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import org.iop.client.version_1.channels.processors.PackageProcessor;

import javax.websocket.Session;

/**
 * Created by mati on 12/08/16.
 */
public class CheckInActorRespondProcessor extends PackageProcessor {

    /**
     * Constructor with parameter
     *
     * @param channel
     */
    public CheckInActorRespondProcessor(NetworkClientCommunicationChannel channel) {
        super(channel, PackageType.CHECK_IN_ACTOR_RESPONSE);
    }


    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: " + packageReceived.getPackageType()+", content: "+packageReceived.getContent());

        ACKRespond actorCheckInRespond = ACKRespond.parseContent(packageReceived.getContent());

        /*
         * Create a raise a new event whit the platformComponentProfile registered
         */
        //todo: ver algo, la pk viene en null. quizás está bien y no nos sirve.
        NetworkClientProfileRegisteredEvent event = getEventManager().getNewEventMati(P2pEventType.NETWORK_CLIENT_ACTOR_PROFILE_REGISTERED, NetworkClientProfileRegisteredEvent.class);
        event.setSource(EventSource.NETWORK_CLIENT);
        event.setPackageId(actorCheckInRespond.getPackageId());
        event.setStatus(event.getStatus());

        /*
         * Raise the event
         */
        System.out.println("CheckInActorRespondProcessor - Raised a event = P2pEventType.NETWORK_ACTOR_REGISTERED");
        getEventManager().raiseEvent(event);

    }

}
