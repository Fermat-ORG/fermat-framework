package com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.money;

import com.bitdubai.smartwallet.core.platform.systemwide.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatMoney implements Money {

    private FiatCurrency mFiatCurrency;
    private double mFiatAmount;
}
