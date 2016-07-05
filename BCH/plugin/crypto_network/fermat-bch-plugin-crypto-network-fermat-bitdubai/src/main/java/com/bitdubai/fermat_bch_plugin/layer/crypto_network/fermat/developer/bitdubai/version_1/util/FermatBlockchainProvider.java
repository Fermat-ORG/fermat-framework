package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_bch_api.layer.crypto_network.blockchain_configuration.AbstractBlockChainConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_network.blockchain_configuration.BlockchainProvider;
import com.bitdubai.fermat_bch_api.layer.crypto_network.blockchain_configuration.BlockchainProviderName;
import com.bitdubai.fermat_bch_api.layer.crypto_network.fermat.FermatNetworkConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 6/29/16.
 */
public class FermatBlockchainProvider extends AbstractBlockChainConfiguration implements BlockchainProvider {
    private BlockchainNetworkType activeNetworkType;

    @Override
    public BlockchainProviderName getName() {
        return BlockchainProviderName.FERMAT;
    }

    @Override
    public  List<BlockchainNetworkType> getSupportedNetworkTypes() {
        List<BlockchainNetworkType> supportedNetworks = new ArrayList<>();
        supportedNetworks.add(BlockchainNetworkType.PRODUCTION);
        supportedNetworks.add(BlockchainNetworkType.TEST_NET);
        supportedNetworks.add(BlockchainNetworkType.REG_TEST);
        return supportedNetworks;
    }

    @Override
    public BlockchainNetworkType getDefaultBlockchainNetworkType() {
        return FermatNetworkConfiguration.DEFAULT_NETWORK_TYPE;
    }

    @Override
    public BlockchainNetworkType getActiveBlockchainNetworkType() {
        return activeNetworkType;
    }

    @Override
    public void setActiveBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType){
        this.activeNetworkType = blockchainNetworkType;
    }

    @Override
    public CryptoCurrency getBlockchainCryptoCurrency() {
        return CryptoCurrency.FERMAT;
    }


    @Override
    public BlockchainProvider getBlockchainProvider() {
        return this;
    }
}
