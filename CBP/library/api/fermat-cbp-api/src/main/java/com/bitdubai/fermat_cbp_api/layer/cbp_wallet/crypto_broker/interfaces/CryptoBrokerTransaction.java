package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;


import java.util.UUID;

/**
 * Created by yordin on 02/10/15.
 */
public interface CryptoBrokerTransaction {

    UUID getTransactionId();

    UUID getMoneyTransactionId();

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    //BankTransactionStatus getStatus();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    float getAmount();

    CurrencyType getCurrencyType();

    long getTimestamp();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();
}
