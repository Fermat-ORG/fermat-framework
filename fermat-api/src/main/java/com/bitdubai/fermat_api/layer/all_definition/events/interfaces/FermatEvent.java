package com.bitdubai.fermat_api.layer.all_definition.events.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.event.interfaces.FermatEvent</code>
 * represent the basic functionality of a Fermat Event.<p/>
 * <p/>
 * Created by ciencias.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatEvent<T extends FermatEventEnum> {

    /**
     * Throw the method <code>getEventType</code> you can get the information of the event type.
     *
     * @return an instance of a Fermat Enum.
     */
    T getEventType();

    /**
     * Throw the method <code>setSource</code> you can set the source of the event.
     *
     * @param eventSource an element of event source enum in reference to the source of the event
     */
    void setSource(EventSource eventSource);

    /**
     * Throw the method <code>setSource</code> you can get the source of the event.
     *
     * @return an element of event source enum in reference to the source of the event.
     */
    EventSource getSource();

}
