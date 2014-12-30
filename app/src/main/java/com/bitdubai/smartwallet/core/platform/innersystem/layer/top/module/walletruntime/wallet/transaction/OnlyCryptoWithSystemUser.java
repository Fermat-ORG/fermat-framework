package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.walletruntime.wallet.transaction;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.money.CryptoMoney;

/**
 * Created by ciencias on 22.12.14.
 */
public class OnlyCryptoWithSystemUser implements TransactionWithSystemUser, OnlyCryptoTransaction {

    private CryptoMoney mCryptoMoney;

}
