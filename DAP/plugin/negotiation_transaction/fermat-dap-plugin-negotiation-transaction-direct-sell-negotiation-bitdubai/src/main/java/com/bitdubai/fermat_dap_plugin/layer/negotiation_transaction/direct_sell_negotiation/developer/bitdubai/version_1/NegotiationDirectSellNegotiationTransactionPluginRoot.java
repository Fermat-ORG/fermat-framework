package com.bitdubai.fermat_dap_plugin.layer.negotiation_transaction.direct_sell_negotiation.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_negotiation_transaction.negotiation_direct_sell.interfaces.NegotiationDirectSellManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_plugin.layer.negotiation_transaction.direct_sell_negotiation.developer.bitdubai.version_1.developer_utils.NegotiationDirectSellDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.negotiation_transaction.direct_sell_negotiation.developer.bitdubai.version_1.structure.database.NegotiationDirectSellDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.negotiation_transaction.direct_sell_negotiation.developer.bitdubai.version_1.structure.database.NegotiationDirectSellDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.negotiation_transaction.direct_sell_negotiation.developer.bitdubai.version_1.structure.events.NegotiationDirectSellMonitorAgent;
import com.bitdubai.fermat_dap_plugin.layer.negotiation_transaction.direct_sell_negotiation.developer.bitdubai.version_1.structure.events.NegotiationDirectSellRecorderService;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/09/15.
 */
public class NegotiationDirectSellNegotiationTransactionPluginRoot extends AbstractPlugin implements
        NegotiationDirectSellManager,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    //VARIABLE DECLARATION

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_ASSET_VAULT)
    private AssetVaultManager assetVaultManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_USER)
    private AssetUserWalletManager assetUserWalletManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_NETWORK, plugin = Plugins.BITCOIN_NETWORK)
    private BitcoinNetworkManager bitcoinNetworkManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_MODULE, plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_VAULT)
    private CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.EXTRA_WALLET_USER)
    private ExtraUserManager extraUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.ASSET_TRANSMISSION)
    private AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_ISSUER)
    private AssetIssuerWalletManager assetIssuerWalletManager;


    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    NegotiationDirectSellRecorderService recorderService;
    NegotiationDirectSellMonitorAgent monitorAgent;

    //CONSTRUCTORS

    public NegotiationDirectSellNegotiationTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartPluginException {
        System.out.println("VAMM: PLUGIN DIRECT SELL NEGOTIATION INICIADO!!");

        String context = "pluginId : " + pluginId + "\n" +
                "ErrorManager : " + errorManager + "\n" +
                "pluginDatabaseSystem : " + pluginDatabaseSystem + "\n" +
                "pluginFileSystem : " + pluginFileSystem + "\n" +
                "logManager : " + logManager + "\n" +
                "eventManager : " + eventManager + "\n" +
                "recorderService : " + recorderService + "\n";

        try {
            //CREATES DIRECT SELL NEGOTIATION DATABASE AND ITS TABLES.
            NegotiationDirectSellDatabaseFactory databaseFactory = new NegotiationDirectSellDatabaseFactory(pluginDatabaseSystem);
            if (!databaseFactory.isDatabaseCreated(pluginId)) {
                databaseFactory.createDatabase(pluginId);
            }
            recorderService = new NegotiationDirectSellRecorderService(pluginId, eventManager, pluginDatabaseSystem);
            recorderService.start();
            monitorAgent = new NegotiationDirectSellMonitorAgent(pluginDatabaseSystem,
                    logManager,
                    errorManager,
                    pluginId,
                    extraUserManager);
            monitorAgent.start();
        } catch (Exception e) {
            throw new CantStartPluginException(FermatException.wrapException(e), context, e.getMessage());
        } finally {
            this.serviceStatus = ServiceStatus.STOPPED;
        }

        super.start();
    }

    @Override
    public void stop() {
        monitorAgent.stop();
        recorderService.stop();
        super.stop();
    }

    //PRIVATE METHODS

    //GETTERS AND SETTERS

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.NegotiationDirectSellNegotiationTransactionPluginRoot");
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
            if (NegotiationDirectSellNegotiationTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                NegotiationDirectSellNegotiationTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                NegotiationDirectSellNegotiationTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                NegotiationDirectSellNegotiationTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            return NegotiationDirectSellNegotiationTransactionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return NegotiationDirectSellDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory, pluginId);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return NegotiationDirectSellDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, NegotiationDirectSellDatabaseConstants.DIRECT_SELL_NEGOTIATION_DATABASE);
            return NegotiationDirectSellDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DIRECT_SELL_NEGOTIATION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DIRECT_SELL_NEGOTIATION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", exception, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DIRECT_SELL_NEGOTIATION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }

}
