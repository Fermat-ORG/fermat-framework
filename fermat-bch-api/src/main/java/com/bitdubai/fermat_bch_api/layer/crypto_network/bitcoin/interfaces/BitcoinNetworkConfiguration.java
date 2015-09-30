package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.RegTestParams;

/**
 * Created by rodrigo on 9/19/15.
 */
public interface BitcoinNetworkConfiguration {
    public static final NetworkParameters DEFAULT_NETWORK_PARAMETERS = RegTestParams.get();
    public static final String BITCOIN_FULL__NODE_IP = "52.11.156.16";
    public static final int  BITCOIN_FULL__NODE_PORT = 18444;
    public static final String USER_AGENT_NAME = "Fermat Agent";
    public static final String USER_AGENT_VERSION ="1.0.0";
}
