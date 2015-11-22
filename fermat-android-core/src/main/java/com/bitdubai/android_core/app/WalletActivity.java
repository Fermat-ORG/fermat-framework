package com.bitdubai.android_core.app;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.connections.ConnectionConstants;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetResourcesManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ModuleManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ResourcesManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatCallback;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions.WalletRuntimeExceptions;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.List;
import java.util.Objects;


/**
 * Created by Matias Furszyfer
 */


public class WalletActivity extends FermatActivity implements FermatScreenSwapper {


    public static final String INSTALLED_WALLET = "installedWallet";

    public static final String WALLET_PUBLIC_KEY = "walletPublicKey";
    private static final String WALLET_TYPE = "walletType";
    private static final String WALLET_CATEGORY = "walletCategory";


    private InstalledWallet lastWallet;



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

            loadUI(createOrCallWalletSession());

        } catch (Exception e) {
            getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
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


    /**
     * Method call when back button is pressed
     */

    @Override
    public void onBackPressed() {
        // get actual fragment on execute


        String frgBackType = null;

        WalletRuntimeManager walletRuntimeManager = getWalletRuntimeManager();

        WalletNavigationStructure walletNavigationStructure = walletRuntimeManager.getLastWallet();

        Activity activity = walletNavigationStructure.getLastActivity();

        com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment = activity.getLastFragment();

        if (fragment != null) frgBackType = fragment.getBack();

        if (frgBackType != null) {

            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragmentBack = walletRuntimeManager.getLastWallet().getLastActivity().getFragment(fragment.getBack());

            //loadFragment(frgBackType);
            changeWalletFragment(walletNavigationStructure.getWalletCategory(), walletNavigationStructure.getWalletType(), walletNavigationStructure.getPublicKey(), frgBackType);


        } else if (activity != null && activity.getBackActivity() != null && activity.getBackAppPublicKey()!=null) {
            changeActivity(activity.getBackActivity().getCode(),activity.getBackAppPublicKey());
        } else {
//            getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_MANAGER);
//            getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
//            resetThisActivity();
//            Intent intent = new Intent(this, SubAppActivity.class);
//            intent.putExtra(DEVELOP_MODE, developMode);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            finish();
//            startActivity(intent);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            Intent intent = new Intent(this, DesktopActivity.class);
            if(developMode==true) intent.putExtra("flag",true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);

        }


//        if (getWalletRuntimeManager().getLastWallet().getLastActivity().getType()!= Activities.CWP_WALLET_MANAGER_MAIN){
//            getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_MANAGER);
//            getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
//            resetThisActivity();
//
//            // TODO : Esto debe ir hacia la subAppActivity en caso de querer ver el home, en un futuro cuando se quiera ver la lista de walletAbiertas va ser distinto
//            Intent intent = new Intent(this, SubAppActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//
//        }else{
//            super.onBackPressed();
//        }
    }


    /**
     * Method that loads the UI
     */
    protected void loadUI(WalletSession walletSession) {

        try {

            WalletNavigationStructure wallet = getWalletRuntimeManager().getLastWallet();

            Activity activity = wallet.getLastActivity();

            loadBasicUI(activity);

            if (activity.getTabStrip() == null && activity.getFragments().size() > 1) {
                initialisePaging();
            }
            if (activity.getTabStrip() != null) {
                setPagerTabs(wallet, activity.getTabStrip(), walletSession);
            }
            if (activity.getFragments().size() == 1) {
                setOneFragmentInScreen();
            }
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setOneFragmentInScreen() {
        WalletNavigationStructure walletRuntime = getWalletRuntimeManager().getLastWallet();
        String walletPublicKey = walletRuntime.getPublicKey();
        String walletCategory = walletRuntime.getWalletCategory();
        String walletType = walletRuntime.getWalletType();

        WalletFragmentFactory walletFragmentFactory = com.bitdubai.android_core.app.common.version_1.fragment_factory.WalletFragmentFactory.getFragmentFactoryByWalletType(walletCategory, walletType, walletPublicKey);

        WalletSession walletSession = getWalletSessionManager().getWalletSession(walletPublicKey);
        String fragment = walletRuntime.getLastActivity().getLastFragment().getType();
        try {
            if (walletFragmentFactory != null) {

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                tabLayout.setVisibility(View.GONE);

                ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
                pagertabs.setVisibility(View.VISIBLE);


                adapter = new TabsPagerAdapter(getFragmentManager(),
                        getApplicationContext(),
                        walletFragmentFactory,
                        fragment,
                        walletSession,
                        getWalletResourcesProviderManager(),
                        getResources());
                pagertabs.setAdapter(adapter);


                //pagertabs.setCurrentItem();
                final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                        .getDisplayMetrics());
                pagertabs.setPageMargin(pageMargin);
                //pagertabs.setCurrentItem(tabStrip.getStartItem(), true);


                //tabLayout.setupWithViewPager(pagertabs);
                //pagertabs.setOffscreenPageLimit(tabStrip.getTabs().size());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private WalletSession createOrCallWalletSession() {
        WalletSession walletSession = null;
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                if (bundle.containsKey(INSTALLED_WALLET)) {
                    lastWallet = (InstalledWallet) bundle.getSerializable(INSTALLED_WALLET);
                } else if (bundle.containsKey(WALLET_PUBLIC_KEY)) {
                    String walletPublicKey = (String) bundle.get(WALLET_PUBLIC_KEY);
                    lastWallet = getWalletManager().getInstalledWallet(walletPublicKey);
                }
                if (getWalletSessionManager().isWalletOpen(lastWallet.getWalletPublicKey())) {
                    walletSession = getWalletSessionManager().getWalletSession(lastWallet.getWalletPublicKey());
                } else {
                    WalletSettings walletSettings = getWalletSettingsManager().getSettings(lastWallet.getWalletPublicKey());
                    walletSession = getWalletSessionManager().openWalletSession(lastWallet, getCryptoWalletManager(), walletSettings, getWalletResourcesProviderManager(), getErrorManager(), getCryptoBrokerWalletModuleManager(), getCryptoCustomerWalletModuleManager(), getAssetIssuerWalletModuleManager(), getAssetUserWalletModuleManager(), getAssetRedeemPointWalletModuleManager(), getIntraUserModuleManager());
                }
            }
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }

        return walletSession;
    }

    public CryptoWalletManager getCryptoWalletManager() {

        try {
            return (CryptoWalletManager) ((ApplicationSession) getApplication()).getFermatSystem().getModuleManager(
                    new PluginVersionReference(
                            Platforms.CRYPTO_CURRENCY_PLATFORM,
                            Layers.WALLET_MODULE,
                            Plugins.CRYPTO_WALLET,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (ModuleManagerNotFoundException |
                CantGetModuleManagerException e) {

            getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN,e);

            return null;
        } catch (Exception e) {

            getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);

            return null;
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

            getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);;

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
                    loadUI(getWalletSessionManager().getWalletSession(getWalletRuntimeManager().getLastWallet().getPublicKey()));
                }
            }else{
                connectWithSubApp(null,objects,subApp.getPublicKey());
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

    @Override
    public void selectSubApp(InstalledSubApp installedSubApp) {

    }

    @Override
    public void changeWalletFragment(String walletCategory, String walletType, String walletPublicKey, String fragmentType) {
        try {
            getWalletRuntimeManager().getLastWallet().getLastActivity().getFragment(fragmentType);
            WalletFragmentFactory walletFragmentFactory = com.bitdubai.android_core.app.common.version_1.fragment_factory.WalletFragmentFactory.getFragmentFactoryByWalletType(walletCategory, walletType, walletPublicKey);
            Fragment fragment = walletFragmentFactory.getFragment(fragmentType, getWalletSessionManager().getWalletSession(getWalletRuntimeManager().getLastWallet().getPublicKey()), getWalletSettingsManager().getSettings(walletPublicKey), getWalletResourcesProviderManager());
            FragmentTransaction FT = this.getFragmentManager().beginTransaction();
            FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            FT.replace(R.id.fragment_container2, fragment);
            FT.commit();
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeWalletFragment"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCallbackViewObserver(FermatCallback fermatCallback) {

    }

    @Override
    public void connectWithOtherApp(Engine engine,Object[] objectses) {
        switch (engine){
            case BITCOIN_WALLET_CALL_INTRA_USER_COMMUNITY:

                //subApp runtime
             //   try {

                    //Ultima pantalla de la wallet que quiere conectarse con la app
                    WalletNavigationStructure walletNavigationStructure = getWalletRuntimeManager().getLastWallet();
                    Activity lastWalletActivityWhoAskForConnetion = walletNavigationStructure.getLastActivity();

//                    SubApp installedSubApp = getSubAppRuntimeMiddleware().getSubAppByPublicKey();
//
//                    installedSubApp.getActivity(Activities.CWP_INTRA_USER_ACTIVITY).changeBackActivity(
//                            walletNavigationStructure.getPublicKey(),
//                            lastWalletActivityWhoAskForConnetion.getActivityType());
//
//                    connectWithSubApp(engine,objectses,installedSubApp.getPublicKey());

//                } catch (InvalidParameterException e) {
//                    e.printStackTrace();
//                }

                break;
            default:
                break;
        }
    }

    private void connectWithSubApp(Engine engine, Object[] objects,String appPublicKey){
        Intent intent = new Intent(this, SubAppActivity.class);
        intent.putExtra(ConnectionConstants.ENGINE_CONNECTION, engine);
        intent.putExtra(ConnectionConstants.SEARCH_NAME,objects);
        intent.putExtra(ConnectionConstants.SUB_APP_CONNECTION,appPublicKey);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
    }

    @Override
    public Object[] connectBetweenAppsData() {
        Objects[] objectses = (Objects[]) getIntent().getSerializableExtra(ConnectionConstants.SEARCH_NAME);
        return objectses;
    }

    @Override
    public void changeScreen(String fragment, int containerId, Object[] objects) {


    }

    @Override
    public void selectWallet(InstalledWallet installedWallet) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        //outState.putSerializable(INSTALLED_WALLET, lastWallet);
        outState.putString(WALLET_PUBLIC_KEY, lastWallet.getWalletPublicKey());
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        try {
            String walletPublicKey = savedInstanceState.getString(WALLET_PUBLIC_KEY);

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
    protected List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> getNavigationMenu() {
        return getWalletRuntimeManager().getLastWallet().getLastActivity().getSideMenu().getMenuItems();
    }


    @Override
    protected void onNavigationMenuItemTouchListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {
        try {
            String activityCode = data.getLinkToActivity().getCode();
            String appLickPublicKey = data.getAppLinkPublicKey();
            if(activityCode.equals("develop_mode")){
                developMode = true;
                onBackPressed();
            }else
                changeActivity(activityCode,appLickPublicKey);
        }catch (Exception e){

        }
    }

    @Override
    public void changeActivityBack(String appBackPublicKey,String activityCode){
        try {
            getWalletRuntimeManager().getLastWallet().getLastActivity().changeBackActivity(appBackPublicKey,activityCode);
        } catch (InvalidParameterException e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivityBack"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }
}
