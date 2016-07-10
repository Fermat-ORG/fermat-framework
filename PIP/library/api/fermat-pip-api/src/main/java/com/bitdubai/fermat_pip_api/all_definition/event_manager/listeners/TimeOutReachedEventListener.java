package com.bitdubai.fermat_pip_api.all_definition.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 4/4/16.
 */
public class TimeOutReachedEventListener extends GenericEventListener {
    public TimeOutReachedEventListener(FermatEventMonitor fermatEventMonitor) {
        super(EventType.TIMEOUT_REACHED, fermatEventMonitor);
    }
}
