package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by nelson on 02/12/15.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private Calendar calendar = Calendar.getInstance();
    private java.text.DateFormat dateFormat;
    private View view;

    public static TimePickerFragment getNewInstance(View callingView) {
        TimePickerFragment datePickerFragment = new TimePickerFragment();
        datePickerFragment.view = callingView;

        return datePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateFormat = DateFormat.getTimeFormat(getActivity());

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(getActivity());

        return new TimePickerDialog(getActivity(), this, hour, minute, is24HourFormat);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        if (view instanceof TextView) {

            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            TextView textView = (TextView) view;
            textView.setText(dateFormat.format(calendar.getTime()));
        }
    }
}

