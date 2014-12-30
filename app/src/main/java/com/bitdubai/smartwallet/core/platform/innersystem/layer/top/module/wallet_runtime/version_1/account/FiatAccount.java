package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.wallet_runtime.version_1.account;

import com.bitdubai.smartwallet.core.platform.systemwide.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatAccount extends Account {
    private FiatCurrency mFiatCurrency;
    private BalanceChunk[] mBalanceChunks;

}
