package com.bitdubai.fermat_bch_api.layer.crypto_network;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.RegTestParams;

/**
 * Created by rodrigo on 9/19/15.
 */
public interface BitcoinNetworkConfiguration {
    public static final NetworkParameters NETWORK_PARAMETERS = RegTestParams.get();
}
