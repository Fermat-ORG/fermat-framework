package com.bitdubai.android_core.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.adapters.WizardPageAdapter;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.android_core.app.common.version_1.fragments.DepthPageTransformer;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatAppConnection;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardPageListener;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWizardActivity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WizardPage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Wizard Activity
 *
 * @author Francisco Vásquez
 * @author Matias Furszyfer
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
                FermatStructure wallet = FermatApplication.getInstance().getAppManager().getLastAppStructure();
                FermatSession referenceAppFermatSession = FermatApplication.getInstance().getAppManager().getAppsSession(wallet.getPublicKey());

                FermatAppConnection fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(wallet.getPublicKey(), this, referenceAppFermatSession);

                FermatFragmentFactory walletFragmentFactory = fermatAppConnection.getFragmentFactory(); //WalletFragmentFactory.getFragmentFactoryByWalletType(wallet.getWalletCategory(), wallet.getWalletType(), wallet.getPublicKey());
                for (WizardPage page : wizarType.getPages()) {
                    //todo: ver porqué null el ultimo parametro
                    Fragment abstractFermatFragment = walletFragmentFactory.getFragment(page.getFragment(), referenceAppFermatSession, null, null);
                    fragments.add(abstractFermatFragment);
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
    public void backScreen() {
        doBack();
    }

    @Override
    public void nextScreen() {
        doNext();
    }

    @Override
    public void onBackPressed() {

        try {
            String frgBackType = null;

            FermatStructure walletNavigationStructure = FermatApplication.getInstance().getAppManager().getLastAppStructure();

            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity activity = walletNavigationStructure.getLastActivity();

            FermatFragment fragment = activity.getLastFragment();

            if (fragment != null) frgBackType = fragment.getBack();

            if (frgBackType != null) {
                FermatFragment fragmentBack = walletNavigationStructure.getLastActivity().getFragment(fragment.getBack());
                //changeFragment(walletNavigationStructure.getWalletCategory(), walletNavigationStructure.getWalletType(), walletNavigationStructure.getPublicKey(), frgBackType);
            } else if (activity != null && activity.getBackActivity() != null && activity.getBackAppPublicKey() != null) {
                //changeActivity(activity.getBackActivity().getCode(),activity.getBackAppPublicKey());
            } else {
                Intent intent = new Intent(this, DesktopActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //super.onBackPressed();
    }

    @Override
    public void changeScreen(String screen, int idContainer, Object[] objects) {

    }

    @Override
    public void changeActivity(String activityName, String appBackPublicKey, Object... objects) {

    }

    @Override
    public void connectWithOtherApp(String fermatAppPublicKey, Object[] objectses) {

    }


    @Override
    public void onControlledActivityBack(String activityCodeBack) {

    }

    @Override
    public void setChangeBackActivity(Activities activityCodeBack) {

    }

    @Override
    public void setTabCustomImageView(int position, View view) {

    }

    @Override
    public void removeCollapseAnimation(ElementsWithAnimation elementsWithAnimation) {

    }
}
