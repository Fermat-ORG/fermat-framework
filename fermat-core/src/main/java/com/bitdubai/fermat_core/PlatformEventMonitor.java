package com.bitdubai.fermat_core;

/**
 * Created by ciencias on 25.01.15.
 */

import com.bitdubai.fermat_api.layer.all_definition.event.EventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;

/**
 * The event monitor is called when an Event Handler cant handle an Exception.
 */

public class PlatformEventMonitor implements EventMonitor {


    public void handleEventException (Exception exception, PlatformEvent platformEvent ){

    }

}
