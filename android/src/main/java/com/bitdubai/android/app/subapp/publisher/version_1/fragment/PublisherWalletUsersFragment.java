package com.bitdubai.android.app.subapp.publisher.version_1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.android.app.common.version_1.classes.MyApplication;

public  class PublisherWalletUsersFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    private String[] item;





    public static PublisherWalletUsersFragment newInstance(int position) {
        PublisherWalletUsersFragment f = new PublisherWalletUsersFragment();
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
        rootView = inflater.inflate(R.layout.publisher_wallet_user, container, false);
        TextView item_1;
        TextView item_2;
        TextView item_3;


        item_1 = (TextView) rootView.findViewById(R.id.item_1);
        item_1.setTypeface(MyApplication.getDefaultTypeface());
        item_2 = (TextView) rootView.findViewById(R.id.item_2);
        item_2.setTypeface(MyApplication.getDefaultTypeface());
        item_3 = (TextView) rootView.findViewById(R.id.item_3);
        item_3.setTypeface(MyApplication.getDefaultTypeface());


        return rootView;
    }
}
