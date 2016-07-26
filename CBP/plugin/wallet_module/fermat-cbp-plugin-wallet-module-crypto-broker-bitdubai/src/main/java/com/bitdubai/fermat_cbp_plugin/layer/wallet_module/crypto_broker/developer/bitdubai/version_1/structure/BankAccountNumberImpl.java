package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;

/**
 * Created by franklin on 20/01/16.
 */
public class BankAccountNumberImpl implements BankAccountNumber {
    private String bankName;
    private BankAccountType bankAccountType;
    private String alias;
    private String account;
    private FiatCurrency currencyType;
    private String imageId;

    public BankAccountNumberImpl(String bankName, BankAccountType bankAccountType, String alias, String account, FiatCurrency currencyType, String imageId) {
        this.bankName = bankName;
        this.bankAccountType = bankAccountType;
        this.alias = alias;
        this.account = account;
        this.currencyType = currencyType;
        this.imageId = imageId;
    }

    public BankAccountNumberImpl() {
    }

    @Override
    public String getBankName() {
        return bankName;
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
        return currencyType;
    }

    @Override
    public String getAccountImageId() {
        return imageId;
    }
}
