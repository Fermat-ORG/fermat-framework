package platform.layer._2_event.manager;

import platform.layer._1_definition.event.EventMonitor;
import platform.layer._1_definition.event.PlatformEvent;

/**
 * Created by ciencias on 26.01.15.
 */
public class UserCreatedEventListener implements EventListener {

    EventMonitor eventMonitor;
    private EventType eventType;
    private EventHandler eventHandler;

    public UserCreatedEventListener (EventType eventType, EventMonitor eventMonitor){
        this.eventType = eventType;
        this.eventMonitor = eventMonitor;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void getEventHandler() {

    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

        try
        {
            this.eventHandler.raiseEvent(platformEvent);
        }
        catch (Exception exception)
        {
            eventMonitor.handleEventException(exception, platformEvent);
        }

    }
}
