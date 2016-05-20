package com.bitdubai.android_core.app;


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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;
import com.bitdubai.fermat_android_api.engine.FermatAppsManager;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatAppConnection;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;

import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getAppResources;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getErrorManager;

/**
 * Created by Matias Furszyfer
 */


public class AppActivity extends FermatActivity implements FermatScreenSwapper {


    private static final String TAG = "AppActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            loadUI(createOrOpenApplication());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

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
            e.printStackTrace();
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
            e.printStackTrace();
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

    public void onControlledActivityBack(String activityBackCode){
        try {
            String frgBackType = null;
            RuntimeManager runtimeManager = null;
            FermatStructure fermatStructure = null;
            FermatAppsManager fermatAppsManager = ApplicationSession.getInstance().getAppManager();
            if (activityBackCode != null) {
                runtimeManager = fermatAppsManager.selectRuntimeManager(fermatAppsManager.getApp(activityBackCode).getAppType());
                fermatStructure = runtimeManager.getAppByPublicKey(activityBackCode);
            } else {
                fermatStructure = fermatAppsManager.getLastAppStructure();
            }
            Activity activity = (fermatStructure != null) ? fermatStructure.getLastActivity() : null;
            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment = (activity != null) ? activity.getLastFragment() : null;
            if (fragment != null) frgBackType = fragment.getBack();
            if (frgBackType != null) {
                changeFragment(fermatStructure.getPublicKey(), frgBackType);
            } else if (((activity != null) ? activity.getBackActivity() : null) != null && activity.getBackAppPublicKey() != null) {
                if (activityBackCode != null) {
                    changeActivity(activity.getBackActivity().getCode(), activity.getBackAppPublicKey());
                } else
                    changeActivity(activity.getBackActivity().getCode(), activity.getBackAppPublicKey());
            } else {
                Intent intent = new Intent(this, DesktopActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setChangeBackActivity(Activities activityCodeBack) {
        try {
            ApplicationSession.getInstance().getAppManager().getLastAppStructure().getLastActivity().setBackActivity(activityCodeBack);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }


    private Runnable onControlledActivityBackRunnable;
    /**
     * Method call when back button is pressed
     */
    @Override
    public void onBackPressed() {
        onBackPressedNotificate();
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(getDrawerLayout()!=null){
            if(onControlledActivityBackRunnable==null){
                onControlledActivityBackRunnable = new Runnable() {
                    @Override
                    public void run() {

                    }
                };
            }
            closeDrawerAndRunAnAction(onControlledActivityBackRunnable);
        }
        onControlledActivityBack(null);
    }


    /**
     * Method that loads the UI
     */
    protected void loadUI(final FermatSession fermatSession) {
        try {
            if(fermatSession!=null) {
//                Log.i("APP ACTIVITY loadUI", "INICIA " + System.currentTimeMillis());
                FermatStructure appStructure = ApplicationSession.getInstance().getAppManager().getAppStructure(fermatSession.getAppPublicKey());
//                Log.i("APP ACTIVITY loadUI", "Get App Structure " + System.currentTimeMillis());
                final AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(appStructure.getPublicKey(), this, fermatSession);
//                Log.i("APP ACTIVITY loadUI", "getFermatAppConnection " + System.currentTimeMillis());
                FermatFragmentFactory fermatFragmentFactory = fermatAppConnection.getFragmentFactory();
//                Log.i("APP ACTIVITY loadUI", "getFragmentFactory " + System.currentTimeMillis());
                Activity activity = appStructure.getLastActivity();
//                Log.i("APP ACTIVITY loadUI", "getLastActivity " + System.currentTimeMillis());
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            fermatAppConnection.setActiveIdentity(fermatSession.getModuleManager().getSelectedActorIdentity());
                            //refreshSideMenu(fermatAppConnection);
                        } catch (CantGetSelectedActorIdentityException e) {
                            e.printStackTrace();
                        } catch (ActorIdentityNotSelectedException e) {
                            e.printStackTrace();
                        }
                    }
                });

//                Log.i("APP ACTIVITY loadUI", "getSelectedActorIdentity " + System.currentTimeMillis());
                loadBasicUI(activity, fermatAppConnection);
//                Log.i("APP ACTIVITY loadUI", "loadBasicUI " + System.currentTimeMillis());
                hideBottonIcons();
//                Log.i("APP ACTIVITY loadUI", "hideBottonIcons " + System.currentTimeMillis());
                paintScreen(activity);
//                Log.i("APP ACTIVITY loadUI", "paintScreen " + System.currentTimeMillis());
                if (activity.getTabStrip() == null && activity.getFragments().size() > 1) {
                    initialisePaging();
//                    Log.i("APP ACTIVITY loadUI", "initialisePaging " + System.currentTimeMillis());
                }
                if (activity.getTabStrip() != null) {
                    setPagerTabs(activity.getTabStrip(), fermatSession, fermatFragmentFactory);
//                    Log.i("APP ACTIVITY loadUI", "setPagerTabs " + System.currentTimeMillis());
                }
                if (activity.getFragments().size() == 1) {
                    setOneFragmentInScreen(fermatFragmentFactory, fermatSession, appStructure);
//                    Log.i("APP ACTIVITY loadUI", "setOneFragmentInScreen " + System.currentTimeMillis());
                }
//                Log.i("APP ACTIVITY loadUI", " TERMINA " + System.currentTimeMillis());
            }else{
//                Log.i("APP ACTIVITY loadUI", " SESSION NULL");
                Toast.makeText(getApplicationContext(), "Recovering from system error", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"ERROR: Session null, please verify this");
                handleExceptionAndRestart();
            }
        } catch (Exception e) {
//            Log.i("APP ACTIVITY loadUI", " ERROR " + e.getMessage());
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Recovering from system error",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            handleExceptionAndRestart();
        }
    }

    private void paintScreen(Activity activity) {
        try {
            String backgroundColor = activity.getBackgroundColor();
            if (backgroundColor != null) {
                Drawable colorDrawable = new ColorDrawable(Color.parseColor(backgroundColor));
                getWindow().setBackgroundDrawable(colorDrawable);
            }
        }catch (Exception e){
//            getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
//            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
//                    Toast.LENGTH_LONG).show();
//            e.printStackTrace();
        }
    }


    @Override
    public void changeActivity(String activityName,String appBackPublicKey, Object... objects) {
        Activity lastActivity = null;
        Activity nextActivity = null;
        try {
            FermatStructure fermatStructure = ApplicationSession.getInstance().getAppManager().getLastAppStructure();
            lastActivity = fermatStructure.getLastActivity();
            try {
                nextActivity = fermatStructure.getActivity(Activities.getValueFromString(activityName));
            }catch (Exception e){
                e.printStackTrace();
//                Log.e(TAG, "changeActivity error, Avisarle a Mati con este log:");
//                Log.e(TAG,"FermatStructure PK: "+fermatStructure.getPublicKey());
//                Log.e(TAG,"lastActivity: "+lastActivity);
//                Log.e(TAG,"nextActivity code: "+activityName);
                try {
                    //TODO: this is only because i am tired, remember to delete this.
                    fermatStructure = ApplicationSession.getInstance().getAppManager().getAppStructure(appBackPublicKey);
                    nextActivity = fermatStructure.getActivity(Activities.getValueFromString(activityName));
                }catch (Exception e1){
                    handleExceptionAndRestart();
                }
            }
            if (nextActivity != null) {
                if (!nextActivity.equals(lastActivity)) {
                    resetThisActivity();
                    Intent intent = getIntent(); //new Intent(this,LoadingScreenActivity.class);
                    intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, appBackPublicKey);
                    //recreate();
                    //startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    //finish();
                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    loadUI(ApplicationSession.getInstance().getAppManager().getAppsSession(fermatStructure.getPublicKey()));
                }
            }else{
                Log.e(TAG, "nextActivity null, activity code: "+ activityName+ "fermat structure: "+fermatStructure.getPublicKey());
            }
        } catch (Exception e) {
            if(activityName.equals("develop_mode"))
                onBackPressed();
            else {
                getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivity"));
                Toast.makeText(getApplicationContext(), "Recovering from system error", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                handleExceptionAndRestart();
            }
        } catch (Throwable throwable) {
            Toast.makeText(getApplicationContext(), "Recovering from system error. Throwable", Toast.LENGTH_LONG).show();
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

    public void changeFragment(String appPublicKey, String fragmentType) {
        try {
            ApplicationSession.getInstance().getAppManager().getLastAppStructure().getLastActivity().getFragment(fragmentType);
            FermatAppConnection fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(appPublicKey,this,ApplicationSession.getInstance().getAppManager().getAppsSession(appPublicKey));
            FermatFragmentFactory fragmentFactory = fermatAppConnection.getFragmentFactory();
            Fragment fragment = fragmentFactory.getFragment(fragmentType,ApplicationSession.getInstance().getAppManager().getAppsSession(appPublicKey),getAppResources());
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
    public void connectWithOtherApp(String fermatAppPublicKey,Object[] objectses) {
        try {
            selectApp(ApplicationSession.getInstance().getAppManager().getApp(fermatAppPublicKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectApp(FermatApp fermatApp) {
        Intent intent;
        try {
            intent = new Intent();
            intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY,fermatApp.getAppPublicKey());
            intent.putExtra(ApplicationConstants.INTENT_APP_TYPE, fermatApp.getAppType());
            intent.setAction("org.fermat.APP_LAUNCHER");
            sendBroadcast(intent);
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectApp"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void changeScreen(String fragment, int containerId, Object[] objects) {


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

    }

    /**
     * Dispatch onStop() to all fragments.  Ensure all loaders are stopped.
     */
    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onNavigationMenuItemTouchListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {
        try {
            String activityCode = data.getLinkToActivity().getCode();
            String appLickPublicKey = data.getAppLinkPublicKey();
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
        if(tab!=null) tab.setCustomView(view);
    }

    @Override
    public void removeCollapseAnimation(ElementsWithAnimation elementsWithAnimation) {

    }
}
