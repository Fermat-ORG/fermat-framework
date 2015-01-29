package com.bitdubai.smartwallet.android.app.subapp.publisher.version_1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;

public  class PublisherShopsFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    private String[] item;





    public static PublisherShopsFragment newInstance(int position) {
        PublisherShopsFragment f = new PublisherShopsFragment();
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

        TextView number = (TextView) rootView.findViewById(R.id.shops_affiliated);
        number.setTypeface(MyApplication.getDefaultTypeface());

        return rootView;
    }
}
