package com.bitdubai.fermat_api.layer.all_definition.enums.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum</code>
 * haves the representation of the basic functionality of a Fermat Enum.
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 18/09/2015.
 */
public interface FermatEventEnum extends FermatEnum {

    /**
     * Throw the method <code>getPlatform</code> you can know to which platform the enum belongs.
     *
     * @return an instance of Platforms enum.
     */
    Platforms getPlatform();

    /**
     * Throw the method <code>getNewListener</code> you can get a listener for an specific event.
     *
     * @param fermatEventMonitor an instance of fermat event monitor
     * @return a new listener for the event
     */
    FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor);

    /**
     * Throw the method <code>getNewEvent</code> you can get a new event event for an specific event type.
     *
     * @return an instance of the new event
     */
    FermatEvent getNewEvent();

}
