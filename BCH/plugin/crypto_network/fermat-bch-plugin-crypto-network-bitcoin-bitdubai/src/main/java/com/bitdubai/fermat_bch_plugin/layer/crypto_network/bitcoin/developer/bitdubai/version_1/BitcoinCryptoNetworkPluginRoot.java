package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
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
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.CryptoVaults;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDeveloperDatabaseFactory;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinCryptoNetworkDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkEventsAgent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.UTXOProvider;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 9/23/15.
 */
public class BitcoinCryptoNetworkPluginRoot extends AbstractPlugin implements
        BitcoinNetworkManager,
        DatabaseManagerForDevelopers,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.ANDROID         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    public BitcoinCryptoNetworkPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * BitcoinNetworkManager variable
     */
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DatabaseManagerForDevelopers interface implementations
     */
    BitcoinCryptoNetworkDeveloperDatabaseFactory bitcoinCryptoNetworkDeveloperDatabaseFactory;
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
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
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
     * @throws CantGetGenesisTransactionException
     */
    @Override
    public List<CryptoTransaction> getGenesisTransaction(String txHash) throws CantGetGenesisTransactionException {
        return bitcoinCryptoNetworkManager.getGenesisTransaction(txHash);
    }

    /**
     * Broadcast a well formed, commited and signed transaction into the specified network
     * @param blockchainNetworkType
     * @param tx
     * @throws CantBroadcastTransactionException
     */
    @Override
    public void broadcastTransaction(BlockchainNetworkType blockchainNetworkType, Transaction tx) throws CantBroadcastTransactionException {
        bitcoinCryptoNetworkManager.broadcastTransaction(blockchainNetworkType, tx);
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
}