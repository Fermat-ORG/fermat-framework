package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.classes;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;

/**
 * Created by memo on 27/11/15.
 */
public class BankAccountNumberImpl implements BankAccountNumber {

    String alias;
    String Account;
    FiatCurrency currency;
    BankAccountType bankAccountType;
    String bankName;
    String imageId;

    public BankAccountNumberImpl(String alias, String account, FiatCurrency currency,BankAccountType bankAccountType,String bankName, String imageId) {
        this.alias = alias;
        Account = account;
        this.currency = currency;
        this.bankAccountType=bankAccountType;
        this.bankName = bankName;
        this.imageId = imageId;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getAccount() {
        return Account;
    }

    @Override
    public FiatCurrency getCurrencyType() {
        return currency;
    }

    @Override
    public String getAccountImageId() { return imageId; }

    @Override
    public String getBankName() {
        return bankName;
    }

    @Override
    public BankAccountType getAccountType() {
        return this.bankAccountType;
    }
}
