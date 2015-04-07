package com.bitdubai.android.app;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bitdubai.android.app.common.version_1.classes.MyApplication;

import com.bitdubai.android.app.common.version_1.classes.PagerSlidingTabStrip;

import com.bitdubai.android.app.common.version_1.fragment.NavigationDrawerFragment;
import com.bitdubai.android.app.shell.version_1.activity.FileImageActivity;
import com.bitdubai.android.app.shell.version_1.activity.PagerAdapter;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopChatFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopHistoryFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopMapFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopProductsFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopReviewsFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopShopFragment;
import com.bitdubai.android.app.subapp.shop_manager.version_1.fragment.ShopDesktopFragment;
import com.bitdubai.android.app.subapp.wallet_manager.version_1.fragment.WalletDesktopFragment;

import com.bitdubai.fermat_api.layer._10_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer._10_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer._1_definition.enums.Addons;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_dmp_plugin.layer._12_middleware.app_runtime.developer.bitdubai.version_1.structure.RuntimeActivity;
import com.bitdubai.fermat_dmp_plugin.layer._12_middleware.app_runtime.developer.bitdubai.version_1.structure.RuntimeFragment;
import com.runtime_wallet.bitdubai.age.kids.boys.fragments.CommunityFragment;
import com.runtime_wallet.bitdubai.age.kids.boys.fragments.ContactsFragment;
import com.runtime_wallet.bitdubai.age.kids.boys.fragments.ProfileCardFrontFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.BalanceFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.DiscountsFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.HomeFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ReceiveFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.RefillFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.SendFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ShopFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.AccountDetailAllFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.AccountDetailCreditsFragment;
import com.bitdubai.android.app.subapp.wallet_store.version_1.fragment.AllFragment;
import com.bitdubai.android.app.subapp.wallet_store.version_1.fragment.FreeFragment;
import com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.AndroidOsAddonRoot;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.os.StrictMode;

import com.bitdubai.fermat_api.CantReportCriticalStartingProblem;
import com.bitdubai.fermat_api.CantStartPlatformException;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.*;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.Fragment;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.smartwallet.R;

import com.bitdubai.fermat_core.CorePlatformContext;
import com.runtime_wallet.bitdubai.age.kids.boys.fragments.UsdBalanceFragment;

import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by toshiba on 16/02/2015.
 */
