package com.bitdubai.android_core.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.android_core.app.common.version_1.util.BottomMenuReveal;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.DesktopAppSelector;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreSettings;
import com.bitdubai.sub_app.wallet_manager.fragment.FermatNetworkSettings;

import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getAndroidCoreModule;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getCloudClient;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getDesktopRuntimeManager;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getErrorManager;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getWalletRuntimeManager;

/**
 * Created by Matias Furszyfer on 2015.11.19..
 */
public class DesktopActivity extends FermatActivity implements FermatScreenSwapper,DesktopAppSelector {


    private static final String TAG = "DesktopActivity";
    private BottomMenuReveal bottomMenuReveal;

    /**
     *  Called when the activity is first created
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            loadUI();
        } catch (Exception e) {
            //reportUnexpectedUICoreException
            //hacer un enum con areas genericas
            //TODO error manager null
             getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

        try {

//            ClassLoaderManager classLoaderManager = new ClassLoaderManager<>(FermatApplication.getInstance());
//            Object o = classLoaderManager.load("com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.DeveloperBitDubai");
//            for (Method method : o.getClass().getMethods()) {
//                Log.i(TAG, method.getName());
//            }
//            Method m = o.getClass().getDeclaredMethod("start");
//            m.invoke(o);
//
//            Method getAbstractPlugin = o.getClass().getMethod("getAbstractPlugin");
//            Object ab = getAbstractPlugin.invoke(o);
//            Method abstractPluginStart = ab.getClass().getMethod("start");
//            abstractPluginStart.invoke(ab);
//
//        Log.i(TAG,o.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    protected void onDestroy() {

        if(bottomMenuReveal !=null) bottomMenuReveal.clear();
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
        try {

            // get actual fragment on execute

            // Check if no view has focus:
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            String frgBackType = null;

            RuntimeManager runtimeManager = getDesktopRuntimeManager();

            FermatStructure structure = runtimeManager.getLastApp();

            Activity activity = structure.getLastActivity();

            FermatFragment fragment = activity.getLastFragment();

            onBackPressedNotificate();

            if(activity.getType() == Activities.DESKTOP_SETTING_FERMAT_NETWORK){
                try {
                    String[] ipPort = ((FermatNetworkSettings) getAdapter().getLstCurrentFragments().get(0)).getIpPort();
                    final String ip = ipPort[0];
                    final String port = ipPort[1];

                    Thread threadChangeIP = new Thread() {
                        @Override
                        public void run(){
                            getCloudClient().changeIpAndPortProperties(ip, Integer.parseInt(port));
                        }
                    };

                    executor.submit(threadChangeIP);

                }catch (Exception e){

                }
            }

            if (fragment != null) frgBackType = fragment.getBack();

            if (activity != null && activity.getBackActivity() != null && activity.getBackAppPublicKey()!=null) {
                changeActivity(activity.getBackActivity().getCode(),activity.getBackAppPublicKey());
            } else {
                finish();
                super.onBackPressed();
            }


        }catch (Exception e){
            e.printStackTrace();
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


            //loadFragment(subAppRuntimeManager.getLastApp().getType(), idContainer, screen);

        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeFragment"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void selectWallet(InstalledWallet installedWallet){
        Intent intent;

        try {

            FermatStructure walletNavigationStructure= getWalletRuntimeManager().getWallet(installedWallet.getWalletPublicKey());
            intent = new Intent(this, AppActivity.class);
            intent.putExtra(ApplicationConstants.INSTALLED_FERMAT_APP ,installedWallet);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            removecallbacks();
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
            Activities activities = Activities.getValueFromString(activityName);
                if(!activities.equals(Activities.CCP_DESKTOP)){
                    getDesktopRuntimeManager().getLastDesktopObject().getActivity(activities);
                    resetThisActivity();
                    loadUI();
                }else {
                    try {
                        resetThisActivity();
                        getDesktopRuntimeManager().getLastApp().getActivity(activities);

                        Intent intent = new Intent(this, DesktopActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        startActivity(intent);
                    } catch (Exception e) {
                        getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivity"));
                        Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
                    }
                }
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectWithOtherApp(String fermatAppPublicKey, Object[] objectses) {

    }


    @Override
    public void selectSubApp(InstalledSubApp installedSubApp) {
        Intent intent;

        try {

            if(installedSubApp.getSubAppType() == SubApps.SETTINGS){

                bottomMenuReveal.getOnClickListener().onClick(null);


            }else {


                FermatStructure subAppNavigationStructure = getWalletRuntimeManager().getWallet(installedSubApp.getAppPublicKey());

//                if(subAppNavigationStructure.getPlatform() != Platforms.CRYPTO_BROKER_PLATFORM || subAppNavigationStructure.getPlatform() != Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION) {
                    intent = new Intent(this, AppActivity.class);
                    intent.putExtra(ApplicationConstants.INSTALLED_FERMAT_APP, installedSubApp);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    resetThisActivity();
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                }else{
//                    Toast.makeText(this,"App in develop :)",Toast.LENGTH_SHORT).show();
//                }

            }
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void selectApp(FermatApp installedApp) {
        Intent intent;
        try {
            if(installedApp.getAppName().equals("Chat")){
                intent = new Intent(this, AppActivity.class);
                intent.putExtra(ApplicationConstants.INSTALLED_FERMAT_APP, installedApp);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resetThisActivity();
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }else {
                Toast.makeText(this,"App in develop :)",Toast.LENGTH_SHORT).show();
            }

//            intent = new Intent();
//            intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY,installedApp.getAppPublicKey());
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra(ApplicationConstants.INTENT_APP_TYPE, installedApp.getAppType());
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            intent.setAction("org.fermat.APP_LAUNCHER");
//            sendBroadcast(intent);
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
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

    protected void loadUI() {
        try {

            Activity activity = null;

            try {
                AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection("main_desktop", this);

                
                FermatApplication.getInstance().getAppManager().openApp(getDesktopManager(), fermatAppConnection);
                //TODO: ver esto de pasarle el appConnection en null al desktop o hacerle uno
                /**
                 *
                 * Get current activity to paint
                 */

