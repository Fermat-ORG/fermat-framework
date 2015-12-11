package com.bitdubai.fermat_cer_api.layer.provider.interfaces;

import com.bitdubai.fermat_cer_api.all_definition.enums.Currency;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public interface CurrencyPair {
    public Currency getFrom();
    public Currency getTo();
}