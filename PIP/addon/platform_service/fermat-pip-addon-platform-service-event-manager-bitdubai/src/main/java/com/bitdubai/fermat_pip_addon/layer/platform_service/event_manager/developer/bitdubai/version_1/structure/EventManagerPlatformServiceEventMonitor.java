package com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * The class <code>com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1.structure.EventManagerPlatformServiceEventMonitor</code> is the responsible
 * to manage all the exceptions of the events in fermat platform.
 *
 * The event monitor is called when an Event Handler cant handle an Exception.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */

public class EventManagerPlatformServiceEventMonitor implements FermatEventMonitor {

    private final ErrorManager errorManager;

    public EventManagerPlatformServiceEventMonitor(final ErrorManager errorManager) {

        this.errorManager = errorManager;
    }

    public void handleEventException (final Exception   exception  ,
                                      final FermatEvent fermatEvent){

        // TODO here you can add an specific logic to manage the exceptions in events.

        errorManager.reportUnexpectedEventException(fermatEvent, exception);
    }

}
