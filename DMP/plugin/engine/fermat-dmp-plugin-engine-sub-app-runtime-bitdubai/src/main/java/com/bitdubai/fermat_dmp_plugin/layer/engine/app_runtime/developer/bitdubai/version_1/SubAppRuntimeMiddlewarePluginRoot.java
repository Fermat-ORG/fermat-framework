package com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1;


/**
 * Created by Matias Furszyfer 24/07/15
 */

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WizardPage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardPageTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubAppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Apps;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.event_handlers.WalletResourcesInstalledEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.exceptions.CantFactoryResetException;
import com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.structure.RuntimeApp;
import com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.structure.RuntimeSubApp;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
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


public class SubAppRuntimeMiddlewarePluginRoot implements Service, SubAppRuntimeManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * SubAppRuntimeManager Interface member variables.
     */

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    Map<SubApps, SubApp> listSubApp = new HashMap<SubApps, SubApp>();

    SubApps lastSubapp;

    RuntimeSubApp homeScreen;

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


    public void addToNavigationStructure(/*String NavigationStructure, WalletModule*/) {

        /*
        FermatEvent platformEvent = eventManager.getNewEvent(EventType.NAVIGATION_STRUCTURE_UPDATED);
        ((NavigationStructureUpdatedEvent) platformEvent).----------(this.-----);
        eventManager.raiseEvent(platformEvent);
        */
    }
    
    /*
    FermatEvent platformEvent = eventManager.getNewEvent(EventType.NAVIGATION_STRUCTURE_UPDATED);
    ((NavigationStructureUpdatedEvent) platformEvent).--------(this.-------);
    eventManager.raiseEvent(platformEvent);
*/


    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;
            fermatEventListener = eventManager.getNewListener(EventType.WALLET_RESOURCES_INSTALLED);
            fermatEventHandler = new WalletResourcesInstalledEventHandler();
            ((WalletResourcesInstalledEventHandler) fermatEventHandler).setSubAppRuntimeManager(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            /**
             * At this time the only thing I can do is a factory reset. Once there should be a possibility to add
             * functionality based on wallets downloaded by users this wont be an option.
             * * *
             */
            factoryReset();

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantFactoryResetException ex) {
            String message = CantStartPluginException.DEFAULT_MESSAGE;
            FermatException cause = ex;
            String context = "App Runtime Start";
            String possibleReason = "Some null definition";
            throw new CantStartPluginException(message, cause, context, possibleReason);
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Unchecked Exception occurred, check the cause");
        }
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
         * I will remove all the listeners registered with the event manager. 
         */
        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
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
    public SubApp getLastSubApp() {
        if (lastSubapp != null) {
            return listSubApp.get(lastSubapp);
        }
        return homeScreen;
    }

    @Override
    public SubApp getSubApp(SubApps subApps) {
        SubApp subApp = listSubApp.get(subApps);
        if (subApp != null) {
            lastSubapp = subApps;
            return subApp;
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;

//        Iterator<Map.Entry<SubApps, SubApp>> eSubApp = listSubApp.entrySet().iterator();
//        while (eSubApp.hasNext()) {
//            Map.Entry<SubApps, SubApp> walletEntry = eSubApp.next();
//            SubApp subApp = (SubApp) walletEntry.getValue();
//            if (subApp.getType().equals(subApps)) {
//                lastSubapp = subApps;
//                return subApp;
//            }
//        }

    }

    @Override
    public SubApp getHomeScreen() {
        lastSubapp = SubApps.CWP_WALLET_MANAGER;
        homeScreen.getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
        return homeScreen;
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
    private void factoryReset() throws CantFactoryResetException {

        loadHomeScreen();

        try {
            RuntimeApp runtimeApp = new RuntimeApp();
            runtimeApp.setType(Apps.CRYPTO_WALLET_PLATFORM);

            RuntimeSubApp runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CWP_SHELL);
            runtimeApp.addSubApp(runtimeSubApp);
            listSubApp.put(SubApps.CWP_SHELL, runtimeSubApp);

            Activity runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_SHELL_LOGIN);
            runtimeSubApp.addActivity(runtimeActivity);

            Fragment runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_SHELL_LOGIN.getKey());
            runtimeActivity.addFragment(Fragments.CWP_SHELL_LOGIN.getKey(), runtimeFragment);

            TitleBar runtimeTitleBar;
            SideMenu runtimeSideMenu;
            MainMenu runtimeMainMenu;
            MenuItem runtimeMenuItem;
            TabStrip runtimeTabStrip;
            StatusBar statusBar;

            Tab runtimeTab;


            /**
             * Definition of Developer Manager App
             */

            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CWP_DEVELOPER_APP);
            listSubApp.put(SubApps.CWP_DEVELOPER_APP, runtimeSubApp);

            //acá estoy seteando los colores y toda la vaina esa
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_SUB_APP_ALL_DEVELOPER);
            runtimeActivity.setColor("#b46a54");

            //runtimeActivity.setStatusBarColor();

            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            statusBar.setColor("#d07b62");
            runtimeActivity.setStatusBar(statusBar);

            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.setStartActivity(Activities.CWP_SUB_APP_ALL_DEVELOPER);


            runtimeTitleBar = new TitleBar();
            //Navigation

            runtimeSideMenu = new SideMenu();


            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Personal Wallets");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Shops");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Commercial wallets");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Factory Projects");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_FACTORY_MAIN);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Published Wallets");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Wallet Store");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);


            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Exit");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);

            //fin navigation


            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Developer");
            //runtimeTitleBar.setColor("#d07b62");
            runtimeActivity.setTitleBar(runtimeTitleBar);


            runtimeTabStrip = new TabStrip();
            runtimeTabStrip.setTabsColor("#d07b62");
            runtimeTabStrip.setTabsTextColor("#FFFFFF");
            runtimeTabStrip.setTabsIndicateColor("#b46a54");

            runtimeTab = new Tab();
            runtimeTab.setLabel("DataBase Tools");
            runtimeTab.setFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT);

            runtimeTabStrip.addTab(runtimeTab);


            runtimeTab = new Tab();
            runtimeTab.setLabel("Log Tools");
            runtimeTab.setFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT);
            runtimeTabStrip.addTab(runtimeTab);


            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey());
            runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey());
            runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey());
            runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey());
            runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey());
            runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey());
            runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey(), runtimeFragment);

            /**
             * End of Developer tabs.
             */

            //wallet factory app
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CWP_WALLET_FACTORY);
            runtimeApp.addSubApp(runtimeSubApp);
            listSubApp.put(SubApps.CWP_WALLET_FACTORY, runtimeSubApp);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_MAIN);
            runtimeActivity.setColor("#b46a54");
            runtimeSubApp.setStartActivity(Activities.CWP_WALLET_FACTORY_MAIN);
            //runtimeActivity.setStatusBarColor("");


            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            statusBar.setColor("#b46a54");

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Wallet Factory");
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeTabStrip = new TabStrip();
            runtimeTabStrip.setTabsColor("#d07b62");
            runtimeTabStrip.setTabsTextColor("#FFFFFF");
            runtimeTabStrip.setTabsIndicateColor("#b46a54");

            runtimeTab = new Tab();
            runtimeTab.setLabel("Developer Projects");
            runtimeTab.setFragment(Fragments.CWP_WALLET_FACTORY_DEVELOPER_PROJECTS);

            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Edit Mode");
            runtimeTab.setFragment(Fragments.CWP_WALLET_FACTORY_AVAILABLE_PROJECTS);

            runtimeTabStrip.addTab(runtimeTab);


            runtimeActivity.setTabStrip(runtimeTabStrip);
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_DEVELOPER_PROJECTS.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_FACTORY_DEVELOPER_PROJECTS.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_AVAILABLE_PROJECTS.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_FACTORY_AVAILABLE_PROJECTS.getKey(), runtimeFragment);

            /* Adding WizardTypes */
            Wizard runtimeWizard = new Wizard();
            // step 1 wizard create from scratch
            WizardPage runtimeWizardPage = new WizardPage();
            runtimeWizardPage.setType(WizardPageTypes.CWP_WALLET_FACTORY_CREATE_STEP_1);
            runtimeWizard.addPage(runtimeWizardPage);
            //step 2 wizard
            runtimeWizardPage = new WizardPage();
            runtimeWizardPage.setType(WizardPageTypes.CWP_WALLET_FACTORY_CREATE_STEP_2);
            runtimeWizard.addPage(runtimeWizardPage);
            /* Adding wizard */
            runtimeActivity.addWizard(WizardTypes.CWP_WALLET_FACTORY_CREATE_NEW_PROJECT, runtimeWizard);

            /**
             *  Edit Activity
             */
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_EDIT_WALLET);
            runtimeActivity.setColor("#b46a54");

            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            statusBar.setColor("#b46a54");

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Edit Wallet");
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeTabStrip = new TabStrip();

            runtimeTabStrip.setTabsColor("#8bba9e");

            runtimeTabStrip.setTabsTextColor("#FFFFFF");

            runtimeTabStrip.setTabsIndicateColor("#72af9c");

            runtimeTab = new Tab();
            runtimeTab.setLabel("Balance");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Contacts");
            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTabStrip.setDividerColor(0x72af9c);
            //runtimeTabStrip.setBackgroundColor("#72af9c");
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey(), runtimeFragment);


            runtimeSubApp.addActivity(runtimeActivity);


            /**End Wallet Factory*/

            //wallet Publisher app
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CWP_WALLET_PUBLISHER);
            runtimeApp.addSubApp(runtimeSubApp);
            listSubApp.put(SubApps.CWP_WALLET_PUBLISHER, runtimeSubApp);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_PUBLISHER_MAIN);
            runtimeActivity.setColor("#b46a54");
            //runtimeActivity.setStatusBarColor("#b46a54");556
            runtimeSubApp.setStartActivity(Activities.CWP_WALLET_PUBLISHER_MAIN);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Wallet Publisher");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeWizard = new Wizard();
            runtimeWizardPage = new WizardPage();
            runtimeWizardPage.setType(WizardPageTypes.CWP_WALLET_PUBLISHER_PUBLISH_STEP_1);
            runtimeWizard.addPage(runtimeWizardPage);

            runtimeWizardPage = new WizardPage();
            runtimeWizardPage.setType(WizardPageTypes.CWP_WALLET_PUBLISHER_PUBLISH_STEP_2);
            runtimeWizard.addPage(runtimeWizardPage);

            runtimeWizardPage = new WizardPage();
            runtimeWizardPage.setType(WizardPageTypes.CWP_WALLET_PUBLISHER_PUBLISH_STEP_3);
            runtimeWizard.addPage(runtimeWizardPage);

            runtimeActivity.addWizard(WizardTypes.CWP_WALLET_PUBLISHER_PUBLISH_PROJECT, runtimeWizard);

            runtimeSideMenu = new SideMenu();
            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Personal Wallets");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Shops");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Commercial wallets");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Factory Projects");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_FACTORY_MAIN);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Published Wallets");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Wallet Store");
            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);


            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Exit");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);


            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_PUBLISHER_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_PUBLISHER_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CWP_WALLET_PUBLISHER_MAIN_FRAGMENT.getKey());


            /**End Wallet Publisher*/

            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CWP_WALLET_MANAGER);
            runtimeApp.addSubApp(runtimeSubApp);
            listSubApp.put(SubApps.CWP_WALLET_MANAGER, runtimeSubApp);

            //TODO: testing
            //lastSubapp = SubApps.CWP_WALLET_MANAGER;

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_MANAGER_MAIN.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_MANAGER_MAIN.getKey(), runtimeFragment);


            //Desktop page Developer sub App
            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_SUB_APP_DEVELOPER.getKey());
            runtimeActivity.addFragment(Fragments.CWP_SUB_APP_DEVELOPER.getKey(), runtimeFragment);


            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CWP_WALLET_RUNTIME);
            runtimeApp.addSubApp(runtimeSubApp);
            listSubApp.put(SubApps.CWP_WALLET_RUNTIME, runtimeSubApp);


