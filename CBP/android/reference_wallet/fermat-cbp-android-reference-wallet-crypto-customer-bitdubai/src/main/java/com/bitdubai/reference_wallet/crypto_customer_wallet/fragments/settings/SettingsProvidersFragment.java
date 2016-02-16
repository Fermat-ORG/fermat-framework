package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by guillermo on 16/02/16.
 */
public class SettingsProvidersFragment extends AbstractFermatFragment {

    public static SettingsProvidersFragment newInstance() {
        SettingsProvidersFragment fragment = new SettingsProvidersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_main,container,false);
        System.out.println("customer settings providers");
        return layout;
    }
}
