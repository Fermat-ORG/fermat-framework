package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

/**
 * Contain info about the exchange rate between the merchandise currency and the payment currency
 */
public interface MerchandiseExchangeRate {

    Currency getMerchandiseCurrency();

    Currency getPaymentCurrency();

    double getExchangeRate();
}
