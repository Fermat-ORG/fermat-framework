package com.bitdubai.smartwallet.android.app.subapp.publisher.version_1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;

public  class ShopsFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    private String[] item;





    public static ShopsFragment newInstance(int position) {
        ShopsFragment f = new ShopsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = new String[]{""};


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.publisher_shops, container, false);



        return rootView;
    }
}
