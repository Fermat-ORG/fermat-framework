package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by ciencias on 26.01.15.
 * modified by  by Leon Acosta (laion.cj91@gmail.com) on 27/06/2015.
 */
public class DeviceUserCreatedEvent extends AbstractFermatEvent {

    private String publicKey;


    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public DeviceUserCreatedEvent(EventType eventType) {
        super(eventType);
    }


}
