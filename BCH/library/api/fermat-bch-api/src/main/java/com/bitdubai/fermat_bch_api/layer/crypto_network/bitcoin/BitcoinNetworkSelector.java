package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;

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
                return  BitcoinNetworkConfiguration.DEFAULT_NETWORK_PARAMETERS;
        }
    }

    /**
     * For Asset Vault key hierarchy configuration, we need to provide the correct Account Number
     * @param blockchainNetworkType
     * @return
     */
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
