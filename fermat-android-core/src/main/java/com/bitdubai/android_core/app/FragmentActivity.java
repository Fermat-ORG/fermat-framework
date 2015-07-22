package com.bitdubai.android_core.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;


/**
 * Created by Natalia on 24/02/2015.
 */
public class FragmentActivity  extends FermatActivity implements FermatScreenSwapper {

    private String actionKey;

    private Object[] screenObjects;


    private com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity activity;
    private com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.runtime_app_activity_fragment);
        try{

            this.activity = getAppRuntimeMiddleware().getLasActivity();

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
        this.fragment = getAppRuntimeMiddleware().getLastFragment();

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

        this.fragment = getAppRuntimeMiddleware().getLastFragment();

        //get setting fragment to back
        Fragments frgBackType = this.fragment.getBack();

        if(frgBackType != null){


            Fragment fragmentBack = getAppRuntimeMiddleware().getFragment(frgBackType); //set back fragment to actual fragment to run

            //I get string context with params pass to fragment to return with this data
            ApplicationSession.mParams=fragmentBack.getContext();
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
                getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalStateException("Oooops! recovering from system error."));
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
