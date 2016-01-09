package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;

/**
 * Created by rodrigo on 9/19/15.
 */
public interface BitcoinNetworkConfiguration {
    /**
     * Network that we are using as Default in the platform
     */
    public static final NetworkParameters DEFAULT_NETWORK_PARAMETERS = RegTestParams.get();

    /**
     * RegTest client configuration
     */
    public static final String BITCOIN_FULL_NODE_1_IP = "52.32.106.35";
    public static final int  BITCOIN_FULL_NODE_1_PORT = 19020;
    public static final String BITCOIN_FULL_NODE_2_IP = "52.34.184.168";
    public static final int  BITCOIN_FULL_NODE_2_PORT = 19030;

    /**
     * Agent name and version
     */
    public static final String USER_AGENT_NAME = "Fermat Agent";
    public static final String USER_AGENT_VERSION ="2.1.0";

    /**
     * amount of blocks depth to consider transaction IRReversible
     */
    public static final int IRREVERSIBLE_BLOCK_DEPTH = 3;

    /**
     * Amount of Timeout minutes for broadcasting transactions
     */
    public static final int TRANSACTION_BROADCAST_TIMEOUT = 5;

    /**
     * Max amount of iterations to get a child transaction. We will iterate x times a transaction
     * going deeper each time, until we found what we are looking for.
     */
    public static final int MAX_DEPTH_SARCHING_CHILD_TRANSACTIONS = 10;
}
