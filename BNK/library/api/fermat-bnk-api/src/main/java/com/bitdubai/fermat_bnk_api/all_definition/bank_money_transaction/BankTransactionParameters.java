package com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

import java.util.UUID;

/**
 * Created by memo on 18/11/15.
 */
public interface BankTransactionParameters {
    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    UUID getTransactionId();

    /**
     * Returns the public key of the CASH wallet linked to this transaction
     * @return      Public key of the CSH wallet
     */
    String getPublicKeyWallet();

    /**
     * Returns the public key of the Actor which made the transaction
     * @return      Public key of the Actor which made the transaction
     */
    String getPublicKeyActor();

    /**
     * Returns the amount of the transaction
     * @return      Amount of the transaction
     */
    float getAmount();

    /**
     * Returns the account number of the transaction
     * @return      account number of the transaction
     */
    int getAccountNumber();

    /**
     * Returns the currency of the transaction (e.g. USD, EUR)
     * @return      Currency of the transaction
     */
    FiatCurrency getCurrency();

    /**
     * Returns the memo of the transaction
     * @return      Memo of the transaction
     */
    String getMemo();
}
