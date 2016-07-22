package com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.provisory;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FontType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatRuntimeFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatTextViewRuntime;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WizardPage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.drawables.Badge;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardPageTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuChangeActivityOnPressEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuViewsAvailables;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.search_view.SearchMenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.search_view.SearchViewOnPressEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.tab_layout.TabBadgeView;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;

import java.util.HashMap;


/**
 * Created by mati on 2016.05.09..
 */
public class SubAppAppsGenerator {


    public HashMap<String, AppNavigationStructure> listSubApp;

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

            FermatRuntimeFragment runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_SHELL_LOGIN.getKey());
            runtimeActivity.addFragment(Fragments.CWP_SHELL_LOGIN.getKey(), runtimeFragment);

            TitleBar runtimeTitleBar;
            SideMenu runtimeSideMenu;
            OptionsMenu runtimeOptionsMenu;
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
//            runtimeTab.setFragment(new FermatRuntimeFragment(1,)Fragments.CWP_WALLET_FACTORY_DEVELOPER_PROJECTS);
//
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Edit Mode");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_FACTORY_AVAILABLE_PROJECTS);

            runtimeTabStrip.addTab(runtimeTab);


            runtimeActivity.setTabStrip(runtimeTabStrip);
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_FACTORY_DEVELOPER_PROJECTS.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_FACTORY_DEVELOPER_PROJECTS.getKey(), runtimeFragment);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_FACTORY_AVAILABLE_PROJECTS.getKey());
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
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTab = new Tab();
            runtimeTab.setLabel("Contacts");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
            runtimeTabStrip.addTab(runtimeTab);

            runtimeTabStrip.setDividerColor(0x72af9c);
            //runtimeTabStrip.setBackgroundColor("#72af9c");
            runtimeActivity.setTabStrip(runtimeTabStrip);
            runtimeActivity.setBackPublicKey(factory_public_key);
            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey(), runtimeFragment);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
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


            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_PUBLISHER_MAIN_FRAGMENT.getKey());
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

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_MANAGER_MAIN.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_MANAGER_MAIN.getKey(), runtimeFragment);


            //Desktop page Developer sub App
            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_SUB_APP_DEVELOPER.getKey());
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


            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_PUBLISHER_MAIN_FRAGMENT.getKey());
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
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Products");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Reviews");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Chat");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("History");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Map");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP);
            runtimeTabStrip.addTab(runtimeTab);


            runtimeTabStrip.setDividerColor(0xFFFFFFFF);
            runtimeTabStrip.setIndicatorColor(0xFFFFFFFF);
            runtimeTabStrip.setIndicatorHeight(9);
            runtimeTabStrip.setBackgroundColor(0xFF76dc4a);
            runtimeTabStrip.setTextColor(0xFFFFFFFF);
            runtimeActivity.setTabStrip(runtimeTabStrip);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP.getKey(), runtimeFragment);


            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS.getKey(), runtimeFragment);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS.getKey(), runtimeFragment);


            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT.getKey(), runtimeFragment);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY.getKey());
            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY.getKey(), runtimeFragment);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP.getKey());
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
           /* AppNavigationStructure subAppIntraUser = new AppNavigationStructure();

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
            runtimeTitleBar.setLabelSize(16);
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeTitleBar.setColor("#0072bb");

            runtimeActivity.setTitleBar(runtimeTitleBar);

            //Menu
            OptionsMenu optionsMenu = new OptionsMenu();
            Owner owner = new Owner();
            owner.setOwnerAppPublicKey(SubAppsPublicKeys.CCP_COMMUNITY.getCode());

            //Search optionMenu
            OptionMenuItem optionMenuItem = new OptionMenuItem(1);
            optionMenuItem.setFermatDrawable(new FermatDrawable(1, "search", owner, SourceLocation.DEVELOPER_RESOURCES));
            optionMenuItem.setLabel("Search");
            optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
            optionMenuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
            optionsMenu.addMenuItem(optionMenuItem);

            //Location optionMenu
            optionMenuItem = new OptionMenuItem(2);
            optionMenuItem.setFermatDrawable(new FermatDrawable(2, "ic_location", owner, SourceLocation.DEVELOPER_RESOURCES));
            optionMenuItem.setLabel("location");
            optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
            optionsMenu.addMenuItem(optionMenuItem);

            //Help optionMenu
            optionMenuItem = new OptionMenuItem(3);
            optionMenuItem.setFermatDrawable(new FermatDrawable(3, "ic_help", owner, SourceLocation.DEVELOPER_RESOURCES));
            optionMenuItem.setLabel("Help");
            optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
            optionsMenu.addMenuItem(optionMenuItem);

            runtimeActivity.setOptionsMenu(optionsMenu);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());


            runtimeSideMenu = new SideMenu();
            runtimeSideMenu.setBackgroundColor("#0072bb");

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Crypto wallet users");
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

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT.getKey());

            runtimeSideMenu = new SideMenu();
            runtimeSideMenu.setBackgroundColor("#0072bb");

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Crypto wallet users");
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

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());

            runtimeSideMenu = new SideMenu();
            runtimeSideMenu.setBackgroundColor("#0072bb");
            runtimeSideMenu.setHasFooter(false);

            runtimeMenuItem = new MenuItem();
            runtimeMenuItem.setLabel("Crypto wallet users");
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

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());

            subAppIntraUser.addActivity(runtimeActivity);*/
            /**
             * End of community intra user CCP
             */

            /**
             * Start DAP

             * DAP ASSET ISSUER IDENTITY
             */
            createAssetIssuerIdentitySubAppNavigationStructure();
            /**
             * DAP ASSET USER IDENTITY
             */
            createAssetUserIdentitySubAppNavigationStructure();
            /**
             * DAP ASSET REDEEM POINT IDENTITY
             */
            createAssetRedeemPointIdentitySubAppNavigationStructure();
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
             * Start CBP

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
             * End CBP
             */

            /**
             * Start CHT

             * CHT CHAT
             */
            createChatSubAppNavigationStructure();
            /**
             * CHT COMMUNITY
             */
            createChatCommunitySubAppNavigationStructure();
            /**
             * CHAT IDENTITY
             */
            createChatIdentitySubAppNavigationStructure();
            /**
             * End CHT
             */

            /**
             * CCP INTRA USER COMMUNITY
             */

            createIntraUserCommunitySubAppNavigationStructure();

            /*
            *ART MusicPlayer
            */
            createMusicPlayerSubAppNavigationStructure();
           /*
            *ART ARTIST IDENTITY
            */
            //createArtArtistIdentitySubAppNavigationStructure();
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
            runtimeTitleBar.setTitleColor("#3A3A3A");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);
            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);
            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey());
            runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey());
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);


            /**
             * ART FAN IDENTITY
             */

            runtimeSubApp = new AppNavigationStructure();

         /*
             *TKY ARTIST IDENTITY
             */
            //runtimeSubApp = new RuntimeSubApp();
            //runtimeSubApp.setType(SubApps.ART_FAN_IDENTITY);
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
            runtimeTitleBar.setColor("#FFFFFF");
            runtimeTitleBar.setTitleColor("#000000");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.ART_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);


            //createTkyArtistIdentityNavigationStructure();

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
            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey());
            runtimeActivity.addFragment(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey());
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

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

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
//            runtimeFragment = new FermatRuntimeFragment();
//            runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());
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
            runtimeTitleBar.setColor("#21386D");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);

            statusBar = new StatusBar();
            statusBar.setColor("#48000000");
            runtimeActivity.setStatusBar(statusBar);

            //Menu


            OptionsMenu optionsMenu = new OptionsMenu();
            Owner owner = new Owner();
            owner.setOwnerAppPublicKey(SubAppsPublicKeys.CCP_IDENTITY.getCode());


            //Search optionMenu
            OptionMenuItem optionMenuItem = new OptionMenuItem(2);
            optionMenuItem.setFermatDrawable(new FermatDrawable(2, "ic_location", owner, SourceLocation.DEVELOPER_RESOURCES));
            optionMenuItem.setLabel("Geolocation");
            optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
            optionsMenu.addMenuItem(optionMenuItem);

            //Help optionMenu
            optionMenuItem = new OptionMenuItem(1);
            optionMenuItem.setFermatDrawable(new FermatDrawable(1, "ic_help", owner, SourceLocation.DEVELOPER_RESOURCES));
            optionMenuItem.setLabel("Help");
            optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
            optionsMenu.addMenuItem(optionMenuItem);


            runtimeActivity.setOptionsMenu(optionsMenu);


            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());


            // Activity: Create New Identity
            runtimeActivity = new Activity();
            runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_IDENTITY_GEOLOCATION_IDENTITY);
            runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_IDENTITY_GEOLOCATION_IDENTITY.getCode());
            //runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);
            runtimeActivity.setColor("#03A9F4");
            runtimeSubApp.addActivity(runtimeActivity);

            runtimeTitleBar = new TitleBar();
            runtimeTitleBar.setLabel("Geolocation Settings");
            runtimeTitleBar.setColor("#1189a4");
            runtimeTitleBar.setTitleColor("#ffffff");
            runtimeTitleBar.setLabelSize(18);
            runtimeTitleBar.setIsTitleTextStatic(true);
            runtimeActivity.setTitleBar(runtimeTitleBar);



            optionsMenu = new OptionsMenu();
            owner = new Owner();
            owner.setOwnerAppPublicKey(SubAppsPublicKeys.CCP_IDENTITY.getCode());

            //Help optionMenu
            optionMenuItem = new OptionMenuItem(1);
            optionMenuItem.setFermatDrawable(new FermatDrawable(1, "ic_help", owner, SourceLocation.DEVELOPER_RESOURCES));
            optionMenuItem.setLabel("Help");
            optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
            optionsMenu.addMenuItem(optionMenuItem);
            runtimeActivity.setOptionsMenu(optionsMenu);

            statusBar = new StatusBar();
            statusBar.setColor("#1189a4");
            runtimeActivity.setStatusBar(statusBar);

            runtimeFragment = new FermatRuntimeFragment();
            runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_INTRA_IDENTITY_GEOLOCATION_IDENTITY.getKey());
            runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_IDENTITY_GEOLOCATION_IDENTITY.getKey(), runtimeFragment);
            runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_IDENTITY_GEOLOCATION_IDENTITY.getKey());

            runtimeSubApp.addActivity(runtimeActivity);


            listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

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
        FermatRuntimeFragment runtimeFragment;

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

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_STORE_MAIN_ACTIVITY.getKey());
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

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_STORE_DETAIL_ACTIVITY.getKey());
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

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getKey());
    }

    private void createAssetIssuerIdentitySubAppNavigationStructure() throws InvalidParameterException {

        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
        OptionsMenu optionsMenu;
        OptionMenuItem menuItem;
        Owner owner;
        String identityPublicKey = SubAppsPublicKeys.DAP_IDENTITY_ISSUER.getCode();

        runtimeSubApp = new AppNavigationStructure();

        owner = new Owner();
        owner.setOwnerAppPublicKey(identityPublicKey);

        runtimeSubApp.setPublicKey(identityPublicKey);
        runtimeSubApp.setPlatform(Platforms.DIGITAL_ASSET_PLATFORM);

        // Activity: List of identities
//            runtimeActivity = new Activity();
//            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY);
//            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(null);
//            runtimeActivity.setColor("#03A9F4");
//            runtimeSubApp.addActivity(runtimeActivity);
////            runtimeSubApp.addPosibleStartActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY);

//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Issuer Identity");
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
//            runtimeFragment = new FermatRuntimeFragment();
//            runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT.getKey());
//            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
//            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT.getKey());

        // Activity: Create New Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY);
        runtimeActivity.setColor("#03A9F4");

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Asset Issuer Identity");
        runtimeTitleBar.setIconName("back");
        runtimeTitleBar.setColor("#1B1B1B");
        runtimeTitleBar.setTitleColor("#ffffff");

        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);

        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#1B1B1B");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(1);
        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_location", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Location");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(2);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY.getCode());

        //Activity Geolocation
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_GEOLOCATION_ACTIVITY);
        runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_GEOLOCATION_ACTIVITY.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setBackPublicKey(identityPublicKey);
