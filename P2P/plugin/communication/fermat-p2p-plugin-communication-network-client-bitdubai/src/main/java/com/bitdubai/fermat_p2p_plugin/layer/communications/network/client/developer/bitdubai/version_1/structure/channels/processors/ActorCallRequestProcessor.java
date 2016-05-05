package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorCallRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.channels.endpoints.CommunicationsNetworkClientChannel;

import javax.websocket.Session;

/**
 * The Class <code>ActorCallRequestProcessor</code>
 * process all packages received the type <code>PackageType.ACTOR_CALL_REQUEST</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorCallRequestProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param communicationsNetworkClientChannel register
     */
    public ActorCallRequestProcessor(final CommunicationsNetworkClientChannel communicationsNetworkClientChannel) {
        super(
                communicationsNetworkClientChannel,
                PackageType.ACTOR_CALL_REQUEST
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        ActorCallRequest actorCallRequest = ActorCallRequest.parseContent(packageReceived.getContent());

       // todo raise event

    }

}
