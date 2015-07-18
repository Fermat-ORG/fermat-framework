package com.bitdubai.android_core.app;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;


import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.App;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Fragment;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.MainMenu;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.ScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SideMenu;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.TabStrip;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.TitleBar;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Fragments;


import com.bitdubai.fermat.R;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;


import java.util.Map;



/**
 * Created by Natalia on 24/02/2015.
 */
public class FragmentActivity  extends Activity implements ScreenSwapper {

    private String actionKey;

    private Object[] screenObjects;

    // TODO: Raul: Esto no se de donde salio y para que se usa. Posiblemente tenga que volar .. Luis.
    //TODO: esta activity se usa para cargar los fragmentos cuando se empieza a navegar dentro de la sub app (Natalia)

    public CharSequence Title;
    private Menu menu;
    private PagerSlidingTabStrip tabStrip;
    private App app;
    private SubApp subApp;
    private com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Activity activity;
    private com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Fragment fragment;
    private Map<Fragments, Fragment> fragments;

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
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;

        setContentView(R.layout.runtime_app_activity_fragment);
        try{


            this.app = ApplicationSession.getAppRuntime().getLastApp();
            this.subApp =  ApplicationSession.getAppRuntime().getLastSubApp();
            this.activity = ApplicationSession.getAppRuntime().getLasActivity();
            // Fragment fragment = appRuntimeMiddleware.getLastFragment();
            ApplicationSession.setActivityId(activity.getType().getKey());

            //get activity settings
            this.tabs = activity.getTabStrip();

            this.titleBar = activity.getTitleBar();

            this.mainMenumenu= activity.getMainMenu();

            NavigateFragment();


        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Can't Create Fragment: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }




    }


    private void NavigateFragment(){

        Object params;
        Intent intent;
        //get actual activity Fragment to execute
        this.fragment = ApplicationSession.getAppRuntime().getLastFragment();

        if(fragment != null){

            Fragments type = fragment.getType();
            switch (type){



                case CWP_SHELL_LOGIN:
                    break;
                case CWP_WALLET_MANAGER_MAIN:
                    break;
                case CWP_SUB_APP_DEVELOPER:
                    break;
                case CWP_WALLET_MANAGER_SHOP:
                    break;
                case CWP_SHOP_MANAGER_MAIN:
                    break;
                case CWP_SHOP_MANAGER_FREE:
                    break;
                case CWP_SHOP_MANAGER_PAID:
                    break;
                case CWP_SHOP_MANAGER_ACCEPTED_NEARBY:
                    break;


                case CWP_WALLET_STORE_MAIN:
                    break;
                case CWP_WALLET_FACTORY_MAIN:
                    break;
                case CWP_WALLET_PUBLISHER_MAIN:
                    break;
            }

        }


        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        this.abTitle = (TextView) findViewById(titleId);

        ApplicationSession.setActivityProperties(this, getWindow(), getResources(), tabStrip, getActionBar(), titleBar, abTitle, Title);

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


    @Override
    public void onBackPressed() {
        // get actual fragment on execute

        this.fragment = ApplicationSession.getAppRuntime().getLastFragment();

        //get setting fragment to back
        Fragments frgBackType = this.fragment.getBack();

        if(frgBackType != null){


            Fragment fragmentBack = ApplicationSession.getAppRuntime().getFragment(frgBackType); //set back fragment to actual fragment to run

            //I get string context with params pass to fragment to return with this data
            ApplicationSession.setParams(fragmentBack.getContext());
        }


        NavigateFragment();

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


            Fragments fragmentType = Fragments.getValueFromString(actionKey);

            if(fragmentType != null)
            {
                switch (fragmentType) {


                    //developer app fragments
                    case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES:

                        break;
                }
            }
            else
            {
                ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, new IllegalArgumentException("the given number doesn't match any Status."));

            }




        } catch (Exception e) {
            //ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
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
        this.screenObjects = objects;
    }

}
