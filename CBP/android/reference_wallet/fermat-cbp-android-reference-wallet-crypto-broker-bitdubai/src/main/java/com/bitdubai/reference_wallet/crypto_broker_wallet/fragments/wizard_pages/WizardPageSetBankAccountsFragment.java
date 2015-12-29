package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.fragments.FermatWizardPageFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.util.Map;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetBankAccountsFragment extends FermatWizardPageFragment {

    public static WizardPageSetBankAccountsFragment newInstance() {
        return new WizardPageSetBankAccountsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hello,null);
        return v;
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
