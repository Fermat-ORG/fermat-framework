package com.bitdubai.fermat_android_api.layer.definition.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;

/**
 * Created by Matias Furszyfer on 2015.07.21..
 */
public class FermatFragment extends Fragment {

    /**
     * FLAGS
     */
    protected boolean isAttached;

    /**
     * REFERENCES
     */
    protected WizardConfiguration context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = (WizardConfiguration) getActivity();
        } catch (Exception ex) {
            throw new ClassCastException("cannot convert the current context to FermatActivity");
        }
    }

    protected void startWizard(WizardTypes key) {
        if (context != null && isAttached) {
            context.showWizard(key);
        }
    }

    protected void dismissWizard() {
        if (context != null && isAttached) {
            context.dismissWizard();
        }
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
}

