package com.bitdubai.fermat_ccp_plugin.layer.loss_protected_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;


/**
 * The class <code>com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBasicWalletTransactionSummary</code>
 * haves all methods for the transaction summary of a bitcoin wallet.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BitcoinWalletBasicWalletTransactionSummary implements BitcoinLossProtectedWalletTransactionSummary {

    private int sentTransactionsNumber;
    private int receivedTransactionsNumber;

    private long sentAmount;
    private long receivedAmount;

    public BitcoinWalletBasicWalletTransactionSummary(int sentTransactionsNumber, int receivedTransactionsNumber, long sentAmount, long receivedAmount) {
        this.sentTransactionsNumber = sentTransactionsNumber;
        this.receivedTransactionsNumber = receivedTransactionsNumber;
        this.sentAmount = sentAmount;
        this.receivedAmount = receivedAmount;
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
    public long getSentAmount() {
        return sentAmount;
    }

    @Override
    public long getReceivedAmount() {
        return receivedAmount;
    }
}
