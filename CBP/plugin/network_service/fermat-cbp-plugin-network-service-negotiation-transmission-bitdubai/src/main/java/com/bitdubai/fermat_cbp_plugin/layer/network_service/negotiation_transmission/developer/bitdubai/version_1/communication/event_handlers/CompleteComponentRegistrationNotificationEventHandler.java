package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCompleteComponentRegistrationNotificationEventHandler;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.CompleteComponentRegistrationNotificationEventHandler</code>
 * contains all the basic functionality of a CompleteComponentRegistrationNotificationEventHandler.
 *
 * The method <code>handleCompleteComponentRegistrationNotificationEvent</code> can be override to modify its behavior.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CompleteComponentRegistrationNotificationEventHandler extends AbstractCompleteComponentRegistrationNotificationEventHandler {

    public CompleteComponentRegistrationNotificationEventHandler(final AbstractNetworkService networkService) {
        super(networkService);
    }

}
