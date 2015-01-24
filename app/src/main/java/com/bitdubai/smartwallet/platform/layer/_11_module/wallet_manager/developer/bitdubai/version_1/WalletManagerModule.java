package com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._11_module.Module;
import com.bitdubai.smartwallet.platform.layer._2_event.*;
import com.bitdubai.smartwallet.platform.layer._3_os.FileSystem;
import com.bitdubai.smartwallet.platform.layer._3_os.DealWithFileSystem;

/**
 * Created by ciencias on 21.01.15.
 */
public class WalletManagerModule implements Module, DealWithEvents, DealWithFileSystem {

    EventManager eventManager;
    FileSystem fileSystem;

    @Override
    public void getReady() {

        /**
         * The only thing I can do for now is to wait for a User to log in.
         */

        EventListener eventListener = eventManager.getNewListener(Event.USER_LOGGED_IN);
        EventHandler eventHandler = new UserLoggedInEventHandler();
        eventListener.setEventHandler(eventHandler);
        eventManager.registerListener(eventListener);

    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }
}
