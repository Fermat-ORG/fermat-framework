package com.bitdubai.fermat_cry_api.layer.definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The enum <code>ccom.bitdubai.fermat_cry_api.layer.definition.enums.EventType</code>
 * represent the different type for the events of cry api.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */

    EVENTO1("BWI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return null; }
        public FermatEvent getNewEvent() {
            return null;
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

    public abstract FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor);

    public abstract FermatEvent getNewEvent();

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.CRYPTO_CURRENCY_PLATFORM;
    }

}
