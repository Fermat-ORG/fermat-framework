package com.bitdubai.fermat_cbp_api.layer.cbp_agent.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_agent.crypto_broker.exceptions.CantCalculateBasePriceMerchandiseCurrencyException;
import com.bitdubai.fermat_cbp_api.layer.cbp_agent.crypto_broker.exceptions.CantCalculateSpreadException;
import com.bitdubai.fermat_cbp_api.layer.cbp_agent.crypto_broker.exceptions.CantCalculateSuggestedPriceSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_agent.crypto_broker.exceptions.CantCalculateSuggestedPricePurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_agent.crypto_broker.exceptions.CantCalculateProtecionInvestmentSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_agent.crypto_broker.exceptions.CantCalculateProtecionInvestmentPurchaseException;


/**
 * Created by Yordin Alayn on 29.09.15.
 */
public interface CryptoBrokerManager {

    CryptoBroker BasePriceMerchandiseCurrency(
         final Double totalMerchandiseCurrency
        ,final Double totalInvestedMerchandiseCurrency
    ) throws CantCalculateBasePriceMerchandiseCurrencyException;

    CryptoBroker Spread(
         final Float percentageSale
        ,final Float percentagePurchase
    ) throws CantCalculateSpreadException;

    CryptoBroker SuggestedPriceSale(
         final Float percentageSale
        ,final Float basePriceMerchandiseCurrency
    ) throws CantCalculateSuggestedPriceSaleException;

    CryptoBroker SuggestedPricePurchase(
         final Float percentagePurchase
        ,final Float basePriceMerchandiseCurrency
    ) throws CantCalculateSuggestedPricePurchaseException;

    CryptoBroker ProtecionInvestmentSale(
         final Double referenceCurrency
        ,final Double priceMarketCurrent
        ,final Double suggestedPriceSale
        ,final Double priceSale
    ) throws CantCalculateProtecionInvestmentSaleException;

    CryptoBroker ProtecionInvestmentPurchase(
         final Double referenceCurrency
        ,final Double priceMarketCurrent
        ,final Double suggestedPricePurchase
        ,final Double pricePurchase
    ) throws CantCalculateProtecionInvestmentPurchaseException;
}
