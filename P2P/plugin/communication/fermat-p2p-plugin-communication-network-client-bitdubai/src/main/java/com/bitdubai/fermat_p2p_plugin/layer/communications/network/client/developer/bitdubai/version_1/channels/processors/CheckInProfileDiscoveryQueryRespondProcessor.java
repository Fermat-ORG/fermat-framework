package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.ClientsConnectionsManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;

import javax.websocket.Session;

/**
 * The Class <code>CheckInProfileDiscoveryQueryRespondProcessor</code>
 * process all packages received the type <code>PackageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_RESPOND</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CheckInProfileDiscoveryQueryRespondProcessor extends PackageProcessor {

    /*
     * Represent the clientsConnectionsManager
     */
    private ClientsConnectionsManager clientsConnectionsManager;

    /**
     * Constructor whit parameter
     *
     * @param communicationsNetworkClientChannel register
     */
    public CheckInProfileDiscoveryQueryRespondProcessor(final com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel communicationsNetworkClientChannel) {
        super(
                communicationsNetworkClientChannel,
                PackageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_RESPOND
        );
        this.clientsConnectionsManager =  (ClientsConnectionsManager) ClientContext.get(ClientContextItem.CLIENTS_CONNECTIONS_MANAGER);
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

                String uriToNode = getChannel().getNetworkClientCommunicationConnection().getUri().getHost() + ":" +
                                    getChannel().getNetworkClientCommunicationConnection().getUri().getPort();

                /*
                 * set the ListActorConnectIntoNode with IdentityPublicKey of Actor and
                 * the uriToNode to can find the NetworkClientCommunicationConnection in the
                 * ListConnectionActiveToNode
                 */
                clientsConnectionsManager.getListActorConnectIntoNode().put(
                        checkInProfileListMsgRespond.getProfileList().get(0).getIdentityPublicKey(),
                        uriToNode
                );

                /*
                 * set the ListConnectionActiveToNode with uriToNode and the
                 * NetworkClientCommunicationConnection respective
                 */
                clientsConnectionsManager.getListConnectionActiveToNode().put(
                        uriToNode,
                        getChannel().getNetworkClientCommunicationConnection()
                );

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
                        getChannel().getNetworkClientCommunicationConnection().actorTraceDiscoveryQuery(checkInProfileListMsgRespond.getDiscoveryQueryParameters());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }

}
