package com.bitdubai.smartwallet.core;

/**
 * Created by ciencias on 21.12.14.
 */
public class Refill extends ExternalTransaction {

    private long mTimestamp;

    private FiatCurrency mFiatCurrency;
    private double mFiatAmount;

    private CryptoCurrency mCryptoCurrency;
    private double mCryptoAmount;

    private BalanceChunk mBalanceChunk = new BalanceChunk();

    private RefillPoint mRefillPoint;

}
