package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.util;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;

/**
 * Created by franklin on 15/02/16.
 */
public class CurrencyMatchingImp implements CurrencyMatching {
    private String originTransactionId;
    private Currency currencyGiving;
    private Currency currencyReceiving;
    private float amountGiving;
    private float amountReceiving;

    public CurrencyMatchingImp() {
    }

    public CurrencyMatchingImp(String originTransactionId,
                               Currency currencyGiving,
                               Currency currencyReceiving,
                               float amountGiving,
                               float amountReceiving) {
        this.originTransactionId = originTransactionId;
        this.currencyGiving = currencyGiving;
        this.currencyReceiving = currencyReceiving;
        this.amountGiving = amountGiving;
        this.amountReceiving = amountReceiving;
    }

    @Override
    public String getOriginTransactionId() {
        return originTransactionId;
    }

    @Override
    public Currency getCurrencyGiving() {
        return currencyGiving;
    }

    @Override
    public float getAmountGiving() {
        return amountGiving;
    }

    @Override
    public Currency getCurrencyReceiving() {
        return currencyReceiving;
    }

    @Override
    public float getAmountReceiving() {
        return amountReceiving;
    }

    @Override
    public String toString() {
        return "CurrencyMatchingImp{" + "originTransactionId='" + originTransactionId + '\'' + ", currencyGiving=" + currencyGiving + ", currencyReceiving=" + currencyReceiving + ", amountGiving=" + amountGiving + ", amountReceiving=" + amountReceiving + '}';
    }
}
