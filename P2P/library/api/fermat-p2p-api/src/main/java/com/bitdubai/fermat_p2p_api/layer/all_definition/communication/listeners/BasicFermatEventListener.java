/*
 * @#BasicFermatEventListener.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners.BasicFermatEventListener</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BasicFermatEventListener implements FermatEventListener {

    /**
     * Represent the eventMonitor
     */
    private FermatEventMonitor eventMonitor;

    /**
     * Represent the p2pEventType
     */
    private P2pEventType p2pEventType;

    /**
     * Represent the eventHandler
     */
    private FermatEventHandler eventHandler;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     * @param eventMonitor
     */
    public BasicFermatEventListener(P2pEventType p2pEventType, FermatEventMonitor eventMonitor){
        this.p2pEventType = p2pEventType;
        this.eventMonitor = eventMonitor;
    }

    /**
     * (non-javadoc)
     * @see FermatEventListener#getEventType()
     */
    @Override
    public P2pEventType getEventType() {
        return p2pEventType;
    }

    /**
     * (non-javadoc)
     * @see FermatEventListener#setEventHandler(FermatEventHandler)
     */
    @Override
    public void setEventHandler(FermatEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * (non-javadoc)
     * @see FermatEventListener#getEventHandler()
     */
    @Override
    public FermatEventHandler getEventHandler() {
        return this.eventHandler;
    }

    /**
     * (non-javadoc)
     * @see FermatEventListener#raiseEvent(FermatEvent)
     */
    @Override
    public void raiseEvent(FermatEvent platformEvent) {

        try
        {
            this.eventHandler.handleEvent(platformEvent);
        }
        catch (Exception exception)
        {
            eventMonitor.handleEventException(exception, platformEvent);
        }

    }
}
