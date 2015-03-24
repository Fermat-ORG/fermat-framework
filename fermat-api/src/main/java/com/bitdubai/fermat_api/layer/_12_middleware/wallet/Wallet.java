package com.bitdubai.fermat_api.layer._12_middleware.wallet;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantInitializeWalletException;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */
public interface Wallet {

    
    public UUID getWalletId();

    public FiatAccount[] getFiatAccounts();

    public CryptoAccount[] getCryptoAccounts();
    
    public FiatAccount createFiatAccount (FiatCurrency fiatCurrency);

    public CryptoAccount createCryptoAccount (CryptoCurrency cryptoCurrency);

    
    
    
    


    public void transferFromFiatToFiat (FiatAccount fiatAccountFrom, FiatAccount fiatAccountTo, Double amountFrom, Double amountTo);

    public void transferFromCryptoToCrypto (CryptoAccount cryptoAccountFrom, CryptoAccount cryptoAccountTo, Double amountFrom, Double amountTo);
    
    public void transferFromFiatToCrypto (FiatAccount fiatAccountFrom, CryptoAccount cryptoAccountTo, Double amountFrom, Double amountTo);

    public void transferFromCryptoToFiat (  CryptoAccount cryptoAccountFrom, FiatAccount fiatAccountTo, Double amountFrom, Double amountTo);

    public void debitFiatAccount (FiatAccount fiatAccount,Double amount);

    public void creditFiatAccount (FiatAccount fiatAccount,Double amount);

    public void debitCryptoAccount (CryptoAccount cryptoAccount,Double amount);

    public void creditCryptoAccount (CryptoAccount cryptoAccount,Double amount);
    
}
