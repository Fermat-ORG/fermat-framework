package com.bitdubai.fermat_api.layer._1_definition.money;

import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatMoney implements Money {

    private FiatCurrency mFiatCurrency;
    private double mFiatAmount;
}
