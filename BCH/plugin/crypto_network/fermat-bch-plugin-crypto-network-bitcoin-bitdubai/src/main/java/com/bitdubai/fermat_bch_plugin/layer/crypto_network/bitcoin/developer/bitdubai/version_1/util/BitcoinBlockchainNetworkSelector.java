package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;

/**
 * Created by rodrigo on 6/29/16.
 */
public class BitcoinBlockchainNetworkSelector {


    public static NetworkParameters getNetworkParameter(BlockchainNetworkType blockchainNetworkType){
        switch (blockchainNetworkType){
            case PRODUCTION:
                return MainNetParams.get();
            case TEST_NET:
                return TestNet3Params.get();
            case REG_TEST:
                return RegTestParams.get();
            default:
                return  getNetworkParameter(BitcoinNetworkConfiguration.DEFAULT_NETWORK_TYPE);
        }
    }

    /**
     * Gets the correct BlockchainNetworkType based in the passed NetworkParameters
     * @param networkParameters
     * @return
     */
    public static BlockchainNetworkType getBlockchainNetworkType(NetworkParameters networkParameters){
        /**
         * I will return the correct network type.
         */
        BlockchainNetworkType blockchainNetworkType = null;
        if (networkParameters == RegTestParams.get()){
            blockchainNetworkType = BlockchainNetworkType.REG_TEST;
        } else if(networkParameters == MainNetParams.get()){
            blockchainNetworkType = BlockchainNetworkType.PRODUCTION;
        } else if(networkParameters == TestNet3Params.get()){
            blockchainNetworkType = BlockchainNetworkType.TEST_NET;
        }

        return blockchainNetworkType;
    }
}
