package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

import java.io.Serializable;


/**
 * Created by franklin on 04/12/15.
 */
public interface FiatIndex extends Serializable {
    //TODO; Documentar y excepciones

    /**
     * The method <code>getMerchandise</code> returns the merchandise of the FiatIndex
     *
     * @return a FermatEnum of the merchandise
     */
    FermatEnum getMerchandise();

    /**
     * The method <code>getSalePrice</code> returns sale price of the FiatIndex
     *
     * @return a float of the sale price
     */
    float getSalePrice();

    /**
     * The method <code>getPurchasePrice</code> returns the purchase price of the FiatIndex
     *
     * @return a float of the purchase price
     */
    float getPurchasePrice();

    /**
     * The method <code>getSalePriceUpSpread</code> returns the sale price up spread of the FiatIndex
     *
     * @return a float of the sale price up spread
     */
    float getSalePriceUpSpread();

    /**
     * The method <code>getPurchasePriceDownSpread</code> returns the purchase price down spread of the FiatIndex
     *
     * @return a float of the purchase price down spread
     */
    float getPurchasePriceDownSpread();

    /**
     * The method <code>getSalePurchaseUpSpread</code> returns the sale purchase up spread of the FiatIndex
     *
     * @return a float of the sale purchase up spread
     */
    float getSalePurchaseUpSpread();

    /**
     * The method <code>getPurchasePurchaseDownSpread</code> returns the purchase up spread of the FiatIndex
     *
     * @return a float of the sale purchase down spread
     */
    float getPurchasePurchaseDownSpread();

    /**
     * The method <code>getPriceReference</code> returns the price reference of the FiatIndex
     *
     * @return a float of the price reference
     */
    float getPriceReference();

    /**
     * The method <code>getPriceVolatility</code> returns the price reference of the FiatIndex
     *
     * @return a float of the volatility of rate
     */
    float getPriceVolatility();

    /**
     * The method <code>getFiatCurrency</code> returns the fiat currency of the FiatIndex
     *
     * @return FiatCurrency
     */
    FiatCurrency getFiatCurrency();
}
