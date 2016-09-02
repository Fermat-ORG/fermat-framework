package com.bitdubai.fermat_cht_api.layer.network_service.chat.events;

import com.bitdubai.fermat_cht_api.all_definition.events.AbstractCHTFermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;

/**
 * Created by Gabriel Araujo on 17/08/16.
 */
public class IncomingMessageEvent extends AbstractCHTFermatEvent {

    public IncomingMessageEvent(EventType eventType) {
        super(eventType);
    }
}
