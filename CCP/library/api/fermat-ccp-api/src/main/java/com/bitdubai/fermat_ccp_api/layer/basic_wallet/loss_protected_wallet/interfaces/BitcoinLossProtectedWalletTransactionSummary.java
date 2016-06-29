package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces;

/**
 * The interface <code>CryptoWalletTransactionSummary</code>
 * haves all methods for the contacts activity of a bitcoin wallet
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface BitcoinLossProtectedWalletTransactionSummary {

    int getSentTransactionsNumber();

    int getReceivedTransactionsNumber();

    long getSentAmount();

    long getReceivedAmount();

}
