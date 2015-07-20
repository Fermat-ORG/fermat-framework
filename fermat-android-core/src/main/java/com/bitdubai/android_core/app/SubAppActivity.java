package com.bitdubai.android_core.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.*;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
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
import android.widget.Toast;
import com.bitdubai.fermat.R;



/**
 * Created by Matias Furszyfer
 */


public class SubAppActivity extends FermatActivity implements FermatScreenSwapper {

    /**
     * Members used by back button
     */
    private String actionKey;
    private Object[] screenObjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setActivityType(FermatActivity.ACTIVITY_TYPE_SUB_APP);

        try {

            super.onCreate(savedInstanceState);

        } catch (Exception e) {
            ApplicationSession.errorManager.reportUnexpectedSubAppException(ApplicationSession.appRuntimeMiddleware.getLastSubApp().getType(), UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Error in LoadUI SubApp - " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }



    /**
     * This method replaces the current fragment by the next in navigation
     * @param fragmentType Type Id of fragment to show
     */
        private void loadFragment(Fragments fragmentType)
        {
            FragmentTransaction transaction;
            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment=null;
            switch (fragmentType) {


                //developer app fragments
                case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS:
                    fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS);

                      DatabaseToolsFragment frag= DatabaseToolsFragment.newInstance(0);
                        //set data pass to fragment
                        fragment.setContext(screenObjects);

                         transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.databasecontainer, frag);
                        // Commit the transaction
                        transaction.commit();

                    break;

                case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES:
                    fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES);


                        DatabaseToolsDatabaseListFragment fragd= DatabaseToolsDatabaseListFragment.newInstance(0);
                        fragd.setResource((Resource)screenObjects[0]);
                        //set data pass to fragment
                        fragment.setContext(screenObjects);

                         transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.startContainer, fragd);
                        // Commit the transaction
                        transaction.commit();


                    break;
                case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_TABLES:

                    fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_TABLES);

                        DatabaseToolsDatabaseTableListFragment fragt= DatabaseToolsDatabaseTableListFragment.newInstance(0);

                        //set data pass to fragment
                        fragment.setContext(screenObjects);
                        fragt.setResource((Resource) screenObjects[0]);
                        fragt.setDeveloperDatabase((DeveloperDatabase) screenObjects[1]);

                         transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.startContainer, fragt);
                        // Commit the transaction
                        transaction.commit();

                    break;
                case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_RECORDS:

                    fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_RECORDS);

                    DatabaseToolsDatabaseTableRecordListFragment fragr= DatabaseToolsDatabaseTableRecordListFragment.newInstance(0);


                    //set data pass to fragment
                    fragment.setContext(screenObjects);
                    fragr.setResource((Resource) screenObjects[0]);
                    fragr.setDeveloperDatabase((DeveloperDatabase) screenObjects[1]);
                    fragr.setDeveloperDatabaseTable((DeveloperDatabaseTable) screenObjects[2]);

                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.startContainer, fragr);
                    // Commit the transaction
                    transaction.commit();
                    break;

                case CWP_SUB_APP_DEVELOPER_LOG_TOOLS:
                    fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_TOOLS);

                        LogToolsFragment frag1= LogToolsFragment.newInstance(0);

                        //set data pass to fragment
                        fragment.setContext(screenObjects);

                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.logContainer, frag1);
                        // Commit the transaction
                        transaction1.commit();
                    break;

                case CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS:

                    fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS);

                    LogToolsFragmentLevel1 fragl= LogToolsFragmentLevel1.newInstance(0);
                    fragl.setLoggers((ArrayListLoggers) screenObjects[0]);
                    //set data pass to fragment
                    fragment.setContext(screenObjects);

                     transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.logContainer, fragl);
                    // Commit the transaction
                    transaction.commit();
                    break;

                case CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS:

                    fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS);

                    LogToolsFragmentLevel2 fragl2= LogToolsFragmentLevel2.newInstance(0);
                    fragl2.setLoggers((ArrayListLoggers) screenObjects[0]);
                    fragl2.setLoggerLevel((int)screenObjects[1]);

                        //set data pass to fragment
                        fragment.setContext(screenObjects);

                       transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.logContainer, fragl2);
                        // Commit the transaction
                        transaction.commit();
                    break;

                case CWP_SUB_APP_DEVELOPER_LOG_LEVEL_3_TOOLS:

                    fragment = ApplicationSession.appRuntimeMiddleware.getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS);

                    LogToolsFragmentLevel3 fragl3= LogToolsFragmentLevel3.newInstance(0);
                    fragl3.setLoggers((ArrayListLoggers) screenObjects[0]);
                    fragl3.setLoggerLevel((int) screenObjects[1]);
                    //set data pass to fragment
                    fragment.setContext(screenObjects);

                  transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.logContainer, fragl3);
                    // Commit the transaction
                    transaction.commit();
                    break;


            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater inflater = getMenuInflater();


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
            ApplicationSession.errorManager.reportUnexpectedSubAppException(ApplicationSession.appRuntimeMiddleware.getLastSubApp().getType(), UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
            //Toast.makeText(getApplicationContext(), "Error in OptionsItemSelecte " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

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
            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment = appRuntimeManager.getLastFragment();
            //get setting fragment to back
            //if not fragment to back I back to desktop
             frgBackType = fragment.getBack();

        }catch (Exception e){
            ApplicationSession.errorManager.reportUnexpectedSubAppException(ApplicationSession.appRuntimeMiddleware.getLastSubApp().getType(), UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
            e.printStackTrace();
        }



        if(frgBackType != null){

            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragmentBack = ApplicationSession.appRuntimeMiddleware.getFragment(frgBackType); //set back fragment to actual fragment to run

            //I get string context with params pass to fragment to return with this data
            ApplicationSession.mParams=fragmentBack.getContext();

            this.loadFragment(frgBackType);
        }
        else{
            // set Desktop current activity
            Activity activity=ApplicationSession.appRuntimeMiddleware.getLasActivity();
            if (activity.getType() != Activities.CWP_WALLET_MANAGER_MAIN) {
                resetThisActivity();
                activity = ApplicationSession.appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
                //cleanWindows();

                loadUI();
            } else {
                super.onBackPressed();
            }
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
                resetThisActivity();

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

            ApplicationSession.errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, new IllegalArgumentException("the given number doesn't match any Status."));
            Toast.makeText(getApplicationContext(), "Error in change screen " + e.getMessage(), Toast.LENGTH_LONG).show();
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

}
