package com.bitdubai.android_core.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.bitdubai.android_core.app.common.PagerAdapter;
import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;
import com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Activity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Fragment;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.MainMenu;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SideMenu;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Tab;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.TabStrip;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.TitleBar;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.BalanceFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactsFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.SendFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ReceiveFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.TransactionsFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.WalletDesktopFragment;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import com.bitdubai.fermat.R;

/**
 * Created by natalia on 22/06/factory.
 */
public class WalletActivity extends FragmentActivity implements com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment.NavigationDrawerCallbacks{

    private NavigationDrawerFragment NavigationDrawerFragment;
    private MyPagerAdapter adapter;
    private int currentColor = 0xFF666666;

    /**
     *  Method called when the activity is starting.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {

            //load wallet UI
            NavigateWallet();

        } catch (Exception e) {
            //System.err.println("CantStartPlatformException: " + e.getMessage());
            Log.e(getClass().getSimpleName(),"CantStartPlatformException: " + e.getMessage());
            Toast.makeText(getApplicationContext(), "Error Load RuntimeApp - " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Initialize the contents of the Activity's standard options menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            MenuInflater inflater = getMenuInflater();


            switch ( ApplicationSession.getwalletRuntime().getLasActivity().getType()) {


                case CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN:
                    inflater.inflate(R.menu.app_activity_main_empty_menu, menu);

                    break;
                case CWP_WALLET_STORE_MAIN:
                    break;


            }

        }
        catch (Exception e) {

            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Error in CleanWindows " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }



        return super.onCreateOptionsMenu(menu);



    }


    /**
     * This hook is called whenever an item in your options menu is selected.
     * @param item
     * @return
     */
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



