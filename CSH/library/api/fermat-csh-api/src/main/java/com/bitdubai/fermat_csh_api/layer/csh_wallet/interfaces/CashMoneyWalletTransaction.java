package com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 26.09.15.
 * Modified by Alejandro Bicelis on 23/11/2015
 */
public interface CashMoneyWalletTransaction {

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    UUID getCashTransactionId();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    String getPublicKeyActorFrom();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    String getPublicKeyActorTo();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    CashTransactionStatus getStatus();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    BalanceType getBalanceType();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    TransactionType getTransactionType();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    double getAmount();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    CashCurrencyType getCashCurrencyType();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    String getCashReference();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    long getTimestamp();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    long getRunningBookBalance();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    long getRunningAvailableBalance();

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    String getMemo();

}
