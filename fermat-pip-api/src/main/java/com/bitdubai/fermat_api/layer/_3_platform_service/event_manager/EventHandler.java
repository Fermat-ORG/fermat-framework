package com.bitdubai.fermat_api.layer._3_platform_service.event_manager;

import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;

/**
 * Created by ciencias on 24.01.15.
 */
public interface EventHandler {

    public void handleEvent(PlatformEvent platformEvent) throws Exception;

}
