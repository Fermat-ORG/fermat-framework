package com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.provisory;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by mati on 2016.05.09..
 */
public class SubAppAppsGenerator {


    public  HashMap<String,AppNavigationStructure> listSubApp ;

    public SubAppAppsGenerator() {
        listSubApp = new HashMap<>();
    }
    /*
     * Here is where I actually generate the factory structure of the APP. This method is also useful to reset to the
     * factory structure.
     */
    public void factoryReset() throws Exception {

        try {



            AppNavigationStructure runtimeSubApp = new AppNavigationStructure();
            runtimeSubApp.setPublicKey(SubAppsPublicKeys.CWP_SHEL_LOGIN.getCode());

            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

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

            createDeveloperSubAppNavigationStructure();

            /**
             * End of Developer tabs.
             */

            //wallet factory app
            runtimeSubApp = new AppNavigationStructure();
            String factory_public_key = SubAppsPublicKeys.CWP_FACTORY.getCode();
            runtimeSubApp.setPublicKey(factory_public_key);
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_MAIN);
            runtimeActivity.setColor("#b46a54");
            runtimeActivity.setBackPublicKey(factory_public_key);
//            runtimeSubApp.addPosibleStartActivity(Activities.CWP_WALLET_FACTORY_MAIN);
            //runtimeActivity.setStatusBarColor("");


            statusBar = new StatusBar();
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
            runtimeActivity.addWizard(WizardTypes.CWP_WALLET_FACTORY_CREATE_NEW_PROJECT.getKey(), runtimeWizard);

            /**
             *  Edit Activity
             */
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_EDIT_WALLET);
            runtimeActivity.setColor("#b46a54");
            runtimeActivity.setBackPublicKey(factory_public_key);
            statusBar = new StatusBar();
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
            runtimeActivity.setBackPublicKey(factory_public_key);
            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey(), runtimeFragment);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey(), runtimeFragment);


            runtimeSubApp.addActivity(runtimeActivity);


            /**End Wallet Factory*/

            //wallet Publisher app
            runtimeSubApp = new AppNavigationStructure();

            runtimeSubApp.setPublicKey(SubAppsPublicKeys.CWP_PUBLISHER.getCode());

            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CWP_WALLET_PUBLISHER_MAIN);
            runtimeActivity.setColor("#b46a54");
            //runtimeActivity.setStatusBarColor("#b46a54");556
//            runtimeSubApp.addPosibleStartActivity(Activities.CWP_WALLET_PUBLISHER_MAIN);

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

            runtimeActivity.addWizard(WizardTypes.CWP_WALLET_PUBLISHER_PUBLISH_PROJECT.getKey(), runtimeWizard);

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

            runtimeSubApp = new AppNavigationStructure();

            runtimeSubApp.setPublicKey(SubAppsPublicKeys.CWP_MANAGER.getCode());

            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

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


            runtimeSubApp = new AppNavigationStructure();

            runtimeSubApp.setPublicKey(SubAppsPublicKeys.CWP_RUNTIME.getCode());
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);


