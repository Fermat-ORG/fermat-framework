package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by franklin on 04/12/15.
 */
public interface FiatIndex {
    //TODO; Documentar y excepciones
    FermatEnum getMerchandise();
    float getSalePrice();
    float getPurchasePrice();
    float getSalePriceUpSpread();
    float getPurchasePriceDownSpread();
    float getSalePurchaseUpSpread();
    float getPurchasePurchaseDownSpread();
    float getPriceReference();
}
