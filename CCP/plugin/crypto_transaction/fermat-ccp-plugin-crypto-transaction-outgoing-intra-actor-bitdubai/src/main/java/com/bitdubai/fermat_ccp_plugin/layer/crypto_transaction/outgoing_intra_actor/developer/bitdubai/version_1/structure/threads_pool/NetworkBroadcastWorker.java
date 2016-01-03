package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.threads_pool;

import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.ErrorBroadcastingTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;

/**
 * Created by mati on 2016.01.02..
 */
public class NetworkBroadcastWorker implements Runnable {

    String transactionHash;
    BitcoinNetworkManager bitcoinNetworkManager;
    CryptoVaultManager cryptoVaultManager;

    public NetworkBroadcastWorker(String transactionHash, BitcoinNetworkManager bitcoinNetworkManager, CryptoVaultManager cryptoVaultManager) {
        this.transactionHash = transactionHash;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.cryptoVaultManager = cryptoVaultManager;
    }

    @Override
    public void run() {
        try {
            bitcoinNetworkManager.broadcastTransaction(transactionHash);
        } catch (CantBroadcastTransactionException e) {
            e.printStackTrace();
        }
    }
}
