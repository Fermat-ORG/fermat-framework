package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by ciencias on 23.01.15.
 */
public interface EventManager {

    FermatEventListener getNewListener(EventType eventType);

    FermatEvent getNewEvent(EventType eventType);

    void addListener(FermatEventListener listener);

    void removeListener(FermatEventListener listener);

    void raiseEvent(FermatEvent fermatEvent);

}
