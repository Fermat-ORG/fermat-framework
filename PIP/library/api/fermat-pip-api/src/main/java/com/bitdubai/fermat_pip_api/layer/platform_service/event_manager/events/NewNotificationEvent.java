package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */

public class NewNotificationEvent extends AbstractFermatEvent {


    /**
     * Represent the eventType
     */
    private EventType eventType;

    /**
     * Represent the eventSource
     */
    private EventSource eventSource;

    /**
     *  Notification members
     */
    private String notificationTitle;
    private String notificationTextTitle;
    private String notificationTextBody;


    /**
     * Constructor
     *
     */
    public NewNotificationEvent(){
        super(EventType.NEW_NOTIFICATION);
    }


    /**
     * (non-Javadoc)
     *
     * @see FermatEvent#getEventType()
     */
    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEvent#setSource(EventSource)
     */
    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEvent#getSource()
     */
    @Override
    public EventSource getSource() {
        return this.eventSource;
    }

    /**
     *  Members Getters and Setters
     */

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationTextTitle() {
        return notificationTextTitle;
    }

    public void setNotificationTextTitle(String notificationTextTitle) {
        this.notificationTextTitle = notificationTextTitle;
    }

    public String getNotificationTextBody() {
        return notificationTextBody;
    }

    public void setNotificationTextBody(String notificationTextBody) {
        this.notificationTextBody = notificationTextBody;
    }
}
