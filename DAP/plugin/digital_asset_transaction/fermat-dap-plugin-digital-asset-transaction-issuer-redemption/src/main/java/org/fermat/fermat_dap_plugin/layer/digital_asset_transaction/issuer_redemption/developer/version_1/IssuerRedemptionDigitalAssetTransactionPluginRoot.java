package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_api.layer.dap_transaction.issuer_appropriation.interfaces.IssuerAppropriationManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.issuer_redemption.interfaces.IssuerRedemptionManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.developers_utils.IssuerRedemptionDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.structure.database.IssuerRedemptionDao;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.structure.database.IssuerRedemptionDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.structure.database.IssuerRedemptionDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.structure.events.IssuerRedemptionMonitorAgent;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.structure.events.IssuerRedemptionRecorderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH,
        maintainerMail = "marsvicam@gmail.com",
        createdBy = "manuel",
        layer = Layers.DIGITAL_ASSET_TRANSACTION,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_ISSUER_REDEMPTION_TRANSACTION)
public class IssuerRedemptionDigitalAssetTransactionPluginRoot extends AbstractPlugin implements
        IssuerRedemptionManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_ROUTER, plugin = Plugins.INCOMING_CRYPTO)
    IncomingCryptoManager incomingCryptoManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_MODULE, plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_ISSUER)
    private AssetIssuerWalletManager assetIssuerWalletManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_NETWORK, plugin = Plugins.BITCOIN_NETWORK)
    private BlockchainManager bitcoinNetworkManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    private ActorAssetIssuerManager actorAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.ASSET_TRANSMISSION)
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_ASSET_VAULT)
    private AssetVaultManager AssetVaultManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ISSUER_APPROPRIATION)
    private IssuerAppropriationManager issuerAppropriationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletMiddlewareManager;

    public IssuerRedemptionDigitalAssetTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        IssuerRedemptionDeveloperDatabaseFactory assetReceptionDatabaseFactory = new IssuerRedemptionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return assetReceptionDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return IssuerRedemptionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_DATABASE);
            return IssuerRedemptionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (DatabaseNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return IssuerRedemptionDigitalAssetTransactionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("IssuerRedemptionDigitalAssetTransactionPluginRoot");
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
            if (IssuerRedemptionDigitalAssetTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                IssuerRedemptionDigitalAssetTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                IssuerRedemptionDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                IssuerRedemptionDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.pluginDatabaseSystem.openDatabase(this.pluginId, IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_DATABASE);
        } catch (DatabaseNotFoundException | CantOpenDatabaseException exception) {
            try {
                createIssuerRedemptionDatabase();
            } catch (CantCreateDatabaseException e) {
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "Starting Asset Issuing plugin - " + this.pluginId, "Cannot open or create the plugin database");
            }
        }
        try {
            IssuerRedemptionDao issuerRedemptionDao = new IssuerRedemptionDao(pluginId, pluginDatabaseSystem);
            IssuerRedemptionRecorderService issuerRedemptionRecorderService = new IssuerRedemptionRecorderService(issuerRedemptionDao, eventManager);
            IssuerRedemptionMonitorAgent issuerRedemptionMonitorAgent = new IssuerRedemptionMonitorAgent(
                    assetIssuerWalletManager,
                    actorAssetIssuerManager,
                    bitcoinNetworkManager,
                    cryptoAddressBookManager,
                    this,
                    pluginId,
                    pluginDatabaseSystem,
                    AssetVaultManager,
                    issuerAppropriationManager,
                    walletMiddlewareManager,
                    assetTransmissionManager,
                    actorAssetUserManager,
                    actorAssetRedeemPointManager,
                    incomingCryptoManager);
            try {
                issuerRedemptionRecorderService.start();
                issuerRedemptionMonitorAgent.start();
            } catch (CantStartServiceException | CantStartAgentException e) {
                this.serviceStatus = ServiceStatus.STOPPED;
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantStartPluginException("Event Recorded could not be started", e, Plugins.BITDUBAI_ISSUER_REDEMPTION_TRANSACTION.getCode(), "The plugin event recorder is not started");
            }
        } catch (CantExecuteDatabaseOperationException e) {
            this.serviceStatus = ServiceStatus.STOPPED;
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "Starting pluginDatabaseSystem in Issuer Redemption plugin", "Error in constructor method IssuerRedemptionDao");
        } catch (CantStartServiceException | CantSetObjectException e) {
            this.serviceStatus = ServiceStatus.STOPPED;
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "Starting Issuing Redemption plugin", "cannot start monitor agent");
        }
    }

    /**
     * This method will create the plugin database
     *
     * @throws CantCreateDatabaseException
     */
    private void createIssuerRedemptionDatabase() throws CantCreateDatabaseException {
        IssuerRedemptionDatabaseFactory databaseFactory = new IssuerRedemptionDatabaseFactory(this.pluginDatabaseSystem);
        databaseFactory.createDatabase(pluginId, IssuerRedemptionDatabaseConstants.ASSET_ISSUER_REDEMPTION_DATABASE);
    }
}

