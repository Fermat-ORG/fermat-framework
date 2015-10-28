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
     * Gets the correct BlockchainNetworkType based in the passed NetworkParameters
     * @param networkParameters
     * @return
     */
    public static BlockchainNetworkType getBlockchainNetworkType(NetworkParameters networkParameters){
        /**
         * if this is the default network type, I will return it.
         */
        if (BitcoinNetworkConfiguration.DEFAULT_NETWORK_PARAMETERS == networkParameters){
            return BlockchainNetworkType.DEFAULT;
        }

        /**
         * I will return the correct network type.
         */
        BlockchainNetworkType blockchainNetworkType = null;
        if (networkParameters == RegTestParams.get()){
            blockchainNetworkType = BlockchainNetworkType.REG_TEST;
        } else if(networkParameters == MainNetParams.get()){
            blockchainNetworkType = BlockchainNetworkType.PRODUCTION;
        } else if(networkParameters == TestNet3Params.get()){
            blockchainNetworkType = BlockchainNetworkType.TEST;
        }

        return blockchainNetworkType;
    }


}
