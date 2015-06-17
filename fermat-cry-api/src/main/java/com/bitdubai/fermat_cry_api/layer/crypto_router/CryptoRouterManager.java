package com.bitdubai.fermat_cry_api.layer.crypto_router;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface CryptoRouterManager {
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager();
}
