package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by guillermo on 16/02/16.
 */
public class SettingsBankAccountsFragment extends AbstractFermatFragment {

    public static SettingsBankAccountsFragment newInstance() {
        SettingsBankAccountsFragment fragment = new SettingsBankAccountsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_main,container,false);
        System.out.println("customer settings bank");
        return layout;
    }
}
