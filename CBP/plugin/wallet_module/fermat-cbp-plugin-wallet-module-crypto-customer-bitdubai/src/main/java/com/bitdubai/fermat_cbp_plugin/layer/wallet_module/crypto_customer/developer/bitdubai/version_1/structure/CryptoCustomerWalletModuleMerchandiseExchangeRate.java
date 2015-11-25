package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;

/**
 * Created by nelson on 24/11/15.
 */
public class CryptoCustomerWalletModuleMerchandiseExchangeRate implements MerchandiseExchangeRate {

    private FermatEnum merchandise;
    private FermatEnum payment;
    private double exchangeRate;

    public CryptoCustomerWalletModuleMerchandiseExchangeRate(FermatEnum merchandise, FermatEnum payment, double exchangeRate) {
        this.merchandise = merchandise;
        this.payment = payment;
        this.exchangeRate = exchangeRate;
    }


    @Override
    public FermatEnum getMerchandiseCurrency() {
        return merchandise;
    }

    @Override
    public FermatEnum getPaymentCurrency() {
        return payment;
    }

    @Override
    public double getExchangeRate() {
        return exchangeRate;
    }
}
