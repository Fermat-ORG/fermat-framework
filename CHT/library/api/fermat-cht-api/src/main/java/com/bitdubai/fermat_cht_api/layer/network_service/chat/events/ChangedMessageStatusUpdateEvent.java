package com.bitdubai.fermat_cht_api.layer.network_service.chat.events;

import com.bitdubai.fermat_cht_api.all_definition.events.AbstractCHTFermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public final class ChangedMessageStatusUpdateEvent extends AbstractCHTFermatEvent {

    public ChangedMessageStatusUpdateEvent(EventType eventType) {
        super(eventType);
    }

}
