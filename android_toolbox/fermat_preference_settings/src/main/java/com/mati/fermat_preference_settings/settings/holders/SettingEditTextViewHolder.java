package com.mati.fermat_preference_settings.settings.holders;

import android.view.View;
import android.widget.EditText;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.mati.fermat_preference_settings.R;

/**
 * Created by mati on 2016.02.08..
 */
public class SettingEditTextViewHolder extends FermatViewHolder {


    private EditText settings_edit_text;
    private FermatTextView textView;

    /**
     * Constructor
     *
     * @param itemView
     */
    public SettingEditTextViewHolder(View itemView, int type) {
        super(itemView,type);
        settings_edit_text = (EditText) itemView.findViewById(R.id.settings_edit_text);
        textView = (FermatTextView) itemView.findViewById(R.id.txt_settings_name);
    }

    public EditText getSettingsEditText() {
        return settings_edit_text;
    }

    public FermatTextView getTextView() {
        return textView;
    }
}
