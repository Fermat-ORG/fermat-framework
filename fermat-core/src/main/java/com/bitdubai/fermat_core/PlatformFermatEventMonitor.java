package com.bitdubai.fermat_core;

/**
 * Created by ciencias on 25.01.15.
 */

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.io.Serializable;

/**
 * The event monitor is called when an Event Handler cant handle an Exception.
 */

public class PlatformFermatEventMonitor implements DealsWithErrors, FermatEventMonitor,Serializable {

    private ErrorManager errorManager;

    public PlatformFermatEventMonitor(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    public void handleEventException (Exception exception, FermatEvent fermatEvent){
        errorManager.reportUnexpectedEventException(fermatEvent, exception);
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

}
