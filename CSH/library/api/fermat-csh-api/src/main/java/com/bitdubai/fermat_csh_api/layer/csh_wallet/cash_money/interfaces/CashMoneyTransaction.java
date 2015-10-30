package com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 26.09.15.
 */
public interface CashMoneyTransaction{

    UUID getCashTransactionId();

    String getPublicKeyActorFrom();

    String getPublicKeyActorTo();

    CashTransactionStatus getStatus();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    double getAmount();

    CashCurrencyType getCashCurrencyType();

    String getCashReference();

    long getTimestamp();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();

}
