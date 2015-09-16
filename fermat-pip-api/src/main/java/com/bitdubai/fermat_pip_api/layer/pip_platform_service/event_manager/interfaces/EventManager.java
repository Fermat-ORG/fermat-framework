package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by ciencias on 23.01.15.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 */
public interface EventManager {

    com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener getNewListener(EventType eventType);

    com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent getNewEvent(EventType eventType);

    void addListener(com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener listener);

    void removeListener(FermatEventListener listener);

    void raiseEvent(com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent fermatEvent);

}
