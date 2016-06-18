package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorFoundEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionSuccessEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientConnectionsManager;

import javax.websocket.Session;

/**
 * The Class <code>CheckInProfileDiscoveryQueryRespondProcessor</code>
 * process all packages received the type <code>PackageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_RESPONSE</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CheckInProfileDiscoveryQueryRespondProcessor extends PackageProcessor {

    /*
     * Represent the networkClientConnectionsManager
     */
    private NetworkClientConnectionsManager networkClientConnectionsManager;

    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public CheckInProfileDiscoveryQueryRespondProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_RESPONSE
        );
        this.networkClientConnectionsManager =  (NetworkClientConnectionsManager) ClientContext.get(ClientContextItem.CLIENTS_CONNECTIONS_MANAGER);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        CheckInProfileListMsgRespond checkInProfileListMsgRespond = CheckInProfileListMsgRespond.parseContent(packageReceived.getContent());

        System.out.println(checkInProfileListMsgRespond.toJson());

        if(checkInProfileListMsgRespond.getStatus() == CheckInProfileListMsgRespond.STATUS.SUCCESS){
            //raise event

            if(checkInProfileListMsgRespond.getProfileList() != null && checkInProfileListMsgRespond.getProfileList().size() > 0){

                String uriToNode = getChannel().getConnection().getUri().getHost() + ":" +
                                    getChannel().getConnection().getUri().getPort();

                /*
                 * set the ListConnectionActiveToNode with uriToNode and the
                 * NetworkClientCommunicationConnection respective
                 */
                networkClientConnectionsManager.getActiveConnectionsToExternalNodes().put(
                        uriToNode,
                        getChannel().getConnection()
                );

                /*
                * Create a raise a new event whit the platformComponentProfile registered
                */
                FermatEvent event = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_ACTOR_FOUND);
                event.setSource(EventSource.NETWORK_CLIENT);

                /*
                 * this is to filter the networkservice intermediate
                 */
                ((NetworkClientActorFoundEvent) event).setNetworkServiceTypeIntermediate(checkInProfileListMsgRespond.getDiscoveryQueryParameters().getNetworkServiceTypeIntermediate());

                /*
                 * this is to know who is the nodeprofile to send message
                 */
                ((NetworkClientActorFoundEvent) event).setActorProfile((ActorProfile)checkInProfileListMsgRespond.getProfileList().get(0));

                /*
                 * this is to filter when the client is checkin in other node
                 */
                ((NetworkClientActorFoundEvent) event).setUriToNode(uriToNode);

                /*
                 * Raise the event
                 */
                System.out.println("CheckInProfileDiscoveryQueryRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_ACTOR_FOUND");
                getEventManager().raiseEvent(event);

               /*
                * Create a raise a new event whit the NETWORK_CLIENT_CONNECTION_SUCCESS
                */
                FermatEvent eventConnectionSuccess = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_CONNECTION_SUCCESS);
                event.setSource(EventSource.NETWORK_CLIENT);

                ((NetworkClientConnectionSuccessEvent) eventConnectionSuccess).setUriToNode(uriToNode);

                /*
                 * Raise the event
                 */
                System.out.println("CheckInProfileDiscoveryQueryRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_CONNECTION_SUCCESS");
                getEventManager().raiseEvent(eventConnectionSuccess);


            }

        } else {
            //there is some wrong

            if(checkInProfileListMsgRespond.getDiscoveryQueryParameters() != null){

                /*
                 * Validate if it was a network service that did the request
                 * else is an Actor the responsable of the request
                 */
                if (checkInProfileListMsgRespond.getDiscoveryQueryParameters().getNetworkServiceType() != null &&
                        checkInProfileListMsgRespond.getDiscoveryQueryParameters().getNetworkServiceType() !=  NetworkServiceType.UNDEFINED){

                }else{

                    /*
                     * we realize the actorTraceDiscoveryQuery because is an Actor that realized the call up
                     */
                    try {
                        getChannel().getConnection().actorTraceDiscoveryQuery(checkInProfileListMsgRespond.getDiscoveryQueryParameters());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }

}
