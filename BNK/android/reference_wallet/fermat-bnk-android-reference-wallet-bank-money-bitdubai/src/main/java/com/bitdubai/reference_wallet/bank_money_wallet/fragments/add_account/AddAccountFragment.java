package com.bitdubai.reference_wallet.bank_money_wallet.fragments.add_account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

/**
 * Created by memo on 03/01/16.
 */
public class AddAccountFragment extends AbstractFermatFragment implements View.OnClickListener, Spinner.OnItemSelectedListener {

    public AddAccountFragment() {
    }

    public static AddAccountFragment newInstance(){
        return new AddAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("entro al la actividad AddAccountFragment");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.bw_add_account, container, false);
        return layout;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
