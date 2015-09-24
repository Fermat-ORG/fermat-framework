package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners.CompleteComponentConnectionRequestNotificationEventListener;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners.CompleteComponentRegistrationNotificationEventListener;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners.CompleteRequestListComponentRegisteredNotificationEventListener;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType</code>
 * represent the different type for the events of cry api.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 * Update by Roberto Requena (rart3001@gmail.com) on 17/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType implements FermatEventEnum {

    /**
     * Declaration of the Web Socket Communication Layer Events
     */
    COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION("CL_CRCN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new CompleteComponentRegistrationNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new CompleteComponentRegistrationNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION("CL_CRLCRN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new CompleteRequestListComponentRegisteredNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new CompleteRequestListComponentRegisteredNotificationEvent(this);
        }
    },

    COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION("CL_CCCRN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new CompleteComponentConnectionRequestNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new CompleteComponentConnectionRequestNotificationEvent(this);
        }
    };

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    EventType(String code) {
        this.code = code;
    }


    public abstract FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor);

    public abstract FermatEvent getNewEvent();

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return EventType enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static EventType getByCode(String code) throws InvalidParameterException {
        for (EventType eventType : EventType.values()) {
            if (eventType.code.equals(code)) {
                return eventType;
            }
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code isn't valid for the EventType Enum");
    }

    @Override
    public Platforms getPlatform() {
        return null;
    }

    @Override
    public String getCode() {
        return this.code;
    }


    @Override
    public String toString() {
        return getCode();
    }

}
