package com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * Created by rodrigoa on 20/05/15.
 */
public interface BitcoinManager extends FermatManager {

    // changed from UUID to Strubg representing a public key
    // Ezequiel Postan August 15th 2015
    String walletID = null;

    /**
     *   Used to specify the network that will be used by the plug in.
     *   MAIN_NET is the production Bitcoin network: we are dealing with real money!
     *   TEST3_NET is the testing Bitcoin network. Preferable and will be used most of the time.
     *   REGTEST_NET or Regression Test Bitcoin network is used connecting to a local node and we define the difficulty!
     *   FERMAT_TEST and FERMAT_MAIN are not implemented. We might be using our own servers some day.
     */
    static enum BitcoinNetworkUsed {MAIN_NET, Test3_NET, REGTEST_NET, FERMAT_TEST, FERMAT_MAIN}


    /**
     * Agent name and version that Full node peers will see when we connect to them to retrieve blockchain info
     */
    public final String FERMAT_AGENT_NAME = "Fermat Agent";
    public final String FERMAT_AGENT_VERSION = "1.0";

    /**
     * Bitcoin network used to run plug in Bitcoin Crypto network.
     */
    public final BitcoinNetworkUsed NETWORK_CONNECTION = BitcoinNetworkUsed.REGTEST_NET;

    /**
     * Bitcoin server hosted in cloud
     */

    //public final String REGTEST_SERVER_1_ADDRESS = "52.27.201.67";
    //public final int REGTEST_SERVER_1_PORT = 18444;

    public final String REGTEST_SERVER_1_ADDRESS = "192.168.0.11";

    public final int REGTEST_SERVER_1_PORT = 19000;

    //public final String REGTEST_SERVER_2_ADDRESS = "52.88.160.234";
    //public final int REGTEST_SERVER_2_PORT = 18444;


    public final String REGTEST_SERVER_2_ADDRESS = "192.168.0.11";

    public final int REGTEST_SERVER_2_PORT = 19010;



}
