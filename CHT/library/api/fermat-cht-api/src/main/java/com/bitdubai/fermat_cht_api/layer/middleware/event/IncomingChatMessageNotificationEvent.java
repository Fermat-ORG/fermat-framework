package com.bitdubai.fermat_cht_api.layer.middleware.event;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 27/01/16.
 */
public class IncomingChatMessageNotificationEvent implements FermatEvent {


    public IncomingChatMessageNotificationEvent(EventType eventType) {
        this.eventType = eventType;
    }

    private EventType eventType;

    private EventSource eventSource;

    /**
     * Message
     */
    private String alertTitle;
    private String textTitle;
    private String textBody;

    /**
     * Image
     */
    private byte[] image;

    /**
     * Screen/activity to show
     */
    private Activities screen;

    /**
     * Component to show
     */
    private String localPublicKey;

    /**
     * Notification type
     */
    private String notificationType;

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Activities getScreen() {
        return screen;
    }

    public void setScreen(Activities screen) {
        this.screen = screen;
    }

    public String getLocalPublicKey() {
        return localPublicKey;
    }

    public void setLocalPublicKey(String localPublicKey) {
        this.localPublicKey = localPublicKey;
    }

    @Override
    public FermatEventEnum getEventType() {
        return this.eventType;
    }

    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public EventSource getSource() {
        return this.eventSource;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }


    public String getNotificationType() {
        return notificationType;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("IncomingChatMessageNotificationEvent{")
                .append("eventType=").append(eventType)
                .append(", eventSource=").append(eventSource)
                .append(", alertTitle='").append(alertTitle)
                .append('\'')
                .append(", textTitle='").append(textTitle)
                .append('\''
                ).append(", textBody='").append(textBody)
                .append('\'')
                .append(", localPublicKey='").append(localPublicKey)
                .append('\'')
                .append(", notificationType='").append(notificationType)
                .append('\'')
                .append('}').toString();
    }
}
