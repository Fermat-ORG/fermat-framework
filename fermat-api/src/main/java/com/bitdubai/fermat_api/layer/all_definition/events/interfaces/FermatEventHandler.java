package com.bitdubai.fermat_api.layer.all_definition.events.interfaces;

import com.bitdubai.fermat_api.FermatException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.event.interfaces.FermatEventHandler</code>
 * represent the basic functionality of a Fermat Event Handler.<p/>
 * <p/>
 * Created by ciencias.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatEventHandler<T extends FermatEvent> {

    /**
     * Throw the method <code>handleEvent</code> you can handle the fermat event.
     *
     * @param fermatEvent event to be handled.
     * @throws FermatException if something goes wrong.
     */
    void handleEvent(T fermatEvent) throws FermatException;

}
