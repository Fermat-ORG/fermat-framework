package com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.R;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 21/03/16.
 */
public class TkyIdentityHomeFragment  extends AbstractFermatFragment {


    public static TkyIdentityHomeFragment newInstance() {
        return new TkyIdentityHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_tky_artist_home, container, false);


        return rootLayout;
    }


}
