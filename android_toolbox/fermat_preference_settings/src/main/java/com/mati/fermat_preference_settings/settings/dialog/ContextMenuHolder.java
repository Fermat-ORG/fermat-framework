package com.mati.fermat_preference_settings.settings.dialog;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.mati.fermat_preference_settings.R;

/**
 * Created by mati on 2016.02.08..
 */
public class ContextMenuHolder extends FermatViewHolder {

    private FermatTextView fermatTextView;

    protected ContextMenuHolder(View itemView) {
        super(itemView);
        fermatTextView = (FermatTextView) itemView.findViewById(R.id.txt_context_menu_text);

    }

    public FermatTextView getFermatTextView() {
        return fermatTextView;
    }
}
