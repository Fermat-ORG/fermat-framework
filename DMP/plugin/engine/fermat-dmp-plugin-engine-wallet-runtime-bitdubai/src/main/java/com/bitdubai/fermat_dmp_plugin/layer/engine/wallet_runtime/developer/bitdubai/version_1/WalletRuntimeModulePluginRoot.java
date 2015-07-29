package com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRecordClosedWalletException;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRecordOpenedWalletException;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRemoveWalletNavigationStructureException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletClosedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletInstalledEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletOpenedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletUnnInstalledEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.exceptions.CantFactoryReset;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 23.07.15.
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


    Map<String, Wallet> listWallets = new HashMap<String, Wallet>();

    String lastWalletPublicKey;


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

            eventListener = eventManager.getNewListener(EventType.WALLET_INSTALLED);
            eventHandler = new WalletInstalledEventHandler();
            ((WalletInstalledEventHandler) eventHandler).setWalletRuntimeManager(this);
            eventListener.setEventHandler(eventHandler);
            eventManager.addListener(eventListener);
            listenersAdded.add(eventListener);

            eventListener = eventManager.getNewListener(EventType.WALLET_UNINSTALLED);
            eventHandler = new WalletUnnInstalledEventHandler();
            ((WalletUnnInstalledEventHandler) eventHandler).setWalletRuntimeManager(this);
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

    @Override
    public void recordNavigationStructure(String walletId) {

    }

    @Override
    public boolean removeNavigationStructure(String publicKey) throws CantRemoveWalletNavigationStructureException {
        return false;
    }

    @Override
    public Wallet getNavigationStructureFromWallet(String publicKey) {

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



    @Override
    public Wallet getLastWallet() {

        Iterator<Map.Entry<String, Wallet>> ewallet = this.listWallets.entrySet().iterator();

        while (ewallet.hasNext()) {
            Map.Entry<String, Wallet> walletEntry = ewallet.next();
            Wallet wallet = (Wallet) walletEntry.getValue();
            if(wallet.getPublicKey().equals(lastWalletPublicKey)){
                return wallet;
            }
        }
        return null;
    }


    @Override
    public Wallet getWallet(String publicKey) {
        Iterator<Map.Entry<String, Wallet>> ewallet = this.listWallets.entrySet().iterator();
        while (ewallet.hasNext()) {
            Map.Entry<String, Wallet> walletEntry = ewallet.next();
            Wallet wallet = (Wallet) walletEntry.getValue();
            if(wallet.getPublicKey().equals(publicKey)){
                this.lastWalletPublicKey=publicKey;
                return wallet;
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

            Activity runtimeActivity;
            Fragment runtimeFragment;
            Wallet runtimeWallet;
            TitleBar runtimeTitleBar;
            SideMenu runtimeSideMenu;
            MainMenu runtimeMainMenu;
            MenuItem runtimeMenuItem;
            TabStrip runtimeTabStrip;
            StatusBar runtimeStatusBar;

            Tab runtimeTab;

            String publicKey;


            /**
             * Wallet Kids definition.
             * */


            runtimeWallet = new Wallet();
            //   runtimeSubApp.addWallet(runtimeWallet);
            publicKey="kids";
            listWallets.put(publicKey, runtimeWallet);
            runtimeWallet.setPublicKey(publicKey);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Kids Wallet");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#84DCF5");

            runtimeSideMenu = new SideMenu();

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Menu item 1");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN); // Solo es un ej.
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);

            runtimeTabStrip = new TabStrip();

            runtimeTab = new Tab();
            runtimeTab.setLabel("Profile");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Desktop");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Contacts");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Community");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY);
            runtimeTabStrip.addTab(runtimeTab);


            runtimeTabStrip.setDividerColor(0xFFFFFFFF);


            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY,runtimeFragment);


            /**
             * End of Wallet Kids fragments.
             * */

            /**
             * Fermat Bitcoin Reference Walletc definition.
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


            runtimeWallet = new Wallet();
            publicKey="reference_wallet";
            runtimeWallet.setPublicKey(publicKey);
            listWallets.put(publicKey, runtimeWallet);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
            runtimeActivity.setColor("#8bba9e");
            runtimeWallet.addActivity(runtimeActivity);
            runtimeWallet.setStartActivity(runtimeActivity.getType());

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Fermat Bitcoin Reference Wallet");

            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#72af9c");
            //runtimeActivity.setColor("#d07b62");


            runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            runtimeStatusBar.setColor("#72af9c");

            runtimeActivity.setStatusBar(runtimeStatusBar);


            runtimeTabStrip = new TabStrip();

            runtimeTabStrip.setTabsColor("#8bba9e");

            runtimeTabStrip.setTabsTextColor("#FFFFFF");

            runtimeTabStrip.setTabsIndicateColor("#72af9c");

            runtimeTab = new Tab();
            runtimeTab.setLabel("Balance");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Send");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Receive");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Transactions");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Contacts");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
            runtimeTabStrip.addTab(runtimeTab);





            runtimeTabStrip.setDividerColor(0x72af9c);
            //runtimeTabStrip.setBackgroundColor("#72af9c");
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE,runtimeFragment);


            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND,runtimeFragment);


            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS,runtimeFragment);



            /**
             * End of Wallet basic fragments.
             */





            // Wallet adults

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN);
            //   runtimeSubApp.addActivity(runtimeActivity);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_MAIN,runtimeFragment);

            runtimeWallet = new Wallet();
            // runtimeSubApp.addWallet(runtimeWallet);
            publicKey="adults_wallet";
            runtimeWallet.setPublicKey(publicKey);
            listWallets.put(publicKey,runtimeWallet);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_MAIN);
            //runtimeSubApp.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Adults wallet");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeSideMenu = new SideMenu();

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Contacts");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Accounts");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Banks");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Coupons");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Discounts");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Vouchers");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Gift Cards");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Clones");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Childs");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Exit");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_MANAGER_MAIN);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("Home");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Balance");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Send");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Receive");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Shops");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Refill");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Discounts");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTabStrip.setDividerColor(0xFFFFFFFF);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_CHAT);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_CHAT,runtimeFragment);



            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Contacts");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS,runtimeFragment);


            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Available balance");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SEND_HISTORY);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Sent History");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_SEND_HISTORY);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_SEND_HISTORY,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_SEND);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Send To Contact");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_SEND);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_SEND,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CHAT_TRX);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CHAT_TRX,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_RECEIVE);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Receive From Contact");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("");
            runtimeTabStrip.addTab(runtimeTab);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_RECEIVE);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_RECEIVE,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT,runtimeFragment);


            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Send to new contact");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_SEND);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_SEND,runtimeFragment);

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE);
            runtimeWallet.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Receive from new contact");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setColor("#F0E173");

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_RECEIVE);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_RECEIVE,runtimeFragment);

            /**
             * End of Wallet Adults tabs.
             */

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SHOPS);
            runtimeActivity.setColor("#76dc4a");
            //  runtimeSubApp.addActivity(runtimeActivity);



            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("My Shop");

            runtimeActivity.setTitleBar(runtimeTitleBar);


            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("Shop");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Products");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Reviews");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Chat");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("History");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Map");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP);
            runtimeTabStrip.addTab(runtimeTab);


            runtimeTabStrip.setDividerColor(0xFFFFFFFF);
            runtimeTabStrip.setIndicatorColor(0xFFFFFFFF);
            runtimeTabStrip.setIndicatorHeight(9);
            runtimeTabStrip.setBackgroundColor(0xFF76dc4a);
            runtimeTabStrip.setTextColor(0xFFFFFFFF);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP,runtimeFragment);
            /**
             * End of SHOPS tabs.
             */



            //Account Details

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNT_DETAIL);
            runtimeActivity.setColor("#F0E173");
            //runtimeSubApp.addActivity(runtimeActivity);



            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Account details");

            runtimeActivity.setTitleBar(runtimeTitleBar);


            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("Debits");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("Credits");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
            runtimeTabStrip.addTab(runtimeTab);
            runtimeTab = new Tab();
            runtimeTab.setLabel("All");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL,runtimeFragment);



        /*------------------------------*/

            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REFFILS);
            // runtimeSubApp.addActivity(runtimeActivity);
//-----------------------------------------------------------------------------------
            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
            //runtimeSubApp.addActivity(runtimeActivity);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED,runtimeFragment);
//------------------------------------------------------------------------------------
            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
            //runtimeSubApp.addActivity(runtimeActivity);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND,runtimeFragment);
//-----------------------------------------------------------------------------------
            runtimeActivity= new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS);
            //runtimeSubApp.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Account details");
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeTabStrip = new TabStrip();
            runtimeTab = new Tab();
            runtimeTab.setLabel("Debits");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Credits");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("All");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
            runtimeTabStrip.addTab(runtimeTab);


            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS,runtimeFragment);
            //listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS,runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL,runtimeFragment);
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
