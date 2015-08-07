package com.bitdubai.android_core.app.common.version_1.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bitdubai.android_core.app.common.version_1.adapters.WizardPageAdapter;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WizardPage;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.wizard.CreateWalletFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.wizard.SetupNavigationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Wizard Dialog Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class WizardFragment extends DialogFragment {

    private static final String TAG = "WizardFragment";
    /**
     * FLAGS
     */
    private boolean isAttached;

    /**
     * DATA
     */
    private Wizard wizard;
    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
    private int position = -1;
    /**
     * UI
     */
    private View rootView;
    private ViewPager viewPager;

    /**
     * Set Wizard Fragments
     *
     * @param wizard
     */
    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenTheme);
        setupFragments();
        if (fragments == null || fragments.size() == 0) {
            // nothing to see here...
            dismiss();
            return;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public void setStyle(int style, int theme) {
        super.setStyle(R.style.FullScreenDialog, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.runtime_app_wizard_fragment, container, false);
        if (fragments != null && fragments.size() > 0) {
            Log.i(TAG, String.format("Wizard Pages: %d", fragments.size()));
            // load ui
            viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
            WizardPageAdapter adapter = new WizardPageAdapter(getChildFragmentManager(), fragments);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);
            position = 0;
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    private void setupFragments() {
        if (wizard != null) {
            for (WizardPage page : wizard.getPages()) {
                switch (page.getType()) {
                    case CWP_WALLET_FACTORY_CREATE_STEP_1:
                        fragments.add(new CreateWalletFragment());
                        break;
                    case CWP_WALLET_FACTORY_CREATE_STEP_2:
                        fragments.add(new SetupNavigationFragment());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
