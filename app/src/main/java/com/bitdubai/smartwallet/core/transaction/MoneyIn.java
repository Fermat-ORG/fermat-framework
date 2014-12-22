package com.bitdubai.smartwallet.core.transaction;

import com.bitdubai.smartwallet.core.account.BalanceChunk;

/**
 * Created by ciencias on 21.12.14.
 */
public abstract class MoneyIn extends FiatCryptoTransaction {

    private BalanceChunk mBalanceChunk = new BalanceChunk();

}
