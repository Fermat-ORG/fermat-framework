package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by nelson on 29/12/15.
 */
public class CreateNewLocationFragment extends AbstractFermatFragment {

    public static CreateNewLocationFragment newInstance() {
        return new CreateNewLocationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.cbw_fragement_create_new_location, container, false);

        return layout;
    }
}
