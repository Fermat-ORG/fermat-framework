package com.bitdubai.fermat_bch_api.layer.crypto_network;

import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.BlockchainNetworkType;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;

/**
 * Created by rodrigo on 9/21/15.
 * Calculates the correct network Parameters based on the requested network configuration
 */
public class BitcoinNetworkSelector {
    public static NetworkParameters getNetworkParameter(BlockchainNetworkType blockchainNetworkType){
        switch (blockchainNetworkType){
            case PRODUCTION:
                return MainNetParams.get();
            case TEST:
                return TestNet3Params.get();
            case REG_TEST:
                return RegTestParams.get();
            default:
                return  com.bitdubai.fermat_bch_api.layer.crypto_network.interfaces.BitcoinNetworkConfiguration.DEFAULT_NETWORK_PARAMETERS;
        }
    }

    public static int getNetworkAccountNumber(BlockchainNetworkType blockchainNetworkType){
        switch (blockchainNetworkType){
            case PRODUCTION:
                return 0;
            case TEST:
                return 1;
            case REG_TEST:
                return 2;
            default:
                return 2;
        }
    }
}
