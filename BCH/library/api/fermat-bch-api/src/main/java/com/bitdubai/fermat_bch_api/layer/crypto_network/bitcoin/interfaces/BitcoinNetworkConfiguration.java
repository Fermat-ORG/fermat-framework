package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;

/**
 * Created by rodrigo on 9/19/15.
 */
public interface BitcoinNetworkConfiguration {
    public static final NetworkParameters DEFAULT_NETWORK_PARAMETERS = RegTestParams.get();
    public static final String BITCOIN_FULL_NODE_1_IP = "192.168.0.18";
    public static final int  BITCOIN_FULL_NODE_1_PORT = 19020;
    public static final String BITCOIN_FULL_NODE_2_IP = "192.168.0.18";
    public static final int  BITCOIN_FULL_NODE_2_PORT = 19030;


    public static final String USER_AGENT_NAME = "Fermat Agent";
    public static final String USER_AGENT_VERSION ="2.1.0";
}
