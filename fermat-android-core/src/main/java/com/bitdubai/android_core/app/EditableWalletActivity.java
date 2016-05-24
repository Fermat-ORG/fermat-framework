package com.bitdubai.android_core.app;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetResourcesManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ModuleManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ResourcesManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions.WalletRuntimeExceptions;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getErrorManager;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getWalletRuntimeManager;


/**
 * Created by Matias Furszyfer
 */


public class EditableWalletActivity extends FermatActivity implements FermatScreenSwapper {


    public static final String INSTALLED_WALLET = "installedWallet";

    public static final String WALLET_PUBLIC_KEY = "walletPublicKey";

    public static final String WALLET_NAVIGATION_STRUCTURE = "walletNavigation";

    private InstalledWallet lastWallet;


    private AppNavigationStructure appNavigationStructure;


    /**
     * Called when the activity is first created
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getIntent().getExtras();

        appNavigationStructure = (AppNavigationStructure) bundle.getSerializable(WALLET_NAVIGATION_STRUCTURE);

        lastWallet = (InstalledWallet) bundle.getSerializable(INSTALLED_WALLET);


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
            //MenuInflater inflater = getMenuInflater();

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
        super.onRestoreInstanceState(savedInstanceState);
    }


    /**
     * Method call when back button is pressed
     */

    @Override
    public void onBackPressed() {
        // get actual fragment on execute
        String frgBackType = null;

        try {

            WalletRuntimeManager walletRuntimeManager = getWalletRuntimeManager();

            FermatStructure walletNavigationStructure = walletRuntimeManager.getLastWallet();

            Activity activity = walletNavigationStructure.getLastActivity();

            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment = activity.getLastFragment();

            if (fragment != null) frgBackType = fragment.getBack();

            if (frgBackType != null) {

                com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragmentBack = walletRuntimeManager.getLastWallet().getLastActivity().getFragment(fragment.getBack());

                //loadFragment(frgBackType);
//            changeFragment(appNavigationStructure.getWalletCategory(), appNavigationStructure.getWalletType(), appNavigationStructure.getPublicKey(), frgBackType);


            } else if (activity != null && activity.getBackActivity() != null) {
                //todo: hacer esto
                //changeActivity(activity.getBackActivity().getCode());
            } else {
//            getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_MANAGER);
//            getSubAppRuntimeMiddleware().getLastApp().getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
//            resetThisActivity();
//            Intent intent = new Intent(this, SubAppActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            finish();
//            startActivity(intent);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }


//        if (getWalletRuntimeManager().getLastWallet().getLastActivity().getType()!= Activities.CWP_WALLET_MANAGER_MAIN){
//            getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_MANAGER);
//            getSubAppRuntimeMiddleware().getLastApp().getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onNavigationMenuItemTouchListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {

    }


    /**
     * Method that loads the UI
     */
    protected void loadUI(FermatSession walletSession) {

        try {

            Activity activity = appNavigationStructure.getLastActivity();
//            FermatAppConnection fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(appNavigationStructure.getPublicKey(), this, getIntraUserModuleManager().getActiveIntraUserIdentity(), getAssetIssuerWalletModuleManager().getActiveAssetIssuerIdentity(), getAssetUserWalletModuleManager().getActiveAssetUserIdentity(), getAssetRedeemPointWalletModuleManager().getActiveAssetRedeemPointIdentity());
            AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(appNavigationStructure.getPublicKey(), this, ApplicationSession.getInstance().getAppManager().getAppsSession(appNavigationStructure.getPublicKey()));

            FermatFragmentFactory fermatFragmentFactory = fermatAppConnection.getFragmentFactory();
            loadBasicUI(activity,fermatAppConnection);

            if (activity.getTabStrip() == null && activity.getFragments().size() > 1) {
                initialisePaging();
            }
            if (activity.getTabStrip() != null) {

                setPagerTabs(activity.getTabStrip(), walletSession,fermatFragmentFactory);
            }
            if (activity.getFragments().size() == 1) {
                setOneFragmentInScreen(fermatFragmentFactory,walletSession, appNavigationStructure);
            }
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
    }


    private FermatSession createOrCallWalletSession() {
        AbstractFermatSession walletSession = null;
        try {
            //TODO: hacerlo con lo nuevo de appConnection cuando esta actividad vuelva a usarse
            //WalletSettings walletSettings = getWalletSettingsManager().getAndLoadSettingsFile(lastWallet.getWalletPublicKey());
            //walletSession = getWalletSessionManager().openWalletSession(lastWallet, getCryptoWalletManager(), null, getWalletResourcesProviderManager(), getErrorManager(), getCryptoBrokerWalletModuleManager(), getCryptoCustomerWalletModuleManager(), getAssetIssuerWalletModuleManager(), getAssetUserWalletModuleManager(), getAssetRedeemPointWalletModuleManager(), getIntraUserModuleManager(),getBankMoneyWalletModuleManager(), getCashMoneyWalletModuleManager());
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

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

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

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }


    @Override
    public void changeActivity(String activityName,String appPublicKey, Object... objects) {
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

        try {
            //resetThisActivity();

            getWalletRuntimeManager().getLastWallet().getActivity(Activities.getValueFromString(activityName));


            Intent intent;

            try {

                //Activities activityType = Activities.getValueFromString(this.actionKey);


                //getWalletRuntimeManager().getWallet(getWalletRuntimeManager().getLastWallet().getPublicKey());


                intent = getIntent();//new Intent(this, com.bitdubai.android_core.app.WalletActivity.class);
                if (intent.hasExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY)){
                    intent.removeExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY);
                }
                if (intent.hasExtra(ApplicationConstants.INSTALLED_FERMAT_APP)) {
                    intent.removeExtra(ApplicationConstants.INSTALLED_FERMAT_APP);
                }
                intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, getWalletRuntimeManager().getLastWallet().getPublicKey());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);


                //finalize();
                //System.gc();
                //overridePendingTransition(0, 0);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //this.finalize();

                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //startActivity(intent);
                recreate();
                //finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //System.gc();

            } catch (Exception e) {
                getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
                Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            //loadUI(getWalletSessionManager().getWalletSession());


        } catch (Exception e) {

            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivity"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void connectWithOtherApp(String fermatAppPublicKey, Object[] objectses) {

    }


    @Override
    public void onControlledActivityBack(String activityCodeBack) {
        //todo implement in the super class
    }

    @Override
    public void setChangeBackActivity(Activities activityCodeBack) {

    }


    private void loadFragment(String fragmentType) {

    }

    @Override
    public void changeScreen(String screen, int idContainer, Object[] objects) {

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
    public void setTabCustomImageView(int position, View view) {

    }

    @Override
    public void removeCollapseAnimation(ElementsWithAnimation elementsWithAnimation) {

    }
}
