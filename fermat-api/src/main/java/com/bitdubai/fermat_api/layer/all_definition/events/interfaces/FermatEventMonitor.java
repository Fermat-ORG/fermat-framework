package com.bitdubai.fermat_api.layer.all_definition.events.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.event.interfaces.FermatEventMonitor</code>
 * represent the basic functionality of a Fermat Event Monitor.<p/>
 * <p/>
 * Created by ciencias.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatEventMonitor {

    /**
     * Throw the method <code>handleEventException</code> you can handle the event exception.
     *
     * @param exception   an instance of the exception.
     * @param fermatEvent an instance of the event which falls in an exception.
     */
    void handleEventException(Exception exception, FermatEvent fermatEvent);

}
