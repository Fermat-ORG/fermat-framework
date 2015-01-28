package com.bitdubai.wallet_platform_core.layer._2_event.manager;

import com.bitdubai.wallet_platform_api.layer._2_event.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._2_event.EventManager;
import com.bitdubai.wallet_platform_api.layer._2_event.EventSubsystem;
import com.bitdubai.wallet_platform_core.layer._2_event.manager.developer.DeveloperBitDubai;

/**
 * Created by ciencias on 23.01.15.
 */
public class ManagerSubsystem implements EventSubsystem {

    EventManager mEventManager;

    @Override
    public EventManager getEventManager() {
        return mEventManager;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mEventManager = developerBitDubai.getEventManager();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
