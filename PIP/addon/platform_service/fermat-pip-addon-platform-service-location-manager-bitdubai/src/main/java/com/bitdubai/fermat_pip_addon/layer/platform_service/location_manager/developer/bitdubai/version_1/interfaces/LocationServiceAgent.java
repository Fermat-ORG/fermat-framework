package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.exception.LocationServiceException;

/**
 * Created by firuzzz on 5/12/15.
 */
public interface LocationServiceAgent {
    void start() throws LocationServiceException;

    void stop();
}
