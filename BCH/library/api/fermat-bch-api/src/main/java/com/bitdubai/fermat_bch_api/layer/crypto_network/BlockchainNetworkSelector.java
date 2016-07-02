package com.bitdubai.fermat_bch_api.layer.crypto_network;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;

/**
 * Created by rodrigo on 6/30/16.
 */
public class BlockchainNetworkSelector {




    public static NetworkParameters getNetworkParameter(BlockchainNetworkType blockchainNetworkType){
        switch (blockchainNetworkType){
            case PRODUCTION:
                return MainNetParams.get();
            case TEST_NET:
                return TestNet3Params.get();
            case REG_TEST:
                return RegTestParams.get();
            default:
                return  RegTestParams.get();
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
