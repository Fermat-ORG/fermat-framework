package com.bitdubai.smartwallet.android.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;

import com.bitdubai.smartwallet.android.app.common.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.smartwallet.android.app.common.version_1.fragment.NavigationDrawerFragment;
import com.bitdubai.smartwallet.android.app.shell.version_1.activity.PagerAdapter;
import com.bitdubai.smartwallet.android.app.subapp.shop.version_1.activity.ShopActivity;
import com.bitdubai.smartwallet.android.app.subapp.shop_manager.version_1.fragment.ShopDesktopFragment;
import com.bitdubai.smartwallet.android.app.subapp.wallet_manager.version_1.fragment.WalletDesktopFragment;
import com.bitdubai.smartwallet.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity.FrameworkActivity;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.AndroidOsAddonRoot;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.wallet_platform_api.CantStartPlatformException;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.*;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.AppRuntime;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Fragment;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Plugins;
import com.bitdubai.wallet_platform_core.Platform;

import com.bitdubai.smartwallet.R;
import com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure.*;


import com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.AppRuntimePluginRoot;

import com.bitdubai.wallet_platform_core.CorePlatformContext;
import com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure.RuntimeSubApp;

import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Vector;
import com.bitdubai.smartwallet.android.app.common.version_1.fragment.NavigationDrawerFragment;

/**
 * Created by toshiba on 16/02/2015.
 */
public class RuntimeAppActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    private NavigationDrawerFragment NavigationDrawerFragment;
    private PagerAdapter PagerAdapter;
    private CharSequence Title;
    private Menu menu;
    private PagerSlidingTabStrip tabs;
    private App app;
    private SubApp subApp;
    private com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activity activity;
    private Map<Fragments, Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            //init runtime app
            Platform platform = MyApplication.getPlatform();
            Context mContext = this.getApplicationContext();

            AndroidOsAddonRoot Os = new AndroidOsAddonRoot();

            Os.setContext(this);
            platform.setOs(Os);
            platform.start();

            CorePlatformContext platformContext = platform.getCorePlatformContext();

            AppRuntime appRuntimeMiddleware =  (AppRuntime)platformContext.getPlugin(Plugins.APP_RUNTIME_MIDDLEWARE);

           this.app = appRuntimeMiddleware.getLastApp();
            this.subApp = appRuntimeMiddleware.getLastSubApp();
            this. activity = appRuntimeMiddleware.getLasActivity();

           // Fragment fragment = appRuntimeMiddleware.getLastFragment();

           /* Map<SubApps, SubApp> subapps = app.getSubApps();
            Iterator<Map.Entry<SubApps, SubApp>>  entries = subapps.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<SubApps, SubApp> thisEntry =  entries.next();
                RuntimeSubApp subApp = (RuntimeSubApp)thisEntry.getValue();
                SubApps type = subApp.getType();
                Map<Activities, com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activity> activities = subApp.getActivities();
                Map<Wallets, Wallet> wallets = subApp.getWallets();
                Iterator<Map.Entry<Activities, com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activity>>  eactivities = activities.entrySet().iterator();

                while (eactivities.hasNext()) {
                    Map.Entry<Activities, com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activity> activityEntry =  eactivities.next();
                    try {
                        RuntimeActivity activity = (RuntimeActivity)activityEntry.getValue();
                        Activities activitytype =  activity.getType();
                        TabStrip tabs = activity.getTabStrip();
                        MainMenu menu = activity.getMainMenu();
                        TitleBar titleBar =activity.getTitleBar();
                        Map<Fragments, Fragment> fragments =activity.getFragments();

                    }
                    catch (Exception e){

                    }

                }

            }*/

            //si la actividad tiene mas de un fragmento y no tiene tabs creo un PagerAdapter
            //para armar el NavigationDrawerFragment verifico si la actividad tiene un SideMenu

            MyApplication.setActivityId(activity.getType().getKey());
            setContentView(R.layout.shell_activity_wallet_desktop);

            TitleBar titleBar = activity.getTitleBar();
            TabStrip tabStrip = activity.getTabStrip();

            if(titleBar !=null){
                this.Title = activity.getTitleBar().getLabel();
                int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
                TextView abTitle = (TextView) findViewById(titleId);
                abTitle.setTextColor(Color.WHITE);
                ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),this.tabs, getActionBar(), getResources(),abTitle, this.Title.toString());

            }

            if(activity.getTitleBar() == null)
                getActionBar().hide();

            SideMenu sidemenu = activity.getSideMenu();
            TabStrip tabs = activity.getTabStrip();
            this.fragments =activity.getFragments();
            if(sidemenu != null)
            {
                this.NavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

                this.NavigationDrawerFragment = (NavigationDrawerFragment)
                        getFragmentManager().findFragmentById(R.id.navigation_drawer);
                // Set up the drawer.
                this.NavigationDrawerFragment.setUp(
                        R.id.navigation_drawer,
                        (DrawerLayout) findViewById(R.id.drawer_layout));
            }


            Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
            ((MyApplication) this.getApplication()).setDefaultTypeface(tf);

            if(tabs == null && fragments.size() > 1){
                this.initialisePaging();
            }



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
                try {
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
                catch (Exception e){

                }

            }





            this.PagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
            //
            ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
            pager.setAdapter(this.PagerAdapter);

            pager.setBackgroundResource(R.drawable.background_tiled_diagonal_light);


        }catch (Exception ex) {
            String strError = ex.getMessage();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
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
        String tagId="";
        tagId = v.getTag().toString();

        String activityId="";
        String walletId="";

        if(tagId.contains("|")){
            activityId =  tagId.split("\\|")[0];
            walletId =  tagId.split("\\|")[1];
        }
        else
            activityId =  tagId;

        Activities activityType = Activities.CWP_WALLET_MANAGER_MAIN;

       // Activities mode = Enum.valueOf(Activities.class, activityId);

        Intent intent = new Intent();
        switch (activityType) {

            case CWP_SHOP_MANAGER_MAIN:
                //seteo la actividad actual para tomarla como last activity
                //seteo el fragmento de esa actividad y sus propiedades, menu, tabs, etc
                //ejecuto RuntimeAppActivity de nuevo
                intent = new Intent(this, ShopActivity.class);
                startActivity(intent);
                break;
            case CWP_WALLET_MANAGER_MAIN:
                //verificacion a controlar si viene del WalletDesktopFragment
                  if (Integer.parseInt(walletId) > 4)
                 {
                   Toast.makeText(getApplicationContext(), "This part of the prototype is not ready yet",
                 Toast.LENGTH_LONG).show();
                }
                else
                 {
                ((MyApplication) this.getApplication()).setWalletId(Integer.parseInt(walletId));

                intent = new Intent(this, FrameworkActivity.class);
                startActivity(intent);
                }
                break;
            case CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_MAIN:
                break;
            case CWP_WALLET_STORE_MAIN:
                break;
            case CWP_WALLET_ADULTS_ALL_MAIN:
                break;
            case CWP_WALLET_RUNTIME_STORE_MAIN:
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
            case CWP_WALLET_ADULTS_ALL_SHOPS:
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
}