//        runtimeActivity.setBackgroundColor("#F9F9F9");

        statusBar = new StatusBar();
        statusBar.setColor("#1B1B1B");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Geolocation");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#1B1B1B");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIconName("back");
        runtimeTitleBar.setLabelSize(18);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_GEOLOCATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());

        runtimeSubApp.addActivity(runtimeActivity);

        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    }

    private void createAssetUserIdentitySubAppNavigationStructure() throws InvalidParameterException {

        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
        OptionsMenu optionsMenu;
        OptionMenuItem menuItem;
        Owner owner;
        String identityPublicKey = SubAppsPublicKeys.DAP_IDENTITY_USER.getCode();

        runtimeSubApp = new AppNavigationStructure();

        owner = new Owner();
        owner.setOwnerAppPublicKey(identityPublicKey);

        runtimeSubApp.setPublicKey(identityPublicKey);
        runtimeSubApp.setPlatform(Platforms.DIGITAL_ASSET_PLATFORM);

        // Activity: List of identities
//            runtimeActivity = new Activity();
//            runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY);
//            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(null);
//            runtimeActivity.setColor("#03A9F4");
//            runtimeSubApp.addActivity(runtimeActivity);
////            runtimeSubApp.addPosibleStartActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("User Identity");
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
//            runtimeFragment = new FermatRuntimeFragment();
//            runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT.getKey());
//            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
//            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT.getKey());

        // Activity: Create New Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setColor("#03A9F4");
        //runtimeSubApp.addActivity(runtimeActivity);
        //runtimeSubApp.changeActualStartActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY.getCode());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Asset User Identity");
        runtimeTitleBar.setColor("#1189a4");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#1189a4");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(1);
        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_location", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Location");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(2);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY.getCode());

        //Activity Geolocation
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_GEOLOCATION_ACTIVITY);
        runtimeActivity.setActivityType(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_GEOLOCATION_ACTIVITY.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setBackPublicKey(identityPublicKey);
        //        runtimeActivity.setBackgroundColor("#F9F9F9");

        statusBar = new StatusBar();
        statusBar.setColor("#1189a4");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Geolocation");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#1189a4");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIconName("back");
        runtimeTitleBar.setLabelSize(18);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_GEOLOCATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_USER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());

        runtimeSubApp.addActivity(runtimeActivity);

        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    }

    private void createAssetRedeemPointIdentitySubAppNavigationStructure() throws InvalidParameterException {

        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
        OptionsMenu optionsMenu;
        OptionMenuItem menuItem;
        Owner owner;
        String identityPublicKey = SubAppsPublicKeys.DAP_IDENTITY_REDEEM.getCode();

        runtimeSubApp = new AppNavigationStructure();

        owner = new Owner();
        owner.setOwnerAppPublicKey(identityPublicKey);

        runtimeSubApp.setPublicKey(identityPublicKey);
        runtimeSubApp.setPlatform(Platforms.DIGITAL_ASSET_PLATFORM);

        // Activity: List of identities
//            runtimeActivity = new Activity();
//            runtimeActivity.setType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY);
//            runtimeActivity.setActivityType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(null);
//            runtimeActivity.setColor("#03A9F4");
//            runtimeSubApp.addActivity(runtimeActivity);
////            runtimeSubApp.addPosibleStartActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Redeem Point Identity");
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
//            runtimeFragment = new FermatRuntimeFragment();
//            runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT.getKey());
//            runtimeActivity.addFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
//            runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT.getKey());

        // Activity: Create New Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setActivityType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY.getCode());
//            runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setColor("#03A9F4");
//        runtimeSubApp.addActivity(runtimeActivity);
//        runtimeSubApp.changeActualStartActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY.getCode());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Redeem Point Identity");
        runtimeTitleBar.setIconName("back");
        runtimeTitleBar.setColor("#009688");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#009688");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(1);
        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_location", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Location");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(2);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY.getCode());

        //Activity Geolocation
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_GEOLOCATION_ACTIVITY);
        runtimeActivity.setActivityType(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_GEOLOCATION_ACTIVITY.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setBackPublicKey(identityPublicKey);
        //        runtimeActivity.setBackgroundColor("#F9F9F9");

        statusBar = new StatusBar();
        statusBar.setColor("#009688");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Geolocation");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#009688");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIconName("back");
        runtimeTitleBar.setLabelSize(18);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_GEOLOCATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_GEOLOCATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_REDEEM_POINT_IDENTITY_GEOLOCATION_FRAGMENT.getKey());

        runtimeSubApp.addActivity(runtimeActivity);

        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    }

    private SideMenu loadSideMenuAssetFactory(String publicKey) {

        SideMenu runtimeSideMenu = new SideMenu();
        //DAP V3
        runtimeSideMenu.setBackgroundColor("#1d1d25");
//        runtimeSideMenu.setNavigationIconColor("#ffffff");
        runtimeSideMenu.setHasFooter(true);

        MenuItem runtimeMenuItem;

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        return runtimeSideMenu;
    }

    private void createAssetFactorySubAppNavigationStructure() throws InvalidParameterException {

        AppNavigationStructure dapFactory;
        Activity runtimeActivity;
        Tab runtimeTab;
        TabStrip runtimeTabStrip;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
        OptionsMenu optionsMenu;
        OptionMenuItem menuItem;
        Owner owner;
        String titleBarColor = "#3f5b77";// "#4B5E75";
        String statusBarColor = "#3f5b77";
        String textTitleColor = "#ffffff";
        String dapFactoryPublicKey = SubAppsPublicKeys.DAP_FACTORY.getCode();

        dapFactory = new AppNavigationStructure();

        owner = new Owner();
        owner.setOwnerAppPublicKey(dapFactoryPublicKey);

        dapFactory.setPublicKey(dapFactoryPublicKey);
        dapFactory.setPlatform(Platforms.DIGITAL_ASSET_PLATFORM);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_MAIN);
        runtimeActivity.setColor("#1d1d25");
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Asset Factory");
        runtimeTitleBar.setTitleColor(textTitleColor);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#CEE3F4");
        runtimeTabStrip.setTabsIndicateColor("#8296ab");
        runtimeTabStrip.setTabsTextColor("#8296ab");

        runtimeTab = new Tab();
        runtimeTab.setLabel("Draft");
        runtimeTab.setFragment(new FermatRuntimeFragment(1, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.DAP_SUB_APP_ASSET_FACTORY_EDITABLE_TAB_FRAGMENT.getKey()));
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Issued");
        runtimeTab.setFragment(new FermatRuntimeFragment(2, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.DAP_SUB_APP_ASSET_FACTORY_PUBLISHED_TAB_FRAGMENT.getKey()));
        runtimeTabStrip.addTab(runtimeTab);

        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_FACTORY_EDITABLE_TAB_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_EDITABLE_TAB_FRAGMENT.getKey(), runtimeFragment);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_FACTORY_PUBLISHED_TAB_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_PUBLISHED_TAB_FRAGMENT.getKey(), runtimeFragment);

        runtimeActivity.setSideMenu(loadSideMenuAssetFactory(dapFactoryPublicKey));

        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(1);
        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_search", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Search");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        menuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(2);
//        menuItem.setFermatDrawable(new FermatDrawable(2, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        dapFactory.addActivity(runtimeActivity);
        dapFactory.changeActualStartActivity(Activities.DAP_MAIN.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_EDITOR_ACTIVITY);
        runtimeActivity.setBackActivity(Activities.DAP_MAIN);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor("#1d1d25");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Draft Asset");
        runtimeTitleBar.setTitleColor(textTitleColor);
        runtimeTitleBar.setColor(titleBarColor);

        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY.getKey());

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY.getKey(), runtimeFragment);

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY SETTINGS
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS);
        runtimeActivity.setBackActivity(Activities.DAP_MAIN);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor("#3f5b77");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setTitleColor(textTitleColor);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetFactory(dapFactoryPublicKey));

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY SETTINGS_NETWORK
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN);
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor("#3f5b77");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Network");
        runtimeTitleBar.setTitleColor(textTitleColor);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);


        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN.getKey());

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY SETTINGS_NOTIFICATIONS
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS);
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor("#1d1d25");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setTitleColor(textTitleColor);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS.getKey());

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS.getKey(), runtimeFragment);

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY WIZARD MULTIMEDIA
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA);
        runtimeActivity.setBackActivity(Activities.DAP_MAIN);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor(titleBarColor);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Multimedia");
        runtimeTitleBar.setTitleColor(textTitleColor);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setIconName("Back");
//        MenuItem leftIconMenuItem = new MenuItem();
//
//        leftIconMenuItem.setFermatDrawable(new FermatDrawable(10, "open_nav", owner, SourceLocation.DEVELOPER_RESOURCES));
//        leftIconMenuItem.setAppLinkPublicKey("back");
//        runtimeTitleBar.setNavItem(leftIconMenuItem);

        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(2);
        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA.getKey());

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY WIZARD PROPERTIES
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES);
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor(titleBarColor);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Properties");
        runtimeTitleBar.setTitleColor(textTitleColor);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setIconName("Back");
//        MenuItem leftIconMenuItem2 = new MenuItem();
//
//        leftIconMenuItem2.setFermatDrawable(new FermatDrawable(11, "open_nav", owner, SourceLocation.DEVELOPER_RESOURCES));
//        leftIconMenuItem2.setAppLinkPublicKey("back");
//        runtimeTitleBar.setNavItem(leftIconMenuItem2);

        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(2);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES.getKey());

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY WIZARD CRYPTO
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO);
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor(titleBarColor);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Crypto");
        runtimeTitleBar.setTitleColor(textTitleColor);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setIconName("Back");
//        MenuItem leftIconMenuItem3 = new MenuItem();
//
//        leftIconMenuItem3.setFermatDrawable(new FermatDrawable(12, "open_nav", owner, SourceLocation.DEVELOPER_RESOURCES));
//        leftIconMenuItem3.setAppLinkPublicKey("back");
//        runtimeTitleBar.setNavItem(leftIconMenuItem3);

        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(2);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO.getKey());

        dapFactory.addActivity(runtimeActivity);

        //DAP FACTORY WIZARD VERIFY
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY);
        runtimeActivity.setBackActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO);
        runtimeActivity.setBackPublicKey(dapFactoryPublicKey);
        runtimeActivity.setColor(titleBarColor);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Verify");
        runtimeTitleBar.setTitleColor(textTitleColor);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setIconName("Back");
//        MenuItem leftIconMenuItem4 = new MenuItem();
//
//        leftIconMenuItem4.setFermatDrawable(new FermatDrawable(13, "open_nav", owner, SourceLocation.DEVELOPER_RESOURCES));
//        leftIconMenuItem4.setAppLinkPublicKey("back");
//        runtimeTitleBar.setNavItem(leftIconMenuItem4);

        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(2);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY.getKey());
        runtimeActivity.addFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY.getKey());

        dapFactory.addActivity(runtimeActivity);

        listSubApp.put(dapFactory.getPublicKey(), dapFactory);
    }

    private SideMenu loadSideMenuAssetIssuerCommunity(String publicKey) {

        SideMenu runtimeSideMenu = new SideMenu();
        //DAP V3
        runtimeSideMenu.setBackgroundColor("#0072bb");
//        runtimeSideMenu.setNavigationIconColor("#ffffff");
//        runtimeSideMenu.setHasFooter(true);

        MenuItem runtimeMenuItem;

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Settings");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS);
//        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        return runtimeSideMenu;
    }

    private void createAssetIssuerCommunitySubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure dapAssetIssuerCommunity;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
        OptionsMenu optionsMenu;
        OptionMenuItem menuItem;
        Owner owner;
        String statusBarColor = "#0072bb";
        String titleBarColor = "#0072bb";
        String communityIssuerPublicKey = SubAppsPublicKeys.DAP_COMMUNITY_ISSUER.getCode();

        owner = new Owner();
        owner.setOwnerAppPublicKey(communityIssuerPublicKey);

        dapAssetIssuerCommunity = new AppNavigationStructure();

        dapAssetIssuerCommunity.setPublicKey(communityIssuerPublicKey);
        dapAssetIssuerCommunity.setPlatform(Platforms.DIGITAL_ASSET_PLATFORM);
        dapAssetIssuerCommunity.changeActualStartActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Asset Issuer Community");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetIssuerCommunity(communityIssuerPublicKey));

        optionsMenu = new OptionsMenu();

        menuItem = new OptionMenuItem(1);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Connect");
        menuItem.setOrder(0);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(2);
