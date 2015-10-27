package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by loui on 05/02/15.
 */
public class WalletClosedEvent extends AbstractFermatEvent {
    private String publicKey;


    public void setWalletPublicKey (String publicKey){
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public WalletClosedEvent (EventType eventType){
        super(eventType);
    }
}