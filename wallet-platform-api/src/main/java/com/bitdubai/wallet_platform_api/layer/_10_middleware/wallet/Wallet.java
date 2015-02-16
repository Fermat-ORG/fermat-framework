package com.bitdubai.wallet_platform_api.layer._10_middleware.wallet;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.FiatCurrency;

import java.util.List;

/**
 * Created by ciencias on 2/15/15.
 */
public interface Wallet {

    public FiatAccount createFiatAccount (FiatCurrency fiatCurrency);

    public CryptoAccount createCryptoAccount (CryptoCurrency cryptoCurrency);

    public void removeFiatAccount (int index);

    public void removeCryptoAccount (int index);

    public FiatAccount getFiatAccount (int index);

    public CryptoAccount getCryptoAccount (int index);

    public void sizeOfFiatAccounts ();

    public void sizeOfCryptoAccounts ();

    public void transferFromFiatToFiat (FiatAccount fiatAccountFrom, FiatAccount fiatAccountTo, Double amountFrom, Double amountTo);

    public void transferFromCryptoToCrypto (CryptoAccount cryptoAccountFrom, CryptoAccount cryptoAccountTo, Double amountFrom, Double amountTo);
    
    public void transferFromFiatToCrypto (FiatAccount fiatAccountFrom, CryptoAccount cryptoAccountTo, Double amountFrom, Double amountTo);

    public void transferFromCryptoToFiat (  CryptoAccount cryptoAccountFrom, FiatAccount fiatAccountTo, Double amountFrom, Double amountTo);

}
