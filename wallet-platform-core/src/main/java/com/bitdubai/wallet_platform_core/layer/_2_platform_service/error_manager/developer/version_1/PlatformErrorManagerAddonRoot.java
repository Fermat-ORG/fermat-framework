package com.bitdubai.wallet_platform_core.layer._2_platform_service.error_manager.developer.version_1;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 05.02.15.
 */
public class PlatformErrorManagerAddonRoot implements Service, ErrorManager, Addon {


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


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
