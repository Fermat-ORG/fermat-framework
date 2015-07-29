package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.DealsWithWalletStoreMiddleware;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DealsWithWalletStoreNetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author ciencias
 * Created by ciencias on 30.12.14.
 */

/**
 * This plugin manages the information about all wallets published only at the level needed for showing it at the wallet
 * store sub app.
 * * *
 */

public class WalletStoreModulePluginRoot implements DealsWithErrors, DealsWithEvents, DealsWithLogger, DealsWithWalletStoreNetworkService, DealsWithWalletStoreMiddleware, LogManagerForDevelopers,Plugin, Service{

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus;
    List<EventListener> listenersAdded = new ArrayList<>();


    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithWalletStoreNetworkService interface member variable
     */
    WalletStoreManager walletStoreManagerNetworkService;

    /**
     * DealsWithWalletStoreMiddleware interface member variable
     */
    com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager walletStoreManagerMiddleware;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;


    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {
        this.serviceStatus = ServiceStatus.STARTED;
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
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * DealWithEvents Interface implementation.
     */
    
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     *DealWithErrors Interface implementation. 
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }


    /**
     * DealsWithWalletStoreNetworkService interface implementation
     */
    @Override
    public void setWalletStoreManager(WalletStoreManager walletStoreManager) {
        this.walletStoreManagerNetworkService = walletStoreManager;
    }

    /**
     * DealsWithWalletStoreMiddleware interface implementation
     */
    @Override
    public void setWalletStoreManager(com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager walletStoreManager) {
        this.walletStoreManagerMiddleware = walletStoreManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.fermat_dmp_plugin.layer.module.wallet_factory.developer.bitdubai.version_1.WalletFactoryModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreCatalog");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreDatabaseFactory");

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
            if (WalletStoreModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletStoreModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletStoreModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletStoreModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

}
