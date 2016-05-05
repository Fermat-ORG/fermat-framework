package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.NetworkServiceCallRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.call_channels.NetworkClientCommunicationCallChannelManagerAgent;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.endpoints.CommunicationsNetworkClientChannel;

import javax.websocket.Session;

/**
 * The Class <code>NetworkServiceCallRespondProcessor</code>
 * process all packages received the type <code>PackageType.NETWORK_SERVICE_CALL_RESPOND</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkServiceCallRespondProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param communicationsNetworkClientChannel register
     */
    public NetworkServiceCallRespondProcessor(final CommunicationsNetworkClientChannel communicationsNetworkClientChannel) {
        super(
                communicationsNetworkClientChannel,
                PackageType.NETWORK_SERVICE_CALL_RESPOND
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        NetworkServiceCallRespond networkServiceCallRespond = NetworkServiceCallRespond.parseContent(packageReceived.getContent());

        if(networkServiceCallRespond.getStatus() == NetworkServiceCallRespond.STATUS.SUCCESS){
            //raise event

            NetworkClientCommunicationCallChannelManagerAgent callChannelManagerAgent = NetworkClientCommunicationCallChannelManagerAgent.getInstance();

            /*
             * Create a new VPN client
             */
           // TODO IMPLEMENT
           /*
           callChannelManagerAgent.createNewCallChannel(
                    vpnServerUri,
                    vpnServerIdentity,
                    networkServiceCallRespond.getActorTo(),
                    networkServiceCallRespond.getActorFrom(),
                    networkServiceCallRespond.getNetworkServiceFrom(),
                    getChannel().getEventManager()
            );
            */


        } else{
            //there is some wrong
        }

    }

}
