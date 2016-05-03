package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.io.Serializable;


/**
 * Created by franklin on 04/12/15.
 */
public interface Quote extends Serializable {
    //TODO; Documentar y excepciones

    /**
     * The method <code>getMerchandise</code> returns the merchandise of the Quote
     *
     * @return a FermatEnum of the merchandise
     */
    FermatEnum getMerchandise();

    /**
     * The method <code>getFiatCurrency</code> returns the fiat currency of the Quote
     *
     * @return FiatCurrency
     */
    Currency getFiatCurrency();

    /**
     * The method <code>getPriceReference</code> returns the price reference of the Quote
     *
     * @return a float of the price reference
     */
    float getPriceReference();

    /**
     * The method <code>getQuantity</code> returns the quantity of the Quote
     *
     * @return a float of the quantity
     */
    float getQuantity();
}
