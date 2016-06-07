package com.bitdubai.fermat_pip_api.all_definition.event_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.events.MaxTimeOutNotificationReachedEvent;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.events.TimeOutReachedEvent;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.listeners.MaxTimeOutNotificationReachedEventListener;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.listeners.TimeOutReachedEventListener;

/**
 * Created by rodrigo on 4/4/16.
 */
public enum EventType implements FermatEventEnum {

    //TimeOutNotifier Events
    TIMEOUT_REACHED("TOR") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new TimeOutReachedEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new TimeOutReachedEvent();
        }
    },

    MAX_TIMEOUT_NOTIFICATION_REACHED("MTNR") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new MaxTimeOutNotificationReachedEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new MaxTimeOutNotificationReachedEvent();
        }
    };


    private final String code;

    EventType(String code) {
        this.code = code;
    }

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
    public String getCode() {
        return this.code;
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.PLUG_INS_PLATFORM;
    }


}
