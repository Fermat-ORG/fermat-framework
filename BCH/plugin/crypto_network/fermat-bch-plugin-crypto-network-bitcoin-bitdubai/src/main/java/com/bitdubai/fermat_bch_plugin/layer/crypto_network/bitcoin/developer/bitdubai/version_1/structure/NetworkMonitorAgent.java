package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.SPVBlockStore;

import java.io.File;
import java.net.InetSocketAddress;

/**
 * Created by rodrigo on 9/30/15.
 */
class NetworkMonitorAgent implements Agent{
    Wallet wallet;
    NetworkParameters networkParameters;
    PeerGroup peerGroup;
    EventManager eventManager;

    /**
     * Constructor
     * @param wallet
     */
    public NetworkMonitorAgent(Wallet wallet, NetworkParameters networkParameters, EventManager eventManager) {
        this.wallet = wallet;
        this.networkParameters = networkParameters;
        this.eventManager = eventManager;
    }

    @Override
    public void start() throws CantStartAgentException {
           doTheThing();
    }

    //todo I will fix this, I properly need to define the Agent structure to run on a separate thread.
    private void doTheThing(){
        try {
            NetworkMonitoringAgentEvents networkMonitoringAgentEvents = new NetworkMonitoringAgentEvents(this.eventManager);


            //todo this needs to be fixed. I will in the future save the blocks in a database
            SPVBlockStore spvBlockStore = new SPVBlockStore(networkParameters, new File("/data/data/com.bitdubai.fermat/files/bitcoinnetwork.spv"));

            BlockChain blockChain = new BlockChain(networkParameters, wallet, spvBlockStore);
            peerGroup = new PeerGroup(networkParameters, blockChain);

            peerGroup.addWallet(wallet);
            peerGroup.setUseLocalhostPeerWhenPossible(true);


            // If I'm connecting to RegTest, I will get the server information from the platform
            if (networkParameters == RegTestParams.get()){
                InetSocketAddress inetSocketAddress = new InetSocketAddress(BitcoinNetworkConfiguration.BITCOIN_FULL__NODE_IP, BitcoinNetworkConfiguration.BITCOIN_FULL__NODE_PORT);
                PeerAddress peerAddress = new PeerAddress(inetSocketAddress);
                peerGroup.addAddress(peerAddress);
            } else {
                peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
            }
            peerGroup.setUserAgent(BitcoinNetworkConfiguration.USER_AGENT_NAME, BitcoinNetworkConfiguration.USER_AGENT_VERSION);

            wallet.addEventListener(networkMonitoringAgentEvents);
            peerGroup.addEventListener(networkMonitoringAgentEvents);

            peerGroup.startAsync();
            peerGroup.downloadBlockChain();
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        peerGroup.stop();
    }
}
