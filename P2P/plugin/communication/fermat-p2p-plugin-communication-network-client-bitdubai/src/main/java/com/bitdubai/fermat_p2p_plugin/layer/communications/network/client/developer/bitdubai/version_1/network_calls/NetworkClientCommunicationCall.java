package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.network_calls;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientCall;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.network_calls.NetworkClientCommunicationCall</code>
 * is the responsible for sending messages to other network services to a client through a node.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientCommunicationCall implements NetworkClientCall {

    private final NetworkServiceType                   networkServiceType;
    private final Profile                              profile           ;
    private final NetworkClientCommunicationConnection connection        ;

    public NetworkClientCommunicationCall(final NetworkServiceType                   networkServiceType,
                                          final Profile                              profile           ,
                                          final NetworkClientCommunicationConnection connection        ) {

        this.networkServiceType = networkServiceType;
        this.profile = profile;
        this.connection         = connection        ;
    }

    @Override
    public void sendPackageMessage(final NetworkServiceMessage packageContent) throws CantSendMessageException {

        connection.sendPackageMessage(
                packageContent,
                networkServiceType,
                profile.getIdentityPublicKey()
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
    public Profile getProfile() {
        return profile;
    }

    @Override
    public boolean isConnected() {
        return connection.isConnected();
    }
}
