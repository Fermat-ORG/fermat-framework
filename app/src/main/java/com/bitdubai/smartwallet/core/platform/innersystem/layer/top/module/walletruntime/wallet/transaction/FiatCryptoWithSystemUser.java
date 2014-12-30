package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.walletruntime.wallet.transaction;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.money.CryptoMoney;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.money.FiatMoney;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.walletruntime.wallet.account.Account;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatCryptoWithSystemUser implements TransactionWithSystemUser, FiatCryptoTransaction {

    private Account mAccount;

    private FiatMoney mFiatMoney;
    private CryptoMoney mCryptoMoney;


}
