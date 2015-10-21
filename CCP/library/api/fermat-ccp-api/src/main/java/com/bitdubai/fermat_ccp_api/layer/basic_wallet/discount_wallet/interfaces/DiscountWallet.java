package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

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
     * fiat amount of money the user has.
    */
    public long getBalance() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CantCalculateBalanceException;

    /*
     * Get the balance of the wallet, the result represents the
     * fiat amount of money the user has.
    */
    public long getAvailableAmount() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CantCalculateAvailableAmountException;

    /*
     * Debit a fiat amount of money that is equivalent to the crypto amount
     * taken as second parameter
     * The return value is the discount produced during the credit and
     * represents in fiat amount.
    */
    public long debit(long fiatAmount, long cryptoAmount) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.DebitFailedException;

    /*
     * Deposit a fiat amount of money that is equivalent to the crypto amount
     * taken as second parameter
    */
    public void credit(long fiatAmount, long cryptoAmount) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CreditFailedException;

    /*
     * Given a fiat amount and its equivalent crypto amount this methods calculates the
     * discount that would be produced if the user debit the same amounts
     * The return value represents fiat currency.
     */
    public long calculateDiscount(long fiatAmount, long cryptoAmount) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CalculateDiscountFailedException;

}