//            runtimeSubApp = new AppNavigationStructure();
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


            createAppStoreNavigationStructure();


            /**
             * CCP Intra User Community SubApp
             */
            AppNavigationStructure subAppIntraUser = new AppNavigationStructure();

            String communityPublicKey = SubAppsPublicKeys.CCP_COMMUNITY.getCode();
            subAppIntraUser.setPublicKey(communityPublicKey);
            listSubApp.put(subAppIntraUser.getPublicKey(), subAppIntraUser);

            //Activity Explore
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD.getCode());

            runtimeActivity.setColor("#FF0B46F0");

            statusBar = new StatusBar();
            statusBar.setColor("#0072bb");
            runtimeActivity.setStatusBar(statusBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabelSize(20);
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeTitleBar.setColor("#0072bb");

            runtimeActivity.setTitleBar(runtimeTitleBar);

/*            runtimeMainMenu = new MainMenu();
            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Settings");
            runtimeMainMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setMainMenu(runtimeMainMenu);*/

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());


            runtimeSideMenu = new SideMenu();
            runtimeSideMenu.setBackgroundColor("#0072bb");

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Cripto wallet users");
            runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Connections");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST);
            runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Notifications");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS);
            runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);

            subAppIntraUser.addActivity(runtimeActivity);
            subAppIntraUser.changeActualStartActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD.getCode());

            // Activity: Connection
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST.getCode());
            runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD);
            runtimeActivity.setBackPublicKey(communityPublicKey);
            runtimeActivity.setColor("#FF0B46F0");

            statusBar = new StatusBar();
            statusBar.setColor("#0072bb");
            runtimeActivity.setStatusBar(statusBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Connections");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeTitleBar.setColor("#0072bb");
            runtimeTitleBar.setLabelSize(20);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0072bb");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT.getKey());

            runtimeSideMenu = new SideMenu();
            runtimeSideMenu.setBackgroundColor("#0072bb");

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Cripto wallet users");
            runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Connections");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST);
            runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Notifications");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS);
            runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);
            subAppIntraUser.addActivity(runtimeActivity);

            // Activity: Notifications
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS.getCode());
            runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD);
            runtimeActivity.setBackPublicKey(communityPublicKey);
            runtimeActivity.setColor("#FF0B46F0");

            statusBar = new StatusBar();
            statusBar.setColor("#0072bb");
            runtimeActivity.setStatusBar(statusBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Notifications");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeTitleBar.setColor("#0072bb");
            runtimeTitleBar.setLabelSize(20);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0072bb");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());

            runtimeSideMenu = new SideMenu();
            runtimeSideMenu.setBackgroundColor("#0072bb");
            runtimeSideMenu.setHasFooter(false);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Cripto wallet users");
            runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Connections");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST);
            runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Notifications");
            runtimeMenuItem.setLinkToActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS);
            runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
            runtimeSideMenu.addMenuItem(runtimeMenuItem);

            runtimeActivity.setSideMenu(runtimeSideMenu);

            subAppIntraUser.addActivity(runtimeActivity);

            // Activity: Other Profile
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode());
            runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD);
            runtimeActivity.setBackPublicKey(communityPublicKey);
            runtimeActivity.setColor("#FF0B46F0");

            statusBar = new StatusBar();
            statusBar.setColor("#0072bb");
            runtimeActivity.setStatusBar(statusBar);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeTitleBar.setColor("#0072bb");
            runtimeTitleBar.setIconName("back");
            runtimeTitleBar.setLabelSize(20);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#0072bb");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());

            subAppIntraUser.addActivity(runtimeActivity);
            /**
             * End of community intra user CCP
             */

            /**
             * Start DAP
             */

            /**
             * DAP ASSET FACTORY
             */
            createAssetFactorySubAppNavigationStructure();
            /**
             * DAP ASSET ISSUER COMMUNITY
             */
            createAssetIssuerCommunitySubAppNavigationStructure();

            /**
             * DAP ASSET USER COMMUNITY
             */
            createAssetUserCommunitySubAppNavigationStructure();

            /**
             * DAP REDEEM POINT COMMUNITY
             */
            createRedeemPointCommunitySubAppNavigationStructure();

            /**
             * End DAP
             */

            /**
             * CRYPTO BROKER IDENTITY
             */
            createCryptoBrokerIdentitySubAppNavigationStructure();

            /**
             * CBP CRYPTO CUSTOMER  IDENTITY
             */
            createCryptoCustomerIdentitySubAppNavigationStructure();

            /**
             * CBP CRYPTO BROKER COMMUNITY
             */
            createCryptoBrokerCommunitySubAppNavigationStructure();

            /**
             * CBP CRYPTO CUSTOMER COMMUNITY
             */
            createCryptoCustomerCommunitySubAppNavigationStructure();

            /**
             * CHT CHAT
             */
            createChatSubAppNavigationStructure();

            /**
             * CHT COMMUNITY
             */
            createChatCommunitySubAppNavigationStructure();

            /**
             * CCP INTRA USER IDENTITY
             */

            /*
            *ART MusicPlayer
            */

            createMusicPlayerSubAppNavigationStructure();

           /*
            *ART ARTIST IDENTITY
            */
            //createArtArtistIdentitySubAppNavigationStructure();



            runtimeSubApp = new AppNavigationStructure();

            String artArtistUserIdentityPublicKey = SubAppsPublicKeys.ART_ARTIST_IDENTITY.getCode();
            runtimeSubApp.setPublicKey(artArtistUserIdentityPublicKey);

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.ART_ARTIST_IDENTITY_CREATE_PROFILE);
            runtimeActivity.setActivityType(Activities.ART_ARTIST_IDENTITY_CREATE_PROFILE.getCode());
            //runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.changeActualStartActivity(Activities.ART_ARTIST_IDENTITY_CREATE_PROFILE.getCode());
            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Artist Identity");
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);
            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);
            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey());
            runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey());
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);




            /**
             * ART FAN IDENTITY
             */

            runtimeSubApp = new AppNavigationStructure();

            String artFanUserIdentityPublicKey = SubAppsPublicKeys.ART_FAN_IDENTITY.getCode();
            runtimeSubApp.setPublicKey(artFanUserIdentityPublicKey);
            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.ART_FAN_IDENTITY_CREATE_PROFILE);
            runtimeActivity.setActivityType(Activities.ART_FAN_IDENTITY_CREATE_PROFILE.getCode());
            //runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            //runtimeSubApp.addPosibleStartActivity(Activities.ART_FAN_IDENTITY_CREATE_PROFILE);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Art Fan Identity");
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.ART_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.ART_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);


            /*
             *TKY ARTIST IDENTITY
             */
            //createTkyArtistIdentityNavigationStructure();
            /**
             * Start ART
             */

            /**
             * ART FAN COMMUNITY
             */
            createFanCommunitySubAppNavigationStructure();

            /**
             * ART ARTIST COMMUNITY
             */

            createArtistCommunitySubAppNavigationStructure();




            /**
             * End ART
             */

            runtimeSubApp = new AppNavigationStructure();

            String tkyArtistUserIdentityPublicKey = SubAppsPublicKeys.TKY_ARTIST_IDENTITY.getCode();
            runtimeSubApp.setPublicKey(tkyArtistUserIdentityPublicKey);
            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.TKY_ARTIST_IDENTITY_CREATE_PROFILE);
            runtimeActivity.setActivityType(Activities.TKY_ARTIST_IDENTITY_CREATE_PROFILE.getCode());
            //runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.changeActualStartActivity(Activities.TKY_ARTIST_IDENTITY_CREATE_PROFILE.getCode());
            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Tokenly Artist Identity");
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);
            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);
            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey());
            runtimeActivity.addFragment(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey());
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

            /**
             * CHAT IDENTITY
             */

            runtimeSubApp = new AppNavigationStructure();

            String chatIdentityPublicKey = SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode();
            runtimeSubApp.setPublicKey(chatIdentityPublicKey);

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CHT_CHAT_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.CHT_CHAT_CREATE_IDENTITY.getCode());

            runtimeActivity.setColor("#47BF73");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.changeActualStartActivity(Activities.CHT_CHAT_CREATE_IDENTITY.getCode());

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Chat Identity");
            runtimeTitleBar.setColor("#47BF73");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#47BF73");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CHT_CHAT_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CHT_CHAT_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CHT_CHAT_CREATE_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);



            /*
             *TKY FAN IDENTITY
             */

            runtimeSubApp = new AppNavigationStructure();

            String tkyFanUserIdentityPublicKey = SubAppsPublicKeys.TKY_FAN_IDENTITY.getCode();
            runtimeSubApp.setPublicKey(tkyFanUserIdentityPublicKey);
            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT);
            runtimeActivity.setActivityType(Activities.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getCode());
            //runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.changeActualStartActivity(Activities.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getCode());

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Tokenly Fan Identity");
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

            /*

             */
            runtimeSubApp = new AppNavigationStructure();

            String intraUserIdentityPublicKey = SubAppsPublicKeys.CCP_IDENTITY.getCode();
            runtimeSubApp.setPublicKey(intraUserIdentityPublicKey);

            // Activity: List of identities
