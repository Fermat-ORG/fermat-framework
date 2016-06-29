package com.bitdubai.sub_app.wallet_factory.ui.wizards;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.fragments.FermatWizardPageFragment;
import com.bitdubai.sub_app.wallet_factory.R;

import java.util.Map;

/**
 * Created by francisco on 05/08/15.
 */
public class CreateWalletFragment extends FermatWizardPageFragment {

    /**
     * UI
     */
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wizard_create_wallet_fragment, container, false);

        return rootView;
    }

    /**
     * Wizard Page Listeners Methods
     */

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void savePage() {

    }

    @Override
    public void onWizardFinish(Map<String, Object> data) {

    }

    @Override
    public void onActivated(Map<String, Object> data) {

    }

    @Override
    public CharSequence getTitle() {
        return "";
    }
}
