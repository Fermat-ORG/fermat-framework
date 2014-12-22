package com.bitdubai.smartwallet.core;

import com.bitdubai.smartwallet.core.transaction.FiatCryptoExternalTransaction;

/**
 * Created by ciencias on 21.12.14.
 */
public class CashOut extends FiatCryptoExternalTransaction {

    private BalanceChunk mBalanceChunk = new BalanceChunk();

    private CashOutPoint mCashOutPoint;
}
