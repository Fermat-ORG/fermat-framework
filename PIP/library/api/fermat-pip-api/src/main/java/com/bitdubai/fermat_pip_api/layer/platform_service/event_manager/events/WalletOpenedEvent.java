package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by loui on 05/02/15.
 */
public class WalletOpenedEvent extends AbstractFermatEvent {
    private String publicKey;

    public void setWalletPublicKey (String publicKey){
        this.publicKey = publicKey;
    }
    
    public String getWalletPublicKey() {
        return this.publicKey;
    }
    
    public WalletOpenedEvent (EventType eventType){
        super(eventType);
    }
}
