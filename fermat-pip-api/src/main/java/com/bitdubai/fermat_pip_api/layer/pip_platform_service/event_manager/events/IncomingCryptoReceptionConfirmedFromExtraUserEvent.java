package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by loui on 20/02/15.
 */
public class IncomingCryptoReceptionConfirmedFromExtraUserEvent extends AbstractFermatEvent {


    public IncomingCryptoReceptionConfirmedFromExtraUserEvent(EventType eventType){
        super(eventType);
    }


}
