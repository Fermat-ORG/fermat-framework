package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseConstants;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "manuel",
        layer = Layers.DIGITAL_ASSET_TRANSACTION,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION)
public class AssetDistributionDigitalAssetTransactionPluginRoot extends AbstractPlugin implements
        AssetDistributionManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    public static long DELIVERING_TIMEOUT = 1 /*MINUTES!!*/ * 40 * 1000;
    public static long BROADCASTING_MAX_ATTEMPT_NUMBER = 10;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;
    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager actorAssetIssuerManager;
    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_ISSUER)
    AssetIssuerWalletManager assetIssuerWalletManager;
    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.ASSET_TRANSMISSION)
    AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;
    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_NETWORK, plugin = Plugins.BITCOIN_NETWORK)
    BitcoinNetworkManager bitcoinNetworkManager;
    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_ASSET_VAULT)
    AssetVaultManager assetVaultManager;
    org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.DigitalAssetDistributionVault digitalAssetDistributionVault;
    org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.AssetDistributionTransactionManager assetDistributionTransactionManager;
    org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events.AssetDistributionMonitorAgent assetDistributionMonitorAgent;
    Database assetDistributionDatabase;
    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    public AssetDistributionDigitalAssetTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return AssetDistributionDigitalAssetTransactionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    private void createAssetDistributionTransactionDatabase() throws CantCreateDatabaseException {
        org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseFactory databaseFactory = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseFactory(this.pluginDatabaseSystem);
        assetDistributionDatabase = databaseFactory.createDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE);
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            try {
                this.assetDistributionDatabase = this.pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE);
            } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
                try {
                    createAssetDistributionTransactionDatabase();
                } catch (CantCreateDatabaseException innerException) {
                    throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException, "Starting Asset Distribution plugin - " + this.pluginId, "Cannot open or create the plugin database");
                }
            }
            this.digitalAssetDistributionVault = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.DigitalAssetDistributionVault(this.pluginId,
                    this.pluginFileSystem,
                    this.errorManager,
                    assetIssuerWalletManager,
                    actorAssetIssuerManager,
                    bitcoinNetworkManager);
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDao assetDistributionDao = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDao(pluginDatabaseSystem,
                    pluginId,
                    digitalAssetDistributionVault);
            this.assetDistributionTransactionManager = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.AssetDistributionTransactionManager(
                    this.assetVaultManager,
                    this.errorManager,
                    this.pluginId,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.bitcoinNetworkManager,
                    digitalAssetDistributionVault,
                    assetDistributionDao,
                    assetTransmissionNetworkServiceManager,
                    actorAssetIssuerManager);
            //Starting Event Recorder
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events.AssetDistributionRecorderService assetDistributionRecorderService = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events.AssetDistributionRecorderService(assetDistributionDao, eventManager);
            try {
                assetDistributionRecorderService.start();
            } catch (CantStartServiceException exception) {
                //This plugin must be stopped if this happens.
                this.serviceStatus = ServiceStatus.STOPPED;
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_RECEPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantStartPluginException("Asset reception Event Recorded could not be started", exception, Plugins.BITDUBAI_ASSET_RECEPTION_TRANSACTION.getCode(), "The plugin event recorder is not started");
            }
            startMonitorAgent();
        } catch (CantSetObjectException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, "Starting Asset Distribution plugin", "Cannot set an object, probably is null");
        } catch (CantExecuteDatabaseOperationException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, "Starting pluginDatabaseSystem in DigitalAssetDistributor", "Error in constructor method AssetDistributor");
        } catch (Exception exception) {
            System.out.println("Asset Distribution test exception " + exception);
            exception.printStackTrace();
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    //TODO: DELETE THIS USELESS METHOD
    private void printSomething(String information) {
        System.out.println("ASSET_DISTRIBUTION: " + information);
    }

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the asset distribution plugin
     *
     * @throws CantGetLoggedInDeviceUserException
     * @throws CantSetObjectException
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantSetObjectException, CantStartAgentException {
        if (this.assetDistributionMonitorAgent == null) {
            this.assetDistributionMonitorAgent = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events.AssetDistributionMonitorAgent(this.pluginDatabaseSystem,
                    this.errorManager,
                    this.pluginId,
                    pluginFileSystem,
                    assetVaultManager,
                    bitcoinNetworkManager,
                    logManager,
                    digitalAssetDistributionVault,
                    assetTransmissionNetworkServiceManager);
        }
        this.assetDistributionMonitorAgent.start();
    }

    @Override
    public void distributeAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute, String walletPublicKey) throws CantDistributeDigitalAssetsException {
        printSomething("The Wallet public key is hardcoded");
        walletPublicKey = WalletUtilities.WALLET_PUBLIC_KEY;
        this.assetDistributionTransactionManager.distributeAssets(digitalAssetsToDistribute, walletPublicKey);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.developer_utils.AssetDistributionDeveloperDatabaseFactory assetDistributionDeveloperDatabaseFactory = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.developer_utils.AssetDistributionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return assetDistributionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.developer_utils.AssetDistributionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE);
            return org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.developer_utils.AssetDistributionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", exception, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("AssetDistributionDeveloperDatabaseFactory");
        returnedClasses.add("CantCheckAssetDistributionProgressException");
        returnedClasses.add("CantDeliverDigitalAssetException");
        returnedClasses.add("CantGetActorAssetIssuerException");
        returnedClasses.add("AssetDistributionDao");
        returnedClasses.add("AssetDistributionDatabaseConstants");
        returnedClasses.add("AssetDistributionDatabaseFactory");
        returnedClasses.add("AssetDistributionMonitorAgent");
        returnedClasses.add("AssetDistributionTransactionManager");
        returnedClasses.add("DigitalAssetDistributor");
        returnedClasses.add("AssetDistributionDigitalAssetTransactionPluginRoot");
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
            if (AssetDistributionDigitalAssetTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetDistributionDigitalAssetTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetDistributionDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetDistributionDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }
}
