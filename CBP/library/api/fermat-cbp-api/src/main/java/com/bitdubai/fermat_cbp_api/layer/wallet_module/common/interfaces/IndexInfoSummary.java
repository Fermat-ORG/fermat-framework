package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;

import java.io.Serializable;
import java.util.UUID;


/**
 * Summarized information about a index
 */
public interface IndexInfoSummary extends Serializable {

    /**
     * @return the provider's name or "Unknown Provider"
     */
    String getProviderName();

    /**
     * @return the exchange rate data: sell and buy values, dates, etc
     */
    ExchangeRate getExchangeRateData();

    /**
     * @return String with formatted like this: Currency / Reference Currency. <br> Example: BTC/USD
     */
    String getCurrencyAndReferenceCurrency();

    /**
     * @return String formatted like this: Currency SalePrice. <br> Example: USD 325,81
     */
    String getSalePriceAndCurrency();

    /**
     * @return String formatted like this: Currency PurchasePrice. <br> Example: USD 314,56
     */
    String getPurchasePriceAndCurrency();

    /***
     * @return the Provider's Plugin ID
     */
    UUID getProviderId();
}
