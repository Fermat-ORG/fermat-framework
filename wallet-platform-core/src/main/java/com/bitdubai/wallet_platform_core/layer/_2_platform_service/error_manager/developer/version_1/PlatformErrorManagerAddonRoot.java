package com.bitdubai.wallet_platform_core.layer._2_platform_service.error_manager.developer.version_1;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;

/**
 * Created by ciencias on 05.02.15.
 */
public class PlatformErrorManagerAddonRoot implements Service, ErrorManager, Addon {

    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public ServiceStatus getStatus() {
        return null;
    }
}
