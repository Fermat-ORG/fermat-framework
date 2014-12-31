package com.bitdubai.smartwallet.core.platform.system_wide.definitions.money;

import com.bitdubai.smartwallet.core.platform.system_wide.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatMoney implements Money {

    private FiatCurrency mFiatCurrency;
    private double mFiatAmount;
}
