package com.bitdubai.sub_app.wallet_manager.fragment.welcome_wizard;

import android.app.Fragment;
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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardPageListener;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_module.DesktopManagerSettings;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_wpd.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.adapter.WizardPageAdapter;
import com.bitdubai.sub_app.wallet_manager.commons.wizard.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.04.13..
 */
public class WelcomeWizardFragment extends AbstractFermatFragment<ReferenceAppFermatSession<WalletManager>, ResourceProviderManager> implements View.OnClickListener {

    ViewPager viewPager;
    WizardPageAdapter wizardPageAdapter;
    private String TAG = "WelcomeFragment";
    int position = 0;
    Button btnGotIt;
    CheckBox checkbox;
    List<AbstractFermatFragment> fragments = new ArrayList<>();
    private DesktopManagerSettings appManagerSettings;
    private RadioGroup radio_group;
    private LinearLayout txt_dont_show_me_again;

    private Bundle savedInstanceState;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        this.savedInstanceState = savedInstanceState;
//        setRetainInstance(true);

        if (savedInstanceState != null){
            Integer  count  = savedInstanceState.getInt("tabsCount");
            for (int i = 0; i < count; i++){
                fragments.add((AbstractFermatFragment) getFragment(i));
            }
        }

        super.onCreate(savedInstanceState);
    }

    private Fragment getFragment(int position){
        return savedInstanceState == null ? wizardPageAdapter.getItem(position) : getFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + R.id.view_pager_welcome + ":" + position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tabsCount",      wizardPageAdapter.getCount());
//        outState.putStringArray("titles", wizardPageAdapter.getTitles().toArray(new String[0]));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wizard_base_layout, container, false);

        if(fragments.isEmpty()) {
            fragments.add(WelcomeWizardFirstFragment.newInstance());
            fragments.add(WelcomeWizardSecondFragment.newInstance());
            fragments.add(WelcomeWizardThridFragment.newInstance());
            fragments.add(WelcomeWizardFourthFragment.newInstance());
            fragments.add(WelcomeWizardFifthFragment.newInstance());
        }

        viewPager = (ViewPager) view.findViewById(R.id.view_pager_welcome);
        btnGotIt = (Button) view.findViewById(R.id.btn_got_it);
        btnGotIt.setText("Next >>");
        checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        txt_dont_show_me_again = (LinearLayout) view.findViewById(R.id.txt_dont_show_me_again);
        txt_dont_show_me_again.setVisibility(View.GONE);

        radio_group = (RadioGroup) view.findViewById(R.id.radio_group);
        radio_group.check(R.id.radio_first);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int itemPos = 0;
                if (checkedId == R.id.radio_first) {
                    itemPos = 0;
                } else if (checkedId == R.id.radio_second) {
                    itemPos = 1;
                } else if (checkedId == R.id.radio_third) {
                    itemPos = 2;
                } else if (checkedId == R.id.radio_fourth) {
                    itemPos = 3;
                } else if (checkedId == R.id.radio_fifth) {
                    itemPos = 4;
                }
                viewPager.setCurrentItem(itemPos);
                boolean isNext = WelcomeWizardFragment.this.position <= position;
                WelcomeWizardFragment.this.position = itemPos;
            }
        });

        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.i(TAG, String.format("Change to position %d - Position offset %f", position, positionOffset));
            }

            @Override
            public void onPageSelected(int position) {
                boolean isNext = WelcomeWizardFragment.this.position <= position;
                WelcomeWizardFragment.this.position = position;
                checkRadioButton(position);
                if (position == 0) {
                    showView(false, btnGotIt);
                    showView(true, btnGotIt);
                } else if (position > 0) {
                    showView(true, btnGotIt);
                    showView(true, btnGotIt);
                }
                if (position >= fragments.size() - 1) {
                    btnGotIt.setText("Got it");
                    txt_dont_show_me_again.setVisibility(View.VISIBLE);
                } else
                    btnGotIt.setText("Next >>");
                if (position > 0 && isNext) {
                    // Save last page before moving to the next slide
                    WizardPageListener lastPage =
                            (WizardPageListener) fragments.get(position - 1);
                    lastPage.savePage();
                    // notify fragment active
                    //WizardPageListener page = (WizardPageListener) fragments.get(position);
                    //page.onActivated(getData());
                    //setWizardActivity(page.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // do nothing...
            }
        });

        wizardPageAdapter = new WizardPageAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(wizardPageAdapter);
        viewPager.setCurrentItem(0);
        position = 0;

        btnGotIt.setOnClickListener(this);


        checkbox.setOnClickListener(this);

        return view;
    }

    private void checkRadioButton(int position){
        switch (position){
            case 0:
                radio_group.check(R.id.radio_first);
                break;
            case 1:
                radio_group.check(R.id.radio_second);
                break;
            case 2:
                radio_group.check(R.id.radio_third);
                break;
            case 3:
                radio_group.check(R.id.radio_fourth);
                break;
            case 4:
                radio_group.check(R.id.radio_fifth);
                break;
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveSettings(!checkbox.isChecked());
                        if(checkbox.isChecked()){
                            Log.i(TAG, "Starting desktop");
//                            try {
//                                wizardPageAdapter.getActiveFragment(viewPager, 3).changeStartActivity(Activities.CCP_DESKTOP.getCode());
//                            }catch (Exception e){
//                                e.printStackTrace();
//                                try {
//                                    wizardPageAdapter.getActiveFragment(viewPager, fragments.size() - 1).changeStartActivity(Activities.CCP_DESKTOP.getCode());
//                                }catch (Exception e1){
//                                    e1.printStackTrace();
//                                }
//                            }
                            changeStartActivity(Activities.CCP_DESKTOP.getCode());
                        }
                    }
                }).start();

                home();
            }else {
                doNext();

            }
        }
    }

    private void saveSettings(boolean isHelpEnabled){
//        settingsSettingsManager = appSession.getModuleManager().getSettingsManager();

        try {
            appManagerSettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            appManagerSettings = new DesktopManagerSettings();
        }
        if(appManagerSettings!=null) {
            appManagerSettings.setIsPresentationHelpEnabled(isHelpEnabled);
            try {
                appSession.getModuleManager().persistSettings(appSession.getAppPublicKey(), appManagerSettings);
            } catch (CantPersistSettingsException e1) {
                e1.printStackTrace();
            }
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
//                                finish();
                            } else if (pos > -1) {
                                if (viewPager != null) {
                                    viewPager.setCurrentItem(pos, true);
//                                    viewPager.setCurrentItem(pos);
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
