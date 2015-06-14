package com.bitdubai.fermat_api.layer._1_definition.event;

import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;

/**
 * Created by ciencias on 24.01.15.
 */
public interface PlatformEvent {


    public EventType getEventType();

    public void setSource(EventSource eventSource);

    public EventSource getSource();

}
