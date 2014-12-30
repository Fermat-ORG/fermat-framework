package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.walletruntime.wallet.account;

import com.bitdubai.smartwallet.core.platform.systemwide.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatAccount extends Account {
    private FiatCurrency mFiatCurrency;
    private BalanceChunk[] mBalanceChunks;

}
