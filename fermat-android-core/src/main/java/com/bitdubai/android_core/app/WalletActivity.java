package com.bitdubai.android_core.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import com.bitdubai.android_core.app.common.version_1.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;
import com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Activity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.MainMenu;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SideMenu;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.StatusBar;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.TabStrip;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.TitleBar;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import com.bitdubai.fermat.R;

/**
 * Created by natalia on 22/06/factory.
 * Reformated by Matias
 */
public class WalletActivity extends FragmentActivity{

    private NavigationDrawerFragment NavigationDrawerFragment;
    private TabsPagerAdapter adapter;

    /**
     *  Called when the activity is first created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {

            /*
            * Load wallet UI
            */
            loadUI();

        } catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Error loading the UI - " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu
     * @param menu
     * @return true if all is okey
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            MenuInflater inflater = getMenuInflater();

            /**
             *  Our future code goes here...
             */

        }
        catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Error in create optionMenu " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        return super.onCreateOptionsMenu(menu);

    }


    /**
     * This hook is called whenever an item in your options menu is selected.
     * @param item
     * @return true if button is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        try {

            int id = item.getItemId();

            /**
             *  Our future code goes here...
             */


        }
        catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, FermatException.wrapException(e));

            Toast.makeText(getApplicationContext(), "Error in OptionsItemSelected " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     *  Called to retrieve per-instance state from an activity before being killed so that the state can be restored in onCreate(Bundle) or onRestoreInstanceState(Bundle)
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    /**
     * This method is called after onStart() when the activity is being re-initialized from a previously saved state, given here in savedInstanceState.
     * Most implementations will simply use onCreate(Bundle) to restore their state, but it is sometimes convenient to do it here after all of the initialization has been done or to allow subclasses to decide whether to use your default implementation
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    /**
     * Method call when back button is pressed
     */
    @Override
    public void onBackPressed() {


        if (ApplicationSession.getwalletRuntime().getLasActivity().getType() != Activities.CWP_WALLET_MANAGER_MAIN){

            resetThisActivity();

            Intent intent = new Intent(this, SubAppActivity.class); // TODO : (LUIS) no puede irse a una sub app

            startActivity(intent);
        }else{
            super.onBackPressed();
        }


    }


    /**
     * Method that loads the UI
     */
    private void loadUI() {

        try
        {
            /**
             * Get current activity to paint
             */
            Activity activity =  ApplicationSession.getwalletRuntime().getLasActivity();
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
             * Get the title
             */
            TextView abTitle = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", "android")); // TODO (Luis) esto debe ir dentro del TitleBar

            /**
             * Pick the layout
             */
            setMainLayout(sideMenu);

            /**
             * Paint tabs in layout
             */
            PagerSlidingTabStrip pagerSlidingTabStrip=((PagerSlidingTabStrip) findViewById(R.id.tabs));
            paintTabs(tabs,pagerSlidingTabStrip,activity);

            /**
             * Paint statusBar
             */
            paintStatusBar(activity.getStatusBar());

            /**
             * Set activities properties
             */
            ApplicationSession.setActivityProperties(this, getWindow(), getResources(), null, getActionBar(), titleBar, abTitle, "Titulo"); // TODO dividirlo en los paints que tenemos, si algo no encaja ver si hace falta otro paint.

            /**
             * Paint a simgle fragment
             */
            if(tabs == null && fragments.size() > 1){
                Toast.makeText(getApplicationContext(),"Error unico fragmento",Toast.LENGTH_SHORT).show(); // TODO Arreglar esta vaina.
                Log.e(getClass().getSimpleName(),"wallets con un fragmento no pensadas");

            }else{
                /**
                 * Paint tabs
                 */
                setPagerTabs(pagerSlidingTabStrip);
                /**
                 * Paint color of the activity
                 */
                String color = activity.getColor();
                if (color != null) ((ApplicationSession) this.getApplication()).changeColor(Color.parseColor(color), getResources());
            }
        }
        catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Error in loadUI " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    /**
     *
     * @param pagerSlidingTabStrip
     */
    private void setPagerTabs(PagerSlidingTabStrip pagerSlidingTabStrip){
        /**
         * Get pagerTabs
         */
        ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
        pagertabs.setVisibility(View.VISIBLE);

        /**
         * Making the pagerTab adapter
         */
        adapter = new TabsPagerAdapter(getSupportFragmentManager(),getApplicationContext());
        pagertabs.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pagertabs.setPageMargin(pageMargin);
        /**
         * Put tabs in pagerSlidingTabsStrp
         */
        pagerSlidingTabStrip.setViewPager(pagertabs);
    }

    /**
     *
     * @param sidemenu
     */
    private void setMainLayout(SideMenu sidemenu){
        if(sidemenu != null){
            setContentView(R.layout.runtime_app_wallet_runtime_navigator);
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
            setContentView(R.layout.runtime_app_wallet_runtime);
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

        }

        // paint tabs color
        if (activity.getTabStrip().getTabsColor()!=null){
            pagerSlidingTabStrip.setBackgroundColor(Color.parseColor(activity.getTabStrip().getTabsColor()));
            //tabStrip.setDividerColor(Color.TRANSPARENT);
        }

        // paint tabs text color
        if(activity.getTabStrip().getTabsTextColor()!=null){
            pagerSlidingTabStrip.setTextColor(Color.parseColor(activity.getTabStrip().getTabsTextColor()));
        }

        //paint tabs indicate color
        if(activity.getTabStrip().getTabsIndicateColor()!=null){
            pagerSlidingTabStrip.setIndicatorColor(Color.parseColor(activity.getTabStrip().getTabsIndicateColor()));
        }

        // put tabs font
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/CaviarDreams.ttf");
        if (pagerSlidingTabStrip != null){
            pagerSlidingTabStrip.setTypeface(tf, 1);
        }
    }

    /**
     * Method to set status bar color in different version of android
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void paintStatusBar(StatusBar statusBar){
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
                        ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                        Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                    }
                }
            }
        }
    }

    /**
     *  Method used to clean the screen
     */

    private void resetThisActivity()
    {
        try
        {
            //clean page adapter
            ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
            if(adapter != null) pagertabs.removeAllViews();

            ViewPager viewpager = (ViewPager)super.findViewById(R.id.viewpager);
            viewpager.setVisibility(View.INVISIBLE);
            ViewPager pager = (ViewPager)super.findViewById(R.id.pager);
            pager.setVisibility(View.INVISIBLE);

            if(NavigationDrawerFragment!= null)
            {
                this.NavigationDrawerFragment.setMenuVisibility(false);
                NavigationDrawerFragment = null;
            }

            this.adapter = null;
            //this.tabStrip=null;
            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();

        }
        catch (Exception e) {

            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, FermatException.wrapException(e));

            Toast.makeText(getApplicationContext(), "Error in CleanWindows " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
