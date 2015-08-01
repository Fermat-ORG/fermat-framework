package com.bitdubai.android_core.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppSessionManager;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.*;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.*;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.structure.RuntimeSubApp;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
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
import android.view.View;
import android.view.ViewGroup;
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


    /**
     *  Called when the activity is first created
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityType(ActivityType.ACTIVITY_TYPE_SUB_APP);

        try {

            loadUI();

        } catch (Exception e) {

            //reportUnexpectedUICoreException
            //hacer un enum con areas genericas
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindDrawables(findViewById(R.id.drawer_layout));
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }



    /**
     * This method replaces the current fragment by the next in navigation
     * @param fragmentType Type Id of fragment to show
     */

    private void loadFragment(Fragments fragmentType){


        FragmentTransaction transaction;
        com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment=null;

        SubAppSessionManager subAppSessionManager=((ApplicationSession) getApplication()).getSubAppSessionManager();
        switch (fragmentType) {


            //developer app fragments
            case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS:
                fragment = getAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS);
                    DatabaseToolsFragment frag= DatabaseToolsFragment.newInstance(0,subAppSessionManager.listOpenSubApps().get(SubApps.CWP_DEVELOPER_APP));
                    //set data pass to fragment
                    fragment.setContext(screenObjects);

                     transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.databasecontainer, frag);
                    // Commit the transaction
                    transaction.commit();

                break;

            case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES:
                fragment = getAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES);

                    DatabaseToolsDatabaseListFragment fragd= DatabaseToolsDatabaseListFragment.newInstance(0,subAppSessionManager.listOpenSubApps().get(SubApps.CWP_DEVELOPER_APP));
                    fragd.setResource((Resource)screenObjects[0]);
                    //set data pass to fragment
                    fragment.setContext(screenObjects);

                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.startContainer, fragd);
                    // Commit the transaction
                    transaction.commit();


                break;
            case CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_TABLES:

                fragment = getAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_TABLES);

                    DatabaseToolsDatabaseTableListFragment fragt= DatabaseToolsDatabaseTableListFragment.newInstance(0,subAppSessionManager.listOpenSubApps().get(SubApps.CWP_DEVELOPER_APP));

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

                fragment = getAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_RECORDS);

                DatabaseToolsDatabaseTableRecordListFragment fragr= DatabaseToolsDatabaseTableRecordListFragment.newInstance(0,subAppSessionManager.listOpenSubApps().get(SubApps.CWP_DEVELOPER_APP));


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
                fragment = getAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_TOOLS);

                    LogToolsFragment frag1= LogToolsFragment.newInstance(0, subAppSessionManager.listOpenSubApps().get(SubApps.CWP_DEVELOPER_APP));

                    //set data pass to fragment
                    fragment.setContext(screenObjects);

                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.logContainer, frag1);
                    // Commit the transaction
                    transaction1.commit();
                break;

            case CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS:

                fragment = getAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS);

                LogToolsFragmentLevel1 fragl= LogToolsFragmentLevel1.newInstance(0,subAppSessionManager.listOpenSubApps().get(SubApps.CWP_DEVELOPER_APP));
                fragl.setLoggers((ArrayListLoggers) screenObjects[0]);
                //set data pass to fragment
                fragment.setContext(screenObjects);

                 transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.logContainer, fragl);
                // Commit the transaction
                transaction.commit();
                break;

            case CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS:

                fragment = getAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS);

                LogToolsFragmentLevel2 fragl2= LogToolsFragmentLevel2.newInstance(0,subAppSessionManager.listOpenSubApps().get(SubApps.CWP_DEVELOPER_APP));
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

                fragment = getAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS);

                LogToolsFragmentLevel3 fragl3= LogToolsFragmentLevel3.newInstance(0,subAppSessionManager.listOpenSubApps().get(SubApps.CWP_DEVELOPER_APP));
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

    /**
     * Initialize the contents of the Activity's standard options menu
     * @param menu
     * @return true if all is okey
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        /**
         *  Our future code goes here...
         */

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

        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * This method catch de back bottom event and decide which screen load
     */

    @Override
    public void onBackPressed() {

        resetThisActivity();

        // get actual fragment on execute
        Fragments frgBackType = null;
        try {
            SubAppRuntimeManager subAppRuntimeManager = getAppRuntimeMiddleware();
            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment = subAppRuntimeManager.getLastSubApp().getLastActivity().getLastFragment();


            //get setting fragment to back
            //if not fragment to back I back to desktop

            if (fragment != null)
                frgBackType = fragment.getBack();


            if (frgBackType != null) {

                com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragmentBack = getAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(frgBackType); //set back fragment to actual fragment to run


                this.loadFragment(frgBackType);
            } else {
                // set Desktop current activity
                Activity activity = getAppRuntimeMiddleware().getLastSubApp().getLastActivity();
                if (activity.getType() != Activities.CWP_WALLET_MANAGER_MAIN) {
                    resetThisActivity();
                    //getAppRuntimeMiddleware().getHomeScreen();
                    getAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_MANAGER);
                    getAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
                    //cleanWindows();

                    loadUI();
                } else {
                    super.onBackPressed();
                }
            }
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
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
    public void changeScreen(String screen,Object[] objects) {

        try {

            this.screenObjects = objects;
            this.actionKey = screen;

            Intent intent;

            Activities activityType = Activities.getValueFromString(this.actionKey);

            //if activity type is null I execute a fragment, get fragment type

            if(activityType != null){
                //Clean all object from the previous activity
                resetThisActivity();

                switch (activityType){


                    case CWP_SUP_APP_ALL_DEVELOPER: //Developer manager


                        getAppRuntimeMiddleware().getSubApp(SubApps.CWP_DEVELOPER_APP);
                        getAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_SUP_APP_ALL_DEVELOPER);

                        intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slid_in, R.anim.slide_out);

                        break;

                    case CWP_WALLET_BASIC_ALL_MAIN: //basic Wallet
                        //go to wallet basic definition
                        //getWalletRuntimeManager().getActivity(Activities.getValueFromString("CWRWBWBV1M"));
                        //get the Wallet type

                        //getWalletRuntimeManager().getWallet();
                        //intent = new Intent(this, com.bitdubai.android_core.app.WalletActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //startActivity(intent);
                        Toast.makeText(this,"por acá no tiene que venir",Toast.LENGTH_LONG).show();
                        break;
                    //wallet factory
                    case CWP_WALLET_FACTORY_MAIN:

                        getAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_FACTORY);
                        getAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_FACTORY_MAIN);

                        intent = new Intent(this, com.bitdubai.android_core.app.WalletFactoryActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slid_in, R.anim.slide_out);
                        break;

                    //wallet publisher
                    case CWP_WALLET_PUBLISHER_MAIN:

                        getAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_PUBLISHER);
                        getAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_PUBLISHER_MAIN);
                        intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slid_in, R.anim.slide_out);
                        break;

                    case CWP_WALLET_RUNTIME_STORE_MAIN:
                        getAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_STORE);
                        getAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_RUNTIME_STORE_MAIN);
                        intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slid_in, R.anim.slide_out);
                        break;
                }

            }
            else{

                Fragments fragmentType = Fragments.getValueFromString(actionKey);

                if(fragmentType != null){
                    this.loadFragment(fragmentType);
                }
                else{
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("the given number doesn't match any Status."));

                }

            }


        }catch (Exception e){

            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("the given number doesn't match any Status."));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void selectWallet(String screen, InstalledWallet installedWallet){
        Intent intent;

        this.actionKey=screen;

        Activities activityType = Activities.getValueFromString(this.actionKey);

        //if activity type is null I execute a fragment, get fragment type

        if(activityType != null) {
            //Clean all object from the previous activity
            resetThisActivity();

            switch (activityType) {
                case CWP_WALLET_BASIC_ALL_MAIN: //basic Wallet
                    //go to wallet basic definition
                    //getWalletRuntimeManager().getActivity(Activities.getValueFromString("CWRWBWBV1M"));
                    //get the Wallet type
                    try {
                        getWalletRuntimeManager().getWallet(installedWallet.getWalletPublicKey());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    intent = new Intent(this, com.bitdubai.android_core.app.WalletActivity.class);
                    intent.putExtra(WalletActivity.INSTALLED_WALLET,installedWallet);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slid_in, R.anim.slide_out);
                    break;
            }
        }
    }



    /**
     * Method that loads the UI
     */

    protected void loadUI(){

        try{
            /**
             * Get current activity to paint
             */
            Activity activity = getActivityUsedType();

            loadBasicUI(activity);

            /**
             * Paint the screenPager
             */
            if(activity.getTabStrip() == null && activity.getFragments().size() > 1){
                initialisePaging();
            }else{
                /**
                 * Paint tabs
                 */

                // el runtime SubApp es para tener la base de como debe ir el metodo, falta poder obtenerlo desde el AppRuntime
                setPagerTabs(new RuntimeSubApp(),activity.getTabStrip());
            }
        }
        catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
    }

}
