package com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.developer.bitdubai.version_1;


import com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.WalletManager;
import com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.WalletManagerWallet;
import com.bitdubai.smartwallet.platform.layer._1_definition.enums.DeviceDirectory;
import com.bitdubai.smartwallet.platform.layer._2_event.*;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventType;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventHandler;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventListener;
import com.bitdubai.smartwallet.platform.layer._3_os.*;
import com.bitdubai.smartwallet.platform.layer._11_module.ModuleService;
import com.bitdubai.smartwallet.platform.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.CantLoadWalletException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 21.01.15.
 */
public class WalletManagerModule implements ModuleService, WalletManager, DealsWithEvents, DealsWithFileSystem {

    /**
     * WalletManager Interface member variables.
     */

    ServiceStatus status;
    UUID userId;

    List<WalletManagerWallet> userWallets;

    /**
     * UsesFileSystem Interface member variables.
     */
    FileSystem fileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    public WalletManagerModule (){
        userWallets = new ArrayList<>();
        this.status = ServiceStatus.CREATED;
    }

    /**
     * WalletManager Interface implementation.
     */

    public List<WalletManagerWallet> getUserWallets() {
        return userWallets;
    }

    public void loadUserWallets (UUID userId) throws CantLoadWalletException {

        this.userId = userId;

        try
        {
            PlatformFile platformFile = this.fileSystem.getFile(
                    DeviceDirectory.LOCAL_WALLETS.getName(),
                    userId.toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

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

                this.status = ServiceStatus.PAUSED;

                throw new CantLoadWalletException();
            }

            String[] walletsId = platformFile.getContent().split(";",-1);
            for ( String walletId : walletsId)
            {
                WalletManagerWallet wallet = new Wallet();
                try
                {
                    wallet.loadWallet(UUID.fromString(walletId));
                }
                catch (CantLoadWalletException cantLoadWalletException)
                {
                    /**
                     * Initially we would assume that the wallet failed to load to circumstantial stuff. We assume that
                     * we can retry to put load it when the user tries to open it.
                     *
                     * TODO: Raise an event to handle this problem. Probably the platform can do something like retrying
                     * by itself or notifying the developers team.
                     */
                    System.err.println("CantLoadWalletException: " + cantLoadWalletException.getMessage());
                    cantLoadWalletException.printStackTrace();
                }

                userWallets.add(wallet);
            }

        }
        catch (FileNotFoundException fileNotFoundException)
        {
            /**
             * This is possible if the user is new and has no userWallets at all. I wont take any action now.
             */
            System.err.println("FileNotFoundException: " + fileNotFoundException.getMessage());
            fileNotFoundException.printStackTrace();
        }

    }

    /**
     * Module Interface implementation.
     */

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

        this.status = ServiceStatus.RUNNING;
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
    }

    @Override
    public ServiceStatus getStatus() {
        return this.status;
    }

    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

}
