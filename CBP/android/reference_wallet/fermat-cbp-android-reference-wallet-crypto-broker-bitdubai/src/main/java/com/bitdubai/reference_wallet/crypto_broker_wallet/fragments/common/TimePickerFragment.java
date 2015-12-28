package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by nelson on 02/12/15.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private SelectedTime listener;
    private int hour, minute;


    public interface SelectedTime {
        void getTime(int hourOfDay, int minute);
    }

    public static TimePickerFragment getNewInstance(long timeInMillis, SelectedTime listener) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.listener = listener;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        timePickerFragment.hour = calendar.get(Calendar.HOUR_OF_DAY);
        timePickerFragment.minute = calendar.get(Calendar.MINUTE);

        return timePickerFragment;
    }

    public static TimePickerFragment getNewInstance(int hour, int minute, SelectedTime listener) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.listener = listener;
        timePickerFragment.hour = hour;
        timePickerFragment.minute = minute;

        return timePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        boolean is24HourFormat = DateFormat.is24HourFormat(getActivity());
        return new TimePickerDialog(getActivity(), this, hour, minute, is24HourFormat);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        listener.getTime(hourOfDay, minute);
    }
}
