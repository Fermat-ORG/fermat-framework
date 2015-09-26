package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;

/**
 * Created by jorgegonzalez on 23-09-2015.
 */
public enum EventType implements FermatEventEnum {
;

    @Override
    public Platforms getPlatform() {
        return null;
    }

    @Override
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
        return null;
    }

    @Override
    public FermatEvent getNewEvent() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }
}
