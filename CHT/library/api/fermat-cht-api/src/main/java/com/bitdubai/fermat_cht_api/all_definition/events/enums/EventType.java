package com.bitdubai.fermat_cht_api.all_definition.events.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;

/** The enum <code>com.bitdubai.fermat_cht_api.fermat_cbp_api.events.enums.EventType</code>
 * represent the different type of events found on cht platform.<p/>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/01/16.
 */
public enum EventType implements FermatEventEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */

    FOO_EVENT("FOO"){
        public final FermatEvent getNewEvent() { return null;}
    },
    ;

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.CHAT_PLATFORM;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override// by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
}
