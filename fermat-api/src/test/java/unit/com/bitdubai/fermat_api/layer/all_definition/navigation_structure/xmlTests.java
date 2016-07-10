//package unit.com.bitdubai.fermat_api.layer.all_definition.navigation_structure;
//
//import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
//import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.OptionsMenu;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
//import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
//
//import org.junit.Test;
//
///**
// * Created by Matias Furszyfer on 8/14/15.
// */
//public class xmlTests {
//
//    @Test
//    public void fromObjectToXml(){
//        Activity runtimeActivity;
//        Fragment runtimeFragment;
//        WalletNavigationStructure runtimeWalletNavigationStructure;
//        TitleBar runtimeTitleBar;
//        SideMenu runtimeSideMenu;
//        OptionsMenu runtimeMainMenu;
//        MenuItem runtimeMenuItem;
//        TabStrip runtimeTabStrip;
//        StatusBar runtimeStatusBar;
//
//        Tab runtimeTab;
//
//        String publicKey;
//
//        runtimeWalletNavigationStructure = new WalletNavigationStructure();
//       runtimeWalletNavigationStructure.setWalletCategory(WalletCategory.REFERENCE_WALLET.getCode());
//       runtimeWalletNavigationStructure.setWalletType(WalletType.REFERENCE.getCode());
//        publicKey="reference_wallet";
//       runtimeWalletNavigationStructure.setPublicKey(publicKey);
//
//        //listWallets.put(publicKey, runtimeWalletNavigationStructure);
//
//        runtimeActivity= new Activity();
//        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
//        runtimeActivity.setColor("#8bba9e");
//        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//        runtimeWalletNavigationStructure.setStartActivity(runtimeActivity.getType());
//
//        runtimeTitleBar = new TitleBar();
//        runtimeTitleBar.setLabel("bitDubai bitcoin Wallet");
//        runtimeTitleBar.setLabelSize(16);
//
//        runtimeActivity.setTitleBar(runtimeTitleBar);
//        runtimeActivity.setColor("#72af9c");
//        //runtimeActivity.setColor("#d07b62");
//
//
//        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
//        runtimeStatusBar.setColor("#72af9c");
//
//        runtimeActivity.setStatusBar(runtimeStatusBar);
//
//
//        runtimeTabStrip = new TabStrip();
//
//        runtimeTabStrip.setTabsColor("#8bba9e");
//
//        runtimeTabStrip.setTabsTextColor("#FFFFFF");
//
//        runtimeTabStrip.setTabsIndicateColor("#72af9c");
//
//
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Send");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
//        runtimeTabStrip.addTab(runtimeTab);
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Balance");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
//        runtimeTabStrip.addTab(runtimeTab);
//        runtimeTabStrip.setStartItem(1);
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Receive");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
//        runtimeTabStrip.addTab(runtimeTab);
//        /*
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Transactions");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
//        runtimeTabStrip.addTab(runtimeTab);
//
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Money request");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST);
//        runtimeTabStrip.addTab(runtimeTab);
//
//        */
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Contacts");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
//        runtimeTabStrip.addTab(runtimeTab);
//
//
//
//
//
//        runtimeTabStrip.setDividerColor(0x72af9c);
//        //runtimeTabStrip.setBackgroundColor("#72af9c");
//        runtimeActivity.setTabStrip(runtimeTabStrip);
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey(), runtimeFragment);
//
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND.getKey(),runtimeFragment);
//
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE.getKey(),runtimeFragment);
//
////        runtimeFragment = new Fragment();
////        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
////        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS,runtimeFragment);
//
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST.getKey(), runtimeFragment);
//
//
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey(), runtimeFragment);
//
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS.getKey());
//        runtimeFragment.setBack(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS.getKey(), runtimeFragment);
//
//        //Navigation
//
//        runtimeSideMenu = new SideMenu();
//
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Contacts");
//        runtimeMenuItem.setIcon("contacts");
//        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Transactions");
//        runtimeMenuItem.setIcon("transactions");
//        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Payment request");
//        runtimeMenuItem.setIcon("Payment_request");
//        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Settings");
//        runtimeMenuItem.setIcon("Settings");
//        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Exit");
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeActivity.setSideMenu(runtimeSideMenu);
//
//        //fin navigation
//
//
//        /**
//         * Menu
//         */
//
//        runtimeMainMenu = new OptionsMenu();
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Settings");
//        runtimeMainMenu.addMenuItem(runtimeMenuItem);
//
//
//        runtimeActivity.setOptionsMenu(runtimeMainMenu);
//
//        /**
//         *  Fin de menu
//         */
//
//        /**
//         * Transaction Activity
//         */
//
//        runtimeActivity= new Activity();
//        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
//        runtimeActivity.setColor("#8bba9e");
//        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
//
//        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//        runtimeTitleBar = new TitleBar();
//        runtimeTitleBar.setLabel("bitdubai bitcoin Wallet");
//        runtimeTitleBar.setLabelSize(16);
//        runtimeActivity.setTitleBar(runtimeTitleBar);
//        runtimeActivity.setColor("#72af9c");
//        //runtimeActivity.setColor("#d07b62");
//        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
//
//
//        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
//        runtimeStatusBar.setColor("#72af9c");
//
//        runtimeActivity.setStatusBar(runtimeStatusBar);
//
//        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS.getKey());
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS.getKey());
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS.getKey(), runtimeFragment);
//
//
//        //Navigation
//
//        runtimeSideMenu = new SideMenu();
//
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Personal Wallets");
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Shops");
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Commercial wallets");
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Factory Projects");
//        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_FACTORY_MAIN);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Published Wallets");
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Wallet Store");
//        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_STORE_MAIN_ACTIVITY);
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Exit");
//        runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//        runtimeActivity.setSideMenu(runtimeSideMenu);
//
//        //fin navigation
//
//        /**
//         * Menu
//         */
//
//        runtimeMainMenu = new OptionsMenu();
//        runtimeMenuItem = new MenuItem();
//        runtimeMenuItem.setLabel("Settings");
//        runtimeMainMenu.addMenuItem(runtimeMenuItem);
//
//
//        runtimeActivity.setOptionsMenu(runtimeMainMenu);
//
//        /**
//         *  Fin de menu
//         */
//
//
//       String xml =XMLParser.parseObject(runtimeWalletNavigationStructure);
//      System.out.println(xml);
//    }
//
//}
