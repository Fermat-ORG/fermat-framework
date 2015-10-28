package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by loui on 20/02/15.
 */
public class IncomingMoneyRequestReceivedEvent extends AbstractFermatEvent {

    public IncomingMoneyRequestReceivedEvent(EventType eventType){
        super(eventType);
    }
}