//            runtimeSubApp = new RuntimeSubApp();
//            runtimeSubApp.setType(SubApps.CWP_WALLET_STORE);
//            runtimeApp.addSubApp(runtimeSubApp);
//            listSubApp.put(SubApps.CWP_WALLET_STORE, runtimeSubApp);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY);
            runtimeSubApp.addActivity(runtimeActivity);


            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_PUBLISHER_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_PUBLISHER_MAIN_FRAGMENT.getKey(), runtimeFragment);


            /**
             * Definition of Shop Manager
             */

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SHOPS);
            runtimeActivity.setColor("#76dc4a");
            runtimeSubApp.addActivity(runtimeActivity);


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
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP.getKey(), runtimeFragment);


            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS.getKey(), runtimeFragment);


            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP.getKey(), runtimeFragment);
            /**
             * End of SHOPS tabs.
             */


            /**
             * WALLET STORE
             */
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CWP_WALLET_STORE);
            listSubApp.put(SubApps.CWP_WALLET_STORE, runtimeSubApp);

            //Activity 1
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY);
            runtimeActivity.setColor("#B46A54");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.setStartActivity(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY);

            statusBar = new StatusBar();
            statusBar.setColor("#B4573C");
            runtimeActivity.setStatusBar(statusBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Wallet Store");
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN_ACTIVITY.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_MAIN_ACTIVITY.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CWP_WALLET_STORE_MAIN_ACTIVITY.getKey());

            //Activity 2
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_STORE_DETAIL_ACTIVITY);
            runtimeActivity.setColor("#B46A54");
            runtimeActivity.setBackActivity(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY);
            runtimeSubApp.addActivity(runtimeActivity);

            statusBar = new StatusBar();
            statusBar.setColor("#B4573C");
            runtimeActivity.setStatusBar(statusBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabel("Wallet Details");
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_STORE_DETAIL_ACTIVITY.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_DETAIL_ACTIVITY.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CWP_WALLET_STORE_DETAIL_ACTIVITY.getKey());

            //Activity 3
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY);
            runtimeActivity.setColor("#B46A54");
            runtimeActivity.setBackActivity(Activities.CWP_WALLET_STORE_DETAIL_ACTIVITY);
            runtimeSubApp.addActivity(runtimeActivity);

            statusBar = new StatusBar();
            statusBar.setColor("#B4573C");
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Wallet Store more detail");
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getKey());

            /**
             * Start Intra user community sub app
             */
            RuntimeSubApp subAppIntraUser = new RuntimeSubApp();
            subAppIntraUser.setType(SubApps.CCP_INTRA_USER_COMMUNITY);
            listSubApp.put(SubApps.CCP_INTRA_USER_COMMUNITY, subAppIntraUser);


            //Activity 1
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_INTRA_USER_ACTIVITY);
            subAppIntraUser.setStartActivity(Activities.CWP_INTRA_USER_ACTIVITY);
            //runtimeSubApp.addActivity(runtimeActivity);
            runtimeActivity.setColor("#FF0B46F0");

            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();

            statusBar.setColor("#FF0B46F0");

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("World");
            runtimeTitleBar.setColor("#FF0B46F0");
            runtimeTitleBar.setIconName("world");

            //RuntimeFernatComboBox comboBox = new RuntimeFernatComboBox();
            //comboBox.addValue("Mati");

            //runtimeTitleBar.setComboBox(comboBox);

            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeTabStrip = new TabStrip();
