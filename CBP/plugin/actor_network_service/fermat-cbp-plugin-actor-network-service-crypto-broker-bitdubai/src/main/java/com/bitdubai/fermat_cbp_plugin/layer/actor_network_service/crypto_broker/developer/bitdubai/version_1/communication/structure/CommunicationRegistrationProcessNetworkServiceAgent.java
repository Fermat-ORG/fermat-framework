package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.structure;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.AbstractCommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.structure.CommunicationRegistrationProcessNetworkServiceAgent</code>
 * <p/>
 * Created by lnacosta - (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationRegistrationProcessNetworkServiceAgent extends AbstractCommunicationRegistrationProcessNetworkServiceAgent {

    public CommunicationRegistrationProcessNetworkServiceAgent(final AbstractNetworkService         networkServicePluginRoot      ,
                                                               final CommunicationsClientConnection communicationsClientConnection) {

        super(networkServicePluginRoot, communicationsClientConnection);
    }

}
