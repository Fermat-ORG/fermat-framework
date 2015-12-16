package com.bitdubai.reference_wallet.bank_money_wallet.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

/**
 * Created by memo on 08/12/15.
 */
public class AccountDetailFragment extends FermatWalletFragment {

    public AccountDetailFragment() {
    }

    public static AccountsListFragment newInstance() {
        return new AccountsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bw_clear_project, container, false);
    }
}
