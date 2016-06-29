package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.interfaces.ActorTransactionSummary</code>
 * contains all methods needed to consume the Transaction Summary of an Actor.
 *
 * Created by mati on 2015.09.17.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 18/09/15.
 * @version 1.0
 */
public interface ActorTransactionSummary {

    int getSentTransactionsNumber();

    int getReceivedTransactionsNumber();

    int getTotalTransactionsNumber();

    long getSentAmount();

    long getReceivedAmount();

    long getBalanceAmount();

}
