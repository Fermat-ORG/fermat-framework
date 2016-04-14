package com.mati.fermat_preference_settings.drawer.holders;

import android.view.View;
import android.widget.EditText;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.mati.fermat_preference_settings.R;

/**
 * Created by natalia on 11/04/16.
 */
public class SettingLinkTextViewHolder extends FermatViewHolder {


    private FermatTextView textView;

    /**
     * Constructor
     *
     * @param itemView
     */
    public SettingLinkTextViewHolder(View itemView,int type) {
        super(itemView,type);

        textView = (FermatTextView) itemView.findViewById(R.id.txt_settings_name);
    }


    public FermatTextView getTextView() {
        return textView;
    }


}
