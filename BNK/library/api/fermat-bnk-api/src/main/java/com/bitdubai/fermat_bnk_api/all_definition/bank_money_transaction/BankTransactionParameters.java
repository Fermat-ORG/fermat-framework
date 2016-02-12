package com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by memo on 18/11/15.
 */
public interface BankTransactionParameters {

    UUID getTransactionId();

    String getPublicKeyPlugin();

    String getPublicKeyWallet();


    String getPublicKeyActor();


    BigDecimal getAmount();


    String getAccount();


    FiatCurrency getCurrency();


    String getMemo();
}
