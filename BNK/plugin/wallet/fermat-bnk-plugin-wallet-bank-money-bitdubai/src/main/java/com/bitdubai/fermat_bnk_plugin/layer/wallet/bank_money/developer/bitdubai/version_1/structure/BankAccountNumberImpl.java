package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

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

    public BankAccountNumberImpl(String alias, String account, FiatCurrency currency,BankAccountType bankAccountType) {
        this.alias = alias;
        Account = account;
        this.currency = currency;
        this.bankAccountType=bankAccountType;
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

    //TODO: agregar bankName como setting y retornar el  acountType;

    @Override
    public String getBankName() {
        return "Test Account";
    }

    @Override
    public BankAccountType getAccountType() {
        return this.bankAccountType;
    }
}
