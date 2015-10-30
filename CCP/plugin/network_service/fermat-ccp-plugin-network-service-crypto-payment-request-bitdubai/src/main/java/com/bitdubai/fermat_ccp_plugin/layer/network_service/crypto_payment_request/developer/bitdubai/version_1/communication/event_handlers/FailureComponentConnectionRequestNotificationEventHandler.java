package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractFailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers.FailureComponentConnectionRequestNotificationEventHandler</code>
 * implements the handle of the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.FailureComponentConnectionRequestNotificationEvent</code><p/>
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class FailureComponentConnectionRequestNotificationEventHandler extends AbstractFailureComponentConnectionRequestNotificationEventHandler {

    public FailureComponentConnectionRequestNotificationEventHandler(final AbstractNetworkService networkService) {

        super(networkService);
    }

}
