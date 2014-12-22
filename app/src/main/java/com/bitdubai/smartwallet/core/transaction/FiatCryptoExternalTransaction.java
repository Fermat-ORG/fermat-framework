package com.bitdubai.smartwallet.core.transaction;

import com.bitdubai.smartwallet.core.Account;
import com.bitdubai.smartwallet.core.CryptoCurrency;
import com.bitdubai.smartwallet.core.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatCryptoExternalTransaction extends ExternalTransaction {

    private Account mAccount;

    private FiatCurrency mFiatCurrency;
    private double mFiatAmount;

    private CryptoCurrency mCryptoCurrency;
    private double mCryptoAmount;

}
