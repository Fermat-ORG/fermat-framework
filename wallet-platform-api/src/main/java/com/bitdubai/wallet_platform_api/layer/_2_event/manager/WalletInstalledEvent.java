package com.bitdubai.wallet_platform_api.layer._2_event.manager;

import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WalletInstalledEvent implements PlatformEvent {
    private UUID walletId;
    private EventType eventType;
    private EventSource eventSource;
    
    public void setWalletId (UUID walletId){
        this.walletId = walletId;        
    }
    
    public UUID getWalletId(){
        return this.walletId;
    } 
    
    public WalletInstalledEvent (EventType eventType){
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
