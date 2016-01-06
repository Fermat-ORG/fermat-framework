package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by memo on 06/01/16.
 */
public class SettingsMylocationsFragment extends AbstractFermatFragment{

    public SettingsMylocationsFragment() {
    }

    public static SettingsMylocationsFragment newInstance() {
        return  new SettingsMylocationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.cbw_settings_my_locations,container,false);
        return layout;
    }
}
