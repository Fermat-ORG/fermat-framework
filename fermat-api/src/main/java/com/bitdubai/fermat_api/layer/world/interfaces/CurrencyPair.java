package com.bitdubai.fermat_api.layer.world.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.world.interfaces.CurrencyPair</code>
 * Represents and contains all the basic information that a currency pair in fermat must have.
 *
 * Created by Leon Acosta (lnacosta) - (laion.cj91@gmail.com) on 28/01/2016.
 */
public interface CurrencyPair extends FermatEnum {

    Currency getCurrencyFrom();

    Currency getCurrencyTo();

}
