package com.bitdubai.fermat_bch_api.layer.crypto_network.blockchain_configuration;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

import java.util.List;

/**
 * Created by rodrigo on 6/22/16.
 * Methods a Blockchain Provider must include. In Fermat we identity a Blockchain Provider as the provider of a network we are
 * operating in. For example, Bitcoin.
 * The platform now supports bitcoins and Fermats, but will support more providers in the future, like Etherium, Rootstock, etc.
 */
public interface BlockchainProvider {

    /**
     * Gets the name of the provider
     * @return
     */
    BlockchainProviderName getName();

    /**
     * Gets the list of supported network types for this blockchain provider.
     * Its usully a production, testing and regression test networks.
     * @return
     */
    List<BlockchainNetworkType> getSupportedNetworkTypes();


    /**
     * The platform starts this provider network in the default network type. For example, Bitcoin using the MainNet.
     * Each Wallet is able to activate other BlockchainNetworkType that will be activated every time the platform starts.
     * But initially, only the default network type is started.
     * @return
     */
    BlockchainNetworkType getDefaultBlockchainNetworkType();

    /**
     * Gets the active network type that we are using.
     * @return
     */
    BlockchainNetworkType getActiveBlockchainNetworkType();

    /**
     * sets the default network type
     * @param blockchainNetworkType
     */
    void setActiveBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType);

    /**
     * The CryptoCurrency this provider supports.
     * @return
     */
    CryptoCurrency getBlockchainCryptoCurrency();

}
