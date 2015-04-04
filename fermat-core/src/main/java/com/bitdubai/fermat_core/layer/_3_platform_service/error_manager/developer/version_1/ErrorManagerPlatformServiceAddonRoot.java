package com.bitdubai.fermat_core.layer._3_platform_service.error_manager.developer.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

/**
 * Created by ciencias on 05.02.15.
 */
public class ErrorManagerPlatformServiceAddonRoot implements Addon, ErrorManager, Service {


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    
    /**
     * ErrorManager Interface implementation.
     */
    @Override
    public void reportUnexpectedPlatformException(PlatformComponents exceptionSource, UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity, Exception exception) {
        
    }

    @Override
    public void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception) {

    }


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
        return serviceStatus;
    }


}
