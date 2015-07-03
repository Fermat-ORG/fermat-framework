package com.bitdubai.android_core.app;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bitdubai.android_core.app.common.PagerAdapter;
import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;
import com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment;
import com.bitdubai.android_core.app.subapp.shop.version_1.fragment.ShopChatFragment;
import com.bitdubai.android_core.app.subapp.shop.version_1.fragment.ShopHistoryFragment;
import com.bitdubai.android_core.app.subapp.shop.version_1.fragment.ShopMapFragment;
import com.bitdubai.android_core.app.subapp.shop.version_1.fragment.ShopProductsFragment;
import com.bitdubai.android_core.app.subapp.shop.version_1.fragment.ShopReviewsFragment;
import com.bitdubai.android_core.app.subapp.shop.version_1.fragment.ShopShopFragment;
import com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.AccountDetailAllFragment;
import com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.AccountDetailCreditsFragment;
import com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.DiscountsFragment;
import com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.HomeFragment;
import com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.RefillFragment;
import com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ShopFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Activity;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.App;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Fragment;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.MainMenu;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.SideMenu;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Tab;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.TabStrip;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.TitleBar;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Wallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.app_runtime.developer.bitdubai.version_1.structure.RuntimeFragment;
import com.bitdubai.reference_niche_wallet.age.kids.boys.fragments.CommunityFragment;
import com.bitdubai.reference_niche_wallet.age.kids.boys.fragments.ProfileCardFrontFragment;
import com.bitdubai.reference_niche_wallet.age.kids.boys.fragments.UsdBalanceFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.BalanceFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactsFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsFragment;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ReceiveFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.SendFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.TransactionsFragment;
import com.bitdubai.sub_app.shop_manager.fragment.ShopDesktopFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.WalletDesktopFragment;
import com.bitdubai.sub_app.wallet_store.fragment.AllFragment;
import com.bitdubai.sub_app.wallet_store.fragment.FreeFragment;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import com.bitdubai.fermat.R;

/**
 * Created by natalia on 22/06/15.
 */
public class WalletActivity extends FragmentActivity implements com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment.NavigationDrawerCallbacks{

    private NavigationDrawerFragment NavigationDrawerFragment;

    private com.bitdubai.android_core.app.common.PagerAdapter PagerAdapter;
    public CharSequence Title;
    private Menu menu;
    private PagerSlidingTabStrip tabStrip;
    private App app;
    private SubApp subApp;
    private Activity activity;
    private Map<Fragments, Fragment> fragments;
    private AppRuntimeManager appRuntimeMiddleware;
    private WalletRuntimeManager walletRuntimeMiddleware;
    private ErrorManager errorManager;


    private CorePlatformContext platformContext;


    private ViewPager pager;
    private ViewPager pagertabs;
    private MyPagerAdapter adapter;
    private TextView abTitle;

    private int currentColor = 0xFF666666;
    private MainMenu mainMenumenu;
    private SideMenu sidemenu;
    private String walletStyle = "";
    private TabStrip tabs;
    private TitleBar titleBar; // Comment

