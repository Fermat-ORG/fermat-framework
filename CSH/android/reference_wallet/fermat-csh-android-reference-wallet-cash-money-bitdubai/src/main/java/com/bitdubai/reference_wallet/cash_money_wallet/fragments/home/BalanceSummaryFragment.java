package com.bitdubai.reference_wallet.cash_money_wallet.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.reference_wallet.cash_money_wallet.R;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public class BalanceSummaryFragment extends FermatWalletFragment {

    public BalanceSummaryFragment() {
    }

    public static BalanceSummaryFragment newInstance() {
        return new BalanceSummaryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.balance_summary, container, false);
    }

}
