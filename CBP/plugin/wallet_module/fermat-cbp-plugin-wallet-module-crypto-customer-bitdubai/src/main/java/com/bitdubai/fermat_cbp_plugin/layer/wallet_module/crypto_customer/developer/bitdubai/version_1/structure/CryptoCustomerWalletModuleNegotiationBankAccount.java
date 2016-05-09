package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;

import java.util.UUID;


/**
 * Created by nelson on 01/03/16.
 */
public class CryptoCustomerWalletModuleNegotiationBankAccount implements NegotiationBankAccount {

    private FiatCurrency currencyType;
    private UUID bankAccountId;
    private String bankAccount;

    public CryptoCustomerWalletModuleNegotiationBankAccount(FiatCurrency currencyType, String bankAccount) {
        bankAccountId = UUID.randomUUID();

        this.currencyType = currencyType;
        this.bankAccount = bankAccount;
    }

    @Override
    public UUID getBankAccountId() {
        return bankAccountId;
    }

    @Override
    public String getBankAccount() {
        return bankAccount;
    }

    @Override
    public FiatCurrency getCurrencyType() {
        return currencyType;
    }
}
