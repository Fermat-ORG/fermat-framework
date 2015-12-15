package com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;


/**
 * Created by memo on 09/12/15.
 */
public class BankAccountNumberImpl implements BankAccountNumber {

    private BankAccountType bankAccountType;
    private String alias;
    private String account;
    private FiatCurrency fiatCurrency;

    public BankAccountNumberImpl(BankAccountType bankAccountType, String alias, String account, FiatCurrency fiatCurrency) {
        this.bankAccountType = bankAccountType;
        this.alias = alias;
        this.account = account;
        this.fiatCurrency = fiatCurrency;
    }

    @Override
    public String getBankName() {
        return "Test bank name";
    }

    @Override
    public BankAccountType getAccountType() {
        return bankAccountType;
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
        return fiatCurrency;
    }
}
