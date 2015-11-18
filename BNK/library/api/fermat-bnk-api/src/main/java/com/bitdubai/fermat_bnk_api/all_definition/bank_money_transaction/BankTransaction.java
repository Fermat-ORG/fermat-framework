package com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;

import java.util.UUID;

/**
 * Created by memo on 18/11/15.
 */
public interface BankTransaction {

    UUID getTransactionId();

    String getPublicKeyPlugin();

    String getPublicKeyWallet();


    String getPublicKeyActor();


    float getAmount();


    long getAccountNumber();


    FiatCurrency getCurrency();


    String getMemo();

    BankOperationType getBankOperationType();

    TransactionType getTransactionType();

    long getTimestamp();

}
