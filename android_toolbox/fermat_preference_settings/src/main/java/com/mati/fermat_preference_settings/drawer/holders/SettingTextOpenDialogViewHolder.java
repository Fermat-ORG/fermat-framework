package com.mati.fermat_preference_settings.drawer.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.mati.fermat_preference_settings.R;

/**
 * Created by mati on 2016.02.08..
 */
public class SettingTextOpenDialogViewHolder extends FermatViewHolder {


    private FermatTextView textView;

    /**
     * Constructor
     *
     * @param itemView
     */
    public SettingTextOpenDialogViewHolder(View itemView,int type) {
        super(itemView,type);
        textView = (FermatTextView) itemView.findViewById(R.id.txt_settings_name);
    }


    public FermatTextView getTextView() {
        return textView;
    }
}
