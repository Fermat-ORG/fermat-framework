package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingAddressExchangeRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.DealsWithCryptoAddressesNetworkService;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.AddressExchangeRequest;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.event_handlers.CryptoAddressDeniedEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.event_handlers.CryptoAddressReceivedEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressDeniedEventException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressReceivedEventException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsMiddlewareDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

public class WalletContactsMiddlewarePluginRoot extends AbstractPlugin
        implements DatabaseManagerForDevelopers          ,
                   DealsWithCryptoAddressesNetworkService,
                   DealsWithErrors                       ,
                   DealsWithEvents                       ,
                   DealsWithPluginDatabaseSystem         ,
                   DealsWithLogger                       ,
                   LogManagerForDevelopers               ,
                   WalletContactsManager                 {

    public WalletContactsMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * DealsWithCryptoAddressesNetworkService Interface member variables.
     */
    private CryptoAddressesManager cryptoAddressesManager;

    /**
     * DealWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Plugin Interface member variables.
     */
    private UUID pluginId;

    /**
     * DealsWithLogger interface member variable
     */
    private LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public static final Actors actorType = Actors.INTRA_USER;


    /**
     * WalletContactsManager Interface implementation.
     */
    public WalletContactsRegistry getWalletContactsRegistry() throws CantGetWalletContactRegistryException {

        try {
            WalletContactsMiddlewareRegistry walletContactsRegistry = new WalletContactsMiddlewareRegistry(null, errorManager, logManager, pluginDatabaseSystem, pluginId);

            walletContactsRegistry.initialize();

            return walletContactsRegistry;
        } catch (CantInitializeWalletContactsMiddlewareDatabaseException exception) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
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
                errorManager          ,
                logManager            ,
                pluginDatabaseSystem  ,
                pluginId
        );

        try {

            walletContactsRegistry.initialize();
        } catch (CantInitializeWalletContactsMiddlewareDatabaseException e) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e);
        }
        // execute pending address exchange requests
        executePendingAddressExchangeRequests(walletContactsRegistry);

        FermatEventListener cryptoAddressReceivedEventListener = eventManager.getNewListener(EventType.CRYPTO_ADDRESS_RECEIVED);
        cryptoAddressReceivedEventListener.setEventHandler(new CryptoAddressReceivedEventHandler(walletContactsRegistry, this));
        eventManager.addListener(cryptoAddressReceivedEventListener);
        listenersAdded.add(cryptoAddressReceivedEventListener);

        FermatEventListener cryptoAddressDeniedEventListener = eventManager.getNewListener(EventType.CRYPTO_ADDRESS_DENIED);
        cryptoAddressDeniedEventListener.setEventHandler(new CryptoAddressDeniedEventHandler(walletContactsRegistry, this));
        eventManager.addListener(cryptoAddressDeniedEventListener);
        listenersAdded.add(cryptoAddressDeniedEventListener);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    private void executePendingAddressExchangeRequests(WalletContactsMiddlewareRegistry walletContactsRegistry) {
        try {
            List<AddressExchangeRequest> addressExchangeRequestRespondedList = cryptoAddressesManager.listPendingRequests(
                    actorType
            );

            for (AddressExchangeRequest request : addressExchangeRequestRespondedList) {

                if (request.getAction().equals(RequestAction.ACCEPT))
                    walletContactsRegistry.handleCryptoAddressReceivedEvent(request);

                if (request.getAction().equals(RequestAction.DENY))
                    walletContactsRegistry.handleCryptoAddressDeniedEvent(request);

            }

        } catch(CantListPendingAddressExchangeRequestsException |
                CantHandleCryptoAddressDeniedEventException     |
                CantHandleCryptoAddressReceivedEventException   e) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    /**
     * Plugin interface implementation
     * @param pluginId identifying this plugin
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * DealsWithLogger interface implementations
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();

        returnedClasses.add("WalletContactsMiddlewarePluginRoot");
        returnedClasses.add("WalletContactsMiddlewareRegistry");
        returnedClasses.add("WalletContactsMiddlewareSearch");
        returnedClasses.add("WalletContactsMiddlewareRecord");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet())
                    WalletContactsMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, we);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return developerDatabaseTableRecordList;
    }

    /**
     * DealWithCryptoAddressesNetworkService Interface implementation.
     */
    @Override
    public void setCryptoAddressesManager(CryptoAddressesManager cryptoAddressesManager) {
        this.cryptoAddressesManager = cryptoAddressesManager;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

}
