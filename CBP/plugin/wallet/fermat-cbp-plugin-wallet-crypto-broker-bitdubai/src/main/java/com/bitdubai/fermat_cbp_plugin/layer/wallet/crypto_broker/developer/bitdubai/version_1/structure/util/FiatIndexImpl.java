package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;

/**
 * Created by franklin on 07/12/15.
 */
public class FiatIndexImpl implements FiatIndex {
    //Documentar
    FermatEnum merchandise;
    float salePrice;
    float purchasePrice;
    float salePriceUpSpread;
    float salePriceDownSpread;
    float purchasePriceUpSpread;
    float purchasePriceDownSpread;
    float priceReference;
    float volatility;
    FiatCurrency fiatCurrency;

    public FiatIndexImpl(FermatEnum merchandise,
                         float salePrice,
                         float purchasePrice,
                         float salePriceUpSpread,
                         float salePriceDownSpread,
                         float purchasePriceUpSpread,
                         float purchasePriceDownSpread,
                         float priceReference,
                         float volatility,
                         FiatCurrency fiatCurrency) {
        this.merchandise = merchandise;
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
        this.salePriceUpSpread = salePriceUpSpread;
        this.salePriceDownSpread = salePriceDownSpread;
        this.purchasePriceUpSpread = purchasePriceUpSpread;
        this.purchasePriceDownSpread = purchasePriceDownSpread;
        this.priceReference = priceReference;
        this.volatility = volatility;
        this.fiatCurrency = fiatCurrency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FermatEnum getMerchandise() {
        return merchandise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getSalePrice() {
        return salePrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getSalePriceUpSpread() {
        return salePriceUpSpread;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPurchasePriceDownSpread() {
        return purchasePriceDownSpread;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getSalePurchaseUpSpread() {
        return salePriceUpSpread;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPurchasePurchaseDownSpread() {
        return purchasePriceDownSpread;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPriceReference() {
        return priceReference;
    }

    @Override
    public float getPriceVolatility() {
        return this.volatility;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
    }
}