//        menuItem.setFermatDrawable(new FermatDrawable(2, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Cancel Connecting");
        menuItem.setOrder(1);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(3);
//        menuItem.setFermatDrawable(new FermatDrawable(3, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Select All");
        menuItem.setOrder(2);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(4);
//        menuItem.setFermatDrawable(new FermatDrawable(4, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Unselect All");
        menuItem.setOrder(3);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(5);
//        menuItem.setFermatDrawable(new FermatDrawable(5, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setOrder(4);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(6);
        menuItem.setFermatDrawable(new FermatDrawable(2, "ic_search", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Search");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        menuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(7);
        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_location", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Location");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_ISSUER_COMMUNITY_CONNECTIONS_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_CONNECTIONS_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_CONNECTIONS_LIST_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetIssuerCommunity(communityIssuerPublicKey));

        //reinicializacion optionMenu for not reply menu en diferente activity
        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(1);
//        menuItem.setFermatDrawable(new FermatDrawable(5, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
//        menuItem.setOrder(4);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);
        runtimeActivity.setOptionsMenu(optionsMenu);

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Issuer Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());

        //reinicializacion optionMenu for not reply menu en diferente activity
        optionsMenu = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenu);

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_LIST_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_LIST_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Issuer Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());

        //reinicializacion optionMenu for not reply menu en diferente activity
        optionsMenu = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenu);

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetIssuerCommunity(communityIssuerPublicKey));

        //reinicializacion optionMenu for not reply menu en diferente activity
        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(1);
//        menuItem.setFermatDrawable(new FermatDrawable(5, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
//        menuItem.setOrder(4);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);
        runtimeActivity.setOptionsMenu(optionsMenu);

        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        //INI ISSUER SETTINGS ACTIVITY
//        runtimeActivity = new Activity();
//        runtimeActivity.setType(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS);
//        runtimeActivity.setActivityType(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS.getCode());
//        runtimeActivity.setBackActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
//        runtimeActivity.setBackPublicKey(communityIssuerPublicKey);
//        runtimeActivity.setColor("#FF0B46F0");
//
//        statusBar = new StatusBar();
//        statusBar.setColor("#0072bb");
//        runtimeActivity.setStatusBar(statusBar);
//
//        runtimeTitleBar = new TitleBar();
//        runtimeTitleBar.setLabel("Settings");
//        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
//        runtimeTitleBar.setColor("#0072bb");
//        runtimeTitleBar.setLabelSize(20);
//        runtimeActivity.setTitleBar(runtimeTitleBar);
//
//        statusBar = new StatusBar();
//        statusBar.setColor("#0072bb");
//        runtimeActivity.setStatusBar(statusBar);
//
//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS.getKey());
//        runtimeActivity.addFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS.getKey());
//
//        runtimeSideMenu = new SideMenu();
//        runtimeSideMenu.setBackgroundColor("#0072bb");
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Home");
//        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Connections");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
//        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Notifications");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN);
//        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Settings");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_SETTINGS);
//        runtimeMenuItem.setAppLinkPublicKey(communityIssuerPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeActivity.setSideMenu(runtimeSideMenu);
//
//        dapAssetIssuerCommunity.addActivity(runtimeActivity);

        listSubApp.put(dapAssetIssuerCommunity.getPublicKey(), dapAssetIssuerCommunity);
    }

    private SideMenu loadSideMenuAssetUserCommunity(String publicKey) {

        SideMenu runtimeSideMenu = new SideMenu();
        //DAP V3
        runtimeSideMenu.setBackgroundColor("#0072bb");
//        runtimeSideMenu.setNavigationIconColor("#ffffff");
//        runtimeSideMenu.setHasFooter(true);

        MenuItem runtimeMenuItem;

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Manage Groups");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Settings");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
//        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        return runtimeSideMenu;
    }

    private void createAssetUserCommunitySubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure dapAssetUserCommunity;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        String statusBarColor = "#0072bb";
        String titleBarColor = "#0072bb";
        Owner owner;
        FermatRuntimeFragment runtimeFragment;

        String communityUserPublicKey = SubAppsPublicKeys.DAP_COMMUNITY_USER.getCode();

        owner = new Owner();
        owner.setOwnerAppPublicKey(communityUserPublicKey);

        dapAssetUserCommunity = new AppNavigationStructure();

        dapAssetUserCommunity.setPublicKey(communityUserPublicKey);
        dapAssetUserCommunity.setPlatform(Platforms.DIGITAL_ASSET_PLATFORM);
        dapAssetUserCommunity.changeActualStartActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Asset User Community");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetUserCommunity(communityUserPublicKey));

        OptionsMenu optionsMenuMain = new OptionsMenu();

        OptionMenuItem menuItemMain = new OptionMenuItem(0);
//        menuItemMain.setFermatDrawable(new FermatDrawable(0, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemMain.setLabel("Connect");
        menuItemMain.setOrder(0);
        menuItemMain.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuMain.addMenuItem(menuItemMain);

        menuItemMain = new OptionMenuItem(1);
//        menuItemMain.setFermatDrawable(new FermatDrawable(1, "disconnect", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemMain.setLabel("Disconnect");
        menuItemMain.setOrder(1);
        menuItemMain.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuMain.addMenuItem(menuItemMain);

        menuItemMain = new OptionMenuItem(2);
//        menuItemMain.setFermatDrawable(new FermatDrawable(2, "cancel_connecting", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemMain.setLabel("Cancel Connecting");
        menuItemMain.setOrder(2);
        menuItemMain.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuMain.addMenuItem(menuItemMain);

        menuItemMain = new OptionMenuItem(3);
//        menuItemMain.setFermatDrawable(new FermatDrawable(3, "select_all", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemMain.setLabel("Select All");
        menuItemMain.setOrder(3);
        menuItemMain.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuMain.addMenuItem(menuItemMain);

        menuItemMain = new OptionMenuItem(4);
//        menuItemMain.setFermatDrawable(new FermatDrawable(4, "unselect_all", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemMain.setLabel("Unselect All");
        menuItemMain.setOrder(4);
        menuItemMain.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuMain.addMenuItem(menuItemMain);

        menuItemMain = new OptionMenuItem(5);
//        menuItemMain.setFermatDrawable(new FermatDrawable(5, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemMain.setLabel("Help");
        menuItemMain.setOrder(5);
        menuItemMain.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuMain.addMenuItem(menuItemMain);

        menuItemMain = new OptionMenuItem(6);
        menuItemMain.setFermatDrawable(new FermatDrawable(2, "ic_search", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemMain.setLabel("Search");
        menuItemMain.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        menuItemMain.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionsMenuMain.addMenuItem(menuItemMain);

        menuItemMain = new OptionMenuItem(7);
        menuItemMain.setFermatDrawable(new FermatDrawable(1, "ic_location", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemMain.setLabel("Location");
        menuItemMain.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuMain.addMenuItem(menuItemMain);

        runtimeActivity.setOptionsMenu(optionsMenuMain);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        //INI User other profile activity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("User Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        //reinicializacion optionMenu for not reply menu en diferente activity
        OptionsMenu optionsMenuProfile = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenuProfile);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_LIST_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_LIST_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("User Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_OTHER_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_OTHER_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_OTHER_PROFILE_FRAGMENT.getKey());
        //reinicializacion optionMenu for not reply menu en diferente activity
        OptionsMenu optionsMenuProfile2 = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenuProfile2);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        //INI User connections list activity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetUserCommunity(communityUserPublicKey));

        //reinicializacion optionMenu for not reply menu en diferente activity
        OptionsMenu optionsMenuConnection = new OptionsMenu();
        OptionMenuItem menuItemConnection = new OptionMenuItem(1);
//        menuItemConnection.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemConnection.setLabel("Help");
//        menuItemConnection.setOrder(4);
        menuItemConnection.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuConnection.addMenuItem(menuItemConnection);
        runtimeActivity.setOptionsMenu(optionsMenuConnection);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        // Activity: Manage Groups
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Manage Groups");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetUserCommunity(communityUserPublicKey));

        //reinicializacion optionMenu for not reply menu en diferente activity
        OptionsMenu optionsMenuMgroup = new OptionsMenu();
        OptionMenuItem menuItemMgroup = new OptionMenuItem(1);
//        menuItemMgroup.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemMgroup.setLabel("Help");
//        menuItemMgroup.setOrder(4);
        menuItemMgroup.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuMgroup.addMenuItem(menuItemMgroup);
        runtimeActivity.setOptionsMenu(optionsMenuMgroup);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        //runtimeTitleBar.setLabel("GROUP NAME");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetUserCommunity(communityUserPublicKey));

        //reinicializacion optionMenu for not reply menu en diferente activity
        OptionsMenu optionsMenuGroupUser = new OptionsMenu();
        OptionMenuItem menuItemGroupUser = new OptionMenuItem(1);
//        menuItemGroupUser.setFermatDrawable(new FermatDrawable(1, "rename_group", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemGroupUser.setLabel("Rename Group");
        menuItemGroupUser.setOrder(0);
        menuItemGroupUser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuGroupUser.addMenuItem(menuItemGroupUser);

        menuItemGroupUser = new OptionMenuItem(2);
//        menuItemGroupUser.setFermatDrawable(new FermatDrawable(2, "delete_group", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemGroupUser.setLabel("Delete Group");
        menuItemGroupUser.setOrder(1);
        menuItemGroupUser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuGroupUser.addMenuItem(menuItemGroupUser);

        menuItemGroupUser = new OptionMenuItem(3);
//        menuItemGroupUser.setFermatDrawable(new FermatDrawable(3, "delete_selected_users", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemGroupUser.setLabel("Delete Selected Users");
        menuItemGroupUser.setOrder(2);
        menuItemGroupUser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuGroupUser.addMenuItem(menuItemGroupUser);

        menuItemGroupUser = new OptionMenuItem(4);
//        menuItemGroupUser.setFermatDrawable(new FermatDrawable(4, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemGroupUser.setLabel("Help");
        menuItemGroupUser.setOrder(3);
        menuItemGroupUser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuGroupUser.addMenuItem(menuItemGroupUser);
        runtimeActivity.setOptionsMenu(optionsMenuGroupUser);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        //runtimeTitleBar.setLabel("GROUP NAME");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetUserCommunity(communityUserPublicKey));

        //reinicializacion optionMenu for not reply menu en diferente activity
        OptionsMenu optionsMenuAdminUser = new OptionsMenu();
        OptionMenuItem menuItemAdminUser = new OptionMenuItem(1);
//        menuItemAdminUser.setFermatDrawable(new FermatDrawable(1, "add_to_group", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemAdminUser.setLabel("Add to Group");
        menuItemAdminUser.setOrder(0);
        menuItemAdminUser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuAdminUser.addMenuItem(menuItemAdminUser);

        menuItemAdminUser = new OptionMenuItem(2);
//        menuItemAdminUser.setFermatDrawable(new FermatDrawable(2, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemAdminUser.setLabel("Help");
        menuItemAdminUser.setOrder(1);
        menuItemAdminUser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuAdminUser.addMenuItem(menuItemAdminUser);
        runtimeActivity.setOptionsMenu(optionsMenuAdminUser);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityUserPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(loadSideMenuAssetUserCommunity(communityUserPublicKey));

        //reinicializacion optionMenu for not reply menu en diferente activity
        OptionsMenu optionsMenuNotification = new OptionsMenu();
        OptionMenuItem menuItemNotification = new OptionMenuItem(1);
//        menuItemNotification.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItemNotification.setLabel("Help");
//        menuItemNotification.setOrder(4);
        menuItemNotification.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenuNotification.addMenuItem(menuItemNotification);
        runtimeActivity.setOptionsMenu(optionsMenuNotification);

        dapAssetUserCommunity.addActivity(runtimeActivity);

        //INI USER SETTINGS ACTIVITY
//        runtimeActivity = new Activity();
//        runtimeActivity.setType(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
//        runtimeActivity.setActivityType(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS.getCode());
//        runtimeActivity.setBackActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
//        runtimeActivity.setBackPublicKey(communityUserPublicKey);
//        runtimeActivity.setColor("#FF0B46F0");
//
//        statusBar = new StatusBar();
//        statusBar.setColor("#0072bb");
//        runtimeActivity.setStatusBar(statusBar);
//
//        runtimeTitleBar = new TitleBar();
//        runtimeTitleBar.setLabel("Settings");
//        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
//        runtimeTitleBar.setColor("#0072bb");
//        runtimeTitleBar.setLabelSize(20);
//        runtimeActivity.setTitleBar(runtimeTitleBar);
//
//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_USER_COMMUNITY_SETTINGS_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.DAP_ASSET_USER_COMMUNITY_SETTINGS_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_USER_COMMUNITY_SETTINGS_FRAGMENT.getKey());
//
//        runtimeSideMenu = new SideMenu();
//        runtimeSideMenu.setBackgroundColor("#0072bb");
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Home");
//        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Connections");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
//        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Notifications");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT);
//        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Manage Groups");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN);
//        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Settings");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_USER_COMMUNITY_SETTINGS);
//        runtimeMenuItem.setAppLinkPublicKey(communityUserPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeActivity.setSideMenu(runtimeSideMenu);
//
//        dapAssetUserCommunity.addActivity(runtimeActivity);

        listSubApp.put(dapAssetUserCommunity.getPublicKey(), dapAssetUserCommunity);
    }

    private SideMenu loadSideMenuRedeemPointCommunity(String publicKey) {

        SideMenu runtimeSideMenu = new SideMenu();
        //DAP V3
        runtimeSideMenu.setBackgroundColor("#0072bb");
//        runtimeSideMenu.setNavigationIconColor("#ffffff");
//        runtimeSideMenu.setHasFooter(true);

        MenuItem runtimeMenuItem;

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Connections");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Notifications");
        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Settings");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS);
//        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        return runtimeSideMenu;
    }

    private void createRedeemPointCommunitySubAppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure dapAssetRedeemPointCommunity;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        OptionsMenu optionsMenu;
        OptionMenuItem menuItem;
        Owner owner;
        String statusBarColor = "#0072bb";
        String titleBar = "#0072bb";
        FermatRuntimeFragment runtimeFragment;

        String communityRedeemPointPublicKey = SubAppsPublicKeys.DAP_COMMUNITY_REDEEM.getCode();

        owner = new Owner();
        owner.setOwnerAppPublicKey(communityRedeemPointPublicKey);

        dapAssetRedeemPointCommunity = new AppNavigationStructure();

        dapAssetRedeemPointCommunity.setPublicKey(communityRedeemPointPublicKey);
        dapAssetRedeemPointCommunity.setPlatform(Platforms.DIGITAL_ASSET_PLATFORM);
        dapAssetRedeemPointCommunity.changeActualStartActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getCode());

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getCode());
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Redeem Point Community");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBar);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getKey());
        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN.getKey(), runtimeFragment);

        runtimeActivity.setSideMenu(loadSideMenuRedeemPointCommunity(communityRedeemPointPublicKey));

        optionsMenu = new OptionsMenu();

        menuItem = new OptionMenuItem(1);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Connect");
        menuItem.setOrder(0);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(2);