//            runtimeTabStrip.setTabsColor("#d07b62");
//            runtimeTabStrip.setTabsTextColor("#FFFFFF");
//            runtimeTabStrip.setTabsIndicateColor("#b46a54");
            //mati
            SearchView runtimeSearchView = new SearchView();
            runtimeSearchView.setLabel("Search");
            runtimeTitleBar.setRuntimeSearchView(runtimeSearchView);

            //runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeMainMenu = new MainMenu();
            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Settings");
            runtimeMainMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setMainMenu(runtimeMainMenu);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_STORE_ALL_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_ALL_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CWP_WALLET_STORE_ALL_FRAGMENT.getKey());


            runtimeSideMenu = new SideMenu();


            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Connections");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("World");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Pending request");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Exit");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);

            subAppIntraUser.addActivity(runtimeActivity);
            subAppIntraUser.setStartActivity(Activities.CWP_INTRA_USER_ACTIVITY);


            //Activity 3
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_INTRA_USER_CONNECTION_REQUEST_ACTIVITY);
            subAppIntraUser.addActivity(runtimeActivity);
            runtimeActivity.setColor("#FF0B46F0");
            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            statusBar.setColor("#FF0B46F0");
            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Connections Request");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeSearchView = new SearchView();
            runtimeSearchView.setLabel("Search");
            runtimeTitleBar.setRuntimeSearchView(runtimeSearchView);
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_STORE_PAID_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_PAID_FRAGMENT.getKey(), runtimeFragment);

            // Activity: Connections
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS.getCode());
            runtimeActivity.setBackActivity(Activities.CWP_INTRA_USER_ACTIVITY);
            runtimeActivity.setColor("#FF0B46F0");

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Connections");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#FF0B46F0");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS_FRAGMENT.getKey());

            runtimeSideMenu = new SideMenu();


            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Connections");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("World");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Pending request");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Exit");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);

            subAppIntraUser.addActivity(runtimeActivity);

            // Activity: List of irequest
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_REQUEST);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_REQUEST.getCode());
            runtimeActivity.setBackActivity(Activities.CWP_INTRA_USER_ACTIVITY);
            runtimeActivity.setColor("#FF0B46F0");

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Request list");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#FF0B46F0");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_FRAGMENT.getKey());

            runtimeSideMenu = new SideMenu();


            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Connections");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("World");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Pending request");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Exit");
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);

            subAppIntraUser.addActivity(runtimeActivity);

            /**
             * End of community intra user CCP
             */

            // DAP
            RuntimeSubApp dapFactory = new RuntimeSubApp();
            dapFactory.setType(SubApps.DAP_ASSETS_FACTORY);
            dapFactory.setStartActivity(Activities.DAP_MAIN);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_MAIN);
            runtimeActivity.setColor("#FF0B46F0");

            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            statusBar.setColor("#FF0B46F0");
            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("DAP factory");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_MAIN_ACTIVITY.getKey());
            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_FACTORY_MAIN_ACTIVITY.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_MAIN_ACTIVITY.getKey(), runtimeFragment);


            dapFactory.addActivity(runtimeActivity);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_ASSET_EDITOR_ACTIVITY);
            runtimeActivity.setColor("#FF0B46F0");

            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            statusBar.setColor("#FF0B46F0");
            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Asset Editor");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY.getKey());

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY.getKey(), runtimeFragment);

            dapFactory.addActivity(runtimeActivity);

            listSubApp.put(SubApps.DAP_ASSETS_FACTORY, dapFactory);

            /**
             * Dap Asset Issuer Community
             */
            RuntimeSubApp dapAssetIssuerCommunity = new RuntimeSubApp();
            dapAssetIssuerCommunity.setType(SubApps.DAP_ASSETS_COMMUNITY_ISSUER);
            dapAssetIssuerCommunity.setStartActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
            runtimeActivity.setColor("#0072bc");

            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            statusBar.setColor("#0072bc");

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Asset Issuer Community");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getKey());
            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getKey());
            runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getKey(), runtimeFragment);

            dapAssetIssuerCommunity.addActivity(runtimeActivity);
            listSubApp.put(SubApps.DAP_ASSETS_COMMUNITY_ISSUER, dapAssetIssuerCommunity);

            /**
             * Dap Asset User Community
             */
            RuntimeSubApp dapAssetUserCommunity = new RuntimeSubApp();
            dapAssetUserCommunity.setType(SubApps.DAP_ASSETS_COMMUNITY_USER);
            dapAssetUserCommunity.setStartActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
            runtimeActivity.setColor("#0072bc");

            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            statusBar.setColor("#0072bc");

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Asset User Community");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getKey());
            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getKey());
            runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getKey(), runtimeFragment);

            dapAssetUserCommunity.addActivity(runtimeActivity);
            listSubApp.put(SubApps.DAP_ASSETS_COMMUNITY_USER, dapAssetUserCommunity);

            /**
             * Dap Asset Redeem Point Community
             */
            RuntimeSubApp dapAssetRedeemPointCommunity = new RuntimeSubApp();
            dapAssetRedeemPointCommunity.setType(SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT);
            dapAssetRedeemPointCommunity.setStartActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
            runtimeActivity.setColor("#0072bc");

            statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
            statusBar.setColor("#0072bc");

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Asset Redeem Point Community");
            runtimeActivity.setTitleBar(runtimeTitleBar);
            runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getKey());
            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getKey());
            runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getKey(), runtimeFragment);

            dapAssetRedeemPointCommunity.addActivity(runtimeActivity);
            listSubApp.put(SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT, dapAssetRedeemPointCommunity);

            /**
             * End of DAP
             */

            /**
             * CRYPTO BROKER IDENTITY
             */
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CBP_CRYPTO_BROKER_IDENTITY);

            // Activity: List of identities
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY);
            runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY.getCode());
            runtimeActivity.setColor("#1189a5");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.setStartActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY);

            statusBar = new StatusBar();
            statusBar.setColor("#0e738b");
            runtimeActivity.setStatusBar(statusBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Crypto Broker Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY.getCode());
            runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY);
            runtimeActivity.setColor("#1189a5");
            runtimeSubApp.addActivity(runtimeActivity);

            statusBar = new StatusBar();
            statusBar.setColor("#0e738b");
            runtimeActivity.setStatusBar(statusBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Create New Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            // Activity: Edit Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY);
            runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY.getCode());
            runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY);
            runtimeActivity.setColor("#1189a5");
            runtimeSubApp.addActivity(runtimeActivity);

            statusBar = new StatusBar();
            statusBar.setColor("#0e738b");
            runtimeActivity.setStatusBar(statusBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Edit Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(SubApps.CBP_CRYPTO_BROKER_IDENTITY, runtimeSubApp);

            /**
             * CBP CRYPTO CUSTOMER  IDENTITY
             */
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY);

            // Activity: List of identities
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);
            runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY.getCode());
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.setStartActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Crypto Customer Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY.getCode());
            runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Create New Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            // Activity: Edit Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY);
            runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY.getCode());
            runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);
            runtimeActivity.setColor("#1189a5");
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Edit Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY, runtimeSubApp);

            /**
             * CCP INTRA USER IDENTITY
             */
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.CWP_INTRA_USER_IDENTITY);

            // Activity: List of identities
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY.getCode());
            runtimeActivity.setBackActivity(null);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.setStartActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Intra user Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
            runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Create New Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(SubApps.CWP_INTRA_USER_IDENTITY, runtimeSubApp);

            /**
             * DAP IDENTITIES
             */

            /**
             * DAP ISSUER IDENTITY
             */
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.DAP_ASSETS_IDENTITY_ISSUER);

            // Activity: List of identities
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY.getCode());
            runtimeActivity.setBackActivity(null);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.setStartActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Issuer Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY.getCode());
            runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Create New Issuer Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(SubApps.DAP_ASSETS_IDENTITY_ISSUER, runtimeSubApp);

            /**
             * DAP USER IDENTITY
             */
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.DAP_ASSETS_IDENTITY_USER);

            // Activity: List of identities
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY.getCode());
            runtimeActivity.setBackActivity(null);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.setStartActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("User Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY.getCode());
            runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Create New User Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(SubApps.DAP_ASSETS_IDENTITY_USER, runtimeSubApp);

            /**
             * REDEEM POINT IDENTITY
             */
            runtimeSubApp = new RuntimeSubApp();
            runtimeSubApp.setType(SubApps.DAP_REDEEM_POINT_IDENTITY);

            // Activity: List of identities
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY.getCode());
            runtimeActivity.setBackActivity(null);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.setStartActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Redeem Point Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY.getCode());
            runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Create New Redeem Point Identity");
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setLabelSize(16);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0288D1");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(SubApps.DAP_REDEEM_POINT_IDENTITY, runtimeSubApp);

        } catch (Exception e) {
            String message = CantFactoryResetException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Error on method Factory Reset, setting the structure of the apps";
            String possibleReason = "some null definition";
            throw new CantFactoryResetException(message, cause, context, possibleReason);

        }

    }


    /**
     * Load home screen subApp
     */
    private void loadHomeScreen() {

        homeScreen = new RuntimeSubApp();
        homeScreen.setType(SubApps.CWP_WALLET_MANAGER);
        listSubApp.put(SubApps.CWP_WALLET_MANAGER, homeScreen);

        Activity activity = new Activity();
        /**
         * set type home
         */
        activity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
        Fragment fragment = new Fragment();

        /**
         * Add WalletManager fragment
         */
        fragment = new Fragment();
        fragment.setType(Fragments.CWP_WALLET_MANAGER_MAIN.getKey());
        activity.addFragment(Fragments.CWP_WALLET_MANAGER_MAIN.getKey(), fragment);

        /**
         * Add developer subApp fragment
         */
        fragment = new Fragment();
        fragment.setType(Fragments.CWP_SUB_APP_DEVELOPER.getKey());
        activity.addFragment(Fragments.CWP_SUB_APP_DEVELOPER.getKey(), fragment);


        homeScreen.setStartActivity(activity.getType());
        homeScreen.addActivity(activity);


    }

}
