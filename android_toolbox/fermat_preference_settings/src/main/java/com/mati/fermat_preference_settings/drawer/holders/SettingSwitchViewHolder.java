package com.mati.fermat_preference_settings.drawer.holders;

import android.view.View;
import android.widget.Switch;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.mati.fermat_preference_settings.R;

/**
 * Created by mati on 2016.02.08..
 */
public class SettingSwitchViewHolder extends FermatViewHolder {


    private Switch settings_switch;
    private FermatTextView textView;

    /**
     * Constructor
     *
     * @param itemView
     */
    public SettingSwitchViewHolder(View itemView,int type) {
        super(itemView,type);
        settings_switch = (Switch) itemView.findViewById(R.id.settings_switch);
        textView = (FermatTextView) itemView.findViewById(R.id.txt_settings_name);
    }

    public Switch getSettings_switch() {
        return settings_switch;
    }

    public FermatTextView getTextView() {
        return textView;
    }
}