//        menuItem.setFermatDrawable(new FermatDrawable(2, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Disconnect");
        menuItem.setOrder(1);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(3);
//        menuItem.setFermatDrawable(new FermatDrawable(3, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Cancel Connecting");
        menuItem.setOrder(2);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(4);
//        menuItem.setFermatDrawable(new FermatDrawable(4, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Select All");
        menuItem.setOrder(3);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(5);
//        menuItem.setFermatDrawable(new FermatDrawable(5, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Unselect All");
        menuItem.setOrder(4);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(6);
//        menuItem.setFermatDrawable(new FermatDrawable(6, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
        menuItem.setOrder(5);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(7);
        menuItem.setFermatDrawable(new FermatDrawable(2, "ic_search", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Search");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        menuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(7);
        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_location", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Location");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        //INI redeem point community other profile

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityRedeemPointPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Redeem Point Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBar);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
        //reinicializacion optionMenu for not reply menu en diferente activity
        optionsMenu = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenu);

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        //INI Redeem Point connections list activity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
        runtimeActivity.setBackPublicKey(communityRedeemPointPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBar);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_CONNECTION_LIST_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(loadSideMenuRedeemPointCommunity(communityRedeemPointPublicKey));

        //reinicializacion optionMenu for not reply menu en diferente activity
        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(1);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
//        menuItem.setOrder(5);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);
        runtimeActivity.setOptionsMenu(optionsMenu);

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        //INI REDEEM POINT SETTINGS ACTIVITY
//        runtimeActivity = new Activity();
//        runtimeActivity.setType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS);
//        runtimeActivity.setActivityType(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS.getCode());
//        runtimeActivity.setBackActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
//        runtimeActivity.setBackPublicKey(communityRedeemPointPublicKey);
//        runtimeActivity.setColor("#FF0B46F0");
//
//        statusBar = new StatusBar();
//        statusBar.setColor("#0072bb");
//        runtimeActivity.setStatusBar(statusBar);
//
//        runtimeTitleBar = new TitleBar();
//        runtimeTitleBar.setLabel("Settings");
//        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
//        runtimeTitleBar.setColor("#0072bb");
//        runtimeTitleBar.setLabelSize(20);
//        runtimeActivity.setTitleBar(runtimeTitleBar);
//
//        statusBar = new StatusBar();
//        statusBar.setColor("#0072bb");
//        runtimeActivity.setStatusBar(statusBar);
//
//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS_FRAGMENT.getKey());
//
//        runtimeSideMenu = new SideMenu();
//        runtimeSideMenu.setBackgroundColor("#0072bb");
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Home");
//        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Connections");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_CONNECTIONS_LIST);
//        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Notifications");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT);
//        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Settings");
//        runtimeMenuItem.setLinkToActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS);
//        runtimeMenuItem.setAppLinkPublicKey(communityRedeemPointPublicKey);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeActivity.setSideMenu(runtimeSideMenu);
//
//        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

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
        runtimeTitleBar.setColor(titleBar);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT.getKey());
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
        statusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(titleBar);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT.getKey());

        runtimeActivity.setSideMenu(loadSideMenuRedeemPointCommunity(communityRedeemPointPublicKey));

        //reinicializacion optionMenu for not reply menu en diferente activity
        optionsMenu = new OptionsMenu();
        menuItem = new OptionMenuItem(1);
//        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Help");
//        menuItem.setOrder(5);
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);//SHOW_AS_ACTION_ALWAYS (2) - SHOW_AS_ACTION_WITH_TEXT (4)
        optionsMenu.addMenuItem(menuItem);
        runtimeActivity.setOptionsMenu(optionsMenu);

        dapAssetRedeemPointCommunity.addActivity(runtimeActivity);

        listSubApp.put(dapAssetRedeemPointCommunity.getPublicKey(), dapAssetRedeemPointCommunity);
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

        FermatRuntimeFragment runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey());
        runtimeActivity.addFragment(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE.getKey());

        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);


    }

    private void createArtArtistIdentitySubAgracppNavigationStructure() throws InvalidParameterException {
        AppNavigationStructure artArtistIdentity;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
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
        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE.getKey(), runtimeFragment);

