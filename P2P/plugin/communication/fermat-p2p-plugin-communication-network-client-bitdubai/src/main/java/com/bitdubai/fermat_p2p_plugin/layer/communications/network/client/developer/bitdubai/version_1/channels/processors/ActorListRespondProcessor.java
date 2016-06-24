package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorListReceivedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorUnreachableEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientCallConnectedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorCallMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorsProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ResultDiscoveryTraceActor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.network_calls.NetworkClientCommunicationCall;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientConnectionsManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                PackageType.ACTOR_LIST_RESPONSE
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
        FermatEvent actorListReceived = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_ACTOR_LIST_RECEIVED);
        actorListReceived.setSource(EventSource.NETWORK_CLIENT);

        ((NetworkClientActorListReceivedEvent) actorListReceived).setActorList(actorListMsgRespond.getActors());
        ((NetworkClientActorListReceivedEvent) actorListReceived).setNetworkServiceType(actorListMsgRespond.getNetworkServiceType());

        if(actorListMsgRespond.getStatus() == ActorListMsgRespond.STATUS.SUCCESS){
            ((NetworkClientActorListReceivedEvent) actorListReceived).setStatus(NetworkClientActorListReceivedEvent.STATUS.SUCCESS);
        }else{
            ((NetworkClientActorListReceivedEvent) actorListReceived).setStatus(NetworkClientActorListReceivedEvent.STATUS.FAILED);
        }

        /*
         * Raise the event
         */
        System.out.println("ActorListRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_ACTOR_LIST_RECEIVED");
        getEventManager().raiseEvent(actorListReceived);

    }

}
