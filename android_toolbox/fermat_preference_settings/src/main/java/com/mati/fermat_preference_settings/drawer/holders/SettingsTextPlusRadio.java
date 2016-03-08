package com.mati.fermat_preference_settings.drawer.holders;

import android.view.View;
import android.widget.RadioButton;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.mati.fermat_preference_settings.R;


/**
 * Created by mati on 2016.02.08..
 */
public class SettingsTextPlusRadio extends FermatViewHolder {

    private RadioButton radio_context;

    public SettingsTextPlusRadio(View itemView,int type) {
        super(itemView,type);
        radio_context = (RadioButton) itemView.findViewById(R.id.radio_context);

    }

    public RadioButton getRadio() {
        return radio_context;
    }
}
