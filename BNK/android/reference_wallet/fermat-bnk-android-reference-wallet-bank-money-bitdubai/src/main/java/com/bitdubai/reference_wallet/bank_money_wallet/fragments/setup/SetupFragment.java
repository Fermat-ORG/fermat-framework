package com.bitdubai.reference_wallet.bank_money_wallet.fragments.setup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

/**
 * Created by memo on 13/01/16.
 */
public class SetupFragment extends AbstractFermatFragment implements View.OnClickListener {

    public static SetupFragment newInstance(){
        return new SetupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.bw_setup,container,false);
        return layout;
    }


        @Override
    public void onClick(View v) {

    }
}
