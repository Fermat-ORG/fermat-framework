package com.bitdubai.android_core.app;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.bitdubai.android_core.app.common.version_1.ApplicationConstants;
import com.bitdubai.android_core.app.common.version_1.adapters.ScreenPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;
import com.bitdubai.android_core.app.common.version_1.base_structure.config.FermatActivityConfiguration;
import com.bitdubai.android_core.app.common.version_1.bottom_navigation.BottomNavigation;
import com.bitdubai.android_core.app.common.version_1.builders.FooterBuilder;
import com.bitdubai.android_core.app.common.version_1.builders.SideMenuBuilder;
import com.bitdubai.android_core.app.common.version_1.classes.BroadcastManager;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.android_core.app.common.version_1.navigation_view.FermatActionBarDrawerEventListener;
import com.bitdubai.android_core.app.common.version_1.notifications.NotificationService;
import com.bitdubai.android_core.app.common.version_1.provisory.FermatInstalledDesktop;
import com.bitdubai.android_core.app.common.version_1.provisory.InstalledDesktop;
import com.bitdubai.android_core.app.common.version_1.provisory.ProvisoryData;
import com.bitdubai.android_core.app.common.version_1.recents.RecentsActivity;
import com.bitdubai.android_core.app.common.version_1.runtime_estructure_manager.RuntimeStructureManager;
import com.bitdubai.android_core.app.common.version_1.util.AndroidCoreUtils;
import com.bitdubai.android_core.app.common.version_1.util.LogReader;
import com.bitdubai.android_core.app.common.version_1.util.MainLayoutHelper;
import com.bitdubai.android_core.app.common.version_1.util.ServiceCallback;
import com.bitdubai.android_core.app.common.version_1.util.SharedMemory;
import com.bitdubai.android_core.app.common.version_1.util.mail.YourOwnSender;
import com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;
import com.bitdubai.fermat_android_api.engine.FermatAppsManager;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.PaintActivityFeatures;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatActivityManager;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.FermatStates;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetBitcoinNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFooter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatHeader;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatRuntime;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopObject;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopRuntimeManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.manager.fragment.DesktopSubAppFragment;
import com.bitdubai.sub_app.wallet_manager.fragment_factory.DesktopFragmentsEnumType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getAppResources;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getDesktopRuntimeManager;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getErrorManager;
import static java.lang.System.gc;

/**
 * Created by Matias Furszyfer
 * Update by Miguel Payarez on 2016.04.08
 */

public abstract class FermatActivity extends AppCompatActivity implements
        WizardConfiguration,
        PaintActivityFeatures,
        NavigationView.OnNavigationItemSelectedListener,
        FermatStates,
        FermatActivityManager,
        FermatListItemListeners<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem>,
        ServiceCallback {


    private static final String TAG = "fermat-core";
    public static final int TASK_MANAGER_STACK = 100;
    private MainMenu mainMenu;

    /**
     * Screen adapters
     */
    protected TabsPagerAdapter adapter;
    private ScreenPagerAdapter screenPagerAdapter;

    /**
     * Manager
     */
    private BroadcastManager broadcastManager;
    /**
     * WizardTypes
     */
    private Map<String, Wizard> wizards;

    /**
     * Activity type
     */
    private ActivityType activityType;

    /**
     * Constans
     */
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";

    /**
     * Handlers
     */
    private final Handler mDrawerActionHandler = new Handler();
    private final Handler refreshHandler = new Handler();

    /**
     * UI
     */
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;
    private Toolbar mToolbar;
    private RecyclerView navigation_recycler_view;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager pagertabs;
    protected TabLayout tabLayout;
    private CoordinatorLayout coordinatorLayout;
    private DrawerLayout mDrawerLayout;

    /**
     * This code will be in a manager in the new core
     */
    private List<ElementsWithAnimation> elementsWithAnimation = new ArrayList<>();
    private BottomNavigation bottomNavigation;

    /**
     * Listeners
     */
    private RuntimeStructureManager runtimeStructureManager;

    /**
     * Executor
     */
    protected ExecutorService executor;




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

        executor = Executors.newFixedThreadPool(FermatActivityConfiguration.POOL_THREADS);

        broadcastManager = new BroadcastManager(this);
        AndroidCoreUtils.getInstance().setContextAndResume(broadcastManager);
        if(!AndroidCoreUtils.getInstance().isStarted())
            AndroidCoreUtils.getInstance().setStarted(true);
        runtimeStructureManager = new RuntimeStructureManager(this);

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
//                        return true;
//                    }
//                });
                }
                //getMenuInflater().inflate(R.menu.wallet_store_activity_wallet_menu, menu);

            }
            return true;


        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
