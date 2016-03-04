package com.bitdubai.android_core.app;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.ApplicationConstants;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.android_core.app.common.version_1.connections.ConnectionConstants;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatAppConnection;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatCallback;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubAppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.all_definition.WalletNavigationStructure;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils.getErrorManager;
import static com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils.getSubAppRuntimeMiddleware;
import static com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils.getWalletRuntimeManager;


/**
 * Created by Matias Furszyfer
 */


public class SubAppActivity extends FermatActivity implements FermatScreenSwapper{

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
            //TODO error manager null
          //  getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            e.printStackTrace();
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
        if(view!=null) {
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
    }

    /**
     * This method replaces the current fragment by the next in navigation
     * @param fragmentType Type Id of fragment to show
     */

    private void loadFragment(int idContainer, String fragmentType,FermatFragmentFactory fermatFragmentFactory,FermatSession fermatSession) throws InvalidParameterException {




        try {
            //getSubAppRuntimeMiddleware().getLastApp().getLastActivity().getFragment(fragmentType);
            //com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory subAppFragmentFactory = SubAppFragmentFactory.getFragmentFactoryBySubAppType(subApp);

            FermatFragmentsEnumType fermatFragmentsEnumType = fermatFragmentFactory.getFermatFragmentEnumType(fragmentType);
            android.app.Fragment fragment =  fermatFragmentFactory.getFragment(fragmentType,fermatSession,null);


            FragmentTransaction FT = this.getFragmentManager().beginTransaction();
            FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            FT.replace(idContainer, fragment);
            FT.commit();
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
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

        return false;
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

            Activity activity = null;
            WeakReference<FermatAppConnection> fermatAppConnection = null;

            FermatSession fermatSession = null;
            try{
                SubApp subApp = subAppRuntimeManager.getLastApp();
                activity= subApp.getLastActivity();
                fragment  = activity.getLastFragment();
                fermatSession = getFermatAppManager().getAppsSession(subApp.getAppPublicKey());

//                fermatAppConnection = new WeakReference<FermatAppConnection>(FermatAppConnectionManager.getFermatAppConnection(subApp.getPublicKey(),this,getIntraUserModuleManager().getActiveIntraUserIdentity(), getAssetIssuerWalletModuleManager().getActiveAssetIssuerIdentity(), getAssetUserWalletModuleManager().getActiveAssetUserIdentity(), getAssetRedeemPointWalletModuleManager().getActiveAssetRedeemPointIdentity()));

                fermatAppConnection = new WeakReference<FermatAppConnection>(FermatAppConnectionManager.getFermatAppConnection(subApp.getPublicKey(),this,fermatSession)) ;


            }catch (NullPointerException nullPointerException){
                fragment=null;
            }

            //get setting fragment to back
            //if not fragment to back I back to desktop

            if (fragment != null)
                frgBackType = fragment.getBack();


            if (frgBackType != null) {

                Activity activities = getSubAppRuntimeMiddleware().getLastApp().getLastActivity();
                com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragmentBack = activities.getFragment(frgBackType); //set back fragment to actual fragment to run
                //TODO: ver como hacer para obtener el id del container
                if(fragmentBack.getType().equals("CSADDTD") || fragmentBack.getType().equals("CSADDTT") || fragmentBack.getType().equals("CSADDTR")  || fragmentBack.getType().equals("CSADDT")){
                    loadFragment( R.id.logContainer,frgBackType,fermatAppConnection.get().getFragmentFactory(),fermatSession);
                }else {
                    loadFragment( R.id.startContainer,frgBackType,fermatAppConnection.get().getFragmentFactory(),fermatSession);
                }

            }else if(activity!=null && activity.getBackActivity()!=null){

                //todo: hacer
                changeActivity(activity.getBackActivity().getCode(),activity.getBackAppPublicKey());

            } else {
                // set Desktop current activity
                activity = getSubAppRuntimeMiddleware().getLastApp().getLastActivity();
                if (activity.getType() != Activities.CWP_WALLET_MANAGER_MAIN) {
//                    resetThisActivity();
//                    //getSubAppRuntimeMiddleware().getHomeScreen();
//                    getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_MANAGER);
//                    getSubAppRuntimeMiddleware().getLastApp().getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
                    //cleanWindows();

                    Intent intent = new Intent(this, DesktopActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    startActivity(intent);


//                    loadUI(createOrCallSubAppSession());
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
    public void changeScreen(String screen,int idContainer,Object[] objects) {
        try {
            SubAppRuntimeManager subAppRuntimeManager= getSubAppRuntimeMiddleware();
            SubApp subApp = subAppRuntimeManager.getLastApp();
            loadFragment(idContainer, screen, FermatAppConnectionManager.getFermatAppConnection(subApp.getPublicKey(), this, getFermatAppManager().getAppsSession(subApp.getAppPublicKey())).getFragmentFactory(),getFermatAppManager().getAppsSession(subApp.getAppPublicKey()));

        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeWalletFragment"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void selectWallet(InstalledWallet installedWallet){
        Intent intent;
        try {

            WalletNavigationStructure walletNavigationStructure= getWalletRuntimeManager().getWallet(installedWallet.getWalletPublicKey());

            intent = new Intent(this, com.bitdubai.android_core.app.WalletActivity.class);
            intent.putExtra(ApplicationConstants.INSTALLED_FERMAT_APP, installedWallet);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void changeActivity(String activityName, String appBackPublicKey, Object... objects) {
        try {
            if(Activities.getValueFromString(activityName).equals(Activities.CWP_WALLET_FACTORY_EDIT_WALLET.getCode())){
                Intent intent;
                try {


                    intent = new Intent(this, EditableWalletActivity.class);
                    intent.putExtra(EditableWalletActivity.WALLET_NAVIGATION_STRUCTURE,(WalletNavigationStructure)objects[0]);
                    intent.putExtra(EditableWalletActivity.INSTALLED_WALLET,(InstalledWallet)objects[1]);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                }catch (Exception e){
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
                    Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
                }

            }else{
                try {


                    boolean isConnectionWithOtherApp = false;
                    Activity lastActivity = null;
                    Activity nextActivity = null;
                    SubApp subApp = null;
                    try {
                        SubApp subAppNavigationStructure = getSubAppRuntimeMiddleware().getLastApp();
                        if(subAppNavigationStructure.getPublicKey().equals(appBackPublicKey)) {
                            lastActivity = subAppNavigationStructure.getLastActivity();
                            nextActivity = subAppNavigationStructure.getActivity(Activities.getValueFromString(activityName));
                        }else{
                            subApp= getSubAppRuntimeMiddleware().getSubAppByPublicKey(appBackPublicKey);
                            if(subApp!=null){
                                isConnectionWithOtherApp = true;
                                subApp.getActivity(Activities.getValueFromString(activityName));
                            }
                        }
                        if(!isConnectionWithOtherApp) {
                            if (!nextActivity.equals(lastActivity)) {
                                resetThisActivity();
                                loadUI(getFermatAppManager().getAppsSession(subAppNavigationStructure.getAppPublicKey()));
                            }
                        }else{
                            //connectWithSubApp(null,objects,subApp.getPublicKey());
                        }

                    } catch (Exception e) {
                        getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivity"));
                        Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
                    } catch (Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Oooops! recovering from system error. Throwable", Toast.LENGTH_LONG).show();
                        throwable.printStackTrace();
                    }

                    //resetThisActivity();

                    Activity a =  getSubAppRuntimeMiddleware().getLastApp().getActivity(Activities.getValueFromString(activityName));

                    loadUI(getFermatAppManager().getAppsSession(getSubAppRuntimeMiddleware().getLastApp().getAppPublicKey()));


                }catch (Exception e){

                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivity"));
                    Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
                }
            }
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void selectSubApp(InstalledSubApp installedSubApp) {
        Intent intent;
        try {
            SubApp subAppNavigationStructure= getSubAppRuntimeMiddleware().getSubAppByPublicKey(installedSubApp.getAppPublicKey());

            intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
            intent.putExtra(ApplicationConstants.INSTALLED_FERMAT_APP, installedSubApp);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
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

    @Override
    public void onCallbackViewObserver(FermatCallback fermatCallback) {
    }

    @Override
    public void connectWithOtherApp(Engine engine, String fermatAppPublicKey,Object[] objectses) {
        WalletNavigationStructure walletNavigationStructure = getWalletRuntimeManager().getLastWallet();
        SubApp installedSubApp = getSubAppRuntimeMiddleware().getSubAppByPublicKey(fermatAppPublicKey);
        switch (engine){
            case BITCOIN_WALLET_CALL_INTRA_USER_COMMUNITY:

                //subApp runtime
                try {
                    connectWithSubApp(engine,objectses,installedSubApp);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case BITCOIN_WALLET_CALL_INTRA_USER_IDENTITY:

                try {

                    connectWithSubApp(engine,objectses,installedSubApp);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    @Override
    public Object[] connectBetweenAppsData() {
        Object[] objects = (Object[]) getIntent().getSerializableExtra(ConnectionConstants.SEARCH_NAME);
        return objects;
    }

    @Override
    public void onControlledActivityBack(String activityCodeBack) {
        //TODO: implement in the super class
    }

    @Override
    public void setChangeBackActivity(Activities activityCodeBack) {

    }


    /**
     * Method that loads the UI
     */

    protected void loadUI(FermatSession<FermatApp,?> subAppSession) {
        try {
            AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(subAppSession.getAppPublicKey(), this, subAppSession);
            Activity activity = getActivityUsedType();
            loadBasicUI(activity, fermatAppConnection);
            FermatFragmentFactory fermatFragmentFactory = fermatAppConnection.getFragmentFactory();
            hideBottonIcons();
            if (activity.getTabStrip() == null && activity.getFragments().size() > 1) {
                initialisePaging();
            }
            if (activity.getTabStrip() != null) {
                setPagerTabs(activity.getTabStrip(), subAppSession,fermatFragmentFactory);
            }
            if (activity.getFragments().size() == 1) {
                setOneFragmentInScreen(fermatFragmentFactory,subAppSession);
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

    private FermatSession<FermatApp,?> createOrCallSubAppSession(){
        return createOrOpenApplication();
    }


    @Override
    protected List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> getNavigationMenu() {
        return getSubAppRuntimeMiddleware().getLastApp().getLastActivity().getSideMenu().getMenuItems();
    }

    @Override
    protected void onNavigationMenuItemTouchListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {
        try {
            String activityCode = data.getLinkToActivity().getCode();
            if(activityCode.equals("develop_mode")){
                onBackPressed();
            }else
                changeActivity(activityCode,data.getAppLinkPublicKey());
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in onNavigationMenuItemTouchListener"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public FermatStructure getAppInUse() {
        return getSubAppRuntimeMiddleware().getLastApp();
    }

    @Override
    public FermatStructure getAppInUse(String publicKey)throws Exception {
        return getSubAppRuntimeMiddleware().getSubAppByPublicKey(publicKey);
    }


    private void connectWithSubApp(Engine engine, Object[] objects,SubApp subApp){
        Intent intent = new Intent(this, SubAppActivity.class);
        intent.putExtra(ConnectionConstants.ENGINE_CONNECTION, engine);
        intent.putExtra(ConnectionConstants.SEARCH_NAME,objects);
        intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY,subApp.getAppPublicKey());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
    }
}
