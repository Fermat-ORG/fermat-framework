package platform;

/**
 * Created by ciencias on 25.01.15.
 */

import platform.layer._1_definition.event.EventMonitor;
import platform.layer._1_definition.event.PlatformEvent;

/**
 * The event monitor is called when an Event Handler cant handle an Exception.
 */

public class PlatformEventMonitor implements EventMonitor {


    public void handleEventException (Exception exception, PlatformEvent platformEvent ){

    }

}
