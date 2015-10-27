package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;

import java.util.UUID;

/**
 * Created by jorge on 26-10-2015.
 */
public interface WalletTransaction {
    UUID getTransactionId();

    FermatEnum getStockType();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    CurrencyType getCurrencyType();

    String getWalletPublicKey();

    String getOwnerPublicKey();

    float getAmount();

    long getTimestamp();

    String getMemo();
}
