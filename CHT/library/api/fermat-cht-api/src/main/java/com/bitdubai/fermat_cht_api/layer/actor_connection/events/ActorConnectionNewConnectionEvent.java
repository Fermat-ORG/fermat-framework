package com.bitdubai.fermat_cht_api.layer.actor_connection.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public class ActorConnectionNewConnectionEvent extends AbstractEvent {
    public ActorConnectionNewConnectionEvent(FermatEventEnum eventType) {
        super(eventType);
    }
}
