package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.StepItemGetter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.DatePickerFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.TimePickerFragment;

import java.util.Calendar;


public class DateTimeStepViewHolder extends StepViewHolder
        implements View.OnClickListener, DatePickerFragment.SelectedDate, TimePickerFragment.SelectedTime {

    private Calendar calendar;
    private java.text.DateFormat dateFormat;
    private java.text.DateFormat timeFormat;

    CryptoCustomerWalletManager walletManager;
    private Activity activity;
    private RecyclerView.Adapter adapter;

    private Button buttonDate;
    private Button buttonTime;
    private TextView descriptionTextView;
    private long selectedValue;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;


    public DateTimeStepViewHolder(RecyclerView.Adapter adapter, View viewItem, Activity activity, CryptoCustomerWalletManager walletManager) {
        super(viewItem, (StepItemGetter) adapter);

        this.adapter = adapter;
        this.activity = activity;
        timeFormat = DateFormat.getTimeFormat(activity);
        dateFormat = DateFormat.getDateFormat(activity);

        this.walletManager = walletManager;

        descriptionTextView = (TextView) viewItem.findViewById(R.id.ccw_date_time_description_text);
        buttonDate = (Button) viewItem.findViewById(R.id.ccw_date_value);
        buttonDate.setOnClickListener(this);
        buttonTime = (Button) viewItem.findViewById(R.id.ccw_time_value);
        buttonTime.setOnClickListener(this);
    }

    public void bind(int clauseNumber, int titleRes, int descriptionRes, String timeInMillis) {
        long timeInMillisLong = Long.valueOf(timeInMillis);
        bind(clauseNumber, titleRes, descriptionRes, timeInMillisLong);
    }

    public void bind(int stepNumber, int titleRes, int descriptionRes, long timeInMillis) {
        super.bind(stepNumber);

        selectedValue = timeInMillis;

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedValue);

        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        titleTextView.setText(titleRes);
        descriptionTextView.setText(descriptionRes);
        buttonTime.setText(timeFormat.format(selectedValue));
        buttonDate.setText(dateFormat.format(selectedValue));
    }

    @Override
    public void getDate(int year, int monthOfYear, int dayOfMonth) {
        if (!valuesHasChanged) valuesHasChanged = this.year != year;
        if (!valuesHasChanged) valuesHasChanged = this.monthOfYear != monthOfYear;
        if (!valuesHasChanged) valuesHasChanged = this.dayOfMonth != dayOfMonth;

        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;

        calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
        selectedValue = calendar.getTimeInMillis();
        buttonDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void getTime(int hourOfDay, int minute) {
        if (!valuesHasChanged) valuesHasChanged = this.hourOfDay != hourOfDay;
        if (!valuesHasChanged) valuesHasChanged = this.minute != minute;

        this.hourOfDay = hourOfDay;
        this.minute = minute;

        calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
        selectedValue = calendar.getTimeInMillis();
        buttonTime.setText(timeFormat.format(calendar.getTime()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == buttonDate.getId()) {
            DialogFragment datePicker = DatePickerFragment.getNewInstance(year, monthOfYear, dayOfMonth, this);
            datePicker.show(activity.getFragmentManager(), "datePicker");

        } else if (view.getId() == buttonTime.getId()) {
            DialogFragment timePicker = TimePickerFragment.getNewInstance(hourOfDay, minute, this);
            timePicker.show(activity.getFragmentManager(), "timePicker");
        }

    }

    @Override
    public void setStatus(NegotiationStepStatus stepStatus) {
        super.setStatus(stepStatus);

        switch (stepStatus) {
            case ACCEPTED:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                break;
            case CHANGED:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                break;
            case CONFIRM:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                break;
        }
    }

    @Override
    protected void modifyData(NegotiationStepStatus stepStatus) {
        super.modifyData(stepStatus);

        NegotiationStep step = stepItemGetter.getItem(itemPosition);
        //TODO walletManager.modifyNegotiationStepValues(step, stepStatus, String.valueOf(selectedValue));
        adapter.notifyItemChanged(itemPosition);
    }
}
