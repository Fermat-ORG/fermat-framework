package com.bitdubai.platform.layer._11_module.wallet_manager.developer.bitdubai.version_1;


import com.bitdubai.platform.layer._11_module.wallet_manager.*;
import com.bitdubai.platform.layer._1_definition.enums.DeviceDirectory;
import com.bitdubai.platform.layer._2_event.*;
import com.bitdubai.platform.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.platform.layer._2_event.manager.EventType;
import com.bitdubai.platform.layer._2_event.manager.EventHandler;
import com.bitdubai.platform.layer._2_event.manager.EventListener;
import com.bitdubai.platform.layer._3_os.*;
import com.bitdubai.platform.layer._11_module.ModuleService;
import com.bitdubai.platform.layer._1_definition.enums.ServiceStatus;

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

    public void loadUserWallets (UUID userId) throws CantLoadWalletsException {

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
                 * implies that this module is unstable and should be shut down or the com.bitdubai.platform should do something about
                 * it.
                 */
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();

                this.status = ServiceStatus.PAUSED;

                throw new CantLoadWalletsException();
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
                     * As it is a single wallet, I don't wont to halt everything for this situation. Instead this should
                     * raise an event to be handled by the com.bitdubai.platform itself, or some more code should be written to better
                     * diagnose the situation and try to get a work around.
                     *
                     * TODO: Raise an event to handle this problem. Probably the com.bitdubai.platform can do something like retrying
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

    @Override
    public void createDefaultWallets(UUID userId) throws CantCreateDefaultWalletsException {

        /**
         * By now I will create only a new wallet, In the future there will be more than one default wallets.
         */

        WalletManagerWallet wallet = new Wallet();

        try
        {
            wallet.createWallet(WalletType.DEFAULT);
        }
        catch (CantCreateWalletException cantCreateWalletException)
        {
            /**
             * Well, if it is not possible to create a wallet, then we have a problem that I can not handle...
             */
            System.err.println("CantCreateWalletException: " + cantCreateWalletException.getMessage());
            cantCreateWalletException.printStackTrace();

            throw new CantCreateDefaultWalletsException();
        }

    }

    /**
     * Module Interface implementation.
     */

    @Override
    public void run() {

        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.USER_CREATED);
        eventHandler = new UserCreatedEventHandler();
        ((UserCreatedEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.registerListener(eventListener);

        eventListener = eventManager.getNewListener(EventType.USER_LOGGED_IN);
        eventHandler = new UserLoggedInEventHandler();
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