public class RuntimeAppActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    private NavigationDrawerFragment NavigationDrawerFragment;

    private PagerAdapter PagerAdapter;
    public CharSequence Title; // NATALIA TODO:porque esto es publico? LUIS lo usa la funcion Restore Action bar
    private Menu menu;
    private PagerSlidingTabStrip tabStrip;
    private App app;
    private SubApp subApp;
    private com.bitdubai.fermat_api.layer._12_middleware.app_runtime.Activity activity;
    private Map<Fragments, Fragment> fragments;
    private AppRuntimeManager appRuntimeMiddleware;
    private AndroidOsAddonRoot Os;
    private CorePlatformContext platformContext;
    private ViewPager pager;
    private ViewPager pagertabs;
    private MyPagerAdapter adapter;
    private  TextView abTitle;
    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;
    private  MainMenu mainMenumenu;
    private SideMenu sidemenu;
    private String walletStyle = "";
    private TabStrip tabs;
    private  TitleBar titleBar; // Comment
    private boolean firstexecute = true;
    private Bundle savedInstanceState;
    private ViewGroup collection;
    private Platform platform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            setContentView(R.layout.runtime_app_activity_runtime);
            this.savedInstanceState = savedInstanceState;
            //init runtime app

            platform = MyApplication.getPlatform();
            // Context context = this.getApplicationContext();

            this.Os = new AndroidOsAddonRoot();

            this.Os.setContext(this);
            platform.setOs(Os);

            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                if(bundle.getString("executeStart").toString() == "0")
                    platform.start();
            }else
            {
                platform.start();
            }

            this.platformContext = platform.getCorePlatformContext();

            this.appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);



            /** Download wallet images **/
          /*  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

           try{
                WalletResourcesManager  walletResourceManger = (WalletResourcesManager)platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE);
                walletResourceManger.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);
                walletResourceManger.checkResources();
            }
            catch (CantCheckResourcesException e) {
                e.printStackTrace();}*/

            /**************/

            NavigateActivity();
        }
        catch (CantStartPlatformException | CantReportCriticalStartingProblem e) {
            System.err.println("CantStartPlatformException: " + e.getMessage());
        }




    }



    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        try
        {
            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
            Iterator<Map.Entry<Fragments, Fragment>>  efragments = this.fragments.entrySet().iterator();

            while (efragments.hasNext()) {
                Map.Entry<Fragments, Fragment> fragmentEntry =  efragments.next();

                RuntimeFragment fragment = (RuntimeFragment)fragmentEntry.getValue();
                Fragments type = fragment.getType();

                switch (type) {
                    case CWP_SHELL_LOGIN:
                        break;
                    case CWP_WALLET_MANAGER_MAIN:
                        fragments.add(android.support.v4.app.Fragment.instantiate(this, WalletDesktopFragment.class.getName()));
                        break;
                    case CWP_WALLET_MANAGER_SHOP:
                        fragments.add(android.support.v4.app.Fragment.instantiate(this, ShopDesktopFragment.class.getName()));
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS:
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL:
                        break;
                    case CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED:
                        break;
                    case CWP_WALLET_ADULTS_ALL_REQUEST_SEND:
                        break;
                    case CWP_WALLET_STORE_MAIN:
                        break;
                    case CWP_WALLET_FACTORY_MAIN:
                        break;

                }

            }
            this.PagerAdapter  = new PagerAdapter(getSupportFragmentManager(), fragments);

            ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
            pager.setVisibility(View.VISIBLE);

            pager.setAdapter(this.PagerAdapter);

            pager.setBackgroundResource(R.drawable.background_tiled_diagonal_light);


        }catch (Exception ex) {
            String strError = ex.getMessage();
        }

    }

    /**
     * Init activity navigation
     */
    //if the activity has more of a fragment and has no tabs I create a PagerAdapter
    // to arm the NavigationDrawerFragment verified whether the activity has a SideMenu
    private void NavigateActivity() {

        int walletId = ((MyApplication) this.getApplication()).getWalletId();
        this.app = appRuntimeMiddleware.getLastApp();
        this.subApp = appRuntimeMiddleware.getLastSubApp();
        this.activity = appRuntimeMiddleware.getLasActivity();


        MyApplication.setActivityId(activity.getType().getKey());


        this.tabs = activity.getTabStrip();
        this.fragments =activity.getFragments();
        this.titleBar = activity.getTitleBar();

        this.mainMenumenu= activity.getMainMenu();
        this.sidemenu = activity.getSideMenu();

        if(tabs == null)
            ((PagerSlidingTabStrip) findViewById(R.id.tabs)).setVisibility(View.INVISIBLE);
        else{
            ((PagerSlidingTabStrip) findViewById(R.id.tabs)).setVisibility(View.VISIBLE);
            this.tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        }
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        this.abTitle = (TextView) findViewById(titleId);


        MyApplication.setActivityProperties(this, getWindow(),getResources(),tabStrip,getActionBar(), titleBar,abTitle,Title);

        if(sidemenu != null)
        {
            this.NavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

            this.NavigationDrawerFragment.setMenuVisibility(true);
            // Set up the drawer.
            this.NavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout),sidemenu);
        }


        if(tabs == null && fragments.size() > 1){
            this.initialisePaging();

        }
        else{
            pagertabs = (ViewPager) findViewById(R.id.pager);
            pagertabs.setVisibility(View.VISIBLE);
            adapter = new MyPagerAdapter(getSupportFragmentManager());

            pagertabs.setAdapter(adapter);

            final int pageMargin = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                    .getDisplayMetrics());
            pagertabs.setPageMargin(pageMargin);

            tabStrip.setViewPager(pagertabs);

            String color = activity.getColor();
            if (color != null)
                ((MyApplication) this.getApplication()).changeColor(Color.parseColor(color), getResources());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        switch ( this.activity.getType()) {

            case CWP_SHELL_LOGIN:
                break;
            case CWP_SHOP_MANAGER_MAIN:

                break;
            case CWP_WALLET_MANAGER_MAIN:
                break;
            case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN:

                break;
            case CWP_WALLET_STORE_MAIN:
                break;
            case CWP_WALLET_ADULTS_ALL_MAIN:
                int walletId = ((MyApplication) this.getApplication()).getWalletId();
                if (walletId == 1 || walletId == 2)
                    inflater.inflate(R.menu.wallet_framework_activity_framework_menu2, menu);
                else
                    inflater.inflate(R.menu.wallet_framework_activity_framework_menu, menu);
                break;
            case CWP_WALLET_RUNTIME_STORE_MAIN:
                break;
            case CWP_WALLET_RUNTIME_WALLET_AGE_ADULTS_ALL_BITDUBAI_VERSION_1_MAIN:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_MAIN:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT:
                getMenuInflater().inflate(R.menu.wallet_framework_activity_sent_all_menu, menu);
                return true;
            case CWP_WALLET_ADULTS_ALL_SHOPS:
                inflater.inflate(R.menu.wallet_shop_activity_account_detail_menu, menu);
                break;
            case CWP_WALLET_ADULTS_ALL_REFFILS:
                break;
            case CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED:
                break;
            case CWP_WALLET_ADULTS_ALL_REQUEST_SEND:
                break;
            case CWP_WALLET_FACTORY_MAIN:
                break;
        }


        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_wallet_store) {
            ((MyApplication) this.getApplication()).setWalletId(0);
            this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_STORE_MAIN);
            NavigateActivity();

            return true;
        }

        if (id == R.id.action_file) {

            Intent intent;
            intent = new Intent(this, FileImageActivity.class);
            startActivity(intent);

            return true;
        }

        if (id == R.id.action_requests_sent){
            Intent intent;
            MyApplication.setChildId("1|1");
            activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX);
            intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);
            startActivity(intent);
            return true;
        }


        if (id == R.id.action_requests_received){
            Intent intent;
            MyApplication.setChildId("1|1");
            activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX);
            intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //***
    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    public void onItemSelectedClicked(View v) {

        firstexecute = false;

        MyApplication.setContact("");
        String tagId = v.getTag().toString();
        String activityKey="";
        String paramId="0";

        if(tagId.contains("|")){
            activityKey =  tagId.split("\\|")[0];

            if(tagId.split("\\|").length > 2)
                paramId =  tagId.split("\\|")[1] + "|" + tagId.split("\\|")[2];
            else
                paramId =  tagId.split("\\|")[1];
        }
        else
            activityKey =  tagId;


        Activities activityType = Activities.getValueFromString (activityKey);
        com.bitdubai.fermat_api.layer._12_middleware.app_runtime.Activity activity;
        Intent intent;

        switch (activityType) {

            case CWP_SHOP_MANAGER_MAIN:

                break;
            case CWP_WALLET_MANAGER_MAIN:


                break;
            case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN:
                cleanWindows();
                ((MyApplication) this.getApplication()).setWalletId(Integer.parseInt(paramId));
                activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN);

                NavigateActivity();
                break;
            case CWP_WALLET_STORE_MAIN:
                break;
            case CWP_WALLET_ADULTS_ALL_MAIN:
                cleanWindows();
                if (Integer.parseInt(paramId) > 4)
                {
                    Toast.makeText(getApplicationContext(), "This part of the prototype is not ready yet",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    ((MyApplication) this.getApplication()).setWalletId(Integer.parseInt(paramId));
                    this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_MAIN);
                    NavigateActivity();


                }
                break;
            case CWP_WALLET_RUNTIME_STORE_MAIN:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_MAIN:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE:
                this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE);
                intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);

                startActivity(intent);
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS:
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT:
                MyApplication.setContact(paramId);
                this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT);
                intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);
                startActivity(intent);
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_SEND:
                MyApplication.setChildId(paramId);
                this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_SEND);
                intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);
                startActivity(intent);
                break;
            case CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_RECEIVE:
                MyApplication.setChildId(paramId);
                this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_RECEIVE);
                intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);
                startActivity(intent);


                break;
            case CWP_WALLET_ADULTS_ALL_SHOPS:
                ((MyApplication) this.getApplication()).setWalletId(0);
                cleanWindows();
                activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_SHOPS);
                NavigateActivity();

                break;
            case CWP_WALLET_ADULTS_ALL_CHAT_TRX:
                MyApplication.setChildId(paramId);
                this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX);
                intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);
                startActivity(intent);
                break;
            case CWP_WALLET_ADULTS_ALL_REFFILS:
                break;
            case CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED:
                break;
            case CWP_WALLET_ADULTS_ALL_REQUEST_SEND:
                break;
            case CWP_WALLET_ADULTS_ALL_SEND_HISTORY:
                MyApplication.setTagId(Integer.parseInt(paramId));

                this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_SEND_HISTORY);
                intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);
                startActivity(intent);
                break;
            case CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY:
                MyApplication.setTagId(Integer.parseInt(paramId));

                this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY);
                intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);
                startActivity(intent);
                break;
            case CWP_WALLET_FACTORY_MAIN:
                cleanWindows();
                this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_FACTORY_MAIN);
                NavigateActivity();
                break;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(this.Title);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        // set Desktop current activity
        MyApplication.setActivityId("DesktopActivity");
        ((MyApplication) this.getApplication()).setWalletId(0);
        if (activity.getType() != Activities.CWP_WALLET_MANAGER_MAIN){
            activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
            cleanWindows();

            NavigateActivity();
        }else{
            super.onBackPressed();
        }


    }

    private void cleanWindows()
    {
        //clean page adapter
        pagertabs = (ViewPager) findViewById(R.id.pager);
        this.collection = pagertabs;
        // if(adapter != null) {
        collection.removeAllViews();

        ViewPager viewpager = (ViewPager)super.findViewById(R.id.viewpager);
        viewpager.setVisibility(View.INVISIBLE);
        ViewPager pager = (ViewPager)super.findViewById(R.id.pager);
        pager.setVisibility(View.INVISIBLE);
        if(NavigationDrawerFragment!= null)
            this.NavigationDrawerFragment.setMenuVisibility(false);
        NavigationDrawerFragment = null;
        this.PagerAdapter = null;
        this.abTitle = null;
        this.adapter = null;
        this.pager = null;
        this.pagertabs = null;
        this.Title = "";

        List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
        this.PagerAdapter  = new PagerAdapter(getSupportFragmentManager(), fragments);


    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            List<Tab> titleTabs = tabs.getTabs();
            titles = new String[titleTabs.size()];
            for (int i = 0; i < titleTabs.size(); i++) {
                Tab tab = titleTabs.get(i);
                titles[i] = tab.getLabel();
            }
        }


        public void destroyItem(android.view.ViewGroup container, int position, Object object) {

            FragmentManager manager = ((android.support.v4.app.Fragment) object).getFragmentManager();
            if(manager != null) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.detach((android.support.v4.app.Fragment) object);
                trans.remove((android.support.v4.app.Fragment) object);
                trans.commit();


            }

        }

        @Override
        public CharSequence getPageTitle(int position) {

            return titles[position];
        }

        @Override
        public int getCount() {

            return titles.length;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            com.runtime_wallet.bitdubai.Platform platform = null;
            android.support.v4.app.Fragment currentFragment = null;
            Fragments fragmentType = Fragments.CWP_SHELL_LOGIN;
            List<Tab> titleTabs = tabs.getTabs();
            for (int j = 0; j < titleTabs.size(); j++) {
                if (j == position)
                {
                    Tab tab = titleTabs.get(j);
                    fragmentType = tab.getFragment();
                    break;
                }

            }
//execute current activity fragments

            switch (fragmentType) {
                case CWP_SHELL_LOGIN:

                    break;
                case CWP_WALLET_MANAGER_MAIN:
                    currentFragment =  WalletDesktopFragment.newInstance(position);
                    break;
                case CWP_WALLET_MANAGER_SHOP:
                    currentFragment =  ShopDesktopFragment.newInstance(position);
                    break;
                case CWP_SHOP_MANAGER_MAIN:
                    currentFragment =  AllFragment.newInstance(0);
                    break;
                case CWP_SHOP_MANAGER_FREE:
                    currentFragment =  FreeFragment.newInstance(1);
                    break;

                case CWP_SHOP_MANAGER_PAID:
                    currentFragment =  AllFragment.newInstance(2);
                    break;
                case CWP_SHOP_MANAGER_ACCEPTED_NEARBY:
                    currentFragment =  AllFragment.newInstance(3);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE:
                    currentFragment =  ProfileCardFrontFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP:
                    platform = new com.runtime_wallet.bitdubai.Platform();
                    platform.setWalletResourcesManager((WalletResourcesManager) platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));
                    platform.setErrorManager((ErrorManager)platformContext.getAddon(Addons.ERROR_MANAGER));
                    UsdBalanceFragment.setPlatform(platform);
                    currentFragment =  UsdBalanceFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS:
                    platform = new com.runtime_wallet.bitdubai.Platform();
                    platform.setWalletResourcesManager((WalletResourcesManager) platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));
                    platform.setErrorManager((ErrorManager)platformContext.getAddon(Addons.ERROR_MANAGER));
                    ContactsFragment.setPlatform(platform);
                    currentFragment =  ContactsFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY:
                    platform = new com.runtime_wallet.bitdubai.Platform();
                    platform.setWalletResourcesManager((WalletResourcesManager) platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));
                    platform.setErrorManager((ErrorManager)platformContext.getAddon(Addons.ERROR_MANAGER));
                    CommunityFragment.setPlatform(platform);
                    currentFragment =  CommunityFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME:
                    currentFragment =   HomeFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE:
                    currentFragment =  BalanceFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND:
                    currentFragment =  SendFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE:
                    currentFragment =  ReceiveFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS:
                    currentFragment =  ShopFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL:
                    currentFragment =  RefillFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS:
                    currentFragment =   DiscountsFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP:
                    currentFragment =   ShopShopFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS:
                    currentFragment =   ShopProductsFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS:
                    currentFragment =   ShopReviewsFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT:
                    currentFragment = ShopChatFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY:
                    currentFragment =  ShopHistoryFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP:
                    currentFragment =  ShopMapFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS:
                    currentFragment = AccountDetailAllFragment.newInstance(2);

                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS:
                    currentFragment =  AccountDetailCreditsFragment.newInstance(1);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL:
                    currentFragment =   AccountDetailAllFragment.newInstance(0);
                    break;
                case CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED:
                    break;
                case CWP_WALLET_ADULTS_ALL_REQUEST_SEND:
                    break;
                case CWP_WALLET_STORE_MAIN:
                    break;
                case CWP_WALLET_FACTORY_MAIN:
                    break;
            }

            return currentFragment;
        }

    }
}
