package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;


/**
 * FERMAT-ORG
 * Developed by Lozadaa on 15/04/16.
 */
public class DialogGetTimePicker extends FermatDialog {
    String formatedDate;
    String time_select;
    public DialogGetTimePicker(Context activity, FermatSession referenceAppFermatSession, ResourceProviderManager resources) {
        super(activity, referenceAppFermatSession, resources);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnselect = (Button) findViewById(R.id.buttondialog);
        TimePicker time = (TimePicker) findViewById(R.id.ChtTimePicker);
        int   hour  = time.getHour();
        int   minute = time.getMinute();

        time_select = hour+":"+minute;
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: solo para testeo, FALTA AÑADIR COSAS
                dismiss();
            }
        });
    }
    public String getTimeSelect(){return time_select; }
    public Boolean isGetTimeSelect(){if(getTimeSelect() != null && getTimeSelect() != "") return true; else return false;}
    @Override
    protected int setLayoutId() {
        return R.layout.cht_dialog_time_picker;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }
}
