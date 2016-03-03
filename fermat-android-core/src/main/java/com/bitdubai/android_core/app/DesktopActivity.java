package com.bitdubai.android_core.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.android_core.app.common.version_1.connections.ConnectionConstants;
import com.bitdubai.android_core.app.common.version_1.util.BottomMenuReveal;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatCallback;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubAppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.all_definition.WalletNavigationStructure;
import com.bitdubai.sub_app.wallet_manager.fragment.FermatNetworkSettings;

import java.util.List;
import java.util.Objects;

import static com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils.getCloudClient;
import static com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils.getDesktopRuntimeManager;
import static com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils.getErrorManager;
import static com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils.getSubAppRuntimeMiddleware;
import static com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils.getWalletRuntimeManager;

/**
 * Created by mati on 2015.11.19..
 */
public class DesktopActivity extends FermatActivity implements FermatScreenSwapper {



    private BottomMenuReveal bottomMenuReveal;

    /**
     *  Called when the activity is first created
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityType(ActivityType.ACTIVITY_TYPE_DESKTOP);

//        if(getIntent().getExtras()!=null) {
//            if (getIntent().getExtras().containsKey(StartActivity.START_ACTIVITY_INIT)) {
//                System.out.println("EJECUTANDO START ALL");
//
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            getApplicationSession().getFermatSystem().startAllRegisteredPlatforms();
//                        } catch (CantStartAllRegisteredPlatformsException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                thread.start();
//
//
//            }
//        }

//        Intent i= new Intent(this, NotificationService.class);
//// potentially add data to the intent
//        startService(i);


//        RemoteViews mContentView = new RemoteViews(getPackageName(), R.layout.test_tt);
//        Notification notification = new Notification.Builder(this).setSmallIcon(R.drawable.fermat_bitcoin2).setTicker("ticker")
//                .setPriority(Notification.PRIORITY_HIGH)
//                .setAutoCancel(false)
//                .setOngoing(true)
//                .setContent(mContentView)
//                .setWhen(System.currentTimeMillis()).build();
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(3,notification);
        try {

            loadUI();

        } catch (Exception e) {

            //reportUnexpectedUICoreException
            //hacer un enum con areas genericas
            //TODO error manager null
             getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onDestroy() {

        if(bottomMenuReveal !=null) bottomMenuReveal.clear();



        super.onDestroy();

        unbindDrawables(findViewById(R.id.drawer_layout));
        System.gc();
    }

    @Override
    public FermatStructure getAppInUse() {
        return getDesktopRuntimeManager().getLastDesktopObject();
    }

    @Override
    public FermatStructure getAppInUse(String publicKey) throws Exception {
        return getDesktopRuntimeManager().getDesktopObject(publicKey);
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

            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment = activity.getLastFragment();

            onBackPressedNotificate();

            if(activity.getType() != Activities.CCP_DESKTOP){
                String[] ipPort = ((FermatNetworkSettings)getAdapter().getLstCurrentFragments().get(0)).getIpPort();
                getCloudClient().changeIpAndPortProperties(ipPort[0],Integer.getInteger(ipPort[1]));
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

            SubAppRuntimeManager subAppRuntimeManager= getSubAppRuntimeMiddleware();

            //loadFragment(subAppRuntimeManager.getLastApp().getType(), idContainer, screen);

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
            if(activities.equals(Activities.CWP_WALLET_FACTORY_EDIT_WALLET.getCode())){
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


                if(activities.equals(Activities.DESKTOP_SETTING_FERMAT_NETWORK)){
                    Toast.makeText(this, "toca", Toast.LENGTH_SHORT).show();

                    getDesktopRuntimeManager().getLastDesktopObject().getActivity(activities);
                    resetThisActivity();
                    loadUI();
//                    List<AbstractFermatFragment> list = new ArrayList<>();
//                    list.add(new FermatNetworkSettings());
//                    getScreenAdapter().removeAllFragments();
//                    getScreenAdapter().changeData(list);
//                    getScreenAdapter().startUpdate(getPagertabs());

//                    ScreenPagerAdapter screenPagerAdapter = new ScreenPagerAdapter(getFragmentManager(),list);
//                    getPagertabs().setAdapter(screenPagerAdapter);
//                    getPagertabs().invalidate();




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
            }
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void selectSubApp(InstalledSubApp installedSubApp) {
        Intent intent;

        try {

            if(installedSubApp.getSubAppType() == SubApps.SETTINGS){

                if(bottomMenuReveal ==null){
                    findViewById(R.id.reveal_bottom_container).setVisibility(View.VISIBLE);
                    bottomMenuReveal = new BottomMenuReveal((ViewGroup) findViewById(R.id.reveal) ,this);
                    bottomMenuReveal.buildMenuSettings();
                }

                bottomMenuReveal.getOnClickListener().onClick(null);


            }else {

                SubApp subAppNavigationStructure = getSubAppRuntimeMiddleware().getSubAppByPublicKey(installedSubApp.getAppPublicKey());

                intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
                intent.putExtra(SubAppActivity.INSTALLED_SUB_APP, installedSubApp);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resetThisActivity();
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
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
    public void connectWithOtherApp(Engine emgine, String fermatAppPublicKey,Object[] objectses) {

    }

    @Override
    public Object[] connectBetweenAppsData() {
        Objects[] objectses = (Objects[]) getIntent().getSerializableExtra(ConnectionConstants.SEARCH_NAME);
        return objectses;
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

            /**
             * Get current activity to paint
             */
            Activity activity = getActivityUsedType();

            try {


                AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection("main_desktop", this);
                //TODO: ver esto de pasarle el appConnection en null al desktop o hacerle uno
                loadBasicUI(activity, fermatAppConnection);

                if (activity.getType() == Activities.CCP_DESKTOP) {
                    findViewById(R.id.reveal_bottom_container).setVisibility(View.VISIBLE);
                    initialisePaging();
                } else {

                    hideBottonIcons();

                    paintScreen(activity);

                    if (activity.getFragments().size() == 1) {
                        setOneFragmentInScreen(fermatAppConnection.getFragmentFactory(),getFermatAppManager().lastAppSession());
                    }
                }
            } catch (Exception e) {
                getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                        Toast.LENGTH_LONG).show();
            }

            try {
                if (activity.getBottomNavigationMenu() != null) {
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

    private void paintScreen(Activity activity) {
        String backgroundColor = activity.getBackgroundColor();
        if(backgroundColor!=null){
            Drawable colorDrawable = new ColorDrawable(Color.parseColor(backgroundColor));
            getWindow().setBackgroundDrawable(colorDrawable);
        }

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
            e.printStackTrace();
        }
    }

}
