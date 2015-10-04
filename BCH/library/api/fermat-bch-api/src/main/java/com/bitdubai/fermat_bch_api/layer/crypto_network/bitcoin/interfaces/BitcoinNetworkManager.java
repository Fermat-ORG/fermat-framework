package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;

import org.bitcoinj.core.ECKey;

import java.util.List;

/**
 * Created by rodrigo on 9/30/15.
 */
public interface BitcoinNetworkManager {

    /**
     * Starts monitoring the network active networks with the list of keys passed.
     * @param keyList
     * @throws CantMonitorBitcoinNetworkException
     */
    void monitorNetworkFromKeyList(List<BlockchainNetworkType> blockchainNetworkTypes,List<ECKey> keyList) throws CantMonitorBitcoinNetworkException;
}
