package com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._2_event.EventHandler;
import com.bitdubai.smartwallet.platform.layer._2_event.PlatformEvent;
import com.bitdubai.smartwallet.platform.layer._2_event.UserLoggedInEvent;

import java.util.UUID;

/**
 * Created by ciencias on 24.01.15.
 */
public class UserLoggedInEventHandler implements EventHandler {
    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

        UUID userId = ((UserLoggedInEvent) platformEvent).getUserId();
    }
}
