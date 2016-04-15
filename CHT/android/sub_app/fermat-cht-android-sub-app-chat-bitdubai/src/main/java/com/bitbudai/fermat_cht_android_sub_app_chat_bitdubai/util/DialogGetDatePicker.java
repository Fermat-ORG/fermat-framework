package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import android.content.Context;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 15/04/16.
 */
public class DialogGetDatePicker extends FermatDialog {

    public DialogGetDatePicker(Context activity, FermatSession fermatSession, ResourceProviderManager resources) {
        super(activity, fermatSession, resources);
    }

    @Override
    protected int setLayoutId() {
       return R.layout.cht_dialog_date_picker;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }
}
