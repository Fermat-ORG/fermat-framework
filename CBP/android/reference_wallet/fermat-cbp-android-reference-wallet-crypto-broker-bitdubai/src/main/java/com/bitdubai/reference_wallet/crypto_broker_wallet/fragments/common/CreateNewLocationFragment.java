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
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_api.layer.all_definition.enums.Country;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.List;


public class CreateNewLocationFragment extends AbstractFermatFragment {

    private Country[] countries;
    private Country selectedCountry;


    public static CreateNewLocationFragment newInstance() {
        return new CreateNewLocationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.cbw_fragement_create_new_location, container, false);

        countries = Country.values();

        final Spinner countrySpinner = (Spinner) layout.findViewById(R.id.cbw_country_spinner);
        countrySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = countries[position];
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getList(countries));
        countrySpinner.setAdapter(adapter);
        countrySpinner.setSelection(0);

        final FermatEditText cityTextView = (FermatEditText) layout.findViewById(R.id.cbw_city_edit_text);

        final FermatEditText stateEditText = (FermatEditText) layout.findViewById(R.id.cbw_state_edit_text);
        final FermatEditText zipCodeEditText = (FermatEditText) layout.findViewById(R.id.cbw_zip_code_edit_text);
        final FermatEditText addressLineOneEditText = (FermatEditText) layout.findViewById(R.id.cbw_address_line_1_edit_text);
        final FermatEditText addressLineTwoEditText = (FermatEditText) layout.findViewById(R.id.cbw_address_line_2_edit_text);


        final FermatButton createLocationButton = (FermatButton) layout.findViewById(R.id.cbw_create_new_location_button);
        createLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer location = new StringBuffer();

                location.append(selectedCountry.getCountry());

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
        });
        return layout;
    }

    private List<String> getList(Country[] countries) {
        List<String> data = new ArrayList<>();

        for (int i = 0; i < countries.length; i++)
            data.add(countries[i].getCountry());

        return data;
    }
}
