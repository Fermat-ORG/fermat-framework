package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.communication.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteRequestListComponentRegisteredNotificationEvent</code><p/>
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 20/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CompleteRequestListComponentRegisteredNotificationEventHandler extends AbstractCommunicationBaseEventHandler<CompleteRequestListComponentRegisteredNotificationEvent>  {

     public CompleteRequestListComponentRegisteredNotificationEventHandler(NetworkService networkService) {
        super(networkService);
    }

    @Override
    public void processEvent(CompleteRequestListComponentRegisteredNotificationEvent event) {
        this.networkService.handleCompleteRequestListComponentRegisteredNotificationEvent(event.getRegisteredComponentList());
    }
}
