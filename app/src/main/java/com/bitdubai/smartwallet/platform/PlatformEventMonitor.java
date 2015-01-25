package com.bitdubai.smartwallet.platform;

/**
 * Created by ciencias on 25.01.15.
 */

import com.bitdubai.smartwallet.platform.layer._1_definition.event.EventMonitor;

/**
 * The event monitor inserts itself in each event raises a monitor that the listener completes its task successfully.
 * If it doesn't, it takes actions.
 */

public class PlatformEventMonitor implements com.bitdubai.smartwallet.platform.layer._1_definition.event.EventMonitor {


    public void handleEventException (Exception e){

    }

}