//            runtimeActivity = new Activity();
//            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
//            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(null);
//            runtimeActivity.setColor("#03A9F4");
//            runtimeSubApp.addActivity(runtimeActivity);
//
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Intra user Identity");
//            runtimeTitleBar.setIsTitleTextStatic(true);
//            runtimeTitleBar.setColor("#1189a4");
//            runtimeTitleBar.setTitleColor("#ffffff");
//            runtimeTitleBar.setLabelSize(18);
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//
//            statusBar = new StatusBar();
//            statusBar.setColor("#1189a4");
//            runtimeActivity.setStatusBar(statusBar);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());
//            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
//            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
            //runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.changeActualStartActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Identity Manager");
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

            /**
             * DAP IDENTITIES
             */

            /**
             * DAP ISSUER IDENTITY
             */
            runtimeSubApp = new AppNavigationStructure();

            runtimeSubApp.setPublicKey(SubAppsPublicKeys.DAP_IDENTITY_ISSUER.getCode());
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

            // Activity: List of identities
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY.getCode());
            runtimeActivity.setBackActivity(null);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
//            runtimeSubApp.addPosibleStartActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Issuer Identity");
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.changeActualStartActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY.getCode());

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Create New Issuer Identity");
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());


            /**
             * DAP USER IDENTITY
             */
            runtimeSubApp = new AppNavigationStructure();

            runtimeSubApp.setPublicKey(SubAppsPublicKeys.DAP_IDENTITY_USER.getCode());
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

            // Activity: List of identities
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY.getCode());
            runtimeActivity.setBackActivity(null);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
//            runtimeSubApp.addPosibleStartActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("User Identity");
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.changeActualStartActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY.getCode());

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Create New User Identity");
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            /**
             * REDEEM POINT IDENTITY
             */
            runtimeSubApp = new AppNavigationStructure();

            runtimeSubApp.setPublicKey(SubAppsPublicKeys.DAP_IDENTITY_REDEEM.getCode());
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

            // Activity: List of identities
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY.getCode());
            runtimeActivity.setBackActivity(null);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
