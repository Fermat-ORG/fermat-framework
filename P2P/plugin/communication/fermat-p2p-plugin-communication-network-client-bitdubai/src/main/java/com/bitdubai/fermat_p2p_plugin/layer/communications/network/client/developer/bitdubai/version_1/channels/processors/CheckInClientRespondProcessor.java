package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionSuccessEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel;

import javax.websocket.Session;

/**
 * The Class <code>CheckInClientRespondProcessor</code>
 * process all packages received the type <code>PackageType.CHECK_IN_CLIENT_RESPOND</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckInClientRespondProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param communicationsNetworkClientChannel register
     */
    public CheckInClientRespondProcessor(final CommunicationsNetworkClientChannel communicationsNetworkClientChannel) {
        super(
                communicationsNetworkClientChannel,
                PackageType.CHECK_IN_CLIENT_RESPOND
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(final Session session        ,
                                  final Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        CheckInProfileMsjRespond checkInProfileMsjRespond = CheckInProfileMsjRespond.parseContent(packageReceived.getContent());

        if(checkInProfileMsjRespond.getStatus() == CheckInProfileMsjRespond.STATUS.SUCCESS){

            /*
             * set nodesListPosition to -1 when the client is checkIn to avoid connecting to other node if this fails
             */
            getChannel().getConnection().setNodesListPosition();

            /*
             * set registered
             */
            getChannel().setIsRegistered(Boolean.TRUE);

            /*
             * if is connection to other node extern then
             * send profile of the Network Service
             */
            if(getChannel().getConnection().isExternalNode()) {

                String uriToNode = getChannel().getConnection().getUri().getHost() + ":" +
                        getChannel().getConnection().getUri().getPort();

                 /*
                 * Create a raise a new event whit the NETWORK_CLIENT_CONNECTION_SUCCESS
                 */
                FermatEvent event = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_CONNECTION_SUCCESS);
                event.setSource(EventSource.NETWORK_CLIENT);

                ((NetworkClientConnectionSuccessEvent) event).setUriToNode(uriToNode);

                /*
                 * Raise the event
                 */
                System.out.println("CheckInClientRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_CONNECTION_SUCCESS");
                getEventManager().raiseEvent(event);



            }else{

            /*
             * Create a raise a new event whit the platformComponentProfile registered
             */
                FermatEvent event = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_REGISTERED);
                event.setSource(EventSource.NETWORK_CLIENT);

                ((NetworkClientRegisteredEvent) event).setCommunicationChannel(CommunicationChannels.P2P_SERVERS);

            /*
             * Raise the event
             */
                System.out.println("CheckInClientRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_REGISTERED");
                getEventManager().raiseEvent(event);
            }

        } else {
            //there is some wrong
        }

    }

}
