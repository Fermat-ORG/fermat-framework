package com.bitdubai.android_core.app;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bitdubai.android_core.app.common.version_1.classes.MyTypefaceSpan;
import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;

import com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment;

import com.bitdubai.android_core.app.common.PagerAdapter;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.*;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ReceiveFragment;

import com.bitdubai.sub_app.developer.common.ArrayListLoggers;
import com.bitdubai.sub_app.developer.common.Resource;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsDatabaseListFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsDatabaseTableListFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsDatabaseTableRecordListFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsFragment;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragment;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragmentLevel1;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragmentLevel2;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragmentLevel3;
import com.bitdubai.sub_app.manager.fragment.SubAppDesktopFragment;


import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.MainFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.WalletDesktopFragment;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Fragments;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat.R;


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
public class SubAppActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,ScreenSwapper {

    private NavigationDrawerFragment NavigationDrawerFragment;

    private PagerAdapter PagerAdapter;
    private ViewPager pagertabs;
    private MyPagerAdapter adapter;

    private com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Fragment fragment;
    private String actionKey;

    private Object[] screenObjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {


            NavigateActivity();
        } catch (Exception e) {
            // TODO: el errorManager no estaria instanciado aca....
            ApplicationSession.errorManager.reportUnexpectedSubAppException(ApplicationSession.appRuntimeMiddleware.getLastSubApp().getType(), UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));

