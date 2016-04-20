package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel;

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

    /**
     * Constructor whit parameter
     *
     * @param communicationsNetworkClientChannel register
     */
    public CheckInProfileDiscoveryQueryRespondProcessor(final CommunicationsNetworkClientChannel communicationsNetworkClientChannel) {
        super(
                communicationsNetworkClientChannel,
                PackageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_RESPOND
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        CheckInProfileListMsgRespond checkInProfileListMsgRespond = CheckInProfileListMsgRespond.parseContent(packageReceived.getContent());

        if(checkInProfileListMsgRespond.getStatus() == CheckInProfileListMsgRespond.STATUS.SUCCESS){
            //raise event

        } else {
            //there is some wrong
        }

    }

}
