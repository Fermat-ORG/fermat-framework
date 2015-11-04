package com.bitdubai.android_core.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.FragmentFactory.SubAppFragmentFactory;
import com.bitdubai.android_core.app.common.version_1.FragmentFactory.WalletFragmentFactory;
import com.bitdubai.android_core.app.common.version_1.Sessions.SubAppSessionManager;
import com.bitdubai.android_core.app.common.version_1.Sessions.WalletSessionManager;
import com.bitdubai.android_core.app.common.version_1.adapters.ScreenPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapterWithIcons;
import com.bitdubai.android_core.app.common.version_1.classes.MyTypefaceSpan;
import com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment;
import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.PaintActivtyFeactures;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatHeader;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatNotifications;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubAppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.notification.NotificationType;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopObject;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopRuntimeManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.notifications.FermatNotificationListener;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.SubAppSettingsManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.sub_app.manager.fragment.SubAppDesktopFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.WalletDesktopFragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.lang.System.gc;

/**
 * Created by Matias Furszyfer
 */

public class FermatActivity extends FragmentActivity implements WizardConfiguration, FermatNotifications, PaintActivtyFeactures, Observer,FermatNotificationListener {

    private static final String TAG = "fermat-core";
    public static final String DEVELOP_MODE = "develop_mode";
    private MainMenu mainMenu;

    /**
     * Navigation menu
     */
    protected NavigationDrawerFragment navigationDrawerFragment;

    /**
     * Screen adapters
     */
    private TabsPagerAdapter adapter;
    private TabsPagerAdapterWithIcons adapterWithIcons;
    private ScreenPagerAdapter screenPagerAdapter;

    /**
     * Current view
     */
    private int currentViewId = -1;
    /**
     * WizardTypes
     */
    private Map<WizardTypes, Wizard> wizards;

    /**
     * Activity type
     */
    private ActivityType activityType;

    ArrayList activePlatforms;

    protected boolean developMode;

    private GestureDetectorCompat mDetector;

    private String DEBUG_TAG =  "TESTEANDO";

    private float lastTouchY;

