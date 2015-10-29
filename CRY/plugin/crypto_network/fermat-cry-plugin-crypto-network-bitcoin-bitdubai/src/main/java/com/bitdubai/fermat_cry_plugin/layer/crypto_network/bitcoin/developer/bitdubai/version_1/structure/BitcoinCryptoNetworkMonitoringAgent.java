package com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantCreateBlockStoreFileException;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBroadcast;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by rodrigo on 08/06/15.
 */
public class BitcoinCryptoNetworkMonitoringAgent implements Agent, BitcoinManager, DealsWithErrors, DealsWithLogger, DealsWithPluginFileSystem, DealsWithPluginIdentity {


    /**
     * BitcoinCryptoNetworkMonitoringAgent member variables
     */
    private BitcoinEventListeners myListeners;
    private NetworkParameters networkParameters;
    private StoredBlockChain storedBlockChain;
    private PeerGroup peers;
    private Wallet wallet;
    private String userPublicKey;


    /**
     * Agent interface member variables
     */
    private Thread agentThread;
    private MonitorAgent monitorAgent;

    /**
     * DealaWithError interface member variables
     */
     private ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    private LogManager logManager;

    /**
     * DealsWithPluginFileSystem interface member variable
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentify interface member variable
     */
    private UUID pluginId;

    /**
     * constructor
     * @param wallet the BitcoinJ wallet that will be used to store the transactions and specify which
     *               addresses to monitore
     * @param userPublicKey the user ID that we are calling the connection for.
     */
    public BitcoinCryptoNetworkMonitoringAgent(Wallet wallet, String userPublicKey){
        this.wallet = wallet;
        this.userPublicKey = userPublicKey;
        this.networkParameters = BitcoinNetworkConfiguration.getNetworkConfiguration();
        peers = null;
    }

    /**
     * DealsWithErrors interface impplementation
     *
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPlugInFileSystem interface implementation
     *
     * @param pluginFileSystem
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation
     *
     * @param pluginId
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * DealsWithLogger interface implementation
     *
     * @param logManager
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * Agent interface implementation
     *
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
        if (peers.isRunning())
            peers.stop();
    }

    /**
     * return the amount of connected peers. Warning, may change as soon as this is executed.
     *
     * @return
     */
    public int getConnectedPeers(){
        if (peers != null && peers.isRunning())
            return peers.numConnectedPeers();
        else
            return 0;
    }

    /**
     * Used by the Vault when we want to send bitcoins.
     *
     * @return
     */
    public PeerGroup getPeers() {
        return peers;
    }

    /**
     * return true if the service is running. It doest't mean we are connected. We might be without
     * internet access but the service still running.
     *
     * @return
     */
    public boolean isRunning() {
        if (peers == null)
            return false;
        else
            return peers.isRunning();
    }

    public void configureBlockChain() throws CantCreateBlockStoreFileException {
        /**
         * I prepare the block chain object
         */
        try {
            storedBlockChain = new StoredBlockChain(wallet, userPublicKey);
            storedBlockChain.setPluginId(pluginId);
            storedBlockChain.setPluginFileSystem(pluginFileSystem);
            storedBlockChain.setErrorManager(errorManager);
            storedBlockChain.createBlockChain();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantCreateBlockStoreFileException(CantCreateBlockStoreFileException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void configurePeers() {
        /**
         * I define the peers information that I will be connecting to.
         */
        myListeners = new BitcoinEventListeners();
        try {
            storedBlockChain.getBlockChain().addWallet(wallet);
            storedBlockChain.getBlockChain().addListener(myListeners);
            peers = new PeerGroup(this.networkParameters, storedBlockChain.getBlockChain());
            peers.addWallet(wallet);


            peers.setUserAgent(BitcoinManager.FERMAT_AGENT_NAME, BitcoinManager.FERMAT_AGENT_VERSION);
            peers.setUseLocalhostPeerWhenPossible(false);
            /**
             * If we are using RegTest network, we will connect to local server
             */
            if (networkParameters == RegTestParams.get()) {
                InetSocketAddress inetSocketAddress1 = new InetSocketAddress(REGTEST_SERVER_1_ADDRESS, REGTEST_SERVER_1_PORT);
                PeerAddress peerAddress1 = new PeerAddress(inetSocketAddress1);
                peers.addAddress(peerAddress1);

                InetSocketAddress inetSocketAddress2 = new InetSocketAddress(REGTEST_SERVER_2_ADDRESS, REGTEST_SERVER_2_PORT);
                PeerAddress peerAddress2 = new PeerAddress(inetSocketAddress2);
                peers.addAddress(peerAddress2);
            } else
            /**
             * If it is not RegTest, then I will get the Peers by DNSDiscovery
             */ {
                logManager.log(BitcoinCryptoNetworkPluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoNetwork information: Using " + networkParameters.toString() + " network.", null, null);
                peers.addPeerDiscovery(new DnsDiscovery(this.networkParameters));
            }

            myListeners.setLogManager(this.logManager);
            peers.addEventListener(myListeners);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }
    }


    /**
     * private class executed by the start of the Agent.
     */
    private class MonitorAgent implements Runnable {
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
            try {
                peers.start();
                peers.downloadBlockChain();
                //while (true){
                    //endless loop. Since bitcoinj upgrade, this is no longer running as a guava service.
                    // so we need to keep the thread active.
               // }
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new CantConnectToBitcoinNetwork("Couldn't connect to Bitcoin Network.", exception, "", "Error executing Agent.");
            }
        }
    }

    public void broadcastTransaction(Transaction transaction) throws ExecutionException, InterruptedException {
        /**
         * I make sure the service is running.
         */
        if (!peers.isRunning())
            peers.start();

        /**
         * I will make sure I have all blocks
         */
        peers.downloadBlockChain();

        /**
         * If I don't have any peers connected, I will continue trying to connect before broadcasting.
         */
        while (peers.numConnectedPeers() == 0){
            peers.stop();
            peers.addPeerDiscovery(new DnsDiscovery(networkParameters));
            peers.start();
            peers.downloadBlockChain();
        }

        TransactionBroadcast broadcast = peers.broadcastTransaction(transaction);
        broadcast.setProgressCallback(new TransactionBroadcast.ProgressCallback() {
            @Override
            public void onBroadcastProgress(double progress) {
                System.out.println("broadCast progress: " + progress);
            }
        });
        broadcast.future().get();
    }
}
