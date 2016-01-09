package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by nelson on 02/12/15.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private SelectedDate listener;
    private int year, monthOfYear, dayOfMonth;

    public interface SelectedDate {
        void getDate(int year, int monthOfYear, int dayOfMonth);
    }

    public static DatePickerFragment getNewInstance(long timeInMillis, SelectedDate listener) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.listener = listener;

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(timeInMillis);
        datePickerFragment.year = calendar.get(Calendar.YEAR);
        datePickerFragment.monthOfYear = calendar.get(Calendar.MONTH);
        datePickerFragment.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return datePickerFragment;
    }

    public static DatePickerFragment getNewInstance(int year, int monthOfYear, int dayOfMonth, SelectedDate listener) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.listener = listener;
        datePickerFragment.year = year;
        datePickerFragment.monthOfYear = monthOfYear;
        datePickerFragment.dayOfMonth = dayOfMonth;

        return datePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        listener.getDate(year, monthOfYear, dayOfMonth);
    }
}
