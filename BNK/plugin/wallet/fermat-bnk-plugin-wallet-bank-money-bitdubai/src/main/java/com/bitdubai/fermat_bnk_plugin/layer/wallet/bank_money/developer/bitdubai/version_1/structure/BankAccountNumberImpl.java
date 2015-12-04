package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;

/**
 * Created by memo on 27/11/15.
 */
public class BankAccountNumberImpl implements BankAccountNumber {

    String alias;
    String Account;
    FiatCurrency currency;

    public BankAccountNumberImpl(String alias, String account, FiatCurrency currency) {
        this.alias = alias;
        Account = account;
        this.currency = currency;
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
}
