package com.bitdubai.android_core.app.common.version_1.util.mnemonic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;

/**
 * Created by mati on 2016.02.20..
 */
public class MnemonicFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bitcoin_mnemonic_fragment_main,container,false);

        FermatTextView txt_mnemonic = (FermatTextView)view.findViewById(R.id.txt_mnemonic);
        txt_mnemonic.setText("mnemonic");
        txt_mnemonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
