package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;

import java.util.UUID;

/**
 * Created by angel on 07/12/15.
 */
public class NegotiationBankAccountSale implements NegotiationBankAccount {

    private final UUID bankAccountId;
    private final String bankAccount;
    private final FiatCurrency typeBankAccount;

    public NegotiationBankAccountSale(UUID bankAccountId, String bankAccount,  FiatCurrency typeBankAccount){
        this.bankAccountId = bankAccountId;
        this.bankAccount = bankAccount;
        this.typeBankAccount = typeBankAccount;
    }

    @Override
    public UUID getBankAccountId() {
        return this.bankAccountId;
    }

    @Override
    public String getBankAccount() {
        return this.bankAccount;
    }

    @Override
    public FiatCurrency getCurrencyType() {
        return this.typeBankAccount;
    }
}
