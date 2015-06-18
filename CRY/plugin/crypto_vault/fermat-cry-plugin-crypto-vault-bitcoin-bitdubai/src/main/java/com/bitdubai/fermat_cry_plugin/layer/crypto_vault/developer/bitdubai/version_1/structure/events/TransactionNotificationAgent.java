package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

/**
 * Created by rodrigo on 2015.06.18..
 */
public class TransactionNotificationAgent implements DealsWithErrors, Service {

    /**
     * TransactionNotificationAgent variables
     */
    Database database;
    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * Constructor
     * @param database
     */
    public TransactionNotificationAgent (Database database){
        this.database = database;
    }


    /**
     * DealsWithErrors interface implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * Service interface implementation
     * @throws CantStartPluginException
     */
    @Override
    public void start() throws CantStartPluginException {

        serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {

        serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {

        serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }
}
