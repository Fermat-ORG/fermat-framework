package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 02.10.15.
 */
public interface CryptoBrokerBalanceRecord {

    UUID getTransactionId();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    CurrencyType getCurrencyType();

    String getPublicKeyActorFrom();

    String getPublicKeyActorTo();

    float getAmount();

    long getTimestamp();

    String getMemo();

}
