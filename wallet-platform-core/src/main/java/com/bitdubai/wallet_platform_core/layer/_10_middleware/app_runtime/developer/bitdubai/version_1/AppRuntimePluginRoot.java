package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/14/15.
 */

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._10_middleware.MiddlewareEngine;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.*;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.DealsWithPluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.PluginFileSystem;
import com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The App Runtime is the module in charge of the UI navigation structure. A user is always at a certain point in this 
 * structure.
 */


/**
 * A Navigation stack is maintained by this plugin to allow the user to go back all the stack down to the root if necessary.
 */



public class AppRuntimePluginRoot implements Service, MiddlewareEngine, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

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

       @Override
       public void start(){
           /**
            * I will initialize the handling of com.bitdubai.platform events.
            */

           EventListener eventListener;
           EventHandler eventHandler;

           this.serviceStatus = ServiceStatus.STARTED;
           
       }
    @Override
    public void pause(){
        
        this.serviceStatus = ServiceStatus.PAUSED;
        
    }

    @Override
    public void resume(){
        
        this.serviceStatus = ServiceStatus.STARTED;
        
    }
    
    @Override
    public void stop(){

        /**
         * I will remove all the listeners registered with the event manager. 
         */
        
        for (EventListener eventListener : listenersAdded){
            eventManager.removeListener(eventListener);
        }
        
        listenersAdded.clear();
        
        this.serviceStatus = ServiceStatus.STOPPED;
        
    }

    @Override
    public ServiceStatus getStatus() { 
        return this.serviceStatus;
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
     * DealWithErrors Interface implementation. 
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
    
    
    /**
     * The first time this plugins runs, it will setup the initial structure for the App, subApp and so on through the local
     * interfaces of the classes involved, 
     */
     private void firstRunCheck() {

         /**
          * First I check weather this a structure already created, if not I create the "Factory" structure.
          */
         
         
     }


    /**
     * Here is where I actually generate the factory structure of the APP. This method is also useful to reset to the 
     * factory structure.
     */
    private void factoryReset() {

        RuntimeApp runtimeApp;
        RuntimeSubApp subApp;
        RuntimeActivity runtimeActivity;
        RuntimeFragment runtimeFragment;
        RuntimeWallet runtimeWallet;
        RuntimeTitleBar runtimeTitleBar;
        RuntimeSideMenu runtimeSideMenu;
        RuntimeMainMenu runtimeMainMenu;
        RuntimeMenuItem runtimeMenuItem;

        runtimeApp = new RuntimeApp();

        runtimeApp.setType(Apps.CRYPTO_WALLET_PLATFORM);
       
        
        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_SHELL);
        runtimeApp.addSubApp(subApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_SHELL_LOGIN);
        subApp.addActivity(runtimeActivity);
        
        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHELL_LOGIN);
        runtimeActivity.addFragment(runtimeFragment);
        
        
        
        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_WALLET_FACTORY);
        runtimeApp.addSubApp(subApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_MAIN);
        subApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        
        

        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_WALLET_MANAGER);
        runtimeApp.addSubApp(subApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
        subApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_MANAGER_MAIN);
        runtimeActivity.addFragment(runtimeFragment);

        
        
        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_WALLET_RUNTIME);
        runtimeApp.addSubApp(subApp);

        runtimeWallet = new RuntimeWallet();
        runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);
        subApp.addWallet(runtimeWallet);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_MAIN);
        runtimeWallet.addActivity(runtimeActivity);

        runtimeTitleBar = new RuntimeTitleBar();
        runtimeTitleBar.setLabel("Kids Wallet");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeSideMenu = new RuntimeSideMenu();
        
        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Menu item 1");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_MAIN); // Solo es un ej.
        runtimeSideMenu.addMenuItem(runtimeMenuItem);
        
        runtimeActivity.setSideMenu(runtimeSideMenu);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY);
        runtimeActivity.addFragment(runtimeFragment);
        
        
        
        subApp = new RuntimeSubApp();
        subApp.setType(SubApps.CWP_WALLET_STORE);
        runtimeApp.addSubApp(subApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN);
        subApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        
        
        
    }
    
}
