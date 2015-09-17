package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 24.01.15.
 */
public interface EventHandler {

    public void handleEvent(PlatformEvent platformEvent) throws FermatException;

}
