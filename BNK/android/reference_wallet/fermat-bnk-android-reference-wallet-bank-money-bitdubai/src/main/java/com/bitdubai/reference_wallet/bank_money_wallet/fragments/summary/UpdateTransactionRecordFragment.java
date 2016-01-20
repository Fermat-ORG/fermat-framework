package com.bitdubai.reference_wallet.bank_money_wallet.fragments.summary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

/**
 * Created by memo on 19/01/16.
 */
public class UpdateTransactionRecordFragment extends AbstractFermatFragment {


    public static UpdateTransactionRecordFragment newInstance(){
        return new UpdateTransactionRecordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.bw_clear_project,container,false);
        return layout;
    }
}
