package com.bitdubai.fermat_api.layer._12_middleware.wallet;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.*;
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

    public Account[] getFiatAccounts();

    public Account createAccount(FiatCurrency fiatCurrency) throws CantCreateAccountException;


    /**
     * Transactional functionality.
     */

    public void transfer (Account accountFrom, Account accountTo, long amountFrom, long amountTo, String memo) throws TransferFailedException;
    
    public void debit (Account account,long fiatAmount, CryptoCurrency cryptoCurrency,long cryptoAmount) throws DebitFailedException;

    public void credit (Account account,long fiatAmount, CryptoCurrency cryptoCurrency,long cryptoAmount) throws CreditFailedException;

}
