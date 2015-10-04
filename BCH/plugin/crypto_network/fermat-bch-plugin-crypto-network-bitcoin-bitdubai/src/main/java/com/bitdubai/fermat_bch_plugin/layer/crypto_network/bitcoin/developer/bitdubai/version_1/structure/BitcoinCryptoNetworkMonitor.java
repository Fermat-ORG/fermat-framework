package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;

import java.net.InetSocketAddress;

/**
 * Created by rodrigo on 10/4/15.
 */
class BitcoinCryptoNetworkMonitor implements Agent {
    /**
     * agent execution flag
     */
    private boolean isSupposedToBeRunning = false;
    Wallet wallet;

    /**
     * Platform variables
     */
    EventManager eventManager;
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor
     * @param eventManager
     * @param pluginDatabaseSystem
     */
    public BitcoinCryptoNetworkMonitor(EventManager eventManager, PluginDatabaseSystem pluginDatabaseSystem, Wallet wallet) {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.wallet = wallet;
    }

    @Override
    public void start() throws CantStartAgentException {
        isSupposedToBeRunning = true;
        Thread agentThread = new Thread(new BitcoinCryptoNetworkMonitorAgent());
        agentThread.start();
    }

    @Override
    public void stop() {
        isSupposedToBeRunning = false;

    }

    /**
     * Class that generates all the objects needed to connect to the network with the passed wallet.
     */
    private class BitcoinCryptoNetworkMonitorAgent implements Runnable{
        final NetworkParameters NETWORK_PARAMETERS = wallet.getNetworkParameters();

        @Override
        public void run() {
            while (isSupposedToBeRunning){
                doTheMainTask();
            }
        }

        /**
         * Agent main method
         */
        private void doTheMainTask(){
            //todo this won't be the final implementation of the network monitor. I will be using for now a simplified version.
            BlockStore memoryBlockStore = new MemoryBlockStore(NETWORK_PARAMETERS);
            try {
                BlockChain blockChain = new BlockChain(NETWORK_PARAMETERS, wallet, memoryBlockStore);
                PeerGroup peerGroup = new PeerGroup(NETWORK_PARAMETERS, blockChain);
                peerGroup.addWallet(wallet);

                BitcoinNetworkEvents events = new BitcoinNetworkEvents(eventManager, pluginDatabaseSystem);
                peerGroup.addEventListener(events);
                wallet.addEventListener(events);

                if (NETWORK_PARAMETERS == RegTestParams.get()){
                    InetSocketAddress inetSocketAddress = new InetSocketAddress(BitcoinNetworkConfiguration.BITCOIN_FULL_NODE_IP, BitcoinNetworkConfiguration.BITCOIN_FULL_NODE_PORT);
                   PeerAddress peerAddress = new PeerAddress(inetSocketAddress);
                    peerGroup.addAddress(peerAddress);
                } else
                    peerGroup.addPeerDiscovery(new DnsDiscovery(NETWORK_PARAMETERS));

                peerGroup.setUserAgent(BitcoinNetworkConfiguration.USER_AGENT_NAME, BitcoinNetworkConfiguration.USER_AGENT_VERSION);

                peerGroup.start();
                peerGroup.downloadBlockChain();
                while (true){

                }
            } catch (BlockStoreException e) {
                //todo handle
            }



        }
    }
}
