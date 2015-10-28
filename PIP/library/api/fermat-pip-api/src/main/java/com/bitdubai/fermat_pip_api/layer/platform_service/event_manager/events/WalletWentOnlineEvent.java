package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public class WalletWentOnlineEvent extends AbstractFermatEvent {

    private UUID walletId;


    public void setWalletId (UUID walletId){
        this.walletId = walletId;
    }

    public UUID getWalletId() {
        return this.walletId;
    }


    public WalletWentOnlineEvent (EventType eventType){
        super(eventType);
    }


}

