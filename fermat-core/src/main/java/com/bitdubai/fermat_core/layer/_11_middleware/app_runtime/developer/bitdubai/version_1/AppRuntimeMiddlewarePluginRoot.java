package com.bitdubai.fermat_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/14/15.
 */

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._11_middleware.Middleware;
import com.bitdubai.fermat_api.layer._11_middleware.app_runtime.*;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._3_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._3_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.event_handlers.WalletResourcesInstalledEventHandler;
import com.bitdubai.fermat_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.structure.*;

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



public class AppRuntimeMiddlewarePluginRoot implements Service, Middleware, AppRuntimeManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();
    List<App> listApps = new ArrayList<>();
    List<SubApp> listSubApp = new ArrayList<>();
    List<Activity> listActivities = new ArrayList<>();
    List<Fragment> listFragments = new ArrayList<>();
    List<Wallet> listWallets = new ArrayList<>();
    int lastApp = 0;
    int lastSubapp = 0;
    int lastActivity = 0;
    int lastFragment = 0;
    int lastWallet = 0;
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

    public void addToNavigationStructure(/*String NavigationStructure, WalletType*/) {

        /*
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.NAVIGATION_STRUCTURE_UPDATED);
        ((NavigationStructureUpdatedEvent) platformEvent).----------(this.-----);
        eventManager.raiseEvent(platformEvent);
        */
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
        return listApps.get(lastApp);
    }

    @Override
    public SubApp getLastSubApp() {
        return listSubApp.get(lastSubapp);
    }

    @Override
    public Wallet getLastWallet() {
        return listWallets.get(lastWallet);
    }

    @Override
    public Activity getLasActivity() {
        return listActivities.get(lastActivity);
    }

    @Override
    public Fragment getLastFragment() {
        return listFragments.get(lastFragment);
    }

    @Override
    public Activity getActivity(Activities app) {
        for (int i = 0; i < listActivities.size(); i++) {
            if(listActivities.get(i).getType().name().equals(app.name()) ){
                lastActivity = i;
                return listActivities.get(i) ;
            }
        }
        return null;
    }

/*
    PlatformEvent platformEvent = eventManager.getNewEvent(EventType.NAVIGATION_STRUCTURE_UPDATED);
    ((NavigationStructureUpdatedEvent) platformEvent).--------(this.-------);
    eventManager.raiseEvent(platformEvent);
*/


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
        RuntimeTab runtimeTab;
        runtimeApp = new RuntimeApp();

        runtimeApp.setType(Apps.CRYPTO_WALLET_PLATFORM);


        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_SHELL);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.add(runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_SHELL_LOGIN);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHELL_LOGIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);



        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_FACTORY);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.add(runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);


        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_MANAGER);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.add(runtimeSubApp);
        lastSubapp = 2;

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);

        lastActivity = 2;

        runtimeSideMenu = new RuntimeSideMenu();
        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("");
        runtimeSideMenu.addMenuItem(runtimeMenuItem);
        runtimeActivity.setSideMenu(runtimeSideMenu);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_MANAGER_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_SHOP_MANAGER_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);


        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_RUNTIME);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.add(runtimeSubApp);

        runtimeWallet = new RuntimeWallet();
        runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);
        runtimeSubApp.addWallet(runtimeWallet);
        listWallets.add(runtimeWallet);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN);
        runtimeWallet.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);

        runtimeTitleBar = new RuntimeTitleBar();
        runtimeTitleBar.setLabel("Kids Wallet");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeSideMenu = new RuntimeSideMenu();

        runtimeMenuItem = new RuntimeMenuItem();
        runtimeMenuItem.setLabel("Menu item 1");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN); // Solo es un ej.
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        runtimeTabStrip = new RuntimeTabStrip();

        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Profile");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Desktop");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Contacts");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Community");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY);
        runtimeTabStrip.addTab(runtimeTab);


        runtimeTabStrip.setDividerColor(0xFFFFFFFF);


        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);


        /**
         * End of Wallet Kids fragments.
         * */


        runtimeSubApp = new RuntimeSubApp();
        runtimeSubApp.setType(SubApps.CWP_WALLET_STORE);
        runtimeApp.addSubApp(runtimeSubApp);
        listSubApp.add(runtimeSubApp);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);


        runtimeWallet = new RuntimeWallet();
        runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI);
        runtimeSubApp.addWallet(runtimeWallet);
        listWallets.add(runtimeWallet);

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_MAIN);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);

        runtimeTitleBar = new RuntimeTitleBar();
        runtimeTitleBar.setLabel("Ladies wallet");
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
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Home");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Balance");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Send");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Receive");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Shops");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Refill");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTab = new RuntimeTab();
        runtimeTab.setLabel("Discounts");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS);
        runtimeTabStrip.addTab(runtimeTab);


        runtimeTabStrip.setDividerColor(0xFFFFFFFF);


        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);
        /**
         * End of Wallet Adults tabs.
         */

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SHOPS);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);

        runtimeTitleBar = new RuntimeTitleBar();
        runtimeTitleBar.setLabel("My Shop");

        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeTabStrip = new RuntimeTabStrip();
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
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);
        /**
         * End of SHOPS tabs.
         */

        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REFFILS);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);
//-----------------------------------------------------------------------------------
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);
//------------------------------------------------------------------------------------
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);
//-----------------------------------------------------------------------------------
        runtimeActivity= new RuntimeActivity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS);
        runtimeSubApp.addActivity(runtimeActivity);
        listActivities.add(runtimeActivity);


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
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        runtimeFragment = new RuntimeFragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
        runtimeActivity.addFragment(runtimeFragment);
        listFragments.add(runtimeFragment);

        listApps.add(runtimeApp);
        /**
         * End of Wallet Accounts tabs.
         */


    }

}
