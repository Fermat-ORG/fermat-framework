package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;


import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNegotiationDetailsFragment extends FermatWalletFragment {


    public OpenNegotiationDetailsFragment() {
        // Required empty public constructor
    }

    public static OpenNegotiationDetailsFragment newInstance() {
        return new OpenNegotiationDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cbw_fragment_negotiation_details_activity, container, false);

        final Button paymentDateValue = (Button) rootView.findViewById(R.id.payment_date_value);
        paymentDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = DatePickerFragment.getNewInstance(paymentDateValue);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });

        final Button paymentTimeValue = (Button) rootView.findViewById(R.id.payment_time_value);
        paymentTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = TimePickerFragment.getNewInstance(paymentTimeValue);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });

        final Button deliveryDateValue = (Button) rootView.findViewById(R.id.delivery_date_value);
        deliveryDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = DatePickerFragment.getNewInstance(deliveryDateValue);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });

        final Button deliveryTimeValue = (Button) rootView.findViewById(R.id.delivery_time_value);
        deliveryTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = TimePickerFragment.getNewInstance(deliveryTimeValue);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });

        final Button expirationDateValue = (Button) rootView.findViewById(R.id.expiration_date_value);
        expirationDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = DatePickerFragment.getNewInstance(expirationDateValue);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });

        final Button expirationTimeValue = (Button) rootView.findViewById(R.id.expiration_time_value);
        expirationTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = TimePickerFragment.getNewInstance(expirationTimeValue);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });

        return rootView;
    }


}