    private ViewGroup collection;
    private Platform platform;
    private SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {


            //init runtime app

            //get platform object
            platform = ApplicationSession.getFermatPlatform();
            this.platformContext = platform.getCorePlatformContext();



            Context context = this.getApplicationContext();

            // get instances of Runtime middleware object
            this.appRuntimeMiddleware = ApplicationSession.getAppRuntime();
            this.walletRuntimeMiddleware = ApplicationSession.getwalletRuntime();
            this.errorManager = (ErrorManager) platformContext.getAddon(Addons.ERROR_MANAGER);

            //load wallet UI
            NavigateWallet();

        } catch (Exception e) {
            System.err.println("CantStartPlatformException: " + e.getMessage());

            Toast.makeText(getApplicationContext(), "Error Load RuntimeApp - " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


    }

    private void NavigateWallet() {

        try
        {
            //get actual activity to execute
            this.activity = walletRuntimeMiddleware.getLasActivity();


            ApplicationSession.setActivityId(activity.getType().getKey());


            this.tabs = activity.getTabStrip();
            this.fragments =activity.getFragments();
            this.titleBar = activity.getTitleBar();

            this.mainMenumenu= activity.getMainMenu();

            this.sidemenu = activity.getSideMenu();

            //if wallet do not set the navigator drawer I load a layout without him
            if(sidemenu != null)
            {
                setContentView(R.layout.runtime_app_wallet_runtime_navigator);
                this.NavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

                this.NavigationDrawerFragment.setMenuVisibility(true);
                // Set up the drawer.
                this.NavigationDrawerFragment.setUp(
                        R.id.navigation_drawer,
                        (DrawerLayout) findViewById(R.id.drawer_layout), sidemenu);

            }
            else
            {
                setContentView(R.layout.runtime_app_wallet_runtime);
            }


            if(tabs == null)
                ((PagerSlidingTabStrip) findViewById(R.id.tabs)).setVisibility(View.INVISIBLE);
            else{
                //acá puedo cambiar todo esto, mati
                PagerSlidingTabStrip pagerSlidingTabStrip=((PagerSlidingTabStrip) findViewById(R.id.tabs));
                pagerSlidingTabStrip.setVisibility(View.VISIBLE);
                //pagerSlidingTabStrip.setTextColor(Color.GREEN);
                //pagerSlidingTabStrip.setBackgroundColor(Color.GREEN);
                this.tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

            }
            int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
            this.abTitle = (TextView) findViewById(titleId);


            ApplicationSession.setActivityProperties(this, getWindow(), getResources(), tabStrip, getActionBar(), titleBar, abTitle, Title);




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
                    ((ApplicationSession) this.getApplication()).changeColor(Color.parseColor(color), getResources());

            }
        }
        catch (Exception e) {
            this.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Error in NavigateWallet " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        try
        {
            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
            Iterator<Map.Entry<Fragments, Fragment>> efragments = this.fragments.entrySet().iterator();
            boolean flag=false;
            while (efragments.hasNext()) {
                Map.Entry<Fragments, com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Fragment> fragmentEntry =  efragments.next();

                RuntimeFragment fragment = (RuntimeFragment)fragmentEntry.getValue();
                Fragments type = fragment.getType();


                switch (type) {
                    case CWP_SHELL_LOGIN:
                        break;
                   case CWP_WALLET_MANAGER_SHOP:
                     //   fragments.add(android.support.v4.app.Fragment.instantiate(this, ShopDesktopFragment.class.getName()));
                        break;
                   // case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE :
                     //   fragments.add(android.support.v4.app.Fragment.instantiate(this, ReceiveFragment.class.getName()));
                     //   break;
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

            //set default page to show
            pager.setCurrentItem(0);


        }
        catch (Exception e)
        {
            this.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN,e);

            Toast.makeText(getApplicationContext(), "Can't Create Tabs: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Init activity navigation
     */
    //if the activity has more of a fragment and has no tabs I create a PagerAdapter
    // to make the NavigationDrawerFragment verified whether the activity has a SideMenu




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            this.menu=menu;
            MenuInflater inflater = getMenuInflater();


            switch ( this.activity.getType()) {

                case CWP_SHELL_LOGIN:
                    break;
                case CWP_SHOP_MANAGER_MAIN:
                    MenuItem searchItem = menu.findItem(com.wallet_store.bitdubai.R.id.action_search);
                    searchItem.setVisible(true);
                    MenuItemCompat.setShowAsAction(searchItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
                    //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

                    //mSearchView.setIconifiedByDefault(false);
                    break;
                case CWP_WALLET_MANAGER_MAIN:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN:

                    break;
                case CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN:
                    inflater.inflate(R.menu.app_activity_main_empty_menu, menu);

                    break;
                case CWP_WALLET_STORE_MAIN:
                    break;
                case CWP_WALLET_ADULTS_ALL_MAIN:
                    int walletId = ((ApplicationSession) this.getApplication()).getWalletId();
                    if (walletId == 1 || walletId == 2)
                        inflater.inflate(R.menu.wallet_framework_activity_framework_menu2, menu);
                    else
                        inflater.inflate(R.menu.wallet_framework_activity_framework_menu, menu);
                    break;
                case CWP_WALLET_RUNTIME_STORE_MAIN:
                /*inflater.inflate(R.menu.wallet_store_activity_wallet_menu, menu);
                MenuItem searchItem1 = menu.findItem(com.wallet_store.bitdubai.R.id.action_search);
                MenuItemCompat.setShowAsAction(searchItem1, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);

                mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem1);
                mSearchView.setIconifiedByDefault(false);
                */
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

        }
        catch (Exception e) {

            this.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN,e);

            Toast.makeText(getApplicationContext(), "Error in CleanWindows " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }



        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        try {


            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }


            if (id == R.id.action_search) {

                    /*((ApplicationSession) this.getApplication()).setWalletId(0);
                    this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_STORE_MAIN);
                    NavigateActivity();

                    */

                //Toast.makeText(this,"holas_runtime",Toast.LENGTH_SHORT).show();
                return true;
            }
            if (id == R.id.action_file) {
                return true;
            }

            if (id == R.id.action_requests_sent){
                Intent intent;
                ApplicationSession.setChildId("1|1");
                activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX);
                intent = new Intent(this, com.bitdubai.android_core.app.FragmentActivity.class);
                startActivity(intent);
                return true;
            }


            if (id == R.id.action_requests_received){
                Intent intent;
                ApplicationSession.setChildId("1|1");
                activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX);
                intent = new Intent(this, com.bitdubai.android_core.app.FragmentActivity.class);
                startActivity(intent);
                return true;
            }


        }
        catch (Exception e) {
            this.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Error in OptionsItemSelecte " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    //***
    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    public void onItemSelectedClicked(View v) {

        try
        {
              ApplicationSession.setContact("");
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
            Activity activity;
            Intent intent;



        }
        catch (Exception e) {
            this.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);


            Toast.makeText(getApplicationContext(), "Error in OptionsItemSelecte " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


    }


    //cambio mati test
    @Override
    public void onResume(){
        super.onResume();
        //cleanWindows();
        //NavigateWallet();
    }

    int mRequestCode;
    int mResultCode;
    Intent mData;
    boolean returningWithResult = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        returningWithResult = true;
        this.mData = data;
        mRequestCode = requestCode;
        mResultCode = resultCode;

        //it returns to fragment call
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onPostResume() {


        super.onPostResume();
        if (returningWithResult)
        {
             //Get actual wallet to execute activity result method
            Activity wallet = this.walletRuntimeMiddleware.getLasActivity();

            if (wallet.getType() == Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN)
            {
                android.support.v4.app.Fragment currentFragment =  SendFragment.newInstance(0);
                currentFragment.onActivityResult(mRequestCode, mResultCode, mData);
            }

        }


        returningWithResult = false;
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
        ApplicationSession.setActivityId("DesktopActivity");
        ((ApplicationSession) this.getApplication()).setWalletId(0);
        if (activity.getType() != Activities.CWP_WALLET_MANAGER_MAIN){
            activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
            cleanWindows();

            Intent intent = new Intent(this, SubAppActivity.class);

            startActivity(intent);
        }else{
            super.onBackPressed();
        }


    }

    private void cleanWindows()
    {
        try
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
            {
                this.NavigationDrawerFragment.setMenuVisibility(false);
                NavigationDrawerFragment = null;
            }

            this.PagerAdapter = null;
            this.abTitle = null;
            this.adapter = null;
            this.pager = null;
            this.pagertabs = null;
            this.Title = "";

            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();

            this.PagerAdapter  = new PagerAdapter(getSupportFragmentManager(), fragments);


        }
        catch (Exception e) {

            this.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN,e);

            Toast.makeText(getApplicationContext(), "Error in CleanWindows " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            if(tabs != null){
                List<Tab> titleTabs = tabs.getTabs();
                titles = new String[titleTabs.size()];
                for (int i = 0; i < titleTabs.size(); i++) {
                    Tab tab = titleTabs.get(i);
                    titles[i] = tab.getLabel();
                }
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

            if (titles != null)
                return titles.length;
            else
                return 0;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            com.bitdubai.reference_niche_wallet.age.kids.boys.Platform kidsplatform = null;
            com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform bitcoinPlatform = null;
            com.bitdubai.sub_app.developer.fragment.Platform developerPlatform = null;

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
            try {
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

                    case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS:
                        developerPlatform = new com.bitdubai.sub_app.developer.fragment.Platform();
                        developerPlatform.setErrorManager((ErrorManager) platformContext.getAddon(Addons.ERROR_MANAGER));
                        developerPlatform.setToolManager((ToolManager) platformContext.getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER));

                        currentFragment = DatabaseToolsFragment.newInstance(position);
                        break;

                    case CWP_SUB_APP_DEVELOPER_LOG_TOOLS:
                        developerPlatform = new com.bitdubai.sub_app.developer.fragment.Platform();
                         developerPlatform.setErrorManager((ErrorManager) platformContext.getAddon(Addons.ERROR_MANAGER));
                        developerPlatform.setToolManager((ToolManager) platformContext.getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER));
                        currentFragment = LogToolsFragment.newInstance(0);
                        break;


                    /**
                     * Executing fragments for BITCOIN WALLET.
                     */

                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) platformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));

                        currentFragment =  BalanceFragment.newInstance(0);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) platformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));

