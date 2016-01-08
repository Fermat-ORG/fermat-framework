package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCompleteComponentConnectionRequestNotificationEventHandler;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public class CompleteComponentConnectionRequestNotificationEventHandler extends AbstractCompleteComponentConnectionRequestNotificationEventHandler {
    /**
     * Constructor with parameter.
     *
     * @param networkService
     */
    public CompleteComponentConnectionRequestNotificationEventHandler(AbstractNetworkService networkService) {
        super(networkService);
    }
}
