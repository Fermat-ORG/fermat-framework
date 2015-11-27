package com.bitdubai.android_core.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.adapters.WizardPageAdapter;
import com.bitdubai.android_core.app.common.version_1.connections.ConnectionConstants;
import com.bitdubai.android_core.app.common.version_1.fragment_factory.WalletFragmentFactory;
import com.bitdubai.android_core.app.common.version_1.util.DepthPageTransformer;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardPageListener;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWizardActivity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WizardPage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatCallback;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.sub_app.wallet_factory.ui.wizards.CreateWalletFragment;
import com.bitdubai.sub_app.wallet_factory.ui.wizards.SetupNavigationFragment;
import com.bitdubai.sub_app.wallet_publisher.wizard.PublishFactoryProjectStep1;
import com.bitdubai.sub_app.wallet_publisher.wizard.PublishFactoryProjectStep2;
import com.bitdubai.sub_app.wallet_publisher.wizard.PublishFactoryProjectSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wizard Activity
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class WizardActivity extends FermatActivity
        implements FermatWizardActivity,
        View.OnClickListener,
        FermatScreenSwapper {

    private static final String TAG = "WizardActivity";
    /**
     * ARGUMENTS
     */
    private static Object[] args;
    private static Wizard wizarType;
    /**
     * DATA
     */
    private Map<String, Object> dataHash;
    private List<Fragment> fragments = new ArrayList<>();
    private int position = -1;
    /**
     * UI
     */
    private ViewPager viewPager;
    private FermatTextView back;
    private FermatTextView next;


    public static void open(Activity context, Object[] _args, Wizard _wizardType) {
        args = _args;
        wizarType = _wizardType;
        Intent wizardIntent = new Intent(context, WizardActivity.class);
        context.startActivity(wizardIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runtime_app_wizard_fragment);
        setupFragments();
        if (fragments == null || fragments.size() == 0) {
            // nothing to see here...
            finish();
            return;
        }

        if (fragments != null && fragments.size() > 0) {
            Log.i(TAG, String.format("Wizard Pages: %d", fragments.size()));
            // load ui
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setPageTransformer(true, new DepthPageTransformer());
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.i(TAG, String.format("Change to position %d - Position offset %f", position, positionOffset));
                }

                @Override
                public void onPageSelected(int position) {
                    boolean isNext = WizardActivity.this.position <= position;
                    WizardActivity.this.position = position;
                    if (position == 0) {
                        showView(false, back);
                        showView(true, next);
                    } else if (position > 0) {
                        showView(true, back);
                        showView(true, next);
                    }
                    if (position >= fragments.size() - 1)
                        next.setText("Finish");
                    else
                        next.setText("Next >>");
                    if (position > 0 && isNext) {
                        // Save last page before moving to the next slide
                        WizardPageListener lastPage =
                                (WizardPageListener) fragments.get(position - 1);
                        lastPage.savePage();
                        // notify fragment active
                        WizardPageListener page = (WizardPageListener) fragments.get(position);
                        page.onActivated(getData());
                        setWizardActivity(page.getTitle());
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // do nothing...
                }
            });
            WizardPageAdapter adapter = new WizardPageAdapter(getFragmentManager(), fragments);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);
            position = 0;

            back = (FermatTextView) findViewById(R.id.back);
            next = (FermatTextView) findViewById(R.id.next);

            if (position == 0 && back != null)
                back.setVisibility(View.INVISIBLE);

            if (fragments.size() > 1 && next != null) {
                next.setText("Next >>");
                next.setVisibility(View.VISIBLE);
            } else if (next != null) {
                next.setText("Finish");
                next.setVisibility(View.VISIBLE);
            }
            // Setting up listeners
            if (next != null)
                next.setOnClickListener(this);
            if (back != null)
                back.setOnClickListener(this);

        }
    }

    private void setupFragments() {
        if (wizarType != null) {
            try {
                WalletNavigationStructure wallet = getWalletRuntimeManager().getLastWallet();
                com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory walletFragmentFactory = WalletFragmentFactory.getFragmentFactoryByWalletType(wallet.getWalletCategory(), wallet.getWalletType(), wallet.getPublicKey());
                for (WizardPage page : wizarType.getPages()) {
                    fragments.add(walletFragmentFactory.getFragment(page.getFragment(),(WalletSession) getWalletSessionManager().getWalletSession(wallet.getPublicKey()), null, getWalletResourcesProviderManager()));
//                    switch (page.getType()) {
//                        case CWP_WALLET_FACTORY_CREATE_STEP_1:
//                            fragments.add(new CreateWalletFragment());
//                            break;
//                        case CWP_WALLET_FACTORY_CREATE_STEP_2:
//                            fragments.add(new SetupNavigationFragment());
//                            break;
//                        case CWP_WALLET_PUBLISHER_PUBLISH_STEP_1:
//                            fragments.add(PublishFactoryProjectStep1.newInstance(args));
//                            break;
//                        case CWP_WALLET_PUBLISHER_PUBLISH_STEP_2:
//                            fragments.add(PublishFactoryProjectStep2.newInstance(args));
//                            break;
//                        case CWP_WALLET_PUBLISHER_PUBLISH_STEP_3:
//                            fragments.add(PublishFactoryProjectSummary.newInstance(args));
//                            break;
//                        default:
//                            break;
//                    }
                }
            } catch (FragmentNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                doBack();
                break;
            case R.id.next:
                doNext();
                break;
            default:
                break;
        }
    }

    /**
     * Perform back event
     */
    public void doBack() {
        if (position > 0 && viewPager != null)
            viewPager.setCurrentItem(position - 1);
    }

    /**
     * Move to next slide or finish this wizard
     */
    public void doNext() {
        if (position >= fragments.size() - 1) {
            // validate all fragments before finish
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage("Please wait...");
            dialog.show();
            new Thread() {
                @Override
                public void run() {
                    int posFail = -1;
                    for (int x = 0; x < fragments.size(); x++) {
                        try {
                            WizardPageListener page =
                                    (WizardPageListener) fragments.get(x);
                            if (!page.validate()) {
                                posFail = x;
                                break;
                            }
                        } catch (Exception ex) {
                            posFail = x;
                            Log.getStackTraceString(ex);
                            break;
                        }
                    }
                    final int pos = posFail;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            if (pos == -1) {
                                WizardPageListener page =
                                        (WizardPageListener) fragments.get(fragments.size() - 1);
                                page.onWizardFinish(dataHash);
                                //finish();
                            } else if (pos > -1) {
                                if (viewPager != null) {
                                    viewPager.setCurrentItem(pos);
                                    Toast.makeText(WizardActivity.this, "Something is missing, please review this step.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
            }.run();
        } else if (position < (fragments.size() - 1)) {
            // move to the next slide
            if (viewPager != null) {
                viewPager.setCurrentItem(position + 1);
            }
        }
    }

    /**
     * Show or hide any view
     *
     * @param show true to show, otherwise false
     * @param view View to show or hide
     */
    public void showView(boolean show, View view) {
        if (view == null)
            return;
        Animation fade = AnimationUtils.loadAnimation(this, show ? R.anim.fade_in : R.anim.fade_out);
        view.setAnimation(fade);
        if (show && (view.getVisibility() == View.INVISIBLE || view.getVisibility() == View.GONE))
            view.setVisibility(View.VISIBLE);
        else if (!show && (view.getVisibility() == View.VISIBLE))
            view.setVisibility(View.INVISIBLE);
        else
            return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        args = null;
        wizarType = null;
    }

    @Override
    protected List<MenuItem> getNavigationMenu() {
        return null;
    }

    @Override
    protected void onNavigationMenuItemTouchListener(MenuItem data, int position) {

    }

    @Override
    public Map<String, Object> getData() {
        return dataHash;
    }

    @Override
    public void putData(String key, Object data) {
        if (dataHash == null)
            dataHash = new HashMap<>();
        dataHash.put(key, data);
    }

    @Override
    public void putData(Map<String, Object> data) {
        if (dataHash == null)
            dataHash = new HashMap<>();
        dataHash.putAll(data);
    }

    @Override
    public void setData(Map<String, Object> data) {
        dataHash = data;
    }

    @Override
    public void setWizardActivity(CharSequence title) {
        if (title == null || getActionBar() == null)
            return;
        getActionBar().setTitle(title);
    }

    @Override
    public void changeActivityBack(String appBackPublicKey, String activityCode) {

    }

    @Override
    public void onBackPressed() {

        String frgBackType = null;

        WalletRuntimeManager walletRuntimeManager = getWalletRuntimeManager();

        WalletNavigationStructure walletNavigationStructure = walletRuntimeManager.getLastWallet();

        com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity activity = walletNavigationStructure.getLastActivity();

        com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment = activity.getLastFragment();

        if (fragment != null) frgBackType = fragment.getBack();

        if (frgBackType != null) {
            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragmentBack = walletRuntimeManager.getLastWallet().getLastActivity().getFragment(fragment.getBack());
            //changeWalletFragment(walletNavigationStructure.getWalletCategory(), walletNavigationStructure.getWalletType(), walletNavigationStructure.getPublicKey(), frgBackType);
        } else if (activity != null && activity.getBackActivity() != null && activity.getBackAppPublicKey()!=null) {
            //changeActivity(activity.getBackActivity().getCode(),activity.getBackAppPublicKey());
        } else {
            Intent intent = new Intent(this, DesktopActivity.class);
            if(developMode==true) intent.putExtra("flag",true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        //super.onBackPressed();
    }

    @Override
    public void changeScreen(String screen, int idContainer, Object[] objects) {

    }

    @Override
    public void selectWallet(InstalledWallet installedWallet) {

    }

    @Override
    public void changeActivity(String activityName, String appBackPublicKey, Object... objects) {

    }

    @Override
    public void selectSubApp(InstalledSubApp installedSubApp) {

    }

    @Override
    public void changeWalletFragment(String walletCategory, String walletType, String walletPublicKey, String fragmentType) {

    }

    @Override
    public void onCallbackViewObserver(FermatCallback fermatCallback) {

    }

    @Override
    public void connectWithOtherApp(Engine engine, String fermatAppPublicKey, Object[] objectses) {
        WalletNavigationStructure walletNavigationStructure = getWalletRuntimeManager().getLastWallet();
        com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity lastWalletActivityWhoAskForConnetion = walletNavigationStructure.getLastActivity();
        SubApp installedSubApp = getSubAppRuntimeMiddleware().getSubAppByPublicKey(fermatAppPublicKey);
        switch (engine){
            case BITCOIN_WALLET_CALL_INTRA_USER_COMMUNITY:
                //subApp runtime
                try {
                    String activityType = lastWalletActivityWhoAskForConnetion.getActivityType();
                    //Ultima pantalla de la wallet que quiere conectarse con la app
                    installedSubApp.getActivity(Activities.CWP_INTRA_USER_ACTIVITY).changeBackActivity(
                            walletNavigationStructure.getPublicKey(),
                            activityType);

                    connectWithSubApp(engine,objectses,installedSubApp);

                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                }
                break;
            case BITCOIN_WALLET_CALL_INTRA_USER_IDENTITY:
                try {

                    //Ultima pantalla de la wallet que quiere conectarse con la app
                    String activityType = lastWalletActivityWhoAskForConnetion.getActivityType();

//                    installedSubApp.getActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY);.changeBackActivity(
//                            walletNavigationStructure.getPublicKey(),
//                            activityType);

                    connectWithSubApp(engine,objectses,installedSubApp);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    private void connectWithSubApp(Engine engine, Object[] objects,SubApp subApp){
        Intent intent = new Intent(this, SubAppActivity.class);
        intent.putExtra(ConnectionConstants.ENGINE_CONNECTION, engine);
        intent.putExtra(ConnectionConstants.SEARCH_NAME,objects);
        intent.putExtra(ConnectionConstants.SUB_APP_CONNECTION,subApp.getAppPublicKey());
        intent.putExtra(ConnectionConstants.SUB_APP_CONNECTION_TYPE,subApp.getType());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
    }

    @Override
    public Object[] connectBetweenAppsData() {
        return new Object[0];
    }
}
