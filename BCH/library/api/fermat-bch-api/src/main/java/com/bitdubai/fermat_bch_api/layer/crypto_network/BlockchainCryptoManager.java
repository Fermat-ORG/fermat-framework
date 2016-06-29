package com.bitdubai.fermat_bch_api.layer.crypto_network;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import org.bitcoinj.core.Transaction;

/**
 * Created by rodrigo on 6/22/16.
 */
public interface BlockchainCryptoManager  {

    /**
     * Get the bitcoin transaction stored by the CryptoNetwork
     *
     * @param blockchainNetworkType the network type
     * @param transactionHash       the transsaction hash
     * @return the bitcoin transaction
     */
    Transaction getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, String transactionHash);

}
