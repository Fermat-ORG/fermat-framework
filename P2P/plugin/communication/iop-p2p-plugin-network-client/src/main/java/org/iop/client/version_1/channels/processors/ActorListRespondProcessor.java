package org.iop.client.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorListReceivedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

import org.iop.client.version_1.channels.endpoints.NetworkClientCommunicationChannel;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.ActorListRespondProcessor</code>
 * process all packages received the type <code>PackageType.ACTOR_LIST_RESPONSE</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorListRespondProcessor extends PackageProcessor {


    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public ActorListRespondProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.ACTOR_LIST_REQUEST
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: " + packageReceived.getPackageType());
        ActorListMsgRespond actorListMsgRespond = ActorListMsgRespond.parseContent(packageReceived.getContent());

        /*
         * Create a raise a new event whit the NETWORK_CLIENT_ACTOR_LIST_RECEIVED
         */
        NetworkClientActorListReceivedEvent actorListReceived = getEventManager().getNewEventMati(P2pEventType.NETWORK_CLIENT_ACTOR_LIST_RECEIVED,NetworkClientActorListReceivedEvent.class);
        actorListReceived.setSource(EventSource.NETWORK_CLIENT);

        actorListReceived.setActorList(actorListMsgRespond.getActors());
//        actorListReceived.setNetworkServiceType(NetworkServiceType.getByCode(actorListMsgRespond.getNetworkServiceType()));


        if(actorListMsgRespond.getStatus() == STATUS.SUCCESS){
            actorListReceived.setStatus(STATUS.SUCCESS);
        }else{
            actorListReceived.setStatus(STATUS.FAIL);
        }

        actorListReceived.setPackageId(packageReceived.getPackageId());

        /*
         * Raise the event
         */
        System.out.println("ActorListRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_ACTOR_LIST_RECEIVED");
        getEventManager().raiseEvent(actorListReceived);

    }

}
