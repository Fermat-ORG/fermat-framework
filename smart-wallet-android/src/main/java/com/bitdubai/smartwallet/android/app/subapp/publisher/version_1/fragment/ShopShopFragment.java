package com.bitdubai.smartwallet.android.app.subapp.publisher.version_1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;


public  class ShopShopFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    ExpandableListView lv;

    public static ShopShopFragment newInstance(int position) {
        ShopShopFragment f = new ShopShopFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shop_shop_fragment, container, false);


        TextView name = (TextView) rootView.findViewById(R.id.shop_name);
        name.setTypeface(MyApplication.getDefaultTypeface());
        TextView direction = (TextView) rootView.findViewById(R.id.shop_direction);
        direction.setTypeface(MyApplication.getDefaultTypeface());
        TextView address = (TextView) rootView.findViewById(R.id.shop_address);
        address.setTypeface(MyApplication.getDefaultTypeface());
        TextView number = (TextView) rootView.findViewById(R.id.shop_number);
        number.setTypeface(MyApplication.getDefaultTypeface());
        TextView state_description = (TextView) rootView.findViewById(R.id.state_description);
        state_description.setTypeface(MyApplication.getDefaultTypeface());
        TextView state = (TextView) rootView.findViewById(R.id.state);
        state.setTypeface(MyApplication.getDefaultTypeface());
        return rootView;
    }
}
