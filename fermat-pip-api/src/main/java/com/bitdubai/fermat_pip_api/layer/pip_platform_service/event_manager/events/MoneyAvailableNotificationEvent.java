package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */

public class MoneyAvailableNotificationEvent extends AbstractPlatformEvent {


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
    private String walletPublicKey;


    /**
     * Constructor
     *
     */
    public MoneyAvailableNotificationEvent(){
        super(EventType.NEW_NOTIFICATION);
    }


    /**
     * (non-Javadoc)
     *
     * @see PlatformEvent#getEventType()
     */
    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformEvent#setSource(EventSource)
     */
    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformEvent#getSource()
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

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }
}
