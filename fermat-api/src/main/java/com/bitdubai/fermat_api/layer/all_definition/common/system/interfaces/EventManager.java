package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;

/**
 * Created by ciencias on 23.01.15.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 */
public interface EventManager extends FermatManager {

    /**
     * The method <code>getNewListener</code> is used to get a new listener.
     *
     * @param eventType an element of EventType enum indicating the event that you want to listen.
     * @return an instance of FermatEventListener for this event.
     */
    FermatEventListener getNewListener(FermatEventEnum eventType);

    /**
     * The method <code>getNewEvent</code> is used to get a new event.
     *
     * @param eventType an element of EventType enum indicating the event that you want to listen.
     * @return a new event for this type of event.
     */
    FermatEvent getNewEvent(FermatEventEnum eventType);

    void addListener(FermatEventListener listener);

    void removeListener(FermatEventListener listener);

    void raiseEvent(FermatEvent fermatEvent);

}