/*
        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#0072bb");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        */

        artArtistIdentity.addActivity(runtimeActivity);

        listSubApp.put(artArtistIdentity.getPublicKey(), artArtistIdentity);
    } //ArtArtistIdentity

    private void createCryptoCustomerIdentitySubAppNavigationStructure() throws InvalidParameterException {
        final int ADD_IDENTITY_OPTION_MENU_ID = 1;
        final int HELP_OPTION_MENU_ID = 2;
        final int CREATE_IDENTITY_MENU_ID = 3;
        final int GEOLOCATION_SETTINGS_OPTION_MENU_ID = 4;
        final String PUBLIC_KEY = SubAppsPublicKeys.CBP_CUSTOMER_IDENTITY.getCode();
        final Owner OWNER = new Owner(PUBLIC_KEY);

        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
        OptionsMenu optionsMenu;
        OptionMenuItem optionMenuItem;


        runtimeSubApp = new AppNavigationStructure();

        runtimeSubApp.setPublicKey(PUBLIC_KEY);
        runtimeSubApp.setPlatform(Platforms.CRYPTO_BROKER_PLATFORM);
        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

        // Activity: List of identities
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY.getCode());
        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY.getCode());

        statusBar = new StatusBar();
        statusBar.setColor("#2D2E82");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Customer Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#1189a5");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        // Option Menu - Create New Identity Activity
        optionsMenu = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenu);

        // Option Menu Item - Create Identity
        optionMenuItem = new OptionMenuItem(ADD_IDENTITY_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(ADD_IDENTITY_OPTION_MENU_ID, "action_add", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Create Identity");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_IF_ROOM);
        optionMenuItem.setOptionMenuPressEvent(new OptionMenuChangeActivityOnPressEvent(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY.getCode()));
        optionsMenu.addMenuItem(optionMenuItem);

        // Option Menu Item - Help
        optionMenuItem = new OptionMenuItem(HELP_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(HELP_OPTION_MENU_ID, "action_help", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Help");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT.getKey());

        // Activity: ImageCropper
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#2D2E82");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER_FRAGMENT.getKey());

        // Activity: Create New Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#2D2E82");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("New Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#1189a5");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        // Option Menu - Create New Identity Activity
        optionsMenu = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenu);

        // Option Menu Item - GEOLOCATION
        optionMenuItem = new OptionMenuItem(GEOLOCATION_SETTINGS_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(GEOLOCATION_SETTINGS_OPTION_MENU_ID, "ic_location", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);


        // Option Menu Item - Create Identity
        optionMenuItem = new OptionMenuItem(CREATE_IDENTITY_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(CREATE_IDENTITY_MENU_ID, "action_create", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Create Identity");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

        // Activity: Geolocation identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_CREATE_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_CREATE_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#2D2E82");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Geolocation");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#2D2E82");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());

        // Activity: Edit Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#2D2E82");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Edit Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#1189a5");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());

        // Option Menu - Edit Identity Activity
        optionsMenu = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenu);

        // Option Menu Item - Geolocation Settings
        optionMenuItem = new OptionMenuItem(GEOLOCATION_SETTINGS_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(GEOLOCATION_SETTINGS_OPTION_MENU_ID, "cbp_id_geolocation_icon", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Geolocation identity");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

        // Option Menu Item - Create Identity
        optionMenuItem = new OptionMenuItem(CREATE_IDENTITY_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(CREATE_IDENTITY_MENU_ID, "action_create", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Edit Identity");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());

        // Activity: Geolocation in Edit Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_EDIT_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_EDIT_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#2D2E82");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Geolocation");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#9A12B09F");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());
    }

    private void createCryptoBrokerIdentitySubAppNavigationStructure() throws InvalidParameterException {
        final int ADD_IDENTITY_OPTION_MENU_ID = 1;
        final int HELP_OPTION_MENU_ID = 2;
        final int GEOLOCATION_SETTINGS_OPTION_MENU_ID = 3;

        final String PUBLIC_KEY = SubAppsPublicKeys.CBP_BROKER_IDENTITY.getCode();
        final Owner OWNER = new Owner(PUBLIC_KEY);

        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        StatusBar statusBar;
        TitleBar runtimeTitleBar;
        FermatRuntimeFragment runtimeFragment;

        runtimeSubApp = new AppNavigationStructure();
        runtimeSubApp.setPublicKey(PUBLIC_KEY);
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
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        // Option Menu - List of identities Activity
        OptionsMenu optionsMenu = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenu);

        // Option Menu Item - Create Identity
        OptionMenuItem optionMenuItem = new OptionMenuItem(ADD_IDENTITY_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(ADD_IDENTITY_OPTION_MENU_ID, "action_add", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Create Identity");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionMenuItem.setOptionMenuPressEvent(new OptionMenuChangeActivityOnPressEvent(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY.getCode()));
        optionsMenu.addMenuItem(optionMenuItem);

        // Option Menu Item - Help
        optionMenuItem = new OptionMenuItem(HELP_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(HELP_OPTION_MENU_ID, "action_help", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Help");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT.getKey());

        // Activity: ImageCropper
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#9A12B09F");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER_FRAGMENT.getKey());

        // Activity: Create New Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#9A12B09F");
        runtimeActivity.setStatusBar(statusBar);

        // Option Menu - Create New Identity Activity
        optionsMenu = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenu);

        // Option Menu Item - Geolocation Settings
        optionMenuItem = new OptionMenuItem(GEOLOCATION_SETTINGS_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(GEOLOCATION_SETTINGS_OPTION_MENU_ID, "cbp_id_geolocation_icon", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Geolocation identity");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("New Identity");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#9A12B09F");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

        // Activity: Geolocation in Create Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_CREATE_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_CREATE_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#9A12B09F");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Geolocation");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#9A12B09F");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());

        // Activity: Edit Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
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

        // Option Menu - Edit Identity Activity
        optionsMenu = new OptionsMenu();
        runtimeActivity.setOptionsMenu(optionsMenu);

        // Option Menu Item - Geolocation Settings
        optionMenuItem = new OptionMenuItem(GEOLOCATION_SETTINGS_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(GEOLOCATION_SETTINGS_OPTION_MENU_ID, "cbp_id_geolocation_icon", OWNER, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Geolocation identity");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT.getKey());

        // Activity: Geolocation in Edit Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_EDIT_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_EDIT_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY);
        runtimeActivity.setBackPublicKey(PUBLIC_KEY);
        runtimeSubApp.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#9A12B09F");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Geolocation");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#9A12B09F");
        runtimeTitleBar.setIconName("Back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_FRAGMENT.getKey());

    }

    private void createCryptoBrokerCommunitySubAppNavigationStructure() throws InvalidParameterException {
        final int SEARCH_FILTER_OPTION_MENU_ID = 1;
        final int LOCATION_FILTER_OPTION_MENU_ID = 2;
        final int HELP_OPTION_MENU_ID = 3;
        final int CBC_BACKGROUND_TAB_ID = 4;
        final int GO_TO_WALLET_CUSTOMER = 5;

        final String PUBLIC_KEY = SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode();
        final Owner owner = new Owner(PUBLIC_KEY);

        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        Activity runtimeActivity;
        FermatRuntimeFragment runtimeFragment;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;
        OptionsMenu optionsMenu;
        OptionMenuItem optionMenuItem;


        AppNavigationStructure subAppBrokerCommunity = new AppNavigationStructure();

        subAppBrokerCommunity.setPlatform(Platforms.CRYPTO_BROKER_PLATFORM);
        subAppBrokerCommunity.setPublicKey(PUBLIC_KEY);
        listSubApp.put(subAppBrokerCommunity.getPublicKey(), subAppBrokerCommunity);

        statusBar = new StatusBar();
        statusBar.setColor("#1375a7");

        //Activity Home Browser
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getCode());
        runtimeActivity.setBackgroundColor("#f9f9f9");
        subAppBrokerCommunity.changeActualStartActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getCode());

        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("  Broker Community");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#1291A3");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setOwner(owner);
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getKey());


        //TabStrip
        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setBackgroundDrawable(new FermatDrawable(CBC_BACKGROUND_TAB_ID, "cbc_tab_background", owner, SourceLocation.DEVELOPER_RESOURCES));
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");
        runtimeActivity.setTabStrip(runtimeTabStrip);

        //Tabs Browser
        runtimeTab = new Tab();
        runtimeTab.setLabel("BROWSER");
        runtimeFragment = new FermatRuntimeFragment(1, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeTab.setFragment(runtimeFragment);
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeTabStrip.addTab(runtimeTab);

        optionsMenu = new OptionsMenu();
        runtimeFragment.setOptionsMenu(optionsMenu);

        optionMenuItem = new OptionMenuItem(SEARCH_FILTER_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(SEARCH_FILTER_OPTION_MENU_ID, "search", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Search");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionMenuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(LOCATION_FILTER_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(LOCATION_FILTER_OPTION_MENU_ID, "location", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Location");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_IF_ROOM);
        optionsMenu.addMenuItem(optionMenuItem);

//        optionMenuItem = new OptionMenuItem(GO_TO_WALLET_CUSTOMER);
//        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
//        optionMenuItem.setOrder(0);
//        optionMenuItem.setLabel("Go to Wallet Customer");
//        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(HELP_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(HELP_OPTION_MENU_ID, "help", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Help");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_NEVER);
        optionsMenu.addMenuItem(optionMenuItem);


        //Tabs Connections
        runtimeTab = new Tab();
        runtimeTab.setLabel("CONNECTIONS");
        runtimeFragment = new FermatRuntimeFragment(2, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());
        runtimeTab.setFragment(runtimeFragment);
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey(), runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);

        optionsMenu = new OptionsMenu();
        runtimeFragment.setOptionsMenu(optionsMenu);

        optionMenuItem = new OptionMenuItem(SEARCH_FILTER_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(SEARCH_FILTER_OPTION_MENU_ID, "search", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Search");
        optionMenuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

//        optionMenuItem = new OptionMenuItem(GO_TO_WALLET_CUSTOMER);
//        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
//        optionMenuItem.setOrder(0);
//        optionMenuItem.setLabel("Go to Wallet Customer");
//        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(HELP_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(HELP_OPTION_MENU_ID, "help", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Help");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        //TODO: en esta version a de no aparecer porque uno no recibe Solicitudes de coneccion de parte de un broker
        //Tabs Requests
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("REQUESTS");
//        runtimeFragment = new FermatRuntimeFragment(3, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());
//        runtimeTab.setFragment(runtimeFragment);
//        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey(), runtimeFragment);
//        runtimeTabStrip.addTab(runtimeTab);
//
//        optionsMenu = new OptionsMenu();
//        runtimeFragment.setOptionsMenu(optionsMenu);
//
//        optionMenuItem = new OptionMenuItem(HELP_OPTION_MENU_ID);
//        optionMenuItem.setFermatDrawable(new FermatDrawable(HELP_OPTION_MENU_ID, "help", owner, SourceLocation.DEVELOPER_RESOURCES));
//        optionMenuItem.setLabel("Help");
//        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        optionsMenu.addMenuItem(optionMenuItem);

        subAppBrokerCommunity.addActivity(runtimeActivity);
        listSubApp.put(subAppBrokerCommunity.getPublicKey(), subAppBrokerCommunity);
    }

    private void createCryptoCustomerCommunitySubAppNavigationStructure() throws InvalidParameterException {
        final int SEARCH_FILTER_OPTION_MENU_ID = 1;
        final int LOCATION_FILTER_OPTION_MENU_ID = 2;
        final int HELP_OPTION_MENU_ID = 3;
        final int CCC_BACKGROUND_TAB_ID = 4;
        final int GO_TO_WALLET_BROKER = 5;

        final String PUBLIC_KEY = SubAppsPublicKeys.CBP_CUSTOMER_COMMUNITY.getCode();
        final Owner owner = new Owner(PUBLIC_KEY);

        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        Activity runtimeActivity;
        FermatRuntimeFragment runtimeFragment;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;
        OptionsMenu optionsMenu;
        OptionMenuItem optionMenuItem;

        AppNavigationStructure subAppCustomerCommunity = new AppNavigationStructure();

        subAppCustomerCommunity.setPlatform(Platforms.CRYPTO_BROKER_PLATFORM);
        subAppCustomerCommunity.setPublicKey(PUBLIC_KEY);
        listSubApp.put(subAppCustomerCommunity.getPublicKey(), subAppCustomerCommunity);

        statusBar = new StatusBar();
        statusBar.setColor("#2D2E82");

        //Activity Home Browser
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setActivityType(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getCode());
        runtimeActivity.setBackgroundColor("#f9f9f9");
        subAppCustomerCommunity.changeActualStartActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getCode());

        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("  Customer Community");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#2D2E82");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setOwner(owner);
        runtimeFragment.setFragmentCode(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getKey());

        //TabStrip
        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setBackgroundDrawable(new FermatDrawable(CCC_BACKGROUND_TAB_ID, "ccc_tab_background", owner, SourceLocation.DEVELOPER_RESOURCES));
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");
        runtimeActivity.setTabStrip(runtimeTabStrip);

        //Tabs Browser
        runtimeTab = new Tab();
        runtimeTab.setLabel("BROWSER");
        runtimeFragment = new FermatRuntimeFragment(1, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeTab.setFragment(runtimeFragment);
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeTabStrip.addTab(runtimeTab);

        optionsMenu = new OptionsMenu();
        runtimeFragment.setOptionsMenu(optionsMenu);

        optionMenuItem = new OptionMenuItem(SEARCH_FILTER_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(SEARCH_FILTER_OPTION_MENU_ID, "search", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Search");
        optionMenuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(LOCATION_FILTER_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(LOCATION_FILTER_OPTION_MENU_ID, "location", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Location");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_NEVER);
        optionsMenu.addMenuItem(optionMenuItem);

//        optionMenuItem = new OptionMenuItem(GO_TO_WALLET_BROKER);
//        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
//        optionMenuItem.setOrder(0);
//        optionMenuItem.setLabel("Go to Wallet Broker");
//        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(HELP_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(HELP_OPTION_MENU_ID, "help", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Help");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        //Tabs Connections
        runtimeTab = new Tab();
        runtimeTab.setLabel("CONNECTIONS");
        runtimeFragment = new FermatRuntimeFragment(2, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());
        runtimeTab.setFragment(runtimeFragment);
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST.getKey(), runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);

        optionsMenu = new OptionsMenu();
        runtimeFragment.setOptionsMenu(optionsMenu);

        optionMenuItem = new OptionMenuItem(SEARCH_FILTER_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(SEARCH_FILTER_OPTION_MENU_ID, "search", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Search");
        optionMenuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(optionMenuItem);

//        optionMenuItem = new OptionMenuItem(GO_TO_WALLET_BROKER);
//        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
//        optionMenuItem.setOrder(0);
//        optionMenuItem.setLabel("Go to Wallet Broker");
//        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(HELP_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(HELP_OPTION_MENU_ID, "help", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Help");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        // Tab Requests
        runtimeTab = new Tab();
        runtimeTab.setLabel("REQUESTS");
        runtimeFragment = new FermatRuntimeFragment(3, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());
        runtimeTab.setFragment(runtimeFragment);
        runtimeActivity.addFragment(Fragments.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey(), runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);

        optionsMenu = new OptionsMenu();
        runtimeFragment.setOptionsMenu(optionsMenu);

//        optionMenuItem = new OptionMenuItem(GO_TO_WALLET_BROKER);
//        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
//        optionMenuItem.setOrder(0);
//        optionMenuItem.setLabel("Go to Wallet Broker");
//        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(HELP_OPTION_MENU_ID);
        optionMenuItem.setFermatDrawable(new FermatDrawable(HELP_OPTION_MENU_ID, "help", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Help");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

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
        FermatRuntimeFragment runtimeFragment;
        final int titleBarLabelSize = 20;
        Owner owner;
        String publickeyDeveloperTools = SubAppsPublicKeys.PIP_DEVELOPER.getCode();

        runtimeSubApp = new AppNavigationStructure();
        runtimeSubApp.setPublicKey(publickeyDeveloperTools);
        runtimeSubApp.setPlatform(Platforms.PLUG_INS_PLATFORM);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_SUB_APP_ALL_DEVELOPER);
        runtimeActivity.setActivityType(Activities.CWP_SUB_APP_ALL_DEVELOPER.getCode());
        runtimeActivity.setColor("#b46a54");

        statusBar = new StatusBar();
        statusBar.setColor("#d07b62");
        runtimeActivity.setStatusBar(statusBar);

//        runtimeSubApp.addActivity(runtimeActivity);
//        runtimeSubApp.changeActualStartActivity(Activities.CWP_SUB_APP_ALL_DEVELOPER.getCode());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Developer");
        runtimeTitleBar.setLabelSize(titleBarLabelSize);
        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#d07b62");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#d07b62");
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#b46a54");

        runtimeTab = new Tab();
        runtimeTab.setLabel("DataBase Tools");
        owner = new Owner();
        owner.setOwnerAppPublicKey(publickeyDeveloperTools);
        runtimeTab.setFragment(new FermatRuntimeFragment(2, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey()));
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Log Tools");
        owner.setOwnerAppPublicKey(publickeyDeveloperTools);
        runtimeTab.setFragment(new FermatRuntimeFragment(3, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey()));
        runtimeTabStrip.addTab(runtimeTab);

        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey(), runtimeFragment);

//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());

        OptionsMenu optionsMenu = new OptionsMenu();
        OptionMenuItem optionMenuItem = new OptionMenuItem(1);
        optionMenuItem.setFermatDrawable(new FermatDrawable(1, "search", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Search");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionMenuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CWP_SUB_APP_ALL_DEVELOPER.getCode());

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

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey());
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey(), runtimeFragment);

        runtimeSubApp.addActivity(runtimeActivity);

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


        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey());

        runtimeSubApp.addActivity(runtimeActivity);

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


        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey());

        runtimeSubApp.addActivity(runtimeActivity);

//        /*New FermatRuntimeFragment - Log Level 1*/
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS);
        runtimeActivity.setActivityType(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS.getCode());
        runtimeActivity.setBackActivity(Activities.CWP_SUB_APP_ALL_DEVELOPER);
        runtimeActivity.setBackPublicKey(runtimeSubApp.getPublicKey());
        runtimeActivity.setColor("#b46a54");

        statusBar = new StatusBar();
        statusBar.setColor("#d07b62");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Log > Level 1");
        runtimeTitleBar.setLabelSize(titleBarLabelSize);
        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#d07b62");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey(), runtimeFragment);
//        runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey());
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey());

        runtimeSubApp.addActivity(runtimeActivity);

        /*New FermatRuntimeFragment - Log Level 2*/
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS);
        runtimeActivity.setActivityType(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS.getCode());
        runtimeActivity.setBackActivity(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS);
        runtimeActivity.setBackPublicKey(runtimeSubApp.getPublicKey());
        runtimeActivity.setColor("#b46a54");

        statusBar = new StatusBar();
        statusBar.setColor("#d07b62");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Log > Level 2");
        runtimeTitleBar.setLabelSize(titleBarLabelSize);
        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#d07b62");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey());

        runtimeSubApp.addActivity(runtimeActivity);

//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey());

        /*New FermatRuntimeFragment - Log Level 3*/
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_3_TOOLS);
        runtimeActivity.setActivityType(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_3_TOOLS.getCode());
        runtimeActivity.setBackActivity(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS);
        runtimeActivity.setBackPublicKey(runtimeSubApp.getPublicKey());
        runtimeActivity.setColor("#b46a54");

        statusBar = new StatusBar();
        statusBar.setColor("#d07b62");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Log > Level 3");
        runtimeTitleBar.setLabelSize(titleBarLabelSize);
        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#d07b62");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey());

        runtimeSubApp.addActivity(runtimeActivity);

        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    }

    private void createChatSubAppNavigationStructure() throws InvalidParameterException {

        AppNavigationStructure chtChat;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;

        chtChat = new AppNavigationStructure();
        final String chatPublicKey = SubAppsPublicKeys.CHT_OPEN_CHAT.getCode();
        chtChat.setPublicKey(chatPublicKey);
        chtChat.setPlatform(Platforms.CHAT_PLATFORM);
        //listSubApp.put(chtChat.getPublicKey(), chtChat);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");

        //Activity Chat
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        runtimeActivity.setColor("#F9F9F9");
        //chtChat.changeActualStartActivity(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("P2P Chat");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(false);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeActivity.setStatusBar(statusBar);

        //Owner
        Owner owner = new Owner();
        owner.setOwnerAppPublicKey(chatPublicKey);

//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());

        //Menu Tabs
        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#075E55");
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#F9F9F9");

        //Tabs Chats
        runtimeTab = new Tab();
        runtimeTab.setLabel("CHATS");

        //Badge in the tab
//        Badge fermatDrawable = new Badge("ic_badge");
//        fermatDrawable.setTestSize(32);
//        fermatDrawable.setNumber(1);
//        runtimeTab.setFermatDrawable(fermatDrawable);

        //FermatTextView
        //todo: josé acá agregale lo tuyo
//        FermatTextViewRuntime fermatTextViewRuntime = new FermatTextViewRuntime();
//        fermatTextViewRuntime.setTitle("CHAT");
//        fermatTextViewRuntime.setFontType(FontType.ROBOTO_REGULAR);
//        fermatTextViewRuntime.setTitleSize(20);
//
//        TabBadgeView tabBadgeView = new TabBadgeView();
//        tabBadgeView.setFermatTextViewRuntime(fermatTextViewRuntime);
//        tabBadgeView.setBadge(fermatDrawable);
//        runtimeTab.setFermatView(tabBadgeView);

        runtimeTab.setFragment(new FermatRuntimeFragment(1, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey()));
        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());
        runtimeFragment.setOwner(owner);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());

        OptionsMenu optionsMenu = new OptionsMenu();
        SearchMenuItem searchOptionMenuItem = new SearchMenuItem(1);
        searchOptionMenuItem.setFermatDrawable(new FermatDrawable(2, "search", owner, SourceLocation.DEVELOPER_RESOURCES));
        searchOptionMenuItem.setLabel("Search");
        searchOptionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        searchOptionMenuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);

        //search view focus event
        SearchViewOnPressEvent esearchEvent = new SearchViewOnPressEvent();
        esearchEvent.setIsToolbarTitleVisible(false);
        searchOptionMenuItem.setOptionMenuPressEvent(esearchEvent);
        optionsMenu.addMenuItem(searchOptionMenuItem);

        OptionMenuItem optionMenuItem = new OptionMenuItem(2);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Delete All Chats");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(3);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Go to Profile");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(4);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Go to Community");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(5);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Help");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeFragment.setOptionsMenu(optionsMenu);

        runtimeTab.setFragment(runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey(), runtimeFragment);

        //Tabs Contacts
        runtimeTab = new Tab();
        runtimeTab.setLabel("CONTACTS");

        runtimeTab.setFragment(new FermatRuntimeFragment(2, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey()));
        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey());
        runtimeFragment.setOwner(owner);

        //Badge Tab View
//        TabBadgeView tabBadgeView = new TabBadgeView(200,SourceLocation.FERMAT_FRAMEWORK);
//        tabBadgeView.setTitle("CONTACTS");
//        runtimeTab.setFermatView(tabBadgeView);

        optionsMenu = new OptionsMenu();
        optionMenuItem = new OptionMenuItem(1);
        optionMenuItem.setFermatDrawable(new FermatDrawable(2, "ic_search", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Search");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionMenuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(2);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Go to Profile");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(3);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Go to Community");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(4);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Help");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeFragment.setOptionsMenu(optionsMenu);

        runtimeTab.setFragment(runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey(), runtimeFragment);

        runtimeActivity.setTabStrip(runtimeTabStrip);

        chtChat.addActivity(runtimeActivity);
        listSubApp.put(chtChat.getPublicKey(), chtChat);

        // Activity: Broadcast step one
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broadcast");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey());

        // Activity: Broadcast step two
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#F9F9F9");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broadcast");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey());

        // Activity: Contacts
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_CONTACTLIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_CONTACTLIST.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        //chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#47BF73");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contacts");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#47BF73");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey());

        // Activity: Wizard step two scheduled
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_SCHEDULED_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_SCHEDULED_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
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

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_SCHEDULED_DETAIL.getKey());
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
//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT.getKey(), runtimeFragment);
//        runtimeTabStrip.addTab(runtimeTab);
//
//        //Tabs Contacts
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("CONTACTS");
//        runtimeTab.setFragment(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT);
//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT.getKey());
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
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contact Profile");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_CONTACT_DETAIL_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CONTACT_DETAIL_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CONTACT_DETAIL_FRAGMENT.getKey());

        // Activity: Edit Contact
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_EDIT_CONTACT);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_EDIT_CONTACT.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
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

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_EDIT_CONTACT_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_EDIT_CONTACT_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_EDIT_CONTACT_FRAGMENT.getKey());

        // Activity: Connection list
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_CONNECTIONLIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_CONNECTIONLIST.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Available Connections");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_CONNECTIONLIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_CONNECTIONLIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_CONNECTIONLIST_FRAGMENT.getKey());

        // Activity: Chat Detail Messages
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_MESSAGE_LIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_MESSAGE_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("");//set title in fragment
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");

        owner = new Owner();
        owner.setOwnerAppPublicKey(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());

        MenuItem leftIconMenuItem = new MenuItem();
        leftIconMenuItem.setFermatDrawable(new FermatDrawable(7, "open_nav", owner, SourceLocation.DEVELOPER_RESOURCES));
        leftIconMenuItem.setAppLinkPublicKey("back");
        //leftIconMenuItem.setLinkToActivity(Activities.CHT_CHAT_OPEN_CHATLIST);
        runtimeTitleBar.setNavItem(leftIconMenuItem);

        runtimeActivity.setTitleBar(runtimeTitleBar);

        owner = new Owner();
        owner.setOwnerAppPublicKey(SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());

        optionsMenu = new OptionsMenu();
        optionMenuItem = new OptionMenuItem(1);
        optionMenuItem.setFermatDrawable(new FermatDrawable(2, "ic_search", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItem.setLabel("Search");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionMenuItem.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionsMenu.addMenuItem(optionMenuItem);

        optionMenuItem = new OptionMenuItem(2);
        optionMenuItem.setLabel("Clear Chat");
        optionMenuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenu.addMenuItem(optionMenuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_MESSAGE_LIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_MESSAGE_LIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_MESSAGE_LIST_FRAGMENT.getKey());

        // Activity: Broadcast
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broadcast");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL.getKey());

        // Activity: Broadcast 2
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broadcast");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL.getKey());

        // Activity: Profile list
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_PROFILELIST);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_PROFILELIST.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_PROFILELIST);
        runtimeActivity.setBackPublicKey(chatPublicKey);
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Your Profiles");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_PROFILELIST_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_PROFILELIST_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_PROFILELIST_FRAGMENT.getKey());

        // Activity: Profile Detail
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("My Profile");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_PROFILE_DETAIL_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_PROFILE_DETAIL_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_PROFILE_DETAIL_FRAGMENT.getKey());

        // Activity: Send Error Report
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT);
        runtimeActivity.setBackPublicKey(chatPublicKey);//runtimeActivity.setBackPublicKey(Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        chtChat.addActivity(runtimeActivity);

        statusBar = new StatusBar();
        statusBar.setColor("#075E55");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Error Report");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#FFFFFF");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E55");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_OPEN_SEND_ERROR_REPORT_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_OPEN_SEND_ERROR_REPORT_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_OPEN_SEND_ERROR_REPORT_FRAGMENT.getKey());

        listSubApp.put(chtChat.getPublicKey(), chtChat);
    }

    private void createIntraUserCommunitySubAppNavigationStructure() throws InvalidParameterException{

        AppNavigationStructure subAppIntraUser;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;

        subAppIntraUser = new AppNavigationStructure();
        final String communityPublicKey = SubAppsPublicKeys.CCP_COMMUNITY.getCode();
        subAppIntraUser.setPublicKey(communityPublicKey);
        subAppIntraUser.setPlatform(Platforms.CRYPTO_CURRENCY_PLATFORM);
        //listSubApp.put(subAppIntraUser.getPublicKey(), subAppIntraUser);

        //Activity Home Browser
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        runtimeActivity.setColor("#F9F9F9");
        //subAppIntraUser.changeActualStartActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD.getCode());
        statusBar = new StatusBar();
        //statusBar.setColor("#48000000");
        statusBar.setColor("#21386D");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("CCP Community");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(false);
        runtimeTitleBar.setColor("#21386D");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        //Owner
        Owner owner = new Owner();
        owner.setOwnerAppPublicKey(communityPublicKey);


        //Menu Tabs
        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#21386D");
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#7DD5CA");

        //Tabs Browser
        runtimeTab = new Tab();
        runtimeTab.setLabel("BROWSER");

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());
        runtimeFragment.setOwner(owner);
        runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());

        OptionsMenu optionsMenuBrowser = new OptionsMenu();


        OptionMenuItem optionMenuItemBrowser = new OptionMenuItem(1);
        optionMenuItemBrowser.setFermatDrawable(new FermatDrawable(1, "search_icon", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemBrowser.setLabel("Search");
        optionMenuItemBrowser.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionMenuItemBrowser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenuBrowser.addMenuItem(optionMenuItemBrowser);

        optionMenuItemBrowser = new OptionMenuItem(2);
        optionMenuItemBrowser.setFermatDrawable(new FermatDrawable(2, "geolocalitation_icon", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemBrowser.setLabel("Geolocalitation");
        optionMenuItemBrowser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenuBrowser.addMenuItem(optionMenuItemBrowser);

        optionMenuItemBrowser = new OptionMenuItem(3);
        optionMenuItemBrowser.setFermatDrawable(new FermatDrawable(3, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemBrowser.setLabel("Edit My Profile");
        optionMenuItemBrowser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuBrowser.addMenuItem(optionMenuItemBrowser);

        optionMenuItemBrowser = new OptionMenuItem(4);
        optionMenuItemBrowser.setFermatDrawable(new FermatDrawable(3, "help_icon", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemBrowser.setLabel("Help");
        optionMenuItemBrowser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuBrowser.addMenuItem(optionMenuItemBrowser);




        runtimeFragment.setOptionsMenu(optionsMenuBrowser);

        runtimeTab.setFragment(runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);
//        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey(), runtimeFragment);

        //Tabs Connections
        runtimeTab = new Tab();
        runtimeTab.setLabel("CONNECTIONS");

        runtimeTab.setFragment(new FermatRuntimeFragment(2, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT.getKey()));
        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT.getKey());
        runtimeFragment.setOwner(owner);

        OptionsMenu optionsMenuConn = new OptionsMenu();

        OptionMenuItem optionMenuItemConn = new OptionMenuItem(1);
        optionMenuItemConn.setFermatDrawable(new FermatDrawable(1, "search_icon", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemConn.setLabel("Search");
        optionMenuItemConn.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionMenuItemConn.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenuConn.addMenuItem(optionMenuItemConn);

        optionMenuItemConn = new OptionMenuItem(2);
        optionMenuItemConn.setFermatDrawable(new FermatDrawable(2, "geolocalitation_icon", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemConn.setLabel("Geolocalitation");
        optionMenuItemConn.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenuConn.addMenuItem(optionMenuItemConn);

        optionMenuItemConn = new OptionMenuItem(3);
        optionMenuItemConn.setFermatDrawable(new FermatDrawable(2, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemConn.setLabel("Delete all contacts");
        optionMenuItemConn.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuConn.addMenuItem(optionMenuItemConn);

        optionMenuItemConn = new OptionMenuItem(4);
        optionMenuItemConn.setFermatDrawable(new FermatDrawable(3, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemConn.setLabel("Edit My Profile");
        optionMenuItemConn.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuConn.addMenuItem(optionMenuItemConn);

        optionMenuItemConn = new OptionMenuItem(5);
        optionMenuItemConn.setFermatDrawable(new FermatDrawable(3, "help_icon", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemConn.setLabel("Help");
        optionMenuItemConn.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuConn.addMenuItem(optionMenuItemConn);

        runtimeFragment.setOptionsMenu(optionsMenuConn);

        runtimeTab.setFragment(runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);
       // runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT.getKey(), runtimeFragment);

        //Tabs Notifications
        runtimeTab = new Tab();
        runtimeTab.setLabel("NOTIFICATIONS");

        runtimeTab.setFragment(new FermatRuntimeFragment(3, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey()));
        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());
        runtimeFragment.setOwner(owner);

        OptionsMenu optionsMenuNot = new OptionsMenu();

        OptionMenuItem optionMenuItemNot = new OptionMenuItem(1);
        optionMenuItemNot.setFermatDrawable(new FermatDrawable(1, "search_icon", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemNot.setLabel("Search");
        optionMenuItemNot.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionMenuItemNot.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenuNot.addMenuItem(optionMenuItemNot);

        optionMenuItemNot = new OptionMenuItem(2);
        optionMenuItemNot.setFermatDrawable(new FermatDrawable(2, "geolocalitation_icon", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemNot.setLabel("Geolocalitation");
        optionMenuItemNot.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenuNot.addMenuItem(optionMenuItemNot);

        optionMenuItemNot = new OptionMenuItem(3);
        optionMenuItemNot.setFermatDrawable(new FermatDrawable(3, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemNot.setLabel("Edit My Profile");
        optionMenuItemNot.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuNot.addMenuItem(optionMenuItemNot);

        optionMenuItemNot = new OptionMenuItem(4);
        optionMenuItemNot.setFermatDrawable(new FermatDrawable(3, "help_icon", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemNot.setLabel("Help");
        optionMenuItemNot.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuNot.addMenuItem(optionMenuItemNot);



        runtimeFragment.setOptionsMenu(optionsMenuNot);

        runtimeTab.setFragment(runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);
        //runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey(), runtimeFragment);

        runtimeActivity.setTabStrip(runtimeTabStrip);

        subAppIntraUser.addActivity(runtimeActivity);
        subAppIntraUser.changeActualStartActivity(runtimeActivity.getType().getCode());

        listSubApp.put(subAppIntraUser.getPublicKey(), subAppIntraUser);

        // Activity: Other Profile
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE);
        runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        //runtimeActivity.setColor("#FF0B46F0");
        runtimeActivity.setBackgroundColor("#F9F9F9");

        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#21386D");
        runtimeTitleBar.setIconName("back");
        runtimeTitleBar.setLabelSize(18);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());

        subAppIntraUser.addActivity(runtimeActivity);
        listSubApp.put(subAppIntraUser.getPublicKey(), subAppIntraUser);
    }

    private void createChatCommunitySubAppNavigationStructure() throws InvalidParameterException {

        AppNavigationStructure chtComm;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;

        chtComm = new AppNavigationStructure();
        final String communityPublicKey = SubAppsPublicKeys.CHT_COMMUNITY.getCode();
        chtComm.setPublicKey(communityPublicKey);
        chtComm.setPlatform(Platforms.CHAT_PLATFORM);
        //listSubApp.put(chtComm.getPublicKey(), chtComm);

        statusBar = new StatusBar();
        statusBar.setColor("#075E54");

        //Activity Home Browser
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setActivityType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD.getCode());
        runtimeActivity.setBackgroundColor("#F9F9F9");
        runtimeActivity.setColor("#F9F9F9");
        //chtComm.changeActualStartActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD.getCode());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("P2P Chat Community");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(false);
        runtimeTitleBar.setColor("#075E54");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeActivity.setStatusBar(statusBar);

        //Owner
        Owner owner = new Owner();
        owner.setOwnerAppPublicKey(communityPublicKey);

//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());

        //runtimeActivity.setSideMenu(runtimeSideMenu);
        //chtComm.addActivity(runtimeActivity);

        //Menu Tabs
        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#075E54");
        runtimeTabStrip.setTabsTextColor("#F9F9F9");
        runtimeTabStrip.setTabsIndicateColor("#F9F9F9");

        //Tabs Browser
        runtimeTab = new Tab();
        runtimeTab.setLabel("   BROWSER   ");

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());
        runtimeFragment.setOwner(owner);
        runtimeActivity.setStartFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey());

        OptionsMenu optionsMenuBrowser = new OptionsMenu();
        OptionMenuItem optionMenuItemBrowser = new OptionMenuItem(1);
        optionMenuItemBrowser.setFermatDrawable(new FermatDrawable(3, "ic_search", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemBrowser.setLabel("Search");
        optionMenuItemBrowser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionMenuItemBrowser.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionsMenuBrowser.addMenuItem(optionMenuItemBrowser);

        optionMenuItemBrowser = new OptionMenuItem(2);
        optionMenuItemBrowser.setFermatDrawable(new FermatDrawable(2, "location", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemBrowser.setLabel("Location");
        optionMenuItemBrowser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenuBrowser.addMenuItem(optionMenuItemBrowser);

        optionMenuItemBrowser = new OptionMenuItem(3);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemBrowser.setLabel("Go to Profile");
        optionMenuItemBrowser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuBrowser.addMenuItem(optionMenuItemBrowser);

        optionMenuItemBrowser = new OptionMenuItem(4);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemBrowser.setLabel("Go to Chat");
        optionMenuItemBrowser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuBrowser.addMenuItem(optionMenuItemBrowser);

        optionMenuItemBrowser = new OptionMenuItem(5);
        optionMenuItemBrowser.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemBrowser.setLabel("Help");
        optionMenuItemBrowser.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuBrowser.addMenuItem(optionMenuItemBrowser);

        runtimeFragment.setOptionsMenu(optionsMenuBrowser);

        runtimeTab.setFragment(runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);
//        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT.getKey(), runtimeFragment);

        //Tabs Connections
        runtimeTab = new Tab();
        runtimeTab.setLabel(" CONNECTIONS ");

        runtimeTab.setFragment(new FermatRuntimeFragment(2, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT.getKey()));
        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT.getKey());
        runtimeFragment.setOwner(owner);

        OptionsMenu optionsMenuConn = new OptionsMenu();
        OptionMenuItem optionMenuItemConn = new OptionMenuItem(1);
        optionMenuItemConn.setFermatDrawable(new FermatDrawable(3, "search", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemConn.setLabel("Search");
        optionMenuItemConn.setActionViewClass(OptionMenuViewsAvailables.SEARCH_VIEW);
        optionMenuItemConn.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenuConn.addMenuItem(optionMenuItemConn);

        optionMenuItemConn = new OptionMenuItem(2);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemConn.setLabel("Go to Profile");
        optionMenuItemConn.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuConn.addMenuItem(optionMenuItemConn);

        optionMenuItemConn = new OptionMenuItem(3);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemConn.setLabel("Go to Chat");
        optionMenuItemConn.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuConn.addMenuItem(optionMenuItemConn);

        optionMenuItemConn = new OptionMenuItem(4);
        optionMenuItemConn.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemConn.setLabel("Help");
        optionMenuItemConn.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuConn.addMenuItem(optionMenuItemConn);

        runtimeFragment.setOptionsMenu(optionsMenuConn);

        runtimeTab.setFragment(runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT.getKey(), runtimeFragment);

        //Tabs Notifications
        runtimeTab = new Tab();
        runtimeTab.setLabel("NOTIFICATIONS");

        runtimeTab.setFragment(new FermatRuntimeFragment(3, owner, SourceLocation.DEVELOPER_RESOURCES, Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey()));
        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());
        runtimeFragment.setOwner(owner);

        OptionsMenu optionsMenuNot = new OptionsMenu();
        OptionMenuItem optionMenuItemNot = new OptionMenuItem(1);
        //optionMenuItem.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemNot.setLabel("Go to Profile");
        optionMenuItemNot.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuNot.addMenuItem(optionMenuItemNot);

        optionMenuItemNot = new OptionMenuItem(2);
        //optionMenuItemNot.setFermatDrawable(new FermatDrawable(1, "delete_all_chats", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemNot.setLabel("Go to Chat");
        optionMenuItemNot.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuNot.addMenuItem(optionMenuItemNot);

        optionMenuItemNot = new OptionMenuItem(3);
        optionMenuItemNot.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner, SourceLocation.DEVELOPER_RESOURCES));
        optionMenuItemNot.setLabel("Help");
        optionMenuItemNot.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        optionsMenuNot.addMenuItem(optionMenuItemNot);

        runtimeFragment.setOptionsMenu(optionsMenuNot);

        runtimeTab.setFragment(runtimeFragment);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey(), runtimeFragment);

        runtimeActivity.setTabStrip(runtimeTabStrip);

        chtComm.addActivity(runtimeActivity);
        chtComm.changeActualStartActivity(runtimeActivity.getType().getCode());

        listSubApp.put(chtComm.getPublicKey(), chtComm);
//
//        // Activity: Connection
//        runtimeActivity = new Activity();
//        runtimeActivity.setType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST);
//        runtimeActivity.setActivityType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST.getCode());
//        runtimeActivity.setBackActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD);
//        runtimeActivity.setBackPublicKey(communityPublicKey);
//        runtimeActivity.setBackgroundColor("F9F9F9");
//
//        runtimeActivity.setStatusBar(statusBar);
//
//        runtimeTitleBar = new TitleBar();
//        runtimeTitleBar.setLabel("CONNECTIONS");
//        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
//        runtimeTitleBar.setColor("#47BF73");
//        runtimeTitleBar.setLabelSize(18);
//        runtimeActivity.setTitleBar(runtimeTitleBar);
//
//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT.getKey());
//
//        //runtimeActivity.setSideMenu(runtimeSideMenu);
//        chtComm.addActivity(runtimeActivity);
//
//        // Activity: Notifications
//        runtimeActivity = new Activity();
//        runtimeActivity.setType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS);
//        runtimeActivity.setActivityType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS.getCode());
//        runtimeActivity.setBackActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD);
//        runtimeActivity.setBackPublicKey(communityPublicKey);
//        runtimeActivity.setBackgroundColor("F9F9F9");
//
//        runtimeActivity.setStatusBar(statusBar);
//
//        runtimeTitleBar = new TitleBar();
//        runtimeTitleBar.setLabel("Notifications");
//        runtimeTitleBar.setTitleColor("#ffffff");
//        runtimeTitleBar.setIsTitleTextStatic(true);
//        runtimeTitleBar.setColor("#47BF73");
//        runtimeTitleBar.setLabelSize(20);
//        runtimeActivity.setTitleBar(runtimeTitleBar);
//
//        runtimeFragment = new FermatRuntimeFragment();
//        runtimeFragment.setFragmentCode(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());
//        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey(), runtimeFragment);
//        runtimeActivity.setStartFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT.getKey());
//
//        //runtimeActivity.setSideMenu(runtimeSideMenu);
//        chtComm.addActivity(runtimeActivity);

        // Activity: Other Profile
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE);
        runtimeActivity.setActivityType(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        //runtimeActivity.setColor("#FF0B46F0");
        runtimeActivity.setBackgroundColor("#F9F9F9");

        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E54");
        runtimeTitleBar.setIconName("back");
        runtimeTitleBar.setLabelSize(18);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT.getKey());

        chtComm.addActivity(runtimeActivity);
        listSubApp.put(chtComm.getPublicKey(), chtComm);
    }

    private void createChatIdentitySubAppNavigationStructure() throws InvalidParameterException {

        AppNavigationStructure runtimeSubApp;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar statusBar;
        FermatRuntimeFragment runtimeFragment;

        runtimeSubApp = new AppNavigationStructure();
        String chatIdentityPublicKey = SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode();
        runtimeSubApp.setPublicKey(chatIdentityPublicKey);

        // Activity: Create New Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_CREATE_IDENTITY);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_CREATE_IDENTITY.getCode());

        runtimeActivity.setColor("#075e53");
        runtimeSubApp.addActivity(runtimeActivity);
        runtimeSubApp.changeActualStartActivity(Activities.CHT_CHAT_CREATE_IDENTITY.getCode());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("P2P Chat Profile");
        runtimeTitleBar.setColor("#075e53");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        MenuItem leftIconMenuItem = new MenuItem();

        //Owner
        Owner owner = new Owner();
        owner.setOwnerAppPublicKey(chatIdentityPublicKey);

        leftIconMenuItem.setFermatDrawable(new FermatDrawable(7, "open_nav", owner, SourceLocation.DEVELOPER_RESOURCES));
        leftIconMenuItem.setAppLinkPublicKey("back");
        runtimeTitleBar.setNavItem(leftIconMenuItem);

        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#075e53");
        runtimeActivity.setStatusBar(statusBar);

        Owner owner2 = new Owner();
        owner2.setOwnerAppPublicKey(SubAppsPublicKeys.CHT_COMMUNITY.getCode());

        OptionsMenu optionsMenu = new OptionsMenu();
        OptionMenuItem menuItem = new OptionMenuItem(1);
        menuItem.setFermatDrawable(new FermatDrawable(2, "ic_location", owner2, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setLabel("Location");
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_ALWAYS);
        optionsMenu.addMenuItem(menuItem);

        menuItem = new OptionMenuItem(2);
        menuItem.setFermatDrawable(new FermatDrawable(1, "ic_welcome_dialog", owner2, SourceLocation.DEVELOPER_RESOURCES));
        menuItem.setShowAsAction(OptionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menuItem.setLabel("Help");
        optionsMenu.addMenuItem(menuItem);

        runtimeActivity.setOptionsMenu(optionsMenu);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_CREATE_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_CREATE_IDENTITY_FRAGMENT.getKey());

        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

        //Activity Geolocation
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CHT_CHAT_GEOLOCATION_IDENTITY);
        runtimeActivity.setActivityType(Activities.CHT_CHAT_GEOLOCATION_IDENTITY.getCode());
        runtimeActivity.setBackActivity(Activities.CHT_CHAT_CREATE_IDENTITY);
        runtimeActivity.setBackPublicKey(chatIdentityPublicKey);
        runtimeActivity.setBackgroundColor("#F9F9F9");

        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setLabel("Geolocation");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#075E54");
        runtimeTitleBar.setIconName("back");
        runtimeTitleBar.setLabelSize(18);
        leftIconMenuItem = new MenuItem();

        leftIconMenuItem.setFermatDrawable(new FermatDrawable(7, "open_nav", owner, SourceLocation.DEVELOPER_RESOURCES));
        leftIconMenuItem.setAppLinkPublicKey("back");
        runtimeTitleBar.setNavItem(leftIconMenuItem);

        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.CHT_CHAT_GEOLOCATION_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CHT_CHAT_GEOLOCATION_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CHT_CHAT_GEOLOCATION_IDENTITY_FRAGMENT.getKey());

        runtimeSubApp.addActivity(runtimeActivity);
        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    }

    private void createFanCommunitySubAppNavigationStructure() throws InvalidParameterException {
        TitleBar runtimeTitleBar;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;
        StatusBar statusBar;
        Activity runtimeActivity;
        //Fragment runtimeFragment;
        String tabTitleColor = "#555759";
        String tabBarColor="#E6E7E8";
        FermatRuntimeFragment runtimeFragment;

        AppNavigationStructure subAppFanCommunity = new AppNavigationStructure();

        String communityPublicKey = SubAppsPublicKeys.ART_FAN_COMMUNITY.getCode();
        subAppFanCommunity.setPublicKey(communityPublicKey);

        //Side Menu definition
        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#F1F2F2");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Fan Users");
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Your Identities");
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_LOCAL_IDENTITIES_LIST);
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
        statusBar.setColor("#000000");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Fan Users");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor(tabTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(tabBarColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppFanCommunity.addActivity(runtimeActivity);

        //LOCAL IDENTITIES

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_FAN_COMMUNITY_LOCAL_IDENTITIES_LIST);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_FAN_COMMUNITY_LOCAL_IDENTITIES_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#0072bb");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Your Identities");
        runtimeTitleBar.setTitleColor(tabTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(tabBarColor);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#E6E7E8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_FAN_COMMUNITY_LOCAL_IDENTITIES_LIST.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_LOCAL_IDENTITIES_LIST.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_FAN_COMMUNITY_LOCAL_IDENTITIES_LIST.getKey());

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
        statusBar.setColor("#E6E7E8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor(tabTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(tabBarColor);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#E6E7E8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());
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
        runtimeTitleBar.setTitleColor(tabTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(tabBarColor);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#6D6F71");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());
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
        runtimeTitleBar.setTitleColor(tabTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor(tabBarColor);
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#E6E7E8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());
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
        FermatRuntimeFragment runtimeFragment;

        AppNavigationStructure subAppArtistCommunity = new AppNavigationStructure();
        String communityPublicKey = SubAppsPublicKeys.ART_ARTIST_COMMUNITY.getCode();
        subAppArtistCommunity.setPublicKey(communityPublicKey);

        //Side Menu definition
        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#363636");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Artist Users");
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Your Identities");
        runtimeMenuItem.setAppLinkPublicKey(communityPublicKey);
        runtimeMenuItem.setLinkToActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_LOCAL_IDENTITIES_LIST);
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
        statusBar.setColor("#368CA8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Artist Users");
        runtimeTitleBar.setLabelSize(20);
        runtimeTitleBar.setTitleColor("#000000");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getKey());

        runtimeActivity.setSideMenu(runtimeSideMenu);
        subAppArtistCommunity.addActivity(runtimeActivity);

        //LOCAL IDENTITIES

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_LOCAL_IDENTITIES_LIST);
        runtimeActivity.setActivityType(Activities.ART_SUB_APP_ARTIST_COMMUNITY_LOCAL_IDENTITIES_LIST.getCode());
        runtimeActivity.setBackActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD);
        runtimeActivity.setBackPublicKey(communityPublicKey);
        runtimeActivity.setColor("#FF0B46F0");

        statusBar = new StatusBar();
        statusBar.setColor("#368CA8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Your Identities");
        runtimeTitleBar.setTitleColor("#000000");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#368CA8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_LOCAL_IDENTITIES_LIST.getKey());
        runtimeActivity.addFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_LOCAL_IDENTITIES_LIST.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_LOCAL_IDENTITIES_LIST.getKey());

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
        statusBar.setColor("#368CA8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Connections");
        runtimeTitleBar.setTitleColor("#000000");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#368CA8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_FRIEND_LIST.getKey());
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
        statusBar.setColor("#368CA8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Notifications");
        runtimeTitleBar.setTitleColor("#000000");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#368CA8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_NOTIFICATIONS.getKey());
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
        statusBar.setColor("#368CA8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Profile");
        runtimeTitleBar.setIconName("Back");
        runtimeTitleBar.setTitleColor("#000000");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setColor("#0072bb");
        runtimeTitleBar.setLabelSize(20);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        statusBar = new StatusBar();
        statusBar.setColor("#368CA8");
        runtimeActivity.setStatusBar(statusBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_OTHER_PROFILE.getKey());
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
        FermatRuntimeFragment runtimeFragment;
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
        runtimeTitleBar.setTitleColor("#6D6F71");
        runtimeTitleBar.setColor("#E6E7E8");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.ART_MUSIC_PLAYER_MAIN_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.ART_MUSIC_PLAYER_MAIN_ACTIVITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.ART_MUSIC_PLAYER_MAIN_ACTIVITY.getKey());


        listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    }

}
