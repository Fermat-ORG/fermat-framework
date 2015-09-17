package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces;

/**
 * Created by ciencias on 25.01.15.
 */
public interface EventMonitor {

    public void handleEventException (Exception exception, PlatformEvent platformEvent );

}
