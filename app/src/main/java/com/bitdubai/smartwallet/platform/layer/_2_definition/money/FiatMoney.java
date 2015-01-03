package com.bitdubai.smartwallet.platform.layer._2_definition.money;

import com.bitdubai.smartwallet.platform.layer._2_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatMoney implements Money {

    private FiatCurrency mFiatCurrency;
    private double mFiatAmount;
}
