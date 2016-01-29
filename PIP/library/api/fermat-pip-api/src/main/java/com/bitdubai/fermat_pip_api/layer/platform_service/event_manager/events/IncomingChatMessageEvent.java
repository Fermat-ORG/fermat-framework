package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 27/01/16.
 */
public class IncomingChatMessageEvent extends AbstractFermatEvent {

    public IncomingChatMessageEvent(EventType eventType) {
        super(eventType);
    }
}
