package com.bitdubai.fermat_api.layer.all_definition.events.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.event.interfaces.FermatEventListener</code>
 * represent the basic functionality of a Fermat Event Listener.<p/>
 *
 * Created by ciencias.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatEventListener {

    /**
     * Throw the method <code>getEventType</code> you can get the information of the event type.
     * @return an instance of a Fermat Enum.
     */
    FermatEventEnum getEventType();

    /**
     * Throw the method <code>setEventHandler</code> you can set a handler for the listener.
     * @param fermatEventHandler handler for the event listener.
     */
    void setEventHandler(FermatEventHandler fermatEventHandler);

    /**
     * Throw the method <code>getEventHandler</code>  you can get the handler assigned to the listener.
     * @return an instance of FermatEventHandler.
     */
    FermatEventHandler getEventHandler();

    /**
     * Throw the method <code>raiseEvent</code> you can raise the event to be listened.
     * @param fermatEvent an instance of fermat event to be listened.
     */
    void raiseEvent(FermatEvent fermatEvent);

}
