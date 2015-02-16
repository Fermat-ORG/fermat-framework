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
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.PluginFileSystem;
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



public class AppRuntimePluginRoot implements Service, MiddlewareEngine, AppRuntime, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();
    List<App> listApps = new ArrayList<>();
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


           /**
            * At this time the only thing I can do is a factory reset. Once there should be a possibility to add
            * functionality based on wallets downloaded by users this wont be an option.
            * * *
            */
           factoryReset();

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
     * AppRuntime Interface implementation.
     */

    @Override
    public App getApp(Apps app) {

        return null;
    }

    @Override
    public App getLastApp() {
        return listApps.get(0);
    }

    @Override
    public SubApps getLastSubApp() {
        return null;
    }

    @Override
    public Wallets getLastWallet() {
        return null;
    }

    @Override
    public Activities getLasActivity() {
        return null;
    }

    @Override
    public Fragments getLastFragment() {
        return null;
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
        RuntimeSubApp runtimeSubApp;
        RuntimeActivity runtimeActivity;
        RuntimeFragment runtimeFragment;
        RuntimeWallet runtimeWallet;
        RuntimeTitleBar runtimeTitleBar;
        RuntimeSideMenu runtimeSideMenu;
        RuntimeMainMenu runtimeMainMenu;
        RuntimeMenuItem runtimeMenuItem;
        RuntimeTabStrip runtimeTabStrip;

        runtimeApp = new RuntimeApp();

        runtimeApp.setType(Apps.CRYPTO_WALLET_PLATFORM);
       
        
        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_SHELL);
        runtimeApp.addSubApp(runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_SHELL_LOGIN);
        runtimeSubApp.addActivity(runtimeActivity);
        
        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHELL_LOGIN);
        runtimeActivity.addFragment(runtimeFragment);
        
        
        
        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_FACTORY);
        runtimeApp.addSubApp(runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        
        

        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_MANAGER);
        runtimeApp.addSubApp(runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_MANAGER_MAIN);
        runtimeActivity.addFragment(runtimeFragment);

        
        
        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_RUNTIME);
        runtimeApp.addSubApp(runtimeSubApp);

        runtimeWallet = new RuntimeWallet();
        runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);
        runtimeSubApp.addWallet(runtimeWallet);

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

        runtimeTabStrip = new RuntimeTabStrip();
        
        runtimeTabStrip.addTab("Profile");
        runtimeTabStrip.addTab("Desktop");
        runtimeTabStrip.addTab("Contacts");
        runtimeTabStrip.addTab("Community");

        runtimeActivity.setTabStrip(runtimeTabStrip);

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


        /**
         * End of Wallet Kids fragments.
         * */
        
        
        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_STORE);
        runtimeApp.addSubApp(runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN);
        runtimeActivity.addFragment(runtimeFragment);


        runtimeWallet = new RuntimeWallet();
        runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI);
        runtimeSubApp.addWallet(runtimeWallet);


        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);

        runtimeTitleBar = new RuntimeTitleBar();
        runtimeTitleBar.setLabel("Adults Wallet");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeSideMenu = new RuntimeSideMenu();

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Contacts");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Accounts");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Banks");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Coupons");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Discounts");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Vouchers");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Gift Cards");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Clones");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Childs");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Exit");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_MANAGER_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);
        
        runtimeActivity.setSideMenu(runtimeSideMenu);

        runtimeTabStrip = new RuntimeTabStrip();

        runtimeTabStrip.addTab("Home");
        runtimeTabStrip.addTab("Balance");
        runtimeTabStrip.addTab("Send");
        runtimeTabStrip.addTab("Receive");
        runtimeTabStrip.addTab("Shops");
        runtimeTabStrip.addTab("Refill");
        runtimeTabStrip.addTab("Discounts");

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS);
        runtimeActivity.addFragment(runtimeFragment);
        /**
         * End of Wallet Adults tabs.
         */
        
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SHOPS);
        runtimeSubApp.addActivity(runtimeActivity);
        
        runtimeTabStrip = new RuntimeTabStrip();

        runtimeTabStrip.addTab("Shop");
        runtimeTabStrip.addTab("Products");
        runtimeTabStrip.addTab("Reviews");
        runtimeTabStrip.addTab("Chat");
        runtimeTabStrip.addTab("History");
        runtimeTabStrip.addTab("Map");

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP);
        runtimeActivity.addFragment(runtimeFragment);
        /**
         * End of SHOPS tabs.
         */
        
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REFFILS);
        runtimeSubApp.addActivity(runtimeActivity);
//-----------------------------------------------------------------------------------
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
        runtimeSubApp.addActivity(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
        runtimeActivity.addFragment(runtimeFragment);
//------------------------------------------------------------------------------------
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
        runtimeSubApp.addActivity(runtimeActivity);
        
        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
        runtimeActivity.addFragment(runtimeFragment);        
//-----------------------------------------------------------------------------------
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS);
        runtimeSubApp.addActivity(runtimeActivity);




        runtimeTabStrip = new RuntimeTabStrip();

        runtimeTabStrip.addTab("Debits");
        runtimeTabStrip.addTab("Credits");
        runtimeTabStrip.addTab("All");


        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
        runtimeActivity.addFragment(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
        runtimeActivity.addFragment(runtimeFragment);

        listApps.add(runtimeApp);
        /**
         * End of Wallet Accounts tabs.
         */

        
    }

}
