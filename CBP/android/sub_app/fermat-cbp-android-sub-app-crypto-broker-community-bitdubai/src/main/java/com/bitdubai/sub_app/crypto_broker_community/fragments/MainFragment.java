package com.bitdubai.sub_app.crypto_broker_community.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends FermatFragment {


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


}
