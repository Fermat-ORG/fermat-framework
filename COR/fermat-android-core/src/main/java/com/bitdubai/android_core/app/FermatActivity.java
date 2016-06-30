package com.bitdubai.android_core.app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.text.InputType;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.adapters.FermatScreenAdapter;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter2;
import com.bitdubai.android_core.app.common.version_1.base_structure.config.FermatActivityConfiguration;
import com.bitdubai.android_core.app.common.version_1.bottom_navigation.BottomNavigation;
import com.bitdubai.android_core.app.common.version_1.builders.FooterBuilder;
import com.bitdubai.android_core.app.common.version_1.builders.SideMenuBuilder;
import com.bitdubai.android_core.app.common.version_1.builders.nav_menu.NavMenuBasicAdapter;
import com.bitdubai.android_core.app.common.version_1.builders.option_menu.OptionMenuFrameworkHelper;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.android_core.app.common.version_1.navigation_view.FermatActionBarDrawerEventListener;
import com.bitdubai.android_core.app.common.version_1.notifications.NotificationService;
import com.bitdubai.android_core.app.common.version_1.provisory.FermatInstalledDesktop;
import com.bitdubai.android_core.app.common.version_1.provisory.InstalledDesktop;
import com.bitdubai.android_core.app.common.version_1.provisory.ProvisoryData;
import com.bitdubai.android_core.app.common.version_1.receivers.UpdateViewReceiver;
import com.bitdubai.android_core.app.common.version_1.runtime_estructure_manager.RuntimeStructureManager;
import com.bitdubai.android_core.app.common.version_1.util.AndroidCoreUtils;
import com.bitdubai.android_core.app.common.version_1.util.LogReader;
import com.bitdubai.android_core.app.common.version_1.util.MainLayoutHelper;
import com.bitdubai.android_core.app.common.version_1.util.ResourceLocationSearcherHelper;
import com.bitdubai.android_core.app.common.version_1.util.SharedMemory;
import com.bitdubai.android_core.app.common.version_1.util.mail.YourOwnSender;
import com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils;
import com.bitdubai.fermat.BuildConfig;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;
import com.bitdubai.fermat_android_api.engine.FermatAppsManager;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.PaintActivityFeatures;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ComboAppType2FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatActivityManager;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FrameworkHelpers;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFooter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatHeader;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatRuntime;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatSideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.nav_menu.FermatBasicNavigationMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuChangeActivityOnPressEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuPressEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopObject;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopRuntimeManager;
import com.bitdubai.sub_app.manager.fragment.DesktopSubAppFragment;
import com.bitdubai.sub_app.wallet_manager.fragment_factory.DesktopFragmentsEnumType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getDesktopRuntimeManager;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getErrorManager;
import static java.lang.System.gc;

/**
 * Created by Matias Furszyfer
 */

