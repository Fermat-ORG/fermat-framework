package com.bitdubai.fermat_cbp_api.all_definition.world;

import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrencyType;

/**
 * Created by jorge on 30-10-2015.
 */
public interface Index {
    void setCurrency(FiatCurrencyType currency);
    void setReferenceCurrency(FiatCurrencyType currency);
    void setSalePrice(double salePrice);
    void setPurchasePrice(double purchasePrice);
    void setTimeStamp(long timestamp);

    FiatCurrencyType getCurrency();
    FiatCurrencyType getReferenceCurrency();
    double getSalePrice();
    double getPurchasePrice();
    long getTimeStamp();
}
