package com.bitdubai.fermat_api.layer.all_definition.events.common;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class GenericEventListener implements FermatEventListener {

    private FermatEventMonitor fermatEventMonitor;
    private FermatEventEnum eventType;
    private FermatEventHandler fermatEventHandler;

    public GenericEventListener(final FermatEventEnum eventType, final FermatEventMonitor fermatEventMonitor) {
        this.fermatEventMonitor = fermatEventMonitor;
        this.eventType = eventType;
    }

    @Override
    public FermatEventEnum getEventType() {
        return this.eventType;
    }

    @Override
    public void setEventHandler(final FermatEventHandler fermatEventHandler) {
        this.fermatEventHandler = fermatEventHandler;
    }

    @Override
    public FermatEventHandler getEventHandler() {
        return this.fermatEventHandler;
    }

    @Override
    public void raiseEvent(final FermatEvent fermatEvent) {
        try{
            this.fermatEventHandler.handleEvent(fermatEvent);
        } catch (Exception exception){
            fermatEventMonitor.handleEventException(exception, fermatEvent);
        }
    }
}
