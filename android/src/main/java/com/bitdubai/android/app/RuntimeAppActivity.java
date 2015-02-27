package com.bitdubai.android.app;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.bitdubai.android.app.common.version_1.classes.MyApplication;

import com.bitdubai.android.app.common.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.android.app.common.version_1.fragment.NavigationDrawerFragment;
import com.bitdubai.android.app.shell.version_1.activity.PagerAdapter;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopChatFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopHistoryFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopMapFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopProductsFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopReviewsFragment;
import com.bitdubai.android.app.subapp.shop.version_1.fragment.ShopShopFragment;
import com.bitdubai.android.app.subapp.shop_manager.version_1.fragment.ShopDesktopFragment;
import com.bitdubai.android.app.subapp.wallet_manager.version_1.fragment.WalletDesktopFragment;

import com.bitdubai.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyLayoutInflaterFactory;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment.ProfileCardFrontFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.BalanceFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.DiscountsFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.HomeFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ReceiveFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.RefillFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.SendFragment;
import com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment.ShopFragment;
import com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.AndroidOsAddonRoot;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;

import com.bitdubai.fermat_api.CantStartPlatformException;

import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.*;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.AppRuntimeManager;

import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.Fragment;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_core.Platform;

import com.bitdubai.smartwallet.R;
import com.bitdubai.fermat_core.layer._12_middleware.app_runtime.developer.bitdubai.version_1.structure.*;

import com.bitdubai.fermat_core.CorePlatformContext;

import java.io.IOException;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            setContentView(R.layout.runtime_app_activity_runtime);
            this.savedInstanceState = savedInstanceState;
            //init runtime app

            Platform platform = MyApplication.getPlatform();
            Context mContext = this.getApplicationContext();

            this.Os = new AndroidOsAddonRoot();

            this.Os.setContext(this);
            platform.setOs(Os);
            platform.start();

            this.platformContext = platform.getCorePlatformContext();

            this.appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.APP_RUNTIME_MIDDLEWARE);
            NavigateActivity();


        }
        catch (CantStartPlatformException e) {
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
                    case CWP_SHOP_MANAGER_MAIN:
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
    //si la actividad tiene mas de un fragmento y no tiene tabs creo un PagerAdapter
    //para armar el NavigationDrawerFragment verifico si la actividad tiene un SideMenu
    private void NavigateActivity() {

        int walletId = ((MyApplication) this.getApplication()).getWalletId();
        this.app = appRuntimeMiddleware.getLastApp();
        this.subApp = appRuntimeMiddleware.getLastSubApp();
        this.activity = appRuntimeMiddleware.getLasActivity();


        // Fragment fragment = appRuntimeMiddleware.getLastFragment();
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

      /*  if(titleBar !=null){
            this.Title = activity.getTitleBar().getLabel();
            int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
            this.abTitle = (TextView) findViewById(titleId);
            abTitle.setTextColor(Color.WHITE);
            abTitle.setTypeface(MyApplication.getDefaultTypeface());
            ActionBar actionBar = getActionBar();


            actionBar.setTitle(this.Title );
            getActionBar().show();
            if (tabStrip != null)
                    ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),tabStrip, getActionBar(), getResources(),this.abTitle, this.Title.toString());

            if (walletId == 2 || walletId == 1){
                getActionBar().setDisplayHomeAsUpEnabled(false);
                DrawerLayout draw = (DrawerLayout) findViewById(R.id.drawer_layout);
                draw.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }else
            {
                actionBar.setDisplayShowTitleEnabled(true);
            }
        }
        else
        {
            getActionBar().hide();
        }

          Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        ((MyApplication) this.getApplication()).setDefaultTypeface(tf);

        */

        if(sidemenu != null)
        {
            this.NavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

            this.NavigationDrawerFragment.setMenuVisibility(true);
            // Set up the drawer.
            this.NavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
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

        // if(this.mainMenumenu != null){

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



        LayoutInflater inflaterClone = getLayoutInflater().cloneInContext(getLayoutInflater().getContext());
        LayoutInflater.Factory lif = new MyLayoutInflaterFactory();
        inflaterClone.setFactory(lif);

        return super.onCreateOptionsMenu(menu);
        // }else
        // {
        // return true;
        // }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



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
            paramId =  tagId.split("\\|")[1];
        }
        else
            activityKey =  tagId;

        //  Activities activityType = Activities.CWP_WALLET_MANAGER_MAIN;
        Activities activityType = Activities.getValueFromString(activityKey);
        com.bitdubai.fermat_api.layer._12_middleware.app_runtime.Activity activity;
        Intent intent;
        RuntimeActivity runtimeActivity;
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
                    activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_MAIN);
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
                activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT);
                intent = new Intent(this, com.bitdubai.android.app.FragmentActivity.class);
                startActivity(intent);
                break;
            case CWP_WALLET_ADULTS_ALL_SHOPS:
                ((MyApplication) this.getApplication()).setWalletId(0);
                cleanWindows();
                activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_ADULTS_ALL_SHOPS);
                NavigateActivity();

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
      /*  pagertabs = (ViewPager) findViewById(R.id.pager);
        this.collection = pagertabs;
         if(adapter != null) {
                 for (int i = 0; i < fragments.size(); i++) {
                     try {
                         Object objectobject = adapter.instantiateItem(pagertabs,i);
                         if (objectobject != null)
                             adapter.destroyItem(pagertabs, 0, objectobject);
                     } catch (Exception e) {

                     }
                 }
         }*/


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


       /* public void destroyItem(android.view.ViewGroup container, int position, Object object) {

                FragmentManager manager = ((android.support.v4.app.Fragment) object).getFragmentManager();
            if(manager != null){
                FragmentTransaction trans = manager.beginTransaction();
                trans.detach((android.support.v4.app.Fragment) object);
                trans.remove((android.support.v4.app.Fragment) object);
                trans.commit();
                collection.removeViewAt(position);
            }



        }*/

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
                case CWP_SHOP_MANAGER_MAIN:
                    currentFragment =  ShopDesktopFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE:
                    currentFragment =  ProfileCardFrontFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP:
                    currentFragment =  com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment.UsdBalanceFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS:
                    currentFragment =  com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment.ContactsFragment.newInstance(position);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY:
                    currentFragment =  com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment.CommunityFragment.newInstance(position);
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


            return currentFragment;
        }

    }
}
