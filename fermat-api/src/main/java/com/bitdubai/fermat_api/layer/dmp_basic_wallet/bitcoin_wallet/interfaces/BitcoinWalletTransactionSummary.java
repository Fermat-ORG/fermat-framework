package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionSummary</code>
 * haves all methods for the contacts activity of a bitcoin wallet
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface BitcoinWalletTransactionSummary {

    int getSentTransactionsNumber();

    int getReceivedTransactionsNumber();

    long getSentAmount();

    long getReceivedAmount();

}
