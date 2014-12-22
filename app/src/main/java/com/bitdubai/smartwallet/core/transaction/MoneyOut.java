package com.bitdubai.smartwallet.core.transaction;

import com.bitdubai.smartwallet.core.BalanceChunk;

/**
 * Created by ciencias on 21.12.14.
 */
public abstract class MoneyOut extends FiatCryptoExternalTransaction {

    private BalanceChunk mBalanceChunk = new BalanceChunk();

}
