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
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantCancellBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetActiveBlockchainNetworkTypeException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetImportedAddressesException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorCryptoNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainConnectionStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.database.FermatCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.database.FermatCryptoNetworkDeveloperDatabaseFactory;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions.CantInitializeFermatCryptoNetworkDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.structure.FermatCryptoNetworkEventsAgent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.structure.FermatCryptoNetworkManager;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util.FermatBlockchainProvider;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;


/**
 * Created by rodrigo on 5/20/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH, maintainerMail = "acosta_rodrigo@hotmail.com", createdBy = "acostarodrigo", layer = Layers.CRYPTO_NETWORK, platform = Platforms.BLOCKCHAINS, plugin = Plugins.FERMAT_NETWORK)
public class FermatCryptoNetworkPluginRoot
        extends AbstractPlugin
        implements  TransactionSender<CryptoTransaction>,
        BlockchainManager<ECKey, Transaction>,
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
    private FermatBlockchainProvider blockchainProvider;

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
         * instantiate the Fermat Blockchain Provider
         */
        blockchainProvider = new FermatBlockchainProvider();

        /**
         * instantiate the network Manager
         */
        fermatCryptoNetworkManager = new FermatCryptoNetworkManager(this.eventManager, this.pluginFileSystem, this.pluginId, this.errorManager, getDao(), blockchainProvider);

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

        System.out.println("***********************************\n");
        System.out.println("Fermat network started\n");
        System.out.println("***********************************\n");

        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
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
     * BlockchainManager Implemented methods.
     */

    @Override
    public void monitorCryptoNetworkFromKeyList(CryptoVaults requester, List<BlockchainNetworkType> blockchainNetworkTypes, List<ECKey> keyList) throws CantMonitorCryptoNetworkException {
        fermatCryptoNetworkManager.monitorCryptoNetworkFromKeyList(requester, blockchainNetworkTypes, keyList);
    }

    @Override
    public List<CryptoTransaction> getCryptoTransactions(String txHash) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getCryptoTransactions(txHash);
    }

    @Override
    public CryptoStatus getCryptoStatus(String txHash) throws CantGetTransactionCryptoStatusException {
        return fermatCryptoNetworkManager.getCryptoStatus(txHash);
    }

    @Override
    public void storeTransaction(BlockchainNetworkType blockchainNetworkType, Transaction tx, UUID transactionId, boolean commit) throws CantStoreTransactionException {
        fermatCryptoNetworkManager.storeTransaction(blockchainNetworkType, tx, transactionId, commit);
    }

    @Override
    public void broadcastTransaction(String txHash) throws CantBroadcastTransactionException {
        fermatCryptoNetworkManager.broadcastTransaction(txHash);
    }

    @Override
    public void cancelBroadcast(String txHash) throws CantCancellBroadcastTransactionException {
        fermatCryptoNetworkManager.cancelBroadcast(txHash);
    }

    @Override
    public BroadcastStatus getBroadcastStatus(String txHash) throws CantGetBroadcastStatusException {
        return fermatCryptoNetworkManager.getBroadcastStatus(txHash);
    }

    @Override
    public List<Transaction> getBlockchainProviderTransactions(BlockchainNetworkType blockchainNetworkType) {
        return fermatCryptoNetworkManager.getBlockchainProviderTransactions(blockchainNetworkType);
    }

    @Override
    public Transaction getBlockchainProviderTransaction(BlockchainNetworkType blockchainNetworkType, String transactionHash) {
        return fermatCryptoNetworkManager.getBlockchainProviderTransaction(blockchainNetworkType, transactionHash);
    }

    @Override
    public BlockchainConnectionStatus getBlockchainConnectionStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBlockchainConnectionStatusException {
        return fermatCryptoNetworkManager.getBlockchainConnectionStatus(blockchainNetworkType);
    }

    @Override
    public List<BlockchainNetworkType> getActivesBlockchainNetworkTypes() throws CantGetActiveBlockchainNetworkTypeException {
        return fermatCryptoNetworkManager.getActivesBlockchainNetworkTypes();
    }

    @Override
    public CryptoTransaction getCryptoTransaction(String txHash, @Nullable CryptoTransactionType cryptoTransactionType, @Nullable CryptoAddress toAddress) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getCryptoTransaction(txHash, cryptoTransactionType, toAddress);
    }

    @Override
    public List<CryptoTransaction> getCryptoTransactions(BlockchainNetworkType blockchainNetworkType, CryptoAddress addressTo, @Nullable CryptoTransactionType cryptoTransactionType) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getCryptoTransactions(blockchainNetworkType, addressTo, cryptoTransactionType);
    }

    @Override
    public CryptoTransaction getGenesisCryptoTransaction(@Nullable BlockchainNetworkType blockchainNetworkType, LinkedHashMap<String, String> transactionChain) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getGenesisCryptoTransaction(blockchainNetworkType, transactionChain);
    }

    @Override
    public List<CryptoTransaction> getChildTransactionsFromParent(String parentTransactionHash) throws CantGetCryptoTransactionException {
        return fermatCryptoNetworkManager.getChildTransactionsFromParent(parentTransactionHash);
    }

    @Override
    public BlockchainDownloadProgress getBlockchainDownloadProgress(BlockchainNetworkType blockchainNetworkType) throws CantGetBlockchainDownloadProgress {
        return fermatCryptoNetworkManager.getBlockchainDownloadProgress(blockchainNetworkType);
    }

    @Override
    public List<CryptoAddress> getImportedAddresses(BlockchainNetworkType blockchainNetworkType)  {
        return null;
    }
}