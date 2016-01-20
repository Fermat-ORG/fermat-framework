package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.*;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/11/15.
 */
public class CompleteComponentRegistrationNotificationEventHandler extends AbstractCompleteComponentRegistrationNotificationEventHandler {
    /**
     * Constructor with parameter.
     *
     * @param networkService
     */
    public CompleteComponentRegistrationNotificationEventHandler(AbstractNetworkService networkService) {
        super(networkService);
    }
}
