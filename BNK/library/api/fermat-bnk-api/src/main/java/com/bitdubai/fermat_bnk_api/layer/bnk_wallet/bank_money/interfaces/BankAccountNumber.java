package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;

import java.io.Serializable;


/**
 * Created by memo on 24/11/15.
 * Modified by Alejandro Bicelis on 13/05/16
 */
public interface BankAccountNumber extends Serializable {


    /**
     * Name of the bank
     */
    String getBankName();

    /**
     * Type of account, savings, checking..
     */
    BankAccountType getAccountType();

    /**
     * An alias for the bank account
     */
    String getAlias();

    /**
     * Stupidly badly named bank account number
     */
    String getAccount();

    /**
     * Currency type of the bank account
     */
    FiatCurrency getCurrencyType();

    /**
    * This image id is used by the wallet so that it can display the selected image associated with the account
    */
    String getAccountImageId();

}
