package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_statistics.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.exceptions.CantGetWalletStatisticsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.interfaces.WalletStatistics;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.interfaces.WalletStatisticsManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;

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
public class WalletStatisticsNetworkServicePluginRoot implements DealsWithErrors, NetworkService, Plugin, Service, WalletStatisticsManager {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

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
        return null;
    }
}
