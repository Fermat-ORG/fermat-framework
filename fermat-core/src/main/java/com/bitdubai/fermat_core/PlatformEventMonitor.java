package com.bitdubai.fermat_core;

/**
 * Created by ciencias on 25.01.15.
 */

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.io.Serializable;

/**
 * The event monitor is called when an Event Handler cant handle an Exception.
 */

public class PlatformEventMonitor implements DealsWithErrors, EventMonitor,Serializable {

    private ErrorManager errorManager;

    public PlatformEventMonitor(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    public void handleEventException (Exception exception, PlatformEvent platformEvent ){
        errorManager.reportUnexpectedEventException(platformEvent, exception);
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

}
