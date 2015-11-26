package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Contain info about the exchange rate between the merchandise currency and the payment currency
 */
public interface MerchandiseExchangeRate {

    FermatEnum getMerchandiseCurrency();

    FermatEnum getPaymentCurrency();

    double getExchangeRate();
}
