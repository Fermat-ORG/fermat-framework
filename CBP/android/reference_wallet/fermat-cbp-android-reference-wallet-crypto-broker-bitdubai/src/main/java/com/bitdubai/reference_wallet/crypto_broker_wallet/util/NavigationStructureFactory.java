package com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Header;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

/**
 * Created by nelson on 29/09/15.
 */
public class NavigationStructureFactory {

    public static WalletNavigationStructure createNavigationStructure() {
        WalletNavigationStructure navigationStructure = new WalletNavigationStructure();
        navigationStructure.setWalletCategory(WalletCategory.REFERENCE_WALLET.getCode());
        navigationStructure.setWalletType(WalletType.REFERENCE.getCode());
        navigationStructure.setPublicKey("crypto_broker_wallet");

        SideMenu sideMenu = createSideMenu();

        Activity activity = createHomeActivity();
        activity.setSideMenu(sideMenu);
        navigationStructure.addActivity(activity);
        navigationStructure.setStartActivity(activity.getType());

        activity = createDealsActivity();
        activity.setSideMenu(sideMenu);
        navigationStructure.addActivity(activity);

        activity = createStockPreferenceActivity();
        activity.setSideMenu(sideMenu);
        navigationStructure.addActivity(activity);

        activity = createSettingsActivity();
        activity.setSideMenu(sideMenu);
        navigationStructure.addActivity(activity);

        return navigationStructure;
    }

    public static String getNavigationStructureAsXmlString() {
        WalletNavigationStructure navigationStructure = createNavigationStructure();
        String xml = XMLParser.parseObject(navigationStructure);
        return xml;
    }

    private static SideMenu createSideMenu() {
        SideMenu sideMenu = new SideMenu();

        MenuItem menuItem = new MenuItem();
        menuItem.setLabel("Home");
        menuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        sideMenu.addMenuItem(menuItem);

        menuItem = new MenuItem();
        menuItem.setLabel("Contracts History");
        menuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        sideMenu.addMenuItem(menuItem);

        menuItem = new MenuItem();
        menuItem.setLabel("Earnings");
        menuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS);
        sideMenu.addMenuItem(menuItem);

        menuItem = new MenuItem();
        menuItem.setLabel("Settings");
        menuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS);
        sideMenu.addMenuItem(menuItem);
        return sideMenu;
    }

    private static Activity createHomeActivity() {
        Activity activity = new Activity();
        activity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        activity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_HOME.getCode());
        activity.setColor("#03A9F4");


        TitleBar titleBar = new TitleBar();
        titleBar.setLabel("Crypto Broker Wallet");
        titleBar.setColor("#FFFFFF");
        titleBar.setLabelSize(16);
        activity.setTitleBar(titleBar);


        StatusBar statusBar = new StatusBar();
        statusBar.setColor("#0288D1");
        activity.setStatusBar(statusBar);

        Header header = new Header();
        header.setLabel("Market rate");

        TabStrip tabStrip = new TabStrip();
        tabStrip.setTabsColor("#0288D1");
        tabStrip.setTabsTextColor("#FFFFFF");
        tabStrip.setTabsIndicateColor("#72af9c");
        tabStrip.setDividerColor(0x72af9c);
        activity.setTabStrip(tabStrip);

        Tab tab = new Tab();
        tab.setLabel("Negotiations");
        tab.setFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB);
        tabStrip.addTab(tab);
        Fragment fragment = new Fragment();
        fragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey());
        activity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey(), fragment);

        tab = new Tab();
        tab.setLabel("Contracts");
        tab.setFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB);
        tabStrip.addTab(tab);
        fragment = new Fragment();
        fragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB.getKey());
        activity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB.getKey(), fragment);


        fragment = new Fragment();
        fragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_DEAL_DETAILS.getKey());
        fragment.setBack(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey());
        activity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_DEAL_DETAILS.getKey(), fragment);

        fragment = new Fragment();
        fragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getKey());
        fragment.setBack(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB.getKey());
        activity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS.getKey(), fragment);

        fragment = new Fragment();
        fragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey());
        activity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey(), fragment);


        return activity;
    }

    private static Activity createDealsActivity() {
        Activity activity = new Activity();
        activity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        activity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getCode());
        activity.setColor("#03A9F4");


        TitleBar titleBar = new TitleBar();
        titleBar.setLabel("Deals History");
        titleBar.setColor("#FFFFFF");
        titleBar.setLabelSize(16);
        activity.setTitleBar(titleBar);


        StatusBar statusBar = new StatusBar();
        statusBar.setColor("#0288D1");
        activity.setStatusBar(statusBar);


        Fragment fragment = new Fragment();
        fragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_DEALS.getKey());
        activity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_DEALS.getKey(), fragment);

        fragment = new Fragment();
        fragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_DEAL_DETAILS.getKey());
        fragment.setBack(Fragments.CBP_CRYPTO_BROKER_WALLET_DEALS.getKey());
        activity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_DEAL_DETAILS.getKey(), fragment);

        return activity;
    }


    private static Activity createStockPreferenceActivity() {
        Activity activity = new Activity();
        activity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS);
        activity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getCode());
        activity.setColor("#03A9F4");


        TitleBar titleBar = new TitleBar();
        titleBar.setLabel("Stock Preferences");
        titleBar.setColor("#FFFFFF");
        titleBar.setLabelSize(16);
        activity.setTitleBar(titleBar);


        StatusBar statusBar = new StatusBar();
        statusBar.setColor("#0288D1");
        activity.setStatusBar(statusBar);


        Fragment fragment = new Fragment();
        fragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_PREFERENCE.getKey());
        activity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_PREFERENCE.getKey(), fragment);

        return activity;
    }

    private static Activity createSettingsActivity() {
        Activity activity = new Activity();
        activity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS);
        activity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getCode());
        activity.setColor("#03A9F4");


        TitleBar titleBar = new TitleBar();
        titleBar.setLabel("Settings");
        titleBar.setColor("#FFFFFF");
        titleBar.setLabelSize(16);
        activity.setTitleBar(titleBar);


        StatusBar statusBar = new StatusBar();
        statusBar.setColor("#0288D1");
        activity.setStatusBar(statusBar);


        Fragment fragment = new Fragment();
        fragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey());
        activity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey(), fragment);

        return activity;
    }
}
