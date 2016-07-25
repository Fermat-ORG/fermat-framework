package com.bitdubai.reference_wallet.crypto_customer_wallet.common.dialogs;

import android.app.Activity;

import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.DatePickerFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.TimePickerFragment;

import java.util.Calendar;

/**
 * Created by Yordin Alayn on 30/01/16.
 */
public class ClauseDateTimeDialog implements DatePickerFragment.SelectedDate, TimePickerFragment.SelectedTime/*, View.OnClickListener*/ {

    private Activity activity;
    private Calendar calendar;
    private long selectedValue;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;

    private OnClickAcceptListener acceptBtnListener;

    public interface OnClickAcceptListener {
        void getDate(long selectedValue);
    }

    public ClauseDateTimeDialog(Activity activity, long timeInMillis) {

        this.activity = activity;

        bind(timeInMillis);
    }

    @Override
    public void getDate(int year, int monthOfYear, int dayOfMonth) {
//        if (!valuesHasChanged) valuesHasChanged = this.year != year;
//        if (!valuesHasChanged) valuesHasChanged = this.monthOfYear != monthOfYear;
//        if (!valuesHasChanged) valuesHasChanged = this.dayOfMonth != dayOfMonth;

        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;

        calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
        selectedValue = calendar.getTimeInMillis();
        acceptBtnListener.getDate(selectedValue);
    }

    @Override
    public void getTime(int hourOfDay, int minute) {
//        if (!valuesHasChanged) valuesHasChanged = this.hourOfDay != hourOfDay;
//        if (!valuesHasChanged) valuesHasChanged = this.minute != minute;

        this.hourOfDay = hourOfDay;
        this.minute = minute;

        calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
        selectedValue = calendar.getTimeInMillis();
        acceptBtnListener.getDate(selectedValue);
    }

    public void bind(long timeInMillis) {

        selectedValue = timeInMillis;

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedValue);

        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

    }

    public void getDateDialog() {
        DatePickerFragment datePicker = DatePickerFragment.getNewInstance(year, monthOfYear, dayOfMonth, this);
        datePicker.show(activity.getFragmentManager(), "datePicker");
    }

    public void getTimeDialog() {
        TimePickerFragment timePicker = TimePickerFragment.getNewInstance(hourOfDay, minute, this);
        timePicker.show(activity.getFragmentManager(), "timePicker");
    }

    public void setAcceptBtnListener(OnClickAcceptListener acceptBtnListener) {
        this.acceptBtnListener = acceptBtnListener;
    }

}
