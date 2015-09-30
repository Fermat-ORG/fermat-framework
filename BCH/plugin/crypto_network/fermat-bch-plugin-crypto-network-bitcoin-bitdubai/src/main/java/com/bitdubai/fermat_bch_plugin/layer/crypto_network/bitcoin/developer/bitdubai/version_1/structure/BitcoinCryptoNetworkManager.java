package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.Wallet;
import org.bitcoinj.wallet.DeterministicSeed;

/**
 * Created by rodrigo on 9/30/15.
 */
public class BitcoinCryptoNetworkManager implements BitcoinNetworkManager {

    EventManager eventManager;

    /**
     * Constructor
     * @param eventManager
     */
    public BitcoinCryptoNetworkManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void monitorNetworkFromSeed(BlockchainNetworkType blockchainNetworkType, DeterministicSeed deterministicSeed) throws CantMonitorBitcoinNetworkException {
        Wallet wallet = Wallet.fromSeed(BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType), deterministicSeed);
        NetworkMonitorAgent networkMonitorAgent = new NetworkMonitorAgent(wallet, wallet.getNetworkParameters(), this.eventManager);
        try {
            networkMonitorAgent.start();
        } catch (CantStartAgentException e) {
            e.printStackTrace();
        }
    }
}
