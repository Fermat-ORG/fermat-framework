package com.bitdubai.android_core.app;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;



import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.App;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Fragment;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.MainMenu;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.SideMenu;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.TabStrip;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.TitleBar;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.structure.RuntimeFragment;
import com.bitdubai.fermat.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Natalia on 24/02/2015.
 */
public class FragmentActivity  extends Activity {


    // TODO: Raul: Esto no se de donde salio y para que se usa. Posiblemente tenga que volar .. Luis.

    public CharSequence Title;
    private Menu menu;
    private PagerSlidingTabStrip tabStrip;
    private App app;
    private SubApp subApp;
    private com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.Activity activity;
    private Map<Fragments, Fragment> fragments;

    private AppRuntimeManager appRuntimeMiddleware;
    private static WalletRuntimeManager walletRuntimeMiddleware;

    private CorePlatformContext platformContext;
    private ViewPager pager;

    private  TextView abTitle;
    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;
    private MainMenu mainMenumenu;
    private SideMenu sidemenu;
    private String walletStyle = "";
    private TabStrip tabs;
    private TitleBar titleBar; // Comment
    private String tagParam  = ApplicationSession.getChildId(); //get param with data for fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runtime_app_activity_fragment);
        try{
            // get instances of Runtime middleware object

            this.appRuntimeMiddleware =  ApplicationSession.getAppRuntime(); //(AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);

            this.app = appRuntimeMiddleware.getLastApp();
            this.subApp = appRuntimeMiddleware.getLastSubApp();

            walletRuntimeMiddleware = ApplicationSession.getwalletRuntime();

            //get actual activity to execute
            this.activity = walletRuntimeMiddleware.getLasActivity();


            // Fragment fragment = appRuntimeMiddleware.getLastFragment();
            ApplicationSession.setActivityId(activity.getType().getKey());

            //get activity settings
            this.tabs = activity.getTabStrip();
            this.fragments =activity.getFragments();
            this.titleBar = activity.getTitleBar();

            this.mainMenumenu= activity.getMainMenu();



            if(fragments.size() == 1){
                List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
                Iterator<Map.Entry<Fragments, Fragment>> efragments = this.fragments.entrySet().iterator();

                while (efragments.hasNext()) {
                    Map.Entry<Fragments, Fragment> fragmentEntry = efragments.next();

                    RuntimeFragment fragment = (RuntimeFragment) fragmentEntry.getValue();
                    Fragments type = fragment.getType();
                    switch (type) {

                        case CWP_SHOP_MANAGER_MAIN:
                            break;

                        case CWP_WALLET_STORE_MAIN:
                            break;
                        case CWP_WALLET_FACTORY_MAIN:
                            break;

                    }
                }

            }

            int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
            this.abTitle = (TextView) findViewById(titleId);

            ApplicationSession.setActivityProperties(this, getWindow(), getResources(), tabStrip, getActionBar(), titleBar, abTitle, Title);

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Can't Create Fragment: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        switch ( this.activity.getType()) {

            case CWP_SHELL_LOGIN:
                break;
            case CWP_SHOP_MANAGER_MAIN:

                break;

        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
