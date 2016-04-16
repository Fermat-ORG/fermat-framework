package com.bitdubai.sub_app.fan_community.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.sub_app.fan_community.R;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/04/16.
 */
public class MainFragment extends AbstractFermatFragment {

    /**
     * Default constructor
     */
    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * This method returns a MainFragment instance.
     * @return
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    /**
     * This method inflates the layout for this fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.afc_fragment_main, container, false);
    }


}