package com.bitdubai.android_core.app;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bitdubai.android_core.app.common.version_1.FragmentFactory.SubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppSessionManager;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
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
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantLoadSubAppSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.structure.RuntimeSubApp;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppNavigationStructure;
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
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.bitdubai.fermat.R;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;


/**
 * Created by Matias Furszyfer
 */


public class SubAppActivity extends FermatActivity implements FermatScreenSwapper {

    private static final String INSTALLED_SUB_APP = "sub_app";
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

            loadUI(createOrCallSubAppSession());

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

    private void loadFragment(String fragmentType){


        FragmentTransaction transaction;
        com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment=null;

        SubAppSessionManager subAppSessionManager=((ApplicationSession) getApplication()).getSubAppSessionManager();
        SubAppsSession subAppsSession = subAppSessionManager.getSubAppsSession(getSubAppRuntimeMiddleware().getLastSubApp().getType().getCode());
        switch (Fragments.getValueFromString(fragmentType)) {


            //developer app fragments
            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT:
                fragment = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT.getKey());
                    DatabaseToolsFragment frag= DatabaseToolsFragment.newInstance(0);
                    frag.setDeveloperSubAppSession((DeveloperSubAppSession)subAppsSession);
                    //set data pass to fragment
                    fragment.setContext(screenObjects);

                     transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.databasecontainer, frag);
                    // Commit the transaction
                    transaction.commit();

