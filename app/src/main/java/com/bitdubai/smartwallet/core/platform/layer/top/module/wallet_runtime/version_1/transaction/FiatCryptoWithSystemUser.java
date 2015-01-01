package com.bitdubai.smartwallet.core.platform.layer.top.module.wallet_runtime.version_1.transaction;

import com.bitdubai.smartwallet.core.platform.global.definitions.money.CryptoMoney;
import com.bitdubai.smartwallet.core.platform.global.definitions.money.FiatMoney;
import com.bitdubai.smartwallet.core.platform.layer.top.module.wallet_runtime.version_1.account.Account;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatCryptoWithSystemUser implements TransactionWithSystemUser, FiatCryptoTransaction {

    private Account mAccount;

    private FiatMoney mFiatMoney;
    private CryptoMoney mCryptoMoney;


}
