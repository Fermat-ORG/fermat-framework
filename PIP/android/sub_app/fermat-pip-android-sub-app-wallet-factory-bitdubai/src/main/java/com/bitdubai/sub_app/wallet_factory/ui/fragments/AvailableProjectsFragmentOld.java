package com.bitdubai.sub_app.wallet_factory.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatCallback;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.sub_app.wallet_factory.R;

import java.util.ArrayList;

/**
 * Available projects fragment to clone
 *
 * @author Francisco Vasquez
 * @author Matias Furszy
 * @version 1.0
 */
public class AvailableProjectsFragmentOld extends AbstractFermatFragment implements FermatCallback {

    /**
     * Views
     */
    private View rootView;
    private ListView listView;

    private ArrayList<InstalledWallet> wallets;

    public static AbstractFermatFragment newInstance() {
        return new AvailableProjectsFragmentOld();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        wallets = new ArrayList<>();
        Wallet wallet = new Wallet();
        wallet.setNavigation(startWalletNavigationStructure());
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        */
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.factory_available_projects_old_fragment, container, false);

        listView = (ListView) rootView.findViewById(R.id.projects);
        ArrayAdapter<InstalledWallet> adapter = new ArrayAdapter<InstalledWallet>(getActivity(), android.R.layout.simple_list_item_1, wallets);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /**
                 * Francisco por ahora pasale de primer parametro la estructura de navegación y de segundo la InstalledWallet,
                 *
                 * Usá el metodo ese de changeActivity, y pasale esos parametros, despues lo que va a hacer esto es pintarte en la pantalla toda la wallet con tu estructura de navegación
                 * y los fragmentos nuevos que acabas de poner
                 */
//

                Object[] o = new Object[2];
                o[0] = startWalletNavigationStructure();
                o[1] = wallets.get(i);

                //TODO: Como tercer parametro me tenes que pasar el Skin de la wallet que vas a cambiar así lo pinto por pantalla y se va cambiando

                //TODO: tenes que conseguir el skin desde el tu module o desde algún lado
                //o[2] = (Object)


                o[3] = this;
                changeActivity(Activities.CWP_WALLET_FACTORY_EDIT_WALLET.getCode(), o);
            }
        });

        return rootView;
    }

    /**
     * Meanwhile
     *
     * @return
     */

    private AppNavigationStructure startWalletNavigationStructure() {

        Activity runtimeActivity;
        Fragment runtimeFragment;
        AppNavigationStructure runtimeAppNavigationStructure;
        TitleBar runtimeTitleBar;
        SideMenu runtimeSideMenu;
        MainMenu runtimeMainMenu;
        MenuItem runtimeMenuItem;
        TabStrip runtimeTabStrip;
        StatusBar runtimeStatusBar;

        Tab runtimeTab;

        String publicKey;

        runtimeAppNavigationStructure = new AppNavigationStructure();
        publicKey = "test_wallet";
        runtimeAppNavigationStructure.setPublicKey(publicKey);
        //listWallets.put(publicKey, runtimeAppNavigationStructure);

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeActivity.setColor("#8bba9e");
        runtimeAppNavigationStructure.addActivity(runtimeActivity);
        runtimeAppNavigationStructure.changeActualStartActivity(runtimeActivity.getType().getCode());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("bitDubai bitcoin Wallet");
        runtimeTitleBar.setLabelSize(16);

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
        runtimeTab.setLabel("Send");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Balance");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeTabStrip.setStartItem(1);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Receive");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
        runtimeTabStrip.addTab(runtimeTab);
        /*
        runtimeTab = new Tab();
        runtimeTab.setLabel("Transactions");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
        runtimeTabStrip.addTab(runtimeTab);


        runtimeTab = new Tab();
        runtimeTab.setLabel("Money request");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST);
        runtimeTabStrip.addTab(runtimeTab);

        */

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
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND.getKey());
        runtimeFragment.setBack(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND.getKey(), runtimeFragment);


        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE.getKey(), runtimeFragment);

//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS,runtimeFragment);


        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST.getKey());
        runtimeFragment.setBack(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST.getKey(), runtimeFragment);


        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey(), runtimeFragment);


        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS.getKey());
        runtimeFragment.setBack(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS.getKey(), runtimeFragment);

        //Navigation

        runtimeSideMenu = new SideMenu();


        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contacts");
        runtimeMenuItem.setIcon("contacts");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Transactions");
        runtimeMenuItem.setIcon("transactions");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Payment request");
        runtimeMenuItem.setIcon("Payment_request");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);


        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setIcon("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);


        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Exit");
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        //fin navigation


        /**
         * Menu
         */

        runtimeMainMenu = new MainMenu();
        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMainMenu.addMenuItem(runtimeMenuItem);


        runtimeActivity.setMainMenu(runtimeMainMenu);

        /**
         *  Fin de menu
         */

        /**
         * Transaction Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
        runtimeActivity.setColor("#8bba9e");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);

        runtimeAppNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("bitdubai bitcoin Wallet");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#72af9c");
        //runtimeActivity.setColor("#d07b62");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);


        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#72af9c");

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS.getKey(), runtimeFragment);


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


        return runtimeAppNavigationStructure;
    }

    @Override
    public void onTouchView(Object v) {

    }

    @Override
    public int getCallBackId() {
        return 0;
    }
}