                        currentFragment = ReceiveFragment.newInstance(0);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) platformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));

                        currentFragment =  SendFragment.newInstance(0);
                        break;

                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) platformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));

                        currentFragment =  TransactionsFragment.newInstance(0);

                        break;
                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) platformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));

                        currentFragment =  ContactsFragment.newInstance(0);
                        break;


                    case CWP_SHOP_MANAGER_ACCEPTED_NEARBY:
                        currentFragment =  AllFragment.newInstance(3);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE:
                        kidsplatform = new com.bitdubai.reference_niche_wallet.age.kids.boys.Platform();
                        kidsplatform.setWalletResourcesManager((WalletResourcesManager) platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));
                        kidsplatform.setErrorManager((ErrorManager)platformContext.getAddon(Addons.ERROR_MANAGER));
                        ProfileCardFrontFragment.setPlatform(kidsplatform);
                        currentFragment =  ProfileCardFrontFragment.newInstance(position);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP:
                        kidsplatform = new com.bitdubai.reference_niche_wallet.age.kids.boys.Platform();
                        kidsplatform.setWalletResourcesManager((WalletResourcesManager) platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));
                        kidsplatform.setErrorManager((ErrorManager)platformContext.getAddon(Addons.ERROR_MANAGER));
                        UsdBalanceFragment.setPlatform(kidsplatform);
                        currentFragment =  UsdBalanceFragment.newInstance(position);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS:
                        kidsplatform = new com.bitdubai.reference_niche_wallet.age.kids.boys.Platform();
                        kidsplatform.setWalletResourcesManager((WalletResourcesManager) platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));
                        kidsplatform.setErrorManager((ErrorManager)platformContext.getAddon(Addons.ERROR_MANAGER));
                        com.bitdubai.reference_niche_wallet.age.kids.boys.fragments.ContactsFragment.setPlatform(kidsplatform);
                        currentFragment =  com.bitdubai.reference_niche_wallet.age.kids.boys.fragments.ContactsFragment.newInstance(position);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY:
                        kidsplatform = new com.bitdubai.reference_niche_wallet.age.kids.boys.Platform();
                        kidsplatform.setWalletResourcesManager((WalletResourcesManager) platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));
                        kidsplatform.setErrorManager((ErrorManager)platformContext.getAddon(Addons.ERROR_MANAGER));
                        CommunityFragment.setPlatform(kidsplatform);
                        currentFragment =  CommunityFragment.newInstance(position);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME:
                        currentFragment =   HomeFragment.newInstance(position);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE:
                        currentFragment =  com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.BalanceFragment.newInstance(position);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND:
                        currentFragment =  com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.SendFragment.newInstance(position);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE:
                        currentFragment =  com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ReceiveFragment.newInstance(position);
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

            }
            catch(Exception ex)
            {
                errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, ex);

                Toast.makeText(getApplicationContext(), "Error in PagerAdapter GetItem " + ex.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

            return currentFragment;
        }

    }
}
