package com.bitdubai.fermat_cht_api.layer.network_service.chat.events;

import com.bitdubai.fermat_cht_api.all_definition.events.AbstractCHTFermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 28/04/16.
 */
public class IncomingWritingStatusUpdateEvent extends AbstractCHTFermatEvent {

    public IncomingWritingStatusUpdateEvent(EventType eventType) {
        super(eventType);
    }
}
