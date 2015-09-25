package com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.ccp_basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.ccp_basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.UnexpectedTransactionException;

/**
 * Created by jorgegonzalez on 2015.07.08..
 */
public interface TransactionExecutor {

    public void executeTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterCreditException, CantRegisterDebitException, UnexpectedTransactionException;
}
