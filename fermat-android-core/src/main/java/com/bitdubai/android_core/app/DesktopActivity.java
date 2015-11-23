package com.bitdubai.android_core.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.connections.ConnectionConstants;
import com.bitdubai.android_core.app.common.version_1.fragment_factory.SubAppFragmentFactory;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatCallback;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubAppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;

import java.util.List;
import java.util.Objects;

/**
 * Created by mati on 2015.11.19..
 */
public class DesktopActivity extends FermatActivity implements FermatScreenSwapper {


    /**
     *  Called when the activity is first created
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activePlatforms = getIntent().getParcelableArrayListExtra(StartActivity.ACTIVE_PLATFORMS);

        developMode = getIntent().getBooleanExtra(DEVELOP_MODE,false);

        setActivityType(ActivityType.ACTIVITY_TYPE_DESKTOP);

        try {

            loadUI();

        } catch (Exception e) {

            //reportUnexpectedUICoreException
            //hacer un enum con areas genericas
            //TODO error manager null
            //  getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
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
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
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
        finish();
        super.onBackPressed();

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

            //loadFragment(subAppRuntimeManager.getLastSubApp().getType(), idContainer, screen);

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
            intent.putExtra(WalletActivity.INSTALLED_WALLET, installedWallet);
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
                    //resetThisActivity();

                    Activity a =  getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.getValueFromString(activityName));

                    loadUI();


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

            //Activities activityType = Activities.getValueFromString(this.actionKey);

            SubApp subAppNavigationStructure= getSubAppRuntimeMiddleware().getSubAppByPublicKey(installedSubApp.getAppPublicKey());


            intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
            intent.putExtra(SubAppActivity.INSTALLED_SUB_APP, installedSubApp);
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
    public void connectWithOtherApp(Engine emgine, Object[] objectses) {

    }

    @Override
    public Object[] connectBetweenAppsData() {
        Objects[] objectses = (Objects[]) getIntent().getSerializableExtra(ConnectionConstants.SEARCH_NAME);
        return objectses;
    }


    /**
     * Method that loads the UI
     */

    protected void loadUI() {

        try {
            /**
             * Get current activity to paint
             */
            Activity activity = getActivityUsedType();

            loadBasicUI(activity);

            if (activity.getTabStrip() == null && activity.getFragments().size() > 1) {
                initialisePaging();
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

    private void setOneFragmentInScreen() throws InvalidParameterException {

        SubAppRuntimeManager subAppRuntimeManager= getSubAppRuntimeMiddleware();
        SubApp subApp = subAppRuntimeManager.getLastSubApp();
        SubApps subAppType =subApp.getType();

        com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory subAppFragmentFactory = SubAppFragmentFactory.getFragmentFactoryBySubAppType(subAppType);
        String fragment = subAppRuntimeManager.getLastSubApp().getLastActivity().getLastFragment().getType();
        SubAppsSession subAppsSession = getSubAppSessionManager().getSubAppsSession(subApp.getAppPublicKey());

        try {
            if(subAppFragmentFactory !=null){


                TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                tabLayout.setVisibility(View.GONE);

                ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
                pagertabs.setVisibility(View.VISIBLE);


                adapter = new TabsPagerAdapter(getFragmentManager(),
                        getApplicationContext(),
                        subAppFragmentFactory,
                        fragment,
                        subAppsSession,
                        getSubAppResourcesProviderManager(),
                        getResources());
                pagertabs.setAdapter(adapter);

                final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                        .getDisplayMetrics());
                pagertabs.setPageMargin(pageMargin);

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
//    private SubAppsSession createOrCallSubAppSession(){
//        SubAppsSession subAppSession = null;
//        try {
//            Bundle bundle = getIntent().getExtras();
//            InstalledSubApp installedSubApp=null;
//            if(bundle!=null){
//                if(bundle.containsKey(StartActivity.START_ACTIVITY_INIT)){
//
//                }else if(bundle.containsKey(ConnectionConstants.SUB_APP_CONNECTION)){
//                    //installedSubApp =  bundle.getSerializable(ConnectionConstants.SUB_APP_CONNECTION);
//                }
//            }
//            if(installedSubApp!=null){
//                if (getSubAppSessionManager().isSubAppOpen(installedSubApp.getAppPublicKey())) {
//                    subAppSession = getSubAppSessionManager().getSubAppsSession(installedSubApp.getAppPublicKey());
//                } else {
//                    ManagerFactory managerFactory = new ManagerFactory(((ApplicationSession) getApplication()).getFermatSystem());
//                    subAppSession = getSubAppSessionManager().openSubAppSession(
//                            installedSubApp,
//                            getErrorManager(),
//                            managerFactory.getModuleManagerFactory(installedSubApp.getSubAppType())
//                    );
//                }
//            }
//
//        } catch (NullPointerException nullPointerException){
//            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(nullPointerException));
//        } catch (Exception e){
//            e.printStackTrace();
//            //this happend when is in home screen
//        }
//        return subAppSession;
//    }


    @Override
    protected List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> getNavigationMenu() {
        return getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getSideMenu().getMenuItems();
    }

    @Override
    protected void onNavigationMenuItemTouchListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {
        try {
            String activityCode = data.getLinkToActivity().getCode();
            if(activityCode.equals("develop_mode")){
                developMode = true;
                onBackPressed();
            }else
                changeActivity(activityCode,data.getAppLinkPublicKey());
        }catch (Exception e){

        }
    }
    @Override
    public void changeActivityBack(String appBackPublicKey,String activityCode){
        try {
            getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().changeBackActivity(appBackPublicKey,activityCode);
        } catch (InvalidParameterException e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivityBack"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

}