//            runtimeSubApp.addPosibleStartActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Redeem Point Identity");
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT.getKey());

            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);
            runtimeSubApp.changeActualStartActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY.getCode());

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Create New Redeem Point Identity");
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new Fragment();
            runtimeFragment.setType(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

        } catch (Exception e) {
            String message = FermatException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Error on method Factory Reset, setting the structure of the apps";
            String possibleReason = e.getMessage();
            throw new FermatException(message, cause, context, possibleReason);

        }

    }

    private void createAppStoreNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        StatusBar statusBar;
        TitleBar runtimeTitleBar;
        Fragment runtimeFragment;

        String publicKey = SubAppsPublicKeys.CWP_STORE.getCode();

        runtimeSubApp = new AppNavigationStructure();

        runtimeSubApp.setPublicKey(publicKey);
        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

        //List of wallets
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY);
        runtimeActivity.setActivityType(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY.getCode());
        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY.getCode());

        statusBar = new StatusBar();
        statusBar.setColor("#286C99");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("App Store");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#B46A54");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_MAIN_ACTIVITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_STORE_MAIN_ACTIVITY.getKey());

        //Wallet Details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_STORE_DETAIL_ACTIVITY);
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#B4573C");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Wallet Details");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#B46A54");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_STORE_DETAIL_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_DETAIL_ACTIVITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_STORE_DETAIL_ACTIVITY.getKey());

        //More Details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY);
        runtimeActivity.setColor("#FFFFFF");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_STORE_DETAIL_ACTIVITY);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#AAAAAA");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("More Details");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#222222");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getKey());
    }

    private void createAssetFactorySubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure dapFactory;
        Activity runtimeActivity;
        Tab runtimeTab;
        TabStrip runtimeTabStrip;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        Fragment runtimeFragment;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;

        String dapFactoryPublicKey = SubAppsPublicKeys.DAP_FACTORY.getCode();

        dapFactory = new AppNavigationStructure();


        dapFactory.setPublicKey(dapFactoryPublicKey);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_MAIN);
        runtimeActivity.setColor("#1d1d25");
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);

        statusBar = new StatusBar();
        statusBar.setColor("#1d1d25");

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Asset Factory");
        runtimeTitleBar.setColor("#1d1d25");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#1d1d25");
        runtimeTabStrip.setTabsIndicateColor("#ffffff");
        runtimeTabStrip.setTabsTextColor("#ffffff");

        runtimeTab = new Tab();
        runtimeTab.setLabel("Draft");
        runtimeTab.setFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_EDITABLE_TAB_FRAGMENT);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Issued");
        runtimeTab.setFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_PUBLISHED_TAB_FRAGMENT);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_FACTORY_EDITABLE_TAB_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_EDITABLE_TAB_FRAGMENT.getKey(), runtimeFragment);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_FACTORY_PUBLISHED_TAB_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_PUBLISHED_TAB_FRAGMENT.getKey(), runtimeFragment);

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#1d1d25");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(dapFactoryPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setAppLinkPublicKey(dapFactoryPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapFactory.addActivity(runtimeActivity);
        dapFactory.changeActualStartActivity(Activities.DAP_MAIN.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_EDITOR_ACTIVITY);
        runtimeActivity.setBackActivity(Activities.DAP_MAIN);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor("#1d1d25");

        statusBar = new StatusBar();
        statusBar.setColor("#1d1d25");

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Draft Asset");
        runtimeTitleBar.setColor("#1d1d25");

        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY.getKey(), runtimeFragment);

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY SETTINGS
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS);
        runtimeActivity.setBackActivity(Activities.DAP_MAIN);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor("#1d1d25");

        statusBar = new StatusBar();
        statusBar.setColor("#1d1d25");

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setColor("#1d1d25");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS.getKey(), runtimeFragment);

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#1d1d25");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(dapFactoryPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setAppLinkPublicKey(dapFactoryPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY SETTINGS_NETWORK
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN);
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor("#1d1d25");

        statusBar = new StatusBar();
        statusBar.setColor("#1d1d25");

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Network");
        runtimeTitleBar.setColor("#1d1d25");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN.getKey(), runtimeFragment);

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY SETTINGS_NOTIFICATIONS
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS);
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor("#1d1d25");

        statusBar = new StatusBar();
        statusBar.setColor("#1d1d25");

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setColor("#1d1d25");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS.getKey(), runtimeFragment);

        dapFactory.addActivity(runtimeActivity);

        listSubApp.put(dapFactory.getPublicKey(), dapFactory);
    }

    private void createAssetIssuerCommunitySubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure dapAssetIssuerCommunity;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        Fragment runtimeFragment;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;

        String communityIssuerPublicKey = SubAppsPublicKeys.DAP_COMMUNITY_ISSUER.getCode();

        dapAssetIssuerCommunity = new AppNavigationStructure();

        dapAssetIssuerCommunity.setPublicKey(communityIssuerPublicKey);
        dapAssetIssuerCommunity.changeActualStartActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Asset Issuer Community");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_ISSUER_COMMUNITY_CONNECTIONS_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_CONNECTIONS_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_CONNECTIONS_LIST_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Issuer Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_LIST_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_LIST_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Issuer Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        //INI ISSUER SETTINGS ACTIVITY
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        listSubApp.put(dapAssetIssuerCommunity.getPublicKey(), dapAssetIssuerCommunity);
    }

    private void createAssetUserCommunitySubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure dapAssetUserCommunity;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        Fragment runtimeFragment;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;

        String communityUserPublicKey = SubAppsPublicKeys.DAP_COMMUNITY_USER.getCode();

        dapAssetUserCommunity = new AppNavigationStructure();

        dapAssetUserCommunity.setPublicKey(communityUserPublicKey);
        dapAssetUserCommunity.changeActualStartActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Asset User Community");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getKey());
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getKey(), runtimeFragment);

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Manage Groups");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        //INI User other profile activity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("User Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());

        dapAssetUserCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_LIST_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_LIST_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("User Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_OTHER_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_OTHER_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_OTHER_PROFILE_FRAGMENT.getKey());

        dapAssetUserCommunity.addActivity(runtimeActivity);

        //INI User connections list activity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Manage Groups");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        // Activity: Manage Groups
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Manage Groups");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Manage Groups");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        //runtimeTitleBar.setLabel("GROUP NAME");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Manage Groups");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        //runtimeTitleBar.setLabel("GROUP NAME");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Manage Groups");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Manage Groups");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        //INI USER SETTINGS ACTIVITY
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_USER_COMMUNITY_SETTINGS_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_SETTINGS_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_SETTINGS_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Manage Groups");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        listSubApp.put(dapAssetUserCommunity.getPublicKey(), dapAssetUserCommunity);
    }

    private void createTkyArtistIdentityNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure runtimeSubApp = new AppNavigationStructure();
        Activity runtimeActivity;
        StatusBar statusBar;




        String tkyFanUserIdentityPublicKey = SubAppsPublicKeys.TKY_ARTIST_IDENTITY.getCode();
        runtimeSubApp.setPublicKey(tkyFanUserIdentityPublicKey);
        // Activity: Create New Identity
        runtimeActivity = new Activity();

        runtimeActivity.setType(Activities.TKY_ARTIST_IDENTITY);
        runtimeActivity.setActivityType(Activities.TKY_ARTIST_IDENTITY.getCode());
        //runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
        runtimeActivity.setColor("#03A9F4");
        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.TKY_ARTIST_IDENTITY.getCode());

        TitleBar runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Tokenly Identity");
        runtimeTitleBar.setColor("#1189a4");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#1189a4");
        runtimeActivity.setStatusBar(statusBar);

        Fragment runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey());
        runtimeActivity.addFragment(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey());

        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);


    }

    private void createArtArtistIdentitySubAgracppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure artArtistIdentity;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        Fragment runtimeFragment;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;

        String IdentityArtistPublicKey = SubAppsPublicKeys.ART_ARTIST_IDENTITY.getCode();

        artArtistIdentity = new AppNavigationStructure();

        artArtistIdentity.setPublicKey(IdentityArtistPublicKey);
        artArtistIdentity.changeActualStartActivity(Activities.ART_ARTIST_IDENTITY_CREATE_PROFILE.getCode());

        runtimeActivity = new Activity();
        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);


        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Artist Identity");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");

        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey());
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey(), runtimeFragment);

