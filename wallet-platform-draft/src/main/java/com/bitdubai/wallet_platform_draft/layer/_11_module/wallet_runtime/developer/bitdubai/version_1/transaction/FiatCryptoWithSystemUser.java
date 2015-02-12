package com.bitdubai.wallet_platform_draft.layer._11_module.wallet_runtime.developer.bitdubai.version_1.transaction;

import com.bitdubai.wallet_platform_api.layer._1_definition.money.CryptoMoney;
import com.bitdubai.wallet_platform_api.layer._1_definition.money.FiatMoney;
import com.bitdubai.wallet_platform_draft.layer._11_module.wallet_runtime.developer.bitdubai.version_1.account.Account;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatCryptoWithSystemUser implements TransactionWithSystemUser, FiatCryptoTransaction {

    private Account mAccount;

    private FiatMoney mFiatMoney;
    private CryptoMoney mCryptoMoney;


}
