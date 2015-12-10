package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCompleteComponentConnectionRequestNotificationEventHandler;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteComponentConnectionRequestNotificationEvent</code><p/>
 *
 * The method <code>handleCompleteComponentRegistrationNotificationEvent</code> can be override to modify its behavior.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 20/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CompleteComponentConnectionRequestNotificationEventHandler extends AbstractCompleteComponentConnectionRequestNotificationEventHandler {

    public CompleteComponentConnectionRequestNotificationEventHandler(AbstractNetworkService networkService) {
        super(networkService);
    }

}
