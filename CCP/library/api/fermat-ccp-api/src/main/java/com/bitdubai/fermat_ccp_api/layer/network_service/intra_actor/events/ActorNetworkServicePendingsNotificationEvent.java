package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.16..
 */
public class ActorNetworkServicePendingsNotificationEvent extends AbstractCCPEvent {

    public ActorNetworkServicePendingsNotificationEvent(FermatEventEnum eventType) {
        super(eventType);
    }
}