/*
        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        */

        artArtistIdentity.addActivity(runtimeActivity);

        listSubApp.put(artArtistIdentity.getPublicKey(), artArtistIdentity);
    } //ArtArtistIdentity

    private void createRedeemPointCommunitySubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure dapAssetRedeemPointCommunity;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        Fragment runtimeFragment;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;

        String communityRedeemPointPublicKey = SubAppsPublicKeys.DAP_COMMUNITY_REDEEM.getCode();

        dapAssetRedeemPointCommunity = new AppNavigationStructure();

        dapAssetRedeemPointCommunity.setPublicKey(communityRedeemPointPublicKey);
        dapAssetRedeemPointCommunity.changeActualStartActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Redeem Point Community");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getKey());
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getKey(), runtimeFragment);

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        //INI redeem point community other profile

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityRedeemPointPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Redeem Point Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        //INI Redeem Point connections list activity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityRedeemPointPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        //INI REDEEM POINT SETTINGS ACTIVITY
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityRedeemPointPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_LIST_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_LIST_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setBackPublicKey(communityRedeemPointPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Redeem Point Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityRedeemPointPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        listSubApp.put(dapAssetRedeemPointCommunity.getPublicKey(), dapAssetRedeemPointCommunity);
    }

    private void createCryptoCustomerIdentitySubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        Fragment runtimeFragment;

        String publicKey = SubAppsPublicKeys.CBP_CUSTOMER_IDENTITY.getCode();

        runtimeSubApp = new AppNavigationStructure();

        runtimeSubApp.setPublicKey(publicKey);
        runtimeSubApp.setPlatform(Platforms.CRYPTO_BROKER_PLATFORM);
        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

        // Activity: List of identities
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY.getCode());
        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY.getCode());

        statusBar = new StatusBar();
        statusBar.setColor("#0e738b");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Customer Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#1189a5");
        //runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());

        // Activity: Create New Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#0e738b");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("New Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#1189a5");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

        // Activity: Edit Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#0e738b");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Edit Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#1189a5");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.         CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.     CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());
    }

    private void createCryptoBrokerIdentitySubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        StatusBar statusBar;
        TitleBar runtimeTitleBar;
        Fragment runtimeFragment;

        String publicKey = SubAppsPublicKeys.CBP_BROKER_IDENTITY.getCode();

        runtimeSubApp = new AppNavigationStructure();
        runtimeSubApp.setPublicKey(publicKey);
        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

        // Activity: List of identities
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY.getCode());
        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY.getCode());

        statusBar = new StatusBar();
        statusBar.setColor("#9A12B09F");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broker Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#9A12B09F");
        //runtimeTitleBar.setIconName("Back");
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
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#9A12B09F");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("New Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#9A12B09F");
        runtimeTitleBar.setIconName("Back");
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
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#9A12B09F");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Edit Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#9A12B09F");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());
    }

    private void createCryptoBrokerCommunitySubAppNavigationStructure() throws InvalidParameterException {
        TitleBar runtimeTitleBar;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;
        StatusBar statusBar;
        Activity runtimeActivity;
        Fragment runtimeFragment;

        AppNavigationStructure subAppBrokerCommunity = new AppNavigationStructure();

        String communityPublicKey = SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode();
        subAppBrokerCommunity.setPlatform(Platforms.CRYPTO_BROKER_PLATFORM);
        subAppBrokerCommunity.setPublicKey(communityPublicKey);

        //Side Menu definition
        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Crypto Broker Users");
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);


        //Activity: CONNECTION_WORLD
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getCode());
        subAppBrokerCommunity.changeActualStartActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Crypto Broker Users");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppBrokerCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_FRIEND_LIST
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppBrokerCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_NOTIFICATIONS
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppBrokerCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_OTHER_PROFILE
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_OTHER_PROFILE);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());

        subAppBrokerCommunity.addActivity(runtimeActivity);


        listSubApp.put(subAppBrokerCommunity.getPublicKey(), subAppBrokerCommunity);
    }

    private void createCryptoCustomerCommunitySubAppNavigationStructure() throws InvalidParameterException {
        TitleBar runtimeTitleBar;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;
        StatusBar statusBar;
        Activity runtimeActivity;
        Fragment runtimeFragment;

        AppNavigationStructure subAppCustomerCommunity = new AppNavigationStructure();

        String communityPublicKey = SubAppsPublicKeys.CBP_CUSTOMER_COMMUNITY.getCode();
        subAppCustomerCommunity.setPublicKey(communityPublicKey);
        subAppCustomerCommunity.setPlatform(Platforms.CRYPTO_BROKER_PLATFORM);

        //Side Menu definition
        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Crypto Customer Users");
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);


        //Activity: CONNECTION_WORLD
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getCode());
        subAppCustomerCommunity.changeActualStartActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Crypto Customer Users");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppCustomerCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_FRIEND_LIST
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppCustomerCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_NOTIFICATIONS
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppCustomerCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_OTHER_PROFILE
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());

        subAppCustomerCommunity.addActivity(runtimeActivity);


        listSubApp.put(subAppCustomerCommunity.getPublicKey(), subAppCustomerCommunity);
    }

    private void createDeveloperSubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        StatusBar statusBar;
        TitleBar runtimeTitleBar;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;
        Fragment runtimeFragment;
        final int titleBarLabelSize = 20;

        runtimeSubApp = new AppNavigationStructure();
        runtimeSubApp.setPublicKey(SubAppsPublicKeys.PIP_DEVELOPER.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_SUB_APP_ALL_DEVELOPER);
        runtimeActivity.setActivityType(Activities.CWP_SUB_APP_ALL_DEVELOPER.getCode());
        runtimeActivity.setColor("#b46a54");

        statusBar = new StatusBar();
        statusBar.setColor("#d07b62");
        runtimeActivity.setStatusBar(statusBar);

        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CWP_SUB_APP_ALL_DEVELOPER.getCode());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Developer");
        runtimeTitleBar.setLabelSize(titleBarLabelSize);
        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#d07b62");
        runtimeActivity.setTitleBar(runtimeTitleBar);

