package com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface TransactionManager {
    public void send(UUID walletID, CryptoAddress destinationAddress, long cryptoAmount);
}
