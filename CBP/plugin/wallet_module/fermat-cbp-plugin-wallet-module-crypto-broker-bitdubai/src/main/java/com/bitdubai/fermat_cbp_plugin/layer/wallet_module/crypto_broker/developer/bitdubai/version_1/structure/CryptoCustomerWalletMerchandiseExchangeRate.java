package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;

/**
 * Created by nelson on 24/11/15.
 */
public class CryptoCustomerWalletMerchandiseExchangeRate implements MerchandiseExchangeRate {
    private FermatEnum merchandiseCurrency;
    private FermatEnum paymentCurrency;
    private double exchangeRate;

    public CryptoCustomerWalletMerchandiseExchangeRate(FermatEnum merchandiseCurrency, FermatEnum paymentCurrency, double exchangeRate) {
        this.merchandiseCurrency = merchandiseCurrency;
        this.paymentCurrency = paymentCurrency;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public FermatEnum getMerchandiseCurrency() {
        return merchandiseCurrency;
    }

    @Override
    public FermatEnum getPaymentCurrency() {
        return paymentCurrency;
    }

    @Override
    public double getExchangeRate() {
        return exchangeRate;
    }
}
