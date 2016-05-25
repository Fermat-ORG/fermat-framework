package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionSummary;

/**
 * The class <code>com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.CryptoWalletBasicWalletTransactionSummary</code>
 * haves all methods for the transaction summary of a bitcoin wallet.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoWalletBasicWalletTransactionSummary implements CryptoWalletTransactionSummary {

    private int sentTransactionsNumber;
    private int receivedTransactionsNumber;

    private long sentAmount;
    private long receivedAmount;

    public CryptoWalletBasicWalletTransactionSummary(int sentTransactionsNumber, int receivedTransactionsNumber, long sentAmount, long receivedAmount) {
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