            if (id == R.id.action_file) {
                return true;
            }






        }
        catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Error in OptionsItemSelecte " + e.getMessage(),
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
        outState.putInt("currentColor", currentColor);
    }


    /**
     * This method is called after onStart() when the activity is being re-initialized from a previously saved state, given here in savedInstanceState.
     * Most implementations will simply use onCreate(Bundle) to restore their state, but it is sometimes convenient to do it here after all of the initialization has been done or to allow subclasses to decide whether to use your default implementation
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor= savedInstanceState.getInt("currentColor");
    }


    /**
     * Method call when back button is pressed
     */
    @Override
    public void onBackPressed() {


        if (ApplicationSession.getwalletRuntime().getLasActivity().getType() != Activities.CWP_WALLET_MANAGER_MAIN){

            cleanWindows();

            Intent intent = new Intent(this, SubAppActivity.class);

            startActivity(intent);
        }else{
            super.onBackPressed();
        }


    }

    /**
     * Method implemented for manage the navigation drawer
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }


    // TODO: Este metodo se debe dividir en metodos más pequeños donde:
    // *Se obtengan los objetos a pintar
    // *Se obtengan los objetos del runtime
    // *Se le agregue lo necesario a cada uno

    /**
     * Method who load the screen from walletRuntime
     */
    private void NavigateWallet() {

        try
        {
            //get actual activity to execute
            Activity activity =  ApplicationSession.getwalletRuntime().getLasActivity();

            ApplicationSession.setActivityId(activity.getType().getKey());


            TabStrip tabs = activity.getTabStrip();
            Map<Fragments, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment> fragments =activity.getFragments();
            TitleBar titleBar = activity.getTitleBar();

            MainMenu mainMenumenu= activity.getMainMenu();

            SideMenu sidemenu = activity.getSideMenu();

            //if wallet do not set the navigator drawer I load a layout without him
            if(sidemenu != null)
            {
                setContentView(R.layout.runtime_app_wallet_runtime_navigator);
                this.NavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

                this.NavigationDrawerFragment.setMenuVisibility(true);
                // Set up the drawer.
                this.NavigationDrawerFragment.setUp(
                        R.id.navigation_drawer,
                        (DrawerLayout) findViewById(R.id.drawer_layout), sidemenu);

            }
            else
            {
                setContentView(R.layout.runtime_app_wallet_runtime);
            }

            PagerSlidingTabStrip pagerSlidingTabStrip=((PagerSlidingTabStrip) findViewById(R.id.tabs));
            if(tabs == null)
                pagerSlidingTabStrip.setVisibility(View.INVISIBLE);
            else{
                pagerSlidingTabStrip.setVisibility(View.VISIBLE);
                //pagerSlidingTabStrip.setTextColor(Color.GREEN);
                //pagerSlidingTabStrip.setBackgroundColor(Color.GREEN);

            }
            int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
            TextView abTitle = (TextView) findViewById(titleId);



            String status_color=activity.getStatusBarColor();
            if(activity.getStatusBarColor()!=null){
                setStatusBarColor(activity.getStatusBarColor());

            }
            if (activity.getTabStrip().getTabsColor()!=null){
                pagerSlidingTabStrip.setBackgroundColor(Color.parseColor(activity.getTabStrip().getTabsColor()));
                //tabStrip.setDividerColor(Color.TRANSPARENT);

            }
            if(activity.getTabStrip().getTabsTextColor()!=null){
                pagerSlidingTabStrip.setTextColor(Color.parseColor(activity.getTabStrip().getTabsTextColor()));
            }

            if(activity.getTabStrip().getTabsIndicateColor()!=null){
                pagerSlidingTabStrip.setIndicatorColor(Color.parseColor(activity.getTabStrip().getTabsIndicateColor()));
            }
            Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/CaviarDreams.ttf");
            if (pagerSlidingTabStrip != null){
                pagerSlidingTabStrip.setTypeface(tf,1 );
            }


            //TODO por ahora le estoy pasando un null al tabstrip porque ahí adentro lo está cargando mal, porque hace un clean de la pantalla y borra el color de los tabs
            // que yo puse acá
            ApplicationSession.setActivityProperties(this, getWindow(), getResources(), null, getActionBar(), titleBar, abTitle, "Titulo");



            if(tabs == null && fragments.size() > 1){
                //this.initialisePaging();
                Toast.makeText(getApplicationContext(),"Error 1111",Toast.LENGTH_SHORT).show();
                Log.e(getClass().getSimpleName(),"wallets con un fragmento no pensadas");

            }
            else{
                ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
                pagertabs.setVisibility(View.VISIBLE);
                adapter = new MyPagerAdapter(getSupportFragmentManager());

                pagertabs.setAdapter(adapter);

                final int pageMargin = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                        .getDisplayMetrics());
                pagertabs.setPageMargin(pageMargin);

                pagerSlidingTabStrip.setViewPager(pagertabs);

                String color = activity.getColor();
                if (color != null)
                    ((ApplicationSession) this.getApplication()).changeColor(Color.parseColor(color), getResources());

            }
        }
        catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Error in NavigateWallet " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Method to set status bar color in different version of android
     * @param color
     */
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

    /**
     *  Method used to clean the screen
     */

    private void cleanWindows()
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

            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            Toast.makeText(getApplicationContext(), "Error in CleanWindows " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Tabs adapter
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;



        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            if(ApplicationSession.getwalletRuntime().getLasActivity().getTabStrip() != null){
                List<Tab> titleTabs = ApplicationSession.getwalletRuntime().getLasActivity().getTabStrip().getTabs();
                titles = new String[titleTabs.size()];
                for (int i = 0; i < titleTabs.size(); i++) {
                    Tab tab = titleTabs.get(i);
                    titles[i] = tab.getLabel();
                }
            }

        }


        public void destroyItem(android.view.ViewGroup container, int position, Object object) {

            FragmentManager manager = ((android.support.v4.app.Fragment) object).getFragmentManager();
            if(manager != null) {
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

            com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform bitcoinPlatform = null;
            com.bitdubai.sub_app.developer.fragment.Platform developerPlatform = null;

            android.support.v4.app.Fragment currentFragment = null;
            Fragments fragmentType = Fragments.CWP_SHELL_LOGIN;
            List<Tab> titleTabs = ApplicationSession.getwalletRuntime().getLasActivity().getTabStrip().getTabs();
            for (int j = 0; j < titleTabs.size(); j++) {
                if (j == position)
                {
                    Tab tab = titleTabs.get(j);
                    fragmentType = tab.getFragment();
                    break;
                }
            }
            //execute current activity fragments
            try {
                switch (fragmentType) {
                    case CWP_SHELL_LOGIN:

                        break;
                    case CWP_WALLET_MANAGER_MAIN:
                        currentFragment =  WalletDesktopFragment.newInstance(position);
                        break;


                    /**
                     * Executing fragments for BITCOIN WALLET.
                     */

                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager)ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment =  BalanceFragment.newInstance(0);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment = ReceiveFragment.newInstance(0);
                        break;
                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment =  SendFragment.newInstance(0);
                        break;

                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment =  TransactionsFragment.newInstance(0);

                        break;
                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS:
                        bitcoinPlatform = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform();
                        bitcoinPlatform.setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
                        bitcoinPlatform.setErrorManager((ErrorManager) ApplicationSession.getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER));
                        currentFragment =  ContactsFragment.newInstance(0);
                        break;



                }

            }
            catch(Exception ex)
            {
                ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, ex);

                Toast.makeText(getApplicationContext(), "Error in PagerAdapter GetItem " + ex.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

            return currentFragment;
        }

    }
}
