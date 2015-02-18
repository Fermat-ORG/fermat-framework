package com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager;

import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;

import java.util.UUID;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourcesInstalledEvent implements PlatformEvent {
   //TODO: cambiar walletType por la variable correspodiente del tipo de dato correspondiente.
    private UUID walletType;
    private EventType eventType;
    private EventSource eventSource;

    public void setWalletType (UUID walletType){
        this.walletType = walletType;
    }

    public UUID getWalletType(){
        return this.walletType;
    }

    public WalletResourcesInstalledEvent (EventType eventType){
        this.eventType = eventType;
    }

    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public EventSource getSource() {
        return this.eventSource;
    }
}

