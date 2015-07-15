package com.bitdubai.android_core.app;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;

import com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment;

import com.bitdubai.android_core.app.common.PagerAdapter;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;

import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ReceiveFragment;

import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.*;
import com.bitdubai.sub_app.manager.fragment.SubAppDesktopFragment;


import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.WalletRuntimeManager;

import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.MainFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.WalletDesktopFragment;

import com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.structure.RuntimeFragment;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;

import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.fermat.R;


import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.sub_app.wallet_store.fragment.AcceptedNearbyFragment;
import com.bitdubai.sub_app.wallet_store.fragment.AllFragment;
import com.bitdubai.sub_app.wallet_store.fragment.FreeFragment;
import com.bitdubai.sub_app.wallet_store.fragment.PaidFragment;


import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Vector;


/**
 * Created by toshiba on 16/02/2015.
 */
public class SubAppActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment NavigationDrawerFragment;

    private PagerAdapter PagerAdapter;
    public CharSequence Title; // NATALIA TODO:porque esto es publico? LUIS lo usa la funcion Restore Action bar
    private Menu menu;
    private PagerSlidingTabStrip tabStrip;
    private App app;
    private SubApp subApp;
    private Activity activity;
    private Map<Fragments, com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Fragment> fragments;
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
    private TabStrip tabs;
    private TitleBar titleBar; // Comment
    private boolean firstexecute = true;
    private Bundle savedInstanceState;
    private ViewGroup collection;
    private Platform platform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {


            this.savedInstanceState = savedInstanceState;
            //init runtime app

            platform = ApplicationSession.getFermatPlatform();

            //get platform object
            this.platformContext = platform.getCorePlatformContext();

            //get instances of Runtime Middleware object
            this.appRuntimeMiddleware = (AppRuntimeManager) platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
            this.walletRuntimeMiddleware = (WalletRuntimeManager) platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);

            this.errorManager = (ErrorManager) platformContext.getAddon(Addons.ERROR_MANAGER);
            ApplicationSession.setErrorManager(errorManager);

            NavigateActivity();
        } catch (Exception e) {
            // TODO: el errorManager no estaria instanciado aca....
            //this.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Error Load RuntimeApp - " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        try {
            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
            Iterator<Map.Entry<Fragments, com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Fragment>> efragments = this.fragments.entrySet().iterator();
            boolean flag = false;
            while (efragments.hasNext()) {
                Map.Entry<Fragments, com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Fragment> fragmentEntry = efragments.next();

                RuntimeFragment fragment = (RuntimeFragment) fragmentEntry.getValue();
                Fragments type = fragment.getType();

                switch (type) {
                    case CWP_SHELL_LOGIN:
                        break;
                    case CWP_WALLET_MANAGER_MAIN:

                        //Matias this flag is because this fragment appair two times and when press the back button in a fragment
                        //the application crash
                      //  if (!flag) {
                            fragments.add(android.support.v4.app.Fragment.instantiate(this, WalletDesktopFragment.class.getName()));
                           // flag = true;
                       // }
                        break;
                    case CWP_WALLET_MANAGER_SHOP:
                         break;
                    case CWP_SUB_APP_DEVELOPER:
                        fragments.add(android.support.v4.app.Fragment.instantiate(this, com.bitdubai.sub_app.manager.fragment.SubAppDesktopFragment.class.getName()));
                        break;

                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                        fragments.add(android.support.v4.app.Fragment.instantiate(this, ReceiveFragment.class.getName()));
                        break;

                }

            }
            this.PagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);

            ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
            pager.setVisibility(View.VISIBLE);

            //set default page to show
            pager.setCurrentItem(0);

            pager.setAdapter(this.PagerAdapter);

            pager.setBackgroundResource(R.drawable.background_tiled_diagonal_light);


        } catch (Exception ex) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, ex);

            Toast.makeText(getApplicationContext(), "Can't Load tabs: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Init activity navigation
     */
    //if the activity has more of a fragment and has no tabs I create a PagerAdapter
    // to make the NavigationDrawerFragment verified whether the activity has a SideMenu
    private void NavigateActivity() {
        try {
            this.app = appRuntimeMiddleware.getLastApp();
            this.subApp = appRuntimeMiddleware.getLastSubApp();
            this.activity = appRuntimeMiddleware.getLasActivity();

            ApplicationSession.setActivityId(activity.getType().getKey());

            this.tabs = activity.getTabStrip();
            this.fragments = activity.getFragments();
            this.titleBar = activity.getTitleBar();

            this.mainMenumenu = activity.getMainMenu();
            this.sidemenu = activity.getSideMenu();


            //if wallet do not set the navigator drawer I load a layout without him
            if (sidemenu != null) {

                setContentView(R.layout.runtime_app_activity_runtime_navigator);


                this.NavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

                this.NavigationDrawerFragment.setMenuVisibility(true);
                // Set up the drawer.
                this.NavigationDrawerFragment.setUp(
                        R.id.navigation_drawer,
                        (DrawerLayout) findViewById(R.id.drawer_layout), sidemenu);
            } else {
                setContentView(R.layout.runtime_app_activity_runtime);

            }

            if (tabs == null)
                (findViewById(R.id.tabs)).setVisibility(View.INVISIBLE);
            else {
                (findViewById(R.id.tabs)).setVisibility(View.VISIBLE);
                this.tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

            }
            int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
            this.abTitle = (TextView) findViewById(titleId);


            String status_color = activity.getStatusBarColor();
            if (activity.getStatusBarColor() != null) {
                setStatusBarColor(this.activity.getStatusBarColor());

            }

            if (activity.getTabStrip() != null)
            {
                if (activity.getTabStrip().getTabsColor()!=null){
                    tabStrip.setBackgroundColor(Color.parseColor(this.activity.getTabStrip().getTabsColor()));
                    //tabStrip.setDividerColor(Color.TRANSPARENT);

                }
                if(activity.getTabStrip().getTabsTextColor()!=null){
                    tabStrip.setTextColor(Color.parseColor(this.activity.getTabStrip().getTabsTextColor()));
                }

                if(this.activity.getTabStrip().getTabsIndicateColor()!=null){
                    tabStrip.setIndicatorColor(Color.parseColor(this.activity.getTabStrip().getTabsIndicateColor()));
                }
            }

            Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/CaviarDreams.ttf");
            if (tabStrip != null){
                tabStrip.setTypeface(tf,1 );
            }

            ApplicationSession.setActivityProperties(this, getWindow(), getResources(), tabStrip, getActionBar(), titleBar, abTitle, Title);




            if (tabs == null && fragments.size() > 1) {
                this.initialisePaging();

            } else {
                pagertabs = (ViewPager) findViewById(R.id.pager);
                pagertabs.setVisibility(View.VISIBLE);
                adapter = new MyPagerAdapter(getSupportFragmentManager());

                pagertabs.setAdapter(adapter);

                final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                        .getDisplayMetrics());
                pagertabs.setPageMargin(pageMargin);

                tabStrip.setViewPager(pagertabs);



                String color = activity.getColor();
                if (color != null)
                    ((ApplicationSession) this.getApplication()).changeColor(Color.parseColor(color), getResources());

            }
        } catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Error in NavigateActivity " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



    // TODO: Aca dependiendo del tipo de activity se esta inflando uno u otro menu. Esto esta lejos de ser como debe. Se supone que en el plugin WalletRuntime o AppRuntime están definidos los menues para cada actividad y es en base a eso que se debe crear los menues.
    // TODO: Si no es posible, como aparentemente no lo es inflar un menu de manera programatica sin basarse en un layout, lo que deberiamos tener es en todo caso un conjunto de layouts que sirvan de templates
    // TODO: Por ejemplo, un layout con un item, otro con dos items, otro con tres y asi, si el problema es definir dinamicamente la cantidad.
    // TODO: Dicho template debiera tener ya incorporado los iconos (con un PNG invisible) que luego se pueda reemplazar desde el codigo.
    // TODO: En definitiva, tenemos que llegar al punto de que la parametrización este en el plugin WalletRuntime y APPRuntime y solo ahi.


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            this.menu = menu;
            //MenuInflater inflater = getMenuInflater();


            switch (this.activity.getType()) {

                case CWP_SHELL_LOGIN:
                    break;
                case CWP_SHOP_MANAGER_MAIN:
                         break;
                case CWP_WALLET_MANAGER_MAIN:
                    break;

                case CWP_WALLET_STORE_MAIN:
                    break;


            }

        } catch (Exception e) {

            // TODO:  Error manager null
//            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
            e.printStackTrace();

            Toast.makeText(getApplicationContext(), "Can't CreateoptionMenu: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

            if (id == R.id.action_wallet_store) {
                ((ApplicationSession) this.getApplication()).setWalletId(0);
                this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_STORE_MAIN);
                NavigateActivity();

                return true;
            }


            if (id == R.id.action_file) {
                return true;
            }





        } catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
            //Matias
            //por ahora lo saco porque no me toma el tag del parametro, vamos a ver como implementarlo
            //Toast.makeText(getApplicationContext(), "Error in OptionsItemSelecte " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(String color){
        if(Build.VERSION.SDK_INT>20) {
            try {

                Window window = this.getWindow();

                // clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                // finally change the color
                // window.setStatusBarColor(this.getResources().getColor(com.bitdubai.sub_app.developer.R.color.wallet_factory_orange));
                Color color_status = new Color();
                window.setStatusBarColor(color_status.parseColor(color));
            } catch (Exception e) {
                Log.d("DatabaseToolsFragment", "Versión del sdk no compatible con el cambio de color del status bar");
            }
        }
    }




    // TODO: Aparentemente este es el manejo que se hace de lo que vendria a ser el Wallet Manager, ya que distribuye la navegacion de acuerdo al item que se clickea.
    // TODO: Definitivamente esto no debiera estar aca siendo que es parte de una SUB APP llamada Wallet Manager.
    // TODO: Hay que decifrar esto y diseñar la manera de resolverlo.

    public void onItemSelectedClicked(View v) {

        try {

            ApplicationSession.setContact("");
            String tagId = v.getTag().toString();
            String activityKey = "";
            String paramId = "0";

            if (tagId.contains("|")) {
                activityKey = tagId.split("\\|")[0];

                if (tagId.split("\\|").length > 2)
                    paramId = tagId.split("\\|")[1] + "|" + tagId.split("\\|")[2];
                else
                    paramId = tagId.split("\\|")[1];
            } else
                activityKey = tagId;


            Activities activityType = Activities.getValueFromString(activityKey);
            Intent intent;

            cleanWindows();
            switch (activityType) {

                case CWP_SUP_APP_ALL_DEVELOPER: //Developer manager
                    ((ApplicationSession) this.getApplication()).setWalletId(0);

                    this.activity = this.walletRuntimeMiddleware.getActivity(Activities.CWP_SUP_APP_ALL_DEVELOPER);
                    //execute NavigateWallet to go wallet activity
                    intent = new Intent(this, com.bitdubai.android_core.app.WalletActivity.class);
                    startActivity(intent);
                    break;
                case CWP_WALLET_BASIC_ALL_MAIN: //basic Wallet
                    //go to wallet basic definition
                    ApplicationSession.setWalletId(4);
                    this.walletRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
                    intent = new Intent(this, com.bitdubai.android_core.app.WalletActivity.class);
                    startActivity(intent);
                    break;
                //Bitcoin wallet fragments
                case CWP_WALLET_RUNTIME_BITCOIN_ALL_CONTACTS_SEND:
                    ApplicationSession.setChildId(paramId);
                    this.walletRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_BITCOIN_ALL_CONTACTS_SEND);
                    intent = new Intent(this, com.bitdubai.android_core.app.FragmentActivity.class);

                    startActivity(intent);

                    break;
                //wallet factory
                case CWP_WALLET_FACTORY_MAIN:

                    this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_FACTORY_MAIN);

                    intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                    startActivity(intent);

                    break;

                //wallet publisher
                case CWP_WALLET_PUBLISHER_MAIN:

                    this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_PUBLISHER_MAIN);
                    intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                    startActivity(intent);

                    break;

                case CWP_WALLET_RUNTIME_STORE_MAIN:
                    this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_STORE_MAIN);
                    intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                    startActivity(intent);

                    break;
            }

        } catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
            //Esto va a habr que cambiarlo porque no me toma el tag, Matias
            //Toast.makeText(getApplicationContext(), "Error in OptionsItemSelecte " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

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
        if (activity.getType() != Activities.CWP_WALLET_MANAGER_MAIN) {
            activity = this.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
            cleanWindows();

            NavigateActivity();
        } else {
            super.onBackPressed();
        }


    }

    private void cleanWindows() {
        try {
            //clean page adapter
            pagertabs = (ViewPager) findViewById(R.id.pager);
            this.collection = pagertabs;
            // if(adapter != null) {
            collection.removeAllViews();

            ViewPager viewpager = (ViewPager) super.findViewById(R.id.viewpager);
            viewpager.setVisibility(View.INVISIBLE);
            ViewPager pager = (ViewPager) super.findViewById(R.id.pager);
            pager.setVisibility(View.INVISIBLE);
            if (NavigationDrawerFragment != null)
                this.NavigationDrawerFragment.setMenuVisibility(false);
            NavigationDrawerFragment = null;
            this.PagerAdapter = null;
            this.abTitle = null;
            this.adapter = null;
            this.pager = null;
            this.pagertabs = null;
            this.Title = "";

            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();

            this.PagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        } catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Can't Clean Windows: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            if (tabs != null) {
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
            if (manager != null) {
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

            try {
                android.support.v4.app.Fragment currentFragment = null;
                Fragments fragmentType = Fragments.CWP_SHELL_LOGIN;
                List<Tab> titleTabs = tabs.getTabs();
                for (int j = 0; j < titleTabs.size(); j++) {
                    if (j == position) {
                        Tab tab = titleTabs.get(j);
                        fragmentType = tab.getFragment();
                        break;
                    }
                }

                com.bitdubai.sub_app.developer.fragment.Platform developerPlatform;

                //execute current activity fragments
                try {
                    switch (fragmentType) {

                        //wallet store
                        case CWP_SHOP_MANAGER_MAIN:
                            currentFragment = AllFragment.newInstance(position);
                            break;
                        case CWP_SHOP_MANAGER_FREE:
                            currentFragment = FreeFragment.newInstance(position);
                            break;
                        case CWP_SHOP_MANAGER_PAID:
                            currentFragment = PaidFragment.newInstance(position);
                            break;
                        case CWP_SHOP_MANAGER_ACCEPTED_NEARBY:
                            currentFragment = AcceptedNearbyFragment.newInstance(position);
                            break;
                        //**
                        case CWP_WALLET_MANAGER_MAIN:
                            currentFragment = WalletDesktopFragment.newInstance(position);
                            break;

                        case CWP_SUB_APP_DEVELOPER:
                            currentFragment = SubAppDesktopFragment.newInstance(position);
                            break;

                        case CWP_WALLET_FACTORY_MAIN:
                            currentFragment = MainFragment.newInstance(position);
                            break;
                        case CWP_WALLET_PUBLISHER_MAIN:
                            currentFragment = com.bitdubai.sub_app.wallet_publisher.fragment.MainFragment.newInstance(position);
                            break;
                    }

                } catch (Exception ex) {
                    ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, ex);

                    Toast.makeText(getApplicationContext(), "Error in PagerAdapter GetItem " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                return currentFragment;
            } catch (Exception e) {
                ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                Toast.makeText(getApplicationContext(), "Can't getItem PageAdpater: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }
}
