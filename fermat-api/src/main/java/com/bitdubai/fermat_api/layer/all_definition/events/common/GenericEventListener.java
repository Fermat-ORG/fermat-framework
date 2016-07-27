package com.bitdubai.fermat_api.layer.all_definition.events.common;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener</code>
 * contains the basic functionality of a fermat event listener.
 * <p/>
 * Created by rodrigo on 2015.07.08..
 * Updated by Leon Acosta (laion.cj91@gmail.com) on 18/02/2016.
 */
public class GenericEventListener<Z extends FermatEvent, T extends FermatEventEnum> implements FermatEventListener<Z, T> {

    private FermatEventMonitor fermatEventMonitor;
    private FermatEventHandler fermatEventHandler;
    private T eventType;

    public GenericEventListener(final T eventType,
                                final FermatEventMonitor fermatEventMonitor) {

        this.fermatEventMonitor = fermatEventMonitor;
        this.eventType = eventType;
    }

    @Override
    public T getEventType() {
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
    public void raiseEvent(final Z fermatEvent) {

        try {

            this.fermatEventHandler.handleEvent(fermatEvent);

        } catch (final Exception exception) {

            fermatEventMonitor.handleEventException(exception, fermatEvent);
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("GenericEventListener{")
                .append("fermatEventMonitor=").append(fermatEventMonitor)
                .append(", fermatEventHandler=").append(fermatEventHandler)
                .append(", eventType=").append(eventType)
                .append('}').toString();
    }

}
