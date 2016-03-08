package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedActorTransactionSummary;

/**
 * The class <code>CryptoWalletWalletModuleActorTransactionSummary</code>
 * implements the interface ActorTransactionSummary and all its methods.
 *
 * Created Natalia Cortez on 07/03/2016.
 * @version 1.0
 */
public class LossProtectedWalletModuleActorTransactionSummary implements LossProtectedActorTransactionSummary {

    private final int   sentTransactionsNumber;
    private final int   receivedTransactionsNumber;
    private final long  sentAmount;
    private final long  receivedAmount;

    public LossProtectedWalletModuleActorTransactionSummary(final int   sentTransactionsNumber,
                                                           final int   receivedTransactionsNumber,
                                                           final long  sentAmount,
                                                           final long  receivedAmount) {

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
