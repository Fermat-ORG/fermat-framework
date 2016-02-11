package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;

/**
 * Created by nelson on 24/11/15.
 */
public class CryptoCustomerWalletModuleMerchandiseExchangeRate implements MerchandiseExchangeRate {

    private Currency merchandise;
    private Currency payment;
    private double exchangeRate;

    public CryptoCustomerWalletModuleMerchandiseExchangeRate(Currency merchandise, Currency payment, double exchangeRate) {
        this.merchandise = merchandise;
        this.payment = payment;
        this.exchangeRate = exchangeRate;
    }

    public CryptoCustomerWalletModuleMerchandiseExchangeRate(QuotesExtraData quotesExtraData) {
        this.merchandise = quotesExtraData.getMerchandise();
        this.payment = quotesExtraData.getPaymentCurrency();
        this.exchangeRate = quotesExtraData.getPrice();
    }

    @Override
    public Currency getMerchandiseCurrency() {
        return merchandise;
    }

    @Override
    public Currency getPaymentCurrency() {
        return payment;
    }

    @Override
    public double getExchangeRate() {
        return exchangeRate;
    }
}