            //this.errorManage.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
            //ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
            Toast.makeText(getApplicationContext(), "Error Load RuntimeApp - " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        try {
            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
            Iterator<Map.Entry<Fragments, Fragment>> efragments = ApplicationSession.appRuntimeMiddleware.getLasActivity().getFragments().entrySet().iterator();
            boolean flag = false;
            while (efragments.hasNext()) {
                Map.Entry<Fragments, Fragment> fragmentEntry = efragments.next();

                Fragment fragment = (Fragment) fragmentEntry.getValue();
                Fragments type = fragment.getType();

                switch (type) {
                    case CWP_SHELL_LOGIN:
                        break;
                    case CWP_WALLET_MANAGER_MAIN:
                            fragments.add(android.support.v4.app.Fragment.instantiate(this, WalletDesktopFragment.class.getName()));
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
            ApplicationSession.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, ex);
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
            Activity activity = ApplicationSession.appRuntimeMiddleware.getLasActivity();

            /**
             * Get tabs to paint
             */
            TabStrip tabs = activity.getTabStrip();
            /**
             * Get activities fragment
             */
            Map<Fragments, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment> fragments =activity.getFragments();
            /**
             * get actionBar to paint
             */
            TitleBar titleBar = activity.getTitleBar();
            /**
             * Get mainMenu to paint
             */
            MainMenu mainMenu= activity.getMainMenu();
            /**
             * Get NavigationDrawer to paint
             */
            SideMenu sideMenu = activity.getSideMenu();


            /**
             * Pick the layout
             */
            setMainLayout(sideMenu);


            /**
             * Paint tabs in layout
             */
            PagerSlidingTabStrip pagerSlidingTabStrip=((PagerSlidingTabStrip) findViewById(R.id.tabs));
            paintTabs(tabs, pagerSlidingTabStrip, activity);


            setStatusBarColor(activity.getStatusBar());


            paintTitleBar(titleBar);

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
                PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
                tabStrip.setViewPager(pagertabs);



                String color = activity.getColor();
                //if (color != null)
                    //((ApplicationSession) this.getApplication()).changeColor(Color.parseColor(color), getResources(),getActionBar());

            }
        } catch (Exception e) {
            //TODO: Error manager is null in this point
           // ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Error in NavigateActivity " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     *
     * @param titleBar
     */
    private void paintTitleBar(TitleBar titleBar){
        try {
            TextView abTitle = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", "android"));
            if (titleBar != null) {

                String title = titleBar.getLabel();

                if (abTitle != null) {
                    abTitle.setTextColor(Color.WHITE);
                    abTitle.setTypeface(ApplicationSession.getDefaultTypeface());
                }
                getActionBar().setTitle(title);
                getActionBar().show();
                setActionBarProperties(abTitle,title);
            } else {
                getActionBar().hide();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param abTitle
     * @param title
     */
    private void setActionBarProperties(TextView abTitle, String title){
        SpannableString s = new SpannableString(title);


        s.setSpan(new MyTypefaceSpan(getApplicationContext(), "CaviarDreams.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        getActionBar().setTitle(s);

        // actionBar
        Drawable bg = getResources().getDrawable(R.drawable.transparent);
        bg.setVisible(false, false);
        Drawable wallpaper = getResources().getDrawable(R.drawable.transparent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(Color.parseColor(ApplicationSession.appRuntimeMiddleware.getLasActivity().getColor()));
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //ld.setCallback(drawableCallback);
            } else {
                getActionBar().setBackgroundDrawable(ld);
            }
        }
    }

    /**
     *
     * @param tabs
     * @param pagerSlidingTabStrip
     * @param activity
     */
    private void paintTabs(TabStrip tabs,PagerSlidingTabStrip pagerSlidingTabStrip,Activity activity){
        if(tabs == null)
            pagerSlidingTabStrip.setVisibility(View.INVISIBLE);
        else{
            pagerSlidingTabStrip.setVisibility(View.VISIBLE);
            Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/CaviarDreams.ttf");
            pagerSlidingTabStrip.setTypeface(tf,1 );
            pagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);

            // paint tabs color
            if (tabs.getTabsColor()!=null){
                pagerSlidingTabStrip.setBackgroundColor(Color.parseColor(activity.getTabStrip().getTabsColor()));
                //tabStrip.setDividerColor(Color.TRANSPARENT);
            }

            // paint tabs text color
            if(tabs.getTabsTextColor()!=null){
                pagerSlidingTabStrip.setTextColor(Color.parseColor(activity.getTabStrip().getTabsTextColor()));
            }

            //paint tabs indicate color
            if(tabs.getTabsIndicateColor()!=null){
                pagerSlidingTabStrip.setIndicatorColor(Color.parseColor(activity.getTabStrip().getTabsIndicateColor()));
            }
        }



        // put tabs font
        if (pagerSlidingTabStrip != null){
            pagerSlidingTabStrip.setTypeface(ApplicationSession.mDefaultTypeface, 1);
        }
    }

    /**
     *
     * @param sidemenu
     */
    private void setMainLayout(SideMenu sidemenu){
        if(sidemenu != null){
            setContentView(R.layout.runtime_app_activity_runtime_navigator);
            NavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

            NavigationDrawerFragment.setMenuVisibility(true);
            /**
             * Set up the navigationDrawer
             */
            NavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout), sidemenu);
        }

        /**
         * Paint layout without navigationDrawer
         */
        else{
            setContentView(R.layout.runtime_app_activity_runtime);
        }
    }


    /**
     * This method replaces the current fragment by the next in navigation
     * @param fragmentType Type Id of fragment to show
     */
        private void loadFragment(Fragments fragmentType)
        {
            FragmentTransaction transaction;
            switch (fragmentType) {

                //developer app fragments
                case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS:
                    this.fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS);

                      DatabaseToolsFragment frag= DatabaseToolsFragment.newInstance(0);
                        //set data pass to fragment
                        this.fragment.setContext(screenObjects);

                         transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.databasecontainer, frag);
                        // Commit the transaction
                        transaction.commit();

                    break;

                case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES:
                    this.fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES);


                        DatabaseToolsDatabaseListFragment fragd= DatabaseToolsDatabaseListFragment.newInstance(0);
                        fragd.setResource((Resource)screenObjects[0]);
                        //set data pass to fragment
                        this.fragment.setContext(screenObjects);

                         transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.startContainer, fragd);
                        // Commit the transaction
                        transaction.commit();


                    break;
                case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_TABLES:

                    this.fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_TABLES);

                        DatabaseToolsDatabaseTableListFragment fragt= DatabaseToolsDatabaseTableListFragment.newInstance(0);

                        //set data pass to fragment
                        this.fragment.setContext(screenObjects);
                        fragt.setResource((Resource) screenObjects[0]);
                        fragt.setDeveloperDatabase((DeveloperDatabase) screenObjects[1]);

