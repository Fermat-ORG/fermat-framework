package com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Footer;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Header;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;

/**
 * Created by nelson on 29/09/15.
 */
public class NavigationStructureFactory {

    public static AppNavigationStructure createNavigationStructure() {
        AppNavigationStructure runtimeAppNavigationStructure;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar runtimeStatusBar;
        Header runtimeHeader;
        Fragment runtimeFragment;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;

        final String publicKey = "crypto_broker_wallet";
        final String statusBarColor = "#254478";
        final String titleBarColor = "#254478";
        final String titleBarTitleColor = "#ffffff";
        final int titleBarTextSize = 16;

        runtimeAppNavigationStructure = new AppNavigationStructure();
        runtimeAppNavigationStructure.setPublicKey(publicKey);


        // Side Menu
        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setHasFooter(true);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contracts History");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Earnings");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        // WIZARD
        // step 1 - Set Identity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_IDENTITY);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_IDENTITY.getCode());
        runtimeAppNavigationStructure.addActivity(runtimeActivity);
        runtimeAppNavigationStructure.changeActualStartActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_IDENTITY.getCode());

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_IDENTITY.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_IDENTITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_IDENTITY.getKey());

        // step 2 - Set Merchandises
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_MERCHANDISES);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_MERCHANDISES.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_IDENTITY);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_MERCHANDISES.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_MERCHANDISES.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_MERCHANDISES.getKey());

        // step 3 - Set Earnings
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_MERCHANDISES);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS.getKey());

        // step 4 - Set Providers
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_PROVIDERS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_PROVIDERS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_PROVIDERS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_PROVIDERS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_PROVIDERS.getKey());

        // step 5 - Set Locations
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS.getKey());

        // step 6 - Set Bank Accounts (If bank wallet has been set as Stock)
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_BANK_ACCOUNT);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SET_BANK_ACCOUNT.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_BANK_ACCOUNT.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_BANK_ACCOUNT.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SET_BANK_ACCOUNT.getKey());

        // Create New Location in Wizard Activity
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Create Location");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeTitleBar.setColor(titleBarColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setIconName("back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD.getKey());


        // Activity: Home
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_HOME.getCode());
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broker Wallet");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeHeader = new Header();
        runtimeHeader.setLabel("Market rate");
        runtimeActivity.setHeader(runtimeHeader);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS.getKey(), runtimeFragment);

        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#1278a6");
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#3ec8e8");
        runtimeTabStrip.setDividerColor(0x72af9c);
        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Negotiations");
        runtimeTab.setFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey());

        runtimeTab = new Tab();
        runtimeTab.setLabel("Contracts");
        runtimeTab.setFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB.getKey(), runtimeFragment);

        // TODO falta agregar un footer a navigation structure
        Footer runtimeFooter = new Footer();
        runtimeFooter.setBackgroundColor("#AAAAAA");
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey(), runtimeFragment);
        runtimeFooter.setFragmentCode(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey());
        runtimeActivity.setFooter(runtimeFooter);


        // Activity: Open Negotiation details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Negotiation Details");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setIconName("back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey());


        // Activity: Close Negotiation details - Open Contract
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS_OPEN_CONTRACT);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS_OPEN_CONTRACT.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Negotiation Details");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setIconName("back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());


        // Activity: Close Negotiation details - Close Contract
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS_CLOSE_CONTRACT);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS_CLOSE_CONTRACT.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Negotiation Details");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setIconName("back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());


        // Activity: Open Contract Details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeActivity.setColor("#1189a5");
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contract Details");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setIconName("back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getKey());


        // Activity: Close Contract Details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeActivity.setColor("#1189a5");
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contract Details");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setIconName("back");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey());


        // Activity: Contracts History
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contracts History");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getKey());


        // Activity: Earnings
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Earnings");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getKey());


        // Activity: Settings
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey());

        // Activity: Contracts Details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contracts Details");
        runtimeTitleBar.setLabelSize(titleBarTextSize);
        runtimeTitleBar.setTitleColor(titleBarTitleColor);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getKey());

        return runtimeAppNavigationStructure;
    }

    public static String getNavigationStructureAsXmlString() {
        AppNavigationStructure navigationStructure = createNavigationStructure();
        return XMLParser.parseObject(navigationStructure);
    }
}
