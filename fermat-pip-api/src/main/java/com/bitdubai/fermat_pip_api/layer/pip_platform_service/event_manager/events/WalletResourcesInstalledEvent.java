package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

import java.util.UUID;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourcesInstalledEvent extends AbstractFermatEvent {
   //Loui TODO: cambiar walletType por la variable correspodiente del tipo de dato correspondiente.
    private UUID walletType;

    private EventSource eventSource;

    public void setWalletType (UUID walletType){
        this.walletType = walletType;
    }

    public UUID getWalletType(){
        return this.walletType;
    }

    public WalletResourcesInstalledEvent (EventType eventType){
        super(eventType);
    }


}

