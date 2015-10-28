package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

/**
 * Created by loui on 20/02/15.
 */
public class IncomingCryptoReceivedFromIntraUserEvent extends AbstractFermatEvent {


    public IncomingCryptoReceivedFromIntraUserEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType){
        super(eventType);
    }

}
