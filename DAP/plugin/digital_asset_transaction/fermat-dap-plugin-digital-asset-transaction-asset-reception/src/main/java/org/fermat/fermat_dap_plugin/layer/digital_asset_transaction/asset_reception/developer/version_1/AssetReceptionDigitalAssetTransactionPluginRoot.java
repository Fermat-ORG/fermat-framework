package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1;

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

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_reception.interfaces.AssetReceptionManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.developer_utils.AssetReceptionDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.DigitalAssetReceptionVault;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.DigitalAssetReceptor;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.database.AssetReceptionDao;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.database.AssetReceptionDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.database.AssetReceptionDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.events.AssetReceptionMonitorAgent;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.events.AssetReceptionRecorderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH,
        maintainerMail = "marsvicam@gmail.com",
        createdBy = "manuel",
        layer = Layers.DIGITAL_ASSET_TRANSACTION,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_ASSET_RECEPTION_TRANSACTION)
public class AssetReceptionDigitalAssetTransactionPluginRoot extends AbstractPlugin implements
        AssetReceptionManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_ROUTER, plugin = Plugins.INCOMING_CRYPTO)
    IncomingCryptoManager incomingCryptoManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_USER)
    AssetUserWalletManager assetUserWalletManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.ASSET_TRANSMISSION)
    AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager actorAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_NETWORK, plugin = Plugins.BITCOIN_NETWORK)
    BlockchainManager bitcoinNetworkManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
    private AssetReceptionMonitorAgent assetReceptionMonitorAgent;
    private DigitalAssetReceptor digitalAssetReceptor;

    public AssetReceptionDigitalAssetTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetReceptionDeveloperDatabaseFactory assetReceptionDatabaseFactory = new AssetReceptionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return assetReceptionDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return AssetReceptionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetReceptionDatabaseConstants.ASSET_RECEPTION_DATABASE);
            return AssetReceptionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
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

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
//        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_rception.developer.bitdubai.version_1.structure.events.AssetReceptionMonitorAgent");
        returnedClasses.add("AssetReceptionDigitalAssetTransactionPluginRoot");
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
            if (AssetReceptionDigitalAssetTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetReceptionDigitalAssetTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetReceptionDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetReceptionDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    private void createAssetReceptionTransactionDatabase() throws CantCreateDatabaseException {
        AssetReceptionDatabaseFactory databaseFactory = new AssetReceptionDatabaseFactory(this.pluginDatabaseSystem);
        databaseFactory.createDatabase(pluginId, AssetReceptionDatabaseConstants.ASSET_RECEPTION_DATABASE);
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.pluginDatabaseSystem.openDatabase(pluginId, AssetReceptionDatabaseConstants.ASSET_RECEPTION_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            try {
                createAssetReceptionTransactionDatabase();
            } catch (CantCreateDatabaseException innerException) {
                throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException, "Starting Asset Reception plugin - " + this.pluginId, "Cannot open or create the plugin database");
            }
        }
        try {
            AssetReceptionDao assetReceptionDao = new AssetReceptionDao(this.pluginDatabaseSystem, this.pluginId);
            DigitalAssetReceptionVault digitalAssetReceptionVault = new DigitalAssetReceptionVault(
                    pluginId,
                    pluginFileSystem,
                    this,
                    assetUserWalletManager);

            digitalAssetReceptor = new DigitalAssetReceptor(
                    this,
                    this.pluginId,
                    this.pluginFileSystem,
                    this.bitcoinNetworkManager,
                    digitalAssetReceptionVault,
                    assetReceptionDao);


            AssetReceptionRecorderService assetReceptionRecorderService = new AssetReceptionRecorderService(assetReceptionDao, eventManager);
            try {
                //I need to check if this works
                assetReceptionRecorderService.start();
            } catch (CantStartServiceException e) {
                //This plugin must be stopped if this happens.
                this.serviceStatus = ServiceStatus.STOPPED;
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantStartPluginException("Asset reception Event Recorded could not be started", e, Plugins.BITDUBAI_ASSET_RECEPTION_TRANSACTION.getCode(), "The plugin event recorder is not started");
            }
            /**
             * I will start the Monitor Agent at start up
             */
            this.startMonitorAgent(digitalAssetReceptionVault);

        } catch (CantSetObjectException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "Starting Asset Reception plugin", "Cannot set an object, probably is null");
        } catch (CantExecuteDatabaseOperationException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "Starting Asset Reception plugin", "Cannot execute a database operation");
        } catch (CantStartServiceException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "Starting Asset Reception plugin", "Cannot start event recorder service");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            System.out.println("ASSET RECEPTION EXCEPTION TEST " + e);
            e.printStackTrace();
        } finally {
            this.serviceStatus = ServiceStatus.STOPPED;
        }

        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the asset distribution plugin
     *
     * @throws CantGetLoggedInDeviceUserException
     * @throws CantSetObjectException
     * @throws CantStartAgentException
     */
    public void startMonitorAgent(DigitalAssetReceptionVault digitalAssetReceptionVault) throws CantGetLoggedInDeviceUserException, CantSetObjectException, CantStartAgentException {
        if (assetReceptionMonitorAgent == null) {
            assetReceptionMonitorAgent = new AssetReceptionMonitorAgent(
                    pluginDatabaseSystem,
                    this,
                    pluginId,
                    logManager,
                    bitcoinNetworkManager,
                    assetTransmissionNetworkServiceManager,
                    actorAssetUserManager,
                    actorAssetIssuerManager,
                    actorAssetRedeemPointManager,
                    digitalAssetReceptor,
                    digitalAssetReceptionVault,
                    incomingCryptoManager);
        }
        assetReceptionMonitorAgent.start();

    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return AssetReceptionDigitalAssetTransactionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }
}
