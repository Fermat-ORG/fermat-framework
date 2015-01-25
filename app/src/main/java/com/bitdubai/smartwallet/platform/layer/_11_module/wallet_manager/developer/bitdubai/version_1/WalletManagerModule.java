package com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._11_module.Module;
import com.bitdubai.smartwallet.platform.layer._11_module.ModuleStatus;
import com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.CantLoadUserWalletsException;
import com.bitdubai.smartwallet.platform.layer._2_event.*;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.DealWithEvents;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventType;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventHandler;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventListener;
import com.bitdubai.smartwallet.platform.layer._3_os.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 21.01.15.
 */
public class WalletManagerModule implements Module, DealWithEvents, DealWithFileSystem {

    ModuleStatus status;
    EventManager eventManager;
    FileSystem fileSystem;
    UUID userId;

    List<Wallet> wallets;

    public List<Wallet> getWallets() {
        return wallets;
    }

    public WalletManagerModule (){
        wallets = new ArrayList<>();
        this.status = ModuleStatus.CREATED;
    }


    public void loadUserWallets (UUID userId) throws CantLoadUserWalletsException{

        this.userId = userId;

        try
        {
            PlatformFile platformFile = this.fileSystem.getFile(userId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            try
            {
                platformFile.loadToMemory();
            }
            catch (CantLoadFileException cantLoadFileException)
            {
                /**
                 * This shouldn't happen, but if it does it signals that something is quite bad. This kind of situation
                 * implies that this module is unstable and should be shut down or the platform should do something about
                 * it.
                 */
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();

                this.status = ModuleStatus.PAUSED;

                throw new CantLoadUserWalletsException();
            }

            /* TODO: read the content of the File and create de wallets.*/

        }
        catch (FileNotFoundException fileNotFoundException)
        {
            /**
             * This is possible if the user is new and has no wallets at all. I wont take any action now.
             */
            System.err.println("FileNotFoundException: " + fileNotFoundException.getMessage());
            fileNotFoundException.printStackTrace();
        }

    }

    @Override
    public void run() {

        /**
         * The only thing I can do for now is to wait for a User to log in.
         */

        EventListener eventListener = eventManager.getNewListener(EventType.USER_LOGGED_IN);
        EventHandler eventHandler = new UserLoggedInEventHandler();
        ((UserLoggedInEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.registerListener(eventListener);

        this.status = ModuleStatus.RUNNING;
    }

    @Override
    public ModuleStatus getStatus() {
        return this.status;
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
