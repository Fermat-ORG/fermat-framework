package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1;



import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BlockchainConnectionStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantCancellBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetActiveBlockchainNetworkTypeException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.fermat.interfaces.FermatNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.database.FermatCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.database.FermatCryptoNetworkDeveloperDatabaseFactory;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions.CantInitializeFermatCryptoNetworkDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.structure.FermatCryptoNetworkEventsAgent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.structure.FermatCryptoNetworkManager;

import org.bitcoinj.core.ECKey;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;


/**
 * Created by rodrigo on 5/20/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH, maintainerMail = "acosta_rodrigo@hotmail.com", createdBy = "acostarodrigo", layer = Layers.CRYPTO_NETWORK, platform = Platforms.BLOCKCHAINS, plugin = Plugins.FERMAT_NETWORK)
public class FermatCryptoNetworkPluginRoot extends AbstractPlugin implements
        FermatNetworkManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;



    /**
     * Class variables
     */
    private FermatCryptoNetworkDatabaseDao dao;
    private FermatCryptoNetworkManager fermatCryptoNetworkManager;

    /**
     * Default Constructor
     */
    public FermatCryptoNetworkPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * instantiates the dao object to access the database
     * @return
     */
    private FermatCryptoNetworkDatabaseDao getDao(){
        if (dao == null)
            dao = new FermatCryptoNetworkDatabaseDao(this.pluginId, this.pluginDatabaseSystem);

        return dao;
    }

    /**
     * DatabaseManagerForDevelopers interface implementations
     */
    private FermatCryptoNetworkDeveloperDatabaseFactory fermatCryptoNetworkDeveloperDatabaseFactory;
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        if (fermatCryptoNetworkDeveloperDatabaseFactory == null){
            fermatCryptoNetworkDeveloperDatabaseFactory = new FermatCryptoNetworkDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            try {
                fermatCryptoNetworkDeveloperDatabaseFactory.initializeDatabase();
            } catch (CantInitializeFermatCryptoNetworkDatabaseException e) {
                e.printStackTrace();
            }
        }
        return fermatCryptoNetworkDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return fermatCryptoNetworkDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return fermatCryptoNetworkDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    @Override
    public void start() throws CantStartPluginException {
        /**
         * instantiate the network Manager
         */
        fermatCryptoNetworkManager = new FermatCryptoNetworkManager(this.eventManager, this.pluginFileSystem, this.pluginId, this.errorManager, getDao());

        /**
         * Start the agent that will search for pending transactions to be notified.
         */
        FermatCryptoNetworkEventsAgent fermatCryptoNetworkEventsAgent = new FermatCryptoNetworkEventsAgent(this.eventManager, getDao());
        try {
            fermatCryptoNetworkEventsAgent.start();
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "Cant start FermatCryptoNetworkEventsAgent agent.", null);
        }

        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * test method
     */
    private void testBlockChainConnectionStatus() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    for (BlockchainNetworkType networkType : getActivesBlockchainNetworkTypes()){
                        System.out.println("***CryptoNetwork***Test " + getBlockchainConnectionStatus(networkType).toString());
                    }
                } catch (CantGetActiveBlockchainNetworkTypeException e) {
                    e.printStackTrace();
                } catch (CantGetBlockchainConnectionStatusException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Pass the Keys to the bitcoin network to monitor the network
     * @param cryptoVault
     * @param blockchainNetworkTypes
     * @param keyList
     * @throws CantMonitorBitcoinNetworkException
     */
    @Override
    public void monitorNetworkFromKeyList(CryptoVaults cryptoVault, List<BlockchainNetworkType> blockchainNetworkTypes, List<ECKey> keyList) throws CantMonitorBitcoinNetworkException {
        try {
            fermatCryptoNetworkManager.monitorNetworkFromKeyList(cryptoVault, blockchainNetworkTypes, keyList);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantMonitorBitcoinNetworkException (CantMonitorBitcoinNetworkException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * returns Transcation Manager for the Incoming Crypto Router
     * @return
     */
    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return fermatCryptoNetworkManager;
    }

    /**
     * Gets all CryptoTransactions that matches that passed transcation hash
     * We may have multiple CryptoTranscation because each have a different CryptoStatus
     * @param txHash
     * @return
     * @throws CantGetCryptoTransactionException
     */
    @Override
    public List<CryptoTransaction> getCryptoTransactions(String txHash) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getGenesisTransaction(txHash);
    }

    /**
     * Gets the specified bitcoin transaction
     * @param transactionHash
     * @return
     */
    @Override
    public org.bitcoinj.core.Transaction getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, String transactionHash) {
        return fermatCryptoNetworkManager.getBitcoinTransaction(blockchainNetworkType, transactionHash);
    }

    /**
     * gets the current Crypto Status for the specified Transaction ID
     * @param txHash the Bitcoin transaction hash
     * @return the last crypto status
     * @throws CantGetTransactionCryptoStatusException
     */
    @Override
    public CryptoStatus getCryptoStatus(String txHash) throws CantGetTransactionCryptoStatusException {
        return fermatCryptoNetworkManager.getCryptoStatus(txHash);
    }

    /**
     * Broadcast a well formed, commited and signed transaction into the network.
     * @param txHash
     * @throws CantBroadcastTransactionException
     */
    @Override
    public synchronized void broadcastTransaction(String txHash) throws CantBroadcastTransactionException {
        fermatCryptoNetworkManager.broadcastTransaction(txHash);
    }

    /**
     * Returns the broadcast Status for a specified transaction.
     * @param txHash
     * @return
     * @throws CantGetBroadcastStatusException
     */
    @Override
    public BroadcastStatus getBroadcastStatus(String txHash) throws CantGetBroadcastStatusException {
        return fermatCryptoNetworkManager.getBroadcastStatus(txHash);
    }


    /**
     * Stores a Bitcoin Transaction in the CryptoNetwork to be broadcasted later
     * @param blockchainNetworkType
     * @param tx
     * @param transactionId
     * @param commit
     * @throws CantStoreBitcoinTransactionException
     */
    @Override
    public synchronized void storeBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, org.bitcoinj.core.Transaction tx, UUID transactionId, boolean commit) throws CantStoreBitcoinTransactionException {
        fermatCryptoNetworkManager.storeBitcoinTransaction(blockchainNetworkType, tx, transactionId, commit);
    }

    /**
     * Will mark the passed transaction as cancelled, and it won't be broadcasted again.
     * @param txHash
     * @throws CantCancellBroadcastTransactionException
     */
    @Override
    public void cancelBroadcast(String txHash) throws CantCancellBroadcastTransactionException {
        fermatCryptoNetworkManager.cancelBroadcast(txHash);
    }

    /**
     * Will get the BlockchainConnectionStatus for the specified network.
     * @param blockchainNetworkType the Network type we won't to get info from. If the passed network is not currently activated,
     *                              then we will receive null.
     * @return BlockchainConnectionStatus with information of amount of peers currently connected, etc.
     * @exception CantGetBlockchainConnectionStatusException
     */
    @Override
    public BlockchainConnectionStatus getBlockchainConnectionStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBlockchainConnectionStatusException {
        return fermatCryptoNetworkManager.getBlockchainConnectionStatus(blockchainNetworkType);
    }

    /**
     * Gets the active networks running on the Crypto Network
     * @return the list of active networks {MainNet, TestNet and RegTest}
     * @throws CantGetActiveBlockchainNetworkTypeException
     */
    @Override
    public List<BlockchainNetworkType> getActivesBlockchainNetworkTypes() throws CantGetActiveBlockchainNetworkTypeException {
        return  fermatCryptoNetworkManager.getActivesBlockchainNetworkTypes();
    }

    /**
     * Get the bitcoin transactions stored by the CryptoNetwork
     * @param blockchainNetworkType the network type
     * @return the bitcoin transaction
     */
    @Override
    public List<org.bitcoinj.core.Transaction> getBitcoinTransactions(BlockchainNetworkType blockchainNetworkType) {
        return fermatCryptoNetworkManager.getBitcoinTransactions(blockchainNetworkType);
    }

    /**
     * Gets a stored CryptoTransaction in whatever network.
     * @param txHash the transaction hash of the transaction
     * @param cryptoTransactionType the type of CryptoTransaction we are looking for
     * @param toAddress the address this transaction was sent to.
     * @return the CryptoTransaction with the latest cryptoStatus
     * @throws CantGetCryptoTransactionException
     */
    @Override
    public CryptoTransaction getCryptoTransaction(String txHash, @Nullable CryptoTransactionType cryptoTransactionType, @Nullable CryptoAddress toAddress) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getCryptoTransaction(txHash, cryptoTransactionType, toAddress);
    }

    /**
     * Based on the passed transaction chain of Transactions hashes and Blocks hashes, determines the entire path
     * of the chain until the Genesis Transaction is reached.
     * The genesis Transaction will be the first transaction in the map.
     * @param transactionChain a Map with the form TransactionHash / BlockHash
     * @return all the CryptoTransactions originated at the genesis transaction
     * @throws CantGetCryptoTransactionException
     */
    @Override
    public CryptoTransaction getGenesisCryptoTransaction(@Nullable BlockchainNetworkType blockchainNetworkType, LinkedHashMap<String, String> transactionChain) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getGenesisCryptoTransaction(blockchainNetworkType, transactionChain);
    }

    /**
     * Based on the Parent trasaction passed, will return all the CryptoTransactions that are a direct descendant of this parent.
     * Only if it is a locally stored transaction
     * @param parentTransactionHash the hash of the parent trasaction
     * @return the list of CryptoTransactions that are a direct child of the parent.
     * @throws CantGetCryptoTransactionException
     */
    @Override
    public List<CryptoTransaction> getChildTransactionsFromParent(String parentTransactionHash) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getChildTransactionsFromParent(parentTransactionHash);
    }

    /**
     * Gets the download progress from the specified network
     * @param blockchainNetworkType The network type we want to know the download progress
     * @return the BlockchainDownloadProgress class which includes information about pending blocks, total blocks, etc.
     * @throws CantGetBlockchainDownloadProgress
     */
    @Override
    public BlockchainDownloadProgress getBlockchainDownloadProgress(BlockchainNetworkType blockchainNetworkType) throws CantGetBlockchainDownloadProgress {
        return fermatCryptoNetworkManager.getBlockchainDownloadProgress(blockchainNetworkType);
    }

    /**
     * Gets the list of stored CryptoTransactions for the specified network type
     * @param blockchainNetworkType the network type to get the transactions from.
     * @return the list of Crypto Transaction
     * @throws CantGetCryptoTransactionException
     */
    @Override
    public List<CryptoTransaction> getCryptoTransactions(BlockchainNetworkType blockchainNetworkType, CryptoAddress addressTo, @Nullable CryptoTransactionType cryptoTransactionType) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getCryptoTransactions(blockchainNetworkType, addressTo, cryptoTransactionType);
    }
}