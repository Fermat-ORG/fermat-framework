package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.BlockchainException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * Created by rodrigo on 10/4/15.
 */
class BitcoinCryptoNetworkMonitor implements Agent {
    /**
     * agent execution flag
     */
    private boolean isSupposedToBeRunning = false;

    /**
     * class variables
     */
    Wallet wallet;
    BitcoinCryptoNetworkMonitorAgent bitcoinCryptoNetworkMonitorAgent;


    /**
     * Platform variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID plugId;

    /**
     * Constructor
     * @param pluginDatabaseSystem
     */
    public BitcoinCryptoNetworkMonitor(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, Wallet wallet) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.wallet = wallet;
        this.plugId = pluginId;
    }

    /**
     * Broadcast a well formed, commited and signed transaction into the network
     * @param tx
     * @throws CantBroadcastTransactionException
     */
    public void broadcastTransaction(Transaction tx) throws CantBroadcastTransactionException {
        bitcoinCryptoNetworkMonitorAgent.broadcastTransaction(tx);
    }

    @Override
    public void start() throws CantStartAgentException {
        isSupposedToBeRunning = true;

        /**
         * I will also start another agent that will double check that we don't miss any transactions from the events
         * raised by bitcoinj
         */
        BitcoinCryptoNetworkMissingTransactionsWatcherAgent transactionsWatcherAgent = new BitcoinCryptoNetworkMissingTransactionsWatcherAgent(this.wallet, this.pluginDatabaseSystem, this.plugId);
        try {
            transactionsWatcherAgent.start();
        } catch (com.bitdubai.fermat_api.CantStartAgentException e) {
            e.printStackTrace();
        }

        /**
         * Then I will start the agent that connects to the bitcoin network to get new transactions
         */
        bitcoinCryptoNetworkMonitorAgent = new BitcoinCryptoNetworkMonitorAgent();
        Thread agentThread = new Thread(bitcoinCryptoNetworkMonitorAgent);
        agentThread.start();
    }

    @Override
    public void stop() {
        isSupposedToBeRunning = false;

    }

    /**
     * Wallet setter
     * @param wallet
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    /**
     * Class that generates all the objects needed to connect to the network with the passed wallet.
     */
    private class BitcoinCryptoNetworkMonitorAgent implements Runnable{
        /**
         * sets this agent network type
         */
        final NetworkParameters NETWORK_PARAMETERS = wallet.getNetworkParameters();

        /**
         * class variables.
         */
        PeerGroup peerGroup;

        @Override
        public void run(){
            while (isSupposedToBeRunning){
                try {
                    doTheMainTask();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Agent main method
         */
        private void doTheMainTask() throws BlockchainException {
            System.out.println("Crypto Network starting and connecting...");

            /**
             * creates the blockchain object for the specified network.
             */
            BitcoinCryptoNetworkBlockChain CryptoNetworkBlockChain = new BitcoinCryptoNetworkBlockChain(NETWORK_PARAMETERS);
            BlockChain blockChain = CryptoNetworkBlockChain.getBlockChain();
            blockChain.addWallet(wallet);

            /**
             * creates the peerGroup object
             */
            peerGroup = new PeerGroup(NETWORK_PARAMETERS, blockChain);
            peerGroup.addWallet(wallet);

            /**
             * add the events
             */
            BitcoinNetworkEvents events = new BitcoinNetworkEvents(pluginDatabaseSystem, plugId);
            peerGroup.addEventListener(events);
            wallet.addEventListener(events);

            /**
             * I will connect to the regTest server of search for peers if we are in a different network.
             */
            if (NETWORK_PARAMETERS == RegTestParams.get()){
                InetSocketAddress inetSocketAddress = new InetSocketAddress(BitcoinNetworkConfiguration.BITCOIN_FULL_NODE_IP, BitcoinNetworkConfiguration.BITCOIN_FULL_NODE_PORT);
               PeerAddress peerAddress = new PeerAddress(inetSocketAddress);
                peerGroup.addAddress(peerAddress);
            } else
                peerGroup.addPeerDiscovery(new DnsDiscovery(NETWORK_PARAMETERS));

            /**
             * Define internal agent information.
             */
            peerGroup.setUserAgent(BitcoinNetworkConfiguration.USER_AGENT_NAME, BitcoinNetworkConfiguration.USER_AGENT_VERSION);

            /**
             * starts the monitoring
             */
            peerGroup.start();
            peerGroup.downloadBlockChain();

            /**
             * will start a loop to let the network sync and download until is requested to stop.
             */
            while (isSupposedToBeRunning){

            }
            peerGroup.stop();
        }

        /**
         * Broadcast a well formed, commited and signed transaction into the network
         * @param tx
         * @throws CantBroadcastTransactionException
         */
        public void broadcastTransaction(Transaction tx) throws CantBroadcastTransactionException {
            try{
                peerGroup.broadcastTransaction(tx);
            } catch (Exception e){
                throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, e, null, null);
            }

        }
    }
}
