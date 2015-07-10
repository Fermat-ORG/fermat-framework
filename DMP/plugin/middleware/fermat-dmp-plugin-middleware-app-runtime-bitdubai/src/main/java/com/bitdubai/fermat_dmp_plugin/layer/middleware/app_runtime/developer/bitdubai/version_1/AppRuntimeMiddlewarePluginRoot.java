package com.bitdubai.fermat_dmp_plugin.layer.middleware.app_runtime.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/14/15.
 */

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.*;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Apps;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.app_runtime.developer.bitdubai.version_1.event_handlers.WalletResourcesInstalledEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.app_runtime.developer.bitdubai.version_1.exceptions.CantFactoryReset;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.app_runtime.developer.bitdubai.version_1.structure.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The App Runtime is the module in charge of the UI navigation structure. A user is always at a certain point in this 
 * structure.
 */


/**
 * A Navigation stack is maintained by this plugin to allow the user to go back all the stack down to the root if necessary.
 */



public class AppRuntimeMiddlewarePluginRoot implements Service, AppRuntimeManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * AppRuntimeManager Interface member variables.
     */

    List<EventListener> listenersAdded = new ArrayList<>();
    Map<Apps, App> listApps = new HashMap<Apps, App>();
    Map<SubApps, SubApp> listSubApp = new HashMap<SubApps, SubApp>();
    Map<Activities, Activity> listActivities = new HashMap<Activities, Activity>();
    Map<Fragments, Fragment> listFragments = new HashMap<Fragments, Fragment>();

    Apps lastApp;
    SubApps lastSubapp;
    Activities  lastActivity;
    Fragments lastFragment;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;



    public void addToNavigationStructure(/*String NavigationStructure, NicheWalletType*/) {

        /*
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.NAVIGATION_STRUCTURE_UPDATED);
        ((NavigationStructureUpdatedEvent) platformEvent).----------(this.-----);
        eventManager.raiseEvent(platformEvent);
        */
    }
    
    /*
    PlatformEvent platformEvent = eventManager.getNewEvent(EventType.NAVIGATION_STRUCTURE_UPDATED);
    ((NavigationStructureUpdatedEvent) platformEvent).--------(this.-------);
    eventManager.raiseEvent(platformEvent);
*/



    @Override
    public void start() throws CantStartPluginException{
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;
        eventListener = eventManager.getNewListener(EventType.WALLET_RESOURCES_INSTALLED);
        eventHandler = new WalletResourcesInstalledEventHandler();
        ((WalletResourcesInstalledEventHandler) eventHandler).setAppRuntimeManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        /**
         * At this time the only thing I can do is a factory reset. Once there should be a possibility to add
         * functionality based on wallets downloaded by users this wont be an option.
         * * *
         */

        try
        {
            factoryReset();
        }
        catch(CantFactoryReset ex)
        {
            String message = CantStartPluginException.DEFAULT_MESSAGE;
            FermatException cause = ex;
            String context = "App Runtime Start";

            String possibleReason = "Some null definition";
            throw new CantStartPluginException(message, cause, context, possibleReason);
        }



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
        Iterator<Map.Entry<Apps, App>> eapp = this.listApps.entrySet().iterator();

        while (eapp.hasNext()) {
            Map.Entry<Apps, App> appEntry = eapp.next();
            RuntimeApp app = (RuntimeApp) appEntry.getValue();
            if(app.getType().name().equals(lastApp.name())){
                return app;
            }


        }
        return null;

    }

    @Override
    public SubApp getLastSubApp() {
        Iterator<Map.Entry<SubApps, SubApp>> esubapp = this.listSubApp.entrySet().iterator();

        while (esubapp.hasNext()) {
            Map.Entry<SubApps, SubApp> subappEntry = esubapp.next();
            RuntimeSubApp subapp = (RuntimeSubApp) subappEntry.getValue();
            if(subapp.getType().name().equals(this.lastSubapp.name())){
                return subapp;
            }


        }

        return null;
    }



    @Override
    public Activity getLasActivity() {
        Iterator<Map.Entry<Activities, Activity>> eactivity = this.listActivities.entrySet().iterator();

        while (eactivity.hasNext()) {
            Map.Entry<Activities, Activity> activityEntry = eactivity.next();
            RuntimeActivity activity = (RuntimeActivity) activityEntry.getValue();
            if(activity.getType().name().equals(this.lastActivity.name())){
                return activity;
            }
        }

        return null;
    }

    @Override
    public Fragment getLastFragment() {
        return listFragments.get(lastFragment);
    }

    @Override
    public Activity getActivity(Activities app) {
        Iterator<Map.Entry<Activities, Activity>> eactivity = this.listActivities.entrySet().iterator();

        while (eactivity.hasNext()) {
            Map.Entry<Activities, Activity> activityEntry = eactivity.next();
            RuntimeActivity activity = (RuntimeActivity) activityEntry.getValue();
            if(activity.getType().name().equals(app.name())){
                lastActivity = activity.getType();
                return activity;
            }


        }
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

        this.errorManager = errorManager;

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
    private void factoryReset() throws CantFactoryReset {

        try
        {
        RuntimeApp runtimeApp;
        RuntimeSubApp runtimeSubApp;
        RuntimeActivity runtimeActivity;
        RuntimeFragment runtimeFragment;

        RuntimeTitleBar runtimeTitleBar;
        RuntimeSideMenu runtimeSideMenu;
        RuntimeMainMenu runtimeMainMenu;
        RuntimeMenuItem runtimeMenuItem;
        RuntimeTabStrip runtimeTabStrip;

        RuntimeTab runtimeTab;
        runtimeApp = new RuntimeApp();

        runtimeApp.setType(Apps.CRYPTO_WALLET_PLATFORM);


        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_SHELL);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.put(SubApps.CWP_SHELL, runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_SHELL_LOGIN);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_SHELL_LOGIN, runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHELL_LOGIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_SHELL_LOGIN, runtimeFragment);


        //wallet factory app
        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_FACTORY);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.put(SubApps.CWP_WALLET_FACTORY, runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_MAIN);
            runtimeActivity.setColor("#b46a54");
            runtimeActivity.setStatusBarColor("#b46a54");

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Wallet Factory");
             runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTabStrip.setTabsColor("#d07b62");
            runtimeTabStrip.setTabsTextColor("#FFFFFF");
            runtimeTabStrip.setTabsIndicateColor("#b46a54");

            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("Wallet Factory");
            runtimeTab.setFragment(Fragments.CWP_WALLET_FACTORY_MAIN);

            runtimeTabStrip.addTab(runtimeTab);

            runtimeActivity.setTabStrip(runtimeTabStrip);


            runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_FACTORY_MAIN, runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_FACTORY_MAIN,runtimeFragment);

            /**End Wallet Publisher*/

            //wallet Publisher app
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CWP_WALLET_PUBLISHER);
            runtimeApp.addSubApp(runtimeSubApp);
            listSubApp.put(SubApps.CWP_WALLET_PUBLISHER, runtimeSubApp);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_PUBLISHER_MAIN);
            runtimeActivity.setColor("#b46a54");
            runtimeActivity.setStatusBarColor("#b46a54");

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Wallet Publisher");

            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTabStrip.setTabsColor("#d07b62");
            runtimeTabStrip.setTabsTextColor("#FFFFFF");
            runtimeTabStrip.setTabsIndicateColor("#b46a54");

            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("Wallet Publisher");
            runtimeTab.setFragment(Fragments.CWP_WALLET_PUBLISHER_MAIN);

            runtimeTabStrip.addTab(runtimeTab);

            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeSubApp.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_PUBLISHER_MAIN, runtimeActivity);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_PUBLISHER_MAIN);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_PUBLISHER_MAIN,runtimeFragment);

            /**End Wallet Publisher*/

        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_MANAGER);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.put(SubApps.CWP_WALLET_MANAGER,runtimeSubApp);
        lastSubapp = SubApps.CWP_WALLET_MANAGER;

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_MANAGER_MAIN, runtimeActivity);

        lastActivity = Activities.CWP_WALLET_MANAGER_MAIN;


       /* runtimeSideMenu = new RuntimeSideMenu();


        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Personal Wallets");
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Shops");
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Commercial wallets");
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Factory Projects");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_FACTORY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Published Wallets");
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Wallet Store");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_STORE_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);


        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Exit");
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);*/

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_MANAGER_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_MANAGER_MAIN, runtimeFragment);

        //Desktop page My Shop
      //  runtimeFragment = new RuntimeFragment();
       // runtimeFragment.setType(Fragments.CWP_WALLET_MANAGER_SHOP);
       // runtimeActivity.addFragment(runtimeFragment);
      //  listFragments.put(Fragments.CWP_WALLET_MANAGER_SHOP,runtimeFragment);

        //Desktop page Developer sub App
        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SUB_APP_DEVELOPER);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_SUB_APP_DEVELOPER,runtimeFragment);


        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_RUNTIME);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.put(SubApps.CWP_WALLET_RUNTIME,runtimeSubApp);


        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_STORE);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.put(SubApps.CWP_WALLET_STORE,runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_STORE_MAIN, runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_STORE_MAIN,runtimeFragment);


        /**
         * Definition of Shop Manager
         */

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SHOPS);
        runtimeActivity.setColor("#76dc4a");
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_SHOPS, runtimeActivity);



        runtimeTitleBar = new RuntimeTitleBar();
        runtimeTitleBar.setLabel("My Shop");

        runtimeActivity.setTitleBar(runtimeTitleBar);


        runtimeTabStrip = new RuntimeTabStrip();
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Shop");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Products");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Reviews");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Chat");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("History");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Map");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP);
        runtimeTabStrip.addTab(runtimeTab);


        runtimeTabStrip.setDividerColor(0xFFFFFFFF);
        runtimeTabStrip.setIndicatorColor(0xFFFFFFFF);
        runtimeTabStrip.setIndicatorHeight(9);
        runtimeTabStrip.setBackgroundColor(0xFF76dc4a);
        runtimeTabStrip.setTextColor(0xFFFFFFFF);
        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP,runtimeFragment);
        /**
         * End of SHOPS tabs.
         */





        /*-- wallet store --*/
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_STORE_MAIN);

            runtimeActivity.setColor("#b46a54");
            runtimeActivity.setStatusBarColor("#b46a54");

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Wallet Store");

            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTabStrip.setTabsColor("#d07b62");
            runtimeTabStrip.setTabsTextColor("#FFFFFF");
            runtimeTabStrip.setTabsIndicateColor("#b46a54");
            runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_RUNTIME_STORE_MAIN, runtimeActivity);




        //mati
        RuntimeSearchView runtimeSearchView= new RuntimeSearchView();
        runtimeSearchView.setLabel("Search");
        runtimeTitleBar.setRuntimeSearchView(runtimeSearchView);

        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeTabStrip = new RuntimeTabStrip();
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("All");
        runtimeTab.setFragment(Fragments.CWP_SHOP_MANAGER_MAIN);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Free");
        runtimeTab.setFragment(Fragments.CWP_SHOP_MANAGER_FREE);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Paid");
        runtimeTab.setFragment(Fragments.CWP_SHOP_MANAGER_PAID);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Accepted nearby");
        runtimeTab.setFragment(Fragments.CWP_SHOP_MANAGER_ACCEPTED_NEARBY);
        runtimeTabStrip.addTab(runtimeTab);



        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHOP_MANAGER_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_SHOP_MANAGER_MAIN,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHOP_MANAGER_FREE);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_SHOP_MANAGER_FREE,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHOP_MANAGER_PAID);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_SHOP_MANAGER_PAID,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHOP_MANAGER_ACCEPTED_NEARBY);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_SHOP_MANAGER_ACCEPTED_NEARBY,runtimeFragment);


