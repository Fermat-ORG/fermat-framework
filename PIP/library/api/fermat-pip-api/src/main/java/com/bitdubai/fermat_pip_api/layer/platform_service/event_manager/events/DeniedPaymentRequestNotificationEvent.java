package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

/**
 * Created by natalia on 30/12/15.
 */
public class DeniedPaymentRequestNotificationEvent extends AbstractFermatEvent {


    private long amount;
    private CryptoCurrency cryptoCurrency;


    public DeniedPaymentRequestNotificationEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType) {
        super(eventType);
    }


    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }


}
