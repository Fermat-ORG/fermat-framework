package com.bitdubai.android_core.app;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.ccp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.ccp_module.wallet_manager.interfaces.InstalledSubApp;
import com.bitdubai.fermat_api.layer.ccp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.fermat_api.layer.ccp_wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;


/**
 * Created by Matias Furszyfer
 */


public class EditableWalletActivity extends FermatActivity implements FermatScreenSwapper{


    public static final String INSTALLED_WALLET="installedWallet";


    InstalledWallet lastWallet;

    /**
     *  Called when the activity is first created
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityType(ActivityType.ACTIVITY_TYPE_WALLET);

        try {

            /*
            * Load wallet UI
            */

            loadUI(createOrCallWalletSession());

        } catch (Exception e) {
            getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
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

        try {
            MenuInflater inflater = getMenuInflater();

            /**
             *  Our future code goes here...
             */

        }
        catch (Exception e) {
            getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

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
        }
        catch (Exception e) {
            getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     *  Called to retrieve per-instance state from an activity before being killed so that the state can be restored in onCreate(Bundle) or onRestoreInstanceState(Bundle)
     * @param outState
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    /**
     * This method is called after onStart() when the activity is being re-initialized from a previously saved state, given here in savedInstanceState.
     * Most implementations will simply use onCreate(Bundle) to restore their state, but it is sometimes convenient to do it here after all of the initialization has been done or to allow subclasses to decide whether to use your default implementation
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

        if (getWalletRuntimeManager().getLastWallet().getLastActivity().getType()!= Activities.CWP_WALLET_MANAGER_MAIN){
            getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_MANAGER);
            getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
            resetThisActivity();

            Intent intent = new Intent(this, SubAppActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }else{
            super.onBackPressed();
        }
    }

    /**
     * Method that loads the UI
     */
    protected void loadUI(WalletSession walletSession) {

        try
        {
            /**
             * Selected wallet to paint
             */
            WalletNavigationStructure wallet= getWalletRuntimeManager().getLastWallet();

            /**
             * Get current activity to paint
             */
            Activity activity=wallet.getLastActivity();


            /**
             * Load screen basics returning PagerSlidingTabStrip to load fragments
             */
            loadBasicUI(activity);

            /**
             * Paint a simgle fragment
             */
            if(activity.getTabStrip() == null && activity.getFragments().size() > 1){
                initialisePaging();
            }
            if (activity.getTabStrip() !=null ){
                /**
                 * Paint tabs
                 */
                setPagerTabs(wallet, activity.getTabStrip(), walletSession);
            }
            if(activity.getFragments().size() == 1){
                setOneFragmentInScreen();
            }
        }
        catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
    }
    private void setOneFragmentInScreen(){
        RelativeLayout relativeLayout = ((RelativeLayout) findViewById(R.id.only_fragment_container));
        WalletNavigationStructure walletRuntime= getWalletRuntimeManager().getLastWallet();
        String walletPublicKey = walletRuntime.getPublicKey();
        String walletCategory = walletRuntime.getWalletCategory();
        String walletType = walletRuntime.getWalletType();
        WalletFragmentFactory walletFragmentFactory = com.bitdubai.android_core.app.common.version_1.FragmentFactory.WalletFragmentFactory.getFragmentFactoryByWalletType(walletCategory,walletType,walletPublicKey);

        try {
            if(walletFragmentFactory !=null){
                String fragment = walletRuntime.getLastActivity().getLastFragment().getType();
                WalletSession walletSession = getWalletSessionManager().getWalletSession(walletPublicKey);
                android.app.Fragment fragmet= walletFragmentFactory.getFragment(fragment.toString(), walletSession,getWalletResourcesProviderManager());
                FragmentTransaction FT = getFragmentManager().beginTransaction();
                FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                FT.replace(R.id.only_fragment_container, fragmet);
                FT.addToBackStack(null);
                FT.attach(fragmet);
                FT.show(fragmet);
                FT.commit();

            }
        } catch (FragmentNotFoundException e) {
            e.printStackTrace();
        }

    }
    private WalletSession createOrCallWalletSession(){
        try {

            //getWalletSettingsManager().getSettings(getWalletRuntimeManager().getLastWallet().getPublicKey());
            return getWalletSessionManager().openWalletSession(WalletCategory.REFERENCE_WALLET,getCryptoWalletManager(),null, getWalletResourcesProviderManager(), getErrorManager());


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public CryptoWalletManager getCryptoWalletManager(){
        return (CryptoWalletManager) ((ApplicationSession)getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE);
    }

    /**
     *
     */

    public WalletResourcesProviderManager getWalletResourcesProviderManager(){
        return (WalletResourcesProviderManager) ((ApplicationSession)getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE);

    }

    @Override
    public void changeActivity(String activityName, Object... objects) {

        try {
            resetThisActivity();

            Activities activities = Activities.getValueFromString(activityName);

            getWalletRuntimeManager().getLastWallet().getActivity(activities);

            WalletNavigationStructure walletRuntimeManager =getWalletRuntimeManager().getLastWallet();


            loadUI(getWalletSessionManager().getWalletSession(getWalletRuntimeManager().getLastWallet().getPublicKey()));

        }catch (Exception e){

            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("the given number doesn't match any Status."));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void selectSubApp(InstalledSubApp installedSubApp) {

    }

    @Override
    public void changeWalletFragment(String walletCategory, String walletType, String walletPublicKey, String fragmentType) {

    }

    @Override
    public void changeScreen(String screen, Object[] objects) {

    }

    @Override
    public void selectWallet(InstalledWallet installedWallet) {

    }
}
