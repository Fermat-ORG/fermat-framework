package com.bitdubai.smartwallet.core.wallet.transaction;

import com.bitdubai.smartwallet.core.system.money.CryptoMoney;

/**
 * Created by ciencias on 22.12.14.
 */
public class OnlyCryptoWithSystemUser implements TransactionWithSystemUser, OnlyCryptoTransaction {

    private CryptoMoney mCryptoMoney;

}
