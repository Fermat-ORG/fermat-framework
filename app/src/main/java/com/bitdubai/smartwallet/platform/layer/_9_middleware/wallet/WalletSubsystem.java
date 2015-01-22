package com.bitdubai.smartwallet.platform.layer._9_middleware.wallet;


import com.bitdubai.smartwallet.platform.layer._9_middleware.CantStartSubsystemException;
import com.bitdubai.smartwallet.platform.layer._9_middleware.MiddlewareEngine;
import com.bitdubai.smartwallet.platform.layer._9_middleware.MiddlewareSubsystem;
import com.bitdubai.smartwallet.platform.layer._9_middleware.wallet.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 20.01.15.
 */
public class WalletSubsystem implements MiddlewareSubsystem {

    MiddlewareEngine mMiddlewareEngine;

    @Override
    public MiddlewareEngine getMiddlewareEngine() {
        return mMiddlewareEngine;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mMiddlewareEngine = developerBitDubai.getMiddlewareEngine();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