public abstract class FermatActivity extends AppCompatActivity implements
        WizardConfiguration,
        PaintActivityFeatures,
        NavigationView.OnNavigationItemSelectedListener,
        FermatStates,
        FrameworkHelpers,
        FermatActivityManager,
        FermatListItemListeners<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem>{


    private static final String TAG = "fermat-core";
    private OptionsMenu optionsMenu;

    /**
     * Screen adapters
     */
    private FermatScreenAdapter adapter;
//    private ScreenPagerAdapter<?> screenPagerAdapter;

    /**
     * WizardTypes
     */
    private Map<String, Wizard> wizards;

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
     * receivers
     */
    UpdateViewReceiver updateViewReceiver;


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

        if(!AndroidCoreUtils.getInstance().isStarted())
            AndroidCoreUtils.getInstance().setStarted(true);
        runtimeStructureManager = new RuntimeStructureManager(this);

        updateViewReceiver = new UpdateViewReceiver(this);
        IntentFilter intentFilter = new IntentFilter(UpdateViewReceiver.INTENT_NAME);
        registerReceiver(updateViewReceiver, intentFilter);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        try {
            menu.clear();
            if (optionsMenu != null) {
                List<OptionMenuItem> optionsMenuItems = optionsMenu.getMenuItems();
                loadMenu(menu,optionsMenuItems);
//                for (int i=0;i< optionsMenuItems.size();i++) {
//                    OptionMenuItem menuItem = optionsMenuItems.get(i);
//                    int id = menuItem.getId();
//                    int groupId = menuItem.getGroupId();
//                    int order = menuItem.getOrder();
//                    int showAsAction = menuItem.getShowAsAction();
//                    MenuItem item = menu.add(groupId, id, order, menuItem.getLabel());
//                    FermatDrawable icon = menuItem.getFermatDrawable();
//                    if(icon!=null) {
//                        int iconRes = ResourceLocationSearcherHelper.obtainRes(this,icon.getId(),icon.getSourceLocation(),icon.getOwner().getOwnerAppPublicKey());
//                        item.setIcon(iconRes);//.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//
//                    }
//                    if(showAsAction!=-1)item.setShowAsAction(menuItem.getShowAsAction());
//                    int actionViewClass = menuItem.getActionViewClass();
//                    if(actionViewClass!=-1){
//                        item.setActionView(OptionMenuFrameworkHelper.obtainFrameworkAvailableOptionMenuItems(this,actionViewClass));
//                    }
//                    if(menuItem.getOptionMenuPressEvent()!=null){
//                        final OptionMenuPressEvent optionMenuPressEvent = menuItem.getOptionMenuPressEvent();
//                        if(optionMenuPressEvent instanceof OptionMenuChangeActivityOnPressEvent) {
//                            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                                @Override
//                                public boolean onMenuItemClick(MenuItem item) {
//                                    changeActivity(((OptionMenuChangeActivityOnPressEvent) optionMenuPressEvent).getActivityCode(),null);
//                                    //return true because i want to cancell the rest of the callback if this is an activity change
//                                    return true;
//                                }
//                            });
//                        }
//                    }
//                }
            }
            return true;


        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
//            makeText(getApplicationContext(), "Oooops! recovering from system error",
//                    LENGTH_LONG).show();
            e.printStackTrace();
        }
        return true;
    }

    private void loadMenu(Menu menu,List<OptionMenuItem> menuItemList){
        for (int i=0;i< menuItemList.size();i++) {
            OptionMenuItem menuItem = menuItemList.get(i);
            int id = menuItem.getId();
            int groupId = menuItem.getGroupId();
            int order = menuItem.getOrder();
            int showAsAction = menuItem.getShowAsAction();
            MenuItem item = menu.add(groupId, id, order, menuItem.getLabel());
            FermatDrawable icon = menuItem.getFermatDrawable();
            if(icon!=null) {
                int iconRes = ResourceLocationSearcherHelper.obtainRes(this,icon.getId(),icon.getSourceLocation(),icon.getOwner().getOwnerAppPublicKey());
                item.setIcon(iconRes);//.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

            }
            if(showAsAction!=-1)item.setShowAsAction(menuItem.getShowAsAction());
            int actionViewClass = menuItem.getActionViewClass();
            if(actionViewClass!=-1){
                item.setActionView(OptionMenuFrameworkHelper.obtainFrameworkAvailableOptionMenuItems(this,actionViewClass));
            }
            if(menuItem.hasSubMenu()){
                SubMenu subMenu = item.getSubMenu();
                List<OptionMenuItem> subMenuItemList = menuItem.getSubMenuOptionList();
                loadMenu(subMenu, subMenuItemList);
            }
            item.setVisible(menuItem.isVisible());
            if(menuItem.getOptionMenuPressEvent()!=null){
                final OptionMenuPressEvent optionMenuPressEvent = menuItem.getOptionMenuPressEvent();
                if(optionMenuPressEvent instanceof OptionMenuChangeActivityOnPressEvent) {
                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            changeActivity(((OptionMenuChangeActivityOnPressEvent) optionMenuPressEvent).getActivityCode(),null);
                            //return true because i want to cancell the rest of the callback if this is an activity change
                            return true;
                        }
                    });
                }
            }
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
            if (optionsMenu != null) {
                List<OptionMenuItem> optionsMenuItems = optionsMenu.getMenuItems();
                for (int i=0;i< optionsMenuItems.size();i++) {
                    OptionMenuItem menuItem = optionsMenuItems.get(i);
                    int id = menuItem.getId();
                    int groupId = menuItem.getGroupId();
                    int order = menuItem.getOrder();
                    int showAsAction = menuItem.getShowAsAction();
                    MenuItem item = menu.add(groupId, id, order, menuItem.getLabel());
                    FermatDrawable icon = menuItem.getFermatDrawable();
                    if(icon!=null) {
                        int iconRes = ResourceLocationSearcherHelper.obtainRes(this,icon.getId(),icon.getSourceLocation(),icon.getOwner().getOwnerAppPublicKey());
                        item.setIcon(iconRes);//.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                    }
                    if(showAsAction!=-1)item.setShowAsAction(menuItem.getShowAsAction());
                    int actionViewClass = menuItem.getActionViewClass();
                    if(actionViewClass!=-1){
                        item.setActionView(OptionMenuFrameworkHelper.obtainFrameworkAvailableOptionMenuItems(this,actionViewClass));
                    }
                    if(menuItem.getOptionMenuPressEvent()!=null){
                        final OptionMenuPressEvent optionMenuPressEvent = menuItem.getOptionMenuPressEvent();
                        if(optionMenuPressEvent instanceof OptionMenuChangeActivityOnPressEvent) {
                            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    changeActivity(((OptionMenuChangeActivityOnPressEvent) optionMenuPressEvent).getActivityCode(),null);
                                    //return true because i want to cancell the rest of the callback if this is an activity change
                                    return true;
                                }
                            });
                        }
                    }
                }
            }
            return true;


        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
