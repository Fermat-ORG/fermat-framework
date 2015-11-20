package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

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
    
    public WalletInstalledEvent (com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType){
        super(eventType);
    }
}
