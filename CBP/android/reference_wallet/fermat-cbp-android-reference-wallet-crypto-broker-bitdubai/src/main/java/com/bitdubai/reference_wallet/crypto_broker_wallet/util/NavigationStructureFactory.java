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

        activity = createContractsHistoryActivity();
        activity.setSideMenu(sideMenu);
        navigationStructure.addActivity(activity);

        activity = createEarningsActivity();
        activity.setSideMenu(sideMenu);
        navigationStructure.addActivity(activity);

        activity = createSettingsActivity();
        activity.setSideMenu(sideMenu);
        navigationStructure.addActivity(activity);

        activity = createOpenNegotiationDetailsActivity();
        navigationStructure.addActivity(activity);

        activity = createCloseNegotiationDetailsActivity();
        navigationStructure.addActivity(activity);

        activity = createOpenContractDetailsActivity();
        navigationStructure.addActivity(activity);

        activity = createCloseContractDetailsActivity();
        navigationStructure.addActivity(activity);

        return navigationStructure;
    }

    public static String getNavigationStructureAsXmlString() {
        WalletNavigationStructure navigationStructure = createNavigationStructure();
        String xml = XMLParser.parseObject(navigationStructure);
        return xml;
    }

    private static SideMenu createSideMenu() {
        SideMenu runtimeSideMenu = new SideMenu();

        MenuItem runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contracts History");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Earnings");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        return runtimeSideMenu;
    }

    private static Activity createHomeActivity() {
        Activity runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_HOME.getCode());
        runtimeActivity.setColor("#03A9F4");

        TitleBar runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Crypto Broker Wallet");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        StatusBar runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor("#0288D1");
        runtimeActivity.setStatusBar(runtimeStatusBar);

        Header runtimeHeader = new Header();
        runtimeHeader.setLabel("Market rate");
        Fragment runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS.getKey(), runtimeFragment);

        TabStrip runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#0288D1");
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#72af9c");
        runtimeTabStrip.setDividerColor(0x72af9c);
        runtimeActivity.setTabStrip(runtimeTabStrip);

        Tab runtimeTab = new Tab();
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
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey(), runtimeFragment);

        return runtimeActivity;
    }

    private static Activity createOpenNegotiationDetailsActivity(){
        Activity runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setColor("#03A9F4");

        TitleBar runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Negotiation Details");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        StatusBar runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor("#0288D1");
        runtimeActivity.setStatusBar(runtimeStatusBar);

        Fragment runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey());
        return runtimeActivity;
    }

    private static Activity createCloseNegotiationDetailsActivity(){
        Activity runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getCode());
        runtimeActivity.setColor("#03A9F4");

        TitleBar runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Negotiation Details");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        StatusBar runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor("#0288D1");
        runtimeActivity.setStatusBar(runtimeStatusBar);

        Fragment runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());

        return runtimeActivity;
    }

    private static Activity createOpenContractDetailsActivity(){
        Activity runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS.getCode());
        runtimeActivity.setColor("#03A9F4");

        TitleBar runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contract Details");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        StatusBar runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor("#0288D1");
        runtimeActivity.setStatusBar(runtimeStatusBar);

        Fragment runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS.getKey());

        return runtimeActivity;
    }

    private static Activity createCloseContractDetailsActivity(){
        Activity runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getCode());
        runtimeActivity.setColor("#03A9F4");

        TitleBar runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contract Details");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        StatusBar runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor("#0288D1");
        runtimeActivity.setStatusBar(runtimeStatusBar);

        Fragment runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey());

        return runtimeActivity;
    }

    private static Activity createContractsHistoryActivity(){
        Activity runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getCode());
        runtimeActivity.setColor("#03A9F4");

        TitleBar runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contracts History");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        StatusBar runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor("#0288D1");
        runtimeActivity.setStatusBar(runtimeStatusBar);

        Fragment runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getKey());

        return runtimeActivity;
    }

    private static Activity createEarningsActivity() {
        Activity runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getCode());
        runtimeActivity.setColor("#03A9F4");

        TitleBar runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Earnings");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        StatusBar runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor("#0288D1");
        runtimeActivity.setStatusBar(runtimeStatusBar);

        Fragment runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getKey());

        return runtimeActivity;
    }

    private static Activity createSettingsActivity() {
        Activity runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getCode());
        runtimeActivity.setColor("#03A9F4");

        TitleBar runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        StatusBar runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor("#0288D1");
        runtimeActivity.setStatusBar(runtimeStatusBar);

        Fragment runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey(), runtimeFragment);

        return runtimeActivity;
    }

}
