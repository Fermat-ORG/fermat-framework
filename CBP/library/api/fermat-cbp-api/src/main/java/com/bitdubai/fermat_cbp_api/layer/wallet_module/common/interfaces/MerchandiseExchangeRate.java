package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.io.Serializable;
import java.util.List;


/**
 * Contain info about the exchange rate between the merchandise currency and the payment currency
 */
public interface MerchandiseExchangeRate extends Serializable {

    Currency getMerchandiseCurrency();

    Currency getPaymentCurrency();

    double getExchangeRate();

    List<Platforms> getPlatformsSupported();
}