//            makeText(getApplicationContext(), "Oooops! recovering from system error",
//                    LENGTH_LONG).show();
            e.printStackTrace();
        }

        return true;
    }
    /**
     * Dispatch onStop() to all fragments.  Ensure all loaders are stopped.
     */
    @Override
    protected void onStop() {
        try {
            super.onStop();

            if(updateViewReceiver!=null){
                try {
                    unregisterReceiver(updateViewReceiver);
                    updateViewReceiver.clear();
                }catch (Exception e){
                    //nothing
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that loads the UI
     */
    protected void loadBasicUI(Activity activity, final AppConnections appConnections) {
        // rendering UI components
        if(activity==null) Log.e(TAG,"Error, runtime activity is null, please check your app in the AppRuntime.");
        try {
           // Log.i("FERMAT ACTIVITY loadUI", "INICIA " + System.currentTimeMillis());
            TabStrip tabs = activity.getTabStrip();
            TitleBar titleBar = activity.getTitleBar();
            OptionsMenu optionsMenu = activity.getOptionsMenu();

            SideMenu sideMenu = activity.getSideMenu();

            setMainLayout(sideMenu, activity.getHeader());
           // Log.i("FERMAT ACTIVITY loadUI", "setMainLayout " + System.currentTimeMillis());

            setOptionsMenu(optionsMenu);
           // Log.i("FERMAT ACTIVITY loadUI", "setOptionsMenu " + System.currentTimeMillis());

            paintTabs(tabs, activity);
            //Log.i("FERMAT ACTIVITY loadUI", " paintTabs " + System.currentTimeMillis());

            paintStatusBar(activity.getStatusBar());
           // Log.i("FERMAT ACTIVITY loadUI", " paintStatusBar " + System.currentTimeMillis());

            paintTitleBar(titleBar, activity);
            //Log.i("FERMAT ACTIVITY loadUI", " paintTitleBar " + System.currentTimeMillis());

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

            invalidateOptionsMenu();

           // Log.i("FERMAT ACTIVITY loadUI", "FIN " + System.currentTimeMillis());
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Recovering from system error",
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

    private <T extends FermatSideMenu> void paintSideMenu(Activity activity, T sideMenu,AppConnections appConnections) {
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
                /**
                 * Set header
                 */
                FrameLayout frameLayout = SideMenuBuilder.setHeader(this, viewPainter);
                /**
                 * Set adapter
                 */
                List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> lstItems = sideMenu.getMenuItems();
                //todo: mejorar esto
                FermatAdapter mAdapter = (viewPainter!=null)?viewPainter.addNavigationViewAdapter():new NavMenuBasicAdapter(this,lstItems,((FermatBasicNavigationMenu)sideMenu).getBody());
                SideMenuBuilder.setAdapter(
                        navigation_recycler_view,
                        mAdapter,
                        (viewPainter!=null)?viewPainter.addItemDecoration():null,
                        (viewPainter!=null)?lstItems:((FermatBasicNavigationMenu)sideMenu).getBody().getMenuItems(),
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

                /**
                 * Background drawable
                 */
                if (sideMenu.getBackgroundDrawable()!=null){
                    FermatDrawable backgroundDrawableColor = sideMenu.getBackgroundDrawable();
                    navigationView.setBackgroundResource(ResourceLocationSearcherHelper.obtainRes(this,backgroundDrawableColor.getId(), backgroundDrawableColor.getSourceLocation(),backgroundDrawableColor.getOwner().getOwnerAppPublicKey()));
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

    private void setOptionsMenu(OptionsMenu optionsMenu) {
        this.optionsMenu = optionsMenu;
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
                try {
                    if (titleBar.getFont() != null)
                        typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + titleBar.getFont());
                }catch (Exception e){
                    e.printStackTrace();
                }
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


                if(titleBar.getBackgroundDrawable()!=null){
                    FermatDrawable backgroundDrawable = titleBar.getBackgroundDrawable();
                    mToolbar.setBackgroundResource(ResourceLocationSearcherHelper.obtainRes(this,backgroundDrawable.getId(),backgroundDrawable.getSourceLocation(),backgroundDrawable.getOwner().getOwnerAppPublicKey()));
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
        if(titleBar.getNavItem()!=null){
            final com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem menuItem = titleBar.getNavItem();
            FermatDrawable leftIconFermatDrawable = menuItem.getFermatDrawable();
            int resId = ResourceLocationSearcherHelper.obtainRes(
                    this,
                    leftIconFermatDrawable.getId(),
                    leftIconFermatDrawable.getSourceLocation(),
                    leftIconFermatDrawable.getOwner().getOwnerAppPublicKey()
            );
            mToolbar.setNavigationIcon(resId);
            if(menuItem.getAppLinkPublicKey().equals("back")){
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }else if(menuItem.getAppLinkPublicKey().equals("nav_menu")) {
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //this is for open the nav menu
                    }
                });
            }else {
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeActivity(menuItem.getLinkToActivity().getCode(),null,null);
                    }
                });
            }
        }else {
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
//            FermatDrawable colorDrawable = new ColorDrawable(Color.parseColor(activity.getColor()));
//            FermatDrawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
//            LayerDrawable ld = new LayerDrawable(new FermatDrawable[]{colorDrawable, bottomDrawable});
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
     * Tabs
     */
    protected void setPagerTabs(TabStrip tabStrip,FermatSession session){
        int tabsSize = tabStrip.getTabs().size();
        List<Tab> tabs = tabStrip.getTabs();
        Fragment[] fragments = new Fragment[tabsSize];
        String[] tabTitles = new String[tabsSize];
        FermatFragment[] fermatFragments = new FermatFragment[tabsSize];
        FermatDrawable[] tabsDrawables = new FermatDrawable[tabsSize];
        //View[] tabsViews = new View[tabsSize];
        try {
            for (int i=0;i<tabs.size();i++) {
                Tab tab = tabs.get(i);
                FermatFragment fragment = tab.getFragment();
                fermatFragments[i] = fragment;
                //optionMenu
                if(fragment.getOptionsMenu()!=null)addOptionMenuItems(fragment.getOptionsMenu());
                Owner owner = fragment.getOwner();
                if(owner==null) throw new NullPointerException("Owner null on fragment: "+fragment.getType()+" in app: "+session.getAppPublicKey()+", Please check your App structure");
                String appPublicKey = owner.getOwnerAppPublicKey().equals(session.getAppPublicKey()) ? session.getAppPublicKey() : fragment.getOwner().getOwnerAppPublicKey();
                AppConnections appConnections = FermatAppConnectionManager.getFermatAppConnection(appPublicKey, this);
                if (session instanceof ComboAppType2FermatSession) {
                    session = ((ComboAppType2FermatSession) session).getFermatSession(appPublicKey, FermatSession.class);
                }
                try {
                    fragments[i] = appConnections.getFragmentFactory().getFragment(fragment.getType(),session,null,fragment);
                } catch (FragmentNotFoundException e) {
                   throw new InvalidParameterException(e,"Fragment not found: "+fragment.getType()+" with owner: "+fragment.getOwner(),"Framework building tabs");
                }
                tabTitles[i] = tab.getLabel();
                tabsDrawables[i] = tab.getDrawable();
                //tabsViews[i] = tab.getFermatView();
            }
            tabLayout.setVisibility(View.VISIBLE);
            pagertabs = (ViewPager) findViewById(R.id.pager);
            pagertabs.setVisibility(View.VISIBLE);
            adapter = new TabsPagerAdapter2(getFragmentManager(),tabTitles,fragments);
            pagertabs.setAdapter(adapter);
            if(tabStrip.isHasIcon()){
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    byte[] image = tabStrip.getTabs().get(i).getIcon();
                    tabLayout.getTabAt(i).setIcon(new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(image,0, image.length)));
                }
                for (int i=0;i<tabsSize;i++) {
                    FermatDrawable tabDrawables = tabsDrawables[i];
                    if(tabDrawables!=null){
                        tabLayout.getTabAt(i).setIcon(ResourceLocationSearcherHelper.obtainRes(this,tabDrawables.getId(),tabDrawables.getSourceLocation(),tabDrawables.getOwner().getOwnerAppPublicKey()));
                    }
                }
            }




            final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                    .getDisplayMetrics());
            pagertabs.setPageMargin(pageMargin);
            adapter.setStartFragmentPosition(tabStrip.getStartItem());
            pagertabs.setCurrentItem(tabStrip.getStartItem(), true);
            tabLayout.setupWithViewPager(pagertabs);

            // fragment focus
            pagertabs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    adapter.onFragmentFocus(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (InvalidParameterException e) {
            Log.e(TAG, "Invalid parameter, please check your runtime");
            e.printStackTrace();
            handleExceptionAndRestart();
        } catch (Exception e){
            Log.e(TAG,e.getMessage());
            handleExceptionAndRestart();
        }
    }

    /**
     *  Print tabs and return a FermatFragment array to be painted
     *
     * @param tabStrip
     * @return
     */
    //todo: use this improved method
    private FermatFragment[] setTabs(TabStrip tabStrip){
        List<Tab> tabs = tabStrip.getTabs();
        FermatFragment[] fragments = new FermatFragment[tabStrip.getTabs().size()];
        String[] tabTitles = new String[tabStrip.getTabs().size()];
        for (int i=0;i<tabs.size();i++) {
            Tab tab = tabs.get(i);
            fragments[i] = tab.getFragment();
            //optionMenu
            tabTitles[i] = tab.getLabel();
        }
        tabLayout.setVisibility(View.VISIBLE);
        if(tabStrip.isHasIcon()){
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                byte[] image = tabStrip.getTabs().get(i).getIcon();
                tabLayout.getTabAt(i).setIcon(new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(image,0, image.length)));
            }
        }
        tabLayout.setupWithViewPager(pagertabs);
        pagertabs.setCurrentItem(tabStrip.getStartItem(), true);
        // fragment focus
        pagertabs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.onFragmentFocus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return fragments;
    }
    //todo: use this improved method
    private void paintBodyFragments(FermatFragment[] fermatFragments,FermatSession session){
        Fragment[] fragments = new Fragment[fermatFragments.length];
        for (int i=0;i<fermatFragments.length;i++) {
            try {
                FermatFragment fragment = fermatFragments[i];
                //optionMenu
                if (fragment.getOptionsMenu() != null)
                    addOptionMenuItems(fragment.getOptionsMenu());
                String appPublicKey = fragment.getOwner().getOwnerAppPublicKey().equals(session.getAppPublicKey()) ? session.getAppPublicKey() : fragment.getOwner().getOwnerAppPublicKey();
                AppConnections appConnections = FermatAppConnectionManager.getFermatAppConnection(appPublicKey, this);
                if (session instanceof ComboAppType2FermatSession) {
                    session = ((ComboAppType2FermatSession) session).getFermatSession(appPublicKey, FermatSession.class);
                }
                try {
                    fragments[i] = appConnections.getFragmentFactory().getFragment(fragment.getType(), session, null,fragment);
                } catch (FragmentNotFoundException e) {
                    throw new InvalidParameterException(e, "Fragment not found: " + fragment.getType() + " with owner: " + fragment.getOwner(), "Framework building tabs");
                }
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
        }
        pagertabs = (ViewPager) findViewById(R.id.pager);
        pagertabs.setVisibility(View.VISIBLE);
        //todo: descomentar y ver que onda
        //adapter = new TabsPagerAdapter2(getFragmentManager(),fragments);
        pagertabs.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        pagertabs.setPageMargin(pageMargin);

    }

    /**
     *  One Fragment screen
     *
     * @param fermatFragmentFactory
     * @param referenceAppFermatSession
     * @param runtimeFragment
     */
    protected void setOneFragmentInScreen(FermatFragmentFactory fermatFragmentFactory,FermatSession referenceAppFermatSession, FermatFragment runtimeFragment) {
        try {
            String fragment = runtimeFragment.getType();
            if(runtimeFragment.getOptionsMenu()!=null)addOptionMenuItems(runtimeFragment.getOptionsMenu());
            if (fermatFragmentFactory != null) {
                tabLayout.setVisibility(View.GONE);
                pagertabs = (ViewPager) findViewById(R.id.pager);
                pagertabs.setVisibility(View.VISIBLE);
                adapter = new TabsPagerAdapter2(
                        getFragmentManager(),
                        null,
                        new Fragment[]{fermatFragmentFactory.getFragment(fragment,referenceAppFermatSession,null,runtimeFragment)});
                pagertabs.setAdapter(adapter);
                final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
                pagertabs.setPageMargin(pageMargin);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleExceptionAndRestart();
        }
    }

    protected void addOptionMenuItems(OptionsMenu optionsMenu) {
        optionsMenu.addMenuItems(optionsMenu.getMenuItems());
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
                if(!(this instanceof DesktopActivity)){
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
                    int scrollRange = -1;
                    boolean alreadyPerform = false;

                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if(verticalOffset == 0) {
                            alreadyPerform = false;
                            for(ElementsWithAnimation element : elementsWithAnimation)
                                element.startCollapseAnimation(getApplicationContext(), verticalOffset);
                        } else if (verticalOffset < 0 && !alreadyPerform) {
                            alreadyPerform = true;
                            for(ElementsWithAnimation element : elementsWithAnimation)
                                element.startExpandAnimation(getApplicationContext(), verticalOffset);
                        }
                    }
                });




            if (header == null) {
                if (appBarLayout != null) {
                    appBarLayout.setExpanded(false);
                    appBarLayout.setEnabled(false);
                }

            }else{
                if(header.getRemoveHeaderScroll()) {
                    final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)
                            collapsingToolbarLayout.getLayoutParams();
                    params.setScrollFlags(0);
                    collapsingToolbarLayout.setLayoutParams(params);
                }

                if(header.getStartCollapsed()){
                    appBarLayout.setExpanded(false);
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


    @Override
    protected void onResume() {
        if(updateViewReceiver==null){
            updateViewReceiver = new UpdateViewReceiver(this);
            IntentFilter intentFilter = new IntentFilter(UpdateViewReceiver.INTENT_NAME);
            registerReceiver(updateViewReceiver, intentFilter);
        }
        super.onResume();

    }


    /**
     * @param tabs
     * @param activity
     */
    protected void paintTabs(TabStrip tabs, Activity activity) {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if(tabLayout!=null){
            if (tabs == null )
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
                    if (tabs.getSelectedTabTextColor() != null) {
                        tabLayout.setTabTextColors(Color.parseColor(activity.getTabStrip().getTabsTextColor()), Color.parseColor(activity.getTabStrip().getSelectedTabTextColor()));
                    } else {
                        tabLayout.setTabTextColors(Color.parseColor(activity.getTabStrip().getTabsTextColor()), Color.WHITE);
                    }

                }
                if (tabs.getTabsIndicateColor() != null) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor(activity.getTabStrip().getTabsIndicateColor()));
                }
                if (tabs.getIndicatorHeight() != -1) {
                    tabLayout.setSelectedTabIndicatorHeight(tabs.getIndicatorHeight());
                }
                if (tabs.isReduceTabHeight()) {
                    float heightDp = getResources().getDisplayMetrics().heightPixels * 0.32F;
                    CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                    //  System.out.println("TOOLBARTAMANO:"+heightDp);
                    lp.height = (int) heightDp;
                }

            }
        }else{
            Log.e(TAG, "TablLayout null");
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

                        if(collapsingToolbarLayout!=null) collapsingToolbarLayout.setFitsSystemWindows(true);
                        if(coordinatorLayout!=null)coordinatorLayout.setFitsSystemWindows(true);
                        if(mToolbar!=null)mToolbar.setFitsSystemWindows(true);
                        if(mDrawerLayout!=null)mDrawerLayout.setFitsSystemWindows(true);
                        if(appBarLayout!=null)appBarLayout.setFitsSystemWindows(true);

                        //todo: provisory...
                        View v = getToolbar();
                        v.getLayoutParams().height = v.getLayoutParams().height+36;
                        v.setPadding(v.getPaddingLeft(),v.getPaddingTop()+36,v.getPaddingRight(),v.getPaddingBottom());
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
                    //window.setBackgroundDrawable(FermatDrawable.createFromStream(inputStream, null));
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
                    Toast.makeText(this, "out of memory exception", LENGTH_SHORT).show();
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
                    //window.setBackgroundDrawable(FermatDrawable.createFromStream(inputStream, null));
                } catch (Exception e) {
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                    Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                }
            }
        }
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

            //TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
            Toast.makeText(getApplicationContext(), "Recovering from system error",
                    LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    protected void removecallbacks() {
        try {

            if(adapter!=null) {
                adapter.destroyCurrentFragments();
                this.adapter = null;
            }

            paintStatusBar(null);
            if (navigation_recycler_view != null) {
                navigation_recycler_view.removeAllViews();
                navigation_recycler_view.removeAllViewsInLayout();
            }

            if (navigationView != null) {
                navigationView.removeAllViews();
                navigationView.removeAllViewsInLayout();

            }

            Fragment[] fragments = null;

            elementsWithAnimation = new ArrayList<>();
            if(bottomNavigation!=null) {
                bottomNavigation.reset();
                bottomNavigation = null;
            }

            if(pagertabs!=null) {
                pagertabs.removeAllViews();
                pagertabs=null;
            }


//            this.screenPagerAdapter = new ScreenPagerAdapter(getFragmentManager(), fragments);

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

            Toast.makeText(getApplicationContext(), "Recovering from system error",
                    LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Initialise the fragments to be paged
     */
    protected void initialisePaging() {

        try {
            List<Fragment> fragments = new Vector<>();

            DesktopRuntimeManager desktopRuntimeManager = getDesktopRuntimeManager();

            Fragment[] fragmentsArray = new AbstractFermatFragment[3];

            for (DesktopObject desktopObject : desktopRuntimeManager.listDesktops().values()) {
                //TODO: este switch se cambiara por un FragmentFactory en algún momento al igual que el Activity factory
                switch (desktopObject.getType()) {
                    case "DCCP":
                        //por ahora va esto
                        AppConnections appConnections = FermatAppConnectionManager.getFermatAppConnection(desktopObject.getPublicKey(), this);
                            //WalletDesktopFragment walletDesktopFragment = WalletDesktopFragment.newInstance(0, manager);

//                            DesktopFragment desktopFragment = DesktopFragment.newInstance((WalletManagerModule) manager);

                        //String type = desktopObject.getLastActivity().getLastFragment().getType();

                        FermatFragment fermatFragment = desktopObject.getLastActivity().getFragment(DesktopFragmentsEnumType.DESKTOP_MAIN.getKey());
                        String type = fermatFragment.getType();

                        fragmentsArray[1] = appConnections.getFragmentFactory().getFragment(
                                type,
                                createOrOpenApp(getDesktopManager()),
                                null,
                                fermatFragment
                        );

                        fermatFragment = desktopObject.getLastActivity().getFragment(DesktopFragmentsEnumType.DESKTOP_P2P_MAIN.getKey());
                        type = fermatFragment.getType();



                        fragmentsArray[0] = appConnections.getFragmentFactory().getFragment(
                                type,
                                createOrOpenApp(getDesktopManager()),
                                null,
                                fermatFragment
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

//                            fermatFragment = desktopObject.getLastActivity().getFragment(DesktopFragmentsEnumType.DESKTOP_TOOLS.getKey());
                        break;

                }
            }
            adapter = new TabsPagerAdapter2(getFragmentManager(), fragmentsArray);

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
            pagertabs.setAdapter(this.adapter);

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
                if(FermatFramework.applicationState==FermatFramework.STATE_STARTED) {
                    //FermatDrawable d = FermatDrawable.createFromStream(getAssets().open("drawables/mdpi.jpg"), null);
                    //getWindow().setBackgroundDrawable(FermatDrawable.createFromStream(getAssets().open("drawables/mdpi.jpg"), null));
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

                    FermatApplication.getInstance().changeApplicationState(FermatFramework.STATE_STARTED_DESKTOP);
                }
            }


        } catch (Exception ex) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(ex));
            Toast.makeText(getApplicationContext(), "Recovering from system error", LENGTH_SHORT).show();
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
            String publicKey = null;
            if (bundle != null) {
                if (bundle.containsKey(ApplicationConstants.INSTALLED_FERMAT_APP)) {
                    fermatApp = ((FermatApp) bundle.getSerializable(ApplicationConstants.INSTALLED_FERMAT_APP));
                } else if (bundle.containsKey(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY)) {
                    publicKey = bundle.getString(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY);
                }
                if (fermatApp == null) {
                    fermatApp = FermatApplication.getInstance().getAppManager().getApp(publicKey);
                }
                if (bundle.containsKey(ApplicationConstants.ACTIVITY_CODE_TO_OPEN)) {
                    String activityCode = bundle.getString(ApplicationConstants.ACTIVITY_CODE_TO_OPEN);
                    if (activityCode != null)
                        try {
                            FermatApplication.getInstance().getAppManager().getAppStructure(fermatApp.getAppPublicKey()).getActivity(Activities.valueOf(activityCode));
                        } catch (IllegalArgumentException e) {
                            Log.e(TAG,"Error: illegalArgumentException, Activity code: "+activityCode+" not founded in App: "+fermatApp.getAppName());
                        }
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
    private FermatSession createOrOpenApp(FermatApp fermatApp) throws Exception {
        FermatSession fermatSession = null;
        FermatAppsManager fermatAppsManager = FermatApplication.getInstance().getAppManager();
        if(fermatAppsManager.isAppOpen(fermatApp.getAppPublicKey())){
            fermatSession = fermatAppsManager.getAppsSession(fermatApp.getAppPublicKey());
        }else{
            AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(fermatApp.getAppPublicKey(), this);
            fermatSession = fermatAppsManager.openApp(fermatApp,fermatAppConnection);
        }
        return fermatSession;
    }

    /**
     *  Method used to get a Session
     *
     * @param appPublicKey
     * @return
     * @throws Exception
     */
    protected FermatSession createOrOpenSession(String appPublicKey) throws Exception {
        FermatSession fermatSession = null;
        FermatAppsManager fermatAppsManager = FermatApplication.getInstance().getAppManager();
        if(fermatAppsManager.isAppOpen(appPublicKey)){
            fermatSession = fermatAppsManager.getAppsSession(appPublicKey);
        }else{
            AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(appPublicKey, this);
            fermatSession = fermatAppsManager.openApp(fermatAppsManager.getApp(appPublicKey),fermatAppConnection);
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

            /**
             * stop every service
             */
            //FermatApplication.getInstance().getServicesHelpers().unbindServices();

            resetThisActivity();

            try {
                unregisterReceiver(updateViewReceiver);
                updateViewReceiver.clear();
            }catch (Exception e){
                //nothing
            }
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

    @Override
    public void goHome() {
        try {
            FermatApplication.getInstance().getApplicationManager().openFermatHome();
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
                    FermatStructure fermatStructure = FermatApplication.getInstance().getAppManager().getLastAppStructure();
                    final Activity activity = fermatStructure.getLastActivity();
                    FermatSession session = FermatApplication.getInstance().getAppManager().getAppsSession(fermatStructure.getPublicKey());
                    final AppConnections appsConnections = FermatAppConnectionManager.getFermatAppConnection(fermatStructure.getPublicKey(), getApplicationContext(), session);
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

    protected void refreshSideMenu(final AppConnections appConnections){
        try {
            if (!(this instanceof DesktopActivity)) {
                final FermatStructure fermatStructure = FermatApplication.getInstance().getAppManager().getLastAppStructure();
                final NavigationViewPainter viewPainter = appConnections.getNavigationViewPainter();
                if (viewPainter != null) {
                    final FermatAdapter mAdapter = viewPainter.addNavigationViewAdapter();
                    Activity activity = fermatStructure.getLastActivity();
                    if (activity != null) {
                        SideMenu sideMenu = activity.getSideMenu();
                        List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> lstItems = null;
                        if (sideMenu != null) lstItems = sideMenu.getMenuItems();
                        final List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> finalLstItems = (lstItems != null) ? lstItems : new ArrayList<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem>();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                FrameLayout frameLayout = SideMenuBuilder.setHeader(FermatActivity.this, viewPainter);
                                try {
                                    SideMenuBuilder.setAdapter(
                                            navigation_recycler_view,
                                            mAdapter,
                                            viewPainter.addItemDecoration(),
                                            finalLstItems,
                                            FermatActivity.this,
                                            fermatStructure.getLastActivity().getActivityType()
                                    );
                                } catch (InvalidParameterException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        Log.e(TAG, "ActivityObject null, line:" + new Throwable().getStackTrace()[0].getLineNumber());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void onBackPressedNotificate(){
        if(getAdapter()!=null) {
            List<AbstractFermatFragmentInterface> list = getAdapter().getLstCurrentFragments();
            for (AbstractFermatFragmentInterface abstractFermatFragment : list) {
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
//        if (item.getId() == android.support.v7.appcompat.R.id.home) {
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
        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onNavigationMenuItemTouchListener(data, position);
            }
        }, DRAWER_CLOSE_DELAY_MS);
        FermatApplication.getInstance().getAppManager().clearRuntime();

    }

    @Override
    public void onLongItemClickListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {

    }
    @Override
    public void setActivityBackgroundColor(Drawable drawable){
        if(drawable!=null) pagertabs.setBackground(drawable);

    }

    public void addCollapseAnimation(ElementsWithAnimation elementsWithAnimation) {
        this.elementsWithAnimation.add(elementsWithAnimation);
    }

    public void removeCollapseAnimation(ElementsWithAnimation elementsWithAnimation){
        this.elementsWithAnimation.remove(elementsWithAnimation);
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

    /**
     * Abstract methods
     */

    public abstract void changeActivity(String activityName,String appBackPublicKey, Object... objects);

    /**
     * Report error
     */
    public void reportError(String mailUserTo) throws Exception {
//        YourOwnSender yourOwnSender = new YourOwnSender(this);
//        String log = LogReader.getLog().toString();
//        yourOwnSender.send(mailUserTo,log );
        LogReader.getLog(this, mailUserTo);
      //  AndroidExternalAppsIntentHelper.sendMail(this,new String[]{mailUserTo},"Error report",log);


    }

    //send mail

    public void sendMailExternal(String mailUserTo, String bodyText) throws Exception {
        YourOwnSender yourOwnSender = new YourOwnSender(this);
        yourOwnSender.sendPrivateKey(mailUserTo, bodyText);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(broadcastManager!=null)broadcastManager.stop();
//        networkStateReceiver.removeListener(this);
    }

    @Override
    public void selectApp(String appPublicKey) throws Exception {
        try{
            FermatApplication.getInstance().getApplicationManager().openFermatApp(appPublicKey);
        }catch (Exception e){
            throw new Exception("Error calling openFermatApp in ReferenceAppFermatSession");
        }
    }


    public void openRecentsScreen(){
//        Intent resultIntent = new Intent(getApplicationContext(),RecentsActivity.class);
//        // TODO Add extras or a data URI to this intent as appropriate.
//        setResult(android.app.Activity.RESULT_OK, resultIntent);
//        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        resultIntent.putExtra(ApplicationConstants.RECENT_APPS, FermatApplication.getInstance().getAppManager().getRecentsAppsStack().toArray());
//        startActivityForResult(resultIntent, TASK_MANAGER_STACK);
        FermatApplication.getInstance().getApplicationManager().openRecentsScreen();
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
//        switch(requestCode) {
//            case (TASK_MANAGER_STACK) : {
//                if (resultCode == android.app.Activity.RESULT_OK) {
//                    // TODO Extract the data returned from the child Activity. and open the app
//                    data.setAction("org.fermat.APP_LAUNCHER");
//                    sendBroadcast(data);
//                    //finish();
//                } else if(resultCode == android.app.Activity.RESULT_CANCELED){
//                    // if i want i could do something here
//                }
//                break;
//            }
//        }
    }

    protected void handleExceptionAndRestart(){
        if(BuildConfig.DEBUG){
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Error occur");
            alert.setMessage("Please enter the developer email");
            final EditText email = new EditText(this);
            email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            email.setHint("Email...");
            alert.setView(email);
            alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        reportError(email.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(FermatActivity.this, "Error sending the report", LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

            try {
                alert.setNegativeButton("Cancel", null);
//                alert.show();

                }catch (Exception e){
                    e.printStackTrace();
                }

        }
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public FermatRuntime getRuntimeManager(){
        return runtimeStructureManager;
    }

    public ViewPager getPagertabs() {
        return pagertabs;
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

    public FermatScreenAdapter getAdapter() {
        return adapter;
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


    @Override
    public void cancelNotification(String appPublicKey) {
        FermatApplication.getInstance().getNotificationService().cancelNotification(appPublicKey);
    }

    @Override
    public void pushNotification(String appPublicKey,Notification notification) {
        FermatApplication.getInstance().getNotificationService().pushNotification(appPublicKey,notification);
    }

    /**
     * Framework Helpers
     */
    @Override
    public int obtainRes(int id, SourceLocation sourceLocation, String appOwnerPublicKey) {
        return ResourceLocationSearcherHelper.obtainRes(this, id, sourceLocation, appOwnerPublicKey);
    }

    @Override
    public View obtainClassView(int id, SourceLocation sourceLocation, String appOwnerPublicKey) {
        return ResourceLocationSearcherHelper.obtainView(this, id, sourceLocation, appOwnerPublicKey);
    }

    @Override
    //todo: ampliar esto a mayor cantidad de clases
    public View obtainFrameworkOptionMenuClassViewAvailable(int id, SourceLocation sourceLocation) {
        return OptionMenuFrameworkHelper.obtainFrameworkAvailableOptionMenuItems(this, id);
    }

    @Override
    public View obtainFrameworkOptionMenuClassViewAvailable(int id, SourceLocation sourceLocation, Object[] listeners) {
        return OptionMenuFrameworkHelper.obtainFrameworkAvailableOptionMenuItems(this, id,listeners);
    }

    /**
     *  Method to change the visibility of an optionMenu
     *  todo: poner esto en otra clase
     * @param id
     * @param isVisible
     * @param appPublicKey
     * @throws InvalidParameterException
     */
    public void changeOptionMenuVisibility(int id,boolean isVisible,String appPublicKey) throws InvalidParameterException {
        Activity activity = FermatApplication.getInstance().getAppManager().getAppStructure(appPublicKey).getLastActivity();
        OptionsMenu optionMenu = activity.getOptionsMenu();
        if(optionMenu!=null){
            optionMenu.getItem(id).setVisibility(isVisible);
            getToolbar().getMenu().findItem(id).setVisible(isVisible);
        }else{
            throw new InvalidParameterException("OptionMenu in activity: "+activity.getType().getCode()+" in app: "+appPublicKey);
        }
    }


}
