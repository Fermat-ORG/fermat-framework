package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_statistics.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.exceptions.CantGetWalletStatisticsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.interfaces.WalletStatistics;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.interfaces.WalletStatisticsManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletStatisticsNetworkServicePluginRoot implements DealsWithErrors, DealsWithLogger,LogManagerForDevelopers,NetworkService, Plugin, Service, WalletStatisticsManager {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;



    /**
     * Service Interface implementation.
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
     * NetworkService Interface implementation.
     */
    
    @Override
    public UUID getId() {
        return pluginId;
    }

    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * Plugin methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public WalletStatistics getWalletStatistics(UUID walletCatalogId) throws CantGetWalletStatisticsException{
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }


    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_statistics.developer.bitdubai.version_1.WalletStatisticsNetworkServicePluginRoot");
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
            if (WalletStatisticsNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletStatisticsNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletStatisticsNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletStatisticsNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

}
