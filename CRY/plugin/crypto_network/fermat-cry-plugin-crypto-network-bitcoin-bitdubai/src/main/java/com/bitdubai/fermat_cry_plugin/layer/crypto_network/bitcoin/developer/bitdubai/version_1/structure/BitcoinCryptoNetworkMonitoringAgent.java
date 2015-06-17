package com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantInitializeMonitorAgentException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinManager;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by rodrigo on 08/06/15.
 */
public class BitcoinCryptoNetworkMonitoringAgent implements Agent, BitcoinManager, DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginIdentity {


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
     * DealsWithPluginFileSystem interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentify interface member variable
     */
    UUID pluginId;

    /**
     * constructor
     * @param wallet the BitcoinJ wallet that will be used to store the transactions and specify which
     *               addresses to monitore
     * @param UserId the user ID that we are calling the connection for.
     */
    public BitcoinCryptoNetworkMonitoringAgent(Wallet wallet, UUID UserId){
        this.wallet = wallet;
        this.userId = UserId;
        this.networkParameters = BitcoinNetworkConfiguration.getNetworkConfiguration();
        peers = null;
    }


    /**
     * Agent interface implementation
     * @throws CantStartAgentException
     */
    @Override
    public void start() throws CantStartAgentException {
        /**
         * I prepare the block chain object
         */
        try {
            storedBlockChain = new StoredBlockChain(wallet, userId);
            storedBlockChain.setPluginId(pluginId);
            storedBlockChain.setPluginFileSystem(pluginFileSystem);
            storedBlockChain.setErrorManager(errorManager);
            storedBlockChain.createBlockChain();
        } catch (CantInitializeMonitorAgentException e) {
            /**
             * If I was not able to save the blockchain in a SPV file or memory I can't monitor the network
             */
            errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM_IDENTITY_MANAGER, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
            throw new CantStartAgentException();
        }

        /**
         * I define the peers information that I will be connecting to.
         */
        peers = new PeerGroup(this.networkParameters, storedBlockChain.getBlockChain());
        peers.addWallet(wallet);
        peers.setUserAgent(BitcoinManager.FERMAT_AGENT_NAME, BitcoinManager.FERMAT_AGENT_VERSION);
        peers.addPeerDiscovery(new DnsDiscovery(this.networkParameters));

        myListeners = new BitcoinEventListeners();
        peers.addEventListener(myListeners);

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
        if (peers.isRunning())
            try {
                peers.awaitTerminated(10, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                System.out.println("Could't stop the Bitcoin agent.");
                e.printStackTrace();
            }
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
     * private class executed by the start of the Agent.
     */
    private class MonitorAgent implements Runnable{
        @Override
        public void run() {
            doTheMainTask();
        }
    }

    /**
     * triggers the connection to peers, the download (or update) of the block chain
     * and the listening to incoming transactions.
     */
    private void doTheMainTask() {
        peers.startAsync();
        peers.awaitRunning();
        peers.downloadBlockChain();
    }
}
