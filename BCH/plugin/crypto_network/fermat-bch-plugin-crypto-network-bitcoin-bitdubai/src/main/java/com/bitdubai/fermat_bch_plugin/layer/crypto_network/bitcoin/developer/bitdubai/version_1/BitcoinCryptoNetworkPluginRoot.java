package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantFixTransactionInconsistenciesException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.ErrorBroadcastingTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDeveloperDatabaseFactory;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinCryptoNetworkDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkEventsAgent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.UTXOProvider;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by rodrigo on 9/23/15.
 */
public class BitcoinCryptoNetworkPluginRoot extends AbstractPlugin implements
        BitcoinNetworkManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    public BitcoinCryptoNetworkPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * BitcoinNetworkManager variable
     */
    private BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    /**
     * DatabaseManagerForDevelopers interface implementations
     */
    private BitcoinCryptoNetworkDeveloperDatabaseFactory bitcoinCryptoNetworkDeveloperDatabaseFactory;
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        if (bitcoinCryptoNetworkDeveloperDatabaseFactory == null){
            bitcoinCryptoNetworkDeveloperDatabaseFactory = new BitcoinCryptoNetworkDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            try {
                bitcoinCryptoNetworkDeveloperDatabaseFactory.initializeDatabase();
            } catch (CantInitializeBitcoinCryptoNetworkDatabaseException e) {
                e.printStackTrace();
            }
        }
        return bitcoinCryptoNetworkDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return bitcoinCryptoNetworkDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return bitcoinCryptoNetworkDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    @Override
    public void start() throws CantStartPluginException {
        /**
         * instantiate the network Manager
         */
        bitcoinCryptoNetworkManager = new BitcoinCryptoNetworkManager(this.eventManager, this.pluginDatabaseSystem, this.pluginId);

        /**
         * Start the agent that will search for pending transactions to be notified.
         */
        BitcoinCryptoNetworkEventsAgent bitcoinCryptoNetworkEventsAgent = new BitcoinCryptoNetworkEventsAgent(this.pluginDatabaseSystem, this.pluginId, this.eventManager);
        try {
            bitcoinCryptoNetworkEventsAgent.start();
        } catch (Exception e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "Cant start BitcoinCryptoNetworkEventsAgent agent.", null);
        }

        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
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
            bitcoinCryptoNetworkManager.monitorNetworkFromKeyList(cryptoVault, blockchainNetworkTypes, keyList);
        } catch (Exception e) {
            throw new CantMonitorBitcoinNetworkException (CantMonitorBitcoinNetworkException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * returns Transcation Manager for the Incoming Crypto Router
     * @return
     */
    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return bitcoinCryptoNetworkManager;
    }

    /**
     * Gets all CryptoTransactions that matches that passed transcation hash
     * We may have multiple CryptoTranscation because each have a different CryptoStatus
     * @param txHash
     * @return
     * @throws CantGetCryptoTransactionException
     */
    @Override
    public List<CryptoTransaction> getCryptoTransaction(String txHash) throws CantGetCryptoTransactionException {
        return bitcoinCryptoNetworkManager.getGenesisTransaction(txHash);
    }

    /**
     * Broadcast a well formed, commited and signed transaction into the specified network
     * @param blockchainNetworkType
     * @param tx
     * @param transactionId the internal Fermat Transaction
     * @throws CantBroadcastTransactionException
     */
    @Override
    public void broadcastTransaction(BlockchainNetworkType blockchainNetworkType, Transaction tx, UUID transactionId) throws CantBroadcastTransactionException {
        bitcoinCryptoNetworkManager.broadcastTransaction(blockchainNetworkType, tx, transactionId);
    }

    /**
     * Gets the UTXO provider from the CryptoNetwork on the specified Network
     * @param blockchainNetworkType
     * @return
     */
    @Override
    public UTXOProvider getUTXOProvider(BlockchainNetworkType blockchainNetworkType) {
        return bitcoinCryptoNetworkManager.getUTXOProvider(blockchainNetworkType);
    }

    /**
     * Gets the specified bitcoin transaction
     * @param transactionHash
     * @return
     */
    @Override
    public Transaction getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, String transactionHash) {
        return bitcoinCryptoNetworkManager.getBitcoinTransaction(blockchainNetworkType, transactionHash);
    }

    @Override
    public List<Transaction> getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, ECKey ecKey) {
        return null;
    }

    @Override
    public List<Transaction> getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, List<ECKey> ecKeys) {
        return null;
    }

    @Override
    public List<Transaction> getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, VaultType vaultType) {
        //todo improve this to obtain only transactions from the specified vaultype.
        return bitcoinCryptoNetworkManager.getBitcoinTransactions(blockchainNetworkType);
    }

    /**
     * Will get the CryptoTransaction directly from the blockchain by requesting it to a peer.
     * If the transaction is not part of any of our vaults, we will ask it to a connected peer to retrieve it.
     * @param txHash the Hash of the transaction we are going to look for.
     * @param blockHash the Hash of block where this transaction was stored..
     * @return a CryptoTransaction with the information of the transaction.
     * @throws CantGetCryptoTransactionException
     */
    @Override
    public CryptoTransaction getCryptoTransactionFromBlockChain(String txHash, String blockHash) throws CantGetCryptoTransactionException {
        return  bitcoinCryptoNetworkManager.getCryptoTransactionFromBlockChain(txHash, blockHash);
    }


    /**
     * Will get all the CryptoTransactions stored in the CryptoNetwork which are a child of a parent Transaction
     * @param parentHash
     * @return
     * @throws CantGetCryptoTransactionException
     */
    @Override
    public List<CryptoTransaction> getChildCryptoTransaction(String parentHash) throws CantGetCryptoTransactionException {
        return bitcoinCryptoNetworkManager.getChildCryptoTransaction(parentHash);
    }

    /**
     * Will get all the CryptoTransactions stored in the CryptoNetwork which are a child of a parent Transaction
     * @param parentHash the parent transaction
     * @param depth the depth of how many transactions we will navigate until we reach the parent transaction. Max is 10
     * @return
     * @throws CantGetCryptoTransactionException
     */
    @Override
    public List<CryptoTransaction> getChildCryptoTransaction(String parentHash, int depth) throws CantGetCryptoTransactionException {
        return bitcoinCryptoNetworkManager.getChildCryptoTransaction(parentHash, depth);
    }

    /**
     * gets the current Crypto Status for the specified Transaction ID
     * @param txHash the Bitcoin transaction hash
     * @return the last crypto status
     * @throws CantGetTransactionCryptoStatusException
     */
    @Override
    public CryptoStatus getCryptoStatus(String txHash) throws CantGetTransactionCryptoStatusException {
        return bitcoinCryptoNetworkManager.getCryptoStatus(txHash);
    }

     /**
     * Broadcast a well formed, commited and signed transaction into the network.
     * @param txHash
     * @throws CantBroadcastTransactionException
     */
    @Override
    public void broadcastTransaction(String txHash) throws CantBroadcastTransactionException, ExecutionException, InterruptedException {
        bitcoinCryptoNetworkManager.broadcastTransaction(txHash);
    }

    /**
     * Returns the broadcast Status for a specified transaction.
     * @param txHash
     * @return
     * @throws CantGetBroadcastStatusException
     */
    @Override
    public BroadcastStatus getBroadcastStatus(String txHash) throws CantGetBroadcastStatusException {
        return bitcoinCryptoNetworkManager.getBroadcastStatus(txHash);
    }


    /**
     * Stores a Bitcoin Transaction in the CryptoNetwork to be broadcasted later
     * @param blockchainNetworkType
     * @param tx
     * @param transactionId
     * @throws CantStoreBitcoinTransactionException
     */
    @Override
    public void storeBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, Transaction tx, UUID transactionId) throws CantStoreBitcoinTransactionException {
        bitcoinCryptoNetworkManager.storeBitcoinTransaction(blockchainNetworkType, tx, transactionId);
    }
}