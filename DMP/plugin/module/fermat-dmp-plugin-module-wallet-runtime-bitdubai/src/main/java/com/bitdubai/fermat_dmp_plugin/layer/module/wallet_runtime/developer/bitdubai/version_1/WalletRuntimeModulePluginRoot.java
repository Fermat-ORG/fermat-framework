package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Activity;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Fragment;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Wallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.exceptions.CantRecordClosedWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.exceptions.CantRecordOpenedWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.*;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletClosedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletOpenedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.structure.RuntimeActivity;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.structure.RuntimeFragment;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.structure.*;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.exceptions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Luis on 21.01.15.
 */
public class WalletRuntimeModulePluginRoot implements Service, WalletRuntimeManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();
    
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

    /**
     * WalletRuntimeManager Interface member variables.
     */



    Map<Activities, Activity> listActivities = new HashMap<Activities, Activity>();
    Map<Fragments, Fragment> listFragments = new HashMap<Fragments, Fragment>();
    Map<Wallets, Wallet> listWallets = new HashMap<Wallets, Wallet>();

    Activities  lastActivity;
    Fragments lastFragment;
    Wallets lastWallet;
    
    /**
     * PlatformService Interface implementation.
     */
    
    @Override
    public void start() throws CantStartPluginException{
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */


            EventListener eventListener;
            EventHandler eventHandler;

            eventListener = eventManager.getNewListener(EventType.WALLET_OPENED);
            eventHandler = new WalletOpenedEventHandler();
            ((WalletOpenedEventHandler) eventHandler).setWalletRuntimeManager(this);
            eventListener.setEventHandler(eventHandler);
            eventManager.addListener(eventListener);
            listenersAdded.add(eventListener);

            eventListener = eventManager.getNewListener(EventType.WALLET_CLOSED);
            eventHandler = new WalletClosedEventHandler();
            ((WalletClosedEventHandler) eventHandler).setWalletRuntimeManager(this);
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


        } catch(CantFactoryReset ex){
            String message = CantStartPluginException.DEFAULT_MESSAGE;
            FermatException cause = ex;
            String context = "Wallet Runtime Start";

            String possibleReason = "Some null definition";
            throw new CantStartPluginException(message, cause, context, possibleReason);
        }

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;
    }
    
    @Override
    public void resume() {


        this.serviceStatus = ServiceStatus.STARTED;
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

    /**
     * WalletRuntime Interface implementation.
     */
    @Override
    public void recordOpenedWallet(UUID walletId) throws CantRecordOpenedWalletException {

    }

    @Override
    public void recordClosedWallet(UUID walletId) throws CantRecordClosedWalletException {

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
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }







    public Wallet getLastWallet() {

        Iterator<Map.Entry<Wallets, Wallet>> ewallet = this.listWallets.entrySet().iterator();

        while (ewallet.hasNext()) {
            Map.Entry<Wallets, Wallet> walletEntry = ewallet.next();
            RuntimeWallet wallet = (RuntimeWallet) walletEntry.getValue();
            if(wallet.getType().name().equals(this.lastWallet.name())){
                return wallet;
            }


        }

        return null;
    }


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


    public Fragment getLastFragment() {
        return listFragments.get(lastFragment);
    }


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
     * Here is where I actually generate the factory structure of the APP. This method is also useful to reset to the
     * factory structure.
     */
    private void factoryReset() throws CantFactoryReset{
        try{

            RuntimeActivity runtimeActivity;
            RuntimeFragment runtimeFragment;
            RuntimeWallet runtimeWallet;
            RuntimeTitleBar runtimeTitleBar;
            RuntimeSideMenu runtimeSideMenu;
            RuntimeMainMenu runtimeMainMenu;
            RuntimeMenuItem runtimeMenuItem;
            RuntimeTabStrip runtimeTabStrip;

            RuntimeTab runtimeTab;


            /**
             * Wallet Kids definition.
             * */


            runtimeWallet = new RuntimeWallet();
            runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);
            //   runtimeSubApp.addWallet(runtimeWallet);
            listWallets.put(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI,runtimeWallet);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Kids Wallet");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#84DCF5");

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
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY,runtimeFragment);


            /**
             * End of Wallet Kids fragments.
             * */

            /**
             * Bitcoin Basic Wallet definition.
             *
             * Structure:
             *
             * TYPE: CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN
             *
             * TITLE: Fermat Bitcoin Wallet
             *
             * TAB STRIP:
             *      *BALANCE - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE
             *      *SEND - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND
             *      *RECEIVE - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE
             *      *TRANSACTIONS - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS
             *      *CONTACTS - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS
             *      *DATABASE TOOLS - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DATABASE_TOOLS
             *      *LOG TOOLS - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_LOG_TOOLS
             */


            runtimeWallet = new RuntimeWallet();
            runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI);
            listWallets.put(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, runtimeWallet);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Fermat Bitcoin Wallet");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            //runtimeActivity.setColor("#8bba9e");
            runtimeActivity.setColor("#d07b62");



            runtimeTabStrip = new RuntimeTabStrip();

            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("Balance");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("Send");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("Receive");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("Transactions");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("Contacts");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
            runtimeTabStrip.addTab(runtimeTab);





            runtimeTabStrip.setDividerColor(0x72af9c);
            //runtimeTabStrip.setBackgroundColor("#72af9c");
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE, runtimeFragment);


            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND,runtimeFragment);


            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS,runtimeFragment);



            /**
             * End of Wallet basic fragments.
             */


            /**
             * Definition of Developer Manager
             */

            runtimeWallet = new RuntimeWallet();
            runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_DEVELOPER_APP_ALL_BITDUBAI);
            listWallets.put(Wallets.CWP_WALLET_RUNTIME_WALLET_DEVELOPER_APP_ALL_BITDUBAI, runtimeWallet);


            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_SUP_APP_ALL_DEVELOPER);
            //runtimeActivity.setColor("#d07b62");
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_SUP_APP_ALL_DEVELOPER, runtimeActivity);



            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Developer");

            runtimeActivity.setTitleBar(runtimeTitleBar);



            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("DataBase Tools");
            runtimeTab.setFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS);

            runtimeTabStrip.addTab(runtimeTab);


            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("Log Tools");
            runtimeTab.setFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_TOOLS);
            runtimeTabStrip.addTab(runtimeTab);


            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS,runtimeFragment);



            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_SUB_APP_DEVELOPER_LOG_TOOLS);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_SUB_APP_DEVELOPER_LOG_TOOLS,runtimeFragment);

            /**
             * End of Developer tabs.
             */


            // Wallet adults

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN);
            //   runtimeSubApp.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_STORE_MAIN, runtimeActivity);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_STORE_MAIN,runtimeFragment);


            runtimeWallet = new RuntimeWallet();
            runtimeWallet.setType(Wallets.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI);
            // runtimeSubApp.addWallet(runtimeWallet);
            listWallets.put(Wallets.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI,runtimeWallet);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_MAIN);
            //runtimeSubApp.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_MAIN, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Adults wallet");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

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
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL,runtimeFragment);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_CHAT);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_CHAT,runtimeFragment);



            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Contacts");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS,runtimeFragment);


            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Available balance");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SEND_HISTORY);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_SEND_HISTORY, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Sent History");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_SEND_HISTORY);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_ADULTS_ALL_SEND_HISTORY,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_SEND);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_SEND, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Send To Contact");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_SEND);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_SEND,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CHAT_TRX);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CHAT_TRX,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_RECEIVE);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_RECEIVE, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Receive From Contact");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new RuntimeTabStrip();
            runtimeTab = new RuntimeTab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_RECEIVE);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_RECEIVE,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT,runtimeFragment);


            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Send to new contact");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_SEND);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_SEND,runtimeFragment);

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE);
            runtimeWallet.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE, runtimeActivity);

            runtimeTitleBar = new RuntimeTitleBar();
            runtimeTitleBar.setLabel("Receive from new contact");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_RECEIVE);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_RECEIVE,runtimeFragment);

            /**
             * End of Wallet Adults tabs.
             */

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SHOPS);
            runtimeActivity.setColor("#76dc4a");
            //  runtimeSubApp.addActivity(runtimeActivity);
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



            //Account Details

            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNT_DETAIL);
            runtimeActivity.setColor("#F0E173");
            //runtimeSubApp.addActivity(runtimeActivity);
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
            // runtimeSubApp.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_REFFILS, runtimeActivity);
//-----------------------------------------------------------------------------------
            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
            //runtimeSubApp.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED, runtimeActivity);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED,runtimeFragment);
//------------------------------------------------------------------------------------
            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
            //runtimeSubApp.addActivity(runtimeActivity);
            listActivities.put(Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND, runtimeActivity);

            runtimeFragment = new RuntimeFragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
            runtimeActivity.addFragment(runtimeFragment);
            listFragments.put(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND,runtimeFragment);
//-----------------------------------------------------------------------------------
            runtimeActivity= new RuntimeActivity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS);
            //runtimeSubApp.addActivity(runtimeActivity);
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
            //  listApps.put(Apps.CRYPTO_WALLET_PLATFORM,runtimeApp);
            //lastApp = Apps.CRYPTO_WALLET_PLATFORM;
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
