package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientProfileRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.UpdateProfileMsjRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;

import javax.websocket.Session;

/**
 * The Class <code>UpdateActorProfileRespondProcessor</code>
 * process all the packages received with package type <code>PackageType.UPDATE_ACTOR_PROFILE_RESPONSE</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class UpdateActorProfileRespondProcessor extends PackageProcessor {

    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public UpdateActorProfileRespondProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel) {
        super(
                networkClientCommunicationChannel,
                PackageType.UPDATE_ACTOR_PROFILE_RESPONSE
        );
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());

        UpdateProfileMsjRespond updateProfileMsjRespond = UpdateProfileMsjRespond.parseContent(packageReceived.getContent());

        System.out.println(updateProfileMsjRespond.toJson());

        /*
         * Create a raise a new event whit the public key of the profile updated or not
         */
        FermatEvent event = getEventManager().getNewEvent(P2pEventType.NETWORK_CLIENT_ACTOR_PROFILE_UPDATED);
        event.setSource(EventSource.NETWORK_CLIENT);

        ((NetworkClientProfileRegisteredEvent) event).setPublicKey(updateProfileMsjRespond.getIdentityPublicKey());

        if(updateProfileMsjRespond.getStatus() == UpdateProfileMsjRespond.STATUS.SUCCESS){
            ((NetworkClientProfileRegisteredEvent) event).setStatus(NetworkClientProfileRegisteredEvent.STATUS.SUCCESS);
        } else {
            ((NetworkClientProfileRegisteredEvent) event).setStatus(NetworkClientProfileRegisteredEvent.STATUS.FAILED);
        }

        System.out.println("UpdateActorProfileRespondProcessor - Raised a event = P2pEventType.NETWORK_CLIENT_ACTOR_PROFILE_UPDATED");

        /*
         * Raise the event
         */
        getEventManager().raiseEvent(event);

    }

}
