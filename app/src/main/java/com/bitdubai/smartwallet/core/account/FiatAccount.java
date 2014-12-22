package com.bitdubai.smartwallet.core.account;

import com.bitdubai.smartwallet.core.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatAccount extends Account {
    private FiatCurrency mFiatCurrency;
    private BalanceChunk[] mBalanceChunks;

}
