package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.NetworkServiceCallRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel;

import javax.websocket.Session;

/**
 * The Class <code>NetworkServiceCallRequestProcessor</code>
 * process all packages received the type <code>PackageType.NETWORK_SERVICE_CALL_REQUEST</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkServiceCallRequestProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param communicationsNetworkClientChannel register
     */
    public NetworkServiceCallRequestProcessor(final CommunicationsNetworkClientChannel communicationsNetworkClientChannel) {
        super(
                communicationsNetworkClientChannel,
                PackageType.NETWORK_SERVICE_CALL_REQUEST
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        NetworkServiceCallRequest networkServiceCallRequest = NetworkServiceCallRequest.parseContent(packageReceived.getContent());

       // todo raise event

    }

}
