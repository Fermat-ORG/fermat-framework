package com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_manager.developer.bitdubai.version_1;



import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.*;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantCreateDefaultWalletsException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.*;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_manager.developer.bitdubai.version_1.event_handlers.NavigationStructureUpdatedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_manager.developer.bitdubai.version_1.event_handlers.UserCreatedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_manager.developer.bitdubai.version_1.event_handlers.UserLoggedInEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_manager.developer.bitdubai.version_1.event_handlers.WalletCreatedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_manager.developer.bitdubai.version_1.event_handlers.WalletResourcesInstalledEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerWallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 21.01.15.
 */
public class WalletManagerModulePluginRoot implements Service, WalletManager, DealsWithEvents,DealsWithErrors, DealsWithPluginFileSystem, Plugin {
    
    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus;
    List<EventListener> listenersAdded = new ArrayList<>();
    
    /**
     * WalletManager Interface member variables.
     */
    UUID userId;

    List<Wallet> userWallets;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;


    


    public WalletManagerModulePluginRoot(){
        userWallets = new ArrayList<>();
        this.serviceStatus = ServiceStatus.CREATED;
    }





    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {

        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.DEVICE_USER_CREATED);
        eventHandler = new UserCreatedEventHandler();
        ((UserCreatedEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.DEVICE_USER_LOGGED_IN);
        eventHandler = new UserLoggedInEventHandler();
        ((UserLoggedInEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.WALLET_RESOURCES_INSTALLED);
        eventHandler = new WalletResourcesInstalledEventHandler();
        ((WalletResourcesInstalledEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.NAVIGATION_STRUCTURE_UPDATED);
        eventHandler = new NavigationStructureUpdatedEventHandler();
        ((NavigationStructureUpdatedEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.WALLET_CREATED);
        eventHandler = new WalletCreatedEventHandler();
        ((WalletCreatedEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }
    
    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }
    
    
    
    public void enableWallet(){
        
        
    }
    
    
    private void finishedWalletInstallation(){
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.FINISHED_WALLET_INSTALLATION);
        platformEvent.setSource(EventSource.MODULE_WALLET_MANAGER_PLUGIN);
        eventManager.raiseEvent(platformEvent);
        
    }
    
    /**
     * WalletManager Interface implementation.
     */

    public List<Wallet> getUserWallets() {
        return userWallets;
    }

    public void loadUserWallets (UUID userId) throws CantLoadWalletsException {

        this.userId = userId;
/*
        try
        {
            
            PluginFile pluginFile = this.pluginFileSystem.getFile(
                    pluginId,
                    DeviceDirectory.LOCAL_WALLETS.getName(),
                    userId.toString(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            try
            {
                pluginFile.loadToMemory();
            }
            catch (CantLoadFileException cantLoadFileException)
            {
*/
                /**
                 * This shouldn't happen, but if it does it signals that something is quite bad. This kind of situation
                 * implies that this module is unstable and should be shut down or the com.bitdubai.platform should do something about
                 * it.
                 */
/*
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();

                this.serviceStatus = ServiceStatus.PAUSED;

                throw new CantLoadWalletsException();
            }
  
            String[] walletsId = pluginFile.getContent().split(";",-1);
            for ( String walletId : walletsId)
            {
                WalletManagerWallet wallet = new Wallet();
                
                ((DealsWithFileSystem) wallet).setPluginFileSystem(pluginFileSystem);
                ((DealsWithEvents) wallet).setEventManager(eventManager);
                ((DealsWithPluginIdentity) wallet).setPluginId(pluginId);
             
               
                try
                {
                    wallet.loadWallet(UUID.fromString(walletId));
                }
                catch (CantLoadWalletException cantLoadWalletException)
                {
*/
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
      /*
                    System.err.println("CantLoadWalletException: " + cantLoadWalletException.getMessage());
                    cantLoadWalletException.printStackTrace();
                }

                userWallets.add(wallet);
            }

        }
        catch (FileNotFoundException fileNotFoundException)
        {
    */
            /**
             * This is possible if the user is new and has no userWallets at all. I wont take any action now.
             */
  /*
            System.err.println("FileNotFoundException: " + fileNotFoundException.getMessage());
            fileNotFoundException.printStackTrace();
        }
*/
    }

    @Override
    public void createDefaultWallets(UUID userId) throws CantCreateDefaultWalletsException {

        /**
         * By now I will create only a new wallet, In the future there will be more than one default wallets.
         */

        Wallet wallet = new WalletManagerWallet();

        ((DealsWithPluginFileSystem) wallet).setPluginFileSystem(pluginFileSystem);
        ((DealsWithEvents) wallet).setEventManager(eventManager);
        ((DealsWithPluginIdentity) wallet).setPluginId(pluginId);
        
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
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}

