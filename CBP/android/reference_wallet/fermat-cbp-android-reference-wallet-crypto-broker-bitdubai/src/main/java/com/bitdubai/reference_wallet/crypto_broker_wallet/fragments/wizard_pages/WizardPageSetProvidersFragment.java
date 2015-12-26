package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWizardPageFragment;

import java.util.Map;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetProvidersFragment extends FermatWizardPageFragment {

    public static WizardPageSetProvidersFragment newInstance() {
        return new WizardPageSetProvidersFragment();
    }

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
        return null;
    }
}
