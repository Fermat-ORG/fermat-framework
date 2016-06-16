package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientProfileRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;

import javax.websocket.Session;

/**
 * The Class <code>CheckInNetworkServiceRespondProcessor</code>
 * process all packages received the type <code>PackageType.CHECK_IN_NETWORK_SERVICE_RESPONSE</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CheckInNetworkServiceRespondProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public CheckInNetworkServiceRespondProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.CHECK_IN_NETWORK_SERVICE_RESPONSE
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        CheckInProfileMsjRespond checkInProfileMsjRespond = CheckInProfileMsjRespond.parseContent(packageReceived.getContent());

        if(checkInProfileMsjRespond.getStatus() == CheckInProfileMsjRespond.STATUS.SUCCESS){

            /*
             * Create a raise a new event whit the platformComponentProfile registered
             */
            FermatEvent event = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_NETWORK_SERVICE_PROFILE_REGISTERED);
            event.setSource(EventSource.NETWORK_CLIENT);

            ((NetworkClientProfileRegisteredEvent) event).setPublicKey(checkInProfileMsjRespond.getIdentityPublicKey());

            /*
             * Raise the event
             */
            System.out.println("CheckInClientRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_NETWORK_SERVICE_PROFILE_REGISTERED");
            getEventManager().raiseEvent(event);

        }else{
            //there is some wrong
        }

    }

}
