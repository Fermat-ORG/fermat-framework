package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.event_handlers.CryptoAddressesNewsEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressDeniedActionException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressReceivedActionException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsMiddlewareDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Leon Acosta (laion.cj91@gmail.com) on 10/09/2015.
 * <p/>
 * This plugin manages list of contacts.
 * <p/>
 * A contact list is associated with one or more wallets. This is useful if a user want to share contacts between
 * wallets. A single wallet can be part of more tha one list also.
 * <p/>
 * * * * * *
 */

@PluginInfo(createdBy = "Leon Acosta", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_CONTACTS)


public class WalletContactsMiddlewarePluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers     ,
        WalletContactsManager       {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CRYPTO_ADDRESSES)
    private CryptoAddressesManager cryptoAddressesManager;


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    public WalletContactsMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    public static final Actors actorType = Actors.INTRA_USER;


    /**
     * WalletContactsManager Interface implementation.
     */
    public WalletContactsRegistry getWalletContactsRegistry() throws CantGetWalletContactRegistryException {

        try {
            WalletContactsMiddlewareRegistry walletContactsRegistry = new WalletContactsMiddlewareRegistry(null, getErrorManager(), logManager, pluginDatabaseSystem, pluginId, broadcaster);

            walletContactsRegistry.initialize();

            return walletContactsRegistry;
        } catch (CantInitializeWalletContactsMiddlewareDatabaseException exception) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetWalletContactRegistryException(CantGetWalletContactRegistryException.DEFAULT_MESSAGE, exception);
        } catch (Exception exception){

            throw new CantGetWalletContactRegistryException(CantGetWalletContactRegistryException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }
    }


    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {

        WalletContactsMiddlewareRegistry walletContactsRegistry = new WalletContactsMiddlewareRegistry(
                cryptoAddressesManager,
                getErrorManager()          ,
                logManager            ,
                pluginDatabaseSystem  ,
                pluginId,
                broadcaster
        );

        try {

            walletContactsRegistry.initialize();
        } catch (CantInitializeWalletContactsMiddlewareDatabaseException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e);
        }
        // execute pending address exchange requests
        executePendingAddressExchangeRequests(walletContactsRegistry);

        FermatEventListener cryptoAddressNewsEventListener = eventManager.getNewListener(EventType.CRYPTO_ADDRESSES_NEWS);
        cryptoAddressNewsEventListener.setEventHandler(new CryptoAddressesNewsEventHandler(walletContactsRegistry, this));
        eventManager.addListener(cryptoAddressNewsEventListener);
        listenersAdded.add(cryptoAddressNewsEventListener);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    private void executePendingAddressExchangeRequests(WalletContactsMiddlewareRegistry walletContactsRegistry) {
        try {
            List<CryptoAddressRequest> list = cryptoAddressesManager.listAllPendingRequests();

            System.out.println("----------------------------\n" +
                    "WALLET CONTACT MIDDLEWARE  : executePendingAddressExchangeRequests " +  list.size()
                    + "\n-------------------------------------------------");

            for (CryptoAddressRequest request : list) {

                if (request.getAction().equals(RequestAction.ACCEPT))
                    walletContactsRegistry.handleCryptoAddressReceivedEvent(request);

                if (request.getAction().equals(RequestAction.DENY))
                    walletContactsRegistry.handleCryptoAddressDeniedEvent(request);
//
//                if(request.getAction().equals(RequestAction.NONE))
//                    walletContactsRegistry.handleCryptoAddressReceivedEvent(request);

            }

        } catch(CantListPendingCryptoAddressRequestsException |
                CantHandleCryptoAddressDeniedActionException |
                CantHandleCryptoAddressReceivedActionException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    public void stop() {

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();

        returnedClasses.add("WalletContactsMiddlewarePluginRoot");
//        returnedClasses.add("WalletContactsMiddlewareRegistry");
//        returnedClasses.add("WalletContactsMiddlewareSearch");
//        returnedClasses.add("WalletContactsMiddlewareRecord");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet())
                    WalletContactsMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    public static LogLevel getLogLevelByClass(String className){
        try{
            String[] correctedClass = className.split(Pattern.quote("$"));
            return WalletContactsMiddlewarePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            return LogLevel.MODERATE_LOGGING;
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabase> developerDatabaseList = null;
        try {
            WalletContactsMiddlewareDeveloperDatabaseFactory dbFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            developerDatabaseList = dbFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return developerDatabaseList;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        List<DeveloperDatabaseTable> developerDatabaseTableList = null;
        try {
            WalletContactsMiddlewareDeveloperDatabaseFactory dbFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            developerDatabaseTableList = dbFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return developerDatabaseTableList;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = new ArrayList<>();
        try {
            WalletContactsMiddlewareDeveloperDatabaseFactory dbFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dbFactory.initializeDatabase();
            developerDatabaseTableRecordList = dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeWalletContactsMiddlewareDatabaseException we) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, we);
        } catch (Exception e) {
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return developerDatabaseTableRecordList;
    }
}
