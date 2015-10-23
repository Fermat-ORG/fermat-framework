package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

import java.util.UUID;

/**
*
 * Created by by Matias Furszyfer
 */
public class ActorNetworkServiceCompleteRegistration extends AbstractCCPEvent {

    public ActorNetworkServiceCompleteRegistration(FermatEventEnum eventType) {
        super(eventType);
    }


}
