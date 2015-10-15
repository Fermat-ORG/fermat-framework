/*
* @#BasicFermatEventListener.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.all_definition.dap_actor_network_service.asset_user.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_dap_api.layer.all_definition.dap_actor_network_service.asset_user.enums.DapEvenType;

/**
 * The Class <code>BasicFermatEventListener</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 11/10/15.
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
     * Represent the eventType
     */
    private DapEvenType eventType;

    /**
     * Represent the eventHandler
     */
    private FermatEventHandler eventHandler;

    /**
     * Constructor with parameters
     *
     * @param eventType
     * @param eventMonitor
     */
    public BasicFermatEventListener(DapEvenType eventType, FermatEventMonitor eventMonitor){
        this.eventType = eventType;
        this.eventMonitor = eventMonitor;
    }

    /**
     * (non-javadoc)
     * @see FermatEventListener#getEventType()
     */
    @Override
    public DapEvenType getEventType() {
        return eventType;
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
