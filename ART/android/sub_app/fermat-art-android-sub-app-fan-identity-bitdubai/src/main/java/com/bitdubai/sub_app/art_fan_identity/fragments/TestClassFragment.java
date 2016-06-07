package com.bitdubai.sub_app.art_fan_identity.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.sub_app.art_fan_identity.R;


/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 03/06/16.
 */
public class TestClassFragment extends AbstractFermatFragment {


    public static TestClassFragment newInstance() {
        return new TestClassFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_test, container, false);


        return rootLayout;
    }


}
