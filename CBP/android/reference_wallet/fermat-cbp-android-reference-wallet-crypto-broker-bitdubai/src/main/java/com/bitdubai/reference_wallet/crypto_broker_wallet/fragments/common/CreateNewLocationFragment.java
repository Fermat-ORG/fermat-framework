package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_api.layer.all_definition.enums.Country;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.List;


public class CreateNewLocationFragment extends AbstractFermatFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    // Data
    private Country[] countries;
    private Country selectedCountry;

    // UI
    private FermatEditText cityTextView;
    private FermatEditText stateEditText;
    private FermatEditText zipCodeEditText;
    private FermatEditText addressLineOneEditText;
    private FermatEditText addressLineTwoEditText;


    public static CreateNewLocationFragment newInstance() {
        return new CreateNewLocationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.cbw_fragement_create_new_location, container, false);

        countries = Country.values();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.cbw_spinner_item, getListOfCountryNames(countries));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner countrySpinner = (Spinner) layout.findViewById(R.id.cbw_country_spinner);
        countrySpinner.setOnItemSelectedListener(this);
        countrySpinner.setAdapter(adapter);
        //countrySpinner.setSelection(0);

        cityTextView = (FermatEditText) layout.findViewById(R.id.cbw_city_edit_text);

        stateEditText = (FermatEditText) layout.findViewById(R.id.cbw_state_edit_text);
        zipCodeEditText = (FermatEditText) layout.findViewById(R.id.cbw_zip_code_edit_text);
        addressLineOneEditText = (FermatEditText) layout.findViewById(R.id.cbw_address_line_1_edit_text);
        addressLineTwoEditText = (FermatEditText) layout.findViewById(R.id.cbw_address_line_2_edit_text);

        layout.findViewById(R.id.cbw_create_new_location_button).setOnClickListener(this);

        return layout;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCountry = countries[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        StringBuilder location = new StringBuilder();

        if (selectedCountry != null)
            location.append(selectedCountry.getCountry()).append(", ");

        if (cityTextView.getText().toString().length() > 0)
            location.append(cityTextView.getText().toString()).append(", ");

        if (stateEditText.getText().toString().length() > 0)
            location.append(stateEditText.getText().toString()).append(", ");

        if (zipCodeEditText.getText().toString().length() > 0)
            location.append(zipCodeEditText.getText().toString()).append(", ");

        if (addressLineOneEditText.getText().toString().length() > 0)
            location.append(addressLineOneEditText.getText().toString()).append(", ");

        if (addressLineTwoEditText.getText().toString().length() > 0)
            location.append(addressLineTwoEditText.getText().toString()).append(", ");

        if (location.length() > 0) {
            List<String> locations = (List<String>) appSession.getData(CryptoBrokerWalletSession.LOCATION_LIST);
            locations.add(location.toString());

            changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS, appSession.getAppPublicKey());

        } else {
            Toast.makeText(getActivity(), "Need to set the fields", Toast.LENGTH_LONG).show();
        }
    }

    private List<String> getListOfCountryNames(Country[] countries) {
        List<String> data = new ArrayList<>();

        for (int i = 0; i < countries.length; i++)
            data.add(countries[i].getCountry());

        return data;
    }
}
