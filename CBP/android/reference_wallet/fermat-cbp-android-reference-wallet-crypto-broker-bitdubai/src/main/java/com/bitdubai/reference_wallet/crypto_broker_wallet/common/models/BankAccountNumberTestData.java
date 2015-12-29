package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;

/**
 * Created by nelson on 28/12/15.
 */
public class BankAccountNumberTestData implements BankAccountNumber {
    private String alias;
    private String account;
    private FiatCurrency currency;


    public BankAccountNumberTestData(String alias, String account, FiatCurrency currency) {
        this.alias = alias;
        this.account = account;
        this.currency = currency;
    }

    @Override
    public String getBankName() {
        return null;
    }

    @Override
    public BankAccountType getAccountType() {
        return null;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public FiatCurrency getCurrencyType() {
        return currency;
    }
}