//        runtimeTabStrip = new TabStrip();
//        runtimeTabStrip.setTabsColor("#d07b62");
//        runtimeTabStrip.setTabsTextColor("#FFFFFF");
//        runtimeTabStrip.setTabsIndicateColor("#b46a54");
//        runtimeActivity.setTabStrip(runtimeTabStrip);
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("DataBase Tools");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT);
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());
//        runtimeTabStrip.addTab(runtimeTab);
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Log Tools");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT);
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey(), runtimeFragment);
//        runtimeTabStrip.addTab(runtimeTab);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE);
        runtimeActivity.setActivityType(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE.getCode());
        runtimeActivity.setBackActivity(Activities.CWP_SUB_APP_ALL_DEVELOPER);
        runtimeActivity.setBackPublicKey(runtimeSubApp.getPublicKey());
        runtimeActivity.setColor("#b46a54");

        statusBar = new StatusBar();
        statusBar.setColor("#d07b62");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Developer > Databases");
        runtimeTitleBar.setLabelSize(titleBarLabelSize);
        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#d07b62");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE.getCode());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST);
        runtimeActivity.setActivityType(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE);
        runtimeActivity.setBackPublicKey(runtimeSubApp.getPublicKey());
        runtimeActivity.setColor("#b46a54");

        statusBar = new StatusBar();
        statusBar.setColor("#d07b62");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Developer > Tables");
        runtimeTitleBar.setLabelSize(titleBarLabelSize);
        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#d07b62");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST.getCode());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST);
        runtimeActivity.setActivityType(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST);
        runtimeActivity.setBackPublicKey(runtimeSubApp.getPublicKey());
        runtimeActivity.setColor("#b46a54");

        statusBar = new StatusBar();
        statusBar.setColor("#d07b62");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Developer > Records");
        runtimeTitleBar.setLabelSize(titleBarLabelSize);
        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#d07b62");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST.getCode());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey());
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey(), runtimeFragment);
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey(), runtimeFragment);
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey(), runtimeFragment);

        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    }

    private void createChatSubAppNavigationStructure() throws InvalidParameterException {

        AppNavigationStructure chtChat;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        Fragment runtimeFragment;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;

        chtChat = new AppNavigationStructure();
        String chatPublicKey = SubAppsPublicKeys.CHT_OPEN_CHAT.getCode();
        chtChat.setPublicKey(chatPublicKey);
        listSubApp.put(chtChat.getPublicKey(), chtChat);

        //Activity Chat
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.changeActualStartActivity(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Fermat Chat");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());

        //Menu Tabs
        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#000000");
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#47BF73");
//        runtimeTabStrip.setBackgroundColor(0xFFFFFF);
//        runtimeTabStrip.setDividerColor(0xFFFFFF);
        runtimeActivity.setTabStrip(runtimeTabStrip);

        //Tabs Chats
        runtimeTab = new Tab();
        runtimeTab.setLabel("CHATS");
        runtimeTab.setFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());
        runtimeTabStrip.addTab(runtimeTab);

        //Tabs Contacts
        runtimeTab = new Tab();
        runtimeTab.setLabel("CONTACTS");
        runtimeTab.setFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey(), runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);

        chtChat.addActivity(runtimeActivity);
        listSubApp.put(chtChat.getPublicKey(), chtChat);

        // Activity: Broadcast step one
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broadcast");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey());

        // Activity: Broadcast step two
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broadcast");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey());

        // Activity: Contacts
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_CONTACTLIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_CONTACTLIST.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        //chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contacts");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey());
        // Activity: Wizard step two scheduled
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_SCHEDULED_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_SCHEDULED_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contacts");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_SCHEDULED_DETAIL.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_SCHEDULED_DETAIL.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_SCHEDULED_DETAIL.getKey());

