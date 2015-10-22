package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantTransactionCryptoBrokerException;

import java.util.List;

/**
 * Created by Yordin Alayn on 30.09.15.
 */
public interface CryptoBroker {

    CryptoBrokerBalance getBookBalance(BalanceType balanceType) throws CantTransactionCryptoBrokerException;

    CryptoBrokerBalance getAvailableBalance(BalanceType balanceType) throws CantTransactionCryptoBrokerException;

    List<CryptoBrokerTransactionRecord> getTransactions(BalanceType balanceType, int max, int offset)throws CantTransactionCryptoBrokerException;

    CryptoBrokerTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionCryptoBrokerException;
}
