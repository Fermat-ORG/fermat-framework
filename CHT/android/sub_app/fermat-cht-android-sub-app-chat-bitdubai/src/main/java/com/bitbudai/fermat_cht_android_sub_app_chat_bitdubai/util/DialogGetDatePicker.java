package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 15/04/16.
 */
public class DialogGetDatePicker extends FermatDialog {
    String formatedDate;

    public DialogGetDatePicker(Context activity, FermatSession referenceAppFermatSession, ResourceProviderManager resources) {
        super(activity, referenceAppFermatSession, resources);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnselect = (Button) findViewById(R.id.buttondialog);
        DatePicker date = (DatePicker) findViewById(R.id.ChtdatePicker);
        int day = date.getDayOfMonth();
        int month = date.getMonth();
        int year = date.getYear();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        formatedDate = sdf.format(new Date(year, month, day));
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: solo para testeo, FALTA AÑADIR COSAS
                dismiss();
            }
        });
    }

    public String getDatePick() {
        return formatedDate;
    }

    public Boolean isGetDatePick() {
        if (getDatePick() != null && getDatePick() != "") return true;
        else return false;
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
