package com.bitdubai.smartwallet.core.platform.layer.top.module.wallet_runtime.version_1.account;

import com.bitdubai.smartwallet.core.platform.system_wide.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatAccount implements  Account {
    private FiatCurrency mFiatCurrency;
    private BalanceChunk[] mBalanceChunks;

}