//            makeText(getApplicationContext(), "Oooops! recovering from system error",
//                    LENGTH_LONG).show();
            e.printStackTrace();
        }

        return super.onCreateOptionsMenu(menu);

    }
    /**
     * Dispatch onStop() to all fragments.  Ensure all loaders are stopped.
     */
    @Override
    protected void onStop() {
        try {
            super.onStop();
//            try{
//                AndroidCoreUtils.getInstance().clear();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that loads the UI
     */
    protected void loadBasicUI(Activity activity, final AppConnections appConnections) {
        // rendering UI components
        try {
           // Log.i("FERMAT ACTIVITY loadUI", "INICIA " + System.currentTimeMillis());
            TabStrip tabs = activity.getTabStrip();
            TitleBar titleBar = activity.getTitleBar();
            MainMenu mainMenu = activity.getMainMenu();

            SideMenu sideMenu = activity.getSideMenu();

            setMainLayout(sideMenu, activity.getHeader());
           // Log.i("FERMAT ACTIVITY loadUI", "setMainLayout " + System.currentTimeMillis());

            setMainMenu(mainMenu);
           // Log.i("FERMAT ACTIVITY loadUI", "setMainMenu " + System.currentTimeMillis());

            paintTabs(tabs, activity);
            //Log.i("FERMAT ACTIVITY loadUI", " paintTabs " + System.currentTimeMillis());

            paintStatusBar(activity.getStatusBar());
           // Log.i("FERMAT ACTIVITY loadUI", " paintStatusBar " + System.currentTimeMillis());

            paintTitleBar(titleBar, activity);
            //Log.i("FERMAT ACTIVITY loadUI", " paintTitleBar " + System.currentTimeMillis());

//            if(appConnections.getFullyLoadedSession().getModuleManager()!=null && sideMenu!=null) sideMenu.setNotifications(appConnections.getFullyLoadedSession().getModuleManager().getMenuNotifications());
            paintSideMenu(activity, sideMenu, appConnections);
            //Log.i("FERMAT ACTIVITY loadUI", " paintSideMenu " + System.currentTimeMillis());
            paintFooter(activity.getFooter(), appConnections.getFooterViewPainter());

           // Log.i("FERMAT ACTIVITY loadUI", " paintFooter " + System.currentTimeMillis());

            pantHeader(activity.getHeader(), appConnections.getHeaderViewPainter());

            //Log.i("FERMAT ACTIVITY loadUI", " pantHeader " + System.currentTimeMillis());
            setScreen(activity);

           // Log.i("FERMAT ACTIVITY loadUI", " setScreen " + System.currentTimeMillis());
            // rendering wizards components
            if (tabs != null && tabs.getWizards() != null)
                setWizards(tabs.getWizards());
            if (activity.getWizards() != null)
                setWizards(activity.getWizards());

           // Log.i("FERMAT ACTIVITY loadUI", " setWizards " + System.currentTimeMillis());

           // Log.i("FERMAT ACTIVITY loadUI", "FIN " + System.currentTimeMillis());
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getApplicationContext(), "Recovering from system error",
                    LENGTH_LONG).show();
            e.printStackTrace();
            handleExceptionAndRestart();
        }
    }

    private void pantHeader(FermatHeader header, HeaderViewPainter headerViewPainter) {
        if(header!=null && headerViewPainter!=null){
            if(header.hasExpandable()){
                headerViewPainter.addExpandableHeader(getToolbarHeader());
            }

        }
    }

    private void paintFooter(FermatFooter footer,FooterViewPainter footerViewPainter) {
        try {
            SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
            FrameLayout slide_container = (FrameLayout) findViewById(R.id.slide_container);
            RelativeLayout footer_container = (RelativeLayout) findViewById(R.id.footer_container);
            if (footer != null && footerViewPainter != null) {
                slide_container.setVisibility(View.VISIBLE);
                footer_container.setVisibility(View.VISIBLE);
                slidingDrawer.setVisibility(View.VISIBLE);
                if (footer.getBackgroundColor() != null) {
                    footer_container.setBackgroundColor(Color.parseColor(footer.getBackgroundColor()));
                }
                FooterBuilder.Builder.build(getLayoutInflater(), slide_container, footer_container, footerViewPainter);
            } else {
                if (slide_container != null) slide_container.setVisibility(View.GONE);
                if (footer_container != null) footer_container.setVisibility(View.GONE);
                findViewById(R.id.SlidingDrawer).setVisibility(View.GONE);
            }
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, e);
        }
    }

    private void paintSideMenu(Activity activity, SideMenu sideMenu,AppConnections appConnections) {
        try {
            if (sideMenu != null) {
                String backgroundColor = sideMenu.getBackgroudColor();
                if (backgroundColor != null) {
                    navigationView.setBackgroundColor(Color.parseColor(backgroundColor));
                }
                if(sideMenu.getNavigationIconColor()!=null)
                if(sideMenu.getNavigationIconColor().equals("#ffffff")){
                    mToolbar.setNavigationIcon(R.drawable.ic_actionbar_menu);
                }

                final NavigationViewPainter viewPainter = appConnections.getNavigationViewPainter();
                if(viewPainter!=null) {
                    /**
                     * Set header
                     */
                    FrameLayout frameLayout = SideMenuBuilder.setHeader(this, viewPainter,appConnections.getActiveIdentity());
                    assert frameLayout != null;
                    frameLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Object[] object = new Object[2];
                            if (viewPainter.hasClickListener())
                                connectWithOtherApp(Engine.BITCOIN_WALLET_CALL_INTRA_USER_IDENTITY, "public_key_ccp_intra_user_identity", object);
                        }
                    });
                    /**
                     * Set adapter
                     */
                    FermatAdapter mAdapter = viewPainter.addNavigationViewAdapter();
                    List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> lstItems = ApplicationSession.getInstance().getAppManager().getLastAppStructure().getLastActivity().getSideMenu().getMenuItems();
                    SideMenuBuilder.setAdapter(
                            navigation_recycler_view,
                            mAdapter,
                            viewPainter.addItemDecoration(),
                            lstItems,
                            this,
                            activity.getActivityType()
                    );
                    /**
                     * Body
                     */
                    RelativeLayout navigation_view_footer = (RelativeLayout) findViewById(R.id.navigation_view_footer);
                    SideMenuBuilder.setBody(navigation_view_footer,sideMenu.hasFooter(),viewPainter,getLayoutInflater());
                    /**
                     * Background color
                     */
                    final RelativeLayout navigation_view_body_container = (RelativeLayout) findViewById(R.id.navigation_view_body_container);
                    SideMenuBuilder.setBackground(navigation_view_body_container, viewPainter, getResources());
            }

            } else {
                mDrawerLayout.setEnabled(false);
                //test
                //mDrawerToggle.onDrawerClosed(mDrawerLayout);
            }
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            e.printStackTrace();
        }
    }

    private void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * @param titleBar
     */
    protected void paintTitleBar(TitleBar titleBar, Activity activity) {
        try {
            if (titleBar != null) {
                getSupportActionBar().setWindowTitle("");
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                mToolbar.setTitleTextColor(Color.TRANSPARENT);
                Typeface typeface = null;
                if(titleBar.getFont()!=null)
                typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/"+titleBar.getFont());

                String title = titleBar.getLabel();

                if(titleBar.isTitleTextStatic()){
                    View toolabarContainer = getLayoutInflater().inflate(R.layout.text_view, null);
                    FermatTextView txt_title = (FermatTextView) toolabarContainer.findViewById(R.id.txt_title);
                    txt_title.setText(title);
                    txt_title.setTypeface(typeface);
                    txt_title.setTextSize(titleBar.getLabelSize());
                    if(titleBar.getTitleColor()!=null)txt_title.setTextColor(Color.parseColor(titleBar.getTitleColor()));
                    mToolbar.addView(toolabarContainer);
                }else {

                    if (collapsingToolbarLayout != null) {
                        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT);
                        collapsingToolbarLayout.setCollapsedTitleTypeface(typeface);
                        collapsingToolbarLayout.setTitle(title);
                    }
                        mToolbar.setTitle(title);

                }

                if (titleBar.getColor() != null) {

                    if (collapsingToolbarLayout != null) {

                        collapsingToolbarLayout.setBackgroundColor(Color.parseColor(titleBar.getColor()));
                        collapsingToolbarLayout.setScrimsShown(true);
                        collapsingToolbarLayout.setContentScrimColor(Color.parseColor(titleBar.getColor()));
                        mToolbar.setBackgroundColor(Color.parseColor(titleBar.getColor()));
                        appBarLayout.setBackgroundColor(Color.parseColor(titleBar.getColor()));
                        //  mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                        //collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(R.color.gps_friends_green_main));
//                        if (titleBar.getTitleColor() != null) {
//                            collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor(titleBar.getTitleColor()));
//                        }
                    } else {
                        mToolbar.setBackgroundColor(Color.parseColor(titleBar.getColor()));
                        appBarLayout.setBackgroundColor(Color.parseColor(titleBar.getColor()));


                        if (titleBar.getTitleColor() != null) {
                            mToolbar.setTitleTextColor(Color.parseColor(titleBar.getTitleColor()));
                        }
                    }


                }


                setActionBarProperties(title, activity);
                paintToolbarIcon(titleBar);
            } else {
                if(appBarLayout!=null)
                appBarLayout.setVisibility(View.GONE);
                if (collapsingToolbarLayout != null)
                    collapsingToolbarLayout.setVisibility(View.GONE);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void paintToolbarIcon(TitleBar titleBar) {
        if (titleBar.getIconName() != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_action_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if no view has focus:
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    onBackPressed();
                }
            });
        }
        byte[] toolbarIcon = titleBar.getNavigationIcon();
        if (toolbarIcon != null)
            if (toolbarIcon.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(titleBar.getNavigationIcon(), 0, titleBar.getNavigationIcon().length);
                mToolbar.setNavigationIcon(new BitmapDrawable(getResources(), bitmap));
            }

    }

    /**
     * @param title
     */
    protected void setActionBarProperties(String title, Activity activity) {
        if(title!=null) {
            SpannableString s = new SpannableString(title);


//        s.setSpan(new MyTypefaceSpan(getApplicationContext(), "Roboto-Regular.ttf"), 0, s.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        // Update the action bar title with the TypefaceSpan instance
//        if (collapsingToolbarLayout != null)
//            collapsingToolbarLayout.setTitle(s);
//        mToolbar.setTitle(s);


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
    }

    /**
     * Method used from app to paint tabs
     */
    protected void setPagerTabs(TabStrip tabStrip, FermatSession fermatSession,FermatFragmentFactory fermatFragmentFactory) {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setVisibility(View.VISIBLE);
        pagertabs = (ViewPager) findViewById(R.id.pager);
        pagertabs.setVisibility(View.VISIBLE);
        adapter = new TabsPagerAdapter(getFragmentManager(),
                getApplicationContext(),
                fermatFragmentFactory,
                tabStrip,
                fermatSession,
                getAppResources());
        pagertabs.setAdapter(adapter);
        if(tabStrip.isHasIcon()){
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                byte[] image = tabStrip.getTabs().get(i).getIcon();
                tabLayout.getTabAt(i).setIcon(new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(image,0, image.length)));
            }
        }
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pagertabs.setPageMargin(pageMargin);
        pagertabs.setCurrentItem(tabStrip.getStartItem(), true);
        tabLayout.setupWithViewPager(pagertabs);
    }


    protected void setOneFragmentInScreen(FermatFragmentFactory fermatFragmentFactory,FermatSession fermatSession,FermatStructure fermatStructure) {

        try {
            String fragment = fermatStructure.getLastActivity().getLastFragment().getType();

            if (fermatFragmentFactory != null) {

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                tabLayout.setVisibility(View.GONE);

                pagertabs = (ViewPager) findViewById(R.id.pager);
                pagertabs.setVisibility(View.VISIBLE);


                adapter = new TabsPagerAdapter(getFragmentManager(),
                        getApplicationContext(),
                        fermatFragmentFactory,
                        fragment,
                        fermatSession,
                        getAppResources());
                pagertabs.setAdapter(adapter);


                //pagertabs.setCurrentItem();
                final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                        .getDisplayMetrics());
                pagertabs.setPageMargin(pageMargin);
                //pagertabs.setCurrentItem(tabStrip.getStartItem(), true);


                //tabLayout.setupWithViewPager(pagertabs);
                //pagertabs.setOffscreenPageLimit(tabStrip.getTabs().size());
            }


        } catch (Exception e) {
            e.printStackTrace();
            handleExceptionAndRestart();
        }

    }

    /**
     * Select the xml based on the activity type
     *
     * @param sidemenu
     * @param header
     */
    protected void setMainLayout(SideMenu sidemenu, FermatHeader header) {
        try {
            if (header != null) {
                setContentView(R.layout.new_wallet_runtime);
            } else {
                setContentView(R.layout.base_layout_desktop);
                if(activityType != ActivityType.ACTIVITY_TYPE_DESKTOP){
                    findViewById(R.id.reveal_bottom_container).setVisibility(View.GONE);
                    findViewById(R.id.bottom_navigation_container).setVisibility(View.GONE);
                }
            }

            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);


            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null)
                    setSupportActionBar(mToolbar);

            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

            if (collapsingToolbarLayout != null) {
                collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
                collapsingToolbarLayout.setTitle("");
                collapsingToolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT);
            }


            appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

            if (appBarLayout != null)
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    boolean isShow = false;
                    int scrollRange = -1;

                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if (scrollRange + verticalOffset == 0) {
                            collapsingToolbarLayout.setTitle("");
                            if(!isShow)
                                for(ElementsWithAnimation element : elementsWithAnimation){
                                    element.startCollapseAnimation(scrollRange);
                                }

                            isShow = true;
                        } else if (isShow) {
                            collapsingToolbarLayout.setTitle("");
                            for(ElementsWithAnimation element : elementsWithAnimation){
                                element.startExpandAnimation(scrollRange);
                            }
                            isShow = false;
                        }
                    }
                });

            if (header == null) {
                if (appBarLayout != null) {
                    appBarLayout.setExpanded(false);
                    appBarLayout.setEnabled(false);
                }

            }

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


            // listen for navigation events
            navigationView = (NavigationView) findViewById(R.id.navigation);


            if (sidemenu != null) {

                if (navigationView != null) {
                    navigationView.setNavigationItemSelectedListener(this);


                    navigation_recycler_view = (RecyclerView) findViewById(R.id.navigation_recycler_view);
                    RecyclerView.LayoutManager mLayoutManager;
                    // Letting the system know that the list objects are of fixed size
                    navigation_recycler_view.setHasFixedSize(true);

                    // Creating a layout Manager
                    mLayoutManager = new LinearLayoutManager(this);
                    // Setting the layout Manager
                    navigation_recycler_view.setLayoutManager(mLayoutManager);

                    // select the correct nav menu item
                    //navigationView.getMenu().findItem(mNavItemId).setChecked(true);

                    mToolbar.setNavigationIcon(R.drawable.ic_actionbar_menu);
                            /* setting up drawer layout */
                    mDrawerToggle = new FermatActionBarDrawerEventListener(this,
                            mDrawerLayout,
                            mToolbar,
                            R.string.open, R.string.close);

                    mDrawerLayout.setDrawerListener(mDrawerToggle);
                    mDrawerLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mDrawerToggle.syncState();
                        }
                    });

                    mDrawerToggle.setDrawerIndicatorEnabled(false);


                    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDrawerLayout.openDrawer(GravityCompat.START);
                        }
                    });

                    navigate(mNavItemId);
                }
            } else {
                navigationView.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
            handleExceptionAndRestart();
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
//        try {
//            if(broadcastManager!=null)broadcastManager.resume(this);
//            else broadcastManager = new BroadcastManager(this);
//            AndroidCoreUtils.getInstance().setContextAndResume(broadcastManager);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
            for (int position = 0; position < tabLayout.getTabCount(); position++) {
                ((TextView) tabLayout.getTabAt(position).getCustomView()).setTypeface(tf);
            }
            tabLayout.setVisibility(View.VISIBLE);
            if (tabs.getTabsColor() != null) {
                tabLayout.setBackgroundColor(Color.parseColor(activity.getTabStrip().getTabsColor()));
            }
            if (tabs.getTabsTextColor() != null) {
                if(tabs.getSelectedTabTextColor()!=null){
                    tabLayout.setTabTextColors(Color.parseColor(activity.getTabStrip().getTabsTextColor()), Color.parseColor(activity.getTabStrip().getSelectedTabTextColor()));
                }else{
                    tabLayout.setTabTextColors(Color.parseColor(activity.getTabStrip().getTabsTextColor()), Color.WHITE);
                }

            }
            if (tabs.getTabsIndicateColor() != null) {
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor(activity.getTabStrip().getTabsIndicateColor()));
            }
            if (tabs.getIndicatorHeight() != -1) {
                tabLayout.setSelectedTabIndicatorHeight(tabs.getIndicatorHeight());
            }
        }
    }





    private void setTranslucentStatusFlag(boolean on) {
        if (Build.VERSION.SDK_INT >= 19) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
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
                        window.setStatusBarColor(Color.parseColor(statusBar.getColor()));
                    } catch (Exception e) {
                        getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                        Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                    }
                }else{
                    try {
                        Window window = this.getWindow();
                        // clear FLAG_TRANSLUCENT_STATUS flag:
                        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                        // finally change the color
                        MainLayoutHelper.setTranslucentStatusBar(getWindow(),0);
                        gc();


                        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
                            //enable translucent statusbar via flags
                            setTranslucentStatusFlag(true);
                        }
                        if (Build.VERSION.SDK_INT >= 19) {
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                        }


                        //add a padding to the content of the drawer (25dp on devices starting with api v19)
                        //mDrawerContentRoot.setPadding(0, mActivity.getResources().getDimensionPixelSize(R.dimen.tool_bar_top_padding), 0, 0);

                        // define the statusBarColor
                        //mDrawerContentRoot.setInsetForeground(mStatusBarColor);


                        if(collapsingToolbarLayout!=null) collapsingToolbarLayout.setFitsSystemWindows(true);
                        if(coordinatorLayout!=null)coordinatorLayout.setFitsSystemWindows(true);
                        if(mToolbar!=null)mToolbar.setFitsSystemWindows(true);
                        if(mDrawerLayout!=null)mDrawerLayout.setFitsSystemWindows(true);
                        if(appBarLayout!=null)appBarLayout.setFitsSystemWindows(true);


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
                    if (Build.VERSION.SDK_INT > 20)
                        window.setStatusBarColor(Color.TRANSPARENT);
                    MainLayoutHelper.setTranslucentStatusBar(getWindow(),0);
                    gc();
                    //InputStream inputStream = getAssets().open("drawables/mdpi.jpg");
                    //window.setBackgroundDrawable(Drawable.createFromStream(inputStream, null));
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
                    window.setStatusBarColor(Color.TRANSPARENT);

                    MainLayoutHelper.setTranslucentStatusBar(getWindow(), 0);
                } catch (Exception e) {
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                    Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                } catch (OutOfMemoryError outOfMemoryError) {
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, new Exception());
                    makeText(this, "out of memory exception", LENGTH_SHORT).show();
                }
            }else{
                try {
                    Window window = this.getWindow();
                    // clear FLAG_TRANSLUCENT_STATUS flag:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    }
                    // finally change the color
                    if (Build.VERSION.SDK_INT > 20)
                        window.setStatusBarColor(Color.TRANSPARENT);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }

                    MainLayoutHelper.setTranslucentStatusBar(getWindow(), 0);

                    gc();
                    //InputStream inputStream = getAssets().open("drawables/mdpi.jpg");
                    //window.setBackgroundDrawable(Drawable.createFromStream(inputStream, null));
                } catch (Exception e) {
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                    Log.d("WalletActivity", "Sdk version not compatible with status bar color");
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
            if (pager != null) {
                pager.removeAllViews();
                pager.removeAllViewsInLayout();
                pager.clearOnPageChangeListeners();
                pager.setVisibility(View.GONE);
                ((ViewGroup) pager.getParent()).removeView(pager);
                pager = null;
            }
            System.gc();

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            if (tabLayout != null) {
                tabLayout.removeAllTabs();
                tabLayout.removeAllViews();
                tabLayout.removeAllViewsInLayout();
            }

            final io.codetail.widget.RevealFrameLayout mRevealView = (io.codetail.widget.RevealFrameLayout) findViewById(R.id.reveal);
            if(mRevealView!=null){
                mRevealView.removeAllViews();
                mRevealView.setVisibility(View.GONE);
            }


            removecallbacks();
            onRestart();
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            makeText(getApplicationContext(), "Recovering from system error",
                    LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    protected void removecallbacks() {
        try {

            if(adapter!=null) adapter.destroyCurrentFragments();
            this.adapter = null;
            paintStatusBar(null);
            if (navigation_recycler_view != null) {
                navigation_recycler_view.removeAllViews();
                navigation_recycler_view.removeAllViewsInLayout();
            }

            if (navigationView != null) {
                navigationView.removeAllViews();
                navigationView.removeAllViewsInLayout();

            }

            List<AbstractFermatFragment> fragments = new Vector<AbstractFermatFragment>();

            elementsWithAnimation = new ArrayList<>();
            if(bottomNavigation!=null) {
                bottomNavigation.reset();
                bottomNavigation = null;
            }

            this.screenPagerAdapter = new ScreenPagerAdapter(getFragmentManager(), fragments);

            System.gc();
            closeContextMenu();
            closeOptionsMenu();

            // Check if no view has focus:
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }


        } catch (Exception e) {

            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));

            makeText(getApplicationContext(), "Recovering from system error",
                    LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Initialise the fragments to be paged
     */
    protected void initialisePaging() {

        try {
            List<AbstractFermatFragment> fragments = new Vector<>();
            DesktopRuntimeManager desktopRuntimeManager = getDesktopRuntimeManager();

            AbstractFermatFragment[] fragmentsArray = new AbstractFermatFragment[3];


            for (DesktopObject desktopObject : desktopRuntimeManager.listDesktops().values()) {
                //TODO: este switch se cambiara por un FragmentFactory en algún momento al igual que el Activity factory
                switch (desktopObject.getType()) {
                    case "DCCP":
                        //por ahora va esto
                        AppConnections appConnections = FermatAppConnectionManager.getFermatAppConnection(desktopObject.getPublicKey(), this);
                            //WalletDesktopFragment walletDesktopFragment = WalletDesktopFragment.newInstance(0, manager);

//                            DesktopFragment desktopFragment = DesktopFragment.newInstance((WalletManagerModule) manager);

                        //String type = desktopObject.getLastActivity().getLastFragment().getType();

                        String type = desktopObject.getLastActivity().getFragment(DesktopFragmentsEnumType.DESKTOP_MAIN.getKey()).getType();

                        fragmentsArray[1] = appConnections.getFragmentFactory().getFragment(
                                type,
                                createOrOpenApp(getDesktopManager()),
                                null
                        );

                        type = desktopObject.getLastActivity().getFragment(DesktopFragmentsEnumType.DESKTOP_P2P_MAIN.getKey()).getType();

                        fragmentsArray[0] = appConnections.getFragmentFactory().getFragment(
                                type,
                                createOrOpenApp(getDesktopManager()),
                                null
                        );

//                        type = desktopObject.getLastActivity().getFragment(DesktopFragmentsEnumType.DESKTOP_SOCIAL_MAIN.getKey()).getType();
//
//                        fragmentsArray[2] = appConnections.getFragmentFactory().getFragment(
//                                type,
//                                createOrOpenApp(getDesktopManager()),
//                                null
//                        );

                        break;
                    case "WPD":
                            DesktopSubAppFragment subAppDesktopFragment = DesktopSubAppFragment.newInstance();
                            fragmentsArray[2] =  subAppDesktopFragment;
                        break;

                }
            }
            fragments = Arrays.asList(fragmentsArray);
            screenPagerAdapter = new ScreenPagerAdapter(getFragmentManager(), fragments);

            pagertabs = (ViewPager) super.findViewById(R.id.pager);
            pagertabs.setVisibility(View.VISIBLE);

            //set default page to show
            pagertabs.setCurrentItem(0);

            final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
            radioGroup.setVisibility(View.VISIBLE);
            pagertabs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            radioGroup.check(R.id.radioButton);
                            break;
                        case 1:
                            radioGroup.check(R.id.radioButton2);
                            break;
                        case 2:
                            radioGroup.check(R.id.radioButton3);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            pagertabs.setAdapter(this.screenPagerAdapter);

            for(int childPos = 0 ; childPos < radioGroup.getChildCount();childPos++){
                final int finalChildPos = childPos;
                radioGroup.getChildAt(childPos).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pagertabs.setCurrentItem(finalChildPos, true);
                    }
                });
            }

            if (pagertabs.getBackground() == null) {
                if(ApplicationSession.applicationState==ApplicationSession.STATE_STARTED) {
                    //Drawable d = Drawable.createFromStream(getAssets().open("drawables/mdpi.jpg"), null);
                    //getWindow().setBackgroundDrawable(Drawable.createFromStream(getAssets().open("drawables/mdpi.jpg"), null));
                    //pager.setBackground(d);
                    //getWindow().addFlags(WindowManager.LayoutParams.);
                    if (Build.VERSION.SDK_INT > 20) {
                        getWindow().setStatusBarColor(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        getWindow().setStatusBarColor(Color.TRANSPARENT);
                    }
                    final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                    Drawable wallpaperDrawable = null;
                    if (Build.VERSION.SDK_INT > 19)
                        wallpaperDrawable = wallpaperManager.getBuiltInDrawable();

//                    Display display = getWindowManager().getDefaultDisplay();
//                    Point size = new Point();
//                    display.getSize(size);
//                    Bitmap bmp = Bitmap.createScaledBitmap(((BitmapDrawable) wallpaperDrawable).getBitmap(), size.x + 300, size.y, true);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER,
                            WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);

                    ApplicationSession.applicationState = ApplicationSession.STATE_STARTED_DESKTOP;
                }
            }


        } catch (Exception ex) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(ex));
            makeText(getApplicationContext(), "Recovering from system error", LENGTH_SHORT).show();
            ex.printStackTrace();
            handleExceptionAndRestart();
        }
    }



    protected void bottomNavigationEnabled(boolean enabled){
        try {
            if (enabled) {
                bottomNavigation = new BottomNavigation(this, ProvisoryData.getBottomNavigationProvisoryData(), null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    protected FermatSession createOrOpenApplication() {
        try {
            Bundle bundle = getIntent().getExtras();
            FermatApp fermatApp = null;
            FermatAppType fermatAppType = null;
            String publicKey = null;
            if (bundle != null) {
                if (bundle.containsKey(ApplicationConstants.INSTALLED_FERMAT_APP)) {
                    fermatApp = ((FermatApp) bundle.getSerializable(ApplicationConstants.INSTALLED_FERMAT_APP));
                } else if (bundle.containsKey(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY)) {
                    publicKey = bundle.getString(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY);
                }
                if (fermatApp == null) {
                    fermatApp = ApplicationSession.getInstance().getAppManager().getApp(publicKey);
                }
                if (bundle.containsKey(ApplicationConstants.ACTIVITY_CODE_TO_OPEN)) {
                    String activityCode = bundle.getString(ApplicationConstants.ACTIVITY_CODE_TO_OPEN);
                    if (activityCode != null)
                        ApplicationSession.getInstance().getAppManager().getAppStructure(fermatApp.getAppPublicKey()).getActivity(Activities.valueOf(activityCode));
                }
            }
            return createOrOpenApp(fermatApp);
        }catch (Exception e){
            e.printStackTrace();
            handleExceptionAndRestart();
        }
        return null;
    }


    /**
     * Method to create or get a session in the session manager
     *
     * @param fermatApp
     * @return
     */
    private FermatSession createOrOpenApp(FermatApp fermatApp){
        FermatSession fermatSession = null;
        FermatAppsManager fermatAppsManager = ApplicationSession.getInstance().getAppManager();
        if(fermatAppsManager.isAppOpen(fermatApp.getAppPublicKey())){
            fermatSession = fermatAppsManager.getAppsSession(fermatApp.getAppPublicKey());
        }else{
            AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(fermatApp.getAppPublicKey(), this);
            fermatSession = fermatAppsManager.openApp(fermatApp,fermatAppConnection);
        }
        return fermatSession;
    }


    //TODO: esto es un plugin más para el manejo de los desktops
    protected InstalledDesktop getDesktopManager(){
        return new FermatInstalledDesktop();
    }

    /**
     * Set up wizards to this activity can be more than one.
     *
     * @param wizards
     */
    public void setWizards(Map<String, Wizard> wizards) {
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
    public void showWizard(String key, Object... args) {
        try {
            if (wizards == null)
                throw new NullPointerException("the wizard is null");

            Set<String> keys = wizards.keySet();
            System.out.println(keys);
            Wizard wizard = wizards.get(key);
            if (wizard != null) {
            /* Starting Wizard Activity */
                WizardActivity.open(this, args, wizard);
            } else {
                Log.e(TAG, "Wizard not found...");
            }
        } catch (Exception ex) {
            //makeText(this, "Cannot instantiate wizard runtime because the wizard called is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            wizards = null;
            Intent intent = new Intent(this, NotificationService.class);
            stopService(intent);

            //navigationDrawerFragment.onDetach();

            if (runtimeStructureManager != null) {
                runtimeStructureManager.clear();
            }

            try {
                broadcastManager.stop();
                AndroidCoreUtils.getInstance().clear(broadcastManager);
            } catch (Exception e) {
                e.printStackTrace();
            }


            /**
             * stop every service
             */
            //ApplicationSession.getInstance().getServicesHelpers().unbindServices();

            resetThisActivity();

            executor.shutdownNow();
            super.onDestroy();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void hideBottonIcons(){
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.icons_container);
        if(linearLayout!=null)
        linearLayout.setVisibility(View.GONE);
    }

    public int notificateProgressBroadcast(FermatBundle bundle) {
        int id = 0;
        try {
            //ApplicationSession.getInstance().getNotificationService().notificateProgress(bundle);
//            if(mNotificationServiceConnected){
//                id = notificationService.notificateProgress(bundle);
//            }else{
//                Intent intent = new Intent(this, NotificationService.class);
//                intent.putExtra(NotificationService.LOG_TAG,"Activity 1");
//                startService(intent);
//                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public void notificateBroadcast(String appPublicKey,String code){
        try {
            ApplicationSession.getInstance().getNotificationService().notificate(code, ApplicationSession.getInstance().getAppManager().getAppStructure(appPublicKey));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void notificateBroadcast(String appPublicKey,FermatBundle bundle){
        try {
//            if(mNotificationServiceConnected){
//                //notificationService.notificate(appPublicKey,bundle);
//            }else{
//                Intent intent = new Intent(this, NotificationService.class);
//                //acá puedo mandarle el messenger con el handler para el callback
//                intent.putExtra(NotificationService.LOG_TAG,"Activity 1");
//                startService(intent);
//                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void goHome() {
        try {
            FermatAppsManagerService appsManagerService = ApplicationSession.getInstance().getAppManager();
            appsManagerService.getAppStructure("main_desktop").getActivity(Activities.CCP_DESKTOP);
//            onBackPressed();
            Intent intent = new Intent(this, DesktopActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDesktopCallBack(DesktopHolderClickCallback desktopHolderClickCallback ){
        if(bottomNavigation!=null) bottomNavigation.setDesktopHolderClickCallback(desktopHolderClickCallback);
    }


    @Override
    public void invalidate() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    FermatStructure fermatStructure = ApplicationSession.getInstance().getAppManager().getLastAppStructure();
                    final Activity activity = fermatStructure.getLastActivity();
                    FermatSession fermatSession = ApplicationSession.getInstance().getAppManager().getAppsSession(fermatStructure.getPublicKey());
                    final AppConnections appsConnections = FermatAppConnectionManager.getFermatAppConnection(fermatStructure.getPublicKey(), getApplicationContext(), fermatSession);
                    try {
                        appsConnections.setActiveIdentity(fermatSession.getModuleManager().getSelectedActorIdentity());
                    } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            paintSideMenu(activity, activity.getSideMenu(), appsConnections);
                            paintFooter(activity.getFooter(), appsConnections.getFooterViewPainter());
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }

    protected void refreshSideMenu(AppConnections appConnections){
        try {
            //TODO: acá seria bueno un getLastApp
            if (ActivityType.ACTIVITY_TYPE_DESKTOP != activityType) {
                final FermatStructure fermatStructure = ApplicationSession.getInstance().getAppManager().getLastAppStructure();
                final NavigationViewPainter viewPainter = appConnections.getNavigationViewPainter();
                if(viewPainter!=null) {
                    final FermatAdapter mAdapter = viewPainter.addNavigationViewAdapter();
                    Activity activity = fermatStructure.getLastActivity();
                    if(activity!=null) {
                        SideMenu sideMenu = activity.getSideMenu();
                        List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> lstItems = null;
                        if (sideMenu != null) lstItems = sideMenu.getMenuItems();
                        final List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> finalLstItems = (lstItems != null) ? lstItems : new ArrayList<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem>();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SideMenuBuilder.setAdapter(
                                            navigation_recycler_view,
                                            mAdapter,
                                            viewPainter.addItemDecoration(),
                                            finalLstItems,
                                            getLisItemListenerMenu(),
                                            fermatStructure.getLastActivity().getActivityType()
                                    );
                                } catch (InvalidParameterException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else{
                        Log.e(TAG,"ActivityObject null, line:"+new Throwable().getStackTrace()[0].getLineNumber());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void onBackPressedNotificate(){
        if(getAdapter()!=null) {
            for (AbstractFermatFragment abstractFermatFragment : getAdapter().getLstCurrentFragments()) {
                abstractFermatFragment.onBackPressed();
            }
        }else if(getScreenAdapter()!=null){
            for (AbstractFermatFragment abstractFermatFragment : getScreenAdapter().getLstCurrentFragments()) {
                abstractFermatFragment.onBackPressed();
            }
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
        try {
            super.onConfigurationChanged(newConfig);
            if(mDrawerToggle!=null) mDrawerToggle.onConfigurationChanged(newConfig);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void navigate(final int itemId) {
        // perform the actual navigation logic, updating the main content fragment etc
        //Toast.makeText(this,"holas",LENGTH_SHORT).show();
    }

//    @Override
//    public boolean onOptionsItemSelected(final MenuItem item) {
//        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
//            return mDrawerToggle.onOptionsItemSelected(item);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private Runnable closeDrawerRunnable;
    @Override
    public void onBackPressed() {
        if(mDrawerLayout!=null) {
            if(closeDrawerRunnable==null){
                closeDrawerRunnable = new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                };
            }
            closeDrawerAndRunAnAction(closeDrawerRunnable);
        }else{
            super.onBackPressed();
        }
    }

    protected void closeDrawerAndRunAnAction(Runnable runnable){
        if(mDrawerLayout!=null) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mDrawerActionHandler.postDelayed(runnable, DRAWER_CLOSE_DELAY_MS);
            }
        }
    }

    /**
     * Navigation menu event handlers
     */

    @Override
    public void onItemClickListener(final com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, final int position) {
        data.setSelected(true);
        mNavItemId = data.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onNavigationMenuItemTouchListener(data, position);
            }
        }, DRAWER_CLOSE_DELAY_MS);
        ApplicationSession.getInstance().getAppManager().clearRuntime();

    }

    @Override
    public void onLongItemClickListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {

    }
    @Override
    public void setActivityBackgroundColor(Drawable drawable){
        if(drawable!=null) pagertabs.setBackground(drawable);

    }

    public void addCollapseAnimation(ElementsWithAnimation elementsWithAnimation){
        this.elementsWithAnimation.add(elementsWithAnimation);
    }



    /**
     * This methos is a touch listener from the navigation view.
     * the class that implement this methis have to use changeActivity, changeFragment, selectWallet or selectSubApp
     *
     * @param data
     * @param position
     */
    protected abstract void onNavigationMenuItemTouchListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position);

    public void setScreen(Activity activity) {
        try {
            if (activity.isFullScreen()) {
                // finally change the color
                try {
                    requestWindowFeature(Window.FEATURE_NO_TITLE);
                }catch (Exception ignored){

                }
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
            if(activity.getBackgroundColor()!=null && coordinatorLayout!=null){
                    coordinatorLayout.setBackgroundColor(Color.parseColor(activity.getBackgroundColor()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private FermatListItemListeners getLisItemListenerMenu(){
        return this;
    }

    /**
     * Abstract methods
     */

    public abstract void changeActivity(String activityName,String appBackPublicKey, Object... objects);

    //TODO: to remove
    public abstract void connectWithOtherApp(Engine engine,String fermatAppPublicKey,Object[] objectses);

    /**
     * Report error
     */
    public void reportError(String mailUserTo) throws Exception {
//        YourOwnSender yourOwnSender = new YourOwnSender(this);
//        String log = LogReader.getLog().toString();
//        yourOwnSender.send(mailUserTo,log );
        LogReader.getLog(this,mailUserTo);
      //  AndroidExternalAppsIntentHelper.sendMail(this,new String[]{mailUserTo},"Error report",log);


    }

    //send mail

    public void sendMailExternal(String mailUserTo, String bodyText) throws Exception {
        YourOwnSender yourOwnSender = new YourOwnSender(this);
        yourOwnSender.send(mailUserTo, bodyText);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(broadcastManager!=null)broadcastManager.stop();
//        networkStateReceiver.removeListener(this);
    }

    @Override
    public void selectApp(String appPublicKey) throws Exception {
        try{
            Intent intent;
            intent = new Intent();
            intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, appPublicKey);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(ApplicationConstants.INTENT_APP_TYPE,ApplicationSession.getInstance().getAppManager().getApp(appPublicKey).getAppType());
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            intent.setAction("org.fermat.APP_LAUNCHER");
            sendBroadcast(intent);
        }catch (Exception e){
            throw new Exception("App public key not exist");
        }
    }


    public void openRecentsScreen(){
        Intent resultIntent = new Intent(getApplicationContext(),RecentsActivity.class);
        // TODO Add extras or a data URI to this intent as appropriate.
        setResult(android.app.Activity.RESULT_OK, resultIntent);
        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        resultIntent.putExtra(ApplicationConstants.RECENT_APPS, ApplicationSession.getInstance().getAppManager().getRecentsAppsStack().toArray());
        startActivityForResult(resultIntent, TASK_MANAGER_STACK);
    }


    /**
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      //  Log.i("APP", "requestCode" + String.valueOf(requestCode));
      //  Log.i("APP", "resultcode" + String.valueOf(resultCode));
       // Log.i("APP", "data" + String.valueOf(data));
        switch(requestCode) {
            case (TASK_MANAGER_STACK) : {
                if (resultCode == android.app.Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity. and open the app
                    data.setAction("org.fermat.APP_LAUNCHER");
                    sendBroadcast(data);
                    //finish();
                } else if(resultCode == android.app.Activity.RESULT_CANCELED){
                    // if i want i could do something here
                }
                break;
            }
        }
    }

    protected void handleExceptionAndRestart(){
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
        finish();
    }

    private ServiceCallback getServiceCallback(){
        return this;
    }


    @Override
    public void callback(int option) {
        if (option==1){

        }
    }

    @Override
    public FermatRuntime getRuntimeManager(){
        return runtimeStructureManager;
    }

    public ScreenPagerAdapter getScreenAdapter() {
        return screenPagerAdapter;
    }

    public ViewPager getPagertabs() {
        return pagertabs;
    }

    public ActivityType getType() {
        return activityType;
    }


    @Override
    public NetworkStatus getFermatNetworkStatus() throws CantGetCommunicationNetworkStatusException {
        try {
            return FermatSystemUtils.getAndroidCoreModule().getFermatNetworkStatus();
        } catch (CantCreateProxyException e) {
            throw new CantGetCommunicationNetworkStatusException("",e,"","");
        }
    }

    @Override
    public NetworkStatus getBitcoinNetworkStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBitcoinNetworkStatusException {
        try {
            return FermatSystemUtils.getAndroidCoreModule().getBitcoinNetworkStatus(blockchainNetworkType);
        } catch (CantCreateProxyException e) {
            throw new CantGetBitcoinNetworkStatusException("",e,"","");
        }
    }

    @Override
    public NetworkStatus getPrivateNetworkStatus() {
        try {
            return FermatSystemUtils.getAndroidCoreModule().getPrivateNetworkStatus();
        } catch (CantCreateProxyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TabsPagerAdapter getAdapter() {
        return adapter;
    }


    public BroadcastManager getBroadcastManager() {
        return broadcastManager;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }


    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public RelativeLayout getToolbarHeader() {
        return (RelativeLayout) findViewById(R.id.toolbar_header_container);
    }


    public void setAppStatus(AppsStatus appStatus) {
        SharedMemory.getInstance().changeAppStatus(appStatus);
    }

    public AppsStatus getAppStatus(){
        return SharedMemory.getInstance().getAppStatus();
    }
}
