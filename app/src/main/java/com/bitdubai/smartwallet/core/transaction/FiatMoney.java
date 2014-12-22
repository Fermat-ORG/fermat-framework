package com.bitdubai.smartwallet.core.transaction;

import com.bitdubai.smartwallet.core.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatMoney extends Money {

    private FiatCurrency mFiatCurrency;
    private double mFiatAmount;
}
