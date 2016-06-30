package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;


/**
 * Created by rodrigo on 9/19/15.
 */
public interface BitcoinNetworkConfiguration {
    /**
     * The crypto currency of this network
     */
    CryptoCurrency CRYPTO_CURRENCY = CryptoCurrency.BITCOIN;


    /**
     * amount of blocks depth to consider transaction IRReversible
     */
    int IRREVERSIBLE_BLOCK_DEPTH = 3;
    int MIN_BROADCAST_CONNECTIONS = 2;

    /**
     * Amount of Timeout minutes for broadcasting transactions
     */
    int TRANSACTION_BROADCAST_TIMEOUT = 15;

    /**
     * The minimun of Satoshis that we can send, to avoid dusty sends.
     * Dusty sends are considered invalid in the network
     */
    long MIN_ALLOWED_SATOSHIS_ON_SEND = 35430;

    /**
     * Fixed fee value for outgoing transactions
     */
    long FIXED_FEE_VALUE = 30000;
}
