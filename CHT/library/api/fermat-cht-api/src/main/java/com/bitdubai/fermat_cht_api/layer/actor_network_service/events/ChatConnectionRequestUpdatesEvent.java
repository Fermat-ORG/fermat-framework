package com.bitdubai.fermat_cht_api.layer.actor_network_service.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 */
public class ChatConnectionRequestUpdatesEvent extends AbstractEvent {
    public ChatConnectionRequestUpdatesEvent(FermatEventEnum eventType) {
        super(eventType);
    }
}
