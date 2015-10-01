package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WalletInstalledEvent extends AbstractFermatEvent {
    private UUID walletId;

    private String publicKey;

    public void setWalletId (UUID walletId){
        this.walletId = walletId;        
    }
    
    public UUID getWalletId(){
        return this.walletId;
    }

    public String getPublicKey(){
        return publicKey;
    }
    
    public WalletInstalledEvent (EventType eventType){
        super(eventType);
    }
}
