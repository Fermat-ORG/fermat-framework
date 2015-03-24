package com.bitdubai.fermat_api.layer._12_middleware.wallet;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantInitializeWalletException;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CreditFailedException;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.TransferFailedException;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */
public interface Wallet {

    /**
     * Accounts functionality.
     */
    
    public UUID getWalletId();

    public FiatAccount[] getFiatAccounts();

    public CryptoAccount[] getCryptoAccounts();
    
    public FiatAccount createFiatAccount (FiatCurrency fiatCurrency);

    public CryptoAccount createCryptoAccount (CryptoCurrency cryptoCurrency);
    
    /**
     * Transactional functionality.
     */

    public void transfer (FiatAccount fiatAccountFrom, FiatAccount fiatAccountTo, long amountFrom, long amountTo, String memo) throws TransferFailedException;
    
    public void debit (FiatAccount fiatAccount,long fiatAmount, CryptoAccount cryptoAccount,long cryptoAmount) throws CreditFailedException;

    public void credit (FiatAccount fiatAccount,long fiatAmount, CryptoAccount cryptoAccount,long cryptoAmount) throws CreditFailedException;

}
