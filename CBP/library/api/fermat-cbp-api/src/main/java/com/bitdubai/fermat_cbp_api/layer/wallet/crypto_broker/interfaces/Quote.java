package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by franklin on 04/12/15.
 */
public interface Quote {
    //TODO; Documentar y excepciones
    FermatEnum getMerchandise();
    FiatCurrency getFiatCurrency();
    float getPriceReference();
    float getQuantity();
}
