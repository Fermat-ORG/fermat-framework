package com.bitdubai.sub_app.wallet_manager.fragment.welcome_wizard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardPageListener;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_module.DesktopManagerSettings;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.adapter.WizardPageAdapter;
import com.bitdubai.sub_app.wallet_manager.commons.wizard.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.04.13..
 */
public class WelcomeWizardFragment extends AbstractFermatFragment implements View.OnClickListener {

    ViewPager viewPager;
    WizardPageAdapter wizardPageAdapter;
    private String TAG = "WelcomeFragment";
    int position = 0;
    Button btnGotIt;
    CheckBox checkbox;
    List<AbstractFermatFragment> fragments = new ArrayList<>();
    private SettingsManager settingsSettingsManager;
    private DesktopManagerSettings appManagerSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wizard_base_layout, container, false);
        fragments.add(WelcomeWizardFirstFragment.newInstance());
        fragments.add(WelcomeWizardSecondFragment.newInstance());
        fragments.add(WelcomeWizardThridFragment.newInstance());
        fragments.add(WelcomeWizardFourthFragment.newInstance());

        viewPager = (ViewPager) view.findViewById(R.id.view_pager_welcome);
        btnGotIt = (Button) view.findViewById(R.id.btn_got_it);
        btnGotIt.setText("Next >>");
        checkbox = (CheckBox) view.findViewById(R.id.checkbox);


        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, String.format("Change to position %d - Position offset %f", position, positionOffset));
            }

            @Override
            public void onPageSelected(int position) {
                boolean isNext = WelcomeWizardFragment.this.position <= position;
                WelcomeWizardFragment.this.position = position;
                if (position == 0) {
                    showView(false, btnGotIt);
                    showView(true, btnGotIt);
                } else if (position > 0) {
                    showView(true, btnGotIt);
                    showView(true, btnGotIt);
                }
                if (position >= fragments.size() - 1)
                    btnGotIt.setText("Got it");
                else
                    btnGotIt.setText("Next >>");
                if (position > 0 && isNext) {
                    // Save last page before moving to the next slide
                    WizardPageListener lastPage =
                            (WizardPageListener) fragments.get(position - 1);
                    lastPage.savePage();
                    // notify fragment active
                    WizardPageListener page = (WizardPageListener) fragments.get(position);
                    //page.onActivated(getData());
                    //setWizardActivity(page.getTitle());
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

        btnGotIt.setOnClickListener(this);


        checkbox.setOnClickListener(this);

        return view;
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
        Animation fade = AnimationUtils.loadAnimation(getActivity(), show ? R.anim.fade_in : R.anim.fade_out);
        view.setAnimation(fade);
        if (show && (view.getVisibility() == View.INVISIBLE || view.getVisibility() == View.GONE))
            view.setVisibility(View.VISIBLE);
        else if (!show && (view.getVisibility() == View.VISIBLE))
            view.setVisibility(View.INVISIBLE);
        else
            return;
    }

    public static AbstractFermatFragment newInstance() {
        return new WelcomeWizardFragment();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id==R.id.btn_got_it){
            if(position==fragments.size()-1){
                saveSettings(!checkbox.isChecked());
                if(checkbox.isChecked()){
                    getRuntimeManager().changeStartActivity(Activities.CCP_DESKTOP.getCode());
                }
                home();
            }else {
                doNext();

            }
        } else if(id==R.id.checkbox){
        }
    }

    private void saveSettings(boolean isHelpEnabled){
        settingsSettingsManager = appSession.getModuleManager().getSettingsManager();

        try {
            appManagerSettings = (DesktopManagerSettings) settingsSettingsManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            appManagerSettings = new DesktopManagerSettings();
        }
        appManagerSettings.setIsPresentationHelpEnabled(isHelpEnabled);
        try {
            settingsSettingsManager.persistSettings(appSession.getAppPublicKey(),appManagerSettings);
        } catch (CantPersistSettingsException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Move to next slide or finish this wizard
     */
    public void doNext() {
        if (position >= fragments.size() - 1) {
            // validate all fragments before finish
            final ProgressDialog dialog = new ProgressDialog(getActivity());
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            if (pos == -1) {
                                WizardPageListener page =
                                        (WizardPageListener) fragments.get(fragments.size() - 1);
                                //page.onWizardFinish(dataHash);
                                //finish();
                            } else if (pos > -1) {
                                if (viewPager != null) {
                                    viewPager.setCurrentItem(pos);
                                    Toast.makeText(getActivity(), "Something is missing, please review this step.", Toast.LENGTH_LONG).show();
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
}
