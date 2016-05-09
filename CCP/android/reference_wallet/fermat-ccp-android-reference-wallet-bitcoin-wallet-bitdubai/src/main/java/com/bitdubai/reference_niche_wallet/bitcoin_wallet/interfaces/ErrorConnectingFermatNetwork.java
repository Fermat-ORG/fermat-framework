package com.bitdubai.reference_niche_wallet.bitcoin_wallet.interfaces;

/**
 * Created by natalia on 13/01/16.
 */
public interface ErrorConnectingFermatNetwork {

    /**
     * Is called when user is not connected to fermat network
     *
     * @param connected boolean true when user is connected to network
     */
    void errorConnectingFermatNetwork(boolean connected);

}
