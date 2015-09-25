package com.bitdubai.fermat_api.layer.ccp_wallet_module.fiat_over_crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.ccp_request.money_request.interfaces.CryptoRequestInformation;

/**
 * The interface <code>CryptoRequestToFiatOverCryptoWallet</code>
 * provides the information about crypto requests received by the wallet.
 */
public interface CryptoRequestToFiatOverCryptoWallet {

    /**
     * The method <code><getCryptoRequest/code> gives us the information provided by the request module
     *
     * @return the information provided by the money request module
     */
    CryptoRequestInformation getCryptoRequest();

    /**
     * The method <code>getCurrentFiatValue</code> gives us the equivalent in fiat currency registered
     * in the request
     *
     * @return the fiat amount
     */
    long getCurrentFiatValue();

    /**
     *
     * @return the name of the sender
     */
    String requestSenderName();

    /**
     *
     * @return the name of the receiver
     */
    String requestReceiverName();
}
