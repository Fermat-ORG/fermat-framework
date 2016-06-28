package com.bitdubai.fermat_bch_api.layer.crypto_network;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.BlockchainProviderName;

import java.util.List;

/**
 * Created by rodrigo on 6/22/16.
 */
public interface BlockchainProvider {

    /**
     * Gets the name of the provider
     * @return
     */
    BlockchainProviderName getName();

    /**
     * Gets the list of supported network types for this blockchain provider.
     * @return
     */
    List<BlockchainNetworkType> getSupportedNetworkTypes();
}
