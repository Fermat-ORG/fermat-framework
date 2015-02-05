package com.bitdubai.wallet_platform_core.layer._2_platform_service.error_manager;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.PlatformServiceSubsystem;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_core.layer._2_platform_service.error_manager.developer.DeveloperBitDubai;

/**
 * Created by ciencias on 05.02.15.
 */
public class ErrorManagerSubsystem implements PlatformServiceSubsystem {

    Service mErrorManager;

    @Override
    public Service getService() {
        return mErrorManager;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mErrorManager = developerBitDubai.getErrorManager();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
