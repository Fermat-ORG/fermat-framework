/*
* @#NewCryptoAddressRequestAssetUserNotificationEventListener.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
/**
 * The Class <code>com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.NewCryptoAddressRequestAssetUserActorNotificationEventListener</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 28/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewCryptoAddressRequestAssetUserActorNotificationEventListener implements FermatEventListener {


    FermatEventMonitor fermatEventMonitor;
    private EventType eventType;
    private FermatEventHandler fermatEventHandler;

    public NewCryptoAddressRequestAssetUserActorNotificationEventListener(EventType eventType, FermatEventMonitor fermatEventMonitor) {
        this.eventType = eventType;
        this.fermatEventMonitor = fermatEventMonitor;
    }

    @Override
    public FermatEventEnum getEventType() {
        return eventType;
    }

    @Override
    public void setEventHandler(FermatEventHandler fermatEventHandler) {
        this.fermatEventHandler = fermatEventHandler;
    }

    @Override
    public FermatEventHandler getEventHandler() {
        return this.fermatEventHandler;
    }

    @Override
    public void raiseEvent(FermatEvent fermatEvent) {
        try {
            this.fermatEventHandler.handleEvent(fermatEvent);
        } catch (Exception exception) {
            fermatEventMonitor.handleEventException(exception, fermatEvent);
        }
    }
}
