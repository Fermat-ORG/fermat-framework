package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.NearNodeListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import javax.websocket.Session;

/**
 * The Class <code>NearNodeListRespondProcessor</code>
 * process all packages received the type <code>PackageType.NEAR_NODE_LIST_RESPONSE</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NearNodeListRespondProcessor extends PackageProcessor {

    private EventManager eventManager  ;

    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public NearNodeListRespondProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.NEAR_NODE_LIST_RESPONSE
        );

        this.eventManager              = (EventManager) ClientContext.get(ClientContextItem.EVENT_MANAGER);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        NearNodeListMsgRespond nearNodeListMsgRespond = NearNodeListMsgRespond.parseContent(packageReceived.getContent());

        if(nearNodeListMsgRespond.getStatus() == NearNodeListMsgRespond.STATUS.SUCCESS){
            //raise event

        }else{
            //there is some wrong
        }

    }

}
