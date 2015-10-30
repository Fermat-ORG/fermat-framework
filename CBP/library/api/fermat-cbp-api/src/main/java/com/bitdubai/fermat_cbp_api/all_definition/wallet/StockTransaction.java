package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;

import java.util.UUID;

/**
 * Created by jorge on 30-09-2015.
 */
public interface StockTransaction {

    UUID getTransactionId();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    String getWalletPublicKey();

    String getOwnerPublicKey();

    float getAmount();

    long getTimestamp();

    String getMemo();
}
