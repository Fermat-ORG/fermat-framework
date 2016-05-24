package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 26.09.15.
 */
public interface BankMoneyTransactionRecord extends Serializable{

    UUID getBankTransactionId();

    BankTransactionStatus getStatus();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    /*String getPublicKeyActorFrom();

    String getPublicKeyActorTo();*/

    float getAmount();

    FiatCurrency getCurrencyType();

    BankOperationType getBankOperationType();

    String getBankDocumentReference();

    String getBankName();

    String getBankAccountNumber();

    BankAccountType getBankAccountType();

    long getTimestamp();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();

}
