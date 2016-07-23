package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;

import java.util.UUID;

/**
 * Created by franklin on 29/12/15.
 */
public class NegotiationBankAccountImpl implements NegotiationBankAccount {
    private final UUID bankAccountId;
    private final String bankAccount;
    private final FiatCurrency fiatCurrency;

    public NegotiationBankAccountImpl(UUID bankAccountId,
                                      String bankAccount,
                                      FiatCurrency fiatCurrency) {
        this.bankAccountId = bankAccountId;
        this.bankAccount = bankAccount;
        this.fiatCurrency = fiatCurrency;

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
        return fiatCurrency;
    }
}
