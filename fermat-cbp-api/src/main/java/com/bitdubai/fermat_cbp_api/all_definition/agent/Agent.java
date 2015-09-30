package com.bitdubai.fermat_cbp_api.all_definition.agent;

/**
 * Created by Yordin Alayn on 25,09,15.
 */
public interface Agent {

    Double getTotalMerchandiseCurrency();

    Double getTotalInvestedMerchandiseCurrency();

    Float getPercentageSale();

    Float getPercentagePurchase();

    Double getPriceReferenceCurrencyCurrent();

    Double getPriceMarketMerchandiseCurrencyCurrent();

    Double getPriceSale();

    Double getPricePurchase();
}
