package com.bitdubai.android_core.app.common.version_1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.DesktopActivity;
import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatAppConnection;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetResourcesManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ResourcesManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_wpd_api.all_definition.WalletNavigationStructure;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions.WalletRuntimeExceptions;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.List;

import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getErrorManager;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getFermatAppManager;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getSubAppRuntimeMiddleware;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getWalletRuntimeManager;

/**
 * Created by mati on 2016.03.16..
 */
public class AppActivity extends FermatActivity{

    /**
     * Called when the activity is first created
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setActivityType(ActivityType.ACTIVITY_TYPE_WALLET);

        try {

            loadUI(createOrOpenApplication());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Initialize the contents of the Activity's standard options menu
     *
     * @param menu
     * @return true if all is okey
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            MenuInflater inflater = getMenuInflater();

            /**
             *  Our future code goes here...
             */

        } catch (Exception e) {
            getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

        return super.onCreateOptionsMenu(menu);

    }


    /**
     * This hook is called whenever an item in your options menu is selected.
     *
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
            getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Called to retrieve per-instance state from an activity before being killed so that the state can be restored in onCreate(Bundle) or onRestoreInstanceState(Bundle)
     *
     * @param outState
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    /**
     * This method is called after onStart() when the activity is being re-initialized from a previously saved state, given here in savedInstanceState.
     * Most implementations will simply use onCreate(Bundle) to restore their state, but it is sometimes convenient to do it here after all of the initialization has been done or to allow subclasses to decide whether to use your default implementation
     *
     * @param savedInstanceState
     */

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {

            if (savedInstanceState == null) {
                savedInstanceState = new Bundle();
            } else
                super.onRestoreInstanceState(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onControlledActivityBack(String ActivityBackCode){
        // get actual fragment on execute

        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        String frgBackType = null;

        WalletRuntimeManager walletRuntimeManager = getWalletRuntimeManager();

        WalletNavigationStructure walletNavigationStructure = walletRuntimeManager.getLastWallet();

        Activity activity = walletNavigationStructure.getLastActivity();

        com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment = activity.getLastFragment();

        if (fragment != null) frgBackType = fragment.getBack();

        if (frgBackType != null) {
            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragmentBack = walletRuntimeManager.getLastWallet().getLastActivity().getFragment(fragment.getBack());
            changeFragment(walletNavigationStructure.getWalletCategory(), walletNavigationStructure.getWalletType(), walletNavigationStructure.getPublicKey(), frgBackType);
        } else if (activity != null && activity.getBackActivity() != null && activity.getBackAppPublicKey()!=null) {
            if(ActivityBackCode!=null){
                changeActivity(activity.getBackActivity().getCode(),activity.getBackAppPublicKey());
            }else changeActivity(activity.getBackActivity().getCode(),activity.getBackAppPublicKey());
        } else {
            Intent intent = new Intent(this, DesktopActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }


    /**
     * Method call when back button is pressed
     */

    @Override
    public void onBackPressed() {
        onControlledActivityBack(null);
    }


    /**
     * Method that loads the UI
     */
    protected void loadUI(FermatSession walletSession) {

        try {

            FermatStructure wallet = getFermatAppManager().getLastAppStructure();


//            FermatAppConnection fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(wallet.getPublicKey(), this, getIntraUserModuleManager().getActiveIntraUserIdentity(), getAssetIssuerWalletModuleManager().getActiveAssetIssuerIdentity(), getAssetUserWalletModuleManager().getActiveAssetUserIdentity(), getAssetRedeemPointWalletModuleManager().getActiveAssetRedeemPointIdentity());

            AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(wallet.getPublicKey(), this, walletSession);


            FermatFragmentFactory fermatFragmentFactory = fermatAppConnection.getFragmentFactory();
            Activity activity = wallet.getLastActivity();

            loadBasicUI(activity,fermatAppConnection);

            hideBottonIcons();

            paintScreen(activity);

            if (activity.getTabStrip() == null && activity.getFragments().size() > 1) {
                initialisePaging();
            }
            if (activity.getTabStrip() != null) {
                setPagerTabs(activity.getTabStrip(), walletSession,fermatFragmentFactory);
            }
            if (activity.getFragments().size() == 1) {
                setOneFragmentInScreen(fermatFragmentFactory,walletSession,wallet);
            }
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void paintScreen(Activity activity) {
        String backgroundColor = activity.getBackgroundColor();
        if(backgroundColor!=null){
            Drawable colorDrawable = new ColorDrawable(Color.parseColor(backgroundColor));
            getWindow().setBackgroundDrawable(colorDrawable);
        }

    }

    /**
     *
     */

    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
        try {
            return (WalletResourcesProviderManager) ((ApplicationSession) getApplication()).getFermatSystem().getResourcesManager(
                    new PluginVersionReference(
                            Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION,
                            Layers.NETWORK_SERVICE,
                            Plugins.WALLET_RESOURCES,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (ResourcesManagerNotFoundException |
                CantGetResourcesManagerException e) {

            getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            return null;
        } catch (Exception e) {

            getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            return null;
        }
    }

    @Override
    public void changeActivity(String activityName,String appBackPublicKey, Object... objects) {
//        Method m = null;
//        try {
//            m = StrictMode.class.getMethod("incrementExpectedActivityCount", Class.class);
//            m.invoke(null, WalletActivity.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

        boolean isConnectionWithOtherApp = false;
        Activity lastActivity = null;
        Activity nextActivity = null;
        SubApp subApp = null;
        try {
            WalletNavigationStructure walletNavigationStructure = getWalletRuntimeManager().getLastWallet();
            if(walletNavigationStructure.getPublicKey().equals(appBackPublicKey)) {
                lastActivity = walletNavigationStructure.getLastActivity();
                nextActivity = walletNavigationStructure.getActivity(Activities.getValueFromString(activityName));
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
                    loadUI(getFermatAppManager().getAppsSession(getWalletRuntimeManager().getLastWallet().getPublicKey()));
                }
            }else{
//                connectWithSubApp(null,objects,subApp);
            }

        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivity"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        } catch (Throwable throwable) {
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error. Throwable", Toast.LENGTH_LONG).show();
            throwable.printStackTrace();
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void changeFragment(String walletCategory, String walletType, String walletPublicKey, String fragmentType) {
        try {
            getWalletRuntimeManager().getLastWallet().getLastActivity().getFragment(fragmentType);

//            FermatAppConnection fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(walletPublicKey,this,getIntraUserModuleManager().getActiveIntraUserIdentity(), getAssetIssuerWalletModuleManager().getActiveAssetIssuerIdentity(), getAssetUserWalletModuleManager().getActiveAssetUserIdentity(), getAssetRedeemPointWalletModuleManager().getActiveAssetRedeemPointIdentity());

            FermatAppConnection fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(walletPublicKey,this,getFermatAppManager().getAppsSession(walletPublicKey));

            FermatFragmentFactory walletFragmentFactory = fermatAppConnection.getFragmentFactory(); //com.bitdubai.android_core.app.common.version_1.fragment_factory.WalletFragmentFactory.getFragmentFactoryByWalletType(walletCategory, walletType, walletPublicKey);
            Fragment fragment = walletFragmentFactory.getFragment(fragmentType,getFermatAppManager().getAppsSession(getWalletRuntimeManager().getLastWallet().getPublicKey()),getWalletResourcesProviderManager());
            FragmentTransaction FT = this.getFragmentManager().beginTransaction();
            FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            FT.replace(R.id.fragment_container2, fragment);
            FT.commit();
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeFragment"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        //outState.putSerializable(INSTALLED_WALLET, lastWallet);
        //outState.putString(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, lastWallet.getWalletPublicKey());
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        try {
            String walletPublicKey = savedInstanceState.getString(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY);

            getWalletRuntimeManager().getWallet(walletPublicKey);

        } catch (WalletRuntimeExceptions walletRuntimeExceptions) {
            walletRuntimeExceptions.printStackTrace();
        }
    }

    /**
     * Dispatch onStop() to all fragments.  Ensure all loaders are stopped.
     */
    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void connectWithOtherApp(Engine engine, String fermatAppPublicKey, Object[] objectses) {

    }

    @Override
    protected void onNavigationMenuItemTouchListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {
        try {
            String activityCode = data.getLinkToActivity().getCode();
            String appLickPublicKey = data.getAppLinkPublicKey();
            if(activityCode.equals("develop_mode")){
                onBackPressed();
            }else
                changeActivity(activityCode,appLickPublicKey);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void setTabCustomImageView(int position,View view) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
//        ImageView imageView = new ImageView(this);
//        imageView.setImageDrawable(new BadgeDrawable.BadgeDrawableBuilder(this).setCount().setPosition(BadgeDrawable.Position.CENTER).build());
        tab.setCustomView(view);
    }
}
