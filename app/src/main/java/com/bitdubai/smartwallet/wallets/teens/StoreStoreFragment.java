package com.bitdubai.smartwallet.wallets.teens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.bitdubai.smartwallet.R;





public  class StoreStoreFragment extends Fragment {

    private static final String ARG_POSITION = "position";



    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] amounts;
    private String[] whens;
    private String[] notes;
    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;



    private int position;

    public static StoreStoreFragment newInstance(int position) {
        StoreStoreFragment f = new StoreStoreFragment();
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
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_store_store, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


}