                break;

            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT:
                fragment = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey());

                    DatabaseToolsDatabaseListFragment fragd= DatabaseToolsDatabaseListFragment.newInstance(0,(DeveloperSubAppSession)subAppsSession);
                    fragd.setDeveloperSubAppSession((DeveloperSubAppSession)subAppsSession);
                    fragd.setResource((Resource) screenObjects[0]);

                    //set data pass to fragmentg
                    fragment.setContext(screenObjects);

                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.startContainer, fragd);
                    // Commit the transaction
                    transaction.commit();

                break;
            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT:

                fragment = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey());

                    DatabaseToolsDatabaseTableListFragment fragt= DatabaseToolsDatabaseTableListFragment.newInstance(0);
                    fragt.setDeveloperSubAppSession((DeveloperSubAppSession) subAppsSession);
                    //set data pass to fragment
                    fragment.setContext(screenObjects);
                    fragt.setResource((Resource) screenObjects[0]);
                    fragt.setDeveloperDatabase((DeveloperDatabase) screenObjects[1]);

                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.startContainer, fragt);
                    // Commit the transaction
                    transaction.commit();

                break;
            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT:

                fragment = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey());

                DatabaseToolsDatabaseTableRecordListFragment fragr= DatabaseToolsDatabaseTableRecordListFragment.newInstance(0,subAppsSession);
                fragr.setDeveloperSubAppSession((DeveloperSubAppSession) subAppsSession);



                //set data pass to fragment
                fragment.setContext(screenObjects);
                fragr.setResource((Resource) screenObjects[0]);
                fragr.setDeveloperDatabase((DeveloperDatabase) screenObjects[1]);
                fragr.setDeveloperDatabaseTable((DeveloperDatabaseTable) screenObjects[2]);

                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.startContainer, fragr);
                // Commit the transaction
                transaction.commit();
                break;

            case CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT:
                fragment = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT.getKey());

                    LogToolsFragment frag1= LogToolsFragment.newInstance(0);
                    frag1.setDeveloperSubAppSession((DeveloperSubAppSession) subAppsSession);
                    //set data pass to fragment
                    fragment.setContext(screenObjects);

                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                    transaction1.replace(R.id.logContainer, frag1);
                    // Commit the transaction
                    transaction1.commit();
                break;

            case CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT:

                fragment = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey());

                LogToolsFragmentLevel1 fragl= LogToolsFragmentLevel1.newInstance(0,subAppsSession);
                fragl.setLoggers((ArrayListLoggers) screenObjects[0]);
                fragl.setDeveloperSubAppSession((DeveloperSubAppSession) subAppsSession);
                //set data pass to fragment
                fragment.setContext(screenObjects);

                 transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.logContainer, fragl);
                // Commit the transaction
                transaction.commit();
                break;

            case CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT:

                fragment = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey());

                LogToolsFragmentLevel2 fragl2= LogToolsFragmentLevel2.newInstance(0,subAppsSession);
                fragl2.setLoggers((ArrayListLoggers) screenObjects[0]);
                fragl2.setLoggerLevel((int) screenObjects[1]);
                fragl2.setDeveloperSubAppSession((DeveloperSubAppSession) subAppsSession);

                    //set data pass to fragment
                    fragment.setContext(screenObjects);

                   transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.logContainer, fragl2);
                    // Commit the transaction
                    transaction.commit();
                break;

            case CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT:

                fragment = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(Fragments.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT.getKey());

                LogToolsFragmentLevel3 fragl3= LogToolsFragmentLevel3.newInstance(0,subAppsSession);
                fragl3.setLoggers((ArrayListLoggers) screenObjects[0]);
                fragl3.setLoggerLevel((int) screenObjects[1]);
                fragl3.setDeveloperSubAppSession((DeveloperSubAppSession) subAppsSession);
                //set data pass to fragment
                fragment.setContext(screenObjects);

                transaction = getFragmentManager().beginTransaction();
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



        // get actual fragment on execute
        String frgBackType = null;
        try {
            SubAppRuntimeManager subAppRuntimeManager = getSubAppRuntimeMiddleware();

            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment=null;

            try{

                fragment  = subAppRuntimeManager.getLastSubApp().getLastActivity().getLastFragment();

            }catch (NullPointerException nullPointerException){
                fragment=null;
            }



            //get setting fragment to back
            //if not fragment to back I back to desktop

            if (fragment != null)
                frgBackType = fragment.getBack();


            if (frgBackType != null) {

                com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragmentBack = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(frgBackType); //set back fragment to actual fragment to run


                this.loadFragment(frgBackType);
            } else {
                // set Desktop current activity
                Activity activity = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity();
                if (activity.getType() != Activities.CWP_WALLET_MANAGER_MAIN) {
                    resetThisActivity();
                    //getSubAppRuntimeMiddleware().getHomeScreen();
                    getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_MANAGER);
                    getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
                    //cleanWindows();

                    loadUI(createOrCallSubAppSession());
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


                    case CWP_SUB_APP_ALL_DEVELOPER: //Developer manager


                        getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_DEVELOPER_APP);
                        getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_SUB_APP_ALL_DEVELOPER);

                        intent = new Intent(this, SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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

                        getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_FACTORY);
                        getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_FACTORY_MAIN);

                        intent = new Intent(this, SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    //wallet publisher
                    case CWP_WALLET_PUBLISHER_MAIN:

                        getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_PUBLISHER);
                        getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_PUBLISHER_MAIN);
                        intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case CWP_WALLET_RUNTIME_STORE_MAIN:
                        getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_STORE);
                        getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_RUNTIME_STORE_MAIN);
                        intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                }

            }
            else{

                //String fragmentType = FragmentsEnumType.getValueFromString(actionKey);
                String fragmentType = actionKey;
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
    public void selectWallet(InstalledWallet installedWallet){
        Intent intent;

        try {

            //Activities activityType = Activities.getValueFromString(this.actionKey);

            WalletNavigationStructure walletNavigationStructure= getWalletRuntimeManager().getWallet(installedWallet.getWalletPublicKey());

            intent = new Intent(this, com.bitdubai.android_core.app.WalletActivity.class);
            intent.putExtra(WalletActivity.INSTALLED_WALLET, installedWallet);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void changeActivity(String activity, Object... objects) {
        if(activity.equals(Activities.CWP_WALLET_FACTORY_EDIT_WALLET.getCode())){
            Intent intent;
            try {

                String publicKey="reference_wallet";

                WalletNavigationStructure walletNavigationStructure= getWalletRuntimeManager().getWallet(publicKey);


                intent = new Intent(this, com.bitdubai.android_core.app.EditableWalletActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }catch (Exception e){
                getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
                Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void selectSubApp(InstalledSubApp installedSubApp) {
        Intent intent;

        try {

            //Activities activityType = Activities.getValueFromString(this.actionKey);

            SubApp subAppNavigationStructure= getSubAppRuntimeMiddleware().getSubApp(installedSubApp.getSubAppType());

            intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
            intent.putExtra(SubAppActivity.INSTALLED_SUB_APP, installedSubApp);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void changeWalletFragment(String walletCategory, String walletType, String walletPublicKey, String fragmentType) {

    }


    /**
     * Method that loads the UI
     */

    protected void loadUI(SubAppsSession subAppSession) {

        try {
            /**
             * Get current activity to paint
             */
            Activity activity = getActivityUsedType();

            loadBasicUI(activity);

            /**
             * Paint the screenPager
             */
            if (activity.getTabStrip() == null && activity.getFragments().size() > 1) {
                initialisePaging();
            }
            /**
             * Paint tabs
             */
            if (activity.getTabStrip() != null) {
                setPagerTabs(getSubAppRuntimeMiddleware().getLastSubApp(), activity.getTabStrip(), subAppSession);
            }
            /**
             * Paint single screen
             */
            if (activity.getFragments().size() == 1) {
                setOneFragmentInScreen();
            }
        }catch (NullPointerException e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setOneFragmentInScreen(){
        RelativeLayout relativeLayout = ((RelativeLayout) findViewById(R.id.only_fragment_container));
        SubAppRuntimeManager subAppRuntimeManager= getSubAppRuntimeMiddleware();
        String subAppType = subAppRuntimeManager.getLastSubApp().getType().getCode();

        com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory subAppFragmentFactory = SubAppFragmentFactory.getFragmentFactoryBySubAppType(subAppType);


        try {
            if(subAppFragmentFactory !=null){
                //TODO está linea está tirando error
                String fragment = subAppRuntimeManager.getLastSubApp().getLastActivity().getLastFragment().getType();
                SubAppsSession subAppsSession = getSubAppSessionManager().getSubAppsSession(subAppType);

                android.app.Fragment fragmet= subAppFragmentFactory.getFragment(fragment.toString(), subAppsSession, null,getSubAppResourcesProviderManager());
                FragmentTransaction FT = getFragmentManager().beginTransaction();
                FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                FT.replace(R.id.only_fragment_container, fragmet);
//                FT.addToBackStack(null);
//                FT.attach(fragmet);
//                FT.show(fragmet);
                FT.commit();

            }
        } catch (FragmentNotFoundException e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }

    }
    private SubAppsSession createOrCallSubAppSession(){
        SubAppsSession subAppSession = null;
        try {
            Bundle bundle = getIntent().getExtras();
            InstalledSubApp installedSubApp=null;
            if(bundle!=null){
                if(bundle.containsKey(INSTALLED_SUB_APP)){
                    installedSubApp  = (InstalledSubApp) bundle.getSerializable(INSTALLED_SUB_APP);
                }
            }
            if(installedSubApp!=null){
                if (getSubAppSessionManager().isSubAppOpen(installedSubApp.getSubAppType())) {
                    subAppSession = getSubAppSessionManager().getSubAppsSession(installedSubApp.getSubAppType().getCode());
                } else {
                    com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager toolManager = getToolManager();
                    WalletStoreModuleManager walletStoreModuleManager = getWalletStoreModuleManager();
                    WalletPublisherModuleManager walletPublisherModuleManager = getWalletPublisherManager();
                    subAppSession = getSubAppSessionManager().openSubAppSession(installedSubApp.getSubAppType(), getErrorManager(), getWalletFactoryManager(), toolManager,walletStoreModuleManager,walletPublisherModuleManager,getIntraUserModuleManager());
                }
            }

        } catch (NullPointerException nullPointerException){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(nullPointerException));
        } catch (Exception e){
            e.printStackTrace();
            //this happend when is in home screen
        }
        return subAppSession;
    }

}
