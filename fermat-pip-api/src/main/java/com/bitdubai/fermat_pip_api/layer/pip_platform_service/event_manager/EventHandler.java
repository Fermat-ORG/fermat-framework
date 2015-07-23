package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager;

import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;

/**
 * Created by ciencias on 24.01.15.
 */
public interface EventHandler {

    public void handleEvent(PlatformEvent platformEvent) throws Exception;

}
