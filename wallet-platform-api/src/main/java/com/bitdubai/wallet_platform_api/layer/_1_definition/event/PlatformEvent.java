package com.bitdubai.wallet_platform_api.layer._1_definition.event;

import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventSource;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventType;

/**
 * Created by ciencias on 24.01.15.
 */
public interface PlatformEvent {


    public EventType getEventType();

    public void setSource(EventSource eventSource);

    public EventSource getSource();

}
