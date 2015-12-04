package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

import java.util.UUID;

/**
 * Created by angel on 03/12/15.
 */
public interface NegotiationBankAccount {

    UUID getBankAccountId();
    String getBankAccount();
    FiatCurrency getCurrencyType();
}
