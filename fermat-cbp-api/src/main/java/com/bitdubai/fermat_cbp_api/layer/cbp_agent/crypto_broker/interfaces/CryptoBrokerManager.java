package com.bitdubai.fermat_cbp_api.layer.cbp_agent.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_agent.crypto_broker.exceptions.CantCalculateBasePriceMerchandiseCurrencyException;
/**
 * Created by Yordin Alayn on 29/09/15.
 */
public interface CryptoBrokerManager {

    CryptoBroker BasePriceMerchandiseCurrency(
         final Double totalMerchandiseCurrency
        ,final Double totalInvestedMerchandiseCurrency
    ) throws CantCalculateBasePriceMerchandiseCurrencyException;

    CryptoBroker Spread(
         final Float percentageSale
        ,final Float percentagePurchase
    ) throws CantCalculateBasePriceMerchandiseCurrencyException;

    CryptoBroker SuggestedPriceSale(
         final Float percentageSale
        ,final Float basePriceMerchandiseCurrency
    ) throws CantCalculateBasePriceMerchandiseCurrencyException;

    CryptoBroker SuggestedPricePurchase(
         final Float percentagePurchase
        ,final Float basePriceMerchandiseCurrency
    ) throws CantCalculateBasePriceMerchandiseCurrencyException;

    CryptoBroker ProtecionInvestmentSale(
         final Double referenceCurrency
        ,final Double priceMarketCurrent
        ,final Double suggestedPriceSale
        ,final Double priceSale
    ) throws CantCalculateBasePriceMerchandiseCurrencyException;

    CryptoBroker ProtecionInvestmentPurchase(
            final Double referenceCurrency
            ,final Double priceMarketCurrent
            ,final Double suggestedPricePurchase
            ,final Double pricePurchase
    ) throws CantCalculateBasePriceMerchandiseCurrencyException;
}
