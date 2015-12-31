package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.AssetIssuingTransactionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetIssuingVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingRecorderService;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingTransactionMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public class AssetIssuingDigitalAssetTransactionPluginRoot extends AbstractPlugin implements
        AssetIssuingManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    BitcoinWalletManager bitcoinWalletManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_MODULE, plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.OUTGOING_INTRA_ACTOR)
    OutgoingIntraActorManager outgoingIntraActorManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_VAULT)
    CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager actorAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.INTRA_WALLET_USER)
    IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_ISSUER)
    AssetIssuerWalletManager assetIssuerWalletManager;


    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_NETWORK, plugin = Plugins.BITCOIN_NETWORK)
    BitcoinNetworkManager bitcoinNetworkManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_ASSET_VAULT)
    AssetVaultManager assetVaultManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    EventManager eventManager;


    public AssetIssuingDigitalAssetTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();
    private AssetIssuingTransactionManager assetIssuingTransactionManager;
    private AssetIssuingTransactionMonitorAgent assetIssuingTransactionMonitorAgent;
    private DigitalAssetIssuingVault digitalAssetIssuingVault;

    //TODO: Delete this log object
    Logger LOG = Logger.getGlobal();

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetIssuingTransactionDeveloperDatabaseFactory assetIssuingTransactionDeveloperDatabaseFactory = new AssetIssuingTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return assetIssuingTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return AssetIssuingTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
            return AssetIssuingTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", FermatException.wrapException(exception), "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
        } catch (DatabaseNotFoundException | CantOpenDatabaseException exception) {
            try {
                createAssetIssuingTransactionDatabase();
            } catch (CantCreateDatabaseException innerException) {
                throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException, "Starting Asset Issuing plugin - " + this.pluginId, "Cannot open or create the plugin database");
            }
        }
        try {
            digitalAssetIssuingVault = new DigitalAssetIssuingVault(pluginId,
                    pluginFileSystem,
                    errorManager,
                    assetIssuerWalletManager,
                    actorAssetIssuerManager);
            AssetIssuingTransactionDao assetIssuingTransactionDao = new AssetIssuingTransactionDao(this.pluginDatabaseSystem, this.pluginId);
            AssetTransactionService assetIssuingEventRecorderService = new AssetIssuingRecorderService(assetIssuingTransactionDao, eventManager);
            assetIssuingTransactionManager = new AssetIssuingTransactionManager(pluginId,
                    cryptoVaultManager,
                    bitcoinWalletManager,
                    pluginFileSystem,
                    errorManager,
                    assetVaultManager,
                    cryptoAddressBookManager,
                    outgoingIntraActorManager,
                    assetIssuerWalletManager,
                    digitalAssetIssuingVault,
                    assetIssuingTransactionDao,
                    actorAssetIssuerManager,
                    intraWalletUserIdentityManager);
            try {
                assetIssuingEventRecorderService.start();
            } catch (CantStartServiceException exception) {
                //This plugin must be stopped if this happens.
                this.serviceStatus = ServiceStatus.STOPPED;
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantStartPluginException("Event Recorded could not be started", exception, Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION.getCode(), "The plugin event recorder is not started");
            }

            startMonitorAgent();
        } catch (CantSetObjectException exception) {
            this.serviceStatus = ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, "Starting Asset Issuing plugin", "Cannot set an object, probably is null");
        } catch (CantExecuteDatabaseOperationException exception) {
            this.serviceStatus = ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, "Starting pluginDatabaseSystem in DigitalAssetCryptoTransactionFactory", "Error in constructor method AssetIssuingTransactionDao");
        } catch (CantStartAgentException exception) {
            this.serviceStatus = ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, "Starting Asset Issuing plugin", "cannot start monitor agent");
        } catch (Exception exception) {
            this.serviceStatus = ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Starting Asset Issuing plugin", "Unexpected exception");
        }
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the asset issuing plugin
     *
     * @throws CantGetLoggedInDeviceUserException
     * @throws CantSetObjectException
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantSetObjectException, CantStartAgentException {
        if (assetIssuingTransactionMonitorAgent == null) {
            assetIssuingTransactionMonitorAgent = new AssetIssuingTransactionMonitorAgent(pluginDatabaseSystem,
                    errorManager,
                    logManager,
                    pluginId,
                    outgoingIntraActorManager,
                    bitcoinNetworkManager,
                    digitalAssetIssuingVault);
        }
        assetIssuingTransactionMonitorAgent.start();
    }


    @Override
    public void stop() {
        this.assetIssuingTransactionMonitorAgent.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, String walletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException {
        try {
            System.out.println("Asset issuing manager" + this.assetIssuingTransactionManager);
            this.assetIssuingTransactionManager.issueAssets(digitalAssetToIssue, assetsAmount, walletPublicKey, blockchainNetworkType);
        } catch (Exception exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing Assets", "Unexpected exception");
        }

    }

    @Override
    public void issuePendingDigitalAssets(String publicKey) {
        this.assetIssuingTransactionManager.issuePendingDigitalAssets(publicKey);
    }


    /**
     * This method will create the plugin database
     *
     * @throws CantCreateDatabaseException
     */
    private void createAssetIssuingTransactionDatabase() throws CantCreateDatabaseException {
        AssetIssuingTransactionDatabaseFactory databaseFactory = new AssetIssuingTransactionDatabaseFactory(this.pluginDatabaseSystem);
        databaseFactory.createDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
    }

    @Override
    public void confirmReception(String genesisTransaction) throws CantConfirmTransactionException {
        this.assetIssuingTransactionManager.confirmReception(genesisTransaction);
    }

    @Override
    public int getNumberOfIssuedAssets(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        return this.assetIssuingTransactionManager.getNumberOfIssuedAssets(assetPublicKey);
    }

    @Override
    public IssuingStatus getIssuingStatus(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        return this.assetIssuingTransactionManager.getIssuingStatus(assetPublicKey);
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.AssetIssuingTransactionMonitorAgentMaxIterationsReachedException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetTransactionException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantIssueDigitalAssetException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisAddressException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisTransactionException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingRecorderService");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingTransactionMonitorAgent");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.AssetIssuingTransactionManager");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetCryptoTransactionFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetIssuingVault");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.AssetIssuingDigitalAssetTransactionPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.DeveloperBitDubai");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (AssetIssuingDigitalAssetTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetIssuingDigitalAssetTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetIssuingDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetIssuingDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return AssetIssuingDigitalAssetTransactionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }
}
