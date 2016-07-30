package com.bitdubai.fermat_cer_api.all_definition.interfaces;


import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.io.Serializable;


/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public interface CurrencyPair extends Serializable {
    Currency getFrom();

    Currency getTo();
}