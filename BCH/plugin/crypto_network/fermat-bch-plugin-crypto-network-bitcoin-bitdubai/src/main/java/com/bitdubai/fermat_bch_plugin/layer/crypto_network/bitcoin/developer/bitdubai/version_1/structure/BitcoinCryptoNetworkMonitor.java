package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.BlockchainException;

import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBroadcast;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.WalletTransaction;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by rodrigo on 10/4/15.
 */
public class BitcoinCryptoNetworkMonitor implements Agent {
    /**
     * class variables
     */
    Wallet wallet;
    PeerGroup peerGroup;
    File walletFileName;
    BlockChain blockChain;
    final NetworkParameters NETWORK_PARAMETERS;



    /**
     * Platform variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID plugId;

    /**
     * Constructor
     * @param pluginDatabaseSystem
     */
    public BitcoinCryptoNetworkMonitor(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, Wallet wallet, File walletFilename) {
        /**
         * I initialize the local variables
         */
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.wallet = wallet;
        this.plugId = pluginId;
        this.walletFileName = walletFilename;

        /**
         * I get the network parameter from the passed wallet.
         */
        NETWORK_PARAMETERS = wallet.getNetworkParameters();
    }


    @Override
    public void start() throws CantStartAgentException {
        //todo move this to the correct new thread format.
        try {
            doTheMainTask();
        } catch (BlockchainException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        /**
         * will wait until the peer agent stops.
         */
        peerGroup.stop();
        while(getPeerGroup().isRunning()){

        }
    }

    /**
     * Wallet setter
     * @param wallet
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }


    /**
     * Agent main method
     */
    private void doTheMainTask() throws BlockchainException {
        System.out.println("Crypto Network starting and connecting...");

        try{
            /**
             * creates the blockchain object for the specified network.
             */
//        BitcoinCryptoNetworkBlockChain CryptoNetworkBlockChain = new BitcoinCryptoNetworkBlockChain(NETWORK_PARAMETERS, wallet);
//        BlockChain blockChain = CryptoNetworkBlockChain.getBlockChain();
            BlockStore blockStore = new MemoryBlockStore(NETWORK_PARAMETERS);
            try {
                blockChain = new BlockChain(NETWORK_PARAMETERS, wallet, blockStore);
            } catch (BlockStoreException e) {
                e.printStackTrace();
            }


            /**
             * creates the peerGroup object
             */
            peerGroup = new PeerGroup(NETWORK_PARAMETERS, blockChain);
            peerGroup.addWallet(this.wallet);

            /**
             * add the events
             */
            BitcoinNetworkEvents events = new BitcoinNetworkEvents(pluginDatabaseSystem, plugId, this.walletFileName);
            peerGroup.addEventListener(events);
            this.wallet.addEventListener(events);
            blockChain.addListener(events);


            /**
             * I will connect to the regTest server or search for peers if we are in a different network.
             */
            if (NETWORK_PARAMETERS == RegTestParams.get()){
                /**
                 * Peer 1
                 */
                InetSocketAddress inetSocketAddress1 = new InetSocketAddress(BitcoinNetworkConfiguration.BITCOIN_FULL_NODE_1_IP, BitcoinNetworkConfiguration.BITCOIN_FULL_NODE_1_PORT);
                PeerAddress peerAddress1 = new PeerAddress(inetSocketAddress1);
                peerGroup.addAddress(peerAddress1);

                /**
                 * Peer 2
                 */
                InetSocketAddress inetSocketAddress2 = new InetSocketAddress(BitcoinNetworkConfiguration.BITCOIN_FULL_NODE_2_IP, BitcoinNetworkConfiguration.BITCOIN_FULL_NODE_2_PORT);
                PeerAddress peerAddress2 = new PeerAddress(inetSocketAddress2);
                peerGroup.addAddress(peerAddress2);
            } else
                peerGroup.addPeerDiscovery(new DnsDiscovery(NETWORK_PARAMETERS));

            /**
             * Define internal agent information.
             */
            peerGroup.setUserAgent(BitcoinNetworkConfiguration.USER_AGENT_NAME, BitcoinNetworkConfiguration.USER_AGENT_VERSION);

            /**
             * starts the monitoring
             */
            peerGroup.setDownloadTxDependencies(true);
            peerGroup.start();
            peerGroup.startBlockChainDownload(null);
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * Broadcast a well formed, commited and signed transaction into the network
     * @param tx
     * @throws CantBroadcastTransactionException
     */
    public void broadcastTransaction(Transaction tx) throws CantBroadcastTransactionException {
        try{
            /**
             * I will add this transaction to the wallet.
             */
            WalletTransaction walletTransaction = new WalletTransaction(WalletTransaction.Pool.PENDING, tx);
            wallet.addWalletTransaction(walletTransaction);

            /**
             * save the added transaction in the wallet
             */
            wallet.saveToFile(walletFileName);

            /**
             * Broadcast it.
             */
            TransactionBroadcast broadcast = peerGroup.broadcastTransaction(tx);
            broadcast.setProgressCallback(new TransactionBroadcast.ProgressCallback() {
                @Override
                public void onBroadcastProgress(double progress) {
                    System.out.println("****CryptoNetwork: progress broadcast " + progress);
                }
            });

            broadcast.broadcast().get(2, TimeUnit.MINUTES);
            broadcast.future().get(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Exception exception){
            throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, exception, "There was an unexpected issue while broadcasting a transaction.", null);
        }

    }

    /**
     * gets the peer group
     * @return
     */
    public PeerGroup getPeerGroup() {
        return peerGroup;
    }

    /**
     * Will get the CryptoTransaction directly from the blockchain by requesting it to a peer.
     * If the transaction is not part of any of our vaults, we will ask it to a connected peer to retrieve it.
     * @param txHash the Hash of the transaction we are going to look for.
     * @param blockHash the Hash of block where this transaction was stored..
     * @return a CryptoTransaction with the information of the transaction.
     * @throws CantGetCryptoTransactionException
     */

    public CryptoTransaction getCryptoTransactionFromBlockChain(String txHash, String blockHash) throws CantGetCryptoTransactionException {
        /**
         * I will check I don't get nulls in the parameters
         */
        if (txHash == null || blockHash == null)
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null, "TxHash or blocHash parameters can't be null", null);

        try{
            /**
             * I get the hash of the block
             */
            Sha256Hash blockSha256Hash = Sha256Hash.wrap(blockHash);


            /**
             * If I don't have this block, then I will get the block from the peer
             */
            Block genesisBlock = getBlockFromPeer(blockSha256Hash);

            /**
             * Will search all transactions from the block until I find my own.
             */
            for (Transaction transaction : genesisBlock.getTransactions()){
                if (transaction.getHashAsString().contentEquals(txHash)){
                    /**
                     * I form the CryptoTransaction and return it.
                     */
                    return getCryptoTransactionFromBitcoinTransaction(transaction);
                }
            }

            /**
             * If I couldn't find it. then I will return null.
             * */
            return null;
        } catch (Exception e){
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * Transform a Bitcoin Transaction into a Crypto Transaction
     * @param transaction
     * @return
     */
    private CryptoTransaction getCryptoTransactionFromBitcoinTransaction(Transaction transaction) {
        return CryptoTransaction.getCryptoTransaction(transaction);
    }

    /**
     * Gets from the DownloadPeer the block that matches the passed hash
     * @param blockHash
     * @return
     */
    private Block getBlockFromPeer(Sha256Hash blockHash) throws CantGetCryptoTransactionException {
        try {
            return peerGroup.getDownloadPeer().getBlock(blockHash).get(1, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "There was a problem trying to get the block from the Peer.", null);
        }
    }
}
