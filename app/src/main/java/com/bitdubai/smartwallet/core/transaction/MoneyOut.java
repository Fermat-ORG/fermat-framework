package com.bitdubai.smartwallet.core.transaction;

import com.bitdubai.smartwallet.core.account.BalanceChunk;

/**
 * Created by ciencias on 21.12.14.
 */
public abstract class MoneyOut extends FiatCryptoTransaction {

    private MoneySource[] mMoneySource = new BalanceChunk();
    private Discount mDiscount;
}
