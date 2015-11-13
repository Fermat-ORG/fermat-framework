package com.bitdubai.android_core.app;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.fragment_factory.SubAppFragmentFactory;
import com.bitdubai.android_core.app.common.version_1.fragment_factory.WalletFragmentFactory;
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
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
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
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.notifications.FermatNotificationListener;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
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

public abstract class FermatActivity extends AppCompatActivity
        implements
            WizardConfiguration,
            FermatNotifications,
            PaintActivtyFeactures,
            Observer,
            FermatNotificationListener,
            NavigationView.OnNavigationItemSelectedListener,
            NavigationDrawerFragment.NavigationDrawerCallbacks,
            FermatListItemListeners<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> {



    private static final String TAG = "fermat-core";
    public static final String DEVELOP_MODE = "develop_mode";
    private MainMenu mainMenu;

    /**
     * Screen adapters
     */
    protected TabsPagerAdapter adapter;
    private TabsPagerAdapterWithIcons adapterWithIcons;
    private ScreenPagerAdapter screenPagerAdapter;

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

    private DrawerLayout mDrawerLayout;
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";

    private final Handler mDrawerActionHandler = new Handler();

    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;
    private Toolbar mToolbar;
    private RecyclerView navigation_recycler_view;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager pagertabs;
    private CoordinatorLayout coordinatorLayout;


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
            menu.clear();
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
            if (titleBar != null) {

                String title = titleBar.getLabel();



                if (collapsingToolbarLayout != null) {
                    collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                    collapsingToolbarLayout.setCollapsedTitleTypeface((Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Regular.ttf")));
                    //if (titleBar.getLabelSize() != -1) {
                        //collapsingToolbarLayout.setCollapsedTitleTex(titleBar.getLabelSize());

                    //}
                    collapsingToolbarLayout.setTitle(title);
                }else{
                    mToolbar.setTitle(title);
                }

                if(titleBar.getColor() != null){
                    mToolbar.setBackgroundColor(Color.parseColor(titleBar.getColor()));
                    appBarLayout.setBackgroundColor(Color.parseColor(titleBar.getColor()));


                    if(collapsingToolbarLayout!=null) {
                        collapsingToolbarLayout.setBackgroundColor(Color.parseColor(titleBar.getColor()));
                        //  mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                        //collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(R.color.gps_friends_green_main));
                        collapsingToolbarLayout.setContentScrimColor(Color.parseColor(titleBar.getColor()));
                    }

                }






                setActionBarProperties(title, activity);
                paintToolbarIcon(titleBar);
            } else {
                appBarLayout.setVisibility(View.GONE);
                if(collapsingToolbarLayout!=null)
                collapsingToolbarLayout.setVisibility(View.GONE);
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        collapsingToolbarLayout.setNestedScrollingEnabled(false);
                        appBarLayout.setNestedScrollingEnabled(false);
                        mToolbar.setNestedScrollingEnabled(false);
                        pagertabs.setNestedScrollingEnabled(false);
                        coordinatorLayout.setNestedScrollingEnabled(false);

                    }
                    AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP); // list other flags here by |
                    collapsingToolbarLayout.setLayoutParams(params);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void paintToolbarIcon(TitleBar titleBar) {
        if (titleBar.getIconName() != null) {
            mToolbar.setLogo(R.drawable.world);
        }

    }

    /**
     * @param title
     */
    protected void setActionBarProperties(String title, Activity activity) {
        SpannableString s = new SpannableString(title);


        s.setSpan(new MyTypefaceSpan(getApplicationContext(), "Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        if(collapsingToolbarLayout!=null)
        collapsingToolbarLayout.setTitle(s);
        mToolbar.setTitle(s);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//
//            Drawable colorDrawable = new ColorDrawable(Color.parseColor(activity.getColor()));
//            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
//            LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
//
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                //ld.setCallback(drawableCallback);
//                Log.d(getClass().getSimpleName(), "Version incompatible con status bar");
//            } else {
//                collapsingToolbarLayout.setBackgroundDrawable(ld);
//            }
//        }
    }

    /**
     * Method used from a Wallet to paint tabs
     */
    protected void setPagerTabs(WalletNavigationStructure wallet, TabStrip tabStrip, WalletSession walletSession) {

        //PagerSlidingTabStrip pagerSlidingTabStrip = ((PagerSlidingTabStrip) findViewById(R.id.tabs));
        //pagerSlidingTabStrip.setShouldExpand(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setVisibility(View.VISIBLE);

        pagertabs = (ViewPager) findViewById(R.id.pager);
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
        //pagerSlidingTabStrip.setViewPager(pagertabs);
        //pagerSlidingTabStrip.setShouldExpand(true);
        tabLayout.setupWithViewPager(pagertabs);
       // pagertabs.setOffscreenPageLimit(tabStrip.getTabs().size());

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

    /**
     * Select the xml based on the activity type
     *
     * @param sidemenu
     * @param header
     */
    protected void setMainLayout(SideMenu sidemenu, FermatHeader header) {
        try {

            if(header!=null){
                setContentView(R.layout.new_wallet_runtime);
            }else{
                setContentView(R.layout.base_layout_without_collapse);
            }


            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);


                    mToolbar = (Toolbar) findViewById(R.id.toolbar);
                    if(mToolbar!=null)
                    setSupportActionBar(mToolbar);

                    collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

                    if(collapsingToolbarLayout!=null)
                    collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

                    appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

                    if(appBarLayout!=null)
                    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                        boolean isShow = false;
                        int scrollRange = -1;

                        @Override
                        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                            if (scrollRange == -1) {
                                scrollRange = appBarLayout.getTotalScrollRange();
                            }
                            if (scrollRange + verticalOffset == 0) {
                                //collapsingToolbarLayout.setTitle("Title");
                                isShow = true;
                            } else if (isShow) {
                                //ollapsingToolbarLayout.setTitle("");
                                isShow = false;
                            }
                        }
                    });

            if(header==null){
                appBarLayout.setExpanded(false);
                appBarLayout.setEnabled(false);
            }

                    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


                    // listen for navigation events
                    navigationView = (NavigationView) findViewById(R.id.navigation);

                    if (sidemenu != null) {

                        if (navigationView != null) {
                            navigationView.setNavigationItemSelectedListener(this);


                            navigation_recycler_view = (RecyclerView) findViewById(R.id.navigation_recycler_view);
                            RecyclerView.LayoutManager mLayoutManager;

                            navigation_recycler_view.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size


                            mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
                            navigation_recycler_view.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


                            // select the correct nav menu item
                            //navigationView.getMenu().findItem(mNavItemId).setChecked(true);

                            /* setting up drawer layout */
                            mDrawerToggle = new ActionBarDrawerToggle(this,
                                    mDrawerLayout,
                                    mToolbar,
                                    R.string.open, R.string.close) {
                                @Override
                                public void onDrawerOpened(View drawerView) {
                                    super.onDrawerOpened(drawerView);
                                    //setTitle(mTitle);
                                    invalidateOptionsMenu();
                                }

                                @Override
                                public void onDrawerClosed(View drawerView) {
                                    super.onDrawerClosed(drawerView);
                                    //setTitle(mTitle);
                                    invalidateOptionsMenu();
                                }

                                @Override
                                public void onDrawerSlide(View drawerView, float slideOffset) {
                                    InputMethodManager imm =
                                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (getCurrentFocus() != null && imm != null && imm.isActive()) {
                                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                    }
                                    super.onDrawerSlide(drawerView, slideOffset);
                                    float moveFactor = (navigationView.getWidth() * slideOffset);
                                    //findViewById(R.id.content).setTranslationX(moveFactor);
                                }
                            };
                            mDrawerLayout.setDrawerListener(mDrawerToggle);
                            mDrawerLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    mDrawerToggle.syncState();
                                }
                            });

                            navigate(mNavItemId);
                        }
                    }else {
                        navigationView.setVisibility(View.GONE);
                    }

        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
        }
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
        try {
            getNotificationManager().addObserver(this);
            getNotificationManager().addCallback(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            getNotificationManager().deleteObserver(this);
            getNotificationManager().deleteCallback(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param tabs
     * @param activity
     */
    protected void paintTabs(TabStrip tabs, Activity activity) {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        if (tabs == null)
            tabLayout.setVisibility(View.GONE);
        else {
            Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Regular.ttf");
            for(int position=0;position<tabLayout.getTabCount();position++){
                ((TextView)tabLayout.getTabAt(position).getCustomView()).setTypeface(tf);
            }
            tabLayout.setVisibility(View.VISIBLE);

            // paint tabs color
            if (tabs.getTabsColor() != null) {
                tabLayout.setBackgroundColor(Color.parseColor(activity.getTabStrip().getTabsColor()));
            }

            // paint tabs text color
            if (tabs.getTabsTextColor() != null) {
                tabLayout.setTabTextColors(Color.parseColor(activity.getTabStrip().getTabsTextColor()),Color.WHITE);
            }

            //paint tabs indicate color
            if (tabs.getTabsIndicateColor() != null) {
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor(activity.getTabStrip().getTabsIndicateColor()));
            }

            if(tabs.getIndicatorHeight() != -1){
                tabLayout.setSelectedTabIndicatorHeight(tabs.getIndicatorHeight());
            }
        }

        // put tabs font
        //if (pagerSlidingTabStrip != null) {
            //pagerSlidingTabStrip.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Regular.ttf"), 1);
        //}
    }

    /**
     * Method to set status bar color in different version of android
     */

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
                        window.setStatusBarColor(Color.parseColor(statusBar.getColor()));
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
                    if(Build.VERSION.SDK_INT>20)
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

            //clean page adapter

            ViewPager pager = (ViewPager) findViewById(R.id.pager);
            if(pager!=null) {
                pager.removeAllViews();
                pager.removeAllViewsInLayout();
                pager.clearOnPageChangeListeners();
                pager.setVisibility(View.GONE);
                ((ViewGroup) pager.getParent()).removeView(pager);
                pager = null;
            }
            System.gc();


            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            if(tabLayout!=null){
                tabLayout.removeAllTabs();
                tabLayout.removeAllViews();
                tabLayout.removeAllViewsInLayout();
            }


            this.getNotificationManager().deleteObserver(this);

            this.adapter = null;
            paintStatusBar(null);

            if(navigationView!=null){
                navigationView.removeAllViewsInLayout();

            }

            List<android.app.Fragment> fragments = new Vector<android.app.Fragment>();

            this.screenPagerAdapter = new ScreenPagerAdapter(getFragmentManager(), fragments);

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



    /**
     * Initialise the fragments to be paged
     */
    protected void initialisePaging() {

        try {

            if(activePlatforms==null) {
                activePlatforms = new ArrayList();
                activePlatforms.add(Platforms.CRYPTO_CURRENCY_PLATFORM);
            }

            if(true){
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

            ViewPager pager = (ViewPager) super.findViewById(R.id.pager);
            pager.setVisibility(View.VISIBLE);

            //set default page to show
            pager.setCurrentItem(0);

            pager.setAdapter(this.screenPagerAdapter);

            if (pager.getBackground() == null) {
                //Drawable d = Drawable.createFromStream(getAssets().open("drawables/mdpi.jpg"), null);
                getWindow().setBackgroundDrawable(Drawable.createFromStream(getAssets().open("drawables/mdpi.jpg"), null));
                //pager.setBackground(d);
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
        return (CryptoBrokerWalletModuleManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CBP_CRYPTO_BROKER_WALLET_MODULE);
    }

    public CryptoCustomerWalletModuleManager getCryptoCustomerWalletModuleManager() {
        return (CryptoCustomerWalletModuleManager) ((ApplicationSession) getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CBP_CRYPTO_CUSTOMER_WALLET_MODULE);
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
    public void changeNavigationDrawerAdapter(FermatAdapter adapter) {
        adapter.changeDataSet(getNavigationMenu());
        adapter.setFermatListEventListener(this);
        navigation_recycler_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addNavigationViewHeader(View view){
        try {
            navigationView.addHeaderView(view);
            navigationView.invalidate();
            navigationView.postInvalidate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public RelativeLayout getToolbarHeader(){
        return (RelativeLayout) findViewById(R.id.toolbar_header_container);
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

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // update highlighted item in the navigation menu
        item.setChecked(true);
        mNavItemId = item.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(item.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);

        return true;
    }
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    private void navigate(final int itemId) {
        // perform the actual navigation logic, updating the main content fragment etc
    }

//    @Override
//    public boolean onOptionsItemSelected(final MenuItem item) {
//        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
//            return mDrawerToggle.onOptionsItemSelected(item);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     *
     * Navigation menu event handlers
     */

    @Override
    public void onItemClickListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {
        onNavigationMenuItemTouchListener(data,position);
    }

    @Override
    public void onLongItemClickListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {

    }

    /**
     *
     * Abstract methods
     */

    public abstract void onNavigationDrawerItemSelected(int position, String activityCode);

    protected abstract List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> getNavigationMenu();

    /**
     * This methos is a touch listener from the navigation view.
     * the class that implement this methis have to use changeActivity, changeFragment, selectWallet or selectSubApp
     *
     * @param data
     * @param position
     */
    protected abstract void onNavigationMenuItemTouchListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position);

}
