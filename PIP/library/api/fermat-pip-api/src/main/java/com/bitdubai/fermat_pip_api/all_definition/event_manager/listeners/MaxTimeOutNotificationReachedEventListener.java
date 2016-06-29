package com.bitdubai.fermat_pip_api.all_definition.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 4/9/16.
 */
public class MaxTimeOutNotificationReachedEventListener extends GenericEventListener {
    public MaxTimeOutNotificationReachedEventListener(FermatEventMonitor fermatEventMonitor) {
        super(EventType.MAX_TIMEOUT_NOTIFICATION_REACHED, fermatEventMonitor);
    }
}
