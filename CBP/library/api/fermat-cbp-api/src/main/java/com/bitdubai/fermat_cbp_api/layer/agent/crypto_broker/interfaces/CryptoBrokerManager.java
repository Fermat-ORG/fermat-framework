package com.bitdubai.fermat_cbp_api.layer.agent.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.agent.crypto_broker.exceptions.CantCalculateProtecionInvestmentPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.agent.crypto_broker.exceptions.CantCalculateSpreadException;
import com.bitdubai.fermat_cbp_api.layer.agent.crypto_broker.exceptions.CantCalculateSuggestedPricePurchaseException;
import com.bitdubai.fermat_cbp_api.layer.agent.crypto_broker.exceptions.CantCalculateSuggestedPriceSaleException;


/**
 * Created by Yordin Alayn on 29.09.15.
 */
public interface CryptoBrokerManager {

    CryptoBroker basePriceMerchandiseCurrency(
            final Double totalMerchandiseCurrency
            , final Double totalInvestedMerchandiseCurrency
    ) throws com.bitdubai.fermat_cbp_api.layer.agent.crypto_broker.exceptions.CantCalculateBasePriceMerchandiseCurrencyException;

    CryptoBroker spread(
            final Float percentageSale
            , final Float percentagePurchase
    ) throws CantCalculateSpreadException;

    CryptoBroker suggestedPriceSale(
            final Float percentageSale
            , final Float basePriceMerchandiseCurrency
    ) throws CantCalculateSuggestedPriceSaleException;

    Float suggestedPercentageSale();

    Float suggestedPercentagePurchase();

    CryptoBroker suggestedPricePurchase(
            final Float percentagePurchase
            , final Float basePriceMerchandiseCurrency
    ) throws CantCalculateSuggestedPricePurchaseException;

    CryptoBroker protecionInvestmentSale(
            final Double priceReferenceCurrencyCurrent
            , final Double priceMarketMerchandiseCurrencyCurrent
            , final Double suggestedPriceSale
            , final Double priceSale
    ) throws com.bitdubai.fermat_cbp_api.layer.agent.crypto_broker.exceptions.CantCalculateProtecionInvestmentSaleException;

    CryptoBroker protecionInvestmentPurchase(
            final Double priceReferenceCurrencyCurrent
            , final Double priceMarketMerchandiseCurrencyCurrent
            , final Double suggestedPricePurchase
            , final Double pricePurchase
    ) throws CantCalculateProtecionInvestmentPurchaseException;
}
