package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.util.UUID;

/**
 * Created by franklin on 14/02/16.
 */
public interface CurrencyMatching {
    UUID getCurrencyGivingId();
    void setCurrencyGivingId(UUID currencyGivingId);
    UUID getCurrencyReceivingId();
    void setCurrencyReceivingId(UUID currencyReceivingId);
    Currency getCurrencyGiving();
    void setCurrencyGiving(Currency currencyGiving);
    float getAmountGiving();
    void setAmountGiving(float amountGiving);
    Currency getCurrencyReceiving();
    void setCurrencyReceiving(Currency currencyReceiving);
    float getAmountReceiving();
    void setAmountReceiving(float amountReceiving);
}
