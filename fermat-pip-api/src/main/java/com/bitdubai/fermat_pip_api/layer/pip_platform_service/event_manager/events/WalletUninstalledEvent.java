package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WalletUninstalledEvent extends AbstractPlatformEvent{
    private UUID walletId;

    public void setWalletId (UUID walletId){
        this.walletId = walletId;
    }

    public UUID getWalletId(){
        return this.walletId;
    }

    public WalletUninstalledEvent (EventType eventType){
        super(eventType);
    }


}
