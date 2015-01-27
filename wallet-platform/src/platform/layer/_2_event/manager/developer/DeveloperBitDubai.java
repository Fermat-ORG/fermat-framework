package platform.layer._2_event.manager.developer;

import platform.layer._2_event.EventDeveloper;
import platform.layer._2_event.EventManager;
import platform.layer._2_event.manager.developer.bitdubai.version_1.PlatformEventManager;


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
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mEventManager = new PlatformEventManager();

    }
}
