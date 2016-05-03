package com.bitdubai.fermat_cht_api.layer.network_service.chat.events;

import com.bitdubai.fermat_cht_api.all_definition.events.AbstractCHTFermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;

/**
 * Created by root on 06/01/16.
 */
public class OutgoingChat extends AbstractCHTFermatEvent {

    public OutgoingChat(EventType eventType) {
        super(eventType);
    }
}
