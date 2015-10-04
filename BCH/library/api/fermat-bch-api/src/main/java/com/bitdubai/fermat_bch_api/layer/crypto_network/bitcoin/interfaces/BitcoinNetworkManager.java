package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.List;

/**
 * Created by rodrigo on 9/30/15.
 */
public interface BitcoinNetworkManager {
    /**
     * Starts monitoring the specified bitcoin network using the provided seed.
     * @param blockchainNetworkType
     * @param deterministicSeed
     * @throws CantMonitorBitcoinNetworkException
     */
    void monitorNetworkFromSeed(BlockchainNetworkType blockchainNetworkType, DeterministicSeed deterministicSeed) throws CantMonitorBitcoinNetworkException;

    void monitorNetworkFromWatchingKey(BlockchainNetworkType blockchainNetworkType, DeterministicKey watchingKey) throws CantMonitorBitcoinNetworkException;

    void monitorNetworkFromKeyList(List<ECKey> keyList) throws CantMonitorBitcoinNetworkException;


}
