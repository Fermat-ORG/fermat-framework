package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import java.util.UUID;

/**
 * Created by loui on 17/02/15.
 */
public class BegunWalletInstallationEvent extends AbstractFermatEvent {
    private UUID walletId;

    public void setWalletId (UUID walletId){
        this.walletId = walletId;
    }

    public UUID getWalletId(){
        return this.walletId;
    }

    public BegunWalletInstallationEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType) {
        super(eventType);
    }


}
