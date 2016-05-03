package com.bitdubai.fermat_api.layer.world.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by Alejandro Bicelis on 11/9/2015.
 * Updated by Leon Acosta (laion.cj91@gmail.com) on 07/02/2016.
 */
public interface Currency extends FermatEnum {
    //Placeholder class to use as generic for FiatCurrency and CryptoCurrency

    String getFriendlyName();

    /**
     * @return an element of the currency types determining which type of currency it is.
     */
    CurrencyTypes getType();

}
