package com.bitdubai.android_core.app.common.version_1;


import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bitdubai.android_core.app.common.version_1.bottom_navigation.BottomNavigation;
import com.bitdubai.android_core.app.common.version_1.classes.BroadcastManager;
import com.bitdubai.android_core.app.common.version_1.runtime_estructure_manager.RuntimeStructureManager;
import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.11.10..
 */
public abstract class FermatActivityBase extends AppCompatActivity {

    private static final String TAG = "fermat-core";
    public static final int TASK_MANAGER_STACK = 100;


    /**
     * Manager
     */
    BroadcastManager broadcastManager;
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

    private Activity activeActivity;




}