                FermatStructure fermatStructure = FermatApplication.getInstance().getAppManager().getLastAppStructure();
                activity = fermatStructure.getLastActivity();



//                if(isHelpEnabled(fermatStructure.getPublicKey())){
//                    if (activity.getWizards() != null)
//                        setWizards(activity.getWizards());
//                    showWizard(WizardTypes.DESKTOP_WELCOME_WIZARD.getKey());
//                }else {

                    loadBasicUI(activity, fermatAppConnection);

                    if (activity.getType() == Activities.CCP_DESKTOP) {
                        showWizard(WizardTypes.DESKTOP_WELCOME_WIZARD.getKey());
                        findViewById(R.id.reveal_bottom_container).setVisibility(View.VISIBLE);
                        initialisePaging();
                        if(bottomMenuReveal ==null){
                            findViewById(R.id.reveal_bottom_container).setVisibility(View.VISIBLE);
                            bottomMenuReveal = new BottomMenuReveal((ViewGroup) findViewById(R.id.reveal),this);
                            bottomMenuReveal.buildMenuSettings();
                        }
                    } else {

                        hideBottonIcons();

                        findViewById(R.id.bottom_navigation_container).setVisibility(View.GONE);

                        if (activity.getFragments().size() == 1) {
                            setOneFragmentInScreen(fermatAppConnection.getFragmentFactory(), FermatApplication.getInstance().getAppManager().lastAppSession(), activity.getLastFragment());
                        }
                    }

                    paintScreen(activity);

                    if ((activity.getBottomNavigationMenu()) != null) {
                        bottomNavigationEnabled(true);
                    }
            } catch (Exception e) {
                getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                        Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private boolean loadSettings(){
        AndroidCoreSettings androidCoreSettings = null;
        try {
            androidCoreSettings =getAndroidCoreModule().loadAndGetSettings(ApplicationConstants.SETTINGS_CORE);
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            androidCoreSettings = new AndroidCoreSettings(AppsStatus.ALPHA);
            androidCoreSettings.setIsPresentationHelpEnabled(true);
            try {
                getAndroidCoreModule().persistSettings(ApplicationConstants.SETTINGS_CORE, androidCoreSettings);
            } catch (CantPersistSettingsException e1) {
                e1.printStackTrace();
            } catch (CantCreateProxyException e1) {
                e1.printStackTrace();
            }
        } catch (CantCreateProxyException e) {
            e.printStackTrace();
        }
        return androidCoreSettings.isHelpEnabled();
    }

    private void paintScreen(Activity activity) {
        String backgroundColor = activity.getBackgroundColor();
        if(backgroundColor!=null){
            Drawable colorDrawable = new ColorDrawable(Color.parseColor(backgroundColor));
            getWindow().setBackgroundDrawable(colorDrawable);
        }

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
            e.printStackTrace();
        }
    }

    @Override
    public void setTabCustomImageView(int position,View view) {

    }

    @Override
    public void removeCollapseAnimation(ElementsWithAnimation elementsWithAnimation) {

    }
}