                         transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.startContainer, fragt);
                        // Commit the transaction
                        transaction.commit();

                    break;
                case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_RECORDS:

                    this.fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_RECORDS);

                    DatabaseToolsDatabaseTableRecordListFragment fragr= DatabaseToolsDatabaseTableRecordListFragment.newInstance(0);


                    //set data pass to fragment
                    this.fragment.setContext(screenObjects);
                    fragr.setResource((Resource) screenObjects[0]);
                    fragr.setDeveloperDatabase((DeveloperDatabase) screenObjects[1]);
                    fragr.setDeveloperDatabaseTable((DeveloperDatabaseTable) screenObjects[2]);

                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.startContainer, fragr);
                    // Commit the transaction
                    transaction.commit();
                    break;

                case CWP_SUB_APP_DEVELOPER_LOG_TOOLS:
                    this.fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_TOOLS);

                        LogToolsFragment frag1= LogToolsFragment.newInstance(0);

                        //set data pass to fragment
                        this.fragment.setContext(screenObjects);

                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.logContainer, frag1);
                        // Commit the transaction
                        transaction1.commit();
                    break;

                case CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS:

                    this.fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS);

                    LogToolsFragmentLevel1 fragl= LogToolsFragmentLevel1.newInstance(0);
                    fragl.setLoggers((ArrayListLoggers) screenObjects[0]);
                    //set data pass to fragment
                    this.fragment.setContext(screenObjects);

                     transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.logContainer, fragl);
                    // Commit the transaction
                    transaction.commit();
                    break;

                case CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS:

                    this.fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS);

                       LogToolsFragmentLevel2 fragl2= LogToolsFragmentLevel2.newInstance(0);
                    fragl2.setLoggers((ArrayListLoggers) screenObjects[0]);
                    fragl2.setLoggerLevel((int)screenObjects[1]);

                        //set data pass to fragment
                        this.fragment.setContext(screenObjects);

                       transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.logContainer, fragl2);
                        // Commit the transaction
                        transaction.commit();
                    break;

                case CWP_SUB_APP_DEVELOPER_LOG_LEVEL_3_TOOLS:

                    this.fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS);

                    LogToolsFragmentLevel3 fragl3= LogToolsFragmentLevel3.newInstance(0);
                    fragl3.setLoggers((ArrayListLoggers) screenObjects[0]);
                    fragl3.setLoggerLevel((int) screenObjects[1]);
                    //set data pass to fragment
                    this.fragment.setContext(screenObjects);

                  transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.logContainer, fragl3);
                    // Commit the transaction
                    transaction.commit();
                    break;


            }
        }

    // TODO: Aca dependiendo del tipo de activity se esta inflando uno u otro menu. Esto esta lejos de ser como debe. Se supone que en el plugin WalletRuntime o AppRuntime están definidos los menues para cada actividad y es en base a eso que se debe crear los menues.
    // TODO: Si no es posible, como aparentemente no lo es inflar un menu de manera programatica sin basarse en un layout, lo que deberiamos tener es en todo caso un conjunto de layouts que sirvan de templates
    // TODO: Por ejemplo, un layout con un item, otro con dos items, otro con tres y asi, si el problema es definir dinamicamente la cantidad.
    // TODO: Dicho template debiera tener ya incorporado los iconos (con un PNG invisible) que luego se pueda reemplazar desde el codigo.
    // TODO: En definitiva, tenemos que llegar al punto de que la parametrización este en el plugin WalletRuntime y APPRuntime y solo ahi.


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater inflater = getMenuInflater();


            /*switch (this.activity.getType()) {

                case CWP_SHELL_LOGIN:
                    break;
                case CWP_SHOP_MANAGER_MAIN:
                         break;
                case CWP_WALLET_MANAGER_MAIN:
                    break;

                case CWP_WALLET_STORE_MAIN:
                    break;


            }*/

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
                //((ApplicationSession) this.getApplication()).setWalletId(0);
                ApplicationSession.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_STORE_MAIN);
                //NavigateActivity();

                return true;
            }


            if (id == R.id.action_file) {
                return true;
            }





        } catch (Exception e) {
            ApplicationSession.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
            //Matias
            //por ahora lo saco porque no me toma el tag del parametro, vamos a ver como implementarlo
            //Toast.makeText(getApplicationContext(), "Error in OptionsItemSelecte " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
    /**
     * Method to set status bar color in different version of android
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(StatusBar statusBar){
        if(statusBar!=null) {
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
                        ApplicationSession.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                        Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                    }
                }
            }
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
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    /**
     * This method catch de back bottom event and decide which screen load
     */
    @Override
    public void onBackPressed() {

        // get actual fragment on execute
        Fragments frgBackType = null;
        try {
            AppRuntimeManager appRuntimeManager = ApplicationSession.appRuntimeMiddleware;
            this.fragment = appRuntimeManager.getLastFragment();
            //get setting fragment to back
            //if not fragment to back I back to desktop
             frgBackType = this.fragment.getBack();

        }catch (Exception e){
            e.printStackTrace();
        }



        if(frgBackType != null){

            com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Fragment fragmentBack = ApplicationSession.appRuntimeMiddleware.getFragment(frgBackType); //set back fragment to actual fragment to run

            //I get string context with params pass to fragment to return with this data
            ApplicationSession.mParams=fragmentBack.getContext();

            this.loadFragment(frgBackType);
        }
        else{
            // set Desktop current activity
            //ApplicationSession.setActivityId("DesktopActivity");
            //((ApplicationSession) this.getApplication()).setWalletId(0);
            Activity activity=ApplicationSession.appRuntimeMiddleware.getLasActivity();
            if (activity.getType() != Activities.CWP_WALLET_MANAGER_MAIN) {
                cleanWindows();
                activity = ApplicationSession.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
                //cleanWindows();

                NavigateActivity();
            } else {
                super.onBackPressed();
            }
        }


    }

    private void cleanWindows() {
        try {
            //clean page adapter
            pagertabs = (ViewPager) findViewById(R.id.pager);
            pagertabs.removeAllViews();

            ViewPager viewpager = (ViewPager) super.findViewById(R.id.viewpager);
            viewpager.setVisibility(View.INVISIBLE);
            ViewPager pager = (ViewPager) super.findViewById(R.id.pager);
            pager.setVisibility(View.INVISIBLE);
            if (NavigationDrawerFragment != null)
                this.NavigationDrawerFragment.setMenuVisibility(false);
            NavigationDrawerFragment = null;
            this.PagerAdapter = null;
            this.adapter = null;
            this.pagertabs = null;

            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();

            this.PagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        } catch (Exception e) {
            ApplicationSession.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Can't Clean Windows: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }


    /**
     * ScreenSwapper interface implementation
     */


    /**
     * This Method execute the navigation to an other fragment or activity
     * Get button action of screen
     */
    @Override
    public void changeScreen() {

        try {

            Intent intent;

            Activities activityType = Activities.getValueFromString(this.actionKey);

            //if activity type is null I execute a fragment, get fragment type

            if(activityType != null)
            {
                //Clean all object from the previous activity
                cleanWindows();

                switch (activityType) {


                    case CWP_SUP_APP_ALL_DEVELOPER: //Developer manager

                        ApplicationSession.appRuntimeMiddleware.getActivity(Activities.CWP_SUP_APP_ALL_DEVELOPER);

                        intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        break;

                    case CWP_WALLET_BASIC_ALL_MAIN: //basic Wallet
                        //go to wallet basic definition
                        //ApplicationSession.setWalletId(4);
                        ApplicationSession.walletRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
                        intent = new Intent(this, com.bitdubai.android_core.app.WalletActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    //wallet factory
                    case CWP_WALLET_FACTORY_MAIN:

                        ApplicationSession.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_FACTORY_MAIN);

                        intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        break;

                    //wallet publisher
                    case CWP_WALLET_PUBLISHER_MAIN:

                        ApplicationSession.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_PUBLISHER_MAIN);
                        intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        break;

                    case CWP_WALLET_RUNTIME_STORE_MAIN:
                        ApplicationSession.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_STORE_MAIN);
                        intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        break;
                }

            }
            else
            {

                Fragments fragmentType = Fragments.getValueFromString(actionKey);

                if(fragmentType != null)
                {
                        this.loadFragment(fragmentType);
                }
                else
                {
                    ApplicationSession.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, new IllegalArgumentException("the given number doesn't match any Status."));

                }

            }


        } catch (Exception e) {
            //TODO: error manager is null in this point
           // ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
            //Esto va a habr que cambiarlo porque no me toma el tag, Matias
            Toast.makeText(getApplicationContext(), "Error in OptionsItemSelecte " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void setScreen(String screen){
      this.actionKey = screen;
    }

    /**
     * This method set de params to pass to screens
     * @param objects
     */
    @Override
    public void setParams(Object[] objects){
        this.screenObjects = null;
        this.screenObjects = objects;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            TabStrip tabs= ApplicationSession.appRuntimeMiddleware.getLasActivity().getTabStrip();
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
                List<Tab> titleTabs = ApplicationSession.appRuntimeMiddleware.getLasActivity().getTabStrip().getTabs();
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

                        //developr aap
                        case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS:
                            developerPlatform = new com.bitdubai.sub_app.developer.fragment.Platform();
                            developerPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                            developerPlatform.setToolManager((ToolManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER));
                            currentFragment = DatabaseToolsFragment.newInstance(position);
                            break;

                        case CWP_SUB_APP_DEVELOPER_LOG_TOOLS:
                            developerPlatform = new com.bitdubai.sub_app.developer.fragment.Platform();
                            developerPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                            developerPlatform.setToolManager((ToolManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER));
                            currentFragment = LogToolsFragment.newInstance(0);
                            break;
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
                    ApplicationSession.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, ex);

                    Toast.makeText(getApplicationContext(), "Error in PagerAdapter GetItem " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                return currentFragment;
            } catch (Exception e) {
                ApplicationSession.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

                Toast.makeText(getApplicationContext(), "Can't getItem PageAdpater: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }
}
