package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractNewReceiveMessagesNotificationEventHandler;

/**
 * Created by lnacosta (laion.cj91@gmail.com) on 21/11/2015.
 */
public final class NewReceiveMessagesNotificationEventHandler extends AbstractNewReceiveMessagesNotificationEventHandler {

    public NewReceiveMessagesNotificationEventHandler(AbstractNetworkService cryptoAddressesNetworkServicePluginRoot) {
        super(cryptoAddressesNetworkServicePluginRoot);
    }

}