    /**
     * Called when the activity is first created
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            // The Activity is being created for the first time, so create and
            // add new fragments.
            super.onCreate(savedInstanceState);
        } else {

            super.onCreate(new Bundle());
            // Otherwise, the activity is coming back after being destroyed.
            // The FragmentManager will restore the old Fragments so we don't
            // need to create any new ones here.
        }



        FermatGestureDetector fermatGestureDetector = new FermatGestureDetector(this);

        mDetector = new GestureDetectorCompat(this,fermatGestureDetector);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(fermatGestureDetector);

        try {

            activePlatforms = new ArrayList();

            /*
            *  Our Future code goes here ...
            */

        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            makeText(getApplicationContext(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu
     *
     * @param menu
     * @return true if all is okey
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            //mainMenu = getActivityUsedType().getMainMenu();
            if (mainMenu != null) {
                for (com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem menuItem : mainMenu.getMenuItems()) {
                    MenuItem item = menu.add(menuItem.getLabel());

//                item.setOnMenuItemClickListener (new ActionMenuView.OnMenuItemClickListener(){
//                    @Override
//                    public boolean onMenuItemClick (MenuItem item){
//
//                        //makeText(, "Mati",LENGTH_SHORT).show();
//                        return true;
//                    }
//                });
                }
                //getMenuInflater().inflate(R.menu.wallet_store_activity_wallet_menu, menu);

            }


            return true;


        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getApplicationContext(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }

        return super.onCreateOptionsMenu(menu);

    }


    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */

    /**
     * Dispatch onStop() to all fragments.  Ensure all loaders are stopped.
     */
    @Override
    protected void onStop() {
        try {
            super.onStop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item
     * @return true if button is clicked
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {

            int id = item.getItemId();

            /**
             *  Our future code goes here...
             */


        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getApplicationContext(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method that loads the UI
     */
    protected void loadBasicUI(Activity activity) {
        // rendering UI components
        try {
            TabStrip tabs = activity.getTabStrip();
            Map<String, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment> fragments = activity.getFragments();
            TitleBar titleBar = activity.getTitleBar();
            MainMenu mainMenu = activity.getMainMenu();

            SideMenu sideMenu = activity.getSideMenu();

            setMainLayout(sideMenu, activity.getHeader());

            setMainMenu(mainMenu);

            paintTabs(tabs, activity);

            paintStatusBar(activity.getStatusBar());

            paintTitleBar(titleBar, activity);
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getApplicationContext(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        // rendering wizards components
        try {
            TabStrip tabs = activity.getTabStrip();
            if (tabs != null && tabs.getWizards() != null)
                setWizards(tabs.getWizards());
            if (activity.getWizards() != null)
                setWizards(activity.getWizards());
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getApplicationContext(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
    }

    private void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public Activity getActivityUsedType() {
        Activity activity = null;
        if (ActivityType.ACTIVITY_TYPE_SUB_APP == activityType) {
            SubApp subApp = getSubAppRuntimeMiddleware().getLastSubApp();
            activity = subApp.getLastActivity();
        } else if (ActivityType.ACTIVITY_TYPE_WALLET == activityType) {
            //activity = getWalletRuntimeManager().getLasActivity();
        }
        return activity;
    }

    /**
     * @param titleBar
     */
    protected void paintTitleBar(TitleBar titleBar, Activity activity) {
        try {
            ActionBar actionBar = getActionBar();
            TextView abTitle = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", "android"));
            if (titleBar != null) {

                String title = titleBar.getLabel();

                if (abTitle != null) {
                    abTitle.setTextColor(Color.WHITE);
                    abTitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/roboto.ttf"));
                    if (titleBar.getLabelSize() != -1) {
                        abTitle.setTextSize(titleBar.getLabelSize());

                    }

                }

                actionBar.setTitle(title);


                actionBar.show();
                setActionBarProperties(title, activity);
                paintToolbarIcon(titleBar);
            } else {
                actionBar.hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void paintToolbarIcon(TitleBar titleBar) {
        if (titleBar.getIconName() != null) {

            getActionBar().setIcon(R.drawable.world);
        }

    }

    /**
     * @param title
     */
    protected void setActionBarProperties(String title, Activity activity) {
        SpannableString s = new SpannableString(title);


        s.setSpan(new MyTypefaceSpan(getApplicationContext(), "roboto.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        getActionBar().setTitle(s);

        // actionBar
        Drawable bg = getResources().getDrawable(R.drawable.transparent);
        bg.setVisible(false, false);
        Drawable wallpaper = getResources().getDrawable(R.drawable.transparent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(Color.parseColor(activity.getColor()));
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //ld.setCallback(drawableCallback);
                Log.d(getClass().getSimpleName(), "Version incompatible con status bar");
            } else {
                getActionBar().setBackgroundDrawable(ld);
            }
        }
    }

    /**
     * Method used from a Wallet to paint tabs
     */
    protected void setPagerTabs(WalletNavigationStructure wallet, TabStrip tabStrip, WalletSession walletSession) {

        PagerSlidingTabStrip pagerSlidingTabStrip = ((PagerSlidingTabStrip) findViewById(R.id.tabs));
        pagerSlidingTabStrip.setShouldExpand(true);

        ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
        pagertabs.setVisibility(View.VISIBLE);

        if (tabStrip.isHasIcon()) {
            adapterWithIcons = new TabsPagerAdapterWithIcons(getFragmentManager(),
                    getApplicationContext(),
                    WalletFragmentFactory.getFragmentFactoryByWalletType(wallet.getWalletCategory(), wallet.getWalletType(), wallet.getPublicKey()),
                    tabStrip,
                    walletSession,
                    getWalletResourcesProviderManager(),
                    getResources());
            pagertabs.setAdapter(adapterWithIcons);
        } else {
            adapter = new TabsPagerAdapter(getFragmentManager(),
                    getApplicationContext(),
                    WalletFragmentFactory.getFragmentFactoryByWalletType(wallet.getWalletCategory(), wallet.getWalletType(), wallet.getPublicKey()),
                    tabStrip,
                    walletSession,
                    getWalletResourcesProviderManager(),
                    getResources());
            pagertabs.setAdapter(adapter);
        }


        //pagertabs.setCurrentItem();
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pagertabs.setPageMargin(pageMargin);
        pagertabs.setCurrentItem(tabStrip.getStartItem(), true);

        /**
         * Put tabs in pagerSlidingTabsStrp
         */
        pagerSlidingTabStrip.setViewPager(pagertabs);
        pagerSlidingTabStrip.setShouldExpand(true);
        pagertabs.setOffscreenPageLimit(tabStrip.getTabs().size());

    }

    /**
     * Method used from a subApp to paint tabs
     */
    protected void setPagerTabs(SubApp subApp, TabStrip tabStrip, SubAppsSession subAppsSession) throws InvalidParameterException {

        PagerSlidingTabStrip pagerSlidingTabStrip = ((PagerSlidingTabStrip) findViewById(R.id.tabs));

        ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
        pagertabs.setVisibility(View.VISIBLE);


        SubApps subAppType = subApp.getType();

        com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory subAppFragmentFactory = SubAppFragmentFactory.getFragmentFactoryBySubAppType(subAppType);

        adapter = new TabsPagerAdapter(getFragmentManager(),
                getApplicationContext(),
                getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity(),
                subAppsSession,
                getErrorManager(),
                subAppFragmentFactory,
                null,//getSubAppSettingSettingsManager(),
                getSubAppResourcesProviderManager()
        );

        pagertabs.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pagertabs.setPageMargin(pageMargin);
        /**
         * Put tabs in pagerSlidingTabsStrp
         */
        pagerSlidingTabStrip.setViewPager(pagertabs);

        pagertabs.setOffscreenPageLimit(tabStrip.getTabs().size());


    }

    private List<android.support.v4.app.Fragment> getWalletFragments(String walletType) {
        List<android.support.v4.app.Fragment> lstWalletFragment = new ArrayList<android.support.v4.app.Fragment>();
        //tengo que traer el WalletFragmentFactory dependiendo del tipo de wallet que es un enum ejemplo basic_wallet
        //ReferenceWalletFragmentFactory.getFragmentFactoryByWalletType(getWalletRuntimeManager().getActivity(.))
        return null;
    }

    /**
     * Select the xml based on the activity type
     *
     * @param sidemenu
     * @param header
     */
    protected void setMainLayout(SideMenu sidemenu, FermatHeader header) {
        try {
            if (sidemenu != null) {
                if (ActivityType.ACTIVITY_TYPE_SUB_APP == activityType) {
                    setCurrentViewById(R.layout.runtime_app_activity_runtime_navigator);
                } else if (ActivityType.ACTIVITY_TYPE_WALLET == activityType) {
                    setCurrentViewById(R.layout.runtime_app_wallet_runtime_navigator);

                }


                //TODO: tengo que agregar el header en los 4 xml base para que esto no se caiga cuando no lo tiene
                try {
                    ((RelativeLayout) findViewById(R.id.container_header_balance)).setVisibility((header != null) ? View.VISIBLE : View.GONE);
                } catch (Exception e) {

                }



                navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

                /**
                 * Set up the navigationDrawer
                 */

                if(navigationDrawerFragment != null) {
                    navigationDrawerFragment.setUp(
                            R.id.navigation_drawer,
                            (DrawerLayout) findViewById(R.id.drawer_layout), sidemenu);

                    navigationDrawerFragment.setMenuVisibility(true);
                }

                //if (navigationDrawerFragment == null)
                    //navigationDrawerFragment = (navigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

//                navigationDrawerFragment = NavigationDrawerFragment.newInstance(this);
//
//                /**
//                 * Set up the navigationDrawer
//                 */
//                navigationDrawerFragment.setUp(
//                        R.id.navigation_drawer,
//                        (DrawerLayout) findViewById(R.id.drawer_layout), sidemenu);
//
//                navigationDrawerFragment.setMenuVisibility(true);
//
//                FragmentManager     fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.navigation_drawer, navigationDrawerFragment);
//                ft.commit();

//                drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//
//                navigationView = (NavigationView) findViewById(R.id.navigation_view);
//                //navigationView.setNavigationItemSelectedListener(this);
//
//                //navigationView = (NavigationView) findViewById(R.id.navigation_view);
//                navigationHeaderView = (ViewGroup) navigationView.inflateHeaderView(R.layout.header_nav_view);
//                //navigationHeaderView.findViewById(R.id.icon)
//                //        .setOnClickListener(this);
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////                    navigationHeaderView.setPadding(0, DisplayUtil.getStatusBarHeight(this), 0, 0);
////                }
//                    //initOptions();
//
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
//
//
//                List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> list  = new ArrayList<>();
//
//                list.add(new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem("mati","hola",null));
//                list.add(new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem("mati","hola",null));
//                list.add(new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem("mati","hola",null));
//                list.add(new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem("mati","hola",null));
//                list.add(new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem("mati","hola",null));
//
//
//                NavigationDrawerAdapter navigationDrawerAdapter = new NavigationDrawerAdapter(
//                        this,
//                        list);
//
//                recyclerView.setAdapter(navigationDrawerAdapter);
//
//
//        /* setting up drawer layout */
//                //drawerLayout = (DrawerLayout) findViewById(R.id.drawer_activity);
//                 drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
//                        R.string.open, R.string.close) {
//                    @Override
//                    public void onDrawerOpened(View drawerView) {
//                        super.onDrawerOpened(drawerView);
//                        //setTitle(mTitle);
//                        invalidateOptionsMenu();
//                    }
//
//                    @Override
//                    public void onDrawerClosed(View drawerView) {
//                        super.onDrawerClosed(drawerView);
//                        //setTitle(mTitle);
//                        invalidateOptionsMenu();
//                    }
//
//                    @Override
//                    public void onDrawerSlide(View drawerView, float slideOffset) {
//                        InputMethodManager imm =
//                                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        if (getCurrentFocus() != null && imm != null && imm.isActive()) {
//                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                        }
//                        super.onDrawerSlide(drawerView, slideOffset);
//                        float moveFactor = (navigationView.getWidth() * slideOffset);
//                        //findViewById(R.id.content).setTranslationX(moveFactor);
//                    }
//                };
//                drawerLayout.setDrawerListener(drawerToggle);
//                drawerLayout.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        drawerToggle.syncState();
//                    }
//                });


                /**
                 * Paint layout without navigationDrawer
                 */
            }else {
                if (ActivityType.ACTIVITY_TYPE_SUB_APP == activityType) {
                    setCurrentViewById(R.layout.runtime_app_activity_runtime);
                } else if (ActivityType.ACTIVITY_TYPE_WALLET == activityType) {
                    setCurrentViewById(R.layout.runtime_app_wallet_runtime);
                }

            }
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY,UnexpectedUIExceptionSeverity.CRASH,e);
        }
    }

    public void setCurrentViewById(int id){
        if(getCurrentViewById() != id) {
            setContentView(id);
            currentViewId = id;
        }
    }

    public int getCurrentViewById(){
        return currentViewId;
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        getNotificationManager().addObserver(this);
        getNotificationManager().addCallback(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getNotificationManager().deleteObserver(this);
        getNotificationManager().deleteCallback(this);
    }

    /**
     * @param tabs
     * @param activity
     */
    protected void paintTabs(TabStrip tabs, Activity activity) {
        /**
         * Get Pager from xml
         */
        PagerSlidingTabStrip pagerSlidingTabStrip = ((PagerSlidingTabStrip) findViewById(R.id.tabs));

        if (tabs == null)
            pagerSlidingTabStrip.setVisibility(View.INVISIBLE);
        else {
            pagerSlidingTabStrip.setVisibility(View.VISIBLE);
            Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/roboto.ttf");
            pagerSlidingTabStrip.setTypeface(tf, 1);
            pagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);

            // paint tabs color
            if (tabs.getTabsColor() != null) {
                pagerSlidingTabStrip.setBackgroundColor(Color.parseColor(activity.getTabStrip().getTabsColor()));
                //tabStrip.setDividerColor(Color.TRANSPARENT);
            }

            // paint tabs text color
            if (tabs.getTabsTextColor() != null) {
                pagerSlidingTabStrip.setTextColor(Color.parseColor(activity.getTabStrip().getTabsTextColor()));
            }

            //paint tabs indicate color
            if (tabs.getTabsIndicateColor() != null) {
                pagerSlidingTabStrip.setIndicatorColor(Color.parseColor(activity.getTabStrip().getTabsIndicateColor()));
            }
        }

        // put tabs font
        if (pagerSlidingTabStrip != null) {
            pagerSlidingTabStrip.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/roboto.ttf"), 1);
        }
    }

    /**
     * Method to set status bar color in different version of android
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void paintStatusBar(StatusBar statusBar) {


        if (statusBar != null) {
            if (statusBar.getColor() != null) {
                if (Build.VERSION.SDK_INT > 20) {
                    try {

                        Window window = this.getWindow();

                        // clear FLAG_TRANSLUCENT_STATUS flag:
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                        // finally change the color
                        Color color_status = new Color();
                        window.setStatusBarColor(color_status.parseColor(statusBar.getColor()));
                    } catch (Exception e) {
                        getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                        Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                    }
                }
            } else {
                try {

                    Window window = this.getWindow();

                    // clear FLAG_TRANSLUCENT_STATUS flag:
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                    // finally change the color
                    window.setStatusBarColor(Color.TRANSPARENT);

                    gc();
                    InputStream inputStream = getAssets().open("drawables/mdpi.jpg");


                    window.setBackgroundDrawable(Drawable.createFromStream(inputStream, null));
                } catch (Exception e) {
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                    Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                }
            }
        } else {
            if (Build.VERSION.SDK_INT > 20) {
                try {

                    Window window = this.getWindow();

                    // clear FLAG_TRANSLUCENT_STATUS flag:
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                    // finally change the color
                    window.setStatusBarColor(Color.TRANSPARENT);
                    //window.setBackgroundDrawable(Drawable.createFromStream(getAssets().open("drawables/fondo.jpg"), null));
                } catch (Exception e) {
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                    Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                } catch (OutOfMemoryError outOfMemoryError) {
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, new Exception());
                    Toast.makeText(this, "out of memory exception", LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Get the activity type
     *
     * @return ActivityType enum value
     */

    public ActivityType getActivityType() {
        return activityType;
    }

    /**
     * Set the activity type
     *
     * @param activityType Enum value
     */

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    /**
     * Method used to clean the screen
     */

    protected void resetThisActivity() {

        try {

            RelativeLayout header_cotainer = (RelativeLayout) findViewById(R.id.container_header_balance);
            if (header_cotainer != null) {
                header_cotainer.removeAllViews();
                header_cotainer.setVisibility(View.GONE);
            }

            //clean page adapter
            ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
            if (adapter != null) pagertabs.removeAllViews();

            ViewPager viewpager = (ViewPager) super.findViewById(R.id.viewpager);
            viewpager.removeAllViews();
            viewpager.removeAllViewsInLayout();
            viewpager.clearOnPageChangeListeners();
            viewpager.setVisibility(View.GONE);

            viewpager = null;
            ViewPager pager = (ViewPager) super.findViewById(R.id.pager);
            pager.removeAllViews();
            pager.removeAllViewsInLayout();
            pager.clearOnPageChangeListeners();
            pager.setVisibility(View.GONE);
            pager = null;

            com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip pagerTabStrip = (com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip) findViewById(R.id.tabs);

            pagerTabStrip.destroyDrawingCache();
            pagerTabStrip.clearAnimation();
            pagerTabStrip.removeAllViews();
            pagerTabStrip.removeAllViewsInLayout();
            pagerTabStrip.setOnPageChangeListener(null);
            // todo: DEBERIA VER SI LO DESTRUÍ TODO O QUEDÓ ALGO FLOTANDO EN EL PAGERTABSTRIP
            pagerTabStrip.setVisibility(View.GONE);
            pagerTabStrip = null;

            // hide actionBar
            getActionBar().hide();
            getActionBar().setListNavigationCallbacks(null, null);

            if (navigationDrawerFragment != null) {

//                getSupportFragmentManager().beginTransaction().
//                        remove(getSupportFragmentManager().findFragmentById(R.id.only_fragment_container)).commit();
//                navigationDrawerFragment.setMenuVisibility(false);
//                navigationDrawerFragment.onDetach();
                //if()
                //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.only_fragment_container)).commit();
                navigationDrawerFragment.onDetach();
                navigationDrawerFragment = null;
               FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = getFragmentManager().findFragmentById(R.id.navigation_drawer);
                if(fragment!=null)
                fragmentTransaction.remove(fragment).commit();
                fragmentManager.executePendingTransactions();
            }

            this.getNotificationManager().deleteObserver(this);

            this.adapter = null;
            paintStatusBar(null);

            List<android.app.Fragment> fragments = new Vector<android.app.Fragment>();


            this.screenPagerAdapter = new ScreenPagerAdapter(getFragmentManager(), fragments);

            currentViewId = -1;



            System.gc();
            closeContextMenu();
            closeOptionsMenu();

            onRestart();

        } catch (Exception e) {

            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));

            makeText(getApplicationContext(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
    }

    public void resetPager() {
        //clean page adapter
        ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
        if (adapter != null) pagertabs.removeAllViews();

        ViewPager viewpager = (ViewPager) super.findViewById(R.id.viewpager);
        viewpager.setVisibility(View.INVISIBLE);
        ViewPager pager = (ViewPager) super.findViewById(R.id.pager);
        pager.setVisibility(View.INVISIBLE);
        this.adapter = null;
        List<android.app.Fragment> fragments = new Vector<android.app.Fragment>();
        this.screenPagerAdapter = new ScreenPagerAdapter(getFragmentManager(), fragments);
    }

    public void cleanTabs() {
        try {


            PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);


//            //clean page adapter
//            ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
//            if (adapter != null) pagertabs.removeAllViews();
//
//            ViewPager viewpager = (ViewPager) super.findViewById(R.id.viewpager);
//            viewpager.setVisibility(View.INVISIBLE);
//            ViewPager pager = (ViewPager) super.findViewById(R.id.pager);
//            pager.setVisibility(View.INVISIBLE);
//
//            if (navigationDrawerFragment != null) {
//                this.navigationDrawerFragment.setMenuVisibility(false);
//                navigationDrawerFragment = null;
//            }
//
//
//            this.adapter = null;
//            paintStatusBar(null);
//
//            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
//
//
//            this.screenPagerAdapter = new ScreenPagerAdapter(getSupportFragmentManager(), fragments);

        } catch (Exception e) {

            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));

            makeText(getApplicationContext(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
    }

    /**
     * Initialise the fragments to be paged
     */
    protected void initialisePaging() {

        try {

            if(activePlatforms==null) {
                activePlatforms = new ArrayList();
                activePlatforms.add(Platforms.CRYPTO_CURRENCY_PLATFORM);
            }

            if(developMode){
                activePlatforms.add(Platforms.CRYPTO_CURRENCY_PLATFORM);
                activePlatforms.add(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION);
                activePlatforms.add(Platforms.DIGITAL_ASSET_PLATFORM);
                activePlatforms.add(Platforms.CRYPTO_BROKER_PLATFORM);
            }

            List<android.app.Fragment> fragments = new Vector<android.app.Fragment>();

            DesktopRuntimeManager desktopRuntimeManager = getDesktopRuntimeManager();

            for (DesktopObject desktopObject : desktopRuntimeManager.listDesktops()) {
                //TODO: este switch se cambiara por un FragmentFactory en algún momento al igual que el Activity factory
                switch (desktopObject.getType()) {
                    case "DCCP":
                        //por ahora va esto
                        if(activePlatforms.contains(Platforms.CRYPTO_CURRENCY_PLATFORM)) {
                            WalletManager manager = getWalletManager();
                            WalletDesktopFragment walletDesktopFragment = WalletDesktopFragment.newInstance(0, manager);
                            fragments.add(walletDesktopFragment);
                        }
                        break;
                    case "WPD":
                        if(activePlatforms.contains(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION)) {
                            SubAppDesktopFragment subAppDesktopFragment = SubAppDesktopFragment.newInstance(0);
                            fragments.add(subAppDesktopFragment);
                        }
                        break;
                    case "DDAP":
                        if(activePlatforms.contains(Platforms.DIGITAL_ASSET_PLATFORM)) {
                            com.bitdubai.fermat_dap_android_desktop_wallet_manager_bitdubai.fragment.WalletDesktopFragment went1 = com.bitdubai.fermat_dap_android_desktop_wallet_manager_bitdubai.fragment.WalletDesktopFragment.newInstance(0);
                            fragments.add(went1);
                            com.bitdubai.fermat_dap_android_desktop_sub_app_manager_bitdubai.SubAppDesktopFragment dapDesktopFragment = com.bitdubai.fermat_dap_android_desktop_sub_app_manager_bitdubai.SubAppDesktopFragment.newInstance(0);
                            fragments.add(dapDesktopFragment);
                        }
                        break;
                    case "DCBP":
                        if(activePlatforms.contains(Platforms.CRYPTO_BROKER_PLATFORM)) {
                            com.bitdubai.desktop.wallet_manager.fragments.WalletDesktopFragment dapDesktopFragment3 = com.bitdubai.desktop.wallet_manager.fragments.WalletDesktopFragment.newInstance(0);
                            fragments.add(dapDesktopFragment3);
                            com.bitdubai.desktop.sub_app_manager.SubAppDesktopFragment walletDesktopFragment2 = com.bitdubai.desktop.sub_app_manager.SubAppDesktopFragment.newInstance(0);
                            fragments.add(walletDesktopFragment2);
                        }
                        break;

                }
            }

            /**
             * this pagerAdapter is the screenPagerAdapter with no tabs
             */
            screenPagerAdapter = new ScreenPagerAdapter(getFragmentManager(), fragments);

            ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
            pager.setVisibility(View.VISIBLE);

            //set default page to show
            pager.setCurrentItem(0);

            pager.setAdapter(this.screenPagerAdapter);

            if (pager.getBackground() == null) {
                Drawable d = Drawable.createFromStream(getAssets().open("drawables/mdpi.jpg"), null);
                pager.setBackground(d);
            }


        } catch (Exception ex) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(ex));
            makeText(getApplicationContext(), "Oooops! recovering from system error", LENGTH_SHORT).show();
        }
    }

    @Override
    public void paintComboBoxInActionBar(ArrayAdapter adapter, ActionBar.OnNavigationListener listener) {
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        //ArrayAdapter<String> itemsAdapter =
        //      new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        getActionBar().setListNavigationCallbacks(adapter, listener);
        adapter.notifyDataSetChanged();
    }


    /**
     * Get wallet session manager
     *
     * @return
     */

    public WalletSessionManager getWalletSessionManager() {
        return ((ApplicationSession) getApplication()).getWalletSessionManager();
    }

    /**
     * Gwt subApp session manager
     *
     * @return
     */
    public SubAppSessionManager getSubAppSessionManager() {
        return ((ApplicationSession) getApplication()).getSubAppSessionManager();
    }

    /**
     * Get SubAppRuntimeManager from the fermat platform
     *
     * @return reference of SubAppRuntimeManager
     */

    public SubAppRuntimeManager getSubAppRuntimeMiddleware() {
        return (SubAppRuntimeManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
    }

    /**
     * Get WalletRuntimeManager from the fermat platform
     *
     * @return reference of WalletRuntimeManager
     */

    public WalletRuntimeManager getWalletRuntimeManager() {
        return (WalletRuntimeManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);
    }

    /**
     * Get WalletManager from the fermat platform
     *
     * @return reference of WalletManagerManager
     */

    public WalletManager getWalletManager() {
        return (WalletManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE);
    }

    /**
     * Get ErrorManager from the fermat platform
     *
     * @return reference of ErrorManager
     */

    public ErrorManager getErrorManager() {
        return (ErrorManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER);
    }

    /**
     * Get WalletSettingsManager
     */
    public WalletSettingsManager getWalletSettingsManager() {
        return (WalletSettingsManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE);
    }


    /**
     * Get WalletResourcesProvider
     */
    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
        return (WalletResourcesProviderManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE);
    }

    /**
     * Get SubAppResourcesProvider
     */
    public SubAppResourcesProviderManager getSubAppResourcesProviderManager() {
        return (SubAppResourcesProviderManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE);
    }


    /**
     * Get IntraUserModuleManager
     */
    public IntraUserModuleManager getIntraUserModuleManager() {
        return (IntraUserModuleManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_INTRA_USER_FACTORY_MODULE);
    }


    /**
     * Get NotificationManager
     */
    public NotificationManagerMiddleware getNotificationManager() {
        return (NotificationManagerMiddleware) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_MIDDLEWARE_NOTIFICATION);
    }

    /**
     * Get DesktopRuntimeManager
     */
    public DesktopRuntimeManager getDesktopRuntimeManager() {
        return (DesktopRuntimeManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_DESKTOP_RUNTIME);
    }



    /**
     * Assest Issuer Wallet Module
     */
    public AssetIssuerWalletSupAppModuleManager getAssetIssuerWalletModuleManager() {
        return (AssetIssuerWalletSupAppModuleManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE);
    }

   /**
     * Asset User Wallet Module
     */
    public AssetUserWalletSubAppModuleManager getAssetUserWalletModuleManager() {
        return (AssetUserWalletSubAppModuleManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE);
    }

   /**
     * Asset Redeem Point
     */
    public AssetRedeemPointWalletSubAppModule getAssetRedeemPointWalletModuleManager() {
        return (AssetRedeemPointWalletSubAppModule) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE);
    }
    /**
     * CBP
     */
    public CryptoBrokerWalletModuleManager getCryptoBrokerWalletModuleManager() {
        //return (CryptoBrokerWalletModuleManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BRO);
        return null;
    }

    /**
     * Set up wizards to this activity can be more than one.
     *
     * @param wizards
     */
    public void setWizards(Map<WizardTypes, Wizard> wizards) {
        if (wizards != null && wizards.size() > 0) {
            if (this.wizards == null)
                this.wizards = new HashMap<>();
            this.wizards.putAll(wizards);
        }
    }

    /**
     * Launch wizard configuration from key
     *
     * @param key  Name of FermatWizard Enum
     * @param args Object... arguments to passing to the wizard fragment
     */
    @Override
    public void showWizard(WizardTypes key, Object... args) {
        try {
            if (wizards == null)
                throw new NullPointerException("the wizard is null");
            Wizard wizard = wizards.get(key);
            if (wizard != null) {
            /* Starting Wizard Activity */
                WizardActivity.open(this, args, wizard);
            } else {
                Log.e(TAG, "Wizard not found...");
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Cannot instantiate wizard runtime because the wizard called is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wizards = null;

        //navigationDrawerFragment.onDetach();
        resetThisActivity();
    }

    @Override
    public void launchWalletNotification(String walletPublicKey, String notificationTitle, String notificationImageText, String notificationTextBody) {
        //try {
        //getWalletRuntimeManager().getWallet(walletPublicKey).getLastActivity();
        notificateWallet(walletPublicKey, notificationTitle, notificationImageText, notificationTextBody);

        //} catch (WalletRuntimeExceptions walletRuntimeExceptions) {
        //    walletRuntimeExceptions.printStackTrace();
        // }

    }

    public void notificateWallet(String walletPublicKey, String notificationTitle, String notificationImageText, String notificationTextBody) {
        //Log.i(TAG, "Got a new result: " + notification_title);
        Resources r = getResources();
        PendingIntent pi = null;
        if (walletPublicKey != null) {
            Intent intent = new Intent(this, WalletActivity.class);
            intent.putExtra(WalletActivity.WALLET_PUBLIC_KEY, walletPublicKey);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);


            pi = PendingIntent
                    .getActivity(this, 0, intent, 0);

        }
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(notificationTitle)
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(notificationImageText)
                .setContentText(notificationTextBody)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);

    }


    public void notificate(String notificationTitle, String notificationImageText, String notificationTextBody) {
        //Log.i(TAG, "Got a new result: " + notification_title);
        Resources r = getResources();
        PendingIntent pi = PendingIntent
                .getActivity(this, 0, new Intent(this, SubAppActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(notificationTitle)
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(notificationImageText)
                .setContentText(notificationTextBody)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);

    }

    @Override
    public void update(Observable observable, Object o) {
        try {

            for (NotificationEvent notificationEvent : getNotificationManager().getPoolNotification()) {

                switch (NotificationType.getByCode(notificationEvent.getNotificationType())) {
                    case INCOMING_MONEY:
                        launchWalletNotification(notificationEvent.getWalletPublicKey(), notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;
                    case INCOMING_CONNECTION:
                        //launchWalletNotification(notificationEvent.getWalletPublicKey(), notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;
                    case INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION:
                        launchWalletNotification(notificationEvent.getWalletPublicKey(), notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;
                    case MONEY_REQUEST:
                        break;
                    case CLOUD_CONNECTED_NOTIFICATION:
                        launchWalletNotification(null, notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;
                    default:
                        launchWalletNotification(notificationEvent.getWalletPublicKey(), notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;

                }

            }

        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

    }

    @Override
    public RelativeLayout getActivityHeader() {
        return (RelativeLayout) findViewById(R.id.container_header_balance);
    }

    @Override
    public void changeNavigationDrawerAdapter(ListAdapter listAdapter) {
        navigationDrawerFragment.changeNavigationDrawerAdapter(listAdapter);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        this.mDetector.onTouchEvent(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(DEBUG_TAG,"Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int eventaction=event.getAction();

        final RelativeLayout header = getActivityHeader();

        if(header!=null) {
            final float y = event.getY();
            // Remember where we started
            lastTouchY = y;

            switch (eventaction) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(DEBUG_TAG, "Action was MOVE");

//                    TranslateAnimation anim = new TranslateAnimation(0, 0, header.getX() - header.getHeight(), 0);
//                    anim.setDuration(1000);
//
//                    anim.setAnimationListener(new TranslateAnimation.AnimationListener() {
//
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
////                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)header.getLayoutParams();
////                        params.topMargin += amountToMoveDown;
////                        params.leftMargin += amountToMoveRight;
////                        view.setLayoutParams(params);
//                            header.setVisibility(View.GONE);
//                        }
//                    });
//
//                    header.startAnimation(anim);
                    Log.d(TAG, TAG + "::onTouchEvent: parent ACTION_DOWN");


                    return false;
                case MotionEvent.ACTION_MOVE:
                    Log.d(DEBUG_TAG, "Action was DOWN");
                    return false;

                case MotionEvent.ACTION_UP:
                    Log.d(DEBUG_TAG, "Action was MOVE");

//                    TranslateAnimation anim1 = new TranslateAnimation(0, 0, 0, y);
//                    anim1.setDuration(1000);
//
//                    anim1.setAnimationListener(new TranslateAnimation.AnimationListener() {
//
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//                            header.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
////                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)header.getLayoutParams();
////                        params.topMargin += amountToMoveDown;
////                        params.leftMargin += amountToMoveRight;
////                        view.setLayoutParams(params);
//                        }
//                    });
//                    header.startAnimation(anim1);
                    Log.d(TAG, TAG + "::onTouchEvent: parent ACTION_UP");
                    return false;
                default:
                    break;
            }
        }

        return super.dispatchTouchEvent(event);
    }


    @Override
    public void invalidate() {
        //( (RelativeLayout) findViewById(R.id.activity_header)).invalidate();
    }

    @Override
    public void notificate(NotificationEvent notification) {
        try {

            for (NotificationEvent notificationEvent : getNotificationManager().getPoolNotification()) {

                switch (NotificationType.getByCode(notificationEvent.getNotificationType())) {
                    case INCOMING_MONEY:
                        launchWalletNotification(notificationEvent.getWalletPublicKey(), notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;
                    case INCOMING_CONNECTION:
                        //launchWalletNotification(notificationEvent.getWalletPublicKey(), notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;
                    case INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION:
                        launchWalletNotification(notificationEvent.getWalletPublicKey(), notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;
                    case MONEY_REQUEST:
                        break;
                    case CLOUD_CONNECTED_NOTIFICATION:
                        launchWalletNotification(null, notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;
                    default:
                        launchWalletNotification(notificationEvent.getWalletPublicKey(), notificationEvent.getAlertTitle(), notificationEvent.getTextTitle(), notificationEvent.getTextBody());
                        break;

                }

            }

        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

    }


    /**
     * Called when an item in the navigation drawer is selected.
     *
     * @param position
     */
    // @Override
    // public void onNavigationDrawerItemSelected(int position,String activityCode) {
    //      Toast.makeText(this,"holas",LENGTH_SHORT).show();
    //  }

}
