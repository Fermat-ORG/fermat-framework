package com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantInitializeMonitorAgentException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantCreateBlockStoreFileException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantDisconnectFromNetworkException;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.BlockStoreException;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by rodrigo on 08/06/15.
 */
public class BitcoinCryptoNetworkMonitoringAgent implements Agent, BitcoinManager, DealsWithErrors, DealsWithLogger, DealsWithPluginFileSystem, DealsWithPluginIdentity {


    /**
     * BitcoinCryptoNetworkMonitoringAgent member variables
     */
    BitcoinEventListeners myListeners;
    NetworkParameters networkParameters;
    StoredBlockChain storedBlockChain;
    PeerGroup peers;
    Wallet wallet;
    UUID userId;


    /**
     * Agent interface member variables
     */
    Thread agentThread;
    MonitorAgent monitorAgent;

     /**
     * DealaWithError interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    /**
     * DealsWithPluginFileSystem interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentify interface member variable
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface impplementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPlugInFileSystem interface implementation
     * @param pluginFileSystem
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation
     * @param pluginId
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * DealsWithLogger interface implementation
     * @param logManager
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * constructor
     * @param wallet the BitcoinJ wallet that will be used to store the transactions and specify which
     *               addresses to monitore
     * @param UserId the user ID that we are calling the connection for.
     */
    public BitcoinCryptoNetworkMonitoringAgent(Wallet wallet, UUID UserId){
        this.wallet = wallet;
        this.userId = UserId;
        this.networkParameters = BitcoinNetworkConfiguration.getNetworkConfiguration(null);
        peers = null;
    }

    /**
     * Agent interface implementation
     * @throws CantStartAgentException
     */
    @Override
    public void start() throws CantStartAgentException {
        /**
         * I start the thread that will launch the class that connects to bitcoin.
         */
        monitorAgent = new MonitorAgent();
        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }

    /**
     * Agent interface implementation
     */
    @Override
    public void stop() {
        /**
         * will stop the bitcoin monitoring agent.
         */
        peers.stopAsync();
        peers.awaitTerminated();
    }

    /**
     * return the amount of connected peers. Warning, may change as soon as this is executed.
     * @return
     */
    public int getConnectedPeers(){
        if (peers.isRunning())
            return peers.numConnectedPeers();
        else
            return 0;
    }

    /**
     * Used by the Vault when we want to send bitcoins.
     * @return
     */
    public PeerGroup getPeers(){
        return peers;
    }

    /**
     * return true if the service is running. It doest't mean we are connected. We might be without
     * internet access but the service still running.
     * @return
     */
    public boolean isRunning(){
        if (peers == null)
            return false;
        else
         return peers.isRunning();
    }

    public void configureBlockChain() throws CantCreateBlockStoreFileException {
        /**
         * I prepare the block chain object
         */
        storedBlockChain = new StoredBlockChain(wallet, userId);
        storedBlockChain.setPluginId(pluginId);
        storedBlockChain.setPluginFileSystem(pluginFileSystem);
        storedBlockChain.setErrorManager(errorManager);
        storedBlockChain.createBlockChain();
    }

    public void configurePeers() {
        /**
         * I define the peers information that I will be connecting to.
         */
        storedBlockChain.getBlockChain().addWallet(wallet);
        peers = new PeerGroup(this.networkParameters, storedBlockChain.getBlockChain());
        peers.addWallet(wallet);

        peers.setUserAgent(BitcoinManager.FERMAT_AGENT_NAME, BitcoinManager.FERMAT_AGENT_VERSION);
        peers.setUseLocalhostPeerWhenPossible(true);
        /**
         * If we are using RegTest network, we will connect to local server
         */
        if (networkParameters == RegTestParams.get())
        {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(REGTEST_SERVER_ADDRESS, REGTEST_SERVER_PORT);
            PeerAddress peerAddress = new PeerAddress(inetSocketAddress);
            peers.addAddress(peerAddress);
            logManager.log(BitcoinCryptoNetworkPluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoNetwork information: Using RegTest. Connecting to " + inetSocketAddress.toString(), null, null);
        }
        else
        /**
         * If it is not RegTest, then I will get the Peers by DNSDiscovery
         */
        {
            logManager.log(BitcoinCryptoNetworkPluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoNetwork information: Using " + networkParameters.toString() + " network.", null, null);
            peers.addPeerDiscovery(new DnsDiscovery(this.networkParameters));
        }


        myListeners = new BitcoinEventListeners();
        myListeners.setLogManager(this.logManager);
        peers.addEventListener(myListeners);

    }


    /**
     * private class executed by the start of the Agent.
     */
    private class MonitorAgent implements Runnable{
        @Override
        public void run() {
            try {
                doTheMainTask();
            } catch (CantConnectToBitcoinNetwork cantConnectToBitcoinNetwork) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantConnectToBitcoinNetwork);
            }
        }


        /**
         * triggers the connection to peers, the download (or update) of the block chain
         * and the listening to incoming transactions.
         */
        private void doTheMainTask() throws CantConnectToBitcoinNetwork {
            try{
                peers.startAsync();
                peers.awaitRunning();
                peers.downloadBlockChain();
            } catch (Exception exception){
                throw new CantConnectToBitcoinNetwork("Couldn't connect to Bitcoin Network.", exception,"", "Error executing Agent.");
            }
        }
    }
}
