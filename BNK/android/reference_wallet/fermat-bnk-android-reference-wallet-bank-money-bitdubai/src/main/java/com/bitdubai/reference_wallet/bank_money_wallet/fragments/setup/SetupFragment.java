package com.bitdubai.reference_wallet.bank_money_wallet.fragments.setup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

/**
 * Created by memo on 13/01/16.
 */
public class SetupFragment extends AbstractFermatFragment implements View.OnClickListener {

    public static SetupFragment newInstance(){
        return new SetupFragment();
    }


    EditText bankName;

    ImageView okBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.bw_setup,container,false);
        bankName = (EditText) layout.findViewById(R.id.bank_name_edittext);
        okBtn = (ImageView) layout.findViewById(R.id.bw_setup_ok_btn);

        return layout;
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bw_setup_ok_btn){
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME,appSession.getAppPublicKey());
        }
    }
}
