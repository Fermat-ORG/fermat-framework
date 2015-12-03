package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by nelson on 02/12/15.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private java.text.DateFormat dateFormat;
    private Calendar calendar;

    private View view;

    public static DatePickerFragment getNewInstance(View callingView) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.view = callingView;

        return datePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateFormat = DateFormat.getDateFormat(getActivity());

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            calendar.set(year, monthOfYear, dayOfMonth);
            textView.setText(dateFormat.format(calendar.getTime()));
        }
    }
}