//        //Menu Tabs
//        runtimeTabStrip = new TabStrip();
//        runtimeTabStrip.setTabsColor("#000000");
//        runtimeTabStrip.setTabsTextColor("#FFFFFF");
//        runtimeTabStrip.setTabsIndicateColor("#47BF73");
//        //runtimeTabStrip.setBackgroundColor(0xFFFFFF);
//        //runtimeTabStrip.setDividerColor(0xFFFFFF);
//        runtimeActivity.setTabStrip(runtimeTabStrip);
//
//        //Tabs Chats
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("CHATS");
//        runtimeTab.setFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT);
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey(), runtimeFragment);
//        runtimeTabStrip.addTab(runtimeTab);
//
//        //Tabs Contacts
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("CONTACTS");
//        runtimeTab.setFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT);
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey());
//        runtimeTabStrip.addTab(runtimeTab);

        chtChat.addActivity(runtimeActivity);
        listSubApp.put(chtChat.getPublicKey(), chtChat);

        // Activity: Contact Detail
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_CONTACT_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_CONTACT_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contact Profile");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_CONTACT_DETAIL_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CONTACT_DETAIL_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CONTACT_DETAIL_FRAGMENT.getKey());

        // Activity: Edit Contact
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_EDIT_CONTACT);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_EDIT_CONTACT.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Edit Contact Profile");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_EDIT_CONTACT_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_EDIT_CONTACT_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_EDIT_CONTACT_FRAGMENT.getKey());

        // Activity: Connection list
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_CONNECTIONLIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_CONNECTIONLIST.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Available Connections");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_CONNECTIONLIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CONNECTIONLIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CONNECTIONLIST_FRAGMENT.getKey());

        // Activity: Chat Detail Messages
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_MESSAGE_LIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_MESSAGE_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("");//set title in fragment
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_MESSAGE_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_MESSAGE_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_MESSAGE_LIST_FRAGMENT.getKey());


        // Activity: Broadcast
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broadcast");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey());

        // Activity: Broadcast 2
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broadcast");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey());





        // Activity: Profile list
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_PROFILELIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_PROFILELIST.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_PROFILELIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Your Profiles");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_PROFILELIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_PROFILELIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_PROFILELIST_FRAGMENT.getKey());

        // Activity: Profile Detail
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("My Profile");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_PROFILE_DETAIL_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_PROFILE_DETAIL_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_PROFILE_DETAIL_FRAGMENT.getKey());

        // Activity: Send Error Report
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Error Report");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_CHAT_OPEN_SEND_ERROR_REPORT_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_SEND_ERROR_REPORT_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_SEND_ERROR_REPORT_FRAGMENT.getKey());

        listSubApp.put(chtChat.getPublicKey(), chtChat);
    }

    private void createChatCommunitySubAppNavigationStructure() throws InvalidParameterException {
        TitleBar runtimeTitleBar;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;
        StatusBar statusBar;
        Activity runtimeActivity;
        Fragment runtimeFragment;
        AppNavigationStructure chtComm;

        chtComm = new AppNavigationStructure();

        String communityPublicKey = SubAppsPublicKeys.CHT_COMMUNITY.getCode();
        chtComm.setPlatform(Platforms.CHAT_PLATFORM);
        chtComm.setPublicKey(communityPublicKey);
        //listSubApp.put(chtComm.getPublicKey(), chtComm);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#FFFFFF");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Chat Community");
        runtimeMenuItem.setIcon("cht_ic_home");
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Go to Chat");
//        runtimeMenuItem.setLinkToActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
//        runtimeMenuItem.setAppLinkPublicKey(SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        //Activity Explore
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setActivityType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD.getCode());
        chtComm.changeActualStartActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD.getCode());
        //runtimeActivity.setColor("#FF0B46F0");
        runtimeActivity.setBackgroundColor("F9F9F9");

        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Chat Community");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        chtComm.addActivity(runtimeActivity);

        // Activity: Connection
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST);
        runtimeActivity.setActivityType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        //runtimeActivity.setColor("#FF0B46F0");
        runtimeActivity.setBackgroundColor("F9F9F9");

        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        chtComm.addActivity(runtimeActivity);

        // Activity: Notifications
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeActivity.setActivityType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        //runtimeActivity.setColor("#FF0B46F0");
        runtimeActivity.setBackgroundColor("F9F9F9");

        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        chtComm.addActivity(runtimeActivity);

        // Activity: Other Profile
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE);
        runtimeActivity.setActivityType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        //runtimeActivity.setColor("#FF0B46F0");
        runtimeActivity.setBackgroundColor("F9F9F9");

        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeTitleBar.setIconName("back");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());

        chtComm.addActivity(runtimeActivity);
        listSubApp.put(chtComm.getPublicKey(), chtComm);
    }

    private void createFanCommunitySubAppNavigationStructure() throws InvalidParameterException {
        TitleBar runtimeTitleBar;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;
        StatusBar statusBar;
        Activity runtimeActivity;
        Fragment runtimeFragment;

        AppNavigationStructure subAppFanCommunity = new AppNavigationStructure();

        String communityPublicKey = SubAppsPublicKeys.ART_FAN_COMMUNITY.getCode();
        subAppFanCommunity.setPublicKey(communityPublicKey);

        //Side Menu definition
        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Fan Users");
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_FRIEND_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);


        //Activity: CONNECTION_WORLD
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getCode());
        subAppFanCommunity.changeActualStartActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Fan Users");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppFanCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_FRIEND_LIST
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_FRIEND_LIST);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_FRIEND_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_FRIEND_LIST.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppFanCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_NOTIFICATIONS
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_NOTIFICATIONS.getCode());
        runtimeActivity.setBackActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppFanCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_OTHER_PROFILE
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_OTHER_PROFILE);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());

        subAppFanCommunity.addActivity(runtimeActivity);


        listSubApp.put(subAppFanCommunity.getPublicKey(), subAppFanCommunity);
    }

    private void createArtistCommunitySubAppNavigationStructure() throws InvalidParameterException {
        TitleBar runtimeTitleBar;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;
        StatusBar statusBar;
        Activity runtimeActivity;
        Fragment runtimeFragment;

        AppNavigationStructure subAppArtistCommunity = new AppNavigationStructure();
        String communityPublicKey = SubAppsPublicKeys.ART_ARTIST_COMMUNITY.getCode();
        subAppArtistCommunity.setPublicKey(communityPublicKey);

        //Side Menu definition
        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Artist Users");
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_FRIEND_LIST);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);


        //Activity: CONNECTION_WORLD
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getCode());
        subAppArtistCommunity.changeActualStartActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Artist Users");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppArtistCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_FRIEND_LIST
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_FRIEND_LIST);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_FRIEND_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_FRIEND_LIST.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppArtistCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_NOTIFICATIONS
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_NOTIFICATIONS);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_NOTIFICATIONS.getCode());
        runtimeActivity.setBackActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppArtistCommunity.addActivity(runtimeActivity);


        // Activity: CONNECTION_OTHER_PROFILE
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_OTHER_PROFILE);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());

        subAppArtistCommunity.addActivity(runtimeActivity);


        listSubApp.put(subAppArtistCommunity.getPublicKey(), subAppArtistCommunity);
    }

    private void createMusicPlayerSubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        StatusBar statusBar;
        TitleBar runtimeTitleBar;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;
        Fragment runtimeFragment;
        final int titleBarLabelSize = 20;

        runtimeSubApp = new AppNavigationStructure();
        runtimeSubApp.setPublicKey(SubAppsPublicKeys.ART_MUSIC_PLAYER.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_MUSIC_PLAYER_MAIN_ACTIVITY);
        runtimeActivity.setActivityType(Activities.ART_MUSIC_PLAYER_MAIN_ACTIVITY.getCode());
        runtimeActivity.setColor("#ffffff");

        statusBar = new StatusBar();
        statusBar.setColor("#000000");
        runtimeActivity.setStatusBar(statusBar);
        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.ART_MUSIC_PLAYER_MAIN_ACTIVITY.getCode());
        //   runtimeSubApp.addPosibleStartActivity(Activities.ART_MUSIC_PLAYER_MAIN_ACTIVITY);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Fermat Music Player");
        runtimeTitleBar.setLabelSize(titleBarLabelSize);
        runtimeTitleBar.setTitleColor("#000000");
        runtimeTitleBar.setColor("#fafafa");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.ART_MUSIC_PLAYER_MAIN_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.ART_MUSIC_PLAYER_MAIN_ACTIVITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_MUSIC_PLAYER_MAIN_ACTIVITY.getKey());


        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    }

}
