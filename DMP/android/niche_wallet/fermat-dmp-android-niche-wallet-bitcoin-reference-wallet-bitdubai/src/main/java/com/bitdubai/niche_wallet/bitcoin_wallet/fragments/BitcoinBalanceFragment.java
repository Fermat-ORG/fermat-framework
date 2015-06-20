package com.bitdubai.niche_wallet.bitcoin_wallet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.TextView;
import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
/**
 * Created by Natalia on 02/06/2015.
 */
public class BitcoinBalanceFragment extends  Fragment {
    View rootView;

    String[] balances;

    private static final String ARG_POSITION = "position";

    private int position;

    public static BitcoinBalanceFragment newInstance(int position) {
        BitcoinBalanceFragment f = new BitcoinBalanceFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        balances = new String[]{"BTC 0.0039"};

      //  MyApplication.changeColor(Color.parseColor("#F0E173"), super.getActivity().getResources());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_home_list_item, container, false);
        TextView tv = ((TextView)rootView.findViewById(R.id.balance));
        tv.setText(balances[0]);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }



}

