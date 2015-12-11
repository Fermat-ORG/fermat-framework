package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;

/**
 * Created by franklin on 07/12/15.
 */
public class FiatIndexImpl implements FiatIndex {
    //Documentar
    FermatEnum merchandise;
    float      salePrice;
    float      purchasePrice;
    float      salePriceUpSpread;
    float      salePriceDownSpread;
    float      purchasePriceUpSpread;
    float      purchasePriceDownSpread;

    public FiatIndexImpl(FermatEnum merchandise,
                         float      salePrice,
                         float      purchasePrice,
                         float      salePriceUpSpread,
                         float      salePriceDownSpread,
                         float      purchasePriceUpSpread,
                         float      purchasePriceDownSpread)
    {
        this.merchandise                = merchandise;
        this.salePrice                  = salePrice;
        this.purchasePrice              = purchasePrice;
        this.salePriceUpSpread          = salePriceUpSpread;
        this.salePriceDownSpread        = salePriceDownSpread;
        this.purchasePriceUpSpread      = purchasePriceUpSpread;
        this.purchasePriceDownSpread    = purchasePriceDownSpread;
    }

    @Override
    public FermatEnum getMerchandise() {
        return merchandise;
    }

    @Override
    public float getSalePrice() {
        return salePrice;
    }

    @Override
    public float getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public float getSalePriceUpSpread() {
        return salePriceUpSpread;
    }

    @Override
    public float getPurchasePriceDownSpread() {
        return purchasePriceDownSpread;
    }

    @Override
    public float getSalePurchaseUpSpread() {
        return salePriceUpSpread;
    }

    @Override
    public float getPurchasePurchaseDownSpread() {
        return purchasePriceDownSpread;
    }
}
