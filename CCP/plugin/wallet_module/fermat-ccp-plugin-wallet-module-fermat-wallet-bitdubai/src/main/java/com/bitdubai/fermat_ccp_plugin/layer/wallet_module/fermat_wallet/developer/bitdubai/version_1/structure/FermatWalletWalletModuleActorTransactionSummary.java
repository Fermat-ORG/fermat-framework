package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.ActorTransactionSummary;

import java.io.Serializable;

/**
 * The class <code>FermatWalletWalletModuleActorTransactionSummary</code>
 * implements the interface ActorTransactionSummary and all its methods.
 *
 * Created by mati on 2015.09.17..
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 18/09/15.
 * @version 1.0
 */
public class FermatWalletWalletModuleActorTransactionSummary implements ActorTransactionSummary,Serializable {

    private final int   sentTransactionsNumber;
    private final int   receivedTransactionsNumber;
    private final long  sentAmount;
    private final long  receivedAmount;

    public FermatWalletWalletModuleActorTransactionSummary(final int sentTransactionsNumber,
                                                           final int receivedTransactionsNumber,
                                                           final long sentAmount,
                                                           final long receivedAmount) {

        this.sentTransactionsNumber     = sentTransactionsNumber;
        this.receivedTransactionsNumber = receivedTransactionsNumber;
        this.sentAmount                 = sentAmount;
        this.receivedAmount             = receivedAmount;
    }

    @Override
    public int getSentTransactionsNumber() {
        return sentTransactionsNumber;
    }

    @Override
    public int getReceivedTransactionsNumber() {
        return receivedTransactionsNumber;
    }

    @Override
    public int getTotalTransactionsNumber() {
        return sentTransactionsNumber+receivedTransactionsNumber;
    }

    @Override
    public long getSentAmount() {
        return sentAmount;
    }

    @Override
    public long getReceivedAmount() {
        return receivedAmount;
    }

    @Override
    public long getBalanceAmount() {
        return receivedAmount-sentAmount;
    }
}
