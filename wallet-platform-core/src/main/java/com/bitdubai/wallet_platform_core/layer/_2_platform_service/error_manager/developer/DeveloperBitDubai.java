package com.bitdubai.wallet_platform_core.layer._2_platform_service.error_manager.developer;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorDeveloper;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_core.layer._2_platform_service.error_manager.developer.version_1.PlatformErrorManager;

/**
 * Created by ciencias on 05.02.15.
 */
public class DeveloperBitDubai implements ErrorDeveloper {

    Service mEventManager;

    @Override
    public Service getErrorManager() {
        return mEventManager;
    }


    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        mEventManager = new PlatformErrorManager();

    }
}
