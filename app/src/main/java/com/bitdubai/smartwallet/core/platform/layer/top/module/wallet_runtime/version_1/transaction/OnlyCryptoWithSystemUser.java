package com.bitdubai.smartwallet.core.platform.layer.top.module.wallet_runtime.version_1.transaction;

import com.bitdubai.smartwallet.core.platform.system_wide.definitions.money.CryptoMoney;

/**
 * Created by ciencias on 22.12.14.
 */
public class OnlyCryptoWithSystemUser implements TransactionWithSystemUser, OnlyCryptoTransaction {

    private CryptoMoney mCryptoMoney;

}
