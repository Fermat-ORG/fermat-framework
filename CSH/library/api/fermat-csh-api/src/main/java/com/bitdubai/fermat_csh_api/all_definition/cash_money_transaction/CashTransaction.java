package com.bitdubai.fermat_csh_api.all_definition.cash_money_transaction;

import com.bitdubai.fermat_api.layer.world.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/17/2015.
 */
public interface CashTransaction {

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
