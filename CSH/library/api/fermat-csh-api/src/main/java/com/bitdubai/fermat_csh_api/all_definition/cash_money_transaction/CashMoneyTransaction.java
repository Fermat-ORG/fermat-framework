package com.bitdubai.fermat_csh_api.all_definition.cash_money_transaction;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 18.09.2015
 */

public interface CashMoneyTransaction {

    UUID getCashTransactionId();

    String getPublicKeyActorTo();

    String getPublicKeyActorFrom();

    CashTransactionStatus getStatus();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    float getAmount();

    CashCurrencyType getCashCurrencyType();

    String getCashReference();

    long getTimestamp();

}