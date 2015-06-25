package com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.CantSendFundsException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InconsistentFundsException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InsufficientFundsException;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface TransactionManager {
    public void send(UUID walletID, CryptoAddress destinationAddress, long cryptoAmount) throws InsufficientFundsException, CantSendFundsException, InconsistentFundsException;
}
