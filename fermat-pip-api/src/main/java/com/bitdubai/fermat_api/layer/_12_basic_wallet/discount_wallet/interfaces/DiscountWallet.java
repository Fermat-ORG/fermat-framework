package com.bitdubai.fermat_api.layer._12_basic_wallet.discount_wallet.interfaces;

import com.bitdubai.fermat_api.layer._12_basic_wallet.discount_wallet.exceptions.*;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */
public interface DiscountWallet {

    /*
     * Get wallet Id
    */
    public UUID getWalletId();

    /*
     * This wallet works with a fixed crypto currency
    */
    public CryptoCurrency getCryptoCurrency();

    /*
     * This wallet works with a fixed Fiat currency
    */
    public FiatCurrency getFiatCurrency();

    /*
     * Get the balance of the wallet, the result represents the
     * fiat amuont of money the user has.
    */
    public long balance();

    /*
     * Get the balance of the wallet, the result represents the
     * fiat amuont of money the user has.
    */
    public long available();

    /*
     * Debit a fiat amount of money that is equivalent to the crypto amount 
     * taken as second parameter
    */
    public void debit (long fiatAmount, long cryptoAmount) throws DebitFailedException;

    /*
     * Deposit a fiat amount of money that is equivalent to the crypto amount 
     * taken as second parameter
    */
    public void credit (long fiatAmount, long cryptoAmount) throws CreditFailedException;

}
