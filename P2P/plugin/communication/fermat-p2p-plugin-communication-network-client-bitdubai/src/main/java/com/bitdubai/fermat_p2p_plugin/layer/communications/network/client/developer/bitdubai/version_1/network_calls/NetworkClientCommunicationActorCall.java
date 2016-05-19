package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.network_calls;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientCall;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.network_calls.NetworkClientCommunicationActorCall</code>
 * is the responsible for sending messages to other actors to a client through a node.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientCommunicationActorCall implements NetworkClientCall<ActorProfile> {

    private final NetworkServiceType                   networkServiceType;
    private final ActorProfile                         actorProfile      ;
    private final NetworkClientCommunicationConnection connection        ;

    public NetworkClientCommunicationActorCall(final NetworkServiceType                   networkServiceType,
                                               final ActorProfile                         actorProfile      ,
                                               final NetworkClientCommunicationConnection connection        ) {

        this.networkServiceType = networkServiceType;
        this.actorProfile       = actorProfile      ;
        this.connection         = connection        ;
    }

    @Override
    public void sendPackageMessage(final PackageContent packageContent) throws CantSendMessageException {

        connection.sendPackageMessage(
                packageContent,
                networkServiceType,
                actorProfile.getIdentityPublicKey(),
                actorProfile.getClientIdentityPublicKey()
        );
    }

    @Override
    public void hangUp() {

        connection.hangUp(this);
    }

    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    @Override
    public ActorProfile getProfile() {
        return actorProfile;
    }
}
