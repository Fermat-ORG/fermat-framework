package com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;

import java.util.UUID;

/**
 * Created by ciencias on 26.01.15.
 */
public class WalletCreatedEvent implements PlatformEvent {

    private UUID walletId;
    private EventType eventType;
    private EventSource eventSource;
    private CryptoCurrency cryptoCurrency;
    private FiatCurrency fiatCurrency;

    public void setWalletId (UUID walletId){
        this.walletId = walletId;
    }

    public UUID getWalletId() {
        return this.walletId;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency){this.cryptoCurrency = cryptoCurrency;}

    public CryptoCurrency getCryptoCurrency(){return cryptoCurrency;}

    public void setFiatCurrency(FiatCurrency fiatCurrency){this.fiatCurrency = fiatCurrency;}

    public FiatCurrency getFiatCurrency(){return fiatCurrency;}

    public WalletCreatedEvent (EventType eventType){
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

