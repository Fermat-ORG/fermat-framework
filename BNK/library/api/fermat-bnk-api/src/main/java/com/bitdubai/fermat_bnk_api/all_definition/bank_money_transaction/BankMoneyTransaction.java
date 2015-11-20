package com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 18.09.2015
 */

public interface BankMoneyTransaction {

    UUID getBankTransactionId();

    String getPublicKeyActorTo();

    String getPublicKeyActorFrom();

    BankTransactionStatus getStatus();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    float getAmount();

    BankCurrencyType getBankCurrencyType();

    BankOperationType getBankOperationType();

    String getBankDocumentReference();

    String getBankToName();

    String getBankToAccountNumber();

    BankAccountType getBankToAccountType();

    String getBankFromName();

    String getBankFromAccountNumber();

    BankAccountType getBankFromAccountType();

    long getTimestamp();

}