/**
 * End of Wallet Store
 */

        //Account Details

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNT_DETAIL);
        runtimeActivity.setColor("#F0E173");
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNT_DETAIL, runtimeActivity);



        runtimeTitleBar = new RuntimeTitleBar();
        runtimeTitleBar.setLabel("Account details");

        runtimeActivity.setTitleBar(runtimeTitleBar);


        runtimeTabStrip = new RuntimeTabStrip();
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Debits");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Credits");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("All");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL,runtimeFragment);



        /*------------------------------*/

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REFFILS);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_REFFILS, runtimeActivity);
//-----------------------------------------------------------------------------------
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED, runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED,runtimeFragment);
//------------------------------------------------------------------------------------
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND, runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND,runtimeFragment);
//-----------------------------------------------------------------------------------
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.put(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS, runtimeActivity);

        runtimeTitleBar = new RuntimeTitleBar();
        runtimeTitleBar.setLabel("Account details");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeTabStrip = new RuntimeTabStrip();
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Debits");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Credits");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("All");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
        runtimeTabStrip.addTab(runtimeTab);


        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS,runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL,runtimeFragment);
        listApps.put(Apps.CRYPTO_WALLET_PLATFORM,runtimeApp);
        lastApp = Apps.CRYPTO_WALLET_PLATFORM;
        /**
         * End of Wallet Accounts tabs.
         */
        }
        catch(Exception e)
        {
            String message = CantFactoryReset.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Error on method Factory Reset, setting the structure of the apps";
            String possibleReason = "some null definition";
            throw new CantFactoryReset(message, cause, context, possibleReason);

        }

    }

}
