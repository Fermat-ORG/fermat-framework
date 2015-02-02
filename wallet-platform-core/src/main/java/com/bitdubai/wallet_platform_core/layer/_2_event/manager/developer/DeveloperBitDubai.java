package com.bitdubai.wallet_platform_core.layer._2_event.manager.developer;

import com.bitdubai.wallet_platform_api.layer._2_event.EventDeveloper;
import com.bitdubai.wallet_platform_api.layer._2_event.EventManager;
import com.bitdubai.wallet_platform_core.layer._2_event.manager.developer.bitdubai.version_1.PlatformEventManager;


/**
 * Created by ciencias on 23.01.15.
 */
public class DeveloperBitDubai implements EventDeveloper {

    EventManager mEventManager;

    @Override
    public EventManager getEventManager() {
        return mEventManager;
    }


    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        mEventManager = new PlatformEventManager();

    }
}
