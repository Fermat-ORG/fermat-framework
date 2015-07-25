package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;

/**
 * Created by rodrigo on 7/24/15.
 */
public class WalletStoreNetworkServiceMonitoringAgent implements Agent, DealsWithEvents, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem {


    /**
     * DealsWithEvents interface member variable
     */
    EventManager eventManager;

    /**
     * DealsWithErrors interface member variable
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member varible
     */
    LogManager logManager;


    /**
     * DealsWithPlugindatabaseSystem interface member variable
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * constructor
     * @param eventManager
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     */
    public WalletStoreNetworkServiceMonitoringAgent(EventManager eventManager, ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem) {
        this.eventManager = eventManager;
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Agent interface implementation
     */

    @Override
    public void start() throws CantStartAgentException {

    }

    @Override
    public void stop() {

    }

    /**
     * DealsWithEvents interface implementation
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithError interface implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealsWithLogger interface implementation
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }


    /**
     * DealsWithPluginDatabaseSystem interface implementation
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
