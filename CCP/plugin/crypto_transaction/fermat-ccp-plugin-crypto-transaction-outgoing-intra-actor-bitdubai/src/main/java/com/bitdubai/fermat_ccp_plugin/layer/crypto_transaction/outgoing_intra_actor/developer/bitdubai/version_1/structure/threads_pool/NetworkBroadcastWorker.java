package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.threads_pool;

import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;

/**
 * Created by mati on 2016.01.02..
 */
public class NetworkBroadcastWorker implements Runnable {

    String transactionHash;
    BlockchainManager<ECKey, Transaction> bitcoinNetworkManager;
    CryptoVaultManager cryptoVaultManager;
    NetworkExecutorPool networkExecutorPool;

    public NetworkBroadcastWorker(String transactionHash, BlockchainManager<ECKey, Transaction> bitcoinNetworkManager, CryptoVaultManager cryptoVaultManager,NetworkExecutorPool networkExecutorPool) {
        this.transactionHash = transactionHash;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.cryptoVaultManager = cryptoVaultManager;
        this.networkExecutorPool = networkExecutorPool;
    }

    @Override
    public void run() {
        try {
            bitcoinNetworkManager.broadcastTransaction(transactionHash);
        } catch (CantBroadcastTransactionException e) {
            e.printStackTrace();
            networkExecutorPool.rejectedBroadcastExecution(transactionHash);
        }
    }

    public String getTransactionHash() {
        return transactionHash;
    }

//    @Override
//    public Object call() throws Exception {
//        bitcoinNetworkManager.broadcastTransaction(transactionHash);
//        return null;
//    }

}
